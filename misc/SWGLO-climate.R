library(jNSMR) # remotes::install_github("ncss-tech/jNSMR")
library(soilDB)
library(terra)
library(rgeedim)

nominal_resolution <- 100
location_string <- "MLRA38+39"
soil_air_offset <- 2.5
awc_issr800 <- TRUE
model_O_horizon <- FALSE

x <- vect(fetchSDA_spatial(c("38", "39"),
                           by.col = "MLRARSYM",
                           geom.src = "mlrapolygon"))
x <- buffer(aggregate(x), 25000)

# get PRISM climate and SSURGO-derived available water storage data
predictors <- c(newhall_prism_subset(x), newhall_issr800_subset(x))

# take 9x9 focal window to smooth/remove NoData in AWC
if (!awc_issr800) {
  predictors$awc[!is.na(predictors$awc)] <- 200
}
predictors$awc <- focal(predictors$awc, 9, fun = "mean", na.rm = TRUE)

if (model_O_horizon){
  # use Lang et al. (2023) canopy height <https://www.nature.com/articles/s41559-023-02206-6>
  if (!file.exists("canopy_height.tif")) {
    gd_initialize()
    
    gd_image_from_id('users/nlang/ETH_GlobalCanopyHeight_2020_10m_v1') |>
      gd_download(
        region = as.polygons(x, ext = TRUE),
        bands = list("b1"),
        filename = "canopy_height.tif",
        scale = 100
      )
  }
  
  h <- classify(rast("canopy_height.tif"), cbind(NA, 0))
  names(h) <- "canopy_height_m"
  
  # inspect
  plot(h)
  
  ## simple model: apply a threshold
  # assume if canopy is 2x minimum for tree height (4m) there is an O horizon
  # predictors$hasOHorizon <- project(h, predictors) > 8
  
  ## logistic regression model
  ##  - simple model (no temperature/wetness included) with just canopy height threshold near 19m 
  mohz <- readRDS("misc/CONUS_O_horizon_by_canopy_height_m.rds") # from logistic-regression-O-horizon.R
  
  # apply cutoff to probabilities
  predictors$hasOHorizon <- project(predict(h, mohz), predictors) > 0.5
} else {
  predictors$hasOHorizon <- FALSE
}

# inspect
plot(predictors$hasOHorizon)

# add elevation
if (!file.exists("elev.tif")) {
  gd_image_from_id('USGS/NED') |>
    gd_download(
      region = as.polygons(x, ext = TRUE),
      bands = list("elevation"),
      filename = "elev.tif",
      scale = 100
    )
}
e <- rast("elev.tif")
predictors$elev <- project(e, predictors)

# run batches of models on gridded data
res <- newhall_batch(
    predictors,
    core_thresh = 2e5,
    cores = 12,
    soilAirOffset = soil_air_offset
  )

y <- fetchSDA_spatial(SDA_spatialQuery(predictors, "areasymbol")$areasymbol,
                      by.col = "areasymbol",
                      geom.src = "sapolygon")

# inspect one modeled soil taxonomic property
plot(
  res$dryDaysAfterSummerSolstice,
  main = "dryDaysAfterSummerSolstice",
  col = grDevices::hcl.colors(50, "cividis")
)
plot(as.lines(vect(y)), add = TRUE, col = "darkorange")

# apply xeric threshold (dry >1.5 months after summer solstice)
plot(
  res$dryDaysAfterSummerSolstice > 45,
  main = "dryDaysAfterSummerSolstice",
  col = grDevices::hcl.colors(2, "cividis")
)
plot(as.lines(vect(y)), add = TRUE, col = "darkorange")

plot(res$temperatureRegime == "Frigid",
     col = grDevices::hcl.colors(7, "cividis"))
plot(as.lines(vect(y)), add = TRUE, col = "darkorange")

writeRaster(res, paste0("newhall_", nominal_resolution, "m_SAO-",
                        soil_air_offset, "degC_", location_string, 
                        ifelse(awc_issr800, "_ISSR800AWC", ""),
                        ifelse(!model_O_horizon, "_noO", ""), ".tif"),
            overwrite = TRUE)

ss <- spatSample(c(predictors,res), 100000)

split(ss$elev, ss$temperatureRegime) |>
  sapply(quantile, prob = c(0, 0.01, 0.05, 0.1, 0.25, 0.5,
                            0.75, 0.9, 0.95, 0.99, 1)) |>
  round(-1) |>
  t()
