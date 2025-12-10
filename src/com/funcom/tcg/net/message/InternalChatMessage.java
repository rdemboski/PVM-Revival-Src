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
/*    */ public class InternalChatMessage
/*    */   implements Message
/*    */ {
/*    */   private int playerId;
/*    */   private String message;
/*    */   
/*    */   public InternalChatMessage() {}
/*    */   
/*    */   public InternalChatMessage(int playerId, String message) {
/* 22 */     this.playerId = playerId;
/* 23 */     this.message = message;
/*    */   }
/*    */   
/*    */   public InternalChatMessage(ByteBuffer buffer) {
/* 27 */     this.playerId = MessageUtils.readInt(buffer);
/* 28 */     this.message = MessageUtils.readStr(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 33 */     return 82;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 38 */     return new InternalChatMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 43 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeStr(this.message);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 49 */     MessageUtils.writeInt(buffer, this.playerId);
/* 50 */     MessageUtils.writeStr(buffer, this.message);
/* 51 */     return buffer;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 55 */     return this.message;
/*    */   }
/*    */   
/*    */   public void setMessage(String message) {
/* 59 */     this.message = message;
/*    */   }
/*    */   
/*    */   public int getPlayerId() {
/* 63 */     return this.playerId;
/*    */   }
/*    */   
/*    */   public void setPlayerId(int playerId) {
/* 67 */     this.playerId = playerId;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\InternalChatMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */