# batch demo

library(jNSMR)

pathname <- system.file("extdata/All PA jNSM Example Batch Metric.csv", package = "jNSMR")[1]

res <- newhall_batch(pathname = pathname)

head(res)
