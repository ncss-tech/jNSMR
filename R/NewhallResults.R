
#' Create an instance of _XMLResultsExporter_
#' @param pathname character; output path
#' @return an instance of _XMLResultsExporter_ class
#' @export
#' @importFrom rJava .jnew
XMLResultsExporter <- function(pathname) {
  rJava::.jnew("org/psu/newhall/util/XMLResultsExporter", rJava::.jnew("java/io/File", pathname))
}

#' Create an instance of _XMLStringResultsExporter_
#' @return an instance of _XMLStringResultsExporter_ class
#' @export
#' @importFrom rJava .jnew
XMLStringResultsExporter <- function() {
  rJava::.jnew("org/psu/newhall/util/XMLStringResultsExporter")
}

#' Export Newhall Results, Data and Metadata to XML file with _XMLResultsExporter_
#'
#' @param dataset _NewhallDataset_ `jobjRef`
#' @param results _NewhallResults_ `jobjRef`
#' @param pathname output XML file path
#' @return an XML file written to pathname
#' @export
newhall_XMLResultsExporter <- function(dataset, results, pathname) {
  rJava::.jcall(XMLResultsExporter(pathname), "V", "export", results, dataset)
}

#' Export Newhall Results, Data and Metadata to XML string with _XMLStringResultsExporter_
#'
#' @param dataset _NewhallDataset_ `jobjRef`
#' @param results _NewhallResults_ `jobjRef`
#'
#' @return _character_ containing XML string
#' @export
#' @importFrom rJava .jcall
newhall_XMLStringResultsExporter <- function(dataset, results) {
  rJava::.jcall(XMLStringResultsExporter(), "S", "export", results, dataset)
}

#' Create an instance of _CSVResultsExporter_
#' @param results _NewhallResults_ `jobjRef`
#' @param pathname a character containing pathname
#' @return an instance of _CSVResultsExporter_ class
#' @export
#' @importFrom rJava .jnew
CSVResultsExporter <- function(results, pathname) {
  rJava::.jnew("org/psu/newhall/util/CSVResultsExporter", results, rJava::.jnew("java/io/File", pathname))
}

#' Export Newhall Results, Data and Metadata to CSV file with _CSVResultsExporter_
#'
#' @param results _NewhallResults_ `jobjRef`
#' @param pathname output CSV file path; default: `NULL`
#'
#' @return a CSV file written to specified path
#' @export
#' @importFrom rJava .jcall
newhall_CSVResultsExporter <- function(results, pathname) {
  rJava::.jcall(CSVResultsExporter(results, pathname), , "save")
}

