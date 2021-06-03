# tools for creating _NewhallDataset_ from file or R objects

#' Create an instance of _XMLFileParser_
#' @param pathname _character_ containing `pathname`
#' @return an instance of _XMLFileParser_ class
#' @export
#' @importFrom rJava .jnew
XMLFileParser <- function(pathname) {
    rJava::.jnew("org/psu/newhall/util/XMLFileParser",
                 rJava::.jnew("java/io/File", pathname))
}

#' Create an instance of _CSVFileParser_
#' @param pathname _character_ containing `pathname`
#' @return an instance of _CSVFileParser_ class
#' @export
#' @importFrom rJava .jnew
CSVFileParser <- function(pathname) {
  rJava::.jnew("org/psu/newhall/util/CSVFileParser",
               rJava::.jnew("java/io/File", pathname))
}

#' Create an instance of _NewhallDataset_ from XML or CSV file
#' @param pathname _character_ containing `pathname`
#' @param .parser either `XMLFileParser` or `CSVFileParser`
#' @export
#' @aliases xml_NewhallDataset csv_NewhallDataset
#' @importFrom rJava .jcall
NewhallDatasetFromPath <- function(pathname, .parser = XMLFileParser) {
  rJava::.jcall(obj = .parser(pathname),
                returnSig="Lorg/psu/newhall/sim/NewhallDataset;",
                method = "getDataset")
}

# convenience methods supply .parser argument

#' @export
#' @rdname NewhallDatasetFromPath
xml_NewhallDataset <- function(pathname) {
  NewhallDatasetFromPath(pathname = pathname, .parser = XMLFileParser)
}

#' @export
#' @rdname NewhallDatasetFromPath
csv_NewhallDataset <- function(pathname) {
  NewhallDatasetFromPath(pathname = pathname, .parser = CSVFileParser)
}

#' Create an instance of _NewhallDataset_
#'
#' @param stationName _character_; station name
#' @param country _character_; country
#' @param lat _double_; latitude decimal degrees
#' @param lon _double_; longitude decimal degrees
#' @param nsHemisphere _character_ (length `1`);`'N'` OR `'S'`
#' @param ewHemisphere _character_ (length `1`); `'E'` OR `'W'`
#' @param stationElevation _double_; station elevation
#' @param allPrecipsDbl _double_; length `12` precipitation, monthly
#' @param allAirTempsDbl _double_; length `12` air temperature, monthly
#' @param pdbegin _integer_; beginning year
#' @param pdend _integer_; ending year
#' @param smcsawc _double_; soil moisture control section available water capacity
#' @export
#' @importFrom rJava .jnew .jchar .jcast
#' @return an instance of _NewhallDataset_
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
  function(stationName, # String
           country, # String
           lat, # double
           lon, # double
           nsHemisphere, # char
           ewHemisphere, # char
           stationElevation, # double
           allPrecipsDbl, # List<Double>
           allAirTempsDbl, # List<Double>
           pdbegin, # integer
           pdend, # integer
           smcsawc # double
          ) {

    rJava::.jnew(
      "org/psu/newhall/sim/NewhallDataset",
      as.character(stationName),
      as.character(country),
      as.double(lat),
      as.double(lon),
      rJava::.jchar(strtoi(charToRaw(substr(nsHemisphere, 1, 1)), 16L)),
      rJava::.jchar(strtoi(charToRaw(substr(ewHemisphere, 1, 1)), 16L)),
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
