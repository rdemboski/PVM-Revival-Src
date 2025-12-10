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
/*    */ 
/*    */ 
/*    */ public class ErrorMessage
/*    */   implements Message
/*    */ {
/*    */   private String errorMessage;
/*    */   private String title;
/*    */   
/*    */   public ErrorMessage(String errorMessage, String title) {
/* 20 */     this.errorMessage = errorMessage;
/* 21 */     this.title = title;
/*    */   }
/*    */ 
/*    */   
/*    */   public ErrorMessage() {}
/*    */   
/*    */   public ErrorMessage(ByteBuffer buffer) {
/* 28 */     this.errorMessage = MessageUtils.readStr(buffer);
/* 29 */     this.title = MessageUtils.readStr(buffer);
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 33 */     return 29;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 37 */     return new ErrorMessage(buffer);
/*    */   }
/*    */   
/*    */   public String getErrorMessage() {
/* 41 */     return this.errorMessage;
/*    */   }
/*    */   
/*    */   public String getTitle() {
/* 45 */     return this.title;
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 49 */     return MessageUtils.getSizeStr(this.errorMessage) + MessageUtils.getSizeStr(this.title);
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 53 */     MessageUtils.writeStr(buffer, this.errorMessage);
/* 54 */     MessageUtils.writeStr(buffer, this.title);
/* 55 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\ErrorMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */