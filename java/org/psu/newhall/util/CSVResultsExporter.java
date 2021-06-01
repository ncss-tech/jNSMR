/*    */ package org.psu.newhall.util;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileWriter;
/*    */ import java.io.PrintWriter;
/*    */ import javax.swing.JOptionPane;
/*    */ import org.psu.newhall.sim.NewhallResults;
/*    */ 
/*    */ public class CSVResultsExporter
/*    */ {
/*    */   private NewhallResults nr;
/*    */   private File outputFile;
/*    */   
/*    */   public CSVResultsExporter(NewhallResults nr, File outputFile) {
/* 15 */     this.nr = nr;
/* 16 */     this.outputFile = outputFile;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void save() {
/*    */     PrintWriter pw;
/*    */     try {
/* 25 */       FileWriter fw = new FileWriter(this.outputFile);
/* 26 */       pw = new PrintWriter(fw);
/* 27 */     } catch (Exception e) {
/* 28 */       JOptionPane.showMessageDialog(null, "Error occured while saving file: \n\n" + e.getMessage());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/*    */       return;
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 51 */     pw.print(this.nr.getFlxFile());
/* 52 */     pw.close();
/*    */   }
/*    */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/psu/newhall/util/CSVResultsExporter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */