/*    */ package com.funcom.rpgengine2.creatures;
/*    */ 
/*    */ import com.funcom.rpgengine2.items.EquipException;
/*    */ import com.funcom.rpgengine2.items.Item;
/*    */ import com.funcom.rpgengine2.items.ItemChangeListener;
/*    */ import com.funcom.rpgengine2.items.ItemHolder;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class EquipDollSupport
/*    */   implements RpgQueryableSupport, SupportEventListener, ItemContainer, LifeCycleAware
/*    */ {
/*    */   protected final ItemHolder itemHolder;
/*    */   protected final PassiveAbilityHandlingSupport passiveSupport;
/*    */   protected final StatSupport statSupport;
/*    */   
/*    */   public EquipDollSupport(ItemHolder itemHolder, StatSupport statSupport, PassiveAbilityHandlingSupport passiveSupport) {
/* 17 */     this.itemHolder = itemHolder;
/* 18 */     this.statSupport = statSupport;
/* 19 */     this.passiveSupport = passiveSupport;
/*    */   }
/*    */   
/*    */   public void init() {
/* 23 */     this.itemHolder.addChangeListener(new EquipmentUpdateListener());
/*    */   }
/*    */ 
/*    */   
/*    */   public void dispose() {}
/*    */   
/*    */   public void processEvent(SupportEvent event) {
/* 30 */     if (event.getType() == PassiveAbilityHandlingSupport.EventType.ADD_PASSIVE_ABILITIES) {
/* 31 */       for (Item item : this.itemHolder) {
/* 32 */         if (item != null) {
/* 33 */           this.passiveSupport.addPassiveAbilities(item.getDescription().getAbilities(), true);
/*    */         }
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public ItemHolder getItemHolder() {
/* 40 */     return this.itemHolder;
/*    */   }
/*    */   
/*    */   public Item getItem(int slotId) {
/* 44 */     return this.itemHolder.getItem(slotId);
/*    */   }
/*    */   
/*    */   public void removeItem(int slotId) throws EquipException {
/* 48 */     this.itemHolder.removeItem(slotId);
/*    */   }
/*    */   
/*    */   public void removeItem(Item item) throws EquipException {
/* 52 */     this.itemHolder.removeItem(item);
/*    */   }
/*    */   
/*    */   public int getCapacity() {
/* 56 */     return this.itemHolder.getCapacity();
/*    */   }
/*    */   
/*    */   public int getSlotCount() {
/* 60 */     return this.itemHolder.getSlotCount();
/*    */   }
/*    */   
/*    */   public Iterator<Item> iterator() {
/* 64 */     return this.itemHolder.iterator();
/*    */   }
/*    */   
/*    */   public int putItem(int slotId, Item item) {
/* 68 */     return this.itemHolder.putItem(slotId, item);
/*    */   }
/*    */   
/*    */   private class EquipmentUpdateListener
/*    */     implements ItemChangeListener {
/*    */     public void itemChanged(ItemHolder source, int placement, Item newItem, Item oldItem) {
/* 74 */       if (newItem != null) {
/* 75 */         EquipDollSupport.this.passiveSupport.addPassiveAbilities(newItem.getDescription().getAbilities(), true);
/*    */       }
/*    */       
/* 78 */       if (oldItem != null) {
/* 79 */         EquipDollSupport.this.passiveSupport.removePassiveAbilities(oldItem.getDescription().getAbilities(), true);
/*    */       }
/*    */       
/* 82 */       if ((newItem != null || oldItem != null) && 
/* 83 */         EquipDollSupport.this.statSupport != null)
/* 84 */         EquipDollSupport.this.statSupport.resetModifiers(EquipDollSupport.this.itemHolder.getOwner()); 
/*    */     }
/*    */     
/*    */     private EquipmentUpdateListener() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\EquipDollSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */