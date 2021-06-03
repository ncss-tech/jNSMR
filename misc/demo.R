library(jNSMR)

# read single-station XML  file
input_xml <- xml_NewhallDataset("misc/WILLIAMSPORT_1930_1930_input.xml")

# or specify inputs directly to the constructor
input_direct <- NewhallDataset(stationName = "WILLIAMSPORT",
                               country = "US",
                               lat = 41.24,
                               lon = -76.92,
                               nsHemisphere = 'N',
                               ewHemisphere = 'W',
                               stationElevation = 158.0,
                               allPrecipsDbl = c(44.2,40.39,113.54,96.77,95.0,98.55,
                                                 66.04,13.46,54.86,6.35,17.53,56.39),
                               allAirTempsDbl = c(-2.17,0.89,3.72,9.11,16.28,21.11,
                                                  22.83,21.94,19.78,10.5,5.33,-1.06),
                               pdbegin = 1930,
                               pdend = 1930,
                               smcsawc = 200.0)

# run model
output <- newhall_simulation(input_direct)

# inspect results as string (requires the metadata specified in XML)
cat(newhall_XMLStringResultsExporter(input_xml, output))

# write XML results to file
newhall_XMLResultsExporter(dataset = input_direct,
                           result = output,
                           pathname = "misc/WILLIAMSPORT_1930_1930_export.xml")

# convenience method to open GUI
newhall_GUI()
