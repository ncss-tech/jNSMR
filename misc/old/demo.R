## ---- eval = FALSE-----------------------------------------------------------------------------
## # install.packages('remotes')
## remotes::install_github('ncss-tech/jNSMR')


## ----------------------------------------------------------------------------------------------
library(jNSMR)

# read single-station XML file (NewhallDataset + NewhallDatasetMetadata)
input_xml_file <- system.file("extdata/WILLIAMSPORT_1930_1930_input.xml", package = "jNSMR")[1]
input_xml <- xml_NewhallDataset(input_xml_file)

# or specify inputs directly to the constructor
input_direct <- NewhallDataset(stationName = "WILLIAMSPORT",
                               country = "US",
                               latDD = 41.24,
                               lonDD = -76.92,
                               elev = 158.0,
                               allPrecipsDbl = c(44.2,40.39,113.54,96.77,95.0,98.55,
                                                 66.04,13.46,54.86,6.35,17.53,56.39),
                               allAirTempsDbl = c(-2.17,0.89,3.72,9.11,16.28,21.11,
                                                  22.83,21.94,19.78,10.5,5.33,-1.06),
                               pdbegin = 1930,
                               pdend = 1930,
                               smcsawc = 200.0)

# set minimal _NewhallDatasetMetadata_ for our constructed _NewhallDataset_
input_direct$setMetadata(NewhallDatasetMetadata(stationName = "WILLIAMSPORT"))


## ----------------------------------------------------------------------------------------------
# run single model (from XML file)
output_xml <- newhall_simulation(input_xml)

# run single model (from direct input)
output <- newhall_simulation(input_direct)


## ---- eval=FALSE-------------------------------------------------------------------------------
## # convenience method to open GUI with the R package
## newhall_GUI()


## ----------------------------------------------------------------------------------------------
cat(output$getFormattedStatistics())


## ----------------------------------------------------------------------------------------------
cat(output$getFormattedMoistureCalendar())


## ----------------------------------------------------------------------------------------------
cat(output$getFormattedTemperatureCalendar())


## ----------------------------------------------------------------------------------------------
# inspect XML result format
cat(newhall_XMLStringResultsExporter(input_xml, output))


## ----------------------------------------------------------------------------------------------
# write XML results to file
newhall_XMLResultsExporter(dataset = input_direct,
                           result = output,
                           pathname = "misc/WILLIAMSPORT_1930_1930_export.xml")


## ----------------------------------------------------------------------------------------------
pathname <- system.file("extdata/All_PA_jNSM_Example_Batch_Metric.csv", package = "jNSMR")[1]

res <- data.table::data.table(newhall_batch(pathname = pathname, toString = FALSE, verbose = FALSE))

head(res)

