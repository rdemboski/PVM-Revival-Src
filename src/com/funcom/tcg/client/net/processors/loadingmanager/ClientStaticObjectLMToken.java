/*     */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*     */ import com.funcom.commons.utils.DimensionFloat;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.WorldUtils;
/*     */ import com.funcom.gameengine.model.GraphicsConfig;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.model.token.TokenTargetNode;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import com.funcom.gameengine.view.Effects;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.shape.Quad;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import java.awt.Point;
/*     */ import java.nio.ByteBuffer;
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
/*     */ 
/*     */ 
/*     */ public class ClientStaticObjectLMToken
/*     */   extends LoadingManagerToken
/*     */ {
/*  66 */   private Future<Texture> LoadTextureFuture = null;
/*  67 */   Prop prop = null;
/*  68 */   WorldCoordinate coord = null;
/*  69 */   float scale = 1.0F;
/*  70 */   float angle = 0.0F;
/*  71 */   float z = 0.0F;
/*  72 */   String resourceName = null;
/*  73 */   float[] tintColor = null;
/*  74 */   TokenTargetNode tokenTargetNode = null;
/*  75 */   Point tileCoord = null;
/*     */ 
/*     */   
/*     */   public ClientStaticObjectLMToken(Prop prop, WorldCoordinate coord, float scale, float angle, float z, String resourceName, float[] tintColor, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/*  79 */     this.prop = prop;
/*  80 */     this.coord = coord;
/*  81 */     this.scale = scale;
/*  82 */     this.angle = angle;
/*  83 */     this.z = z;
/*  84 */     this.resourceName = resourceName;
/*  85 */     this.tintColor = tintColor;
/*  86 */     this.tokenTargetNode = tokenTargetNode;
/*  87 */     this.tileCoord = tileCoord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processRequestAssets() throws Exception {
/*  94 */     Callable<Integer> callable = new LoadTextureCallable(this.resourceName);
/*  95 */     this.LoadTextureFuture = LoadingManager.INSTANCE.submitCallable(callable);
/*     */ 
/*     */     
/*  98 */     return true;
/*     */   }
/*     */   
/*     */   public boolean processWaitingAssets() throws Exception {
/* 102 */     return (this.LoadTextureFuture == null || this.LoadTextureFuture.isDone());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processGame() throws Exception {
/* 108 */     ColorRGBA colorRGBA = SpatialUtils.convertToColorRGBA(this.tintColor);
/* 109 */     PropNode propNode = newBillboard(TcgGame.getResourceGetter(), this.prop, this.resourceName, this.tileCoord, colorRGBA, null);
/*     */ 
/*     */     
/* 112 */     RepresentationalNode representationalNode = new RepresentationalNode(propNode.getName(), propNode.getEffectDescriptionFactory(), propNode.getContentType());
/* 113 */     representationalNode.setRunsDfxs(false);
/*     */     
/* 115 */     representationalNode.attachRepresentation(propNode.getRepresentation());
/* 116 */     representationalNode.getLocalRotation().fromAngleNormalAxis(this.angle, PropNode.DOWN_VEC);
/* 117 */     representationalNode.setScale(this.scale);
/* 118 */     float x = WorldUtils.getScreenX(this.prop.getPosition(), this.tileCoord.x);
/* 119 */     float y = WorldUtils.getScreenY(this.prop.getPosition(), this.tileCoord.y);
/* 120 */     representationalNode.setLocalTranslation(x, this.z, y);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     if (this.tintColor != null) {
/* 126 */       representationalNode.getEffects().setTintRbga(SpatialUtils.convertToColorRGBA(this.tintColor));
/* 127 */       representationalNode.getEffects().tint(Effects.TintMode.MODULATE);
/* 128 */       representationalNode.getEffects().lockTint();
/*     */     } 
/*     */     
/* 131 */     this.tokenTargetNode.attachStaticChild((Spatial)representationalNode);
/* 132 */     representationalNode.propagateBoundToRoot();
/*     */     
/* 134 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PropNode newBillboard(ResourceGetter resourceGetter, Prop prop, String resourceName, Point tileCoord, ColorRGBA tintColor, DireEffectDescriptionFactory effectDescriptionFactory) throws Exception {
/* 140 */     PropNode propNode = new PropNode(prop, 2, resourceName, effectDescriptionFactory);
/* 141 */     propNode.setRunsDfxs(false);
/*     */     
/* 143 */     TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*     */     
/* 145 */     Texture t = null;
/* 146 */     if (this.LoadTextureFuture != null && !this.LoadTextureFuture.isCancelled()) {
/* 147 */       t = this.LoadTextureFuture.get();
/*     */     } else {
/*     */       
/* 150 */       t = resourceGetter.getTexture(resourceName, CacheType.CACHE_TEMPORARILY);
/*     */     } 
/* 152 */     this.LoadTextureFuture = null;
/*     */     
/* 154 */     if (t == null) {
/* 155 */       return null;
/*     */     }
/* 157 */     ts.setTexture(t);
/*     */ 
/*     */     
/* 160 */     DimensionFloat size = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 168 */       if (LoadingManager.USE_DDS_FORMAT) {
/* 169 */         String resDesc = resourceName.toLowerCase().replace(".png", ".desc");
/* 170 */         ByteBuffer buf = resourceGetter.getBlob(resDesc);
/* 171 */         byte[] bytearr = new byte[buf.remaining()];
/* 172 */         buf.get(bytearr);
/* 173 */         String s = new String(bytearr);
/* 174 */         String[] split = s.split(",");
/*     */         try {
/* 176 */           int texWidth = Integer.parseInt(split[2]);
/* 177 */           int texHeight = Integer.parseInt(split[3]);
/* 178 */           size = SpatialUtils.getBillboardRenderSize(texWidth, texHeight);
/*     */         }
/* 180 */         catch (Exception ee) {
/* 181 */           size = null;
/*     */         }
/*     */       
/*     */       } 
/* 185 */     } catch (Exception e) {
/* 186 */       size = null;
/*     */     } 
/*     */     
/* 189 */     if (size == null) {
/* 190 */       size = SpatialUtils.getBillboardRenderSize(t);
/*     */     }
/*     */     
/* 193 */     Quad staticQuad = SpatialUtils.createPropQuad("BillboardProp", size.getWidth(), size.getHeight(), ts);
/*     */ 
/*     */     
/* 196 */     GraphicsConfig config = GraphicsConfig.getBillboardConfig(resourceGetter, resourceName);
/*     */     
/* 198 */     staticQuad.setRenderState(config.getBlendMode().getRenderState());
/*     */     
/* 200 */     propNode.attachRepresentation((Spatial)staticQuad);
/* 201 */     if (tileCoord != null) {
/* 202 */       propNode.updatePropVectors(tileCoord);
/*     */     }
/* 204 */     if (tintColor != null) {
/* 205 */       propNode.getEffects().setTintRbga(tintColor);
/* 206 */       propNode.getEffects().tint(Effects.TintMode.MODULATE);
/*     */     } 
/*     */     
/* 209 */     return propNode;
/*     */   }
/*     */   
/*     */   class LoadTextureCallable implements Callable {
/*     */     private String resourceName;
/*     */     
/*     */     public LoadTextureCallable(String resourceName) {
/* 216 */       this.resourceName = resourceName;
/*     */     }
/*     */     
/*     */     public Texture call() {
/* 220 */       Texture t = TcgGame.getResourceGetter().getTexture(this.resourceName, CacheType.CACHE_TEMPORARILY);
/* 221 */       return t;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\ClientStaticObjectLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */