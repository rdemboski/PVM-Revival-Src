/*    */ package com.funcom.rpgengine2.creatures;
/*    */ 
/*    */ import com.funcom.rpgengine2.Stat;
/*    */ 
/*    */ 
/*    */ public abstract class AbstractStatRegenSupport
/*    */   implements RpgUpdateable
/*    */ {
/*    */   protected static final float MILLIS_PER_SECOND = 1000.0F;
/*    */   protected StatSupport statSupport;
/*    */   
/*    */   protected AbstractStatRegenSupport(StatSupport statSupport) {
/* 13 */     this.statSupport = statSupport;
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getUpdatePriorities() {
/* 18 */     return new int[] { 500000 };
/*    */   }
/*    */ 
/*    */   
/*    */   public void update(int priority, long updateMillis) {
/* 23 */     if (meetsConditions(updateMillis) && inUpdateTimeframe(updateMillis)) {
/* 24 */       Stat stat = this.statSupport.getStatById(getStatId());
/* 25 */       if (stat != null)
/* 26 */         stat.addBase(getAmmount(updateMillis)); 
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract boolean inUpdateTimeframe(long paramLong);
/*    */   
/*    */   protected abstract short getStatId();
/*    */   
/*    */   protected abstract boolean meetsConditions(long paramLong);
/*    */   
/*    */   protected abstract int getAmmount(long paramLong);
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\AbstractStatRegenSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */