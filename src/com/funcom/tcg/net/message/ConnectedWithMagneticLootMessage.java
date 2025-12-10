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
/*    */ public class ConnectedWithMagneticLootMessage
/*    */   implements Message
/*    */ {
/*    */   private int itemID;
/*    */   
/*    */   public ConnectedWithMagneticLootMessage() {}
/*    */   
/*    */   public ConnectedWithMagneticLootMessage(int itemID) {
/* 19 */     this.itemID = itemID;
/*    */   }
/*    */   
/*    */   public ConnectedWithMagneticLootMessage(ByteBuffer byteBuffer) {
/* 23 */     this.itemID = MessageUtils.readInt(byteBuffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 28 */     return 231;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 33 */     return new ConnectedWithMagneticLootMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 38 */     return MessageUtils.getSizeInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 43 */     MessageUtils.writeInt(buffer, this.itemID);
/* 44 */     return buffer;
/*    */   }
/*    */   
/*    */   public int getItemID() {
/* 48 */     return this.itemID;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 53 */     StringBuffer sb = new StringBuffer();
/* 54 */     sb.append(", itemId:").append(this.itemID).append("]");
/*    */     
/* 56 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\ConnectedWithMagneticLootMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */