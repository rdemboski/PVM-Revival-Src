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
/*    */ public class RemoveItemMessage
/*    */   implements Message
/*    */ {
/*    */   private int id;
/*    */   private int type;
/*    */   private int containerId;
/*    */   private int slotId;
/*    */   
/*    */   public RemoveItemMessage(int id, int type, int containerId, int slotId) {
/* 20 */     this.id = id;
/* 21 */     this.type = type;
/* 22 */     this.containerId = containerId;
/* 23 */     this.slotId = slotId;
/*    */   }
/*    */   
/*    */   public RemoveItemMessage(ByteBuffer buffer) {
/* 27 */     this.id = MessageUtils.readInt(buffer);
/* 28 */     this.type = MessageUtils.readInt(buffer);
/* 29 */     this.containerId = MessageUtils.readInt(buffer);
/* 30 */     this.slotId = MessageUtils.readInt(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public RemoveItemMessage() {}
/*    */   
/*    */   public short getMessageType() {
/* 37 */     return 25;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 41 */     return new RemoveItemMessage(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 45 */     return MessageUtils.getSizeInt() * 4;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 49 */     return this.id;
/*    */   }
/*    */   
/*    */   public int getType() {
/* 53 */     return this.type;
/*    */   }
/*    */   
/*    */   public int getContainerId() {
/* 57 */     return this.containerId;
/*    */   }
/*    */   
/*    */   public int getSlotId() {
/* 61 */     return this.slotId;
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 65 */     MessageUtils.writeInt(buffer, this.id);
/* 66 */     MessageUtils.writeInt(buffer, this.type);
/* 67 */     MessageUtils.writeInt(buffer, this.containerId);
/* 68 */     MessageUtils.writeInt(buffer, this.slotId);
/* 69 */     return buffer;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 73 */     StringBuffer sb = new StringBuffer();
/* 74 */     sb.append("[Id:").append(this.id).append(", type:").append(this.type).append(", containerId:").append(this.containerId).append(", slotId:").append(this.slotId).append("]");
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 79 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\RemoveItemMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */