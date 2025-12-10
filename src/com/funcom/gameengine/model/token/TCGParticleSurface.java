/*    */ package com.funcom.gameengine.model.token;
/*    */ 
/*    */ import com.funcom.gameengine.model.ParticleSurface;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.jme.scene.Node;
/*    */ import com.jme.scene.Spatial;
/*    */ import com.turborilla.jops.jme.JopsNode;
/*    */ 
/*    */ public class TCGParticleSurface
/*    */   implements ParticleSurface {
/*    */   public TCGParticleSurface(Node particleNode) {
/* 12 */     this.particleNode = particleNode;
/*    */   }
/*    */   private Node particleNode;
/*    */   
/*    */   public void addWorldParticleEmitter(JopsNode jopsNode) {
/* 17 */     this.particleNode.attachChild((Spatial)jopsNode);
/*    */   }
/*    */   public void addWorldParticles(JopsNode jopsNode) {
/* 20 */     this.particleNode.attachChild((Spatial)jopsNode.getParticleNode());
/*    */   }
/*    */   
/*    */   public void removeWorldParticles(JopsNode jopsNode) {
/* 24 */     this.particleNode.detachChild((Spatial)jopsNode.getParticleNode());
/*    */   }
/*    */ 
/*    */   
/*    */   public void addDisconnectedMeshEffect(PropNode mesh) {
/* 29 */     this.particleNode.attachChild((Spatial)mesh);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\TCGParticleSurface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */