/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.Collection;
/*    */ import java.util.LinkedList;
/*    */ 
/*    */ 
/*    */ public class QuestsAbandonedMessage
/*    */   implements Message
/*    */ {
/*    */   private Collection<String> questIds;
/*    */   
/*    */   public QuestsAbandonedMessage() {}
/*    */   
/*    */   public QuestsAbandonedMessage(Collection<String> questIds) {
/* 18 */     this.questIds = questIds;
/*    */   }
/*    */   
/*    */   public QuestsAbandonedMessage(ByteBuffer buffer) {
/* 22 */     this.questIds = new LinkedList<String>();
/* 23 */     while (buffer.hasRemaining()) {
/* 24 */       this.questIds.add(MessageUtils.readStr(buffer));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 30 */     return 232;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 35 */     return new QuestsAbandonedMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 40 */     int size = 0;
/* 41 */     for (String questId : this.questIds) {
/* 42 */       size += MessageUtils.getSizeStr(questId);
/*    */     }
/* 44 */     return size;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 49 */     for (String questId : this.questIds) {
/* 50 */       MessageUtils.writeStr(buffer, questId);
/*    */     }
/* 52 */     return buffer;
/*    */   }
/*    */   
/*    */   public Collection<String> getQuestIds() {
/* 56 */     return this.questIds;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\QuestsAbandonedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */