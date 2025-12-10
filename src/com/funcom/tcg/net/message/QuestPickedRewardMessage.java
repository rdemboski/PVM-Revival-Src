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
/*    */ public class QuestPickedRewardMessage
/*    */   implements Message
/*    */ {
/*    */   private String questId;
/*    */   private String rewardId;
/*    */   private int completedId;
/*    */   
/*    */   public QuestPickedRewardMessage() {}
/*    */   
/*    */   public QuestPickedRewardMessage(ByteBuffer buffer) {
/* 23 */     this.completedId = MessageUtils.readInt(buffer);
/* 24 */     this.questId = MessageUtils.readStr(buffer);
/* 25 */     this.rewardId = MessageUtils.readStr(buffer);
/*    */   }
/*    */   
/*    */   public QuestPickedRewardMessage(int completedId, String questId, String rewardId) {
/* 29 */     this.completedId = completedId;
/* 30 */     this.questId = questId;
/* 31 */     this.rewardId = rewardId;
/*    */   }
/*    */   
/*    */   public String getQuestId() {
/* 35 */     return this.questId;
/*    */   }
/*    */   
/*    */   public String getRewardId() {
/* 39 */     return this.rewardId;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 44 */     return 42;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 49 */     return new QuestPickedRewardMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 54 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeStr(this.questId) + MessageUtils.getSizeStr(this.rewardId);
/*    */   }
/*    */   
/*    */   public int getCompletedId() {
/* 58 */     return this.completedId;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 63 */     MessageUtils.writeInt(buffer, this.completedId);
/* 64 */     MessageUtils.writeStr(buffer, this.questId);
/* 65 */     MessageUtils.writeStr(buffer, this.rewardId);
/* 66 */     return buffer;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 71 */     return "QuestPickedRewardMessage{questId='" + this.questId + '\'' + ", rewardId='" + this.rewardId + '\'' + ", completedId=" + this.completedId + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\QuestPickedRewardMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */