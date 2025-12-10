/*    */ package com.funcom.tcg.net2.message.chat;
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
/*    */ public class ListChannelsRequest
/*    */   implements Message
/*    */ {
/*    */   private static final short TYPE_ID = 115;
/*    */   private int userId;
/*    */   
/*    */   public ListChannelsRequest() {}
/*    */   
/*    */   public ListChannelsRequest(int userId) {
/* 22 */     this.userId = userId;
/*    */   }
/*    */   
/*    */   public ListChannelsRequest(ByteBuffer buffer) {
/* 26 */     this(buffer.getInt());
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 30 */     return new ListChannelsRequest(buffer);
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 34 */     return 115;
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 38 */     return MessageUtils.getSizeInt();
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 42 */     buffer.putInt(getUserId());
/* 43 */     return buffer;
/*    */   }
/*    */   
/*    */   public int getUserId() {
/* 47 */     return this.userId;
/*    */   }
/*    */   
/*    */   public boolean equals(Object o) {
/* 51 */     if (this == o) return true; 
/* 52 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 54 */     ListChannelsRequest that = (ListChannelsRequest)o;
/*    */     
/* 56 */     if (this.userId != that.userId) return false;
/*    */     
/* 58 */     return true;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 62 */     return this.userId;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\tcg\net2\message\chat\ListChannelsRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */