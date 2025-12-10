/*     */ package com.funcom.rpgengine2.items;
/*     */ 
/*     */ import com.funcom.rpgengine2.creatures.RpgSourceProviderEntity;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GeneralItemHolder
/*     */   implements ItemHolder
/*     */ {
/*     */   public static final int UNLIMITED_CAPACITY = -1;
/*     */   private final int id;
/*     */   private final int capacity;
/*     */   protected RpgSourceProviderEntity owner;
/*  17 */   protected List<Item> items = new ArrayList<Item>();
/*     */   
/*     */   private List<ItemChangeListener> listeners;
/*     */   
/*     */   public GeneralItemHolder(int id) {
/*  22 */     this(id, -1);
/*     */   }
/*     */   
/*     */   public GeneralItemHolder(int id, int capacity) {
/*  26 */     this.id = id;
/*  27 */     this.capacity = capacity;
/*     */   }
/*     */   
/*     */   public void addChangeListener(ItemChangeListener containerListener) {
/*  31 */     if (this.listeners == null) {
/*  32 */       this.listeners = new ArrayList<ItemChangeListener>();
/*     */     }
/*  34 */     this.listeners.add(containerListener);
/*     */   }
/*     */   
/*     */   public void removeChangeListener(ItemChangeListener containerListener) {
/*  38 */     if (this.listeners != null) {
/*  39 */       this.listeners.remove(containerListener);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getItemCount() {
/*  44 */     int ret = 0;
/*  45 */     int size = this.items.size();
/*  46 */     for (int i = size - 1; i >= 0; i--) {
/*  47 */       if (this.items.get(i) != null) {
/*  48 */         ret++;
/*     */       }
/*     */     } 
/*  51 */     return ret;
/*     */   }
/*     */   
/*     */   public int getSlotCount() {
/*  55 */     if (hasLimitedCapacity()) {
/*  56 */       return getCapacity();
/*     */     }
/*  58 */     return this.items.size();
/*     */   }
/*     */   
/*     */   public Item getItem(String itemId, int tier) {
/*  62 */     for (Item item : this.items) {
/*  63 */       if (item != null && item.getDescription().getId().equals(itemId) && item.getDescription().getTier() == tier)
/*     */       {
/*  65 */         return item;
/*     */       }
/*     */     } 
/*  68 */     return null;
/*     */   }
/*     */   
/*     */   public Item getItem(String itemId) {
/*  72 */     for (Item item : this.items) {
/*  73 */       if (item != null && item.getDescription().getId().equals(itemId)) {
/*  74 */         return item;
/*     */       }
/*     */     } 
/*  77 */     return null;
/*     */   }
/*     */   
/*     */   public boolean hasLimitedCapacity() {
/*  81 */     return (getCapacity() != -1);
/*     */   }
/*     */   
/*     */   public boolean isFull() {
/*  85 */     return (hasLimitedCapacity() && getItemCount() >= getCapacity());
/*     */   }
/*     */   
/*     */   public Item getItem(int slotId) {
/*  89 */     if (this.items != null && slotId < this.items.size()) {
/*  90 */       return this.items.get(slotId);
/*     */     }
/*  92 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSlotForItem(Item item) {
/*  97 */     return this.items.indexOf(item);
/*     */   }
/*     */   
/*     */   public int getSlotIdByItemId(String classId) {
/* 101 */     for (int i = 0; i < this.items.size(); i++) {
/* 102 */       Item item = this.items.get(i);
/* 103 */       if (item != null && item.getDescription().getId().equals(classId)) {
/* 104 */         return i;
/*     */       }
/*     */     } 
/* 107 */     return -1;
/*     */   }
/*     */   
/*     */   public int getSlotId(ItemDescription description) {
/* 111 */     for (int i = 0; i < this.items.size(); i++) {
/* 112 */       Item equipment = this.items.get(i);
/* 113 */       if (equipment != null && equipment.getDescription().equals(description))
/*     */       {
/* 115 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 119 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPut(ItemDescription itemDescription) {
/* 124 */     Item oldItem = getItem(itemDescription.getId(), itemDescription.getTier());
/* 125 */     if (oldItem != null) {
/* 126 */       return canStack(itemDescription);
/*     */     }
/* 128 */     return !isFull();
/*     */   }
/*     */   
/*     */   private boolean canStack(ItemDescription itemDescription) {
/* 132 */     Item item = getItem(itemDescription.getId(), itemDescription.getTier());
/* 133 */     return (item != null && item.getAmount() < itemDescription.getStackSize());
/*     */   }
/*     */   
/*     */   public int getNextAvailableSlot() {
/*     */     int counter;
/* 138 */     for (counter = 0; counter < this.items.size(); counter++) {
/* 139 */       Item item = this.items.get(counter);
/* 140 */       if (item == null) {
/* 141 */         return counter;
/*     */       }
/*     */     } 
/* 144 */     return counter;
/*     */   }
/*     */   
/*     */   public void removeItem(int slotId) throws EquipException {
/* 148 */     putItem(slotId, null);
/*     */   }
/*     */   
/*     */   public void removeItem(Item item) throws EquipException {
/* 152 */     int slotId = this.items.indexOf(item);
/* 153 */     if (slotId != -1) {
/* 154 */       putItem(slotId, null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int putItem(int slotId, Item item) throws EquipException {
/* 161 */     if (item != null && this.items.size() >= slotId) {
/* 162 */       Item oldItem = getItem(item.getDescription().getId(), item.getDescription().getTier());
/* 163 */       if (oldItem != null) {
/* 164 */         if (oldItem.getAmount() + item.getAmount() <= item.getDescription().getStackSize()) {
/* 165 */           oldItem.incrementAmount(item.getAmount());
/* 166 */         } else if (oldItem.getAmount() < item.getDescription().getStackSize()) {
/* 167 */           oldItem.setAmount(item.getDescription().getStackSize());
/*     */         } 
/* 169 */         return getSlotForItem(oldItem);
/*     */       } 
/*     */ 
/*     */       
/* 173 */       oldItem = getItem(item.getDescription().getId());
/* 174 */       if (oldItem != null && oldItem.getDescription().getTier() != item.getDescription().getTier()) {
/*     */         
/* 176 */         slotId = getSlotForItem(oldItem);
/* 177 */         if (item.getDescription().getTier() - oldItem.getDescription().getTier() == 1) {
/* 178 */           item.setAmount(Math.min(item.getDescription().getStackSize(), oldItem.getAmount() + item.getAmount()));
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 185 */     if (slotId == -1 && item != null) {
/* 186 */       slotId = getPreferredSlotId(item);
/* 187 */       if (slotId == -1)
/*     */       {
/*     */         
/* 190 */         slotId = this.items.size();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 195 */     if (hasLimitedCapacity() && slotId >= getCapacity()) {
/* 196 */       throw new EquipException("Capacity overflow");
/*     */     }
/*     */     
/* 199 */     if (item != null) {
/* 200 */       if (item.getOwner() != null) {
/* 201 */         throw new EquipException("item must be removed from its owner before putting it into somewhere else");
/*     */       }
/* 203 */       if (item.getHolder() != null) {
/* 204 */         throw new EquipException("item must be removed from its holder/container before putting it into somewhere else");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 209 */     while (this.items.size() <= slotId) {
/* 210 */       this.items.add(null);
/*     */     }
/*     */ 
/*     */     
/* 214 */     Item old = this.items.get(slotId);
/* 215 */     if (old != item) {
/*     */       
/* 217 */       this.items.set(slotId, item);
/*     */       
/* 219 */       if (old != null) {
/* 220 */         old.setOwner(null);
/* 221 */         old.setHolder(null);
/*     */       } 
/* 223 */       if (item != null) {
/* 224 */         item.setOwner(this.owner);
/* 225 */         item.setHolder(this);
/*     */       } 
/*     */       
/* 228 */       fireItemChanged(slotId, item, old);
/*     */     } 
/*     */     
/* 231 */     return slotId;
/*     */   }
/*     */   
/*     */   protected int getPreferredSlotId(Item forItem) {
/* 235 */     for (int i = 0; i < this.items.size(); i++) {
/* 236 */       Item itemInSlot = this.items.get(i);
/* 237 */       if (itemInSlot == null) {
/* 238 */         return i;
/*     */       }
/*     */     } 
/* 241 */     return -1;
/*     */   }
/*     */   
/*     */   public void setOwner(RpgSourceProviderEntity owner) {
/* 245 */     this.owner = owner;
/* 246 */     for (Item item : this.items) {
/* 247 */       if (item != null)
/* 248 */         item.setOwner(owner); 
/*     */     } 
/*     */   }
/*     */   public RpgSourceProviderEntity getOwner() {
/* 252 */     return this.owner;
/*     */   }
/*     */   
/*     */   public Iterator<Item> iterator() {
/* 256 */     return new ItemIterator();
/*     */   }
/*     */   
/*     */   protected void fireItemChanged(int placementId, Item newItem, Item oldItem) {
/* 260 */     if (this.listeners != null) {
/* 261 */       for (ItemChangeListener listener : this.listeners) {
/* 262 */         listener.itemChanged(this, placementId, newItem, oldItem);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public int getId() {
/* 268 */     return this.id;
/*     */   }
/*     */   
/*     */   public List<Item> getItems() {
/* 272 */     return this.items;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 279 */     List<Item> items = getItems();
/* 280 */     for (Item item : items) {
/* 281 */       if (item != null) {
/* 282 */         return false;
/*     */       }
/*     */     } 
/* 285 */     return true;
/*     */   }
/*     */   
/*     */   public int getCapacity() {
/* 289 */     return this.capacity;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 293 */     StringBuffer sb = new StringBuffer();
/* 294 */     sb.append("[").append(",items=").append(this.items).append("]");
/*     */ 
/*     */ 
/*     */     
/* 298 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private class ItemIterator implements Iterator<Item> {
/*     */     int index;
/*     */     
/*     */     public boolean hasNext() {
/* 305 */       return (this.index < GeneralItemHolder.this.items.size());
/*     */     }
/*     */     private ItemIterator() {}
/*     */     public Item next() {
/* 309 */       return GeneralItemHolder.this.items.get(this.index++);
/*     */     }
/*     */     
/*     */     public void remove() {
/* 313 */       GeneralItemHolder.this.removeItem(this.index - 1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\items\GeneralItemHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */