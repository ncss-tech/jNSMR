/*    */ package org.psu.newhall.flerry;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jdom.JDOMException;
/*    */ import org.psu.newhall.sim.BASICSimulationModel;
/*    */ import org.psu.newhall.sim.NewhallDataset;
/*    */ import org.psu.newhall.sim.NewhallResults;
/*    */ import org.psu.newhall.util.XMLStringParser;
/*    */ import org.psu.newhall.util.XMLStringResultsExporter;
/*    */ 
/*    */ public class NewhallFlerryConnector
/*    */ {
/*    */   public static String runModel(String inputXmlFile, Boolean isMetric, Double waterHoldingCapacity, Double soilAirRel) {
/* 14 */     return runModelProper(inputXmlFile, isMetric.booleanValue(), waterHoldingCapacity.doubleValue(), soilAirRel.doubleValue());
/*    */   }
/*    */   
/*    */   public static String runModel(String inputXmlFile, Boolean isMetric, Integer waterHoldingCapacity, Integer soilAirRel) {
/* 18 */     return runModelProper(inputXmlFile, isMetric.booleanValue(), waterHoldingCapacity.doubleValue(), soilAirRel.doubleValue());
/*    */   }
/*    */   
/*    */   public static String runModel(String inputXmlFile, Boolean isMetric, Double waterHoldingCapacity, Integer soilAirRel) {
/* 22 */     return runModelProper(inputXmlFile, isMetric.booleanValue(), waterHoldingCapacity.doubleValue(), soilAirRel.doubleValue());
/*    */   }
/*    */   
/*    */   public static String runModel(String inputXmlFile, Boolean isMetric, Integer waterHoldingCapacity, Double soilAirRel) {
/* 26 */     return runModelProper(inputXmlFile, isMetric.booleanValue(), waterHoldingCapacity.doubleValue(), soilAirRel.doubleValue());
/*    */   }
/*    */ 
/*    */   
/*    */   private static String runModelProper(String inputXmlFile, boolean isMetric, double waterHoldingCapacity, double soilAirRel) {
/* 31 */     XMLStringParser xsp = null;
/* 32 */     NewhallDataset dataset = null;
/*    */ 
/*    */     
/*    */     try {
/* 36 */       xsp = new XMLStringParser(inputXmlFile, waterHoldingCapacity, soilAirRel);
/* 37 */       dataset = xsp.getDataset();
/* 38 */     } catch (JDOMException ex) {
/* 39 */       return "JDOMException encountered: " + ex.getMessage();
/* 40 */     } catch (IOException ex) {
/* 41 */       return "IOException encountered: " + ex.getMessage();
/*    */     } 
/*    */     
/* 44 */     NewhallResults results = BASICSimulationModel.runSimulation(dataset, dataset.getWaterholdingCapacity(), dataset
/* 45 */         .getMetadata().getSoilAirOffset(), dataset.getMetadata().getAmplitude());
/*    */     
/* 47 */     return XMLStringResultsExporter.export(results, dataset);
/*    */   }
/*    */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/psu/newhall/flerry/NewhallFlerryConnector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */