/*    */ package com.funcom.server.common;
/*    */ 
/*    */ import java.nio.ByteBuffer;
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
/*    */ public class IOBufferManager
/*    */ {
/*    */   private static final int WRITE_BUFFER_MAGIC_NUMBER = -889274641;
/*    */   
/*    */   public ByteBuffer getForRead(int requiredCapacity) {
/* 19 */     return ByteBuffer.allocate(requiredCapacity);
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
/*    */   
/*    */   public ByteBuffer getForWrite(int requiredCapacity) {
/* 35 */     ByteBuffer buffer = ByteBuffer.allocate(requiredCapacity + 4);
/* 36 */     buffer.putInt(-889274641);
/* 37 */     return buffer;
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
/*    */   public void prepareForWrite(ByteBuffer buffer) {
/* 50 */     prepareForWrite(buffer, true);
/*    */   }
/*    */   
/*    */   public void prepareForWrite(ByteBuffer buffer, boolean check) {
/* 54 */     if (check && buffer.getInt(0) != -889274641) {
/* 55 */       throw new MessageIOFatalException("buffer to be prepared is not a write buffer");
/*    */     }
/*    */ 
/*    */     
/* 59 */     buffer.putInt(0, buffer.position());
/*    */ 
/*    */     
/* 62 */     buffer.flip();
/*    */   }
/*    */   
/*    */   public void moveToWriteData(ByteBuffer writeBuffer) {
/* 66 */     writeBuffer.position(4);
/*    */   }
/*    */   
/*    */   public void putForReuse(ByteBuffer buffer) {}
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\common\IOBufferManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */