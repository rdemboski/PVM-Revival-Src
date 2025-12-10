/*    */ package com.funcom.commons.chat;
/*    */ 
/*    */ import com.funcom.tcg.net2.message.chat.ChatMessage;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.util.HashSet;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class ChatMock
/*    */ {
/*    */   private String[] sentences;
/*    */   private Random random;
/*    */   private ChannelDemultiplexer channelDemultiplexer;
/*    */   private boolean running;
/*    */   
/*    */   public ChatMock(ChannelDemultiplexer channelDemultiplexer) {
/* 20 */     this.channelDemultiplexer = channelDemultiplexer;
/* 21 */     this.random = new Random();
/* 22 */     createSentenceSet();
/*    */   }
/*    */   
/*    */   public void sayRandom() {
/* 26 */     ChatMessage cm = new ChatMessage(this.random.nextInt(UserService.instance().count()), ((ChatChannel)this.channelDemultiplexer.iterator().next()).getId(), this.sentences[this.random.nextInt(this.sentences.length)]);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 31 */     this.channelDemultiplexer.pushMessage(cm);
/*    */   }
/*    */   
/*    */   public void sayUser(ChatUser chatUser) {
/* 35 */     ChatMessage cm = new ChatMessage(this.random.nextInt(chatUser.getId()), ((ChatChannel)this.channelDemultiplexer.iterator().next()).getId(), this.sentences[this.random.nextInt(this.sentences.length)]);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 40 */     this.channelDemultiplexer.pushMessage(cm);
/*    */   }
/*    */   
/*    */   public void startFeed() {
/* 44 */     Thread t = new Thread(new Runnable() {
/*    */           public void run() {
/* 46 */             while (ChatMock.this.running) {
/*    */               try {
/* 48 */                 Thread.sleep(((ChatMock.this.random.nextInt(6) + 2) * 500));
/* 49 */                 ChatMock.this.sayRandom();
/* 50 */               } catch (InterruptedException e) {}
/*    */             } 
/*    */           }
/*    */         });
/*    */ 
/*    */     
/* 56 */     this.running = true;
/* 57 */     t.setPriority(1);
/* 58 */     t.start();
/*    */   }
/*    */   
/*    */   public void stopFeed() {
/* 62 */     this.running = false;
/*    */   }
/*    */   
/*    */   private void createSentenceSet() {
/* 66 */     HashSet<String> sSet = new HashSet<String>();
/* 67 */     InputStream stream = ClassLoader.getSystemResourceAsStream("com/funcom/tcg/chat/lines.txt");
/* 68 */     BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
/*    */     try {
/* 70 */       while (reader.ready())
/* 71 */         sSet.add(reader.readLine()); 
/* 72 */     } catch (IOException e) {
/* 73 */       throw new RuntimeException(e);
/*    */     } finally {
/* 75 */       this.sentences = (String[])sSet.toArray((Object[])new String[sSet.size()]);
/*    */       try {
/* 77 */         reader.close();
/* 78 */       } catch (IOException e) {
/* 79 */         throw new RuntimeException(e);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\chat\ChatMock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */