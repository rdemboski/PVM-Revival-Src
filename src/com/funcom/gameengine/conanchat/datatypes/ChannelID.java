/*    */ package com.funcom.gameengine.conanchat.datatypes;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChannelID
/*    */   extends AbstractDatatype
/*    */ {
/*    */   public static final int SIZE_IN_BYTES = 5;
/*    */   private byte[] value;
/*    */   
/*    */   public ChannelID() {}
/*    */   
/*    */   public ChannelID(byte[] value) {
/* 19 */     this.value = value;
/*    */   }
/*    */   
/*    */   public ChannelID(ByteBuffer byteBuffer) {
/* 23 */     readValue(byteBuffer);
/*    */   }
/*    */   
/*    */   public ByteBuffer toByteBuffer(ByteBuffer byteBuffer) {
/* 27 */     byteBuffer.put(this.value);
/* 28 */     return byteBuffer;
/*    */   }
/*    */   
/*    */   public void readValue(ByteBuffer byteBuffer) {
/* 32 */     this.value = new byte[5];
/* 33 */     byteBuffer.get(this.value);
/*    */   }
/*    */   
/*    */   public int getSizeInBytes() {
/* 37 */     return 5;
/*    */   }
/*    */   
/*    */   public byte[] getValue() {
/* 41 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\datatypes\ChannelID.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */