/*     */ package com.funcom.rpgengine2.items;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EquipSlot
/*     */ {
/*     */   public static final String TYPE_ANY = "any";
/*     */   public static final String TYPE_LEFT_HAND = "left-hand";
/*     */   public static final String TYPE_RIGHT_HAND = "right-hand";
/*     */   public static final String TYPE_HEADGEAR = "headgear";
/*     */   public static final String TYPE_TORSO = "torso";
/*     */   public static final String TYPE_LEGS = "legs";
/*     */   public static final String TYPE_ARMS = "arms";
/*     */   public static final String TYPE_FEET = "feet";
/*     */   public static final String TYPE_NECKLACE = "necklace";
/*     */   public static final String TYPE_LEFT_RING = "left-right";
/*     */   public static final String TYPE_RIGHT_RING = "right-ring";
/*     */   private String type;
/*     */   private Item emptyItem;
/*     */   private Item item;
/*     */   private int id;
/*     */   
/*     */   public EquipSlot(int id) {
/*  46 */     this(id, "any", null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EquipSlot(int id, String type) {
/*  55 */     this(id, type, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EquipSlot(int id, String type, Item emptyItem) {
/*  65 */     this.type = type;
/*  66 */     this.emptyItem = emptyItem;
/*  67 */     setId(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void equip(Item item) {
/*  77 */     if (!isEquipped()) {
/*  78 */       this.item = item;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item swap(Item item) {
/*  88 */     Item oldItem = this.item;
/*  89 */     this.item = item;
/*  90 */     return oldItem;
/*     */   }
/*     */   
/*     */   public void dequip() {
/*  94 */     if (this.item != null) {
/*  95 */       this.item.setOwner(null);
/*     */     }
/*  97 */     this.item = this.emptyItem;
/*     */   }
/*     */   
/*     */   public Item getItem() {
/* 101 */     return this.item;
/*     */   }
/*     */   
/*     */   public boolean isEquipped() {
/* 105 */     return (this.item != null && !this.item.equals(this.emptyItem));
/*     */   }
/*     */   
/*     */   public int getId() {
/* 109 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/* 113 */     this.id = id;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\items\EquipSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */