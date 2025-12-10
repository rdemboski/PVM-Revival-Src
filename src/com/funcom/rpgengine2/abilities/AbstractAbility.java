/*    */ package com.funcom.rpgengine2.abilities;
/*    */ 
/*    */ import com.funcom.commons.localization.JavaLocalization;
/*    */ import com.funcom.rpgengine2.loader.AbilityParams;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractAbility
/*    */   implements Ability
/*    */ {
/*    */   protected String id;
/*    */   protected int levelFrom;
/*    */   protected int levelTo;
/*    */   protected String languageKey;
/*    */   protected String dfxReference;
/*    */   protected String icon;
/*    */   protected boolean initialized;
/*    */   protected Set<String> cureStatuses;
/*    */   
/*    */   protected void init(AbilityParams params) {
/* 23 */     if (!this.initialized) {
/* 24 */       if (!params.isReadingStarted()) {
/* 25 */         throw new IllegalArgumentException("Params is not in record, advance to next record before call");
/*    */       }
/*    */       
/* 28 */       this.id = params.getStr(AbilityParams.ParamName.ID);
/*    */       
/* 30 */       this.levelFrom = params.getInt(AbilityParams.ParamName.REQ_LEVEL_FROM);
/* 31 */       this.levelTo = params.getLevel(AbilityParams.ParamName.REQ_LEVEL_TO);
/*    */       
/* 33 */       String languageKey = (getClass().getSimpleName() + ": NO LANGUAGE KEY").intern();
/* 34 */       this.languageKey = params.getStrOptional(AbilityParams.ParamName.LANGUAGE_KEY, languageKey);
/* 35 */       this.dfxReference = params.getStrOptional(AbilityParams.ParamName.DFX_REFERENCE, "");
/* 36 */       this.icon = params.getStrOptional(AbilityParams.ParamName.SMALL_ICON, "");
/*    */       
/* 38 */       this.initialized = true;
/*    */     } 
/*    */   }
/*    */   
/*    */   public final String getId() {
/* 43 */     return this.id;
/*    */   }
/*    */   
/*    */   public final int getLevelFrom() {
/* 47 */     return this.levelFrom;
/*    */   }
/*    */   
/*    */   public final int getLevelTo() {
/* 51 */     return this.levelTo;
/*    */   }
/*    */   
/*    */   public final String getDFXReference() {
/* 55 */     return this.dfxReference;
/*    */   }
/*    */   
/*    */   public String getLanguageKey() {
/* 59 */     if (!this.languageKey.contains("NO LANGUAGE KEY")) {
/* 60 */       return JavaLocalization.getInstance().getLocalizedRPGText(this.languageKey);
/*    */     }
/* 62 */     return "";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 67 */     return this.icon;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcons() {
/* 72 */     return this.icon;
/*    */   }
/*    */   
/*    */   protected <D> Set<D> addCureData(D data, Set<D> set) {
/* 76 */     if (data != null) {
/* 77 */       if (set == null) {
/* 78 */         set = new HashSet<D>();
/*    */       }
/* 80 */       set.add(data);
/*    */     } 
/*    */     
/* 83 */     return set;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\AbstractAbility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */