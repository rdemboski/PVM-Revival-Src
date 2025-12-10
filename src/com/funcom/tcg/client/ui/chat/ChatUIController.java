/*     */ package com.funcom.tcg.client.ui.chat;
/*     */ 
/*     */ import com.funcom.gameengine.GameLoop;
/*     */ import com.funcom.gameengine.Updated;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChatUIController
/*     */   implements Updated
/*     */ {
/*     */   public static final String ELM_CHAT_MESSAGE = "cm";
/*     */   public static final String ELM_FRIEND_MESSAGE = "friend";
/*     */   public static final String ATTR_CHAT_MESSAGE = "message";
/*     */   private LinkedList<String> chatHistoryList;
/*     */   private List<ChangeListener> changeListeners;
/*     */   private List<ChatBubble> chatBubbleList;
/*     */   private HashMap<String, ChatBubble> infoBubbleMap;
/*     */   
/*     */   public ChatUIController(GameLoop gameLoop) {
/*  33 */     this.chatHistoryList = new LinkedList<String>();
/*  34 */     this.changeListeners = new ArrayList<ChangeListener>();
/*  35 */     this.chatBubbleList = new ArrayList<ChatBubble>();
/*     */     
/*  37 */     this.infoBubbleMap = new HashMap<String, ChatBubble>();
/*     */     
/*  39 */     gameLoop.addToUpdateList(this);
/*     */   }
/*     */   
/*     */   private void fireChangeListener() {
/*  43 */     for (ChangeListener changeListener : this.changeListeners) {
/*  44 */       changeListener.historyChanged();
/*     */     }
/*     */   }
/*     */   
/*     */   public void addChangeListener(ChangeListener changeListener) {
/*  49 */     this.changeListeners.add(changeListener);
/*     */   }
/*     */   
/*     */   public LinkedList<Element> getSortedChildrenList(Element parent) {
/*  53 */     LinkedList<Element> childs = new LinkedList<Element>();
/*  54 */     List<Element> children = parent.getChildren();
/*  55 */     for (Element child : children) {
/*  56 */       childs.addFirst(child);
/*     */     }
/*  58 */     return childs;
/*     */   }
/*     */   
/*     */   public LinkedList<String> getChatHistoryList() {
/*  62 */     return this.chatHistoryList;
/*     */   }
/*     */   
/*     */   public void createChatBubble(String output, String history, PropNode player, boolean info) {
/*  66 */     if (info && 
/*  67 */       this.infoBubbleMap.containsKey(output)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     ChatBubble bubble = new ChatBubble(player, output, this, info);
/*  87 */     if (info) this.infoBubbleMap.put(output, bubble); 
/*  88 */     this.chatHistoryList.addFirst(history);
/*  89 */     fireChangeListener();
/*     */   }
/*     */   
/*     */   public void createChatBubble(String output, String history, PropNode player) {
/*  93 */     new ChatBubble(player, output, this, false);
/*  94 */     this.chatHistoryList.addFirst(history);
/*  95 */     fireChangeListener();
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(float time) {
/* 100 */     Iterator<ChatBubble> bubbleIterator = this.chatBubbleList.iterator();
/* 101 */     while (bubbleIterator.hasNext()) {
/* 102 */       ChatBubble chatBubble = bubbleIterator.next();
/* 103 */       if (chatBubble.isFinished()) {
/* 104 */         bubbleIterator.remove(); continue;
/*     */       } 
/* 106 */       chatBubble.update(time);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addToUpdateList(ChatBubble chatBubble) {
/* 111 */     this.chatBubbleList.add(chatBubble);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashMap<String, ChatBubble> getInfoBubbleMap() {
/* 119 */     return this.infoBubbleMap;
/*     */   }
/*     */   
/*     */   public static interface ChangeListener {
/*     */     void historyChanged();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\chat\ChatUIController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */