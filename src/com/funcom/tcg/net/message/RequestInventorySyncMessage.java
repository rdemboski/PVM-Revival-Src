/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RequestInventorySyncMessage
/*    */   implements Message
/*    */ {
/*    */   private static final short TYPE_ID = 20;
/* 15 */   private static final int MSG_SIZE = MessageUtils.getSizeInt() + MessageUtils.getSizeInt() + MessageUtils.getSizeInt() + MessageUtils.getSizeInt();
/*    */   
/*    */   private int inventoryType;
/*    */   private int inventoryId;
/*    */   private int playerIdentity;
/*    */   private int rpgCreatureType;
/*    */   
/*    */   public RequestInventorySyncMessage(int inventoryType, int inventoryId, int playerIdentity, int rpgCreatureType) {
/* 23 */     this.inventoryType = inventoryType;
/* 24 */     this.inventoryId = inventoryId;
/* 25 */     this.playerIdentity = playerIdentity;
/* 26 */     this.rpgCreatureType = rpgCreatureType;
/*    */   }
/*    */ 
/*    */   
/*    */   public RequestInventorySyncMessage() {}
/*    */   
/*    */   public RequestInventorySyncMessage(ByteBuffer byteBuffer) {
/* 33 */     this.inventoryType = MessageUtils.readInt(byteBuffer);
/* 34 */     this.inventoryId = MessageUtils.readInt(byteBuffer);
/* 35 */     this.playerIdentity = MessageUtils.readInt(byteBuffer);
/* 36 */     this.rpgCreatureType = MessageUtils.readInt(byteBuffer);
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 40 */     return 20;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 44 */     return new RequestInventorySyncMessage(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 48 */     return MSG_SIZE;
/*    */   }
/*    */   
/*    */   public int getInventoryType() {
/* 52 */     return this.inventoryType;
/*    */   }
/*    */   
/*    */   public int getInventoryId() {
/* 56 */     return this.inventoryId;
/*    */   }
/*    */   
/*    */   public int getPlayerIdentity() {
/* 60 */     return this.playerIdentity;
/*    */   }
/*    */   
/*    */   public int getRpgCreatureType() {
/* 64 */     return this.rpgCreatureType;
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer byteBuffer) {
/* 68 */     MessageUtils.writeInt(byteBuffer, this.inventoryType);
/* 69 */     MessageUtils.writeInt(byteBuffer, this.inventoryId);
/* 70 */     MessageUtils.writeInt(byteBuffer, this.playerIdentity);
/* 71 */     MessageUtils.writeInt(byteBuffer, this.rpgCreatureType);
/* 72 */     return byteBuffer;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 77 */     StringBuffer sb = new StringBuffer();
/* 78 */     sb.append("[").append("itemContainer=").append(this.inventoryType).append(",inventoryId=").append(this.inventoryId).append(",playerIdentity=").append(this.playerIdentity).append(",rpgCreatureType=").append(this.rpgCreatureType).append("]");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 85 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\RequestInventorySyncMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */