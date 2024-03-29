test_that("newhall_batch() works", {

  pathname <- system.file("extdata/All_PA_jNSM_Example_Batch_Metric.csv", package = "jNSMR")[1]
  .data <- read.csv(pathname)
  cat("\n\n")

  res2 <- newhall_batch(.data = .data, toString = FALSE)

  res2 <- newhall_batch(.data = .data, toString = FALSE)

  # testing for legacy batch interface (v1.6.1)
  resold <- batch1(.data)

  expect_true(inherits(res2, 'data.frame'))
  expect_true(inherits(resold, 'data.frame'))

  # sample minimal batch file
  newhall_writeBatchTemplate(tempfile())

  if (newhall_version() >= "1.6.3") {
    # newhall_writeBatchOutput(output_file = tempfile(), pathname = pathname)
    res3 <- batch2(.data = .data)

    expect_true(all(res2$TemperatureRegime == res3$temperatureRegime))
    expect_true(all(res2$MoistureRegime == res3$moistureRegime))
  }
})

test_that("newhall_batch() raster interfaces", {
  pathname <- system.file("extdata/All_PA_jNSM_Example_Batch_Metric.csv", package = "jNSMR")[1]
  x <- read.csv(pathname)
  x2 <- subset(x, select = c("latDD", "lonDD",
                             "tJan", "tFeb", "tMar", "tApr", "tMay", "tJun", "tJul", "tAug", "tSep", "tOct", "tNov", "tDec",
                             "pJan", "pFeb", "pMar", "pApr", "pMay", "pJun", "pJul", "pAug", "pSep", "pOct", "pNov", "pDec"))
  r <- terra::rast(lapply(x2, function(a) terra::rast(matrix(a, nrow = 9, ncol = 12), crs = "")))

  # PRISM info
  r$stationName <- 1:(terra::ncell(r))
  r$awc <- 200
  r$maatmast <- 1.2
  # r$pdType <- "Normal"
  r$pdStartYr <- 1981
  r$pdEndYr <- 2010
  r$cntryCode <- "US"

  # boilerplate minimum metadata -- these are not used by the algorithm
  r$netType <- ""
  r$elev <- -9999
  r$stProvCode <- ""
  r$notes <- ""
  r$stationID <- 1:(terra::ncell(r))
  res <- newhall_batch(r)
  expect_true(inherits(res, 'SpatRaster'))

  # test sum of monthly PET annualPotentialEvapotranspiration
  expect_true(.subset2(coef(lm(terra::values(res$annualRainfall - res$annualPotentialEvapotranspiration) ~
                               terra::values(res$annualWaterBalance))), 2) - 1 < 1e-6)
  skip_on_cran()

  # multicore
  res2 <- newhall_batch(r, cores = 2)
  expect_equal(nrow(res2), nrow(res))

})
