library(jNSMR)
library(terra)
data("us_states", package="spData")

# x <- rast("newhall_conus_800m_200mm_epsg5070.tif")

x <- rast("newhall_conus_800m_AWS_epsg5070.tif")
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
     main = "# of Consecutive Days Moist (in some parts) Over 8 degrees C")
plot(as.lines(v), col = "brown", add = TRUE)
plot(x$numConsecutiveDaysMoistInSomePartsOver8C < 90,
     col = rev(hcl.colors(2)),
     main = "<90 Consecutive Days Moist (in some parts) Over 8 degrees C")

m0 <- x$numCumulativeDaysDryOver5C / (x$numCumulativeDaysDryOver5C + x$numCumulativeDaysMoistDryOver5C + x$numCumulativeDaysMoistOver5C)
m <-  m0 > 0.5 & x$numCumulativeDaysMoistOver5C > 90 & x$numConsecutiveDaysMoistInSomePartsOver8C < 90

plot(m,  col = hcl.colors(2, palette = "cividis"),
     main = ">90 Cumulative Days Moist Over 5 degrees C\nwith Aridic SMR criteria")
plot(as.lines(v), col = "brown", add = TRUE)

if (!file.exists("mollic_crit.tif")) {
  writeRaster(m, "mollic_crit.tif")
}

# convert the areas the model predicts could make aridic SMR
# and also make mollic criterion 8

x <- as.polygons(m)
v <- subset(x, x[[1]] == 1)
y <- spatSample(v, 1000)
z <- soilDB::SDA_spatialQuery(y)
p <- soilDB::get_SDA_property(method = "dominant condition",
                              mukeys = z$mukey,
                              property = "taxsubgrp")
o <- soilDB::get_SDA_property(method = "dominant condition",
                              mukeys = z$mukey,
                              property = "compname")
a <- soilDB::SDA_query(
      paste0("SELECT mukey, muacres FROM mapunit WHERE mukey IN ",
        soilDB::format_SQL_in_statement(z$mukey)))

library(dplyr)

b <- left_join(p, o) |>
  left_join(a)

b |>
  group_by(taxsubgrp) |>
  summarize(sumacres = sum(muacres)) |>
  arrange(desc(sumacres)) |>
  write.csv("aridic-mollic_taxsubgrp.csv", row.names=FALSE)

b |>
  group_by(compname) |>
  summarize(sumacres = sum(muacres)) |>
  arrange(desc(sumacres)) |>
write.csv("aridic-mollic_components.csv", row.names=FALSE)
