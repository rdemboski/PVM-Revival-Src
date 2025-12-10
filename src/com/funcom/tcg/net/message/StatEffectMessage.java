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
/*    */ public class StatEffectMessage
/*    */   implements Message
/*    */ {
/*    */   private int targetId;
/*    */   private int effectSum;
/*    */   private short effectStatId;
/*    */   private boolean critDone;
/*    */   private int creatureType;
/*    */   
/*    */   public StatEffectMessage() {}
/*    */   
/*    */   public StatEffectMessage(int targetId, int effectSum, short effectStatId, boolean critDone, int creatureType) {
/* 23 */     this.targetId = targetId;
/* 24 */     this.effectSum = effectSum;
/* 25 */     this.effectStatId = effectStatId;
/* 26 */     this.critDone = critDone;
/* 27 */     this.creatureType = creatureType;
/*    */   }
/*    */   
/*    */   public StatEffectMessage(ByteBuffer buffer) {
/* 31 */     this.targetId = buffer.getInt();
/* 32 */     this.effectSum = buffer.getInt();
/* 33 */     this.effectStatId = buffer.getShort();
/* 34 */     this.critDone = MessageUtils.readBoolean(buffer).booleanValue();
/* 35 */     this.creatureType = MessageUtils.readInt(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 39 */     return 10 + MessageUtils.getSizeBoolean() + MessageUtils.getSizeInt();
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 43 */     buffer.putInt(this.targetId);
/* 44 */     buffer.putInt(this.effectSum);
/* 45 */     buffer.putShort(this.effectStatId);
/* 46 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.critDone));
/* 47 */     MessageUtils.writeInt(buffer, this.creatureType);
/*    */     
/* 49 */     return buffer;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 53 */     return new StatEffectMessage(buffer);
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 57 */     return 226;
/*    */   }
/*    */   
/*    */   public int getTargetId() {
/* 61 */     return this.targetId;
/*    */   }
/*    */   
/*    */   public int getEffectSum() {
/* 65 */     return this.effectSum;
/*    */   }
/*    */   
/*    */   public short getEffectStatId() {
/* 69 */     return this.effectStatId;
/*    */   }
/*    */   
/*    */   public boolean isCritDone() {
/* 73 */     return this.critDone;
/*    */   }
/*    */   
/*    */   public int getCreatureType() {
/* 77 */     return this.creatureType;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\StatEffectMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */