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
/*    */ public class QuestObjectiveUpdateListMessage
/*    */   implements Message
/*    */ {
/*    */   private List<String> questIds;
/*    */   private List<String> objectiveIds;
/*    */   private List<Integer> newValues;
/*    */   
/*    */   public QuestObjectiveUpdateListMessage() {}
/*    */   
/*    */   public QuestObjectiveUpdateListMessage(List<String> questTrackerId, List<String> objectiveId, List<Integer> newValue) {
/* 22 */     this.questIds = questTrackerId;
/* 23 */     this.objectiveIds = objectiveId;
/* 24 */     this.newValues = newValue;
/*    */   }
/*    */   
/*    */   public QuestObjectiveUpdateListMessage(ByteBuffer buffer) {
/* 28 */     this.questIds = MessageUtils.readListStr(buffer);
/* 29 */     this.objectiveIds = MessageUtils.readListStr(buffer);
/* 30 */     this.newValues = MessageUtils.readListInt(buffer);
/*    */   }
/*    */   
/*    */   public void addQuestUpdate(String trackerId, String objectiveId, Integer newValue) {
/* 34 */     this.questIds.add(trackerId);
/* 35 */     this.objectiveIds.add(objectiveId);
/* 36 */     this.newValues.add(newValue);
/*    */   }
/*    */   
/*    */   public List<String> getQuestIds() {
/* 40 */     return this.questIds;
/*    */   }
/*    */   
/*    */   public List<String> getObjectiveIds() {
/* 44 */     return this.objectiveIds;
/*    */   }
/*    */   
/*    */   public List<Integer> getNewValues() {
/* 48 */     return this.newValues;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 53 */     return 44;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 58 */     return new QuestObjectiveUpdateMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 63 */     return MessageUtils.getSizeListStr(this.questIds) + MessageUtils.getSizeListStr(this.objectiveIds) + MessageUtils.getSizeListInt(this.newValues);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 68 */     MessageUtils.writeListStr(buffer, this.questIds);
/* 69 */     MessageUtils.writeListStr(buffer, this.objectiveIds);
/* 70 */     MessageUtils.writeListInt(buffer, this.newValues);
/* 71 */     return buffer;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 76 */     return "QuestObjectiveUpdateMessage{questId='" + this.questIds + '\'' + ", objectiveId=" + this.objectiveIds + ", newValue=" + this.newValues + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\QuestObjectiveUpdateListMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */