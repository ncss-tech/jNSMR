% Generated by roxygen2: do not edit by hand
% Please edit documentation in R/jNSMR-package.R
\docType{package}
\name{jNSMR-package}
\alias{jNSMR}
\alias{jNSMR-package}
\title{jNSMR: Interface to the 'Java Newhall Simulation Model' (jNSM) "A Traditional Soil Climate Simulation Model"}
\description{
Provides methods to create input, read output, and run the routines from the legacy Java Newhall Simulation Model (jNSM) for soil climate. Currently this package uses a modified version of the jNSM v1.6.1 which is available for download here: \url{https://www.nrcs.usda.gov/wps/portal/nrcs/detail/?cid=nrcs142p2_053559} and the source code found here \url{https://github.com/drww/newhall/}. The system requirements of the extraction and installation tools (Windows .EXE archive) at the official download link may not be met on your system but the core Java class files are stored in a platform-independent format (a Java JAR file; e.g. newhall-1.6.1.jar) which is a core dependency in this package. Several more recent modifications to the Newhall JAR file allow for higher throughput and more efficient batching of many simulations allowing for larger-than-memory raster-based inputs and outputs.
}
\section{Package Options}{


\code{jNSMR.JAR_SUFFIX} - can be used to select alternate JAR file from \code{inst/java} to add to Java classpath. It is assumed that the JAR file starts with the prefix \code{"newhall"} and has file name \code{".jar"}
}

\seealso{
Useful links:
\itemize{
  \item \url{https://ncss-tech.github.io/jNSMR}
  \item \url{https://github.com/ncss-tech/jNSMR}
}

}
\keyword{internal}
