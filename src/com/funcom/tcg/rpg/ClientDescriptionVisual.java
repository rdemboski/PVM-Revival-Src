/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ import com.funcom.commons.localization.JavaLocalization;
/*    */ import com.funcom.rpgengine2.items.PlayerDescription;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientDescriptionVisual
/*    */ {
/*    */   private ClientDescriptionType type;
/*    */   private String classId;
/*    */   private String name;
/*    */   private String maleMeshPath;
/*    */   private String maleTexturePath;
/*    */   private String femaleMeshPath;
/*    */   private String femaleTexturePath;
/*    */   
/*    */   public ClientDescriptionType getType() {
/* 19 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(ClientDescriptionType type) {
/* 23 */     this.type = type;
/*    */   }
/*    */   
/*    */   public String getClassId() {
/* 27 */     return this.classId;
/*    */   }
/*    */   
/*    */   public void setClassId(String classId) {
/* 31 */     this.classId = classId;
/*    */   }
/*    */   
/*    */   public String getMaleMeshPath() {
/* 35 */     return this.maleMeshPath;
/*    */   }
/*    */   
/*    */   public void setMaleMeshPath(String maleMeshPath) {
/* 39 */     this.maleMeshPath = maleMeshPath;
/*    */   }
/*    */   
/*    */   public String getMaleTexturePath() {
/* 43 */     return this.maleTexturePath;
/*    */   }
/*    */   
/*    */   public void setMaleTexturePath(String maleTexturePath) {
/* 47 */     this.maleTexturePath = maleTexturePath;
/*    */   }
/*    */   
/*    */   public String getFemaleMeshPath() {
/* 51 */     return this.femaleMeshPath;
/*    */   }
/*    */   
/*    */   public void setFemaleMeshPath(String femaleMeshPath) {
/* 55 */     this.femaleMeshPath = femaleMeshPath;
/*    */   }
/*    */   
/*    */   public String getFemaleTexturePath() {
/* 59 */     return this.femaleTexturePath;
/*    */   }
/*    */   
/*    */   public void setFemaleTexturePath(String femaleTexturePath) {
/* 63 */     this.femaleTexturePath = femaleTexturePath;
/*    */   }
/*    */   
/*    */   public String getMeshPath(PlayerDescription.Gender gender) {
/* 67 */     if (gender == PlayerDescription.Gender.MALE)
/* 68 */       return this.maleMeshPath; 
/* 69 */     if (gender == PlayerDescription.Gender.FEMALE) {
/* 70 */       return this.femaleMeshPath;
/*    */     }
/* 72 */     throw new IllegalArgumentException("Unknown gender");
/*    */   }
/*    */   
/*    */   public String getTexturePath(PlayerDescription.Gender gender) {
/* 76 */     if (gender == PlayerDescription.Gender.MALE)
/* 77 */       return this.maleTexturePath; 
/* 78 */     if (gender == PlayerDescription.Gender.FEMALE) {
/* 79 */       return this.femaleTexturePath;
/*    */     }
/* 81 */     throw new IllegalArgumentException("Unknown gender");
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 85 */     this.name = name;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 89 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getLocalizedName() {
/* 93 */     return JavaLocalization.getInstance().getLocalizedRPGText(this.name);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\ClientDescriptionVisual.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */