/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class GiftBoxOpenMessage
/*    */   implements Message
/*    */ {
/*    */   private int giftBoxId;
/*    */   
/*    */   public GiftBoxOpenMessage() {}
/*    */   
/*    */   public GiftBoxOpenMessage(int giftBoxId) {
/* 14 */     this.giftBoxId = giftBoxId;
/*    */   }
/*    */   
/*    */   public GiftBoxOpenMessage(ByteBuffer buffer) {
/* 18 */     this.giftBoxId = buffer.getInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 23 */     return new GiftBoxOpenMessage(buffer);
/*    */   }
/*    */   
/*    */   public int getGiftBoxId() {
/* 27 */     return this.giftBoxId;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 32 */     return 234;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 37 */     return 4;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 42 */     return buffer.putInt(this.giftBoxId);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\GiftBoxOpenMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */