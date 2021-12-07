# jNSMR 0.0.0.9007

 - Add SpatRaster and RasterBrick `newhall_batch()` methods for raster input 
 
 - Updated PRISM demo (using SpatRaster interface)
 
# jNSMR 0.0.0.9006
 
 - Added PRISM demo (using data.frame interface) for `newhall_batch()`

# jNSMR 0.0.0.9005

 - Deprecated the JDOM API (JAR v1.6.1 provides legacy support for XML file formats etc.)
 
 - New JAR file v1.6.2 supports command-line inputs for running models

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
