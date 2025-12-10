/*     */ package com.funcom.rpgengine2.abilities;
/*     */ 
/*     */ import com.funcom.rpgengine2.StatEffect;
/*     */ import com.funcom.rpgengine2.combat.DamageCrutchMult;
/*     */ import com.funcom.rpgengine2.combat.DamageResistanceCalc;
/*     */ import com.funcom.rpgengine2.combat.Element;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.creatures.StatEffectSupport;
/*     */ import com.funcom.rpgengine2.creatures.StatSupport;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractDamageFilter
/*     */   implements EffectFilter
/*     */ {
/*  21 */   private static final Logger LOG = Logger.getLogger(AbstractDamageFilter.class.getName());
/*     */   
/*     */   public String getLanguageKey() {
/*  24 */     throw new UnsupportedOperationException();
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
/*     */   public int getPriority() {
/*  36 */     return Integer.MAX_VALUE;
/*     */   }
/*     */   
/*     */   public void addAbility(RpgEntity rpgEntity) {
/*  40 */     ((StatEffectSupport)rpgEntity.getSupport(StatEffectSupport.class)).addUniqueTargetEffectFilters(this);
/*     */   }
/*     */   
/*     */   public void removeAbility(RpgEntity rpgEntity) {
/*  44 */     ((StatEffectSupport)rpgEntity.getSupport(StatEffectSupport.class)).removeUniqueTargetEffectFilters(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void filter(StatEffect statEffect) {
/*  49 */     RpgEntity source = statEffect.getSourceProvider().getSourceObject();
/*  50 */     RpgEntity target = statEffect.getTargetProvider().getTargetObject();
/*     */     
/*  52 */     int resistance = getResistance(target, statEffect.getElement());
/*     */ 
/*     */     
/*  55 */     int sourceLevel = ((StatSupport)source.getSupport(StatSupport.class)).getLevel();
/*  56 */     int targetLevel = ((StatSupport)target.getSupport(StatSupport.class)).getLevel();
/*  57 */     double damageMultiplier = DamageResistanceCalc.getDamageMultiplier(resistance, sourceLevel);
/*  58 */     double crutchMultiplier = DamageCrutchMult.getDamageMultiplier(targetLevel, sourceLevel);
/*     */     
/*  60 */     statEffect.commitBonus();
/*  61 */     int originalValue = statEffect.getOriginalValue();
/*  62 */     int value = (int)Math.min(originalValue * damageMultiplier * crutchMultiplier, -1.0D);
/*  63 */     statEffect.setOriginalValue(value);
/*     */     
/*  65 */     if (LOG.isEnabledFor((Priority)Level.INFO)) {
/*  66 */       LOG.info("RESISTANCE:\n '" + target + "' resistance for '" + statEffect.getElement() + "': " + resistance + "\n attacker/source level: " + sourceLevel + "\n damageMultiplier: " + damageMultiplier + "\n originalValue=" + value + "\n newValue=" + value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract int getResistance(RpgEntity paramRpgEntity, Element paramElement);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSourceFilter() {
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEquipRequired() {
/*  93 */     return false;
/*     */   }
/*     */   
/*     */   public String getId() {
/*  97 */     return getClass().getSimpleName();
/*     */   }
/*     */   
/*     */   public int getLevelFrom() {
/* 101 */     return Integer.MIN_VALUE;
/*     */   }
/*     */   
/*     */   public int getLevelTo() {
/* 105 */     return Integer.MAX_VALUE;
/*     */   }
/*     */   
/*     */   public String getDFXReference() {
/* 109 */     return "";
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 113 */     int sourceLevel = 50;
/*     */     
/* 115 */     for (int i = 0; i < 510; i += 50) {
/* 116 */       double m = DamageResistanceCalc.getDamageMultiplier(i, sourceLevel);
/* 117 */       System.out.println(i + ": " + m);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 123 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcons() {
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDamage(ItemDescription description, RpgEntity statSupport, short statToLookFor) {
/* 133 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\AbstractDamageFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */