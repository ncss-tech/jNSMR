#' Open the Java Newhall Graphical User Interface
#'
#' @param intern passed to `system()`; when `TRUE` return a character result containing output. Default `FALSE` returns invisibly.
#' @param wait passed to `system()` default `FALSE` with `intern=FALSE` will not interrupt R session.
#'
#' @return If `intern=TRUE` (default), the output of the command, one line per character string. `0` if successful. If the command could not be run for any reason, the value is 127 and a warning is issued.
#'
#' @details See documentation for `system()` return result for limitations on line length, error conditions, etc.
#'
#' @export
newhall_GUI <- function(intern = FALSE, wait = FALSE) {


  # TODO: look for JAVA_HOME

  ## requires `java` on PATH
  system(paste(
    "java -jar",
    .jnsm_jar_file()
  ), intern = intern, wait = wait)

  ## when user exits GUI calling System.exit(0) from Java crashes R session
  # rJava::.jcall(rJava::.jnew("org/psu/newhall/Newhall"), , "main", c("", ""))

}

.jnsm_jar_file <- function() system.file("java", "newhall-1.6.1.jar", package = "jNSMR")
