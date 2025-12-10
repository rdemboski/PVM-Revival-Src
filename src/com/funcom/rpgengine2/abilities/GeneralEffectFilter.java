/*     */ package com.funcom.rpgengine2.abilities;
/*     */ 
/*     */ import com.funcom.commons.localization.JavaLocalization;
/*     */ import com.funcom.rpgengine2.Persistence;
/*     */ import com.funcom.rpgengine2.StatEffect;
/*     */ import com.funcom.rpgengine2.abilities.values.GeneralValueParser;
/*     */ import com.funcom.rpgengine2.abilities.values.Value;
/*     */ import com.funcom.rpgengine2.combat.Element;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.creatures.SourceEffectFilterSupport;
/*     */ import com.funcom.rpgengine2.creatures.StatEffectSupport;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.loader.AbilityParams;
/*     */ import com.funcom.rpgengine2.loader.RpgLoader;
/*     */ import com.funcom.rpgengine2.loader.TranslatedEnum;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ public class GeneralEffectFilter
/*     */   extends AbstractAbility implements EffectFilter {
/*  22 */   private static final Logger LOG = Logger.getLogger(GeneralEffectFilter.class.getName());
/*     */   
/*     */   private int priority;
/*     */   private Persistence condPersistence;
/*     */   private NumberState condOriginal;
/*     */   private NumberState condBonus;
/*     */   private Short condStat;
/*     */   private Element condElement;
/*     */   private StatEffect.FieldType effectField;
/*     */   private EffectAssignmentOperator effectAssignmentOp;
/*     */   private Value effectValue1;
/*     */   private Value effectValue2;
/*     */   private EffectOperator effectOp;
/*     */   private boolean sourceFilter;
/*     */   private boolean equipRequired;
/*     */   
/*     */   public int getPriority() {
/*  39 */     return this.priority;
/*     */   }
/*     */   
/*     */   public void addAbility(RpgEntity rpgEntity) {
/*  43 */     if (isSourceFilter()) {
/*  44 */       ((SourceEffectFilterSupport)rpgEntity.getSupport(SourceEffectFilterSupport.class)).addUniqueSourceEffectFilters(this);
/*     */     } else {
/*  46 */       ((StatEffectSupport)rpgEntity.getSupport(StatEffectSupport.class)).addUniqueTargetEffectFilters(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeAbility(RpgEntity rpgEntity) {
/*  51 */     if (isSourceFilter()) {
/*  52 */       ((SourceEffectFilterSupport)rpgEntity.getSupport(SourceEffectFilterSupport.class)).removeUniqueSourceEffectFilters(this);
/*     */     } else {
/*  54 */       ((StatEffectSupport)rpgEntity.getSupport(StatEffectSupport.class)).removeUniqueTargetEffectFilters(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Value getEffectValue1() {
/*  59 */     return this.effectValue1;
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
/*     */   public void filter(StatEffect statEffect) {
/*  74 */     if (this.condPersistence != null && statEffect.getPersistence() != this.condPersistence) {
/*     */       return;
/*     */     }
/*  77 */     if (this.condOriginal != null && !this.condOriginal.matchState(statEffect.getOriginalValue())) {
/*     */       return;
/*     */     }
/*  80 */     if (this.condBonus != null && !this.condBonus.matchState(statEffect.getBonusValue())) {
/*     */       return;
/*     */     }
/*  83 */     if (this.condStat != null && !statEffect.getStatId().equals(this.condStat)) {
/*     */       return;
/*     */     }
/*  86 */     if (this.condElement != null && statEffect.getElement() != this.condElement) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  91 */     String logBeforeFilter = null;
/*  92 */     boolean isInfo = LOG.isEnabledFor((Priority)Level.INFO);
/*  93 */     if (isInfo) {
/*  94 */       logBeforeFilter = statEffect.toString();
/*     */     }
/*     */ 
/*     */     
/*  98 */     int total = 0;
/*     */     
/* 100 */     if (this.effectOp != null) {
/* 101 */       total = this.effectOp.eval(statEffect, this.effectValue1, this.effectValue2);
/* 102 */     } else if (this.effectValue1 != null) {
/* 103 */       total = this.effectValue1.getInt(statEffect);
/* 104 */     } else if (this.effectValue2 != null) {
/* 105 */       total = this.effectValue2.getInt(statEffect);
/*     */     } 
/*     */     
/* 108 */     this.effectAssignmentOp.assign(statEffect, this.effectField, total);
/*     */     
/* 110 */     if (isInfo) {
/* 111 */       LOG.info("FILTER EFFECT:" + getId() + ":" + "\n before=" + logBeforeFilter + "" + "\n after=" + statEffect);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLanguageKey() {
/* 120 */     if (this.id.equalsIgnoreCase("power")) {
/* 121 */       return "<img src=\"gui/v3/character_equipment/icons/attack_size.png\" height=\"20\" width=\"20\"/> " + this.effectValue1.toString() + " " + JavaLocalization.getInstance().getLocalizedRPGText("rpg.filter.power.text");
/*     */     }
/* 123 */     return super.getLanguageKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(AbilityParams params, RpgLoader loader) {
/* 130 */     if (params.getRecordCount() > 1) {
/* 131 */       throw new IllegalArgumentException("Multirow not supported for Effect Filter");
/*     */     }
/* 133 */     params.next();
/*     */     
/* 135 */     init(params);
/*     */     
/* 137 */     this.priority = params.getInt(AbilityParams.ParamName.PRIORITY);
/* 138 */     this.equipRequired = params.getYesNo(AbilityParams.ParamName.EQUIP_REQUIRED);
/*     */     
/* 140 */     this.sourceFilter = params.getBoolean(AbilityParams.ParamName.__SOURCE_FILTER);
/*     */     
/* 142 */     this.condPersistence = (Persistence)params.getEnumOptional(AbilityParams.ParamName.COND_PERSISTENCE, (Enum[])Persistence.values(), null);
/* 143 */     this.condOriginal = (NumberState)params.getEnumOptional(AbilityParams.ParamName.COND_ORIGINAL, (Enum[])NumberState.values(), null);
/* 144 */     this.condBonus = (NumberState)params.getEnumOptional(AbilityParams.ParamName.COND_BONUS, (Enum[])NumberState.values(), null);
/* 145 */     String condStatId = params.getStrOptional(AbilityParams.ParamName.COND_STAT, null);
/*     */     
/* 147 */     if (condStatId != null) {
/* 148 */       this.condStat = loader.getStatIdTranslator().translate(condStatId);
/*     */     } else {
/* 150 */       this.condStat = null;
/* 151 */     }  this.condElement = (Element)params.getEnumOptional(AbilityParams.ParamName.COND_ELEMENT, (Enum[])Element.values(), null);
/*     */     
/* 153 */     this.effectField = (StatEffect.FieldType)params.getEnum(AbilityParams.ParamName.EFFECT_FIELD, (Enum[])StatEffect.FieldType.values());
/* 154 */     this.effectAssignmentOp = (EffectAssignmentOperator)params.getEnum(AbilityParams.ParamName.EFFECT_ASSIGN_OP, (Enum[])EffectAssignmentOperator.values());
/* 155 */     GeneralValueParser valueParser = loader.getValueParser();
/*     */     
/* 157 */     String tmp = params.getStrOptional(AbilityParams.ParamName.EFFECT_1, null);
/* 158 */     if (tmp != null) {
/* 159 */       this.effectValue1 = valueParser.parse(tmp, loader);
/*     */     }
/*     */     
/* 162 */     this.effectOp = (EffectOperator)params.getEnumOptional(AbilityParams.ParamName.EFFECT_OPERATOR, (Enum[])EffectOperator.values(), null);
/*     */     
/* 164 */     tmp = params.getStrOptional(AbilityParams.ParamName.EFFECT_2, null);
/* 165 */     if (tmp != null) {
/* 166 */       this.effectValue2 = valueParser.parse(tmp, loader);
/*     */     }
/*     */     
/* 169 */     if (this.effectOp != null && (this.effectValue1 == null || this.effectValue2 == null))
/*     */     {
/* 171 */       throwException("missing value(s)");
/*     */     }
/*     */     
/* 174 */     if (this.effectValue1 != null && this.effectValue2 != null && this.effectOp == null)
/*     */     {
/* 176 */       throwException("missing op");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSourceFilter() {
/* 187 */     return this.sourceFilter;
/*     */   }
/*     */   
/*     */   public boolean isEquipRequired() {
/* 191 */     return this.equipRequired;
/*     */   }
/*     */   
/*     */   private void throwException(String msg) {
/* 195 */     throw new RuntimeException("Invalid effect filter, " + msg + ":" + " value1=" + this.effectValue1 + " effectOp=" + this.effectOp + " value2=" + this.effectValue2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private enum NumberState
/*     */   {
/* 202 */     POSITIVE {
/*     */       public boolean matchState(int value) {
/* 204 */         return (value > 0);
/*     */       }
/*     */     },
/* 207 */     NEGATIVE {
/*     */       public boolean matchState(int value) {
/* 209 */         return (value < 0);
/*     */       }
/*     */     };
/*     */     
/*     */     public abstract boolean matchState(int param1Int);
/*     */   }
/*     */   
/*     */   private enum EffectAssignmentOperator implements TranslatedEnum {
/* 217 */     SET {
/*     */       public final String getTranslationName() {
/* 219 */         return "=";
/*     */       }
/*     */       
/*     */       void assign(StatEffect statEffect, StatEffect.FieldType effectField, int value) {
/* 223 */         effectField.setValue(statEffect, value);
/*     */       }
/*     */     },
/* 226 */     MIN {
/*     */       public final String getTranslationName() {
/* 228 */         return "min";
/*     */       }
/*     */       
/*     */       void assign(StatEffect statEffect, StatEffect.FieldType effectField, int value) {
/* 232 */         value = Math.min(effectField.getValue(statEffect), value);
/* 233 */         effectField.setValue(statEffect, value);
/*     */       }
/*     */     },
/* 236 */     MAX {
/*     */       public final String getTranslationName() {
/* 238 */         return "max";
/*     */       }
/*     */       
/*     */       void assign(StatEffect statEffect, StatEffect.FieldType effectField, int value) {
/* 242 */         value = Math.max(effectField.getValue(statEffect), value);
/* 243 */         effectField.setValue(statEffect, value);
/*     */       }
/*     */     },
/* 246 */     ADD {
/*     */       public final String getTranslationName() {
/* 248 */         return "+=";
/*     */       }
/*     */       
/*     */       void assign(StatEffect statEffect, StatEffect.FieldType effectField, int value) {
/* 252 */         value += effectField.getValue(statEffect);
/* 253 */         effectField.setValue(statEffect, value);
/*     */       }
/*     */     };
/*     */     
/*     */     abstract void assign(StatEffect param1StatEffect, StatEffect.FieldType param1FieldType, int param1Int);
/*     */   }
/*     */   
/*     */   private enum EffectOperator implements TranslatedEnum {
/* 261 */     MUL {
/*     */       public final String getTranslationName() {
/* 263 */         return "*";
/*     */       }
/*     */ 
/*     */       
/*     */       int eval(StatEffect statEffect, Value value1, Value value2) {
/* 268 */         if (value1.isInteger() && value2.isInteger()) {
/* 269 */           return value1.getInt(statEffect) * value2.getInt(statEffect);
/*     */         }
/*     */         
/* 272 */         return (int)(value1.getFloat(statEffect) * value2.getFloat(statEffect));
/*     */       } },
/* 274 */     DIV {
/*     */       public final String getTranslationName() {
/* 276 */         return "/";
/*     */       }
/*     */ 
/*     */       
/*     */       int eval(StatEffect statEffect, Value value1, Value value2) {
/* 281 */         if (value1.isInteger() && value2.isInteger()) {
/* 282 */           int i = value2.getInt(statEffect);
/* 283 */           int j = value1.getInt(statEffect);
/* 284 */           if (i != 0) {
/* 285 */             return j / i;
/*     */           }
/* 287 */           return j;
/*     */         } 
/*     */         
/* 290 */         float left = value1.getFloat(statEffect);
/* 291 */         float right = value2.getFloat(statEffect);
/* 292 */         if (right != 0.0F) {
/* 293 */           return (int)(left / right);
/*     */         }
/* 295 */         return (int)left;
/*     */       }
/*     */     };
/*     */     
/*     */     abstract int eval(StatEffect param1StatEffect, Value param1Value1, Value param1Value2);
/*     */   }
/*     */   
/*     */   public int getDamage(ItemDescription description, RpgEntity statSupport, short statToLookFor) {
/* 303 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\GeneralEffectFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */