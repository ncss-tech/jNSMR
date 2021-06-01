# NewhallInput.R

#' Create batch input CSV file for Newhall model
#'
#' @param pathname path to CSV file to create/append
#' @param append append to CSV? Default: `FALSE`
#'
#' @return a file written to specified `pathname` containing a sample station ID, `0` for numeric values and `""` for character values
#' @export
#'
newhall_input_csv_batch <- function(pathname, append = FALSE) {
  write.csv(.newhall_csv_template_batch(stationName = "Site #1"), file = pathname, append = append)
}

.newhall_csv_template_batch <-
  function(stationName = character(0),
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
