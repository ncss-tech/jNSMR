/*     */ package org.psu.newhall.util;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
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
/*     */ public class XMLStringResultsExporter {
/*     */   public static String export(NewhallResults results, NewhallDataset dataset) {
/*  20 */     boolean toMetric = true;
/*     */     
/*  22 */     Document doc = new Document();
/*  23 */     Element model = new Element("model");
/*  24 */     doc.setRootElement(model);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  30 */     Element metadata = new Element("metadata");
/*     */     
/*  32 */     Element stninfo = new Element("stninfo");
/*  33 */     Element nettype = new Element("nettype");
/*  34 */     Element stnid = new Element("stnid");
/*  35 */     nettype.setText(dataset.getMetadata().getNetwork());
/*  36 */     Element stnname = new Element("stnname");
/*  37 */     stnname.setText(dataset.getName());
/*  38 */     Element stnelev = new Element("stnelev");
/*  39 */     stnelev.setText(Double.toString(dataset.getElevation()));
/*  40 */     Element stateprov = new Element("stateprov");
/*  41 */     Element country = new Element("country");
/*  42 */     country.setText(dataset.getCountry());
/*     */     
/*  44 */     if (dataset.getMetadata() != null) {
/*  45 */       stnid.setText(dataset.getMetadata().getStationId());
/*  46 */       stateprov.setText(dataset.getMetadata().getStationStateProvidence());
/*     */     } 
/*     */     
/*  49 */     stninfo.addContent((Content)nettype);
/*  50 */     stninfo.addContent((Content)stnname);
/*  51 */     stninfo.addContent((Content)stnid);
/*  52 */     stninfo.addContent((Content)stnelev);
/*  53 */     stninfo.addContent((Content)stateprov);
/*  54 */     stninfo.addContent((Content)country);
/*  55 */     metadata.addContent((Content)stninfo);
/*     */     
/*  57 */     Element mlra = new Element("mlra");
/*  58 */     Element mlraname = new Element("mlraname");
/*  59 */     Element mlraid = new Element("mlraid");
/*     */     
/*  61 */     if (dataset.getMetadata() != null) {
/*  62 */       mlraname.setText(dataset.getMetadata().getMlraName());
/*  63 */       mlraid.setText(Integer.toString(dataset.getMetadata().getMlraId()));
/*     */     } 
/*     */     
/*  66 */     mlra.addContent((Content)mlraname);
/*  67 */     mlra.addContent((Content)mlraid);
/*  68 */     metadata.addContent((Content)mlra);
/*     */     
/*  70 */     Element cntinfo = new Element("cntinfo");
/*     */     
/*  72 */     Element cntper = new Element("cntper");
/*  73 */     Element firstname = new Element("firstname");
/*  74 */     Element lastname = new Element("lastname");
/*  75 */     Element title = new Element("title");
/*     */     
/*  77 */     if (dataset.getMetadata() != null) {
/*  78 */       firstname.setText(dataset.getMetadata().getContribFirstName());
/*  79 */       lastname.setText(dataset.getMetadata().getContribLastName());
/*  80 */       title.setText(dataset.getMetadata().getContribTitle());
/*     */     } 
/*     */     
/*  83 */     cntper.addContent((Content)firstname);
/*  84 */     cntper.addContent((Content)lastname);
/*  85 */     cntper.addContent((Content)title);
/*  86 */     cntinfo.addContent((Content)cntper);
/*     */     
/*  88 */     Element cntorg = new Element("cntorg");
/*  89 */     if (dataset.getMetadata() != null) {
/*  90 */       cntorg.setText(dataset.getMetadata().getContribOrg());
/*     */     }
/*  92 */     cntinfo.addContent((Content)cntorg);
/*     */     
/*  94 */     Element cntaddr = new Element("cntaddr");
/*  95 */     Element address = new Element("address");
/*  96 */     Element city = new Element("city");
/*  97 */     Element stateprovContrib = new Element("stateprov");
/*  98 */     Element postal = new Element("postal");
/*  99 */     Element countryContrib = new Element("country");
/*     */     
/* 101 */     if (dataset.getMetadata() != null) {
/* 102 */       address.setText(dataset.getMetadata().getContribAddress());
/* 103 */       city.setText(dataset.getMetadata().getContribCity());
/* 104 */       stateprovContrib.setText(dataset.getMetadata().getContribStateProvidence());
/* 105 */       postal.setText(dataset.getMetadata().getContribPostal());
/* 106 */       countryContrib.setText(dataset.getMetadata().getContribCountry());
/*     */     } 
/*     */     
/* 109 */     cntaddr.addContent((Content)address);
/* 110 */     cntaddr.addContent((Content)city);
/* 111 */     cntaddr.addContent((Content)stateprovContrib);
/* 112 */     cntaddr.addContent((Content)postal);
/* 113 */     cntaddr.addContent((Content)countryContrib);
/* 114 */     cntinfo.addContent((Content)cntaddr);
/*     */     
/* 116 */     Element cntemail = new Element("cntemail");
/* 117 */     Element cntphone = new Element("cntphone");
/*     */     
/* 119 */     if (dataset.getMetadata() != null) {
/* 120 */       cntemail.setText(dataset.getMetadata().getContribEmail());
/* 121 */       cntphone.setText(dataset.getMetadata().getContribPhone());
/*     */     } 
/*     */     
/* 124 */     cntinfo.addContent((Content)cntemail);
/* 125 */     cntinfo.addContent((Content)cntphone);
/* 126 */     metadata.addContent((Content)cntinfo);
/*     */     
/* 128 */     Element notes = new Element("notes");
/* 129 */     if (dataset.getMetadata() != null) {
/* 130 */       for (String noteText : dataset.getMetadata().getNotes()) {
/* 131 */         Element note = new Element("note");
/* 132 */         note.setText(noteText);
/* 133 */         notes.addContent((Content)note);
/*     */       } 
/*     */     }
/* 136 */     metadata.addContent((Content)notes);
/*     */     
/* 138 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
/* 139 */     Element rundate = new Element("rundate");
/* 140 */     rundate.setText(sdf.format(new Date()));
/* 141 */     metadata.addContent((Content)rundate);
/*     */     
/* 143 */     Element nsmversion = new Element("nsmver");
/* 144 */     Element srcunitsys = new Element("srcunitsys");
/* 145 */     nsmversion.setText(Newhall.NSM_VERSION);
/* 146 */     srcunitsys.setText(dataset.getMetadata().getUnitSystem());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     metadata.addContent((Content)nsmversion);
/* 153 */     metadata.addContent((Content)srcunitsys);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     Element input = new Element("input");
/*     */     
/* 161 */     Element location = new Element("location");
/* 162 */     Element lat = new Element("lat");
/* 163 */     if (dataset.getNsHemisphere() == 'N') {
/* 164 */       lat.setText(Double.toString(round(dataset.getLatitude(), 4)));
/*     */     } else {
/* 166 */       lat.setText(Double.toString(round(dataset.getLatitude() * -1.0D, 4)));
/*     */     } 
/*     */     
/* 169 */     Element lon = new Element("lon");
/* 170 */     if (dataset.getEwHemisphere() == 'E') {
/* 171 */       lon.setText(Double.toString(round(dataset.getLongitude(), 4)));
/*     */     } else {
/* 173 */       lon.setText(Double.toString(round(dataset.getLongitude() * -1.0D, 4)));
/*     */     } 
/* 175 */     Element usercoordfmt = new Element("usercoordfmt");
/* 176 */     usercoordfmt.setText("DD");
/* 177 */     location.addContent((Content)lat);
/* 178 */     location.addContent((Content)lon);
/* 179 */     location.addContent((Content)usercoordfmt);
/* 180 */     input.addContent((Content)location);
/*     */     
/* 182 */     Element recordpd = new Element("recordpd");
/* 183 */     Element pdtype = new Element("pdtype");
/* 184 */     pdtype.setText("normal");
/* 185 */     Element pdbegin = new Element("pdbegin");
/* 186 */     pdbegin.setText(Integer.toString(dataset.getStartYear()));
/* 187 */     Element pdend = new Element("pdend");
/* 188 */     pdend.setText(Integer.toString(dataset.getEndYear()));
/* 189 */     recordpd.addContent((Content)pdtype);
/* 190 */     recordpd.addContent((Content)pdbegin);
/* 191 */     recordpd.addContent((Content)pdend);
/* 192 */     input.addContent((Content)recordpd);
/*     */     
/* 194 */     Element precips = new Element("precips");
/* 195 */     String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
/*     */     
/* 197 */     for (int i = 0; i < months.length; i++) {
/* 198 */       Element precip = new Element("precip");
/* 199 */       precip.setAttribute("id", months[i]);
/* 200 */       Double precipVal = dataset.getPrecipitation().get(i);
/* 201 */       if (toMetric && !dataset.isMetric()) {
/*     */         
/* 203 */         precipVal = Double.valueOf(precipVal.doubleValue() * 25.4D);
/* 204 */       } else if (!toMetric && dataset.isMetric()) {
/*     */         
/* 206 */         precipVal = Double.valueOf(precipVal.doubleValue() * 0.0393700787D);
/*     */       } 
/* 208 */       precip.setText(Double.toString(round(precipVal.doubleValue(), 2)));
/* 209 */       precips.addContent((Content)precip);
/*     */     } 
/* 211 */     input.addContent((Content)precips);
/*     */     
/* 213 */     Element airtemps = new Element("airtemps");
/* 214 */     for (int j = 0; j < months.length; j++) {
/* 215 */       Element airtemp = new Element("airtemp");
/* 216 */       airtemp.setAttribute("id", months[j]);
/* 217 */       Double airtempVal = dataset.getTemperature().get(j);
/* 218 */       if (toMetric && !dataset.isMetric()) {
/*     */         
/* 220 */         airtempVal = Double.valueOf((airtempVal.doubleValue() - 32.0D) * 5.0D / 9.0D);
/* 221 */       } else if (!toMetric && dataset.isMetric()) {
/*     */         
/* 223 */         airtempVal = Double.valueOf(airtempVal.doubleValue() * 9.0D / 5.0D + 32.0D);
/*     */       } 
/* 225 */       airtemp.setText(Double.toString(round(airtempVal.doubleValue(), 2)));
/* 226 */       airtemps.addContent((Content)airtemp);
/*     */     } 
/* 228 */     input.addContent((Content)airtemps);
/*     */     
/* 230 */     Element smcsawc = new Element("smcsawc");
/* 231 */     smcsawc.setText(Double.toString(results.getWaterHoldingCapacity()));
/* 232 */     input.addContent((Content)smcsawc);
/*     */     
/* 234 */     Element soilairrel = new Element("soilairrel");
/*     */ 
/*     */ 
/*     */     
/* 238 */     Element ampltd = new Element("ampltd");
/* 239 */     ampltd.setText(Double.toString(dataset.getMetadata().getAmplitude()));
/*     */     
/* 241 */     Element maatmast = new Element("maatmast");
/* 242 */     double maatmastVal = dataset.getMetadata().getSoilAirOffset();
/* 243 */     if (toMetric && !dataset.isMetric()) {
/*     */       
/* 245 */       maatmastVal *= 0.5555555555555556D;
/* 246 */     } else if (!toMetric && dataset.isMetric()) {
/*     */       
/* 248 */       maatmastVal *= 1.8D;
/*     */     } 
/* 250 */     maatmast.setText(Double.toString(round(maatmastVal, 2)));
/*     */ 
/*     */     
/* 253 */     soilairrel.addContent((Content)ampltd);
/* 254 */     soilairrel.addContent((Content)maatmast);
/* 255 */     input.addContent((Content)soilairrel);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 261 */     Element output = new Element("output");
/*     */     
/* 263 */     Element smrclass = new Element("smrclass");
/* 264 */     Element strclass = new Element("strclass");
/* 265 */     Element subdiv = new Element("subgrpmod");
/* 266 */     Element awb = new Element("awb");
/* 267 */     Element swb = new Element("swb");
/* 268 */     Element smcstates = new Element("smcstates");
/* 269 */     Element pets = new Element("pets");
/*     */     
/* 271 */     Element calendars = new Element("calendars");
/*     */     
/* 273 */     smrclass.setText(results.getMoistureRegime());
/* 274 */     strclass.setText(results.getTemperatureRegime());
/* 275 */     subdiv.setText(results.getRegimeSubdivision1() + " " + results.getRegimeSubdivision2());
/*     */     
/* 277 */     awb.setText(Double.toString(round(results.getAnnualWaterBalance(), 2)));
/* 278 */     swb.setText(Double.toString(round(results.getSummerWaterBalance(), 2)));
/*     */     
/* 280 */     Element cumdays = new Element("cumdays");
/* 281 */     Element yrdry = new Element("yrdry");
/* 282 */     Element yrmd = new Element("yrmd");
/* 283 */     Element yrmst = new Element("yrmst");
/* 284 */     Element bio5dry = new Element("bio5dry");
/* 285 */     Element bio5md = new Element("bio5md");
/* 286 */     Element bio5mst = new Element("bio5mst");
/*     */     
/* 288 */     yrdry.setText(Integer.toString(results.getNumCumulativeDaysDry()));
/* 289 */     yrmd.setText(Integer.toString(results.getNumCumulativeDaysMoistDry()));
/* 290 */     yrmst.setText(Integer.toString(results.getNumCumulativeDaysMoist()));
/*     */     
/* 292 */     bio5dry.setText(Integer.toString(results.getNumCumulativeDaysDryOver5C()));
/* 293 */     bio5md.setText(Integer.toString(results.getNumCumulativeDaysMoistDryOver5C()));
/* 294 */     bio5mst.setText(Integer.toString(results.getNumCumulativeDaysMoistOver5C()));
/*     */     
/* 296 */     cumdays.addContent((Content)yrdry);
/* 297 */     cumdays.addContent((Content)yrmd);
/* 298 */     cumdays.addContent((Content)yrmst);
/* 299 */     cumdays.addContent((Content)bio5dry);
/* 300 */     cumdays.addContent((Content)bio5md);
/* 301 */     cumdays.addContent((Content)bio5mst);
/*     */     
/* 303 */     Element consdays = new Element("consdays");
/* 304 */     Element yrmst2 = new Element("yrmst");
/* 305 */     Element bio8mst = new Element("bio8mst");
/* 306 */     Element smrdry = new Element("smrdry");
/* 307 */     Element wtrmst = new Element("wtrmst");
/*     */     
/* 309 */     yrmst2.setText(Integer.toString(results.getNumConsecutiveDaysMoistInSomeParts()));
/* 310 */     bio8mst.setText(Integer.toString(results.getNumConsecutiveDaysMoistInSomePartsOver8C()));
/* 311 */     smrdry.setText(Integer.toString(results.getDryDaysAfterSummerSolstice()));
/* 312 */     wtrmst.setText(Integer.toString(results.getMoistDaysAfterWinterSolstice()));
/*     */     
/* 314 */     consdays.addContent((Content)yrmst2);
/* 315 */     consdays.addContent((Content)bio8mst);
/* 316 */     consdays.addContent((Content)smrdry);
/* 317 */     consdays.addContent((Content)wtrmst);
/*     */     
/* 319 */     smcstates.addContent((Content)cumdays);
/* 320 */     smcstates.addContent((Content)consdays);
/*     */     
/* 322 */     for (int k = 0; k < months.length; k++) {
/* 323 */       Element pet = new Element("pet");
/* 324 */       pet.setAttribute("id", months[k]);
/* 325 */       Double petVal = results.getMeanPotentialEvapotranspiration().get(k);
/* 326 */       if (!toMetric)
/*     */       {
/* 328 */         petVal = Double.valueOf(petVal.doubleValue() * 0.0393700787D);
/*     */       }
/* 330 */       pet.setText(Double.toString(round(petVal.doubleValue(), 2)));
/* 331 */       pets.addContent((Content)pet);
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
/* 348 */     Element tempCalElement = new Element("tempcal");
/* 349 */     List<Character> tempCal = results.getTemperatureCalendar();
/* 350 */     char lastChar = ((Character)tempCal.get(0)).charValue();
/* 351 */     int lastPos = 0;
/* 352 */     for (int m = 1; m < tempCal.size(); m++) {
/* 353 */       char thisChar = ((Character)tempCal.get(m)).charValue();
/* 354 */       if (thisChar != lastChar || m == tempCal.size() - 1) {
/*     */ 
/*     */         
/* 357 */         Element blockToAdd = null;
/*     */         
/* 359 */         switch (lastChar) {
/*     */           case '-':
/* 361 */             blockToAdd = new Element("stlt5");
/*     */             break;
/*     */           case '5':
/* 364 */             blockToAdd = new Element("st5to8");
/*     */             break;
/*     */           case '8':
/* 367 */             blockToAdd = new Element("stgt8");
/*     */             break;
/*     */           default:
/* 370 */             blockToAdd = new Element("unknown");
/*     */             break;
/*     */         } 
/*     */         
/* 374 */         Element beginday = new Element("beginday");
/* 375 */         Element endday = new Element("endday");
/* 376 */         beginday.setText(Integer.toString(lastPos + 1));
/* 377 */         endday.setText(Integer.toString(m));
/*     */ 
/*     */         
/* 380 */         if (m == tempCal.size() - 1) {
/* 381 */           endday.setText(Integer.toString(tempCal.size()));
/*     */         }
/*     */         
/* 384 */         blockToAdd.addContent((Content)beginday);
/* 385 */         blockToAdd.addContent((Content)endday);
/* 386 */         tempCalElement.addContent((Content)blockToAdd);
/*     */         
/* 388 */         lastChar = thisChar;
/* 389 */         lastPos = m;
/*     */       } 
/*     */     } 
/* 392 */     calendars.addContent((Content)tempCalElement);
/*     */     
/* 394 */     Element moistCalElement = new Element("moistcal");
/* 395 */     List<Integer> moistCal = results.getMoistureCalendar();
/* 396 */     int lastVal = ((Integer)moistCal.get(0)).intValue();
/* 397 */     lastPos = 0;
/* 398 */     for (int n = 1; n < moistCal.size(); n++) {
/* 399 */       int thisVal = ((Integer)moistCal.get(n)).intValue();
/* 400 */       if (thisVal != lastVal || n == moistCal.size() - 1) {
/*     */ 
/*     */         
/* 403 */         Element blockToAdd = null;
/*     */         
/* 405 */         switch (lastVal) {
/*     */           case 1:
/* 407 */             blockToAdd = new Element("dry");
/*     */             break;
/*     */           case 2:
/* 410 */             blockToAdd = new Element("moistdry");
/*     */             break;
/*     */           case 3:
/* 413 */             blockToAdd = new Element("moist");
/*     */             break;
/*     */           default:
/* 416 */             blockToAdd = new Element("unknown");
/*     */             break;
/*     */         } 
/*     */         
/* 420 */         Element beginday = new Element("beginday");
/* 421 */         Element endday = new Element("endday");
/* 422 */         beginday.setText(Integer.toString(lastPos + 1));
/* 423 */         endday.setText(Integer.toString(n));
/*     */ 
/*     */         
/* 426 */         if (n == moistCal.size() - 1) {
/* 427 */           endday.setText(Integer.toString(moistCal.size()));
/*     */         }
/*     */         
/* 430 */         blockToAdd.addContent((Content)beginday);
/* 431 */         blockToAdd.addContent((Content)endday);
/* 432 */         moistCalElement.addContent((Content)blockToAdd);
/*     */         
/* 434 */         lastVal = thisVal;
/* 435 */         lastPos = n;
/*     */       } 
/*     */     } 
/* 438 */     calendars.addContent((Content)moistCalElement);
/*     */     
/* 440 */     output.addContent((Content)smrclass);
/* 441 */     output.addContent((Content)strclass);
/* 442 */     output.addContent((Content)subdiv);
/* 443 */     output.addContent((Content)awb);
/* 444 */     output.addContent((Content)swb);
/* 445 */     output.addContent((Content)smcstates);
/* 446 */     output.addContent((Content)pets);
/*     */     
/* 448 */     output.addContent((Content)calendars);
/*     */ 
/*     */     
/* 451 */     model.addContent((Content)metadata);
/* 452 */     model.addContent((Content)input);
/* 453 */     model.addContent((Content)output);
/*     */ 
/*     */     
/* 456 */     XMLOutputter outputter = new XMLOutputter();
/* 457 */     outputter.setFormat(Format.getPrettyFormat());
/* 458 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*     */     try {
/* 460 */       outputter.output(doc, baos);
/* 461 */     } catch (IOException ex) {
/* 462 */       return "IOException encountered: " + ex.toString();
/*     */     } 
/* 464 */     return baos.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static double round(double d, int decimalPlaces) {
/* 469 */     String format = "#.#";
/* 470 */     for (int i = decimalPlaces - 1; i > 0; i--) {
/* 471 */       format = format + "#";
/*     */     }
/* 473 */     DecimalFormat df = new DecimalFormat(format);
/* 474 */     return Double.valueOf(df.format(d)).doubleValue();
/*     */   }
/*     */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/psu/newhall/util/XMLStringResultsExporter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */