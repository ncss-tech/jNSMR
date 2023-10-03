 package org.psu.newhall.sim;

 import java.util.List;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.stream.Collectors;
 
 /**
 * This is the 1:1 translation of the original Newhall BASIC source code.  Variable names have
 * been preserved (and unfortunately ambiguous as a result).  Array indexes do not start at zero,
 * among other BASIC quirkiness.  This should be modernized in the future, but this version should
 * remain to assure consistency with the original version.
 */

 public class BASICSimulationModel
 {
   public static NewhallResults runSimulation(NewhallDataset dataset, double waterHoldingCapacity) {
    // Calls simulation model with the BASIC version's default constants for FC and FCD.
    return runSimulation(dataset, waterHoldingCapacity, 2.5D, 0.66D);
   }

   
             public static NewhallBatchResults runBatch(String[] name, String[] country, double latitude[], double longitude[], char nsHemisphere[], char ewHemisphere[], double elevation[], double[][] precipitation, double[][] temperature, int[] startYear, int[] endYear, boolean[] isMetric, double[] waterholdingCapacity, double[] fc, double[] fcd) {
                NewhallResults[] r = new NewhallResults[name.length];
                for (int i = 0; i <= (name.length - 1); i++) {
                  NewhallDataset d = new NewhallDataset(name[i], country[i], latitude[i], longitude[i], nsHemisphere[i], ewHemisphere[i], elevation[i], 
                                                    Arrays.stream(precipitation[i]).boxed().collect(Collectors.toList()),
                                                    Arrays.stream(temperature[i]).boxed().collect(Collectors.toList()), 
                                                    startYear[i], endYear[i], isMetric[i], waterholdingCapacity[i]); 
                  r[i] = runSimulation(d, waterholdingCapacity[i], fc[i], fcd[i]);                                   
                }
                return new NewhallBatchResults(r);
             } 
     public static NewhallBatchResults runBatch2(double[][] precipitation, double[][] temperature, double latitude[], double longitude[], char nsHemisphere[], double elevation[], boolean[] isMetric, double[] waterholdingCapacity, double[] fc, double[] fcd, boolean[] hasOHorizon, boolean[] isSaturated) {
                NewhallResults[] r = new NewhallResults[latitude.length];
                for (int i = 0; i <= (latitude.length - 1); i++) {
                  r[i] = runSimulation(temperature[i], precipitation[i], latitude[i], longitude[i], elevation[i], nsHemisphere[i], isMetric[i], waterholdingCapacity[i], fc[i], fcd[i], hasOHorizon[i], isSaturated[i]);                                   
                }
                return new NewhallBatchResults(r);
             } 
             
    public static NewhallResults runSimulation(double[] temperature, 
                                               double[] precip, 
                                               double latDD,
                                               double lonDD,
                                               double elevation,
                                               char northsouth,
                                               boolean isMetric,
                                               double waterHoldingCapacity, 
                                               double fc, 
                                               double fcd,
                                               boolean hasOHorizon,
                                               boolean isSaturated) {
                                                   
    // This is a hack to prevent a problematic region of code from running
    // multiple times.  Problem datasets that test that this hack works
    // are present in the unit tests.
     boolean hasRunAlready = false;


     if (!isMetric) {
       elevation *= 0.3048D; // BASIC code uses 0.305
     }

     double[] upe = new double[13];
     double[] mpe = new double[13];
     double[] mwi = new double[13];
     
     for (int j = 1; j <= 12; j++) {
       if (temperature[j] > 0.0D) {
         double mwiValue = Math.pow(temperature[j] / 5.0D, 1.514D);
         mwi[j] = mwiValue;
       }
     }

     double swi = 0.0D; double[] arrayOfDouble1; int m; byte b;
     for (arrayOfDouble1 = mwi, m = arrayOfDouble1.length, b = 0; b < m; ) { 
       Double mwiElement = Double.valueOf(arrayOfDouble1[b]);
       swi += mwiElement.doubleValue();

       b++; 
     }

     double a = 6.75E-7D * swi * swi * swi - 7.71E-5D * swi * swi + 0.01792D * swi + 0.49239D;

     for (int i1 = 1; i1 <= 12; i1++) {
       if (temperature[i1] > 0.0D) {
         if (temperature[i1] < 26.5D) {
           double aBase = 10.0D * temperature[i1] / swi;
           upe[i1] = 16.0D * Math.pow(aBase, a);
         } else if (temperature[i1] >= 38.0D) {
           upe[i1] = 185.0D;
         } else {
           double[] zt = BASICSimulationModelConstants.zt;
           double[] zpe = BASICSimulationModelConstants.zpe;
           int kl = 0;
           int kk = 0;
           for (int ki = 1; ki <= 24; ki++) {
             kl = ki + 1;
             kk = ki;
             if (temperature[i1] >= zt[ki - 1] && temperature[i1] < zt[kl - 1]) {
               upe[i1] = zpe[kk - 1];


               break;
             }
           }
         }
       }
     }

     if (northsouth == 'N') {

       int nrow = 0; int i19;
       for (i19 = 1; i19 <= 31 &&
         latDD >= BASICSimulationModelConstants.rn[i19 - 1]; i19++)
       {



         nrow++;
       }



       for (i19 = 1; i19 <= 12; i19++) {
         if (upe[i19] > 0.0D)
         {



           mpe[i19] = upe[i19] * BASICSimulationModelConstants.inz[i19 - 1][nrow - 1];
         }
       }

     }
     else {

       int nrow = 0; int i19;
       for (i19 = 1; i19 <= 13 &&
         latDD >= BASICSimulationModelConstants.rs[i19 - 1]; i19++)
       {

         nrow++;
       }




       if (nrow != 0) {

         for (i19 = 1; i19 <= 12; i19++) {
           if (upe[i19] > 0.0D)
           {

             if (nrow >= 13 || BASICSimulationModelConstants.fs[i19 - 1][nrow - 1] == BASICSimulationModelConstants.fs[i19 - 1][nrow]) {


               double cf = BASICSimulationModelConstants.fs[i19 - 1][nrow - 1];
               mpe[i19] = upe[i19] * cf;


             }
             else {


               double cf = (BASICSimulationModelConstants.fs[i19 - 1][nrow] - BASICSimulationModelConstants.fs[i19 - 1][nrow - 1]) * (((int)latDD - BASICSimulationModelConstants.rs[nrow - 1]) * 60.0D + ((latDD - (int)latDD)*60)) / (BASICSimulationModelConstants.rs[nrow] - BASICSimulationModelConstants.rs[nrow - 1]) * 60.0D;

               cf += BASICSimulationModelConstants.fs[i19 - 1][nrow - 1];

               mpe[i19] = upe[i19] * cf;
             }
           }
         }
       } else {

         nrow = 1;
         for (i19 = 1; i19 <= 12; i19++) {
           if (upe[i19] > 0.0D) {




             double cf = (BASICSimulationModelConstants.fs[i19 - 1][0] - BASICSimulationModelConstants.inz[i19 - 1][0]) * (((int)latDD * 60) + ((latDD - (int)latDD)*60)) / 300.0D;

             cf += BASICSimulationModelConstants.inz[i19 - 1][0];
             mpe[i19] = upe[i19] * cf;
           }
         }
       }
     }




     double arf = 0.0D;
     double aev = 0.0D; // TODO: value not used?
     for (int i2 = 1; i2 <= 12; i2++) {
       arf += precip[i2];
       aev += mpe[i2];
     }



     double sumt = 0.0D;
     for (int i3 = 1; i3 <= 12; i3++) {
       sumt += temperature[i3];
     }

     double tma = sumt / 12.0D + fc;
     double at1 = (temperature[6] + temperature[7] + temperature[8]) / 3.0D + fc;

     double at2 = (temperature[1] + temperature[2] + temperature[12]) / 3.0D + fc;


     double st = 0.0D;
     double wt = 0.0D;
     if (northsouth == 'N') {
       st = at1;
       wt = at2;
     } else {
       st = at2;
       wt = at1;
     }

     double dif = Math.abs(at1 - at2);
     // double cs = dif * (1.0D - fcd) / 2.0D; 
     // TODO: check (1.0D - fcd) v.s. fcd v.s. cr[7] expression
     
    /**
     * The cr[] arrays hold criteria to determine temperature regime.  Then
     * the reg[] array holds the flags for each regime.  The last-most flag
     * that is true indicates the temp regime.
     */

    // properly handle cryic criteria
    int cryic_ht = 15;
    if (hasOHorizon) {
        if(isSaturated) {
            cryic_ht = 6;
        } else {
            cryic_ht = 8;
        }
    } else if (isSaturated) {
        cryic_ht = 13;
    }

    boolean[] cr = new boolean[13];
    boolean[] reg = new boolean[13];
    cr[1] = tma < 0;                      // Mean annual air temp (MAAT) < 0C.
    cr[2] = 0 <= tma && tma < 8;          // 0C <= MAAT <= 8C.
    // cr[3] = (st - cs) < 15;            // Summer temp ave minus (summer/winter difference * (1 - SOIL_AIR_REL) * 0.5) < 15C.
                                          // TODO: where did latter part ^^ (... - SWD...) of this come from? Misread cryic crit 1?
    cr[3] = st >= 0 && st < cryic_ht;           // "non-saturated, organic surface, mean _summer_ soil temperature between 0 and 8C/15C
                                          // TODO: st upper limit depends on saturation, O horizon
    cr[7] = (dif * fcd) >= 6;             // Summer/winter difference * SOIL_AIR_REL >= 6 
                                          // NOTE: Taxonomy clearly states difference greater/equal than 6, not 5
    cr[8] = (tma >= 8) && (tma < 15);     // 8C <= MAAT < 15C.
    cr[9] = (tma >= 15) && (tma < 22);    // 15C <= MAAT < 22C.
    cr[10] = tma >= 22;                   // 22C <= MAAT.
    cr[11] = tma < 8;                     // MAAT < 8C.
    reg[1] = cr[1];                       // PERGELIC
    reg[2] = cr[2] && cr[3];              // CRYIC 
    reg[3] = cr[11] && !cr[3] && cr[7];   // FRIGID
    reg[4] = cr[8] && cr[7];              // MESIC
    reg[5] = cr[9] && cr[7];              // THERMIC
    reg[6] = cr[10] && cr[7];             // HYPERTHERMIC
    reg[7] = cr[11] && !cr[7] && !cr[3];  // ISOFRIGID
    reg[8] = cr[8] && !cr[7];             // ISOMESIC
    reg[9] = cr[9] && !cr[7];             // ISOTHERMIC
    reg[10] = cr[10] && !cr[7];           // ISOHYPERTHERMIC

     //st -= cs;
     //wt += cs;
     dif = st - wt;

     String trr = "";
     for (int i4 = 1; i4 <= 10; i4++) {
       if (reg[i4]) {
         trr = BASICSimulationModelConstants.tempRegimes[i4 - 1];
       }
     }

     double whc = waterHoldingCapacity;
     double fsl = whc / 64.0D;
     double[] sl = new double[65];
     for (int i5 = 0; i5 < sl.length; i5++) {
       sl[i5] = 0.0D;
     }

     int k = 1;
     int swst = 0; // TODO: not used?
     int swfi = 0; // TODO: not used?

     double[] ntwi = new double[4];
     double[] ntsu = new double[4];
     double[] nsd = new double[4];
     double[] nzd = new double[4];
     double[] nd = new double[4];
     boolean[] cc = new boolean[4];
     for (int i6 = 1; i6 <= 3; i6++) {
       ntwi[i6] = 0.0D;
       ntsu[i6] = 0.0D;
       nsd[i6] = 0.0D;
       nzd[i6] = 0.0D;
       nd[i6] = 0.0D;
       cc[i6] = false;
     }

     double[] cd = new double[6];
     for (int i7 = 1; i7 <= 5; i7++) {
       cd[i7] = 0.0D;
     }

     int msw = -1;
     int sib = 0;
     double sir = 0.0D;
     int x = 0;
     boolean swm = false;
     int max = 0;
     int icon = 0;
     double lt5c = 0.0D;
     int lt8c = 0;
     int ie = 0;
     int ib = 1;
     double prmo = 0.0D;
     int nccd = 0;
     int nccm = 0;
     int id8c = 0;
     double id5c = 0.0D;
     int swt = 0;
     int tc = 0;
     int np = 0;
     int np8 = 0;
     int ncsm = 0;
     int ncwm = 0;
     int ncsp = 0;
     int ncwp = 0;

     double[] nbd = new double[7];
     double[] ned = new double[7];
     double[] nbd8 = new double[7];
     double[] ned8 = new double[7];
     for (int i8 = 1; i8 <= 6; i8++) {
       nbd[i8] = 0.0D;
       ned[i8] = 0.0D;
       nbd8[i8] = 0.0D;
       ned8[i8] = 0.0D;
     }

     int[] iday = new int[361];
     for (int i9 = 1; i9 <= 360; i9++) {
       iday[i9] = 0;
     }

     int swp = -1;
     int gogr = 0;
     
     boolean noMpeGreaterThanPrecip = true;
     for (int i11 = 1; i11 <= 12; i11++) {
       if (mpe[i11] > precip[i11]) {
         noMpeGreaterThanPrecip = false;
         break;
       }
     }
     if (noMpeGreaterThanPrecip) {
       cd[5] = -1.0D;
       swt = -1;
     }



     for (int n = 1; n <= 10; n++) {
       for (int i19 = 1; i19 <= 12; i19++) {



         int zsw = 0;
         double lp = precip[i19] / 2.0D;
         double npe = (lp - mpe[i19]) / 2.0D;
         if (npe <= 0.0D) {

           npe = 0.0D - npe;
         } else {
           zsw = -1;
         }



         for (int i20 = 1; i20 <= 64; i20++) {
           if (zsw == 0) {

             int nr = BASICSimulationModelConstants.dp[i20 - 1];
             if (sl[nr] > 0.0D)
             {



               double rpe = sl[nr] * BASICSimulationModelConstants.dr[i20 - 1];
               if (npe <= rpe) {

                 sl[nr] = sl[nr] - npe / BASICSimulationModelConstants.dr[i20 - 1];
                 npe = 0.0D;

                 break;
               }

               sl[nr] = 0.0D;
               npe -= rpe;


             }


           }
           else if (sl[i20] < fsl) {




             double esl = fsl - sl[i20];
             if (esl >= npe) {

               sl[i20] = sl[i20] + npe;

               break;
             }

             sl[i20] = fsl;
             npe -= esl;
           }
         }









         double hp = precip[i19] / 2.0D; int i21;
         for (i21 = 1; i21 <= 64; i21++) {
           if (sl[i21] < fsl) {




             double esl = fsl - sl[i21];
             if (esl >= hp) {

               sl[i21] = sl[i21] + hp;
               if (gogr != 0);


               break;
             }


             sl[i21] = fsl;
             hp -= esl;
             if (gogr != 0);
           }
         }








         zsw = 0;
         lp = precip[i19] / 2.0D;
         npe = (lp - mpe[i19]) / 2.0D;
         if (npe <= 0.0D) {

           npe = 0.0D - npe;
         } else {
           zsw = -1;
         }



         for (i21 = 1; i21 <= 64; i21++) {
           if (zsw == 0) {

             int nr = BASICSimulationModelConstants.dp[i21 - 1];
             if (sl[nr] > 0.0D)
             {



               double rpe = sl[nr] * BASICSimulationModelConstants.dr[i21 - 1];
               if (npe <= rpe) {

                 sl[nr] = sl[nr] - npe / BASICSimulationModelConstants.dr[i21 - 1];
                 npe = 0.0D;

                 break;
               }

               sl[nr] = 0.0D;
               npe -= rpe;


             }


           }
           else if (sl[i21] < fsl) {




             double esl = fsl - sl[i21];
             if (esl >= npe) {

               sl[i21] = sl[i21] + npe;

               break;
             }

             sl[i21] = fsl;
             npe -= esl;
           }
         }
       }












       double tmoi = 0.0D;
       for (int it = 1; it <= 64; it++) {
         tmoi += sl[it];
       }



       if (Math.abs(tmoi - prmo) < prmo / 100.0D) {
         break;
       }


       prmo = tmoi;
     }






     gogr = -1;



     for (int i10 = 1; i10 <= 3; i10++) {
       cc[i10] = false;
     }

     boolean[] pc = new boolean[7]; int i12;
     for (i12 = 1; i12 <= 6; i12++) {
       pc[i12] = false;
     }

     pc[1] = (sl[9] <= 0.0D);
     pc[2] = (sl[17] <= 0.0D);
     pc[3] = (sl[25] <= 0.0D);
     cc[1] = (pc[1] && pc[2] && pc[3]);
     cc[2] = (!cc[1] && (pc[1] || pc[2] || pc[3]));
     pc[4] = (sl[9] > 0.0D);
     pc[5] = (sl[17] > 0.0D);
     pc[6] = (sl[25] > 0.0D);
     cc[3] = (pc[4] && pc[5] && pc[6]);
                                                  
     int timesi = 0;
     for (i12 = 1; i12 <= 3; ) {
       if (!cc[i12]) {
         i12++; continue;
       }
       k = i12;
                                                    timesi++;
                                                    if (timesi > cc.length) {
                                                      break;
                                                    }
     }

                                                    










     for (int im = 1; im <= 12; im++) {



       int[] dmc = new int[4];

       for (int i19 = 1; i19 <= 3; i19++) {
         dmc[i19] = 0;
         cc[i19] = false;
       }

       int zsw = 0;
       int dpmc = 0;
       int pmc = k;
       int igmc = 0;

       double lp = precip[im] / 2.0D;
       double npe = (lp - mpe[im]) / 2.0D;
       double cnpe = 0.0D;
       boolean skipi3Loop = false;

       if (npe < 0.0D) {
         npe = -npe;
         cnpe = npe;
       } else if (npe == 0.0D) {

         skipi3Loop = true;
       } else {
         zsw = -1;
         cnpe = npe;
       }


       if (!skipi3Loop) {
         for (int i23 = 1; i23 <= 64; i23++) {
           if (zsw == 0) {

             if (npe <= 0.0D) {
               break;
             }


             int nr = BASICSimulationModelConstants.dp[i23 - 1];
             if (sl[nr] > 0.0D)
             {



               double rpd = sl[nr] * BASICSimulationModelConstants.dr[i23 - 1];
               if (npe <= rpd) {

                 sl[nr] = sl[nr] - npe / BASICSimulationModelConstants.dr[i23 - 1];
                 npe = 0.0D;
               }
               else {

                 sl[nr] = 0.0D;
                 npe -= rpd;
               }

               int i24;

               for (i24 = 1; i24 <= 3; i24++) {
                 cc[i24] = false;
               }

               for (i24 = 1; i24 <= 6; i24++) {
                 pc[i24] = false;
               }

               pc[1] = (sl[9] <= 0.0D);
               pc[2] = (sl[17] <= 0.0D);
               pc[3] = (sl[25] <= 0.0D);
               cc[1] = (pc[1] && pc[2] && pc[3]);
               cc[2] = (!cc[1] && (pc[1] || pc[2] || pc[3]));
               pc[4] = (sl[9] > 0.0D);
               pc[5] = (sl[17] > 0.0D);
               pc[6] = (sl[25] > 0.0D);
               cc[3] = (pc[4] && pc[5] && pc[6]);

                                                                                timesi = 0;
               for (i24 = 1; i24 <= 3; ) {
                 if (!cc[i24]) {
                   i24++; continue;
                 }
                                                                                  timesi++;
                 k = i24;
                                                                                  if(timesi > cc.length) {
                                                                                    break;
                                                                                  }
               }





               if (zsw != 0);









               int kk = k;
               k = pmc;
               if (kk != pmc)
               {



                 if (npe <= 0.0D) {
                   break;
                 }




                 double rpe = cnpe - npe;
                 dmc[k] = (int)(15.0D * rpe / cnpe) - dpmc;
                 igmc = dmc[k];
                 dpmc = dmc[k] + dpmc;
                 dmc[k] = 0;



                 int i25 = 0;
                 int i26 = 0;
                 ie += igmc;
                 for (int i27 = ib; i27 <= ie; i27++) {
                   iday[i27] = k;

                   if (i27 > 30) {
                     i25 = i27 % 30 - 1;
                   } else {
                     i25 = i27;
                   }

                   i26 = i27 / 30;
                   if (i27 % 30 == 0) {
                     i26--;
                   }

                   if (i25 == -1) {
                     i25 = 29;
                   }

                   if (i26 < 0) {
                     i26 = 0;
                   }
                 }


                 ib = ie + 1;
                 nd[k] = nd[k] + igmc;


                 pmc = kk;
                 k = kk;
               }

             }

           } else {

             if (npe <= 0.0D) {
               break;
             }


             if (sl[i23] < fsl) {





               double esl = fsl - sl[i23];
               if (esl >= npe) {

                 sl[i23] = sl[i23] + npe;
                 npe = 0.0D;
               } else {

                 sl[i23] = fsl;

                 npe -= esl;
               }


               int i24;

               for (i24 = 1; i24 <= 3; i24++) {
                 cc[i24] = false;
               }

               for (i24 = 1; i24 <= 6; i24++) {
                 pc[i24] = false;
               }

               pc[1] = (sl[9] <= 0.0D);
               pc[2] = (sl[17] <= 0.0D);
               pc[3] = (sl[25] <= 0.0D);
               cc[1] = (pc[1] && pc[2] && pc[3]);
               cc[2] = (!cc[1] && (pc[1] || pc[2] || pc[3]));
               pc[4] = (sl[9] > 0.0D);
               pc[5] = (sl[17] > 0.0D);
               pc[6] = (sl[25] > 0.0D);
               cc[3] = (pc[4] && pc[5] && pc[6]);

                                                                                    timesi = 0;
               for (i24 = 1; i24 <= 3; ) {
                 if (!cc[i24]) {
                   i24++; continue;
                 }
                                                                                      timesi++;
                 k = i24;
                                                                                      if (timesi > cc.length) {
                                                                                        break;
                                                                                      }
                                                                                      
               }





               if (zsw != 0);









               int kk = k;
               k = pmc;
               if (kk != pmc) {




                 if (npe <= 0.0D) {
                   break;
                 }




                 double rpe = cnpe - npe;
                 dmc[k] = (int)(15.0D * rpe / cnpe) - dpmc;
                 igmc = dmc[k];
                 dpmc = dmc[k] + dpmc;
                 dmc[k] = 0;



                 int i25 = 0;
                 int i26 = 0;
                 ie += igmc;
                 for (int i27 = ib; i27 <= ie; i27++) {
                   iday[i27] = k;

                   if (i27 > 30) {
                     i25 = i27 % 30 - 1;
                   } else {
                     i25 = i27;
                   }

                   i26 = i27 / 30;
                   if (i27 % 30 == 0) {
                     i26--;
                   }

                   if (i25 == -1) {
                     i25 = 29;
                   }

                   if (i26 < 0) {
                     i26 = 0;
                   }
                 }


                 ib = ie + 1;
                 nd[k] = nd[k] + igmc;


                 pmc = kk;
                 k = kk;
               }
             }
           }
         }
       }




       dmc[k] = 15 - dpmc;
       igmc = dmc[k];
       dmc[k] = 0;



       int ii = 0;
       int mm = 0;
       ie += igmc;
       for (int i20 = ib; i20 <= ie; i20++) {
         iday[i20] = k;

         if (i20 > 30) {
           ii = i20 % 30 - 1;
         } else {
           ii = i20;
         }

         mm = i20 / 30;
         if (i20 % 30 == 0) {
           mm--;
         }

         if (ii == -1) {
           ii = 29;
         }

         if (mm < 0) {
           mm = 0;
         }
       }


       ib = ie + 1;
       nd[k] = nd[k] + igmc;






       double hp = precip[im] / 2.0D;
       for (int i22 = 1; i22 <= 64; i22++) {
         if (sl[i22] < fsl) {




           double esl = fsl - sl[i22];
           if (esl >= hp) {

             sl[i22] = sl[i22] + hp;
             if (gogr != 0);


             break;
           }


           sl[i22] = fsl;
           hp -= esl;
           if (gogr != 0);
         }
       }





       int i21;




       for (i21 = 1; i21 <= 3; i21++) {
         cc[i21] = false;
       }

       for (i21 = 1; i21 <= 6; i21++) {
         pc[i21] = false;
       }

       pc[1] = (sl[9] <= 0.0D);
       pc[2] = (sl[17] <= 0.0D);
       pc[3] = (sl[25] <= 0.0D);
       cc[1] = (pc[1] && pc[2] && pc[3]);
       cc[2] = (!cc[1] && (pc[1] || pc[2] || pc[3]));
       pc[4] = (sl[9] > 0.0D);
       pc[5] = (sl[17] > 0.0D);
       pc[6] = (sl[25] > 0.0D);
       cc[3] = (pc[4] && pc[5] && pc[6]);
       timesi = 0;
       for (i21 = 1; i21 <= 3; ) {
         if (!cc[i21]) {
           i21++; continue;
         }
                                                                        timesi++;
         k = i21;
                                                                        
                                                                        if (timesi > cc.length) {
                                                                          break;
                                                                        }
       }





       for (i21 = 1; i21 <= 3; i21++) {
         dmc[i21] = 0;
         cc[i21] = false;
       }

       zsw = 0;
       dpmc = 0;
       pmc = k;

       lp = precip[im] / 2.0D;
       npe = (lp - mpe[im]) / 2.0D;
       cnpe = 0.0D;
       skipi3Loop = false;

       if (npe < 0.0D) {
         npe = -npe;
         cnpe = npe;
       } else if (npe == 0.0D) {

         skipi3Loop = true;
       } else {
         zsw = -1;
         cnpe = npe;
       }


       if (!skipi3Loop) {
         for (int i23 = 1; i23 <= 64; i23++) {
           if (zsw == 0) {

             if (npe <= 0.0D) {
               break;
             }


             int nr = BASICSimulationModelConstants.dp[i23 - 1];
             if (sl[nr] > 0.0D)
             {



               double rpd = sl[nr] * BASICSimulationModelConstants.dr[i23 - 1];
               if (npe <= rpd) {

                 sl[nr] = sl[nr] - npe / BASICSimulationModelConstants.dr[i23 - 1];
                 npe = 0.0D;
               }
               else {

                 sl[nr] = 0.0D;
                 npe -= rpd;
               }

               int i24;

               for (i24 = 1; i24 <= 3; i24++) {
                 cc[i24] = false;
               }

               for (i24 = 1; i24 <= 6; i24++) {
                 pc[i24] = false;
               }

               pc[1] = (sl[9] <= 0.0D);
               pc[2] = (sl[17] <= 0.0D);
               pc[3] = (sl[25] <= 0.0D);
               cc[1] = (pc[1] && pc[2] && pc[3]);
               cc[2] = (!cc[1] && (pc[1] || pc[2] || pc[3]));
               pc[4] = (sl[9] > 0.0D);
               pc[5] = (sl[17] > 0.0D);
               pc[6] = (sl[25] > 0.0D);
               cc[3] = (pc[4] && pc[5] && pc[6]);
               timesi = 0;
               for (i24 = 1; i24 <= 3; ) {
                 if (!cc[i24]) {
                   i24++; continue;
                 }
                 timesi++;
                                                                                              k = i24;
                                                                                              if (timesi > cc.length) {
                                                                                                break;
                                                                                              }
               }





               if (zsw != 0);









               int kk = k;
               k = pmc;
               if (kk != pmc)
               {



                 if (npe <= 0.0D) {
                   break;
                 }




                 double rpe = cnpe - npe;
                 dmc[k] = (int)(15.0D * rpe / cnpe) - dpmc;
                 igmc = dmc[k];
                 dpmc = dmc[k] + dpmc;
                 dmc[k] = 0;



                 ii = 0;
                 mm = 0;
                 ie += igmc;
                 for (int i25 = ib; i25 <= ie; i25++) {
                   iday[i25] = k;

                   if (i25 > 30) {
                     ii = i25 % 30 - 1;
                   } else {
                     ii = i25;
                   }

                   mm = i25 / 30;
                   if (i25 % 30 == 0) {
                     mm--;
                   }

                   if (ii == -1) {
                     ii = 29;
                   }

                   if (mm < 0) {
                     mm = 0;
                   }
                 }


                 ib = ie + 1;
                 nd[k] = nd[k] + igmc;


                 pmc = kk;
                 k = kk;
               }

             }

           } else {

             if (npe <= 0.0D) {
               break;
             }


             if (sl[i23] < fsl) {





               double esl = fsl - sl[i23];
               if (esl >= npe) {

                 sl[i23] = sl[i23] + npe;
                 npe = 0.0D;
               } else {

                 sl[i23] = fsl;

                 npe -= esl;
               }


               int i24;

               for (i24 = 1; i24 <= 3; i24++) {
                 cc[i24] = false;
               }

               for (i24 = 1; i24 <= 6; i24++) {
                 pc[i24] = false;
               }

               pc[1] = (sl[9] <= 0.0D);
               pc[2] = (sl[17] <= 0.0D);
               pc[3] = (sl[25] <= 0.0D);
               cc[1] = (pc[1] && pc[2] && pc[3]);
               cc[2] = (!cc[1] && (pc[1] || pc[2] || pc[3]));
               pc[4] = (sl[9] > 0.0D);
               pc[5] = (sl[17] > 0.0D);
               pc[6] = (sl[25] > 0.0D);
               cc[3] = (pc[4] && pc[5] && pc[6]);
               timesi = 0;
               for (i24 = 1; i24 <= 3; ) {
                 if (!cc[i24]) {
                   i24++; continue;
                 }
                                                                                                  timesi++;
                 k = i24;
                                                                                                  if (timesi > cc.length) {
                                                                                                    break;
                                                                                                  }
               }





               if (zsw != 0);









               int kk = k;
               k = pmc;
               if (kk != pmc) {




                 if (npe <= 0.0D) {
                   break;
                 }




                 double rpe = cnpe - npe;
                 dmc[k] = (int)(15.0D * rpe / cnpe) - dpmc;
                 igmc = dmc[k];
                 dpmc = dmc[k] + dpmc;
                 dmc[k] = 0;



                 ii = 0;
                 mm = 0;
                 ie += igmc;
                 for (int i25 = ib; i25 <= ie; i25++) {
                   iday[i25] = k;

                   if (i25 > 30) {
                     ii = i25 % 30 - 1;
                   } else {
                     ii = i25;
                   }

                   mm = i25 / 30;
                   if (i25 % 30 == 0) {
                     mm--;
                   }

                   if (ii == -1) {
                     ii = 29;
                   }

                   if (mm < 0) {
                     mm = 0;
                   }
                 }


                 ib = ie + 1;
                 nd[k] = nd[k] + igmc;


                 pmc = kk;
                 k = kk;
               }
             }
           }
         }
       }




       dmc[k] = 15 - dpmc;
       igmc = dmc[k];
       dmc[k] = 0;



       ii = 0;
       mm = 0;
       ie += igmc;
       for (i21 = ib; i21 <= ie; i21++) {
         iday[i21] = k;

         if (i21 > 30) {
           ii = i21 % 30 - 1;
         } else {
           ii = i21;
         }

         mm = i21 / 30;
         if (i21 % 30 == 0) {
           mm--;
         }

         if (ii == -1) {
           ii = 29;
         }

         if (mm < 0) {
           mm = 0;
         }
       }


       ib = ie + 1;
       nd[k] = nd[k] + igmc;
     }








     int sn = 0;
     int kj = 0;
     int ia = 0;
     int iz = 0;
     int[] nj = new int[14];
     double crr = 0.0D;
     boolean[] c = new boolean[15];
     boolean tempUnderFive = false;
     boolean zwt = false;

     for (int i13 = 1; i13 <= 12; i13++) {

       if (temperature[i13] < 5.0D) {

         tempUnderFive = true;

         break;
       }
     }

     boolean skipTo890 = false;
     double t13 = 0.0D;

     if (tempUnderFive) {

       t13 = temperature[1];
       for (int it = 1; it <= 11; it++) {
         if (temperature[it] == 5.0D && temperature[it + 1] == 5.0D) {
           temperature[it] = 5.01D;
         }
       }
       if (temperature[12] == 5.0D && t13 == 5.0D) {
         temperature[12] = 5.01D;
       }
       crr = 5.0D;

       int i19;

       for (i19 = 1; i19 <= 12; i19++) {
         nj[i19] = 0;
       }

       zwt = false;
       kj = 1;
       ia = 30;
       iz = 30;

       if (crr != 8.0D) {
         ia = 36;
         iz = 25;
       }

       for (i19 = 1; i19 <= 12; i19++) {
         for (int i22 = 1; i22 <= 14; i22++) {
           c[i22] = false;
         }



         int m0 = i19 - 1;
         int m1 = i19;
         int m2 = i19 + 1;
         if (m2 > 12) {
           m2 -= 12;
         }
         if (m0 < 1) {
           m0 += 12;
         }

         c[1] = (temperature[m1] < crr);
         c[2] = (temperature[m2] > crr);
         c[3] = (temperature[m0] < crr);
         c[4] = (temperature[m1] == crr);
         c[5] = (temperature[m2] > crr);
         c[6] = (temperature[m2] < crr);
         c[7] = (temperature[m1] > crr);
         c[8] = (temperature[m0] > crr);
         c[9] = (c[1] && c[2]);
         c[10] = (c[3] && c[4] && c[5]);
         c[11] = (c[9] || c[10]);
         c[12] = (c[6] && c[7]);
         c[13] = (c[8] && c[6] && c[4]);
         c[14] = (c[12] || c[13]);

         if (c[11]) {

           nj[kj] = m0 * 30 + ia + (int)(30.0D * (crr - temperature[m1]) / (temperature[m2] - temperature[m1]));

           if (nj[kj] > 360) {
             nj[kj] = nj[kj] - 360;
           }
           kj++;
           zwt = true;

         }
         else if (c[14]) {

           nj[kj] = m0 * 30 + iz + (int)(30.0D * (temperature[m1] - crr) / (temperature[m1] - temperature[m2]));

           if (nj[kj] > 360) {
             nj[kj] = nj[kj] - 360;
           }
           kj++;
           zwt = false;
         }
       }








       if (zwt) {

         int le = kj - 2;
         int npro = nj[1];
         for (int i22 = 1; i22 <= le; i22++) {
           nj[i22] = nj[i22 + 1];
         }
         nj[le + 1] = npro;
       }



       int npj = (kj - 1) / 2;
       int[] nbj = new int[7];
       int[] nej = new int[7]; int i21;
       for (i21 = 1; i21 <= npj; i21++) {
         ib = 2 * i21 - 1;
         ie = 2 * i21;
         nbj[i21] = nj[ib];
         nej[i21] = nj[ie];
       }



       for (i21 = 1; i21 <= 6; i21++) {
         nbd[i21] = nbj[i21];
         ned[i21] = nej[i21];
       }

       np = npj;
       tc = -1;
       int i20 = 0;
       double ir = 0.0D;
       double d1 = 0.0D;
       double d2 = 0.0D;

       if (np == 0) {

         if (wt <= 5.0D)
         {


           lt5c = 0.0D;
           nbd[1] = -1.0D;
           id5c = 0.0D;
           for (int ik = 1; ik <= 3; ik++) {
             nsd[ik] = 0.0D;
           }
           tc = 0;
           skipTo890 = true;
         }

       } else {

         for (int i22 = 1; i22 <= np; i22++) {
           ib = (int)nbd[i22];
           if (nbd[i22] < ned[i22]) {

             ir = ned[i22] - nbd[i22] + 1.0D;
             i20 = ib;
             d1 = ir;
           }
           else {

             ir = 361.0D - nbd[i22] + ned[i22];
             i20 = ib;
             d1 = ir;
           }



           for (int ij = 1; ij <= 3; ij++) {
             nzd[ij] = 0.0D;
           }

           d2 = i20 + d1 - 1.0D;

           for (int l = i20; l <= d2; l++) {
             int i23 = l;
             if (i23 > 360) {
               i23 -= 360;
             }
             int ik = iday[i23];
             nzd[ik] = nzd[ik] + 1.0D;
           }


          // TODO: Investigate this section.  Pittsburgh 1950 causes 
          //   this region of code to be called twice instead of once.
          //   In normal datasets this region is only called once.
          //   Problem dataset looked correct on the first pass, but
          //   the second pass had a days per year greater than 360.
           if (!hasRunAlready) {
             for (int i23 = 1; i23 <= 3; i23++) {
               nsd[i23] = nsd[i23] + nzd[i23];
               hasRunAlready = true;
             }
           }

           lt5c += ir;
         }




         id5c = nbd[1];
         skipTo890 = true;
       }
     }









     if (!skipTo890) {
       tc = 0;
       for (int i19 = 1; i19 <= 3; i19++) {
         nsd[i19] = nd[i19];
       }
       lt5c = 360.0D;
       id5c = 0.0D;
     }



     int[] ncpm = new int[4];
     for (int ic = 1; ic <= 2; ic++) {
       ncpm[ic] = 0;
     }

     boolean tempUnder8C = false;
     for (int i14 = 1; i14 <= 12; i14++) {
       if (temperature[i14] < 8.0D) {

         tempUnder8C = true;

         break;
       }
     }
     if (tempUnder8C) {

       t13 = temperature[1];
       for (int it = 1; it <= 11; it++) {
         if (temperature[it] == 8.0D && temperature[it + 1] == 8.0D) {
           temperature[it] = 8.01D;
         }
       }

       crr = 8.0D;
       if (temperature[12] == 8.0D && t13 == 8.0D) {
         temperature[12] = 8.01D;
       }

       int i19;

       for (i19 = 1; i19 <= 12; i19++) {
         nj[i19] = 0;
       }

       zwt = false;
       kj = 1;
       ia = 30;
       iz = 30;

       if (crr != 8.0D) {
         ia = 36;
         iz = 25;
       }

       for (i19 = 1; i19 <= 12; i19++) {
         for (int i21 = 1; i21 <= 14; i21++) {
           c[i21] = false;
         }



         int m0 = i19 - 1;
         int m1 = i19;
         int m2 = i19 + 1;
         if (m2 > 12) {
           m2 -= 12;
         }
         if (m0 < 1) {
           m0 += 12;
         }

         c[1] = (temperature[m1] < crr);
         c[2] = (temperature[m2] > crr);
         c[3] = (temperature[m0] < crr);
         c[4] = (temperature[m1] == crr);
         c[5] = (temperature[m2] > crr);
         c[6] = (temperature[m2] < crr);
         c[7] = (temperature[m1] > crr);
         c[8] = (temperature[m0] > crr);
         c[9] = (c[1] && c[2]);
         c[10] = (c[3] && c[4] && c[5]);
         c[11] = (c[9] || c[10]);
         c[12] = (c[6] && c[7]);
         c[13] = (c[8] && c[6] && c[4]);
         c[14] = (c[12] || c[13]);

         if (c[11]) {

           nj[kj] = m0 * 30 + ia + (int)(30.0D * (crr - temperature[m1]) / (temperature[m2] - temperature[m1]));

           if (nj[kj] > 360) {
             nj[kj] = nj[kj] - 360;
           }
           kj++;
           zwt = true;

         }
         else if (c[14]) {

           nj[kj] = m0 * 30 + iz + (int)(30.0D * (temperature[m1] - crr) / (temperature[m1] - temperature[m2]));

           if (nj[kj] > 360) {
             nj[kj] = nj[kj] - 360;
           }
           kj++;
           zwt = false;
         }
       }








       if (zwt) {

         int le = kj - 2;
         int npro = nj[1];
         for (int i21 = 1; i21 <= le; i21++) {
           nj[i21] = nj[i21 + 1];
         }
         nj[le + 1] = npro;
       }



       int npj = (kj - 1) / 2;
       int[] nbj = new int[7];
       int[] nej = new int[7]; int i20;
       for (i20 = 1; i20 <= npj; i20++) {
         ib = 2 * i20 - 1;
         ie = 2 * i20;
         nbj[i20] = nj[ib];
         nej[i20] = nj[ie];
       }



       for (i20 = 1; i20 <= 6; i20++) {
         nbd8[i20] = nbj[i20];
         ned8[i20] = nej[i20];
       }

       np8 = npj;
       tc = -1;



       if (np8 != 0)
       {


         for (i20 = 1; i20 <= np8; i20++) {
           ib = (int)nbd8[i20];
           double ir = 0.0D;
           if (nbd8[i20] < ned8[i20]) {

             ir = ned8[i20] - nbd8[i20] + 1.0D;
           }
           else {

             ir = 361.0D - nbd8[i20] + ned8[i20];
           }



           msw = 0;
           sib = ib;
           sir = ir;
           x = 1;
           swm = (msw != 0);



           int[] arrayOfInt = new int[5];
           arrayOfInt[x] = 0;
           int i21 = 0;
           int i22 = 0;
           int i23 = 0;
           max = 0;
           double d1 = sib + sir - 1.0D;

           for (int i24 = sib; i24 <= d1; i24++) {
             int n1 = i24 + 1;
             if (n1 > 360) {
               n1 -= 360;
             }

             if (i24 > 360) {
               i23 = i24 - 360;
             } else {
               i23 = i24;
             }

             if (swm) {

               if (iday[i23] == x)
               {
                 if (iday[i23] != iday[n1])
                 {
                   if (i22 != 0)
                   {
                     arrayOfInt[x] = arrayOfInt[x] + 1;
                     if (arrayOfInt[x] > max)
                     {
                       max = arrayOfInt[x];

                       arrayOfInt[x] = 0;
                       i22 = 0;

                     }
                     else
                     {
                       arrayOfInt[x] = 0;
                       i22 = 0;

                     }


                   }

                 }
                 else
                 {
                   arrayOfInt[x] = arrayOfInt[x] + 1;
                   i22 = -1;


                 }


               }

             }
             else if (iday[i23] != x) {

               if (iday[n1] == x) {

                 if (i22 != 0)
                 {
                   arrayOfInt[x] = arrayOfInt[x] + 1;
                   if (arrayOfInt[x] > max)
                   {
                     max = arrayOfInt[x];

                     arrayOfInt[x] = 0;
                     i22 = 0;

                   }
                   else
                   {
                     arrayOfInt[x] = 0;
                     i22 = 0;

                   }


                 }

               }
               else {

                 arrayOfInt[x] = arrayOfInt[x] + 1;
                 i22 = -1;
               }
             }
           }







           if (i22 != 0) {
             i21 = arrayOfInt[x];
           }

           if (i21 > max) {
             max = i21;
           }



           icon = max;
           if (ncpm[2] <= icon) {




             ncpm[2] = icon;
             lt8c = (int)ir;
             id8c = (int)nbd8[i20];
           }
         }



         sn = -1;
       }

     } else {

       msw = 0;

       tc = -1;
       lt8c = 360;
       id8c = 0;
     }




     sib = 1;
     sir = 720.0D;
     x = 1;
     swm = (msw != 0);



     int[] ns = new int[5];
     ns[x] = 0;
     int ifin = 0;
     int sw = 0;
     int si = 0;
     max = 0;
     double siz = sib + sir - 1.0D;

     for (int i16 = sib; i16 <= siz; i16++) {
       int n1 = i16 + 1;
       while (n1 > 360) {
         n1 -= 360;
       }

       if (i16 > 360) {
         si = i16 - 360;
       } else {
         si = i16;
       }

       if (swm) {

         if (iday[si] == x)
         {
           if (iday[si] != iday[n1])
           {
             if (sw != 0)
             {
               ns[x] = ns[x] + 1;
               if (ns[x] > max)
               {
                 max = ns[x];

                 ns[x] = 0;
                 sw = 0;

               }
               else
               {
                 ns[x] = 0;
                 sw = 0;

               }


             }

           }
           else
           {
             ns[x] = ns[x] + 1;
             sw = -1;


           }


         }

       }
       else if (iday[si] != x) {

         if (iday[n1] == x) {

           if (sw != 0)
           {
             ns[x] = ns[x] + 1;
             if (ns[x] > max)
             {
               max = ns[x];

               ns[x] = 0;
               sw = 0;

             }
             else
             {
               ns[x] = 0;
               sw = 0;

             }


           }

         }
         else {

           ns[x] = ns[x] + 1;
           sw = -1;
         }
       }
     }







     if (sw != 0) {
       ifin = ns[x];
     }

     if (ifin > max) {
       max = ifin;
     }



     icon = max;
     if (icon > 360) {
       icon = 360;
     }
     ncpm[1] = icon;
     if (sn == 0)
     {
       ncpm[2] = ncpm[1];
     }



     sn = 0;
     msw = -1;
     int i15 = 0;

     if (northsouth == 'N') {
       ib = 181;
       i15 = 1;
     } else {
       ib = 1;
       i15 = 181;
     }

     sib = ib;
     sir = 120.0D;
     x = 1;
     swm = (msw != 0);



     ns = new int[5];
     ns[x] = 0;
     ifin = 0;
     sw = 0;
     si = 0;
     max = 0;
     siz = sib + sir - 1.0D;
     int i17;
     for (i17 = sib; i17 <= siz; i17++) {
       int n1 = i17 + 1;
       if (n1 > 360) {
         n1 -= 360;
       }

       if (i17 > 360) {
         si = i17 - 360;
       } else {
         si = i17;
       }

       if (swm) {

         if (iday[si] == x)
         {
           if (iday[si] != iday[n1])
           {
             if (sw != 0)
             {
               ns[x] = ns[x] + 1;
               if (ns[x] > max)
               {
                 max = ns[x];

                 ns[x] = 0;
                 sw = 0;

               }
               else
               {
                 ns[x] = 0;
                 sw = 0;

               }


             }

           }
           else
           {
             ns[x] = ns[x] + 1;
             sw = -1;


           }


         }

       }
       else if (iday[si] != x) {

         if (iday[n1] == x) {

           if (sw != 0)
           {
             ns[x] = ns[x] + 1;
             if (ns[x] > max)
             {
               max = ns[x];

               ns[x] = 0;
               sw = 0;

             }
             else
             {
               ns[x] = 0;
               sw = 0;

             }


           }

         }
         else {

           ns[x] = ns[x] + 1;
           sw = -1;
         }
       }
     }







     if (sw != 0) {
       ifin = ns[x];
     }

     if (ifin > max) {
       max = ifin;
     }



     nccd = max;
     sib = i15;
     sir = 120.0D;
     x = 3;
     swm = (msw != 0);



     ns = new int[5];
     ns[x] = 0;
     ifin = 0;
     sw = 0;
     si = 0;
     max = 0;
     siz = sib + sir - 1.0D;

     for (i17 = sib; i17 <= siz; i17++) {
       int n1 = i17 + 1;
       if (n1 > 360) {
         n1 -= 360;
       }

       if (i17 > 360) {
         si = i17 - 360;
       } else {
         si = i17;
       }

       if (swm) {

         if (iday[si] == x)
         {
           if (iday[si] != iday[n1])
           {
             if (sw != 0)
             {
               ns[x] = ns[x] + 1;
               if (ns[x] > max)
               {
                 max = ns[x];

                 ns[x] = 0;
                 sw = 0;

               }
               else
               {
                 ns[x] = 0;
                 sw = 0;

               }


             }

           }
           else
           {
             ns[x] = ns[x] + 1;
             sw = -1;


           }


         }

       }
       else if (iday[si] != x) {

         if (iday[n1] == x) {

           if (sw != 0)
           {
             ns[x] = ns[x] + 1;
             if (ns[x] > max)
             {
               max = ns[x];

               ns[x] = 0;
               sw = 0;

             }
             else
             {
               ns[x] = 0;
               sw = 0;

             }


           }

         }
         else {

           ns[x] = ns[x] + 1;
           sw = -1;
         }
       }
     }







     if (sw != 0) {
       ifin = ns[x];
     }

     if (ifin > max) {
       max = ifin;
     }



     nccm = max;
     int tu = 0;
     boolean skipTo1420 = false;

     if (nd[3] == 360.0D) {

       ncsm = 180;
       ncwm = 180;
       ncsp = 180;
       ncwp = 180;
       ntsu[3] = 180.0D;
       ntwi[3] = 180.0D;

       skipTo1420 = true;
     } else if (nd[1] == 360.0D) {

       skipTo1420 = true;
     } else if (nd[1] == 0.0D) {

       tu = -1;
       sib = ib;
       x = 3;
       swm = (tu != 0);



       ns = new int[5];
       ns[x] = 0;
       ifin = 0;
       sw = 0;
       si = 0;
       max = 0;
       siz = sib + sir - 1.0D;
       int i19;
       for (i19 = sib; i19 <= siz; i19++) {
         int n1 = i19 + 1;
         if (n1 > 360) {
           n1 -= 360;
         }

         if (i19 > 360) {
           si = i19 - 360;
         } else {
           si = i19;
         }

         if (swm) {

           if (iday[si] == x)
           {
             if (iday[si] != iday[n1])
             {
               if (sw != 0)
               {
                 ns[x] = ns[x] + 1;
                 if (ns[x] > max)
                 {
                   max = ns[x];

                   ns[x] = 0;
                   sw = 0;

                 }
                 else
                 {
                   ns[x] = 0;
                   sw = 0;

                 }


               }

             }
             else
             {
               ns[x] = ns[x] + 1;
               sw = -1;


             }


           }

         }
         else if (iday[si] != x) {

           if (iday[n1] == x) {

             if (sw != 0)
             {
               ns[x] = ns[x] + 1;
               if (ns[x] > max)
               {
                 max = ns[x];

                 ns[x] = 0;
                 sw = 0;

               }
               else
               {
                 ns[x] = 0;
                 sw = 0;

               }


             }

           }
           else {

             ns[x] = ns[x] + 1;
             sw = -1;
           }
         }
       }







       if (sw != 0) {
         ifin = ns[x];
       }

       if (ifin > max) {
         max = ifin;
       }



       ncsp = max;
       sib = i15;
       sir = 180.0D;
       swm = (tu != 0);



       ns = new int[5];
       ns[x] = 0;
       ifin = 0;
       sw = 0;
       si = 0;
       max = 0;
       siz = sib + sir - 1.0D;

       for (i19 = sib; i19 <= siz; i19++) {
         int n1 = i19 + 1;
         if (n1 > 360) {
           n1 -= 360;
         }

         if (i19 > 360) {
           si = i19 - 360;
         } else {
           si = i19;
         }

         if (swm) {

           if (iday[si] == x)
           {
             if (iday[si] != iday[n1])
             {
               if (sw != 0)
               {
                 ns[x] = ns[x] + 1;
                 if (ns[x] > max)
                 {
                   max = ns[x];

                   ns[x] = 0;
                   sw = 0;

                 }
                 else
                 {
                   ns[x] = 0;
                   sw = 0;

                 }


               }

             }
             else
             {
               ns[x] = ns[x] + 1;
               sw = -1;


             }


           }

         }
         else if (iday[si] != x) {

           if (iday[n1] == x) {

             if (sw != 0)
             {
               ns[x] = ns[x] + 1;
               if (ns[x] > max)
               {
                 max = ns[x];

                 ns[x] = 0;
                 sw = 0;

               }
               else
               {
                 ns[x] = 0;
                 sw = 0;

               }


             }

           }
           else {

             ns[x] = ns[x] + 1;
             sw = -1;
           }
         }
       }







       if (sw != 0) {
         ifin = ns[x];
       }

       if (ifin > max) {
         max = ifin;
       }



       ncwp = max;

     }
     else {


       tu = 0;
       sib = ib;
       sir = 180.0D;
       x = 1;
       swm = (tu != 0);


       ns = new int[5];
       ns[x] = 0;
       ifin = 0;
       sw = 0;
       si = 0;
       max = 0;
       siz = sib + sir - 1.0D;
       int i19;
       for (i19 = sib; i19 <= siz; i19++) {
         int n1 = i19 + 1;
         if (n1 > 360) {
           n1 -= 360;
         }

         if (i19 > 360) {
           si = i19 - 360;
         } else {
           si = i19;
         }

         if (swm) {

           if (iday[si] == x)
           {
             if (iday[si] != iday[n1])
             {
               if (sw != 0)
               {
                 ns[x] = ns[x] + 1;
                 if (ns[x] > max)
                 {
                   max = ns[x];

                   ns[x] = 0;
                   sw = 0;

                 }
                 else
                 {
                   ns[x] = 0;
                   sw = 0;

                 }


               }

             }
             else
             {
               ns[x] = ns[x] + 1;
               sw = -1;


             }


           }

         }
         else if (iday[si] != x) {

           if (iday[n1] == x) {

             if (sw != 0)
             {
               ns[x] = ns[x] + 1;
               if (ns[x] > max)
               {
                 max = ns[x];

                 ns[x] = 0;
                 sw = 0;

               }
               else
               {
                 ns[x] = 0;
                 sw = 0;

               }


             }

           }
           else {

             ns[x] = ns[x] + 1;
             sw = -1;
           }
         }
       }







       if (sw != 0) {
         ifin = ns[x];
       }

       if (ifin > max) {
         max = ifin;
       }


       ncsm = max;
       sib = i15;


       ns = new int[5];
       ns[x] = 0;
       ifin = 0;
       sw = 0;
       si = 0;
       max = 0;
       siz = sib + sir - 1.0D;

       for (i19 = sib; i19 <= siz; i19++) {
         int n1 = i19 + 1;
         if (n1 > 360) {
           n1 -= 360;
         }

         if (i19 > 360) {
           si = i19 - 360;
         } else {
           si = i19;
         }

         if (swm) {

           if (iday[si] == x)
           {
             if (iday[si] != iday[n1])
             {
               if (sw != 0)
               {
                 ns[x] = ns[x] + 1;
                 if (ns[x] > max)
                 {
                   max = ns[x];

                   ns[x] = 0;
                   sw = 0;

                 }
                 else
                 {
                   ns[x] = 0;
                   sw = 0;

                 }


               }

             }
             else
             {
               ns[x] = ns[x] + 1;
               sw = -1;


             }


           }

         }
         else if (iday[si] != x) {

           if (iday[n1] == x) {

             if (sw != 0)
             {
               ns[x] = ns[x] + 1;
               if (ns[x] > max)
               {
                 max = ns[x];

                 ns[x] = 0;
                 sw = 0;

               }
               else
               {
                 ns[x] = 0;
                 sw = 0;

               }


             }

           }
           else {

             ns[x] = ns[x] + 1;
             sw = -1;
           }
         }
       }







       if (sw != 0) {
         ifin = ns[x];
       }

       if (ifin > max) {
         max = ifin;
       }


       ncwm = max;
     }


     int jb = 0;
     int jr = 0;
     int je = 0;
     if (!skipTo1420) {

       jb = ib;
       jr = 180;


       for (int ij = 1; ij <= 3; ij++) {
         nzd[ij] = 0.0D;
       }

       je = jb + jr - 1;

       for (int l = jb; l <= je; l++) {
         int i20 = l;
         if (i20 > 360) {
           i20 -= 360;
         }
         int ik = iday[i20];
         nzd[ik] = nzd[ik] + 1.0D;
       }

       int i19;
       for (i19 = 1; i19 <= 3; i19++) {
         ntsu[i19] = nzd[i19];
       }
       for (i19 = 1; i19 <= 3; i19++) {
         ntwi[i19] = nd[i19] - ntsu[i19];
       }
     }



     int[] ntd = new int[365];

     if (!tempUnder8C)
     {


       for (int i19 = 1; i19 <= 360; i19++) { ntd[i19] = 56; }

     }
     if (tc != 0 || tu != 0) {


       char[] kl = new char[4];
       double[] kr = new double[25];
       int[] arrayOfInt = new int[25];
       int kt = 0;

       kl[0] = '$';
       kl[1] = '-';
       kl[2] = '5';
       kl[3] = '8';
       int i19;
       for (i19 = 1; i19 <= 24; i19++) {
         kr[i19] = 0.0D;
       }

       if (nbd[1] < 0.0D && nbd8[1] < 0.0D) {

         for (i19 = 1; i19 <= 360; i19++) {
           ntd[i19] = kl[1];
         }
       }
       else if (nbd[1] == 0.0D && nbd8[1] < 0.0D) {

         for (i19 = 1; i19 <= 360; i19++) {
           ntd[i19] = kl[2];
         }
       }
       else {

         if (nbd8[1] < 0.0D) {
           nbd8[1] = 0.0D;
         }

         for (i19 = 1; i19 <= 6; i19++) {
           kr[i19] = nbd[i19];
           arrayOfInt[i19] = 2;
         }

         for (i19 = 7; i19 <= 12; i19++) {
           kt = i19 - 6;
           if (ned[kt] != 0.0D) {




             kr[i19] = ned[kt] + 1.0D;
             if (kr[i19] > 360.0D) {
               kr[i19] = 1.0D;
             }
             arrayOfInt[i19] = 1;
           }
         }




         for (i19 = 13; i19 <= 18; i19++) {
           kt = i19 - 12;
           kr[i19] = nbd8[kt];
           arrayOfInt[i19] = 3;
         }

         for (i19 = 19; i19 <= 24; i19++) {
           kt = i19 - 18;
           if (ned8[kt] != 0.0D) {




             kr[i19] = ned8[kt] + 1.0D;
             if (kr[i19] > 360.0D) {
               kr[i19] = 1.0D;
             }
             arrayOfInt[i19] = 2;
           }
         }




         int nt = 23;
         int stt = 0;
         double itemp = 0.0D;
         int itm = 0;

         for (int i20 = 1; i20 <= 23; i20++) {
           stt = 0;

           for (int i24 = 1; i24 <= nt; i24++) {
             if (kr[i24] > kr[i24 + 1]) {




               itemp = kr[i24];
               itm = arrayOfInt[i24];
               kr[i24] = kr[i24 + 1];
               arrayOfInt[i24] = arrayOfInt[i24 + 1];
               kr[i24 + 1] = itemp;
               arrayOfInt[i24 + 1] = itm;
               stt = -1;
             }
           }




           if (stt == 0) {
             break;
           }


           nt--;
         }





         int ima = arrayOfInt[24];
         int nul = 0;

         for (int i21 = 1; i21 <= 24; i21++) {
           if (kr[i21] == 0.0D) {
             nul++;
           }
         }

         int kk = 0; int i22;
         for (i22 = 1; i22 <= 24; i22++) {
           kk = i22;
           int ipl = i22 + nul;
           if (ipl > 24) {
             break;
           }


           kr[i22] = kr[ipl];
           arrayOfInt[i22] = arrayOfInt[ipl];
         }





         for (i22 = kk; i22 <= 24; i22++) {
           kr[i22] = 0.0D;
         }

         if (kr[1] != 1.0D) {



           ie = (int)(kr[1] - 1.0D);
           for (i22 = 1; i22 <= ie; i22++) {
             ntd[i22] = kl[ima];
           }
         }



         int ns2 = 0;
         int nn = 24 - nul;
         for (int i23 = 1; i23 <= nn; i23++) {
           ns2 = arrayOfInt[i23];
           ib = (int)kr[i23];
           ie = (int)(kr[i23 + 1] - 1.0D);
           if (kr[i23 + 1] == 0.0D) {
             ie = 360;
           }

           for (int i24 = ib; i24 <= ie; i24++) {
             ntd[i24] = kl[ns2];
           }
         }
       }
     }







     String ans = " ";
     String div = " ";
     String q = " ";
     if (swt != 0) {
       ans = "Perudic";

       ncsm = 180;
       ncwm = 180;
       ncsp = 180;
       ncwp = 180;
       ntsu[3] = 180.0D;
       ntwi[3] = 180.0D;
     }
     else if (nsd[1] > lt5c / 2.0D && ncpm[2] < 90) {
       ans = "Aridic";

       if (nd[1] == 360.0D) {
         q = "Extreme";
       } else if (ncpm[2] <= 45) {
         q = "Typic";
       } else {
         q = "Weak";
       }

       div = new String(ans);
     }
     else if (tma < 22.0D && dif >= 5.0D && nccd >= 45 && nccm >= 45) {
       ans = "Xeric";

       if (nccd > 90) {
         q = "Dry";
       } else {
         q = "Typic";
       }

       div = new String(ans);
     }
     else if (nd[1] + nd[2] < 90.0D) {
       ans = "Udic";

       if (nd[1] + nd[2] < 30.0D) {
         q = "Typic";

         div = new String(ans);


       }
       else if (dif < 5.0D) {
         q = "Dry";
         div = "Tropudic";
       }
       else {

         div = "Tempudic";
         q = "Dry";
       }

     }
     else if (!trr.equalsIgnoreCase("pergelic") && !trr.equalsIgnoreCase("cryic")) {
       ans = "Ustic";

       if (dif >= 5.0D) {
         div = "Tempustic";

         if (nccm <= 45) {
           q = "Typic";
         } else if (nccd > 45) {
           q = "Xeric";
         } else {
           q = "Wet";
         }

       } else {

         if (ncpm[2] < 180) {
           q = "Aridic";
         } else if (ncpm[2] < 270) {
           q = "Typic";
         } else {
           q = "Udic";
         }

         div = "Tropustic";
       }
     } else {

       ans = "Undefined";
       div = new String(ans);
     }





     byte[] cc2Bytes = { 7, 0, 7, 0, 7 };
     String cc2 = new String(cc2Bytes); // TODO: value not used?
     int c2 = 0;






















     String flxFile = "\n";
     int i18;
     for (i18 = 1; i18 <= 12; i18++) {
       flxFile = flxFile + precip[i18] + "," + temperature[i18];
       flxFile = flxFile + "," + mpe[i18] + "\n";
     }

     flxFile = flxFile + "\"" + ans + "\"," + id5c + "," + lt5c + "," + id8c + "," + lt8c + ",";
     flxFile = flxFile + nccd + "," + nccm + "," + ncsm + "," + ncwm + ",\"" + div + "\",\"" + q + "\"\n";
     flxFile = flxFile + ncsp + "," + ncwp + "," + tc + "," + tu + "," + swt + "," + swp + "\n";

     for (i18 = 1; i18 < 360; i18++) {
       flxFile = flxFile + iday[i18] + ",";
     }
     flxFile = flxFile + iday[360] + "," + whc + "\n";

     for (i18 = 1; i18 <= 6; i18++) {
       flxFile = flxFile + nbd[i18] + "," + ned[i18] + "," + nbd8[i18] + "," + ned8[i18] + "\n";
     }

     for (i18 = 1; i18 <= 3; i18++) {
       flxFile = flxFile + nd[i18] + "," + nzd[i18] + "," + nsd[i18] + "," + ntsu[i18] + "," + ntwi[i18] + "\n";
     }

     flxFile = flxFile + ncpm[1] + "," + ncpm[2] + "\n";

     for (i18 = 1; i18 <= 5; i18++) {
       flxFile = flxFile + cd[i18] + "\n";
     }

     for (i18 = 1; i18 < 360; i18++) {
       flxFile = flxFile + (char)ntd[i18] + ",";
     }







     double awb = computeWaterBalance(precip, mpe, false, (northsouth == 'N'));
     double swb = computeWaterBalance(precip, mpe, true, (northsouth == 'N'));

     return new NewhallResults(arf, whc, mpe, nccd, nccm, ntd, iday, nd, nsd, ncpm, trr, ans, q, div, awb, swb, flxFile);
   }




   public static NewhallResults runSimulation(NewhallDataset dataset, double waterHoldingCapacity, double fc, double fcd) {
     boolean hasRunAlready = false;


     double elevation = dataset.getElevation(); // TODO: not used?
     if (!dataset.isMetric())
     {
/*   30 */       elevation *= 0.305D;
     }


     double[] temperature = new double[13];
     double[] precip = new double[13];
     for (int i = 1; i <= 12; i++) {
       temperature[i] = ((Double)dataset.getTemperature().get(i - 1)).doubleValue();
       precip[i] = ((Double)dataset.getPrecipitation().get(i - 1)).doubleValue();
     }
/*   40 */     if (!dataset.isMetric()) {
       double cv = 0.5555555555555556D;
       for (int i19 = 1; i19 <= 12; i19++) {

         temperature[i19] = cv * (temperature[i19] - 32.0D);

         precip[i19] = ((Double)dataset.getPrecipitation().get(i19 - 1)).doubleValue() * 25.4D;
       }
     }

/*   50 */     double[] upe = new double[13];
     double[] mpe = new double[13];
     double[] mwi = new double[13];



     for (int j = 1; j <= 12; j++) {
       if (temperature[j] > 0.0D) {
         double mwiValue = Math.pow(temperature[j] / 5.0D, 1.514D);
         mwi[j] = mwiValue;
       }
     }

     double swi = 0.0D; double[] arrayOfDouble1; int m; byte b;
     for (arrayOfDouble1 = mwi, m = arrayOfDouble1.length, b = 0; b < m; ) { Double mwiElement = Double.valueOf(arrayOfDouble1[b]);
       swi += mwiElement.doubleValue();

       b++; }


/*   70 */     double a = 6.75E-7D * swi * swi * swi - 7.71E-5D * swi * swi + 0.01792D * swi + 0.49239D;



     for (int i1 = 1; i1 <= 12; i1++) {
       if (temperature[i1] > 0.0D) {
         if (temperature[i1] < 26.5D) {
           double aBase = 10.0D * temperature[i1] / swi;
           upe[i1] = 16.0D * Math.pow(aBase, a);
         } else if (temperature[i1] >= 38.0D) {
/*   80 */           upe[i1] = 185.0D;
         } else {
           double[] zt = BASICSimulationModelConstants.zt;
           double[] zpe = BASICSimulationModelConstants.zpe;
           int kl = 0;
           int kk = 0;
           for (int ki = 1; ki <= 24; ki++) {
             kl = ki + 1;
             kk = ki;
             if (temperature[i1] >= zt[ki - 1] && temperature[i1] < zt[kl - 1]) {
/*   90 */               upe[i1] = zpe[kk - 1];


               break;
             }
           }
         }
       }
     }

/*  100 */     if (dataset.getNsHemisphere() == 'N') {

/*  102 */       int nrow = 0; int i19;
/*  103 */       for (i19 = 1; i19 <= 31 &&
/*  104 */         dataset.getLatitude() >= BASICSimulationModelConstants.rn[i19 - 1]; i19++)
       {



/*  109 */         nrow++;
       }



       for (i19 = 1; i19 <= 12; i19++) {
         if (upe[i19] > 0.0D)
         {



/*  120 */           mpe[i19] = upe[i19] * BASICSimulationModelConstants.inz[i19 - 1][nrow - 1];
         }
       }

     }
     else {

       int nrow = 0; int i19;
       for (i19 = 1; i19 <= 13 &&
         dataset.getLatitude() >= BASICSimulationModelConstants.rs[i19 - 1]; i19++)
       {

         nrow++;
       }




       if (nrow != 0) {

/*  140 */         for (i19 = 1; i19 <= 12; i19++) {
           if (upe[i19] > 0.0D)
           {

             if (nrow >= 13 || BASICSimulationModelConstants.fs[i19 - 1][nrow - 1] == BASICSimulationModelConstants.fs[i19 - 1][nrow]) {


               double cf = BASICSimulationModelConstants.fs[i19 - 1][nrow - 1];
               mpe[i19] = upe[i19] * cf;


             }
             else {


               double cf = (BASICSimulationModelConstants.fs[i19 - 1][nrow] - BASICSimulationModelConstants.fs[i19 - 1][nrow - 1]) * ((dataset.getLatitudeDegrees() - BASICSimulationModelConstants.rs[nrow - 1]) * 60.0D + dataset.getLatitudeMinutes()) / (BASICSimulationModelConstants.rs[nrow] - BASICSimulationModelConstants.rs[nrow - 1]) * 60.0D;

               cf += BASICSimulationModelConstants.fs[i19 - 1][nrow - 1];

               mpe[i19] = upe[i19] * cf;
             }
           }
         }
       } else {

         nrow = 1;
         for (i19 = 1; i19 <= 12; i19++) {
           if (upe[i19] > 0.0D) {




             double cf = (BASICSimulationModelConstants.fs[i19 - 1][0] - BASICSimulationModelConstants.inz[i19 - 1][0]) * ((dataset.getLatitudeDegrees() * 60) + dataset.getLatitudeMinutes()) / 300.0D;

             cf += BASICSimulationModelConstants.inz[i19 - 1][0];
             mpe[i19] = upe[i19] * cf;
           }
         }
       }
     }




     double arf = 0.0D;
     double aev = 0.0D;
     for (int i2 = 1; i2 <= 12; i2++) {
       arf += precip[i2];
       aev += mpe[i2];
     }



     double sumt = 0.0D;
     for (int i3 = 1; i3 <= 12; i3++) {
       sumt += temperature[i3];
     }

     double tma = sumt / 12.0D + fc;
     double at1 = (temperature[6] + temperature[7] + temperature[8]) / 3.0D + fc;

/*  201 */     double at2 = (temperature[1] + temperature[2] + temperature[12]) / 3.0D + fc;


/*  204 */     double st = 0.0D;
/*  205 */     double wt = 0.0D;
/*  206 */     if (dataset.getNsHemisphere() == 'N') {
/*  207 */       st = at1;
/*  208 */       wt = at2;
     } else {
/*  210 */       st = at2;
       wt = at1;
     }

     double dif = Math.abs(at1 - at2);
     double cs = dif * (1.0D - fcd) / 2.0D;









     boolean[] cr = new boolean[13];
     boolean[] reg = new boolean[13];
     cr[1] = (tma < 0.0D);
     cr[2] = (0.0D <= tma && tma < 8.0D);
     cr[3] = (st * (1 - fcd) < 15.0D);
/*  230 */     cr[7] = (dif * fcd > 5.0D);
     cr[8] = (tma >= 8.0D && tma < 15.0D);
     cr[9] = (tma >= 15.0D && tma < 22.0D);
     cr[10] = (tma >= 22.0D);
     cr[11] = (tma < 8.0D);
     reg[1] = cr[1];
     reg[2] = (cr[2] && cr[3]);
     reg[3] = (cr[11] && !cr[3] && cr[7]);
     reg[4] = (cr[8] && cr[7]);
     reg[5] = (cr[9] && cr[7]);
/*  240 */     reg[6] = (cr[10] && cr[7]);
     reg[7] = (cr[11] && !cr[7] && !cr[3]);
     reg[8] = (cr[8] && !cr[7]);
     reg[9] = (cr[9] && !cr[7]);
     reg[10] = (cr[10] && !cr[7]);

     st -= cs;
     wt += cs;
     dif = st - wt;




     String trr = "";
     for (int i4 = 1; i4 <= 10; i4++) {
       if (reg[i4]) {
         trr = BASICSimulationModelConstants.tempRegimes[i4 - 1];
       }
     }



















     double whc = waterHoldingCapacity;
     double fsl = whc / 64.0D;
/*  280 */     double[] sl = new double[65];
     for (int i5 = 0; i5 < sl.length; i5++) {
       sl[i5] = 0.0D;
     }

     int k = 1;
     int swst = 0; // TODO: not used?
     int swfi = 0; // TODO: not used?

     double[] ntwi = new double[4];
/*  290 */     double[] ntsu = new double[4];
     double[] nsd = new double[4];
     double[] nzd = new double[4];
     double[] nd = new double[4];
     boolean[] cc = new boolean[4];
     for (int i6 = 1; i6 <= 3; i6++) {
       ntwi[i6] = 0.0D;
       ntsu[i6] = 0.0D;
       nsd[i6] = 0.0D;
       nzd[i6] = 0.0D;
/*  300 */       nd[i6] = 0.0D;
/*  301 */       cc[i6] = false;
     }

/*  304 */     double[] cd = new double[6];
/*  305 */     for (int i7 = 1; i7 <= 5; i7++) {
/*  306 */       cd[i7] = 0.0D;
     }



     int msw = -1;
     int sib = 0;
     double sir = 0.0D;
     int x = 0;
     boolean swm = false;
     int max = 0;
     int icon = 0;
     double lt5c = 0.0D;
     int lt8c = 0;
/*  320 */     int ie = 0;
     int ib = 1;
     double prmo = 0.0D;
     int nccd = 0;
     int nccm = 0;
     int id8c = 0;
     double id5c = 0.0D;
     int swt = 0;
     int tc = 0;
     int np = 0;
/*  330 */     int np8 = 0;
     int ncsm = 0;
     int ncwm = 0;
     int ncsp = 0;
     int ncwp = 0;



     double[] nbd = new double[7];
     double[] ned = new double[7];
/*  340 */     double[] nbd8 = new double[7];
     double[] ned8 = new double[7];
     for (int i8 = 1; i8 <= 6; i8++) {
       nbd[i8] = 0.0D;
       ned[i8] = 0.0D;
       nbd8[i8] = 0.0D;
       ned8[i8] = 0.0D;
     }



     int[] iday = new int[361];
     for (int i9 = 1; i9 <= 360; i9++) {
       iday[i9] = 0;
     }



     int swp = -1;
     int gogr = 0;



     boolean noMpeGreaterThanPrecip = true;
     for (int i11 = 1; i11 <= 12; i11++) {
       if (mpe[i11] > precip[i11]) {
         noMpeGreaterThanPrecip = false;
         break;
       }
     }
/*  370 */     if (noMpeGreaterThanPrecip) {
       cd[5] = -1.0D;
       swt = -1;
     }



     for (int n = 1; n <= 10; n++) {
       for (int i19 = 1; i19 <= 12; i19++) {



         int zsw = 0;
         double lp = precip[i19] / 2.0D;
         double npe = (lp - mpe[i19]) / 2.0D;
         if (npe <= 0.0D) {

           npe = 0.0D - npe;
         } else {
           zsw = -1;
         }



         for (int i20 = 1; i20 <= 64; i20++) {
           if (zsw == 0) {

             int nr = BASICSimulationModelConstants.dp[i20 - 1];
             if (sl[nr] > 0.0D)
             {



/*  403 */               double rpe = sl[nr] * BASICSimulationModelConstants.dr[i20 - 1];
/*  404 */               if (npe <= rpe) {

/*  406 */                 sl[nr] = sl[nr] - npe / BASICSimulationModelConstants.dr[i20 - 1];
/*  407 */                 npe = 0.0D;

                 break;
               }

               sl[nr] = 0.0D;
               npe -= rpe;


             }


           }
/*  420 */           else if (sl[i20] < fsl) {




             double esl = fsl - sl[i20];
             if (esl >= npe) {

               sl[i20] = sl[i20] + npe;

               break;
             }

             sl[i20] = fsl;
             npe -= esl;
           }
         }









         double hp = precip[i19] / 2.0D; int i21;
         for (i21 = 1; i21 <= 64; i21++) {
           if (sl[i21] < fsl) {




             double esl = fsl - sl[i21];
             if (esl >= hp) {

               sl[i21] = sl[i21] + hp;
               if (gogr != 0);


               break;
             }


             sl[i21] = fsl;
             hp -= esl;
             if (gogr != 0);
           }
         }








         zsw = 0;
         lp = precip[i19] / 2.0D;
         npe = (lp - mpe[i19]) / 2.0D;
/*  480 */         if (npe <= 0.0D) {

           npe = 0.0D - npe;
         } else {
           zsw = -1;
         }



         for (i21 = 1; i21 <= 64; i21++) {
/*  490 */           if (zsw == 0) {

             int nr = BASICSimulationModelConstants.dp[i21 - 1];
             if (sl[nr] > 0.0D)
             {



               double rpe = sl[nr] * BASICSimulationModelConstants.dr[i21 - 1];
               if (npe <= rpe) {

/*  501 */                 sl[nr] = sl[nr] - npe / BASICSimulationModelConstants.dr[i21 - 1];
/*  502 */                 npe = 0.0D;

                 break;
               }

/*  507 */               sl[nr] = 0.0D;
/*  508 */               npe -= rpe;


             }


           }
           else if (sl[i21] < fsl) {




/*  520 */             double esl = fsl - sl[i21];
             if (esl >= npe) {

               sl[i21] = sl[i21] + npe;

               break;
             }

             sl[i21] = fsl;
             npe -= esl;
           }
         }
       }












       double tmoi = 0.0D;
       for (int it = 1; it <= 64; it++) {
         tmoi += sl[it];
       }



       if (Math.abs(tmoi - prmo) < prmo / 100.0D) {
         break;
       }


       prmo = tmoi;
     }






     gogr = -1;



     for (int i10 = 1; i10 <= 3; i10++) {
/*  570 */       cc[i10] = false;
     }

     boolean[] pc = new boolean[7]; int i12;
     for (i12 = 1; i12 <= 6; i12++) {
       pc[i12] = false;
     }

     pc[1] = (sl[9] <= 0.0D);
     pc[2] = (sl[17] <= 0.0D);
/*  580 */     pc[3] = (sl[25] <= 0.0D);
     cc[1] = (pc[1] && pc[2] && pc[3]);
     cc[2] = (!cc[1] && (pc[1] || pc[2] || pc[3]));
     pc[4] = (sl[9] > 0.0D);
     pc[5] = (sl[17] > 0.0D);
     pc[6] = (sl[25] > 0.0D);
     cc[3] = (pc[4] && pc[5] && pc[6]);

     int timesi = 0;
     for (i12 = 1; i12 <= 3; ) {
       if (!cc[i12]) {
         i12++; continue;
       }
       k = i12;
                 timesi++;
                 if (timesi > cc.length) {
                   break;
                 }
     }












/*  605 */     for (int im = 1; im <= 12; im++) {



/*  609 */       int[] dmc = new int[4];

       for (int i19 = 1; i19 <= 3; i19++) {
         dmc[i19] = 0;
         cc[i19] = false;
       }

       int zsw = 0;
       int dpmc = 0;
       int pmc = k;
       int igmc = 0;

       double lp = precip[im] / 2.0D;
       double npe = (lp - mpe[im]) / 2.0D;
       double cnpe = 0.0D;
       boolean skipi3Loop = false;

       if (npe < 0.0D) {
         npe = -npe;
         cnpe = npe;
       } else if (npe == 0.0D) {

         skipi3Loop = true;
       } else {
         zsw = -1;
         cnpe = npe;
       }


       if (!skipi3Loop) {
         for (int i23 = 1; i23 <= 64; i23++) {
/*  640 */           if (zsw == 0) {

             if (npe <= 0.0D) {
               break;
             }


             int nr = BASICSimulationModelConstants.dp[i23 - 1];
             if (sl[nr] > 0.0D)
             {



               double rpd = sl[nr] * BASICSimulationModelConstants.dr[i23 - 1];
               if (npe <= rpd) {

                 sl[nr] = sl[nr] - npe / BASICSimulationModelConstants.dr[i23 - 1];
                 npe = 0.0D;
               }
               else {

                 sl[nr] = 0.0D;
                 npe -= rpd;
               }

               int i24;

               for (i24 = 1; i24 <= 3; i24++) {
                 cc[i24] = false;
               }

               for (i24 = 1; i24 <= 6; i24++) {
                 pc[i24] = false;
               }

               pc[1] = (sl[9] <= 0.0D);
               pc[2] = (sl[17] <= 0.0D);
               pc[3] = (sl[25] <= 0.0D);
               cc[1] = (pc[1] && pc[2] && pc[3]);
               cc[2] = (!cc[1] && (pc[1] || pc[2] || pc[3]));
/*  680 */               pc[4] = (sl[9] > 0.0D);
               pc[5] = (sl[17] > 0.0D);
               pc[6] = (sl[25] > 0.0D);
               cc[3] = (pc[4] && pc[5] && pc[6]);

                         timesi = 0;
               for (i24 = 1; i24 <= 3; ) {
                 if (!cc[i24]) {
                   i24++; continue;
                 }
                           timesi++;
                 k = i24;
                           if(timesi > cc.length) {
                             break;
                           }
               }





               if (zsw != 0);









/*  706 */               int kk = k;
/*  707 */               k = pmc;
/*  708 */               if (kk != pmc)
               {



                 if (npe <= 0.0D) {
                   break;
                 }




/*  720 */                 double rpe = cnpe - npe;
                 dmc[k] = (int)(15.0D * rpe / cnpe) - dpmc;
                 igmc = dmc[k];
                 dpmc = dmc[k] + dpmc;
                 dmc[k] = 0;



                 int i25 = 0;
                 int i26 = 0;
/*  730 */                 ie += igmc;
                 for (int i27 = ib; i27 <= ie; i27++) {
                   iday[i27] = k;

                   if (i27 > 30) {
                     i25 = i27 % 30 - 1;
                   } else {
                     i25 = i27;
                   }

/*  740 */                   i26 = i27 / 30;
                   if (i27 % 30 == 0) {
                     i26--;
                   }

                   if (i25 == -1) {
                     i25 = 29;
                   }

                   if (i26 < 0) {
/*  750 */                     i26 = 0;
                   }
                 }


                 ib = ie + 1;
                 nd[k] = nd[k] + igmc;


                 pmc = kk;
/*  760 */                 k = kk;
               }

             }

           } else {

             if (npe <= 0.0D) {
               break;
             }


             if (sl[i23] < fsl) {





               double esl = fsl - sl[i23];
               if (esl >= npe) {

                 sl[i23] = sl[i23] + npe;
                 npe = 0.0D;
               } else {

                 sl[i23] = fsl;

                 npe -= esl;
               }


               int i24;

               for (i24 = 1; i24 <= 3; i24++) {
                 cc[i24] = false;
               }

               for (i24 = 1; i24 <= 6; i24++) {
                 pc[i24] = false;
               }

/*  801 */               pc[1] = (sl[9] <= 0.0D);
/*  802 */               pc[2] = (sl[17] <= 0.0D);
/*  803 */               pc[3] = (sl[25] <= 0.0D);
/*  804 */               cc[1] = (pc[1] && pc[2] && pc[3]);
/*  805 */               cc[2] = (!cc[1] && (pc[1] || pc[2] || pc[3]));
/*  806 */               pc[4] = (sl[9] > 0.0D);
/*  807 */               pc[5] = (sl[17] > 0.0D);
/*  808 */               pc[6] = (sl[25] > 0.0D);
/*  809 */               cc[3] = (pc[4] && pc[5] && pc[6]);

                         timesi = 0;
               for (i24 = 1; i24 <= 3; ) {
                 if (!cc[i24]) {
                   i24++; continue;
                 }
                           timesi++;
                 k = i24;
                           if (timesi > cc.length) {
                             break;
                           }

               }





               if (zsw != 0);









               int kk = k;
               k = pmc;
               if (kk != pmc) {




                 if (npe <= 0.0D) {
                   break;
                 }




                 double rpe = cnpe - npe;
                 dmc[k] = (int)(15.0D * rpe / cnpe) - dpmc;
                 igmc = dmc[k];
                 dpmc = dmc[k] + dpmc;
/*  850 */                 dmc[k] = 0;



                 int i25 = 0;
                 int i26 = 0;
                 ie += igmc;
                 for (int i27 = ib; i27 <= ie; i27++) {
                   iday[i27] = k;

/*  860 */                   if (i27 > 30) {
                     i25 = i27 % 30 - 1;
                   } else {
                     i25 = i27;
                   }

                   i26 = i27 / 30;
                   if (i27 % 30 == 0) {
                     i26--;
                   }

                   if (i25 == -1) {
                     i25 = 29;
                   }

                   if (i26 < 0) {
                     i26 = 0;
                   }
                 }


                 ib = ie + 1;
                 nd[k] = nd[k] + igmc;


                 pmc = kk;
                 k = kk;
               }
             }
           }
         }
       }




       dmc[k] = 15 - dpmc;
       igmc = dmc[k];
       dmc[k] = 0;



/*  902 */       int ii = 0;
/*  903 */       int mm = 0;
/*  904 */       ie += igmc;
/*  905 */       for (int i20 = ib; i20 <= ie; i20++) {
/*  906 */         iday[i20] = k;

/*  908 */         if (i20 > 30) {
/*  909 */           ii = i20 % 30 - 1;
         } else {
           ii = i20;
         }

         mm = i20 / 30;
         if (i20 % 30 == 0) {
           mm--;
         }

         if (ii == -1) {
/*  920 */           ii = 29;
         }

         if (mm < 0) {
           mm = 0;
         }
       }


       ib = ie + 1;
/*  930 */       nd[k] = nd[k] + igmc;






       double hp = precip[im] / 2.0D;
       for (int i22 = 1; i22 <= 64; i22++) {
         if (sl[i22] < fsl) {




           double esl = fsl - sl[i22];
           if (esl >= hp) {

             sl[i22] = sl[i22] + hp;
             if (gogr != 0);


             break;
           }


           sl[i22] = fsl;
           hp -= esl;
           if (gogr != 0);
         }
       }





       int i21;




/*  970 */       for (i21 = 1; i21 <= 3; i21++) {
         cc[i21] = false;
       }

       for (i21 = 1; i21 <= 6; i21++) {
         pc[i21] = false;
       }

       pc[1] = (sl[9] <= 0.0D);
       pc[2] = (sl[17] <= 0.0D);
/*  980 */       pc[3] = (sl[25] <= 0.0D);
       cc[1] = (pc[1] && pc[2] && pc[3]);
       cc[2] = (!cc[1] && (pc[1] || pc[2] || pc[3]));
       pc[4] = (sl[9] > 0.0D);
       pc[5] = (sl[17] > 0.0D);
       pc[6] = (sl[25] > 0.0D);
       cc[3] = (pc[4] && pc[5] && pc[6]);
       timesi = 0;
       for (i21 = 1; i21 <= 3; ) {
         if (!cc[i21]) {
           i21++; continue;
         }
                  timesi++;
         k = i21;

                    if (timesi > cc.length) {
                       break;
                     }
       }





       for (i21 = 1; i21 <= 3; i21++) {
        dmc[i21] = 0;
        cc[i21] = false;
       }

      zsw = 0;
      dpmc = 0;
      pmc = k;

      lp = precip[im] / 2.0D;
      npe = (lp - mpe[im]) / 2.0D;
      cnpe = 0.0D;
      skipi3Loop = false;

      if (npe < 0.0D) {
        npe = -npe;
        cnpe = npe;
      } else if (npe == 0.0D) {

        skipi3Loop = true;
       } else {
        zsw = -1;
        cnpe = npe;
       }


      if (!skipi3Loop) {
        for (int i23 = 1; i23 <= 64; i23++) {
          if (zsw == 0) {

            if (npe <= 0.0D) {
               break;
             }


            int nr = BASICSimulationModelConstants.dp[i23 - 1];
            if (sl[nr] > 0.0D)
             {



              double rpd = sl[nr] * BASICSimulationModelConstants.dr[i23 - 1];
              if (npe <= rpd) {

                sl[nr] = sl[nr] - npe / BASICSimulationModelConstants.dr[i23 - 1];
                npe = 0.0D;
               }
               else {

                sl[nr] = 0.0D;
                npe -= rpd;
               }

               int i24;

              for (i24 = 1; i24 <= 3; i24++) {
                cc[i24] = false;
               }

              for (i24 = 1; i24 <= 6; i24++) {
                pc[i24] = false;
               }

              pc[1] = (sl[9] <= 0.0D);
              pc[2] = (sl[17] <= 0.0D);
              pc[3] = (sl[25] <= 0.0D);
              cc[1] = (pc[1] && pc[2] && pc[3]);
              cc[2] = (!cc[1] && (pc[1] || pc[2] || pc[3]));
              pc[4] = (sl[9] > 0.0D);
              pc[5] = (sl[17] > 0.0D);
              pc[6] = (sl[25] > 0.0D);
              cc[3] = (pc[4] && pc[5] && pc[6]);
               timesi = 0;
              for (i24 = 1; i24 <= 3; ) {
                if (!cc[i24]) {
                   i24++; continue;
                 }
                timesi++;
                           k = i24;
                           if (timesi > cc.length) {
                             break;
                           }
               }





              if (zsw != 0);









              int kk = k;
              k = pmc;
              if (kk != pmc)
               {



                if (npe <= 0.0D) {
                   break;
                 }




                double rpe = cnpe - npe;
                dmc[k] = (int)(15.0D * rpe / cnpe) - dpmc;
                igmc = dmc[k];
                dpmc = dmc[k] + dpmc;
                 dmc[k] = 0;



                 ii = 0;
                 mm = 0;
                 ie += igmc;
                 for (int i25 = ib; i25 <= ie; i25++) {
                   iday[i25] = k;

                   if (i25 > 30) {
                     ii = i25 % 30 - 1;
                   } else {
                     ii = i25;
                   }

                   mm = i25 / 30;
                   if (i25 % 30 == 0) {
                     mm--;
                   }

                   if (ii == -1) {
                     ii = 29;
                   }

                   if (mm < 0) {
                     mm = 0;
                   }
                 }


                 ib = ie + 1;
                 nd[k] = nd[k] + igmc;


                 pmc = kk;
                 k = kk;
               }

             }

           } else {

             if (npe <= 0.0D) {
               break;
             }


             if (sl[i23] < fsl) {





               double esl = fsl - sl[i23];
               if (esl >= npe) {

                 sl[i23] = sl[i23] + npe;
                 npe = 0.0D;
               } else {

                 sl[i23] = fsl;

                 npe -= esl;
               }


               int i24;

              for (i24 = 1; i24 <= 3; i24++) {
                 cc[i24] = false;
               }

               for (i24 = 1; i24 <= 6; i24++) {
                 pc[i24] = false;
               }

               pc[1] = (sl[9] <= 0.0D);
               pc[2] = (sl[17] <= 0.0D);
              pc[3] = (sl[25] <= 0.0D);
               cc[1] = (pc[1] && pc[2] && pc[3]);
               cc[2] = (!cc[1] && (pc[1] || pc[2] || pc[3]));
               pc[4] = (sl[9] > 0.0D);
               pc[5] = (sl[17] > 0.0D);
               pc[6] = (sl[25] > 0.0D);
               cc[3] = (pc[4] && pc[5] && pc[6]);
               timesi = 0;
               for (i24 = 1; i24 <= 3; ) {
                 if (!cc[i24]) {
                   i24++; continue;
                 }
                           timesi++;
                k = i24;
                           if (timesi > cc.length) {
                             break;
                           }
               }





              if (zsw != 0);









               int kk = k;
              k = pmc;
               if (kk != pmc) {




                 if (npe <= 0.0D) {
                   break;
                 }




                 double rpe = cnpe - npe;
                 dmc[k] = (int)(15.0D * rpe / cnpe) - dpmc;
                 igmc = dmc[k];
                 dpmc = dmc[k] + dpmc;
                 dmc[k] = 0;



                 ii = 0;
                 mm = 0;
                 ie += igmc;
                 for (int i25 = ib; i25 <= ie; i25++) {
                   iday[i25] = k;

                   if (i25 > 30) {
                     ii = i25 % 30 - 1;
                   } else {
                    ii = i25;
                   }

                   mm = i25 / 30;
                   if (i25 % 30 == 0) {
                     mm--;
                   }

                   if (ii == -1) {
                     ii = 29;
                   }

                   if (mm < 0) {
                     mm = 0;
                   }
                 }


                 ib = ie + 1;
                 nd[k] = nd[k] + igmc;


                 pmc = kk;
                 k = kk;
               }
             }
           }
         }
       }




       dmc[k] = 15 - dpmc;
       igmc = dmc[k];
       dmc[k] = 0;



       ii = 0;
      mm = 0;
       ie += igmc;
       for (i21 = ib; i21 <= ie; i21++) {
         iday[i21] = k;

         if (i21 > 30) {
           ii = i21 % 30 - 1;
         } else {
           ii = i21;
         }

        mm = i21 / 30;
        if (i21 % 30 == 0) {
          mm--;
         }

        if (ii == -1) {
          ii = 29;
         }

        if (mm < 0) {
           mm = 0;
         }
       }


       ib = ie + 1;
       nd[k] = nd[k] + igmc;
     }








     int sn = 0;
     int kj = 0;
     int ia = 0;
    int iz = 0;
     int[] nj = new int[14];
     double crr = 0.0D;
     boolean[] c = new boolean[15];
     boolean tempUnderFive = false;
     boolean zwt = false;

     for (int i13 = 1; i13 <= 12; i13++) {

       if (temperature[i13] < 5.0D) {

         tempUnderFive = true;

         break;
       }
     }

     boolean skipTo890 = false;
     double t13 = 0.0D;

    if (tempUnderFive) {

       t13 = temperature[1];
       for (int it = 1; it <= 11; it++) {
         if (temperature[it] == 5.0D && temperature[it + 1] == 5.0D) {
           temperature[it] = 5.01D;
         }
       }
       if (temperature[12] == 5.0D && t13 == 5.0D) {
         temperature[12] = 5.01D;
       }
       crr = 5.0D;

       int i19;

       for (i19 = 1; i19 <= 12; i19++) {
         nj[i19] = 0;
       }

       zwt = false;
      kj = 1;
       ia = 30;
       iz = 30;

       if (crr != 8.0D) {
         ia = 36;
         iz = 25;
       }

       for (i19 = 1; i19 <= 12; i19++) {
        for (int i22 = 1; i22 <= 14; i22++) {
           c[i22] = false;
         }



         int m0 = i19 - 1;
         int m1 = i19;
         int m2 = i19 + 1;
         if (m2 > 12) {
          m2 -= 12;
         }
         if (m0 < 1) {
           m0 += 12;
         }

         c[1] = (temperature[m1] < crr);
         c[2] = (temperature[m2] > crr);
         c[3] = (temperature[m0] < crr);
         c[4] = (temperature[m1] == crr);
        c[5] = (temperature[m2] > crr);
        c[6] = (temperature[m2] < crr);
        c[7] = (temperature[m1] > crr);
        c[8] = (temperature[m0] > crr);
        c[9] = (c[1] && c[2]);
        c[10] = (c[3] && c[4] && c[5]);
        c[11] = (c[9] || c[10]);
        c[12] = (c[6] && c[7]);
        c[13] = (c[8] && c[6] && c[4]);
        c[14] = (c[12] || c[13]);

         if (c[11]) {

           nj[kj] = m0 * 30 + ia + (int)(30.0D * (crr - temperature[m1]) / (temperature[m2] - temperature[m1]));

           if (nj[kj] > 360) {
             nj[kj] = nj[kj] - 360;
           }
           kj++;
           zwt = true;

         }
         else if (c[14]) {

           nj[kj] = m0 * 30 + iz + (int)(30.0D * (temperature[m1] - crr) / (temperature[m1] - temperature[m2]));

           if (nj[kj] > 360) {
             nj[kj] = nj[kj] - 360;
           }
           kj++;
          zwt = false;
         }
       }








       if (zwt) {

         int le = kj - 2;
         int npro = nj[1];
         for (int i22 = 1; i22 <= le; i22++) {
           nj[i22] = nj[i22 + 1];
         }
         nj[le + 1] = npro;
       }



       int npj = (kj - 1) / 2;
       int[] nbj = new int[7];
       int[] nej = new int[7]; int i21;
       for (i21 = 1; i21 <= npj; i21++) {
         ib = 2 * i21 - 1;
         ie = 2 * i21;
         nbj[i21] = nj[ib];
        nej[i21] = nj[ie];
       }



       for (i21 = 1; i21 <= 6; i21++) {
         nbd[i21] = nbj[i21];
         ned[i21] = nej[i21];
       }

      np = npj;
       tc = -1;
       int i20 = 0;
       double ir = 0.0D;
       double d1 = 0.0D;
       double d2 = 0.0D;

       if (np == 0) {

         if (wt <= 5.0D)
         {


           lt5c = 0.0D;
           nbd[1] = -1.0D;
           id5c = 0.0D;
           for (int ik = 1; ik <= 3; ik++) {
             nsd[ik] = 0.0D;
           }
           tc = 0;
          skipTo890 = true;
         }

       } else {

         for (int i22 = 1; i22 <= np; i22++) {
           ib = (int)nbd[i22];
           if (nbd[i22] < ned[i22]) {

             ir = ned[i22] - nbd[i22] + 1.0D;
            i20 = ib;
            d1 = ir;
           }
           else {

            ir = 361.0D - nbd[i22] + ned[i22];
            i20 = ib;
            d1 = ir;
           }



           for (int ij = 1; ij <= 3; ij++) {
             nzd[ij] = 0.0D;
           }

           d2 = i20 + d1 - 1.0D;

           for (int l = i20; l <= d2; l++) {
             int i23 = l;
            if (i23 > 360) {
               i23 -= 360;
             }
             int ik = iday[i23];
             nzd[ik] = nzd[ik] + 1.0D;
           }








           if (!hasRunAlready) {
             for (int i23 = 1; i23 <= 3; i23++) {
               nsd[i23] = nsd[i23] + nzd[i23];
               hasRunAlready = true;
             }
           }

           lt5c += ir;
         }




         id5c = nbd[1];
         skipTo890 = true;
       }
     }









    if (!skipTo890) {
       tc = 0;
       for (int i19 = 1; i19 <= 3; i19++) {
         nsd[i19] = nd[i19];
       }
       lt5c = 360.0D;
       id5c = 0.0D;
     }



     int[] ncpm = new int[4];
     for (int ic = 1; ic <= 2; ic++) {
       ncpm[ic] = 0;
     }

     boolean tempUnder8C = false;
     for (int i14 = 1; i14 <= 12; i14++) {
       if (temperature[i14] < 8.0D) {

        tempUnder8C = true;

         break;
       }
     }
     if (tempUnder8C) {

       t13 = temperature[1];
       for (int it = 1; it <= 11; it++) {
         if (temperature[it] == 8.0D && temperature[it + 1] == 8.0D) {
          temperature[it] = 8.01D;
         }
       }

       crr = 8.0D;
       if (temperature[12] == 8.0D && t13 == 8.0D) {
         temperature[12] = 8.01D;
       }

       int i19;

      for (i19 = 1; i19 <= 12; i19++) {
        nj[i19] = 0;
       }

      zwt = false;
      kj = 1;
      ia = 30;
      iz = 30;

      if (crr != 8.0D) {
         ia = 36;
         iz = 25;
       }

       for (i19 = 1; i19 <= 12; i19++) {
         for (int i21 = 1; i21 <= 14; i21++) {
           c[i21] = false;
         }



         int m0 = i19 - 1;
         int m1 = i19;
         int m2 = i19 + 1;
         if (m2 > 12) {
           m2 -= 12;
         }
         if (m0 < 1) {
           m0 += 12;
         }

         c[1] = (temperature[m1] < crr);
         c[2] = (temperature[m2] > crr);
         c[3] = (temperature[m0] < crr);
         c[4] = (temperature[m1] == crr);
         c[5] = (temperature[m2] > crr);
         c[6] = (temperature[m2] < crr);
         c[7] = (temperature[m1] > crr);
         c[8] = (temperature[m0] > crr);
        c[9] = (c[1] && c[2]);
         c[10] = (c[3] && c[4] && c[5]);
         c[11] = (c[9] || c[10]);
         c[12] = (c[6] && c[7]);
         c[13] = (c[8] && c[6] && c[4]);
         c[14] = (c[12] || c[13]);

         if (c[11]) {

           nj[kj] = m0 * 30 + ia + (int)(30.0D * (crr - temperature[m1]) / (temperature[m2] - temperature[m1]));

           if (nj[kj] > 360) {
             nj[kj] = nj[kj] - 360;
           }
           kj++;
           zwt = true;

         }
         else if (c[14]) {

          nj[kj] = m0 * 30 + iz + (int)(30.0D * (temperature[m1] - crr) / (temperature[m1] - temperature[m2]));

           if (nj[kj] > 360) {
             nj[kj] = nj[kj] - 360;
           }
           kj++;
           zwt = false;
         }
       }








       if (zwt) {

         int le = kj - 2;
        int npro = nj[1];
         for (int i21 = 1; i21 <= le; i21++) {
           nj[i21] = nj[i21 + 1];
         }
         nj[le + 1] = npro;
       }



       int npj = (kj - 1) / 2;
      int[] nbj = new int[7];
       int[] nej = new int[7]; int i20;
       for (i20 = 1; i20 <= npj; i20++) {
         ib = 2 * i20 - 1;
         ie = 2 * i20;
         nbj[i20] = nj[ib];
         nej[i20] = nj[ie];
       }



      for (i20 = 1; i20 <= 6; i20++) {
        nbd8[i20] = nbj[i20];
        ned8[i20] = nej[i20];
       }

      np8 = npj;
      tc = -1;



       if (np8 != 0)
       {


         for (i20 = 1; i20 <= np8; i20++) {
           ib = (int)nbd8[i20];
           double ir = 0.0D;
           if (nbd8[i20] < ned8[i20]) {

            ir = ned8[i20] - nbd8[i20] + 1.0D;
           }
           else {

             ir = 361.0D - nbd8[i20] + ned8[i20];
           }



           msw = 0;
          sib = ib;
           sir = ir;
           x = 1;
           swm = (msw != 0);



           int[] arrayOfInt = new int[5];
           arrayOfInt[x] = 0;
           int i21 = 0;
          int i22 = 0;
           int i23 = 0;
           max = 0;
           double d1 = sib + sir - 1.0D;

           for (int i24 = sib; i24 <= d1; i24++) {
             int n1 = i24 + 1;
             if (n1 > 360) {
               n1 -= 360;
             }

             if (i24 > 360) {
               i23 = i24 - 360;
             } else {
               i23 = i24;
             }

             if (swm) {

               if (iday[i23] == x)
               {
                 if (iday[i23] != iday[n1])
                 {
                   if (i22 != 0)
                   {
                     arrayOfInt[x] = arrayOfInt[x] + 1;
                     if (arrayOfInt[x] > max)
                     {
                       max = arrayOfInt[x];

                      arrayOfInt[x] = 0;
                       i22 = 0;

                     }
                     else
                     {
                       arrayOfInt[x] = 0;
                       i22 = 0;

                     }


                   }

                 }
                 else
                 {
                   arrayOfInt[x] = arrayOfInt[x] + 1;
                   i22 = -1;


                 }


               }

             }
             else if (iday[i23] != x) {

               if (iday[n1] == x) {

                if (i22 != 0)
                 {
                  arrayOfInt[x] = arrayOfInt[x] + 1;
                  if (arrayOfInt[x] > max)
                   {
                    max = arrayOfInt[x];

                    arrayOfInt[x] = 0;
                    i22 = 0;

                   }
                   else
                   {
                     arrayOfInt[x] = 0;
                     i22 = 0;

                   }


                 }

               }
               else {

                 arrayOfInt[x] = arrayOfInt[x] + 1;
                 i22 = -1;
               }
             }
           }







           if (i22 != 0) {
             i21 = arrayOfInt[x];
           }

           if (i21 > max) {
             max = i21;
           }



           icon = max;
           if (ncpm[2] <= icon) {




             ncpm[2] = icon;
             lt8c = (int)ir;
             id8c = (int)nbd8[i20];
           }
         }



         sn = -1;
       }

     } else {

       msw = 0;

       tc = -1;
       lt8c = 360;
      id8c = 0;
     }




     sib = 1;
     sir = 720.0D;
     x = 1;
     swm = (msw != 0);



     int[] ns = new int[5];
     ns[x] = 0;
     int ifin = 0;
     int sw = 0;
     int si = 0;
     max = 0;
     double siz = sib + sir - 1.0D;

     for (int i16 = sib; i16 <= siz; i16++) {
       int n1 = i16 + 1;
       while (n1 > 360) {
         n1 -= 360;
       }

       if (i16 > 360) {
         si = i16 - 360;
       } else {
        si = i16;
       }

      if (swm) {

        if (iday[si] == x)
         {
          if (iday[si] != iday[n1])
           {
            if (sw != 0)
             {
               ns[x] = ns[x] + 1;
               if (ns[x] > max)
               {
                 max = ns[x];

                 ns[x] = 0;
                 sw = 0;

               }
               else
               {
                 ns[x] = 0;
                 sw = 0;

               }


             }

           }
           else
           {
             ns[x] = ns[x] + 1;
             sw = -1;


           }


         }

       }
       else if (iday[si] != x) {

         if (iday[n1] == x) {

           if (sw != 0)
           {
             ns[x] = ns[x] + 1;
            if (ns[x] > max)
             {
               max = ns[x];

               ns[x] = 0;
               sw = 0;

             }
             else
             {
              ns[x] = 0;
               sw = 0;

             }


           }

         }
         else {

           ns[x] = ns[x] + 1;
           sw = -1;
         }
       }
     }







     if (sw != 0) {
       ifin = ns[x];
     }

     if (ifin > max) {
       max = ifin;
     }



     icon = max;
     if (icon > 360) {
       icon = 360;
     }
     ncpm[1] = icon;
     if (sn == 0)
     {
      ncpm[2] = ncpm[1];
     }



    sn = 0;
    msw = -1;
    int i15 = 0;

    if (dataset.getNsHemisphere() == 'N') {
      ib = 181;
      i15 = 1;
     } else {
      ib = 1;
      i15 = 181;
     }

    sib = ib;
    sir = 120.0D;
    x = 1;
    swm = (msw != 0);



    ns = new int[5];
    ns[x] = 0;
    ifin = 0;
    sw = 0;
    si = 0;
    max = 0;
    siz = sib + sir - 1.0D;
     int i17;
    for (i17 = sib; i17 <= siz; i17++) {
      int n1 = i17 + 1;
      if (n1 > 360) {
        n1 -= 360;
       }

      if (i17 > 360) {
        si = i17 - 360;
       } else {
        si = i17;
       }

      if (swm) {

        if (iday[si] == x)
         {
          if (iday[si] != iday[n1])
           {
            if (sw != 0)
             {
              ns[x] = ns[x] + 1;
              if (ns[x] > max)
               {
                max = ns[x];

                ns[x] = 0;
                sw = 0;

               }
               else
               {
                ns[x] = 0;
                sw = 0;

               }


             }

           }
           else
           {
            ns[x] = ns[x] + 1;
            sw = -1;


           }


         }

       }
      else if (iday[si] != x) {

        if (iday[n1] == x) {

          if (sw != 0)
           {
            ns[x] = ns[x] + 1;
            if (ns[x] > max)
             {
              max = ns[x];

              ns[x] = 0;
              sw = 0;

             }
             else
             {
              ns[x] = 0;
              sw = 0;

             }


           }

         }
         else {

           ns[x] = ns[x] + 1;
           sw = -1;
         }
       }
     }







     if (sw != 0) {
       ifin = ns[x];
     }

     if (ifin > max) {
       max = ifin;
     }



     nccd = max;
     sib = i15;
     sir = 120.0D;
     x = 3;
     swm = (msw != 0);



     ns = new int[5];
     ns[x] = 0;
     ifin = 0;
     sw = 0;
     si = 0;
     max = 0;
     siz = sib + sir - 1.0D;

    for (i17 = sib; i17 <= siz; i17++) {
       int n1 = i17 + 1;
       if (n1 > 360) {
         n1 -= 360;
       }

       if (i17 > 360) {
         si = i17 - 360;
       } else {
         si = i17;
       }

       if (swm) {

         if (iday[si] == x)
         {
           if (iday[si] != iday[n1])
           {
             if (sw != 0)
             {
              ns[x] = ns[x] + 1;
               if (ns[x] > max)
               {
                 max = ns[x];

                 ns[x] = 0;
                 sw = 0;

               }
               else
               {
                 ns[x] = 0;
                 sw = 0;

               }


             }

           }
           else
           {
             ns[x] = ns[x] + 1;
             sw = -1;


           }


         }

       }
      else if (iday[si] != x) {

        if (iday[n1] == x) {

          if (sw != 0)
           {
            ns[x] = ns[x] + 1;
            if (ns[x] > max)
             {
               max = ns[x];

               ns[x] = 0;
               sw = 0;

             }
             else
             {
               ns[x] = 0;
              sw = 0;

             }


           }

         }
         else {

          ns[x] = ns[x] + 1;
           sw = -1;
         }
       }
     }







     if (sw != 0) {
       ifin = ns[x];
     }

     if (ifin > max) {
       max = ifin;
     }



     nccm = max;
     int tu = 0;
     boolean skipTo1420 = false;

     if (nd[3] == 360.0D) {

       ncsm = 180;
       ncwm = 180;
      ncsp = 180;
       ncwp = 180;
       ntsu[3] = 180.0D;
       ntwi[3] = 180.0D;

       skipTo1420 = true;
     } else if (nd[1] == 360.0D) {

       skipTo1420 = true;
     } else if (nd[1] == 0.0D) {

       tu = -1;
       sib = ib;
       x = 3;
       swm = (tu != 0);



       ns = new int[5];
       ns[x] = 0;
      ifin = 0;
       sw = 0;
       si = 0;
       max = 0;
       siz = sib + sir - 1.0D;
       int i19;
       for (i19 = sib; i19 <= siz; i19++) {
         int n1 = i19 + 1;
         if (n1 > 360) {
           n1 -= 360;
         }

         if (i19 > 360) {
           si = i19 - 360;
         } else {
           si = i19;
         }

         if (swm) {

          if (iday[si] == x)
           {
            if (iday[si] != iday[n1])
             {
              if (sw != 0)
               {
                ns[x] = ns[x] + 1;
                if (ns[x] > max)
                 {
                  max = ns[x];

                   ns[x] = 0;
                   sw = 0;

                 }
                 else
                 {
                   ns[x] = 0;
                   sw = 0;

                 }


               }

             }
             else
             {
               ns[x] = ns[x] + 1;
               sw = -1;


             }


           }

         }
         else if (iday[si] != x) {

          if (iday[n1] == x) {

             if (sw != 0)
             {
               ns[x] = ns[x] + 1;
               if (ns[x] > max)
               {
                 max = ns[x];

                 ns[x] = 0;
                sw = 0;

               }
               else
               {
                 ns[x] = 0;
                 sw = 0;

               }


             }

           }
           else {

             ns[x] = ns[x] + 1;
             sw = -1;
           }
         }
       }







       if (sw != 0) {
         ifin = ns[x];
       }

       if (ifin > max) {
         max = ifin;
       }



       ncsp = max;
       sib = i15;
      sir = 180.0D;
       swm = (tu != 0);



       ns = new int[5];
       ns[x] = 0;
       ifin = 0;
       sw = 0;
       si = 0;
      max = 0;
      siz = sib + sir - 1.0D;

      for (i19 = sib; i19 <= siz; i19++) {
        int n1 = i19 + 1;
        if (n1 > 360) {
          n1 -= 360;
         }

        if (i19 > 360) {
          si = i19 - 360;
         } else {
           si = i19;
         }

         if (swm) {

           if (iday[si] == x)
           {
             if (iday[si] != iday[n1])
             {
               if (sw != 0)
               {
                 ns[x] = ns[x] + 1;
                 if (ns[x] > max)
                 {
                   max = ns[x];

                   ns[x] = 0;
                   sw = 0;

                 }
                 else
                 {
                   ns[x] = 0;
                   sw = 0;

                 }


               }

             }
             else
             {
               ns[x] = ns[x] + 1;
               sw = -1;


             }


           }

         }
         else if (iday[si] != x) {

           if (iday[n1] == x) {

             if (sw != 0)
             {
               ns[x] = ns[x] + 1;
               if (ns[x] > max)
               {
                 max = ns[x];

                 ns[x] = 0;
                 sw = 0;

               }
               else
               {
                 ns[x] = 0;
                 sw = 0;

               }


             }

           }
           else {

             ns[x] = ns[x] + 1;
             sw = -1;
           }
         }
       }







       if (sw != 0) {
         ifin = ns[x];
       }

       if (ifin > max) {
        max = ifin;
       }



      ncwp = max;

     }
     else {


       tu = 0;
       sib = ib;
       sir = 180.0D;
       x = 1;
       swm = (tu != 0);


       ns = new int[5];
       ns[x] = 0;
      ifin = 0;
       sw = 0;
       si = 0;
       max = 0;
       siz = sib + sir - 1.0D;
       int i19;
       for (i19 = sib; i19 <= siz; i19++) {
         int n1 = i19 + 1;
         if (n1 > 360) {
           n1 -= 360;
         }

         if (i19 > 360) {
           si = i19 - 360;
         } else {
           si = i19;
         }

         if (swm) {

          if (iday[si] == x)
           {
             if (iday[si] != iday[n1])
             {
               if (sw != 0)
               {
                 ns[x] = ns[x] + 1;
                 if (ns[x] > max)
                 {
                   max = ns[x];

                   ns[x] = 0;
                   sw = 0;

                 }
                 else
                 {
                   ns[x] = 0;
                   sw = 0;

                 }


               }

             }
             else
             {
               ns[x] = ns[x] + 1;
               sw = -1;


             }


           }

         }
         else if (iday[si] != x) {

          if (iday[n1] == x) {

             if (sw != 0)
             {
               ns[x] = ns[x] + 1;
               if (ns[x] > max)
               {
                 max = ns[x];

                 ns[x] = 0;
                sw = 0;

               }
               else
               {
                 ns[x] = 0;
                 sw = 0;

               }


             }

           }
           else {

            ns[x] = ns[x] + 1;
            sw = -1;
           }
         }
       }







       if (sw != 0) {
         ifin = ns[x];
       }

       if (ifin > max) {
         max = ifin;
       }


       ncsm = max;
       sib = i15;


       ns = new int[5];
       ns[x] = 0;
       ifin = 0;
       sw = 0;
       si = 0;
       max = 0;
       siz = sib + sir - 1.0D;

       for (i19 = sib; i19 <= siz; i19++) {
        int n1 = i19 + 1;
         if (n1 > 360) {
           n1 -= 360;
         }

         if (i19 > 360) {
           si = i19 - 360;
         } else {
           si = i19;
         }

         if (swm) {

           if (iday[si] == x)
           {
             if (iday[si] != iday[n1])
             {
               if (sw != 0)
               {
                 ns[x] = ns[x] + 1;
                if (ns[x] > max)
                 {
                   max = ns[x];

                   ns[x] = 0;
                   sw = 0;

                 }
                 else
                 {
                  ns[x] = 0;
                   sw = 0;

                 }


               }

             }
             else
             {
               ns[x] = ns[x] + 1;
               sw = -1;


             }


           }

         }
         else if (iday[si] != x) {

           if (iday[n1] == x) {

             if (sw != 0)
             {
               ns[x] = ns[x] + 1;
               if (ns[x] > max)
               {
                max = ns[x];

                ns[x] = 0;
                sw = 0;

               }
               else
               {
                ns[x] = 0;
                sw = 0;

               }


             }

           }
           else {

             ns[x] = ns[x] + 1;
            sw = -1;
           }
         }
       }







       if (sw != 0) {
         ifin = ns[x];
       }

       if (ifin > max) {
         max = ifin;
       }


      ncwm = max;
     }


     int jb = 0;
     int jr = 0;
     int je = 0;
     if (!skipTo1420) {

       jb = ib;
      jr = 180;


       for (int ij = 1; ij <= 3; ij++) {
         nzd[ij] = 0.0D;
       }

       je = jb + jr - 1;

       for (int l = jb; l <= je; l++) {
        int i20 = l;
         if (i20 > 360) {
           i20 -= 360;
         }
         int ik = iday[i20];
         nzd[ik] = nzd[ik] + 1.0D;
       }

       int i19;
       for (i19 = 1; i19 <= 3; i19++) {
        ntsu[i19] = nzd[i19];
       }
       for (i19 = 1; i19 <= 3; i19++) {
         ntwi[i19] = nd[i19] - ntsu[i19];
       }
     }



     int[] ntd = new int[365];

     if (!tempUnder8C)
     {


       for (int i19 = 1; i19 <= 360; i19++) { ntd[i19] = 56; }

     }
     if (tc != 0 || tu != 0) {


       char[] kl = new char[4];
       double[] kr = new double[25];
       int[] arrayOfInt = new int[25];
       int kt = 0;

       kl[0] = '$';
       kl[1] = '-';
       kl[2] = '5';
       kl[3] = '8';
       int i19;
      for (i19 = 1; i19 <= 24; i19++) {
        kr[i19] = 0.0D;
       }

      if (nbd[1] < 0.0D && nbd8[1] < 0.0D) {

        for (i19 = 1; i19 <= 360; i19++) {
          ntd[i19] = kl[1];
         }
       }
       else if (nbd[1] == 0.0D && nbd8[1] < 0.0D) {

         for (i19 = 1; i19 <= 360; i19++) {
           ntd[i19] = kl[2];
         }
       }
       else {

         if (nbd8[1] < 0.0D) {
          nbd8[1] = 0.0D;
         }

         for (i19 = 1; i19 <= 6; i19++) {
           kr[i19] = nbd[i19];
           arrayOfInt[i19] = 2;
         }

         for (i19 = 7; i19 <= 12; i19++) {
           kt = i19 - 6;
          if (ned[kt] != 0.0D) {




             kr[i19] = ned[kt] + 1.0D;
             if (kr[i19] > 360.0D) {
               kr[i19] = 1.0D;
             }
             arrayOfInt[i19] = 1;
           }
         }




         for (i19 = 13; i19 <= 18; i19++) {
           kt = i19 - 12;
           kr[i19] = nbd8[kt];
           arrayOfInt[i19] = 3;
         }

         for (i19 = 19; i19 <= 24; i19++) {
           kt = i19 - 18;
           if (ned8[kt] != 0.0D) {




             kr[i19] = ned8[kt] + 1.0D;
            if (kr[i19] > 360.0D) {
               kr[i19] = 1.0D;
             }
             arrayOfInt[i19] = 2;
           }
         }




        int nt = 23;
         int stt = 0;
         double itemp = 0.0D;
         int itm = 0;

         for (int i20 = 1; i20 <= 23; i20++) {
           stt = 0;

           for (int i24 = 1; i24 <= nt; i24++) {
             if (kr[i24] > kr[i24 + 1]) {




               itemp = kr[i24];
               itm = arrayOfInt[i24];
               kr[i24] = kr[i24 + 1];
               arrayOfInt[i24] = arrayOfInt[i24 + 1];
               kr[i24 + 1] = itemp;
               arrayOfInt[i24 + 1] = itm;
              stt = -1;
             }
           }




           if (stt == 0) {
             break;
           }


          nt--;
         }





        int ima = arrayOfInt[24];
        int nul = 0;

         for (int i21 = 1; i21 <= 24; i21++) {
           if (kr[i21] == 0.0D) {
             nul++;
           }
         }

         int kk = 0; int i22;
         for (i22 = 1; i22 <= 24; i22++) {
          kk = i22;
           int ipl = i22 + nul;
           if (ipl > 24) {
             break;
           }


           kr[i22] = kr[ipl];
           arrayOfInt[i22] = arrayOfInt[ipl];
         }





         for (i22 = kk; i22 <= 24; i22++) {
           kr[i22] = 0.0D;
         }

         if (kr[1] != 1.0D) {



           ie = (int)(kr[1] - 1.0D);
           for (i22 = 1; i22 <= ie; i22++) {
             ntd[i22] = kl[ima];
           }
         }



         int ns2 = 0;
         int nn = 24 - nul;
         for (int i23 = 1; i23 <= nn; i23++) {
           ns2 = arrayOfInt[i23];
           ib = (int)kr[i23];
           ie = (int)(kr[i23 + 1] - 1.0D);
           if (kr[i23 + 1] == 0.0D) {
             ie = 360;
           }

           for (int i24 = ib; i24 <= ie; i24++) {
             ntd[i24] = kl[ns2];
           }
         }
       }
     }







     String ans = " ";
     String div = " ";
     String q = " ";
     if (swt != 0) {
       ans = "Perudic";

      ncsm = 180;
       ncwm = 180;
       ncsp = 180;
       ncwp = 180;
       ntsu[3] = 180.0D;
       ntwi[3] = 180.0D;
     }
     else if (nsd[1] > lt5c / 2.0D && ncpm[2] < 90) {
       ans = "Aridic";

      if (nd[1] == 360.0D) {
         q = "Extreme";
       } else if (ncpm[2] <= 45) {
         q = "Typic";
       } else {
         q = "Weak";
       }

       div = new String(ans);
     }
    else if (tma < 22.0D && dif >= 5.0D && nccd >= 45 && nccm >= 45) {
      ans = "Xeric";

      if (nccd > 90) {
        q = "Dry";
       } else {
        q = "Typic";
       }

      div = new String(ans);
     }
    else if (nd[1] + nd[2] < 90.0D) {
      ans = "Udic";

      if (nd[1] + nd[2] < 30.0D) {
        q = "Typic";

        div = new String(ans);


       }
      else if (dif < 5.0D) {
        q = "Dry";
        div = "Tropudic";
       }
       else {

        div = "Tempudic";
        q = "Dry";
       }

     }
    else if (!trr.equalsIgnoreCase("pergelic") && !trr.equalsIgnoreCase("cryic")) {
      ans = "Ustic";

      if (dif >= 5.0D) {
        div = "Tempustic";

        if (nccm <= 45) {
          q = "Typic";
        } else if (nccd > 45) {
          q = "Xeric";
         } else {
          q = "Wet";
         }

       } else {

        if (ncpm[2] < 180) {
          q = "Aridic";
        } else if (ncpm[2] < 270) {
          q = "Typic";
         } else {
          q = "Udic";
         }

        div = "Tropustic";
       }
     } else {

      ans = "Undefined";
      div = new String(ans);
     }


    byte[] cc2Bytes = { 7, 0, 7, 0, 7 };
    String cc2 = new String(cc2Bytes); // TODO: not used?
    int c2 = 0;

    String flxFile = "\"" + dataset.getName() + "\",\"" + dataset.getCountry() + "\"," + dataset.getLatitudeDegrees() + ",";
    flxFile = flxFile + dataset.getLatitudeMinutes() + "," + dataset.getNsHemisphere() + "," + dataset.getLongitudeDegrees();
    flxFile = flxFile + "," + dataset.getLongitudeMinutes() + "," + dataset.getEwHemisphere() + "," + dataset.getElevation();
    flxFile = flxFile + "," + arf + "," + aev + ",\"" + trr + "\"," + tma + "," + st + "," + wt + "," + dif + ",";
    flxFile = flxFile + dataset.getStartYear() + "," + dataset.getEndYear() + "\n";
     int i18;
    for (i18 = 1; i18 <= 12; i18++) {
      flxFile = flxFile + precip[i18] + "," + temperature[i18];
      flxFile = flxFile + "," + mpe[i18] + "\n";
     }

    flxFile = flxFile + "\"" + ans + "\"," + id5c + "," + lt5c + "," + id8c + "," + lt8c + ",";
    flxFile = flxFile + nccd + "," + nccm + "," + ncsm + "," + ncwm + ",\"" + div + "\",\"" + q + "\"\n";
    flxFile = flxFile + ncsp + "," + ncwp + "," + tc + "," + tu + "," + swt + "," + swp + "\n";

    for (i18 = 1; i18 < 360; i18++) {
      flxFile = flxFile + iday[i18] + ",";
     }
     flxFile = flxFile + iday[360] + "," + whc + "\n";

     for (i18 = 1; i18 <= 6; i18++) {
       flxFile = flxFile + nbd[i18] + "," + ned[i18] + "," + nbd8[i18] + "," + ned8[i18] + "\n";
     }

     for (i18 = 1; i18 <= 3; i18++) {
       flxFile = flxFile + nd[i18] + "," + nzd[i18] + "," + nsd[i18] + "," + ntsu[i18] + "," + ntwi[i18] + "\n";
     }

     flxFile = flxFile + ncpm[1] + "," + ncpm[2] + "\n";

     for (i18 = 1; i18 <= 5; i18++) {
       flxFile = flxFile + cd[i18] + "\n";
     }

     for (i18 = 1; i18 < 360; i18++) {
       flxFile = flxFile + (char)ntd[i18] + ",";
     }

     double awb = computeWaterBalance(precip, mpe, false, (dataset.getNsHemisphere() == 'N'));
     double swb = computeWaterBalance(precip, mpe, true, (dataset.getNsHemisphere() == 'N'));

     return new NewhallResults(arf, whc, mpe, nccd, nccm, ntd, iday, nd, nsd, ncpm, trr, ans, q, div, awb, swb, flxFile);
   }

   public static double computeWaterBalance(double[] precip, double[] evap, boolean onlySummer, boolean northernHemisphere) {
     double runningBalance = 0.0D;
     if (onlySummer) {
      if (northernHemisphere) {
         for (int i = 6; i <= 8; i++) {
           runningBalance += precip[i];
           runningBalance -= evap[i];
         }
       } else {
         runningBalance += precip[12];
         runningBalance -= evap[12];
         runningBalance += precip[1];
         runningBalance -= evap[1];
        runningBalance += precip[2];
         runningBalance -= evap[2];
       }
     } else {
       for (int i = 1; i <= 12; i++) {
         runningBalance += precip[i];
         runningBalance -= evap[i];
       }
     }

    return runningBalance;
   }

    /**
     * Compute soil temperatures using BASIC version's lag phases with C++ version's
     * method to compute soil temperatures by utilizing lag phases.  BASIC version assumes
     * 21 days in the summer and 10 days in the winter for lag phases elsewhere, so to
     * continue to use that assumption here seems reasonable.
     *
     * Lag phases are dependant on the structure of the soil in the area, and these
     * assumptions should be recognized for their limitations in any results.
     */
   @Deprecated
   private static List<Double> computeSoilCalendar(double[] airTemps, int summerLagPhase, int fallLagPhase, boolean northernHemisphere, double amplitude, double averageOffset) {
     double yearAverage = 0.0D;
     for (double month : airTemps) {
       yearAverage += month;
     }
     yearAverage /= 12.0D;
    yearAverage += averageOffset;

     double summerAverage = airTemps[5] + airTemps[6] + airTemps[7];
     summerAverage /= 3.0D;
     summerAverage += averageOffset;

     double winterAverage = airTemps[11] + airTemps[0] + airTemps[1];
     winterAverage /= 3.0D;
     summerAverage += averageOffset;

    double a = Math.abs(summerAverage - winterAverage) / 2.0D * amplitude;
    double w = 0.017453292519943295D;

    ArrayList<Double> soilTempCalendarUnshifted = new ArrayList<Double>(360);

    for (int i = 0; i < 360; i++) {
      if (northernHemisphere) {

        if (i >= 90 && i < 270) {
          soilTempCalendarUnshifted.add(i, Double.valueOf(yearAverage + a * Math.sin(w * (i + summerLagPhase))));
         } else {
           soilTempCalendarUnshifted.add(i, Double.valueOf(yearAverage + a * Math.sin(w * (i + fallLagPhase))));

         }

       }
       else if (i >= 90 && i < 270) {
         soilTempCalendarUnshifted.add(i, Double.valueOf(yearAverage + a * Math.cos(w * (i + summerLagPhase))));
       } else {
         soilTempCalendarUnshifted.add(i, Double.valueOf(yearAverage + a * Math.cos(w * (i + fallLagPhase))));
       }
     }



     ArrayList<Double> soilTempCalendar = new ArrayList<Double>(360);
     int j;
     for (j = 0; j < 134; j++) {
       soilTempCalendar.add(j, soilTempCalendarUnshifted.get(j + 226));
     }

     for (j = 134; j < 360; j++) {
       soilTempCalendar.add(j, soilTempCalendarUnshifted.get(j - 134));
     }

     return soilTempCalendar;
   }
 }

