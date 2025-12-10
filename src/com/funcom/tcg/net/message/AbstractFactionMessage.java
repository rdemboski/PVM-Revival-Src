/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import com.funcom.tcg.rpg.DuelFaction;
/*    */ import com.funcom.tcg.rpg.Faction;
/*    */ import com.funcom.tcg.rpg.TCGFaction;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractFactionMessage
/*    */ {
/*    */   public int getSizeFaction(Faction faction) {
/* 18 */     return (faction instanceof DuelFaction) ? (MessageUtils.getSizeBoolean() + 3 * MessageUtils.getSizeInt()) : (MessageUtils.getSizeBoolean() + MessageUtils.getSizeInt());
/*    */   }
/*    */   
/*    */   public void writeFaction(ByteBuffer buffer, Faction faction) {
/* 22 */     if (faction instanceof DuelFaction) {
/* 23 */       DuelFaction duelFaction = (DuelFaction)faction;
/* 24 */       MessageUtils.writeBoolean(buffer, Boolean.valueOf(true));
/* 25 */       MessageUtils.writeInt(buffer, faction.getBaseFaction().ordinal());
/* 26 */       MessageUtils.writeInt(buffer, duelFaction.getFightGroup());
/* 27 */       MessageUtils.writeInt(buffer, duelFaction.getTeam());
/*    */     } else {
/* 29 */       MessageUtils.writeBoolean(buffer, Boolean.valueOf(false));
/* 30 */       MessageUtils.writeInt(buffer, faction.getBaseFaction().ordinal());
/*    */     } 
/*    */   }
/*    */   
/*    */   public Faction readFaction(ByteBuffer buffer) {
/* 35 */     boolean duelFaction = MessageUtils.readBoolean(buffer).booleanValue();
/* 36 */     if (duelFaction) {
/* 37 */       int i = MessageUtils.readInt(buffer);
/* 38 */       int fightGroup = MessageUtils.readInt(buffer);
/* 39 */       int team = MessageUtils.readInt(buffer);
/* 40 */       return (Faction)new DuelFaction(TCGFaction.values()[i], fightGroup, team);
/*    */     } 
/* 42 */     int faction = MessageUtils.readInt(buffer);
/* 43 */     return new Faction(TCGFaction.values()[faction]);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\AbstractFactionMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */