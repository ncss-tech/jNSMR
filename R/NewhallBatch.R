#' Run Newhall Soil Climate Simulations
#'
#' `newhall_batch()` provides an interface to multiple runs of the jNSM `BASICSimulationModel()` for the CSV batch file input format used in jNSM 1.6.0, plus the `SpatRaster` and `RasterStack` R object types.
#'   - `newhall_batch(<character>)` - one or more paths to jNSM Comma-Separated Value '.csv' batch files; see details for required column names
#'   - `newhall_batch(<SpatRaster>)` - a `SpatRaster` object, containing the required column names as layers
#'   - `newhall_batch(<RasterStack>)`- a `RasterStack` object, containing the required column names as layers
#'
#' @param .data a _data.frame_ or _character_ vector of paths to CSV files; or a SpatRaster or RasterStack containin the same data elements and names as included in the batch `data.frame`/CSV format
#' @param unitSystem Default: `"metric"` OR `"mm"` OR `"cm"` use _millimeters_ of rainfall (default for the BASIC model); set to `unitSystem="english"` OR `unitSystem="in"` to transform English (inches of precipitation; degrees Fahrenheit) inputs to metric (millimeters of precipitation; degrees Celsius) before running simulation
#' @param soilAirOffset air-soil temperature offset. Conventionally for jNSM: `2.5` for metric units (default); `4.5` for english units.
#' @param amplitude difference in amplitude between soil and air temperature sine waves. Default `0.66`
#' @param verbose print message about number of simulations and elapsed time
#' @param toString call `toString()` method on each _NewhallResults_ object and store in `output` column of result?
#' @param checkargs _logical_; check argument length and data types for each run? Default: `TRUE`
#'
#' @seealso
#'  - `BASICSimulationModel()`: create an instance of the Java Newhall Simulation Model
#'  - `newhall_simulation()`: run a single Newhall model instance, return `NewhallResults` object
#' @return When input is a _data.frame_ or _character_ vector of paths to CSV files, result is a a _data.frame_ with key model outputs (see details) containing list columns with Java Objects for _NewhallDataset_, _NewhallResults_.  If `toString=TRUE` the column `output` is a _character_ containing the `toString()` output from _NewhallResults_
#' @export
#'
#' @importFrom data.table data.table rbindlist
#' @aliases newhall_batch
#' @rdname newhall_batch
#' @export
#'
#' @references
#' van Wambeke, A. and Newhall, F. and United States Soil Management Support Services (1981) Calculated Soil Moisture and Temperature Regimes of South America: A Compilation of Soil Climatic Regimes calculated by using a mathematical model developed by F. Newhall (Soil Conservation Service, USDA, 1972). SMSS : Technical Monograph : Soil management support services. New York State College of Agriculture and Life Sciences, Cornell University, Department of Agronomy. Available online: <https://books.google.com/books?id=jwtIAAAAYAAJ>
#'
#' Thornthwaite, C. W. (1948). An Approach toward a Rational Classification of Climate. Geographical Review, 38(1), 55–94. https://doi.org/10.2307/210739
#'
#' Newhall, F., Berdanier, C. (1996) Calculation of soil moisture regimes from the climatic record. National Soil Survey Center, Natural Resources Conservation Service, U.S. Dept. of Agriculture. Available online: <https://www.nrcs.usda.gov/Internet/FSE_DOCUMENTS/nrcs142p2_052248.pdf>
#'
#' @details
#'
#' #### Required inputs
#'
#' The main inputs to the model are monthly precipitation and air temperature for each site, the location, the soil available water storage, and the elevation.
#'
#' The following columns and names are required in the input data/object:
#'
#'  - Latitude and Longitude in WGS84 Decimal Degrees: "latDD", "lonDD"
#'  - Monthly Air Temperature (degrees C or F): "tJan", "tFeb", "tMar", "tApr", "tMay", "tJun", "tJul", "tAug", "tSep", "tOct", "tNov", "tDec"
#'  - Monthly Precipitation (millimeters or inches of rain): "pJan", "pFeb", "pMar", "pApr", "pMay", "pJun", "pJul", "pAug", "pSep", "pOct", "pNov", "pDec"
#'  - Profile Available Water Storage (millimeters; Default `200`): "awc"
#'  - Elevation (meters or feet): "elev"
#'
#' #### Dry v.s. Moist
#'
#' The concept of "dry" versus "moist" is expressed semi-quantitatively in the Newhall model with three different categories of moisture being recognized: "moist", "moist/dry" and "dry."
#'
#' Of interest to the classification of climate regimes of a soil are not only when/where the soil is dry but how that moisture or lack thereof corresponds with prevailing temperature conditions.
#'
#' #### Standard model output fields and their definitions in the latest available JAR file include:
#'
#'  - `"annualRainfall"` - sum of monthly precipitation values over the year
#'  - `"waterHoldingCapacity"` - total water storage of soil profile in units of length (`mm`). Default: `200` millimeters (8 inches) of water storage. This is approximately the average water storage when calculated using SSURGO available water capacities and depths for the soils in CONUS.
#'  - `"annualWaterBalance"` - sum of difference of precipitation and estimated mean potential evapotranspiration (Thornthwaite, 1948) by month
#'  - `"annualPotentialEvapotranspiration"` - sum of mean monthly potential evapotranspiration (Thornthwaite, 1948)
#'  - `"summerWaterBalance"` - sum of (summer months only) difference of precipitation and estimated mean potential evapotranspiration by month
#'  - `"dryDaysAfterSummerSolstice"` - number of days "dry" after June 21; used in definition of Xeric moisture regime
#'  - `"moistDaysAfterWinterSolstice"` - number of days "moist" after December 21; used in definition of Xeric moisture regime
#'  - `"numCumulativeDaysDry"` - cumulative number of "dry" days per year
#'  - `"numCumulativeDaysMoistDry"` - cumulative number of days "intermediate between moist and dry" per year
#'  - `"numCumulativeDaysMoist"` - cumulative number of days "moist" per year
#'  - `"numCumulativeDaysDryOver5C"` - cumulative number of days "dry" per year when the _soil temperature is over 5 degrees C_
#'  - `"numCumulativeDaysMoistDryOver5C"` - cumulative number of days "intermediate between moist and dry" per year when the _soil temperature is over 5 degrees C_
#'  - `"numCumulativeDaysMoistOver5C"` - cumulative number of days "moist" per year when the _soil temperature is over 5 degrees C_
#'  - `"numConsecutiveDaysMoistInSomeParts"` - maximum number of consecutive days per year where some parts of the profile are "moist"
#'  - `"numConsecutiveDaysMoistInSomePartsOver8C"` - maximum number of consecutive days per year where some parts of the profile are "moist" _and_ the _soil temperature is over 8 degrees C_
#'  - `"temperatureRegime"` - estimated Soil Temperature Regime; one of "Pergelic", "Cryic", "Frigid", "Mesic", "Thermic", "Hyperthermic", "Isofrigid", "Isomesic", "Isothermic", or "Isohyperthermic"
#'  - `"moistureRegime"` - estimated Soil Moisture Regime; one of "Aridic", "Ustic", "Xeric", "Udic", "Perudic", or "Undefined"
#'  - `"regimeSubdivision1"` - estimated "Moisture Regime Subdivision #1"; one of "Typic", "Weak", "Wet", "Dry", "Extreme", "Xeric", "Udic", "Aridic", or " " (See van Wambecke et al., 1981)
#'  - `"regimeSubdivision2"` - estimated "Moisture Regime subdivision #2"; one of "Aridic", "Tempustic", "Tropustic", "Tempudic", "Xeric", "Udic", "Tropudic", "Undefined",  or " " (See van Wambecke et al., 1981)
#'
#' "Years" are based on uniform 12 months with 30 days each for a total of 360 days (no leap years).
#'
#' The following elements have a many:1 relationship with model runs and are not (yet) included in the standard output, but can be accessed using an rJava object reference to a `NewhallResults` class.
#'  - `"meanPotentialEvapotranspiration"` - estimated mean monthly potential evapotranspiration (Thornthwaite, 1948)
#'  - `"temperatureCalendar"` - compressed (360 day) grid "calendar" showing days above 5 and 8 degrees C
#'  - `"moistureCalendar"` - compressed (360 day) grid "calendar" showing "moist", "moist/dry" and "dry" days.
#'
newhall_batch.default <- function(.data = NULL,
                                  unitSystem = "metric",
                                  soilAirOffset = ifelse(unitSystem %in% c("in","english"), 4.5, 2.5),
                                  amplitude = 0.66,
                                  verbose = TRUE,
                                  toString = TRUE,
                                  checkargs = TRUE,
                                  cores = NULL,
                                  file = NULL,
                                  nrows = NULL,
                                  overwrite = NULL) {

  # if newer JAR is available, use the fastest batch method
  if (newhall_version() >= "1.6.3") {
    batch2(
      .data,
      unitSystem = unitSystem,
      soilAirOffset = soilAirOffset,
      amplitude = amplitude,
      verbose = verbose,
      toString = toString,
      checkargs = checkargs
    )
  } else {
    # v1.6.1
    batch1(
      .data = .data,
      unitSystem = unitSystem,
      soilAirOffset = soilAirOffset,
      amplitude = amplitude,
      verbose = verbose,
      toString = toString,
      checkargs = checkargs
    )
  }
}

batch1 <- function(.data = NULL,
                   unitSystem = "metric",
                   soilAirOffset = ifelse(unitSystem %in% c("in","english"), 4.5, 2.5),
                   amplitude = 0.66,
                   verbose = TRUE,
                   toString = TRUE,
                   checkargs = TRUE) {
  t1 <- Sys.time()
   # for NSE in data.table / R CMD check
  .id <- NULL; allAirTempsDbl <- NULL; allPrecipsDbl <- NULL; awc <- NULL; cntryCode <- NULL; dataset <- NULL; elev <- NULL; latDD <- NULL; lonDD <- NULL; pApr <- NULL; pAug <- NULL; pDec <- NULL; pFeb <- NULL; pJan <- NULL; pJul <- NULL; pJun <- NULL; pMar <- NULL; pMay <- NULL; pNov <- NULL; pOct <- NULL; pSep <- NULL; pdEndYr <- NULL; pdStartYr <- NULL; results <- NULL; stationName <- NULL; tApr <- NULL; tAug <- NULL; tDec <- NULL; tFeb <- NULL; tJan <- NULL; tJul <- NULL; tJun <- NULL; tMar <- NULL; tMay <- NULL; tNov <- NULL; tOct <- NULL; tSep <- NULL

  unitSystem <- match.arg(tolower(unitSystem), choices =  c("metric","mm","cm","in","english"))
  if (unitSystem %in% c("metric","mm")) {
    # "cm" is the internal convention in the NewhallDatasetMetadata for _millimeters_ of rainfall, degrees Celsius
    unitSystem <- "cm"
  } else if (unitSystem == "english") {
    # "in" is the internal convention in the NewhallDatasetMetadata for inches of rainfall, degrees Fahrenheit
    unitSystem <- "in"
  }

  # minimum dataset includes all of the codes specified in colnames of batch file template
  mincols <- !(.colnamesNewhallBatch() %in% colnames(.data))

  if (sum(mincols) > 0) {
    stop(sprintf(
      "columns %s are required in the Newhall batch CSV input format",
      paste0(.colnamesNewhallBatch()[mincols], collapse = ", ")
    ), call. = FALSE)
  }

  # convert deg F to deg C
  .doUnitsTemp <- function(x) if (unitSystem == "in") return((x - 32) * 5 / 9) else x

  # convert inches to _millimeters_
  .doUnitsLength <- function(x) if (unitSystem == "in") return(x * 25.4) else x

  batchframe <- data.table::data.table(.data)
  batchframe$.id <- 1:nrow(batchframe)

  batchframe <- batchframe[batchframe[, list(allPrecipsDbl = list(.doUnitsLength(c(pJan,pFeb,pMar,pApr,pMay,pJun,
                                                                                   pJul,pAug,pSep,pOct,pNov,pDec))),
                                             allAirTempsDbl = list(.doUnitsTemp(c(tJan,tFeb,tMar,tApr,tMay,tJun,
                                                                                  tJul,tAug,tSep,tOct,tNov,tDec)))),
                                       by = .id],
                           on = ".id"]

  res <- batchframe[, list(awc = awc,
                           dataset = list(NewhallDataset(stationName = as.character(stationName), # String
                                                         country = as.character(cntryCode), # String
                                                         latDD = as.double(latDD), # double
                                                         lonDD = as.double(lonDD), # double
                                                         elev = as.double(elev), # double
                                                         allPrecipsDbl = as.double(allPrecipsDbl[[1]]), # List<Double>
                                                         allAirTempsDbl = as.double(allAirTempsDbl[[1]]), # List<Double>
                                                         pdbegin = as.integer(pdStartYr), # integer
                                                         pdend = as.integer(pdEndYr), # integer
                                                         smcsawc = as.double(awc),  # double
                                                         checkargs = checkargs
                                                        ))),
                                          by = .id][,
                                            list(dataset = dataset,
                                                 results = list(newhall_simulation(dataset = dataset[[1]],
                                                                                   smcsawc = as.double(awc),
                                                                                   soilAirOffset = as.double(soilAirOffset),
                                                                                   amplitude = as.double(amplitude)))),

                                          by = .id][,
                                            list(dataset = dataset,
                                                 results = results,
                                                 output  = ifelse(toString,
                                                                  list(rJava::.jcall(results[[1]],
                                                                                     returnSig = "S",
                                                                                     method = "toString")),
                                                                  list())),
                                          by = .id]


  .format_results <- function(x) {
    data.frame(
      NumCumulativeDaysMoist = rJava::.jcall(x, 'I', "getNumCumulativeDaysMoist"),
      NumCumulativeDaysMoistDry =  rJava::.jcall(x, 'I', "getNumCumulativeDaysMoistDry"),
      NumCumulativeDaysMoistDryOver5C =  rJava::.jcall(x, 'I', "getNumCumulativeDaysMoistDryOver5C"),
      NumConsecutiveDaysMoistInSomePartsOver8C =  rJava::.jcall(x, 'I', "getNumConsecutiveDaysMoistInSomePartsOver8C"),
      NumCumulativeDaysDry = rJava::.jcall(x, 'I', "getNumCumulativeDaysDry"),
      NumCumulativeDaysDryOver5C = rJava::.jcall(x, 'I', "getNumCumulativeDaysDryOver5C"),
      NumCumulativeDaysMoistOver5C = rJava::.jcall(x, 'I', "getNumCumulativeDaysMoistOver5C"),
      # Ncpm = x$getNcpm(),
      # Nsd = x$getNsd(),
      NumConsecutiveDaysMoistInSomeParts = rJava::.jcall(x, 'I', "getNumConsecutiveDaysMoistInSomeParts"),
      TemperatureRegime = rJava::.jcall(x, 'S', "getTemperatureRegime"),
      MoistureRegime = rJava::.jcall(x, 'S', "getMoistureRegime"),
      RegimeSubdivision1 = rJava::.jcall(x, 'S', "getRegimeSubdivision1"),
      RegimeSubdivision2 = rJava::.jcall(x, 'S', "getRegimeSubdivision2"),
      # MeanPotentialEvapotranspiration = x$getMeanPotentialEvapotranspiration()
      MoistDaysAfterWinterSolstice = rJava::.jcall(x, 'I', "getMoistDaysAfterWinterSolstice"),
      DryDaysAfterSummerSolstice = rJava::.jcall(x, 'I', "getDryDaysAfterSummerSolstice")
    )
  }

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

  res <- cbind(res, res[, .format_results(results[[1]]), by = list(1:nrow(res))])

  if (verbose) {
    deltat <- signif(difftime(Sys.time(), t1, units = "auto"), digits = 2)
    message(sprintf(
      "newhall_batch: ran n=%s simulations in %s %s",
      nrow(res), deltat, attr(deltat, 'units')
    ))
  }

  res$.id <- NULL
  as.data.frame(res)
}

# data.frame -> data.frame
#'
#' @importFrom utils type.convert
batch2 <- function(.data,
                   unitSystem = "metric",
                   soilAirOffset = ifelse(unitSystem %in% c("in","english"), 4.5, 2.5),
                   amplitude = 0.66,
                   verbose = TRUE,
                   toString = TRUE,
                   checkargs = TRUE) {

  t1 <- Sys.time()
  x <- BASICSimulationModel()

  unitSystem <- match.arg(tolower(unitSystem),
                          choices =  c("metric","mm","cm","in","english"))

  if (unitSystem %in% c("metric","mm")) {
    # "cm" is the internal convention in the NewhallDatasetMetadata for _millimeters_ of rainfall, degrees Celsius
    unitSystem <- "cm"
  } else if (unitSystem == "english") {
    # "in" is the internal convention in the NewhallDatasetMetadata for inches of rainfall, degrees Fahrenheit
    unitSystem <- "in"
  }

  # minimum dataset includes all of the codes specified in colnames of batch file template
  mincols <- !(.colnamesNewhallBatch() %in% colnames(.data))

  if (sum(mincols) > 0) {
    stop(sprintf(
      "columns %s are required in the Newhall batch CSV input format",
      paste0(.colnamesNewhallBatch()[mincols], collapse = ", ")
    ), call. = FALSE)
  }

  # convert deg F to deg C
  .doUnitsTemp <- function(x) if (unitSystem == "in") return((x - 32) * 5 / 9) else x

  # convert inches to _millimeters_
  .doUnitsLength <- function(x) if (unitSystem == "in") return(x * 25.4) else x
  #
  # res <- x$runBatch(
  #   rJava::.jarray(as.character(.data$stationID)),
  #   rJava::.jarray(as.character(.data$stationName)),
  #   rJava::.jarray(as.double(.data$latDD)),
  #   rJava::.jarray(as.double(.data$lonDD)),
  #   rJava::.jarray(rJava::.jchar(rep(rJava::.jchar(strtoi(charToRaw('N'), 16L)), nrow(.data)))),
  #   rJava::.jarray(rJava::.jchar(rep(rJava::.jchar(strtoi(charToRaw('W'), 16L)), nrow(.data)))),
  #   # rJava::.jarray(rJava::.jchar(c(rJava::.jchar(strtoi(charToRaw('N'), 16L)), rJava::.jchar(strtoi(charToRaw('S'), 16L))))[as.integer(.data$latDD < 0) + 1]),
  #   # rJava::.jarray(rJava::.jchar(c(rJava::.jchar(strtoi(charToRaw('E'), 16L)), rJava::.jchar(strtoi(charToRaw('W'), 16L))))[as.integer(.data$latDD > 0) + 1]),
  #   rJava::.jarray(as.double(.data$elev)),
  #   rJava::.jarray(do.call('rbind', lapply(1:nrow(.data),
  #                                          function(i) as.double(.data[i,][, grep("^p[A-Z][a-z]{2}$", colnames(.data))]))), dispatch = TRUE),
  #   rJava::.jarray(do.call('rbind', lapply(1:nrow(.data),
  #                                          function(i) as.double(.data[i,][, grep("t[A-Z][a-z]{2}$", colnames(.data))]))), dispatch = TRUE),
  #   rJava::.jarray(as.integer(.data$pdStartYr)),
  #   rJava::.jarray(as.integer(.data$pdEndYr)),
  #   rJava::.jarray(rep(checkargs, nrow(.data))),
  #   rJava::.jarray(as.double(.data$awc)),
  #   rJava::.jarray(rep(soilAirOffset, nrow(.data))),
  #   rJava::.jarray(rep(amplitude, nrow(.data)))
  # )
  .data <- data.frame(.data)
  cnd <- colnames(.data)
  .SD <- NULL
  res <- rJava::.jcall(x, "Lorg/psu/newhall/sim/NewhallBatchResults;", "runBatch2",
  # res <- x$runBatch2(
    rJava::.jarray(cbind(0.0, as.matrix(.data[, cnd[grep("^p[A-Z][a-z]{2}$", cnd)]])), dispatch = TRUE),
    rJava::.jarray(cbind(0.0, as.matrix(.data[, cnd[grep("^t[A-Z][a-z]{2}$", cnd)]])), dispatch = TRUE),
    rJava::.jarray(as.double(.data$latDD)),
    rJava::.jarray(as.double(.data$lonDD)),
    rJava::.jarray(rJava::.jchar(rep(rJava::.jchar(strtoi(charToRaw('N'), 16L)), nrow(.data)))),
    rJava::.jarray(as.double(.data$elev)),
    # rJava::.jarray(rJava::.jchar(c(rJava::.jchar(strtoi(charToRaw('N'), 16L)), rJava::.jchar(strtoi(charToRaw('S'), 16L))))[as.integer(.data$latDD < 0) + 1]),
    # rJava::.jarray(rJava::.jchar(c(rJava::.jchar(strtoi(charToRaw('E'), 16L)), rJava::.jchar(strtoi(charToRaw('W'), 16L))))[as.integer(.data$latDD > 0) + 1]),
    rJava::.jarray(rep(unitSystem == "cm", nrow(.data))),
    rJava::.jarray(as.double(.data$awc)),
    rJava::.jarray(rep(soilAirOffset, nrow(.data))),
    rJava::.jarray(rep(amplitude, nrow(.data)))
  )

  b <- rJava::.jcast(res, "Lorg/psu/newhall/sim/NewhallBatchResults")

  # we can store the arrays of values as public fields in this new class designed to do the iteration and simplification of the data
  fields <- c(
    "annualRainfall",
    "waterHoldingCapacity",
    "annualWaterBalance",
    "annualPotentialEvapotranspiration",
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
  res <- lapply(fields, function(n) rJava::.jfield(b, name = n))
  res <- lapply(res, function(x) {if (length(x) == 0) return(rep(NA, length(res[[1]]))); x})
  res <- as.data.frame(res)
  if (verbose) {
    deltat <- signif(difftime(Sys.time(), t1, units = "auto"), digits = 2)
    message(sprintf(
      "newhall_batch: ran n=%s simulations in %s %s",
      nrow(res), deltat, attr(deltat, 'units')
    ))
  }
  colnames(res) <- fields #, fieldsmatrix
  type.convert(res, as.is = TRUE)
}

#' @export
#' @examples
#' 
#' library(terra)
#' 
#' x <- terra::rast(system.file("extdata", "prism_issr800_sample.tif", package="jNSMR"))
#' x <- c(newhall_prism_extent(ext(x) * 4), newhall_issr800_extent(ext(x) * 4))
#' x$elev <- 0 # elevation is not currently used by the model directly
#' 
#' # reduce resolution (for fast example)
#' x2 <- aggregate(x, 10, na.rm = TRUE)
#' 
#' # calculate winter, summer and annual average temperatures
#' d <- as.data.frame(x2)
#' x2$mwst <- rowMeans(d[, c("tDec","tJan","tFeb")])
#' x2$msst <- rowMeans(d[, c("tJun","tJul","tAug")]) 
#' x2$mast <- rowMeans(d[, paste0("t", month.abb)])
#' x2$dif <- x2$msst - x2$mwst
#' plot(x2$dif)
#'
#' y <- newhall_batch(x2) ## 1/10th resolution
#' 
#' # y <- newhall_batch(x) ## full resolution
#' 
#' par(mfrow=c(2, 1))
#' terra::plot(y$annualWaterBalance, main = "Annual Water Balance (P-PET)")
#' terra::plot(y$waterHoldingCapacity, main = "Water Holding Capacity")
#' 
#' terra::plot(y$temperatureRegime, main = "Temperature Regime")
#' terra::plot(y$moistureRegime, main = "Moisture Regime")
#' 
#' terra::plot(y$numCumulativeDaysDryOver5C, cex.main=0.75,
#'             main = "# Cumulative Days Dry over 5 degrees C")
#' terra::plot(y$numConsecutiveDaysMoistInSomePartsOver8C, cex.main=0.75,
#'             main = "# Consecutive Days Moist\nin some parts over 8 degrees C")
#' par(mfrow=c(1,1))
#' 
newhall_batch <- function(.data,
                          unitSystem = "metric",
                          soilAirOffset = ifelse(unitSystem %in% c("in", "english"), 4.5, 2.5),
                          amplitude = 0.66,
                          verbose = TRUE,
                          toString = TRUE,
                          checkargs = TRUE,
                          cores = 1,
                          file = paste0(tempfile(), ".tif"),
                          nrows = nrow(.data),
                          overwrite = TRUE){
  UseMethod("newhall_batch", .data)
}

#' @rdname newhall_batch
#' @export
newhall_batch.character <- function(.data,
                                    unitSystem = "metric",
                                    soilAirOffset = ifelse(unitSystem %in% c("in", "english"), 4.5, 2.5),
                                    amplitude = 0.66,
                                    verbose = TRUE,
                                    toString = TRUE,
                                    checkargs = TRUE,
                                    cores = 1,
                                    file = paste0(tempfile(), ".tif"),
                                    nrows = nrow(.data),
                                    overwrite = TRUE) {
  # if .data is character, it could specify multiple CSVs
  if (is.character(.data)) {
    # create aggregate table from character vector >1
    .data <- data.table::rbindlist(lapply(.data, function(x) {
      if (!file.exists(x))
        stop(sprintf("file '%s' does not exist", x), call. = FALSE)
      if (all(nchar(x) > 0))
        newhall_readBatchInput(x)
    }), fill = TRUE)
  } else if (!inherits(.data, 'data.frame')) {
    stop("'.data' should be a `data.frame` or a vector of path names to CSV files", call. = FALSE)
  }

  newhall_batch.default(.data,
                        unitSystem = unitSystem,
                        soilAirOffset = soilAirOffset,
                        amplitude = amplitude,
                        verbose = verbose,
                        toString = toString,
                        checkargs = checkargs)

}

.setCats <- function(x) {
    x <- as.data.frame(x)

    # handle character results w/ standard factor levels

    # explicitly set factors  and convert to numeric (so each chunk uses same lookup table)
    # subtract 1 because factor levels in terra are 0-indexed
    # TODO: double check that this is encoding everything correctly
    x$temperatureRegime <- as.numeric(factor(x$temperatureRegime, levels = .str())) - 1
    x$moistureRegime <- as.numeric(factor(x$moistureRegime, levels = .smr())) - 1
    x$regimeSubdivision1 <- as.numeric(factor(gsub(" ", "Undefined", x$regimeSubdivision1), levels = .smrsub1())) - 1
    x$regimeSubdivision2 <- as.numeric(factor(gsub(" ", "Undefined", x$regimeSubdivision2), levels = .smrsub2())) - 1
    x
}

# helpers for setting categories
.fctDF <- function(levels, name = "category", offset = -1) {
  `colnames<-`(data.frame(seq_along(levels) + offset, levels), c("value", name))
}
.str <- function() c("Pergelic", "Cryic", "Frigid", "Mesic", "Thermic", "Hyperthermic", "Isofrigid", "Isomesic", "Isothermic", "Isohyperthermic")
.smr <- function()  c("Aridic", "Ustic", "Xeric", "Udic", "Perudic", "Undefined")
.smrsub1 <- function() c("Typic", "Weak", "Wet", "Dry", "Extreme", "Xeric", "Udic", "Aridic", "Undefined")
.smrsub2 <- function() c("Aridic", "Tempustic", "Tropustic", "Tempudic", "Xeric", "Udic", "Tropudic", "Undefined")

#' @param cores number of cores; used only for processing _SpatRaster_ or _Raster*_ input
#' @param file path to write incremental raster processing output for large inputs that do not fit in memory; passed to `terra::writeStart()` and used only for processing _SpatRaster_ or _Raster*_ input; defaults to a temporary file created by `tempfile()` if needed
#' @param nrows number of rows to use per block; passed to `terra::readValues()` `terra::writeValues()`; used only for processing _SpatRaster_ or _Raster*_ input; defaults to number of rows in dataset if needed
#' @param overwrite logical; overwrite `file`? passed to `terra::writeStart()`; defaults to `TRUE` if needed
#' @export
#' @rdname newhall_batch
#' @return For `SpatRaster` input returns a `SpatRaster` containing numeric and categorical model outputs. `RasterBrick` inputs are first converted to `SpatRaster`, and a `SpatRaster` is returned
#' @importFrom terra rast readStart writeStart readValues writeValues writeStop readStop `nlyr<-` set.cats
#' @importFrom parallel makeCluster stopCluster clusterApply
newhall_batch.SpatRaster <- function(.data,
                                     unitSystem = "metric",
                                     soilAirOffset = ifelse(unitSystem %in% c("in","english"), 4.5, 2.5),
                                     amplitude = 0.66,
                                     verbose = TRUE,
                                     toString = FALSE,
                                     checkargs = TRUE,
                                     cores = 1,
                                     file = paste0(tempfile(), ".tif"),
                                     nrows = nrow(.data),
                                     overwrite = TRUE) {
  stopifnot(requireNamespace("terra"))
  suppressWarnings(terra::readStart(.data))

  # create template brick
  out <- terra::rast(.data)
  if (newhall_version() >= "1.6.4") {
    cnm <- c("annualRainfall", "waterHoldingCapacity", "annualWaterBalance", "annualPotentialEvapotranspiration",
                             "summerWaterBalance", "dryDaysAfterSummerSolstice", "moistDaysAfterWinterSolstice",
                             "numCumulativeDaysDry", "numCumulativeDaysMoistDry", "numCumulativeDaysMoist",
                             "numCumulativeDaysDryOver5C", "numCumulativeDaysMoistDryOver5C",
                             "numCumulativeDaysMoistOver5C", "numConsecutiveDaysMoistInSomeParts",
                             "numConsecutiveDaysMoistInSomePartsOver8C", "temperatureRegime",
                             "moistureRegime", "regimeSubdivision1", "regimeSubdivision2")
  } else {
    cnm <- c("nrow", "numCumulativeDaysMoist",
             "numCumulativeDaysMoistDry", "numCumulativeDaysMoistDryOver5C",
             "numConsecutiveDaysMoistInSomePartsOver8C", "numCumulativeDaysDry",
             "numCumulativeDaysDryOver5C", "numCumulativeDaysMoistOver5C",
             "numConsecutiveDaysMoistInSomeParts", "temperatureRegime", "moistureRegime",
             "regimeSubdivision1", "regimeSubdivision2", "moistDaysAfterWinterSolstice",
             "dryDaysAfterSummerSolstice")
  }
  terra::nlyr(out) <- length(cnm)
  names(out) <- cnm

  out_info <- terra::writeStart(out, filename = file, overwrite = overwrite, progress = 0)
  outrows <- c(out_info$row, nrow(out))
  start_row <- lapply(1:out_info$n, function(i) out_info$row[i] + c(0, (seq_len((out_info$nrows[i]) / nrows) * nrows)))
  n_row <- lapply(seq_along(start_row), function(i) diff(c(start_row[[i]] - 1, outrows[i + 1])))
  n_set <- sum(sapply(start_row, length))

  if (cores > 1) {
    cls <- parallel::makeCluster(cores)
    on.exit(parallel::stopCluster(cls))

    # TODO: can blocks be parallelized?
    count <- 1
    for (i in seq_along(n_row)) {
      for (j in seq_along(n_row[[i]])) {
        if (n_row[[i]][j] > 0) {
          st <- Sys.time()
          blockdata <- terra::readValues(.data,
                                         row = start_row[[i]][j],
                                         nrows = n_row[[i]][j],
                                         dataframe = TRUE)
          ids <- 1:nrow(blockdata)
          skip.idx <- which(is.na(blockdata$awc))

          if (length(skip.idx) > 0) {
            blockcomplete <- blockdata[-skip.idx,]
          } else blockcomplete <- blockdata

          if (nrow(blockcomplete) > 0) {
            # parallel within-block processing
            cids <- 1:nrow(blockcomplete)
            sz <- round(nrow(blockcomplete) / cores) + 1
            X <- split(blockcomplete, f = rep(seq(from = 1, to = floor(length(cids) / sz) + 1),
                                              each = sz)[1:length(cids)])
            r <- data.table::rbindlist(parallel::clusterApply(cls, X, function(x, unitSystem,
                                                                               soilAirOffset,
                                                                               amplitude,
                                                                               verbose,
                                                                               toString,
                                                                               checkargs) {
                batch2(
                  .data = x,
                  unitSystem = unitSystem,
                  soilAirOffset = soilAirOffset,
                  amplitude = amplitude,
                  verbose = verbose,
                  toString = toString,
                  checkargs = checkargs
                )
              }, unitSystem = unitSystem,
                 soilAirOffset = soilAirOffset,
                 amplitude = amplitude,
                 verbose = verbose,
                 toString = toString,
                 checkargs = checkargs), use.names = TRUE, fill = TRUE)
            # TODO: why does fill=TRUE need to be used here? it introduces NAs; can it be fixed on Java side?
            r$regimeSubdivision1[is.na(r$regimeSubdivision1)] <- "Undefined"
            r$regimeSubdivision2[is.na(r$regimeSubdivision2)] <- "Undefined"
          } else {
            r <- data.frame(annualRainfall = logical(0), waterHoldingCapacity = logical(0),
                            annualWaterBalance = logical(0), summerWaterBalance = logical(0),
                            annualPotentialEvapotranspiration = logical(0),
                            dryDaysAfterSummerSolstice = logical(0), moistDaysAfterWinterSolstice = logical(0),
                            numCumulativeDaysDry = logical(0), numCumulativeDaysMoistDry = logical(0),
                            numCumulativeDaysMoist = logical(0), numCumulativeDaysDryOver5C = logical(0),
                            numCumulativeDaysMoistDryOver5C = logical(0), numCumulativeDaysMoistOver5C = logical(0),
                            numConsecutiveDaysMoistInSomeParts = logical(0), numConsecutiveDaysMoistInSomePartsOver8C = logical(0),
                            temperatureRegime = logical(0), moistureRegime = logical(0),
                            regimeSubdivision1 = logical(0), regimeSubdivision2 = logical(0),
                            stringsAsFactors = FALSE)
          }

          # fill skipped NA cells
          r.na <- r[0, , drop = FALSE][1:length(skip.idx), , drop = FALSE]
          r <- rbind(r, r.na)[order(c(ids[!ids %in% skip.idx], skip.idx)),]

          # explicitly set factors
          r <- .setCats(r)

          st2 <- Sys.time()
          terra::writeValues(out, as.matrix(r), start_row[[i]][j], nrows = n_row[[i]][j])
          st3 <- Sys.time()

          deltat <- signif(difftime(Sys.time(), st, units = "auto"), 2)
          message(paste0(
            "Batch ", count, " of ", n_set, " (n=",
            nrow(blockcomplete), " on ", cores, " cores) done in ",
            deltat, " ", attr(deltat, 'units')
          ))
          count <- count + 1
        }
      }
    }
  } else {
    for (i in seq_along(n_row)) {
      for (j in seq_along(n_row[[i]])) {
        if (n_row[[i]][j] > 0) {
          dataall <- terra::readValues(
            .data,
            row = start_row[[i]][j],
            nrows = n_row[[i]][j],
            dataframe = TRUE
          )
          ids <- 1:nrow(dataall)

          skip.idx <- which(is.na(dataall$awc))

          if (length(skip.idx) > 0) {
            datacomplete <- dataall[-skip.idx,]
          } else datacomplete <- dataall

          r2 <- newhall_batch.default(
            .data = datacomplete,
            unitSystem = unitSystem,
            soilAirOffset = soilAirOffset,
            amplitude = amplitude,
            verbose = verbose,
            toString = toString,
            checkargs = checkargs
          )

          # remove list columns
          r2$dataset <- NULL
          r2$results <- NULL
          r2$output <- NULL

          # fill skipped NA cells
          r.na <- r2[0, , drop = FALSE][1:length(skip.idx), , drop = FALSE]
          r2 <- rbind(r2, r.na)[order(c(ids[!ids %in% skip.idx], skip.idx)),]

          # explicitly set factors
          r2 <- .setCats(r2)

          terra::writeValues(out, as.matrix(r2), start_row[[i]][j], nrows = n_row[[i]][j])
        }
      }
    }
  }

  out <- terra::writeStop(out)
  terra::readStop(.data)

  # factors in output object

  ## deprecated way of setting levels in {terra}
  # l <- levels(out)
  # l[[which(names(out) %in% "temperatureRegime")]] <- .str()
  # l[[which(names(out) %in% "moistureRegime")]] <- .smr()
  # l[[which(names(out) %in% "regimeSubdivision1")]] <- .smrsub1()
  # l[[which(names(out) %in% "regimeSubdivision2")]] <- .smrsub2()
  # levels(out) <- l

  # use two-column data.frames
  terra::set.cats(out, .fctDF(.str(), name = "temperatureRegime"),
                  layer = which(names(out) == "temperatureRegime"))
  terra::set.cats(out, .fctDF(.smr(), name = "moistureRegime"),
                  layer = which(names(out) == "moistureRegime"))
  terra::set.cats(out, .fctDF(.smrsub1(), name = "regimeSubdivision1"),
                  layer = which(names(out) == "regimeSubdivision1"))
  terra::set.cats(out, .fctDF(.smrsub2(), name = "regimeSubdivision2"),
                  layer = which(names(out) == "regimeSubdivision2"))
  out
}

#' @export
#' @rdname newhall_batch
newhall_batch.RasterBrick <- function(.data,
                                      unitSystem = "metric",
                                      soilAirOffset = ifelse(unitSystem %in% c("in","english"), 4.5, 2.5),
                                      amplitude = 0.66,
                                      verbose = TRUE,
                                      toString = TRUE,
                                      checkargs = TRUE,
                                      cores = 1,
                                      file = paste0(tempfile(), ".tif"),
                                      nrows = nrow(.data),
                                      overwrite = TRUE) {
  newhall_batch.RasterStack(.data,
                            unitSystem = unitSystem,
                            soilAirOffset = soilAirOffset,
                            amplitude = amplitude,
                            verbose = verbose,
                            toString = toString,
                            checkargs = checkargs,
                            cores = cores,
                            file = file,
                            nrows = nrows,
                            overwrite = overwrite)
}

#' @export
#' @rdname newhall_batch
#' @importFrom terra rast
newhall_batch.RasterStack <- function(.data,
                                      unitSystem = "metric",
                                      soilAirOffset = ifelse(unitSystem %in% c("in","english"), 4.5, 2.5),
                                      amplitude = 0.66,
                                      verbose = TRUE,
                                      toString = TRUE,
                                      checkargs = TRUE,
                                      cores = 1,
                                      file = paste0(tempfile(), ".tif"),
                                      nrows = nrow(.data),
                                      overwrite = TRUE) {
  stopifnot(requireNamespace("terra"))
  newhall_batch.SpatRaster(terra::rast(.data),
                           unitSystem = unitSystem,
                           soilAirOffset = soilAirOffset,
                           amplitude = amplitude,
                           verbose = verbose,
                           toString = toString,
                           checkargs = checkargs,
                           cores = cores,
                           file = file,
                           nrows = nrows,
                           overwrite = overwrite)
}
