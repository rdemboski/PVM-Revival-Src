/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PetTrialStartMessage
/*    */   implements Message
/*    */ {
/*    */   private String petId;
/*    */   private long timeLeft;
/*    */   private boolean setActive;
/*    */   
/*    */   public PetTrialStartMessage() {}
/*    */   
/*    */   public PetTrialStartMessage(String petId, long timeLeft, boolean setActive) {
/* 20 */     this.petId = petId;
/* 21 */     this.timeLeft = timeLeft;
/* 22 */     this.setActive = setActive;
/*    */   }
/*    */   
/*    */   public PetTrialStartMessage(ByteBuffer buffer) {
/* 26 */     this.petId = MessageUtils.readStr(buffer);
/* 27 */     this.timeLeft = MessageUtils.readLong(buffer);
/* 28 */     this.setActive = MessageUtils.readBoolean(buffer).booleanValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 33 */     return 238;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 38 */     return new PetTrialStartMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 43 */     return MessageUtils.getSizeStr(this.petId) + MessageUtils.getSizeLong() + MessageUtils.getSizeBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 48 */     MessageUtils.writeStr(buffer, this.petId);
/* 49 */     MessageUtils.writeLong(buffer, this.timeLeft);
/* 50 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.setActive));
/* 51 */     return buffer;
/*    */   }
/*    */   
/*    */   public String getPetId() {
/* 55 */     return this.petId;
/*    */   }
/*    */   
/*    */   public long getTimeLeft() {
/* 59 */     return this.timeLeft;
/*    */   }
/*    */   
/*    */   public boolean isSetActive() {
/* 63 */     return this.setActive;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\PetTrialStartMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */