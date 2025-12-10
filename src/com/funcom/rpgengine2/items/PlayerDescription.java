/*     */ package com.funcom.rpgengine2.items;
/*     */ 
/*     */ public class PlayerDescription
/*     */ {
/*     */   protected Gender gender;
/*     */   protected String skinColorId;
/*     */   protected String faceId;
/*     */   protected String eyeColorId;
/*     */   protected String hairId;
/*     */   protected String hairColorId;
/*     */   
/*     */   public PlayerDescription() {}
/*     */   
/*     */   public PlayerDescription(Gender gender, String skinColorId, String faceId, String eyeColorId, String hairId, String hairColorId) {
/*  15 */     this.gender = gender;
/*  16 */     this.skinColorId = skinColorId;
/*  17 */     this.faceId = faceId;
/*  18 */     this.eyeColorId = eyeColorId;
/*  19 */     this.hairId = hairId;
/*  20 */     this.hairColorId = hairColorId;
/*     */   }
/*     */   
/*     */   public Gender getGender() {
/*  24 */     return this.gender;
/*     */   }
/*     */   
/*     */   public void setGender(Gender gender) {
/*  28 */     this.gender = gender;
/*     */   }
/*     */   
/*     */   public String getSkinColorId() {
/*  32 */     return this.skinColorId;
/*     */   }
/*     */   
/*     */   public void setSkinColorId(String skinColorId) {
/*  36 */     this.skinColorId = skinColorId;
/*     */   }
/*     */   
/*     */   public String getFaceId() {
/*  40 */     return this.faceId;
/*     */   }
/*     */   
/*     */   public void setFaceId(String faceId) {
/*  44 */     this.faceId = faceId;
/*     */   }
/*     */   
/*     */   public String getEyeColorId() {
/*  48 */     return this.eyeColorId;
/*     */   }
/*     */   
/*     */   public void setEyeColorId(String eyeColorId) {
/*  52 */     this.eyeColorId = eyeColorId;
/*     */   }
/*     */   
/*     */   public String getHairId() {
/*  56 */     return this.hairId;
/*     */   }
/*     */   
/*     */   public void setHairId(String hairId) {
/*  60 */     this.hairId = hairId;
/*     */   }
/*     */   
/*     */   public String getHairColorId() {
/*  64 */     return this.hairColorId;
/*     */   }
/*     */   
/*     */   public void setHairColorId(String hairColorId) {
/*  68 */     this.hairColorId = hairColorId;
/*     */   }
/*     */   
/*     */   public void set(PlayerDescription src) {
/*  72 */     this.gender = src.gender;
/*  73 */     this.skinColorId = src.skinColorId;
/*  74 */     this.faceId = src.faceId;
/*  75 */     this.eyeColorId = src.eyeColorId;
/*  76 */     this.hairId = src.hairId;
/*  77 */     this.hairColorId = src.hairColorId;
/*     */   }
/*     */   
/*     */   public enum Gender {
/*  81 */     MALE((byte)0), FEMALE((byte)1);
/*     */     private final byte id;
/*     */     
/*     */     Gender(byte id) {
/*  85 */       this.id = id;
/*     */     }
/*     */     
/*     */     public byte getId() {
/*  89 */       return this.id;
/*     */     }
/*     */     
/*     */     public static Gender valueOfById(byte id) {
/*  93 */       for (Gender gender : values()) {
/*  94 */         if (gender.getId() == id) {
/*  95 */           return gender;
/*     */         }
/*     */       } 
/*     */       
/*  99 */       throw new IllegalArgumentException("unknown gender id: id=" + id);
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString() {
/* 104 */     return "PlayerDescription{gender=" + this.gender + ", skinColorId='" + this.skinColorId + '\'' + ", faceId='" + this.faceId + '\'' + ", eyeColorId='" + this.eyeColorId + '\'' + ", hairId='" + this.hairId + '\'' + ", hairColorId='" + this.hairColorId + '\'' + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\items\PlayerDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */