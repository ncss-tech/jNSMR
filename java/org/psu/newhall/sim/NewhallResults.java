package org.psu.newhall.sim;

import java.util.ArrayList;
import java.util.List;

public class NewhallResults {

  private double annualRainfall;
  private double waterHoldingCapacity;
  private double annualWaterBalance;
  private double summerWaterBalance;
  private List<Double> meanPotentialEvapotranspiration;
  private int dryDaysAfterSummerSolstice;
  private int moistDaysAfterWinterSolstice;
  private int numCumulativeDaysDry;
  private int numCumulativeDaysMoistDry;
  private int numCumulativeDaysMoist;
  private int numCumulativeDaysDryOver5C;
  private int numCumulativeDaysMoistDryOver5C;
  private int numCumulativeDaysMoistOver5C;
  private int numConsecutiveDaysMoistInSomeParts;
  private int numConsecutiveDaysMoistInSomePartsOver8C;
  private ArrayList<Character> temperatureCalendar;
  private ArrayList<Integer> moistureCalendar;
  //private ArrayList<Double> soilTempCalendar;
  private ArrayList<Integer> nsd;
  private ArrayList<Integer> ncpm;
  private String temperatureRegime;
  private String moistureRegime;
  private String regimeSubdivision1;
  private String regimeSubdivision2;
  private String flxFile;

  public NewhallResults(double arf, double whc, double[] mpe, int nccd, int nccm, int[] ntd, int[] iday,
          double[] nd, double[] nsd, int[] ncpm, String trr, String ans, String div, String q, double awb, double swb,
          /*List<Double> soilTempCalendar,*/ String flxFile) {

    this.annualRainfall = arf;
    this.waterHoldingCapacity = whc;
    this.dryDaysAfterSummerSolstice = nccd;
    this.moistDaysAfterWinterSolstice = nccm;
    this.temperatureRegime = new String(trr);
    this.moistureRegime = new String(ans);
    this.regimeSubdivision1 = new String(div);
    this.regimeSubdivision2 = new String(q);

    this.meanPotentialEvapotranspiration = new ArrayList<Double>();
    for (int i = 1; i <= 12; i++) {
      this.meanPotentialEvapotranspiration.add(mpe[i]);
    }

    this.temperatureCalendar = new ArrayList<Character>();
    for (int i = 1; i <= 360; i++) {
      this.temperatureCalendar.add((char) ntd[i]);
    }

    this.moistureCalendar = new ArrayList<Integer>();
    for (int i = 1; i <= 360; i++) {
      this.moistureCalendar.add(iday[i]);
    }

    this.numCumulativeDaysDry = (int) nd[1];
    this.numCumulativeDaysMoistDry = (int) nd[2];
    this.numCumulativeDaysMoist = (int) nd[3];

    this.numCumulativeDaysDryOver5C = (int) nsd[1];
    this.numCumulativeDaysMoistDryOver5C = (int) nsd[2];
    this.numCumulativeDaysMoistOver5C = (int) nsd[3];

    this.numConsecutiveDaysMoistInSomeParts = ncpm[1];
    this.numConsecutiveDaysMoistInSomePartsOver8C = ncpm[2];

    //this.soilTempCalendar = new ArrayList<Double>(soilTempCalendar);

    this.annualWaterBalance = awb;
    this.summerWaterBalance = swb;

    this.flxFile = new String(flxFile);
  }

  public String getFormattedMoistureCalendar() {
    String result = "";

    for (int i = 0; i < 12; i++) {
      for (int j = 0; j < 30; j++) {
        result += moistureCalendar.get(j + i * 30);
      }
      if (i != 11) {
        result += "\n";
      }
    }

    return result;
  }

  public String getFormattedTemperatureCalendar() {
    String result = "";

    for (int i = 0; i < 12; i++) {
      for (int j = 0; j < 30; j++) {
        result += temperatureCalendar.get(j + (i * 30));
      }
      if (i != 11) {
        result += "\n";
      }
    }

    return result;
  }

  public String getFormattedStatistics() {
    String result = "";

    result += "Number of Cumulative Days that the Moisture Control Section (MCS) is:" + "\n";
    result += "  During one year is:" + "\n";
    result += "    Dry: " + numCumulativeDaysDry + "\n";
    result += "    MoistDry: " + numCumulativeDaysMoistDry + "\n";
    result += "    Moist: " + numCumulativeDaysMoist + "\n";
    result += "  When soil temp is above 5" + (char)176 + "C:" + "\n";
    result += "    Dry: " + numCumulativeDaysDryOver5C + "\n";
    result += "    MoistDry: " + numCumulativeDaysMoistDryOver5C + "\n";
    result += "    Moist: " + numCumulativeDaysMoistOver5C + "\n";
    result += "Highest number of consecutive days that the MCS is:" + "\n";
    result += "  Moist in some parts:" + "\n";
    result += "    Year: " + numConsecutiveDaysMoistInSomeParts + "\n";
    result += "    Temp over 8" + (char)176 + "C: " + numConsecutiveDaysMoistInSomePartsOver8C + "\n";
    result += "  Dry after summer solstice: " + dryDaysAfterSummerSolstice + "\n";
    result += "  Moist after winter solstice: " + moistDaysAfterWinterSolstice;

    return result;
  }

  public String getFlxFile() {
    return flxFile;
  }

  @Override
  public String toString() {
    String result = getFormattedStatistics();

    for (int i = 0; i < 11; i++) {
      result += meanPotentialEvapotranspiration.get(i) + ", ";
    }
    result += meanPotentialEvapotranspiration.get(11) + "\n";

    result += "\nTemperature Calendar: (- = Less than 5C)(5 = Between 5C and 8C)(8 = Excess of 8C)" + "\n";
    result += "1''''''''''''15'''''''''''''30" + "\n";
    for (int i = 0; i < 12; i++) {
      for (int j = 0; j < 30; j++) {
        result += temperatureCalendar.get(j + (i * 30));
      }
      result += "\n";
    }

    result += "\nMoisture Calendar: (1 = Dry)(2 = Moist/Dry)(3 = Moist)" + "\n";
    result += "1''''''''''''15'''''''''''''30" + "\n";
    for (int i = 0; i < 12; i++) {
      for (int j = 0; j < 30; j++) {
        result += moistureCalendar.get(j + i * 30);
      }
      result += "\n";
    }

    return result;
  }

  public double getAnnualRainfall() {
    return annualRainfall;
  }

  public int getDryDaysAfterSummerSolstice() {
    return dryDaysAfterSummerSolstice;
  }

  public List<Double> getMeanPotentialEvapotranspiration() {
    return meanPotentialEvapotranspiration;
  }

  public int getMoistDaysAfterWinterSolstice() {
    return moistDaysAfterWinterSolstice;
  }

  public ArrayList<Integer> getMoistureCalendar() {
    return moistureCalendar;
  }

  public String getMoistureRegime() {
    return moistureRegime;
  }

  public ArrayList<Integer> getNcpm() {
    return ncpm;
  }

  public ArrayList<Integer> getNsd() {
    return nsd;
  }

  public int getNumConsecutiveDaysMoistInSomeParts() {
    return numConsecutiveDaysMoistInSomeParts;
  }

  public int getNumConsecutiveDaysMoistInSomePartsOver8C() {
    return numConsecutiveDaysMoistInSomePartsOver8C;
  }

  public int getNumCumulativeDaysDry() {
    return numCumulativeDaysDry;
  }

  public int getNumCumulativeDaysDryOver5C() {
    return numCumulativeDaysDryOver5C;
  }

  public int getNumCumulativeDaysMoist() {
    return numCumulativeDaysMoist;
  }

  public int getNumCumulativeDaysMoistDry() {
    return numCumulativeDaysMoistDry;
  }

  public int getNumCumulativeDaysMoistDryOver5C() {
    return numCumulativeDaysMoistDryOver5C;
  }

  public int getNumCumulativeDaysMoistOver5C() {
    return numCumulativeDaysMoistOver5C;
  }

  public ArrayList<Character> getTemperatureCalendar() {
    return temperatureCalendar;
  }

  public String getTemperatureRegime() {
    return temperatureRegime;
  }

  public double getWaterHoldingCapacity() {
    return waterHoldingCapacity;
  }

  //public ArrayList<Double> getSoilTempCalendar() {
  //  return soilTempCalendar;
  //}

  /*public ArrayList<Double> getSoilTempAverages() {
    ArrayList<Double> soilTempAverages = new ArrayList<Double>(12);
    for (int i = 0; i < 12; i++) {
      double monthAverage = 0.0;
      for (int j = 0; ji < 30; j++) {
        monthAverage += soilTempCalendar.get(i * 30 + j);
      }
      monthAverage /= 30.0;
      soilTempAverages.add(i, monthAverage);
    }
    return soilTempAverages;
  }*/

  public String getRegimeSubdivision1() {
    return regimeSubdivision1;
  }

  public String getRegimeSubdivision2() {
    return regimeSubdivision2;
  }

  public double getAnnualWaterBalance() {
    return annualWaterBalance;
  }

  public double getSummerWaterBalance() {
    return summerWaterBalance;
  }
  
}
