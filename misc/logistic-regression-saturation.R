# compare canopy height with O horizon presence/absence
library(terra)
library(caTools)
library(ROCR)

SSURGO_COMPPCT_THRESHOLD <- 50
N_EXHAUSTIVE_SAMPLES <- 1e5

h <- rast("dem_conus_800m_wwi.tif")
names(h) <- "wwi"

o <- rast("saturation-pct-prism800m.tif")
names(o) <- "saturation_comppct"

s <- spatSample(c(h, o), N_EXHAUSTIVE_SAMPLES)

# assume that if the canopy height is 0 then there is no O horizon -- this might not always be true
#   probably should be adjusted to account for saturation-induced O accumulation (Histosols)
#   and the model would need appropriate predictors for that. NB: SE Florida
s$saturation_comppct[s$wwi == 0] <- 0

# assume that MUs with >50% of composition having an O horizon have an O over most of 800m pixel
s$is_saturated <- s$saturation_comppct >= SSURGO_COMPPCT_THRESHOLD

# ignoring absence of O horizon in SSURGO from training and testing dataset
s2 <- subset(s, complete.cases(s))

# split into training and testing dataset
spl <- caTools::sample.split(s2, SplitRatio = 0.8)
train <- subset(s2, spl)
test <-  subset(s2, !spl)

# inspect inputs

# continuous relation between O horizon comppct and canopy height
plot(train$saturation_comppct ~ train$wwi)

# binary outcome "has O horizon" as function of canopy height (target model)
plot(train$is_saturated ~ train$wwi)

# logistic regression
m1 <- glm(is_saturated ~ wwi,
          data = s2,
          family = "binomial")
summary(m1)
saveRDS(m1, "misc/CONUS_saturation_by_wwi.rds")

# evaluate simple threshold 
p1 <- predict(m1, data.frame(wwi = (-10):120)) > 0.5
which(p1)[1] # wwi >= 17 canopy threshold for saturation

# try with test data 
p2 <- predict(m1, test) > 0.5

table(test$is_saturated, p2) |> 
  prop.table()

missing_classerr <- mean(p2 != test$is_saturated, na.rm = TRUE)
print(paste('Accuracy =', 1 - missing_classerr))

# ROC-AUC Curve
ROCPred <- prediction(predict(m1, test), test$is_saturated)
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

saturation_modeled <- predict(h, m1) > 0.5
saturation_ssurgo <- o > SSURGO_COMPPCT_THRESHOLD
saturation_ssurgo[is.na(o)] <- 0

plot(saturation_ssurgo) # SSURGO O horizons (many holes)
plot(floor(h) >= 17) # canopy height threshold

plot(saturation_modeled) # logistic regression model
plot(saturation_modeled & !saturation_ssurgo, main = "saturation missing from ssurgo") # areas without O from SSURGO that should have O
plot(!saturation_modeled & saturation_ssurgo, main = "saturation missing from model") # areas without O from SSURGO that should have O
plot(saturation_modeled) # logistic regression v.s. threshold

ssa <- soilDB::fetchSDA_spatial("CA630", 
                                by.col = "areasymbol", 
                                geom = "SAPOLYGON") |> 
  vect() |> 
  project(saturation_modeled)

par(mfrow = c(1, 2))
plot(setNames(crop(saturation_modeled, ssa), 
              "saturation_modeled"), 
     main = "saturation_modeled")
plot(ssa, add = TRUE)
plot(crop(saturation_ssurgo, ssa), 
     main = "saturation_ssurgo")
plot(ssa, add = TRUE)
par(mfrow = c(1, 1))
