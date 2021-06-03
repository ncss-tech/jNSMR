# Add elements of an R vector to new Java ArrayList
#
# @param x a R vector with elements of equivalent type to `class`
# @param class fully qualified class name in JNI notation (e.g. if `x` is type _double_, `class="java/lang/Double"`)
#
# @return `ArrayList<T>` where `T` is `class`
#
# @examples
#
# .rvec2jarraylist(as.double(c(1.234, 2.345)), "java/lang/Double")
#
.rvec2jarraylist <- function(x, class) {
  arrl <- rJava::.jnew("java/util/ArrayList")
  for (y in x)
    arrl$add(rJava::.jnew(class, y))
  arrl
}
