#' Get Java Newhall JAR file version
#'
#' @details This is a wrapper around accessing the public string field `NSM_VERSION` stored within the main _Newhall_ class of the JAR file.
#'
#' @return _character_ containing version number of Newhall JAR file
#' @export
#'
newhall_version <- function() {
  getOption("jNSMR.NSM_VERSION", default = "")
}

# wrapper for system.file location of newhall.jar
.jnsm_jar_file <- function(suffix = "-1.6.1") {
  res <- system.file("java", sprintf("newhall%s.jar", suffix), package = "jNSMR")[1]
  if (res == "") {
    stop(sprintf("newhall%s.jar not found!", suffix), call. = FALSE)
  }
  res
}

#' @importFrom rJava .jinit .jpackage .jaddClassPath
.onLoad <- function(libname, pkgname) {

  # rJava setup
  rJava::.jinit()

  # we load the JAR explicitly, rather than everything in inst/java
  # rJava::.jpackage(pkgname, lib.loc = libname)

  # allow an option to add additional characters to jar file name (custom jar file)
  jas <- getOption("jNSMR.JAR_SUFFIX", default = "-1.6.4")

  jnf <- .jnsm_jar_file(suffix = jas)
  
  # newhall JAR setup: add to class path
  rJava::.jaddClassPath(jnf)
  
}

#' @importFrom rJava .jfield .jnew
#' @importFrom utils packageVersion
.onAttach <- function(libname, pkgname) {
  jas <- getOption("jNSMR.JAR_SUFFIX", default = "-1.6.4")
  jnf <- .jnsm_jar_file(suffix = jas)
  packageStartupMessage(paste0("jNSMR (", packageVersion("jNSMR"), ") -- R interface to the classic Newhall Simulation Model\nAdded JAR file (", basename(jnf), ") to Java class path."))
  
  options(jNSMR.NSM_VERSION = rJava::.jfield(
    o = rJava::.jnew("org/psu/newhall/Newhall"),
    sig = "Ljava/lang/String;",
    name = "NSM_VERSION"
  ))
}
