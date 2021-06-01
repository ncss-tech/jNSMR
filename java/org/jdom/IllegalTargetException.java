/*    */ package org.jdom;
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
/*    */ 
/*    */ 
/*    */ public class IllegalTargetException
/*    */   extends IllegalArgumentException
/*    */ {
/*    */   private static final String CVS_ID = "@(#) $RCSfile: IllegalTargetException.java,v $ $Revision: 1.15 $ $Date: 2007/11/10 05:28:59 $ $Name: jdom_1_1 $";
/*    */   
/*    */   IllegalTargetException(String target, String reason) {
/* 80 */     super("The target \"" + target + "\" is not legal for JDOM/XML Processing Instructions: " + reason + ".");
/*    */   }
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
/*    */ 
/*    */   
/*    */   public IllegalTargetException(String reason) {
/* 95 */     super(reason);
/*    */   }
/*    */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/jdom/IllegalTargetException.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */