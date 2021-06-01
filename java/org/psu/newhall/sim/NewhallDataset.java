/*     */ package org.psu.newhall.sim;
/*     */ 
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NewhallDataset
/*     */ {
/*     */   private String name;
/*     */   private String country;
/*     */   private double latitude;
/*     */   private double longitude;
/*     */   private char nsHemisphere;
/*     */   private char ewHemisphere;
/*     */   private double elevation;
/*     */   private List<Double> precipitation;
/*     */   private List<Double> temperature;
/*     */   private int startYear;
/*     */   private int endYear;
/*     */   private boolean isMetric;
/*     */   private double waterholdingCapacity;
/*     */   private NewhallDatasetMetadata metadata;
/*     */   
/*     */   public NewhallDataset(String name, String country, double latitude, double longitude, char nsHemisphere, char ewHemisphere, double elevation, List<Double> precipitation, List<Double> temperature, int startYear, int endYear, boolean isMetric, double waterholdingCapacity) {
/*  25 */     this.name = name;
/*  26 */     this.country = country;
/*  27 */     this.latitude = latitude;
/*  28 */     this.longitude = longitude;
/*  29 */     this.nsHemisphere = nsHemisphere;
/*  30 */     this.ewHemisphere = ewHemisphere;
/*  31 */     this.elevation = elevation;
/*  32 */     this.precipitation = precipitation;
/*  33 */     this.temperature = temperature;
/*  34 */     this.startYear = startYear;
/*  35 */     this.endYear = endYear;
/*  36 */     this.isMetric = isMetric;
/*  37 */     this.waterholdingCapacity = waterholdingCapacity;
/*     */   }
/*     */   
/*     */   public String getCountry() {
/*  41 */     return this.country;
/*     */   }
/*     */   
/*     */   public double getElevation() {
/*  45 */     return this.elevation;
/*     */   }
/*     */   
/*     */   public int getEndYear() {
/*  49 */     return this.endYear;
/*     */   }
/*     */   
/*     */   public char getEwHemisphere() {
/*  53 */     return this.ewHemisphere;
/*     */   }
/*     */   
/*     */   public boolean isMetric() {
/*  57 */     return this.isMetric;
/*     */   }
/*     */   
/*     */   public double getLatitude() {
/*  61 */     return this.latitude;
/*     */   }
/*     */   
/*     */   public int getLatitudeDegrees() {
/*  65 */     return (int)this.latitude;
/*     */   }
/*     */   
/*     */   public double getLatitudeMinutes() {
/*  69 */     double remainder = this.latitude - (int)this.latitude;
/*  70 */     return remainder * 60.0D;
/*     */   }
/*     */   
/*     */   public double getLongitude() {
/*  74 */     return this.longitude;
/*     */   }
/*     */   
/*     */   public int getLongitudeDegrees() {
/*  78 */     return (int)this.longitude;
/*     */   }
/*     */   
/*     */   public double getLongitudeMinutes() {
/*  82 */     double remainder = this.longitude - (int)this.longitude;
/*  83 */     return remainder * 60.0D;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  87 */     return this.name;
/*     */   }
/*     */   
/*     */   public char getNsHemisphere() {
/*  91 */     return this.nsHemisphere;
/*     */   }
/*     */   
/*     */   public List<Double> getPrecipitation() {
/*  95 */     return this.precipitation;
/*     */   }
/*     */   
/*     */   public int getStartYear() {
/*  99 */     return this.startYear;
/*     */   }
/*     */   
/*     */   public List<Double> getTemperature() {
/* 103 */     return this.temperature;
/*     */   }
/*     */   
/*     */   public void setMetadata(NewhallDatasetMetadata metadata) {
/* 107 */     this.metadata = metadata;
/*     */   }
/*     */   
/*     */   public NewhallDatasetMetadata getMetadata() {
/* 111 */     return this.metadata;
/*     */   }
/*     */   
/*     */   public double getWaterholdingCapacity() {
/* 115 */     return this.waterholdingCapacity;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 120 */     String result = getClass().toString();
/* 121 */     result = result + "\n  Name: " + this.name;
/* 122 */     result = result + "\n  Country: " + this.country;
/* 123 */     result = result + "\n  Latitude: " + this.latitude + " " + this.nsHemisphere;
/* 124 */     result = result + "\n  Longitude: " + this.longitude + " " + this.ewHemisphere;
/* 125 */     result = result + "\n  Elevation: " + this.elevation;
/*     */     
/* 127 */     if (this.isMetric) {
/* 128 */       result = result + " meters";
/*     */     } else {
/* 130 */       result = result + " feet";
/*     */     } 
/*     */     
/* 133 */     result = result + "\n  Precipitation(";
/* 134 */     if (this.isMetric) {
/* 135 */       result = result + "mm): ";
/*     */     } else {
/* 137 */       result = result + "in): ";
/*     */     } 
/* 139 */     for (Double precip : this.precipitation) {
/* 140 */       result = result + precip + " ";
/*     */     }
/*     */     
/* 143 */     result = result + "\n  Temperature(";
/* 144 */     if (this.isMetric) {
/* 145 */       result = result + "C): ";
/*     */     } else {
/* 147 */       result = result + "F): ";
/*     */     } 
/* 149 */     for (Double temp : this.temperature) {
/* 150 */       result = result + temp + " ";
/*     */     }
/*     */     
/* 153 */     result = result + "\n  Starting Year: " + this.startYear;
/* 154 */     result = result + "\n  Ending Year: " + this.endYear;
/* 155 */     result = result + "\n  Has Metadata: " + ((getMetadata() != null) ? 1 : 0);
/*     */     
/* 157 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/psu/newhall/sim/NewhallDataset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */