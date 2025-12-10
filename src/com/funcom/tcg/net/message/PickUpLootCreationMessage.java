/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PickUpLootCreationMessage
/*    */   implements Message
/*    */ {
/*    */   private static final short TYPE_ID = 33;
/*    */   private int pickUpLootId;
/*    */   private WorldCoordinate worldCoordinate;
/*    */   private String pickupDescriptionId;
/*    */   private double radius;
/*    */   private boolean lootIsMagnetic;
/*    */   
/*    */   public PickUpLootCreationMessage() {}
/*    */   
/*    */   public PickUpLootCreationMessage(int pickUpLootId, WorldCoordinate worldCoordinate, String pickupDescriptionId, double radius, boolean isLootMagnetic) {
/* 23 */     this.pickUpLootId = pickUpLootId;
/* 24 */     this.worldCoordinate = worldCoordinate;
/* 25 */     this.pickupDescriptionId = pickupDescriptionId;
/* 26 */     this.radius = radius;
/* 27 */     this.lootIsMagnetic = isLootMagnetic;
/*    */   }
/*    */   
/*    */   public PickUpLootCreationMessage(ByteBuffer buffer) {
/* 31 */     this.pickUpLootId = MessageUtils.readInt(buffer);
/* 32 */     this.worldCoordinate = MessageUtils.readWorldCoordinatePartial(buffer);
/* 33 */     this.pickupDescriptionId = MessageUtils.readStr(buffer);
/* 34 */     this.radius = MessageUtils.readDouble(buffer);
/* 35 */     this.lootIsMagnetic = MessageUtils.readBoolean(buffer).booleanValue();
/*    */   }
/*    */   
/*    */   public int getPickUpLootId() {
/* 39 */     return this.pickUpLootId;
/*    */   }
/*    */   
/*    */   public WorldCoordinate getWorldCoordinate() {
/* 43 */     return this.worldCoordinate;
/*    */   }
/*    */   
/*    */   public String getPickupDescriptionId() {
/* 47 */     return this.pickupDescriptionId;
/*    */   }
/*    */   
/*    */   public boolean isLootMagnetic() {
/* 51 */     return this.lootIsMagnetic;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 56 */     return 33;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 61 */     return new PickUpLootCreationMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 66 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeWorldCoordinate() + MessageUtils.getSizeStr(this.pickupDescriptionId) + MessageUtils.getSizeDouble() + MessageUtils.getSizeBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 71 */     MessageUtils.writeInt(buffer, this.pickUpLootId);
/* 72 */     MessageUtils.writeWorldCoordinatePartial(buffer, this.worldCoordinate);
/* 73 */     MessageUtils.writeStr(buffer, this.pickupDescriptionId);
/* 74 */     MessageUtils.writeDouble(buffer, this.radius);
/* 75 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.lootIsMagnetic));
/* 76 */     return buffer;
/*    */   }
/*    */   
/*    */   public double getRadius() {
/* 80 */     return this.radius;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\PickUpLootCreationMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */