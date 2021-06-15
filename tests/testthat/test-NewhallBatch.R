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
