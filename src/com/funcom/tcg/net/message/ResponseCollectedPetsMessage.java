/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ public class ResponseCollectedPetsMessage
/*    */   implements Message
/*    */ {
/*    */   private int id;
/*    */   private String[] petClassIds;
/*    */   private Integer[] petClassLevels;
/*    */   private Integer[] petClassXp;
/*    */   
/*    */   public ResponseCollectedPetsMessage() {}
/*    */   
/*    */   public ResponseCollectedPetsMessage(ByteBuffer buffer) {
/* 19 */     this.id = MessageUtils.readInt(buffer);
/* 20 */     this.petClassIds = MessageUtils.readStrArray(buffer);
/* 21 */     this.petClassLevels = MessageUtils.readIntArray(buffer);
/* 22 */     this.petClassXp = MessageUtils.readIntArray(buffer);
/*    */   }
/*    */   
/*    */   public ResponseCollectedPetsMessage(int id, String[] petClassIds, Integer[] petClassLevels, Integer[] petClassXp) {
/* 26 */     this.id = id;
/* 27 */     this.petClassIds = petClassIds;
/* 28 */     this.petClassLevels = petClassLevels;
/* 29 */     this.petClassXp = petClassXp;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 33 */     return this.id;
/*    */   }
/*    */   
/*    */   public String[] getPetClassIds() {
/* 37 */     return this.petClassIds;
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 41 */     return 209;
/*    */   }
/*    */   
/*    */   public ResponseCollectedPetsMessage toMessage(ByteBuffer buffer) {
/* 45 */     return new ResponseCollectedPetsMessage(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 49 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeStrArray(this.petClassIds) + MessageUtils.getSizeIntArray(this.petClassLevels) + MessageUtils.getSizeIntArray(this.petClassXp);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 56 */     MessageUtils.writeInt(buffer, this.id);
/* 57 */     MessageUtils.writeStrArray(buffer, this.petClassIds);
/* 58 */     MessageUtils.writeIntArray(buffer, this.petClassLevels);
/* 59 */     MessageUtils.writeIntArray(buffer, this.petClassXp);
/*    */     
/* 61 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer[] getPetClassLevels() {
/* 65 */     return this.petClassLevels;
/*    */   }
/*    */   
/*    */   public Integer[] getPetClassXp() {
/* 69 */     return this.petClassXp;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\ResponseCollectedPetsMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */