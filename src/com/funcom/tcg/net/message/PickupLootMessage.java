/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class PickupLootMessage
/*    */   implements Message
/*    */ {
/*    */   private int lootId;
/*    */   
/*    */   public PickupLootMessage() {}
/*    */   
/*    */   public PickupLootMessage(int lootId) {
/* 14 */     this.lootId = lootId;
/*    */   }
/*    */   
/*    */   public PickupLootMessage(ByteBuffer buffer) {
/* 18 */     this.lootId = buffer.getInt();
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 22 */     return 4;
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 26 */     buffer.putInt(this.lootId);
/* 27 */     return buffer;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 31 */     return new PickupLootMessage(buffer);
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 35 */     return 233;
/*    */   }
/*    */   
/*    */   public int getLootId() {
/* 39 */     return this.lootId;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\PickupLootMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */