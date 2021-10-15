#' Run Newhall _BASICSimulationModel_ simulation
#'
#' @param dataset a _NewhallDataset_ `jobjRef`
#' @param smcsawc Default: `200`
#' @param soilAirOffset air-soil temperature offset. Conventionally for jNSM: `2.5` for metric units (default); `4.5` for english units.
#' @param amplitude Default: `0.66` amplitude difference between soil and air temperature sine waves
#' @param bsm `jobjRef` for _BASICSimulationModel_; Default: `BASICSimulationModel()`
#' @param toString logical; return _NewhallResults_ (Default: `FALSE`), or if `TRUE` call `result.toString()` to get formatted standard output as _character_?
#'
#' @return _NewhallResults_ `jobjRef`
#' @export
#' @importFrom rJava .jcall
newhall_simulation <- function(
                            dataset, # NewhallDataset
                            smcsawc = 200.0, # double
                            soilAirOffset = 2.5, # double
                            amplitude = 0.66, # double
                            bsm = BASICSimulationModel(), # BASICSimulationModel
                            toString = FALSE
                           ) {

    res <- try(rJava::.jcall(bsm, # BASICSimulationModel
                             returnSig = "Lorg/psu/newhall/sim/NewhallResults;",
                             method =  "runSimulation",
                             dataset, # NewhallDataset
                             smcsawc, # double
                             soilAirOffset, # double
                             amplitude # double
                            ))

    if (inherits(res, 'try-error')) stop(res, call. = FALSE)

    if (toString) {
      return(rJava::.jcall(res, returnSig = "S", method = "toString"))
    }

    res
}

#' Create an instance of _BASICSimulationModel_
#'
#' @return an instance of _BASICSimulationModel_ class
#' @export
#' @importFrom rJava .jnew
BASICSimulationModel <- function() {
  rJava::.jnew("org/psu/newhall/sim/BASICSimulationModel")
}
