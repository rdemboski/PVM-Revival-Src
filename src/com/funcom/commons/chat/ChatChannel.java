/*     */ package com.funcom.commons.chat;
/*     */ 
/*     */ import com.funcom.tcg.net2.message.chat.ChatMessage;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class ChatChannel
/*     */   implements Iterable<ChatUser> {
/*     */   private static final int MAX_HISTORY_SIZE = 100;
/*     */   private int id;
/*     */   private String name;
/*     */   
/*     */   public ChatChannel(int id, String name) {
/*  17 */     this.id = id;
/*  18 */     this.name = name;
/*  19 */     this.history = new LinkedList<ChatMessage>();
/*  20 */     this.users = new HashSet<ChatUser>();
/*     */   }
/*     */   private Set<ChatUser> users; private List<ChatMessageListener> listeners; private LinkedList<ChatMessage> history;
/*     */   public void addMessageListener(ChatMessageListener listener) {
/*  24 */     if (this.listeners == null)
/*  25 */       this.listeners = new LinkedList<ChatMessageListener>(); 
/*  26 */     this.listeners.add(listener);
/*     */   }
/*     */   
/*     */   public void removeMessageListener(ChatMessageListener listener) {
/*  30 */     if (this.listeners == null)
/*     */       return; 
/*  32 */     this.listeners.remove(listener);
/*     */   }
/*     */   
/*     */   public void registerUser(ChatUser user) {
/*  36 */     this.users.add(user);
/*     */   }
/*     */   
/*     */   public void deregisterUser(ChatUser user) {
/*  40 */     this.users.remove(user);
/*     */   }
/*     */   
/*     */   public void messageReceived(ChatMessage message) {
/*  44 */     pushToHistory(message);
/*  45 */     fireMessageReceived(message);
/*     */   }
/*     */   
/*     */   private void pushToHistory(ChatMessage message) {
/*  49 */     this.history.add(message);
/*  50 */     if (this.history.size() > 100)
/*  51 */       this.history.removeFirst(); 
/*     */   }
/*     */   
/*     */   private void fireMessageReceived(ChatMessage message) {
/*  55 */     if (this.listeners != null)
/*  56 */       for (ChatMessageListener listener : this.listeners)
/*  57 */         listener.messageReceived(this, message);  
/*     */   }
/*     */   
/*     */   public String getName() {
/*  61 */     return this.name;
/*     */   }
/*     */   
/*     */   public boolean isUserRegistered(ChatUser user) {
/*  65 */     return this.users.contains(user);
/*     */   }
/*     */   
/*     */   public boolean isUserRegistered(int userId) {
/*  69 */     return (getUserForId(userId) != null);
/*     */   }
/*     */   
/*     */   public List<ChatMessage> tail(int numOfMessages) {
/*  73 */     List<ChatMessage> msgs = new LinkedList<ChatMessage>();
/*     */     
/*  75 */     Iterator<ChatMessage> dit = this.history.descendingIterator();
/*  76 */     for (int i = 0; i < numOfMessages && dit.hasNext(); i++)
/*  77 */       msgs.add(dit.next()); 
/*  78 */     return msgs;
/*     */   }
/*     */   
/*     */   public int getId() {
/*  82 */     return this.id;
/*     */   }
/*     */   
/*     */   public ChatUser getUserForId(int userId) {
/*  86 */     for (ChatUser user : this.users) {
/*  87 */       if (user.getId() == userId)
/*  88 */         return user; 
/*  89 */     }  return null;
/*     */   }
/*     */   
/*     */   public Iterator<ChatUser> iterator() {
/*  93 */     return this.users.iterator();
/*     */   }
/*     */   
/*     */   public int userCount() {
/*  97 */     return this.users.size();
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 101 */     if (this == o) return true; 
/* 102 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 104 */     ChatChannel channel = (ChatChannel)o;
/*     */     
/* 106 */     if (this.id != channel.id) return false;
/*     */     
/* 108 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 112 */     return this.id;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 116 */     return "ChatChannel{id=" + this.id + ", name='" + this.name + '\'' + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\chat\ChatChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */