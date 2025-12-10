/*     */ package com.funcom.gameengine.view.particles;
/*     */ 
/*     */ import com.funcom.gameengine.Updated;
/*     */ import com.funcom.gameengine.model.UpdatedController;
/*     */ import com.jme.math.Vector2f;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.Camera;
/*     */ import com.jme.scene.BillboardNode;
/*     */ import com.jme.scene.Controller;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.turborilla.jops.jme.JopsUtils;
/*     */ import com.turborilla.jops.jme.ParticleGeneratorMesh;
/*     */ import java.util.Set;
/*     */ import org.softmed.jops.ParticleSystem;
/*     */ 
/*     */ public class GuiParticleJoint
/*     */ {
/*     */   private String name;
/*     */   private Vector2f screenPosition;
/*     */   private ParticleSystem particleSystem;
/*     */   private BillboardNode billboardNode;
/*     */   private boolean playing;
/*     */   private boolean continuousPlaying;
/*     */   private GuiParticleMotionController controller;
/*     */   private Camera camera;
/*     */   
/*     */   public GuiParticleJoint(String name, Vector2f screenPosition, ParticleSystem particleSystem, Camera camera) {
/*  30 */     this.name = name;
/*  31 */     this.screenPosition = screenPosition;
/*  32 */     this.playing = false;
/*  33 */     this.continuousPlaying = false;
/*  34 */     this.billboardNode = new BillboardNode(particleSystem.getName() + "_billboardparticlenode")
/*     */       {
/*     */         public void updateGeometricState(float v, boolean b) {
/*  37 */           super.updateGeometricState(v, b);
/*     */         }
/*     */       };
/*  40 */     this.billboardNode.setAlignment(1);
/*  41 */     this.billboardNode.addController((Controller)new UpdatedController(new WorldToScreenPositioning(this)));
/*  42 */     this.camera = camera;
/*     */     
/*  44 */     scaleByCameraNearPlane();
/*     */     
/*  46 */     setParticleSystem(particleSystem);
/*     */   }
/*     */   
/*     */   private void scaleByCameraNearPlane() {
/*  50 */     float frustumNear = this.camera.getFrustumNear();
/*  51 */     this.billboardNode.setLocalScale(frustumNear);
/*     */   }
/*     */   
/*     */   public Vector2f getScreenPosition() {
/*  55 */     return this.screenPosition;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  59 */     return this.name;
/*     */   }
/*     */   
/*     */   public ParticleSystem getParticleSystem() {
/*  63 */     return this.particleSystem;
/*     */   }
/*     */   
/*     */   public void setParticleSystem(ParticleSystem particleSystem) {
/*  67 */     this.particleSystem = particleSystem;
/*  68 */     this.billboardNode.detachAllChildren();
/*     */     
/*  70 */     if (particleSystem != null) {
/*  71 */       Set<ParticleGeneratorMesh> meshes = JopsUtils.createParticleGeneratorMeshes(particleSystem, this.camera);
/*  72 */       for (ParticleGeneratorMesh mesh : meshes)
/*  73 */         this.billboardNode.attachChild((Spatial)mesh); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Vector3f getLocalScale() {
/*  78 */     return this.billboardNode.getLocalScale();
/*     */   }
/*     */   
/*     */   public void setLocalScale(float v) {
/*  82 */     this.billboardNode.setLocalScale(v);
/*     */   }
/*     */   
/*     */   public void setLocalScale(Vector3f vector3f) {
/*  86 */     this.billboardNode.setLocalScale(vector3f);
/*     */   }
/*     */   
/*     */   public Node getRenderNode() {
/*  90 */     return (Node)this.billboardNode;
/*     */   }
/*     */   
/*     */   public void play() {
/*  94 */     this.playing = true;
/*  95 */     this.particleSystem.reset();
/*  96 */     if (this.controller != null)
/*  97 */       this.controller.resetTime(); 
/*     */   }
/*     */   
/*     */   public void stop() {
/* 101 */     this.playing = false;
/*     */   }
/*     */   
/*     */   public boolean isContinuousPlaying() {
/* 105 */     return this.continuousPlaying;
/*     */   }
/*     */   
/*     */   public void setContinuousPlaying(boolean continuousPlaying) {
/* 109 */     this.continuousPlaying = continuousPlaying;
/*     */   }
/*     */   
/*     */   public boolean isPlaying() {
/* 113 */     return (this.continuousPlaying || this.playing);
/*     */   }
/*     */   
/*     */   public void setController(GuiParticleMotionController controller) {
/* 117 */     this.controller = controller;
/*     */   }
/*     */   
/*     */   private static class WorldToScreenPositioning implements Updated {
/*     */     private GuiParticleJoint guiParticleJoint;
/*     */     
/*     */     private WorldToScreenPositioning(GuiParticleJoint guiParticleJoint) {
/* 124 */       this.guiParticleJoint = guiParticleJoint;
/*     */     }
/*     */ 
/*     */     
/*     */     public void update(float v) {
/* 129 */       if (!this.guiParticleJoint.isPlaying())
/*     */         return; 
/* 131 */       if (this.guiParticleJoint.controller != null && this.guiParticleJoint.controller.isDone()) {
/* 132 */         this.guiParticleJoint.playing = false;
/*     */         
/*     */         return;
/*     */       } 
/* 136 */       Vector2f position = calculateScreenPosition(v);
/* 137 */       DisplaySystem ds = DisplaySystem.getDisplaySystem();
/* 138 */       Vector3f wc = ds.getWorldCoordinates(position, 0.92F);
/* 139 */       this.guiParticleJoint.billboardNode.setLocalTranslation(wc);
/*     */       
/* 141 */       this.guiParticleJoint.getParticleSystem().processFrame(v);
/*     */     }
/*     */     
/*     */     private Vector2f calculateScreenPosition(float v) {
/*     */       Vector2f position;
/* 146 */       if (this.guiParticleJoint.controller != null) {
/* 147 */         position = this.guiParticleJoint.controller.getPosition(v);
/*     */       } else {
/* 149 */         position = this.guiParticleJoint.getScreenPosition();
/* 150 */       }  return position;
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString() {
/* 155 */     return "GuiParticleJoint{name='" + this.name + '\'' + ", screenPosition=" + this.screenPosition + ", particleSystem='" + this.particleSystem.getName() + "'" + ", playing=" + this.playing + ", continuousPlaying=" + this.continuousPlaying + ", controller=" + this.controller + '}';
/*     */   }
/*     */   
/*     */   public static interface GuiParticleMotionController {
/*     */     void resetTime();
/*     */     
/*     */     Vector2f getPosition(float param1Float);
/*     */     
/*     */     boolean isDone();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\particles\GuiParticleJoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */