# run jNSMR on prism monthly 30yr normals

library(terra)
library(jNSMR)

# PRISM resolution; either 4km or 800m
RESOLUTION <- "4km"

# path to PRISM data
PRISM_PATH <- file.path("~/Geodata/PRISM", RESOLUTION)

bilfile <- list.files(PRISM_PATH, "\\.bil$", recursive = TRUE)

# only download the PRISM data if it can't be found in PRISM_PATH
if (length(bilfile) == 0) {
  source("misc/dl_PRISM.R") # download .BIL files if needed
  # get the monthly files only (hardcoded for 30yr normals for now)
  bilfile <- list.files(PRISM_PATH, "\\.bil$", recursive = TRUE)
}

# capture file name components
monthly.pattern <- ".*PRISM_([a-z]+)_(30yr_normal)_(\\d+[a-z]+)M2_(\\d{2})_bil\\.bil"
bilfile_sub <- bilfile[grep(monthly.pattern, bilfile)]
bilmonth <- strsplit(gsub(monthly.pattern,  "\\1;\\2;\\3;\\4", bilfile_sub), ";")
bil <- data.frame(bilfile_sub, do.call('rbind', bilmonth))
colnames(bil) <- c("bilfile","variable","product","resolution","month")
bilsub <- subset(bil, resolution == RESOLUTION)

# stack rasters, create matrix of values and XY (longlat) locations as data.frame
# prism_stack <- raster::stack(file.path(PRISM_PATH, bilsub$bilfile))

# terra object
prism_rast <- terra::rast(file.path(PRISM_PATH, bilsub$bilfile))

# fix column names for jNSM style batch input format
monthnames <- month.abb[as.integer(gsub(".*([0-9]{2})_bil$", "\\1", names(prism_rast)))]
names(prism_rast) <- paste0(gsub("ppt", "p", gsub("tmean","t", 
                            gsub("PRISM_(ppt|tmean)_.*[0-9]{2}_bil$", "\\1",
                            names(prism_rast)))),  
                            ifelse(is.na(monthnames), "", monthnames))

# add longitude and latitude layers
cx <- terra::xyFromCell(prism_rast, 1:ncell(prism_rast))
prism_rast$lonDD <- cx[,1]
prism_rast$latDD <- cx[,2]

# crop for example
bdy <- soilDB::fetchSDA_spatial(c("CA067","CA620","CA628", "CA630", "CA649", "CA654", "CA651"), "areasymbol", geom.src = "sapolygon")
                                #c("CA067","CA620","CA628", "CA630", "CA649", "CA654", "CA651")
x <- terra::crop(prism_rast, terra::vect(bdy))

# PRISM info
x$stationName <- 1:ncell(x)
x$awc <- 200
x$maatmast <- 1.2
x$pdType <- "Normal"
x$pdStartYr <- 1981
x$pdEndYr <- 2010
x$cntryCode <- "US"

# boilerplate minimum metadata -- these are not used by the algorithm
x$netType <- ""
x$elev <- -9999
x$stProvCode <- ""
x$notes <- ""
x$stationID <-1:ncell(x)

# .data <- x

# S3 newhall_batch.SpatRaster method
# system.time(res <- jNSMR::newhall_batch(x, nrows = 20, cores = 2))
system.time(res <- jNSMR::newhall_batch(x, nrows = 20))

# inspect result
bb <- sf::st_as_sf(as(raster::extent(raster::raster(res)), 'SpatialPolygons'))
sf::st_crs(bb) <- 4326
sapoly <- soilDB::SDA_spatialQuery(bb, what = 'sapolygon')
plot(res$NumCumulativeDaysDryOver5C, main = "# Cumulative Days Dry over 5degC\nw/ SAPOLYGON geometry")
plot(sf::st_geometry(sf::st_cast(bb, 'MULTILINESTRING')), add = TRUE, lwd = 2, col = "RED")
plot(sf::st_geometry(sapoly), add = TRUE)

# compare areasymbol-summaries of STR/SMR with distribution of classes
res2 <- soilDB::SDA_query(sprintf( 
"SELECT areasymbol, taxtempregime, SUM(comppct_r*muacres)/100 AS acres FROM legend
   INNER JOIN mapunit ON mapunit.lkey = legend.lkey
   INNER JOIN component ON component.mukey = mapunit.mukey AND majcompflag = 'Yes'
   WHERE areasymbol IN %s
   GROUP BY areasymbol, taxtempregime", 
soilDB::format_SQL_in_statement(unique(sapoly$areasymbol))))

