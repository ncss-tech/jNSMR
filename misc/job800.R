# use three cores, processing 2 rows at a time (14k cells per block, each core takes 1/3)
library(terra)
library(jNSMR)

test <- FALSE
r <- terra::rast("prism_in_800.tif")
# r <- aggregate(r, 20)

# # # NA-masked static AWC
v <- rep(NA, ncell(r))
v[!is.na(values(r$pJan))] <- 200
values(r$awc) <- v

# ISSR-800 AWC
# download.file(
#   "https://soilmap2-1.lawr.ucdavis.edu/800m_grids/rasters/water_storage.tif",
#   destfile = "water_storage.tif",
#   mode = "wb"
# )
awc <- rast("water_storage.tif")
terra::crs(awc) <- "EPSG:5070"
awc <- project(awc, r)

# global mean for all of conus is near the default newhall value of 200mm
mean(values(awc) * 10, na.rm = TRUE)
#> [1] 197.82760 # 200 (mm water) is default

# # +/- 1 standard deviation ~[100,300]mm
sd(values(awc)*10, na.rm=T)
# #> [1] 97.52501

# # convert cm -> mm?
v <- values(awc) * 10
r$awc <- v#rep(NA, ncell(r))

plot(r$awc)

# test extent
if(test) r2 <- crop(r, ext(r) / 40) else r2 <- r
r2 <- crop(r, vect("D:/CA732/Geodata/CA732_Derived.sqlite", "ca732_wbdhu12"))
plot(r2$awc)

plot(vect("D:/CA732/Geodata/CA732_Derived.sqlite", "ca732_b"), add = T)
plot(density(values(r2$awc), na.rm = TRUE))
quantile(values(r2$awc), na.rm = TRUE)

r2$stationName <- NULL; r2$stationID <- NULL; r2$notes <- NULL; r2$stProvCode <- NULL; r2$netType <- NULL; r2$cntryCode <- NULL; r2$pdEndYr <- NULL; r2$pdStartYr <- NULL; r2$pdType <- NULL; r2$maatmast <- NULL

#system.time(resbig <- newhall_batch(r2))
system.time(resbig <-  newhall_batch(r2,
                                     cores = 6,
                                     nrows = ifelse(ncell(r2) > 100000, 16, 40)))
terra::writeRaster(resbig, filename = "newhall_ca732_800m_200mm.tif", overwrite = TRUE)

# 44% of the full raster is NA
# sum(is.na(values(r$pJan))) / ncell(r)

plot(resbig$waterHoldingCapacity)
plot(resbig$temperatureRegime)
plot(resbig$moistureRegime)
plot(resbig$regimeSubdivision1)
plot(resbig$regimeSubdivision2)
plot(vect("D:/CA732/Geodata/CA732_Derived.sqlite", "ca732_b"), add = T)
