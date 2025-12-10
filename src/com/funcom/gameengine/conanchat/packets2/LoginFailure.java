/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.StringDatatype;
/*    */ import com.funcom.server.common.Message;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LoginFailure
/*    */   implements ChatMessage
/*    */ {
/*    */   private String errorMessage;
/*    */   
/*    */   public LoginFailure() {}
/*    */   
/*    */   public LoginFailure(String errorMessage) {
/* 22 */     this.errorMessage = errorMessage;
/*    */   }
/*    */   
/*    */   public LoginFailure(ByteBuffer inData) {
/* 26 */     this.errorMessage = (new StringDatatype(inData)).getValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 31 */     return 6;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 35 */     return new LoginFailure(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 39 */     return (new StringDatatype(this.errorMessage)).getSizeInBytes();
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 43 */     (new StringDatatype(this.errorMessage)).toByteBuffer(buffer);
/* 44 */     return buffer;
/*    */   }
/*    */   
/*    */   public String getErrorMessage() {
/* 48 */     return this.errorMessage;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\LoginFailure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */