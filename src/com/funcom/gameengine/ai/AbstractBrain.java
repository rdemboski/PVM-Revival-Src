/*    */ package com.funcom.gameengine.ai;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ public abstract class AbstractBrain
/*    */   implements Brain {
/*    */   private Set<Perceptor> perceptors;
/*    */   private BrainControlled controlled;
/*    */   
/*    */   public void addPerceptor(Perceptor perceptor) {
/* 12 */     if (this.perceptors == null)
/* 13 */       this.perceptors = new HashSet<Perceptor>(); 
/* 14 */     this.perceptors.add(perceptor);
/*    */   }
/*    */   
/*    */   public void removePerceptor(Perceptor perceptor) {
/* 18 */     if (this.perceptors != null)
/* 19 */       this.perceptors.remove(perceptor); 
/*    */   }
/*    */   
/*    */   public void setControlled(BrainControlled controlled) {
/* 23 */     if (controlled == null)
/* 24 */       throw new IllegalArgumentException("controlled = null"); 
/* 25 */     this.controlled = controlled;
/*    */   }
/*    */   
/*    */   public BrainControlled getControlled() {
/* 29 */     return this.controlled;
/*    */   }
/*    */   
/*    */   protected Set<Perceptor> getPerceptors() {
/* 33 */     return this.perceptors;
/*    */   }
/*    */   
/*    */   protected Set<Entity> perceptEntities() {
/* 37 */     Set<Entity> percepted = new HashSet<Entity>();
/* 38 */     for (Perceptor perceptor : this.perceptors)
/* 39 */       percepted.addAll(perceptor.getDetectedEntities(this.controlled)); 
/* 40 */     return percepted;
/*    */   }
/*    */   
/*    */   public void notifyEvent(BrainEvent brainEvent) {}
/*    */   
/*    */   public void init() {}
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\ai\AbstractBrain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */