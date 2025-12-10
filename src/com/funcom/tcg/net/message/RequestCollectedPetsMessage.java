/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RequestCollectedPetsMessage
/*    */   implements Message
/*    */ {
/*    */   public short getMessageType() {
/* 13 */     return 208;
/*    */   }
/*    */   
/*    */   public RequestCollectedPetsMessage toMessage(ByteBuffer buffer) {
/* 17 */     return new RequestCollectedPetsMessage();
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 21 */     return 0;
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 25 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\RequestCollectedPetsMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */