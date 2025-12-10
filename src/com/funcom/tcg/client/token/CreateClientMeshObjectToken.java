/*    */ package com.funcom.tcg.client.token;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*    */ import com.funcom.commons.jme.md5importer.controller.JointController;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.WorldUtils;
/*    */ import com.funcom.gameengine.model.ResourceGetter;
/*    */ import com.funcom.gameengine.model.token.TokenTargetNode;
/*    */ import com.funcom.gameengine.utils.SpatialUtils;
/*    */ import com.funcom.gameengine.view.Effects;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.gameengine.view.RepresentationalNode;
/*    */ import com.funcom.gameengine.view.TransparentAlphaState;
/*    */ import com.funcom.tcg.factories.MeshDescription;
/*    */ import com.funcom.tcg.token.CreateMeshObjectToken;
/*    */ import com.jme.scene.Controller;
/*    */ import com.jme.scene.Node;
/*    */ import com.jme.scene.Spatial;
/*    */ import com.jme.scene.state.RenderState;
/*    */ import java.awt.Point;
/*    */ 
/*    */ public class CreateClientMeshObjectToken extends CreateMeshObjectToken {
/*    */   public CreateClientMeshObjectToken(WorldCoordinate coord, float scale, float angle, float z, String name, String resourceName, float[] tintColor, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter, DireEffectDescriptionFactory effectDescriptionFactory) {
/* 24 */     super(coord, scale, angle, z, name, resourceName, tintColor, tokenTargetNode, tileCoord, resourceGetter, effectDescriptionFactory);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void loadMD5model(MeshDescription meshDescription) {
/* 29 */     PropNode propNode = loadMD5Description(meshDescription);
/* 30 */     RepresentationalNode representationalNode = new RepresentationalNode(meshDescription.getMeshPath(), propNode.getEffectDescriptionFactory(), propNode.getContentType());
/* 31 */     representationalNode.setRunsDfxs(false);
/* 32 */     representationalNode.attachRepresentation(propNode.getRepresentation());
/*    */     
/* 34 */     representationalNode.getLocalRotation().fromAngleNormalAxis(this.angle, PropNode.DOWN_VEC);
/* 35 */     representationalNode.setScale(this.scale);
/* 36 */     float x = WorldUtils.getScreenX(this.coord, this.tileCoord.x);
/* 37 */     float y = WorldUtils.getScreenY(this.coord, this.tileCoord.y);
/* 38 */     representationalNode.setLocalTranslation(x, this.z, y);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 43 */     representationalNode.setRenderState((RenderState)TransparentAlphaState.get());
/*    */     
/* 45 */     if (this.tintColor != null) {
/* 46 */       representationalNode.getEffects().setTintRbga(SpatialUtils.convertToColorRGBA(this.tintColor));
/* 47 */       representationalNode.getEffects().tint(Effects.TintMode.MODULATE);
/* 48 */       representationalNode.getEffects().lockTint();
/*    */     } 
/*    */     
/* 51 */     JointController jointController = null;
/* 52 */     for (Controller controller : ((Node)representationalNode.getRepresentation()).getChild(0).getControllers()) {
/* 53 */       if (controller instanceof JointController) {
/* 54 */         jointController = (JointController)controller; break;
/*    */       } 
/*    */     } 
/* 57 */     if (jointController == null || !jointController.isActive()) {
/* 58 */       this.tokenTargetNode.attachStaticChild((Spatial)representationalNode);
/* 59 */       if (jointController != null)
/* 60 */         ((Node)representationalNode.getRepresentation()).getChild(0).removeController((Controller)jointController); 
/*    */     } else {
/* 62 */       this.tokenTargetNode.attachAnimatedChild((Spatial)representationalNode);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\token\CreateClientMeshObjectToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */