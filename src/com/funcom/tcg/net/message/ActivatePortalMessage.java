/*     */ package com.funcom.tcg.net.message;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.MessageUtils;
/*     */ import com.funcom.tcg.portals.InteractibleType;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ActivatePortalMessage
/*     */   implements Message
/*     */ {
/*     */   private int playerId;
/*     */   private WorldCoordinate destinationCoords;
/*     */   private int portalId;
/*     */   private String portalName;
/*     */   private InteractibleType interactibleType;
/*     */   private double radius;
/*     */   
/*     */   public ActivatePortalMessage() {}
/*     */   
/*     */   public ActivatePortalMessage(int playerId, WorldCoordinate destinationCoords, int portalId, String portalName, InteractibleType interactibleType, double radius) {
/*  31 */     this.playerId = playerId;
/*  32 */     this.destinationCoords = destinationCoords;
/*  33 */     this.portalId = portalId;
/*  34 */     this.portalName = portalName;
/*  35 */     this.interactibleType = interactibleType;
/*  36 */     this.radius = radius;
/*     */   }
/*     */   
/*     */   public ActivatePortalMessage(ByteBuffer buffer) {
/*  40 */     this.playerId = MessageUtils.readInt(buffer);
/*  41 */     this.destinationCoords = MessageUtils.readWorldCoordinatePartial(buffer);
/*  42 */     this.portalId = MessageUtils.readInt(buffer);
/*  43 */     this.portalName = MessageUtils.readStr(buffer);
/*  44 */     this.interactibleType = InteractibleType.valueById(buffer.get());
/*  45 */     this.radius = MessageUtils.readDouble(buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getMessageType() {
/*  50 */     return 37;
/*     */   }
/*     */ 
/*     */   
/*     */   public Message toMessage(ByteBuffer buffer) {
/*  55 */     return new ActivatePortalMessage(buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  60 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeWorldCoordinate() + MessageUtils.getSizeInt() + MessageUtils.getSizeStr(this.portalName) + MessageUtils.getSizeInt() + MessageUtils.getSizeDouble();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer serialize(ByteBuffer buffer) {
/*  70 */     MessageUtils.writeInt(buffer, this.playerId);
/*  71 */     MessageUtils.writeWorldCoordinatePartial(buffer, this.destinationCoords);
/*  72 */     MessageUtils.writeInt(buffer, this.portalId);
/*  73 */     MessageUtils.writeStr(buffer, this.portalName);
/*  74 */     buffer.put(this.interactibleType.id);
/*  75 */     MessageUtils.writeDouble(buffer, this.radius);
/*  76 */     return buffer;
/*     */   }
/*     */   
/*     */   public int getPlayerId() {
/*  80 */     return this.playerId;
/*     */   }
/*     */   
/*     */   public WorldCoordinate getDestinationCoords() {
/*  84 */     return this.destinationCoords;
/*     */   }
/*     */   
/*     */   public int getPortalId() {
/*  88 */     return this.portalId;
/*     */   }
/*     */   
/*     */   public String getPortalName() {
/*  92 */     return this.portalName;
/*     */   }
/*     */   
/*     */   public InteractibleType getPortalType() {
/*  96 */     return this.interactibleType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 101 */     return "ActivatePortalMessage{playerId=" + this.playerId + ", destinationCoords=" + this.destinationCoords + ", portalId=" + this.portalId + ", portalName='" + this.portalName + '\'' + ", interactibleType=" + this.interactibleType + ", radius=" + this.radius + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getRadius() {
/* 112 */     return this.radius;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\ActivatePortalMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */