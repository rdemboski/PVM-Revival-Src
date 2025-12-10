/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
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
/*    */ public class ActivateWaypointDestianationportalMessage
/*    */   implements Message
/*    */ {
/*    */   private int propId;
/*    */   private String portalId;
/*    */   private String name;
/*    */   private WorldCoordinate worldCoordinate;
/*    */   private boolean activated;
/*    */   private boolean unlocked;
/*    */   
/*    */   public ActivateWaypointDestianationportalMessage(int propId, String portalId, String name, WorldCoordinate worldCoordinate, boolean activated, boolean unlocked) {
/* 26 */     this.propId = propId;
/* 27 */     this.portalId = portalId;
/* 28 */     this.name = name;
/* 29 */     this.worldCoordinate = worldCoordinate;
/* 30 */     this.activated = activated;
/* 31 */     this.unlocked = unlocked;
/*    */   }
/*    */ 
/*    */   
/*    */   public ActivateWaypointDestianationportalMessage() {}
/*    */   
/*    */   public ActivateWaypointDestianationportalMessage(ByteBuffer buffer) {
/* 38 */     this.propId = MessageUtils.readInt(buffer);
/* 39 */     this.portalId = MessageUtils.readStr(buffer);
/* 40 */     this.name = MessageUtils.readStr(buffer);
/* 41 */     this.worldCoordinate = MessageUtils.readWorldCoordinatePartial(buffer);
/* 42 */     this.activated = MessageUtils.readBoolean(buffer).booleanValue();
/* 43 */     this.unlocked = MessageUtils.readBoolean(buffer).booleanValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 48 */     return 46;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 53 */     return new ActivateWaypointDestianationportalMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 58 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeStr(this.portalId) + MessageUtils.getSizeStr(this.name) + MessageUtils.getSizeWorldCoordinate() + MessageUtils.getSizeBoolean() + MessageUtils.getSizeBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 63 */     MessageUtils.writeInt(buffer, this.propId);
/* 64 */     MessageUtils.writeStr(buffer, this.portalId);
/* 65 */     MessageUtils.writeStr(buffer, this.name);
/* 66 */     MessageUtils.writeWorldCoordinatePartial(buffer, this.worldCoordinate);
/* 67 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.activated));
/* 68 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.unlocked));
/* 69 */     return buffer;
/*    */   }
/*    */   
/*    */   public int getPropId() {
/* 73 */     return this.propId;
/*    */   }
/*    */   
/*    */   public String getPortalId() {
/* 77 */     return this.portalId;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 81 */     return this.name;
/*    */   }
/*    */   
/*    */   public WorldCoordinate getWorldCoordinate() {
/* 85 */     return this.worldCoordinate;
/*    */   }
/*    */   
/*    */   public boolean isActivated() {
/* 89 */     return this.activated;
/*    */   }
/*    */   
/*    */   public boolean isUnlocked() {
/* 93 */     return this.unlocked;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 98 */     return "ActivateWaypointDestianationportalMessage{propId=" + this.propId + ", portalId='" + this.portalId + '\'' + ", name='" + this.name + '\'' + ", worldCoordinate=" + this.worldCoordinate + ", activated=" + this.activated + ", unlocked=" + this.unlocked + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\ActivateWaypointDestianationportalMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */