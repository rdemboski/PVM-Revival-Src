/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SubscriptionStatusUpdateMessage
/*    */   implements Message
/*    */ {
/*    */   private final int newFlags;
/*    */   
/*    */   public SubscriptionStatusUpdateMessage() {
/* 15 */     this.newFlags = -1;
/*    */   }
/*    */   
/*    */   public SubscriptionStatusUpdateMessage(int newFlags) {
/* 19 */     this.newFlags = newFlags;
/*    */   }
/*    */   
/*    */   public SubscriptionStatusUpdateMessage(ByteBuffer byteBuffer) {
/* 23 */     this.newFlags = MessageUtils.readInt(byteBuffer);
/*    */   }
/*    */   
/*    */   public int getNewFlags() {
/* 27 */     return this.newFlags;
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 31 */     return 70;
/*    */   }
/*    */   
/*    */   public SubscriptionStatusUpdateMessage toMessage(ByteBuffer buffer) {
/* 35 */     return new SubscriptionStatusUpdateMessage(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 39 */     return MessageUtils.getSizeInt();
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 43 */     MessageUtils.writeInt(buffer, this.newFlags);
/* 44 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\SubscriptionStatusUpdateMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */