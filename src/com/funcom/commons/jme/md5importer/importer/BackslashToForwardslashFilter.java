/*    */ package com.funcom.commons.jme.md5importer.importer;
/*    */ 
/*    */ import java.io.FilterInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
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
/*    */ public class BackslashToForwardslashFilter
/*    */   extends FilterInputStream
/*    */ {
/*    */   public BackslashToForwardslashFilter(InputStream in) {
/* 20 */     super(in);
/*    */   }
/*    */   
/*    */   public int read() throws IOException {
/* 24 */     int b = this.in.read();
/* 25 */     b = filter(b);
/* 26 */     return b;
/*    */   }
/*    */   
/*    */   public int read(byte[] data, int offset, int length) throws IOException {
/* 30 */     int result = this.in.read(data, offset, length);
/* 31 */     for (int i = offset; i < offset + result; i++) {
/* 32 */       data[i] = filter(data[i]);
/*    */     }
/* 34 */     return result;
/*    */   }
/*    */   
/*    */   private int filter(int i) {
/* 38 */     if (i == 92) {
/* 39 */       return 47;
/*    */     }
/* 41 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   private byte filter(byte b) {
/* 46 */     if (b == 92) {
/* 47 */       return 47;
/*    */     }
/* 49 */     return b;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\importer\BackslashToForwardslashFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */