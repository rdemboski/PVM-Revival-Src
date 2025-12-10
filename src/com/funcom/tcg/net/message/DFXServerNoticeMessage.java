/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DFXServerNoticeMessage
/*    */   implements Message
/*    */ {
/*    */   private final String message;
/*    */   
/*    */   public DFXServerNoticeMessage() {
/* 15 */     this.message = "";
/*    */   }
/*    */   
/*    */   public DFXServerNoticeMessage(String message) {
/* 19 */     this.message = message;
/*    */   }
/*    */   
/*    */   public DFXServerNoticeMessage(ByteBuffer byteBuffer) {
/* 23 */     this.message = MessageUtils.readStr(byteBuffer);
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 27 */     return this.message;
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 31 */     return 69;
/*    */   }
/*    */   
/*    */   public DFXServerNoticeMessage toMessage(ByteBuffer buffer) {
/* 35 */     return new DFXServerNoticeMessage(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 39 */     return MessageUtils.getSizeStr(this.message);
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 43 */     MessageUtils.writeStr(buffer, this.message);
/* 44 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\DFXServerNoticeMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */