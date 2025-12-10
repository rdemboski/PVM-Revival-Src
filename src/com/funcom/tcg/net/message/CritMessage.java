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
/*    */ public class CritMessage
/*    */   implements Message
/*    */ {
/*    */   private int sourceId;
/*    */   private int targetId;
/*    */   private int damageValue;
/*    */   
/*    */   public CritMessage(int sourceId, int targetId, int damageValue) {
/* 19 */     this.sourceId = sourceId;
/* 20 */     this.targetId = targetId;
/* 21 */     this.damageValue = damageValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public CritMessage() {}
/*    */   
/*    */   public CritMessage(ByteBuffer buffer) {
/* 28 */     this.sourceId = MessageUtils.readInt(buffer);
/* 29 */     this.targetId = MessageUtils.readInt(buffer);
/* 30 */     this.damageValue = MessageUtils.readInt(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSourceId() {
/* 35 */     return this.sourceId;
/*    */   }
/*    */   
/*    */   public int getTargetId() {
/* 39 */     return this.targetId;
/*    */   }
/*    */   
/*    */   public int getDamageValue() {
/* 43 */     return this.damageValue;
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 47 */     return 30;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 51 */     return new CritMessage(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 55 */     return MessageUtils.getSizeInt() * 3;
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 59 */     MessageUtils.writeInt(buffer, this.sourceId);
/* 60 */     MessageUtils.writeInt(buffer, this.targetId);
/* 61 */     MessageUtils.writeInt(buffer, this.damageValue);
/* 62 */     return buffer;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 66 */     StringBuffer sb = new StringBuffer();
/* 67 */     sb.append("sourceId=").append(this.sourceId).append(",targetId=").append(this.targetId).append("=,damageValue").append(this.damageValue);
/*    */ 
/*    */ 
/*    */     
/* 71 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\CritMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */