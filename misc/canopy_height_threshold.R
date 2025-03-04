library(rgeedim)
library(terra)
library(jNSMR)
library(soilDB)

gd_initialize()

np <- newhall_prism_rast()

# investigate crisp thresholds applied to global 10m canopy model

gd_image_from_id('users/nlang/ETH_GlobalCanopyHeight_2020_10m_v1') |>
  gd_download(region = as.polygons(np, ext = TRUE),
              bands = list("b1"),
              filename = "misc/canopy_height_conus_800m.tif",
              scale = 800)

h <- classify(rast("misc/canopy_height_conus_800m.tif"), cbind(NA, 0))
h <- project(h, np, filename = "misc/canopy_height_conus_800m.tif", overwrite = TRUE)
plot(h > 4)

x <- fetchSDA_spatial(c("CA077", "CA067", "CA628",
                        "CA630", "CA731", "CA649",
                        "CA644", "CA632"), 
                      by.col = "areasymbol", 
                      geom.src = "SAPOLYGON")

plot(crop(h, project(vect(x), h)) > 8)
plot(project(vect(x), h), add = TRUE)
