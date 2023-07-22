# batch demo
library(tibble)
library(jNSMR)

pathname <- system.file("extdata/All_PA_jNSM_Example_Batch_Metric.csv", package = "jNSMR")[1]

res <- tibble(newhall_batch(pathname = pathname))
#> newhall_batch: ran n=108 simulations in 0.72 seconds

head(res)
#> # A tibble: 6 x 3
#>   dataset   results   output
#>   <list>    <list>    <list>
#> 1 <jobjRef> <jobjRef> <chr [1]>
#> 2 <jobjRef> <jobjRef> <chr [1]>
#> 3 <jobjRef> <jobjRef> <chr [1]>
#> 4 <jobjRef> <jobjRef> <chr [1]>
#> 5 <jobjRef> <jobjRef> <chr [1]>
#> 6 <jobjRef> <jobjRef> <chr [1]>

## n=5546 NWS Co-op stations 1971-2000
## CSV (1.03 MB): https://www.nrcs.usda.gov/wps/PA_NRCSConsumption/download?cid=stelprdb1237654&ext=csv
## set `unitsystem="english"` for inches of precipitation and degrees Fahrenheit

res <- newhall_batch(pathname = "~/Downloads/NWSCOOP_1971-2000_jNSM_Batch_ENGLISH.csv", unitSystem = "english")
#> newhall_batch: ran n=5545 simulations in 32 seconds

dir.create("misc/NWSCOOP_1971-2000/")
wrt <- lapply(seq_len(nrow(res)), function(i) {

    da <- res[i,]$dataset[[1]]

    da$setMetadata(NewhallDatasetMetadata(stationName = da$getName()))

    newhall_XMLResultsExporter(da, res[i,]$result[[1]], file.path("misc/NWSCOOP_1971-2000/", paste0(i,".xml")))
  })

## other arguments

## combine two CSVs in a single newhall_batch() call

# res <- newhall_batch(pathname = c("foo.csv","bar.csv'))
