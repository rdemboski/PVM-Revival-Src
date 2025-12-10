/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class UpdateFriendsMessage
/*    */   implements Message
/*    */ {
/*    */   private Integer friendId;
/*    */   private boolean remove;
/*    */   private boolean blocked;
/*    */   
/*    */   public UpdateFriendsMessage() {}
/*    */   
/*    */   public UpdateFriendsMessage(Integer friendId, boolean remove, boolean blocked) {
/* 17 */     this.friendId = friendId;
/* 18 */     this.remove = remove;
/* 19 */     this.blocked = blocked;
/*    */   }
/*    */   
/*    */   public UpdateFriendsMessage(ByteBuffer buffer) {
/* 23 */     this.friendId = Integer.valueOf(MessageUtils.readInt(buffer));
/* 24 */     this.remove = MessageUtils.readBoolean(buffer).booleanValue();
/* 25 */     this.blocked = MessageUtils.readBoolean(buffer).booleanValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 30 */     return 53;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 35 */     return new UpdateFriendsMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 40 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeBoolean() + MessageUtils.getSizeBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 45 */     MessageUtils.writeInt(buffer, this.friendId.intValue());
/* 46 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.remove));
/* 47 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.blocked));
/* 48 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer getFriendId() {
/* 52 */     return this.friendId;
/*    */   }
/*    */   
/*    */   public boolean isRemove() {
/* 56 */     return this.remove;
/*    */   }
/*    */   
/*    */   public boolean isBlocked() {
/* 60 */     return this.blocked;
/*    */   }
/*    */   
/*    */   public void setBlocked(boolean blocked) {
/* 64 */     this.blocked = blocked;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\UpdateFriendsMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */