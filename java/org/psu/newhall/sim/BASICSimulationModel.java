/*      */ package org.psu.newhall.sim;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BASICSimulationModel
/*      */ {
/*      */   public static NewhallResults runSimulation(NewhallDataset dataset, double waterHoldingCapacity) {
/*   16 */     return runSimulation(dataset, waterHoldingCapacity, 2.5D, 0.66D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static NewhallResults runSimulation(NewhallDataset dataset, double waterHoldingCapacity, double fc, double fcd) {
/*   24 */     boolean hasRunAlready = false;
/*      */ 
/*      */     
/*   27 */     double elevation = dataset.getElevation();
/*   28 */     if (!dataset.isMetric())
/*      */     {
/*   30 */       elevation *= 0.305D;
/*      */     }
/*      */ 
/*      */     
/*   34 */     double[] temperature = new double[13];
/*   35 */     double[] precip = new double[13];
/*   36 */     for (int i = 1; i <= 12; i++) {
/*   37 */       temperature[i] = ((Double)dataset.getTemperature().get(i - 1)).doubleValue();
/*   38 */       precip[i] = ((Double)dataset.getPrecipitation().get(i - 1)).doubleValue();
/*      */     } 
/*   40 */     if (!dataset.isMetric()) {
/*   41 */       double cv = 0.5555555555555556D;
/*   42 */       for (int i19 = 1; i19 <= 12; i19++) {
/*      */         
/*   44 */         temperature[i19] = cv * (temperature[i19] - 32.0D);
/*      */         
/*   46 */         precip[i19] = ((Double)dataset.getPrecipitation().get(i19 - 1)).doubleValue() * 25.4D;
/*      */       } 
/*      */     } 
/*      */     
/*   50 */     double[] upe = new double[13];
/*   51 */     double[] mpe = new double[13];
/*   52 */     double[] mwi = new double[13];
/*      */ 
/*      */ 
/*      */     
/*   56 */     for (int j = 1; j <= 12; j++) {
/*   57 */       if (temperature[j] > 0.0D) {
/*   58 */         double mwiValue = Math.pow(temperature[j] / 5.0D, 1.514D);
/*   59 */         mwi[j] = mwiValue;
/*      */       } 
/*      */     } 
/*      */     
/*   63 */     double swi = 0.0D; double[] arrayOfDouble1; int m; byte b;
/*   64 */     for (arrayOfDouble1 = mwi, m = arrayOfDouble1.length, b = 0; b < m; ) { Double mwiElement = Double.valueOf(arrayOfDouble1[b]);
/*   65 */       swi += mwiElement.doubleValue();
/*      */       
/*      */       b++; }
/*      */ 
/*      */     
/*   70 */     double a = 6.75E-7D * swi * swi * swi - 7.71E-5D * swi * swi + 0.01792D * swi + 0.49239D;
/*      */ 
/*      */ 
/*      */     
/*   74 */     for (int i1 = 1; i1 <= 12; i1++) {
/*   75 */       if (temperature[i1] > 0.0D) {
/*   76 */         if (temperature[i1] < 26.5D) {
/*   77 */           double aBase = 10.0D * temperature[i1] / swi;
/*   78 */           upe[i1] = 16.0D * Math.pow(aBase, a);
/*   79 */         } else if (temperature[i1] >= 38.0D) {
/*   80 */           upe[i1] = 185.0D;
/*      */         } else {
/*   82 */           double[] zt = BASICSimulationModelConstants.zt;
/*   83 */           double[] zpe = BASICSimulationModelConstants.zpe;
/*   84 */           int kl = 0;
/*   85 */           int kk = 0;
/*   86 */           for (int ki = 1; ki <= 24; ki++) {
/*   87 */             kl = ki + 1;
/*   88 */             kk = ki;
/*   89 */             if (temperature[i1] >= zt[ki - 1] && temperature[i1] < zt[kl - 1]) {
/*   90 */               upe[i1] = zpe[kk - 1];
/*      */ 
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  100 */     if (dataset.getNsHemisphere() == 'N') {
/*      */       
/*  102 */       int nrow = 0; int i19;
/*  103 */       for (i19 = 1; i19 <= 31 && 
/*  104 */         dataset.getLatitude() >= BASICSimulationModelConstants.rn[i19 - 1]; i19++)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  109 */         nrow++;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  114 */       for (i19 = 1; i19 <= 12; i19++) {
/*  115 */         if (upe[i19] > 0.0D)
/*      */         {
/*      */ 
/*      */ 
/*      */           
/*  120 */           mpe[i19] = upe[i19] * BASICSimulationModelConstants.inz[i19 - 1][nrow - 1];
/*      */         }
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  127 */       int nrow = 0; int i19;
/*  128 */       for (i19 = 1; i19 <= 13 && 
/*  129 */         dataset.getLatitude() >= BASICSimulationModelConstants.rs[i19 - 1]; i19++)
/*      */       {
/*      */         
/*  132 */         nrow++;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  138 */       if (nrow != 0) {
/*      */         
/*  140 */         for (i19 = 1; i19 <= 12; i19++) {
/*  141 */           if (upe[i19] > 0.0D)
/*      */           {
/*      */             
/*  144 */             if (nrow >= 13 || BASICSimulationModelConstants.fs[i19 - 1][nrow - 1] == BASICSimulationModelConstants.fs[i19 - 1][nrow]) {
/*      */ 
/*      */               
/*  147 */               double cf = BASICSimulationModelConstants.fs[i19 - 1][nrow - 1];
/*  148 */               mpe[i19] = upe[i19] * cf;
/*      */ 
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */               
/*  155 */               double cf = (BASICSimulationModelConstants.fs[i19 - 1][nrow] - BASICSimulationModelConstants.fs[i19 - 1][nrow - 1]) * ((dataset.getLatitudeDegrees() - BASICSimulationModelConstants.rs[nrow - 1]) * 60.0D + dataset.getLatitudeMinutes()) / (BASICSimulationModelConstants.rs[nrow] - BASICSimulationModelConstants.rs[nrow - 1]) * 60.0D;
/*      */               
/*  157 */               cf += BASICSimulationModelConstants.fs[i19 - 1][nrow - 1];
/*      */               
/*  159 */               mpe[i19] = upe[i19] * cf;
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } else {
/*      */         
/*  165 */         nrow = 1;
/*  166 */         for (i19 = 1; i19 <= 12; i19++) {
/*  167 */           if (upe[i19] > 0.0D) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  172 */             double cf = (BASICSimulationModelConstants.fs[i19 - 1][0] - BASICSimulationModelConstants.inz[i19 - 1][0]) * ((dataset.getLatitudeDegrees() * 60) + dataset.getLatitudeMinutes()) / 300.0D;
/*      */             
/*  174 */             cf += BASICSimulationModelConstants.inz[i19 - 1][0];
/*  175 */             mpe[i19] = upe[i19] * cf;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  184 */     double arf = 0.0D;
/*  185 */     double aev = 0.0D;
/*  186 */     for (int i2 = 1; i2 <= 12; i2++) {
/*  187 */       arf += precip[i2];
/*  188 */       aev += mpe[i2];
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  193 */     double sumt = 0.0D;
/*  194 */     for (int i3 = 1; i3 <= 12; i3++) {
/*  195 */       sumt += temperature[i3];
/*      */     }
/*      */     
/*  198 */     double tma = sumt / 12.0D + fc;
/*  199 */     double at1 = (temperature[6] + temperature[7] + temperature[8]) / 3.0D + fc;
/*      */     
/*  201 */     double at2 = (temperature[1] + temperature[2] + temperature[12]) / 3.0D + fc;
/*      */ 
/*      */     
/*  204 */     double st = 0.0D;
/*  205 */     double wt = 0.0D;
/*  206 */     if (dataset.getNsHemisphere() == 'N') {
/*  207 */       st = at1;
/*  208 */       wt = at2;
/*      */     } else {
/*  210 */       st = at2;
/*  211 */       wt = at1;
/*      */     } 
/*      */     
/*  214 */     double dif = Math.abs(at1 - at2);
/*  215 */     double cs = dif * (1.0D - fcd) / 2.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  225 */     boolean[] cr = new boolean[13];
/*  226 */     boolean[] reg = new boolean[13];
/*  227 */     cr[1] = (tma < 0.0D);
/*  228 */     cr[2] = (0.0D <= tma && tma < 8.0D);
/*  229 */     cr[3] = (st - cs < 15.0D);
/*  230 */     cr[7] = (dif * fcd > 5.0D);
/*  231 */     cr[8] = (tma >= 8.0D && tma < 15.0D);
/*  232 */     cr[9] = (tma >= 15.0D && tma < 22.0D);
/*  233 */     cr[10] = (tma >= 22.0D);
/*  234 */     cr[11] = (tma < 8.0D);
/*  235 */     reg[1] = cr[1];
/*  236 */     reg[2] = (cr[2] && cr[3]);
/*  237 */     reg[3] = (cr[11] && !cr[3] && cr[7]);
/*  238 */     reg[4] = (cr[8] && cr[7]);
/*  239 */     reg[5] = (cr[9] && cr[7]);
/*  240 */     reg[6] = (cr[10] && cr[7]);
/*  241 */     reg[7] = (cr[11] && !cr[7] && !cr[3]);
/*  242 */     reg[8] = (cr[8] && !cr[7]);
/*  243 */     reg[9] = (cr[9] && !cr[7]);
/*  244 */     reg[10] = (cr[10] && !cr[7]);
/*      */     
/*  246 */     st -= cs;
/*  247 */     wt += cs;
/*  248 */     dif = st - wt;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  253 */     String trr = "";
/*  254 */     for (int i4 = 1; i4 <= 10; i4++) {
/*  255 */       if (reg[i4]) {
/*  256 */         trr = BASICSimulationModelConstants.tempRegimes[i4 - 1];
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  278 */     double whc = waterHoldingCapacity;
/*  279 */     double fsl = whc / 64.0D;
/*  280 */     double[] sl = new double[65];
/*  281 */     for (int i5 = 0; i5 < sl.length; i5++) {
/*  282 */       sl[i5] = 0.0D;
/*      */     }
/*      */     
/*  285 */     int k = 1;
/*  286 */     int swst = 0;
/*  287 */     int swfi = 0;
/*      */     
/*  289 */     double[] ntwi = new double[4];
/*  290 */     double[] ntsu = new double[4];
/*  291 */     double[] nsd = new double[4];
/*  292 */     double[] nzd = new double[4];
/*  293 */     double[] nd = new double[4];
/*  294 */     boolean[] cc = new boolean[4];
/*  295 */     for (int i6 = 1; i6 <= 3; i6++) {
/*  296 */       ntwi[i6] = 0.0D;
/*  297 */       ntsu[i6] = 0.0D;
/*  298 */       nsd[i6] = 0.0D;
/*  299 */       nzd[i6] = 0.0D;
/*  300 */       nd[i6] = 0.0D;
/*  301 */       cc[i6] = false;
/*      */     } 
/*      */     
/*  304 */     double[] cd = new double[6];
/*  305 */     for (int i7 = 1; i7 <= 5; i7++) {
/*  306 */       cd[i7] = 0.0D;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  311 */     int msw = -1;
/*  312 */     int sib = 0;
/*  313 */     double sir = 0.0D;
/*  314 */     int x = 0;
/*  315 */     boolean swm = false;
/*  316 */     int max = 0;
/*  317 */     int icon = 0;
/*  318 */     double lt5c = 0.0D;
/*  319 */     int lt8c = 0;
/*  320 */     int ie = 0;
/*  321 */     int ib = 1;
/*  322 */     double prmo = 0.0D;
/*  323 */     int nccd = 0;
/*  324 */     int nccm = 0;
/*  325 */     int id8c = 0;
/*  326 */     double id5c = 0.0D;
/*  327 */     int swt = 0;
/*  328 */     int tc = 0;
/*  329 */     int np = 0;
/*  330 */     int np8 = 0;
/*  331 */     int ncsm = 0;
/*  332 */     int ncwm = 0;
/*  333 */     int ncsp = 0;
/*  334 */     int ncwp = 0;
/*      */ 
/*      */ 
/*      */     
/*  338 */     double[] nbd = new double[7];
/*  339 */     double[] ned = new double[7];
/*  340 */     double[] nbd8 = new double[7];
/*  341 */     double[] ned8 = new double[7];
/*  342 */     for (int i8 = 1; i8 <= 6; i8++) {
/*  343 */       nbd[i8] = 0.0D;
/*  344 */       ned[i8] = 0.0D;
/*  345 */       nbd8[i8] = 0.0D;
/*  346 */       ned8[i8] = 0.0D;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  351 */     int[] iday = new int[361];
/*  352 */     for (int i9 = 1; i9 <= 360; i9++) {
/*  353 */       iday[i9] = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  358 */     int swp = -1;
/*  359 */     int gogr = 0;
/*      */ 
/*      */ 
/*      */     
/*  363 */     boolean noMpeGreaterThanPrecip = true;
/*  364 */     for (int i11 = 1; i11 <= 12; i11++) {
/*  365 */       if (mpe[i11] > precip[i11]) {
/*  366 */         noMpeGreaterThanPrecip = false;
/*      */         break;
/*      */       } 
/*      */     } 
/*  370 */     if (noMpeGreaterThanPrecip) {
/*  371 */       cd[5] = -1.0D;
/*  372 */       swt = -1;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  377 */     for (int n = 1; n <= 10; n++) {
/*  378 */       for (int i19 = 1; i19 <= 12; i19++) {
/*      */ 
/*      */ 
/*      */         
/*  382 */         int zsw = 0;
/*  383 */         double lp = precip[i19] / 2.0D;
/*  384 */         double npe = (lp - mpe[i19]) / 2.0D;
/*  385 */         if (npe <= 0.0D) {
/*      */           
/*  387 */           npe = 0.0D - npe;
/*      */         } else {
/*  389 */           zsw = -1;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  394 */         for (int i20 = 1; i20 <= 64; i20++) {
/*  395 */           if (zsw == 0) {
/*      */             
/*  397 */             int nr = BASICSimulationModelConstants.dp[i20 - 1];
/*  398 */             if (sl[nr] > 0.0D)
/*      */             {
/*      */ 
/*      */ 
/*      */               
/*  403 */               double rpe = sl[nr] * BASICSimulationModelConstants.dr[i20 - 1];
/*  404 */               if (npe <= rpe) {
/*      */                 
/*  406 */                 sl[nr] = sl[nr] - npe / BASICSimulationModelConstants.dr[i20 - 1];
/*  407 */                 npe = 0.0D;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */               
/*  412 */               sl[nr] = 0.0D;
/*  413 */               npe -= rpe;
/*      */ 
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*  420 */           else if (sl[i20] < fsl) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  425 */             double esl = fsl - sl[i20];
/*  426 */             if (esl >= npe) {
/*      */               
/*  428 */               sl[i20] = sl[i20] + npe;
/*      */               
/*      */               break;
/*      */             } 
/*      */             
/*  433 */             sl[i20] = fsl;
/*  434 */             npe -= esl;
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  446 */         double hp = precip[i19] / 2.0D; int i21;
/*  447 */         for (i21 = 1; i21 <= 64; i21++) {
/*  448 */           if (sl[i21] < fsl) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  453 */             double esl = fsl - sl[i21];
/*  454 */             if (esl >= hp) {
/*      */               
/*  456 */               sl[i21] = sl[i21] + hp;
/*  457 */               if (gogr != 0);
/*      */ 
/*      */               
/*      */               break;
/*      */             } 
/*      */ 
/*      */             
/*  464 */             sl[i21] = fsl;
/*  465 */             hp -= esl;
/*  466 */             if (gogr != 0);
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  477 */         zsw = 0;
/*  478 */         lp = precip[i19] / 2.0D;
/*  479 */         npe = (lp - mpe[i19]) / 2.0D;
/*  480 */         if (npe <= 0.0D) {
/*      */           
/*  482 */           npe = 0.0D - npe;
/*      */         } else {
/*  484 */           zsw = -1;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  489 */         for (i21 = 1; i21 <= 64; i21++) {
/*  490 */           if (zsw == 0) {
/*      */             
/*  492 */             int nr = BASICSimulationModelConstants.dp[i21 - 1];
/*  493 */             if (sl[nr] > 0.0D)
/*      */             {
/*      */ 
/*      */ 
/*      */               
/*  498 */               double rpe = sl[nr] * BASICSimulationModelConstants.dr[i21 - 1];
/*  499 */               if (npe <= rpe) {
/*      */                 
/*  501 */                 sl[nr] = sl[nr] - npe / BASICSimulationModelConstants.dr[i21 - 1];
/*  502 */                 npe = 0.0D;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */               
/*  507 */               sl[nr] = 0.0D;
/*  508 */               npe -= rpe;
/*      */ 
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*  515 */           else if (sl[i21] < fsl) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  520 */             double esl = fsl - sl[i21];
/*  521 */             if (esl >= npe) {
/*      */               
/*  523 */               sl[i21] = sl[i21] + npe;
/*      */               
/*      */               break;
/*      */             } 
/*      */             
/*  528 */             sl[i21] = fsl;
/*  529 */             npe -= esl;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  545 */       double tmoi = 0.0D;
/*  546 */       for (int it = 1; it <= 64; it++) {
/*  547 */         tmoi += sl[it];
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  552 */       if (Math.abs(tmoi - prmo) < prmo / 100.0D) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/*  557 */       prmo = tmoi;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  565 */     gogr = -1;
/*      */ 
/*      */ 
/*      */     
/*  569 */     for (int i10 = 1; i10 <= 3; i10++) {
/*  570 */       cc[i10] = false;
/*      */     }
/*      */     
/*  573 */     boolean[] pc = new boolean[7]; int i12;
/*  574 */     for (i12 = 1; i12 <= 6; i12++) {
/*  575 */       pc[i12] = false;
/*      */     }
/*      */     
/*  578 */     pc[1] = (sl[9] <= 0.0D);
/*  579 */     pc[2] = (sl[17] <= 0.0D);
/*  580 */     pc[3] = (sl[25] <= 0.0D);
/*  581 */     cc[1] = (pc[1] && pc[2] && pc[3]);
/*  582 */     cc[2] = (!cc[1] && (pc[1] || pc[2] || pc[3]));
/*  583 */     pc[4] = (sl[9] > 0.0D);
/*  584 */     pc[5] = (sl[17] > 0.0D);
/*  585 */     pc[6] = (sl[25] > 0.0D);
/*  586 */     cc[3] = (pc[4] && pc[5] && pc[6]);
/*      */     
/*  588 */     for (i12 = 1; i12 <= 3; ) {
/*  589 */       if (!cc[i12]) {
/*      */         i12++; continue;
/*      */       } 
/*  592 */       k = i12;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  605 */     for (int im = 1; im <= 12; im++) {
/*      */ 
/*      */ 
/*      */       
/*  609 */       int[] dmc = new int[4];
/*      */       
/*  611 */       for (int i19 = 1; i19 <= 3; i19++) {
/*  612 */         dmc[i19] = 0;
/*  613 */         cc[i19] = false;
/*      */       } 
/*      */       
/*  616 */       int zsw = 0;
/*  617 */       int dpmc = 0;
/*  618 */       int pmc = k;
/*  619 */       int igmc = 0;
/*      */       
/*  621 */       double lp = precip[im] / 2.0D;
/*  622 */       double npe = (lp - mpe[im]) / 2.0D;
/*  623 */       double cnpe = 0.0D;
/*  624 */       boolean skipi3Loop = false;
/*      */       
/*  626 */       if (npe < 0.0D) {
/*  627 */         npe = -npe;
/*  628 */         cnpe = npe;
/*  629 */       } else if (npe == 0.0D) {
/*      */         
/*  631 */         skipi3Loop = true;
/*      */       } else {
/*  633 */         zsw = -1;
/*  634 */         cnpe = npe;
/*      */       } 
/*      */ 
/*      */       
/*  638 */       if (!skipi3Loop) {
/*  639 */         for (int i23 = 1; i23 <= 64; i23++) {
/*  640 */           if (zsw == 0) {
/*      */             
/*  642 */             if (npe <= 0.0D) {
/*      */               break;
/*      */             }
/*      */ 
/*      */             
/*  647 */             int nr = BASICSimulationModelConstants.dp[i23 - 1];
/*  648 */             if (sl[nr] > 0.0D)
/*      */             {
/*      */ 
/*      */ 
/*      */               
/*  653 */               double rpd = sl[nr] * BASICSimulationModelConstants.dr[i23 - 1];
/*  654 */               if (npe <= rpd) {
/*      */                 
/*  656 */                 sl[nr] = sl[nr] - npe / BASICSimulationModelConstants.dr[i23 - 1];
/*  657 */                 npe = 0.0D;
/*      */               }
/*      */               else {
/*      */                 
/*  661 */                 sl[nr] = 0.0D;
/*  662 */                 npe -= rpd;
/*      */               } 
/*      */               
/*      */               int i24;
/*      */               
/*  667 */               for (i24 = 1; i24 <= 3; i24++) {
/*  668 */                 cc[i24] = false;
/*      */               }
/*      */               
/*  671 */               for (i24 = 1; i24 <= 6; i24++) {
/*  672 */                 pc[i24] = false;
/*      */               }
/*      */               
/*  675 */               pc[1] = (sl[9] <= 0.0D);
/*  676 */               pc[2] = (sl[17] <= 0.0D);
/*  677 */               pc[3] = (sl[25] <= 0.0D);
/*  678 */               cc[1] = (pc[1] && pc[2] && pc[3]);
/*  679 */               cc[2] = (!cc[1] && (pc[1] || pc[2] || pc[3]));
/*  680 */               pc[4] = (sl[9] > 0.0D);
/*  681 */               pc[5] = (sl[17] > 0.0D);
/*  682 */               pc[6] = (sl[25] > 0.0D);
/*  683 */               cc[3] = (pc[4] && pc[5] && pc[6]);
/*      */               
/*  685 */               for (i24 = 1; i24 <= 3; ) {
/*  686 */                 if (!cc[i24]) {
/*      */                   i24++; continue;
/*      */                 } 
/*  689 */                 k = i24;
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  696 */               if (zsw != 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  706 */               int kk = k;
/*  707 */               k = pmc;
/*  708 */               if (kk != pmc)
/*      */               {
/*      */ 
/*      */ 
/*      */                 
/*  713 */                 if (npe <= 0.0D) {
/*      */                   break;
/*      */                 }
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  720 */                 double rpe = cnpe - npe;
/*  721 */                 dmc[k] = (int)(15.0D * rpe / cnpe) - dpmc;
/*  722 */                 igmc = dmc[k];
/*  723 */                 dpmc = dmc[k] + dpmc;
/*  724 */                 dmc[k] = 0;
/*      */ 
/*      */ 
/*      */                 
/*  728 */                 int i25 = 0;
/*  729 */                 int i26 = 0;
/*  730 */                 ie += igmc;
/*  731 */                 for (int i27 = ib; i27 <= ie; i27++) {
/*  732 */                   iday[i27] = k;
/*      */                   
/*  734 */                   if (i27 > 30) {
/*  735 */                     i25 = i27 % 30 - 1;
/*      */                   } else {
/*  737 */                     i25 = i27;
/*      */                   } 
/*      */                   
/*  740 */                   i26 = i27 / 30;
/*  741 */                   if (i27 % 30 == 0) {
/*  742 */                     i26--;
/*      */                   }
/*      */                   
/*  745 */                   if (i25 == -1) {
/*  746 */                     i25 = 29;
/*      */                   }
/*      */                   
/*  749 */                   if (i26 < 0) {
/*  750 */                     i26 = 0;
/*      */                   }
/*      */                 } 
/*      */ 
/*      */                 
/*  755 */                 ib = ie + 1;
/*  756 */                 nd[k] = nd[k] + igmc;
/*      */ 
/*      */                 
/*  759 */                 pmc = kk;
/*  760 */                 k = kk;
/*      */               }
/*      */             
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/*  767 */             if (npe <= 0.0D) {
/*      */               break;
/*      */             }
/*      */ 
/*      */             
/*  772 */             if (sl[i23] < fsl) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  778 */               double esl = fsl - sl[i23];
/*  779 */               if (esl >= npe) {
/*      */                 
/*  781 */                 sl[i23] = sl[i23] + npe;
/*  782 */                 npe = 0.0D;
/*      */               } else {
/*      */                 
/*  785 */                 sl[i23] = fsl;
/*      */                 
/*  787 */                 npe -= esl;
/*      */               } 
/*      */ 
/*      */               
/*      */               int i24;
/*      */               
/*  793 */               for (i24 = 1; i24 <= 3; i24++) {
/*  794 */                 cc[i24] = false;
/*      */               }
/*      */               
/*  797 */               for (i24 = 1; i24 <= 6; i24++) {
/*  798 */                 pc[i24] = false;
/*      */               }
/*      */               
/*  801 */               pc[1] = (sl[9] <= 0.0D);
/*  802 */               pc[2] = (sl[17] <= 0.0D);
/*  803 */               pc[3] = (sl[25] <= 0.0D);
/*  804 */               cc[1] = (pc[1] && pc[2] && pc[3]);
/*  805 */               cc[2] = (!cc[1] && (pc[1] || pc[2] || pc[3]));
/*  806 */               pc[4] = (sl[9] > 0.0D);
/*  807 */               pc[5] = (sl[17] > 0.0D);
/*  808 */               pc[6] = (sl[25] > 0.0D);
/*  809 */               cc[3] = (pc[4] && pc[5] && pc[6]);
/*      */               
/*  811 */               for (i24 = 1; i24 <= 3; ) {
/*  812 */                 if (!cc[i24]) {
/*      */                   i24++; continue;
/*      */                 } 
/*  815 */                 k = i24;
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  822 */               if (zsw != 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  832 */               int kk = k;
/*  833 */               k = pmc;
/*  834 */               if (kk != pmc) {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  839 */                 if (npe <= 0.0D) {
/*      */                   break;
/*      */                 }
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  846 */                 double rpe = cnpe - npe;
/*  847 */                 dmc[k] = (int)(15.0D * rpe / cnpe) - dpmc;
/*  848 */                 igmc = dmc[k];
/*  849 */                 dpmc = dmc[k] + dpmc;
/*  850 */                 dmc[k] = 0;
/*      */ 
/*      */ 
/*      */                 
/*  854 */                 int i25 = 0;
/*  855 */                 int i26 = 0;
/*  856 */                 ie += igmc;
/*  857 */                 for (int i27 = ib; i27 <= ie; i27++) {
/*  858 */                   iday[i27] = k;
/*      */                   
/*  860 */                   if (i27 > 30) {
/*  861 */                     i25 = i27 % 30 - 1;
/*      */                   } else {
/*  863 */                     i25 = i27;
/*      */                   } 
/*      */                   
/*  866 */                   i26 = i27 / 30;
/*  867 */                   if (i27 % 30 == 0) {
/*  868 */                     i26--;
/*      */                   }
/*      */                   
/*  871 */                   if (i25 == -1) {
/*  872 */                     i25 = 29;
/*      */                   }
/*      */                   
/*  875 */                   if (i26 < 0) {
/*  876 */                     i26 = 0;
/*      */                   }
/*      */                 } 
/*      */ 
/*      */                 
/*  881 */                 ib = ie + 1;
/*  882 */                 nd[k] = nd[k] + igmc;
/*      */ 
/*      */                 
/*  885 */                 pmc = kk;
/*  886 */                 k = kk;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  896 */       dmc[k] = 15 - dpmc;
/*  897 */       igmc = dmc[k];
/*  898 */       dmc[k] = 0;
/*      */ 
/*      */ 
/*      */       
/*  902 */       int ii = 0;
/*  903 */       int mm = 0;
/*  904 */       ie += igmc;
/*  905 */       for (int i20 = ib; i20 <= ie; i20++) {
/*  906 */         iday[i20] = k;
/*      */         
/*  908 */         if (i20 > 30) {
/*  909 */           ii = i20 % 30 - 1;
/*      */         } else {
/*  911 */           ii = i20;
/*      */         } 
/*      */         
/*  914 */         mm = i20 / 30;
/*  915 */         if (i20 % 30 == 0) {
/*  916 */           mm--;
/*      */         }
/*      */         
/*  919 */         if (ii == -1) {
/*  920 */           ii = 29;
/*      */         }
/*      */         
/*  923 */         if (mm < 0) {
/*  924 */           mm = 0;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  929 */       ib = ie + 1;
/*  930 */       nd[k] = nd[k] + igmc;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  937 */       double hp = precip[im] / 2.0D;
/*  938 */       for (int i22 = 1; i22 <= 64; i22++) {
/*  939 */         if (sl[i22] < fsl) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  944 */           double esl = fsl - sl[i22];
/*  945 */           if (esl >= hp) {
/*      */             
/*  947 */             sl[i22] = sl[i22] + hp;
/*  948 */             if (gogr != 0);
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/*      */ 
/*      */           
/*  955 */           sl[i22] = fsl;
/*  956 */           hp -= esl;
/*  957 */           if (gogr != 0);
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       int i21;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  970 */       for (i21 = 1; i21 <= 3; i21++) {
/*  971 */         cc[i21] = false;
/*      */       }
/*      */       
/*  974 */       for (i21 = 1; i21 <= 6; i21++) {
/*  975 */         pc[i21] = false;
/*      */       }
/*      */       
/*  978 */       pc[1] = (sl[9] <= 0.0D);
/*  979 */       pc[2] = (sl[17] <= 0.0D);
/*  980 */       pc[3] = (sl[25] <= 0.0D);
/*  981 */       cc[1] = (pc[1] && pc[2] && pc[3]);
/*  982 */       cc[2] = (!cc[1] && (pc[1] || pc[2] || pc[3]));
/*  983 */       pc[4] = (sl[9] > 0.0D);
/*  984 */       pc[5] = (sl[17] > 0.0D);
/*  985 */       pc[6] = (sl[25] > 0.0D);
/*  986 */       cc[3] = (pc[4] && pc[5] && pc[6]);
/*      */       
/*  988 */       for (i21 = 1; i21 <= 3; ) {
/*  989 */         if (!cc[i21]) {
/*      */           i21++; continue;
/*      */         } 
/*  992 */         k = i21;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  999 */       for (i21 = 1; i21 <= 3; i21++) {
/* 1000 */         dmc[i21] = 0;
/* 1001 */         cc[i21] = false;
/*      */       } 
/*      */       
/* 1004 */       zsw = 0;
/* 1005 */       dpmc = 0;
/* 1006 */       pmc = k;
/*      */       
/* 1008 */       lp = precip[im] / 2.0D;
/* 1009 */       npe = (lp - mpe[im]) / 2.0D;
/* 1010 */       cnpe = 0.0D;
/* 1011 */       skipi3Loop = false;
/*      */       
/* 1013 */       if (npe < 0.0D) {
/* 1014 */         npe = -npe;
/* 1015 */         cnpe = npe;
/* 1016 */       } else if (npe == 0.0D) {
/*      */         
/* 1018 */         skipi3Loop = true;
/*      */       } else {
/* 1020 */         zsw = -1;
/* 1021 */         cnpe = npe;
/*      */       } 
/*      */ 
/*      */       
/* 1025 */       if (!skipi3Loop) {
/* 1026 */         for (int i23 = 1; i23 <= 64; i23++) {
/* 1027 */           if (zsw == 0) {
/*      */             
/* 1029 */             if (npe <= 0.0D) {
/*      */               break;
/*      */             }
/*      */ 
/*      */             
/* 1034 */             int nr = BASICSimulationModelConstants.dp[i23 - 1];
/* 1035 */             if (sl[nr] > 0.0D)
/*      */             {
/*      */ 
/*      */ 
/*      */               
/* 1040 */               double rpd = sl[nr] * BASICSimulationModelConstants.dr[i23 - 1];
/* 1041 */               if (npe <= rpd) {
/*      */                 
/* 1043 */                 sl[nr] = sl[nr] - npe / BASICSimulationModelConstants.dr[i23 - 1];
/* 1044 */                 npe = 0.0D;
/*      */               }
/*      */               else {
/*      */                 
/* 1048 */                 sl[nr] = 0.0D;
/* 1049 */                 npe -= rpd;
/*      */               } 
/*      */               
/*      */               int i24;
/*      */               
/* 1054 */               for (i24 = 1; i24 <= 3; i24++) {
/* 1055 */                 cc[i24] = false;
/*      */               }
/*      */               
/* 1058 */               for (i24 = 1; i24 <= 6; i24++) {
/* 1059 */                 pc[i24] = false;
/*      */               }
/*      */               
/* 1062 */               pc[1] = (sl[9] <= 0.0D);
/* 1063 */               pc[2] = (sl[17] <= 0.0D);
/* 1064 */               pc[3] = (sl[25] <= 0.0D);
/* 1065 */               cc[1] = (pc[1] && pc[2] && pc[3]);
/* 1066 */               cc[2] = (!cc[1] && (pc[1] || pc[2] || pc[3]));
/* 1067 */               pc[4] = (sl[9] > 0.0D);
/* 1068 */               pc[5] = (sl[17] > 0.0D);
/* 1069 */               pc[6] = (sl[25] > 0.0D);
/* 1070 */               cc[3] = (pc[4] && pc[5] && pc[6]);
/*      */               
/* 1072 */               for (i24 = 1; i24 <= 3; ) {
/* 1073 */                 if (!cc[i24]) {
/*      */                   i24++; continue;
/*      */                 } 
/* 1076 */                 k = i24;
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1083 */               if (zsw != 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1093 */               int kk = k;
/* 1094 */               k = pmc;
/* 1095 */               if (kk != pmc)
/*      */               {
/*      */ 
/*      */ 
/*      */                 
/* 1100 */                 if (npe <= 0.0D) {
/*      */                   break;
/*      */                 }
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1107 */                 double rpe = cnpe - npe;
/* 1108 */                 dmc[k] = (int)(15.0D * rpe / cnpe) - dpmc;
/* 1109 */                 igmc = dmc[k];
/* 1110 */                 dpmc = dmc[k] + dpmc;
/* 1111 */                 dmc[k] = 0;
/*      */ 
/*      */ 
/*      */                 
/* 1115 */                 ii = 0;
/* 1116 */                 mm = 0;
/* 1117 */                 ie += igmc;
/* 1118 */                 for (int i25 = ib; i25 <= ie; i25++) {
/* 1119 */                   iday[i25] = k;
/*      */                   
/* 1121 */                   if (i25 > 30) {
/* 1122 */                     ii = i25 % 30 - 1;
/*      */                   } else {
/* 1124 */                     ii = i25;
/*      */                   } 
/*      */                   
/* 1127 */                   mm = i25 / 30;
/* 1128 */                   if (i25 % 30 == 0) {
/* 1129 */                     mm--;
/*      */                   }
/*      */                   
/* 1132 */                   if (ii == -1) {
/* 1133 */                     ii = 29;
/*      */                   }
/*      */                   
/* 1136 */                   if (mm < 0) {
/* 1137 */                     mm = 0;
/*      */                   }
/*      */                 } 
/*      */ 
/*      */                 
/* 1142 */                 ib = ie + 1;
/* 1143 */                 nd[k] = nd[k] + igmc;
/*      */ 
/*      */                 
/* 1146 */                 pmc = kk;
/* 1147 */                 k = kk;
/*      */               }
/*      */             
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/* 1154 */             if (npe <= 0.0D) {
/*      */               break;
/*      */             }
/*      */ 
/*      */             
/* 1159 */             if (sl[i23] < fsl) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1165 */               double esl = fsl - sl[i23];
/* 1166 */               if (esl >= npe) {
/*      */                 
/* 1168 */                 sl[i23] = sl[i23] + npe;
/* 1169 */                 npe = 0.0D;
/*      */               } else {
/*      */                 
/* 1172 */                 sl[i23] = fsl;
/*      */                 
/* 1174 */                 npe -= esl;
/*      */               } 
/*      */ 
/*      */               
/*      */               int i24;
/*      */               
/* 1180 */               for (i24 = 1; i24 <= 3; i24++) {
/* 1181 */                 cc[i24] = false;
/*      */               }
/*      */               
/* 1184 */               for (i24 = 1; i24 <= 6; i24++) {
/* 1185 */                 pc[i24] = false;
/*      */               }
/*      */               
/* 1188 */               pc[1] = (sl[9] <= 0.0D);
/* 1189 */               pc[2] = (sl[17] <= 0.0D);
/* 1190 */               pc[3] = (sl[25] <= 0.0D);
/* 1191 */               cc[1] = (pc[1] && pc[2] && pc[3]);
/* 1192 */               cc[2] = (!cc[1] && (pc[1] || pc[2] || pc[3]));
/* 1193 */               pc[4] = (sl[9] > 0.0D);
/* 1194 */               pc[5] = (sl[17] > 0.0D);
/* 1195 */               pc[6] = (sl[25] > 0.0D);
/* 1196 */               cc[3] = (pc[4] && pc[5] && pc[6]);
/*      */               
/* 1198 */               for (i24 = 1; i24 <= 3; ) {
/* 1199 */                 if (!cc[i24]) {
/*      */                   i24++; continue;
/*      */                 } 
/* 1202 */                 k = i24;
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1209 */               if (zsw != 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1219 */               int kk = k;
/* 1220 */               k = pmc;
/* 1221 */               if (kk != pmc) {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1226 */                 if (npe <= 0.0D) {
/*      */                   break;
/*      */                 }
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1233 */                 double rpe = cnpe - npe;
/* 1234 */                 dmc[k] = (int)(15.0D * rpe / cnpe) - dpmc;
/* 1235 */                 igmc = dmc[k];
/* 1236 */                 dpmc = dmc[k] + dpmc;
/* 1237 */                 dmc[k] = 0;
/*      */ 
/*      */ 
/*      */                 
/* 1241 */                 ii = 0;
/* 1242 */                 mm = 0;
/* 1243 */                 ie += igmc;
/* 1244 */                 for (int i25 = ib; i25 <= ie; i25++) {
/* 1245 */                   iday[i25] = k;
/*      */                   
/* 1247 */                   if (i25 > 30) {
/* 1248 */                     ii = i25 % 30 - 1;
/*      */                   } else {
/* 1250 */                     ii = i25;
/*      */                   } 
/*      */                   
/* 1253 */                   mm = i25 / 30;
/* 1254 */                   if (i25 % 30 == 0) {
/* 1255 */                     mm--;
/*      */                   }
/*      */                   
/* 1258 */                   if (ii == -1) {
/* 1259 */                     ii = 29;
/*      */                   }
/*      */                   
/* 1262 */                   if (mm < 0) {
/* 1263 */                     mm = 0;
/*      */                   }
/*      */                 } 
/*      */ 
/*      */                 
/* 1268 */                 ib = ie + 1;
/* 1269 */                 nd[k] = nd[k] + igmc;
/*      */ 
/*      */                 
/* 1272 */                 pmc = kk;
/* 1273 */                 k = kk;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1283 */       dmc[k] = 15 - dpmc;
/* 1284 */       igmc = dmc[k];
/* 1285 */       dmc[k] = 0;
/*      */ 
/*      */ 
/*      */       
/* 1289 */       ii = 0;
/* 1290 */       mm = 0;
/* 1291 */       ie += igmc;
/* 1292 */       for (i21 = ib; i21 <= ie; i21++) {
/* 1293 */         iday[i21] = k;
/*      */         
/* 1295 */         if (i21 > 30) {
/* 1296 */           ii = i21 % 30 - 1;
/*      */         } else {
/* 1298 */           ii = i21;
/*      */         } 
/*      */         
/* 1301 */         mm = i21 / 30;
/* 1302 */         if (i21 % 30 == 0) {
/* 1303 */           mm--;
/*      */         }
/*      */         
/* 1306 */         if (ii == -1) {
/* 1307 */           ii = 29;
/*      */         }
/*      */         
/* 1310 */         if (mm < 0) {
/* 1311 */           mm = 0;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1316 */       ib = ie + 1;
/* 1317 */       nd[k] = nd[k] + igmc;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1327 */     int sn = 0;
/* 1328 */     int kj = 0;
/* 1329 */     int ia = 0;
/* 1330 */     int iz = 0;
/* 1331 */     int[] nj = new int[14];
/* 1332 */     double crr = 0.0D;
/* 1333 */     boolean[] c = new boolean[15];
/* 1334 */     boolean tempUnderFive = false;
/* 1335 */     boolean zwt = false;
/*      */     
/* 1337 */     for (int i13 = 1; i13 <= 12; i13++) {
/*      */       
/* 1339 */       if (temperature[i13] < 5.0D) {
/*      */         
/* 1341 */         tempUnderFive = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1347 */     boolean skipTo890 = false;
/* 1348 */     double t13 = 0.0D;
/*      */     
/* 1350 */     if (tempUnderFive) {
/*      */       
/* 1352 */       t13 = temperature[1];
/* 1353 */       for (int it = 1; it <= 11; it++) {
/* 1354 */         if (temperature[it] == 5.0D && temperature[it + 1] == 5.0D) {
/* 1355 */           temperature[it] = 5.01D;
/*      */         }
/*      */       } 
/* 1358 */       if (temperature[12] == 5.0D && t13 == 5.0D) {
/* 1359 */         temperature[12] = 5.01D;
/*      */       }
/* 1361 */       crr = 5.0D;
/*      */       
/*      */       int i19;
/*      */       
/* 1365 */       for (i19 = 1; i19 <= 12; i19++) {
/* 1366 */         nj[i19] = 0;
/*      */       }
/*      */       
/* 1369 */       zwt = false;
/* 1370 */       kj = 1;
/* 1371 */       ia = 30;
/* 1372 */       iz = 30;
/*      */       
/* 1374 */       if (crr != 8.0D) {
/* 1375 */         ia = 36;
/* 1376 */         iz = 25;
/*      */       } 
/*      */       
/* 1379 */       for (i19 = 1; i19 <= 12; i19++) {
/* 1380 */         for (int i22 = 1; i22 <= 14; i22++) {
/* 1381 */           c[i22] = false;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1386 */         int m0 = i19 - 1;
/* 1387 */         int m1 = i19;
/* 1388 */         int m2 = i19 + 1;
/* 1389 */         if (m2 > 12) {
/* 1390 */           m2 -= 12;
/*      */         }
/* 1392 */         if (m0 < 1) {
/* 1393 */           m0 += 12;
/*      */         }
/*      */         
/* 1396 */         c[1] = (temperature[m1] < crr);
/* 1397 */         c[2] = (temperature[m2] > crr);
/* 1398 */         c[3] = (temperature[m0] < crr);
/* 1399 */         c[4] = (temperature[m1] == crr);
/* 1400 */         c[5] = (temperature[m2] > crr);
/* 1401 */         c[6] = (temperature[m2] < crr);
/* 1402 */         c[7] = (temperature[m1] > crr);
/* 1403 */         c[8] = (temperature[m0] > crr);
/* 1404 */         c[9] = (c[1] && c[2]);
/* 1405 */         c[10] = (c[3] && c[4] && c[5]);
/* 1406 */         c[11] = (c[9] || c[10]);
/* 1407 */         c[12] = (c[6] && c[7]);
/* 1408 */         c[13] = (c[8] && c[6] && c[4]);
/* 1409 */         c[14] = (c[12] || c[13]);
/*      */         
/* 1411 */         if (c[11]) {
/*      */           
/* 1413 */           nj[kj] = m0 * 30 + ia + (int)(30.0D * (crr - temperature[m1]) / (temperature[m2] - temperature[m1]));
/*      */           
/* 1415 */           if (nj[kj] > 360) {
/* 1416 */             nj[kj] = nj[kj] - 360;
/*      */           }
/* 1418 */           kj++;
/* 1419 */           zwt = true;
/*      */         
/*      */         }
/* 1422 */         else if (c[14]) {
/*      */           
/* 1424 */           nj[kj] = m0 * 30 + iz + (int)(30.0D * (temperature[m1] - crr) / (temperature[m1] - temperature[m2]));
/*      */           
/* 1426 */           if (nj[kj] > 360) {
/* 1427 */             nj[kj] = nj[kj] - 360;
/*      */           }
/* 1429 */           kj++;
/* 1430 */           zwt = false;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1441 */       if (zwt) {
/*      */         
/* 1443 */         int le = kj - 2;
/* 1444 */         int npro = nj[1];
/* 1445 */         for (int i22 = 1; i22 <= le; i22++) {
/* 1446 */           nj[i22] = nj[i22 + 1];
/*      */         }
/* 1448 */         nj[le + 1] = npro;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1453 */       int npj = (kj - 1) / 2;
/* 1454 */       int[] nbj = new int[7];
/* 1455 */       int[] nej = new int[7]; int i21;
/* 1456 */       for (i21 = 1; i21 <= npj; i21++) {
/* 1457 */         ib = 2 * i21 - 1;
/* 1458 */         ie = 2 * i21;
/* 1459 */         nbj[i21] = nj[ib];
/* 1460 */         nej[i21] = nj[ie];
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1465 */       for (i21 = 1; i21 <= 6; i21++) {
/* 1466 */         nbd[i21] = nbj[i21];
/* 1467 */         ned[i21] = nej[i21];
/*      */       } 
/*      */       
/* 1470 */       np = npj;
/* 1471 */       tc = -1;
/* 1472 */       int i20 = 0;
/* 1473 */       double ir = 0.0D;
/* 1474 */       double d1 = 0.0D;
/* 1475 */       double d2 = 0.0D;
/*      */       
/* 1477 */       if (np == 0) {
/*      */         
/* 1479 */         if (wt <= 5.0D)
/*      */         {
/*      */ 
/*      */           
/* 1483 */           lt5c = 0.0D;
/* 1484 */           nbd[1] = -1.0D;
/* 1485 */           id5c = 0.0D;
/* 1486 */           for (int ik = 1; ik <= 3; ik++) {
/* 1487 */             nsd[ik] = 0.0D;
/*      */           }
/* 1489 */           tc = 0;
/* 1490 */           skipTo890 = true;
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1495 */         for (int i22 = 1; i22 <= np; i22++) {
/* 1496 */           ib = (int)nbd[i22];
/* 1497 */           if (nbd[i22] < ned[i22]) {
/*      */             
/* 1499 */             ir = ned[i22] - nbd[i22] + 1.0D;
/* 1500 */             i20 = ib;
/* 1501 */             d1 = ir;
/*      */           }
/*      */           else {
/*      */             
/* 1505 */             ir = 361.0D - nbd[i22] + ned[i22];
/* 1506 */             i20 = ib;
/* 1507 */             d1 = ir;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1512 */           for (int ij = 1; ij <= 3; ij++) {
/* 1513 */             nzd[ij] = 0.0D;
/*      */           }
/*      */           
/* 1516 */           d2 = i20 + d1 - 1.0D;
/*      */           
/* 1518 */           for (int l = i20; l <= d2; l++) {
/* 1519 */             int i23 = l;
/* 1520 */             if (i23 > 360) {
/* 1521 */               i23 -= 360;
/*      */             }
/* 1523 */             int ik = iday[i23];
/* 1524 */             nzd[ik] = nzd[ik] + 1.0D;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1534 */           if (!hasRunAlready) {
/* 1535 */             for (int i23 = 1; i23 <= 3; i23++) {
/* 1536 */               nsd[i23] = nsd[i23] + nzd[i23];
/* 1537 */               hasRunAlready = true;
/*      */             } 
/*      */           }
/*      */           
/* 1541 */           lt5c += ir;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1547 */         id5c = nbd[1];
/* 1548 */         skipTo890 = true;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1560 */     if (!skipTo890) {
/* 1561 */       tc = 0;
/* 1562 */       for (int i19 = 1; i19 <= 3; i19++) {
/* 1563 */         nsd[i19] = nd[i19];
/*      */       }
/* 1565 */       lt5c = 360.0D;
/* 1566 */       id5c = 0.0D;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1571 */     int[] ncpm = new int[4];
/* 1572 */     for (int ic = 1; ic <= 2; ic++) {
/* 1573 */       ncpm[ic] = 0;
/*      */     }
/*      */     
/* 1576 */     boolean tempUnder8C = false;
/* 1577 */     for (int i14 = 1; i14 <= 12; i14++) {
/* 1578 */       if (temperature[i14] < 8.0D) {
/*      */         
/* 1580 */         tempUnder8C = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 1585 */     if (tempUnder8C) {
/*      */       
/* 1587 */       t13 = temperature[1];
/* 1588 */       for (int it = 1; it <= 11; it++) {
/* 1589 */         if (temperature[it] == 8.0D && temperature[it + 1] == 8.0D) {
/* 1590 */           temperature[it] = 8.01D;
/*      */         }
/*      */       } 
/*      */       
/* 1594 */       crr = 8.0D;
/* 1595 */       if (temperature[12] == 8.0D && t13 == 8.0D) {
/* 1596 */         temperature[12] = 8.01D;
/*      */       }
/*      */       
/*      */       int i19;
/*      */       
/* 1601 */       for (i19 = 1; i19 <= 12; i19++) {
/* 1602 */         nj[i19] = 0;
/*      */       }
/*      */       
/* 1605 */       zwt = false;
/* 1606 */       kj = 1;
/* 1607 */       ia = 30;
/* 1608 */       iz = 30;
/*      */       
/* 1610 */       if (crr != 8.0D) {
/* 1611 */         ia = 36;
/* 1612 */         iz = 25;
/*      */       } 
/*      */       
/* 1615 */       for (i19 = 1; i19 <= 12; i19++) {
/* 1616 */         for (int i21 = 1; i21 <= 14; i21++) {
/* 1617 */           c[i21] = false;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1622 */         int m0 = i19 - 1;
/* 1623 */         int m1 = i19;
/* 1624 */         int m2 = i19 + 1;
/* 1625 */         if (m2 > 12) {
/* 1626 */           m2 -= 12;
/*      */         }
/* 1628 */         if (m0 < 1) {
/* 1629 */           m0 += 12;
/*      */         }
/*      */         
/* 1632 */         c[1] = (temperature[m1] < crr);
/* 1633 */         c[2] = (temperature[m2] > crr);
/* 1634 */         c[3] = (temperature[m0] < crr);
/* 1635 */         c[4] = (temperature[m1] == crr);
/* 1636 */         c[5] = (temperature[m2] > crr);
/* 1637 */         c[6] = (temperature[m2] < crr);
/* 1638 */         c[7] = (temperature[m1] > crr);
/* 1639 */         c[8] = (temperature[m0] > crr);
/* 1640 */         c[9] = (c[1] && c[2]);
/* 1641 */         c[10] = (c[3] && c[4] && c[5]);
/* 1642 */         c[11] = (c[9] || c[10]);
/* 1643 */         c[12] = (c[6] && c[7]);
/* 1644 */         c[13] = (c[8] && c[6] && c[4]);
/* 1645 */         c[14] = (c[12] || c[13]);
/*      */         
/* 1647 */         if (c[11]) {
/*      */           
/* 1649 */           nj[kj] = m0 * 30 + ia + (int)(30.0D * (crr - temperature[m1]) / (temperature[m2] - temperature[m1]));
/*      */           
/* 1651 */           if (nj[kj] > 360) {
/* 1652 */             nj[kj] = nj[kj] - 360;
/*      */           }
/* 1654 */           kj++;
/* 1655 */           zwt = true;
/*      */         
/*      */         }
/* 1658 */         else if (c[14]) {
/*      */           
/* 1660 */           nj[kj] = m0 * 30 + iz + (int)(30.0D * (temperature[m1] - crr) / (temperature[m1] - temperature[m2]));
/*      */           
/* 1662 */           if (nj[kj] > 360) {
/* 1663 */             nj[kj] = nj[kj] - 360;
/*      */           }
/* 1665 */           kj++;
/* 1666 */           zwt = false;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1677 */       if (zwt) {
/*      */         
/* 1679 */         int le = kj - 2;
/* 1680 */         int npro = nj[1];
/* 1681 */         for (int i21 = 1; i21 <= le; i21++) {
/* 1682 */           nj[i21] = nj[i21 + 1];
/*      */         }
/* 1684 */         nj[le + 1] = npro;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1689 */       int npj = (kj - 1) / 2;
/* 1690 */       int[] nbj = new int[7];
/* 1691 */       int[] nej = new int[7]; int i20;
/* 1692 */       for (i20 = 1; i20 <= npj; i20++) {
/* 1693 */         ib = 2 * i20 - 1;
/* 1694 */         ie = 2 * i20;
/* 1695 */         nbj[i20] = nj[ib];
/* 1696 */         nej[i20] = nj[ie];
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1701 */       for (i20 = 1; i20 <= 6; i20++) {
/* 1702 */         nbd8[i20] = nbj[i20];
/* 1703 */         ned8[i20] = nej[i20];
/*      */       } 
/*      */       
/* 1706 */       np8 = npj;
/* 1707 */       tc = -1;
/*      */ 
/*      */ 
/*      */       
/* 1711 */       if (np8 != 0)
/*      */       {
/*      */ 
/*      */         
/* 1715 */         for (i20 = 1; i20 <= np8; i20++) {
/* 1716 */           ib = (int)nbd8[i20];
/* 1717 */           double ir = 0.0D;
/* 1718 */           if (nbd8[i20] < ned8[i20]) {
/*      */             
/* 1720 */             ir = ned8[i20] - nbd8[i20] + 1.0D;
/*      */           }
/*      */           else {
/*      */             
/* 1724 */             ir = 361.0D - nbd8[i20] + ned8[i20];
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1729 */           msw = 0;
/* 1730 */           sib = ib;
/* 1731 */           sir = ir;
/* 1732 */           x = 1;
/* 1733 */           swm = (msw != 0);
/*      */ 
/*      */ 
/*      */           
/* 1737 */           int[] arrayOfInt = new int[5];
/* 1738 */           arrayOfInt[x] = 0;
/* 1739 */           int i21 = 0;
/* 1740 */           int i22 = 0;
/* 1741 */           int i23 = 0;
/* 1742 */           max = 0;
/* 1743 */           double d1 = sib + sir - 1.0D;
/*      */           
/* 1745 */           for (int i24 = sib; i24 <= d1; i24++) {
/* 1746 */             int n1 = i24 + 1;
/* 1747 */             if (n1 > 360) {
/* 1748 */               n1 -= 360;
/*      */             }
/*      */             
/* 1751 */             if (i24 > 360) {
/* 1752 */               i23 = i24 - 360;
/*      */             } else {
/* 1754 */               i23 = i24;
/*      */             } 
/*      */             
/* 1757 */             if (swm) {
/*      */               
/* 1759 */               if (iday[i23] == x)
/*      */               {
/* 1761 */                 if (iday[i23] != iday[n1])
/*      */                 {
/* 1763 */                   if (i22 != 0)
/*      */                   {
/* 1765 */                     arrayOfInt[x] = arrayOfInt[x] + 1;
/* 1766 */                     if (arrayOfInt[x] > max)
/*      */                     {
/* 1768 */                       max = arrayOfInt[x];
/*      */                       
/* 1770 */                       arrayOfInt[x] = 0;
/* 1771 */                       i22 = 0;
/*      */                     
/*      */                     }
/*      */                     else
/*      */                     {
/* 1776 */                       arrayOfInt[x] = 0;
/* 1777 */                       i22 = 0;
/*      */                     
/*      */                     }
/*      */ 
/*      */                   
/*      */                   }
/*      */                 
/*      */                 }
/*      */                 else
/*      */                 {
/* 1787 */                   arrayOfInt[x] = arrayOfInt[x] + 1;
/* 1788 */                   i22 = -1;
/*      */ 
/*      */                 
/*      */                 }
/*      */ 
/*      */               
/*      */               }
/*      */             
/*      */             }
/* 1797 */             else if (iday[i23] != x) {
/*      */               
/* 1799 */               if (iday[n1] == x) {
/*      */                 
/* 1801 */                 if (i22 != 0)
/*      */                 {
/* 1803 */                   arrayOfInt[x] = arrayOfInt[x] + 1;
/* 1804 */                   if (arrayOfInt[x] > max)
/*      */                   {
/* 1806 */                     max = arrayOfInt[x];
/*      */                     
/* 1808 */                     arrayOfInt[x] = 0;
/* 1809 */                     i22 = 0;
/*      */                   
/*      */                   }
/*      */                   else
/*      */                   {
/* 1814 */                     arrayOfInt[x] = 0;
/* 1815 */                     i22 = 0;
/*      */                   
/*      */                   }
/*      */ 
/*      */                 
/*      */                 }
/*      */               
/*      */               }
/*      */               else {
/*      */                 
/* 1825 */                 arrayOfInt[x] = arrayOfInt[x] + 1;
/* 1826 */                 i22 = -1;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1837 */           if (i22 != 0) {
/* 1838 */             i21 = arrayOfInt[x];
/*      */           }
/*      */           
/* 1841 */           if (i21 > max) {
/* 1842 */             max = i21;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1847 */           icon = max;
/* 1848 */           if (ncpm[2] <= icon) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1853 */             ncpm[2] = icon;
/* 1854 */             lt8c = (int)ir;
/* 1855 */             id8c = (int)nbd8[i20];
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1861 */         sn = -1;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1866 */       msw = 0;
/*      */       
/* 1868 */       tc = -1;
/* 1869 */       lt8c = 360;
/* 1870 */       id8c = 0;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1876 */     sib = 1;
/* 1877 */     sir = 720.0D;
/* 1878 */     x = 1;
/* 1879 */     swm = (msw != 0);
/*      */ 
/*      */ 
/*      */     
/* 1883 */     int[] ns = new int[5];
/* 1884 */     ns[x] = 0;
/* 1885 */     int ifin = 0;
/* 1886 */     int sw = 0;
/* 1887 */     int si = 0;
/* 1888 */     max = 0;
/* 1889 */     double siz = sib + sir - 1.0D;
/*      */     
/* 1891 */     for (int i16 = sib; i16 <= siz; i16++) {
/* 1892 */       int n1 = i16 + 1;
/* 1893 */       while (n1 > 360) {
/* 1894 */         n1 -= 360;
/*      */       }
/*      */       
/* 1897 */       if (i16 > 360) {
/* 1898 */         si = i16 - 360;
/*      */       } else {
/* 1900 */         si = i16;
/*      */       } 
/*      */       
/* 1903 */       if (swm) {
/*      */         
/* 1905 */         if (iday[si] == x)
/*      */         {
/* 1907 */           if (iday[si] != iday[n1])
/*      */           {
/* 1909 */             if (sw != 0)
/*      */             {
/* 1911 */               ns[x] = ns[x] + 1;
/* 1912 */               if (ns[x] > max)
/*      */               {
/* 1914 */                 max = ns[x];
/*      */                 
/* 1916 */                 ns[x] = 0;
/* 1917 */                 sw = 0;
/*      */               
/*      */               }
/*      */               else
/*      */               {
/* 1922 */                 ns[x] = 0;
/* 1923 */                 sw = 0;
/*      */               
/*      */               }
/*      */ 
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */           else
/*      */           {
/* 1933 */             ns[x] = ns[x] + 1;
/* 1934 */             sw = -1;
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/*      */       
/*      */       }
/* 1943 */       else if (iday[si] != x) {
/*      */         
/* 1945 */         if (iday[n1] == x) {
/*      */           
/* 1947 */           if (sw != 0)
/*      */           {
/* 1949 */             ns[x] = ns[x] + 1;
/* 1950 */             if (ns[x] > max)
/*      */             {
/* 1952 */               max = ns[x];
/*      */               
/* 1954 */               ns[x] = 0;
/* 1955 */               sw = 0;
/*      */             
/*      */             }
/*      */             else
/*      */             {
/* 1960 */               ns[x] = 0;
/* 1961 */               sw = 0;
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1971 */           ns[x] = ns[x] + 1;
/* 1972 */           sw = -1;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1983 */     if (sw != 0) {
/* 1984 */       ifin = ns[x];
/*      */     }
/*      */     
/* 1987 */     if (ifin > max) {
/* 1988 */       max = ifin;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1993 */     icon = max;
/* 1994 */     if (icon > 360) {
/* 1995 */       icon = 360;
/*      */     }
/* 1997 */     ncpm[1] = icon;
/* 1998 */     if (sn == 0)
/*      */     {
/* 2000 */       ncpm[2] = ncpm[1];
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2005 */     sn = 0;
/* 2006 */     msw = -1;
/* 2007 */     int i15 = 0;
/*      */     
/* 2009 */     if (dataset.getNsHemisphere() == 'N') {
/* 2010 */       ib = 181;
/* 2011 */       i15 = 1;
/*      */     } else {
/* 2013 */       ib = 1;
/* 2014 */       i15 = 181;
/*      */     } 
/*      */     
/* 2017 */     sib = ib;
/* 2018 */     sir = 120.0D;
/* 2019 */     x = 1;
/* 2020 */     swm = (msw != 0);
/*      */ 
/*      */ 
/*      */     
/* 2024 */     ns = new int[5];
/* 2025 */     ns[x] = 0;
/* 2026 */     ifin = 0;
/* 2027 */     sw = 0;
/* 2028 */     si = 0;
/* 2029 */     max = 0;
/* 2030 */     siz = sib + sir - 1.0D;
/*      */     int i17;
/* 2032 */     for (i17 = sib; i17 <= siz; i17++) {
/* 2033 */       int n1 = i17 + 1;
/* 2034 */       if (n1 > 360) {
/* 2035 */         n1 -= 360;
/*      */       }
/*      */       
/* 2038 */       if (i17 > 360) {
/* 2039 */         si = i17 - 360;
/*      */       } else {
/* 2041 */         si = i17;
/*      */       } 
/*      */       
/* 2044 */       if (swm) {
/*      */         
/* 2046 */         if (iday[si] == x)
/*      */         {
/* 2048 */           if (iday[si] != iday[n1])
/*      */           {
/* 2050 */             if (sw != 0)
/*      */             {
/* 2052 */               ns[x] = ns[x] + 1;
/* 2053 */               if (ns[x] > max)
/*      */               {
/* 2055 */                 max = ns[x];
/*      */                 
/* 2057 */                 ns[x] = 0;
/* 2058 */                 sw = 0;
/*      */               
/*      */               }
/*      */               else
/*      */               {
/* 2063 */                 ns[x] = 0;
/* 2064 */                 sw = 0;
/*      */               
/*      */               }
/*      */ 
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */           else
/*      */           {
/* 2074 */             ns[x] = ns[x] + 1;
/* 2075 */             sw = -1;
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/*      */       
/*      */       }
/* 2084 */       else if (iday[si] != x) {
/*      */         
/* 2086 */         if (iday[n1] == x) {
/*      */           
/* 2088 */           if (sw != 0)
/*      */           {
/* 2090 */             ns[x] = ns[x] + 1;
/* 2091 */             if (ns[x] > max)
/*      */             {
/* 2093 */               max = ns[x];
/*      */               
/* 2095 */               ns[x] = 0;
/* 2096 */               sw = 0;
/*      */             
/*      */             }
/*      */             else
/*      */             {
/* 2101 */               ns[x] = 0;
/* 2102 */               sw = 0;
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 2112 */           ns[x] = ns[x] + 1;
/* 2113 */           sw = -1;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2124 */     if (sw != 0) {
/* 2125 */       ifin = ns[x];
/*      */     }
/*      */     
/* 2128 */     if (ifin > max) {
/* 2129 */       max = ifin;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2134 */     nccd = max;
/* 2135 */     sib = i15;
/* 2136 */     sir = 120.0D;
/* 2137 */     x = 3;
/* 2138 */     swm = (msw != 0);
/*      */ 
/*      */ 
/*      */     
/* 2142 */     ns = new int[5];
/* 2143 */     ns[x] = 0;
/* 2144 */     ifin = 0;
/* 2145 */     sw = 0;
/* 2146 */     si = 0;
/* 2147 */     max = 0;
/* 2148 */     siz = sib + sir - 1.0D;
/*      */     
/* 2150 */     for (i17 = sib; i17 <= siz; i17++) {
/* 2151 */       int n1 = i17 + 1;
/* 2152 */       if (n1 > 360) {
/* 2153 */         n1 -= 360;
/*      */       }
/*      */       
/* 2156 */       if (i17 > 360) {
/* 2157 */         si = i17 - 360;
/*      */       } else {
/* 2159 */         si = i17;
/*      */       } 
/*      */       
/* 2162 */       if (swm) {
/*      */         
/* 2164 */         if (iday[si] == x)
/*      */         {
/* 2166 */           if (iday[si] != iday[n1])
/*      */           {
/* 2168 */             if (sw != 0)
/*      */             {
/* 2170 */               ns[x] = ns[x] + 1;
/* 2171 */               if (ns[x] > max)
/*      */               {
/* 2173 */                 max = ns[x];
/*      */                 
/* 2175 */                 ns[x] = 0;
/* 2176 */                 sw = 0;
/*      */               
/*      */               }
/*      */               else
/*      */               {
/* 2181 */                 ns[x] = 0;
/* 2182 */                 sw = 0;
/*      */               
/*      */               }
/*      */ 
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */           else
/*      */           {
/* 2192 */             ns[x] = ns[x] + 1;
/* 2193 */             sw = -1;
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/*      */       
/*      */       }
/* 2202 */       else if (iday[si] != x) {
/*      */         
/* 2204 */         if (iday[n1] == x) {
/*      */           
/* 2206 */           if (sw != 0)
/*      */           {
/* 2208 */             ns[x] = ns[x] + 1;
/* 2209 */             if (ns[x] > max)
/*      */             {
/* 2211 */               max = ns[x];
/*      */               
/* 2213 */               ns[x] = 0;
/* 2214 */               sw = 0;
/*      */             
/*      */             }
/*      */             else
/*      */             {
/* 2219 */               ns[x] = 0;
/* 2220 */               sw = 0;
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 2230 */           ns[x] = ns[x] + 1;
/* 2231 */           sw = -1;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2242 */     if (sw != 0) {
/* 2243 */       ifin = ns[x];
/*      */     }
/*      */     
/* 2246 */     if (ifin > max) {
/* 2247 */       max = ifin;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2252 */     nccm = max;
/* 2253 */     int tu = 0;
/* 2254 */     boolean skipTo1420 = false;
/*      */     
/* 2256 */     if (nd[3] == 360.0D) {
/*      */       
/* 2258 */       ncsm = 180;
/* 2259 */       ncwm = 180;
/* 2260 */       ncsp = 180;
/* 2261 */       ncwp = 180;
/* 2262 */       ntsu[3] = 180.0D;
/* 2263 */       ntwi[3] = 180.0D;
/*      */       
/* 2265 */       skipTo1420 = true;
/* 2266 */     } else if (nd[1] == 360.0D) {
/*      */       
/* 2268 */       skipTo1420 = true;
/* 2269 */     } else if (nd[1] == 0.0D) {
/*      */       
/* 2271 */       tu = -1;
/* 2272 */       sib = ib;
/* 2273 */       x = 3;
/* 2274 */       swm = (tu != 0);
/*      */ 
/*      */ 
/*      */       
/* 2278 */       ns = new int[5];
/* 2279 */       ns[x] = 0;
/* 2280 */       ifin = 0;
/* 2281 */       sw = 0;
/* 2282 */       si = 0;
/* 2283 */       max = 0;
/* 2284 */       siz = sib + sir - 1.0D;
/*      */       int i19;
/* 2286 */       for (i19 = sib; i19 <= siz; i19++) {
/* 2287 */         int n1 = i19 + 1;
/* 2288 */         if (n1 > 360) {
/* 2289 */           n1 -= 360;
/*      */         }
/*      */         
/* 2292 */         if (i19 > 360) {
/* 2293 */           si = i19 - 360;
/*      */         } else {
/* 2295 */           si = i19;
/*      */         } 
/*      */         
/* 2298 */         if (swm) {
/*      */           
/* 2300 */           if (iday[si] == x)
/*      */           {
/* 2302 */             if (iday[si] != iday[n1])
/*      */             {
/* 2304 */               if (sw != 0)
/*      */               {
/* 2306 */                 ns[x] = ns[x] + 1;
/* 2307 */                 if (ns[x] > max)
/*      */                 {
/* 2309 */                   max = ns[x];
/*      */                   
/* 2311 */                   ns[x] = 0;
/* 2312 */                   sw = 0;
/*      */                 
/*      */                 }
/*      */                 else
/*      */                 {
/* 2317 */                   ns[x] = 0;
/* 2318 */                   sw = 0;
/*      */                 
/*      */                 }
/*      */ 
/*      */               
/*      */               }
/*      */             
/*      */             }
/*      */             else
/*      */             {
/* 2328 */               ns[x] = ns[x] + 1;
/* 2329 */               sw = -1;
/*      */ 
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*      */         
/*      */         }
/* 2338 */         else if (iday[si] != x) {
/*      */           
/* 2340 */           if (iday[n1] == x) {
/*      */             
/* 2342 */             if (sw != 0)
/*      */             {
/* 2344 */               ns[x] = ns[x] + 1;
/* 2345 */               if (ns[x] > max)
/*      */               {
/* 2347 */                 max = ns[x];
/*      */                 
/* 2349 */                 ns[x] = 0;
/* 2350 */                 sw = 0;
/*      */               
/*      */               }
/*      */               else
/*      */               {
/* 2355 */                 ns[x] = 0;
/* 2356 */                 sw = 0;
/*      */               
/*      */               }
/*      */ 
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 2366 */             ns[x] = ns[x] + 1;
/* 2367 */             sw = -1;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2378 */       if (sw != 0) {
/* 2379 */         ifin = ns[x];
/*      */       }
/*      */       
/* 2382 */       if (ifin > max) {
/* 2383 */         max = ifin;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 2388 */       ncsp = max;
/* 2389 */       sib = i15;
/* 2390 */       sir = 180.0D;
/* 2391 */       swm = (tu != 0);
/*      */ 
/*      */ 
/*      */       
/* 2395 */       ns = new int[5];
/* 2396 */       ns[x] = 0;
/* 2397 */       ifin = 0;
/* 2398 */       sw = 0;
/* 2399 */       si = 0;
/* 2400 */       max = 0;
/* 2401 */       siz = sib + sir - 1.0D;
/*      */       
/* 2403 */       for (i19 = sib; i19 <= siz; i19++) {
/* 2404 */         int n1 = i19 + 1;
/* 2405 */         if (n1 > 360) {
/* 2406 */           n1 -= 360;
/*      */         }
/*      */         
/* 2409 */         if (i19 > 360) {
/* 2410 */           si = i19 - 360;
/*      */         } else {
/* 2412 */           si = i19;
/*      */         } 
/*      */         
/* 2415 */         if (swm) {
/*      */           
/* 2417 */           if (iday[si] == x)
/*      */           {
/* 2419 */             if (iday[si] != iday[n1])
/*      */             {
/* 2421 */               if (sw != 0)
/*      */               {
/* 2423 */                 ns[x] = ns[x] + 1;
/* 2424 */                 if (ns[x] > max)
/*      */                 {
/* 2426 */                   max = ns[x];
/*      */                   
/* 2428 */                   ns[x] = 0;
/* 2429 */                   sw = 0;
/*      */                 
/*      */                 }
/*      */                 else
/*      */                 {
/* 2434 */                   ns[x] = 0;
/* 2435 */                   sw = 0;
/*      */                 
/*      */                 }
/*      */ 
/*      */               
/*      */               }
/*      */             
/*      */             }
/*      */             else
/*      */             {
/* 2445 */               ns[x] = ns[x] + 1;
/* 2446 */               sw = -1;
/*      */ 
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*      */         
/*      */         }
/* 2455 */         else if (iday[si] != x) {
/*      */           
/* 2457 */           if (iday[n1] == x) {
/*      */             
/* 2459 */             if (sw != 0)
/*      */             {
/* 2461 */               ns[x] = ns[x] + 1;
/* 2462 */               if (ns[x] > max)
/*      */               {
/* 2464 */                 max = ns[x];
/*      */                 
/* 2466 */                 ns[x] = 0;
/* 2467 */                 sw = 0;
/*      */               
/*      */               }
/*      */               else
/*      */               {
/* 2472 */                 ns[x] = 0;
/* 2473 */                 sw = 0;
/*      */               
/*      */               }
/*      */ 
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 2483 */             ns[x] = ns[x] + 1;
/* 2484 */             sw = -1;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2495 */       if (sw != 0) {
/* 2496 */         ifin = ns[x];
/*      */       }
/*      */       
/* 2499 */       if (ifin > max) {
/* 2500 */         max = ifin;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 2505 */       ncwp = max;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2511 */       tu = 0;
/* 2512 */       sib = ib;
/* 2513 */       sir = 180.0D;
/* 2514 */       x = 1;
/* 2515 */       swm = (tu != 0);
/*      */ 
/*      */       
/* 2518 */       ns = new int[5];
/* 2519 */       ns[x] = 0;
/* 2520 */       ifin = 0;
/* 2521 */       sw = 0;
/* 2522 */       si = 0;
/* 2523 */       max = 0;
/* 2524 */       siz = sib + sir - 1.0D;
/*      */       int i19;
/* 2526 */       for (i19 = sib; i19 <= siz; i19++) {
/* 2527 */         int n1 = i19 + 1;
/* 2528 */         if (n1 > 360) {
/* 2529 */           n1 -= 360;
/*      */         }
/*      */         
/* 2532 */         if (i19 > 360) {
/* 2533 */           si = i19 - 360;
/*      */         } else {
/* 2535 */           si = i19;
/*      */         } 
/*      */         
/* 2538 */         if (swm) {
/*      */           
/* 2540 */           if (iday[si] == x)
/*      */           {
/* 2542 */             if (iday[si] != iday[n1])
/*      */             {
/* 2544 */               if (sw != 0)
/*      */               {
/* 2546 */                 ns[x] = ns[x] + 1;
/* 2547 */                 if (ns[x] > max)
/*      */                 {
/* 2549 */                   max = ns[x];
/*      */                   
/* 2551 */                   ns[x] = 0;
/* 2552 */                   sw = 0;
/*      */                 
/*      */                 }
/*      */                 else
/*      */                 {
/* 2557 */                   ns[x] = 0;
/* 2558 */                   sw = 0;
/*      */                 
/*      */                 }
/*      */ 
/*      */               
/*      */               }
/*      */             
/*      */             }
/*      */             else
/*      */             {
/* 2568 */               ns[x] = ns[x] + 1;
/* 2569 */               sw = -1;
/*      */ 
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*      */         
/*      */         }
/* 2578 */         else if (iday[si] != x) {
/*      */           
/* 2580 */           if (iday[n1] == x) {
/*      */             
/* 2582 */             if (sw != 0)
/*      */             {
/* 2584 */               ns[x] = ns[x] + 1;
/* 2585 */               if (ns[x] > max)
/*      */               {
/* 2587 */                 max = ns[x];
/*      */                 
/* 2589 */                 ns[x] = 0;
/* 2590 */                 sw = 0;
/*      */               
/*      */               }
/*      */               else
/*      */               {
/* 2595 */                 ns[x] = 0;
/* 2596 */                 sw = 0;
/*      */               
/*      */               }
/*      */ 
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 2606 */             ns[x] = ns[x] + 1;
/* 2607 */             sw = -1;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2618 */       if (sw != 0) {
/* 2619 */         ifin = ns[x];
/*      */       }
/*      */       
/* 2622 */       if (ifin > max) {
/* 2623 */         max = ifin;
/*      */       }
/*      */ 
/*      */       
/* 2627 */       ncsm = max;
/* 2628 */       sib = i15;
/*      */ 
/*      */       
/* 2631 */       ns = new int[5];
/* 2632 */       ns[x] = 0;
/* 2633 */       ifin = 0;
/* 2634 */       sw = 0;
/* 2635 */       si = 0;
/* 2636 */       max = 0;
/* 2637 */       siz = sib + sir - 1.0D;
/*      */       
/* 2639 */       for (i19 = sib; i19 <= siz; i19++) {
/* 2640 */         int n1 = i19 + 1;
/* 2641 */         if (n1 > 360) {
/* 2642 */           n1 -= 360;
/*      */         }
/*      */         
/* 2645 */         if (i19 > 360) {
/* 2646 */           si = i19 - 360;
/*      */         } else {
/* 2648 */           si = i19;
/*      */         } 
/*      */         
/* 2651 */         if (swm) {
/*      */           
/* 2653 */           if (iday[si] == x)
/*      */           {
/* 2655 */             if (iday[si] != iday[n1])
/*      */             {
/* 2657 */               if (sw != 0)
/*      */               {
/* 2659 */                 ns[x] = ns[x] + 1;
/* 2660 */                 if (ns[x] > max)
/*      */                 {
/* 2662 */                   max = ns[x];
/*      */                   
/* 2664 */                   ns[x] = 0;
/* 2665 */                   sw = 0;
/*      */                 
/*      */                 }
/*      */                 else
/*      */                 {
/* 2670 */                   ns[x] = 0;
/* 2671 */                   sw = 0;
/*      */                 
/*      */                 }
/*      */ 
/*      */               
/*      */               }
/*      */             
/*      */             }
/*      */             else
/*      */             {
/* 2681 */               ns[x] = ns[x] + 1;
/* 2682 */               sw = -1;
/*      */ 
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*      */         
/*      */         }
/* 2691 */         else if (iday[si] != x) {
/*      */           
/* 2693 */           if (iday[n1] == x) {
/*      */             
/* 2695 */             if (sw != 0)
/*      */             {
/* 2697 */               ns[x] = ns[x] + 1;
/* 2698 */               if (ns[x] > max)
/*      */               {
/* 2700 */                 max = ns[x];
/*      */                 
/* 2702 */                 ns[x] = 0;
/* 2703 */                 sw = 0;
/*      */               
/*      */               }
/*      */               else
/*      */               {
/* 2708 */                 ns[x] = 0;
/* 2709 */                 sw = 0;
/*      */               
/*      */               }
/*      */ 
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 2719 */             ns[x] = ns[x] + 1;
/* 2720 */             sw = -1;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2731 */       if (sw != 0) {
/* 2732 */         ifin = ns[x];
/*      */       }
/*      */       
/* 2735 */       if (ifin > max) {
/* 2736 */         max = ifin;
/*      */       }
/*      */ 
/*      */       
/* 2740 */       ncwm = max;
/*      */     } 
/*      */ 
/*      */     
/* 2744 */     int jb = 0;
/* 2745 */     int jr = 0;
/* 2746 */     int je = 0;
/* 2747 */     if (!skipTo1420) {
/*      */       
/* 2749 */       jb = ib;
/* 2750 */       jr = 180;
/*      */ 
/*      */       
/* 2753 */       for (int ij = 1; ij <= 3; ij++) {
/* 2754 */         nzd[ij] = 0.0D;
/*      */       }
/*      */       
/* 2757 */       je = jb + jr - 1;
/*      */       
/* 2759 */       for (int l = jb; l <= je; l++) {
/* 2760 */         int i20 = l;
/* 2761 */         if (i20 > 360) {
/* 2762 */           i20 -= 360;
/*      */         }
/* 2764 */         int ik = iday[i20];
/* 2765 */         nzd[ik] = nzd[ik] + 1.0D;
/*      */       } 
/*      */       
/*      */       int i19;
/* 2769 */       for (i19 = 1; i19 <= 3; i19++) {
/* 2770 */         ntsu[i19] = nzd[i19];
/*      */       }
/* 2772 */       for (i19 = 1; i19 <= 3; i19++) {
/* 2773 */         ntwi[i19] = nd[i19] - ntsu[i19];
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2779 */     int[] ntd = new int[365];
/*      */     
/* 2781 */     if (!tempUnder8C)
/*      */     {
/*      */ 
/*      */       
/* 2785 */       for (int i19 = 1; i19 <= 360; ) { ntd[i19] = 56; i19++; }
/*      */     
/*      */     }
/* 2788 */     if (tc != 0 || tu != 0) {
/*      */ 
/*      */       
/* 2791 */       char[] kl = new char[4];
/* 2792 */       double[] kr = new double[25];
/* 2793 */       int[] arrayOfInt = new int[25];
/* 2794 */       int kt = 0;
/*      */       
/* 2796 */       kl[0] = '$';
/* 2797 */       kl[1] = '-';
/* 2798 */       kl[2] = '5';
/* 2799 */       kl[3] = '8';
/*      */       int i19;
/* 2801 */       for (i19 = 1; i19 <= 24; i19++) {
/* 2802 */         kr[i19] = 0.0D;
/*      */       }
/*      */       
/* 2805 */       if (nbd[1] < 0.0D && nbd8[1] < 0.0D) {
/*      */         
/* 2807 */         for (i19 = 1; i19 <= 360; i19++) {
/* 2808 */           ntd[i19] = kl[1];
/*      */         }
/*      */       }
/* 2811 */       else if (nbd[1] == 0.0D && nbd8[1] < 0.0D) {
/*      */         
/* 2813 */         for (i19 = 1; i19 <= 360; i19++) {
/* 2814 */           ntd[i19] = kl[2];
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 2819 */         if (nbd8[1] < 0.0D) {
/* 2820 */           nbd8[1] = 0.0D;
/*      */         }
/*      */         
/* 2823 */         for (i19 = 1; i19 <= 6; i19++) {
/* 2824 */           kr[i19] = nbd[i19];
/* 2825 */           arrayOfInt[i19] = 2;
/*      */         } 
/*      */         
/* 2828 */         for (i19 = 7; i19 <= 12; i19++) {
/* 2829 */           kt = i19 - 6;
/* 2830 */           if (ned[kt] != 0.0D) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2835 */             kr[i19] = ned[kt] + 1.0D;
/* 2836 */             if (kr[i19] > 360.0D) {
/* 2837 */               kr[i19] = 1.0D;
/*      */             }
/* 2839 */             arrayOfInt[i19] = 1;
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2846 */         for (i19 = 13; i19 <= 18; i19++) {
/* 2847 */           kt = i19 - 12;
/* 2848 */           kr[i19] = nbd8[kt];
/* 2849 */           arrayOfInt[i19] = 3;
/*      */         } 
/*      */         
/* 2852 */         for (i19 = 19; i19 <= 24; i19++) {
/* 2853 */           kt = i19 - 18;
/* 2854 */           if (ned8[kt] != 0.0D) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2859 */             kr[i19] = ned8[kt] + 1.0D;
/* 2860 */             if (kr[i19] > 360.0D) {
/* 2861 */               kr[i19] = 1.0D;
/*      */             }
/* 2863 */             arrayOfInt[i19] = 2;
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2870 */         int nt = 23;
/* 2871 */         int stt = 0;
/* 2872 */         double itemp = 0.0D;
/* 2873 */         int itm = 0;
/*      */         
/* 2875 */         for (int i20 = 1; i20 <= 23; i20++) {
/* 2876 */           stt = 0;
/*      */           
/* 2878 */           for (int i24 = 1; i24 <= nt; i24++) {
/* 2879 */             if (kr[i24] > kr[i24 + 1]) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2884 */               itemp = kr[i24];
/* 2885 */               itm = arrayOfInt[i24];
/* 2886 */               kr[i24] = kr[i24 + 1];
/* 2887 */               arrayOfInt[i24] = arrayOfInt[i24 + 1];
/* 2888 */               kr[i24 + 1] = itemp;
/* 2889 */               arrayOfInt[i24 + 1] = itm;
/* 2890 */               stt = -1;
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2897 */           if (stt == 0) {
/*      */             break;
/*      */           }
/*      */ 
/*      */           
/* 2902 */           nt--;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2909 */         int ima = arrayOfInt[24];
/* 2910 */         int nul = 0;
/*      */         
/* 2912 */         for (int i21 = 1; i21 <= 24; i21++) {
/* 2913 */           if (kr[i21] == 0.0D) {
/* 2914 */             nul++;
/*      */           }
/*      */         } 
/*      */         
/* 2918 */         int kk = 0; int i22;
/* 2919 */         for (i22 = 1; i22 <= 24; i22++) {
/* 2920 */           kk = i22;
/* 2921 */           int ipl = i22 + nul;
/* 2922 */           if (ipl > 24) {
/*      */             break;
/*      */           }
/*      */ 
/*      */           
/* 2927 */           kr[i22] = kr[ipl];
/* 2928 */           arrayOfInt[i22] = arrayOfInt[ipl];
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2935 */         for (i22 = kk; i22 <= 24; i22++) {
/* 2936 */           kr[i22] = 0.0D;
/*      */         }
/*      */         
/* 2939 */         if (kr[1] != 1.0D) {
/*      */ 
/*      */ 
/*      */           
/* 2943 */           ie = (int)(kr[1] - 1.0D);
/* 2944 */           for (i22 = 1; i22 <= ie; i22++) {
/* 2945 */             ntd[i22] = kl[ima];
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2951 */         int ns2 = 0;
/* 2952 */         int nn = 24 - nul;
/* 2953 */         for (int i23 = 1; i23 <= nn; i23++) {
/* 2954 */           ns2 = arrayOfInt[i23];
/* 2955 */           ib = (int)kr[i23];
/* 2956 */           ie = (int)(kr[i23 + 1] - 1.0D);
/* 2957 */           if (kr[i23 + 1] == 0.0D) {
/* 2958 */             ie = 360;
/*      */           }
/*      */           
/* 2961 */           for (int i24 = ib; i24 <= ie; i24++) {
/* 2962 */             ntd[i24] = kl[ns2];
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2974 */     String ans = " ";
/* 2975 */     String div = " ";
/* 2976 */     String q = " ";
/* 2977 */     if (swt != 0) {
/* 2978 */       ans = "Perudic";
/*      */       
/* 2980 */       ncsm = 180;
/* 2981 */       ncwm = 180;
/* 2982 */       ncsp = 180;
/* 2983 */       ncwp = 180;
/* 2984 */       ntsu[3] = 180.0D;
/* 2985 */       ntwi[3] = 180.0D;
/*      */     }
/* 2987 */     else if (nsd[1] > lt5c / 2.0D && ncpm[2] < 90) {
/* 2988 */       ans = "Aridic";
/*      */       
/* 2990 */       if (nd[1] == 360.0D) {
/* 2991 */         q = "Extreme";
/* 2992 */       } else if (ncpm[2] <= 45) {
/* 2993 */         q = "Typic";
/*      */       } else {
/* 2995 */         q = "Weak";
/*      */       } 
/*      */       
/* 2998 */       div = new String(ans);
/*      */     }
/* 3000 */     else if (tma < 22.0D && dif >= 5.0D && nccd >= 45 && nccm >= 45) {
/* 3001 */       ans = "Xeric";
/*      */       
/* 3003 */       if (nccd > 90) {
/* 3004 */         q = "Dry";
/*      */       } else {
/* 3006 */         q = "Typic";
/*      */       } 
/*      */       
/* 3009 */       div = new String(ans);
/*      */     }
/* 3011 */     else if (nd[1] + nd[2] < 90.0D) {
/* 3012 */       ans = "Udic";
/*      */       
/* 3014 */       if (nd[1] + nd[2] < 30.0D) {
/* 3015 */         q = "Typic";
/*      */         
/* 3017 */         div = new String(ans);
/*      */ 
/*      */       
/*      */       }
/* 3021 */       else if (dif < 5.0D) {
/* 3022 */         q = "Dry";
/* 3023 */         div = "Tropudic";
/*      */       }
/*      */       else {
/*      */         
/* 3027 */         div = "Tempudic";
/* 3028 */         q = "Dry";
/*      */       }
/*      */     
/*      */     }
/* 3032 */     else if (!trr.equalsIgnoreCase("pergelic") && !trr.equalsIgnoreCase("cryic")) {
/* 3033 */       ans = "Ustic";
/*      */       
/* 3035 */       if (dif >= 5.0D) {
/* 3036 */         div = "Tempustic";
/*      */         
/* 3038 */         if (nccm <= 45) {
/* 3039 */           q = "Typic";
/* 3040 */         } else if (nccd > 45) {
/* 3041 */           q = "Xeric";
/*      */         } else {
/* 3043 */           q = "Wet";
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 3048 */         if (ncpm[2] < 180) {
/* 3049 */           q = "Aridic";
/* 3050 */         } else if (ncpm[2] < 270) {
/* 3051 */           q = "Typic";
/*      */         } else {
/* 3053 */           q = "Udic";
/*      */         } 
/*      */         
/* 3056 */         div = "Tropustic";
/*      */       } 
/*      */     } else {
/*      */       
/* 3060 */       ans = "Undefined";
/* 3061 */       div = new String(ans);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3068 */     byte[] cc2Bytes = { 7, 0, 7, 0, 7 };
/* 3069 */     String cc2 = new String(cc2Bytes);
/* 3070 */     int c2 = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3093 */     String flxFile = "\"" + dataset.getName() + "\",\"" + dataset.getCountry() + "\"," + dataset.getLatitudeDegrees() + ",";
/* 3094 */     flxFile = flxFile + dataset.getLatitudeMinutes() + "," + dataset.getNsHemisphere() + "," + dataset.getLongitudeDegrees();
/* 3095 */     flxFile = flxFile + "," + dataset.getLongitudeMinutes() + "," + dataset.getEwHemisphere() + "," + dataset.getElevation();
/* 3096 */     flxFile = flxFile + "," + arf + "," + aev + ",\"" + trr + "\"," + tma + "," + st + "," + wt + "," + dif + ",";
/* 3097 */     flxFile = flxFile + dataset.getStartYear() + "," + dataset.getEndYear() + "\n";
/*      */     int i18;
/* 3099 */     for (i18 = 1; i18 <= 12; i18++) {
/* 3100 */       flxFile = flxFile + precip[i18] + "," + temperature[i18];
/* 3101 */       flxFile = flxFile + "," + mpe[i18] + "\n";
/*      */     } 
/*      */     
/* 3104 */     flxFile = flxFile + "\"" + ans + "\"," + id5c + "," + lt5c + "," + id8c + "," + lt8c + ",";
/* 3105 */     flxFile = flxFile + nccd + "," + nccm + "," + ncsm + "," + ncwm + ",\"" + div + "\",\"" + q + "\"\n";
/* 3106 */     flxFile = flxFile + ncsp + "," + ncwp + "," + tc + "," + tu + "," + swt + "," + swp + "\n";
/*      */     
/* 3108 */     for (i18 = 1; i18 < 360; i18++) {
/* 3109 */       flxFile = flxFile + iday[i18] + ",";
/*      */     }
/* 3111 */     flxFile = flxFile + iday[360] + "," + whc + "\n";
/*      */     
/* 3113 */     for (i18 = 1; i18 <= 6; i18++) {
/* 3114 */       flxFile = flxFile + nbd[i18] + "," + ned[i18] + "," + nbd8[i18] + "," + ned8[i18] + "\n";
/*      */     }
/*      */     
/* 3117 */     for (i18 = 1; i18 <= 3; i18++) {
/* 3118 */       flxFile = flxFile + nd[i18] + "," + nzd[i18] + "," + nsd[i18] + "," + ntsu[i18] + "," + ntwi[i18] + "\n";
/*      */     }
/*      */     
/* 3121 */     flxFile = flxFile + ncpm[1] + "," + ncpm[2] + "\n";
/*      */     
/* 3123 */     for (i18 = 1; i18 <= 5; i18++) {
/* 3124 */       flxFile = flxFile + cd[i18] + "\n";
/*      */     }
/*      */     
/* 3127 */     for (i18 = 1; i18 < 360; i18++) {
/* 3128 */       flxFile = flxFile + (char)ntd[i18] + ",";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3138 */     double awb = computeWaterBalance(precip, mpe, false, (dataset.getNsHemisphere() == 'N'));
/* 3139 */     double swb = computeWaterBalance(precip, mpe, true, (dataset.getNsHemisphere() == 'N'));
/*      */     
/* 3141 */     return new NewhallResults(arf, whc, mpe, nccd, nccm, ntd, iday, nd, nsd, ncpm, trr, ans, q, div, awb, swb, flxFile);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static double computeWaterBalance(double[] precip, double[] evap, boolean onlySummer, boolean northernHemisphere) {
/* 3147 */     double runningBalance = 0.0D;
/*      */     
/* 3149 */     if (onlySummer) {
/* 3150 */       if (northernHemisphere) {
/* 3151 */         for (int i = 6; i <= 8; i++) {
/* 3152 */           runningBalance += precip[i];
/* 3153 */           runningBalance -= evap[i];
/*      */         } 
/*      */       } else {
/* 3156 */         runningBalance += precip[12];
/* 3157 */         runningBalance -= evap[12];
/* 3158 */         runningBalance += precip[1];
/* 3159 */         runningBalance -= evap[1];
/* 3160 */         runningBalance += precip[2];
/* 3161 */         runningBalance -= evap[2];
/*      */       } 
/*      */     } else {
/* 3164 */       for (int i = 1; i <= 12; i++) {
/* 3165 */         runningBalance += precip[i];
/* 3166 */         runningBalance -= evap[i];
/*      */       } 
/*      */     } 
/*      */     
/* 3170 */     return runningBalance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   private static List<Double> computeSoilCalendar(double[] airTemps, int summerLagPhase, int fallLagPhase, boolean northernHemisphere, double amplitude, double averageOffset) {
/* 3185 */     double yearAverage = 0.0D;
/* 3186 */     for (double month : airTemps) {
/* 3187 */       yearAverage += month;
/*      */     }
/* 3189 */     yearAverage /= 12.0D;
/* 3190 */     yearAverage += averageOffset;
/*      */     
/* 3192 */     double summerAverage = airTemps[5] + airTemps[6] + airTemps[7];
/* 3193 */     summerAverage /= 3.0D;
/* 3194 */     summerAverage += averageOffset;
/*      */     
/* 3196 */     double winterAverage = airTemps[11] + airTemps[0] + airTemps[1];
/* 3197 */     winterAverage /= 3.0D;
/* 3198 */     summerAverage += averageOffset;
/*      */     
/* 3200 */     double a = Math.abs(summerAverage - winterAverage) / 2.0D * amplitude;
/* 3201 */     double w = 0.017453292519943295D;
/*      */     
/* 3203 */     ArrayList<Double> soilTempCalendarUnshifted = new ArrayList<Double>(360);
/*      */     
/* 3205 */     for (int i = 0; i < 360; i++) {
/* 3206 */       if (northernHemisphere) {
/*      */         
/* 3208 */         if (i >= 90 && i < 270) {
/* 3209 */           soilTempCalendarUnshifted.add(i, Double.valueOf(yearAverage + a * Math.sin(w * (i + summerLagPhase))));
/*      */         } else {
/* 3211 */           soilTempCalendarUnshifted.add(i, Double.valueOf(yearAverage + a * Math.sin(w * (i + fallLagPhase))));
/*      */         
/*      */         }
/*      */       
/*      */       }
/* 3216 */       else if (i >= 90 && i < 270) {
/* 3217 */         soilTempCalendarUnshifted.add(i, Double.valueOf(yearAverage + a * Math.cos(w * (i + summerLagPhase))));
/*      */       } else {
/* 3219 */         soilTempCalendarUnshifted.add(i, Double.valueOf(yearAverage + a * Math.cos(w * (i + fallLagPhase))));
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3225 */     ArrayList<Double> soilTempCalendar = new ArrayList<Double>(360);
/*      */     int j;
/* 3227 */     for (j = 0; j < 134; j++) {
/* 3228 */       soilTempCalendar.add(j, soilTempCalendarUnshifted.get(j + 226));
/*      */     }
/*      */     
/* 3231 */     for (j = 134; j < 360; j++) {
/* 3232 */       soilTempCalendar.add(j, soilTempCalendarUnshifted.get(j - 134));
/*      */     }
/*      */     
/* 3235 */     return soilTempCalendar;
/*      */   }
/*      */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/psu/newhall/sim/BASICSimulationModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */