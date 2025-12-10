/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DuelInvitationMessage
/*    */   implements Message
/*    */ {
/*    */   private int challengerId;
/*    */   private int duelId;
/*    */   
/*    */   public DuelInvitationMessage() {}
/*    */   
/*    */   public DuelInvitationMessage(int challengerId, int duelId) {
/* 19 */     this.challengerId = challengerId;
/* 20 */     this.duelId = duelId;
/*    */   }
/*    */   
/*    */   public DuelInvitationMessage(ByteBuffer buffer) {
/* 24 */     this.challengerId = MessageUtils.readInt(buffer);
/* 25 */     this.duelId = MessageUtils.readInt(buffer);
/*    */   }
/*    */   
/*    */   public int getChallengerId() {
/* 29 */     return this.challengerId;
/*    */   }
/*    */   
/*    */   public int getDuelId() {
/* 33 */     return this.duelId;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 38 */     return 247;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 43 */     return new DuelInvitationMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 48 */     return MessageUtils.getSizeInt() * 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 53 */     MessageUtils.writeInt(buffer, this.challengerId);
/* 54 */     MessageUtils.writeInt(buffer, this.duelId);
/* 55 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\DuelInvitationMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */