/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class QuestCompleteMessage
/*    */   implements Message
/*    */ {
/*    */   private String questID;
/*    */   private int questGiver;
/*    */   
/*    */   public QuestCompleteMessage() {}
/*    */   
/*    */   public QuestCompleteMessage(String questID, int questGiver) {
/* 24 */     this.questID = questID;
/* 25 */     this.questGiver = questGiver;
/*    */   }
/*    */   
/*    */   public QuestCompleteMessage(ByteBuffer buffer) {
/* 29 */     this.questID = MessageUtils.readStr(buffer);
/* 30 */     this.questGiver = MessageUtils.readInt(buffer);
/*    */   }
/*    */   
/*    */   public String getQuestID() {
/* 34 */     return this.questID;
/*    */   }
/*    */   
/*    */   public int getQuestGiver() {
/* 38 */     return this.questGiver;
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 42 */     return 223;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 46 */     return new QuestCompleteMessage(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 50 */     return MessageUtils.getSizeStr(this.questID) + MessageUtils.getSizeInt();
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 54 */     MessageUtils.writeStr(buffer, this.questID);
/* 55 */     MessageUtils.writeInt(buffer, this.questGiver);
/* 56 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\QuestCompleteMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */