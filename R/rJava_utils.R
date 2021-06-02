#' Add elements of an R vector to new Java ArrayList
#'
#' @param x an R vector
#' @param class fully qualified class name in JNI notation (e.g. "java/lang/String")
#'
#' @return
#'
#' @examples
#'
#' .rvec2jarraylist(as.double(c(1.234, 2.345)), "java/lang/Double")
#'
.rvec2jarraylist <- function(x, class) {
  arrl <- rJava::.jnew("java/util/ArrayList")
  for (y in x)
    arrl$add(rJava::.jnew(class, y))
  arrl
}
