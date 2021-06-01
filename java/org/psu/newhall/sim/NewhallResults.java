/*     */ package org.psu.newhall.sim;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NewhallResults
/*     */ {
/*     */   private double annualRainfall;
/*     */   private double waterHoldingCapacity;
/*     */   private double annualWaterBalance;
/*     */   private double summerWaterBalance;
/*     */   private List<Double> meanPotentialEvapotranspiration;
/*     */   private int dryDaysAfterSummerSolstice;
/*     */   private int moistDaysAfterWinterSolstice;
/*     */   private int numCumulativeDaysDry;
/*     */   private int numCumulativeDaysMoistDry;
/*     */   private int numCumulativeDaysMoist;
/*     */   private int numCumulativeDaysDryOver5C;
/*     */   private int numCumulativeDaysMoistDryOver5C;
/*     */   private int numCumulativeDaysMoistOver5C;
/*     */   private int numConsecutiveDaysMoistInSomeParts;
/*     */   private int numConsecutiveDaysMoistInSomePartsOver8C;
/*     */   private ArrayList<Character> temperatureCalendar;
/*     */   private ArrayList<Integer> moistureCalendar;
/*     */   private ArrayList<Integer> nsd;
/*     */   private ArrayList<Integer> ncpm;
/*     */   private String temperatureRegime;
/*     */   private String moistureRegime;
/*     */   private String regimeSubdivision1;
/*     */   private String regimeSubdivision2;
/*     */   private String flxFile;
/*     */   
/*     */   public NewhallResults(double arf, double whc, double[] mpe, int nccd, int nccm, int[] ntd, int[] iday, double[] nd, double[] nsd, int[] ncpm, String trr, String ans, String div, String q, double awb, double swb, String flxFile) {
/*  38 */     this.annualRainfall = arf;
/*  39 */     this.waterHoldingCapacity = whc;
/*  40 */     this.dryDaysAfterSummerSolstice = nccd;
/*  41 */     this.moistDaysAfterWinterSolstice = nccm;
/*  42 */     this.temperatureRegime = new String(trr);
/*  43 */     this.moistureRegime = new String(ans);
/*  44 */     this.regimeSubdivision1 = new String(div);
/*  45 */     this.regimeSubdivision2 = new String(q);
/*     */     
/*  47 */     this.meanPotentialEvapotranspiration = new ArrayList<Double>(); int i;
/*  48 */     for (i = 1; i <= 12; i++) {
/*  49 */       this.meanPotentialEvapotranspiration.add(Double.valueOf(mpe[i]));
/*     */     }
/*     */     
/*  52 */     this.temperatureCalendar = new ArrayList<Character>();
/*  53 */     for (i = 1; i <= 360; i++) {
/*  54 */       this.temperatureCalendar.add(Character.valueOf((char)ntd[i]));
/*     */     }
/*     */     
/*  57 */     this.moistureCalendar = new ArrayList<Integer>();
/*  58 */     for (i = 1; i <= 360; i++) {
/*  59 */       this.moistureCalendar.add(Integer.valueOf(iday[i]));
/*     */     }
/*     */     
/*  62 */     this.numCumulativeDaysDry = (int)nd[1];
/*  63 */     this.numCumulativeDaysMoistDry = (int)nd[2];
/*  64 */     this.numCumulativeDaysMoist = (int)nd[3];
/*     */     
/*  66 */     this.numCumulativeDaysDryOver5C = (int)nsd[1];
/*  67 */     this.numCumulativeDaysMoistDryOver5C = (int)nsd[2];
/*  68 */     this.numCumulativeDaysMoistOver5C = (int)nsd[3];
/*     */     
/*  70 */     this.numConsecutiveDaysMoistInSomeParts = ncpm[1];
/*  71 */     this.numConsecutiveDaysMoistInSomePartsOver8C = ncpm[2];
/*     */ 
/*     */ 
/*     */     
/*  75 */     this.annualWaterBalance = awb;
/*  76 */     this.summerWaterBalance = swb;
/*     */     
/*  78 */     this.flxFile = new String(flxFile);
/*     */   }
/*     */   
/*     */   public String getFormattedMoistureCalendar() {
/*  82 */     String result = "";
/*     */     
/*  84 */     for (int i = 0; i < 12; i++) {
/*  85 */       for (int j = 0; j < 30; j++) {
/*  86 */         result = result + this.moistureCalendar.get(j + i * 30);
/*     */       }
/*  88 */       if (i != 11) {
/*  89 */         result = result + "\n";
/*     */       }
/*     */     } 
/*     */     
/*  93 */     return result;
/*     */   }
/*     */   
/*     */   public String getFormattedTemperatureCalendar() {
/*  97 */     String result = "";
/*     */     
/*  99 */     for (int i = 0; i < 12; i++) {
/* 100 */       for (int j = 0; j < 30; j++) {
/* 101 */         result = result + this.temperatureCalendar.get(j + i * 30);
/*     */       }
/* 103 */       if (i != 11) {
/* 104 */         result = result + "\n";
/*     */       }
/*     */     } 
/*     */     
/* 108 */     return result;
/*     */   }
/*     */   
/*     */   public String getFormattedStatistics() {
/* 112 */     String result = "";
/*     */     
/* 114 */     result = result + "Number of Cumulative Days that the Moisture Control Section (MCS) is:\n";
/* 115 */     result = result + "  During one year is:\n";
/* 116 */     result = result + "    Dry: " + this.numCumulativeDaysDry + "\n";
/* 117 */     result = result + "    MoistDry: " + this.numCumulativeDaysMoistDry + "\n";
/* 118 */     result = result + "    Moist: " + this.numCumulativeDaysMoist + "\n";
/* 119 */     result = result + "  When soil temp is above 5°C:\n";
/* 120 */     result = result + "    Dry: " + this.numCumulativeDaysDryOver5C + "\n";
/* 121 */     result = result + "    MoistDry: " + this.numCumulativeDaysMoistDryOver5C + "\n";
/* 122 */     result = result + "    Moist: " + this.numCumulativeDaysMoistOver5C + "\n";
/* 123 */     result = result + "Highest number of consecutive days that the MCS is:\n";
/* 124 */     result = result + "  Moist in some parts:\n";
/* 125 */     result = result + "    Year: " + this.numConsecutiveDaysMoistInSomeParts + "\n";
/* 126 */     result = result + "    Temp over 8°C: " + this.numConsecutiveDaysMoistInSomePartsOver8C + "\n";
/* 127 */     result = result + "  Dry after summer solstice: " + this.dryDaysAfterSummerSolstice + "\n";
/* 128 */     result = result + "  Moist after winter solstice: " + this.moistDaysAfterWinterSolstice;
/*     */     
/* 130 */     return result;
/*     */   }
/*     */   
/*     */   public String getFlxFile() {
/* 134 */     return this.flxFile;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 139 */     String result = getFormattedStatistics();
/*     */     int i;
/* 141 */     for (i = 0; i < 11; i++) {
/* 142 */       result = result + this.meanPotentialEvapotranspiration.get(i) + ", ";
/*     */     }
/* 144 */     result = result + this.meanPotentialEvapotranspiration.get(11) + "\n";
/*     */     
/* 146 */     result = result + "\nTemperature Calendar: (- = Less than 5C)(5 = Between 5C and 8C)(8 = Excess of 8C)\n";
/* 147 */     result = result + "1''''''''''''15'''''''''''''30\n";
/* 148 */     for (i = 0; i < 12; i++) {
/* 149 */       for (int j = 0; j < 30; j++) {
/* 150 */         result = result + this.temperatureCalendar.get(j + i * 30);
/*     */       }
/* 152 */       result = result + "\n";
/*     */     } 
/*     */     
/* 155 */     result = result + "\nMoisture Calendar: (1 = Dry)(2 = Moist/Dry)(3 = Moist)\n";
/* 156 */     result = result + "1''''''''''''15'''''''''''''30\n";
/* 157 */     for (i = 0; i < 12; i++) {
/* 158 */       for (int j = 0; j < 30; j++) {
/* 159 */         result = result + this.moistureCalendar.get(j + i * 30);
/*     */       }
/* 161 */       result = result + "\n";
/*     */     } 
/*     */     
/* 164 */     return result;
/*     */   }
/*     */   
/*     */   public double getAnnualRainfall() {
/* 168 */     return this.annualRainfall;
/*     */   }
/*     */   
/*     */   public int getDryDaysAfterSummerSolstice() {
/* 172 */     return this.dryDaysAfterSummerSolstice;
/*     */   }
/*     */   
/*     */   public List<Double> getMeanPotentialEvapotranspiration() {
/* 176 */     return this.meanPotentialEvapotranspiration;
/*     */   }
/*     */   
/*     */   public int getMoistDaysAfterWinterSolstice() {
/* 180 */     return this.moistDaysAfterWinterSolstice;
/*     */   }
/*     */   
/*     */   public ArrayList<Integer> getMoistureCalendar() {
/* 184 */     return this.moistureCalendar;
/*     */   }
/*     */   
/*     */   public String getMoistureRegime() {
/* 188 */     return this.moistureRegime;
/*     */   }
/*     */   
/*     */   public ArrayList<Integer> getNcpm() {
/* 192 */     return this.ncpm;
/*     */   }
/*     */   
/*     */   public ArrayList<Integer> getNsd() {
/* 196 */     return this.nsd;
/*     */   }
/*     */   
/*     */   public int getNumConsecutiveDaysMoistInSomeParts() {
/* 200 */     return this.numConsecutiveDaysMoistInSomeParts;
/*     */   }
/*     */   
/*     */   public int getNumConsecutiveDaysMoistInSomePartsOver8C() {
/* 204 */     return this.numConsecutiveDaysMoistInSomePartsOver8C;
/*     */   }
/*     */   
/*     */   public int getNumCumulativeDaysDry() {
/* 208 */     return this.numCumulativeDaysDry;
/*     */   }
/*     */   
/*     */   public int getNumCumulativeDaysDryOver5C() {
/* 212 */     return this.numCumulativeDaysDryOver5C;
/*     */   }
/*     */   
/*     */   public int getNumCumulativeDaysMoist() {
/* 216 */     return this.numCumulativeDaysMoist;
/*     */   }
/*     */   
/*     */   public int getNumCumulativeDaysMoistDry() {
/* 220 */     return this.numCumulativeDaysMoistDry;
/*     */   }
/*     */   
/*     */   public int getNumCumulativeDaysMoistDryOver5C() {
/* 224 */     return this.numCumulativeDaysMoistDryOver5C;
/*     */   }
/*     */   
/*     */   public int getNumCumulativeDaysMoistOver5C() {
/* 228 */     return this.numCumulativeDaysMoistOver5C;
/*     */   }
/*     */   
/*     */   public ArrayList<Character> getTemperatureCalendar() {
/* 232 */     return this.temperatureCalendar;
/*     */   }
/*     */   
/*     */   public String getTemperatureRegime() {
/* 236 */     return this.temperatureRegime;
/*     */   }
/*     */   
/*     */   public double getWaterHoldingCapacity() {
/* 240 */     return this.waterHoldingCapacity;
/*     */   }
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
/*     */ 
/*     */   
/*     */   public String getRegimeSubdivision1() {
/* 261 */     return this.regimeSubdivision1;
/*     */   }
/*     */   
/*     */   public String getRegimeSubdivision2() {
/* 265 */     return this.regimeSubdivision2;
/*     */   }
/*     */   
/*     */   public double getAnnualWaterBalance() {
/* 269 */     return this.annualWaterBalance;
/*     */   }
/*     */   
/*     */   public double getSummerWaterBalance() {
/* 273 */     return this.summerWaterBalance;
/*     */   }
/*     */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/psu/newhall/sim/NewhallResults.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */