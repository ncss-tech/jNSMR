#' Iterate over multiband output and write as single-layer files
#' 
#' This function is a simple wrapper around `terra:writeRaster()` that makes it easier
#' to separate the individual layers of an input or output grid as separate files.
#' 
#' @param x character. Path to raster file(s) to split by layer.
#' @param output_dir character. Default: `NULL` creates new directories in current working directory. Alternately specify a different path for output folders to be created.
#' @param ... Additional arguments to `terra::writeRaster()`.
#'
#' @return New directories are created in `output_dir` (or current working directory) based on each input file `x`.
#' @export
#'
#' @examples
#' library(terra)
#' 
#' x <- writeRaster(rast(list(a = rast(matrix(1)), 
#'                            b = rast(matrix(2)))), "test.tif")
#' 
#' writeRasterLayers("test.tif", "test")
#' 
#' unlink(c("test.tif", "test"), recursive=TRUE)
writeRasterLayers <- function(x, output_dir = NULL, ...) {
  stopifnot(is.character(x))
  s <- lapply(x, terra::rast)
  if (is.null(output_dir) || length(output_dir) != length(s)) {
    output_dir <- rep(output_dir, length(s))
  }
  fx <- tools::file_ext(x)
  for (i in seq(s)) {
    bn <- basename(x[i])
    fp <- file.path(output_dir[i],
                    gsub(paste0("\\.", fx[i]), "", bn),
                    paste0(gsub(paste0("\\.", fx[i]), "", bn),
                           paste0("_", names(s[[i]]), ".", fx[i])))
    udn <- unique(dirname(fp))
    if (!dir.exists(udn)) {
      dir.create(udn, recursive = TRUE, showWarnings = FALSE)
    }
    terra::writeRaster(s[[i]], filename = fp, ...)
  }
}
