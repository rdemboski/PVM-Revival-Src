/*     */ package com.funcom.rpgengine2.monsters;
/*     */ 
/*     */ import com.funcom.rpgengine2.combat.Element;
/*     */ import com.funcom.rpgengine2.items.Item;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.items.ItemManager;
/*     */ import com.funcom.rpgengine2.loader.ConfigErrors;
/*     */ import com.funcom.rpgengine2.loader.DataRecords;
/*     */ import com.funcom.rpgengine2.loader.FieldMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MonsterManager
/*     */ {
/*     */   private final ConfigErrors configErrors;
/*     */   private final Class<? extends Enum> factionEnumClass;
/*  21 */   private Map<String, MonsterDescription> monstersById = new HashMap<String, MonsterDescription>();
/*     */   
/*     */   public MonsterManager(ConfigErrors configErrors, Class<? extends Enum> factionEnumClass) {
/*  24 */     this.configErrors = configErrors;
/*  25 */     this.factionEnumClass = factionEnumClass;
/*     */   }
/*     */   
/*     */   public MonsterDescription getDescription(String monsterId) {
/*  29 */     return this.monstersById.get(monsterId);
/*     */   }
/*     */   
/*     */   public void clearData() {
/*  33 */     this.monstersById.clear();
/*     */   }
/*     */   
/*     */   public void putOnce(String monsterId, MonsterDescription monsterDescription) {
/*  37 */     MonsterDescription existingDesc = this.monstersById.get(monsterId);
/*     */     
/*  39 */     if (existingDesc == null) {
/*     */       
/*  41 */       this.monstersById.put(monsterId, monsterDescription);
/*     */     } else {
/*     */       
/*  44 */       throw new IllegalArgumentException("Monster already exists: id=" + monsterId);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/*  49 */     StringBuffer sb = new StringBuffer();
/*  50 */     sb.append("[").append("monstersById=").append(this.monstersById).append("]");
/*  51 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(DataRecords dataRecords, ItemManager itemManager) {
/*  56 */     float maxRadius = 0.0F;
/*  57 */     for (String[] fields : dataRecords.getMonstersFiles()) {
/*  58 */       Enum enum_; FieldMap fieldMap = createMonsterFieldMap(fields);
/*  59 */       MonsterDescription desc = new MonsterDescription();
/*  60 */       desc.setId(fieldMap.get(MonsterFields.MONSTER_ID));
/*  61 */       desc.setModelXml(fieldMap.get(MonsterFields.XML_MODEL));
/*  62 */       desc.setName(fieldMap.get(MonsterFields.NAME));
/*  63 */       desc.setZone(fieldMap.get(MonsterFields.ZONE));
/*  64 */       desc.setLevel(Integer.parseInt(fieldMap.get(MonsterFields.LEVEL)));
/*  65 */       desc.setAi(fieldMap.get(MonsterFields.AI));
/*  66 */       desc.setHardness(MonsterHardness.valueOf(fieldMap.get(MonsterFields.HARDNESS).replace(' ', '_').toUpperCase()));
/*  67 */       String factionStr = fieldMap.get(MonsterFields.FACTION).toUpperCase();
/*     */       
/*     */       try {
/*  70 */         enum_ = Enum.valueOf((Class)this.factionEnumClass, factionStr);
/*  71 */       } catch (IllegalArgumentException e) {
/*  72 */         enum_ = ((Enum[])this.factionEnumClass.getEnumConstants())[0];
/*  73 */         this.configErrors.addError("Monster Faction", "unknownFaction=" + factionStr);
/*     */       } 
/*  75 */       desc.setFaction(enum_);
/*  76 */       desc.setType(fieldMap.get(MonsterFields.TYPE));
/*  77 */       desc.setSubtype(fieldMap.get(MonsterFields.SUBTYPE));
/*  78 */       desc.setRangeFollow(Double.parseDouble(fieldMap.get(MonsterFields.FOLLOW)));
/*  79 */       desc.setRangeWander(Double.parseDouble(fieldMap.get(MonsterFields.WANDER)));
/*  80 */       desc.setRangeAggro(Double.parseDouble(fieldMap.get(MonsterFields.AGGRO)));
/*  81 */       desc.setRangeHelpCall(Double.parseDouble(fieldMap.get(MonsterFields.HELP)));
/*  82 */       float radius = Float.parseFloat(fieldMap.get(MonsterFields.RADIUS));
/*  83 */       if (radius > maxRadius)
/*  84 */         maxRadius = radius; 
/*  85 */       desc.setRadius(radius);
/*  86 */       desc.setMonsterElement(Element.valueOf(fieldMap.get(MonsterFields.ELEMENT).toUpperCase()));
/*  87 */       double xpModifier = 1.0D;
/*     */       try {
/*  89 */         xpModifier = Double.parseDouble(fieldMap.get(MonsterFields.XP_MOD));
/*  90 */       } catch (NumberFormatException e) {}
/*     */       
/*  92 */       desc.setXpModifier(xpModifier);
/*  93 */       desc.setQuestObjective(fieldMap.get(MonsterFields.QUEST_OBJECTIVE));
/*     */       
/*  95 */       ItemDescription item = null;
/*     */       
/*  97 */       int index = MonsterFields.ITEM1.ordinal();
/*  98 */       for (int i = index; i <= MonsterFields.ITEM13.ordinal(); i++) {
/*  99 */         String itemId = fieldMap.get(MonsterFields.values()[i]);
/* 100 */         if (itemManager != null && itemId.length() > 0) {
/*     */           
/* 102 */           item = itemManager.getDescription(itemId, 0);
/* 103 */           if (item == null) {
/* 104 */             throw new RuntimeException("Invalid Usage Item: item#=" + (i + 1) + " itemId=" + itemId + " monsterId=" + desc.getId());
/*     */           }
/*     */         } 
/* 107 */         if (itemId.length() > 0) {
/* 108 */           desc.addUsageItemId(itemId, item);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 113 */       if (this.monstersById.get(desc.getId()) != null) {
/* 114 */         throw new RuntimeException("Duplicate monster: monsterId=" + desc.getId());
/*     */       }
/*     */       
/* 117 */       this.monstersById.put(desc.getId(), desc);
/*     */     } 
/* 119 */     Item.setMaxMonsterRadius(maxRadius);
/*     */   }
/*     */   
/*     */   public Set<String> getIds() {
/* 123 */     return this.monstersById.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<MonsterDescription> getAllDescriptionsByLevel(int level) {
/* 128 */     Set<MonsterDescription> returnable = new HashSet<MonsterDescription>();
/* 129 */     for (MonsterDescription monsterDescription : this.monstersById.values()) {
/* 130 */       if (monsterDescription.getLevel() == level) {
/* 131 */         returnable.add(monsterDescription);
/*     */       }
/*     */     } 
/* 134 */     return returnable;
/*     */   }
/*     */   
/*     */   protected FieldMap createMonsterFieldMap(String[] fields) {
/* 138 */     return new FieldMap((Object[])MonsterFields.values(), fields);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\monsters\MonsterManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */