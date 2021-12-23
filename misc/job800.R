# use three cores, processing 2 rows at a time (14k cells per block, each core takes 1/3)
library(terra)
library(jNSMR)

test <- FALSE 
r <- terra::rast("prism_in_800.tif")

# NA-masked static AWC
v <- rep(NA, ncell(r))
v[!is.na(values(r$pJan))] <- 100 # 200 (mm water) is default

# ISSR-800 AWC
# download.file("https://soilmap2-1.lawr.ucdavis.edu/800m_grids/rasters/water_storage.tif", destfile = "water_storage.tif")
# awc <- rast("water_storage.tif")
# awc <- project(awc, r)
# # global mean is near the default newhall value of 200mm
# mean(values(awc)*10, na.rm=T)
# #> [1] 197.8276
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

# system.time(resbig <- newhall_batch(r2))
system.time(resbig <- newhall_batch(r2, cores = 6, nrows = ifelse(test, 40, 2)))

terra::writeRaster(resbig, filename = "newhall_800m_100mmAWS.tif", overwrite = TRUE)

# 44% of the full raster is NA
# sum(is.na(values(r$pJan))) / ncell(r)
plot(resbig$waterHoldingCapacity)
plot(resbig$temperatureRegime)
plot(resbig$moistureRegime)
plot(resbig$regimeSubdivision1)
plot(resbig$regimeSubdivision2)

