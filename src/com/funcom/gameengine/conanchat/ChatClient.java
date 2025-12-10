/*     */ package com.funcom.gameengine.conanchat;
/*     */ 
/*     */ import com.funcom.gameengine.conanchat.handlers.NotHandledException;
/*     */ import com.funcom.gameengine.conanchat.packets2.ChatMessage;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChatClient
/*     */   extends Shutdownable
/*     */ {
/*  21 */   private static final Logger LOGGER = Logger.getLogger(ChatClient.class.getName());
/*     */   
/*     */   private SocketChannel channel;
/*     */   private IncomingQueue incomingQueue;
/*     */   private OutgoingQueue outgoingQueue;
/*     */   private DefaultChatUser chatUser;
/*     */   private String hostname;
/*     */   private int port;
/*     */   
/*     */   public ChatClient(DefaultChatUser chatUser, String hostname, int port) {
/*  31 */     this.hostname = hostname;
/*  32 */     this.port = port;
/*     */     
/*  34 */     this.incomingQueue = new IncomingQueue();
/*  35 */     this.outgoingQueue = new OutgoingQueue();
/*     */     
/*  37 */     chatUser.setOutgoingQueue(this.outgoingQueue);
/*     */     
/*  39 */     this.chatUser = chatUser;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void establishConnection() throws IOException {
/*  44 */     if (this.channel != null) {
/*     */       try {
/*  46 */         this.channel.close();
/*  47 */       } catch (IOException e) {
/*  48 */         LOGGER.log((Priority)Level.WARN, "IOException on closing old channel", e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  54 */       this.channel = SocketChannel.open(new InetSocketAddress(this.hostname, this.port));
/*  55 */       this.channel.configureBlocking(false);
/*     */       
/*  57 */       this.incomingQueue.setChannel(this.channel);
/*  58 */       this.outgoingQueue.setChannel(this.channel);
/*     */     }
/*  60 */     catch (IOException e) {
/*  61 */       LOGGER.log((Priority)Level.WARN, "Exception on opening channel", e);
/*  62 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void update() throws IOException {
/*  67 */     processIncomingMessages();
/*  68 */     processOutgoingMessages();
/*     */   }
/*     */   
/*     */   public void reconnect() throws IOException {
/*  72 */     LOGGER.error("Reconnecting with the chat.");
/*     */     try {
/*  74 */       establishConnection();
/*  75 */     } catch (IOException e) {
/*     */       
/*  77 */       LOGGER.log((Priority)Level.ERROR, "Failed reconnection, try again later...", e);
/*  78 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void processIncomingMessages() throws IOException {
/*  85 */     this.incomingQueue.queueIncoming();
/*     */     
/*     */     ChatMessage message;
/*  88 */     while ((message = this.incomingQueue.getNextMessage()) != null) {
/*     */       try {
/*  90 */         this.chatUser.messageReceived(message);
/*  91 */       } catch (NotHandledException e) {
/*  92 */         LOGGER.error("FAIL to handle message: " + message);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processOutgoingMessages() throws IOException {
/*  98 */     this.outgoingQueue.sendQueued();
/*     */   }
/*     */   
/*     */   public DefaultChatUser getChatUser() {
/* 102 */     checkState();
/* 103 */     return this.chatUser;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void cleanup() {
/* 108 */     this.outgoingQueue.shutdown();
/* 109 */     this.incomingQueue.shutdown();
/*     */     
/* 111 */     if (this.channel != null)
/*     */       try {
/* 113 */         this.channel.close();
/* 114 */       } catch (IOException e) {
/* 115 */         LOGGER.log((Priority)Level.ERROR, "IOException on closing chat socket channel", e);
/*     */       }  
/*     */   }
/*     */   
/*     */   public void wipeQutgoingQueue() {
/* 120 */     this.outgoingQueue.wipe();
/*     */   }
/*     */   
/*     */   public void ping() {
/* 124 */     this.chatUser.messagePing();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\ChatClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */