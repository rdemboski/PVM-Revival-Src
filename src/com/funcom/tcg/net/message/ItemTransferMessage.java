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
/*    */ public class ItemTransferMessage
/*    */   implements Message
/*    */ {
/*    */   private int sourceInventoryId;
/*    */   private int targetInventoryId;
/*    */   private int sourceInventoryType;
/*    */   private int targetInventoryType;
/*    */   private int sourceSlotId;
/*    */   private int targetSlotId;
/*    */   
/*    */   public ItemTransferMessage() {}
/*    */   
/*    */   public ItemTransferMessage(int sourceInventoryId, int targetInventoryId, int sourceSlotId, int targetSlotId, int sourceInventoryType, int targetInventoryType) {
/* 24 */     this.sourceInventoryId = sourceInventoryId;
/* 25 */     this.targetInventoryId = targetInventoryId;
/* 26 */     this.sourceInventoryType = sourceInventoryType;
/* 27 */     this.targetInventoryType = targetInventoryType;
/* 28 */     this.sourceSlotId = sourceSlotId;
/* 29 */     this.targetSlotId = targetSlotId;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemTransferMessage(ByteBuffer byteBuffer) {
/* 34 */     this.sourceInventoryId = MessageUtils.readInt(byteBuffer);
/* 35 */     this.targetInventoryId = MessageUtils.readInt(byteBuffer);
/* 36 */     this.sourceInventoryType = MessageUtils.readInt(byteBuffer);
/* 37 */     this.targetInventoryType = MessageUtils.readInt(byteBuffer);
/* 38 */     this.sourceSlotId = MessageUtils.readInt(byteBuffer);
/* 39 */     this.targetSlotId = MessageUtils.readInt(byteBuffer);
/*    */   }
/*    */   
/*    */   public int getSourceInventoryId() {
/* 43 */     return this.sourceInventoryId;
/*    */   }
/*    */   
/*    */   public int getTargetInventoryId() {
/* 47 */     return this.targetInventoryId;
/*    */   }
/*    */   
/*    */   public int getSourceSlotId() {
/* 51 */     return this.sourceSlotId;
/*    */   }
/*    */   
/*    */   public int getTargetSlotId() {
/* 55 */     return this.targetSlotId;
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 59 */     return 204;
/*    */   }
/*    */   
/*    */   public ItemTransferMessage toMessage(ByteBuffer buffer) {
/* 63 */     return new ItemTransferMessage(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 67 */     return MessageUtils.getSizeInt() * 6;
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 71 */     MessageUtils.writeInt(buffer, this.sourceInventoryId);
/* 72 */     MessageUtils.writeInt(buffer, this.targetInventoryId);
/* 73 */     MessageUtils.writeInt(buffer, this.sourceInventoryType);
/* 74 */     MessageUtils.writeInt(buffer, this.targetInventoryType);
/* 75 */     MessageUtils.writeInt(buffer, this.sourceSlotId);
/* 76 */     MessageUtils.writeInt(buffer, this.targetSlotId);
/* 77 */     return buffer;
/*    */   }
/*    */   
/*    */   public int getSourceInventoryType() {
/* 81 */     return this.sourceInventoryType;
/*    */   }
/*    */   
/*    */   public int getTargetInventoryType() {
/* 85 */     return this.targetInventoryType;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\ItemTransferMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */