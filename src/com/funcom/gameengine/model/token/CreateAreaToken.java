/*    */ package com.funcom.gameengine.model.token;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*    */ import com.funcom.commons.jme.cpolygon.CPoint2D;
/*    */ import com.funcom.gameengine.collisiondetection.Area;
/*    */ import com.funcom.gameengine.model.ResourceGetter;
/*    */ import com.funcom.gameengine.model.props.Prop;
/*    */ import com.jme.scene.Spatial;
/*    */ import java.awt.Point;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class CreateAreaToken
/*    */   implements Token
/*    */ {
/*    */   private Prop prop;
/*    */   private List<CPoint2D> points;
/*    */   private TokenTargetNode tokenTargetNode;
/*    */   private Point tileCoord;
/*    */   private ResourceGetter resourceGetter;
/*    */   private DireEffectDescriptionFactory direEffectDescriptionFactory;
/*    */   
/*    */   public CreateAreaToken(Prop prop, List<CPoint2D> points, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter, DireEffectDescriptionFactory direEffectDescriptionFactory) {
/* 24 */     this.prop = prop;
/* 25 */     this.points = points;
/* 26 */     this.tokenTargetNode = tokenTargetNode;
/* 27 */     this.tileCoord = tileCoord;
/* 28 */     this.resourceGetter = resourceGetter;
/* 29 */     this.direEffectDescriptionFactory = direEffectDescriptionFactory;
/*    */   }
/*    */   
/*    */   public Token.TokenType getTokenType() {
/* 33 */     return Token.TokenType.GAME_THREAD;
/*    */   }
/*    */   
/*    */   public void process() {
/* 37 */     Area area = new Area(this.prop, "no-resource", this.points, this.direEffectDescriptionFactory);
/* 38 */     this.tokenTargetNode.attachStaticChild((Spatial)area);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\CreateAreaToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */