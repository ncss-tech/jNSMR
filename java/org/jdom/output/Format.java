/*     */ package org.jdom.output;
/*     */ 
/*     */ import java.lang.reflect.Method;
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
/*     */ public class Format
/*     */   implements Cloneable
/*     */ {
/*     */   private static final String CVS_ID = "@(#) $RCSfile: Format.java,v $ $Revision: 1.13 $ $Date: 2007/11/10 05:29:01 $ $Name: jdom_1_1 $";
/*     */   private static final String STANDARD_INDENT = "  ";
/*     */   private static final String STANDARD_LINE_SEPARATOR = "\r\n";
/*     */   private static final String STANDARD_ENCODING = "UTF-8";
/*     */   
/*     */   public static Format getRawFormat() {
/*  89 */     return new Format();
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
/*     */   public static Format getPrettyFormat() {
/* 103 */     Format f = new Format();
/* 104 */     f.setIndent("  ");
/* 105 */     f.setTextMode(TextMode.TRIM);
/* 106 */     return f;
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
/*     */   public static Format getCompactFormat() {
/* 119 */     Format f = new Format();
/* 120 */     f.setTextMode(TextMode.NORMALIZE);
/* 121 */     return f;
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
/*     */   
/* 135 */   String indent = null;
/*     */ 
/*     */   
/* 138 */   String lineSeparator = "\r\n";
/*     */ 
/*     */   
/* 141 */   String encoding = "UTF-8";
/*     */ 
/*     */ 
/*     */   
/*     */   boolean omitDeclaration = false;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean omitEncoding = false;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean expandEmptyElements = false;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean ignoreTrAXEscapingPIs = false;
/*     */ 
/*     */   
/* 160 */   TextMode mode = TextMode.PRESERVE;
/*     */ 
/*     */   
/* 163 */   EscapeStrategy escapeStrategy = new DefaultEscapeStrategy(this, this.encoding);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Class class$java$lang$String;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Format setEscapeStrategy(EscapeStrategy strategy) {
/* 177 */     this.escapeStrategy = strategy;
/* 178 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EscapeStrategy getEscapeStrategy() {
/* 187 */     return this.escapeStrategy;
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
/*     */   public Format setLineSeparator(String separator) {
/* 223 */     this.lineSeparator = separator;
/* 224 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLineSeparator() {
/* 233 */     return this.lineSeparator;
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
/*     */   
/*     */   public Format setOmitEncoding(boolean omitEncoding) {
/* 248 */     this.omitEncoding = omitEncoding;
/* 249 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getOmitEncoding() {
/* 258 */     return this.omitEncoding;
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
/*     */   public Format setOmitDeclaration(boolean omitDeclaration) {
/* 272 */     this.omitDeclaration = omitDeclaration;
/* 273 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getOmitDeclaration() {
/* 282 */     return this.omitDeclaration;
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
/*     */   public Format setExpandEmptyElements(boolean expandEmptyElements) {
/* 295 */     this.expandEmptyElements = expandEmptyElements;
/* 296 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getExpandEmptyElements() {
/* 305 */     return this.expandEmptyElements;
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
/*     */   public void setIgnoreTrAXEscapingPIs(boolean ignoreTrAXEscapingPIs) {
/* 336 */     this.ignoreTrAXEscapingPIs = ignoreTrAXEscapingPIs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIgnoreTrAXEscapingPIs() {
/* 346 */     return this.ignoreTrAXEscapingPIs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Format setTextMode(TextMode mode) {
/* 356 */     this.mode = mode;
/* 357 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextMode getTextMode() {
/* 366 */     return this.mode;
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
/*     */   public Format setIndent(String indent) {
/* 380 */     this.indent = indent;
/* 381 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIndent() {
/* 390 */     return this.indent;
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
/*     */   public Format setEncoding(String encoding) {
/* 402 */     this.encoding = encoding;
/* 403 */     this.escapeStrategy = new DefaultEscapeStrategy(this, encoding);
/* 404 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncoding() {
/* 413 */     return this.encoding;
/*     */   }
/*     */   
/*     */   protected Object clone() {
/* 417 */     Format format = null;
/*     */     
/*     */     try {
/* 420 */       format = (Format)super.clone();
/*     */     }
/* 422 */     catch (CloneNotSupportedException ce) {}
/*     */ 
/*     */     
/* 425 */     return format;
/*     */   }
/*     */ 
/*     */   
/*     */   class DefaultEscapeStrategy
/*     */     implements EscapeStrategy
/*     */   {
/*     */     private int bits;
/*     */     
/*     */     Object encoder;
/*     */     Method canEncode;
/*     */     private final Format this$0;
/*     */     
/*     */     public DefaultEscapeStrategy(Format this$0, String encoding) {
/* 439 */       this.this$0 = this$0;
/* 440 */       if ("UTF-8".equalsIgnoreCase(encoding) || "UTF-16".equalsIgnoreCase(encoding)) {
/*     */         
/* 442 */         this.bits = 16;
/*     */       }
/* 444 */       else if ("ISO-8859-1".equalsIgnoreCase(encoding) || "Latin1".equalsIgnoreCase(encoding)) {
/*     */         
/* 446 */         this.bits = 8;
/*     */       }
/* 448 */       else if ("US-ASCII".equalsIgnoreCase(encoding) || "ASCII".equalsIgnoreCase(encoding)) {
/*     */         
/* 450 */         this.bits = 7;
/*     */       } else {
/*     */         
/* 453 */         this.bits = 0;
/*     */         
/*     */         try {
/* 456 */           Class charsetClass = Class.forName("java.nio.charset.Charset");
/* 457 */           Class encoderClass = Class.forName("java.nio.charset.CharsetEncoder");
/* 458 */           Method forName = charsetClass.getMethod("forName", new Class[] { (Format.class$java$lang$String == null) ? (Format.class$java$lang$String = Format.class$("java.lang.String")) : Format.class$java$lang$String });
/* 459 */           Object charsetObj = forName.invoke(null, new Object[] { encoding });
/* 460 */           Method newEncoder = charsetClass.getMethod("newEncoder", null);
/* 461 */           this.encoder = newEncoder.invoke(charsetObj, null);
/* 462 */           this.canEncode = encoderClass.getMethod("canEncode", new Class[] { char.class });
/*     */         }
/* 464 */         catch (Exception ignored) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldEscape(char ch) {
/* 470 */       if (this.bits == 16) {
/* 471 */         return false;
/*     */       }
/* 473 */       if (this.bits == 8) {
/* 474 */         if (ch > 'ÿ') {
/* 475 */           return true;
/*     */         }
/* 477 */         return false;
/*     */       } 
/* 479 */       if (this.bits == 7) {
/* 480 */         if (ch > '') {
/* 481 */           return true;
/*     */         }
/* 483 */         return false;
/*     */       } 
/*     */       
/* 486 */       if (this.canEncode != null && this.encoder != null) {
/*     */         try {
/* 488 */           Boolean val = (Boolean)this.canEncode.invoke(this.encoder, new Object[] { new Character(ch) });
/* 489 */           return !val.booleanValue();
/*     */         }
/* 491 */         catch (Exception ignored) {}
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 497 */       return false;
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
/*     */   static Class class$(String x0) {
/*     */     try {
/*     */       return Class.forName(x0);
/*     */     } catch (ClassNotFoundException x1) {
/*     */       throw new NoClassDefFoundError(x1.getMessage());
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
/*     */   public static class TextMode
/*     */   {
/* 577 */     public static final TextMode PRESERVE = new TextMode("PRESERVE");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 582 */     public static final TextMode TRIM = new TextMode("TRIM");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 589 */     public static final TextMode NORMALIZE = new TextMode("NORMALIZE");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 595 */     public static final TextMode TRIM_FULL_WHITE = new TextMode("TRIM_FULL_WHITE");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     private TextMode(String name) {
/* 601 */       this.name = name;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 605 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/jdom/output/Format.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */