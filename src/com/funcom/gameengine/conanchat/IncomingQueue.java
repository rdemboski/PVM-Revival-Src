/*    */ package com.funcom.gameengine.conanchat;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Endianess;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Integer16;
/*    */ import com.funcom.gameengine.conanchat.packets2.ChatMessage;
/*    */ import com.funcom.server.common.UnknownMessageTypeException;
/*    */ import java.io.IOException;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.channels.SocketChannel;
/*    */ import java.util.LinkedList;
/*    */ import java.util.Queue;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IncomingQueue
/*    */   extends Shutdownable
/*    */ {
/* 21 */   private static final Logger LOGGER = Logger.getLogger(IncomingQueue.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 29 */   private ByteBuffer buffer = ByteBuffer.allocate(1024);
/*    */   private SocketChannel socketChannel;
/* 31 */   private ConanChatMessageFactory messageFactory = new ConanChatMessageFactory();
/* 32 */   private Queue<ChatMessage> pendingMessages = new LinkedList<ChatMessage>();
/*    */ 
/*    */   
/*    */   public void queueIncoming() throws IOException {
/* 36 */     if (this.socketChannel == null) {
/* 37 */       throw new IllegalStateException("Uninitialized object: socketChannel = null");
/*    */     }
/* 39 */     checkState();
/* 40 */     if ((this.socketChannel.validOps() & 0x1) == 1) {
/* 41 */       int readBytes = this.socketChannel.read(this.buffer);
/*    */       
/* 43 */       if (readBytes <= 0) {
/*    */         return;
/*    */       }
/* 46 */       int pos = this.buffer.position();
/* 47 */       this.buffer.flip();
/*    */       
/*    */       try {
/* 50 */         while (this.buffer.position() < this.buffer.limit()) {
/* 51 */           int tempPos = this.buffer.position();
/* 52 */           if (tempPos + 4 >= this.buffer.capacity()) {
/* 53 */             this.buffer.limit(pos);
/* 54 */             this.buffer.compact();
/*    */             return;
/*    */           } 
/* 57 */           short messageId = (short)(new Integer16(this.buffer, Endianess.BIG_ENDIAN)).getIntValue();
/* 58 */           int messageSize = (new Integer16(this.buffer, Endianess.BIG_ENDIAN)).getIntValue();
/* 59 */           this.buffer.position(tempPos);
/* 60 */           if (this.buffer.position() + messageSize + 4 >= this.buffer.capacity()) {
/* 61 */             this.buffer.limit(pos);
/* 62 */             this.buffer.compact();
/*    */             
/*    */             return;
/*    */           } 
/* 66 */           ChatMessage chatMessage = this.messageFactory.toMessage(this.buffer);
/* 67 */           this.pendingMessages.offer(chatMessage);
/*    */         } 
/* 69 */         this.buffer.clear();
/* 70 */       } catch (UnknownMessageTypeException e) {
/* 71 */         LOGGER.error("Unknown message from server, ID: " + e.getMsgID() + ", buffer: " + e.getBuffer(), (Throwable)e);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public ChatMessage getNextMessage() {
/* 77 */     checkState();
/* 78 */     return this.pendingMessages.poll();
/*    */   }
/*    */   
/*    */   public void setChannel(SocketChannel channel) {
/* 82 */     this.socketChannel = channel;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\IncomingQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */