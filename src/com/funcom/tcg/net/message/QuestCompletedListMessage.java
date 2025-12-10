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
/*    */ public class QuestCompletedListMessage
/*    */   implements Message
/*    */ {
/*    */   private List<String> questIds;
/*    */   private List<Boolean> claimedList;
/*    */   private boolean show;
/*    */   
/*    */   public QuestCompletedListMessage() {}
/*    */   
/*    */   public QuestCompletedListMessage(List<String> questIds, List<Boolean> claimedList, boolean show) {
/* 21 */     this.questIds = questIds;
/* 22 */     this.claimedList = claimedList;
/* 23 */     this.show = show;
/*    */   }
/*    */   
/*    */   public QuestCompletedListMessage(ByteBuffer buffer) {
/* 27 */     this.questIds = MessageUtils.readListStr(buffer);
/* 28 */     this.claimedList = MessageUtils.readListBoolean(buffer);
/* 29 */     this.show = MessageUtils.readBoolean(buffer).booleanValue();
/*    */   }
/*    */   
/*    */   public List<String> getQuestIds() {
/* 33 */     return this.questIds;
/*    */   }
/*    */   
/*    */   public List<Boolean> getClaimedList() {
/* 37 */     return this.claimedList;
/*    */   }
/*    */   
/*    */   public boolean isShow() {
/* 41 */     return this.show;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 46 */     return 72;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 51 */     return new QuestCompletedListMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 56 */     return MessageUtils.getSizeListStr(this.questIds) + MessageUtils.getSizeListBool(this.claimedList) + MessageUtils.getSizeBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 61 */     MessageUtils.writeListStr(buffer, this.questIds);
/* 62 */     MessageUtils.writeListBool(buffer, this.claimedList);
/* 63 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.show));
/* 64 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\QuestCompletedListMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */