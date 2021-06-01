/*     */ package org.jdom.output;
/*     */ 
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.helpers.LocatorImpl;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JDOMLocator
/*     */   extends LocatorImpl
/*     */ {
/*     */   private static final String CVS_ID = "@(#) $RCSfile: JDOMLocator.java,v $ $Revision: 1.4 $ $Date: 2007/11/10 05:29:01 $ $Name: jdom_1_1 $";
/*     */   private Object node;
/*     */   
/*     */   JDOMLocator() {}
/*     */   
/*     */   JDOMLocator(Locator locator) {
/*  92 */     super(locator);
/*     */     
/*  94 */     if (locator instanceof JDOMLocator) {
/*  95 */       setNode(((JDOMLocator)locator).getNode());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getNode() {
/* 105 */     return this.node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setNode(Object node) {
/* 115 */     this.node = node;
/*     */   }
/*     */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/jdom/output/JDOMLocator.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */