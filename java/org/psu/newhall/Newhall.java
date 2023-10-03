package org.psu.newhall;
import java.io.File;
import org.psu.newhall.sim.*;
import org.psu.newhall.util.*;

 public class Newhall {
   public static String NSM_VERSION = "1.6.5";

   public static void main(String[] args) {

             if (args.length < 4) {
               System.out.println("This is Java Newhall Simulation Model v"+NSM_VERSION);
               System.out.println("Usage: java -jar newhall-"+NSM_VERSION+".jar <path-to-CSV-file> <smcsawc> <soilAirOffset> <amplitude>");
             } else {
               NewhallDataset nd = new CSVFileParser(new File(args[0])).getDatset();
               System.out.println(nd.toString());
               NewhallResults nr = BASICSimulationModel.runSimulation(nd,
                                                                      Double.parseDouble(args[1]),
                                                                      Double.parseDouble(args[2]),
                                                                      Double.parseDouble(args[3]));
               System.out.println(nr.toString());
             }


             /* //Single model run example

             NewhallDataset nhd = new NewhallDataset("WILLIAMSPORT",
                                                               "US",
                                                               41.24,
                                                               -76.92,
                                                               'N',
                                                               'W',
                                                               158.0,
                                                               Arrays.stream(new double[] {44.2,40.39,113.54,96.77,95.0,98.55,
                                                                                 66.04,13.46,54.86,6.35,17.53,56.39}).boxed().collect(Collectors.toList()),
                                                               Arrays.stream(new double[] {-2.17,0.89,3.72,9.11,16.28,21.11,
                                                                                  22.83,21.94,19.78,10.5,5.33,-1.06}).boxed().collect(Collectors.toList()),
                                                               1930,
                                                               1930,
                                                               true,
                                                               200.0);
                System.out.println(nhd.toString());
                NewhallResults nr = BASICSimulationModel.runSimulation(nhd, 200);
                System.out.println(nr.toString()); */

   }
}

