/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.net.AccountResult;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class CreateCharacterResponseMessage
/*    */   implements Message
/*    */ {
/*    */   private AccountResult result;
/*    */   
/*    */   public CreateCharacterResponseMessage() {}
/*    */   
/*    */   public CreateCharacterResponseMessage(AccountResult result) {
/* 15 */     this.result = result;
/*    */   }
/*    */   
/*    */   public CreateCharacterResponseMessage(ByteBuffer buffer) {
/* 19 */     this.result = AccountResult.valueOfById(buffer.get());
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 23 */     return new CreateCharacterResponseMessage(buffer);
/*    */   }
/*    */   
/*    */   public AccountResult getResult() {
/* 27 */     return this.result;
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 31 */     return 4;
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 35 */     return 1;
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 39 */     buffer.put(this.result.getId());
/* 40 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\CreateCharacterResponseMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */