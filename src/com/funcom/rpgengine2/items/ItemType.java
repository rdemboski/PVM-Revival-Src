/*    */ package com.funcom.rpgengine2.items;
/*    */ 
/*    */ import com.funcom.rpgengine2.combat.RpgStatus;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public enum ItemType
/*    */ {
/* 10 */   ALL("all", -1, ItemCategory.SPECIAL),
/*    */   
/* 12 */   EQUIP_HEAD("equip_head", 0, ItemCategory.EQUIPMENT),
/* 13 */   EQUIP_TORSO("equip_torso", 1, ItemCategory.EQUIPMENT),
/* 14 */   EQUIP_LEGS("equip_legs", 2, ItemCategory.EQUIPMENT),
/* 15 */   EQUIP_SUBSCRIBER("equip_subscriber", 3, ItemCategory.EQUIPMENT),
/* 16 */   EQUIP_BACK("equip_back", 4, ItemCategory.EQUIPMENT),
/* 17 */   EQUIP_TRINKET("equip_trinket", 5, ItemCategory.EQUIPMENT),
/* 18 */   BONUS("bonus", -1, ItemCategory.BONUS),
/* 19 */   SKILL("skill", -1, ItemCategory.SKILL),
/* 20 */   CRYSTAL("crystal", -1, ItemCategory.CURRENCY),
/* 21 */   POTION("potion", -1, ItemCategory.POTION),
/* 22 */   USE_ON_BUY("use_on_buy", -1, ItemCategory.POTION),
/* 23 */   USE("use", -1, ItemCategory.POTION),
/* 24 */   UNKNOWN("unknown", -1, ItemCategory.UNKNOWN); private static final ItemType[] ITEM_TYPES; private static Map<ItemType, RpgStatus> blockedMap; private final String id;
/*    */   static {
/* 26 */     ITEM_TYPES = values();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 35 */     blockedMap = new HashMap<ItemType, RpgStatus>();
/* 36 */     blockedMap.put(SKILL, RpgStatus.SILENCE);
/* 37 */     blockedMap.put(POTION, RpgStatus.NOPOT);
/*    */   }
/*    */   private final int equipValue; private final ItemCategory category; private final String localizationKey;
/*    */   ItemType(String id, int value, ItemCategory category) {
/* 41 */     this.id = id;
/* 42 */     this.equipValue = value;
/* 43 */     this.category = category;
/*    */     
/* 45 */     this.localizationKey = "itemtype." + id.toLowerCase() + ".text";
/*    */   }
/*    */   
/*    */   public ItemCategory getCategory() {
/* 49 */     return this.category;
/*    */   }
/*    */   
/*    */   public int getEquipValue() {
/* 53 */     return this.equipValue;
/*    */   }
/*    */   
/*    */   public boolean isEquipable() {
/* 57 */     return (this.category == ItemCategory.EQUIPMENT);
/*    */   }
/*    */   
/*    */   public String getId() {
/* 61 */     return this.id;
/*    */   }
/*    */   
/*    */   public static ItemType getItemType(String typeId) {
/* 65 */     for (ItemType itemType : ITEM_TYPES) {
/* 66 */       if (itemType.getId().equalsIgnoreCase(typeId)) {
/* 67 */         return itemType;
/*    */       }
/*    */     } 
/*    */     
/* 71 */     return UNKNOWN;
/*    */   }
/*    */   
/*    */   public RpgStatus getBlockedBy() {
/* 75 */     return blockedMap.get(this);
/*    */   }
/*    */   
/*    */   public String getLocalizationKey() {
/* 79 */     return this.localizationKey;
/*    */   }
/*    */   
/*    */   public enum ItemCategory {
/* 83 */     EQUIPMENT,
/* 84 */     SKILL,
/* 85 */     CURRENCY,
/* 86 */     POTION,
/* 87 */     SPECIAL,
/* 88 */     BONUS,
/* 89 */     UNKNOWN;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\items\ItemType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */