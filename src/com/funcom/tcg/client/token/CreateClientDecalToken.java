/*    */ package com.funcom.tcg.client.token;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.WorldUtils;
/*    */ import com.funcom.gameengine.jme.DecalQuad;
/*    */ import com.funcom.gameengine.jme.LayeredElement;
/*    */ import com.funcom.gameengine.model.ResourceGetter;
/*    */ import com.funcom.gameengine.model.props.Prop;
/*    */ import com.funcom.gameengine.model.token.CreateDecalToken;
/*    */ import com.funcom.gameengine.model.token.TokenTargetNode;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.utils.SpatialUtils;
/*    */ import com.funcom.gameengine.view.Effects;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.gameengine.view.RepresentationalNode;
/*    */ import com.funcom.tcg.client.net.processors.loadingmanager.ClientDecalLMToken;
/*    */ import com.jme.scene.Spatial;
/*    */ import java.awt.Point;
/*    */ 
/*    */ public class CreateClientDecalToken
/*    */   extends CreateDecalToken
/*    */ {
/*    */   public CreateClientDecalToken(Prop prop, WorldCoordinate coord, float scale, float angle, int orderIndex, String resourceName, float[] tintColor, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter, DireEffectDescriptionFactory direEffectDescriptionFactory) {
/* 26 */     super(prop, coord, scale, angle, orderIndex, resourceName, tintColor, tokenTargetNode, tileCoord, resourceGetter, direEffectDescriptionFactory);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void process() {
/* 32 */     if (LoadingManager.USE) {
/* 33 */       LoadingManager.INSTANCE.submit((LoadingManagerToken)new ClientDecalLMToken(this.prop, this.coord, this.scale, this.angle, this.orderIndex, this.resourceName, this.tintColor, this.tokenTargetNode, this.tileCoord, this.resourceGetter, this.direEffectDescriptionFactory));
/*    */     }
/*    */     else {
/*    */       
/* 37 */       DecalQuad decalQuad = createDecalQuad();
/* 38 */       RepresentationalNode representationalNode = new DecalRepresntationalNode(this.prop.getName(), this.direEffectDescriptionFactory, 8);
/* 39 */       representationalNode.setCullHint(Spatial.CullHint.Dynamic);
/* 40 */       representationalNode.setRunsDfxs(false);
/* 41 */       representationalNode.attachRepresentation((Spatial)decalQuad);
/*    */       
/* 43 */       representationalNode.getLocalRotation().fromAngleNormalAxis(this.angle, PropNode.DOWN_VEC);
/* 44 */       representationalNode.setScale(this.scale);
/* 45 */       float x = WorldUtils.getScreenX(this.prop.getPosition(), this.tileCoord.x);
/* 46 */       float y = WorldUtils.getScreenY(this.prop.getPosition(), this.tileCoord.y);
/* 47 */       representationalNode.setLocalTranslation(x, 0.0F, y);
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 52 */       if (this.tintColor != null) {
/* 53 */         representationalNode.getEffects().setTintRbga(SpatialUtils.convertToColorRGBA(this.tintColor));
/* 54 */         representationalNode.getEffects().tint(Effects.TintMode.MODULATE);
/*    */       } 
/*    */       
/* 57 */       this.tokenTargetNode.attachStaticChild((Spatial)representationalNode);
/* 58 */       this.tokenTargetNode.updateRenderState();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static class DecalRepresntationalNode
/*    */     extends RepresentationalNode
/*    */     implements LayeredElement
/*    */   {
/*    */     private DecalRepresntationalNode(String s, DireEffectDescriptionFactory effectDescriptionFactory, int contentType) {
/* 69 */       super(s, effectDescriptionFactory, contentType);
/*    */     }
/*    */ 
/*    */     
/*    */     public void attachRepresentation(Spatial spatial) {
/* 74 */       if (!(spatial instanceof DecalQuad))
/* 75 */         throw new RuntimeException("representation must be a decal quad"); 
/* 76 */       super.attachRepresentation(spatial);
/*    */     }
/*    */ 
/*    */     
/*    */     public int getLayerId() {
/* 81 */       return ((LayeredElement)getRepresentation()).getLayerId();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\token\CreateClientDecalToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */