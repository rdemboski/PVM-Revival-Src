/*    */ package com.funcom.gameengine.model.token;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.jme.DecalQuad;
/*    */ import com.funcom.gameengine.model.ResourceGetter;
/*    */ import com.funcom.gameengine.model.props.Prop;
/*    */ import com.funcom.gameengine.utils.SpatialUtils;
/*    */ import com.funcom.gameengine.view.DecalPropNode;
/*    */ import com.funcom.gameengine.view.Effects;
/*    */ import com.funcom.gameengine.view.TransparentAlphaState;
/*    */ import com.jme.scene.Spatial;
/*    */ import com.jme.scene.shape.Quad;
/*    */ import com.jme.scene.state.RenderState;
/*    */ import java.awt.Point;
/*    */ 
/*    */ 
/*    */ public class CreateDecalToken
/*    */   implements Token
/*    */ {
/*    */   protected Prop prop;
/*    */   protected WorldCoordinate coord;
/*    */   protected float scale;
/*    */   protected float angle;
/*    */   protected int orderIndex;
/*    */   protected String resourceName;
/*    */   protected TokenTargetNode tokenTargetNode;
/*    */   protected Point tileCoord;
/*    */   protected ResourceGetter resourceGetter;
/*    */   protected float[] tintColor;
/*    */   protected DireEffectDescriptionFactory direEffectDescriptionFactory;
/*    */   
/*    */   public CreateDecalToken(Prop prop, WorldCoordinate coord, float scale, float angle, int orderIndex, String resourceName, float[] tintColor, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter, DireEffectDescriptionFactory direEffectDescriptionFactory) {
/* 34 */     this.prop = prop;
/* 35 */     this.coord = coord;
/* 36 */     this.scale = scale;
/* 37 */     this.angle = angle;
/* 38 */     this.orderIndex = orderIndex;
/* 39 */     this.resourceName = resourceName;
/* 40 */     this.tokenTargetNode = tokenTargetNode;
/* 41 */     this.tileCoord = tileCoord;
/* 42 */     this.resourceGetter = resourceGetter;
/* 43 */     this.tintColor = tintColor;
/* 44 */     this.direEffectDescriptionFactory = direEffectDescriptionFactory;
/*    */   }
/*    */   
/*    */   public Token.TokenType getTokenType() {
/* 48 */     return Token.TokenType.GAME_THREAD;
/*    */   }
/*    */   
/*    */   protected void setAlphaState(Quad staticQuad) {
/* 52 */     staticQuad.setRenderState((RenderState)TransparentAlphaState.get());
/*    */   }
/*    */   
/*    */   public void process() {
/* 56 */     DecalQuad decalQuad = createDecalQuad();
/*    */ 
/*    */     
/* 59 */     DecalPropNode decalPropNode = new DecalPropNode(this.prop, this.resourceName, this.direEffectDescriptionFactory);
/* 60 */     decalPropNode.setRunsDfxs(false);
/* 61 */     decalPropNode.attachRepresentation((Spatial)decalQuad);
/* 62 */     decalPropNode.setAngle(this.angle);
/* 63 */     decalPropNode.setScale(this.scale);
/* 64 */     decalPropNode.updatePropVectors(this.tileCoord);
/* 65 */     decalPropNode.setLocalTranslation((decalPropNode.getLocalTranslation()).x, 0.0F, (decalPropNode.getLocalTranslation()).z);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 70 */     if (this.tintColor != null) {
/* 71 */       decalPropNode.getEffects().setTintRbga(SpatialUtils.convertToColorRGBA(this.tintColor));
/* 72 */       decalPropNode.getEffects().tint(Effects.TintMode.MODULATE);
/*    */     } 
/*    */     
/* 75 */     this.tokenTargetNode.attachStaticChild((Spatial)decalPropNode);
/*    */   }
/*    */   
/*    */   protected DecalQuad createDecalQuad() {
/* 79 */     DecalQuad decalQuad = SpatialUtils.createDecalQuad("DecalProp", this.resourceGetter, this.resourceName);
/* 80 */     setAlphaState((Quad)decalQuad);
/* 81 */     return decalQuad;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\CreateDecalToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */