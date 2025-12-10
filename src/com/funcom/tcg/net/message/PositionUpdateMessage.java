/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ public class PositionUpdateMessage
/*    */   implements Message
/*    */ {
/*    */   private static final short TYPE_ID = 9;
/* 13 */   private static final int MSG_SIZE = MessageUtils.getSizeWorldCoordinate() + MessageUtils.getSizeInt() + MessageUtils.getSizeFloat();
/*    */   
/*    */   private WorldCoordinate worldCoordinate;
/*    */   private int identity;
/*    */   private float angle;
/*    */   
/*    */   public PositionUpdateMessage(int identity, WorldCoordinate worldCoordinate, float angle) {
/* 20 */     this.worldCoordinate = worldCoordinate;
/* 21 */     this.identity = identity;
/* 22 */     this.angle = angle;
/*    */   }
/*    */ 
/*    */   
/*    */   public PositionUpdateMessage() {}
/*    */   
/*    */   public PositionUpdateMessage(ByteBuffer buffer) {
/* 29 */     this.worldCoordinate = MessageUtils.readWorldCoordinatePartial(buffer);
/* 30 */     this.identity = MessageUtils.readInt(buffer);
/* 31 */     this.angle = buffer.getFloat();
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 35 */     return MSG_SIZE;
/*    */   }
/*    */   
/*    */   public WorldCoordinate getWorldCoordinate() {
/* 39 */     return this.worldCoordinate;
/*    */   }
/*    */   
/*    */   public int getIdentity() {
/* 43 */     return this.identity;
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 47 */     return 9;
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 51 */     MessageUtils.writeWorldCoordinatePartial(buffer, this.worldCoordinate);
/* 52 */     MessageUtils.writeInt(buffer, this.identity);
/* 53 */     buffer.putFloat(this.angle);
/* 54 */     return buffer;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 58 */     return new PositionUpdateMessage(buffer);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 62 */     return getClass().getName() + "[id=" + this.identity + ",pos=" + this.worldCoordinate + "]";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public float getAngle() {
/* 68 */     return this.angle;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\PositionUpdateMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */