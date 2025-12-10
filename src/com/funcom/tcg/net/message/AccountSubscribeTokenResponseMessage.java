/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class AccountSubscribeTokenResponseMessage
/*    */   implements Message
/*    */ {
/*    */   private volatile String token;
/*    */   
/*    */   public AccountSubscribeTokenResponseMessage() {}
/*    */   
/*    */   public AccountSubscribeTokenResponseMessage(String token) {
/* 15 */     this.token = token;
/*    */   }
/*    */   
/*    */   public AccountSubscribeTokenResponseMessage(ByteBuffer buffer) {
/* 19 */     this.token = MessageUtils.readStr(buffer);
/*    */   }
/*    */   
/*    */   public String getToken() {
/* 23 */     return this.token;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 28 */     return 17;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 33 */     return new AccountSubscribeTokenResponseMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 38 */     return MessageUtils.getSizeStr(this.token);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 43 */     MessageUtils.writeStr(buffer, this.token);
/* 44 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\AccountSubscribeTokenResponseMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */