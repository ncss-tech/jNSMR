/*     */ package org.jdom;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.jdom.output.XMLOutputter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProcessingInstruction
/*     */   extends Content
/*     */ {
/*     */   private static final String CVS_ID = "@(#) $RCSfile: ProcessingInstruction.java,v $ $Revision: 1.47 $ $Date: 2007/11/10 05:28:59 $ $Name: jdom_1_1 $";
/*     */   protected String target;
/*     */   protected String rawData;
/*     */   protected Map mapData;
/*     */   
/*     */   protected ProcessingInstruction() {}
/*     */   
/*     */   public ProcessingInstruction(String target, Map data) {
/* 104 */     setTarget(target);
/* 105 */     setData(data);
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
/*     */   public ProcessingInstruction(String target, String data) {
/* 118 */     setTarget(target);
/* 119 */     setData(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProcessingInstruction setTarget(String newTarget) {
/*     */     String reason;
/* 130 */     if ((reason = Verifier.checkProcessingInstructionTarget(newTarget)) != null)
/*     */     {
/* 132 */       throw new IllegalTargetException(newTarget, reason);
/*     */     }
/*     */     
/* 135 */     this.target = newTarget;
/* 136 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/* 146 */     return this.rawData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTarget() {
/* 156 */     return this.target;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getData() {
/* 165 */     return this.rawData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getPseudoAttributeNames() {
/* 176 */     Set mapDataSet = this.mapData.entrySet();
/* 177 */     List nameList = new ArrayList();
/* 178 */     for (Iterator i = mapDataSet.iterator(); i.hasNext(); ) {
/* 179 */       String wholeSet = i.next().toString();
/* 180 */       String attrName = wholeSet.substring(0, wholeSet.indexOf("="));
/* 181 */       nameList.add(attrName);
/*     */     } 
/* 183 */     return nameList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProcessingInstruction setData(String data) {
/* 193 */     String reason = Verifier.checkProcessingInstructionData(data);
/* 194 */     if (reason != null) {
/* 195 */       throw new IllegalDataException(data, reason);
/*     */     }
/*     */     
/* 198 */     this.rawData = data;
/* 199 */     this.mapData = parseData(data);
/* 200 */     return this;
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
/*     */   public ProcessingInstruction setData(Map data) {
/* 213 */     String temp = toString(data);
/*     */     
/* 215 */     String reason = Verifier.checkProcessingInstructionData(temp);
/* 216 */     if (reason != null) {
/* 217 */       throw new IllegalDataException(temp, reason);
/*     */     }
/*     */     
/* 220 */     this.rawData = temp;
/* 221 */     this.mapData = data;
/* 222 */     return this;
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
/*     */   public String getPseudoAttributeValue(String name) {
/* 236 */     return (String)this.mapData.get(name);
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
/*     */   public ProcessingInstruction setPseudoAttribute(String name, String value) {
/* 249 */     String reason = Verifier.checkProcessingInstructionData(name);
/* 250 */     if (reason != null) {
/* 251 */       throw new IllegalDataException(name, reason);
/*     */     }
/*     */     
/* 254 */     reason = Verifier.checkProcessingInstructionData(value);
/* 255 */     if (reason != null) {
/* 256 */       throw new IllegalDataException(value, reason);
/*     */     }
/*     */     
/* 259 */     this.mapData.put(name, value);
/* 260 */     this.rawData = toString(this.mapData);
/* 261 */     return this;
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
/*     */   public boolean removePseudoAttribute(String name) {
/* 273 */     if (this.mapData.remove(name) != null) {
/* 274 */       this.rawData = toString(this.mapData);
/* 275 */       return true;
/*     */     } 
/*     */     
/* 278 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String toString(Map mapData) {
/* 288 */     StringBuffer rawData = new StringBuffer();
/*     */     
/* 290 */     Iterator i = mapData.keySet().iterator();
/* 291 */     while (i.hasNext()) {
/* 292 */       String name = i.next();
/* 293 */       String value = (String)mapData.get(name);
/* 294 */       rawData.append(name).append("=\"").append(value).append("\" ");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 300 */     if (rawData.length() > 0) {
/* 301 */       rawData.setLength(rawData.length() - 1);
/*     */     }
/*     */     
/* 304 */     return rawData.toString();
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
/*     */   private Map parseData(String rawData) {
/* 323 */     Map data = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 328 */     String inputData = rawData.trim();
/*     */ 
/*     */     
/* 331 */     while (!inputData.trim().equals("")) {
/*     */ 
/*     */ 
/*     */       
/* 335 */       String name = "";
/* 336 */       String value = "";
/* 337 */       int startName = 0;
/* 338 */       char previousChar = inputData.charAt(startName);
/* 339 */       int pos = 1;
/* 340 */       for (; pos < inputData.length(); pos++) {
/* 341 */         char currentChar = inputData.charAt(pos);
/* 342 */         if (currentChar == '=') {
/* 343 */           name = inputData.substring(startName, pos).trim();
/*     */ 
/*     */           
/* 346 */           int[] bounds = extractQuotedString(inputData.substring(pos + 1));
/*     */ 
/*     */           
/* 349 */           if (bounds == null) {
/* 350 */             return new HashMap();
/*     */           }
/* 352 */           value = inputData.substring(bounds[0] + pos + 1, bounds[1] + pos + 1);
/*     */           
/* 354 */           pos += bounds[1] + 1;
/*     */           break;
/*     */         } 
/* 357 */         if (Character.isWhitespace(previousChar) && !Character.isWhitespace(currentChar))
/*     */         {
/* 359 */           startName = pos;
/*     */         }
/*     */         
/* 362 */         previousChar = currentChar;
/*     */       } 
/*     */ 
/*     */       
/* 366 */       inputData = inputData.substring(pos);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 373 */       if (name.length() > 0 && value != null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 379 */         data.put(name, value);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 384 */     return data;
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
/*     */   private static int[] extractQuotedString(String rawData) {
/* 404 */     boolean inQuotes = false;
/*     */ 
/*     */     
/* 407 */     char quoteChar = '"';
/*     */ 
/*     */ 
/*     */     
/* 411 */     int start = 0;
/*     */ 
/*     */ 
/*     */     
/* 415 */     for (int pos = 0; pos < rawData.length(); pos++) {
/* 416 */       char currentChar = rawData.charAt(pos);
/* 417 */       if (currentChar == '"' || currentChar == '\'') {
/* 418 */         if (!inQuotes) {
/*     */           
/* 420 */           quoteChar = currentChar;
/* 421 */           inQuotes = true;
/* 422 */           start = pos + 1;
/*     */         }
/* 424 */         else if (quoteChar == currentChar) {
/*     */           
/* 426 */           inQuotes = false;
/* 427 */           return new int[] { start, pos };
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 434 */     return null;
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
/*     */   public String toString() {
/* 448 */     return "[ProcessingInstruction: " + (new XMLOutputter()).outputString(this) + "]";
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
/*     */   public Object clone() {
/* 462 */     ProcessingInstruction pi = (ProcessingInstruction)super.clone();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 468 */     if (this.mapData != null) {
/* 469 */       pi.mapData = parseData(this.rawData);
/*     */     }
/* 471 */     return pi;
/*     */   }
/*     */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/jdom/ProcessingInstruction.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */