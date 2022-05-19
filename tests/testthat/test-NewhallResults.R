test_that("Test legacy XML input/output", {
  skip_if(newhall_version() == "1.6.1")
  expect_message(XMLResultsExporter())
  expect_message(XMLStringResultsExporter())
  expect_message(XMLFileParser(tempfile()))
  expect_message(xml_NewhallDataset(tempfile()))
})
