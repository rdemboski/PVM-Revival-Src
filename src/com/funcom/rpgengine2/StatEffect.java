/*     */ package com.funcom.rpgengine2;
/*     */ 
/*     */ import com.funcom.rpgengine2.abilities.SourceProvider;
/*     */ import com.funcom.rpgengine2.abilities.TargetProvider;
/*     */ import com.funcom.rpgengine2.combat.Element;
/*     */ import com.funcom.rpgengine2.creatures.SourceEffectFilterSupport;
/*     */ import com.funcom.rpgengine2.items.Item;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ public class StatEffect {
/*  15 */   private static final Logger LOG = Logger.getLogger(StatEffect.class.getName());
/*     */   
/*     */   private final Item parentItem;
/*     */   
/*     */   private final SourceProvider sourceProvider;
/*     */   
/*     */   private final TargetProvider targetProvider;
/*     */   private volatile Persistence persistence;
/*     */   private volatile int originalValue;
/*     */   private volatile int bonusValue;
/*     */   private volatile Short statId;
/*     */   private volatile Element element;
/*     */   private Map<String, Float> abilityValues;
/*     */   
/*     */   public StatEffect(Item parentItem, SourceProvider sourceProvider, TargetProvider targetProvider, Short statId, Persistence persistence, int originalValue, Element element) {
/*  30 */     this.parentItem = parentItem;
/*  31 */     this.sourceProvider = sourceProvider;
/*  32 */     this.targetProvider = targetProvider;
/*  33 */     this.statId = statId;
/*  34 */     this.persistence = persistence;
/*  35 */     this.originalValue = originalValue;
/*  36 */     this.element = element;
/*  37 */     this.abilityValues = new HashMap<String, Float>();
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getParentItem() {
/*  42 */     return this.parentItem;
/*     */   }
/*     */   
/*     */   public Persistence getPersistence() {
/*  46 */     return this.persistence;
/*     */   }
/*     */   
/*     */   public void setPersistence(Persistence persistence) {
/*  50 */     this.persistence = persistence;
/*     */   }
/*     */   
/*     */   public int getOriginalValue() {
/*  54 */     return this.originalValue;
/*     */   }
/*     */   
/*     */   public void setOriginalValue(int originalValue) {
/*  58 */     this.originalValue = originalValue;
/*     */   }
/*     */   
/*     */   public int getBonusValue() {
/*  62 */     return this.bonusValue;
/*     */   }
/*     */   
/*     */   public int getSum() {
/*  66 */     return this.originalValue + this.bonusValue;
/*     */   }
/*     */   
/*     */   public void setBonusValue(int bonusValue) {
/*  70 */     this.bonusValue = bonusValue;
/*     */   }
/*     */   
/*     */   public Short getStatId() {
/*  74 */     return this.statId;
/*     */   }
/*     */   
/*     */   public void setStatId(Short statId) {
/*  78 */     this.statId = statId;
/*     */   }
/*     */   
/*     */   public Element getElement() {
/*  82 */     return this.element;
/*     */   }
/*     */   
/*     */   public void setElement(Element element) {
/*  86 */     this.element = element;
/*     */   }
/*     */   
/*     */   public void addBonus(int bonusToAdd) {
/*  90 */     this.bonusValue += bonusToAdd;
/*     */   }
/*     */   
/*     */   public SourceProvider getSourceProvider() {
/*  94 */     return this.sourceProvider;
/*     */   }
/*     */   
/*     */   public TargetProvider getTargetProvider() {
/*  98 */     return this.targetProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public void commitBonus() {
/* 103 */     this.originalValue += this.bonusValue;
/* 104 */     this.bonusValue = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 109 */     return "StatEffect{sourceProvider=" + this.sourceProvider + ", targetProvider=" + this.targetProvider + ", persistence=" + this.persistence + ", originalValue=" + this.originalValue + ", bonusValue=" + this.bonusValue + ", statId=" + this.statId + ", element=" + this.element + '}';
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
/*     */   public void process(StatCollection stats) {
/* 121 */     Stat stat = stats.get(getStatId());
/* 122 */     String logBefore = null;
/* 123 */     boolean isInfo = LOG.isEnabledFor((Priority)Level.DEBUG);
/* 124 */     if (isInfo) {
/* 125 */       logBefore = stat.toString();
/*     */     }
/* 127 */     if (stat != null) {
/* 128 */       switch (getPersistence()) {
/*     */         
/*     */         case BASE:
/* 131 */           stat.addBase(getSum());
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 142 */     if (isInfo) {
/* 143 */       LOG.info("PROCESS EFFECT:\n before=" + logBefore + "\n after=" + stat.toString() + "\n effect=" + toString());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAbilityValue(String statName) {
/* 151 */     return ((Float)this.abilityValues.get(statName)).floatValue();
/*     */   }
/*     */   
/*     */   public void setAbilityValue(String str, float abilityValue) {
/* 155 */     this.abilityValues.put(str.intern(), Float.valueOf(abilityValue));
/*     */   }
/*     */   
/*     */   public boolean hasAbilityValue(String statName) {
/* 159 */     return this.abilityValues.containsKey(statName);
/*     */   }
/*     */   
/*     */   public void sourceFilter() {
/* 163 */     SourceEffectFilterSupport filterSupport = (SourceEffectFilterSupport)this.sourceProvider.getSourceObject().getSupport(SourceEffectFilterSupport.class);
/*     */     
/* 165 */     if (filterSupport != null)
/* 166 */       filterSupport.filterSourceEffect(this); 
/*     */   }
/*     */   
/*     */   public enum FieldType
/*     */   {
/* 171 */     ORIGINAL {
/*     */       public int getValue(StatEffect statEffect) {
/* 173 */         return statEffect.getOriginalValue();
/*     */       }
/*     */       
/*     */       public void setValue(StatEffect statEffect, int value) {
/* 177 */         statEffect.setOriginalValue(value);
/*     */       } },
/* 179 */     BONUS {
/*     */       public int getValue(StatEffect statEffect) {
/* 181 */         return statEffect.getBonusValue();
/*     */       }
/*     */       
/*     */       public void setValue(StatEffect statEffect, int value) {
/* 185 */         statEffect.setBonusValue(value);
/*     */       }
/*     */     };
/*     */     
/*     */     public abstract int getValue(StatEffect param1StatEffect);
/*     */     
/*     */     public abstract void setValue(StatEffect param1StatEffect, int param1Int);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\StatEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */