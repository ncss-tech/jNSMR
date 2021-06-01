#' Test using BASICSimulationModel class from newhall-1.6.1.jar
#' @param dataset a _NewhallDataset_ `jobjRef`
#' @param bsm a _BASICSimulationModel_ `jobjRef`; default: `BASICSimulationModel()`
#' @return _NewhallResult_ `jobjRef`
#' @export
#' @importFrom rJava .jcall
run_simulation <- function(dataset,
                           waterHoldingCapacity = 200,
                           fc = 2.5,
                           fcd = 0.66,
                           bsm = BASICSimulationModel(),
                           toString = FALSE) {

    res <- rJava::.jcall(bsm,
                         returnSig = "Lorg/psu/newhall/sim/NewhallResults;",
                         method =  "runSimulation",
                         dataset,
                         waterHoldingCapacity,
                         fc,
                         fcd)
    if (toString)
      return(rJava::.jcall(res, returnSig = "S", method = "toString"))
    res
}

#' Create an instance of BASICSimulationModel
#'
#' @return an instance of BASICSimulationModel class
#' @export
#' @importFrom rJava .jnew
BASICSimulationModel <- function() {
  rJava::.jnew("org/psu/newhall/sim/BASICSimulationModel")
}
