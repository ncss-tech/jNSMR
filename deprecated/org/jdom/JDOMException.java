/*     */ package org.jdom;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import org.xml.sax.SAXException;
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
/*     */ public class JDOMException
/*     */   extends Exception
/*     */ {
/*     */   private static final String CVS_ID = "@(#) $RCSfile: JDOMException.java,v $ $Revision: 1.24 $ $Date: 2007/11/10 05:28:59 $ $Name: jdom_1_1 $";
/*     */   private Throwable cause;
/*     */   
/*     */   public JDOMException() {
/*  88 */     super("Error occurred in JDOM application.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JDOMException(String message) {
/*  98 */     super(message);
/*     */   }
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
/*     */   public JDOMException(String message, Throwable cause) {
/* 112 */     super(message);
/* 113 */     this.cause = cause;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable initCause(Throwable cause) {
/* 125 */     this.cause = cause;
/* 126 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/* 138 */     String msg = super.getMessage();
/*     */     
/* 140 */     Throwable parent = this;
/*     */     
/*     */     Throwable child;
/*     */     
/* 144 */     while ((child = getNestedException(parent)) != null) {
/*     */       
/* 146 */       String msg2 = child.getMessage();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 151 */       if (child instanceof SAXException) {
/* 152 */         Throwable grandchild = ((SAXException)child).getException();
/*     */ 
/*     */ 
/*     */         
/* 156 */         if (grandchild != null && msg2 != null && msg2.equals(grandchild.getMessage())) {
/* 157 */           msg2 = null;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 162 */       if (msg2 != null) {
/* 163 */         if (msg != null) {
/* 164 */           msg = msg + ": " + msg2;
/*     */         } else {
/* 166 */           msg = msg2;
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 172 */       if (child instanceof JDOMException) {
/*     */         break;
/*     */       }
/* 175 */       parent = child;
/*     */     } 
/*     */ 
/*     */     
/* 179 */     return msg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printStackTrace() {
/* 189 */     super.printStackTrace();
/*     */     
/* 191 */     Throwable parent = this;
/*     */     
/*     */     Throwable child;
/*     */     
/* 195 */     while ((child = getNestedException(parent)) != null) {
/* 196 */       System.err.print("Caused by: ");
/* 197 */       child.printStackTrace();
/*     */ 
/*     */       
/* 200 */       if (child instanceof JDOMException) {
/*     */         break;
/*     */       }
/* 203 */       parent = child;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printStackTrace(PrintStream s) {
/* 216 */     super.printStackTrace(s);
/*     */     
/* 218 */     Throwable parent = this;
/*     */     
/*     */     Throwable child;
/*     */     
/* 222 */     while ((child = getNestedException(parent)) != null) {
/* 223 */       s.print("Caused by: ");
/* 224 */       child.printStackTrace(s);
/*     */ 
/*     */       
/* 227 */       if (child instanceof JDOMException) {
/*     */         break;
/*     */       }
/* 230 */       parent = child;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printStackTrace(PrintWriter w) {
/* 243 */     super.printStackTrace(w);
/*     */     
/* 245 */     Throwable parent = this;
/*     */     
/*     */     Throwable child;
/*     */     
/* 249 */     while ((child = getNestedException(parent)) != null) {
/* 250 */       w.print("Caused by: ");
/* 251 */       child.printStackTrace(w);
/*     */ 
/*     */       
/* 254 */       if (child instanceof JDOMException) {
/*     */         break;
/*     */       }
/* 257 */       parent = child;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable getCause() {
/* 268 */     return this.cause;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Throwable getNestedException(Throwable parent) {
/* 274 */     if (parent instanceof JDOMException) {
/* 275 */       return ((JDOMException)parent).getCause();
/*     */     }
/*     */     
/* 278 */     if (parent instanceof SAXException) {
/* 279 */       return ((SAXException)parent).getException();
/*     */     }
/*     */     
/* 282 */     if (parent instanceof SQLException) {
/* 283 */       return ((SQLException)parent).getNextException();
/*     */     }
/*     */     
/* 286 */     if (parent instanceof InvocationTargetException) {
/* 287 */       return ((InvocationTargetException)parent).getTargetException();
/*     */     }
/*     */     
/* 290 */     if (parent instanceof ExceptionInInitializerError) {
/* 291 */       return ((ExceptionInInitializerError)parent).getException();
/*     */     }
/*     */     
/* 294 */     if (parent instanceof RemoteException) {
/* 295 */       return ((RemoteException)parent).detail;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 301 */     Throwable nestedException = getNestedException(parent, "javax.naming.NamingException", "getRootCause");
/* 302 */     if (nestedException != null) {
/* 303 */       return nestedException;
/*     */     }
/*     */     
/* 306 */     nestedException = getNestedException(parent, "javax.servlet.ServletException", "getRootCause");
/* 307 */     if (nestedException != null) {
/* 308 */       return nestedException;
/*     */     }
/*     */     
/* 311 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Throwable getNestedException(Throwable parent, String className, String methodName) {
/*     */     try {
/* 320 */       Class testClass = Class.forName(className);
/* 321 */       Class objectClass = parent.getClass();
/* 322 */       if (testClass.isAssignableFrom(objectClass))
/*     */       {
/* 324 */         Class[] argClasses = new Class[0];
/* 325 */         Method method = testClass.getMethod(methodName, argClasses);
/* 326 */         Object[] args = new Object[0];
/* 327 */         return (Throwable)method.invoke(parent, args);
/*     */       }
/*     */     
/* 330 */     } catch (Exception ex) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 337 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/jdom/JDOMException.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */