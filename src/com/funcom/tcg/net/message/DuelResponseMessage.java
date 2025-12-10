/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DuelResponseMessage
/*    */   implements Message
/*    */ {
/*    */   private int duelId;
/*    */   private short responseType;
/*    */   
/*    */   public DuelResponseMessage() {}
/*    */   
/*    */   public DuelResponseMessage(int duelId, short responseType) {
/* 19 */     this.duelId = duelId;
/* 20 */     this.responseType = responseType;
/*    */   }
/*    */   
/*    */   public DuelResponseMessage(ByteBuffer buffer) {
/* 24 */     this.duelId = MessageUtils.readInt(buffer);
/* 25 */     this.responseType = MessageUtils.readShort(buffer);
/*    */   }
/*    */   
/*    */   public int getDuelId() {
/* 29 */     return this.duelId;
/*    */   }
/*    */   
/*    */   public short getResponseType() {
/* 33 */     return this.responseType;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 38 */     return 248;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 43 */     return new DuelResponseMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 48 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeShort();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 53 */     MessageUtils.writeInt(buffer, this.duelId);
/* 54 */     MessageUtils.writeShort(buffer, this.responseType);
/* 55 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\DuelResponseMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */