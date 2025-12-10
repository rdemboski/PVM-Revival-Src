/*     */ package com.funcom.tcg.client.ui.friend;
/*     */ import com.funcom.gameengine.view.DfxTextWindowManager;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.ui.chat.ChatListener;
/*     */ import com.funcom.tcg.net.Friend;
/*     */ import com.funcom.tcg.net.message.UpdateFriendsMessage;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class TcgFriendModel implements FriendModel {
/*     */   private ChatListener chatListener;
/*     */   
/*     */   public TcgFriendModel(ChatListener chatListener) {
/*  19 */     this.chatListener = chatListener;
/*     */     
/*  21 */     this.friendList = new HashMap<Integer, Friend>();
/*  22 */     this.changeListeners = new ArrayList<FriendModel.ChangeListener>();
/*     */   }
/*     */   private Map<Integer, Friend> friendList; private List<FriendModel.ChangeListener> changeListeners;
/*     */   
/*     */   public String getLabelText() {
/*  27 */     return TcgGame.getLocalizedText("addFriendWindow.label.text", new String[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRequestFriendButtonText() {
/*  32 */     return TcgGame.getLocalizedText("addFriendWindow.button.request.text", new String[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAcceptButtonText() {
/*  37 */     return TcgGame.getLocalizedText("addFriendWindow.button.accept.text", new String[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCancelButtonText() {
/*  42 */     return TcgGame.getLocalizedText("addFriendWindow.button.cancel.text", new String[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFriendListTitle() {
/*  47 */     return TcgGame.getLocalizedText("friendslist.title", new String[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAcceptFriendLabelText() {
/*  52 */     return TcgGame.getLocalizedText("acceptFriendWindow.label", new String[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void searchPlayerResult(int id, String nickname) {
/*  57 */     fireSearchResult(id, nickname);
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendFriendRequestToPlayer(Integer friendId) {
/*  62 */     this.chatListener.sendFriendRequest(friendId.intValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean searchPlayer(String nickname) {
/*  67 */     return this.chatListener.searchPlayerRequest(nickname);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChatListener getChatMessageListener() {
/*  72 */     return this.chatListener;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addIdToFriendList(int friendId, boolean blocked) {
/*  77 */     Set<Integer> listFriendsId = this.friendList.keySet();
/*  78 */     if (!listFriendsId.contains(Integer.valueOf(friendId))) {
/*     */       
/*  80 */       this.friendList.put(Integer.valueOf(friendId), null);
/*  81 */       updateFriendsToServer(friendId, false, blocked);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void blockPlayer(int friendId) {
/*  87 */     Set<Integer> listFriendsId = this.friendList.keySet();
/*  88 */     if (!listFriendsId.contains(Integer.valueOf(friendId)))
/*     */     {
/*  90 */       this.friendList.put(Integer.valueOf(friendId), null);
/*     */     }
/*  92 */     updateFriendsToServer(friendId, false, true);
/*     */   }
/*     */   
/*     */   public void fireSearchResult(int playerId, String nickname) {
/*  96 */     for (FriendModel.ChangeListener changeListener : this.changeListeners) {
/*  97 */       changeListener.searchResult(playerId, nickname);
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateFriendsToServer(int friendId, boolean remove, boolean blocked) {
/* 102 */     UpdateFriendsMessage ufm = new UpdateFriendsMessage(Integer.valueOf(friendId), remove, blocked);
/*     */     try {
/* 104 */       NetworkHandler.instance().getIOHandler().send((Message)ufm);
/* 105 */     } catch (InterruptedException e) {
/* 106 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fireDfxText(int friendID) {
/* 111 */     String friendName = this.chatListener.getClientNameFromId(friendID);
/* 112 */     DfxTextWindowManager.instance().getWindow("friends").showText(TcgGame.getLocalizedText("friend.added.dfx.text", new String[0]) + " " + friendName);
/*     */   }
/*     */   
/*     */   public void fireModelChanged() {
/* 116 */     for (FriendModel.ChangeListener changeListener : this.changeListeners) {
/* 117 */       changeListener.friendsListChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addChangeListener(FriendModel.ChangeListener changeListener) {
/* 123 */     this.changeListeners.add(changeListener);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<Integer, Friend> getFriendsList() {
/* 128 */     return this.friendList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeFriend(int playerIdD) {
/* 133 */     Set<Integer> listFriendsId = this.friendList.keySet();
/* 134 */     if (listFriendsId.contains(Integer.valueOf(playerIdD))) {
/* 135 */       updateFriendsToServer(playerIdD, true, false);
/* 136 */       this.chatListener.removeFriend(playerIdD);
/* 137 */       this.chatListener.sendFriendRemove(playerIdD);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void fireDfxFriendAdded(int friendId) {
/* 143 */     fireDfxText(friendId);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\friend\TcgFriendModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */