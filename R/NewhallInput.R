# NewhallInput.R

# Write batch input CSV file template for Newhall 1.6.1 model
#
# @param stationName _character_ equal in length to number of rows in resulting batch table
# @param ... additional column names and values as specified in the Details section
# @param pathname path to batch CSV file to create/append
# @param append append to batch CSV? Default: `FALSE`
# @details additional column names (other than `stationName`) and values for batch file may be specified from this list: "netType", "latDD", "lonDD", "elev", "tJan", "tFeb", "tMar", "tApr", "tMay", "tJun", "tJul", "tAug", "tSep", "tOct", "tNov", "tDec", "pJan", "pFeb", "pMar", "pApr", "pMay", "pJun", "pJul", "pAug", "pSep", "pOct", "pNov", "pDec", "pdType", "pdStartYr", "pdEndYr", "awc", "maatmast", "cntryCode", "stProvCode", "notes", "stationID". Values should have length `1` or length equal to length of `stationName`.
# @return a file written to specified `pathname` containing a sample station ID, `0` for numeric values and `""` for character values
# @export
#' @importFrom utils write.csv
newhall_writeBatchTemplate <- function(pathname, stationName = "", ..., append = FALSE) {
  write.csv(.newhall_batch_template(stationName = stationName, ...),
            file = pathname)
}

# Read batch input CSV file for Newhall 1.6.1 model
#
# @param pathname path to batch CSV file to read
# @return a _data.frame_, one row per "run" and one column per required field a NewhallSimulation
# @export
#
#' @importFrom utils read.csv
newhall_readBatchInput <- function(pathname) {
  read.csv(file = pathname, stringsAsFactors = FALSE)[, .colnamesNewhallBatch()]
}


# Write batch output CSV file for Newhall 1.6.1 model
#
# @param pathname path to batch CSV file to read
# @return a _data.frame_, one row per "run" and one column per required field a NewhallSimulation
# @export
#
#' @importFrom utils write.csv
newhall_writeBatchOutput <- function(output_file, pathname = NULL, input_file = pathname,
                                     batch_results = newhall_batch(input_file)) {
  res <- batch_results
  if ("output" %in% colnames(batch_results)) {
    res <- batch_results[, "output", drop = FALSE]
    res$output <- as.character(res$output)
  } 
  write.csv(as.data.frame(res), file = output_file)
}

.colnamesNewhallBatch <- function() {
  c("latDD","lonDD","tJan","tFeb","tMar","tApr","tMay","tJun","tJul","tAug","tSep","tOct","tNov","tDec","pJan","pFeb","pMar","pApr","pMay","pJun","pJul","pAug","pSep","pOct","pNov","pDec","awc","elev")
  #,"stationName","pdType","pdStartYr","pdEndYr","maatmast","cntryCode","notes","stationID")
}

.newhall_batch_template <-
  function(stationName = as.character(stationName),
           netType = character(length(stationName)),
           latDD = numeric(length(stationName)),
           lonDD = numeric(length(stationName)),
           elev = numeric(length(stationName)),
           tJan = numeric(length(stationName)),
           tFeb = numeric(length(stationName)),
           tMar = numeric(length(stationName)),
           tApr = numeric(length(stationName)),
           tMay = numeric(length(stationName)),
           tJun = numeric(length(stationName)),
           tJul = numeric(length(stationName)),
           tAug = numeric(length(stationName)),
           tSep = numeric(length(stationName)),
           tOct = numeric(length(stationName)),
           tNov = numeric(length(stationName)),
           tDec = numeric(length(stationName)),
           pJan = numeric(length(stationName)),
           pFeb = numeric(length(stationName)),
           pMar = numeric(length(stationName)),
           pApr = numeric(length(stationName)),
           pMay = numeric(length(stationName)),
           pJun = numeric(length(stationName)),
           pJul = numeric(length(stationName)),
           pAug = numeric(length(stationName)),
           pSep = numeric(length(stationName)),
           pOct = numeric(length(stationName)),
           pNov = numeric(length(stationName)),
           pDec = numeric(length(stationName)),
           pdType = numeric(length(stationName)),
           pdStartYr = numeric(length(stationName)),
           pdEndYr = numeric(length(stationName)),
           awc = numeric(length(stationName)),
           maatmast = numeric(length(stationName)),
           cntryCode = character(length(stationName)),
           stProvCode = character(length(stationName)),
           notes = character(length(stationName)),
           stationID = character(length(stationName))
  ) {
  data.frame(stationName,netType,latDD,lonDD,elev,tJan,tFeb,tMar,tApr,tMay,tJun,tJul,tAug,tSep,tOct,tNov,tDec,pJan,pFeb,pMar,pApr,pMay,pJun,pJul,pAug,pSep,pOct,pNov,pDec,pdType,pdStartYr,pdEndYr,awc,maatmast,cntryCode,stProvCode,notes,stationID, stringsAsFactors = FALSE)
}
