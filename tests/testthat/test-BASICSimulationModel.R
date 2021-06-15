test_that("newhall_simulation() works", {
  library(jNSMR)

  cat("\n\njNSMR -- ", basename(.jnsm_jar_file(paste0("-", newhall_version()))), "\n\n")

  # direct input with NewhallDataset built from R objects

  input_direct <- NewhallDataset(
    stationName = "WILLIAMSPORT",
    country = "US",
    latDD = 41.24,
    lonDD = -76.92,
    elev = 158.0,
    allPrecipsDbl = c(44.2, 40.39, 113.54, 96.77, 95, 98.55,
                      66.04, 13.46, 54.86, 6.35, 17.53, 56.39),
    allAirTempsDbl = c(-2.17, 0.89, 3.72, 9.11, 16.28, 21.11,
                       22.83, 21.94, 19.78, 10.5, 5.33, -1.06),
    pdbegin = 1930,
    pdend = 1930,
    smcsawc = 200.0
  )

  expect_true(inherits(newhall_simulation(dataset = input_direct), 'jobjRef'))

  ndmeta <- NewhallDatasetMetadata(stationName = "foo", #String
      stationId = "bar", #String
      elev = 101.1, #double
      stationStateProvidence = "CA", #String
      stationCountry = "USA", #String
      mlraName = "", #String
      mlraId = 0, #int
      contribFirstName = "Josephine", #String
      contribLastName = "Aiken", #String
      contribTitle = "Soil Scientist", #String
      contribOrg = "USDA-NRCS", #String
      contribAddress = "", #String
      contribCity = "", #String
      contribStateProvidence = "", #String
      contribPostal = "", #String
      contribCountry = "", #String
      contribEmail = "", #String
      contribPhone = "", #String
      notes = c("Note 1", "Note 2", "Note 3"), #List<String>
      runDate = "1970-01-01", #String
      modelVersion = newhall_version(), #String
      unitSystem = "cm", #String
      soilAirOffset = 1.2, #double
      amplitude = 0.66, #double
      network = "foo" #String
  )

  expect_equal(ndmeta$getContribFirstName(), "Josephine")

  newhall_CSVResultsExporter(newhall_simulation(dataset = input_direct), tempfile())
})
