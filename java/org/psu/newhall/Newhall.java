/*    */ package org.psu.newhall;
/*    */
/*    */ // import javax.swing.UIManager;
/*    */ // import org.psu.newhall.ui.DefaultNewhallFrame;
         import java.util.Arrays;
         import java.util.stream.Stream;
         import java.util.stream.Collectors;
/*    */ import org.psu.newhall.sim.*;
         import org.psu.newhall.util.*;
/*    */ public class Newhall
/*    */ {
/*  8 */   public static String NSM_VERSION = "1.6.2";
/*    */
/*    */
/*    */
/*    */   public static void main(String[] args) {

             System.out.println("This is Java Newhall Simulation Model v"+NSM_VERSION);
             System.out.println("Running in demo mode...");

/* 13 */     //System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Newhall");
/*    */     //try {
/* 16 */     //  UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
/* 17 */     //} catch (Exception e) {
/* 18 */     //  System.out.println(e);
/*    */     //}
/*    */
/* 21 */     //DefaultNewhallFrame dnf = new DefaultNewhallFrame();
/* 22 */     //dnf.setLocation(100, 100);
/* 23 */     //dnf.setVisible(true);
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
                System.out.println(nr.toString());


/*    */   }
/*    */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/psu/newhall/Newhall.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
