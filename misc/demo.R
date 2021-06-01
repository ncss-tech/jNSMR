# demo.R

library(jNSMR)

# read single-station XML file
input <- NewhallDatasetFromPath("inst/extdata/WILLIAMSPORT_1930_1930_input.xml")

## TODO: create single or multi-station data from an R data.frame (equivalent to batch CSV)
# input <- NewhallDataset(data)

# run model
output <- run_simulation(input)

# inspect results
newhall_XML_string_export(input, output)

# write XML results to file
newhall_XML_export("inst/extdata/WILLIAMSPORT_1930_1930_export2.xml", input, output)

# convenience method to open GUI
openJAR()
