/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ public class AcceptQuestMessage
/*    */   implements Message
/*    */ {
/*    */   private String questId;
/*    */   
/*    */   public AcceptQuestMessage(String questId) {
/* 14 */     this.questId = questId;
/*    */   }
/*    */ 
/*    */   
/*    */   public AcceptQuestMessage() {}
/*    */   
/*    */   public AcceptQuestMessage(ByteBuffer buffer) {
/* 21 */     this.questId = MessageUtils.readStr(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getQuestId() {
/* 26 */     return this.questId;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 31 */     return 41;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 36 */     return new AcceptQuestMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 41 */     return MessageUtils.getSizeStr(this.questId);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 46 */     MessageUtils.writeStr(buffer, this.questId);
/* 47 */     return buffer;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 52 */     return "AcceptQuestMessage{questId='" + this.questId + '\'' + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\AcceptQuestMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */