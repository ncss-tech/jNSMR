# jNSMR

R wrapper for Java Newhall Simulation Model (jNSM) -- "A Traditional Soil Climate Simulation Model"

Provides methods to create input, read output, and run the routines from the legacy Java Newhall Simulation Model (jNSM). 

## Install {jNSMR}

First, install the development version of the R package. This will install the latest version of the Java Newhall model JAR (in the {jNSMR} folder of your package library). 

```r
# install.packages('remotes')
remotes::install_github('brownag/jNSMR')
```

## Basic functions

```r
library(jNSMR)

# read single-station XML file
input <- NewhallDatasetFromPath("WILLIAMSPORT_1930_1930_input.xml")

# run model
output <- run_simulation(input)

# inspect results as string
newhall_XML_string_export(input, output)

# write XML results to file
newhall_XML_export("WILLIAMSPORT_1930_1930_export.xml", input, output)

# convenience method to open GUI
openJAR()
```

## License information

**Currently this package uses version 1.6.1 (released 2016/02/10) of the jNSM (official download here: https://www.nrcs.usda.gov/wps/portal/nrcs/detail/?cid=nrcs142p2_053559)**. The compiled JAR and source code are distributed in this R package under the "New" (3-Clause) BSD License. See _LICENSE_ for more information.

> Newhall 1.6.1, Copyright (C) 2010-2011 
> United States Department of Agriculture - Natural Resources Conservation Service, 
> Penn State University Center for Environmental Informatics
> All rights reserved.

This product includes software developed by the JDOM Project (http://www.jdom.org/).

> JDOM 1.1, Copyright (C) 2000-2004 Jason Hunter & Brett McLaughlin
> All rights reserved.

## System requirements

The system requirements of the extraction and installation tools (Windows .EXE archive) at the above link may not be met on your system but the core Java class files are stored in a platform-independent format (in _newhall-1.6.1.jar_) which is a core dependency in this package. Therefore, as long as you have a modern Java Runtime Environment (you probably do), you will be able to run jNSM via this package with only minimal setup.

### Set up `PATH` to contain `java`

### {rJava}
