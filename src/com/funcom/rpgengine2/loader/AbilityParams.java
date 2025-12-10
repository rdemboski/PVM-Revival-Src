/*     */ package com.funcom.rpgengine2.loader;
/*     */ import com.funcom.commons.localization.Localization;
/*     */ import com.funcom.commons.localization.LocalizationException;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class AbilityParams {
/*  13 */   private Map<ParamName, Object> commonParams = new HashMap<ParamName, Object>();
/*  14 */   private List<Map<ParamName, String>> records = new ArrayList<Map<ParamName, String>>();
/*  15 */   private int recordIndex = -1;
/*     */   
/*  17 */   private TranslationVariables translationVariables = new TranslationVariables();
/*     */   private Localization localization;
/*     */   
/*     */   public AbilityParams() {
/*  21 */     this.localization = new Localization();
/*     */   }
/*     */   
/*     */   public int getRecordCount() {
/*  25 */     return this.records.size();
/*     */   }
/*     */   
/*     */   public boolean next() {
/*  29 */     if (this.recordIndex < this.records.size() - 1) {
/*  30 */       this.recordIndex++;
/*  31 */       return true;
/*     */     } 
/*     */     
/*  34 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStr(ParamName name) {
/*  39 */     Object value = null;
/*     */     
/*  41 */     if (this.recordIndex >= 0 && this.recordIndex < this.records.size()) {
/*     */       
/*  43 */       Map<ParamName, String> record = this.records.get(this.recordIndex);
/*  44 */       value = record.get(name);
/*     */     } 
/*     */     
/*  47 */     if (value == null) {
/*  48 */       value = this.commonParams.get(name);
/*     */     }
/*     */     
/*  51 */     if (value != null) {
/*  52 */       String str = value.toString();
/*     */       
/*     */       try {
/*  55 */         return internResolveVariables(str);
/*  56 */       } catch (LocalizationException e) {
/*  57 */         throw new IllegalArgumentException("Error while formatting field: name=" + name + " value=" + str);
/*     */       } 
/*     */     } 
/*     */     
/*  61 */     return null;
/*     */   }
/*     */   
/*     */   private String internResolveVariables(String str) throws LocalizationException {
/*  65 */     return this.localization.formatString(str, this.translationVariables);
/*     */   }
/*     */   
/*     */   public void setCommon(ParamName paramName, Object value) {
/*  69 */     this.commonParams.put(paramName, value);
/*     */   }
/*     */   
/*     */   public void setRecords(List<String[]> fieldList, ParamName... paramNames) {
/*  73 */     for (String[] fields : fieldList) {
/*     */       
/*  75 */       if (fields.length < paramNames.length) {
/*  76 */         throw new IllegalArgumentException("too few fields, expected at least: expected fields=" + paramNames.length + " actual fields=" + fields.length + " value=" + Arrays.toString(fields));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  82 */       Map<ParamName, String> recordParams = new HashMap<ParamName, String>();
/*     */       
/*  84 */       for (int i = 0; i < paramNames.length; i++) {
/*  85 */         String field = fields[i];
/*     */         
/*  87 */         if (field == null) {
/*  88 */           throw new IllegalArgumentException("field cannot be NULL: fieldName=" + paramNames[i]);
/*     */         }
/*     */         
/*  91 */         recordParams.put(paramNames[i], field);
/*     */       } 
/*     */       
/*  94 */       this.records.add(recordParams);
/*     */     } 
/*     */   }
/*     */   
/*     */   public <E extends Enum> E getEnum(ParamName paramName, E[] enums) {
/*  99 */     String enumName = getStr(paramName);
/*     */     
/* 101 */     for (E enm : enums) {
/* 102 */       if (enm.name().equalsIgnoreCase(enumName)) {
/* 103 */         return enm;
/*     */       }
/* 105 */       if (enm instanceof TranslatedEnum && ((TranslatedEnum)enm).getTranslationName().equalsIgnoreCase(enumName))
/*     */       {
/* 107 */         return enm;
/*     */       }
/*     */     } 
/*     */     
/* 111 */     throw new IllegalArgumentException("Enum not found: wanted enum='" + enumName + "'" + " potential=" + Arrays.toString(enums));
/*     */   }
/*     */ 
/*     */   
/*     */   public <E extends Enum> E getEnumOptional(ParamName paramName, E[] enums, E emptyNameValue) {
/* 116 */     String enumName = getStr(paramName);
/*     */     
/* 118 */     if (enumName.length() == 0) {
/* 119 */       return emptyNameValue;
/*     */     }
/*     */     
/* 122 */     for (E enm : enums) {
/* 123 */       if (enm.name().equalsIgnoreCase(enumName)) {
/* 124 */         return enm;
/*     */       }
/* 126 */       if (enm instanceof TranslatedEnum && ((TranslatedEnum)enm).getTranslationName().equalsIgnoreCase(enumName))
/*     */       {
/* 128 */         return enm;
/*     */       }
/*     */     } 
/*     */     
/* 132 */     throw new IllegalArgumentException("Enum not found: wanted enum=" + enumName + " potential=" + Arrays.toString(enums));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFloat(ParamName paramName) {
/* 137 */     return Float.parseFloat(getStr(paramName));
/*     */   }
/*     */   public float getFloat(ParamName paramName, float defaultValue) {
/* 140 */     String valueStr = getStr(paramName);
/* 141 */     if (valueStr.trim().isEmpty())
/* 142 */       return defaultValue; 
/* 143 */     return Float.parseFloat(valueStr);
/*     */   }
/*     */   
/*     */   public float getFloatOptional(ParamName paramName, float emptyStringValue) {
/* 147 */     String str = getStr(paramName);
/* 148 */     if (str == null || str.length() == 0) {
/* 149 */       return emptyStringValue;
/*     */     }
/* 151 */     return Float.parseFloat(str);
/*     */   }
/*     */   
/*     */   public int getInt(ParamName paramName) {
/* 155 */     return Integer.parseInt(getStr(paramName));
/*     */   }
/*     */   
/*     */   public int getLevel(ParamName paramName) {
/* 159 */     String str = getStr(paramName);
/* 160 */     if (str.length() == 0 || "inf".equalsIgnoreCase(str))
/*     */     {
/* 162 */       return Integer.MAX_VALUE;
/*     */     }
/* 164 */     return Integer.parseInt(str);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIntOptional(ParamName paramName, int emptyStrValue) {
/* 169 */     String str = getStr(paramName);
/* 170 */     if (str == null || str.length() == 0) {
/* 171 */       return emptyStrValue;
/*     */     }
/* 173 */     return Integer.parseInt(str);
/*     */   }
/*     */   
/*     */   public String getStrOptional(ParamName paramName, String emptyStrValue) {
/* 177 */     String str = getStr(paramName);
/*     */     
/* 179 */     if (str == null || str.length() == 0) {
/* 180 */       return emptyStrValue;
/*     */     }
/*     */     
/* 183 */     return str;
/*     */   }
/*     */   
/*     */   public String getStrNonEmpty(ParamName paramName) {
/* 187 */     String str = getStr(paramName);
/*     */     
/* 189 */     if (str == null || str.length() == 0) {
/* 190 */       throw new IllegalArgumentException("fields cannot be empty/NULL: fieldName=" + paramName);
/*     */     }
/*     */     
/* 193 */     return str;
/*     */   }
/*     */   
/*     */   public boolean getBoolean(ParamName paramName) {
/* 197 */     String str = getStr(paramName);
/*     */     
/* 199 */     return Boolean.parseBoolean(str);
/*     */   }
/*     */   
/*     */   public boolean getYesNo(ParamName paramName) {
/* 203 */     String str = getStr(paramName);
/*     */     
/* 205 */     return LoaderUtils.parseYesNo(str);
/*     */   }
/*     */   
/*     */   public void resolveVariables(AbilityParams params) throws LocalizationException {
/* 209 */     Map<ParamName, Object> map = this.commonParams;
/* 210 */     for (Map.Entry<ParamName, Object> entry : map.entrySet()) {
/* 211 */       Object value = entry.getValue();
/* 212 */       String resolvedStr = params.internResolveVariables(value.toString());
/* 213 */       if (value != resolvedStr) {
/* 214 */         entry.setValue(resolvedStr);
/*     */       }
/*     */     } 
/* 217 */     int size = this.records.size();
/* 218 */     for (int i = 0; i < size; i++) {
/* 219 */       Map<ParamName, String> paramNameStringMap = this.records.get(i);
/* 220 */       for (Map.Entry<ParamName, String> entry : paramNameStringMap.entrySet()) {
/* 221 */         Object value = entry.getValue();
/* 222 */         String resolvedStr = params.internResolveVariables(value.toString());
/* 223 */         if (value != resolvedStr) {
/* 224 */           entry.setValue(resolvedStr);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isReadingStarted() {
/* 231 */     return (this.recordIndex >= 0);
/*     */   }
/*     */   
/*     */   private class TranslationVariables extends AbstractMap<String, Object> {
/*     */     public Set<Map.Entry<String, Object>> entrySet() {
/* 236 */       return null;
/*     */     }
/*     */     private TranslationVariables() {}
/*     */     public String get(Object key) {
/* 240 */       AbilityParams.ParamName paramName = AbilityParams.ParamName.valueOf(key.toString().toUpperCase());
/* 241 */       return AbilityParams.this.getStr(paramName);
/*     */     }
/*     */   }
/*     */   
/*     */   public enum ParamName {
/* 246 */     DFX_REFERENCE,
/*     */     
/* 248 */     REQ_LEVEL_FROM, REQ_LEVEL_TO,
/*     */     
/* 250 */     X, Y, Z, K, KA, KB, KC,
/*     */     
/* 252 */     CRIT, VARIANCE,
/*     */     
/* 254 */     PRIORITY, EQUIP_REQUIRED,
/* 255 */     COND_PERSISTENCE, COND_ORIGINAL, COND_BONUS, COND_STAT, COND_ELEMENT,
/* 256 */     EFFECT_FIELD, EFFECT_ASSIGN_OP, EFFECT_1, EFFECT_OPERATOR, EFFECT_2,
/*     */     
/* 258 */     ID, ICON, BUFF_DFX_REFERENCE, SHAPE_REF,
/* 259 */     ADD_STAT_ID, ADD_STAT_PERSISTENCE, ADD_STAT_MUL,
/* 260 */     ADD_FIXED_VALUE, ADD_PET_LEVEL_MULT, ADD_PET_LEVEL_SQUARE_MUL,
/* 261 */     ADD_ITEM_LEVEL_MUL, ADD_SOURCE_LEVEL_MUL,
/* 262 */     ADD_SOURCE_LEVEL_SQUARE_MUL,
/* 263 */     TARGET_STAT_ID, TARGET_STAT_PERSISTENCE,
/* 264 */     ELEMENT,
/*     */     
/* 266 */     STATUS,
/*     */     
/* 268 */     REFLECTION_VALUE,
/*     */     
/* 270 */     TARGET_BUFF,
/*     */     
/* 272 */     MIN_DISTANCE, MAX_DISTANCE,
/* 273 */     TRIGGER_WIDTH, TRIGGER_HEIGHT,
/* 274 */     HOMING, LOCKING_WIDTH, LOCKING_HEIGHT,
/* 275 */     MOVEMENT_DISTANCE, MOVEMENT_TIME, MOVEMENT_ANGLE_DEGREE,
/* 276 */     OFFSET_X, OFFSET_Y, OFFSET_DISTANCE, TUNNELING,
/* 277 */     MULTIPLE_HITS_ALLOWED, REFLECTABLE, COLLISION_HEIGHT,
/*     */     
/* 279 */     LANGUAGE_KEY, SPELL_POWER_COEF, SPELL_POWER_COEF_A, SPELL_POWER_COEF_B, SPELL_POWER_COEF_C, SMALL_ICON,
/*     */     
/* 281 */     DURATION, TOTAL_TICKS, INFINITE, ABILITY_ID, TIER, ABILITY_ID2, TIER2, MAX_CHARGES,
/* 282 */     MONSTER_ID, BRAIN_FILE, NUMBER, SPAWN_RADIUS, COPY_STAT1, COPY_STAT2, COPY_STAT3, COPY_STAT4,
/*     */     
/* 284 */     __SOURCE_FILTER;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loader\AbilityParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */