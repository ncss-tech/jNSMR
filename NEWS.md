# jNSMR 0.0.0.9004

 - Major improvements to handling of metadata and minimum datasets

 - Better error messages and checking of types

 - Proper fix for hemisphere based on lat/lng (note that E/W does not enter into calculation but does affect string/XML output)

 - Access to JAR `NSM_VERSION` and support for custom Newhall JAR file .onLoad

# jNSMR 0.0.0.9003

 - Optimization of `newhall_batch()` (converting everything to `rJava::.jcall()`)

# jNSMR 0.0.0.9002

 - Added a `NEWS.md` file to track changes to the package.
 
# jNSMR 0.0.0.9001

 - jNSM source code and initial R package structure (d992732)
