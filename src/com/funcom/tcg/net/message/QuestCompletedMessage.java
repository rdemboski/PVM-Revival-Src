/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ public class QuestCompletedMessage
/*    */   implements Message
/*    */ {
/*    */   private String questId;
/*    */   private boolean claimed;
/*    */   private int messageInformationType;
/*    */   
/*    */   public QuestCompletedMessage(ByteBuffer buffer) {
/* 16 */     this.questId = MessageUtils.readStr(buffer);
/* 17 */     this.claimed = MessageUtils.readBoolean(buffer).booleanValue();
/* 18 */     this.messageInformationType = MessageUtils.readInt(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public QuestCompletedMessage() {}
/*    */   
/*    */   public QuestCompletedMessage(String questId, boolean claimed, int messageInformationType) {
/* 25 */     this.questId = questId;
/* 26 */     this.claimed = claimed;
/* 27 */     this.messageInformationType = messageInformationType;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 32 */     return 40;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 37 */     return new QuestCompletedMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 42 */     return MessageUtils.getSizeStr(this.questId) + MessageUtils.getSizeBoolean() + MessageUtils.getSizeInt();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getQuestId() {
/* 48 */     return this.questId;
/*    */   }
/*    */   
/*    */   public boolean isClaimed() {
/* 52 */     return this.claimed;
/*    */   }
/*    */   
/*    */   public int getMessageInformationType() {
/* 56 */     return this.messageInformationType;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 61 */     MessageUtils.writeStr(buffer, this.questId);
/* 62 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.claimed));
/* 63 */     MessageUtils.writeInt(buffer, this.messageInformationType);
/* 64 */     return buffer;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 69 */     return "QuestCompletedMessage{, questId='" + this.questId + '\'' + ", messageInformationType=" + this.messageInformationType + '}';
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public enum MessageInformationType
/*    */   {
/* 76 */     NEW(0), COMPLETED(1), UPDATE(2);
/*    */     
/*    */     private int id;
/*    */     
/*    */     MessageInformationType(int id) {
/* 81 */       this.id = id;
/*    */     }
/*    */     
/*    */     public int getId() {
/* 85 */       return this.id;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\QuestCompletedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */