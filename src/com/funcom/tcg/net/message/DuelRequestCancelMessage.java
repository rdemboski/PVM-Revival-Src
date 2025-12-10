/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DuelRequestCancelMessage
/*    */   implements Message
/*    */ {
/*    */   private int challengedId;
/*    */   
/*    */   public DuelRequestCancelMessage() {}
/*    */   
/*    */   public DuelRequestCancelMessage(int challengedId) {
/* 18 */     this.challengedId = challengedId;
/*    */   }
/*    */   
/*    */   public DuelRequestCancelMessage(ByteBuffer buffer) {
/* 22 */     this.challengedId = MessageUtils.readInt(buffer);
/*    */   }
/*    */   
/*    */   public int getChallengedId() {
/* 26 */     return this.challengedId;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 31 */     return 246;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 36 */     return new DuelRequestCancelMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 41 */     return MessageUtils.getSizeInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 46 */     MessageUtils.writeInt(buffer, this.challengedId);
/* 47 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\DuelRequestCancelMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */