/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CompleteTutorialQuestObjectiveMessage
/*    */   implements Message
/*    */ {
/*    */   private String objectiveId;
/*    */   
/*    */   public CompleteTutorialQuestObjectiveMessage() {}
/*    */   
/*    */   public CompleteTutorialQuestObjectiveMessage(String objectiveId) {
/* 18 */     this.objectiveId = objectiveId;
/*    */   }
/*    */   
/*    */   public CompleteTutorialQuestObjectiveMessage(ByteBuffer buffer) {
/* 22 */     this.objectiveId = MessageUtils.readStr(buffer);
/*    */   }
/*    */   
/*    */   public String getObjectiveId() {
/* 26 */     return this.objectiveId;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 31 */     return 74;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 36 */     return new CompleteTutorialQuestObjectiveMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 41 */     return MessageUtils.getSizeStr(this.objectiveId);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 46 */     MessageUtils.writeStr(buffer, this.objectiveId);
/* 47 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\CompleteTutorialQuestObjectiveMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */