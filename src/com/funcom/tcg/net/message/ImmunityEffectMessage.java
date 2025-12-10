/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class ImmunityEffectMessage
/*    */   implements Message
/*    */ {
/*    */   private volatile int targetId;
/*    */   
/*    */   public ImmunityEffectMessage() {}
/*    */   
/*    */   public ImmunityEffectMessage(int targetId) {
/* 14 */     this.targetId = targetId;
/*    */   }
/*    */   
/*    */   public ImmunityEffectMessage(ByteBuffer buffer) {
/* 18 */     this.targetId = buffer.getInt();
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 22 */     return 4;
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 26 */     buffer.putInt(this.targetId);
/* 27 */     return buffer;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 31 */     return new ImmunityEffectMessage(buffer);
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 35 */     return 240;
/*    */   }
/*    */   
/*    */   public int getTargetId() {
/* 39 */     return this.targetId;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\ImmunityEffectMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */