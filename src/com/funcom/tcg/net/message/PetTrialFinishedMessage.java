/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PetTrialFinishedMessage
/*    */   implements Message
/*    */ {
/*    */   private String petId;
/*    */   private String activePetId;
/*    */   
/*    */   public PetTrialFinishedMessage() {}
/*    */   
/*    */   public PetTrialFinishedMessage(String petId, String activePetId) {
/* 19 */     this.petId = petId;
/* 20 */     this.activePetId = activePetId;
/*    */   }
/*    */   
/*    */   public PetTrialFinishedMessage(ByteBuffer buffer) {
/* 24 */     this.petId = MessageUtils.readStr(buffer);
/* 25 */     this.activePetId = MessageUtils.readStr(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 30 */     return 239;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 35 */     return new PetTrialFinishedMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 40 */     return MessageUtils.getSizeStr(this.petId) + MessageUtils.getSizeStr(this.activePetId);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 45 */     MessageUtils.writeStr(buffer, this.petId);
/* 46 */     MessageUtils.writeStr(buffer, this.activePetId);
/* 47 */     return buffer;
/*    */   }
/*    */   
/*    */   public String getPetId() {
/* 51 */     return this.petId;
/*    */   }
/*    */   
/*    */   public String getActivePetId() {
/* 55 */     return this.activePetId;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\PetTrialFinishedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */