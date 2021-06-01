/*     */ package org.psu.newhall.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.JDOMException;
/*     */ import org.jdom.input.SAXBuilder;
/*     */ import org.psu.newhall.sim.NewhallDataset;
/*     */ import org.psu.newhall.sim.NewhallDatasetMetadata;
/*     */ 
/*     */ public class XMLFileParser {
/*     */   NewhallDataset dataset;
/*     */   
/*     */   public XMLFileParser(File file) throws JDOMException, IOException {
/*     */     char nsHemisphere, ewHemisphere;
/*     */     Double smcsawc, maatmast;
/*  20 */     SAXBuilder builder = new SAXBuilder();
/*  21 */     Document doc = null;
/*  22 */     doc = builder.build(file.getAbsolutePath());
/*     */     
/*  24 */     Element root = doc.getRootElement();
/*  25 */     String modelVers = root.getAttributeValue("version");
/*     */     
/*  27 */     System.out.println("Parsing metadata.");
/*     */     
/*  29 */     Element metadata = root.getChild("metadata");
/*     */     
/*  31 */     Element stninfo = metadata.getChild("stninfo");
/*  32 */     String stationName = stninfo.getChildText("stnname");
/*  33 */     String nettype = stninfo.getChildText("nettype");
/*  34 */     String stationId = stninfo.getChildText("stnid");
/*  35 */     double stationElevation = Double.parseDouble(stninfo.getChildText("stnelev"));
/*  36 */     String stateProvidence = stninfo.getChildText("stateprov");
/*  37 */     String country = stninfo.getChildText("country");
/*     */     
/*  39 */     Element mlra = metadata.getChild("mlra");
/*  40 */     String mlraName = mlra.getChildText("mlraname");
/*  41 */     int mlraId = Integer.valueOf(mlra.getChildText("mlraid")).intValue();
/*     */     
/*  43 */     Element cntinfo = metadata.getChild("cntinfo");
/*     */     
/*  45 */     Element cntper = cntinfo.getChild("cntper");
/*  46 */     String firstName = cntper.getChildText("firstname");
/*  47 */     String lastName = cntper.getChildText("lastname");
/*  48 */     String title = cntper.getChildText("title");
/*     */     
/*  50 */     String cntorg = cntinfo.getChildText("cntorg");
/*     */     
/*  52 */     Element cntaddr = cntinfo.getChild("cntaddr");
/*  53 */     String address = cntaddr.getChildText("address");
/*  54 */     String city = cntaddr.getChildText("city");
/*  55 */     String stateprov = cntaddr.getChildText("stateprov");
/*  56 */     String postal = cntaddr.getChildText("postal");
/*  57 */     String cntCountry = cntaddr.getChildText("country");
/*     */     
/*  59 */     String cntemail = cntinfo.getChildText("cntemail");
/*  60 */     String cntphone = cntinfo.getChildText("cntphone");
/*     */     
/*  62 */     Element notes = metadata.getChild("notes");
/*  63 */     List<Element> allNotes = notes.getChildren();
/*  64 */     List<String> allNotesStr = new ArrayList<String>(allNotes.size());
/*  65 */     for (Element note : allNotes) {
/*  66 */       allNotesStr.add(note.getText());
/*     */     }
/*     */     
/*  69 */     String rundate = metadata.getChildText("rundate");
/*  70 */     String nsmver = metadata.getChildText("nsmver");
/*  71 */     String srcunitsys = metadata.getChildText("srcunitsys");
/*     */     
/*  73 */     System.out.println("Parsing input.");
/*     */     
/*  75 */     Element input = root.getChild("input");
/*     */     
/*  77 */     Element location = input.getChild("location");
/*  78 */     double lat = Double.parseDouble(location.getChildText("lat"));
/*     */     
/*  80 */     if (lat >= 0.0D) {
/*  81 */       nsHemisphere = 'N';
/*     */     } else {
/*     */       
/*  84 */       lat *= -1.0D;
/*  85 */       nsHemisphere = 'S';
/*     */     } 
/*  87 */     double lon = Double.parseDouble(location.getChildText("lon"));
/*     */     
/*  89 */     if (lon >= 0.0D) {
/*  90 */       ewHemisphere = 'E';
/*     */     } else {
/*     */       
/*  93 */       lon *= -1.0D;
/*  94 */       ewHemisphere = 'W';
/*     */     } 
/*  96 */     String usercoordfmt = location.getChildText("usercoordfmt");
/*     */     
/*  98 */     Element recordpd = input.getChild("recordpd");
/*  99 */     String pdtype = recordpd.getChildText("pdtype");
/* 100 */     int pdbegin = Integer.valueOf(recordpd.getChildText("pdbegin")).intValue();
/* 101 */     int pdend = Integer.valueOf(recordpd.getChildText("pdend")).intValue();
/*     */     
/* 103 */     Element precips = input.getChild("precips");
/* 104 */     List<Element> allPrecips = precips.getChildren();
/* 105 */     List<Double> allPrecipsDbl = new ArrayList<Double>(12);
/* 106 */     Element airtemps = input.getChild("airtemps");
/* 107 */     List<Element> allAirTemps = airtemps.getChildren();
/* 108 */     List<Double> allAirTempsDbl = new ArrayList<Double>(12);
/*     */     
/* 110 */     String[] monthStr = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
/*     */ 
/*     */     
/* 113 */     System.out.println("Parsing precipitation and air temps.");
/*     */     
/* 115 */     for (String month : monthStr) {
/* 116 */       for (Element precip : allPrecips) {
/* 117 */         String id = precip.getAttributeValue("id");
/* 118 */         if (id.equals(month)) {
/* 119 */           allPrecipsDbl.add(Double.valueOf(precip.getText()));
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 124 */       for (Element airtemp : allAirTemps) {
/* 125 */         String id = airtemp.getAttributeValue("id");
/* 126 */         if (id.equals(month)) {
/* 127 */           allAirTempsDbl.add(Double.valueOf(airtemp.getText()));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 132 */     System.out.println("Parsing soil-air relationship variables.");
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 137 */       smcsawc = Double.valueOf(Double.parseDouble(input.getChildText("smcsawc")));
/* 138 */     } catch (NumberFormatException e) {
/* 139 */       smcsawc = Double.valueOf(200.0D);
/*     */     } 
/*     */     
/* 142 */     Element soilairrel = input.getChild("soilairrel");
/*     */     
/* 144 */     Double ampltd = Double.valueOf(Double.parseDouble(soilairrel.getChildText("ampltd")));
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 149 */       maatmast = Double.valueOf(Double.parseDouble(soilairrel.getChildText("maatmast")));
/* 150 */     } catch (NumberFormatException e) {
/*     */       
/* 152 */       maatmast = Double.valueOf(2.5D);
/*     */     } 
/*     */     
/* 155 */     this
/*     */ 
/*     */       
/* 158 */       .dataset = new NewhallDataset(stationName, country, lat, lon, nsHemisphere, ewHemisphere, stationElevation, allPrecipsDbl, allAirTempsDbl, pdbegin, pdend, true, smcsawc.doubleValue());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     NewhallDatasetMetadata ndm = new NewhallDatasetMetadata(stationName, stationId, stationElevation, stateProvidence, country, mlraName, mlraId, firstName, lastName, title, cntorg, address, city, stateprov, postal, cntCountry, cntemail, cntphone, allNotesStr, rundate, modelVers, srcunitsys, maatmast.doubleValue(), ampltd.doubleValue(), nettype);
/*     */     
/* 166 */     this.dataset.setMetadata(ndm);
/*     */     
/* 168 */     System.out.println("XML dataset built.");
/*     */   }
/*     */ 
/*     */   
/*     */   public NewhallDataset getDataset() {
/* 173 */     return this.dataset;
/*     */   }
/*     */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/psu/newhall/util/XMLFileParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */