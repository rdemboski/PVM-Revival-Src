/*    */ package com.funcom.rpgengine2;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StatCollection
/*    */   implements Cloneable
/*    */ {
/*    */   protected Map<Short, Stat> stats;
/*    */   
/*    */   public StatCollection() {
/* 18 */     init();
/*    */   }
/*    */   
/*    */   private void init() {
/* 22 */     this.stats = new HashMap<Short, Stat>();
/*    */   }
/*    */   
/*    */   public void put(Stat stat) {
/* 26 */     this.stats.put(stat.getId(), stat);
/*    */   }
/*    */   
/*    */   public void remove(Stat stat) {
/* 30 */     this.stats.remove(stat.getId());
/*    */   }
/*    */   
/*    */   public Stat get(Short id) {
/* 34 */     return this.stats.get(id);
/*    */   }
/*    */   
/*    */   public void cleanModifiers() {
/* 38 */     for (Stat stat : this.stats.values()) {
/* 39 */       stat.setModifier(0);
/*    */     }
/*    */   }
/*    */   
/*    */   public Set<Short> getStatIds() {
/* 44 */     return this.stats.keySet();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<Short, Integer> getSimplifiedStats() {
/* 51 */     Map<Short, Integer> setStats = new HashMap<Short, Integer>();
/*    */     
/* 53 */     for (Stat stat : this.stats.values()) {
/* 54 */       setStats.put(stat.getId(), Integer.valueOf(stat.getSum()));
/*    */     }
/*    */     
/* 57 */     return setStats;
/*    */   }
/*    */ 
/*    */   
/*    */   public StatCollection clone() {
/*    */     try {
/* 63 */       StatCollection stats = (StatCollection)super.clone();
/* 64 */       stats.init();
/*    */       
/* 66 */       for (Stat stat : this.stats.values()) {
/* 67 */         stats.put(stat.clone());
/*    */       }
/*    */       
/* 70 */       return stats;
/* 71 */     } catch (CloneNotSupportedException e) {
/* 72 */       throw new RuntimeException("Cannot clone " + getClass() + "!!! Should not happen!!!");
/*    */     } 
/*    */   }
/*    */   
/*    */   public void addAll(StatCollection from) {
/* 77 */     for (Map.Entry<Short, Stat> entry : from.stats.entrySet()) {
/* 78 */       this.stats.put(entry.getKey(), entry.getValue());
/*    */     }
/*    */   }
/*    */   
/*    */   public String toString() {
/* 83 */     StringBuffer sb = new StringBuffer();
/* 84 */     sb.append("[stats contains=").append(this.stats).append("]");
/* 85 */     return sb.toString();
/*    */   }
/*    */   
/*    */   public Collection<Stat> getStats() {
/* 89 */     return this.stats.values();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\StatCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */