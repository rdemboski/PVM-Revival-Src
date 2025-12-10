/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClaimQuestRewardsMessage
/*    */   implements Message
/*    */ {
/*    */   private String questId;
/*    */   
/*    */   public ClaimQuestRewardsMessage() {}
/*    */   
/*    */   public ClaimQuestRewardsMessage(String questId) {
/* 18 */     this.questId = questId;
/*    */   }
/*    */   
/*    */   public ClaimQuestRewardsMessage(ByteBuffer buffer) {
/* 22 */     this.questId = MessageUtils.readStr(buffer);
/*    */   }
/*    */   
/*    */   public String getQuestId() {
/* 26 */     return this.questId;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 31 */     return 253;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 36 */     return new ClaimQuestRewardsMessage(buffer);
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
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\ClaimQuestRewardsMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */