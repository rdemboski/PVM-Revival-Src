/*    */ package com.funcom.gameengine.jme.modular;
/*    */ 
/*    */ import com.funcom.util.SizeCheckedArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public abstract class AbstractModularDescription
/*    */   implements ModularDescription
/*    */ {
/*    */   private static final int MEM_LEAK_WARNING_LIMIT = 16;
/*    */   private List<ModularDescription.ModularChangedListener> listeners;
/*    */   
/*    */   public void addChangedListener(ModularDescription.ModularChangedListener listener) {
/* 14 */     if (this.listeners == null) {
/* 15 */       this.listeners = (List<ModularDescription.ModularChangedListener>)new SizeCheckedArrayList(4, "AbstractModularDescription.listeners", 16);
/*    */     }
/* 17 */     if (!this.listeners.contains(listener)) {
/* 18 */       this.listeners.add(listener);
/*    */     }
/*    */   }
/*    */   
/*    */   public void removeChangedListener(ModularDescription.ModularChangedListener listener) {
/* 23 */     if (this.listeners != null)
/* 24 */       this.listeners.remove(listener); 
/*    */   }
/*    */   
/*    */   protected void clearChangedListeners() {
/* 28 */     if (this.listeners != null) {
/* 29 */       this.listeners.clear();
/*    */     }
/*    */   }
/*    */   
/*    */   protected void firePartChanged(String partName) {
/* 34 */     if (this.listeners != null)
/* 35 */       for (ModularDescription.ModularChangedListener listener : this.listeners)
/* 36 */         listener.partChanged(partName);  
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\modular\AbstractModularDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */