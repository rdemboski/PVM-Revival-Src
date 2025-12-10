/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DuelStartMessage
/*    */   implements Message
/*    */ {
/*    */   private int opponentId;
/*    */   
/*    */   public DuelStartMessage() {}
/*    */   
/*    */   public DuelStartMessage(int opponentId) {
/* 18 */     this.opponentId = opponentId;
/*    */   }
/*    */   
/*    */   public DuelStartMessage(ByteBuffer buffer) {
/* 22 */     this.opponentId = MessageUtils.readInt(buffer);
/*    */   }
/*    */   
/*    */   public int getOpponentId() {
/* 26 */     return this.opponentId;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 31 */     return 250;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 36 */     return new DuelStartMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 41 */     return MessageUtils.getSizeInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 46 */     MessageUtils.writeInt(buffer, this.opponentId);
/* 47 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\DuelStartMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */