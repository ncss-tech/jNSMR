# compare canopy height with O horizon presence/absence
library(terra)
library(caTools)
library(ROCR)

SSURGO_COMPPCT_THRESHOLD <- 50
N_EXHAUSTIVE_SAMPLES <- 1e5

h <- rast("canopy_height_conus_800m.tif")
names(h) <- "canopy_height_m"

o <- rast("O-horizon-pct-prism800m.tif")
names(o) <- "o_horizon_comppct"

s <- spatSample(c(h, o), N_EXHAUSTIVE_SAMPLES)

# assume that if the canopy height is 0 then there is no O horizon -- this might not always be true
#   probably should be adjusted to account for saturation-induced O accumulation (Histosols)
#   and the model would need appropriate predictors for that. NB: SE Florida
s$o_horizon_comppct[s$canopy_height_m == 0] <- 0

# can compare to canopy at or exceeding the threshold for trees (4 meters)
s$has_tree <- s$canopy_height_m > 4

# assume that MUs with >50% of composition having an O horizon have an O over most of 800m pixel
s$has_o_horizon <- s$o_horizon_comppct >= SSURGO_COMPPCT_THRESHOLD

# ignoring absence of O horizon in SSURGO from training and testing dataset
s2 <- subset(s, complete.cases(s))

# split into training and testing dataset
spl <- caTools::sample.split(s2, SplitRatio = 0.8)
train <- subset(s2, spl)
test <-  subset(s2, !spl)

# inspect inputs

# continuous relation between O horizon comppct and canopy height
plot(train$o_horizon_comppct ~ train$canopy_height_m)

# binary outcome "has tree canopy" as function of O horizon comppct
plot(train$has_tree ~ train$o_horizon_comppct)

# binary outcome "has O horizon" as function of canopy height (target model)
plot(train$has_o_horizon ~ train$canopy_height_m)

# logistic regression
m1 <- glm(has_o_horizon ~ canopy_height_m,
          data = s2,
          family = "binomial")
summary(m1)
saveRDS(m1, "misc/CONUS_O_horizon_by_canopy_height_m.rds")

# evaluate simple threshold 
p1 <- predict(m1, data.frame(canopy_height_m = 0:100)) > 0.5
which(p1)[1] # 19m canopy threshold for O horizon

# try with test data 
p2 <- predict(m1, test) > 0.5

table(test$has_o_horizon, p2) |> 
  prop.table()

missing_classerr <- mean(p2 != test$has_o_horizon, na.rm = TRUE)
print(paste('Accuracy =', 1 - missing_classerr))

# ROC-AUC Curve
ROCPred <- prediction(predict(m1, test), test$has_o_horizon)
ROCPer <- performance(ROCPred, measure = "tpr", x.measure = "fpr")
auc <- performance(ROCPred, measure = "auc")
auc <- auc@y.values[[1]]
auc

# Plotting curve
plot(ROCPer, colorize = TRUE,
     print.cutoffs.at = seq(0.1, by = 0.25),
     main = "ROC CURVE")
abline(a = 0, b = 1)
auc <- round(auc, 4)
legend(.6, .4, auc, title = "AUC", cex = 1)

o_horizon_modeled <- predict(h, m1) > 0.5
o_horizon_ssurgo <- o > SSURGO_COMPPCT_THRESHOLD
o_horizon_ssurgo[is.na(o)] <- 0

plot(o_horizon_ssurgo) # SSURGO O horizons (many holes)
plot(floor(h) >= 19) # canopy height threshold

plot(o_horizon_modeled) # logistic regression model
plot(o_horizon_modeled & !o_horizon_ssurgo, main = "O horizons missing from ssurgo") # areas without O from SSURGO that should have O
plot(!o_horizon_modeled & o_horizon_ssurgo, main = "O horizons missing from model") # areas without O from SSURGO that should have O
plot(o_horizon_modeled - (h >= 19)) # logistic regression v.s. threshold

ssa <- soilDB::fetchSDA_spatial("CA630", 
                                by.col = "areasymbol", 
                                geom = "SAPOLYGON") |> 
  vect() |> 
  project(o_horizon_modeled)

par(mfrow = c(1, 2))
plot(setNames(crop(o_horizon_modeled, ssa), 
              "o_horizon_modeled"), 
     main = "o_horizon_modeled")
plot(ssa, add = TRUE)
plot(crop(o_horizon_ssurgo, ssa), 
     main = "o_horizon_ssurgo")
plot(ssa, add = TRUE)
par(mfrow = c(1, 1))
