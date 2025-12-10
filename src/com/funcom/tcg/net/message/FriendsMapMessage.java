/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import com.funcom.tcg.net.Friend;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class FriendsMapMessage
/*    */   implements Message
/*    */ {
/*    */   private Map<Integer, Friend> friendsMap;
/*    */   
/*    */   public FriendsMapMessage() {}
/*    */   
/*    */   public FriendsMapMessage(Map<Integer, Friend> friendsMap) {
/* 19 */     this.friendsMap = friendsMap;
/*    */   }
/*    */   
/*    */   public FriendsMapMessage(ByteBuffer buffer) {
/* 23 */     this.friendsMap = readFriendsMap(buffer);
/*    */   }
/*    */   
/*    */   private Map<Integer, Friend> readFriendsMap(ByteBuffer buffer) {
/* 27 */     this.friendsMap = new HashMap<Integer, Friend>();
/* 28 */     int size = MessageUtils.readInt(buffer);
/* 29 */     for (int i = 0; i < size; i++) {
/* 30 */       int id = MessageUtils.readInt(buffer);
/* 31 */       String nick = MessageUtils.readStr(buffer);
/* 32 */       Boolean isBlocked = MessageUtils.readBoolean(buffer);
/* 33 */       Boolean isOnline = MessageUtils.readBoolean(buffer);
/* 34 */       this.friendsMap.put(Integer.valueOf(id), new Friend(nick, isBlocked, isOnline));
/*    */     } 
/* 36 */     return this.friendsMap;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 41 */     return 61;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 46 */     return new FriendsMapMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 51 */     int messageSize = 0;
/* 52 */     messageSize += MessageUtils.getSizeInt();
/* 53 */     for (Friend friend : this.friendsMap.values()) {
/* 54 */       messageSize += MessageUtils.getSizeInt();
/* 55 */       messageSize += MessageUtils.getSizeStr(friend.getNickname());
/* 56 */       messageSize += MessageUtils.getSizeBoolean();
/* 57 */       messageSize += MessageUtils.getSizeBoolean();
/*    */     } 
/*    */     
/* 60 */     return messageSize;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 65 */     MessageUtils.writeInt(buffer, this.friendsMap.size());
/* 66 */     for (Integer integer : this.friendsMap.keySet()) {
/* 67 */       Friend friend = this.friendsMap.get(integer);
/* 68 */       MessageUtils.writeInt(buffer, integer.intValue());
/* 69 */       MessageUtils.writeStr(buffer, friend.getNickname());
/* 70 */       MessageUtils.writeBoolean(buffer, friend.isBlocked());
/* 71 */       MessageUtils.writeBoolean(buffer, friend.isOnline());
/*    */     } 
/* 73 */     return buffer;
/*    */   }
/*    */   
/*    */   public Map<Integer, Friend> getFriendsMap() {
/* 77 */     return this.friendsMap;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\FriendsMapMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */