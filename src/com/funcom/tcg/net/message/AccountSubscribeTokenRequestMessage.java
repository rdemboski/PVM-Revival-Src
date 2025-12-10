/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AccountSubscribeTokenRequestMessage
/*    */   implements Message
/*    */ {
/*    */   public AccountSubscribeTokenRequestMessage() {}
/*    */   
/*    */   public AccountSubscribeTokenRequestMessage(ByteBuffer buffer) {}
/*    */   
/*    */   public short getMessageType() {
/* 17 */     return 16;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 22 */     return new AccountSubscribeTokenRequestMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 27 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 32 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\AccountSubscribeTokenRequestMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */