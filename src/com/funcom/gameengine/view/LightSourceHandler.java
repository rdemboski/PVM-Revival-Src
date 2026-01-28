/*    */ package com.funcom.gameengine.view;
/*    */ import com.funcom.commons.dfx.Effect;
/*    */ import com.funcom.commons.dfx.LightSourceEffectDescription;
/*    */ import com.funcom.commons.dfx.PositionalEffectDescription;
/*    */ import com.funcom.gameengine.view.lighting.LightNodeWithMarker;
/*    */ import com.jme.light.DirectionalLight;
/*    */ import com.jme.light.Light;
/*    */ import com.jme.light.PointLight;
/*    */ import com.jme.light.SpotLight;
/*    */ import com.jme.math.Vector3f;
/*    */ import com.jme.renderer.ColorRGBA;
/*    */ import com.jme.scene.Node;
/*    */ import com.jme.scene.Spatial;
/*    */ import com.jme.scene.state.LightState;
/*    */ import com.jme.scene.state.RenderState;
import com.jme.system.DisplaySystem;
/*    */ 
/*    */ public class LightSourceHandler extends PositionalEffectHandler {
/* 18 */   private Light light = null;
/*    */   private LightNodeWithMarker node;
/*    */   private Node ancestor;
/*    */   
/*    */   public LightSourceHandler(RepresentationalNode representationalNode) {
/* 23 */     super(representationalNode);
/*    */   }
/*    */ 
/*    */   
/*    */   public void startEffect(Effect sourceEffect) {
/* 28 */     LightSourceEffectDescription desc = (LightSourceEffectDescription)sourceEffect.getDescription();
/* 29 */     this.light = null;
/* 30 */     if (desc.getResource().equals("PointLight")) {
/* 31 */       this.light = (Light)new PointLight();
/* 32 */       this.light.setShadowCaster(false);
/* 33 */     } else if (desc.getResource().equals("DirectionalLight")) {
/* 34 */       this.light = (Light)new DirectionalLight();
/* 35 */       DirectionalLight directionalLight = (DirectionalLight)this.light;
/* 36 */       directionalLight.setDirection(getOffsetPos((PositionalEffectDescription)desc));
/* 37 */       this.light.setShadowCaster(true);
/* 38 */     } else if (desc.getResource().equals("SpotLight")) {
/* 39 */       this.light = (Light)new SpotLight();
/*    */     } else {
/* 41 */       throw new IllegalArgumentException("Bogus light effect resource: " + desc.getResource());
/*    */     } 
/* 43 */     this.light.setAttenuate(true);
/* 44 */     this.light.setEnabled(true);
/*    */     
/* 46 */     this.light.setAmbient(colorFromArray(desc.getAmbient()));
/* 47 */     this.light.setDiffuse(colorFromArray(desc.getDiffuse()));
/* 48 */     this.light.setSpecular(colorFromArray(desc.getSpecular()));
/*    */     
/* 50 */     this.light.setConstant(1.0F);
/* 51 */     this.light.setLinear(desc.getLinearAttenuation());
/* 52 */     this.light.setQuadratic(desc.getQuadraticAttenuation());
/*    */     
/* 54 */     this.ancestor = getWorldNode();
/* 55 */     LightState lightState = getLightState();
/* 56 */     lightState.attach(this.light);
/* 57 */     this.ancestor.updateRenderState();
/*    */     
/* 59 */     float parentScale = getTarget().getLocalScale().length();
/* 60 */     Vector3f offsetPos = getOffsetPos((PositionalEffectDescription)desc).divide(parentScale);
/*    */     
/* 62 */     if (!desc.getResource().equals("DirectionalLight")) {
/* 63 */       this.node = new LightNodeWithMarker(desc.getResource());
/* 64 */       this.node.setLight(this.light);
/* 65 */       this.node.setLocalTranslation(offsetPos);
/*    */     } 
/*    */     
/* 68 */     getTarget().attachChild((Spatial)this.node);
/*    */   }
/*    */   
/*    */   private LightState getLightState() {
/* 72 */     LightState lightState = (LightState)this.ancestor.getRenderState(RenderState.StateType.Light);
/* 73 */     if (lightState == null) {
/* 74 */       lightState = DisplaySystem.getDisplaySystem().getRenderer().createLightState();
/* 75 */       lightState.setEnabled(true);
/* 76 */       this.ancestor.setRenderState((RenderState)lightState);
/*    */     } 
/* 78 */     return lightState;
/*    */   }
/*    */   
/*    */   private ColorRGBA colorFromArray(float[] values) {
/* 82 */     return new ColorRGBA(values[0], values[1], values[2], 0.6F);
/*    */   }
/*    */   
/*    */   private Node getWorldNode() {
/* 86 */     return getTarget().getAncestor();
/*    */   }
/*    */ 
/*    */   
/*    */   public void endEffect(Effect sourceEffect) {
/* 91 */     LightState lightState = getLightState();
/* 92 */     lightState.detach(this.light);
/* 93 */     this.ancestor.updateRenderState();
/* 94 */     getTarget().detachChild((Spatial)this.node);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFinished() {
/* 99 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\LightSourceHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */