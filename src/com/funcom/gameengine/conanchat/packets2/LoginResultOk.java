/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
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
/*    */ public class LoginResultOk
/*    */   implements ChatMessage
/*    */ {
/*    */   public short getMessageType() {
/* 19 */     return 5;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 23 */     return new LoginResultOk();
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 27 */     return 0;
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 31 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\LoginResultOk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */