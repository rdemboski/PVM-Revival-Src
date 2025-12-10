/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TrainPetMessage
/*    */   implements Message
/*    */ {
/*    */   private int numTokens;
/*    */   private String petId;
/*    */   
/*    */   public TrainPetMessage() {}
/*    */   
/*    */   public TrainPetMessage(int numTokens, String petId) {
/* 19 */     this.numTokens = numTokens;
/* 20 */     this.petId = petId;
/*    */   }
/*    */   
/*    */   public TrainPetMessage(ByteBuffer buffer) {
/* 24 */     this.numTokens = MessageUtils.readInt(buffer);
/* 25 */     this.petId = MessageUtils.readStr(buffer);
/*    */   }
/*    */   
/*    */   public int getNumTokens() {
/* 29 */     return this.numTokens;
/*    */   }
/*    */   
/*    */   public String getPetId() {
/* 33 */     return this.petId;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 38 */     return 242;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 43 */     return new TrainPetMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 48 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeStr(this.petId);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 53 */     MessageUtils.writeInt(buffer, this.numTokens);
/* 54 */     MessageUtils.writeStr(buffer, this.petId);
/* 55 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\TrainPetMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */