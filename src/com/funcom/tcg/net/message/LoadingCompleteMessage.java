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
/*    */ public class LoadingCompleteMessage
/*    */   implements Message
/*    */ {
/*    */   private int playerId;
/*    */   
/*    */   public LoadingCompleteMessage(int playerId) {
/* 19 */     this.playerId = playerId;
/*    */   }
/*    */   
/*    */   public LoadingCompleteMessage(ByteBuffer buffer) {
/* 23 */     this.playerId = MessageUtils.readInt(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public LoadingCompleteMessage() {}
/*    */   
/*    */   public int getPlayerId() {
/* 30 */     return this.playerId;
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 34 */     return 31;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 38 */     return new LoadingCompleteMessage(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 42 */     return MessageUtils.getSizeInt();
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 46 */     MessageUtils.writeInt(buffer, this.playerId);
/* 47 */     return buffer;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 52 */     return "LoadingCompleteMessage{playerId=" + this.playerId + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\LoadingCompleteMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */