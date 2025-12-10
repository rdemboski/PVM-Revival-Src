/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ public class ActivePetUpdateMessage
/*    */   implements Message
/*    */ {
/*    */   private int creatureId;
/*    */   private String activePetClassId;
/*    */   private int petLevel;
/*    */   
/*    */   public ActivePetUpdateMessage() {}
/*    */   
/*    */   public ActivePetUpdateMessage(int creatureId, String activePetClassId, int petLevel) {
/* 18 */     this.creatureId = creatureId;
/* 19 */     this.activePetClassId = activePetClassId;
/* 20 */     this.petLevel = petLevel;
/*    */   }
/*    */   
/*    */   public ActivePetUpdateMessage(ByteBuffer buffer) {
/* 24 */     this.creatureId = MessageUtils.readInt(buffer);
/* 25 */     this.activePetClassId = MessageUtils.readStr(buffer);
/* 26 */     this.petLevel = MessageUtils.readInt(buffer);
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 30 */     return 213;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 34 */     return new ActivePetUpdateMessage(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 38 */     return MessageUtils.getSizeInt() * 2 + MessageUtils.getSizeStr(this.activePetClassId);
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 42 */     MessageUtils.writeInt(buffer, this.creatureId);
/* 43 */     MessageUtils.writeStr(buffer, this.activePetClassId);
/* 44 */     MessageUtils.writeInt(buffer, this.petLevel);
/* 45 */     return buffer;
/*    */   }
/*    */   
/*    */   public int getCreatureId() {
/* 49 */     return this.creatureId;
/*    */   }
/*    */   
/*    */   public String getActivePetClassId() {
/* 53 */     return this.activePetClassId;
/*    */   }
/*    */   
/*    */   public int getPetLevel() {
/* 57 */     return this.petLevel;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 61 */     StringBuffer sb = new StringBuffer();
/* 62 */     sb.append("[creatureId:").append(this.creatureId).append(", activePetClassId:").append(this.activePetClassId).append("]");
/*    */ 
/*    */     
/* 65 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\ActivePetUpdateMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */