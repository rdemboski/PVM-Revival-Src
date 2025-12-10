/*    */ package com.funcom.rpgengine2.creatures;
/*    */ 
/*    */ import com.funcom.rpgengine2.items.EquipException;
/*    */ import com.funcom.rpgengine2.items.GeneralItemHolder;
/*    */ import com.funcom.rpgengine2.items.Item;
/*    */ import com.funcom.rpgengine2.items.ItemChangeListener;
/*    */ import com.funcom.rpgengine2.items.ItemHolder;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class ItemSetSupport implements RpgQueryableSupport, SupportEventListener, ItemContainer, LifeCycleAware {
/*    */   protected final GeneralItemHolder itemHolder;
/*    */   protected final PassiveAbilityHandlingSupport passiveSupport;
/*    */   protected final StatSupport statSupport;
/*    */   
/*    */   public ItemSetSupport(GeneralItemHolder itemHolder, StatSupport statSupport, PassiveAbilityHandlingSupport passiveSupport) {
/* 16 */     this.itemHolder = itemHolder;
/* 17 */     this.statSupport = statSupport;
/* 18 */     this.passiveSupport = passiveSupport;
/*    */   }
/*    */   
/*    */   public void init() {
/* 22 */     this.itemHolder.addChangeListener(new EquipmentUpdateListener());
/*    */   }
/*    */ 
/*    */   
/*    */   public void dispose() {}
/*    */   
/*    */   public void processEvent(SupportEvent event) {
/* 29 */     if (event.getType() == PassiveAbilityHandlingSupport.EventType.ADD_PASSIVE_ABILITIES) {
/* 30 */       for (Item item : this.itemHolder) {
/* 31 */         if (item != null) {
/* 32 */           this.passiveSupport.addPassiveAbilities(item.getDescription().getAbilities(), true);
/*    */         }
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public ItemHolder getItemHolder() {
/* 39 */     return (ItemHolder)this.itemHolder;
/*    */   }
/*    */   
/*    */   public Item getItem(int slotId) {
/* 43 */     return this.itemHolder.getItem(slotId);
/*    */   }
/*    */   
/*    */   public void removeItem(int slotId) throws EquipException {
/* 47 */     this.itemHolder.removeItem(slotId);
/*    */   }
/*    */   
/*    */   public void removeItem(Item item) throws EquipException {
/* 51 */     this.itemHolder.removeItem(item);
/*    */   }
/*    */   
/*    */   public int getCapacity() {
/* 55 */     return this.itemHolder.getCapacity();
/*    */   }
/*    */   
/*    */   public int getSlotCount() {
/* 59 */     return this.itemHolder.getSlotCount();
/*    */   }
/*    */   
/*    */   public Iterator<Item> iterator() {
/* 63 */     return this.itemHolder.iterator();
/*    */   }
/*    */   
/*    */   public int putItem(int slotId, Item item) {
/* 67 */     return this.itemHolder.putItem(slotId, item);
/*    */   }
/*    */   
/*    */   private class EquipmentUpdateListener
/*    */     implements ItemChangeListener {
/*    */     public void itemChanged(ItemHolder source, int placement, Item newItem, Item oldItem) {
/* 73 */       if (newItem != null) {
/* 74 */         ItemSetSupport.this.passiveSupport.addPassiveAbilities(newItem.getDescription().getAbilities(), true);
/*    */       }
/*    */       
/* 77 */       if (oldItem != null) {
/* 78 */         ItemSetSupport.this.passiveSupport.removePassiveAbilities(oldItem.getDescription().getAbilities(), true);
/*    */       }
/*    */       
/* 81 */       if ((newItem != null || oldItem != null) && 
/* 82 */         ItemSetSupport.this.statSupport != null)
/* 83 */         ItemSetSupport.this.statSupport.resetModifiers(ItemSetSupport.this.itemHolder.getOwner()); 
/*    */     }
/*    */     
/*    */     private EquipmentUpdateListener() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\ItemSetSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */