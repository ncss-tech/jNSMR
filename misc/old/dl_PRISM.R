# download prism

# download prism monthly + annual 30yr normals

library(prism)
library(raster)

# download 4km or 800m data
.download_month <- function(i, resolution = c("4km","800m"), annual = FALSE, keepZip = FALSE) {
  resolution <- match.arg(resolution, c("4km","800m"))
  # print(resolution)
  if(i == 1)
    annual = TRUE
  prism::get_prism_normals(mon = i, type = "ppt",
                           resolution = resolution,
                           annual = annual, keepZip = keepZip)
  prism::get_prism_normals(mon = i, type = "tmean",
                           resolution = resolution,
                           annual = annual, keepZip = keepZip)
  TRUE
}

PRISM_PATH <- "~/Geodata/PRISM/800m"
dir.create(PRISM_PATH, recursive = TRUE)
prism_set_dl_dir(PRISM_PATH)
res <- lapply(1:12, .download_month, resolution="800m")

PRISM_PATH <- "~/Geodata/PRISM/4km"
dir.create(PRISM_PATH, recursive = TRUE)
prism_set_dl_dir(PRISM_PATH)
prism::prism_get_dl_dir()
res <- lapply(1:12, .download_month, resolution="4km")

