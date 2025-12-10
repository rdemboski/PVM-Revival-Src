/*     */ package com.funcom.tcg.client.model.rpg;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientItemVisual
/*     */ {
/*     */   private String id;
/*     */   private String maleMeshPath;
/*     */   private String maleTexturePath;
/*     */   private String femaleMeshPath;
/*     */   private String femaleTexturePath;
/*     */   private String hairType;
/*     */   private ShowState showHair;
/*     */   private ShowState showHead;
/*     */   private boolean transparent;
/*     */   private String alwaysOnDfxPath;
/*     */   
/*     */   public String getId() {
/*  20 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  24 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getMaleMeshPath() {
/*  28 */     return this.maleMeshPath;
/*     */   }
/*     */   
/*     */   public void setMaleMeshPath(String maleMeshPath) {
/*  32 */     this.maleMeshPath = maleMeshPath;
/*     */   }
/*     */   
/*     */   public String getMaleTexturePath() {
/*  36 */     return this.maleTexturePath;
/*     */   }
/*     */   
/*     */   public void setMaleTexturePath(String maleTexturePath) {
/*  40 */     this.maleTexturePath = maleTexturePath;
/*     */   }
/*     */   
/*     */   public String getFemaleMeshPath() {
/*  44 */     return this.femaleMeshPath;
/*     */   }
/*     */   
/*     */   public void setFemaleMeshPath(String femaleMeshPath) {
/*  48 */     this.femaleMeshPath = femaleMeshPath;
/*     */   }
/*     */   
/*     */   public String getFemaleTexturePath() {
/*  52 */     return this.femaleTexturePath;
/*     */   }
/*     */   
/*     */   public void setFemaleTexturePath(String femaleTexturePath) {
/*  56 */     this.femaleTexturePath = femaleTexturePath;
/*     */   }
/*     */   
/*     */   public String getHairType() {
/*  60 */     return this.hairType;
/*     */   }
/*     */   
/*     */   public void setHairType(String hairType) {
/*  64 */     this.hairType = hairType;
/*     */   }
/*     */   
/*     */   public ShowState getShowHair() {
/*  68 */     return this.showHair;
/*     */   }
/*     */   
/*     */   public void setShowHair(int showHair) {
/*  72 */     this.showHair = ShowState.getStateForIndex(showHair);
/*     */   }
/*     */   
/*     */   public ShowState getShowHead() {
/*  76 */     return this.showHead;
/*     */   }
/*     */   
/*     */   public void setShowHead(int showHead) {
/*  80 */     this.showHead = ShowState.getStateForIndex(showHead);
/*     */   }
/*     */   
/*     */   public boolean isTransparent() {
/*  84 */     return this.transparent;
/*     */   }
/*     */   
/*     */   public void setTransparent(boolean transparent) {
/*  88 */     this.transparent = transparent;
/*     */   }
/*     */   
/*     */   public String getAlwaysOnDfxPath() {
/*  92 */     return this.alwaysOnDfxPath;
/*     */   }
/*     */   
/*     */   public void setAlwaysOnDfxPath(String alwaysOnDfxPath) {
/*  96 */     this.alwaysOnDfxPath = alwaysOnDfxPath;
/*     */   }
/*     */   
/*     */   public enum ShowState {
/* 100 */     SHOW, HIDE, INDIFFERENT;
/*     */     
/*     */     public static ShowState getStateForIndex(int showIndex) {
/* 103 */       switch (showIndex) {
/*     */         case 0:
/* 105 */           return HIDE;
/*     */         case 1:
/* 107 */           return SHOW;
/*     */       } 
/* 109 */       return INDIFFERENT;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\ClientItemVisual.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */