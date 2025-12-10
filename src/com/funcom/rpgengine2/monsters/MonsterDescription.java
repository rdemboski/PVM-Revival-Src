/*     */ package com.funcom.rpgengine2.monsters;
/*     */ 
/*     */ import com.funcom.commons.localization.JavaLocalization;
/*     */ import com.funcom.rpgengine2.combat.Element;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.loot.MonsterGroupLoot;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ public class MonsterDescription
/*     */ {
/*     */   private String id;
/*     */   private String modelXml;
/*     */   private String name;
/*     */   private String zone;
/*     */   private int level;
/*     */   private String ai;
/*     */   private MonsterHardness hardness;
/*     */   private Enum faction;
/*     */   private String type;
/*     */   private String subtype;
/*     */   private double rangeFollow;
/*     */   private double rangeWander;
/*     */   private double rangeAggro;
/*     */   private double rangeHelpCall;
/*     */   private Element monsterElement;
/*  29 */   private List<String> usageItemIds = new ArrayList<String>();
/*  30 */   private List<ItemDescription> usageItems = new ArrayList<ItemDescription>();
/*     */   private List<String> associatedStartQuests;
/*     */   private List<String> associatedHandInQuests;
/*  33 */   private List<MonsterGroupLoot> lootGroups = new ArrayList<MonsterGroupLoot>();
/*     */   
/*     */   private boolean associatedWithQuests;
/*     */   private float radius;
/*     */   private double xpModifier;
/*     */   private String questObjective;
/*     */   
/*     */   public void addUsageItemId(String itemId, ItemDescription item) {
/*  41 */     this.usageItemIds.add(itemId);
/*  42 */     this.usageItems.add(item);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemDescription> getUsageItems() {
/*  47 */     return this.usageItems;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  51 */     this.id = id;
/*     */   }
/*     */   
/*     */   public void setModelXml(String modelXml) {
/*  55 */     this.modelXml = modelXml;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  59 */     this.name = name;
/*     */   }
/*     */   
/*     */   public void setZone(String zone) {
/*  63 */     this.zone = zone;
/*     */   }
/*     */   
/*     */   public void setLevel(int level) {
/*  67 */     this.level = level;
/*     */   }
/*     */   
/*     */   public void setAi(String ai) {
/*  71 */     this.ai = ai;
/*     */   }
/*     */   
/*     */   public void setHardness(MonsterHardness hardness) {
/*  75 */     this.hardness = hardness;
/*     */   }
/*     */   
/*     */   public void setFaction(Enum faction) {
/*  79 */     this.faction = faction;
/*     */   }
/*     */   
/*     */   public void setType(String type) {
/*  83 */     this.type = type;
/*     */   }
/*     */   
/*     */   public void setRangeFollow(double rangeFollow) {
/*  87 */     this.rangeFollow = rangeFollow;
/*     */   }
/*     */   
/*     */   public void setRangeWander(double rangeWander) {
/*  91 */     this.rangeWander = rangeWander;
/*     */   }
/*     */   
/*     */   public void setRangeAggro(double rangeAggro) {
/*  95 */     this.rangeAggro = rangeAggro;
/*     */   }
/*     */   
/*     */   public String getId() {
/*  99 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getModelXml() {
/* 103 */     return this.modelXml;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 107 */     return JavaLocalization.getInstance().getLocalizedRPGText(this.name);
/*     */   }
/*     */   
/*     */   public String getZone() {
/* 111 */     return this.zone;
/*     */   }
/*     */   
/*     */   public int getLevel() {
/* 115 */     return this.level;
/*     */   }
/*     */   
/*     */   public String getAi() {
/* 119 */     return this.ai;
/*     */   }
/*     */   
/*     */   public MonsterHardness getHardness() {
/* 123 */     return this.hardness;
/*     */   }
/*     */   
/*     */   public Enum getFaction() {
/* 127 */     return this.faction;
/*     */   }
/*     */   
/*     */   public String getType() {
/* 131 */     return this.type;
/*     */   }
/*     */   
/*     */   public double getRangeFollow() {
/* 135 */     return this.rangeFollow;
/*     */   }
/*     */   
/*     */   public double getRangeWander() {
/* 139 */     return this.rangeWander;
/*     */   }
/*     */   
/*     */   public double getRangeAggro() {
/* 143 */     return this.rangeAggro;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getUsageItemIds() {
/* 148 */     return this.usageItemIds;
/*     */   }
/*     */   
/*     */   public Element getMonsterElement() {
/* 152 */     return this.monsterElement;
/*     */   }
/*     */   
/*     */   public void setMonsterElement(Element monsterElement) {
/* 156 */     this.monsterElement = monsterElement;
/*     */   }
/*     */   
/*     */   public double getRangeHelpCall() {
/* 160 */     return this.rangeHelpCall;
/*     */   }
/*     */   
/*     */   public void setRangeHelpCall(double rangeHelpCall) {
/* 164 */     this.rangeHelpCall = rangeHelpCall;
/*     */   }
/*     */   
/*     */   public void addAssociatedStartQuest(String questId) {
/* 168 */     if (this.associatedStartQuests == null) {
/* 169 */       this.associatedStartQuests = new LinkedList<String>();
/*     */     }
/* 171 */     this.associatedStartQuests.add(questId);
/*     */   }
/*     */   
/*     */   public void addAssociatedHandInQuest(String questId) {
/* 175 */     if (this.associatedHandInQuests == null) {
/* 176 */       this.associatedHandInQuests = new LinkedList<String>();
/*     */     }
/* 178 */     this.associatedHandInQuests.add(questId);
/*     */   }
/*     */   
/*     */   public List<String> getAssociatedStartQuests() {
/* 182 */     return this.associatedStartQuests;
/*     */   }
/*     */   
/*     */   public List<String> getAssociatedHandInQuests() {
/* 186 */     return this.associatedHandInQuests;
/*     */   }
/*     */   
/*     */   public void setAssociatedWithQuests(boolean associatedWithQuests) {
/* 190 */     this.associatedWithQuests = associatedWithQuests;
/*     */   }
/*     */   
/*     */   public boolean isAssociatedWithQuests() {
/* 194 */     return this.associatedWithQuests;
/*     */   }
/*     */   
/*     */   public void addLootGroup(MonsterGroupLoot lootGroup) {
/* 198 */     this.lootGroups.add(lootGroup);
/*     */   }
/*     */   
/*     */   public List<MonsterGroupLoot> getLootGroups() {
/* 202 */     return this.lootGroups;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 207 */     return "MonsterDescription{id='" + this.id + '\'' + ", modelXml='" + this.modelXml + '\'' + ", name='" + this.name + '\'' + ", zone='" + this.zone + '\'' + ", level=" + this.level + ", ai='" + this.ai + '\'' + ", hardness=" + this.hardness + ", faction=" + this.faction + ", model='" + this.type + '\'' + ", rangeFollow=" + this.rangeFollow + ", rangeWander=" + this.rangeWander + ", rangeAggro=" + this.rangeAggro + ", monsterElement=" + this.monsterElement + ", usageItemIds=" + this.usageItemIds + ", usageItems=" + this.usageItems + ", associatedStartQuests=" + this.associatedStartQuests + ", associatedHandInQuests=" + this.associatedHandInQuests + ", lootGroups=" + this.lootGroups + ", associatedWithQuests=" + this.associatedWithQuests + ", radius=" + this.radius + ", xpModifier=" + this.xpModifier + ", questObjective='" + this.questObjective + '\'' + '}';
/*     */   }
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
/*     */   public float getRadius() {
/* 234 */     return this.radius;
/*     */   }
/*     */   
/*     */   public void setRadius(float radius) {
/* 238 */     this.radius = radius;
/*     */   }
/*     */   
/*     */   public void setXpModifier(double xpModifier) {
/* 242 */     this.xpModifier = xpModifier;
/*     */   }
/*     */   
/*     */   public double getXpModifier() {
/* 246 */     return this.xpModifier;
/*     */   }
/*     */   
/*     */   public void setQuestObjective(String questObjective) {
/* 250 */     this.questObjective = questObjective;
/*     */   }
/*     */   
/*     */   public String getQuestObjective() {
/* 254 */     return this.questObjective;
/*     */   }
/*     */   
/*     */   public String getSubtype() {
/* 258 */     return this.subtype;
/*     */   }
/*     */   
/*     */   public void setSubtype(String subtype) {
/* 262 */     this.subtype = subtype;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\monsters\MonsterDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */