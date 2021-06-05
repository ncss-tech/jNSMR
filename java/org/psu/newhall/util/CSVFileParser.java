/*     */ package org.psu.newhall.util;
/*     */
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.psu.newhall.sim.NewhallDataset;
/*     */ import org.psu.newhall.sim.NewhallDatasetMetadata;
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ public class CSVFileParser
/*     */ {
/*     */   NewhallDataset dataset;
/*     */
/*     */   public CSVFileParser(File inputFile) {
/*  32 */     CSVParser parser = null;
/*     */
/*     */     try {
/*  35 */       parser = new CSVParser(inputFile.getAbsolutePath(), false);
/*  36 */     } catch (FileNotFoundException ex) {
/*  37 */       Logger.getLogger(NewhallDataset.class.getName()).log(Level.SEVERE, (String)null, ex);
/*  38 */     } catch (IOException ex) {
/*  39 */       Logger.getLogger(NewhallDataset.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */     }
/*     */
/*  42 */     ArrayList<String> firstRow = parser.getRecords().get(0);
/*  43 */     ArrayList<String> secondRow = parser.getRecords().get(1);
/*     */
/*  45 */     ArrayList<Double> precipitation = new ArrayList<Double>(12);
/*  46 */     ArrayList<Double> temperature = new ArrayList<Double>(12);
/*     */
/*  48 */     String name = ((String)firstRow.get(0)).replace("\"", "");
/*  49 */     String country = ((String)firstRow.get(1)).replace("\"", "");
/*     */
/*  51 */     double latitude = Double.parseDouble(firstRow.get(2)) + Double.parseDouble(firstRow.get(3)) / 60.0D;
/*  52 */     char nsHemisphere = ((String)firstRow.get(4)).toUpperCase().charAt(1);
/*     */
/*  54 */     double longitude = Double.parseDouble(firstRow.get(5)) + Double.parseDouble(firstRow.get(6)) / 60.0D;
/*  55 */     char ewHemisphere = ((String)firstRow.get(7)).toUpperCase().charAt(1);
/*  56 */     double elevation = Double.parseDouble(firstRow.get(8));
/*     */     int i;
/*  58 */     for (i = 0; i <= 11; i++) {
/*  59 */       precipitation.add(Double.valueOf(Double.parseDouble(secondRow.get(i))));
/*     */     }
/*     */
/*  62 */     for (i = 12; i <= 23; i++) {
/*  63 */       temperature.add(Double.valueOf(Double.parseDouble(secondRow.get(i))));
/*     */     }
/*     */
/*  66 */     int startYear = Integer.parseInt(secondRow.get(24));
/*  67 */     int endYear = Integer.parseInt(secondRow.get(25));
/*     */
/*  69 */     boolean isMetric = false;
/*  70 */     if (((String)secondRow.get(26)).charAt(1) == 'M') {
/*  71 */       isMetric = true;
/*     */     }
/*     */
/*  74 */     this.dataset = new NewhallDataset(name, country, latitude, longitude, nsHemisphere, ewHemisphere, elevation, precipitation, temperature, startYear, endYear, isMetric, 200.0D);
/*     */
/*     */
/*     */
/*     */
/*  79 */     NewhallDatasetMetadata ndm = new NewhallDatasetMetadata();
/*  80 */     ndm.setStationName(name);
/*  81 */     ndm.setStationCountry(country);
/*  82 */     ndm.setStationElevation(elevation);
/*  83 */     if (!isMetric) {
/*  84 */       ndm.setSoilAirOffset(4.5D);
/*  85 */       ndm.setUnitSystem("English");
/*     */     } else {
/*  87 */       ndm.setSoilAirOffset(2.5D);
/*  88 */       ndm.setUnitSystem("Metric");
/*     */     }
/*  90 */     ndm.setAmplitude(0.66D);
/*  91 */     ndm.setMlraId(-1);
/*  92 */     ndm.setNotes(new ArrayList<String>());
/*  93 */     ndm.getNotes().add("Results generated from legacy Newhall input format.");
/*     */
/*  95 */     this.dataset.setMetadata(ndm);
/*     */   }
/*     */
/*     */
/*     */   public NewhallDataset getDatset() {
/* 100 */     return this.dataset;
/*     */   }
/*     */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/psu/newhall/util/CSVFileParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
