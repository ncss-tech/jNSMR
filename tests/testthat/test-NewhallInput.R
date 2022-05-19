test_that("reading/writing batch input/output works", {
  tf <- tempfile(); tf2 <- tempfile()
  expect_silent(newhall_writeBatchTemplate(tf))
  expect_equal(nrow(newhall_readBatchInput(tf)), 1)
  expect_message(newhall_writeBatchOutput(tf2, tf))
  unlink(c(tf, tf2))
})
