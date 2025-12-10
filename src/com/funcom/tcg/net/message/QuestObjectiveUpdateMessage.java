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
/*    */ public class QuestObjectiveUpdateMessage
/*    */   implements Message
/*    */ {
/*    */   private String questId;
/*    */   private String objectiveId;
/*    */   private int newValue;
/*    */   
/*    */   public QuestObjectiveUpdateMessage(String questTrackerId, String objectiveId, int newValue) {
/* 20 */     this.questId = questTrackerId;
/* 21 */     this.objectiveId = objectiveId;
/* 22 */     this.newValue = newValue;
/*    */   }
/*    */   
/*    */   public QuestObjectiveUpdateMessage(ByteBuffer buffer) {
/* 26 */     this.questId = MessageUtils.readStr(buffer);
/* 27 */     this.objectiveId = MessageUtils.readStr(buffer);
/* 28 */     this.newValue = MessageUtils.readInt(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public QuestObjectiveUpdateMessage() {}
/*    */   
/*    */   public String getQuestId() {
/* 35 */     return this.questId;
/*    */   }
/*    */   
/*    */   public String getObjectiveId() {
/* 39 */     return this.objectiveId;
/*    */   }
/*    */   
/*    */   public int getNewValue() {
/* 43 */     return this.newValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 48 */     return 43;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 53 */     return new QuestObjectiveUpdateMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 58 */     return MessageUtils.getSizeStr(this.questId) + MessageUtils.getSizeStr(this.objectiveId) + MessageUtils.getSizeInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 63 */     MessageUtils.writeStr(buffer, this.questId);
/* 64 */     MessageUtils.writeStr(buffer, this.objectiveId);
/* 65 */     MessageUtils.writeInt(buffer, this.newValue);
/* 66 */     return buffer;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 71 */     return "QuestObjectiveUpdateMessage{questId='" + this.questId + '\'' + ", objectiveId=" + this.objectiveId + ", newValue=" + this.newValue + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\QuestObjectiveUpdateMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */