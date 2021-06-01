library(jNSMR)

# read single-station XML file
input <- NewhallDatasetFromPath("misc/WILLIAMSPORT_1930_1930_input.xml")

# run model
output <- run_simulation(input)

# inspect results as string
cat(newhall_XML_string_export(input, output))

# write XML results to file
newhall_XML_export("misc/WILLIAMSPORT_1930_1930_export.xml", input, output)

# convenience method to open GUI
openJAR()
