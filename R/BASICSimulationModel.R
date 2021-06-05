#' Run _BASICSimulationModel_ simulation with newhall-1.6.1.jar
#'
#' @param dataset a _NewhallDataset_ `jobjRef`
#' @param waterHoldingCapacity Default: `200`
#' @param fc Default: `2.5`
#' @param fcd Default: `0.66`
#' @param bsm `jobjRef` for _BASICSimulationModel_; Default: `BASICSimulationModel()`
#' @param toString logical; return _NewhallResults_ (Default: `FALSE`), or if `TRUE` call `result.toString()` to get formatted standard output as _character_?
#'
#' @return _NewhallResults_ `jobjRef`
#' @export
#' @importFrom rJava .jcall
newhall_simulation <- function(
                            dataset, # NewhallDataset
                            waterHoldingCapacity = 200.0, # double
                            fc = 2.5, # double
                            fcd = 0.66, # double
                            bsm = BASICSimulationModel(), # BASICSimulationModel
                            toString = FALSE
                           ) {

    res <- try(rJava::.jcall(bsm, # BASICSimulationModel
                             returnSig = "Lorg/psu/newhall/sim/NewhallResults;",
                             method =  "runSimulation",
                             dataset, # NewhallDataset
                             waterHoldingCapacity, # double
                             fc, # double
                             fcd # double
                            ))

    if (inherits(res, 'try-error')) {
      print(waterHoldingCapacity)
      print(fc)
      print(fcd)
      print(dataset$toString())
      stop(res, call. = FALSE)
    }

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
