/*     */ package com.funcom.tcg.client.model.rpg;
/*     */ 
/*     */ import com.funcom.rpgengine2.items.ItemType;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*     */ import com.funcom.util.SizeCheckedArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ public class ClientEquipDoll
/*     */ {
/*     */   private List<ClientItem> items;
/*     */   private List<EquipChangeListener> listeners;
/*     */   private int id;
/*     */   
/*     */   public ClientEquipDoll(int id) {
/*  17 */     this.id = id;
/*  18 */     this.items = (List<ClientItem>)new SizeCheckedArrayList(10, "ClientEquipDoll.listeners", 16);
/*     */   }
/*     */   
/*     */   public void addChangeListener(EquipChangeListener listener) {
/*  22 */     if (this.listeners == null) {
/*  23 */       this.listeners = new LinkedList<EquipChangeListener>();
/*     */     }
/*  25 */     this.listeners.add(listener);
/*     */   }
/*     */   
/*     */   public void removeChangeListener(EquipChangeListener listener) {
/*  29 */     if (this.listeners != null) {
/*  30 */       this.listeners.remove(listener);
/*     */     }
/*     */   }
/*     */   
/*     */   private void fireItemEquipped(int placementId, ClientItem newItem, ClientItem oldItem) {
/*  35 */     if (this.listeners != null) {
/*  36 */       for (EquipChangeListener listener : this.listeners) {
/*  37 */         listener.itemEquipped(this, placementId, newItem, oldItem);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void fireItemUnequipped(int placementId, ClientItem oldItem) {
/*  43 */     if (this.listeners != null) {
/*  44 */       for (EquipChangeListener listener : this.listeners) {
/*  45 */         listener.itemUnequipped(this, placementId, oldItem);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public int getId() {
/*  51 */     return this.id;
/*     */   }
/*     */   
/*     */   public ClientItem setItem(int slotId, ClientItem clientItem) {
/*  55 */     while (slotId >= this.items.size()) {
/*  56 */       this.items.add(null);
/*     */     }
/*     */     
/*  59 */     ClientItem oldItem = this.items.get(slotId);
/*  60 */     if (oldItem != clientItem) {
/*  61 */       this.items.set(slotId, clientItem);
/*  62 */       fireItemEquipped(slotId, clientItem, oldItem);
/*     */     } 
/*     */     
/*  65 */     return oldItem;
/*     */   }
/*     */   
/*     */   public void setItem(ClientItem clientItem) {
/*  69 */     ItemType type = clientItem.getItemType();
/*  70 */     if (type.getEquipValue() >= 0) {
/*  71 */       setItem(type.getEquipValue(), clientItem);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeItem(int slotId) {
/*  76 */     ClientItem oldItem = null;
/*  77 */     if (slotId < this.items.size()) {
/*  78 */       oldItem = this.items.get(slotId);
/*  79 */       this.items.set(slotId, null);
/*     */     } 
/*  81 */     fireItemUnequipped(slotId, oldItem);
/*     */   }
/*     */   
/*     */   public ClientItem getItem(int slotId) {
/*  85 */     if (slotId >= this.items.size()) {
/*  86 */       return null;
/*     */     }
/*     */     
/*  89 */     return this.items.get(slotId);
/*     */   }
/*     */   
/*     */   public int getItemsCount() {
/*  93 */     return this.items.size();
/*     */   }
/*     */   
/*     */   public List<ClientItem> getItems() {
/*  97 */     return this.items;
/*     */   }
/*     */   
/*     */   public boolean hasItem(InventoryItem item) {
/* 101 */     for (ClientItem clientItem : this.items) {
/* 102 */       if (clientItem != null && 
/* 103 */         clientItem.getClassId().equals(item.getClassId())) {
/* 104 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 108 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\ClientEquipDoll.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */