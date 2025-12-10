/*    */ package com.funcom.tcg.client;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jme.scene.state.RenderState;
/*    */ import com.jme.scene.state.StateRecord;
/*    */ import com.jme.scene.state.TextureState;
/*    */ import com.jme.system.DisplaySystem;
/*    */ import javax.imageio.ImageIO;
/*    */ 
/*    */ 
/*    */ public class TcgOptimization
/*    */ {
/*    */   public static void apply(DisplaySystem display) {
/* 14 */     disableShadowAndDepth(display);
/*    */     
/* 16 */     disableDefaultLightState();
/*    */     
/* 18 */     disableImageIOTempDiskFiles();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void disableImageIOTempDiskFiles() {
/* 26 */     ImageIO.setUseCache(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void disableShadowAndDepth(DisplaySystem display) {
/* 34 */     display.getRenderer().createTextureState();
/*    */     
/* 36 */     ShadowDisableHelper.turnOffFeatures();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void disableDefaultLightState() {
/* 43 */     Renderer.defaultStateList[RenderState.StateType.Light.ordinal()] = null;
/*    */   }
/*    */   
/*    */   private static class ShadowDisableHelper
/*    */     extends TextureState {
/*    */     private static void turnOffFeatures() {
/* 49 */       supportsDepthTexture = false;
/* 50 */       supportsShadow = false;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public void load(int unit) {}
/*    */ 
/*    */ 
/*    */     
/*    */     public void delete(int unit) {}
/*    */ 
/*    */ 
/*    */     
/*    */     public void deleteAll() {}
/*    */ 
/*    */ 
/*    */     
/*    */     public void deleteAll(boolean removeFromCache) {}
/*    */ 
/*    */ 
/*    */     
/*    */     public void apply() {}
/*    */ 
/*    */     
/*    */     public StateRecord createStateRecord() {
/* 75 */       return null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\TcgOptimization.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */