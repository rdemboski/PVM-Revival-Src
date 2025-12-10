/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import com.funcom.tcg.net.SanctionType;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ public class SendSanctionResponseMessage
/*    */   implements Message
/*    */ {
/*    */   private SanctionType sanction;
/*    */   private long sanctionUntil;
/*    */   private String sanctionMessage;
/*    */   
/*    */   public SendSanctionResponseMessage() {}
/*    */   
/*    */   public SendSanctionResponseMessage(SanctionType sanction, long sanctionUntil, String sanctionMessage) {
/* 19 */     this.sanction = sanction;
/* 20 */     this.sanctionUntil = sanctionUntil;
/* 21 */     this.sanctionMessage = sanctionMessage;
/*    */   }
/*    */   
/*    */   public SendSanctionResponseMessage(ByteBuffer buffer) {
/* 25 */     this.sanction = SanctionType.valueOfById(buffer.get());
/* 26 */     this.sanctionUntil = MessageUtils.readLong(buffer);
/* 27 */     this.sanctionMessage = MessageUtils.readStr(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 32 */     return 81;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 37 */     return new SendSanctionResponseMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 42 */     return 1 + MessageUtils.getSizeLong() + MessageUtils.getSizeStr(this.sanctionMessage);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 49 */     buffer.put(this.sanction.getId());
/* 50 */     MessageUtils.writeLong(buffer, this.sanctionUntil);
/* 51 */     MessageUtils.writeStr(buffer, this.sanctionMessage);
/* 52 */     return buffer;
/*    */   }
/*    */   
/*    */   public SanctionType getSanction() {
/* 56 */     return this.sanction;
/*    */   }
/*    */   
/*    */   public long getSanctionUntil() {
/* 60 */     return this.sanctionUntil;
/*    */   }
/*    */   
/*    */   public String getSanctionMessage() {
/* 64 */     return this.sanctionMessage;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\SendSanctionResponseMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */