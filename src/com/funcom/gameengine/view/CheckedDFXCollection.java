/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.commons.dfx.DFXCollection;
/*    */ import com.funcom.commons.dfx.DireEffect;
/*    */ import com.funcom.commons.dfx.DireEffectDescription;
/*    */ import com.funcom.commons.dfx.EffectDescription;
/*    */ 
/*    */ public class CheckedDFXCollection
/*    */   extends DFXCollection {
/*    */   private static final int CHECK_ONCE_FOR_EACH_LOOP = 256;
/*    */   
/*    */   public void update(double dT) {
/* 13 */     super.update(dT);
/*    */     
/* 15 */     if (evalTimeToCheck())
/* 16 */       checkForTooManyInfiniteEffects(); 
/*    */   }
/*    */   private static final int INF_EFFECT_LIMIT = 16; private int runCheckCounter;
/*    */   
/*    */   private boolean evalTimeToCheck() {
/* 21 */     return (this.runCheckCounter++ % 256 == 0);
/*    */   }
/*    */   
/*    */   private void checkForTooManyInfiniteEffects() {
/* 25 */     if (!this.effects.isEmpty()) {
/* 26 */       int infiniteEffects = 0;
/* 27 */       for (DireEffect effect : this.effects) {
/* 28 */         DireEffectDescription effectDescription = effect.getDescription();
/* 29 */         for (EffectDescription description : effectDescription.getEffectDescriptions()) {
/* 30 */           if (description.getEndTime() == Double.POSITIVE_INFINITY) {
/* 31 */             infiniteEffects++;
/*    */           }
/*    */         } 
/*    */       } 
/* 35 */       if (infiniteEffects > 16) {
/* 36 */         System.err.println("------------------------------------------------------------\n TOO MANY INFINITE DURATION EFFECTS ON A _SINGLE_ ENTITY,\n POTENTIAL MEMORY LEAK. PLEASE CHECK YOUR DATA. Effect Ids:");
/*    */         
/* 38 */         for (DireEffect effect : this.effects) {
/* 39 */           DireEffectDescription effectDescription = effect.getDescription();
/* 40 */           for (EffectDescription description : effectDescription.getEffectDescriptions()) {
/* 41 */             if (description.getEndTime() == Double.POSITIVE_INFINITY) {
/* 42 */               System.err.println("    " + effectDescription.getId());
/*    */             }
/*    */           } 
/*    */         } 
/*    */         
/* 47 */         System.err.println("------------------------------------------------------------");
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\CheckedDFXCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */