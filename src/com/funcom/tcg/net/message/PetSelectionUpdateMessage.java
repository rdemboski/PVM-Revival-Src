/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PetSelectionUpdateMessage
/*    */   implements Message
/*    */ {
/*    */   public static final String NO_PET = "";
/*    */   private String[] petClassIds;
/*    */   
/*    */   public PetSelectionUpdateMessage() {}
/*    */   
/*    */   public PetSelectionUpdateMessage(String[] petClassIds) {
/* 19 */     this.petClassIds = petClassIds;
/*    */   }
/*    */   
/*    */   public PetSelectionUpdateMessage(ByteBuffer buffer) {
/* 23 */     this.petClassIds = new String[3];
/* 24 */     for (int i = 0; i < 3; i++) {
/* 25 */       this.petClassIds[i] = MessageUtils.readStr(buffer);
/*    */     }
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 30 */     return 212;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 34 */     return new PetSelectionUpdateMessage(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 38 */     int size = 0;
/* 39 */     for (int i = 0; i < 3; i++) {
/* 40 */       size += MessageUtils.getSizeStr(this.petClassIds[i]);
/*    */     }
/* 42 */     return size;
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 46 */     for (int i = 0; i < 3; i++) {
/* 47 */       MessageUtils.writeStr(buffer, this.petClassIds[i]);
/*    */     }
/*    */     
/* 50 */     return buffer;
/*    */   }
/*    */   
/*    */   public String[] getPetClassIds() {
/* 54 */     return this.petClassIds;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\PetSelectionUpdateMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */