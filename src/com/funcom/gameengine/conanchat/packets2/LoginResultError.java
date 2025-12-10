/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Endianess;
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
/*    */ 
/*    */ 
/*    */ public class LoginResultError
/*    */   implements ChatMessage
/*    */ {
/*    */   private StringDatatype errorMessage;
/*    */   
/*    */   public LoginResultError() {}
/*    */   
/*    */   public LoginResultError(StringDatatype errorMessage) {
/* 25 */     this.errorMessage = errorMessage;
/*    */   }
/*    */   
/*    */   public LoginResultError(ByteBuffer byteBuffer) {
/* 29 */     this.errorMessage = new StringDatatype(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 34 */     return 6;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 39 */     return new LoginResultError(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 44 */     return this.errorMessage.getSizeInBytes();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 49 */     this.errorMessage.toByteBuffer(buffer);
/* 50 */     return buffer;
/*    */   }
/*    */   
/*    */   public StringDatatype getErrorMessage() {
/* 54 */     return this.errorMessage;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\LoginResultError.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */