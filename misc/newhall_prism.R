# run jNSMR on prism monthly 30yr normals

# path to PRISM data
PRISM_PATH <- "~/Geodata/PRISM"

# PRISM resolution; either 4km or 800m
RESOLUTION <- "4km"

# get the monthly files only (hardcoded for 30yr normals for now)
bilfile <- list.files(PRISM_PATH, "\\.bil$", recursive = TRUE)

# only download the PRISM data if it can't be found in PRISM_PATH
if (length(bilfile) == 0) {
  source("misc/dl_PRISM.R") # download .BIL files if needed
}

# capture file name components
monthly.pattern <- ".*PRISM_([a-z]+)_(30yr_normal)_(\\d+[a-z]+)M2_(\\d{2})_bil\\.bil"
bilfile_sub <- bilfile[grep(monthly.pattern, bilfile)]
bilmonth <- strsplit(gsub(monthly.pattern,  "\\1;\\2;\\3;\\4", bilfile_sub), ";")
bil <- data.frame(bilfile_sub, do.call('rbind', bilmonth))
colnames(bil) <- c("bilfile","variable","product","resolution","month")
bilsub <- subset(bil, resolution == RESOLUTION)

# stack rasters, create matrix of values and XY (longlat) locations as data.frame
prism_stack <- raster::stack(file.path(PRISM_PATH, bilsub$bilfile))
names(prism_stack) <- paste0(bilsub$variable, bilsub$month)
prism_frame <- as.data.frame(raster::rasterToPoints(prism_stack))

# fix column names for jNSM style batch input format
monthnames <- month.abb[sapply(names(prism_frame), function(x) as.integer(gsub("([a-z])", "", x)))]
newnames <- gsub("tmean", "t", names(prism_frame))
newnames <- gsub("ppt", "p", newnames)
newnames <- gsub("\\d", "", newnames)
newnames <- paste0(newnames, ifelse(is.na(monthnames), "", monthnames))
newnames[1:2] <- c("lonDD","latDD")
names(prism_frame) <- newnames

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

# make test batch of prism point data values s
test_set <- prism_frame[round(runif(10000, 1, nrow(prism_frame))),]

# write to file
write.csv(test_set, file = "misc/prism_monthly.csv")

# read batch file, run simulations
res <- jNSMR::newhall_batch(pathname = "misc/prism_monthly.csv")

system.time(res2 <- data.table::data.table(res)[, .format_results(results[[1]]), by = list(1:nrow(res))])

newsp <- sf::st_as_sf(cbind(test_set[,c('lonDD','latDD')], res2), coords = c('lonDD','latDD'))
plot(newsp$geometry, col=factor(newsp$TemperatureRegime))
plot(newsp$geometry, col=factor(newsp$MoistureRegime))
plot(newsp$geometry, col=factor(newsp$RegimeSubdivision1))
plot(newsp$geometry, col=factor(newsp$RegimeSubdivision2))

newspsub <- subset(newsp, MoistureRegime %in% c("Xeric","Aridic","Ustic","Udic"))
plot(newspsub$geometry, col=factor(newspsub$MoistureRegime), pch=".", cex=5)

table(newspsub$RegimeSubdivision1)

newspsub2 <- subset(newsp, MoistureRegime %in% c("Aridic","Xeric"))
legnames <- factor(paste(newspsub2$RegimeSubdivision1, newspsub2$MoistureRegime))
nlegnames <- unique(levels(legnames))
nlegcolors <- viridisLite::viridis(length(nlegnames))
plot(newspsub2$geometry, col = nlegcolors[match(legnames, nlegnames)], pch=".", cex=5)
plot(spData::us_states$geometry, add=T)
legend("bottomleft", legend = nlegnames, pch=".", fill = nlegcolors)

