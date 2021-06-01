#' Create an instance of XMLResultsExporter
#' @param pathname a character containing pathname
#' @return an instance of XMLResultsExporter class
#' @export
#' @importFrom rJava .jnew
XMLResultsExporter <- function(pathname) {
  rJava::.jnew("org/psu/newhall/util/XMLResultsExporter",
               rJava::.jnew("java/io/File", pathname))
}

#' Create an instance of XMLStringResultsExporter
#' @param pathname a character containing pathname
#' @return an instance of XMLStringResultsExporter class
#' @export
#' @importFrom rJava .jnew
XMLStringResultsExporter <- function(pathname) {
  rJava::.jnew("org/psu/newhall/util/XMLStringResultsExporter")
}

#' Export Newhall Results, Data and Metadata to XML file
#'
#' @param pathname output XML file path
#' @param dataset _NewhallDataset_ `jobjRef`
#' @param results _NewhallResults_ `jobjRef`
#'
#' @return an XML file written to specified path
#' @export
newhall_XML_export <- function(pathname, dataset, results) {
  # "inst/extdata/WILLIAMSPORT_1930_1930_export.xml"
  rJava::.jcall(XMLResultsExporter(pathname), "V", "export", results, dataset)
}

#' Export Newhall Results, Data and Metadata to XML string
#'
#' @param pathname output XML file path
#' @param dataset _NewhallDataset_ `jobjRef`
#' @param results _NewhallResults_ `jobjRef`
#'
#' @return an XML file written to specified path
#' @export
newhall_XML_string_export <- function(dataset, results) {
  # "inst/extdata/WILLIAMSPORT_1930_1930_export.xml"
  rJava::.jcall(XMLStringResultsExporter(), "S", "export", results, dataset)
}

#' Create an instance of CSVResultsExporter
#' @param pathname a character containing pathname
#' @return an instance of CSVResultsExporter class
#' @export
#' @importFrom rJava .jnew
CSVResultsExporter <- function(pathname) {
  rJava::.jnew("org/psu/newhall/util/CSVResultsExporter",
               rJava::.jnew("java/io/File", pathname))
}

#' Export Newhall Results, Data and Metadata to CSV file
#'
#' @param pathname output CSV file path
#' @param dataset _NewhallDataset_ `jobjRef`
#' @param results _NewhallResults_ `jobjRef`
#'
#' @return an CSV file written to specified path
#' @export
newhall_CSV_export <- function(pathname, dataset, results) {
  rJava::.jcall(CSVResultsExporter(pathname), "export", results, dataset)
}

