/*    */ package com.funcom.gameengine.model.factories;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*    */ import com.funcom.commons.utils.DimensionFloat;
/*    */ import com.funcom.gameengine.model.GraphicsConfig;
/*    */ import com.funcom.gameengine.model.ResourceGetter;
/*    */ import com.funcom.gameengine.model.props.Prop;
/*    */ import com.funcom.gameengine.resourcemanager.CacheType;
/*    */ import com.funcom.gameengine.utils.SpatialUtils;
/*    */ import com.funcom.gameengine.view.Effects;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.jme.image.Texture;
/*    */ import com.jme.renderer.ColorRGBA;
/*    */ import com.jme.scene.Spatial;
/*    */ import com.jme.scene.shape.Quad;
/*    */ import com.jme.scene.state.TextureState;
/*    */ import com.jme.system.DisplaySystem;
/*    */ import java.awt.Point;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BillboardFactory
/*    */ {
/*    */   public static PropNode newBillboard(ResourceGetter resourceGetter, Prop prop, String resourceName, float angle, float scale, Point tileCoord, ColorRGBA tintColor, DireEffectDescriptionFactory direEffectDescriptionFactory) {
/* 26 */     PropNode propNode = newBillboard(resourceGetter, prop, resourceName, tileCoord, tintColor, direEffectDescriptionFactory);
/* 27 */     propNode.setAngle(angle);
/* 28 */     propNode.setScale(scale);
/* 29 */     return propNode;
/*    */   }
/*    */   
/*    */   public static PropNode newBillboard(ResourceGetter resourceGetter, Prop prop, String resourceName, Point tileCoord, ColorRGBA tintColor, DireEffectDescriptionFactory effectDescriptionFactory) {
/* 33 */     PropNode propNode = new PropNode(prop, 2, resourceName, effectDescriptionFactory);
/* 34 */     propNode.setRunsDfxs(false);
/*    */     
/* 36 */     TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/* 37 */     Texture t = resourceGetter.getTexture(resourceName, CacheType.CACHE_TEMPORARILY);
/* 38 */     ts.setTexture(t);
/*    */ 
/*    */     
/* 41 */     DimensionFloat size = SpatialUtils.getBillboardRenderSize(t);
/* 42 */     Quad staticQuad = SpatialUtils.createPropQuad("BillboardProp", size.getWidth(), size.getHeight(), ts);
/*    */ 
/*    */     
/* 45 */     GraphicsConfig config = GraphicsConfig.getBillboardConfig(resourceGetter, resourceName);
/*    */     
/* 47 */     staticQuad.setRenderState(config.getBlendMode().getRenderState());
/*    */     
/* 49 */     propNode.attachRepresentation((Spatial)staticQuad);
/* 50 */     if (tileCoord != null) {
/* 51 */       propNode.updatePropVectors(tileCoord);
/*    */     }
/* 53 */     if (tintColor != null) {
/* 54 */       propNode.getEffects().setTintRbga(tintColor);
/* 55 */       propNode.getEffects().tint(Effects.TintMode.MODULATE);
/*    */     } 
/*    */     
/* 58 */     return propNode;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\factories\BillboardFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */