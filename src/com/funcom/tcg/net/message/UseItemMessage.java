/*     */ package com.funcom.tcg.net.message;
/*     */ 
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.MessageUtils;
/*     */ import com.funcom.tcg.rpg.ItemHolderType;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UseItemMessage
/*     */   implements Message
/*     */ {
/*  15 */   private static final int MSG_SIZE = MessageUtils.getSizeInt() * 4 + MessageUtils.getSizeDouble() * 2;
/*     */   
/*     */   private int creatureId;
/*     */   
/*     */   private ItemHolderType containerType;
/*     */   private int containerId;
/*     */   private int slotId;
/*     */   private double rotation;
/*     */   private double distance;
/*     */   
/*     */   public UseItemMessage() {}
/*     */   
/*     */   public UseItemMessage(int creatureId, ItemHolderType containerType, int containerId, int slotId, float rotation, double distance) {
/*  28 */     this.creatureId = creatureId;
/*  29 */     this.containerType = containerType;
/*  30 */     this.containerId = containerId;
/*  31 */     this.slotId = slotId;
/*  32 */     this.rotation = rotation;
/*  33 */     this.distance = distance;
/*     */   }
/*     */   
/*     */   public UseItemMessage(ByteBuffer byteBuffer) {
/*  37 */     this.creatureId = MessageUtils.readInt(byteBuffer);
/*  38 */     int containerTypeId = MessageUtils.readInt(byteBuffer);
/*     */     
/*  40 */     this.containerType = ItemHolderType.valueById(containerTypeId);
/*  41 */     this.containerId = MessageUtils.readInt(byteBuffer);
/*  42 */     this.slotId = MessageUtils.readInt(byteBuffer);
/*  43 */     this.rotation = MessageUtils.readDouble(byteBuffer);
/*  44 */     this.distance = MessageUtils.readDouble(byteBuffer);
/*     */   }
/*     */   
/*     */   public int getCreatureId() {
/*  48 */     return this.creatureId;
/*     */   }
/*     */   
/*     */   public ItemHolderType getContainerType() {
/*  52 */     return this.containerType;
/*     */   }
/*     */   
/*     */   public int getContainerId() {
/*  56 */     return this.containerId;
/*     */   }
/*     */   
/*     */   public int getSlotId() {
/*  60 */     return this.slotId;
/*     */   }
/*     */   
/*     */   public double getDistance() {
/*  64 */     return this.distance;
/*     */   }
/*     */   
/*     */   public short getMessageType() {
/*  68 */     return 202;
/*     */   }
/*     */   
/*     */   public Message toMessage(ByteBuffer byteBuffer) {
/*  72 */     return new UseItemMessage(byteBuffer);
/*     */   }
/*     */   
/*     */   public int getSerializedSize() {
/*  76 */     return MSG_SIZE;
/*     */   }
/*     */   
/*     */   public ByteBuffer serialize(ByteBuffer byteBuffer) {
/*  80 */     MessageUtils.writeInt(byteBuffer, this.creatureId);
/*  81 */     MessageUtils.writeInt(byteBuffer, this.containerType.getId());
/*  82 */     MessageUtils.writeInt(byteBuffer, this.containerId);
/*  83 */     MessageUtils.writeInt(byteBuffer, this.slotId);
/*  84 */     MessageUtils.writeDouble(byteBuffer, this.rotation);
/*  85 */     MessageUtils.writeDouble(byteBuffer, this.distance);
/*  86 */     return byteBuffer;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("[creatureId:").append(this.creatureId).append(", containerType:").append(this.containerType).append(", containerId:").append(this.containerId).append(", slotId:").append(this.slotId).append(", rotation:").append(this.rotation).append(", distance:").append(this.distance).append("]");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public double getRotation() {
/* 102 */     return this.rotation;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\UseItemMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */