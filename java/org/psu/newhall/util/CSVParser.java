/*    */ package org.psu.newhall.util;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.DataInputStream;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStreamReader;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ public class CSVParser
/*    */ {
/*    */   String filePath;
/*    */   ArrayList<String> headers;
/*    */   ArrayList<ArrayList<String>> records;
/*    */   
/*    */   public CSVParser(String filePath, boolean hasHeader) throws FileNotFoundException, IOException {
/* 19 */     FileInputStream fis = new FileInputStream(filePath);
/* 20 */     DataInputStream dis = new DataInputStream(fis);
/* 21 */     InputStreamReader isr = new InputStreamReader(dis);
/* 22 */     BufferedReader br = new BufferedReader(isr);
/*    */     
/* 24 */     this.filePath = filePath;
/* 25 */     this.headers = new ArrayList<String>();
/* 26 */     this.records = new ArrayList<ArrayList<String>>();
/* 27 */     String line = "";
/*    */     
/* 29 */     if (hasHeader) {
/* 30 */       line = br.readLine();
/* 31 */       for (String str : line.split(",")) {
/* 32 */         this.headers.add(str);
/*    */       }
/*    */     } 
/*    */     
/* 36 */     while ((line = br.readLine()) != null) {
/* 37 */       ArrayList<String> holder = new ArrayList<String>();
/* 38 */       for (String str : line.split(",")) {
/* 39 */         holder.add(str);
/*    */       }
/* 41 */       this.records.add(holder);
/*    */     } 
/*    */     
/* 44 */     fis.close();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getFilePath() {
/* 49 */     return this.filePath;
/*    */   }
/*    */   
/*    */   public ArrayList<String> getHeaders() {
/* 53 */     return this.headers;
/*    */   }
/*    */   
/*    */   public ArrayList<ArrayList<String>> getRecords() {
/* 57 */     return this.records;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 62 */     String result = getClass().toString();
/* 63 */     result = result + "\n  " + this.filePath;
/* 64 */     if (!this.headers.isEmpty()) {
/* 65 */       result = result + "\n  ";
/* 66 */       for (String str : this.headers) {
/* 67 */         result = result + str + " ";
/*    */       }
/*    */     } else {
/* 70 */       result = result + "\n  File has no headers.";
/*    */     } 
/* 72 */     for (ArrayList<String> row : this.records) {
/* 73 */       result = result + "\n    ";
/* 74 */       for (String str : row) {
/* 75 */         result = result + str + " ";
/*    */       }
/*    */     } 
/* 78 */     return result;
/*    */   }
/*    */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/psu/newhall/util/CSVParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */