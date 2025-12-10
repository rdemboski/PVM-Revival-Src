/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ public class RequestQuestMessage
/*    */   implements Message
/*    */ {
/*    */   private String questId;
/*    */   
/*    */   public RequestQuestMessage() {}
/*    */   
/*    */   public RequestQuestMessage(String questId) {
/* 16 */     this.questId = questId;
/*    */   }
/*    */   
/*    */   public RequestQuestMessage(ByteBuffer buffer) {
/* 20 */     this.questId = MessageUtils.readStr(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 25 */     return 221;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 31 */     return new RequestQuestMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 36 */     return MessageUtils.getSizeStr(this.questId);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 41 */     MessageUtils.writeStr(buffer, this.questId);
/* 42 */     return buffer;
/*    */   }
/*    */   
/*    */   public String getQuestId() {
/* 46 */     return this.questId;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\RequestQuestMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */