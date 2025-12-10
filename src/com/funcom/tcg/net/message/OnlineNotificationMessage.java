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
/*    */ public class OnlineNotificationMessage
/*    */   implements Message
/*    */ {
/*    */   private int playerId;
/*    */   private Boolean online;
/*    */   
/*    */   public OnlineNotificationMessage() {}
/*    */   
/*    */   public OnlineNotificationMessage(int playerId, Boolean online) {
/* 22 */     this.playerId = playerId;
/* 23 */     this.online = online;
/*    */   }
/*    */   
/*    */   public OnlineNotificationMessage(ByteBuffer buffer) {
/* 27 */     this.playerId = MessageUtils.readInt(buffer);
/* 28 */     this.online = MessageUtils.readBoolean(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 33 */     return 83;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 38 */     return new OnlineNotificationMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 43 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeBoolean();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 49 */     MessageUtils.writeInt(buffer, this.playerId);
/* 50 */     MessageUtils.writeBoolean(buffer, this.online);
/* 51 */     return buffer;
/*    */   }
/*    */   
/*    */   public int getPlayerId() {
/* 55 */     return this.playerId;
/*    */   }
/*    */   
/*    */   public void setPlayerId(int playerId) {
/* 59 */     this.playerId = playerId;
/*    */   }
/*    */   
/*    */   public Boolean isOnline() {
/* 63 */     return this.online;
/*    */   }
/*    */   
/*    */   public void setOnline(Boolean online) {
/* 67 */     this.online = online;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\OnlineNotificationMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */