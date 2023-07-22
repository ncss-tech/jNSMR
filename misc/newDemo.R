library(jNSMR)
library(soilDB)
library(terra)

x <- fetchSDA_spatial(c("CA067", "CA628", "CA731", "CA729"), 
                      by.col = "areasymbol",
                      geom.src = "sapolygon")

# get PRISM climate and SSURGO-derived available water storage data
predictors <- c(newhall_prism_extent(x),
                newhall_issr800_extent(x))

# fixed elevation (not currently used by the model)
predictors$elev <- 0

# run batches of models on gridded data
res <- newhall_batch(predictors)
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
