/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class PauseStatusMessage
/*    */   implements Message
/*    */ {
/*    */   boolean paused;
/*    */   
/*    */   public PauseStatusMessage(boolean paused) {
/* 13 */     this.paused = paused;
/*    */   }
/*    */   
/*    */   public PauseStatusMessage(ByteBuffer buffer) {
/* 17 */     this.paused = MessageUtils.readBoolean(buffer).booleanValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public PauseStatusMessage() {}
/*    */   
/*    */   public boolean isPaused() {
/* 24 */     return this.paused;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 29 */     return 63;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 34 */     return new PauseStatusMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 39 */     return MessageUtils.getSizeBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 44 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.paused));
/* 45 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\PauseStatusMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */