/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.commons.Vector2d;
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
/*    */ public class DebugSquareAttackShapeMessage
/*    */   implements Message
/*    */ {
/*    */   private Vector2d[] localCorners;
/*    */   private WorldCoordinate position;
/*    */   
/*    */   public DebugSquareAttackShapeMessage() {}
/*    */   
/*    */   public DebugSquareAttackShapeMessage(WorldCoordinate position, Vector2d[] localCorners) {
/* 23 */     this.position = position;
/* 24 */     this.localCorners = localCorners;
/*    */   }
/*    */   
/*    */   private DebugSquareAttackShapeMessage(ByteBuffer buffer) {
/* 28 */     this.position = MessageUtils.readWorldCoordinatePartial(buffer);
/* 29 */     this.localCorners = readCorners(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 34 */     return 243;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 39 */     return new DebugSquareAttackShapeMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 44 */     return MessageUtils.getSizeWorldCoordinate() + getSizeCorners();
/*    */   }
/*    */   
/*    */   private int getSizeCorners() {
/* 48 */     return 4 + 16 * this.localCorners.length;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 53 */     MessageUtils.writeWorldCoordinatePartial(buffer, this.position);
/* 54 */     writeCorners(buffer);
/* 55 */     return buffer;
/*    */   }
/*    */   
/*    */   private void writeCorners(ByteBuffer buffer) {
/* 59 */     buffer.putInt(this.localCorners.length);
/* 60 */     int length = this.localCorners.length;
/* 61 */     for (int i = 0; i < length; i++) {
/* 62 */       Vector2d localCorner = this.localCorners[i];
/* 63 */       buffer.putDouble(localCorner.getX());
/* 64 */       buffer.putDouble(localCorner.getY());
/*    */     } 
/*    */   }
/*    */   
/*    */   private Vector2d[] readCorners(ByteBuffer buffer) {
/* 69 */     int count = buffer.getInt();
/* 70 */     Vector2d[] ret = new Vector2d[count];
/* 71 */     for (int i = 0; i < count; i++) {
/* 72 */       ret[i] = new Vector2d(buffer.getDouble(), buffer.getDouble());
/*    */     }
/* 74 */     return ret;
/*    */   }
/*    */   
/*    */   public WorldCoordinate getPosition() {
/* 78 */     return this.position;
/*    */   }
/*    */   
/*    */   public Vector2d[] getLocalCorners() {
/* 82 */     return this.localCorners;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\DebugSquareAttackShapeMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */