/*    */ package com.funcom.gameengine.model.token;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*    */ import com.funcom.commons.jme.cpolygon.CPoint2D;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.ResourceGetter;
/*    */ import com.funcom.gameengine.model.props.Prop;
/*    */ import java.awt.Point;
/*    */ import java.util.List;
/*    */ 
/*    */ public abstract class SpatialChunkTokenFactory
/*    */   implements ChunkTokenFactory
/*    */ {
/*    */   protected DireEffectDescriptionFactory effectDescriptionFactory;
/*    */   
/*    */   public SpatialChunkTokenFactory(DireEffectDescriptionFactory effectDescriptionFactory) {
/* 17 */     this.effectDescriptionFactory = effectDescriptionFactory;
/*    */   }
/*    */   
/*    */   public Token createStaticObjectToken(Prop prop, WorldCoordinate coord, float scale, float angle, float z, String resourceName, float[] tintColor, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/* 21 */     return new CreateStaticObjectToken(prop, coord, scale, angle, z, resourceName, tintColor, tokenTargetNode, tileCoord, resourceGetter);
/*    */   }
/*    */   
/*    */   public Token createDecalToken(Prop prop, WorldCoordinate coord, float scale, float angle, int orderIndex, String resourceName, float[] tintColor, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/* 25 */     return new CreateDecalToken(prop, coord, scale, angle, orderIndex, resourceName, tintColor, tokenTargetNode, tileCoord, resourceGetter, this.effectDescriptionFactory);
/*    */   }
/*    */   
/*    */   public Token createAreaToken(Prop prop, List<CPoint2D> points, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/* 29 */     return new CreateAreaToken(prop, points, tokenTargetNode, tileCoord, resourceGetter, this.effectDescriptionFactory);
/*    */   }
/*    */   
/*    */   public Token createParticleObjectToken(String name, WorldCoordinate coord, float scale, float angle, float z, String resourceName, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/* 33 */     return new CreateParticleToken(name, coord, scale, angle, z, resourceName, tokenTargetNode, tileCoord, this.effectDescriptionFactory);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\SpatialChunkTokenFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */