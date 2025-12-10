/*    */ package com.funcom.commons.dfx;
/*    */ 
/*    */ public class LightSourceEffectDescription
/*    */   extends PositionalEffectDescription {
/*    */   private float[] ambient;
/*    */   private float[] diffuse;
/*    */   private float[] specular;
/*  8 */   private float constantAttenuation = 1.0F;
/*  9 */   private float linearAttenuation = 0.0F;
/* 10 */   private float quadraticAttenuation = 0.0F;
/*    */   
/*    */   public void setAmbient(float[] ambient) {
/* 13 */     this.ambient = ambient;
/*    */   }
/*    */   
/*    */   public void setDiffuse(float[] diffuse) {
/* 17 */     this.diffuse = diffuse;
/*    */   }
/*    */   
/*    */   public float[] getAmbient() {
/* 21 */     return this.ambient;
/*    */   }
/*    */   
/*    */   public float[] getDiffuse() {
/* 25 */     return this.diffuse;
/*    */   }
/*    */   
/*    */   public float[] getSpecular() {
/* 29 */     return this.specular;
/*    */   }
/*    */   
/*    */   public void setSpecular(float[] specular) {
/* 33 */     this.specular = specular;
/*    */   }
/*    */   
/*    */   public void setConstantAttenuation(float constantAttenuation) {
/* 37 */     this.constantAttenuation = constantAttenuation;
/*    */   }
/*    */   
/*    */   public void setLinearAttenuation(float linearAttenuation) {
/* 41 */     this.linearAttenuation = linearAttenuation;
/*    */   }
/*    */   
/*    */   public void setQuadraticAttenuation(float quadraticAttenuation) {
/* 45 */     this.quadraticAttenuation = quadraticAttenuation;
/*    */   }
/*    */   
/*    */   public float getConstantAttenuation() {
/* 49 */     return this.constantAttenuation;
/*    */   }
/*    */   
/*    */   public float getLinearAttenuation() {
/* 53 */     return this.linearAttenuation;
/*    */   }
/*    */   
/*    */   public float getQuadraticAttenuation() {
/* 57 */     return this.quadraticAttenuation;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\dfx\LightSourceEffectDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */