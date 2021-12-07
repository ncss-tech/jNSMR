test_that("newhall_batch() works", {

  pathname <- system.file("extdata/All_PA_jNSM_Example_Batch_Metric.csv", package = "jNSMR")[1]

  cat("\n\n")

  # sample minimal batch file
  newhall_writeBatchTemplate(tempfile())

  # write output directly to tempfile
  tf2 <- tempfile()
  newhall_writeBatchOutput(output_file = tf2, pathname = pathname)
  # res2 <- newhall_batch(pathname = pathname, toString = FALSE)

  cat("\n")

  expect_true(inherits(read.csv(tf2), 'data.frame'))

})

test_that("newhall_batch() raster interfaces", {
  pathname <- system.file("extdata/All_PA_jNSM_Example_Batch_Metric.csv", package = "jNSMR")[1]
  x <- read.csv(pathname)
  x2 <- subset(x, select = c("latDD", "lonDD", "tJan", 
                             "tFeb", "tMar", "tApr", "tMay", "tJun", "tJul", "tAug", "tSep", 
                             "tOct", "tNov", "tDec", "pJan", "pFeb", "pMar", "pApr", "pMay", 
                             "pJun", "pJul", "pAug", "pSep", "pOct", "pNov", "pDec"))
  r <- terra::rast(lapply(x2, function(a) terra::rast(as.data.frame(a))))
  
  # PRISM info
  r$stationName <- 1:(terra::ncell(r))
  r$awc <- 200
  r$maatmast <- 1.2
  r$pdType <- "Normal"
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
})
