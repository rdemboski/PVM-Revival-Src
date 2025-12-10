/*    */ package com.funcom.gameengine.model.token;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.props.Prop;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.jme.scene.Spatial;
/*    */ import java.awt.Point;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CreateParticleToken
/*    */   implements Token
/*    */ {
/*    */   private String name;
/*    */   private WorldCoordinate coord;
/*    */   private float scale;
/*    */   private float angle;
/*    */   private float z;
/*    */   private String resourceName;
/*    */   private TokenTargetNode tokenTargetNode;
/*    */   private Point tileCoord;
/*    */   private DireEffectDescriptionFactory effectDescriptionFactory;
/*    */   
/*    */   public CreateParticleToken(String name, WorldCoordinate coord, float scale, float angle, float z, String resourceName, TokenTargetNode tokenTargetNode, Point tileCoord, DireEffectDescriptionFactory effectDescriptionFactory) {
/* 27 */     this.name = name;
/* 28 */     this.coord = coord;
/* 29 */     this.scale = scale;
/* 30 */     this.angle = angle;
/* 31 */     this.z = z;
/* 32 */     this.resourceName = resourceName;
/* 33 */     this.tokenTargetNode = tokenTargetNode;
/* 34 */     this.tileCoord = tileCoord;
/* 35 */     this.effectDescriptionFactory = effectDescriptionFactory;
/*    */   }
/*    */   
/*    */   public Token.TokenType getTokenType() {
/* 39 */     return Token.TokenType.GAME_THREAD;
/*    */   }
/*    */   
/*    */   public void process() {
/* 43 */     Prop prop = new Prop(this.name, this.coord);
/* 44 */     PropNode propNode = new PropNode(prop, 17, this.resourceName, this.effectDescriptionFactory);
/* 45 */     setUpPropNode(propNode);
/*    */     
/* 47 */     this.tokenTargetNode.attachAnimatedChild((Spatial)propNode);
/* 48 */     propNode.playDfx(this.resourceName);
/* 49 */     propNode.updateRenderState();
/* 50 */     this.tokenTargetNode.updateRenderState();
/*    */   }
/*    */   
/*    */   protected void setUpPropNode(PropNode propNode) {
/* 54 */     propNode.updatePropVectors(this.tileCoord);
/* 55 */     propNode.setAngle(this.angle);
/* 56 */     propNode.setScale(this.scale);
/* 57 */     propNode.getLocalTranslation().setY(this.z);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 62 */     return getClass().getName() + "[" + this.resourceName + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\CreateParticleToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */