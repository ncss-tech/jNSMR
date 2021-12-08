options("jNSMR.JAR_SUFFIX"="-1.6.3")
library(jNSMR)

x <- BASICSimulationModel()

res <- x$runBatch(
  rJava::.jarray(c("a", "b")),
  rJava::.jarray(c("a", "b")),
  rJava::.jarray(as.double(c(40.1, 40.1))),
  rJava::.jarray(as.double(c(-100.1, -100.1))),
  rJava::.jarray(rJava::.jchar(c(
    rJava::.jchar(strtoi(charToRaw('N'), 16L)), rJava::.jchar(strtoi(charToRaw('N'), 16L))
  ))),
  rJava::.jarray(rJava::.jchar(c(
    rJava::.jchar(strtoi(charToRaw('E'), 16L)), rJava::.jchar(strtoi(charToRaw('E'), 16L))
  ))),
  rJava::.jarray(as.double(c(100.1, 100.1))),
  rJava::.jarray(do.call('rbind', lapply(1:2, function(x) as.double(rep(120,12)))), dispatch = TRUE),
  rJava::.jarray(do.call('rbind', lapply(1:2, function(x) as.double(rep(12,12)))), dispatch = TRUE),
  rJava::.jarray(as.integer(c(1900, 1900))),
  rJava::.jarray(as.integer(c(2000, 2000))),
  rJava::.jarray(as.logical(c(TRUE, TRUE))),
  rJava::.jarray(as.double(c(200.0, 100.0))),
  rJava::.jarray(as.double(c(2.5, 2.5))),
  rJava::.jarray(as.double(c(0.66, 0.66)))
)
a <- lapply(res, rJava::.jcast, "Lorg/psu/newhall/sim/NewhallResults")
.format_results <- function(x) {
  # etal <- x$getMeanPotentialEvapotranspiration()
  data.frame(
    NumCumulativeDaysMoist = rJava::.jcall(x, 'I', "getNumCumulativeDaysMoist"),
    NumCumulativeDaysMoistDry =  rJava::.jcall(x, 'I', "getNumCumulativeDaysMoistDry"),
    NumCumulativeDaysMoistDryOver5C =  rJava::.jcall(x, 'I', "getNumCumulativeDaysMoistDryOver5C"),
    NumConsecutiveDaysMoistInSomePartsOver8C =  rJava::.jcall(x, 'I', "getNumConsecutiveDaysMoistInSomePartsOver8C"),
    NumCumulativeDaysDry = rJava::.jcall(x, 'I', "getNumCumulativeDaysDry"),
    NumCumulativeDaysDryOver5C = rJava::.jcall(x, 'I', "getNumCumulativeDaysDryOver5C"),
    NumCumulativeDaysMoistOver5C = rJava::.jcall(x, 'I', "getNumCumulativeDaysMoistOver5C"),
    NumConsecutiveDaysMoistInSomeParts = rJava::.jcall(x, 'I', "getNumConsecutiveDaysMoistInSomeParts"),
    TemperatureRegime = rJava::.jcall(x, 'S', "getTemperatureRegime"),
    MoistureRegime = rJava::.jcall(x, 'S', "getMoistureRegime"),
    RegimeSubdivision1 = rJava::.jcall(x, 'S', "getRegimeSubdivision1"),
    RegimeSubdivision2 = rJava::.jcall(x, 'S', "getRegimeSubdivision2"),
    # MeanPotentialEvapotranspiration = I(list(sapply(0L:11L, function(i) etal$get(as.integer(i))))), 
    MoistDaysAfterWinterSolstice = rJava::.jcall(x, 'I', "getMoistDaysAfterWinterSolstice"),
    DryDaysAfterSummerSolstice = rJava::.jcall(x, 'I', "getDryDaysAfterSummerSolstice")
  )
}
res <- do.call('rbind', lapply(a, .format_results))

