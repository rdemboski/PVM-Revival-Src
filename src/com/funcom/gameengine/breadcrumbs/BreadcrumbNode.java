/*     */ package com.funcom.gameengine.breadcrumbs;
/*     */ 
/*     */ import com.funcom.commons.configuration.InjectFromProperty;
/*     */ import com.funcom.commons.configuration.URLFieldProperties;
/*     */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.math.Quaternion;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.shape.Quad;
/*     */ import com.jme.scene.state.BlendState;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ 
/*     */ public class BreadcrumbNode
/*     */   extends PropNode
/*     */ {
/*  21 */   static final URLFieldProperties PROPERTIES = new URLFieldProperties(ClassLoader.getSystemResource("breadcrumb.properties"), BreadcrumbNode.class);
/*     */   
/*     */   @InjectFromProperty
/*     */   private static float breadcrumbWidth;
/*     */   
/*     */   @InjectFromProperty
/*     */   private static float breadcrumbHeight;
/*     */   
/*     */   @InjectFromProperty
/*     */   private static boolean alternatingPaws;
/*     */   @InjectFromProperty
/*     */   private static String breadcrumbTexturePath;
/*     */   @InjectFromProperty
/*     */   private static float maxAlpha;
/*     */   
/*     */   public BreadcrumbNode(BreadcrumbModel breadcrumbModel, Prop player, ResourceManager resourceManager, DireEffectDescriptionFactory direEffectDescriptionFactory) {
/*  37 */     super(breadcrumbModel, 22, "UNKNOWN", direEffectDescriptionFactory);
/*  38 */     this.player = player;
/*  39 */     this.resourceManager = resourceManager;
/*     */     
/*  41 */     breadcrumbModel.addPropChangeListener((Prop.PropChangeListener)new ModelListener(this));
/*     */     
/*  43 */     this.pawQuads = new Quad[alternatingPaws ? 2 : 1];
/*  44 */     for (int i = 0; i < this.pawQuads.length; i++) {
/*  45 */       this.pawQuads[i] = new Quad("paw-quad", breadcrumbWidth, breadcrumbHeight);
/*  46 */       this.pawQuads[i].setRenderState((RenderState)pawTexture());
/*  47 */       this.pawQuads[i].setRenderState((RenderState)blendState());
/*  48 */       attachRepresentation((Spatial)this.pawQuads[i]);
/*     */     } 
/*     */ 
/*     */     
/*  52 */     setAlpha(maxAlpha);
/*  53 */     flatThePawQuads();
/*  54 */     updateGeometricState(0.0F, true);
/*  55 */     updateRenderState();
/*     */   }
/*     */ 
/*     */   
/*     */   @InjectFromProperty
/*     */   private static float minAlpha;
/*     */   
/*     */   @InjectFromProperty
/*     */   private static float alphaRange;
/*     */   
/*     */   private Prop player;
/*     */   
/*     */   private ResourceManager resourceManager;
/*     */   
/*     */   private Quad[] pawQuads;
/*     */   
/*     */   private BlendState blendState() {
/*  72 */     BlendState bs = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
/*  73 */     bs.setBlendEnabled(true);
/*  74 */     return bs;
/*     */   }
/*     */   
/*     */   private void flatThePawQuads() {
/*  78 */     for (Quad pawQuad : this.pawQuads) {
/*  79 */       Quaternion quaternion = (new Quaternion()).fromAngles(1.5707964F, 0.0F, 0.0F);
/*  80 */       pawQuad.setLocalRotation(quaternion);
/*     */     } 
/*     */   }
/*     */   
/*     */   private TextureState pawTexture() {
/*  85 */     TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*  86 */     ts.setTexture((Texture)this.resourceManager.getResource(Texture.class, breadcrumbTexturePath));
/*  87 */     return ts;
/*     */   }
/*     */   
/*     */   private void setAlpha(float alpha) {
/*  91 */     if (this.pawQuads != null)
/*  92 */       for (Quad pawQuad : this.pawQuads)
/*  93 */         (pawQuad.getDefaultColor()).a = alpha;  
/*     */   }
/*     */   
/*     */   public float getAlpha() {
/*  97 */     return (this.pawQuads[0].getDefaultColor()).a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateGeometricState(float time, boolean initiator) {
/* 102 */     super.updateGeometricState(time, initiator);
/* 103 */     updateAlpha();
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateAlpha() {
/* 108 */     if (!getProp().isVisible()) {
/* 109 */       setAlpha(0.0F);
/*     */       
/*     */       return;
/*     */     } 
/* 113 */     float distance = getProp().distanceTo(this.player);
/* 114 */     if (distance > alphaRange) {
/* 115 */       setAlpha(1.0F);
/*     */     } else {
/* 117 */       float alphaForDistance = Math.max(minAlpha, distance / alphaRange);
/* 118 */       setAlpha(Math.min(alphaForDistance, maxAlpha));
/*     */     } 
/*     */   }
/*     */   
/*     */   public BreadcrumbModel getProp() {
/* 123 */     return (BreadcrumbModel)super.getProp();
/*     */   }
/*     */   
/*     */   private static class ModelListener extends Prop.PropChangeAdapter {
/*     */     private BreadcrumbNode breadcrumbNode;
/*     */     
/*     */     private ModelListener(BreadcrumbNode breadcrumbNode) {
/* 130 */       this.breadcrumbNode = breadcrumbNode;
/*     */     }
/*     */     
/*     */     public void visibilityChanged(Prop prop, boolean visible) {
/* 134 */       this.breadcrumbNode.setAlpha(visible ? BreadcrumbNode.maxAlpha : 0.0F);
/*     */     }
/*     */     
/*     */     public void propertyChanged(Prop prop, String propertyName, Object newValue, Object oldValue) {
/* 138 */       if (propertyName.equals("deleteme"))
/* 139 */         this.breadcrumbNode.removeFromParent(); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\breadcrumbs\BreadcrumbNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */