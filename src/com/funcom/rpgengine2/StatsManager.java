/*     */ package com.funcom.rpgengine2;
/*     */ 
/*     */ import com.funcom.rpgengine2.loader.StatIdTranslator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StatsManager
/*     */ {
/*  14 */   private Class<? extends StatCollection> statsClass = StatCollection.class;
/*  15 */   private Map<Integer, StatCollection> statsByLevel = new HashMap<Integer, StatCollection>();
/*     */   
/*     */   private boolean ready;
/*     */   private StatIdTranslator statIdTranslator;
/*  19 */   private int minLevel = Integer.MAX_VALUE;
/*  20 */   private int maxLevel = Integer.MIN_VALUE;
/*     */   
/*     */   public StatCollection getStatsByLevel(int levelId) {
/*  23 */     ensureValid();
/*     */     
/*  25 */     return _getStatsByLevel(levelId);
/*     */   }
/*     */   
/*     */   public void clearData() {
/*  29 */     this.statsByLevel.clear();
/*  30 */     this.ready = false;
/*     */   }
/*     */ 
/*     */   
/*     */   private StatCollection _getStatsByLevel(int levelId) {
/*  35 */     if (levelId > this.maxLevel) {
/*  36 */       levelId = this.maxLevel;
/*  37 */     } else if (levelId < this.minLevel) {
/*  38 */       levelId = this.minLevel;
/*     */     } 
/*     */     
/*  41 */     StatCollection stats = this.statsByLevel.get(Integer.valueOf(levelId));
/*     */     
/*  43 */     if (stats == null) {
/*     */ 
/*     */       
/*  46 */       while (levelId >= this.minLevel && stats == null) {
/*  47 */         stats = this.statsByLevel.get(Integer.valueOf(--levelId));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  52 */       while (levelId <= this.maxLevel && stats == null) {
/*  53 */         stats = this.statsByLevel.get(Integer.valueOf(++levelId));
/*     */       }
/*     */     } 
/*     */     
/*  57 */     return stats.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStatValueByLevel(int levelId, short statId) {
/*  68 */     ensureValid();
/*     */     
/*  70 */     StatCollection stats = this.statsByLevel.get(Integer.valueOf(levelId));
/*     */     
/*  72 */     if (stats != null) {
/*  73 */       Stat stat = stats.get(Short.valueOf(statId));
/*     */       
/*  75 */       if (stat != null) {
/*  76 */         return stat.getSum();
/*     */       }
/*     */     } 
/*     */     
/*  80 */     return 0;
/*     */   }
/*     */   
/*     */   private void ensureValid() {
/*  84 */     if (!this.ready) {
/*  85 */       throw new IllegalStateException("Stats Manager is not ready. Call ready() method.");
/*     */     }
/*     */     
/*  88 */     if (this.statsByLevel.isEmpty()) {
/*  89 */       throw new IllegalStateException("No level data");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void put(int levelId, Stat stat) {
/*     */     StatCollection stats;
/*  96 */     if (this.statsByLevel.get(Integer.valueOf(levelId)) == null) {
/*  97 */       stats = newRpgObjectStats();
/*     */     } else {
/*  99 */       stats = this.statsByLevel.get(Integer.valueOf(levelId));
/*     */     } 
/*     */     
/* 102 */     stats.put(stat);
/* 103 */     this.statsByLevel.put(Integer.valueOf(levelId), stats);
/*     */     
/* 105 */     if (levelId > this.maxLevel) {
/* 106 */       this.maxLevel = levelId;
/*     */     }
/* 108 */     if (levelId < this.minLevel) {
/* 109 */       this.minLevel = levelId;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 115 */     StringBuffer sb = new StringBuffer();
/* 116 */     sb.append("[").append("\n");
/* 117 */     for (Integer integer : this.statsByLevel.keySet()) {
/* 118 */       sb.append("levelId ").append(integer).append(" contains={").append(this.statsByLevel.get(integer)).append("}").append("\n");
/*     */     }
/*     */     
/* 121 */     sb.append("]");
/* 122 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public void setStatIdTranslator(StatIdTranslator statIdTranslator) {
/* 126 */     this.statIdTranslator = statIdTranslator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ready() {
/* 136 */     List<Short> validStatIds = this.statIdTranslator.getRuntimeIds();
/*     */     
for (Integer level = Integer.valueOf(1); level.intValue() < this.maxLevel + 1; level = Integer.valueOf(level.intValue() + 1)) {
  StatCollection stats = this.statsByLevel.get(level);
/*     */       
/* 141 */       Set<Short> statIds = stats.getStatIds();
/* 142 */       int size = validStatIds.size();
/* 143 */       for (int i = 0; i < size; i++) {
/* 144 */         Short mustContainId = validStatIds.get(i);
/*     */         
/* 146 */         if (!statIds.contains(mustContainId)) {
/*     */           
/* 148 */           Stat previousLevelStat = null;
/*     */           
/* 150 */           StatCollection prevLvlStats = _getStatsByLevel(level.intValue() - 1);
/* 151 */           if (prevLvlStats != null) {
/* 152 */             previousLevelStat = prevLvlStats.get(mustContainId);
/*     */           }
/*     */           
/* 155 */           if (previousLevelStat != null) {
/*     */             
/* 157 */             stats.put(previousLevelStat.clone());
/*     */           } else {
/*     */             
/* 160 */             stats.put(new Stat(mustContainId, 0));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 165 */     this.ready = true;
/*     */   }
/*     */   
/*     */   public StatCollection getEmptyStats() {
/* 169 */     List<Short> allIds = this.statIdTranslator.getAllIds();
/*     */     
/* 171 */     StatCollection stats = newRpgObjectStats();
/* 172 */     int size = allIds.size();
/* 173 */     for (int i = 0; i < size; i++) {
/* 174 */       stats.put(new Stat(allIds.get(i), 0));
/*     */     }
/*     */     
/* 177 */     return stats;
/*     */   }
/*     */   
/*     */   public void setStatsClass(Class<? extends StatCollection> statsClass) {
/* 181 */     this.statsClass = statsClass;
/*     */ 
/*     */     
/* 184 */     newRpgObjectStats();
/*     */   }
/*     */   
/*     */   protected StatCollection newRpgObjectStats() {
/*     */     try {
/* 189 */       return this.statsClass.newInstance();
/* 190 */     } catch (InstantiationException e) {
/* 191 */       throw new RuntimeException("Cannot instantiate stats", e);
/* 192 */     } catch (IllegalAccessException e) {
/* 193 */       throw new RuntimeException("Cannot instantiate stats", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\StatsManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */