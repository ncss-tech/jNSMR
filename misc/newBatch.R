options("jNSMR.JAR_SUFFIX"="-1.6.3")
library(jNSMR)

x <- BASICSimulationModel()

res <- x$runBatch(
  rJava::.jarray(c("a", "b", "c")),
  rJava::.jarray(c("a", "b", "c")),
  rJava::.jarray(as.double(c(40.1, 40.1, 40.1))),
  rJava::.jarray(as.double(c(-100.1, -100.1, -100.1))),
  rJava::.jarray(rJava::.jchar(c(
    rJava::.jchar(strtoi(charToRaw('N'), 16L)), 
    rJava::.jchar(strtoi(charToRaw('N'), 16L)), 
    rJava::.jchar(strtoi(charToRaw('N'), 16L))
  ))),
  rJava::.jarray(rJava::.jchar(c(
    rJava::.jchar(strtoi(charToRaw('W'), 16L)), 
    rJava::.jchar(strtoi(charToRaw('W'), 16L)), 
    rJava::.jchar(strtoi(charToRaw('W'), 16L))
  ))),
  rJava::.jarray(as.double(c(100.1, 100.1, 100.1))),
  rJava::.jarray(do.call('rbind', lapply(1:3, function(x) as.double(c(rep(100,6), rep(5,6))))), dispatch = TRUE),
  rJava::.jarray(do.call('rbind', lapply(1:3, function(x) as.double(rep(12,12)+5*sin(1:12)))), dispatch = TRUE),
  rJava::.jarray(as.integer(c(1900, 1900, 2000))),
  rJava::.jarray(as.integer(c(2000, 2000, 2000))),
  rJava::.jarray(as.logical(c(TRUE, TRUE, TRUE))),
  rJava::.jarray(as.double(c(20.0, 10.0, 1.0))),
  rJava::.jarray(as.double(c(2.5, 2.5, 2.5))),
  rJava::.jarray(as.double(c(0.66, 0.66, 2.5)))
)

## if runBatch returns NewhallResults[], iterate and cast each as a NewhallResults obj
# a <- lapply(res, rJava::.jcast, "Lorg/psu/newhall/sim/NewhallResults")
#
## handling iterating and unpacking of NewhallResults in R... 
# .format_results <- function(x) {
#   # etal <- x$getMeanPotentialEvapotranspiration()
#   data.frame(
#     NumCumulativeDaysMoist = rJava::.jcall(x, 'I', "getNumCumulativeDaysMoist"),
#     NumCumulativeDaysMoistDry =  rJava::.jcall(x, 'I', "getNumCumulativeDaysMoistDry"),
#     NumCumulativeDaysMoistDryOver5C =  rJava::.jcall(x, 'I', "getNumCumulativeDaysMoistDryOver5C"),
#     NumConsecutiveDaysMoistInSomePartsOver8C =  rJava::.jcall(x, 'I', "getNumConsecutiveDaysMoistInSomePartsOver8C"),
#     NumCumulativeDaysDry = rJava::.jcall(x, 'I', "getNumCumulativeDaysDry"),
#     NumCumulativeDaysDryOver5C = rJava::.jcall(x, 'I', "getNumCumulativeDaysDryOver5C"),
#     NumCumulativeDaysMoistOver5C = rJava::.jcall(x, 'I', "getNumCumulativeDaysMoistOver5C"),
#     NumConsecutiveDaysMoistInSomeParts = rJava::.jcall(x, 'I', "getNumConsecutiveDaysMoistInSomeParts"),
#     # MeanPotentialEvapotranspiration = I(list(sapply(0L:11L, function(i) etal$get(as.integer(i))))), 
#     MoistDaysAfterWinterSolstice = rJava::.jcall(x, 'I', "getMoistDaysAfterWinterSolstice"),
#     DryDaysAfterSummerSolstice = rJava::.jcall(x, 'I', "getDryDaysAfterSummerSolstice"),
#     AnnualWaterBalance = rJava::.jcall(x, 'I', "getAnnualWaterBalance"),
#     SummerWaterBalance = rJava::.jcall(x, 'I', "getSummerWaterBalance"),
#     TemperatureRegime = rJava::.jcall(x, 'S', "getTemperatureRegime"),
#     MoistureRegime = rJava::.jcall(x, 'S', "getMoistureRegime"),
#     RegimeSubdivision1 = rJava::.jcall(x, 'S', "getRegimeSubdivision1"),
#     RegimeSubdivision2 = rJava::.jcall(x, 'S', "getRegimeSubdivision2")
#   )
# }
# 
# res <- do.call('rbind', lapply(a, .format_results))
# res

##BUT!
## if runBatch returns NewhallBatchResults ...
b <- rJava::.jcast(res, "Lorg/psu/newhall/sim/NewhallBatchResults")

# we can store the arrays of values as public fields in this new class designed to do the iteration and simplification of the data
fields <- c(
  "annualRainfall",
  "waterHoldingCapacity",
  "annualWaterBalance",
  "summerWaterBalance",
  "dryDaysAfterSummerSolstice",
  "moistDaysAfterWinterSolstice",
  "numCumulativeDaysDry",
  "numCumulativeDaysMoistDry",
  "numCumulativeDaysMoist",
  "numCumulativeDaysDryOver5C",
  "numCumulativeDaysMoistDryOver5C",
  "numCumulativeDaysMoistOver5C",
  "numConsecutiveDaysMoistInSomeParts",
  "numConsecutiveDaysMoistInSomePartsOver8C",
  "temperatureRegime",
  "moistureRegime",
  "regimeSubdivision1",
  "regimeSubdivision2"
)
fieldsmatrix <- c("meanPotentialEvapotranspiration","temperatureCalendar", "moistureCalendar")

# convert to data frame
res <- as.data.frame(sapply(fields, function(n) rJava::.jfield(b, name = n)))

resmat <- lapply(fieldsmatrix, function(n) rJava::.jfield(b, name = n))

# unholy incantation to convert ArrayList<Character> to a numeric matrix...
# temperatureCalendar <- matrix(as.numeric(t(apply(resmat[[2]], 1, function(x) sapply(x, rawToChar)))), ncol = ncol(resmat[[2]]))

# two types of matrix :  12-column row for each run (PET; analogous to TEMP/PPT inputs)
#                        360 column row for each run (temperature/moisture calendar)

# inspect a 3x360 raster view of the moisture
terra::rast(resmat[[2]]) |> terra::plot()
terra::rast(resmat[[3]]) |> terra::plot()