dstr <- dplyr::group_by(res2, areasymbol) |> 
  dplyr::filter(!is.na(taxtempregime)) |> 
  dplyr::arrange(dplyr::desc(acres)) |> 
  dplyr::slice(1)
sapoly2 <- dplyr::left_join(sapoly, dstr)

plot(res$NumCumulativeDaysDryOver5C,
     main = "# Cumulative Days MoistDryOver5C\nw/ SAPOLYGON geometry")
plot(sf::st_geometry(sapoly2), add=TRUE)
plot(sapoly2['taxtempregime'])

# data.frame interface
prism_frame <- as.data.frame(terra::as.points(prism_rast))

# PRISM info
prism_frame$stationName <- 1:nrow(prism_frame)
prism_frame$awc <- 200
prism_frame$maatmast <- 1.2
prism_frame$pdType <- "Normal"
prism_frame$pdStartYr <- 1981
prism_frame$pdEndYr <- 2010
prism_frame$cntryCode <- "US"

# boilerplate minimum metadata -- these are not used by the algorithm
prism_frame$netType <- ""
prism_frame$elev <- -9999
prism_frame$stProvCode <- ""
prism_frame$notes <- ""
prism_frame$stationID <- 1:nrow(prism_frame)

# make test batch of prism point data values
test_set <- prism_frame[round(runif(20000, 1, nrow(prism_frame))),]

# write to file
afile <- tempfile()
write.csv(test_set, file = afile) #"misc/prism_monthly.csv")
test_set <- read.csv(afile)#"misc/prism_monthly.csv")

# read batch file(s), run simulations. 
#  using newhall_batch.character S3 wrapper for vector of batch file paths
system.time({res <- jNSMR::newhall_batch(afile)}) #"misc/prism_monthly.csv")

newsp <- sf::st_as_sf(cbind(test_set[,c('lonDD','latDD')], res), coords = c('lonDD','latDD'))
sf::st_crs(newsp) <- sf::st_crs(4326)
plot(newsp$geometry, col=factor(newsp$TemperatureRegime))
plot(newsp$geometry, col=factor(newsp$MoistureRegime))
plot(newsp$geometry, col=factor(newsp$RegimeSubdivision1))
plot(newsp$geometry, col=factor(newsp$RegimeSubdivision2))
newspsub <- subset(newsp, MoistureRegime %in% c("Xeric","Aridic","Ustic","Udic"))
legnames <- factor(paste(newspsub$MoistureRegime))
nlegnames <- unique(levels(legnames))
nlegcolors <- rev(viridisLite::viridis(length(nlegnames)))
plot(sf::st_transform(newspsub, sf::st_crs(5070))$geometry,
     col = nlegcolors[match(legnames, nlegnames)], pch=".", cex=5)
plot(sf::st_transform(spData::us_states$geometry, sf::st_crs(5070)), add = TRUE)
legend("topright", legend = nlegnames, pch=".", fill = nlegcolors)

table(newspsub$RegimeSubdivision1)

newspsub2 <- subset(newsp, MoistureRegime %in% c("Aridic","Xeric","Ustic"))
legnames <- factor(paste(newspsub2$RegimeSubdivision1, newspsub2$MoistureRegime))
nlegnames <- unique(levels(legnames))
nlegcolors <- viridisLite::viridis(length(nlegnames))
plot(sf::st_transform(newspsub2, sf::st_crs(5070))$geometry,
     col = nlegcolors[match(legnames, nlegnames)], pch = ".", cex = 5)
plot(sf::st_transform(spData::us_states$geometry, sf::st_crs(5070)), add = TRUE)
legend("bottomleft", legend = nlegnames, pch=".", fill = nlegcolors)

mlra <- sf::st_read("E:/Geodata/soils/mlra/mlra_v42.shp")
mlra <- sf::st_transform(mlra, sf::st_crs(5070))

plot(subset(mlra, LRRSYM == 'C')$geometry)
plot(sf::st_transform(newspsub2, sf::st_crs(5070))$geometry,
     col = nlegcolors[match(legnames, nlegnames)], pch = ".", cex = 5, add=TRUE)
legend("bottomleft", legend = nlegnames, pch=".", fill = nlegcolors)

