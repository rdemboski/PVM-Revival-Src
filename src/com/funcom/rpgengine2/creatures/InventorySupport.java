/*    */ package com.funcom.rpgengine2.creatures;
/*    */ 
/*    */ import com.funcom.rpgengine2.items.EquipException;
/*    */ import com.funcom.rpgengine2.items.Item;
/*    */ import com.funcom.rpgengine2.items.ItemChangeListener;
/*    */ import com.funcom.rpgengine2.items.ItemHolder;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class InventorySupport
/*    */   implements RpgQueryableSupport, SupportEventListener, ItemContainer, LifeCycleAware
/*    */ {
/*    */   protected ItemHolder itemHolder;
/*    */   private final RpgEntity owner;
/*    */   private PassiveAbilityHandlingSupport passiveSupport;
/*    */   
/*    */   public InventorySupport(RpgEntity owner, PassiveAbilityHandlingSupport passiveSupport, ItemHolder itemHolder) {
/* 17 */     this.owner = owner;
/* 18 */     this.passiveSupport = passiveSupport;
/* 19 */     this.itemHolder = itemHolder;
/*    */   }
/*    */   
/*    */   public void init() {
/* 23 */     this.itemHolder.addChangeListener(new InventoryUpdateListener(this.owner));
/*    */   }
/*    */ 
/*    */   
/*    */   public void dispose() {}
/*    */   
/*    */   public void processEvent(SupportEvent event) {
/* 30 */     if (event.getType() == PassiveAbilityHandlingSupport.EventType.ADD_PASSIVE_ABILITIES) {
/* 31 */       for (Item item : this.itemHolder) {
/* 32 */         if (item != null) {
/* 33 */           this.passiveSupport.addPassiveAbilities(item.getDescription().getAbilities(), false);
/*    */         }
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public ItemHolder getItemHolder() {
/* 40 */     return this.itemHolder;
/*    */   }
/*    */   
/*    */   public Iterator<Item> iterator() {
/* 44 */     return this.itemHolder.iterator();
/*    */   }
/*    */   
/*    */   public Item getItem(int slotId) {
/* 48 */     return this.itemHolder.getItem(slotId);
/*    */   }
/*    */   
/*    */   public void removeItem(int slotId) throws EquipException {
/* 52 */     this.itemHolder.removeItem(slotId);
/*    */   }
/*    */   
/*    */   public void removeItem(Item item) throws EquipException {
/* 56 */     this.itemHolder.removeItem(item);
/*    */   }
/*    */   
/*    */   public int getCapacity() {
/* 60 */     return this.itemHolder.getCapacity();
/*    */   }
/*    */   
/*    */   public int getSlotCount() {
/* 64 */     return this.itemHolder.getSlotCount();
/*    */   }
/*    */   
/*    */   public int putItem(int slotId, Item item) {
/* 68 */     return this.itemHolder.putItem(slotId, item);
/*    */   }
/*    */   
/*    */   private static class InventoryUpdateListener implements ItemChangeListener {
/*    */     private RpgEntity entity;
/*    */     
/*    */     private InventoryUpdateListener(RpgEntity entity) {
/* 75 */       this.entity = entity;
/*    */     }
/*    */     
/*    */     public void itemChanged(ItemHolder source, int placement, Item newItem, Item oldItem) {
/* 79 */       PassiveAbilityHandlingSupport passiveSupport = this.entity.<PassiveAbilityHandlingSupport>getSupport(PassiveAbilityHandlingSupport.class);
/*    */       
/* 81 */       if (newItem != null) {
/* 82 */         passiveSupport.addPassiveAbilities(newItem.getDescription().getAbilities(), false);
/*    */       }
/*    */       
/* 85 */       if (oldItem != null) {
/* 86 */         passiveSupport.removePassiveAbilities(oldItem.getDescription().getAbilities(), false);
/*    */       }
/*    */       
/* 89 */       if (newItem != null || oldItem != null) {
/* 90 */         StatSupport statSupport = this.entity.<StatSupport>getSupport(StatSupport.class);
/* 91 */         if (statSupport != null)
/* 92 */           statSupport.resetModifiers(this.entity); 
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\InventorySupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */