/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JoinPvpQueueMessage
/*    */   implements Message
/*    */ {
/*    */   private String map;
/*    */   
/*    */   public JoinPvpQueueMessage() {}
/*    */   
/*    */   public JoinPvpQueueMessage(String map) {
/* 18 */     this.map = map;
/*    */   }
/*    */   
/*    */   public JoinPvpQueueMessage(ByteBuffer buffer) {
/* 22 */     this.map = MessageUtils.readStr(buffer);
/*    */   }
/*    */   
/*    */   public String getMap() {
/* 26 */     return this.map;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 31 */     return 255;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 36 */     return new JoinPvpQueueMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 41 */     return MessageUtils.getSizeStr(this.map);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 46 */     MessageUtils.writeStr(buffer, this.map);
/* 47 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\JoinPvpQueueMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */