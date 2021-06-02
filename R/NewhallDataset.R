#' Create an instance of XMLFileParser
#' @param pathname a character containing pathname
#' @return an instance of XMLFileParser class
#' @export
#' @importFrom rJava .jnew
XMLFileParser <- function(pathname) {
    rJava::.jnew("org/psu/newhall/util/XMLFileParser",
                 rJava::.jnew("java/io/File", pathname))
}

#' Create an instance of CSVFileParser
#' @param pathname a character containing pathname
#' @return an instance of CSVFileParser class
#' @export
#' @importFrom rJava .jnew
CSVFileParser <- function(pathname) {
  rJava::.jnew("org/psu/newhall/util/CSVFileParser",
               rJava::.jnew("java/io/File", pathname))
}

#' Create an instance of NewhallDataset from XML file
#' @param pathname a character containing pathname
#' @param .parser either `XMLFileParser` or `CSVFileParser`
#' @export
#' @importFrom rJava .jcall
NewhallDatasetFromPath <- function(pathname, .parser = XMLFileParser) {
  rJava::.jcall(obj = .parser(pathname),
                returnSig="Lorg/psu/newhall/sim/NewhallDataset;",
                method = "getDataset")
}

#' Create an instance of NewhallDataset
#'
#' @param stationName character; station name
#' @param country character; country
#' @param lat double; latitude
#' @param lon double; longitude
#' @param nsHemisphere character (length 1);`'N'` OR `'S'`
#' @param ewHemisphere character (length 1); `'E'` OR `'W'`
#' @param stationElevation double; station elevation
#' @param allPrecipsDbl precipitation, monthly
#' @param allAirTempsDbl precipitation, monthly
#' @param pdbegin beginning year
#' @param pdend ending year
#' @param smcsawc soil moisture control section available water capacity
#' @export
#' @importFrom rJava .jnew .jchar .jcast
#'
#' @examples
#' input_direct <- NewhallDataset(
#'   stationName = "WILLIAMSPORT",
#'   country = "US",
#'   lat = 41.24,
#'   lon = -76.92,
#'   nsHemisphere = 'N',
#'   ewHemisphere = 'W',
#'   stationElevation = 158.0,
#'   allPrecipsDbl = c(
#'     44.2,
#'     40.39,
#'     113.54,
#'     96.77,
#'     95.0,
#'     98.55,
#'     66.04,
#'     13.46,
#'     54.86,
#'     6.35,
#'     17.53,
#'     56.39
#'   ),
#'   allAirTempsDbl = c(
#'     -2.17,
#'     0.89,
#'     3.72,
#'     9.11,
#'     16.28,
#'     21.11,
#'     22.83,
#'     21.94,
#'     19.78,
#'     10.5,
#'     5.33,
#'     -1.06
#'   ),
#'   pdbegin = 1930,
#'   pdend = 1930,
#'   smcsawc = 200.0
#' )
#'
NewhallDataset <-
  function(stationName,
           country,
           lat,
           lon,
           nsHemisphere,
           ewHemisphere,
           stationElevation,
           allPrecipsDbl,
           allAirTempsDbl,
           pdbegin,
           pdend,
           smcsawc) {

    rJava::.jnew(
      "org/psu/newhall/sim/NewhallDataset",
      as.character(stationName),
      as.character(country),
      as.double(lat),
      as.double(lon),
      rJava::.jchar(nsHemisphere),
      rJava::.jchar(ewHemisphere),
      as.double(stationElevation),
      rJava::.jcast(
        .rvec2jarraylist(as.double(allPrecipsDbl), "java/lang/Double"),
        "java/util/List"
      ),
      rJava::.jcast(
        .rvec2jarraylist(as.double(allAirTempsDbl), "java/lang/Double"),
        "java/util/List"
      ),
      as.integer(pdbegin),
      as.integer(pdend),
      TRUE,
      as.double(smcsawc)
    )
}
