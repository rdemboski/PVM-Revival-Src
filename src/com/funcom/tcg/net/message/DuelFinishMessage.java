/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DuelFinishMessage
/*    */   implements Message
/*    */ {
/*    */   private boolean won;
/*    */   
/*    */   public DuelFinishMessage() {}
/*    */   
/*    */   public DuelFinishMessage(boolean won) {
/* 18 */     this.won = won;
/*    */   }
/*    */   
/*    */   public DuelFinishMessage(ByteBuffer buffer) {
/* 22 */     this.won = MessageUtils.readBoolean(buffer).booleanValue();
/*    */   }
/*    */   
/*    */   public boolean isWon() {
/* 26 */     return this.won;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 31 */     return 251;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 36 */     return new DuelFinishMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 41 */     return MessageUtils.getSizeBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 46 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.won));
/* 47 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\DuelFinishMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */