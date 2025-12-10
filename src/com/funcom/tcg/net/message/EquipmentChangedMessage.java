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
/*    */ public class EquipmentChangedMessage
/*    */   implements Message
/*    */ {
/*    */   private static final short TYPE_ID = 216;
/*    */   private String itemId;
/*    */   private int playerId;
/*    */   private int placement;
/*    */   private boolean equipped;
/*    */   private int tier;
/*    */   
/*    */   public EquipmentChangedMessage(String itemId, int tier, int inventoryId, int placement, boolean equipped) {
/* 24 */     this.itemId = itemId;
/* 25 */     this.tier = tier;
/* 26 */     this.playerId = inventoryId;
/* 27 */     this.placement = placement;
/* 28 */     this.equipped = equipped;
/*    */   }
/*    */   
/*    */   public EquipmentChangedMessage(ByteBuffer buffer) {
/* 32 */     this.itemId = MessageUtils.readStr(buffer);
/* 33 */     this.tier = MessageUtils.readInt(buffer);
/* 34 */     this.playerId = MessageUtils.readInt(buffer);
/* 35 */     this.placement = MessageUtils.readInt(buffer);
/* 36 */     this.equipped = MessageUtils.readBoolean(buffer).booleanValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public EquipmentChangedMessage() {}
/*    */   
/*    */   public String getItemId() {
/* 43 */     return this.itemId;
/*    */   }
/*    */   
/*    */   public int getPlayerId() {
/* 47 */     return this.playerId;
/*    */   }
/*    */   
/*    */   public int getPlacement() {
/* 51 */     return this.placement;
/*    */   }
/*    */   
/*    */   public boolean isEquipped() {
/* 55 */     return this.equipped;
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 59 */     return 216;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 63 */     return new EquipmentChangedMessage(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 67 */     return MessageUtils.getSizeStr(this.itemId) + MessageUtils.getSizeInt() * 3 + MessageUtils.getSizeBoolean();
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 71 */     MessageUtils.writeStr(buffer, this.itemId);
/* 72 */     MessageUtils.writeInt(buffer, this.tier);
/* 73 */     MessageUtils.writeInt(buffer, this.playerId);
/* 74 */     MessageUtils.writeInt(buffer, this.placement);
/* 75 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.equipped));
/* 76 */     return buffer;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 80 */     StringBuffer sb = new StringBuffer();
/* 81 */     sb.append("[itemId:").append(this.itemId).append(", tier:").append(this.tier).append(", playerId:").append(this.playerId).append(", placement:").append(this.placement).append(", equipped:").append(this.equipped).append("]");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 87 */     return sb.toString();
/*    */   }
/*    */   
/*    */   public int getTier() {
/* 91 */     return this.tier;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\EquipmentChangedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */