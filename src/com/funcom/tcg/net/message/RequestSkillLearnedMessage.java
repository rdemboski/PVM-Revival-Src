/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import com.funcom.tcg.rpg.ItemHolderType;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RequestSkillLearnedMessage
/*    */   implements Message
/*    */ {
/*    */   private String petId;
/*    */   private ItemHolderType containerType;
/*    */   private int containerId;
/*    */   private int slotId;
/*    */   
/*    */   public RequestSkillLearnedMessage() {}
/*    */   
/*    */   public RequestSkillLearnedMessage(String petId, String learnedSkillId, ItemHolderType containerType, int containerId, int slotId) {
/* 23 */     this.petId = petId;
/* 24 */     this.containerType = containerType;
/* 25 */     this.containerId = containerId;
/* 26 */     this.slotId = slotId;
/*    */   }
/*    */   
/*    */   public RequestSkillLearnedMessage(ByteBuffer buffer) {
/* 30 */     this.petId = MessageUtils.readStr(buffer);
/* 31 */     int containerTypeId = MessageUtils.readInt(buffer);
/* 32 */     this.containerType = ItemHolderType.valueById(containerTypeId);
/* 33 */     this.containerId = MessageUtils.readInt(buffer);
/* 34 */     this.slotId = MessageUtils.readInt(buffer);
/*    */   }
/*    */   
/*    */   public String getPetId() {
/* 38 */     return this.petId;
/*    */   }
/*    */   
/*    */   public ItemHolderType getContainerType() {
/* 42 */     return this.containerType;
/*    */   }
/*    */   
/*    */   public int getContainerId() {
/* 46 */     return this.containerId;
/*    */   }
/*    */   
/*    */   public int getSlotId() {
/* 50 */     return this.slotId;
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 54 */     return 215;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 58 */     return new RequestSkillLearnedMessage(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 62 */     return MessageUtils.getSizeStr(this.petId) + MessageUtils.getSizeInt() * 3;
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 66 */     MessageUtils.writeStr(buffer, this.petId);
/* 67 */     MessageUtils.writeInt(buffer, this.containerType.getId());
/* 68 */     MessageUtils.writeInt(buffer, this.containerId);
/* 69 */     MessageUtils.writeInt(buffer, this.slotId);
/* 70 */     return buffer;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 74 */     StringBuffer sb = new StringBuffer();
/* 75 */     sb.append("[").append("petId=").append(this.petId).append(", containerType:").append(this.containerType).append(", containerId:").append(this.containerId).append(", slotId:").append(this.slotId).append("]");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 81 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\RequestSkillLearnedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */