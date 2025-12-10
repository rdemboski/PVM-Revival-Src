/*    */ package com.funcom.gameengine.view.particles;
/*    */ 
/*    */ import com.funcom.commons.jme.LayeredRenderPass;
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jme.scene.Node;
/*    */ import com.jme.scene.Spatial;
/*    */ import com.jme.system.DisplaySystem;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import org.softmed.jops.ParticleSystem;
/*    */ 
/*    */ public class GuiParticlesRenderPass
/*    */   extends LayeredRenderPass
/*    */ {
/*    */   private Node rootNode;
/*    */   private Set<GuiParticleJoint> guiParticleJoints;
/*    */   private GuiParticlesConfiguration configuration;
/*    */   
/*    */   public GuiParticlesRenderPass(GuiParticlesConfiguration configuration) {
/* 20 */     this.configuration = configuration;
/* 21 */     this.guiParticleJoints = new HashSet<GuiParticleJoint>();
/*    */     
/* 23 */     this.rootNode = new Node(getClass().getSimpleName());
/* 24 */     if (!configuration.isParticlesLit())
/* 25 */       this.rootNode.setLightCombineMode(Spatial.LightCombineMode.Off); 
/* 26 */     add((Spatial)this.rootNode);
/*    */   }
/*    */   
/*    */   public void addGuiParticleJoint(GuiParticleJoint guiParticleJoint) {
/* 30 */     this.guiParticleJoints.add(guiParticleJoint);
/* 31 */     this.rootNode.attachChild((Spatial)guiParticleJoint.getRenderNode());
/*    */   }
/*    */   
/*    */   public void removeGuiParticleJoint(GuiParticleJoint guiParticleJoint) {
/* 35 */     this.guiParticleJoints.remove(guiParticleJoint);
/* 36 */     this.rootNode.detachChild((Spatial)guiParticleJoint.getRenderNode());
/*    */   }
/*    */   
/*    */   public Set<GuiParticleJoint> getGuiParticleJoinPoints() {
/* 40 */     return this.guiParticleJoints;
/*    */   }
/*    */   
/*    */   public void setParticlesOnJoint(String jointName, ParticleSystem particleSystem) {
/* 44 */     getByName(jointName).setParticleSystem(particleSystem);
/*    */   }
/*    */   
/*    */   public void triggerParticleEffect(String jointName) {
/* 48 */     getByName(jointName).play();
/*    */   }
/*    */   
/*    */   public GuiParticleJoint getByName(String jointName) {
/* 52 */     for (GuiParticleJoint guiParticleJoint : this.guiParticleJoints) {
/* 53 */       if (guiParticleJoint.getName().equals(jointName))
/* 54 */         return guiParticleJoint; 
/*    */     } 
/* 56 */     throw new IllegalStateException("No gui particle joint defined: " + jointName);
/*    */   }
/*    */   
/*    */   public boolean hasJoint(String jointName) {
/* 60 */     for (GuiParticleJoint guiParticleJoint : this.guiParticleJoints) {
/* 61 */       if (guiParticleJoint.getName().equals(jointName))
/* 62 */         return true; 
/*    */     } 
/* 64 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void doUpdate(float v) {
/* 72 */     this.rootNode.updateGeometricState(v, true);
/*    */   }
/*    */   
/*    */   public void doRender(Renderer renderer) {
/* 76 */     DisplaySystem.getDisplaySystem().getRenderer().clearZBuffer();
/* 77 */     for (GuiParticleJoint guiParticleJoint : this.guiParticleJoints) {
/* 78 */       if (guiParticleJoint.isPlaying())
/* 79 */         renderer.draw((Spatial)guiParticleJoint.getRenderNode()); 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void removeAllGuiParticleJoints() {
/* 84 */     this.guiParticleJoints.clear();
/* 85 */     this.rootNode.detachAllChildren();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\particles\GuiParticlesRenderPass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */