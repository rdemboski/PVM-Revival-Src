/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TryToUseItemMessage
/*    */   implements Message
/*    */ {
/*    */   private AutoUseItemMessage.Type type;
/*    */   private int slot;
/*    */   
/*    */   public TryToUseItemMessage() {}
/*    */   
/*    */   public TryToUseItemMessage(AutoUseItemMessage.Type type, int slot) {
/* 19 */     this.type = type;
/* 20 */     this.slot = slot;
/*    */   }
/*    */   
/*    */   public TryToUseItemMessage(ByteBuffer buffer) {
/* 24 */     this.slot = buffer.getInt();
/* 25 */     byte typeId = buffer.get();
/* 26 */     this.type = AutoUseItemMessage.Type.fromId(typeId);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 31 */     return 230;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 36 */     return new TryToUseItemMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 41 */     return MessageUtils.getSizeInt() + 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 46 */     buffer.putInt(this.slot);
/* 47 */     buffer.put(this.type.getId());
/* 48 */     return buffer;
/*    */   }
/*    */   
/*    */   public AutoUseItemMessage.Type getType() {
/* 52 */     return this.type;
/*    */   }
/*    */   
/*    */   public int getSlot() {
/* 56 */     return this.slot;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\TryToUseItemMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */