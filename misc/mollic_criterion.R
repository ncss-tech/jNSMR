library(jNSMR)
library(terra)
data("us_states", package="spData")

x <- rast("newhall_conus_800m_200mm_epsg5070.tif")
v <- project(vect(us_states), x)

plot(x$numCumulativeDaysMoistOver5C,
     col = rev(hcl.colors(360, palette = "cividis")),
     main = "# of Cumulative Days Moist Over 5 degrees C")
plot(as.lines(v), col = "brown", add = TRUE)
plot(x$numCumulativeDaysMoistOver5C < 90,
     col = rev(hcl.colors(2)),
     main = "<90 Cumulative Days Moist Over 5 degrees C")

plot(x$numConsecutiveDaysMoistInSomePartsOver8C,
     col = rev(hcl.colors(360, palette = "cividis")),
     main = "# of Cumulative Days Moist (in some parts) Over 8 degrees C")
plot(as.lines(v), col = "brown", add = TRUE)
plot(x$numConsecutiveDaysMoistInSomePartsOver8C < 90,
     col = rev(hcl.colors(2)),
     main = "<90 Cumulative Days Moist (in some parts) Over 8 degrees C")

m <-  x$numCumulativeDaysMoistOver5C > 90 & x$numConsecutiveDaysMoistInSomePartsOver8C < 90

plot(m,  col = hcl.colors(2, palette = "cividis"),
     main = ">90 Cumulative Days Moist Over 5 degrees C and\n
             <90 Cumulative Days Moist (in some parts) Over 8 degrees C")
plot(as.lines(v), col = "brown", add = TRUE)

if (!file.exists("mollic_crit.tif")) {
  writeRaster(m, "mollic_crit.tif")
}
