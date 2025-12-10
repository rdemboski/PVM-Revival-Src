/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NotifyPetSelectedAndActivatedMessage
/*    */   implements Message
/*    */ {
/*    */   private int characterId;
/*    */   private int slot;
/*    */   private String petId;
/*    */   
/*    */   public NotifyPetSelectedAndActivatedMessage() {}
/*    */   
/*    */   public NotifyPetSelectedAndActivatedMessage(int characterId, int slot, String petId) {
/* 20 */     this.characterId = characterId;
/* 21 */     this.slot = slot;
/* 22 */     this.petId = petId;
/*    */   }
/*    */   
/*    */   public NotifyPetSelectedAndActivatedMessage(ByteBuffer buffer) {
/* 26 */     this.characterId = MessageUtils.readInt(buffer);
/* 27 */     this.slot = MessageUtils.readInt(buffer);
/* 28 */     this.petId = MessageUtils.readStr(buffer);
/*    */   }
/*    */   
/*    */   public int getCharacterId() {
/* 32 */     return this.characterId;
/*    */   }
/*    */   
/*    */   public int getSlot() {
/* 36 */     return this.slot;
/*    */   }
/*    */   
/*    */   public String getPetId() {
/* 40 */     return this.petId;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 45 */     return 36;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 50 */     return new NotifyPetSelectedAndActivatedMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 55 */     return MessageUtils.getSizeInt() * 2 + MessageUtils.getSizeStr(this.petId);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 60 */     MessageUtils.writeInt(buffer, this.characterId);
/* 61 */     MessageUtils.writeInt(buffer, this.slot);
/* 62 */     MessageUtils.writeStr(buffer, this.petId);
/* 63 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\NotifyPetSelectedAndActivatedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */