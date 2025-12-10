/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NewItemCollectedMessage
/*    */   implements Message
/*    */ {
/*    */   private String itemId;
/*    */   private int tier;
/*    */   
/*    */   public NewItemCollectedMessage() {}
/*    */   
/*    */   public NewItemCollectedMessage(String itemId, int tier) {
/* 19 */     this.itemId = itemId;
/* 20 */     this.tier = tier;
/*    */   }
/*    */   
/*    */   public NewItemCollectedMessage(ByteBuffer buffer) {
/* 24 */     this.itemId = MessageUtils.readStr(buffer);
/* 25 */     this.tier = MessageUtils.readInt(buffer);
/*    */   }
/*    */   
/*    */   public String getItemId() {
/* 29 */     return this.itemId;
/*    */   }
/*    */   
/*    */   public int getTier() {
/* 33 */     return this.tier;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 38 */     return 237;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 43 */     return new NewItemCollectedMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 48 */     return MessageUtils.getSizeStr(this.itemId) + MessageUtils.getSizeInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 53 */     MessageUtils.writeStr(buffer, this.itemId);
/* 54 */     MessageUtils.writeInt(buffer, this.tier);
/* 55 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\NewItemCollectedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */