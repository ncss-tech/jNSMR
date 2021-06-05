test_that("newhall_batch() works", {

  pathname <- system.file("extdata/All_PA_jNSM_Example_Batch_Metric.csv", package = "jNSMR")[1]

  cat("\n\n")

  res1 <- newhall_batch(pathname = pathname)
  # res2 <- newhall_batch(pathname = pathname, toString = FALSE)

  cat("\n")

  expect_true(all(sapply(list(res1), inherits, 'data.frame')))

})
