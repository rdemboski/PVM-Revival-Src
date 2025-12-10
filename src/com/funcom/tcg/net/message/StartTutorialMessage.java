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
/*    */ public class StartTutorialMessage
/*    */   implements Message
/*    */ {
/*    */   private boolean petTutorial = true;
/*    */   
/*    */   public StartTutorialMessage() {}
/*    */   
/*    */   public StartTutorialMessage(boolean pet) {
/* 19 */     this.petTutorial = pet;
/*    */   }
/*    */   
/*    */   public StartTutorialMessage(ByteBuffer buffer) {
/* 23 */     this.petTutorial = MessageUtils.readBoolean(buffer).booleanValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 28 */     return 79;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 33 */     return new StartTutorialMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 38 */     return MessageUtils.getSizeBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 43 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.petTutorial));
/* 44 */     return buffer;
/*    */   }
/*    */   
/*    */   public boolean isPetTutorial() {
/* 48 */     return this.petTutorial;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\StartTutorialMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */