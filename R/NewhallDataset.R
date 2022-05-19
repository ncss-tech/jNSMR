# tools for creating _NewhallDataset_ from file or R objects

#' Create an instance of _XMLFileParser_
#' @param pathname _character_ containing `pathname`
#' @return an instance of _XMLFileParser_ class
#' @export
#' @importFrom rJava .jnew
XMLFileParser <- function(pathname) {
  if (newhall_version() == "1.6.1") rJava::.jnew("org/psu/newhall/util/XMLFileParser", rJava::.jnew("java/io/File", pathname))
  else message("XMLFileParser only available with Newhall v1.6.1 JAR")
}

#' Create an instance of _CSVFileParser_
#' @param pathname _character_ containing `pathname`
#' @return an instance of _CSVFileParser_ class
#' @export
#' @importFrom rJava .jnew
CSVFileParser <- function(pathname) {
  rJava::.jnew("org/psu/newhall/util/CSVFileParser", rJava::.jnew("java/io/File", pathname))
}

#' Create an instance of _NewhallDataset_ from XML or CSV file
#' @param pathname _character_ containing `pathname`
#' @param .parser either `XMLFileParser` or `CSVFileParser`
#' @export
#' @aliases xml_NewhallDataset csv_NewhallDataset
#' @importFrom rJava .jcall
NewhallDatasetFromPath <- function(pathname, .parser = XMLFileParser) {
  subpar <- as.character(substitute(.parser))
  rJava::.jcall(
    obj = .parser(pathname),
    returnSig = "Lorg/psu/newhall/sim/NewhallDataset;",
    method = ifelse(
      as.character(as.name(subpar)) == "CSVFileParser",
      "getDatset",
      "getDataset"
    )
  )
}

# convenience methods supply .parser argument

#' @export
#' @rdname NewhallDatasetFromPath
xml_NewhallDataset <- function(pathname) {
  if (newhall_version() == "1.6.1") NewhallDatasetFromPath(pathname = pathname, .parser = XMLFileParser) 
  else message("xml_NewhallDataset only available with Newhall v1.6.1 JAR")
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
#' @param latDD _double_; latitude decimal degrees
#' @param lonDD _double_; longitude decimal degrees
#' @param elev _double_; station elevation
#' @param allPrecipsDbl _double_; length `12` precipitation, monthly (millimeters of water)
#' @param allAirTempsDbl _double_; length `12` air temperature, monthly (degrees celsius)
#' @param pdbegin _integer_; beginning year
#' @param pdend _integer_; ending year
#' @param smcsawc _double_; soil moisture control section available water capacity (millimeters)
#' @param checkargs _logical_; check argument length and data types? Default: `FALSE`
#' @export
#' @importFrom rJava .jnew .jchar .jcast
#' @return an instance of _NewhallDataset_
#'
#' @examples
#' input_direct <- NewhallDataset(
#'   stationName = "WILLIAMSPORT",
#'   country = "US",
#'   latDD = 41.24,
#'   lonDD = -76.92,
#'   elev = 158.0,
#'   allPrecipsDbl = c(44.2, 40.39, 113.54, 96.77, 95, 98.55,
#'                     66.04, 13.46, 54.86, 6.35, 17.53, 56.39),
#'   allAirTempsDbl = c(-2.17, 0.89, 3.72, 9.11, 16.28, 21.11,
#'                      22.83, 21.94, 19.78, 10.5, 5.33, -1.06),
#'   pdbegin = 1930,
#'   pdend = 1930,
#'   smcsawc = 200.0
#' )
#'
NewhallDataset <-
  function(stationName, # String
           country, # String
           latDD, # double
           lonDD, # double
           elev, # double
           allPrecipsDbl, # List<Double>
           allAirTempsDbl, # List<Double>
           pdbegin, # integer
           pdend, # integer
           smcsawc, # double
           checkargs = TRUE
          ) {

    if (checkargs) {
      .checkFUN <- function(x, FUN) x[sapply(x, function(y) FUN(eval(as.symbol(y))))]

      testa <- c("stationName","country","latDD","lonDD",
                 "elev","pdbegin","pdend","smcsawc")
      len1 <- .checkFUN(testa, function(x) length(x) == 1)
      if (length(len1) != length(testa)) {
        stop(sprintf("station '%s' arguments %s should be length 1",
                     stationName[1], paste0(testa[!testa %in% len1], collapse = ", ")), call.=FALSE)
      }

      testb <- c("allPrecipsDbl", "allAirTempsDbl")
      len12 <- .checkFUN(testb, function(x) length(x) == 12)
      if (length(len12) != length(testb)) {
        stop(sprintf("station '%s' arguments %s should be length 12",
                     stationName[1], paste0(testb[!testb %in% len12], collapse = ", ")), call.=FALSE)
      }

      testc <- c("latDD","lonDD","allPrecipsDbl", "allAirTempsDbl",
                 "elev","pdbegin","pdend","smcsawc")
      isnum <- .checkFUN(testc, function(y) is.numeric(y))
      if (length(isnum) != length(testc)) {
        stop(sprintf("station '%s' arguments %s should be numeric",
                     stationName[1], paste0(testc[!testc %in% isnum], collapse = ", ")), call.=FALSE)
      }

    }

    nsHemisphere <- ifelse(latDD > 0, 'N', 'S') # char
    ewHemisphere <- ifelse(lonDD < 0, 'E', 'W') # char

    res <- try(rJava::.jnew(
      "org/psu/newhall/sim/NewhallDataset",
      as.character(stationName[1]),
      as.character(country[1]),
      as.double(latDD[1]),
      as.double(lonDD[1]),
      rJava::.jchar(strtoi(charToRaw(nsHemisphere), 16L)),
      rJava::.jchar(strtoi(charToRaw(ewHemisphere), 16L)),
      as.double(elev[1]),
      rJava::.jcast(
        .rvec2jarraylist(as.double(allPrecipsDbl[1:12]), "java/lang/Double"),
        "java/util/List"
      ),
      rJava::.jcast(
        .rvec2jarraylist(as.double(allAirTempsDbl[1:12]), "java/lang/Double"),
        "java/util/List"
      ),
      as.integer(pdbegin[1]),
      as.integer(pdend[1]),
      TRUE, # isMetric: all calls to the Java Newhall model via run_simulation() are with metric values
      as.double(smcsawc[1])
    ), silent = TRUE)

    if (inherits(res, 'try-error')) {
      # print(stationName)
      # print(country)
      # print(latDD)
      # print(lonDD)
      # print(nsHemisphere)
      # print(ewHemisphere)
      # print(elev)
      # print(allPrecipsDbl)
      # print(allAirTempsDbl)
      # print(pdbegin)
      # print(pdend)
      # print(smcsawc)
      stop(res, call. = FALSE)
    }
    res
}

# public org.psu.newhall.sim.NewhallDataset(java.lang.String, java.lang.String, double, double, char, char, double, java.util.List<java.lang.Double>, java.util.List<java.lang.Double>, int, int, boolean, double);
# descriptor: (Ljava/lang/String;Ljava/lang/String;DDCCDLjava/util/List;Ljava/util/List;IIZD)V
