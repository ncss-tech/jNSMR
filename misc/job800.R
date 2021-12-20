# use three cores, processing 2 rows at a time (14k cells per block, each core takes 1/3)
library(terra)

library(jNSMR)
test <- FALSE
r <- terra::rast("prism_in_800.tif")
# if(test) r <- crop(r, ext(r) / 20)
system.time(resbig <- newhall_batch(r, cores = 3, nrows = 2))
terra::writeRaster(resbig, "newhall_results_800_20211217.tif", overwrite=TRUE)

plot(resbig$temperatureRegime)
plot(resbig$moistureRegime)
plot(resbig$regimeSubdivision1)
plot(resbig$regimeSubdivision2)

