/*     */ package org.psu.newhall.sim;
/*     */
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.ArrayList;
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ public class NewhallDatasetMetadata
/*     */ {
/*     */   String stationName;
/*     */   String stationId;
/*     */   double stationElevation;
/*     */   String stationStateProvidence;
/*     */   String stationCountry;
/*     */   String mlraName;
/*     */   int mlraId;
/*     */   String contribFirstName;
/*     */   String contribLastName;
/*     */   String contribTitle;
/*     */   String contribOrg;
/*     */   String contribAddress;
/*     */   String contribCity;
/*     */   String contribStateProvidence;
/*     */   String contribPostal;
/*     */   String contribCountry;
/*     */   String contribEmail;
/*     */   String contribPhone;
/*     */   List<String> notes;
/*     */   String runDate;
/*     */   String modelVersion;
/*     */   String unitSystem;
/*     */   double soilAirOffset;
/*     */   double amplitude;
/*     */   String network;
/*     */
/*     */   public NewhallDatasetMetadata(String stationName, String stationId, double stationElevation, String stationStateProvidence, String stationCountry, String mlraName, int mlraId, String contribFirstName, String contribLastName, String contribTitle, String contribOrg, String contribAddress, String contribCity, String contribStateProvidence, String contribPostal, String contribCountry, String contribEmail, String contribPhone, List<String> notes, String runDate, String modelVersion, String unitSystem, double soilAirOffset, double amplitude, String network) {
/*  40 */     this.stationName = stationName;
/*  41 */     this.stationId = stationId;
/*  42 */     this.stationElevation = stationElevation;
/*  43 */     this.stationStateProvidence = stationStateProvidence;
/*  44 */     this.stationCountry = stationCountry;
/*  45 */     this.mlraName = mlraName;
/*  46 */     this.mlraId = mlraId;
/*  47 */     this.contribFirstName = contribFirstName;
/*  48 */     this.contribLastName = contribLastName;
/*  49 */     this.contribTitle = contribTitle;
/*  50 */     this.contribOrg = contribOrg;
/*  51 */     this.contribAddress = contribAddress;
/*  52 */     this.contribCity = contribCity;
/*  53 */     this.contribStateProvidence = contribStateProvidence;
/*  54 */     this.contribPostal = contribPostal;
/*  55 */     this.contribCountry = contribCountry;
/*  56 */     this.contribEmail = contribEmail;
/*  57 */     this.contribPhone = contribPhone;
/*  58 */     this.notes = notes;
/*  59 */     this.runDate = runDate;
/*  60 */     this.modelVersion = modelVersion;
/*  61 */     this.unitSystem = unitSystem;
/*  62 */     this.soilAirOffset = soilAirOffset;
/*  63 */     this.amplitude = amplitude;
/*  64 */     this.network = network;
/*     */   }
/*     */
/*     */   public NewhallDatasetMetadata() {
/*  68 */     this.stationName = "";
/*  69 */     this.stationId = "";
/*  70 */     this.stationElevation = 0.0D;
/*  71 */     this.stationStateProvidence = "";
/*  72 */     this.stationCountry = "";
/*  73 */     this.mlraName = "";
/*  74 */     this.mlraId = 0;
/*  75 */     this.contribFirstName = "";
/*  76 */     this.contribLastName = "";
/*  77 */     this.contribTitle = "";
/*  78 */     this.contribOrg = "";
/*  79 */     this.contribAddress = "";
/*  80 */     this.contribCity = "";
/*  81 */     this.contribStateProvidence = "";
/*  82 */     this.contribPostal = "";
/*  83 */     this.contribCountry = "";
/*  84 */     this.contribEmail = "";
/*  85 */     this.contribPhone = "";
/*  86 */     this.notes = new ArrayList<String>();
/*  87 */     this.runDate = "";
/*  88 */     this.modelVersion = "";
/*  89 */     this.unitSystem = "";
/*  90 */     this.soilAirOffset = 2.5D;
/*  91 */     this.amplitude = 0.66D;
/*  92 */     this.network = "Unknown";
/*     */   }
/*     */
/*     */   public String getContribAddress() {
/*  96 */     return this.contribAddress;
/*     */   }
/*     */
/*     */   public String getContribCity() {
/* 100 */     return this.contribCity;
/*     */   }
/*     */
/*     */   public String getContribCountry() {
/* 104 */     return this.contribCountry;
/*     */   }
/*     */
/*     */   public String getContribEmail() {
/* 108 */     return this.contribEmail;
/*     */   }
/*     */
/*     */   public String getContribFirstName() {
/* 112 */     return this.contribFirstName;
/*     */   }
/*     */
/*     */   public String getContribLastName() {
/* 116 */     return this.contribLastName;
/*     */   }
/*     */
/*     */   public String getContribOrg() {
/* 120 */     return this.contribOrg;
/*     */   }
/*     */
/*     */   public String getContribPhone() {
/* 124 */     return this.contribPhone;
/*     */   }
/*     */
/*     */   public String getContribPostal() {
/* 128 */     return this.contribPostal;
/*     */   }
/*     */
/*     */   public String getContribStateProvidence() {
/* 132 */     return this.contribStateProvidence;
/*     */   }
/*     */
/*     */   public String getContribTitle() {
/* 136 */     return this.contribTitle;
/*     */   }
/*     */
/*     */   public int getMlraId() {
/* 140 */     return this.mlraId;
/*     */   }
/*     */
/*     */   public String getMlraName() {
/* 144 */     return this.mlraName;
/*     */   }
/*     */
/*     */   public String getModelVersion() {
/* 148 */     return this.modelVersion;
/*     */   }
/*     */
/*     */   public List<String> getNotes() {
/* 152 */     return this.notes;
/*     */   }
/*     */
/*     */   public String getRunDate() {
/* 156 */     return this.runDate;
/*     */   }
/*     */
/*     */   public String getStationCountry() {
/* 160 */     return this.stationCountry;
/*     */   }
/*     */
/*     */   public double getStationElevation() {
/* 164 */     return this.stationElevation;
/*     */   }
/*     */
/*     */   public String getStationId() {
/* 168 */     return this.stationId;
/*     */   }
/*     */
/*     */   public String getStationName() {
/* 172 */     return this.stationName;
/*     */   }
/*     */
/*     */   public String getStationStateProvidence() {
/* 176 */     return this.stationStateProvidence;
/*     */   }
/*     */
/*     */   public String getUnitSystem() {
/* 180 */     return this.unitSystem;
/*     */   }
/*     */
/*     */   public void setContribAddress(String contribAddress) {
/* 184 */     this.contribAddress = contribAddress;
/*     */   }
/*     */
/*     */   public void setContribCity(String contribCity) {
/* 188 */     this.contribCity = contribCity;
/*     */   }
/*     */
/*     */   public void setContribCountry(String contribCountry) {
/* 192 */     this.contribCountry = contribCountry;
/*     */   }
/*     */
/*     */   public void setContribEmail(String contribEmail) {
/* 196 */     this.contribEmail = contribEmail;
/*     */   }
/*     */
/*     */   public void setContribFirstName(String contribFirstName) {
/* 200 */     this.contribFirstName = contribFirstName;
/*     */   }
/*     */
/*     */   public void setContribLastName(String contribLastName) {
/* 204 */     this.contribLastName = contribLastName;
/*     */   }
/*     */
/*     */   public void setContribOrg(String contribOrg) {
/* 208 */     this.contribOrg = contribOrg;
/*     */   }
/*     */
/*     */   public void setContribPhone(String contribPhone) {
/* 212 */     this.contribPhone = contribPhone;
/*     */   }
/*     */
/*     */   public void setContribPostal(String contribPostal) {
/* 216 */     this.contribPostal = contribPostal;
/*     */   }
/*     */
/*     */   public void setContribStateProvidence(String contribStateProvidence) {
/* 220 */     this.contribStateProvidence = contribStateProvidence;
/*     */   }
/*     */
/*     */   public void setContribTitle(String contribTitle) {
/* 224 */     this.contribTitle = contribTitle;
/*     */   }
/*     */
/*     */   public void setMlraId(int mlraId) {
/* 228 */     this.mlraId = mlraId;
/*     */   }
/*     */
/*     */   public void setMlraName(String mlraName) {
/* 232 */     this.mlraName = mlraName;
/*     */   }
/*     */
/*     */   public void setModelVersion(String modelVersion) {
/* 236 */     this.modelVersion = modelVersion;
/*     */   }
/*     */
/*     */   public void setNotes(List<String> notes) {
/* 240 */     this.notes = notes;
/*     */   }
/*     */
/*     */   public void setRunDate(String runDate) {
/* 244 */     this.runDate = runDate;
/*     */   }
/*     */
/*     */   public void setSoilAirOffset(double soilAirOffset) {
/* 248 */     this.soilAirOffset = soilAirOffset;
/*     */   }
/*     */
/*     */   public void setStationCountry(String stationCountry) {
/* 252 */     this.stationCountry = stationCountry;
/*     */   }
/*     */
/*     */   public void setStationElevation(double stationElevation) {
/* 256 */     this.stationElevation = stationElevation;
/*     */   }
/*     */
/*     */   public void setStationId(String stationId) {
/* 260 */     this.stationId = stationId;
/*     */   }
/*     */
/*     */   public void setStationName(String stationName) {
/* 264 */     this.stationName = stationName;
/*     */   }
/*     */
/*     */   public void setStationStateProvidence(String stationStateProvidence) {
/* 268 */     this.stationStateProvidence = stationStateProvidence;
/*     */   }
/*     */
/*     */   public void setUnitSystem(String unitSystem) {
/* 272 */     this.unitSystem = unitSystem;
/*     */   }
/*     */
/*     */   public double getSoilAirOffset() {
/* 276 */     return this.soilAirOffset;
/*     */   }
/*     */
/*     */   public double getAmplitude() {
/* 280 */     return this.amplitude;
/*     */   }
/*     */
/*     */   public void setAmplitude(double amplitude) {
/* 284 */     this.amplitude = amplitude;
/*     */   }
/*     */
/*     */   public String getNetwork() {
/* 288 */     return this.network;
/*     */   }
/*     */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/psu/newhall/sim/NewhallDatasetMetadata.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
