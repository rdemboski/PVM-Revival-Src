/*    */ package com.funcom.commons.configuration;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CSVWriter
/*    */ {
/*    */   private Writer writer;
/*    */   private boolean isFirstLine;
/*    */   
/*    */   public CSVWriter(Writer writer) {
/* 16 */     this.writer = writer;
/* 17 */     this.isFirstLine = true;
/*    */   }
/*    */   
/*    */   public void close() throws IOException {
/* 21 */     this.writer.close();
/*    */   }
/*    */   
/*    */   public void writeRecord(String... fields) throws IOException {
/* 25 */     boolean isFirstField = true;
/* 26 */     for (String field : fields) {
/* 27 */       writeFieldOld(field, isFirstField);
/* 28 */       isFirstField = false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void writeField(String field, boolean firstField) throws IOException {
/* 33 */     if (!firstField) {
/* 34 */       this.writer.write(",");
/*    */     }
/* 36 */     this.writer.write("\"" + field + "\"");
/*    */   }
/*    */   
/*    */   public void writeFieldOld(String field, boolean firstField) throws IOException {
/* 40 */     if (!firstField) {
/* 41 */       this.writer.write(",");
/*    */     }
/* 43 */     else if (this.isFirstLine) {
/* 44 */       this.isFirstLine = false;
/*    */     } else {
/* 46 */       this.writer.write("\n");
/*    */     } 
/*    */     
/* 49 */     this.writer.write("\"" + field + "\"");
/*    */   }
/*    */   
/*    */   public void writeLine(String... fields) throws IOException {
/* 53 */     boolean isFirstField = true;
/* 54 */     for (String field : fields) {
/* 55 */       writeField(field, isFirstField);
/* 56 */       isFirstField = false;
/*    */     } 
/* 58 */     this.writer.write("\n");
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\configuration\CSVWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */