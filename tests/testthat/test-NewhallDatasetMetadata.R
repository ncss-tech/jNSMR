test_that("NewhallDatasetMetadata works", {

  # construct metadata with only `stationName` (all other vars non-NULL placeholders)
  ndm <- NewhallDatasetMetadata(stationName = "foo")

  # ndm is a jobjRef to NewhallDatasetMetadata
  expect_equal(ndm$getClass()$toString(), "class org.psu.newhall.sim.NewhallDatasetMetadata")

  # make a NewhallDataset without any metadata
  nd <- NewhallDataset(stationName = "foo",
                       country = "US",
                       latDD = 37.2,
                       lonDD = -120.1,
                       elev = 100.1,
                       allPrecipsDbl = rep(2.2, 12),
                       allAirTempsDbl = rep(18.1, 12),
                       pdbegin = 1990,
                       pdend = 1990,
                       smcsawc = 200.0,
                       checkargs = TRUE)
  # metadata are null
  expect_null(nd$getMetadata())

  # set the metadata
  nd$setMetadata(ndm)

  # metadata are now set
  expect_equal(nd$getMetadata()$getClass()$toString(), "class org.psu.newhall.sim.NewhallDatasetMetadata")
})

