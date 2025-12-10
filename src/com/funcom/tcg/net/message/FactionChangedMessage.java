/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import com.funcom.tcg.rpg.Faction;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FactionChangedMessage
/*    */   extends AbstractFactionMessage
/*    */   implements Message
/*    */ {
/*    */   private int creatureId;
/*    */   private Faction faction;
/*    */   private int creatureType;
/*    */   
/*    */   public FactionChangedMessage() {}
/*    */   
/*    */   public FactionChangedMessage(int creatureId, Faction faction, int creatureType) {
/* 21 */     this.creatureId = creatureId;
/* 22 */     this.faction = faction;
/* 23 */     this.creatureType = creatureType;
/*    */   }
/*    */   
/*    */   public FactionChangedMessage(ByteBuffer buffer) {
/* 27 */     this.creatureId = MessageUtils.readInt(buffer);
/* 28 */     this.creatureType = MessageUtils.readInt(buffer);
/* 29 */     this.faction = readFaction(buffer);
/*    */   }
/*    */   
/*    */   public int getCreatureId() {
/* 33 */     return this.creatureId;
/*    */   }
/*    */   
/*    */   public Faction getFaction() {
/* 37 */     return this.faction;
/*    */   }
/*    */   
/*    */   public int getCreatureType() {
/* 41 */     return this.creatureType;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 46 */     return 244;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 51 */     return new FactionChangedMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 56 */     return MessageUtils.getSizeInt() * 2 + getSizeFaction(this.faction);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 61 */     MessageUtils.writeInt(buffer, this.creatureId);
/* 62 */     MessageUtils.writeInt(buffer, this.creatureType);
/* 63 */     writeFaction(buffer, this.faction);
/* 64 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\FactionChangedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */