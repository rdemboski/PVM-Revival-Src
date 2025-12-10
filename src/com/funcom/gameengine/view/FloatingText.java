/*     */ package com.funcom.gameengine.view;
/*     */ 
/*     */ import com.funcom.gameengine.Updated;
/*     */ import com.jme.light.DirectionalLight;
/*     */ import com.jme.light.Light;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.state.BlendState;
/*     */ import com.jme.scene.state.LightState;
/*     */ import com.jme.scene.state.MaterialState;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.font3d.Font3D;
/*     */ import com.jmex.font3d.Text3D;
/*     */ import java.awt.Font;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class FloatingText
/*     */   extends Node
/*     */   implements Updated
/*     */ {
/*     */   private static final float DYING_LIMIT_3DUNITS = 0.7F;
/*     */   private static final float DEAD_LIMIT_3DUNITS = 1.0F;
/*     */   private static final float SIZE = 0.3F;
/*  32 */   private static final Font3D FONT = new Font3D(new Font("Monospaced", 0, 8), 9.999999747378752E-5D, true, true, true);
/*     */   private List<Text3D> texts;
/*     */   private float speed;
/*     */   
/*     */   public FloatingText() {
/*  37 */     super("floating-text");
/*     */     
/*  39 */     this.speed = 0.7F;
/*  40 */     this.texts = new LinkedList<Text3D>();
/*     */     
/*  42 */     LightState lightState = DisplaySystem.getDisplaySystem().getRenderer().createLightState();
/*  43 */     lightState.detachAll();
/*  44 */     lightState.setEnabled(true);
/*  45 */     lightState.setGlobalAmbient(new ColorRGBA(0.9F, 0.9F, 0.9F, 1.0F));
/*     */ 
/*     */     
/*  48 */     DirectionalLight dlight = new DirectionalLight();
/*  49 */     dlight.setDirection(new Vector3f(-1.0F, -1.0F, -1.0F));
/*  50 */     dlight.setDiffuse(new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F));
/*  51 */     dlight.setAmbient(new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F));
/*  52 */     dlight.setSpecular(new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F));
/*  53 */     dlight.setEnabled(true);
/*  54 */     lightState.attach((Light)dlight);
/*     */     
/*  56 */     setRenderState((RenderState)lightState);
/*  57 */     updateRenderState();
/*     */   }
/*     */   
/*     */   public Class<? extends Spatial> getClassTag() {
/*  61 */     return (Class)getClass();
/*     */   }
/*     */   
/*     */   public void appendHeal(int healPoints) {
/*  65 */     appendText("+" + String.valueOf(healPoints), ColorRGBA.green);
/*     */   }
/*     */   
/*     */   public void appendDamage(int damagePoints) {
/*  69 */     appendText("-" + String.valueOf(damagePoints), ColorRGBA.red);
/*     */   }
/*     */   
/*     */   public void appendText(String text) {
/*  73 */     appendText(text, ColorRGBA.yellow);
/*     */   }
/*     */   
/*     */   public void appendText(String text, ColorRGBA colorRGBA) {
/*  77 */     Text3D textNode = FONT.createText(text, 2.0F, 0);
/*     */     
/*  79 */     BlendState blendState = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
/*  80 */     blendState.setBlendEnabled(true);
/*  81 */     textNode.setRenderState((RenderState)blendState);
/*     */     
/*  83 */     MaterialState ms = DisplaySystem.getDisplaySystem().getRenderer().createMaterialState();
/*  84 */     ms.setEnabled(true);
/*  85 */     ms.setDiffuse(colorRGBA);
/*  86 */     textNode.setRenderState((RenderState)ms);
/*     */ 
/*     */     
/*  89 */     Vector3f ls = textNode.getLocalScale();
/*  90 */     ls.x = 0.3F;
/*  91 */     ls.y = 0.3F;
/*  92 */     ls.z = 1.0E-4F;
/*  93 */     textNode.setLocalScale(ls);
/*     */     
/*  95 */     attachChild((Spatial)textNode);
/*  96 */     this.texts.add(textNode);
/*     */     
/*  98 */     textNode.updateRenderState();
/*     */   }
/*     */   
/*     */   public void update(float time) {
/* 102 */     Iterator<Text3D> it = this.texts.iterator();
/* 103 */     while (it.hasNext()) {
/* 104 */       Text3D text = it.next();
/*     */       
/* 106 */       Vector3f lt = text.getLocalTranslation();
/* 107 */       lt.y += time * this.speed;
/*     */       
/* 109 */       if (isDead(text)) {
/* 110 */         it.remove();
/* 111 */         text.removeFromParent(); continue;
/* 112 */       }  if (isDying(text))
/* 113 */         fade(lt.y, text); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fade(float position, Text3D text) {
/* 118 */     float dyingRatio = (1.0F - position) / 0.3F;
/*     */     
/* 120 */     MaterialState ms = (MaterialState)text.getRenderState(3);
/* 121 */     ColorRGBA diff = ms.getDiffuse();
/* 122 */     diff.a = dyingRatio;
/* 123 */     ms.setDiffuse(diff);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isDying(Text3D text) {
/* 128 */     return (text.getLocalTranslation().getY() > 0.7F);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isDead(Text3D text) {
/* 133 */     return (text.getLocalTranslation().getY() > 1.0F);
/*     */   }
/*     */   
/*     */   public void setSpeed(float speed) {
/* 137 */     this.speed = speed;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\FloatingText.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */