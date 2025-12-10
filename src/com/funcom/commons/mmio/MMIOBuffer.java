/*    */ package com.funcom.commons.mmio;
/*    */ 
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.nio.MappedByteBuffer;
/*    */ import java.nio.channels.FileChannel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MMIOBuffer
/*    */ {
/* 16 */   private FileInputStream fis = null;
/* 17 */   private FileChannel fc = null;
/* 18 */   private MappedByteBuffer mbb = null;
/*    */ 
/*    */   
/* 21 */   byte[] bfr = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MMIOBuffer(String FilePath) throws IOException {
/* 28 */     this.fis = new FileInputStream(FilePath);
/* 29 */     this.fc = this.fis.getChannel();
/* 30 */     this.mbb = this.fc.map(FileChannel.MapMode.READ_ONLY, 0L, this.fc.size());
/* 31 */     this.bfr = new byte[this.mbb.limit()];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MMIOBuffer(FileInputStream fis) throws IOException {
/* 39 */     this.fc = fis.getChannel();
/* 40 */     this.mbb = this.fc.map(FileChannel.MapMode.READ_ONLY, 0L, this.fc.size());
/* 41 */     this.bfr = new byte[this.mbb.limit()];
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] getBuffer() {
/* 46 */     this.mbb.get(this.bfr);
/* 47 */     return this.bfr;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\mmio\MMIOBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */