/*     */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.WorldUtils;
/*     */ import com.funcom.gameengine.jme.DecalQuad;
/*     */ import com.funcom.gameengine.jme.LayeredElement;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.model.token.TokenTargetNode;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import com.funcom.gameengine.view.Effects;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import com.funcom.gameengine.view.TransparentAlphaState;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import java.awt.Point;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Future;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientDecalLMToken
/*     */   extends LoadingManagerToken
/*     */ {
/*  42 */   Prop prop = null;
/*  43 */   WorldCoordinate coord = null;
/*  44 */   float scale = 0.0F;
/*  45 */   float angle = 0.0F;
/*  46 */   int orderIndex = 0;
/*  47 */   String resourceName = "";
/*  48 */   float[] tintColor = null;
/*  49 */   TokenTargetNode tokenTargetNode = null;
/*  50 */   Point tileCoord = null;
/*  51 */   ResourceGetter resourceGetter = null;
/*  52 */   DireEffectDescriptionFactory direEffectDescriptionFactory = null;
/*  53 */   private Future LoadDecalQuadFuture = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientDecalLMToken(Prop prop, WorldCoordinate coord, float scale, float angle, int orderIndex, String resourceName, float[] tintColor, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter, DireEffectDescriptionFactory direEffectDescriptionFactory) {
/*  58 */     this.prop = prop;
/*  59 */     this.coord = coord;
/*  60 */     this.scale = scale;
/*  61 */     this.angle = angle;
/*  62 */     this.orderIndex = orderIndex;
/*  63 */     this.resourceName = resourceName;
/*  64 */     this.tintColor = tintColor;
/*  65 */     this.tokenTargetNode = tokenTargetNode;
/*  66 */     this.tileCoord = tileCoord;
/*  67 */     this.resourceGetter = resourceGetter;
/*  68 */     this.direEffectDescriptionFactory = direEffectDescriptionFactory;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processRequestAssets() throws Exception {
/*  73 */     Callable<RepresentationalNode> callable = new LoadDecalQuadCallable(this.resourceName, this.resourceGetter);
/*  74 */     this.LoadDecalQuadFuture = (Future)LoadingManager.INSTANCE.submitCallable(callable);
/*  75 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processWaitingAssets() throws Exception {
/*  80 */     return (this.LoadDecalQuadFuture == null || this.LoadDecalQuadFuture.isDone());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processGame() throws Exception {
/*  86 */     RepresentationalNode representationalNode = null;
/*  87 */     DecalQuad decalQuad = null;
/*  88 */     if (this.LoadDecalQuadFuture != null && !this.LoadDecalQuadFuture.isCancelled()) {
/*  89 */       representationalNode = (RepresentationalNode)this.LoadDecalQuadFuture.get();
/*     */     } else {
/*     */       
/*  92 */       Callable<RepresentationalNode> callable = new LoadDecalQuadCallable(this.resourceName, this.resourceGetter);
/*  93 */       representationalNode = callable.call();
/*     */     } 
/*  95 */     this.LoadDecalQuadFuture = null;
/*     */     
/*  97 */     if (representationalNode != null) {
/*  98 */       this.tokenTargetNode.attachStaticChild((Spatial)representationalNode);
/*  99 */       representationalNode.propagateBoundToRoot();
/*     */     } 
/*     */     
/* 102 */     return true;
/*     */   }
/*     */   
/*     */   private static class DecalRepresntationalNode extends RepresentationalNode implements LayeredElement {
/*     */     private DecalRepresntationalNode(String s, DireEffectDescriptionFactory effectDescriptionFactory, int contentType) {
/* 107 */       super(s, effectDescriptionFactory, contentType);
/*     */     }
/*     */ 
/*     */     
/*     */     public void attachRepresentation(Spatial spatial) {
/* 112 */       if (!(spatial instanceof DecalQuad))
/* 113 */         throw new RuntimeException("representation must be a decal quad"); 
/* 114 */       super.attachRepresentation(spatial);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getLayerId() {
/* 119 */       return ((LayeredElement)getRepresentation()).getLayerId();
/*     */     }
/*     */   }
/*     */   
/*     */   public class LoadDecalQuadCallable
/*     */     implements Callable<RepresentationalNode>
/*     */   {
/* 126 */     String resourceName = "";
/* 127 */     ResourceGetter resourceGetter = null;
/*     */     
/*     */     public LoadDecalQuadCallable(String resourceName, ResourceGetter resourceGetter) {
/* 130 */       this.resourceName = resourceName;
/* 131 */       this.resourceGetter = resourceGetter;
/*     */     }
/*     */ 
/*     */     
/*     */     public RepresentationalNode call() {
/* 136 */       DecalQuad decalQuad = null;
/* 137 */       decalQuad = SpatialUtils.createDecalQuad("DecalProp", this.resourceGetter, this.resourceName);
/*     */       
/* 139 */       decalQuad.setRenderState((RenderState)TransparentAlphaState.get());
/* 140 */       RepresentationalNode representationalNode = new ClientDecalLMToken.DecalRepresntationalNode(ClientDecalLMToken.this.prop.getName(), ClientDecalLMToken.this.direEffectDescriptionFactory, 8);
/* 141 */       representationalNode.setCullHint(Spatial.CullHint.Dynamic);
/* 142 */       representationalNode.setRunsDfxs(false);
/* 143 */       representationalNode.attachRepresentation((Spatial)decalQuad);
/*     */       
/* 145 */       representationalNode.getLocalRotation().fromAngleNormalAxis(ClientDecalLMToken.this.angle, PropNode.DOWN_VEC);
/* 146 */       representationalNode.setScale(ClientDecalLMToken.this.scale);
/* 147 */       float x = WorldUtils.getScreenX(ClientDecalLMToken.this.prop.getPosition(), ClientDecalLMToken.this.tileCoord.x);
/* 148 */       float y = WorldUtils.getScreenY(ClientDecalLMToken.this.prop.getPosition(), ClientDecalLMToken.this.tileCoord.y);
/* 149 */       representationalNode.setLocalTranslation(x, 0.0F, y);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 154 */       if (ClientDecalLMToken.this.tintColor != null) {
/* 155 */         representationalNode.getEffects().setTintRbga(SpatialUtils.convertToColorRGBA(ClientDecalLMToken.this.tintColor));
/* 156 */         representationalNode.getEffects().tint(Effects.TintMode.MODULATE);
/*     */       } 
/*     */       
/* 159 */       return representationalNode;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\ClientDecalLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */