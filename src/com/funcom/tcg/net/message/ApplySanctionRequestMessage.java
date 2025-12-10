/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import com.funcom.tcg.net.SanctionType;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ApplySanctionRequestMessage
/*    */   implements Message
/*    */ {
/*    */   private SanctionType sanction;
/*    */   private long sanctionUntil;
/*    */   private String sanctionMessage;
/*    */   
/*    */   public ApplySanctionRequestMessage() {}
/*    */   
/*    */   public ApplySanctionRequestMessage(SanctionType sanction, long sanctionUntil, String sanctionMessage) {
/* 20 */     this.sanction = sanction;
/* 21 */     this.sanctionUntil = sanctionUntil;
/* 22 */     this.sanctionMessage = sanctionMessage;
/*    */   }
/*    */   
/*    */   public ApplySanctionRequestMessage(ByteBuffer buffer) {
/* 26 */     this.sanction = SanctionType.valueOfById(buffer.get());
/* 27 */     this.sanctionUntil = MessageUtils.readLong(buffer);
/* 28 */     this.sanctionMessage = MessageUtils.readStr(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 33 */     return 80;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 38 */     return new ApplySanctionRequestMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 43 */     return 1 + MessageUtils.getSizeLong() + MessageUtils.getSizeStr(this.sanctionMessage);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 50 */     buffer.put(this.sanction.getId());
/* 51 */     MessageUtils.writeLong(buffer, this.sanctionUntil);
/* 52 */     MessageUtils.writeStr(buffer, this.sanctionMessage);
/* 53 */     return buffer;
/*    */   }
/*    */   
/*    */   public SanctionType getSanction() {
/* 57 */     return this.sanction;
/*    */   }
/*    */   
/*    */   public long getSanctionUntil() {
/* 61 */     return this.sanctionUntil;
/*    */   }
/*    */   
/*    */   public String getSanctionMessage() {
/* 65 */     return this.sanctionMessage;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\ApplySanctionRequestMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */