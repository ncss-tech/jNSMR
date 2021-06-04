#' Run a Newhall simulation for each row in a CSV file
#' @param .data a _data.frame_ or _character_ vector of paths (used as `pathname`)
#' @param pathname paths to CSV file with Newhall batch format and required columns; default `NULL`
#' @param verbose print message about number of simulations and elapsed time
#' @seealso csv_writeNewhallBatch
#' @return a _data.frame_ containing list columns with Java Objects for _NewhallDataset_, _NewhallResults_, and the column `output` which is _character_ containing the `toString()` output from _NewhallResults_
#' @export
#'
#' @importFrom data.table data.table rbindlist
newhall_batch <- function(.data = NULL, pathname = NULL, verbose = TRUE) {

   # for NSE in data.table / R CMD check
allAirTempsDbl <- NULL; allPrecipsDbl <- NULL; awc <- NULL; cntryCode <- NULL; dataset <- NULL; elev <- NULL; latDD <- NULL; lonDD <- NULL; pApr <- NULL; pAug <- NULL; pDec <- NULL; pFeb <- NULL; pJan <- NULL; pJul <- NULL; pJun <- NULL; pMar <- NULL; pMay <- NULL; pNov <- NULL; pOct <- NULL; pSep <- NULL; pdEndYr <- NULL; pdStartYr <- NULL; results <- NULL; stationName <- NULL; tApr <- NULL; tAug <- NULL; tDec <- NULL; tFeb <- NULL; tJan <- NULL; tJul <- NULL; tJun <- NULL; tMar <- NULL; tMay <- NULL; tNov <- NULL; tOct<- NULL; tSep <- NULL

  # if .data not specified (NULL), pathname required
  if (is.null(.data) && !is.null(pathname)) {
    .data <- pathname
  }

  # if .data is character, it could specify multiple CSVs
  if (is.character(.data)) {
    # create aggregate table from character vector >1 
    .data <- data.table::rbindlist(lapply(.data, function(x) {
        if (all(nchar(x) > 0) && file.exists(x))
          csv_readNewhallBatch(x)
      }), fill = TRUE)
  } else if (!inherits(.data, 'data.frame')) {
    stop("'.data' should be a `data.frame` or a vector of path names to CSV files", call. = FALSE)
  }
  
  # minimum dataset includes all of the codes specified in colnames of batch file template
  stopifnot(all(.colnamesNewhallBatch() %in% colnames(.data)))

  batchframe <- data.table::data.table(.data)
  batchframe <- batchframe[batchframe[, list(allAirTempsDbl = list(c(tJan,tFeb,tMar,tApr,tMay,tJun,tJul,tAug,tSep,tOct,tNov,tDec)),
                                             allPrecipsDbl = list(c(pJan,pFeb,pMar,pApr,pMay,pJun,pJul,pAug,pSep,pOct,pNov,pDec))),
                                       by = stationName], on = "stationName"]

  t1 <- Sys.time()
  res <- as.data.frame(batchframe[,    list(dataset = list(NewhallDataset(stationName, # String
                                                                          country = cntryCode, # String
                                                                          lat = latDD, # double
                                                                          lon = lonDD, # double
                                                                          nsHemisphere = 'N', # char
                                                                          ewHemisphere = 'E', # char
                                                                          stationElevation = elev, # double
                                                                          allPrecipsDbl[[1]], # List<Double>
                                                                          allAirTempsDbl[[1]], # List<Double>
                                                                          pdbegin = pdStartYr, # integer
                                                                          pdend = pdEndYr, # integer
                                                                          smcsawc = awc # double
                                                                         ))), by = stationName][,
                                       list(dataset = dataset,
                                            results = list(newhall_simulation(dataset = dataset[[1]]))), by = stationName][,
                                       list(dataset = dataset,
                                            results = results,
                                            output = list(results[[1]]$toString())),
                                    by = stationName])
  if (verbose) {
    message(sprintf("Ran n=%s simulations in %s seconds", nrow(res),  round(as.numeric(Sys.time() - t1, units = "secs"))))
  }
  res
}
