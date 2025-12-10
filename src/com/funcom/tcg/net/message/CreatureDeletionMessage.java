/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.rpgengine2.combat.Element;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CreatureDeletionMessage
/*    */   implements Message
/*    */ {
/*    */   private static final short TYPE_ID = 12;
/*    */   private int creatureId;
/*    */   private int creatureType;
/*    */   private boolean dead;
/*    */   private Element element;
/*    */   private String impact;
/*    */   
/*    */   public CreatureDeletionMessage() {}
/*    */   
/*    */   public CreatureDeletionMessage(int creatureId, int creatureType, boolean dead, Element element, String impact) {
/* 26 */     this.creatureId = creatureId;
/* 27 */     this.creatureType = creatureType;
/* 28 */     this.dead = dead;
/* 29 */     this.element = element;
/* 30 */     this.impact = impact;
/*    */   }
/*    */   
/*    */   public int getCreatureId() {
/* 34 */     return this.creatureId;
/*    */   }
/*    */   
/*    */   public int getCreatureType() {
/* 38 */     return this.creatureType;
/*    */   }
/*    */   
/*    */   public boolean isDead() {
/* 42 */     return this.dead;
/*    */   }
/*    */   
/*    */   public Element getElement() {
/* 46 */     return this.element;
/*    */   }
/*    */   
/*    */   public String getImpact() {
/* 50 */     return this.impact;
/*    */   }
/*    */   
/*    */   public CreatureDeletionMessage(ByteBuffer buffer) {
/* 54 */     this.creatureId = MessageUtils.readInt(buffer);
/* 55 */     this.creatureType = MessageUtils.readInt(buffer);
/* 56 */     this.dead = MessageUtils.readBoolean(buffer).booleanValue();
/* 57 */     this.element = Element.values()[MessageUtils.readInt(buffer)];
/* 58 */     this.impact = MessageUtils.readStr(buffer);
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 62 */     return new CreatureDeletionMessage(buffer);
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 66 */     return 12;
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 70 */     return MessageUtils.getSizeInt() * 3 + MessageUtils.getSizeBoolean() + MessageUtils.getSizeStr(this.impact);
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 74 */     buffer.putInt(this.creatureId);
/* 75 */     buffer.putInt(this.creatureType);
/* 76 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.dead));
/* 77 */     buffer.putInt(this.element.ordinal());
/* 78 */     MessageUtils.writeStr(buffer, this.impact);
/* 79 */     return buffer;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 83 */     return getClass().getName() + "[id=" + this.creatureId + ",creatureType=" + this.creatureType + ",dead=" + this.dead + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\CreatureDeletionMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */