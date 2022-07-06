<!-- badges: start -->

[![R-CMD-check](https://github.com/ncss-tech/jNSMR/workflows/R-CMD-check/badge.svg)](https://github.com/ncss-tech/jNSMR/actions)
[![Codecov test
coverage](https://codecov.io/gh/ncss-tech/jNSMR/branch/main/graph/badge.svg)](https://codecov.io/gh/ncss-tech/jNSMR?branch=main)
[![html-docs](https://camo.githubusercontent.com/f7ba98e46ecd14313e0e8a05bec3f92ca125b8f36302a5b1679d4a949bccbe31/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f646f63732d48544d4c2d696e666f726d6174696f6e616c)](https://ncss-tech.github.io/jNSMR/)
<!-- badges: end -->

# {jNSMR}

R wrapper for Java Newhall Simulation Model (jNSM) – “A Traditional Soil
Climate Simulation Model”

Provides methods to create input, read output, and run the routines from
the legacy Java Newhall Simulation Model (jNSM).

## Install {jNSMR}

First, install the development version of the R package. This will
install the latest version of the Java Newhall model JAR (in the {jNSMR}
folder of your package library).

    # install.packages('remotes')
    remotes::install_github('ncss-tech/jNSMR')

## Run batches of models

The jNSM has a defined CSV (comma-separated value) batch file format.
Examples of batch inputs are included in the official download. The
batching in the {jNSMR} package is handled by R code, not Java. The main
interface to batching is `newhall_batch()`.

`newhall_batch()` takes either a character vector of CSV batch file
paths, a data.frame, a SpatRaster or RasterStack/Brick object as input.

Selected example input files have been included in `inst/extdata`
directory of this package.

    library(jNSMR)

    ## jNSMR (0.1.0) -- R interface to the classic Newhall Simulation Model
    ## Added JAR file (newhall-1.6.4.jar) to Java class path.

    pathname <- system.file("extdata/All_PA_jNSM_Example_Batch_Metric.csv", package = "jNSMR")[1]

    res <- data.table::data.table(newhall_batch(pathname, toString = FALSE, verbose = FALSE))

    head(res)

    ##    annualRainfall waterHoldingCapacity annualWaterBalance
    ## 1:        1147.33                  200           474.2701
    ## 2:         932.69                  200           270.1988
    ## 3:        1084.32                  200           427.3922
    ## 4:         992.64                  200           332.5753
    ## 5:        1262.39                  200           629.7745
    ## 6:        1118.09                  200           452.0308
    ##    annualPotentialEvapotranspiration summerWaterBalance
    ## 1:                          673.0599          -69.77925
    ## 2:                          662.4912         -112.88055
    ## 3:                          656.9278          -79.13638
    ## 4:                          660.0647          -88.15085
    ## 5:                          632.6155          -15.91051
    ## 6:                          666.0592         -107.59863
    ##    dryDaysAfterSummerSolstice moistDaysAfterWinterSolstice numCumulativeDaysDry
    ## 1:                          0                          120                    0
    ## 2:                          0                          120                    0
    ## 3:                          0                          120                    0
    ## 4:                          0                          120                    0
    ## 5:                          0                          120                    0
    ## 6:                          0                          120                    0
    ##    numCumulativeDaysMoistDry numCumulativeDaysMoist numCumulativeDaysDryOver5C
    ## 1:                         0                    360                          0
    ## 2:                         0                    360                          0
    ## 3:                         0                    360                          0
    ## 4:                         0                    360                          0
    ## 5:                         0                    360                          0
    ## 6:                         0                    360                          0
    ##    numCumulativeDaysMoistDryOver5C numCumulativeDaysMoistOver5C
    ## 1:                               0                          227
    ## 2:                               0                          226
    ## 3:                               0                          224
    ## 4:                               0                          226
    ## 5:                               0                          212
    ## 6:                               0                          223
    ##    numConsecutiveDaysMoistInSomeParts numConsecutiveDaysMoistInSomePartsOver8C
    ## 1:                                360                                      205
    ## 2:                                360                                      206
    ## 3:                                360                                      204
    ## 4:                                360                                      205
    ## 5:                                360                                      193
    ## 6:                                360                                      203
    ##    temperatureRegime moistureRegime regimeSubdivision1 regimeSubdivision2
    ## 1:             Mesic           Udic              Typic               Udic
    ## 2:             Mesic           Udic              Typic               Udic
    ## 3:             Mesic           Udic              Typic               Udic
    ## 4:             Mesic           Udic              Typic               Udic
    ## 5:             Mesic           Udic              Typic               Udic
    ## 6:             Mesic           Udic              Typic               Udic

## License information

**This package uses a modified version of the Newhall model v1.6.1
(released 2016/02/10) of the jNSM (official download here:
<https://www.nrcs.usda.gov/wps/portal/nrcs/detail/?cid=nrcs142p2_053559>)**.
The compiled JAR and source code are distributed in this R package under
the “New” (3-Clause) BSD License. See *LICENSE* for more information.
Modifications to the JAR relative to legacy version are only to
facilitate higher throughput batching and access to additional data
elements.

> Newhall 1.6.1, Copyright (C) 2010-2011 United States Department of
> Agriculture - Natural Resources Conservation Service, Penn State
> University Center for Environmental Informatics All rights reserved.

## System requirements

The system requirements of the extraction and installation tools
(Windows .EXE archive) at the official download link may not be met on
your system, but the core Java class files are stored in a
platform-independent format (a Java JAR file e.g *newhall-1.6.1.jar*)–a
core dependency in this package.

As long as you have a modern Java Runtime Environment (you probably do),
you will be able to run jNSM with only minimal setup.
