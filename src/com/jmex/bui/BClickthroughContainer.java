/*    */ package com.jmex.bui;
/*    */ 
/*    */ import com.jmex.bui.layout.BLayoutManager;
/*    */ 
/*    */ public class BClickthroughContainer
/*    */   extends BContainer
/*    */ {
/*    */   public BClickthroughContainer() {}
/*    */   
/*    */   public BClickthroughContainer(String name) {
/* 11 */     super(name);
/*    */   }
/*    */   
/*    */   public BClickthroughContainer(BLayoutManager layout) {
/* 15 */     super(layout);
/*    */   }
/*    */   
/*    */   public BClickthroughContainer(String name, BLayoutManager layout) {
/* 19 */     super(name, layout);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BComponent getHitComponent(int mx, int my) {
/* 29 */     BComponent component = super.getHitComponent(mx, my);
/* 30 */     if (component == this) {
/* 31 */       return null;
/*    */     }
/* 33 */     return component;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BClickthroughContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */