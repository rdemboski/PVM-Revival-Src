/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArrangeItemsInInventoryMessage
/*    */   implements Message
/*    */ {
/*    */   private int fromSlotId;
/*    */   private int toSlotId;
/*    */   
/*    */   public ArrangeItemsInInventoryMessage(int fromSlotId, int toSlotId) {
/* 18 */     this.fromSlotId = fromSlotId;
/* 19 */     this.toSlotId = toSlotId;
/*    */   }
/*    */   
/*    */   public ArrangeItemsInInventoryMessage(ByteBuffer buffer) {
/* 23 */     this.fromSlotId = MessageUtils.readInt(buffer);
/* 24 */     this.toSlotId = MessageUtils.readInt(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public ArrangeItemsInInventoryMessage() {}
/*    */   
/*    */   public short getMessageType() {
/* 31 */     return 219;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 35 */     return new ArrangeItemsInInventoryMessage(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 39 */     return MessageUtils.getSizeInt() * 2;
/*    */   }
/*    */   
/*    */   public int getFromSlotId() {
/* 43 */     return this.fromSlotId;
/*    */   }
/*    */   
/*    */   public int getToSlotId() {
/* 47 */     return this.toSlotId;
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 51 */     MessageUtils.writeInt(buffer, this.fromSlotId);
/* 52 */     MessageUtils.writeInt(buffer, this.toSlotId);
/* 53 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\ArrangeItemsInInventoryMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */