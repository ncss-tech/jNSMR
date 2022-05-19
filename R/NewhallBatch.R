#' Run a Newhall simulation for each row in a CSV file
#' @param .data a _data.frame_ or _character_ vector of paths to CSV files
#' @param unitSystem Default: `"metric"` OR `"mm"` OR `"cm"` use _millimeters_ of rainfall (default for the BASIC model); set to `unitSystem="english"` OR `unitSystem="in"` to transform English (inches of precipitation; degrees Fahrenheit) inputs to metric (millimeters of precipitation; degrees Celsius) before running simulation
#' @param soilAirOffset air-soil temperature offset. Conventionally for jNSM: `2.5` for metric units (default); `4.5` for english units.
#' @param amplitude difference in amplitude between soil and air temperature sine waves. Default `0.66`
#' @param verbose print message about number of simulations and elapsed time
#' @param toString call `toString()` method on each _NewhallResults_ object and store in `output` column of result?
#' @param checkargs _logical_; check argument length and data types for each run? Default: `TRUE`
#' @seealso csv_writeNewhallBatch
#' @return a _data.frame_ containing list columns with Java Objects for _NewhallDataset_, _NewhallResults_.  If `toString=TRUE` the column `output` is a _character_ containing the `toString()` output from _NewhallResults_
#' @export
#'
#' @importFrom data.table data.table rbindlist
#' @aliases newhall_batch
#' @rdname newhall_batch
#' @export
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
  # if (newhall_version() >= "1.6.3") {
    batch2(
      .data,
      unitSystem = unitSystem,
      soilAirOffset = soilAirOffset,
      amplitude = amplitude,
      verbose = verbose,
      toString = toString,
      checkargs = checkargs
    )
  # } else {
  #   # v1.6.1
  #   batch1(
  #     .data = .data,
  #     unitSystem = unitSystem,
  #     soilAirOffset = soilAirOffset,
  #     amplitude = amplitude,
  #     verbose = verbose,
  #     toString = toString,
  #     checkargs = checkargs
  #   )
  # }
}
# 
# batch1 <- function(.data = NULL,
#                    unitSystem = "metric",
#                    soilAirOffset = ifelse(unitSystem %in% c("in","english"), 4.5, 2.5),
#                    amplitude = 0.66,
#                    verbose = TRUE,
#                    toString = TRUE,
#                    checkargs = TRUE) {
#   t1 <- Sys.time()
#    # for NSE in data.table / R CMD check
#   .id <- NULL; allAirTempsDbl <- NULL; allPrecipsDbl <- NULL; awc <- NULL; cntryCode <- NULL; dataset <- NULL; elev <- NULL; latDD <- NULL; lonDD <- NULL; pApr <- NULL; pAug <- NULL; pDec <- NULL; pFeb <- NULL; pJan <- NULL; pJul <- NULL; pJun <- NULL; pMar <- NULL; pMay <- NULL; pNov <- NULL; pOct <- NULL; pSep <- NULL; pdEndYr <- NULL; pdStartYr <- NULL; results <- NULL; stationName <- NULL; tApr <- NULL; tAug <- NULL; tDec <- NULL; tFeb <- NULL; tJan <- NULL; tJul <- NULL; tJun <- NULL; tMar <- NULL; tMay <- NULL; tNov <- NULL; tOct <- NULL; tSep <- NULL
# 
#   unitSystem <- match.arg(tolower(unitSystem), choices =  c("metric","mm","cm","in","english"))
#   if (unitSystem %in% c("metric","mm")) {
#     # "cm" is the internal convention in the NewhallDatasetMetadata for _millimeters_ of rainfall, degrees Celsius
#     unitSystem <- "cm"
#   } else if (unitSystem == "english") {
#     # "in" is the internal convention in the NewhallDatasetMetadata for inches of rainfall, degrees Fahrenheit
#     unitSystem <- "in"
#   }
# 
#   # minimum dataset includes all of the codes specified in colnames of batch file template
#   mincols <- !(.colnamesNewhallBatch() %in% colnames(.data))
# 
#   if (sum(mincols) > 0) {
#     stop(sprintf(
#       "columns %s are required in the Newhall batch CSV input format",
#       paste0(.colnamesNewhallBatch()[mincols], collapse = ", ")
#     ), call. = FALSE)
#   }
# 
#   # convert deg F to deg C
#   .doUnitsTemp <- function(x) if (unitSystem == "in") return((x - 32) * 5 / 9) else x
# 
#   # convert inches to _millimeters_
#   .doUnitsLength <- function(x) if (unitSystem == "in") return(x * 25.4) else x
# 
#   batchframe <- data.table::data.table(.data)
#   batchframe$.id <- 1:nrow(batchframe)
# 
#   batchframe <- batchframe[batchframe[, list(allPrecipsDbl = list(.doUnitsLength(c(pJan,pFeb,pMar,pApr,pMay,pJun,
#                                                                                    pJul,pAug,pSep,pOct,pNov,pDec))),
#                                              allAirTempsDbl = list(.doUnitsTemp(c(tJan,tFeb,tMar,tApr,tMay,tJun,
#                                                                                   tJul,tAug,tSep,tOct,tNov,tDec)))),
#                                        by = .id],
#                            on = ".id"]
# 
#   res <- batchframe[, list(awc = awc,
#                            dataset = list(NewhallDataset(stationName = as.character(stationName), # String
#                                                          country = as.character(cntryCode), # String
#                                                          latDD = as.double(latDD), # double
#                                                          lonDD = as.double(lonDD), # double
#                                                          elev = as.double(elev), # double
#                                                          allPrecipsDbl = as.double(allPrecipsDbl[[1]]), # List<Double>
#                                                          allAirTempsDbl = as.double(allAirTempsDbl[[1]]), # List<Double>
#                                                          pdbegin = as.integer(pdStartYr), # integer
#                                                          pdend = as.integer(pdEndYr), # integer
#                                                          smcsawc = as.double(awc),  # double
#                                                          checkargs = checkargs
#                                                         ))),
#                                           by = .id][,
#                                             list(dataset = dataset,
#                                                  results = list(newhall_simulation(dataset = dataset[[1]],
#                                                                                    smcsawc = as.double(awc),
#                                                                                    soilAirOffset = as.double(soilAirOffset),
#                                                                                    amplitude = as.double(amplitude)))),
# 
#                                           by = .id][,
#                                             list(dataset = dataset,
#                                                  results = results,
#                                                  output  = ifelse(toString,
#                                                                   list(rJava::.jcall(results[[1]],
#                                                                                      returnSig = "S",
#                                                                                      method = "toString")),
#                                                                   list())),
#                                           by = .id]
# 
# 
#   .format_results <- function(x) {
#     data.frame(
#       NumCumulativeDaysMoist = rJava::.jcall(x, 'I', "getNumCumulativeDaysMoist"),
#       NumCumulativeDaysMoistDry =  rJava::.jcall(x, 'I', "getNumCumulativeDaysMoistDry"),
#       NumCumulativeDaysMoistDryOver5C =  rJava::.jcall(x, 'I', "getNumCumulativeDaysMoistDryOver5C"),
#       NumConsecutiveDaysMoistInSomePartsOver8C =  rJava::.jcall(x, 'I', "getNumConsecutiveDaysMoistInSomePartsOver8C"),
#       NumCumulativeDaysDry = rJava::.jcall(x, 'I', "getNumCumulativeDaysDry"),
#       NumCumulativeDaysDryOver5C = rJava::.jcall(x, 'I', "getNumCumulativeDaysDryOver5C"),
#       NumCumulativeDaysMoistOver5C = rJava::.jcall(x, 'I', "getNumCumulativeDaysMoistOver5C"),
#       # Ncpm = x$getNcpm(),
#       # Nsd = x$getNsd(),
#       NumConsecutiveDaysMoistInSomeParts = rJava::.jcall(x, 'I', "getNumConsecutiveDaysMoistInSomeParts"),
#       TemperatureRegime = rJava::.jcall(x, 'S', "getTemperatureRegime"),
#       MoistureRegime = rJava::.jcall(x, 'S', "getMoistureRegime"),
#       RegimeSubdivision1 = rJava::.jcall(x, 'S', "getRegimeSubdivision1"),
#       RegimeSubdivision2 = rJava::.jcall(x, 'S', "getRegimeSubdivision2"),
#       # MeanPotentialEvapotranspiration = x$getMeanPotentialEvapotranspiration()
#       MoistDaysAfterWinterSolstice = rJava::.jcall(x, 'I', "getMoistDaysAfterWinterSolstice"),
#       DryDaysAfterSummerSolstice = rJava::.jcall(x, 'I', "getDryDaysAfterSummerSolstice")
#     )
#   }
#   
#   fields <- c(
#     "annualRainfall",
#     "waterHoldingCapacity",
#     "annualWaterBalance",
#     "summerWaterBalance",
#     "dryDaysAfterSummerSolstice",
#     "moistDaysAfterWinterSolstice",
#     "numCumulativeDaysDry",
#     "numCumulativeDaysMoistDry",
#     "numCumulativeDaysMoist",
#     "numCumulativeDaysDryOver5C",
#     "numCumulativeDaysMoistDryOver5C",
#     "numCumulativeDaysMoistOver5C",
#     "numConsecutiveDaysMoistInSomeParts",
#     "numConsecutiveDaysMoistInSomePartsOver8C",
#     "temperatureRegime",
#     "moistureRegime",
#     "regimeSubdivision1",
#     "regimeSubdivision2"
#   )
# 
#   res <- cbind(res, res[, .format_results(results[[1]]), by = list(1:nrow(res))])
# 
#   if (verbose) {
#     deltat <- signif(difftime(Sys.time(), t1, units = "auto"), digits = 2)
#     message(sprintf(
#       "newhall_batch: ran n=%s simulations in %s %s",
#       nrow(res), deltat, attr(deltat, 'units')
#     ))
#   }
# 
#   res$.id <- NULL
#   as.data.frame(res)
# }

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
newhall_batch <- function(.data,
                          unitSystem = "metric",
                          soilAirOffset = ifelse(unitSystem %in% c("in", "english"), 4.5, 2.5),
                          amplitude = 0.66,
                          verbose = TRUE,
                          toString = TRUE,
                          checkargs = TRUE,
                          cores = 1,
                          file = paste0(tempfile(), ".grd"),
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
                                    file = paste0(tempfile(), ".grd"),
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
  
  if (length(x$temperatureRegime) == 0)
    x$temperatureRegime <- rep(NA, nrow(x))
  if (length(x$moistureRegime) == 0)
    x$moistureRegime <- rep(NA, nrow(x))
  if (length(x$regimeSubdivision1) == 0)
    x$regimeSubdivision1 <- rep(NA, nrow(x))
  if (length(x$regimeSubdivision2) == 0)
    x$regimeSubdivision2 <- rep(NA, nrow(x))
  
  # handle character results w/ standard factor levels

  # explicitly set factors (so each chunk uses same lookup table)
  x$temperatureRegime <- as.numeric(factor(x$temperatureRegime, levels = .str())) - 1
  x$moistureRegime <- as.numeric(factor(x$moistureRegime, levels = .smr())) - 1
  x$regimeSubdivision1 <- as.numeric(factor(x$regimeSubdivision1, levels = .smrsub1())) - 1
  x$regimeSubdivision2 <- as.numeric(factor(x$regimeSubdivision2, levels = .smrsub2())) - 1
  # print(colnames(x)[sapply(x, is.character)])
  # convert anything else character -> factor -> numeric
  # x[sapply(x, is.character)] <- lapply(x[sapply(x, is.character)], function(y) as.numeric(factor(y)) - 1)
  x
}

.str <- function() c("Pergelic", "Cryic", "Frigid", "Mesic", "Thermic", "Hyperthermic", "Isofrigid", "Isomesic", "Isothermic", "Isohyperthermic")
.smr <- function()  c("Aridic", "Ustic", "Xeric", "Udic", "Perudic", "Undefined")
.smrsub1 <- function() c("Typic", "Weak", "Wet", "Dry", "Extreme", "Xeric", "Udic", "Aridic", " ")
.smrsub2 <- function() c("Aridic", "Tempustic", "Tropustic", "Tempudic", "Xeric", "Udic", "Tropudic", "Undefined", " ")


#' @param cores number of cores; used only for processing _SpatRaster_ or _Raster*_ input
#' @param file path to write incremental raster processing output for large inputs that do not fit in memory; passed to `terra::writeStart()` and used only for processing _SpatRaster_ or _Raster*_ input; defaults to a temporary file created by `tempfile()` if needed
#' @param nrows number of rows to use per block; passed to `terra::readValues()` `terra::writeValues()`; used only for processing _SpatRaster_ or _Raster*_ input; defaults to number of rows in dataset if needed
#' @param overwrite logical; overwrite `file`? passed to `terra::writeStart()`; defaults to `TRUE` if needed
#' @export
#' @rdname newhall_batch
#' @importFrom terra rast readStart writeStart readValues writeValues writeStop readStop `nlyr<-`
#' @importFrom parallel makeCluster stopCluster parRapply
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
  start_row <- lapply(1:out_info$n, function(i) seq(outrows[i], outrows[i + 1], nrows))
  n_row <- lapply(seq_along(start_row), function(i) diff(c(start_row[[i]], outrows[i + 1])) + 1)
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
  l <- levels(out)
  
  l[[which(names(out) %in% "temperatureRegime")]] <- .str()
  l[[which(names(out) %in% "moistureRegime")]] <- .smr()
  l[[which(names(out) %in% "regimeSubdivision1")]] <- .smrsub1()
  l[[which(names(out) %in% "regimeSubdivision2")]] <- .smrsub2()
  
  levels(out) <- l
  out
}

#' @export
#' @rdname newhall_batch
#' @importFrom terra rast
newhall_batch.RasterBrick <- function(.data, 
                                      unitSystem = "metric",
                                      soilAirOffset = ifelse(unitSystem %in% c("in","english"), 4.5, 2.5),
                                      amplitude = 0.66,
                                      verbose = TRUE,
                                      toString = TRUE,
                                      checkargs = TRUE, 
                                      cores = 1,
                                      file = paste0(tempfile(),".grd"),
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
