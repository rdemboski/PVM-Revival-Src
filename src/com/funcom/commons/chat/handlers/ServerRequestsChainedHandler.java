/*    */ package com.funcom.commons.chat.handlers;
/*    */ 
/*    */ import com.funcom.commons.chat.ChatServer;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.channels.SelectionKey;
/*    */ 
/*    */ 
/*    */ public abstract class ServerRequestsChainedHandler
/*    */ {
/*    */   private ServerRequestsChainedHandler follower;
/*    */   
/*    */   protected ServerRequestsChainedHandler(ServerRequestsChainedHandler follower) {
/* 13 */     this.follower = follower;
/*    */   }
/*    */   
/*    */   public void handle(short messageType, SelectionKey selectionKey, ByteBuffer buffer, ChatServer server) {
/* 17 */     if (messageType != getType()) {
/* 18 */       passOn(messageType, selectionKey, buffer, server);
/*    */     } else {
/* 20 */       youreTheOne(messageType, selectionKey, buffer, server);
/*    */     } 
/*    */   }
/*    */   private void passOn(short messageType, SelectionKey selectionKey, ByteBuffer buffer, ChatServer server) {
/* 24 */     if (this.follower != null) {
/* 25 */       this.follower.handle(messageType, selectionKey, buffer, server);
/*    */     } else {
/* 27 */       throw new IllegalStateException("Unhandled! Type: " + messageType);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract void youreTheOne(short paramShort, SelectionKey paramSelectionKey, ByteBuffer paramByteBuffer, ChatServer paramChatServer);
/*    */   
/*    */   protected abstract short getType();
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\chat\handlers\ServerRequestsChainedHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */