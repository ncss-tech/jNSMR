package org.psu.newhall.sim;
import java.util.ArrayList;
import java.util.List;

public class NewhallBatchResults {
  public double[] annualRainfall;
  public double[] waterHoldingCapacity;
  public double[] annualWaterBalance;
  public double[] summerWaterBalance;
  public double[] annualPotentialEvapotranspiration;
  public double[][] meanPotentialEvapotranspiration;
  public int[] dryDaysAfterSummerSolstice;
  public int[] moistDaysAfterWinterSolstice;
  public int[] numCumulativeDaysDry;
  public int[] numCumulativeDaysMoistDry;
  public int[] numCumulativeDaysMoist;
  public int[] numCumulativeDaysDryOver5C;
  public int[] numCumulativeDaysMoistDryOver5C;
  public int[] numCumulativeDaysMoistOver5C;
  public int[] numConsecutiveDaysMoistInSomeParts;
  public int[] numConsecutiveDaysMoistInSomePartsOver8C;
  public int[][] temperatureCalendar;
  public int[][] moistureCalendar;
  public String[] temperatureRegime;
  public String[] moistureRegime;
  public String[] regimeSubdivision1;
  public String[] regimeSubdivision2;
  
  // construct a NewhallBatchResults object from an array of NewhallResults objects
  public NewhallBatchResults(NewhallResults[] nr) {
    
    // number of results
    int n = nr.length;
    
    // initialize batch arrays
    this.annualRainfall = new double[n];
    this.waterHoldingCapacity = new double[n];
    
    this.annualWaterBalance = new double[n];
    this.summerWaterBalance = new double[n];
    this.annualPotentialEvapotranspiration = new double[n];
    this.meanPotentialEvapotranspiration = new double[n][12];
    
    this.dryDaysAfterSummerSolstice = new int[n];
    this.moistDaysAfterWinterSolstice = new int[n];
    this.numCumulativeDaysDry = new int[n];
    this.numCumulativeDaysMoistDry = new int[n];
    this.numCumulativeDaysMoist = new int[n];
    this.numCumulativeDaysDryOver5C = new int[n];
    this.numCumulativeDaysMoistDryOver5C = new int[n];
    this.numCumulativeDaysMoistOver5C = new int[n];
    this.numConsecutiveDaysMoistInSomeParts = new int[n];
    this.numConsecutiveDaysMoistInSomePartsOver8C = new int[n];
    
    this.temperatureCalendar = new int[n][360];
    this.moistureCalendar = new int[n][360];
    
    this.temperatureRegime = new String[n];
    this.moistureRegime = new String[n];
    this.regimeSubdivision1 = new String[n];
    this.regimeSubdivision2 = new String[n];
    
    // iterate over results
    for (int i = 0; i <= (nr.length - 1); i++) {
      
      // parameters
      this.waterHoldingCapacity[i] = nr[i].getWaterHoldingCapacity();

      // taxonomic quantities
      this.dryDaysAfterSummerSolstice[i] = nr[i].getDryDaysAfterSummerSolstice();
      this.moistDaysAfterWinterSolstice[i] = nr[i].getMoistDaysAfterWinterSolstice();
      this.numCumulativeDaysDry[i] = nr[i].getNumCumulativeDaysDry();
      this.numCumulativeDaysMoistDry[i] = nr[i].getNumCumulativeDaysMoistDry();
      this.numCumulativeDaysMoist[i] = nr[i].getNumCumulativeDaysMoist();
      this.numCumulativeDaysDryOver5C[i] = nr[i].getNumCumulativeDaysDryOver5C();
      this.numCumulativeDaysMoistDryOver5C[i] = nr[i].getNumCumulativeDaysMoistDryOver5C();
      this.numCumulativeDaysMoistOver5C[i] = nr[i].getNumCumulativeDaysMoistOver5C();
      this.numConsecutiveDaysMoistInSomeParts[i] = nr[i].getNumConsecutiveDaysMoistInSomeParts();
      this.numConsecutiveDaysMoistInSomePartsOver8C[i] = nr[i].getNumConsecutiveDaysMoistInSomePartsOver8C();
      
      // taxonomic classes / intergrades
      this.temperatureRegime[i] = nr[i].getTemperatureRegime();
      this.moistureRegime[i] = nr[i].getMoistureRegime();
      this.regimeSubdivision1[i] = nr[i].getRegimeSubdivision1();
      this.regimeSubdivision2[i] = nr[i].getRegimeSubdivision2();

      // summaries
      this.annualRainfall[i] = nr[i].getAnnualRainfall();
      this.annualWaterBalance[i] = nr[i].getAnnualWaterBalance();
      this.summerWaterBalance[i] = nr[i].getSummerWaterBalance();
      
      // unpacking lists -> matrices
      List<Double> mpe = nr[i].getMeanPotentialEvapotranspiration();
      double[] pet = new double[12];
      double apet = 0.0;
      for (int j = 0; j < 12; j++) {
        pet[j] = mpe.get(j);
        apet += pet[j];
      }
      this.meanPotentialEvapotranspiration[i] = pet;
      this.annualPotentialEvapotranspiration[i] = apet;
      
      // calendars (12 30-day months stored as int[360])      
      ArrayList<Character> tc = nr[i].getTemperatureCalendar();
      ArrayList<Integer> mc = nr[i].getMoistureCalendar();
      int[] tcal = new int[360];
      int[] mcal = new int[360];
      for (int j = 0; j < 360; j++) {
        tcal[j] = Character.getNumericValue(tc.get(j));
        mcal[j] = mc.get(j);
      }
      this.temperatureCalendar[i] = tcal;
      this.moistureCalendar[i] = mcal;
      
    }
  }
  
}