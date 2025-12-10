/*     */ package com.funcom.rpgengine2.abilities.values;
/*     */ 
/*     */ import com.funcom.rpgengine2.Stat;
/*     */ import com.funcom.rpgengine2.StatEffect;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.creatures.StatSupport;
/*     */ import com.funcom.rpgengine2.loader.RpgLoader;
/*     */ 
/*     */ 
/*     */ public class StatValueParser
/*     */   implements ValueParser
/*     */ {
/*     */   private boolean accessSource;
/*     */   
/*     */   public StatValueParser(boolean accessSource) {
/*  16 */     this.accessSource = accessSource;
/*     */   }
/*     */ 
/*     */   
/*     */   public Value parse(String str, RpgLoader loader) {
/*  21 */     String[] parts = str.trim().split("\\s*\\.\\s*");
/*     */     
/*  23 */     if (parts.length < 2) {
/*  24 */       throw new RuntimeException("Cannot parse stat access, access type missing: path=" + str);
/*     */     }
/*     */     
/*  27 */     Short statId = loader.getStatIdTranslator().translate(parts[0]);
/*  28 */     String type = parts[1];
/*     */     
/*  30 */     if ("sum".equalsIgnoreCase(type)) {
/*  31 */       return new SumValue(statId, this.accessSource);
/*     */     }
/*  33 */     if ("base".equalsIgnoreCase(type)) {
/*  34 */       return new BaseValue(statId, this.accessSource);
/*     */     }
/*  36 */     if ("modifier".equalsIgnoreCase(type) || "mod".equalsIgnoreCase(type))
/*     */     {
/*  38 */       return new ModifierValue(statId, this.accessSource);
/*     */     }
/*     */     
/*  41 */     throw new RuntimeException("Unknown type: type=" + type);
/*     */   }
/*     */   
/*     */   private static abstract class AbstractStatValue implements Value {
/*     */     Short statId;
/*     */     private boolean source;
/*     */     
/*     */     protected AbstractStatValue(Short statId, boolean source) {
/*  49 */       this.statId = statId;
/*  50 */       this.source = source;
/*     */     }
/*     */     
/*     */     public boolean isInteger() {
/*  54 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getInt(StatEffect statEffect) {
/*     */       RpgEntity statParent;
/*  60 */       if (this.source) {
/*  61 */         statParent = statEffect.getSourceProvider().getSourceObject();
/*     */       } else {
/*  63 */         statParent = statEffect.getTargetProvider().getTargetObject();
/*     */       } 
/*  65 */       return getValue(((StatSupport)statParent.getSupport(StatSupport.class)).getStatById(this.statId.shortValue()));
/*     */     }
/*     */     
/*     */     protected abstract int getValue(Stat param1Stat);
/*     */     
/*     */     public float getFloat(StatEffect statEffect) {
/*  71 */       return getInt(statEffect);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SumValue extends AbstractStatValue {
/*     */     private SumValue(Short statId, boolean accessSource) {
/*  77 */       super(statId, accessSource);
/*     */     }
/*     */     
/*     */     protected int getValue(Stat stat) {
/*  81 */       return stat.getSum();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class BaseValue extends AbstractStatValue {
/*     */     private BaseValue(Short statId, boolean accessSource) {
/*  87 */       super(statId, accessSource);
/*     */     }
/*     */     
/*     */     protected int getValue(Stat stat) {
/*  91 */       return stat.getBase();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ModifierValue extends AbstractStatValue {
/*     */     private ModifierValue(Short statId, boolean accessSource) {
/*  97 */       super(statId, accessSource);
/*     */     }
/*     */     
/*     */     protected int getValue(Stat stat) {
/* 101 */       return stat.getModifier();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\values\StatValueParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */