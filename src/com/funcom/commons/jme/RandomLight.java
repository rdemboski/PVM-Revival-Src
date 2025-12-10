/*    */ package com.funcom.commons.jme;
/*    */ 
/*    */ import com.jme.bounding.BoundingSphere;
/*    */ import com.jme.bounding.BoundingVolume;
/*    */ import com.jme.light.Light;
/*    */ import com.jme.light.LightNode;
/*    */ import com.jme.light.PointLight;
/*    */ import com.jme.math.Vector3f;
/*    */ import com.jme.renderer.ColorRGBA;
/*    */ import com.jme.scene.Spatial;
/*    */ import com.jme.scene.shape.Box;
/*    */ import com.jme.scene.state.LightState;
/*    */ 
/*    */ 
/*    */ public class RandomLight
/*    */   extends LightNode
/*    */ {
/*    */   public RandomLight(float minx, float maxx, float miny, float maxy, float minz, float maxz, LightState lightState) {
/* 19 */     super("Light Node");
/*    */     
/* 21 */     PointLight pl = new PointLight();
/* 22 */     pl.setEnabled(true);
/* 23 */     pl.setDiffuse(new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F));
/* 24 */     pl.setAmbient(new ColorRGBA(0.0F, 0.0F, 0.0F, 1.0F));
/* 25 */     pl.setSpecular(new ColorRGBA(0.2F, 0.2F, 0.2F, 1.0F));
/* 26 */     pl.setLocation((new Vector3f(0.0F, 0.0F, 0.0F)).normalizeLocal());
/*    */     
/* 28 */     lightState.attach((Light)pl);
/* 29 */     setLight((Light)pl);
/*    */     
/* 31 */     Vector3f min = new Vector3f(-0.15F, -0.15F, -0.15F);
/* 32 */     Vector3f max = new Vector3f(0.15F, 0.15F, 0.15F);
/* 33 */     Box lightBox = new Box("box", min, max);
/* 34 */     lightBox.setLightCombineMode(Spatial.LightCombineMode.Off);
/* 35 */     lightBox.setModelBound((BoundingVolume)new BoundingSphere());
/* 36 */     lightBox.updateModelBound();
/*    */     
/* 38 */     attachChild((Spatial)lightBox);
/* 39 */     setCullHint(Spatial.CullHint.Never);
/*    */     
/* 41 */     addController(new RandomMoveController(minx, maxx, miny, maxy, minz, maxz, (Spatial)this));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\RandomLight.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */