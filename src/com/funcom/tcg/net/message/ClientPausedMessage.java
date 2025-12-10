/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ public class ClientPausedMessage
/*    */   implements Message
/*    */ {
/*    */   private boolean ended;
/*    */   
/*    */   public ClientPausedMessage() {}
/*    */   
/*    */   public ClientPausedMessage(boolean ended) {
/* 16 */     this.ended = ended;
/*    */   }
/*    */   
/*    */   public ClientPausedMessage(ByteBuffer buffer) {
/* 20 */     this.ended = MessageUtils.readBoolean(buffer).booleanValue();
/*    */   }
/*    */   
/*    */   public boolean isEnded() {
/* 24 */     return this.ended;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 29 */     return 64;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 34 */     return new ClientPausedMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 39 */     return MessageUtils.getSizeBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 44 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.ended));
/* 45 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\ClientPausedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */