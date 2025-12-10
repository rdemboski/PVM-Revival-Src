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
/*    */ public class UpdateWaypointDestianationportalMessage
/*    */   implements Message
/*    */ {
/*    */   private int propId;
/*    */   private String portalId;
/*    */   private boolean activated;
/*    */   private boolean unlocked;
/*    */   
/*    */   public UpdateWaypointDestianationportalMessage() {}
/*    */   
/*    */   public UpdateWaypointDestianationportalMessage(int propId, String portalId, boolean activated, boolean unlocked) {
/* 24 */     this.propId = propId;
/* 25 */     this.portalId = portalId;
/* 26 */     this.activated = activated;
/* 27 */     this.unlocked = unlocked;
/*    */   }
/*    */   
/*    */   public UpdateWaypointDestianationportalMessage(ByteBuffer buffer) {
/* 31 */     this.propId = MessageUtils.readInt(buffer);
/* 32 */     this.portalId = MessageUtils.readStr(buffer);
/* 33 */     this.activated = MessageUtils.readBoolean(buffer).booleanValue();
/* 34 */     this.unlocked = MessageUtils.readBoolean(buffer).booleanValue();
/*    */   }
/*    */   
/*    */   public int getPropId() {
/* 38 */     return this.propId;
/*    */   }
/*    */   
/*    */   public String getPortalId() {
/* 42 */     return this.portalId;
/*    */   }
/*    */   
/*    */   public boolean isActivated() {
/* 46 */     return this.activated;
/*    */   }
/*    */   
/*    */   public boolean isUnlocked() {
/* 50 */     return this.unlocked;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 55 */     return 48;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 60 */     return new UpdateWaypointDestianationportalMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 65 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeStr(this.portalId) + MessageUtils.getSizeBoolean() + MessageUtils.getSizeBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 70 */     MessageUtils.writeInt(buffer, this.propId);
/* 71 */     MessageUtils.writeStr(buffer, this.portalId);
/* 72 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.activated));
/* 73 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.unlocked));
/* 74 */     return buffer;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 80 */     return "UpdateWaypointDestianationportalMessage{propId=" + this.propId + ", portalId='" + this.portalId + '\'' + ", activated=" + this.activated + ", unlocked=" + this.unlocked + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\UpdateWaypointDestianationportalMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */