test_that("newhall_data_dir works", {
  x <- newhall_data_dir()
  expect_true(is.character(x) && length(x) == 1)
})

test_that(".add_LL works", {
  x <- .add_LL(terra::rast())
  expect_true(terra::nlyr(x) == 2 && all(c("lonDD", "latDD") %in% names(x)))
})

test_that("newhall_prism_cache works", {
  # NOTE: downloads 1 GB+ of .BIL files to cache dir (so not tested)
  if (FALSE) {
    
    skip_if_not_installed("prism")
    
    skip_if_offline()
    
    skip_on_cran()
    
    p <- file.path(tempdir(), "PRISM")
    
    newhall_prism_cache(resolution = "800m", PRISM_PATH = p) # default
    d800 <- file.path(p, "800m")
    expect_true(length(list.files(d800, "\\.bil$")) > 0)
    
    newhall_prism_cache(resolution = "4km", PRISM_PATH = p)
    d4000 <- file.path(p, "4km")
    expect_true(length(list.files(d4000, "\\.bil$")) > 0)
    
    unlink(p, recursive = TRUE)
  }
})

test_that("newhall_prism_rast works", {
  
  skip_if_not_installed("prism")
  
  skip_if_offline()
  
  skip_on_cran()
  
  expect_error(newhall_prism_rast(PRISM_PATH = tempdir()))
})

test_that("newhall_prism_subset works", {
  
  skip_if_not_installed("prism")
  
  skip_if_offline()
  
  skip_on_cran()
    
  if (TRUE) {
    expect_error({
      x <- newhall_prism_subset(PRISM_PATH = tempdir(),
                                terra::as.polygons(terra::ext(-90, -89, 34, 35),
                                                   crs = "OGC:CRS84"))
    })
  }
})

test_that("newhall_nad83_template works", {
  expect_true(inherits(newhall_nad83_template(), 'SpatRaster'))
  expect_true(inherits(newhall_nad83_template(resolution = "4km"), 'SpatRaster'))
})

test_that("newhall_daymet_subset works", {
  
  skip_if_not_installed("daymetr")
  
  skip_if_offline()
  
  skip_on_cran()
    
  if (TRUE) {
    sink({tf <- tempfile()})
    x <- try(newhall_daymet_subset(
      terra::as.polygons(terra::ext(-90, -89.9, 34.9, 35),
                         crs = "OGC:CRS84"),
      start_year = 2020,
      end_year = 2022
    ))
    sink()
    unlink(tf)
    expect_true(inherits(x, 'SpatRaster') || inherits(x, 'try-error'))
    if (inherits(x, 'SpatRaster')) {
      expect_equal(terra::nlyr(x), 26L)
    } else {
      expect_true(grepl("are missing from", x[1]))
    }
  }
})

test_that("newhall_issr800_cache works", {
  skip_if_offline()
  
  skip_on_cran()
  
  if (FALSE) {
    td <- tempdir() 
    res <- newhall_issr800_cache(td)
    expect_true(inherits(terra::rast(res, "SpatRaster")))
  }
})

test_that("newhall_issr800_rast works", {
  
  skip_if_offline()
  
  skip_on_cran()
  
  expect_error(newhall_issr800_rast(ISSR800_PATH = tempdir()))
})

test_that("newhall_issr800_subset works", {

  skip_if_offline()
  
  skip_on_cran()
  
  if (TRUE) {
    expect_error({
      x <- newhall_issr800_subset(ISSR800_PATH = tempdir(),
                                  terra::as.polygons(terra::ext(-90, -89, 34, 35),
                                                     crs = "OGC:CRS84"))
    })
  }
})
