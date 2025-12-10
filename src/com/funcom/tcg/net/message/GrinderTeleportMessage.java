/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ public class GrinderTeleportMessage
/*    */   implements Message
/*    */ {
/*    */   private String mapId;
/*    */   private int x;
/*    */   private int y;
/*    */   private double offsetX;
/*    */   private double offsetY;
/*    */   
/*    */   public GrinderTeleportMessage() {}
/*    */   
/*    */   public GrinderTeleportMessage(ByteBuffer buffer) {
/* 20 */     this.mapId = MessageUtils.readStr(buffer);
/* 21 */     this.x = MessageUtils.readInt(buffer);
/* 22 */     this.y = MessageUtils.readInt(buffer);
/* 23 */     this.offsetX = MessageUtils.readDouble(buffer);
/* 24 */     this.offsetY = MessageUtils.readDouble(buffer);
/*    */   }
/*    */   
/*    */   public GrinderTeleportMessage(String mapId, int x, int y, double offsetX, double offsetY) {
/* 28 */     this.mapId = mapId;
/* 29 */     this.x = x;
/* 30 */     this.y = y;
/* 31 */     this.offsetX = offsetX;
/* 32 */     this.offsetY = offsetY;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 37 */     return 97;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 42 */     return new GrinderTeleportMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 47 */     return MessageUtils.getSizeStr(this.mapId) + MessageUtils.getSizeInt() + MessageUtils.getSizeInt() + MessageUtils.getSizeDouble() + MessageUtils.getSizeDouble();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 52 */     MessageUtils.writeStr(buffer, this.mapId);
/* 53 */     MessageUtils.writeInt(buffer, this.x);
/* 54 */     MessageUtils.writeInt(buffer, this.y);
/* 55 */     MessageUtils.writeDouble(buffer, this.offsetX);
/* 56 */     MessageUtils.writeDouble(buffer, this.offsetY);
/* 57 */     return buffer;
/*    */   }
/*    */   
/*    */   public String getMapId() {
/* 61 */     return this.mapId;
/*    */   }
/*    */   
/*    */   public int getX() {
/* 65 */     return this.x;
/*    */   }
/*    */   
/*    */   public int getY() {
/* 69 */     return this.y;
/*    */   }
/*    */   
/*    */   public double getOffsetX() {
/* 73 */     return this.offsetX;
/*    */   }
/*    */   
/*    */   public double getOffsetY() {
/* 77 */     return this.offsetY;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\GrinderTeleportMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */