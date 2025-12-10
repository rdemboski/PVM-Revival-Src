/*    */ package com.funcom.tcg.client.token;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.WorldUtils;
/*    */ import com.funcom.gameengine.model.ResourceGetter;
/*    */ import com.funcom.gameengine.model.factories.BillboardFactory;
/*    */ import com.funcom.gameengine.model.props.Prop;
/*    */ import com.funcom.gameengine.model.token.CreateStaticObjectToken;
/*    */ import com.funcom.gameengine.model.token.TokenTargetNode;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.utils.SpatialUtils;
/*    */ import com.funcom.gameengine.view.Effects;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.gameengine.view.RepresentationalNode;
/*    */ import com.funcom.tcg.client.net.processors.loadingmanager.ClientStaticObjectLMToken;
/*    */ import com.jme.renderer.ColorRGBA;
/*    */ import com.jme.scene.Spatial;
/*    */ import java.awt.Point;
/*    */ 
/*    */ public class CreateClientStaticObjectToken extends CreateStaticObjectToken {
/*    */   public CreateClientStaticObjectToken(Prop prop, WorldCoordinate coord, float scale, float angle, float z, String resourceName, float[] tintColor, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/* 22 */     super(prop, coord, scale, angle, z, resourceName, tintColor, tokenTargetNode, tileCoord, resourceGetter);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void process() {
/* 28 */     if (LoadingManager.USE) {
/* 29 */       LoadingManager.INSTANCE.submitByDistance((LoadingManagerToken)new ClientStaticObjectLMToken(this.prop, this.coord, this.scale, this.angle, this.z, this.resourceName, this.tintColor, this.tokenTargetNode, this.tileCoord, this.resourceGetter), this.coord.getX().intValue(), this.coord.getY().intValue(), this.tokenTargetNode);
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 34 */       ColorRGBA colorRGBA = SpatialUtils.convertToColorRGBA(this.tintColor);
/* 35 */       PropNode propNode = BillboardFactory.newBillboard(this.resourceGetter, this.prop, this.resourceName, this.tileCoord, colorRGBA, null);
/*    */ 
/*    */       
/* 38 */       RepresentationalNode representationalNode = new RepresentationalNode(propNode.getName(), propNode.getEffectDescriptionFactory(), propNode.getContentType());
/* 39 */       representationalNode.setRunsDfxs(false);
/*    */       
/* 41 */       representationalNode.attachRepresentation(propNode.getRepresentation());
/* 42 */       representationalNode.getLocalRotation().fromAngleNormalAxis(this.angle, PropNode.DOWN_VEC);
/* 43 */       representationalNode.setScale(this.scale);
/* 44 */       float x = WorldUtils.getScreenX(this.prop.getPosition(), this.tileCoord.x);
/* 45 */       float y = WorldUtils.getScreenY(this.prop.getPosition(), this.tileCoord.y);
/* 46 */       representationalNode.setLocalTranslation(x, this.z, y);
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 51 */       if (this.tintColor != null) {
/* 52 */         representationalNode.getEffects().setTintRbga(SpatialUtils.convertToColorRGBA(this.tintColor));
/* 53 */         representationalNode.getEffects().tint(Effects.TintMode.MODULATE);
/* 54 */         representationalNode.getEffects().lockTint();
/*    */       } 
/* 56 */       this.tokenTargetNode.attachStaticChild((Spatial)representationalNode);
/* 57 */       this.tokenTargetNode.updateRenderState();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\token\CreateClientStaticObjectToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */