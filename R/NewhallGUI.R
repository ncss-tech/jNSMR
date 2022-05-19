
#' Open the Java Newhall Graphical User Interface
#' 
#' This function must be called interactively.
#' 
#' @param command_only If `TRUE` return the command that would be executed to run GUI
#' @return If `intern=TRUE` (default), the output of the command, one line per character string. `0` if successful. If the command could not be run for any reason, the value is 127 and a warning is issued.
#'
#' @details See documentation for `system()` return result for limitations on line length, error conditions, etc.
#'
#' @export
newhall_GUI <- function(command_only = FALSE) {
  cmd <- paste(Sys.which("java"), " -jar", .jnsm_jar_file(suffix = "-1.6.1"))
  if (command_only) return(cmd)
  system(cmd, intern = FALSE, wait = FALSE)
  ## when user exits GUI calling System.exit(0) from Java crashes R session
  # rJava::.jcall(rJava::.jnew("org/psu/newhall/Newhall"), , "main", c("", ""))
}


