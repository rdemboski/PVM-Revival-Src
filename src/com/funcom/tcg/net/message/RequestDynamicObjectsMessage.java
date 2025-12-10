/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RequestDynamicObjectsMessage
/*    */   implements Message
/*    */ {
/*    */   private int id;
/*    */   private List<Integer> friendIds;
/*    */   private String mapName;
/*    */   
/*    */   public RequestDynamicObjectsMessage() {}
/*    */   
/*    */   public RequestDynamicObjectsMessage(int id, List<Integer> friendIds, String mapName) {
/* 25 */     this.id = id;
/* 26 */     this.friendIds = friendIds;
/* 27 */     this.mapName = mapName;
/*    */   }
/*    */   
/*    */   public RequestDynamicObjectsMessage(ByteBuffer buffer) {
/* 31 */     this.id = MessageUtils.readInt(buffer);
/* 32 */     this.mapName = MessageUtils.readStr(buffer);
/* 33 */     this.friendIds = readFriendIds(buffer);
/*    */   }
/*    */   
/*    */   private List<Integer> readFriendIds(ByteBuffer buffer) {
/* 37 */     List<Integer> friendIds = new ArrayList<Integer>();
/* 38 */     int listSize = MessageUtils.readInt(buffer);
/* 39 */     for (int i = 0; i < listSize; i++) {
/* 40 */       friendIds.add(Integer.valueOf(MessageUtils.readInt(buffer)));
/*    */     }
/* 42 */     return friendIds;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 47 */     return 34;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 52 */     return new RequestDynamicObjectsMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 57 */     return MessageUtils.getSizeInt() + getFriendIdListSize() + MessageUtils.getSizeStr(this.mapName);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 62 */     MessageUtils.writeInt(buffer, this.id);
/* 63 */     MessageUtils.writeStr(buffer, this.mapName);
/* 64 */     writeFriendIdList(buffer, this.friendIds);
/* 65 */     return buffer;
/*    */   }
/*    */   
/*    */   private void writeFriendIdList(ByteBuffer buffer, List<Integer> friendIds) {
/* 69 */     MessageUtils.writeInt(buffer, friendIds.size());
/* 70 */     for (Integer friendId : friendIds) {
/* 71 */       MessageUtils.writeInt(buffer, friendId.intValue());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 77 */     return "RequestDynamicObjectsMessage{id=" + this.id + ", friendIds=" + this.friendIds + '}';
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getFriendIdListSize() {
/* 84 */     int size = MessageUtils.getSizeInt();
/* 85 */     for (Integer friendId : this.friendIds) {
/* 86 */       size += MessageUtils.getSizeInt();
/*    */     }
/* 88 */     return size;
/*    */   }
/*    */   
/*    */   public List<Integer> getFriendIdList() {
/* 92 */     return this.friendIds;
/*    */   }
/*    */   
/*    */   public String getMapName() {
/* 96 */     return this.mapName;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\RequestDynamicObjectsMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */