/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LoginFinishedMessage
/*    */   implements Message
/*    */ {
/*    */   public short getMessageType() {
/* 15 */     return 7;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 19 */     return new LoginFinishedMessage();
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 23 */     return 0;
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 27 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\LoginFinishedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */