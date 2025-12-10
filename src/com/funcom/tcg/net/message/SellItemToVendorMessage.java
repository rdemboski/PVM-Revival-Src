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
/*    */ public class SellItemToVendorMessage
/*    */   implements Message
/*    */ {
/*    */   private int clientId;
/*    */   private int containerId;
/*    */   private int containerType;
/*    */   private int slotId;
/*    */   private int amount;
/*    */   private int vendorContainerId;
/*    */   
/*    */   public SellItemToVendorMessage(int clientId, int containerId, int containerType, int slotId, int amount, int vendorId) {
/* 22 */     this.clientId = clientId;
/* 23 */     this.containerId = containerId;
/* 24 */     this.containerType = containerType;
/* 25 */     this.slotId = slotId;
/* 26 */     this.amount = amount;
/* 27 */     this.vendorContainerId = vendorId;
/*    */   }
/*    */ 
/*    */   
/*    */   public SellItemToVendorMessage() {}
/*    */   
/*    */   public SellItemToVendorMessage(ByteBuffer buffer) {
/* 34 */     this.clientId = MessageUtils.readInt(buffer);
/* 35 */     this.containerId = MessageUtils.readInt(buffer);
/* 36 */     this.containerType = MessageUtils.readInt(buffer);
/* 37 */     this.slotId = MessageUtils.readInt(buffer);
/* 38 */     this.amount = MessageUtils.readInt(buffer);
/* 39 */     this.vendorContainerId = MessageUtils.readInt(buffer);
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 43 */     return 24;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 47 */     return new SellItemToVendorMessage(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 51 */     return MessageUtils.getSizeInt() * 6;
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 55 */     MessageUtils.writeInt(buffer, this.clientId);
/* 56 */     MessageUtils.writeInt(buffer, this.containerId);
/* 57 */     MessageUtils.writeInt(buffer, this.containerType);
/* 58 */     MessageUtils.writeInt(buffer, this.slotId);
/* 59 */     MessageUtils.writeInt(buffer, this.amount);
/* 60 */     MessageUtils.writeInt(buffer, this.vendorContainerId);
/* 61 */     return buffer;
/*    */   }
/*    */   
/*    */   public int getClientId() {
/* 65 */     return this.clientId;
/*    */   }
/*    */   
/*    */   public int getContainerId() {
/* 69 */     return this.containerId;
/*    */   }
/*    */   
/*    */   public int getContainerType() {
/* 73 */     return this.containerType;
/*    */   }
/*    */   
/*    */   public int getSlotId() {
/* 77 */     return this.slotId;
/*    */   }
/*    */   
/*    */   public int getAmount() {
/* 81 */     return this.amount;
/*    */   }
/*    */   
/*    */   public int getVendorContainerId() {
/* 85 */     return this.vendorContainerId;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 89 */     StringBuffer sb = new StringBuffer();
/* 90 */     sb.append("[clientId:").append(this.clientId).append(", containerId:").append(this.containerId).append(", containerType:").append(this.containerType).append(", slotId:").append(this.slotId).append(", amount:").append(this.amount).append(", vendorId:").append(this.vendorContainerId).append("]");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 97 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\SellItemToVendorMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */