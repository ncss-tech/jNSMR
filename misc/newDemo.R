library(jNSMR)
library(soilDB)
library(terra)
library(rgeedim)

x <- fetchSDA_spatial(c("CA067", "CA628", "CA731", "CA729", "CA732"), 
                      by.col = "areasymbol",
                      geom.src = "sapolygon")

# get PRISM climate and SSURGO-derived available water storage data
predictors <- c(newhall_prism_extent(x),
                newhall_issr800_extent(x))

# take focal window to smooth/remove NoData in AWC
predictors$awc <- focal(predictors$awc, 5, na.rm=TRUE)

# use Lang et al. (2023) canopy height <https://www.nature.com/articles/s41559-023-02206-6>
gd_initialize()

gd_image_from_id('users/nlang/ETH_GlobalCanopyHeight_2020_10m_v1') |> 
  gd_download(region = as.polygons(vect(x), ext = TRUE),
              bands = list("b1"),
              filename="canopy_height.tif",
              scale = 100)
h <- classify(rast("canopy_height.tif"), cbind(NA, 0))

# inspect
plot(h)

# assume if canopy is 2x minimum for tree height (4m) there is an O horizon
predictors$hasOHorizon <- project(h, predictors) > 8

plot(predictors$hasOHorizon)

# add elevation
gd_image_from_id('USGS/NED') |> 
  gd_download(region = as.polygons(vect(x), ext = TRUE),
              bands = list("elevation"),
              filename="elev.tif",
              scale = 100)
e <- rast("elev.tif")
predictors$elev <- project(e, predictors)

# run batches of models on gridded data
res <- newhall_batch(predictors, soilAirOffset = 2, core_thresh = 1e4, cores = 8)

y <- fetchSDA_spatial(SDA_spatialQuery(predictors, "areasymbol")$areasymbol, 
                      by.col = "areasymbol", 
                      geom.src = "sapolygon")
# inspect one modeled soil taxonomic property
plot(
  res$dryDaysAfterSummerSolstice,
  main = "dryDaysAfterSummerSolstice",
  col = grDevices::hcl.colors(10, "cividis")
)
plot(as.lines(vect(y)), add = TRUE, col = "darkorange")

# apply xeric threshold (dry >1.5 months after summer solstice)
plot(
  res$dryDaysAfterSummerSolstice > 45,
  main = "dryDaysAfterSummerSolstice",
  col = grDevices::hcl.colors(2, "cividis")
)
plot(as.lines(vect(y)), add = TRUE, col = "darkorange")

plot(res$temperatureRegime,
     col = grDevices::hcl.colors(4, "cividis"))
plot(as.lines(vect(y)), add = TRUE, col = "darkorange")
