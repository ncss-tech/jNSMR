# jNSMR 0.2.0

- Added helper functions for raster input data preparation. Initial sources include:
  - PRISM monthly climate "normals," DAYMET monthly aggregated climate, SoilWeb ISSR800 (Available Water Storage Capacity)
    
- Added sample data (PRISM+ISSR800, DAYMET+ISSR800 in GeoTIFF format) to inst/extdata
  
- Added `core_thresh` argument to `newhall_batch()`  and better heuristics for `nrows` with and without multiple `cores`
 - Recompiled JAR v1.6.4
 - Abstracted out longitude/latitude grid calculation, use `"OGC:CRS84"` for consistent determinations globally
   
- Add `newhall_prism_subset()` and `"extdata/prism_issr800_sample.tif"` `newhall_daymet_subset()` and `"extdata/daymet_issr800_sample.tif"`
   - GeoTIFF files use `"INT2S"` (signed, two byte integer) datatype with `"LZW"` compression and `"PREDICTOR=2"` for small file size
     
- Set minimum R version to 4.0.0 (for `tools::R_user_dir()`)

# jNSMR 0.1.1

 - Several fixes related to "Undefined" factor levels for moisture regime and moisture regime subdivisions in the `newhall_batch(<SpatRaster>)` method (#8)
 
 - Fixed layer names for the categorical results in the `newhall_batch(<SpatRaster>)` method
 
 - Set minimum R version to 3.6.0 (based on {rJava})
 
# jNSMR 0.1.0

 - Added vignettes for using "legacy" model v1.6.1 JAR and for PRISM data; general improvements to documentation
 
 - Use `terra::set.cats()` for setting factor levels (prior approach using vector input and `levels<-` was deprecated) 

# jNSMR 0.0.0.9010

 - New JAR file v1.6.4: adds a new result `annualPotentialEvapotranspiration` (sum of mean monthly PET) to _NewhallBatchResults_ class
 
# jNSMR 0.0.0.9009

 - Fixed multicore support in `newhall_batch(<SpatRaster>)`
 
 - Optimization of `newhall_batch(<SpatRaster>)` method to skip `NA` rows and use less memory/fewer inputs
 
# jNSMR 0.0.0.9008
 
 - New JAR file v1.6.3: optimizes interface for batching in Java using simpler data structures better suited to high throughput
 
# jNSMR 0.0.0.9007

 - Add SpatRaster and RasterBrick `newhall_batch()` methods for raster input 
 
 - Updated PRISM demo (using SpatRaster interface)
 
# jNSMR 0.0.0.9006
 
 - Added PRISM demo (using data.frame interface) for `newhall_batch()`

# jNSMR 0.0.0.9005

 - Deprecated the JDOM API (JAR v1.6.1 provides legacy support for XML file formats etc.)
 
 - New JAR file v1.6.2: supports command-line inputs for running models

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
