library(soilDB)
library(terra)
library(jNSMR)
## SSURGO O horizon map

q <- "SELECT
    mapunit.mukey, SUM(comppct_r) AS pct
    FROM legend
    INNER JOIN mapunit ON mapunit.lkey = legend.lkey
    INNER JOIN component ON component.mukey = mapunit.mukey
    INNER JOIN (
      SELECT DISTINCT component.cokey
      FROM 
      component INNER JOIN chorizon ON component.cokey = chorizon.cokey
      WHERE hzname LIKE 'O%'
    ) AS hz ON component.cokey = hz.cokey
    WHERE legend.areasymbol != 'US' 
    GROUP BY mapunit.mukey ;"

x <- SDA_query(q)

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
                     filename = 'O-horizon-pct_testBYTE.tif', 
                     overwrite = TRUE, 
                     wopt = list(datatype = "INT1U")))

## slower:
# system.time(r2 <- classify(g, as.matrix(x), filename = "O-horizon-pct2.tif", overwrite = TRUE))
# 
# g2 <- g
# levels(g2) <- x
# system.time(r3 <- catalyze(g2, filename = 'O-horizon-pct3.tif', overwrite = TRUE))
            
# project/scale to match PRISM 800m grid
rt <- newhall_prism_rast()
system.time(a <- project(r, rt,filename="O-horizon-pct-prism800m.tif"))
