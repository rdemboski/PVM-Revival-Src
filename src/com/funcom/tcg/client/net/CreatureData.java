/*    */ package com.funcom.tcg.client.net;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.rpgengine2.Stat;
/*    */ import com.funcom.rpgengine2.abilities.BuffType;
/*    */ import com.funcom.tcg.net.message.StateUpdateMessage;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CreatureData
/*    */ {
/*    */   private WorldCoordinate networkCoord;
/*    */   private float networkAngle;
/*    */   private Map<Short, Integer> stats;
/*    */   private Map<BuffType, StateUpdateMessage.BuffData> buffSlots;
/*    */   private String type;
/*    */   
/*    */   public CreatureData(WorldCoordinate networkCoord, float networkAngle) {
/* 23 */     this.networkCoord = networkCoord.clone();
/* 24 */     this.networkAngle = networkAngle;
/*    */     
/* 26 */     this.stats = new HashMap<Short, Integer>();
/* 27 */     this.buffSlots = new HashMap<BuffType, StateUpdateMessage.BuffData>();
/*    */   }
/*    */   
/*    */   public WorldCoordinate getNetworkCoord() {
/* 31 */     return this.networkCoord;
/*    */   }
/*    */   
/*    */   public float getNetworkAngle() {
/* 35 */     return this.networkAngle;
/*    */   }
/*    */   
/*    */   public void updateStats(Collection<Stat> newStats) {
/* 39 */     for (Stat newStat : newStats) {
/* 40 */       this.stats.put(newStat.getId(), Integer.valueOf(newStat.getSum()));
/*    */     }
/*    */   }
/*    */   
/*    */   public Map<Short, Integer> getStats() {
/* 45 */     return this.stats;
/*    */   }
/*    */   
/*    */   public void updateBuffs(List<StateUpdateMessage.BuffData> buffDatas) {
/* 49 */     int size = buffDatas.size();
/* 50 */     for (int i = 0; i < size; i++) {
/* 51 */       StateUpdateMessage.BuffData buffData = buffDatas.get(i);
/* 52 */       this.buffSlots.put(buffData.getType(), buffData);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<BuffType, StateUpdateMessage.BuffData> getBuffSlots() {
/* 58 */     return this.buffSlots;
/*    */   }
/*    */   
/*    */   public String getType() {
/* 62 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(String type) {
/* 66 */     this.type = type;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\CreatureData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */