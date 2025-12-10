/*     */ package com.funcom.rpgengine2.items;
/*     */ import com.funcom.rpgengine2.SkillId;
/*     */ import com.funcom.rpgengine2.loader.ConfigErrors;
/*     */ import com.funcom.rpgengine2.pets.PetDescription;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class ItemManager {
/*  12 */   private Map<String, Map<Integer, ItemDescription>> itemsById = new HashMap<String, Map<Integer, ItemDescription>>();
/*  13 */   private static final Integer DEFAULT_ITEM_LEVEL = Integer.valueOf(-1);
/*  14 */   private static final Logger LOGGER = Logger.getLogger(ItemManager.class.getName());
/*     */   
/*  16 */   private Map<String, List<PetDescription>> petSkillMap = new HashMap<String, List<PetDescription>>();
/*  17 */   private Map<String, ItemDescription> petStatItems = new HashMap<String, ItemDescription>();
/*  18 */   private Map<String, ItemSetDesc> itemSetMap = new HashMap<String, ItemSetDesc>();
/*     */   private PetManager petManager;
/*     */   private final ConfigErrors configErrors;
/*  21 */   private Map<String, String> referenceMap = new HashMap<String, String>();
/*  22 */   private Map<String, Set<String>> backReferenceMap = new HashMap<String, Set<String>>();
/*     */   
/*     */   public ItemManager(ConfigErrors configErrors) {
/*  25 */     this.configErrors = configErrors;
/*     */   }
/*     */   
/*     */   public ItemDescription getDescription(SkillId itemId) {
/*  29 */     return getDescription(itemId.getItemId(), itemId.getTier());
/*     */   }
/*     */   
/*     */   public ItemDescription getDescription(String itemId, int tier) {
/*  33 */     return getDescription(itemId, Integer.valueOf(tier), false);
/*     */   }
/*     */   
/*     */   public void clearData() {
/*  37 */     this.itemsById.clear();
/*     */   }
/*     */   
/*     */   public List<ItemDescription> getAll() {
/*  41 */     List<ItemDescription> ret = new ArrayList<ItemDescription>();
/*     */     
/*  43 */     for (Map<Integer, ItemDescription> itemsByLevel : this.itemsById.values()) {
/*  44 */       for (ItemDescription itemDescription : itemsByLevel.values()) {
/*  45 */         ret.add(itemDescription);
/*     */       }
/*     */     } 
/*     */     
/*  49 */     return ret;
/*     */   }
/*     */   
/*     */   public boolean hasDescription(String itemId) {
/*  53 */     return hasDescription(itemId, DEFAULT_ITEM_LEVEL, true);
/*     */   }
/*     */   
/*     */   public boolean hasDescription(String itemId, Integer tier) {
/*  57 */     return hasDescription(itemId, tier, false);
/*     */   }
/*     */   
/*     */   public boolean hasDescription(String itemId, Integer tier, boolean downgradeLevel) {
/*  61 */     Map<Integer, ItemDescription> itemsByLevel = this.itemsById.get(itemId);
/*     */     
/*  63 */     if (itemsByLevel != null) {
/*  64 */       ItemDescription ret = null;
/*  65 */       if (tier.intValue() >= 0) {
/*  66 */         ret = itemsByLevel.get(tier);
/*     */         
/*  68 */         if (ret != null)
/*  69 */           return true; 
/*  70 */         if (downgradeLevel) {
/*     */           
/*  72 */           ItemDescription lastItem = null;
/*     */           
/*  74 */           for (ItemDescription itemDescription : itemsByLevel.values()) {
/*  75 */             if (itemDescription.getTier() > tier.intValue()) {
/*     */               break;
/*     */             }
/*  78 */             lastItem = itemDescription;
/*     */           } 
/*     */           
/*  81 */           if (lastItem != null) {
/*  82 */             return true;
/*     */           }
/*     */         } 
/*     */       } else {
/*  86 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     return false;
/*     */   }
/*     */   
/*     */   public ItemDescription getDescription(String itemId, Integer tier, boolean downgradeLevel) {
/*  94 */     ItemDescription ret = getDescriptionNullable(itemId, tier, downgradeLevel);
/*     */     
/*  96 */     if (ret != null) {
/*  97 */       return ret;
/*     */     }
/*     */     
/* 100 */     ItemDescription itemDesc = new ItemDescription(itemId, tier, DefaultItemFactory.INSTANCE);
/* 101 */     itemDesc.setItemType(ItemType.UNKNOWN);
/*     */     
/* 103 */     if (itemId.equals("xp"))
/* 104 */       return itemDesc; 
/* 105 */     this.configErrors.addError("Missing Item", "This item does not exist in the csv file: " + itemId + " tier " + tier);
/*     */     
/* 107 */     return itemDesc;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected ItemDescription getDescriptionNullable(String itemId, Integer tier, boolean downgradeLevel) {
/* 113 */     Map<Integer, ItemDescription> itemsByLevel = this.itemsById.get(itemId);
/*     */     
/* 115 */     if (itemsByLevel != null) {
/* 116 */       ItemDescription ret = null;
/* 117 */       if (tier.intValue() >= 0) {
/* 118 */         ret = itemsByLevel.get(tier);
/*     */         
/* 120 */         if (ret == null && downgradeLevel) {
/*     */           
/* 122 */           ItemDescription lastItem = null;
/*     */           
/* 124 */           for (ItemDescription itemDescription : itemsByLevel.values()) {
/* 125 */             if (itemDescription.getTier() > tier.intValue()) {
/*     */               break;
/*     */             }
/* 128 */             lastItem = itemDescription;
/*     */           } 
/*     */           
/* 131 */           if (lastItem != null) {
/* 132 */             ret = lastItem;
/*     */           }
/*     */         } 
/*     */       } else {
/*     */         
/* 137 */         Iterator<ItemDescription> it = itemsByLevel.values().iterator();
/* 138 */         if (it.hasNext()) {
/* 139 */           ret = it.next();
/*     */         }
/*     */       } 
/*     */       
/* 143 */       if (ret != null) {
/* 144 */         return ret;
/*     */       }
/*     */     } 
/*     */     
/* 148 */     return null;
/*     */   }
/*     */   
/*     */   public void putOnce(String itemId, String itemRef, ItemDescription itemDescription) {
/* 152 */     if (!itemRef.isEmpty()) {
/* 153 */       this.referenceMap.put(itemId, itemRef);
/* 154 */       Set<String> backRefs = this.backReferenceMap.get(itemRef);
/* 155 */       if (backRefs == null) {
/* 156 */         backRefs = new HashSet<String>();
/*     */       }
/* 158 */       backRefs.add(itemId);
/* 159 */       this.backReferenceMap.put(itemRef, backRefs);
/*     */     } 
/*     */     
/* 162 */     Map<Integer, ItemDescription> itemsByLevel = this.itemsById.get(itemId);
/*     */     
/* 164 */     if (itemsByLevel == null) {
/*     */       
/* 166 */       itemsByLevel = new HashMap<Integer, ItemDescription>(1, 1.0F);
/* 167 */       this.itemsById.put(itemId, itemsByLevel);
/*     */     } 
/*     */     
/* 170 */     ItemDescription existingItem = itemsByLevel.get(Integer.valueOf(itemDescription.getTier()));
/*     */     
/* 172 */     if (existingItem == null) {
/*     */       
/* 174 */       if (itemsByLevel.size() == 1) {
/*     */         
/* 176 */         HashMap<Integer, ItemDescription> tmp = new LinkedHashMap<Integer, ItemDescription>();
/* 177 */         tmp.putAll(itemsByLevel);
/* 178 */         itemsByLevel = tmp;
/* 179 */         this.itemsById.put(itemId, itemsByLevel);
/*     */       } 
/*     */       
/* 182 */       itemsByLevel.put(Integer.valueOf(itemDescription.getTier()), itemDescription);
/*     */     } else {
/*     */       
/* 185 */       throw new IllegalArgumentException("Item already exists: id=" + itemId + " level=" + existingItem.getTier());
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 190 */     StringBuffer sb = new StringBuffer();
/* 191 */     sb.append("[").append("itemsById=").append(this.itemsById).append("]");
/* 192 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public void setPetManager(PetManager petManager) {
/* 196 */     this.petManager = petManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/* 201 */     for (String itemId : this.itemsById.keySet()) {
/* 202 */       List<PetDescription> petsList = this.petSkillMap.get(itemId);
/* 203 */       if (petsList == null) {
/* 204 */         petsList = new LinkedList<PetDescription>();
/* 205 */         this.petSkillMap.put(itemId, petsList);
/*     */       } 
/* 207 */       this.petManager.collectPetsWithSkill(itemId, petsList);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String itemReferencesAs(String itemId) {
/* 212 */     return this.referenceMap.get(itemId);
/*     */   }
/*     */   
/*     */   public Set<String> getBackReferences(String ref) {
/* 216 */     return this.backReferenceMap.get(ref);
/*     */   }
/*     */   
/*     */   public List<PetDescription> getPetListWithSkill(String skillId) {
/* 220 */     return this.petSkillMap.get(skillId);
/*     */   }
/*     */   
/*     */   public void addItemSet(ItemSetDesc itemSetDesc) {
/* 224 */     this.itemSetMap.put(itemSetDesc.getItemSetId(), itemSetDesc);
/*     */   }
/*     */   
/*     */   public ItemSetDesc getSetDesc(String setId) {
/* 228 */     return this.itemSetMap.get(setId);
/*     */   }
/*     */   
/*     */   public Iterable<String> getItemSets() {
/* 232 */     return this.itemSetMap.keySet();
/*     */   }
/*     */   
/*     */   public void setPetStatItem(String id, ItemDescription itemDescription) {
/* 236 */     this.petStatItems.put(id, itemDescription);
/*     */   }
/*     */   
/*     */   public ItemDescription getPetStatItem(String id) {
/* 240 */     return this.petStatItems.get(id);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\items\ItemManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */