## code to prepare `inst/extdata/prism_issr800_sample.tif` dataset goes here
library(terra)
library(soilDB)

# contiguous lower 48 state extent
x <- c(newhall_prism_rast(), newhall_issr800_rast())
x$elev <- 0L # TODO: add elev to newhall_prism_rast()

#### SAMPLE EXTENT: Calaveras/Tuolumne county areas/Stanislaus National Forest
# b <- soilDB::fetchSDA_spatial(c("CA630", "CA731"), "areasymbol", geom="sapolygon")
# sapply(ext(vect(b)), as.numeric)
#>       xmin       xmax       ymin       ymax 
#> -120.99548 -119.59645   37.63352   38.58204 

y <- terra::crop(x, ext(-120.99548, -119.59645, 37.63352, 38.58204))

# check that it works directly with `newhall_batch()`
res <- try({
  z <- newhall_batch(y)
  terra::plot(z$numCumulativeDaysMoistOver5C)
})

# if it worked, write to file
if (!inherits(res, 'try-error')) {
  terra::writeRaster(y, filename = "inst/extdata/prism_issr800_sample.tif", 
                     overwrite = TRUE)
}
