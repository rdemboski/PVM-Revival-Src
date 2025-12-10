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
/*    */ public class MapChangedMessage
/*    */   implements Message
/*    */ {
/*    */   private String mapId;
/*    */   private WorldCoordinate worldCoordinate;
/*    */   
/*    */   public MapChangedMessage(String mapId, WorldCoordinate worldCoordinate) {
/* 21 */     this.mapId = mapId;
/* 22 */     this.worldCoordinate = worldCoordinate;
/*    */   }
/*    */ 
/*    */   
/*    */   public MapChangedMessage() {}
/*    */   
/*    */   public MapChangedMessage(ByteBuffer buffer) {
/* 29 */     this.mapId = MessageUtils.readStr(buffer);
/* 30 */     this.worldCoordinate = MessageUtils.readWorldCoordinatePartial(buffer);
/* 31 */     this.worldCoordinate.setMapId(this.mapId);
/*    */   }
/*    */   
/*    */   public String getMapId() {
/* 35 */     return this.mapId;
/*    */   }
/*    */   
/*    */   public WorldCoordinate getWorldCoordinate() {
/* 39 */     return this.worldCoordinate;
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 43 */     return 27;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 47 */     return new MapChangedMessage(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 51 */     return MessageUtils.getSizeStr(this.mapId) + MessageUtils.getSizeWorldCoordinate();
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 55 */     MessageUtils.writeStr(buffer, this.mapId);
/* 56 */     MessageUtils.writeWorldCoordinatePartial(buffer, this.worldCoordinate);
/* 57 */     return buffer;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 61 */     StringBuffer sb = new StringBuffer();
/* 62 */     sb.append("[").append("mapId=").append(this.mapId).append(",worldCoordinate=").append(this.worldCoordinate).append("]");
/*    */ 
/*    */ 
/*    */     
/* 66 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\MapChangedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */