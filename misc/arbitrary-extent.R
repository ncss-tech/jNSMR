# arbitrary extent
library(terra)
library(jNSMR)

ext <-  vect("D:/CA732/Geodata/CA732_Derived.sqlite", "CA732_wilderness_areas")[1,]

newhall_extent <- function(ext, r, awc = 200, cores = 1, nrows = 40) {
  if (is.numeric(awc)) {
    r$awc <- terra::classify(r$pJan, matrix(c(-Inf, Inf, awc[1]), ncol = 3), filename = "")
  } else if (inherits(r, 'SpatRaster')) {
    r$awc <- awc
  }
  r2 <- terra::crop(r, y = ext, filename = "")
  t0 <- Sys.time()
  resbig <- newhall_batch(r2, cores = cores, nrows = nrows)
  t1 <- Sys.time()

  message("Elapsed time: ", format(signif(t1 - t0, 3), units = "auto"))
  resbig
}

r <- terra::rast("prism_in_800.tif")
x <- newhall_extent(ext, r)

plot(x$dryDaysAfterSummerSolstice)
plot(x$temperatureRegime)

r <- raster::raster(x$temperatureRegime)
levels(r) <- data.frame(ID=0:9, smr=jNSMR:::.str())
mapview::mapview(r)

plot(x$moistureRegime)
r <- raster::raster(x$moistureRegime)
levels(r) <- data.frame(ID=0:5, smr=jNSMR:::.smr())
mapview::mapview(r)
