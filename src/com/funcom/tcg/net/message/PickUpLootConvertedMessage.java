/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PickUpLootConvertedMessage
/*    */   implements Message
/*    */ {
/*    */   private String pickUpId;
/*    */   
/*    */   public PickUpLootConvertedMessage() {}
/*    */   
/*    */   public PickUpLootConvertedMessage(String pickUpId) {
/* 18 */     this.pickUpId = pickUpId;
/*    */   }
/*    */   
/*    */   public PickUpLootConvertedMessage(ByteBuffer buffer) {
/* 22 */     this.pickUpId = MessageUtils.readStr(buffer);
/*    */   }
/*    */   
/*    */   public String getPickUpId() {
/* 26 */     return this.pickUpId;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 31 */     return 54;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 36 */     return new PickUpLootConvertedMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 41 */     return MessageUtils.getSizeStr(this.pickUpId);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 46 */     MessageUtils.writeStr(buffer, this.pickUpId);
/* 47 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\PickUpLootConvertedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */