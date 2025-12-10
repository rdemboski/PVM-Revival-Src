/*    */ package com.funcom.tcg.client.ui.inventory;
/*    */ 
/*    */ import com.funcom.util.SizeCheckedArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractInventory
/*    */   implements Inventory
/*    */ {
/*    */   private static final int MEM_LEAK_WARNING_LIMIT = 16;
/*    */   private List<Inventory.ChangeListener> listeners;
/*    */   private int id;
/*    */   private int capacity;
/*    */   
/*    */   protected AbstractInventory(int id) {
/* 18 */     this.id = id;
/* 19 */     this.listeners = (List<Inventory.ChangeListener>)new SizeCheckedArrayList(4, "AbstractInventory.listeners", 16);
/*    */   }
/*    */   
/*    */   public int getId() {
/* 23 */     return this.id;
/*    */   }
/*    */   
/*    */   public int getCapacity() {
/* 27 */     return this.capacity;
/*    */   }
/*    */   
/*    */   public void setCapacity(int capacity) {
/* 31 */     this.capacity = capacity;
/*    */   }
/*    */   
/*    */   public final void addChangeListener(Inventory.ChangeListener listener) {
/* 35 */     this.listeners.add(listener);
/*    */   }
/*    */   
/*    */   public final void removeChangeListener(Inventory.ChangeListener listener) {
/* 39 */     this.listeners.remove(listener);
/*    */   }
/*    */   
/*    */   protected final void fireChanged(int slotId, InventoryItem oldItem, InventoryItem newItem) {
/* 43 */     for (Inventory.ChangeListener listener : this.listeners)
/* 44 */       listener.slotChanged(this, slotId, oldItem, newItem); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\inventory\AbstractInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */