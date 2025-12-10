/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VisualEffectList
/*    */   implements VisualEffect
/*    */ {
/* 15 */   private List<VisualEffect> visuals = new LinkedList<VisualEffect>();
/*    */ 
/*    */   
/*    */   public void addVisualEffect(VisualEffect visualEffect) {
/* 19 */     this.visuals.add(visualEffect);
/*    */   }
/*    */   
/*    */   public void removeVisualEffect(VisualEffect visualEffect) {
/* 23 */     this.visuals.remove(visualEffect);
/*    */   }
/*    */ 
/*    */   
/*    */   public void apply(Object oldValue, Object newValue) {
/* 28 */     for (VisualEffect visual : this.visuals)
/* 29 */       visual.apply(oldValue, newValue); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\VisualEffectList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */