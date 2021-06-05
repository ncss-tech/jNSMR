test_that("newhall_simulation() works", {

  cat("\n\njNSMR v", newhall_version(), "\n\n")

  # direct input with NewhallDataset built from R objects

  input_direct <- NewhallDataset(
    stationName = "WILLIAMSPORT",
    country = "US",
    lat = 41.24,
    lon = -76.92,
    stationElevation = 158.0,
    allPrecipsDbl = c(44.2, 40.39, 113.54, 96.77, 95, 98.55,
                      66.04, 13.46, 54.86, 6.35, 17.53, 56.39),
    allAirTempsDbl = c(-2.17, 0.89, 3.72, 9.11, 16.28, 21.11,
                       22.83, 21.94, 19.78, 10.5, 5.33, -1.06),
    pdbegin = 1930,
    pdend = 1930,
    smcsawc = 200.0
  )

  expect_true(inherits(newhall_simulation(dataset = input_direct), 'jobjRef'))

  ndmeta <- NewhallDatasetMetadata(stationName = "foo", #String
      stationId = "bar", #String
      stationElevation = 101.1, #double
      stationStateProvidence = "CA", #String
      stationCountry = "USA", #String
      mlraName = "", #String
      mlraId = 0, #int
      contribFirstName = "Josephine", #String
      contribLastName = "Aiken", #String
      contribTitle = "Soil Scientist", #String
      contribOrg = "USDA-NRCS", #String
      contribAddress = "", #String
      contribCity = "", #String
      contribStateProvidence = "", #String
      contribPostal = "", #String
      contribCountry = "", #String
      contribEmail = "", #String
      contribPhone = "", #String
      notes = c("Note 1", "Note 2", "Note 3"), #List<String>
      runDate = "1970-01-01", #String
      modelVersion = newhall_version(), #String
      unitSystem = "cm", #String
      soilAirOffset = 1.2, #double
      amplitude = 0.66, #double
      network = "foo" #String
  )

  expect_equal(ndmeta$getContribFirstName(), "Josephine")
})


test_that("XML input/output works (v1.6.1 only)", {

  skip_if_not(newhall_version() == "1.6.1")

  tf <- tempfile()
  cat(
    '<model>
          <metadata>
            <stninfo>
              <nettype>HCN</nettype>
              <stnname>WILLIAMSPORT</stnname>
              <stnid/>
              <stnelev>158.0</stnelev>
              <stateprov>PA</stateprov>
              <country>US</country>
            </stninfo>
            <mlra>
              <mlraname/>
              <mlraid>0</mlraid>
            </mlra>
            <cntinfo>
              <cntper>
                <firstname>FirstName</firstname>
                <lastname>LastName</lastname>
                <title>Researcher</title>
              </cntper>
              <cntorg>My Organization</cntorg>
              <cntaddr>
                <address/>
                <city/>
                <stateprov/>
                <postal/>
                <country/>
              </cntaddr>
              <cntemail/>
              <cntphone/>
            </cntinfo>
            <notes>
              <note>gaps filled by interpolation of neighboring stations</note>
            </notes>
            <rundate>20111020</rundate>
            <nsmver>1.5.0</nsmver>
            <srcunitsys>english</srcunitsys>
          </metadata>
          <input>
            <location>
              <lat>41.24</lat>
              <lon>-76.92</lon>
              <usercoordfmt>DD</usercoordfmt>
            </location>
            <recordpd>
              <pdtype>normal</pdtype>
              <pdbegin>1930</pdbegin>
              <pdend>1930</pdend>
            </recordpd>
            <precips>
              <precip id="Jan">44.2</precip>
              <precip id="Feb">40.39</precip>
              <precip id="Mar">113.54</precip>
              <precip id="Apr">96.77</precip>
              <precip id="May">95.0</precip>
              <precip id="Jun">98.55</precip>
              <precip id="Jul">66.04</precip>
              <precip id="Aug">13.46</precip>
              <precip id="Sep">54.86</precip>
              <precip id="Oct">6.35</precip>
              <precip id="Nov">17.53</precip>
              <precip id="Dec">56.39</precip>
            </precips>
            <airtemps>
              <airtemp id="Jan">-2.17</airtemp>
              <airtemp id="Feb">0.89</airtemp>
              <airtemp id="Mar">3.72</airtemp>
              <airtemp id="Apr">9.11</airtemp>
              <airtemp id="May">16.28</airtemp>
              <airtemp id="Jun">21.11</airtemp>
              <airtemp id="Jul">22.83</airtemp>
              <airtemp id="Aug">21.94</airtemp>
              <airtemp id="Sep">19.78</airtemp>
              <airtemp id="Oct">10.5</airtemp>
              <airtemp id="Nov">5.33</airtemp>
              <airtemp id="Dec">-1.06</airtemp>
            </airtemps>
            <smcsawc>200.0</smcsawc>
            <soilairrel>
              <ampltd>0.66</ampltd>
              <maatmast>1.2</maatmast>
            </soilairrel>
          </input>
        </model>
        ',
    file = tf
  )

  # read single-station XML file
  input <- xml_NewhallDataset(pathname = tf)
  expect_true(inherits(input, 'jobjRef'))

  # # run model
  output <- newhall_simulation(dataset = input)
  expect_true(inherits(output, 'jobjRef'))

  # # XML string is a character, using NULL pathname
  expect_true(is.character(
    newhall_XMLStringResultsExporter(dataset = input, results = output)
  ))

  # # write XML results to file
  newhall_XMLResultsExporter(dataset = input,
                             results = output,
                             pathname = tempfile())

})
