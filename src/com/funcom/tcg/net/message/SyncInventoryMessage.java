/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ public class SyncInventoryMessage
/*    */   implements Message
/*    */ {
/*    */   private int inventoryId;
/*    */   private int tier;
/*    */   private int inventoryType;
/*    */   private String itemId;
/*    */   private int slotId;
/*    */   private int amount;
/*    */   
/*    */   public SyncInventoryMessage() {}
/*    */   
/*    */   public SyncInventoryMessage(int inventoryId, String itemId, int tier, int slotId, int amount, int inventoryType) {
/* 21 */     this.inventoryId = inventoryId;
/* 22 */     this.inventoryType = inventoryType;
/* 23 */     this.itemId = itemId;
/* 24 */     this.tier = tier;
/* 25 */     this.slotId = slotId;
/* 26 */     this.amount = amount;
/*    */   }
/*    */   
/*    */   public SyncInventoryMessage(ByteBuffer byteBuffer) {
/* 30 */     this.inventoryId = MessageUtils.readInt(byteBuffer);
/* 31 */     this.inventoryType = MessageUtils.readInt(byteBuffer);
/* 32 */     this.itemId = MessageUtils.readStr(byteBuffer);
/* 33 */     this.tier = MessageUtils.readInt(byteBuffer);
/* 34 */     this.slotId = MessageUtils.readInt(byteBuffer);
/* 35 */     this.amount = MessageUtils.readInt(byteBuffer);
/*    */   }
/*    */   
/*    */   public int getInventoryId() {
/* 39 */     return this.inventoryId;
/*    */   }
/*    */   
/*    */   public int getInventoryType() {
/* 43 */     return this.inventoryType;
/*    */   }
/*    */   
/*    */   public String getItemId() {
/* 47 */     return this.itemId;
/*    */   }
/*    */   
/*    */   public int getSlotId() {
/* 51 */     return this.slotId;
/*    */   }
/*    */   
/*    */   public int getAmount() {
/* 55 */     return this.amount;
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 59 */     return 203;
/*    */   }
/*    */   
/*    */   public SyncInventoryMessage toMessage(ByteBuffer buffer) {
/* 63 */     return new SyncInventoryMessage(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 67 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeInt() + MessageUtils.getSizeStr(getItemId()) + MessageUtils.getSizeInt() + MessageUtils.getSizeInt() + MessageUtils.getSizeInt();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 76 */     MessageUtils.writeInt(buffer, this.inventoryId);
/* 77 */     MessageUtils.writeInt(buffer, this.inventoryType);
/* 78 */     MessageUtils.writeStr(buffer, this.itemId);
/* 79 */     MessageUtils.writeInt(buffer, this.tier);
/* 80 */     MessageUtils.writeInt(buffer, this.slotId);
/* 81 */     MessageUtils.writeInt(buffer, this.amount);
/* 82 */     return buffer;
/*    */   }
/*    */   public String toString() {
/* 85 */     return getClass().getName() + "[inventoryId=" + this.inventoryId + ",itemContainer=" + this.inventoryType + ",itemId=" + this.itemId + ",slotId=" + this.slotId + ",amount=" + this.amount + "]";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTier() {
/* 95 */     return this.tier;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\SyncInventoryMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */