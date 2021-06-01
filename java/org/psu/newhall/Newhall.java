/*    */ package org.psu.newhall;
/*    */ 
/*    */ import javax.swing.UIManager;
/*    */ import org.psu.newhall.ui.DefaultNewhallFrame;
/*    */ 
/*    */ public class Newhall
/*    */ {
/*  8 */   public static String NSM_VERSION = "1.6.1";
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] args) {
/* 13 */     System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Newhall");
/*    */     
/*    */     try {
/* 16 */       UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
/* 17 */     } catch (Exception e) {
/* 18 */       System.out.println(e);
/*    */     } 
/*    */     
/* 21 */     DefaultNewhallFrame dnf = new DefaultNewhallFrame();
/* 22 */     dnf.setLocation(100, 100);
/* 23 */     dnf.setVisible(true);
/*    */   }
/*    */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/psu/newhall/Newhall.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */