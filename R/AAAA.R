# @importFrom rJava .jpackage
.onLoad <- function(libname, pkgname) {

  rJava::.jinit()

  rJava::.jpackage(pkgname, lib.loc = libname)

  rJava::.jaddClassPath(.jnsm_jar_file())
}
