# Add elements of an R vector to new Java ArrayList
#
# @param x a R vector with elements of equivalent type to `class`
# @param class fully qualified class name in JNI notation (e.g. if `x` is type _double_, `class="java/lang/Double"`)
#
# @return `ArrayList<T>` where `T` is `class`
#
# @examples
#
# .rvec2jarraylist(as.double(c(1.234, 2.345)), class ="java/lang/Double")
#
.rvec2jarraylist <- function(x, class, n = length(x)) {
  if(!is.double(x)) {
    # iteratively add() ... works for arbitrary Java classes
    arrl <- rJava::.jnew("java/util/ArrayList", n)
    for (i in (seq_along(x) - 1)) {
      rJava::.jcall(
        arrl,
        returnSig = "V",
        method = "add",
        as.integer(i),
        rJava::.jcast(rJava::.jnew(class, x[i + 1]), "java/lang/Object")
      )
    }
    arrl
  } else {
    # DoubleStream collector (JDK 8) (would work for IntStream too)
    rJava::.jcall(
      rJava::.jcall(
        rJava::.jcall(
          rJava::.jnew("java/util/Arrays"),
          returnSig = "Ljava/util/stream/DoubleStream;", 
          method = "stream",
          as.double(x)
        ),
        returnSig =  "Ljava/util/stream/Stream;",
        method = "boxed"
      ),
      returnSig = "Ljava/lang/Object;",
      method = "collect",
      rJava::.jcall(
        rJava::.jnew("java/util/stream/Collectors"),
        returnSig = "Ljava/util/stream/Collector;",
        method = "toList"
      )
    )
  }
}
