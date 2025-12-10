/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import com.funcom.tcg.net.AccountResult;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ public class AccountRegisterResponseMessage
/*    */   implements Message
/*    */ {
/*    */   private AccountResult result;
/*    */   private String name;
/*    */   
/*    */   public AccountRegisterResponseMessage() {}
/*    */   
/*    */   public AccountRegisterResponseMessage(AccountResult result, String name) {
/* 18 */     this.result = result;
/* 19 */     this.name = name;
/*    */   }
/*    */   
/*    */   public AccountRegisterResponseMessage(ByteBuffer buffer) {
/* 23 */     this.result = AccountResult.valueOfById(buffer.get());
/* 24 */     this.name = MessageUtils.readStr(buffer);
/*    */   }
/*    */   
/*    */   public AccountResult getResult() {
/* 28 */     return this.result;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 32 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 37 */     return 15;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 42 */     return new AccountRegisterResponseMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 47 */     return 1 + MessageUtils.getSizeStr(this.name);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 53 */     buffer.put(this.result.getId());
/* 54 */     MessageUtils.writeStr(buffer, this.name);
/*    */     
/* 56 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\AccountRegisterResponseMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */