test_that("GUI works", {
  
  expect_silent(try(newhall_GUI(command_only = TRUE)))
  
  skip_on_cran()
  
})
