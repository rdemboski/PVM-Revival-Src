/*    */ package com.funcom.rpgengine2.creatures;
/*    */ 
/*    */ import com.funcom.rpgengine2.Stat;
/*    */ import com.funcom.rpgengine2.StatCollection;
/*    */ import com.funcom.rpgengine2.StatSupportListener;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class StatSupport
/*    */   implements RpgQueryableSupport {
/* 11 */   protected StatCollection statCollection = new StatCollection();
/* 12 */   protected List<StatSupportListener> listeners = new ArrayList<StatSupportListener>(0);
/*    */   
/*    */   protected Short levelStatId;
/*    */   
/*    */   public StatSupport(Short levelStatId) {
/* 17 */     this.levelStatId = levelStatId;
/*    */   }
/*    */   
/*    */   public int getLevel() {
/* 21 */     if (this.levelStatId != null) {
/* 22 */       return this.statCollection.get(this.levelStatId).getSum();
/*    */     }
/*    */     
/* 25 */     return 0;
/*    */   }
/*    */   
/*    */   public void addListener(StatSupportListener listener) {
/* 29 */     this.listeners.add(listener);
/*    */   }
/*    */   
/*    */   public void removeListener(StatSupportListener listener) {
/* 33 */     this.listeners.remove(listener);
/*    */   }
/*    */   
/*    */   public void resetModifiers(RpgEntity owner) {
/* 37 */     this.statCollection.cleanModifiers();
/*    */     
/* 39 */     if (!this.listeners.isEmpty()) {
/* 40 */       int listenerCount = this.listeners.size();
/* 41 */       for (int i = 0; i < listenerCount; i++)
/* 42 */         ((StatSupportListener)this.listeners.get(i)).onResettedModifiers(this, owner); 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void putStats(StatCollection stats) {
/* 47 */     this.statCollection.addAll(stats);
/*    */   }
/*    */   
/*    */   public Stat getStatById(short statId) {
/* 51 */     return this.statCollection.get(Short.valueOf(statId));
/*    */   }
/*    */   
/*    */   public void put(Stat stat) {
/* 55 */     this.statCollection.put(stat);
/*    */   }
/*    */   
/*    */   public StatCollection getStatCollection() {
/* 59 */     return this.statCollection;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\StatSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */