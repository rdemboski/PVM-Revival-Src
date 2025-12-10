/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DailyQuestNumberUpdateMessage
/*    */   implements Message
/*    */ {
/*    */   private int currentNumber;
/*    */   
/*    */   public DailyQuestNumberUpdateMessage() {}
/*    */   
/*    */   public DailyQuestNumberUpdateMessage(int currentNumber) {
/* 18 */     this.currentNumber = currentNumber;
/*    */   }
/*    */   
/*    */   public DailyQuestNumberUpdateMessage(ByteBuffer buffer) {
/* 22 */     this.currentNumber = MessageUtils.readInt(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 27 */     return 241;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 32 */     return new DailyQuestNumberUpdateMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 37 */     return MessageUtils.getSizeInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 42 */     MessageUtils.writeInt(buffer, this.currentNumber);
/* 43 */     return buffer;
/*    */   }
/*    */   
/*    */   public int getCurrentNumber() {
/* 47 */     return this.currentNumber;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\DailyQuestNumberUpdateMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */