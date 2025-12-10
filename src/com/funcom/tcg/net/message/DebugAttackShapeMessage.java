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
/*    */ public class DebugAttackShapeMessage
/*    */   implements Message
/*    */ {
/*    */   private double angleStartDegrees;
/*    */   private double angleEndDegrees;
/*    */   private double distance;
/*    */   private double offsetX;
/*    */   private double offsetY;
/*    */   private WorldCoordinate position;
/*    */   
/*    */   public DebugAttackShapeMessage() {}
/*    */   
/*    */   public DebugAttackShapeMessage(WorldCoordinate position, double angleStartDegrees, double angleEndDegrees, double distance, double offsetX, double offsetY) {
/* 25 */     this.position = position;
/* 26 */     this.angleStartDegrees = angleStartDegrees;
/* 27 */     this.angleEndDegrees = angleEndDegrees;
/* 28 */     this.distance = distance;
/* 29 */     this.offsetX = offsetX;
/* 30 */     this.offsetY = offsetY;
/*    */   }
/*    */   
/*    */   private DebugAttackShapeMessage(ByteBuffer buffer) {
/* 34 */     this.position = MessageUtils.readWorldCoordinatePartial(buffer);
/* 35 */     this.angleStartDegrees = MessageUtils.readDouble(buffer);
/* 36 */     this.angleEndDegrees = MessageUtils.readDouble(buffer);
/* 37 */     this.distance = MessageUtils.readDouble(buffer);
/* 38 */     this.offsetX = MessageUtils.readDouble(buffer);
/* 39 */     this.offsetY = MessageUtils.readDouble(buffer);
/*    */   }
/*    */   
/*    */   public double getAngleStart() {
/* 43 */     return this.angleStartDegrees;
/*    */   }
/*    */   
/*    */   public double getAngleEnd() {
/* 47 */     return this.angleEndDegrees;
/*    */   }
/*    */   
/*    */   public double getDistance() {
/* 51 */     return this.distance;
/*    */   }
/*    */   
/*    */   public double getOffsetX() {
/* 55 */     return this.offsetX;
/*    */   }
/*    */   
/*    */   public double getOffsetY() {
/* 59 */     return this.offsetY;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 64 */     return 98;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 69 */     return new DebugAttackShapeMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 74 */     return MessageUtils.getSizeWorldCoordinate() + MessageUtils.getSizeDouble() * 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 79 */     MessageUtils.writeWorldCoordinatePartial(buffer, this.position);
/* 80 */     MessageUtils.writeDouble(buffer, this.angleStartDegrees);
/* 81 */     MessageUtils.writeDouble(buffer, this.angleEndDegrees);
/* 82 */     MessageUtils.writeDouble(buffer, this.distance);
/* 83 */     MessageUtils.writeDouble(buffer, this.offsetX);
/* 84 */     MessageUtils.writeDouble(buffer, this.offsetY);
/* 85 */     return buffer;
/*    */   }
/*    */   
/*    */   public WorldCoordinate getPosition() {
/* 89 */     return this.position;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\DebugAttackShapeMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */