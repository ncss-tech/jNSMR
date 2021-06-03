# batch demo

library(jNSMR)

pathname <- system.file("extdata/All_PA_jNSM_Example_Batch_Metric.csv", package = "jNSMR")[1]

res <- newhall_batch(pathname = pathname)

head(res)
