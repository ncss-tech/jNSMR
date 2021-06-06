# NewhallDatasetMetadata

#' Create an instance of _NewhallDatasetMetadata_
#'
#' @param stationName _character_; station name
#' @param stationId _character_; station ID
#' @param elev _double_; station elevation
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
   stationName, # String only required argument
   stationId = character(length(stationName)), #String
   elev = numeric(length(stationName)), #double
   stationStateProvidence = character(length(stationName)), #String
   stationCountry = character(length(stationName)), #String
   mlraName = character(length(stationName)), #String
   mlraId = numeric(length(stationName)), #int
   contribFirstName = character(length(stationName)), #String
   contribLastName = character(length(stationName)), #String
   contribTitle = character(length(stationName)), #String
   contribOrg = character(length(stationName)), #String
   contribAddress = character(length(stationName)), #String
   contribCity = character(length(stationName)), #String
   contribStateProvidence = character(length(stationName)), #String
   contribPostal = character(length(stationName)), #String
   contribCountry = character(length(stationName)), #String
   contribEmail = character(length(stationName)), #String
   contribPhone = character(length(stationName)), #String
   notes = numeric(length(stationName)), # String (may have length >1)
   runDate = rep(Sys.Date(), length(stationName)), #String
   modelVersion = rep(newhall_version(), length(stationName)), #String
   unitSystem = rep("metric", length(stationName)), #String
   soilAirOffset = rep(1.2, length(stationName)), #double
   amplitude = rep(0.66, length(stationName)), #double
   network = character(length(stationName)) #String
 ) {
   rJava::.jnew(
     "org/psu/newhall/sim/NewhallDatasetMetadata",
     as.character(stationName), #String
     as.character(stationId), #String
     as.double(elev), #double
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
