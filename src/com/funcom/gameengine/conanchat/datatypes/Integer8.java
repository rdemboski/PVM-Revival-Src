/*    */ package com.funcom.gameengine.conanchat.datatypes;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Integer8
/*    */   extends AbstractDatatype
/*    */ {
/*    */   public static final int SIZE_IN_BYTES = 1;
/* 13 */   public static final int MAXVALUE = (int)Math.pow(2.0D, 8.0D) - 1;
/*    */   
/*    */   public static final int MINVALUE = 0;
/*    */   private int value;
/*    */   
/*    */   public Integer8() {}
/*    */   
/*    */   public Integer8(int value) {
/* 21 */     if (value > MAXVALUE)
/* 22 */       throw new IllegalArgumentException("Max value is " + MAXVALUE + ", value=" + value + " is too large."); 
/* 23 */     if (value < 0)
/* 24 */       throw new IllegalArgumentException("Min value is 0, value=" + value + " is too small."); 
/* 25 */     this.value = value;
/*    */   }
/*    */   
/*    */   public Integer8(ByteBuffer byteBuffer) {
/* 29 */     readValue(byteBuffer);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Integer8(ByteBuffer byteBuffer, Endianess endianess) {
/* 39 */     this(byteBuffer);
/*    */   }
/*    */   
/*    */   public ByteBuffer toByteBuffer(ByteBuffer byteBuffer) {
/* 43 */     byte leByte = (byte)(this.value & 0xFF);
/* 44 */     byteBuffer.put(leByte);
/* 45 */     return byteBuffer;
/*    */   }
/*    */   
/*    */   public void readValue(ByteBuffer byteBuffer) {
/* 49 */     this.value = byteBuffer.get() & 0xFF;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSizeInBytes() {
/* 54 */     return 1;
/*    */   }
/*    */   
/*    */   public int getIntValue() {
/* 58 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\datatypes\Integer8.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */