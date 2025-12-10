/*    */ package com.funcom.gameengine.model.token;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.ResourceGetter;
/*    */ import com.funcom.gameengine.model.factories.BillboardFactory;
/*    */ import com.funcom.gameengine.model.props.Prop;
/*    */ import com.funcom.gameengine.utils.SpatialUtils;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.jme.scene.Spatial;
/*    */ import java.awt.Point;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CreateStaticObjectToken
/*    */   implements Token
/*    */ {
/*    */   protected Prop prop;
/*    */   protected WorldCoordinate coord;
/*    */   protected float scale;
/*    */   protected float angle;
/*    */   protected float z;
/*    */   protected String resourceName;
/*    */   protected TokenTargetNode tokenTargetNode;
/*    */   protected Point tileCoord;
/*    */   protected ResourceGetter resourceGetter;
/*    */   protected float[] tintColor;
/*    */   
/*    */   public CreateStaticObjectToken(Prop prop, WorldCoordinate coord, float scale, float angle, float z, String resourceName, float[] tintColor, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/* 33 */     this.prop = prop;
/* 34 */     this.coord = coord;
/* 35 */     this.scale = scale;
/* 36 */     this.angle = angle;
/* 37 */     this.z = z;
/* 38 */     this.resourceName = resourceName;
/* 39 */     this.tokenTargetNode = tokenTargetNode;
/* 40 */     this.tileCoord = tileCoord;
/* 41 */     this.resourceGetter = resourceGetter;
/* 42 */     this.tintColor = tintColor;
/*    */   }
/*    */   
/*    */   public Token.TokenType getTokenType() {
/* 46 */     return Token.TokenType.GAME_THREAD;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void process() {
/* 58 */     PropNode propNode = BillboardFactory.newBillboard(this.resourceGetter, this.prop, this.resourceName, this.angle, this.scale, this.tileCoord, SpatialUtils.convertToColorRGBA(this.tintColor), null);
/*    */     
/* 60 */     propNode.setRunsDfxs(false);
/* 61 */     propNode.getLocalTranslation().setY(this.z);
/* 62 */     this.tokenTargetNode.attachStaticChild((Spatial)propNode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 68 */     return getClass().getName() + "[" + this.resourceName + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\CreateStaticObjectToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */