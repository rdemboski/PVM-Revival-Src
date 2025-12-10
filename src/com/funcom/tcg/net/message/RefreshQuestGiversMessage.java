/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RefreshQuestGiversMessage
/*    */   implements Message
/*    */ {
/*    */   private String[] questIds;
/*    */   private String[] handInQuestIds;
/*    */   private Short[] progress;
/*    */   private Integer[] questGiverIds;
/*    */   
/*    */   public RefreshQuestGiversMessage() {}
/*    */   
/*    */   public RefreshQuestGiversMessage(String[] questIds, Short[] progress, Integer[] questGiverIds, String[] handInQuestIds) {
/* 23 */     this.questIds = questIds;
/* 24 */     this.progress = progress;
/* 25 */     this.questGiverIds = questGiverIds;
/* 26 */     this.handInQuestIds = handInQuestIds;
/*    */   }
/*    */   
/*    */   public RefreshQuestGiversMessage(ByteBuffer buffer) {
/* 30 */     this.questIds = MessageUtils.readStrArray(buffer);
/* 31 */     this.progress = MessageUtils.readShortArray(buffer);
/* 32 */     this.questGiverIds = MessageUtils.readIntArray(buffer);
/* 33 */     this.handInQuestIds = MessageUtils.readStrArray(buffer);
/*    */   }
/*    */   
/*    */   public String[] getQuestIds() {
/* 37 */     return this.questIds;
/*    */   }
/*    */   
/*    */   public Short[] getProgress() {
/* 41 */     return this.progress;
/*    */   }
/*    */   
/*    */   public Integer[] getQuestGiverIds() {
/* 45 */     return this.questGiverIds;
/*    */   }
/*    */   
/*    */   public String[] getHandInQuestIds() {
/* 49 */     return this.handInQuestIds;
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 53 */     return 222;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 57 */     return new RefreshQuestGiversMessage(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 61 */     return MessageUtils.getSizeStrArray(this.questIds) + MessageUtils.getSizeShortArray(this.progress) + MessageUtils.getSizeIntArray(this.questGiverIds) + MessageUtils.getSizeStrArray(this.handInQuestIds);
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 65 */     MessageUtils.writeStrArray(buffer, this.questIds);
/* 66 */     MessageUtils.writeShortArray(buffer, this.progress);
/* 67 */     MessageUtils.writeIntArray(buffer, this.questGiverIds);
/* 68 */     MessageUtils.writeStrArray(buffer, this.handInQuestIds);
/* 69 */     return buffer;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 74 */     return "RefreshQuestGiversMessage{questIds=" + ((this.questIds == null) ? null : (String)Arrays.<String>asList(this.questIds)) + ", handInQuestIds=" + ((this.handInQuestIds == null) ? null : (String)Arrays.<String>asList(this.handInQuestIds)) + ", progress=" + ((this.progress == null) ? null : (String)Arrays.<Short>asList(this.progress)) + ", questGiverIds=" + ((this.questGiverIds == null) ? null : (String)Arrays.<Integer>asList(this.questGiverIds)) + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\RefreshQuestGiversMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */