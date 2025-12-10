/*    */ package com.funcom.rpgengine2.deprecated;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.File;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.FileReader;
/*    */ import java.io.Reader;
/*    */ 
/*    */ @Deprecated
/*    */ public class DataFileImpl
/*    */   implements DataFile
/*    */ {
/*    */   private File file;
/*    */   
/*    */   public DataFileImpl(File file) {
/* 16 */     this.file = file;
/*    */   }
/*    */   
/*    */   public Reader getReader() throws FileNotFoundException {
/* 20 */     return new BufferedReader(new FileReader(this.file));
/*    */   }
/*    */   
/*    */   public String getFileName() {
/* 24 */     return this.file.getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\deprecated\DataFileImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */