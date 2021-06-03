# NewhallDatasetMetadata


#' Create an instance of _NewhallDatasetMetadata_
#'
#' @param stationName _character_; station name
#' @param stationId _character_; station ID
#' @param stationElevation _double_; station elevation
#' @param stationStateProvidence _character_; station state / providence
#' @param stationCountry _character_; station country
#' @param mlraName _character_; Major Land Resource Area (MLRA) name
#' @param mlraId _integer_; Major Land Resource Area ID
#' @param contribFirstName _character_; contributor first name
#' @param contribLastName _character_; contributor last name
#' @param contribTitle _character_; contributor title
#' @param contribOrg _character_; contributor organization
#' @param contribAddress _character_; contributor address
#' @param contribCity _character_; contributor city
#' @param contribStateProvidence _character_; contributor state / providence
#' @param contribPostal _character_; contributor postal code
#' @param contribCountry _character_; contributor country
#' @param contribEmail _character_; contributor email
#' @param contribPhone _character_; contributor phone
#' @param notes _character_ (may have length >1); notes
#' @param runDate _character_; run date
#' @param modelVersion _character_; model version
#' @param unitSystem _character_; unit system either "cm" or "in"
#' @param soilAirOffset _double_; soil-air temperature offset
#' @param amplitude _double_; soil-air temperature amplitude
#' @param network _character_; network
#'
#' @return an instance of _NewhallDatasetMetadata_
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
