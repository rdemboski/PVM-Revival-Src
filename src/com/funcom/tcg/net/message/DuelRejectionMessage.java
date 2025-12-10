/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DuelRejectionMessage
/*    */   implements Message
/*    */ {
/*    */   private int responseType;
/*    */   
/*    */   public DuelRejectionMessage() {}
/*    */   
/*    */   public DuelRejectionMessage(int responseType) {
/* 18 */     this.responseType = responseType;
/*    */   }
/*    */   
/*    */   public DuelRejectionMessage(ByteBuffer buffer) {
/* 22 */     this.responseType = MessageUtils.readInt(buffer);
/*    */   }
/*    */   
/*    */   public int getResponseType() {
/* 26 */     return this.responseType;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 31 */     return 249;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 36 */     return new DuelRejectionMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 41 */     return MessageUtils.getSizeInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 46 */     MessageUtils.writeInt(buffer, this.responseType);
/* 47 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\DuelRejectionMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */