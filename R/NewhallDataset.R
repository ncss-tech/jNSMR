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
#' @param allPrecipsDbl `java.util.List<Double>` precipitation, monthly
#' @param allAirTempsDbl `java.util.List<Double>` precipitation, monthly
#' @param pdbegin beginning year
#' @param pdend ending year
#' @param smcsawc soil moisture control section available water capacity
#' @export
#' @importFrom rJava .jcall
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

    stationName = "A"
    country = "US"
    lat = 0.0
    lon = 0.0
    nsHemisphere = 'N'
    ewHemisphere = 'W'
    stationElevation = 100.1
    allPrecipsDbl = 1:12
    allAirTempsDbl = 1:12
    pdbegin = 2020
    pdend = 2020
    isMetric = TRUE
    smcsawc = 20


  obj <- rJava::.jcall(
                      obj = "org/psu/newhall/sim/NewhallDataset",
                      returnSig = "Lorg/psu/newhall/sim/NewhallDataset;",
                      method = "NewhallDataset",
                      stationName,
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
                      TRUE,
                      smcsawc)

  rJava::.jcall(
    obj = "org/psu/newhall/sim/NewhallDataset",
    returnSig = "Lorg/psu/newhall/sim/NewhallDataset;",
    method = "NewhallDataset",
    stationName,
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
    TRUE,
    smcsawc
  )
}
