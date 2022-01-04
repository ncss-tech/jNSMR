# use three cores, processing 2 rows at a time (14k cells per block, each core takes 1/3)
library(terra)
library(jNSMR)

test <- FALSE 
r <- terra::rast("prism_in_800.tif")
r <- aggregate(r, 20)

# # NA-masked static AWC
v <- rep(NA, ncell(r))
v[!is.na(values(r$pJan))] <- 10

# ISSR-800 AWC
# download.file("https://soilmap2-1.lawr.ucdavis.edu/800m_grids/rasters/water_storage.tif", destfile = "water_storage.tif")
# awc <- rast("water_storage.tif")
# awc <- project(awc, r)
# # global mean is near the default newhall value of 200mm
# mean(values(awc)*10, na.rm=T)
# #> [1] 197.82760 # 200 (mm water) is default

# testing blocks of missing data
# awc <- raster::raster("water_storage.tif")
# crs(awc) <- "EPSG:4326"
# awc <- project(awc, r)
# v <- values(awc)
v[1:45000] <- NA
# values(awc) <- v

# 
# # +/- 1 standard deviation ~[100,300]mm
# sd(values(awc)*10, na.rm=T)
# #> [1] 97.52501
# 
# # convert cm -> mm?
# v <- values(awc) * 10 

values(r$awc) <- v

plot(r$awc)

# test extent
if(test) r2 <- crop(r, ext(r) / 40) else r2 <- r

plot(r2$awc)
plot(density(values(r2$awc), na.rm = TRUE))
quantile(values(r2$awc), na.rm = TRUE)

r2$stationName <- NULL; r2$stationID <- NULL; r2$notes <- NULL; r2$stProvCode <- NULL; r2$netType <- NULL; r2$cntryCode <- NULL; r2$pdEndYr <- NULL; r2$pdStartYr <- NULL; r2$pdType <- NULL; r2$maatmast <- NULL

# system.time(resbig <- newhall_batch(r2))
system.time(resbig <- newhall_batch(r2,  cores=6, nrows = ifelse(test, 40, 2)))

terra::writeRaster(resbig, filename = "newhall_800m_100mmAWS.tif", overwrite = TRUE)

# 44% of the full raster is NA
# sum(is.na(values(r$pJan))) / ncell(r)
plot(resbig$waterHoldingCapacity)
plot(resbig$temperatureRegime)
plot(resbig$moistureRegime)
plot(resbig$regimeSubdivision1)
plot(resbig$regimeSubdivision2)

