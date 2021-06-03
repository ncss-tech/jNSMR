test_that("newhall_batch() works", {

  pathname <- system.file("extdata/All PA jNSM Example Batch Metric.csv", package = "jNSMR")[1]

  res <- newhall_batch(pathname = pathname)

  expect_true(inherits(res, 'data.frame'))

})
