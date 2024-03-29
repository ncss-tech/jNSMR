% Generated by roxygen2: do not edit by hand
% Please edit documentation in R/NewhallDatasetMetadata.R
\name{NewhallDatasetMetadata}
\alias{NewhallDatasetMetadata}
\title{Create an instance of \emph{NewhallDatasetMetadata}}
\usage{
NewhallDatasetMetadata(
  stationName,
  stationId = character(length(stationName)),
  elev = numeric(length(stationName)),
  stationStateProvidence = character(length(stationName)),
  stationCountry = character(length(stationName)),
  mlraName = character(length(stationName)),
  mlraId = numeric(length(stationName)),
  contribFirstName = character(length(stationName)),
  contribLastName = character(length(stationName)),
  contribTitle = character(length(stationName)),
  contribOrg = character(length(stationName)),
  contribAddress = character(length(stationName)),
  contribCity = character(length(stationName)),
  contribStateProvidence = character(length(stationName)),
  contribPostal = character(length(stationName)),
  contribCountry = character(length(stationName)),
  contribEmail = character(length(stationName)),
  contribPhone = character(length(stationName)),
  notes = numeric(length(stationName)),
  runDate = rep(Sys.Date(), length(stationName)),
  modelVersion = rep(newhall_version(), length(stationName)),
  unitSystem = rep("metric", length(stationName)),
  soilAirOffset = rep(1.2, length(stationName)),
  amplitude = rep(0.66, length(stationName)),
  network = character(length(stationName))
)
}
\arguments{
\item{stationName}{\emph{character}; station name}

\item{stationId}{\emph{character}; station ID}

\item{elev}{\emph{double}; station elevation}

\item{stationStateProvidence}{\emph{character}; station state / providence}

\item{stationCountry}{\emph{character}; station country}

\item{mlraName}{\emph{character}; Major Land Resource Area (MLRA) name}

\item{mlraId}{\emph{integer}; Major Land Resource Area ID}

\item{contribFirstName}{\emph{character}; contributor first name}

\item{contribLastName}{\emph{character}; contributor last name}

\item{contribTitle}{\emph{character}; contributor title}

\item{contribOrg}{\emph{character}; contributor organization}

\item{contribAddress}{\emph{character}; contributor address}

\item{contribCity}{\emph{character}; contributor city}

\item{contribStateProvidence}{\emph{character}; contributor state / providence}

\item{contribPostal}{\emph{character}; contributor postal code}

\item{contribCountry}{\emph{character}; contributor country}

\item{contribEmail}{\emph{character}; contributor email}

\item{contribPhone}{\emph{character}; contributor phone}

\item{notes}{\emph{character} (may have length >1); notes}

\item{runDate}{\emph{character}; run date}

\item{modelVersion}{\emph{character}; model version}

\item{unitSystem}{\emph{character}; unit system either "cm" or "in"}

\item{soilAirOffset}{\emph{double}; soil-air temperature offset}

\item{amplitude}{\emph{double}; soil-air temperature amplitude}

\item{network}{\emph{character}; network}
}
\value{
an instance of \emph{NewhallDatasetMetadata}
}
\description{
Create an instance of \emph{NewhallDatasetMetadata}
}
