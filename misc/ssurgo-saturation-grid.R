library(soilDB)
library(terra)
library(jNSMR)

# ssurgo saturation grid
q <- "SELECT
    mapunit.mukey, SUM(comppct_r) AS pct
    FROM legend
    INNER JOIN mapunit ON mapunit.lkey = legend.lkey
    INNER JOIN component ON component.mukey = mapunit.mukey
    INNER JOIN (
      SELECT DISTINCT component.cokey
      FROM component 
      INNER JOIN comonth ON component.cokey = comonth.cokey
      INNER JOIN cosoilmoist ON comonth.comonthkey = cosoilmoist.comonthkey
      WHERE soimoiststat LIKE 'Wet' AND soimoistdept_r <= 50
    ) AS cm ON component.cokey = cm.cokey
    WHERE legend.areasymbol != 'US' 
    GROUP BY mapunit.mukey"
x <- soilDB:::.SDA_query_FOR_JSON_AUTO(q)

# CONUS gNATSGO 30m grid
g <- rast('D:/Geodata/gNATSGO/gNATSGO_mukey_grid/gNATSGO-mukey.tif')

.f <- function(i) {
  
  .idx <- match(i, x$mukey)
  .res <- x$pct[.idx]
  
  return(.res)
}

system.time(r <- app(g, fun = function(i, ff) ff(i), 
                     cores = 1,
                     ff = .f,
                     filename = 'saturation-pct.tif', 
                     overwrite = TRUE, 
                     wopt = list(datatype = "INT1U")))

# project/scale to match PRISM 800m grid
rt <- newhall_prism_rast()
system.time(a <- project(r, rt, filename="saturation-pct-prism800m.tif"))

