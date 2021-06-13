#' Run a Newhall simulation for each row in a CSV file
#' @param .data a _data.frame_ or _character_ vector of paths (used as `pathname`)
#' @param pathname path(s) to CSV file(s) with Newhall batch format and required columns; default `NULL`
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
newhall_batch <- function(.data = NULL,
                          pathname = NULL,
                          unitSystem = "metric",
                          soilAirOffset = ifelse(unitSystem %in% c("in","english"), 4.5, 2.5),
                          amplitude = 0.66,
                          verbose = TRUE,
                          toString = TRUE,
                          checkargs = TRUE) {

   # for NSE in data.table / R CMD check
  .id <- NULL; allAirTempsDbl <- NULL; allPrecipsDbl <- NULL; awc <- NULL; cntryCode <- NULL; dataset <- NULL; elev <- NULL; latDD <- NULL; lonDD <- NULL; pApr <- NULL; pAug <- NULL; pDec <- NULL; pFeb <- NULL; pJan <- NULL; pJul <- NULL; pJun <- NULL; pMar <- NULL; pMay <- NULL; pNov <- NULL; pOct <- NULL; pSep <- NULL; pdEndYr <- NULL; pdStartYr <- NULL; results <- NULL; stationName <- NULL; tApr <- NULL; tAug <- NULL; tDec <- NULL; tFeb <- NULL; tJan <- NULL; tJul <- NULL; tJun <- NULL; tMar <- NULL; tMay <- NULL; tNov <- NULL; tOct<- NULL; tSep <- NULL

  unitSystem <- match.arg(tolower(unitSystem), choices =  c("metric","mm","cm","in","english"))
  if (unitSystem %in% c("metric","mm")) {
    # "cm" is the internal convention in the NewhallDatasetMetadata for _millimeters_ of rainfall, degrees Celsius
    unitSystem <- "cm"
  } else if (unitSystem == "english") {
    # "in" is the internal convention in the NewhallDatasetMetadata for inches of rainfall, degrees Fahrenheit
    unitSystem <- "in"
  }

  # if .data not specified (NULL), pathname required
  if (is.null(.data) && !is.null(pathname)) {
    .data <- pathname
  }

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

  t1 <- Sys.time()
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

