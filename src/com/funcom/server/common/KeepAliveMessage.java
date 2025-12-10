/*    */ package com.funcom.server.common;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ public class KeepAliveMessage
/*    */   implements Message
/*    */ {
/*  9 */   public static final KeepAliveMessage INSTANCE = new KeepAliveMessage();
/*    */   
/*    */   public short getMessageType() {
/* 12 */     return -3;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 16 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 20 */     return 0;
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 24 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\common\KeepAliveMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */