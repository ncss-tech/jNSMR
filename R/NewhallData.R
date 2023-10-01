#' Newhall Data Directory
#'
#' Returns a platform-specific user-level directory where data, configuration and cache files may be stored.
#'
#' @param which One of: `"data"`, `"config"`, or `"cache"`
#'
#' @return character. Directory path.
#' @export
newhall_data_dir <- function(which = c("data", "config", "cache")) {
  tools::R_user_dir("jNSMR", which = which)
}

#### PRISM Monthly Normals----

#' Load PRISM Monthly "Normals" at 800 meter or 4 kilometer Resolution
#' 
#' @description `newhall_prism_cache()`: Uses the [prism](https://cran.r-project.org/package=prism) package to download and cache data at the specified resolution. At this time only monthly grids for 30 year Normals (1991-2020) are supported.
#' 
#' @param resolution character. Either `"800m"` or `"4km"`
#' @param overwrite Force download of new cache files? Default: `FALSE`.
#' @param PRISM_PATH Default: `file.path(newhall_data_dir("cache"), "PRISM")`
#' @references PRISM Climate Group, Oregon State University, \url{https://prism.oregonstate.edu}, data created 4 Feb 2014, accessed 22 Jul 2023.
#' @seealso [newhall_data_dir()]
#'
#' @return character. Vector of file paths (to PRISM .BIL files).
#' @rdname newhall_prism
#' @export
newhall_prism_cache <- function(resolution = "800m", overwrite = FALSE,
                                PRISM_PATH = file.path(newhall_data_dir("cache"), "PRISM")) {
  
  resolution <- match.arg(tolower(trimws(resolution)), c("4km", "800m"))
  
  if (!requireNamespace("prism")) {
    stop("package 'prism' is required to download PRISM data", call. = FALSE)
  }
  
  # only download the PRISM data if it can't be found in PRISM_PATH
  PRISM_PATH1 <- file.path(PRISM_PATH, resolution)
  bilfile <- list.files(PRISM_PATH1, "\\.bil$", recursive = TRUE)
  if (length(bilfile) == 0 || overwrite) {
    dir.create(PRISM_PATH1, recursive = TRUE)
    prism::prism_set_dl_dir(PRISM_PATH1)
    # TODO: add support for arbitrary years/intervals
    res <- sapply(1:12, .prism_download_normals, resolution = resolution)
    if (any(!res)) {
      stop("Failed to download ", resolution, " resolution PRISM",
           " grids for the following months:\n\t",
           paste0(month.abb[(1:12)[!res]], collapse = ", "))
    }
  }
  
  bilfile <- do.call('c', lapply(PRISM_PATH1, 
                                 list.files, "\\.bil$",
                                 recursive = TRUE))
  bilfile
}

#' @description `newhall_prism_rast()`: Create a _SpatRaster_ object. This object contains temperature and precipitation data for the specified data set, at the specified resolution, using the standard jNSM column naming scheme. 
#' @param bilfile Optional: custom vector of paths to files to use to build raster. Defaults to all .BIL files in the specified cache directory and resolution.
#'
#' @export
#' @rdname newhall_prism
newhall_prism_rast <- function(resolution = "800m",
                               PRISM_PATH = file.path(newhall_data_dir("cache"), "PRISM"),
                               bilfile = list.files(file.path(PRISM_PATH, resolution),
                                                    "\\.bil$", recursive = TRUE)) {
  
  resolution <- match.arg(tolower(trimws(resolution)), c("4km", "800m"))
  
  # capture file name components
  mp <- ".*PRISM_([a-z]+)_(30yr_normal)_(\\d+[a-z]+)M[1-9]_(\\d{2})_bil\\.bil"
  bilfile_sub <- bilfile[grep(mp, basename(bilfile))]
  bilmonth <- strsplit(gsub(mp,  "\\1;\\2;\\3;\\4", bilfile_sub), ";")
  bil <- data.frame(bilfile_sub, do.call('rbind', bilmonth))
  colnames(bil) <- c("bilfile", "variable", "product", "resolution", "month")
  f <- file.path(PRISM_PATH, resolution, bil$bilfile[which(bil$resolution == resolution)])
  if (length(f) != 24) {
    stop("One or more PRISM monthly grids is missing from ",
         file.path(PRISM_PATH, resolution),
         ".\n\tTry running `newhall_prism_cache()` to download.", call. = FALSE)
  }
  prism_rast <- terra::rast(f)
  
  # fix column names for jNSM style batch input format
  monthnames <- month.abb[as.integer(gsub(".*([0-9]{2})_bil$", "\\1", names(prism_rast)))]
  names(prism_rast) <- paste0(gsub("ppt", "p", gsub("tmean","t", 
                                                    gsub("PRISM_(ppt|tmean)_.*[0-9]{2}_bil$", "\\1",
                                                         names(prism_rast)))),  
                              ifelse(is.na(monthnames), "", monthnames))
  
  # add longitude and latitude layers
  cx <- rast(list(x = terra::init(prism_rast, "x"), 
                  y = terra::init(prism_rast, "y")))
  prism_rast$lonDD <- cx$x
  prism_rast$latDD <- cx$y
  
  prism_rast
}

#' @description `newhall_prism_extent():` Used to create a subset of the PRISM data corresponding to the extent of an input spatial object `x`.
#' 
#' @param x A _SpatVector_, _SpatRaster_, _SpatExtent_, or any other object type suitable to use with `terra::crop()`; 
#' @export
#' @rdname newhall_prism
newhall_prism_extent <- function(x, resolution = "800m") {
  terra::crop(newhall_prism_rast(resolution = resolution), x)
}

#' @param i Month index (0 to 12); 0 for "annual", 1-12 for specific months
#' @param resolution Either `"4km"` or `"800m"`
#' @param annual Download annual raster? Default: `FALSE`. If `i` contains `0` then `annual=TRUE`
#' @param keepZip Keep downloaded ZIP files? Default: `FALSE`
#' @noRd
.prism_download_normals <- function(i,
                                    resolution = c("4km", "800m"),
                                    annual = FALSE,
                                    keepZip = FALSE) {
  
  if (i == 0) {
    annual <- TRUE
  }
  
  res <- try({
    prism::get_prism_normals(
      mon = i,
      type = "ppt",
      resolution = resolution,
      annual = annual,
      keepZip = keepZip
    )
    prism::get_prism_normals(
      mon = i,
      type = "tmean",
      resolution = resolution,
      annual = annual,
      keepZip = keepZip
    )
  })
  return(!inherits(res, 'try-error'))
}

#### ISSR-800/SoilWeb Soil Properties ----

#' Load SoilWeb "ISSR-800" at 800 meter Resolution
#' 
#' Currently the only ISSR-800 data are only available for the contiguous (lower 48) United States. The only property cached for use in the Newhall model is the "available water holding capacity" (sum of storage for the whole profile). For consistency with PRISM grid the values are reprojected from `"EPSG:5070"` to `"EPSG:4269"`.
#' 
#' @param ISSR800_PATH Default: `file.path(newhall_data_dir("cache"), "SoilWeb", "800m")`
#' @param template Template SpatRaster or target CRS specification for reprojection. Default: `newhall_prism_rast()`
#' @param overwrite Force download of new cache files? Default: `FALSE`.
#' @seealso [newhall_data_dir()]
#'
#' @return character. Path to cached water storage GeoTIFF.
#' @export
#' @rdname newhall_issr800
#' @importFrom utils download.file
newhall_issr800_cache <-
  function(ISSR800_PATH = file.path(newhall_data_dir("cache"),
                                    "SoilWeb", "800m"),
           template = newhall_prism_rast(),
           overwrite = FALSE) {
    
  aws <- file.path(ISSR800_PATH, paste0("aws_", format(Sys.time(), "%Y"), ".tif"))
  if (!dir.exists(ISSR800_PATH)) {
    dir.create(ISSR800_PATH, recursive = TRUE)
  }
  if (!file.exists(aws) || overwrite) {
    res <- try(utils::download.file("https://soilmap2-1.lawr.ucdavis.edu/800m_grids/rasters/water_storage.tif", 
                             destfile = aws, mode = "wb"))
    if (inherits(res, "try-error"))
      return(NULL)
  }
  r <- terra::rast(aws)*10
  r <- terra::project(r, template, filename = aws, overwrite = TRUE)
  aws
}


#' @description `newhall_issr800_extent():` Used to create a subset of the ISSR-800 soil available water storage data corresponding to the extent of an input spatial object `x`.
#' 
#' @param x A _SpatVector_, _SpatRaster_, _SpatExtent_, or any other object type suitable to use with `terra::crop()`; 
#' @export
#' @rdname newhall_issr800
newhall_issr800_extent <- function(x) {
  terra::crop(newhall_issr800_rast(), x)
}

#' @description `newhall_issr800_rast()`: Create a _SpatRaster_ object. This object contains Available Water Capacity (Storage) for at 800 meter resolution using the standard jNSM column naming scheme. 
#' @param tiffile Optional: custom vector of paths to files to use to build raster. Defaults to all .TIF files in the specified cache directory and resolution.
#' @references Walkinshaw, Mike, A.T. O'Geen, D.E. Beaudette. "Soil Properties." California Soil Resource Lab, 1 Oct. 2022, \url{https://casoilresource.lawr.ucdavis.edu/soil-properties/}. 
#' @export
#' @rdname newhall_issr800
newhall_issr800_rast <- function(ISSR800_PATH = file.path(newhall_data_dir("cache"), "SoilWeb", "800m"),
                                 tiffile = list.files(ISSR800_PATH, "\\.tif$", full.names = TRUE)) {
  res <- terra::rast(tiffile)
  names(res) <- "awc"
  res
}