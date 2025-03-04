library(rgeedim)
library(terra)
library(jNSMR)
library(soilDB)
library(whitebox)

gd_initialize()

np <- newhall_prism_rast()

# investigate crisp thresholds applied to global 10m canopy model

## NB: look at MERRA2 derivatives as predictors: https://gmao.gsfc.nasa.gov/reanalysis/MERRA-2/

gd_image_from_id('USGS/3DEP/10m') |>
  gd_download(region = as.polygons(np, ext = TRUE),
              bands = list("elevation"),
              filename = "dem_conus_800m.tif",
              scale = 800)

h <- rast("dem_conus_800m.tif")*1
h <- project(h, np, filename = "dem_conus_800m.tif", overwrite = TRUE)
plot(h)

wbt_flow_accumulation_full_workflow("./dem_conus_800m.tif",
                                    "./dem_conus_800m_acc.tif",
                                    "./dem_conus_800m_pnt.tif",
                                    "./dem_conus_800m_sca.tif")
wbt_slope("./dem_conus_800m.tif", "./dem_conus_800m_slope.tif")
wbt_wetness_index("./dem_conus_800m_sca.tif", 
                  "./dem_conus_800m_slope.tif", 
                  "./dem_conus_800m_wwi.tif")
