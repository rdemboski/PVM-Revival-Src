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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuyItemFromVendorMessage
/*    */   implements Message
/*    */ {
/*    */   private int playerId;
/*    */   private int vendorContainerId;
/*    */   private String itemId;
/*    */   private int tier;
/*    */   private int amount;
/*    */   private int purchaseMethod;
/*    */   
/*    */   public BuyItemFromVendorMessage() {}
/*    */   
/*    */   public BuyItemFromVendorMessage(int playerId, int vendorContainerId, String itemId, int tier, int amount, int purchaseMethod) {
/* 28 */     this.playerId = playerId;
/* 29 */     this.vendorContainerId = vendorContainerId;
/* 30 */     this.itemId = itemId;
/* 31 */     this.tier = tier;
/* 32 */     this.amount = amount;
/* 33 */     this.purchaseMethod = purchaseMethod;
/*    */   }
/*    */   
/*    */   public BuyItemFromVendorMessage(ByteBuffer byteBuffer) {
/* 37 */     this.playerId = MessageUtils.readInt(byteBuffer);
/* 38 */     this.vendorContainerId = MessageUtils.readInt(byteBuffer);
/* 39 */     this.itemId = MessageUtils.readStr(byteBuffer);
/* 40 */     this.tier = MessageUtils.readInt(byteBuffer);
/* 41 */     this.amount = MessageUtils.readInt(byteBuffer);
/* 42 */     this.purchaseMethod = MessageUtils.readInt(byteBuffer);
/*    */   }
/*    */   public short getMessageType() {
/* 45 */     return 23;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 49 */     return new BuyItemFromVendorMessage(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 53 */     return MessageUtils.getSizeInt() * 5 + MessageUtils.getSizeStr(getItemId());
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 57 */     MessageUtils.writeInt(buffer, this.playerId);
/* 58 */     MessageUtils.writeInt(buffer, this.vendorContainerId);
/* 59 */     MessageUtils.writeStr(buffer, this.itemId);
/* 60 */     MessageUtils.writeInt(buffer, this.tier);
/* 61 */     MessageUtils.writeInt(buffer, this.amount);
/* 62 */     MessageUtils.writeInt(buffer, this.purchaseMethod);
/* 63 */     return buffer;
/*    */   }
/*    */   
/*    */   public int getPlayerId() {
/* 67 */     return this.playerId;
/*    */   }
/*    */   
/*    */   public int getVendorContainerId() {
/* 71 */     return this.vendorContainerId;
/*    */   }
/*    */   
/*    */   public String getItemId() {
/* 75 */     return this.itemId;
/*    */   }
/*    */   
/*    */   public int getAmount() {
/* 79 */     return this.amount;
/*    */   }
/*    */   
/*    */   public int getPurchaseMethod() {
/* 83 */     return this.purchaseMethod;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 87 */     StringBuffer sb = new StringBuffer();
/* 88 */     sb.append("[playerId:").append(this.playerId).append(", vendorContainerId:").append(this.vendorContainerId).append(", itemId:").append(this.itemId).append(", tier:").append(this.tier).append(", amount:").append(this.amount).append(", purchaseMethod:").append(this.purchaseMethod).append("]");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 95 */     return sb.toString();
/*    */   }
/*    */   
/*    */   public int getTier() {
/* 99 */     return this.tier;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\BuyItemFromVendorMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */