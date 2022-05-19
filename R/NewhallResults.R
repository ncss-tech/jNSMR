# 
# 
#' Create an instance of _XMLResultsExporter_
#' @param pathname character; output path
#' @return an instance of _XMLResultsExporter_ class
#' @export
#' @importFrom rJava .jnew
XMLResultsExporter <- function(pathname) {
  if (newhall_version() == "1.6.1") rJava::.jnew("org/psu/newhall/util/XMLResultsExporter", rJava::.jnew("java/io/File", pathname))
  else message("XMLResultsExporter only available with Newhall v1.6.1 JAR")
}

#' Create an instance of _XMLStringResultsExporter_
#' @return an instance of _XMLStringResultsExporter_ class
#' @export
#' @importFrom rJava .jnew
XMLStringResultsExporter <- function() {
  if (newhall_version() == "1.6.1") rJava::.jnew("org/psu/newhall/util/XMLStringResultsExporter")
  else message("XMLStringResultsExporter only available with Newhall v1.6.1 JAR")
}

#' Export Newhall Results, Data and Metadata to XML file with _XMLResultsExporter_
#'
#' @param dataset _NewhallDataset_ `jobjRef`
#' @param results _NewhallResults_ `jobjRef`
#' @param pathname output XML file path
#' @return an XML file written to pathname
#' @export
newhall_XMLResultsExporter <- function(dataset, results, pathname) {
  if (newhall_version() == "1.6.1") rJava::.jcall(XMLResultsExporter(pathname), "V", "export", results, dataset)
  else message("XMLResultsExporter only available with Newhall v1.6.1 JAR")
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
  if (newhall_version() == "1.6.1") rJava::.jcall(XMLStringResultsExporter(), "S", "export", results, dataset)
  else message("XMLStringResultsExporter only available with Newhall v1.6.1 JAR")
}

# 
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
  rJava::.jcall(CSVResultsExporter(results, pathname), "V", "save")
}

