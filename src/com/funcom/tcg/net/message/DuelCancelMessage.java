/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DuelCancelMessage
/*    */   implements Message
/*    */ {
/*    */   private int cancellerId;
/*    */   
/*    */   public DuelCancelMessage() {}
/*    */   
/*    */   public DuelCancelMessage(int cancellerId) {
/* 18 */     this.cancellerId = cancellerId;
/*    */   }
/*    */   
/*    */   public DuelCancelMessage(ByteBuffer buffer) {
/* 22 */     this.cancellerId = MessageUtils.readInt(buffer);
/*    */   }
/*    */   
/*    */   public int getChallengedId() {
/* 26 */     return this.cancellerId;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 31 */     return 252;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 36 */     return new DuelCancelMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 41 */     return MessageUtils.getSizeInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 46 */     MessageUtils.writeInt(buffer, this.cancellerId);
/* 47 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\DuelCancelMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */