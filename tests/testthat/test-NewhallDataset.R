test_that("csv_NewhallDataset works", {
  oldcsv <- system.file("extdata/mead_agronomy.csv", package = "jNSMR")[1]
  dataset <- csv_NewhallDataset(pathname = oldcsv)

  expect_equal(dataset$getClass()$toString(), "class org.psu.newhall.sim.NewhallDataset")
  expect_silent(res <- newhall_simulation(dataset))

})

test_that("missing or bad input", {

  expect_error(NewhallDataset(stationName = "foo",
                              country = "US",
                              latDD = 37.2,
                              lonDD = -120.1,
                              elev = 100.1,
                              allPrecipsDbl = rep(2.2, 12),
                              allAirTempsDbl = rep(18.1, 12),
                              pdbegin = numeric(0), # length 0, not 1
                              pdend = 1990,
                              smcsawc = 200.0,
                              checkargs = TRUE))

  expect_error(NewhallDataset(stationName = "foo",
                              country = "US",
                              latDD = 37.2,
                              lonDD = -120.1,
                              elev = 100.1,
                              allPrecipsDbl = rep(2.2, 12),
                              allAirTempsDbl = rep(18.1, 2), # length 2, not 12
                              pdbegin = 1990,
                              pdend = 1990,
                              smcsawc = 200.0,
                              checkargs = TRUE))

  expect_error(NewhallDataset(stationName = "foo",
                              country = "US",
                              latDD = 37.2,
                              lonDD = -120.1,
                              elev = 100.1,
                              allPrecipsDbl = rep(2.2, 12),
                              allAirTempsDbl = rep(18.1, 12),
                              pdbegin = "AAAAA", # not coercible to numeric
                              pdend = 1990,
                              smcsawc = 200.0,
                              checkargs = TRUE))
})

test_that("XML input/output works (v1.6.1 only)", {

  skip_if_not(newhall_version() == "1.6.1")
  cat("\n\n")
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
  expect_silent(newhall_XMLResultsExporter(dataset = input,
                                           results = output,
                                           pathname = tempfile()))
  cat("\n")

})
