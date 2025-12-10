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
/*    */ public class VerifyTownPortalActivationMessage
/*    */   implements Message
/*    */ {
/*    */   private int playerId;
/*    */   private WorldCoordinate worldCoordinate;
/*    */   
/*    */   public VerifyTownPortalActivationMessage(int playerId, WorldCoordinate worldCoordinate) {
/* 21 */     this.playerId = playerId;
/* 22 */     this.worldCoordinate = worldCoordinate;
/*    */   }
/*    */ 
/*    */   
/*    */   public VerifyTownPortalActivationMessage() {}
/*    */   
/*    */   public VerifyTownPortalActivationMessage(ByteBuffer buffer) {
/* 29 */     this.playerId = MessageUtils.readInt(buffer);
/* 30 */     this.worldCoordinate = MessageUtils.readWorldCoordinatePartial(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 35 */     return 38;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 40 */     return new VerifyTownPortalActivationMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 45 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeWorldCoordinate();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 50 */     MessageUtils.writeInt(buffer, this.playerId);
/* 51 */     MessageUtils.writeWorldCoordinatePartial(buffer, this.worldCoordinate);
/* 52 */     return buffer;
/*    */   }
/*    */   
/*    */   public int getPlayerId() {
/* 56 */     return this.playerId;
/*    */   }
/*    */   
/*    */   public WorldCoordinate getWorldCoordinate() {
/* 60 */     return this.worldCoordinate;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 65 */     return "VerifyTownPortalActivationMessage{playerId=" + this.playerId + ", worldCoordinate=" + this.worldCoordinate + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\VerifyTownPortalActivationMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */