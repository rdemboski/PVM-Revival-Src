/*    */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.props.Prop;
/*    */ import com.funcom.gameengine.model.token.TokenTargetNode;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.jme.scene.Spatial;
/*    */ import java.awt.Point;
/*    */ import java.util.concurrent.Callable;
/*    */ import java.util.concurrent.Future;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CreateParticleLMToken
/*    */   extends LoadingManagerToken
/*    */ {
/* 22 */   Future<PropNode> ParticleFuture = null;
/*    */   
/* 24 */   String name = "";
/* 25 */   WorldCoordinate coord = null;
/* 26 */   float scale = 1.0F;
/* 27 */   float angle = 0.0F;
/* 28 */   float z = 0.0F;
/* 29 */   String resourceName = "";
/* 30 */   TokenTargetNode tokenTargetNode = null;
/* 31 */   Point tileCoord = null;
/* 32 */   DireEffectDescriptionFactory effectDescriptionFactory = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CreateParticleLMToken(String name, WorldCoordinate coord, float scale, float angle, float z, String resourceName, TokenTargetNode tokenTargetNode, Point tileCoord, DireEffectDescriptionFactory effectDescriptionFactory) {
/* 38 */     this.name = name;
/* 39 */     this.coord = coord;
/* 40 */     this.scale = scale;
/* 41 */     this.angle = angle;
/* 42 */     this.z = z;
/* 43 */     this.resourceName = resourceName;
/* 44 */     this.tokenTargetNode = tokenTargetNode;
/* 45 */     this.tileCoord = tileCoord;
/* 46 */     this.effectDescriptionFactory = effectDescriptionFactory;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean processRequestAssets() {
/* 51 */     Callable<PropNode> callable = new LoadParticleCallable();
/* 52 */     this.ParticleFuture = (Future)LoadingManager.INSTANCE.submitCallable(callable);
/*    */     
/* 54 */     return true;
/*    */   }
/*    */   
/*    */   public boolean processWaitingAssets() {
/* 58 */     return (this.ParticleFuture == null || this.ParticleFuture.isDone());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean processGame() throws Exception {
/* 64 */     PropNode propNode = null;
/* 65 */     if (this.ParticleFuture != null && this.ParticleFuture.isDone()) {
/* 66 */       propNode = this.ParticleFuture.get();
/*    */     } else {
/*    */       
/* 69 */       Callable<PropNode> callable = new LoadParticleCallable();
/* 70 */       propNode = callable.call();
/*    */     } 
/* 72 */     this.ParticleFuture = null;
/*    */     
/* 74 */     this.tokenTargetNode.attachAnimatedChild((Spatial)propNode);
/* 75 */     propNode.playDfx(this.resourceName);
/* 76 */     propNode.updateRenderState();
/* 77 */     this.tokenTargetNode.updateRenderState();
/*    */     
/* 79 */     this.ParticleFuture = null;
/* 80 */     return true;
/*    */   }
/*    */   
/*    */   public class LoadParticleCallable
/*    */     implements Callable {
/*    */     public PropNode call() {
/* 86 */       Prop prop = new Prop(CreateParticleLMToken.this.name, CreateParticleLMToken.this.coord);
/* 87 */       PropNode propNode = new PropNode(prop, 17, CreateParticleLMToken.this.resourceName, CreateParticleLMToken.this.effectDescriptionFactory);
/* 88 */       setUpPropNode(propNode);
/* 89 */       return propNode;
/*    */     }
/*    */     
/*    */     protected void setUpPropNode(PropNode propNode) {
/* 93 */       propNode.updatePropVectors(CreateParticleLMToken.this.tileCoord);
/* 94 */       propNode.setAngle(CreateParticleLMToken.this.angle);
/* 95 */       propNode.setScale(CreateParticleLMToken.this.scale);
/* 96 */       propNode.getLocalTranslation().setY(CreateParticleLMToken.this.z);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\CreateParticleLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */