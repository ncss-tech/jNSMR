# NewhallDatasetMetadata


#' Create an instance of NewhallDatasetMetadata
#'
#' @param stationName character; station name
#' @param stationId character; station ID
#' @param stationElevation double; station elevation
#' @param stationStateProvidence character; station state / providence
#' @param stationCountry character; station country
#' @param mlraName character; Major Land Resource Area (MLRA) name
#' @param mlraId integer; Major Land Resource Area ID
#' @param contribFirstName character; contributor first name
#' @param contribLastName character; contributor last name
#' @param contribTitle character; contributor title
#' @param contribOrg character; contributor organization
#' @param contribAddress character; contributor address
#' @param contribCity character; contributor city
#' @param contribStateProvidence character; contributor state / providence
#' @param contribPostal character; contributor postal code
#' @param contribCountry character; contributor country
#' @param contribEmail character; contributor email
#' @param contribPhone character; contributor phone
#' @param notes character (may have length >1); notes
#' @param runDate character; run date
#' @param modelVersion character; model version
#' @param unitSystem character; unit system either "cm" or "in"
#' @param soilAirOffset double; soil-air temperature offset
#' @param amplitude double; soil-air temperature amplitude
#' @param network character; network
#'
#' @return an instance of NewhallDatasetMetadata
#' @export
#'
NewhallDatasetMetadata <- function(
   stationName, #String
   stationId, #String
   stationElevation, #double
   stationStateProvidence, #String
   stationCountry, #String
   mlraName, #String
   mlraId, #int
   contribFirstName, #String
   contribLastName, #String
   contribTitle, #String
   contribOrg, #String
   contribAddress, #String
   contribCity, #String
   contribStateProvidence, #String
   contribPostal, #String
   contribCountry, #String
   contribEmail, #String
   contribPhone, #String
   notes, #List<String>
   runDate, #String
   modelVersion, #String
   unitSystem, #String
   soilAirOffset, #double
   amplitude, #double
   network #String
 ) {
   rJava::.jnew(
     "org/psu/newhall/sim/NewhallDatasetMetadata",
     as.character(stationName), #String
     as.character(stationId), #String
     as.double(stationElevation), #double
     as.character(stationStateProvidence), #String
     as.character(stationCountry), #String
     as.character(mlraName), #String
     as.integer(mlraId), #int
     as.character(contribFirstName), #String
     as.character(contribLastName), #String
     as.character(contribTitle), #String
     as.character(contribOrg), #String
     as.character(contribAddress), #String
     as.character(contribCity), #String
     as.character(contribStateProvidence), #String
     as.character(contribPostal), #String
     as.character(contribCountry), #String
     as.character(contribEmail), #String
     as.character(contribPhone), #String
     rJava::.jcast(
       .rvec2jarraylist(as.character(notes), "java/lang/String"),
       "java/util/List"
     ), #List<String>
     as.character(runDate), #String
     as.character(modelVersion), #String
     as.character(unitSystem), #String
     as.double(soilAirOffset), #double
     as.double(amplitude), #double
     as.character(network) #String
   )
 }

# public org.psu.newhall.sim.NewhallDatasetMetadata(java.lang.String, java.lang.String, double, java.lang.String, java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.List<java.lang.String>, java.lang.String, java.lang.String, java.lang.String, double, double, java.lang.String);
 # descriptor: (Ljava/lang/String;Ljava/lang/String;DLjava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;DDLjava/lang/String;)V
