/*     */ package org.psu.newhall.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.jdom.Content;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.output.Format;
/*     */ import org.jdom.output.XMLOutputter;
/*     */ import org.psu.newhall.Newhall;
/*     */ import org.psu.newhall.sim.NewhallDataset;
/*     */ import org.psu.newhall.sim.NewhallResults;
/*     */ 
/*     */ public class XMLResultsExporter
/*     */ {
/*     */   public XMLResultsExporter(File outputFile) {
/*  22 */     this.outputFile = outputFile;
/*     */   }
/*     */   File outputFile;
/*     */   
/*     */   public void export(NewhallResults results, NewhallDataset dataset) throws IOException {
/*  27 */     boolean toMetric = true;
/*     */     
/*  29 */     Document doc = new Document();
/*  30 */     Element model = new Element("model");
/*  31 */     doc.setRootElement(model);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  37 */     Element metadata = new Element("metadata");
/*     */     
/*  39 */     Element stninfo = new Element("stninfo");
/*  40 */     Element nettype = new Element("nettype");
/*  41 */     Element stnid = new Element("stnid");
/*  42 */     nettype.setText(dataset.getMetadata().getNetwork());
/*  43 */     Element stnname = new Element("stnname");
/*  44 */     stnname.setText(dataset.getName());
/*  45 */     Element stnelev = new Element("stnelev");
/*  46 */     stnelev.setText(Double.toString(dataset.getElevation()));
/*  47 */     Element stateprov = new Element("stateprov");
/*  48 */     Element country = new Element("country");
/*  49 */     country.setText(dataset.getCountry());
/*     */     
/*  51 */     if (dataset.getMetadata() != null) {
/*  52 */       stnid.setText(dataset.getMetadata().getStationId());
/*  53 */       stateprov.setText(dataset.getMetadata().getStationStateProvidence());
/*     */     } 
/*     */     
/*  56 */     stninfo.addContent((Content)nettype);
/*  57 */     stninfo.addContent((Content)stnname);
/*  58 */     stninfo.addContent((Content)stnid);
/*  59 */     stninfo.addContent((Content)stnelev);
/*  60 */     stninfo.addContent((Content)stateprov);
/*  61 */     stninfo.addContent((Content)country);
/*  62 */     metadata.addContent((Content)stninfo);
/*     */     
/*  64 */     Element mlra = new Element("mlra");
/*  65 */     Element mlraname = new Element("mlraname");
/*  66 */     Element mlraid = new Element("mlraid");
/*     */     
/*  68 */     if (dataset.getMetadata() != null) {
/*  69 */       mlraname.setText(dataset.getMetadata().getMlraName());
/*  70 */       mlraid.setText(Integer.toString(dataset.getMetadata().getMlraId()));
/*     */     } 
/*     */     
/*  73 */     mlra.addContent((Content)mlraname);
/*  74 */     mlra.addContent((Content)mlraid);
/*  75 */     metadata.addContent((Content)mlra);
/*     */     
/*  77 */     Element cntinfo = new Element("cntinfo");
/*     */     
/*  79 */     Element cntper = new Element("cntper");
/*  80 */     Element firstname = new Element("firstname");
/*  81 */     Element lastname = new Element("lastname");
/*  82 */     Element title = new Element("title");
/*     */     
/*  84 */     if (dataset.getMetadata() != null) {
/*  85 */       firstname.setText(dataset.getMetadata().getContribFirstName());
/*  86 */       lastname.setText(dataset.getMetadata().getContribLastName());
/*  87 */       title.setText(dataset.getMetadata().getContribTitle());
/*     */     } 
/*     */     
/*  90 */     cntper.addContent((Content)firstname);
/*  91 */     cntper.addContent((Content)lastname);
/*  92 */     cntper.addContent((Content)title);
/*  93 */     cntinfo.addContent((Content)cntper);
/*     */     
/*  95 */     Element cntorg = new Element("cntorg");
/*  96 */     if (dataset.getMetadata() != null) {
/*  97 */       cntorg.setText(dataset.getMetadata().getContribOrg());
/*     */     }
/*  99 */     cntinfo.addContent((Content)cntorg);
/*     */     
/* 101 */     Element cntaddr = new Element("cntaddr");
/* 102 */     Element address = new Element("address");
/* 103 */     Element city = new Element("city");
/* 104 */     Element stateprovContrib = new Element("stateprov");
/* 105 */     Element postal = new Element("postal");
/* 106 */     Element countryContrib = new Element("country");
/*     */     
/* 108 */     if (dataset.getMetadata() != null) {
/* 109 */       address.setText(dataset.getMetadata().getContribAddress());
/* 110 */       city.setText(dataset.getMetadata().getContribCity());
/* 111 */       stateprovContrib.setText(dataset.getMetadata().getContribStateProvidence());
/* 112 */       postal.setText(dataset.getMetadata().getContribPostal());
/* 113 */       countryContrib.setText(dataset.getMetadata().getContribCountry());
/*     */     } 
/*     */     
/* 116 */     cntaddr.addContent((Content)address);
/* 117 */     cntaddr.addContent((Content)city);
/* 118 */     cntaddr.addContent((Content)stateprovContrib);
/* 119 */     cntaddr.addContent((Content)postal);
/* 120 */     cntaddr.addContent((Content)countryContrib);
/* 121 */     cntinfo.addContent((Content)cntaddr);
/*     */     
/* 123 */     Element cntemail = new Element("cntemail");
/* 124 */     Element cntphone = new Element("cntphone");
/*     */     
/* 126 */     if (dataset.getMetadata() != null) {
/* 127 */       cntemail.setText(dataset.getMetadata().getContribEmail());
/* 128 */       cntphone.setText(dataset.getMetadata().getContribPhone());
/*     */     } 
/*     */     
/* 131 */     cntinfo.addContent((Content)cntemail);
/* 132 */     cntinfo.addContent((Content)cntphone);
/* 133 */     metadata.addContent((Content)cntinfo);
/*     */     
/* 135 */     Element notes = new Element("notes");
/* 136 */     if (dataset.getMetadata() != null) {
/* 137 */       for (String noteText : dataset.getMetadata().getNotes()) {
/* 138 */         Element note = new Element("note");
/* 139 */         note.setText(noteText);
/* 140 */         notes.addContent((Content)note);
/*     */       } 
/*     */     }
/* 143 */     metadata.addContent((Content)notes);
/*     */     
/* 145 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
/* 146 */     Element rundate = new Element("rundate");
/* 147 */     rundate.setText(sdf.format(new Date()));
/* 148 */     metadata.addContent((Content)rundate);
/*     */     
/* 150 */     Element nsmversion = new Element("nsmver");
/* 151 */     Element srcunitsys = new Element("srcunitsys");
/* 152 */     nsmversion.setText(Newhall.NSM_VERSION);
/* 153 */     srcunitsys.setText(dataset.getMetadata().getUnitSystem());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     metadata.addContent((Content)nsmversion);
/* 160 */     metadata.addContent((Content)srcunitsys);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 166 */     Element input = new Element("input");
/*     */     
/* 168 */     Element location = new Element("location");
/* 169 */     Element lat = new Element("lat");
/* 170 */     if (dataset.getNsHemisphere() == 'N') {
/* 171 */       lat.setText(Double.toString(round(dataset.getLatitude(), 4)));
/*     */     } else {
/* 173 */       lat.setText(Double.toString(round(dataset.getLatitude() * -1.0D, 4)));
/*     */     } 
/*     */     
/* 176 */     Element lon = new Element("lon");
/* 177 */     if (dataset.getEwHemisphere() == 'E') {
/* 178 */       lon.setText(Double.toString(round(dataset.getLongitude(), 4)));
/*     */     } else {
/* 180 */       lon.setText(Double.toString(round(dataset.getLongitude() * -1.0D, 4)));
/*     */     } 
/* 182 */     Element usercoordfmt = new Element("usercoordfmt");
/* 183 */     usercoordfmt.setText("DD");
/* 184 */     location.addContent((Content)lat);
/* 185 */     location.addContent((Content)lon);
/* 186 */     location.addContent((Content)usercoordfmt);
/* 187 */     input.addContent((Content)location);
/*     */     
/* 189 */     Element recordpd = new Element("recordpd");
/* 190 */     Element pdtype = new Element("pdtype");
/* 191 */     pdtype.setText("normal");
/* 192 */     Element pdbegin = new Element("pdbegin");
/* 193 */     pdbegin.setText(Integer.toString(dataset.getStartYear()));
/* 194 */     Element pdend = new Element("pdend");
/* 195 */     pdend.setText(Integer.toString(dataset.getEndYear()));
/* 196 */     recordpd.addContent((Content)pdtype);
/* 197 */     recordpd.addContent((Content)pdbegin);
/* 198 */     recordpd.addContent((Content)pdend);
/* 199 */     input.addContent((Content)recordpd);
/*     */     
/* 201 */     Element precips = new Element("precips");
/* 202 */     String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
/*     */     
/* 204 */     for (int i = 0; i < months.length; i++) {
/* 205 */       Element precip = new Element("precip");
/* 206 */       precip.setAttribute("id", months[i]);
/* 207 */       Double precipVal = dataset.getPrecipitation().get(i);
/* 208 */       if (toMetric && !dataset.isMetric()) {
/*     */         
/* 210 */         precipVal = Double.valueOf(precipVal.doubleValue() * 25.4D);
/* 211 */       } else if (!toMetric && dataset.isMetric()) {
/*     */         
/* 213 */         precipVal = Double.valueOf(precipVal.doubleValue() * 0.0393700787D);
/*     */       } 
/* 215 */       precip.setText(Double.toString(round(precipVal.doubleValue(), 2)));
/* 216 */       precips.addContent((Content)precip);
/*     */     } 
/* 218 */     input.addContent((Content)precips);
/*     */     
/* 220 */     Element airtemps = new Element("airtemps");
/* 221 */     for (int j = 0; j < months.length; j++) {
/* 222 */       Element airtemp = new Element("airtemp");
/* 223 */       airtemp.setAttribute("id", months[j]);
/* 224 */       Double airtempVal = dataset.getTemperature().get(j);
/* 225 */       if (toMetric && !dataset.isMetric()) {
/*     */         
/* 227 */         airtempVal = Double.valueOf((airtempVal.doubleValue() - 32.0D) * 5.0D / 9.0D);
/* 228 */       } else if (!toMetric && dataset.isMetric()) {
/*     */         
/* 230 */         airtempVal = Double.valueOf(airtempVal.doubleValue() * 9.0D / 5.0D + 32.0D);
/*     */       } 
/* 232 */       airtemp.setText(Double.toString(round(airtempVal.doubleValue(), 2)));
/* 233 */       airtemps.addContent((Content)airtemp);
/*     */     } 
/* 235 */     input.addContent((Content)airtemps);
/*     */     
/* 237 */     Element smcsawc = new Element("smcsawc");
/* 238 */     smcsawc.setText(Double.toString(results.getWaterHoldingCapacity()));
/* 239 */     input.addContent((Content)smcsawc);
/*     */     
/* 241 */     Element soilairrel = new Element("soilairrel");
/*     */ 
/*     */ 
/*     */     
/* 245 */     Element ampltd = new Element("ampltd");
/* 246 */     ampltd.setText(Double.toString(dataset.getMetadata().getAmplitude()));
/*     */     
/* 248 */     Element maatmast = new Element("maatmast");
/* 249 */     double maatmastVal = dataset.getMetadata().getSoilAirOffset();
/* 250 */     if (toMetric && !dataset.isMetric()) {
/*     */       
/* 252 */       maatmastVal *= 0.5555555555555556D;
/* 253 */     } else if (!toMetric && dataset.isMetric()) {
/*     */       
/* 255 */       maatmastVal *= 1.8D;
/*     */     } 
/* 257 */     maatmast.setText(Double.toString(round(maatmastVal, 2)));
/*     */ 
/*     */     
/* 260 */     soilairrel.addContent((Content)ampltd);
/* 261 */     soilairrel.addContent((Content)maatmast);
/* 262 */     input.addContent((Content)soilairrel);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 268 */     Element output = new Element("output");
/*     */     
/* 270 */     Element smrclass = new Element("smrclass");
/* 271 */     Element strclass = new Element("strclass");
/* 272 */     Element subdiv = new Element("subgrpmod");
/* 273 */     Element awb = new Element("awb");
/* 274 */     Element swb = new Element("swb");
/* 275 */     Element smcstates = new Element("smcstates");
/* 276 */     Element pets = new Element("pets");
/*     */     
/* 278 */     Element calendars = new Element("calendars");
/*     */     
/* 280 */     smrclass.setText(results.getMoistureRegime());
/* 281 */     strclass.setText(results.getTemperatureRegime());
/* 282 */     subdiv.setText(results.getRegimeSubdivision1() + " " + results.getRegimeSubdivision2());
/*     */     
/* 284 */     awb.setText(Double.toString(round(results.getAnnualWaterBalance(), 2)));
/* 285 */     swb.setText(Double.toString(round(results.getSummerWaterBalance(), 2)));
/*     */     
/* 287 */     Element cumdays = new Element("cumdays");
/* 288 */     Element yrdry = new Element("yrdry");
/* 289 */     Element yrmd = new Element("yrmd");
/* 290 */     Element yrmst = new Element("yrmst");
/* 291 */     Element bio5dry = new Element("bio5dry");
/* 292 */     Element bio5md = new Element("bio5md");
/* 293 */     Element bio5mst = new Element("bio5mst");
/*     */     
/* 295 */     yrdry.setText(Integer.toString(results.getNumCumulativeDaysDry()));
/* 296 */     yrmd.setText(Integer.toString(results.getNumCumulativeDaysMoistDry()));
/* 297 */     yrmst.setText(Integer.toString(results.getNumCumulativeDaysMoist()));
/*     */     
/* 299 */     bio5dry.setText(Integer.toString(results.getNumCumulativeDaysDryOver5C()));
/* 300 */     bio5md.setText(Integer.toString(results.getNumCumulativeDaysMoistDryOver5C()));
/* 301 */     bio5mst.setText(Integer.toString(results.getNumCumulativeDaysMoistOver5C()));
/*     */     
/* 303 */     cumdays.addContent((Content)yrdry);
/* 304 */     cumdays.addContent((Content)yrmd);
/* 305 */     cumdays.addContent((Content)yrmst);
/* 306 */     cumdays.addContent((Content)bio5dry);
/* 307 */     cumdays.addContent((Content)bio5md);
/* 308 */     cumdays.addContent((Content)bio5mst);
/*     */     
/* 310 */     Element consdays = new Element("consdays");
/* 311 */     Element yrmst2 = new Element("yrmst");
/* 312 */     Element bio8mst = new Element("bio8mst");
/* 313 */     Element smrdry = new Element("smrdry");
/* 314 */     Element wtrmst = new Element("wtrmst");
/*     */     
/* 316 */     yrmst2.setText(Integer.toString(results.getNumConsecutiveDaysMoistInSomeParts()));
/* 317 */     bio8mst.setText(Integer.toString(results.getNumConsecutiveDaysMoistInSomePartsOver8C()));
/* 318 */     smrdry.setText(Integer.toString(results.getDryDaysAfterSummerSolstice()));
/* 319 */     wtrmst.setText(Integer.toString(results.getMoistDaysAfterWinterSolstice()));
/*     */     
/* 321 */     consdays.addContent((Content)yrmst2);
/* 322 */     consdays.addContent((Content)bio8mst);
/* 323 */     consdays.addContent((Content)smrdry);
/* 324 */     consdays.addContent((Content)wtrmst);
/*     */     
/* 326 */     smcstates.addContent((Content)cumdays);
/* 327 */     smcstates.addContent((Content)consdays);
/*     */     
/* 329 */     for (int k = 0; k < months.length; k++) {
/* 330 */       Element pet = new Element("pet");
/* 331 */       pet.setAttribute("id", months[k]);
/* 332 */       Double petVal = results.getMeanPotentialEvapotranspiration().get(k);
/* 333 */       if (!toMetric)
/*     */       {
/* 335 */         petVal = Double.valueOf(petVal.doubleValue() * 0.0393700787D);
/*     */       }
/* 337 */       pet.setText(Double.toString(round(petVal.doubleValue(), 2)));
/* 338 */       pets.addContent((Content)pet);
/*     */     } 
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
/* 355 */     Element tempCalElement = new Element("tempcal");
/* 356 */     List<Character> tempCal = results.getTemperatureCalendar();
/* 357 */     char lastChar = ((Character)tempCal.get(0)).charValue();
/* 358 */     int lastPos = 0;
/* 359 */     for (int m = 1; m < tempCal.size(); m++) {
/* 360 */       char thisChar = ((Character)tempCal.get(m)).charValue();
/* 361 */       if (thisChar != lastChar || m == tempCal.size() - 1) {
/*     */ 
/*     */         
/* 364 */         Element blockToAdd = null;
/*     */         
/* 366 */         switch (lastChar) {
/*     */           case '-':
/* 368 */             blockToAdd = new Element("stlt5");
/*     */             break;
/*     */           case '5':
/* 371 */             blockToAdd = new Element("st5to8");
/*     */             break;
/*     */           case '8':
/* 374 */             blockToAdd = new Element("stgt8");
/*     */             break;
/*     */           default:
/* 377 */             blockToAdd = new Element("unknown");
/*     */             break;
/*     */         } 
/*     */         
/* 381 */         Element beginday = new Element("beginday");
/* 382 */         Element endday = new Element("endday");
/* 383 */         beginday.setText(Integer.toString(lastPos + 1));
/* 384 */         endday.setText(Integer.toString(m));
/*     */ 
/*     */         
/* 387 */         if (m == tempCal.size() - 1) {
/* 388 */           endday.setText(Integer.toString(tempCal.size()));
/*     */         }
/*     */         
/* 391 */         blockToAdd.addContent((Content)beginday);
/* 392 */         blockToAdd.addContent((Content)endday);
/* 393 */         tempCalElement.addContent((Content)blockToAdd);
/*     */         
/* 395 */         lastChar = thisChar;
/* 396 */         lastPos = m;
/*     */       } 
/*     */     } 
/* 399 */     calendars.addContent((Content)tempCalElement);
/*     */     
/* 401 */     Element moistCalElement = new Element("moistcal");
/* 402 */     List<Integer> moistCal = results.getMoistureCalendar();
/* 403 */     int lastVal = ((Integer)moistCal.get(0)).intValue();
/* 404 */     lastPos = 0;
/* 405 */     for (int n = 1; n < moistCal.size(); n++) {
/* 406 */       int thisVal = ((Integer)moistCal.get(n)).intValue();
/* 407 */       if (thisVal != lastVal || n == moistCal.size() - 1) {
/*     */ 
/*     */         
/* 410 */         Element blockToAdd = null;
/*     */         
/* 412 */         switch (lastVal) {
/*     */           case 1:
/* 414 */             blockToAdd = new Element("dry");
/*     */             break;
/*     */           case 2:
/* 417 */             blockToAdd = new Element("moistdry");
/*     */             break;
/*     */           case 3:
/* 420 */             blockToAdd = new Element("moist");
/*     */             break;
/*     */           default:
/* 423 */             blockToAdd = new Element("unknown");
/*     */             break;
/*     */         } 
/*     */         
/* 427 */         Element beginday = new Element("beginday");
/* 428 */         Element endday = new Element("endday");
/* 429 */         beginday.setText(Integer.toString(lastPos + 1));
/* 430 */         endday.setText(Integer.toString(n));
/*     */ 
/*     */         
/* 433 */         if (n == moistCal.size() - 1) {
/* 434 */           endday.setText(Integer.toString(moistCal.size()));
/*     */         }
/*     */         
/* 437 */         blockToAdd.addContent((Content)beginday);
/* 438 */         blockToAdd.addContent((Content)endday);
/* 439 */         moistCalElement.addContent((Content)blockToAdd);
/*     */         
/* 441 */         lastVal = thisVal;
/* 442 */         lastPos = n;
/*     */       } 
/*     */     } 
/* 445 */     calendars.addContent((Content)moistCalElement);
/*     */     
/* 447 */     output.addContent((Content)smrclass);
/* 448 */     output.addContent((Content)strclass);
/* 449 */     output.addContent((Content)subdiv);
/* 450 */     output.addContent((Content)awb);
/* 451 */     output.addContent((Content)swb);
/* 452 */     output.addContent((Content)smcstates);
/* 453 */     output.addContent((Content)pets);
/*     */     
/* 455 */     output.addContent((Content)calendars);
/*     */ 
/*     */     
/* 458 */     model.addContent((Content)metadata);
/* 459 */     model.addContent((Content)input);
/* 460 */     model.addContent((Content)output);
/*     */ 
/*     */     
/* 463 */     XMLOutputter outputter = new XMLOutputter();
/* 464 */     outputter.setFormat(Format.getPrettyFormat());
/* 465 */     FileWriter writer = new FileWriter(this.outputFile);
/* 466 */     outputter.output(doc, writer);
/* 467 */     writer.close();
/*     */   }
/*     */ 
/*     */   
/*     */   double round(double d, int decimalPlaces) {
/* 472 */     String format = "#.#";
/* 473 */     for (int i = decimalPlaces - 1; i > 0; i--) {
/* 474 */       format = format + "#";
/*     */     }
/* 476 */     DecimalFormat df = new DecimalFormat(format);
/* 477 */     return Double.valueOf(df.format(d)).doubleValue();
/*     */   }
/*     */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/psu/newhall/util/XMLResultsExporter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */