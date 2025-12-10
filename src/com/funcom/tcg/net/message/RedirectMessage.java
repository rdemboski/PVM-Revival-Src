/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RedirectMessage
/*    */   implements Message
/*    */ {
/*    */   private InetSocketAddress newServer;
/*    */   
/*    */   public RedirectMessage() {}
/*    */   
/*    */   public RedirectMessage(InetSocketAddress newServer) {
/* 20 */     this.newServer = newServer;
/*    */   }
/*    */   
/*    */   public RedirectMessage(ByteBuffer buffer) {
/* 24 */     this.newServer = MessageUtils.readInetSocketAddress(buffer);
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 28 */     return new RedirectMessage(buffer);
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 32 */     return 0;
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 36 */     return MessageUtils.getSizeInetSocketAddress(this.newServer);
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 40 */     MessageUtils.writeInetSocketAddress(buffer, this.newServer);
/*    */     
/* 42 */     return buffer;
/*    */   }
/*    */   
/*    */   public InetSocketAddress getNewServer() {
/* 46 */     return this.newServer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\RedirectMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */