/*    */ package com.funcom.gameengine.conanchat;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Integer16;
/*    */ import com.funcom.gameengine.conanchat.packets2.ChatMessage;
/*    */ import com.funcom.server.common.Message;
/*    */ import java.io.IOException;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.channels.SocketChannel;
/*    */ import java.util.Queue;
/*    */ import java.util.concurrent.ConcurrentLinkedQueue;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OutgoingQueue
/*    */   extends Shutdownable
/*    */ {
/* 18 */   private static final Logger LOG = Logger.getLogger(OutgoingQueue.class.getName());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   private Queue<ChatMessage> outgoingQueue = new ConcurrentLinkedQueue<ChatMessage>();
/* 25 */   private ByteBuffer outgoingBuffer = ByteBuffer.allocateDirect(1024);
/*    */   private SocketChannel socketChannel;
/*    */   
/*    */   public void sendQueued() throws IOException {
/* 29 */     if (this.socketChannel == null) {
/* 30 */       throw new IllegalStateException("Uninitialized object: socketChannel = null");
/*    */     }
/* 32 */     checkState();
/*    */     
/* 34 */     boolean pinged = false;
/* 35 */     while (!this.outgoingQueue.isEmpty()) {
/* 36 */       ChatMessage message = this.outgoingQueue.remove();
/* 37 */       if (message instanceof com.funcom.gameengine.conanchat.packets2.Ping) {
/* 38 */         if (pinged)
/*    */           continue; 
/* 40 */         pinged = true;
/*    */       } 
/*    */       
/* 43 */       LOG.debug("Sending message");
/* 44 */       this.outgoingBuffer.clear();
/* 45 */       storeId((Message)message);
/* 46 */       storeSize((Message)message);
/* 47 */       message.serialize(this.outgoingBuffer);
/* 48 */       this.outgoingBuffer.flip();
/*    */ 
/*    */       
/* 51 */       this.socketChannel.write(this.outgoingBuffer);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void storeId(Message message) {
/* 57 */     checkState();
/* 58 */     short mt = message.getMessageType();
/* 59 */     (new Integer16(mt)).toByteBuffer(this.outgoingBuffer);
/*    */   }
/*    */   
/*    */   private void storeSize(Message message) {
/* 63 */     checkState();
/* 64 */     int size = message.getSerializedSize();
/* 65 */     (new Integer16(size)).toByteBuffer(this.outgoingBuffer);
/*    */   }
/*    */   
/*    */   public void queueMessage(ChatMessage message) {
/* 69 */     checkState();
/* 70 */     this.outgoingQueue.offer(message);
/*    */   }
/*    */   
/*    */   public void setChannel(SocketChannel channel) {
/* 74 */     this.socketChannel = channel;
/*    */   }
/*    */   
/*    */   public void wipe() {
/* 78 */     this.outgoingQueue.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\OutgoingQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */