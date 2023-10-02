#### Public general utilities ----

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

#### Private general utilities ----

.add_LL <- function(x, template = NULL) {
  # add longitude and latitude layers
  if (is.null(template)) {
    template <- "OGC:CRS84"
  }
  
  if (!terra::is.lonlat(x)) {
    x1 <- terra::project(x[[1]], template)
    if (!terra::is.lonlat(x1)) {
      stop("`template` should be a longitude/latitude geographic coordinate system", 
           call. = FALSE)
    }
  } else {
    x1 <-  x[[1]]
  }
  
  cx <- terra::rast(list(x = terra::init(x1, "x"), 
                         y = terra::init(x1, "y")))
  cx <- terra::project(cx, x)
  x$lonDD <- cx$x
  x$latDD <- cx$y
  x
}

#### PRISM Monthly Normals----

#' Load PRISM Monthly "Normals" at 800 meter or 4 kilometer Resolution
#' 
#' @description `newhall_prism_cache()`: Uses the [prism](https://cran.r-project.org/package=prism) package to download and cache data at the specified resolution. At this time only monthly grids for 30 year Normals (1991-2020) are supported.
#' 
#' @param resolution character. Either `"800m"` (default) or `"4km"`
#' @param overwrite Force download of new cache files? Default: `FALSE`.
#' @param PRISM_PATH Default: `file.path(newhall_data_dir("cache"), "PRISM")`
#' 
#' @references PRISM Climate Group, Oregon State University, \url{https://prism.oregonstate.edu}, data created 4 Feb 2014, accessed 22 Jul 2023.
#' @seealso [newhall_data_dir()]
#'
#' @return character. Vector of file paths (to PRISM .BIL files).
#' @rdname newhall_prism
#' @export
newhall_prism_cache <- function(
    resolution = "800m",
    overwrite = FALSE,
    PRISM_PATH = file.path(newhall_data_dir("cache"), "PRISM")
  ) {
  
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
newhall_prism_rast <- function(
    resolution = "800m",
    PRISM_PATH = file.path(newhall_data_dir("cache"), "PRISM"),
    bilfile = list.files(file.path(PRISM_PATH, resolution), "\\.bil$", recursive = TRUE)
  ) {
  
  resolution <- match.arg(tolower(trimws(resolution)), c("4km", "800m"))
  
  # capture file name components
  mp <- ".*PRISM_([a-z]+)_(30yr_normal)_(\\d+[a-z]+)M[1-9]_(\\d{2})_bil\\.bil"
  bilfile_sub <- bilfile[grep(mp, basename(bilfile))]
  bilmonth <- strsplit(gsub(mp,  "\\1;\\2;\\3;\\4", bilfile_sub), ";")
  bil <- data.frame(bilfile_sub, do.call('rbind', bilmonth))
  
  if (nrow(bil) != 24) {
    stop("One or more PRISM monthly grids are missing from ",
         file.path(PRISM_PATH, resolution),
         ".\n\tTry running `newhall_prism_cache()` to download.", call. = FALSE)
  }
  
  colnames(bil) <- c("bilfile", "variable", "product", "resolution", "month")
  f <- file.path(PRISM_PATH, resolution, bil$bilfile[which(bil$resolution == resolution)])
  if (length(f) != 24) {
    stop("One or more PRISM monthly grids are missing from ",
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
  
  # use NAD83 longitude/latitude
  .add_LL(prism_rast, newhall_nad83_template(resolution = resolution))
}

#' @description `newhall_prism_subset():` Used to create a subset of the PRISM data corresponding to the extent of an input spatial object `x`.
#' 
#' @param x A _SpatVector_, _SpatRaster_, _SpatExtent_, or any other object type suitable to use with `terra::crop()`.
#' @param template Template _SpatRaster_ or target CRS specification for re-projection. Default: `newhall_nad83_template()`
#' @export
#' @rdname newhall_prism
newhall_prism_subset <- function(
    x,
    resolution = "800m",
    template = newhall_nad83_template(resolution = resolution),
    PRISM_PATH = file.path(newhall_data_dir("cache"), "PRISM")
  ) {
  
  pris <- newhall_prism_rast(PRISM_PATH = PRISM_PATH, resolution = resolution)
  ex <- terra::project(terra::as.polygons(x, extent = TRUE), pris)
  res <- terra::crop(pris, ex)
  if (!terra::same.crs(res, template)) {
    res <- terra::project(res, template)
  }
  res
}

#' Standard PRISM lower 48 NAD83 Template
#' 
#' 
#' @description `newhall_nad83_template()`: Empty `SpatRaster` corresponding to the lower 48 United States PRISM data/extent at the specified resolution.
#' @rdname newhall_prism
#' @export 
#' @examples
#' 
#' newhall_nad83_template()
#' 
#' newhall_nad83_template("4km")
newhall_nad83_template <- function(
    resolution = "800m"
  ) {
  
  nm <- c(paste0("p", month.abb), paste0("t", month.abb),
          'lonDD', 'latDD', 'elev', 'awc')
  
  sr <- 'GEOGCRS[\"NAD83\",DATUM[\"North American Datum 1983\",ELLIPSOID[\"GRS 1980\",6378137,298.257222101,LENGTHUNIT[\"metre\",1]],ID[\"EPSG\",6269]],PRIMEM[\"Greenwich\",0,ANGLEUNIT[\"Degree\",0.0174532925199433]],CS[ellipsoidal,2],AXIS[\"longitude\",east,ORDER[1],ANGLEUNIT[\"Degree\",0.0174532925199433]],AXIS[\"latitude\",north,ORDER[2],ANGLEUNIT[\"Degree\",0.0174532925199433]]]'
  
  resolution <- match.arg(tolower(trimws(resolution)), c("4km", "800m"))
  
  if (resolution == "4km") 
    rast(ncols = 1405, nrows = 621, nlyrs = 28,
         xmin = -125.020833333333, xmax = -66.4791666661985,
         ymin = 24.0624999997935, ymax = 49.9375000000005,
         names = nm, crs = sr)
  else rast(ncols = 7025, nrows = 3105, nlyrs = 28,
            xmin = -125.020833333331, xmax = -66.479166690081,
            ymin = 24.062500342571, ymax = 49.937500332221,
            names = nm, crs = sr)
}

#' @param i Month index (0 to 12); 0 for "annual", 1-12 for specific months
#' @param resolution Either `"4km"` or `"800m"`
#' @param annual Download annual raster? Default: `FALSE`. If `i` contains `0` then `annual=TRUE`
#' @param keepZip Keep downloaded ZIP files? Default: `FALSE`
#' @noRd
.prism_download_normals <- function(
    i,
    resolution = c("4km", "800m"),
    annual = FALSE,
    keepZip = FALSE
  ) {
  
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

#### Daymet Monthly Average ----
# calculated by default for same PRISM "normal" period 1991-2020
# newhall_daymet_cache <- function(start_year = 1991,
#                                  end_year = 2020,
#                                  DAYMET_PATH = file.path(newhall_data_dir("cache"), "DAYMET")) {
#   # slow/inconvenient to download whole dataset, even at monthly granularity
# }

#' Load DAYMET Monthly Data at 1 kilometer Resolution
#' 
#' @description `newhall_daymet_subset():` Used to create a subset of the DAYMET data corresponding to the extent of an input spatial object `x`.
#' 
#' @param x A _SpatVector_, _SpatRaster_, _SpatExtent_, or any other object type suitable to use with `terra::ext()`
#' 
#' @param start_year integer. First year in range to download
#' @param end_year integer. Last year in range to download.
#' @param force logical. Force download when files exist in `DAYMET_PATH`? Default: `FALSE`
#' @param DAYMET_PATH Default: `file.path(newhall_data_dir("cache"), "DAYMET")`
#' @references Thornton, M.M., R. Shrestha, Y. Wei, P.E. Thornton, S-C. Kao, and B.E. Wilson. 2022. Daymet: Monthly Climate Summaries on a 1-km Grid for North America, Version 4 R1. ORNL DAAC, Oak Ridge, Tennessee, USA. \doi{https://doi.org/10.3334/ORNLDAAC/2131}
#' @seealso [newhall_data_dir()]
#'
#' @return A _SpatRaster_ object
#' @export
#' @rdname newhall_daymet
newhall_daymet_subset <- function(
    x,
    start_year = 1991,
    end_year = 2020,
    force = FALSE,
    DAYMET_PATH = tempdir()
  ) {
  
  .daymet_download_monthly(x,
                           start_year = start_year,
                           end_year = end_year,
                           DAYMET_PATH = DAYMET_PATH)
  
  prf <- list.files(DAYMET_PATH, "prcp_.*\\.nc$", full.names = TRUE)
  trf <- list.files(DAYMET_PATH, "t(min|max)_.*\\.nc$", full.names = TRUE)
  
  npr <- terra::rast(prf)
  ntr <- terra::rast(trf)
  
  # TODO: may contain extra years; filter to times within [start,end]
  
  prc <- terra::rast(lapply(terra::split(npr, names(npr)), terra::mean))
  tmp <- terra::rast(lapply(terra::split(ntr, gsub("min|max", "emp", names(ntr))), terra::mean))
  
  names(prc) <- paste0("p", month.abb)
  names(tmp) <- paste0("t", month.abb)
  
  # use OGC:CRS84 longitude/latitude
  .add_LL(c(prc, tmp))
}

.daymet_download_monthly <- function(
    x, 
    start_year = 1991,
    end_year = 2020,
    force = FALSE,
    DAYMET_PATH = tempdir()) {
  # DAYMET_PATH = file.path(newhall_data_dir("cache"), "DAYMET")) { # TODO: cache?
  
  if (!dir.exists(DAYMET_PATH)) {
    dir.create(DAYMET_PATH, recursive = TRUE, showWarnings = FALSE)
  }
  
  ## TODO: cache whole prism extent?
  # c(ymax = 49.937500332221, 
  #   xmin = -125.020833333331, 
  #   ymin = 24.062500342571, 
  #   xmax = -66.479166690081)
  
  loc <- sapply(terra::ext(x), as.numeric)[c(4,1,3,2)]
  
  if (length(list.files(DAYMET_PATH, "\\.nc$"))
      < (3 * (end_year - start_year)) || force) {
    # TODO: need to check all years start:end are present
    # download if missing
    daymetr::download_daymet_ncss(
      location = loc,
      start = start_year,
      end = end_year,
      path = DAYMET_PATH,
      param = c("tmax", "tmin", "prcp"), #,
                # "vp", "srad", "dayl", "swe"),
      frequency = "monthly" 
    )
  }
}

#### ISSR-800/SoilWeb Soil Properties ----

#' Load SoilWeb "ISSR-800" at 800 meter Resolution
#' 
#' Currently the only ISSR-800 data are only available for the contiguous (lower 48) United States. The only property cached for use in the Newhall model is the "available water holding capacity" (sum of storage for the whole profile, in millimeters). For consistency with PRISM grid the values are reprojected from `"EPSG:5070"` to `"EPSG:4269"` (see `newhall_nad83_template()`)
#' 
#' @details The data are stored in centimeters on the SoilWeb server. When `newhall_issr800_cache()` saves the file the data are converted to millimeters, which is required for the Newhall model.
#' 
#' @param ISSR800_PATH Default: `file.path(newhall_data_dir("cache"), "SoilWeb", "800m")`
#' @param template Template _SpatRaster_ or target CRS specification for reprojection. Default: `newhall_nad83_template()`
#' @param overwrite Force download of new cache files? Default: `FALSE`.
#' @seealso [newhall_data_dir()]
#'
#' @return character. Path to cached water storage GeoTIFF.
#' @export
#' @rdname newhall_issr800
#' @importFrom utils download.file
newhall_issr800_cache <- function(
    ISSR800_PATH = file.path(newhall_data_dir("cache"), "SoilWeb", "800m"),
    template = newhall_nad83_template(),
    overwrite = FALSE
  ) {
    
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


#' @description `newhall_issr800_subset():` Used to create a subset of the ISSR-800 soil available water storage data corresponding to the extent of an input spatial object `x`.
#' 
#' @param x A _SpatVector_, _SpatRaster_, _SpatExtent_, or any other object type suitable to use with `terra::crop()`; 
#' @export
#' @rdname newhall_issr800
newhall_issr800_subset <- function(
    x,
    template = newhall_nad83_template(),
    ISSR800_PATH = file.path(newhall_data_dir("cache"), "SoilWeb", "800m")
  ) {
  
  issr <- newhall_issr800_rast(ISSR800_PATH = ISSR800_PATH)
  ex <- terra::project(terra::as.polygons(x, extent = TRUE), issr)
  res <- terra::crop(issr, ex)
    
  if (!terra::same.crs(res, template)) {
    res <- terra::project(res, template)
  }
  res
}

#' @description `newhall_issr800_rast()`: Create a _SpatRaster_ object. This object contains Available Water Capacity (Storage) for at 800 meter resolution using the standard jNSM column naming scheme. 
#' @param tiffile Optional: custom vector of paths to files to use to build raster. Defaults to all .TIF files in the specified cache directory and resolution.
#' @references Walkinshaw, Mike, A.T. O'Geen, D.E. Beaudette. "Soil Properties." California Soil Resource Lab, 1 Oct. 2022, \url{https://casoilresource.lawr.ucdavis.edu/soil-properties/}. 
#' @export
#' @rdname newhall_issr800
newhall_issr800_rast <- function(
    ISSR800_PATH = file.path(newhall_data_dir("cache"), "SoilWeb", "800m"),
    tiffile = list.files(ISSR800_PATH, "\\.tif$", full.names = TRUE)
  ) {
  
  res <- terra::rast(tiffile)
  names(res) <- "awc"
  res
}
