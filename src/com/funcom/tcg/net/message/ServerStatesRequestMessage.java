/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerStatesRequestMessage
/*    */   implements Message
/*    */ {
/*    */   public short getMessageType() {
/* 14 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 19 */     return new ServerStatesRequestMessage();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 24 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 29 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\ServerStatesRequestMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */