library(jNSMR)
library(terra)

f <- newhall_cmip6_cache("EC-Earth3-Veg",
                    resolution = "2.5m",
                    ssp = "585",
                    time = "2061-2080")

data(us_states, package = "spData")
b <- subset(sf::st_as_sf(us_states), NAME == "California")

x <- newhall_cmip6_subset(b, "EC-Earth3-Veg", 
                          resolution = "2.5m", 
                          ssp = "585",
                          time = "2061-2080")
x$elev <- 0
x$awc <- 200

res <- newhall_batch(x)
par(mfrow = c(2, 1))
plot(res$temperatureRegime, col = hcl.colors(10),
     main = "EC-Earth3-Veg (2.5 minute); SSP: 585; 2061-2080")

y <- newhall_prism_subset(b, "4km")
y$elev <- 0
y$awc <- 200
res2 <- newhall_batch(y)
plot(res2$temperatureRegime, col = hcl.colors(10),
     main = "PRISM Normals (4km); 1991-2020")

ncdm8c1 <- terra::project(res$numConsecutiveDaysMoistInSomePartsOver8C, res2)
df <- res2$numConsecutiveDaysMoistInSomePartsOver8C - ncdm8c1
df <- terra::crop(df, ext(ncdm8c1) * 0.95)
plot(df, col=rev(hcl.colors(50)))
title(main = "\n\nChange in Number Consecutive Days Moist in Some Parts over 8 degrees C\n
     EC-Earth3-Veg (2.5 minute); SSP: 585; 2061-2080\nvs.\nPRISM Normals (4km); 1991-2020")
