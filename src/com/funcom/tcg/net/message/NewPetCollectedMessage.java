/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NewPetCollectedMessage
/*    */   implements Message
/*    */ {
/*    */   private int characterId;
/*    */   private String petId;
/*    */   private int level;
/*    */   
/*    */   public NewPetCollectedMessage() {}
/*    */   
/*    */   public NewPetCollectedMessage(int characterId, String petId, int level) {
/* 20 */     this.characterId = characterId;
/* 21 */     this.petId = petId;
/* 22 */     this.level = level;
/*    */   }
/*    */   
/*    */   public NewPetCollectedMessage(ByteBuffer buffer) {
/* 26 */     this.characterId = MessageUtils.readInt(buffer);
/* 27 */     this.petId = MessageUtils.readStr(buffer);
/* 28 */     this.level = MessageUtils.readInt(buffer);
/*    */   }
/*    */   
/*    */   public int getCharacterId() {
/* 32 */     return this.characterId;
/*    */   }
/*    */   
/*    */   public String getPetId() {
/* 36 */     return this.petId;
/*    */   }
/*    */   
/*    */   public int getLevel() {
/* 40 */     return this.level;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 45 */     return 236;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 50 */     return new NewPetCollectedMessage(buffer);
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
/* 61 */     MessageUtils.writeStr(buffer, this.petId);
/* 62 */     MessageUtils.writeInt(buffer, this.level);
/* 63 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\NewPetCollectedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */