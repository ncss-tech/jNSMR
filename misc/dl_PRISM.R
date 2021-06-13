# download prism

# download prism monthly + annual 30yr normals

library(prism)
library(raster)

PRISM_PATH <- "~/Geodata/PRISM"
prism_set_dl_dir(PRISM_PATH)

# download 4km or 800m data
.download_month <- function(i, resolution = c("4km","800m"), annual = FALSE, keepZip = TRUE) {
  resolution <- match.arg(resolution, c("4km","800m"))
  if(i == 1)
    annual = TRUE
  get_prism_normals(mon = i, type = "ppt",
                    resolution = resolution,
                    annual = annual, keepZip = keepZip)
  get_prism_normals(mon = i, type = "tmean",
                    resolution = resolution,
                    annual = annual, keepZip = keepZip)
  TRUE
}
res <- lapply(1:12, .download_month, resolution="4km")
