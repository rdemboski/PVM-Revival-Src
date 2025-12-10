/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RefreshPlayerDataMessage
/*    */   implements Message
/*    */ {
/*    */   private String nick;
/*    */   private String[] selectedPetClassIds;
/*    */   private String activePetClassId;
/*    */   private List<String> visitedMaps;
/*    */   
/*    */   public RefreshPlayerDataMessage() {}
/*    */   
/*    */   public RefreshPlayerDataMessage(String nick, String[] selectedPetClassIds, String activePetClassId, List<String> visitedMaps) {
/* 25 */     this.nick = nick;
/* 26 */     this.selectedPetClassIds = selectedPetClassIds;
/* 27 */     this.activePetClassId = activePetClassId;
/* 28 */     this.visitedMaps = visitedMaps;
/*    */   }
/*    */   
/*    */   public RefreshPlayerDataMessage(ByteBuffer buffer) {
/* 32 */     this.nick = MessageUtils.readStr(buffer);
/* 33 */     this.selectedPetClassIds = MessageUtils.readStrArray(buffer);
/* 34 */     this.activePetClassId = MessageUtils.readStr(buffer);
/* 35 */     this.visitedMaps = MessageUtils.readListStr(buffer);
/*    */   }
/*    */   
/*    */   public String getNick() {
/* 39 */     return this.nick;
/*    */   }
/*    */   
/*    */   public String[] getSelectedPetClassIds() {
/* 43 */     return this.selectedPetClassIds;
/*    */   }
/*    */   
/*    */   public String getActivePetClassId() {
/* 47 */     return this.activePetClassId;
/*    */   }
/*    */   
/*    */   public List<String> getVisitedMaps() {
/* 51 */     return this.visitedMaps;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 56 */     return 71;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 61 */     return new RefreshPlayerDataMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 66 */     return MessageUtils.getSizeStr(this.nick) + MessageUtils.getSizeStrArray(this.selectedPetClassIds) + MessageUtils.getSizeStr(this.activePetClassId) + MessageUtils.getSizeListStr(this.visitedMaps);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 74 */     MessageUtils.writeStr(buffer, this.nick);
/* 75 */     MessageUtils.writeStrArray(buffer, this.selectedPetClassIds);
/* 76 */     MessageUtils.writeStr(buffer, this.activePetClassId);
/* 77 */     MessageUtils.writeListStr(buffer, this.visitedMaps);
/* 78 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\RefreshPlayerDataMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */