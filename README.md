# jNSMR

R wrapper for Java Newhall Simulation Model (jNSM) -- "A Traditional Soil Climate Simulation Model"

Provides methods to create input, read output, and run the routines from the legacy Java Newhall Simulation Model (jNSM). 

## Install {jNSMR}

First, install the development version of the R package. This will install the latest version of the Java Newhall model JAR (in the {jNSMR} folder of your package library). 

```r
# install.packages('remotes')
remotes::install_github('brownag/jNSMR')
```

## Basic functionality 

### Run the BASIC simulation model

Create a _NewhallDataset_ with `xml_NewhallDataset()` or `NewhallDataset()` then run a single simulation with `newhall_simulation()`

```r
library(jNSMR)

# read single-station XML file
input_xml <- xml_NewhallDataset(system.file("extdata/WILLIAMSPORT_1930_1930_input.xml", package = "jNSMR")[1])

# or specify inputs directly to the constructor
input_direct <- NewhallDataset(stationName = "WILLIAMSPORT",
                               country = "US",
                               lat = 41.24,
                               lon = -76.92,
                               nsHemisphere = 'N',
                               ewHemisphere = 'W',
                               stationElevation = 158.0,
                               allPrecipsDbl = c(44.2,40.39,113.54,96.77,95.0,98.55,66.04,13.46,54.86,6.35,17.53,56.39),
                               allAirTempsDbl = c(-2.17,0.89,3.72,9.11,16.28,21.11,22.83,21.94,19.78,10.5,5.33,-1.06),
                               pdbegin = 1930,
                               pdend = 1930,
                               smcsawc = 200.0)

# run single model from XML file
output_xml <- newhall_simulation(input_xml)

# run single model from direct input
output <- newhall_simulation(input_direct)
```

### Run batch sets of models

The jNSM has a defined CSV (comma-separated value) batch file format. Examples of that setup are included in the official download. The batching in the {jNSMR} package is handled by R code, not Java. The main interface to batching is `newhall_batch()`. 

`newhall_batch()` takes either a `pathname` argument, or you can specify `.data` as a _data.frame_ directly.

Selected example input files have been included in `inst/extdata` directory of this package.

```r
pathname <- system.file("extdata/All_PA_jNSM_Example_Batch_Metric.csv", package = "jNSMR")[1]

res <- newhall_batch(pathname = pathname)

head(res)
```

### Support for Newhall XML format for input and output

```r
# inspect XML result format
cat(newhall_XMLStringResultsExporter(input_xml, output))
#> <?xml version="1.0" encoding="UTF-8"?>
#> <model>
#>   <metadata>
#>     <stninfo>
#>       <nettype>HCN</nettype>
#>       <stnname>WILLIAMSPORT</stnname>
#>       <stnid />
#>       <stnelev>158.0</stnelev>
#>       <stateprov>PA</stateprov>
#>       <country>US</country>
#>     </stninfo>
#>     <mlra>
#>       <mlraname />
#>       <mlraid>0</mlraid>
#>     </mlra>
#>     <cntinfo>
#>       <cntper>
#>         <firstname>FirstName</firstname>
#>         <lastname>LastName</lastname>
#>         <title>Researcher</title>
#>       </cntper>
#>       <cntorg>My Organization</cntorg>
#>       <cntaddr>
#>         <address />
#>         <city />
#>         <stateprov />
#>         <postal />
#>         <country />
#>       </cntaddr>
#>       <cntemail />
#>       <cntphone />
#>     </cntinfo>
#>     <notes>
#>       <note>gaps filled by interpolation of neighboring stations</note>
#>     </notes>
#>     <rundate>20210601</rundate>
#>     <nsmver>1.6.1</nsmver>
#>     <srcunitsys>english</srcunitsys>
#>   </metadata>
#>   <input>
#>     <location>
#>       <lat>41.24</lat>
#>       <lon>-76.92</lon>
#>       <usercoordfmt>DD</usercoordfmt>
#>     </location>
#>     <recordpd>
#>       <pdtype>normal</pdtype>
#>       <pdbegin>1930</pdbegin>
#>       <pdend>1930</pdend>
#>     </recordpd>
#>     <precips>
#>       <precip id="Jan">44.2</precip>
#>       <precip id="Feb">40.39</precip>
#>       <precip id="Mar">113.54</precip>
#>       <precip id="Apr">96.77</precip>
#>       <precip id="May">95.0</precip>
#>       <precip id="Jun">98.55</precip>
#>       <precip id="Jul">66.04</precip>
#>       <precip id="Aug">13.46</precip>
#>       <precip id="Sep">54.86</precip>
#>       <precip id="Oct">6.35</precip>
#>       <precip id="Nov">17.53</precip>
#>       <precip id="Dec">56.39</precip>
#>     </precips>
#>     <airtemps>
#>       <airtemp id="Jan">-2.17</airtemp>
#>       <airtemp id="Feb">0.89</airtemp>
#>       <airtemp id="Mar">3.72</airtemp>
#>       <airtemp id="Apr">9.11</airtemp>
#>       <airtemp id="May">16.28</airtemp>
#>       <airtemp id="Jun">21.11</airtemp>
#>       <airtemp id="Jul">22.83</airtemp>
#>       <airtemp id="Aug">21.94</airtemp>
#>       <airtemp id="Sep">19.78</airtemp>
#>       <airtemp id="Oct">10.5</airtemp>
#>       <airtemp id="Nov">5.33</airtemp>
#>       <airtemp id="Dec">-1.06</airtemp>
#>     </airtemps>
#>     <smcsawc>200.0</smcsawc>
#>     <soilairrel>
#>       <ampltd>0.66</ampltd>
#>       <maatmast>1.2</maatmast>
#>     </soilairrel>
#>   </input>
#>   <output>
#>     <smrclass>Ustic</smrclass>
#>     <strclass>Mesic</strclass>
#>     <subgrpmod>Wet Tempustic</subgrpmod>
#>     <awb>13.12</awb>
#>     <swb>-218.22</swb>
#>     <smcstates>
#>       <cumdays>
#>         <yrdry>67</yrdry>
#>         <yrmd>88</yrmd>
#>         <yrmst>205</yrmst>
#>         <bio5dry>48</bio5dry>
#>         <bio5md>58</bio5md>
#>         <bio5mst>118</bio5mst>
#>       </cumdays>
#>       <consdays>
#>         <yrmst>293</yrmst>
#>         <bio8mst>166</bio8mst>
#>         <smrdry>22</smrdry>
#>         <wtrmst>105</wtrmst>
#>       </consdays>
#>     </smcstates>
#>     <pets>
#>       <pet id="Jan">0.0</pet>
#>       <pet id="Feb">1.49</pet>
#>       <pet id="Mar">11.44</pet>
#>       <pet id="Apr">38.55</pet>
#>       <pet id="May">90.93</pet>
#>       <pet id="Jun">127.6</pet>
#>       <pet id="Jul">142.1</pet>
#>       <pet id="Aug">126.57</pet>
#>       <pet id="Sep">96.94</pet>
#>       <pet id="Oct">39.95</pet>
#>       <pet id="Nov">14.39</pet>
#>       <pet id="Dec">0.0</pet>
#>     </pets>
#>     <calendars>
#>       <tempcal>
#>         <stlt5>
#>           <beginday>1</beginday>
#>           <endday>102</endday>
#>         </stlt5>
#>         <st5to8>
#>           <beginday>103</beginday>
#>           <endday>112</endday>
#>         </st5to8>
#>         <stgt8>
#>           <beginday>113</beginday>
#>           <endday>314</endday>
#>         </stgt8>
#>         <st5to8>
#>           <beginday>315</beginday>
#>           <endday>326</endday>
#>         </st5to8>
#>         <stlt5>
#>           <beginday>327</beginday>
#>           <endday>360</endday>
#>         </stlt5>
#>       </tempcal>
#>       <moistcal>
#>         <moistdry>
#>           <beginday>1</beginday>
#>           <endday>15</endday>
#>         </moistdry>
#>         <moist>
#>           <beginday>16</beginday>
#>           <endday>220</endday>
#>         </moist>
#>         <moistdry>
#>           <beginday>221</beginday>
#>           <endday>278</endday>
#>         </moistdry>
#>         <dry>
#>           <beginday>279</beginday>
#>           <endday>345</endday>
#>         </dry>
#>         <moistdry>
#>           <beginday>346</beginday>
#>           <endday>360</endday>
#>         </moistdry>
#>       </moistcal>
#>     </calendars>
#>   </output>
#> </model>
#> 
```

### XML File Output

```r
# write XML results to file
newhall_XMLResultsExporter(dataset = input_direct, 
                           result = output, 
                           pathname = "misc/WILLIAMSPORT_1930_1930_export.xml")
```

### Graphical User Interface for individual runs of _newhall-1.6.1.jar_

```r
# convenience method to open GUI with the R package

newhall_GUI()
```

## License information

**This package uses version 1.6.1 (released 2016/02/10) of the jNSM (official download here: https://www.nrcs.usda.gov/wps/portal/nrcs/detail/?cid=nrcs142p2_053559)**. The compiled JAR and source code are distributed in this R package under the "New" (3-Clause) BSD License. See _LICENSE_ for more information.

> Newhall 1.6.1, Copyright (C) 2010-2011 
> United States Department of Agriculture - Natural Resources Conservation Service, 
> Penn State University Center for Environmental Informatics
> All rights reserved.

This product includes software developed by the JDOM Project (http://www.jdom.org/).

> JDOM 1.1, Copyright (C) 2000-2004 Jason Hunter & Brett McLaughlin
> All rights reserved.

## System requirements

The system requirements of the extraction and installation tools (Windows .EXE archive) at the above link may not be met on your system but the core Java class files are stored in a platform-independent format (in _newhall-1.6.1.jar_) which is a core dependency in this package. Therefore, as long as you have a modern Java Runtime Environment (you probably do), you will be able to run jNSM via this package with only minimal setup.

### {rJava}

### Set up `PATH` to contain `java` (for `newhall_GUI()`)
