/*     */ package com.funcom.tcg.client.ui;
/*     */ 
/*     */ import com.funcom.gameengine.model.ParticleSurface;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.scene.state.ZBufferState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jme.util.Timer;
/*     */ import com.jmex.bui.BGeomView;
/*     */ import com.turborilla.jops.jme.JopsNode;
/*     */ 
/*     */ 
/*     */ public abstract class AbstactAngledGeomView
/*     */   extends BGeomView
/*     */   implements ParticleSurface
/*     */ {
/*     */   private boolean lookAtDirty = true;
/*  22 */   private float viewDistance = 4.0F;
/*  23 */   protected Vector3f lookAtVector = new Vector3f();
/*  24 */   private Vector3f locationVector = new Vector3f();
/*  25 */   private PropNode geom = null;
/*  26 */   public float zoom = 1.0F;
/*  27 */   private float lookAtAngle = 22.5F;
/*     */   
/*     */   public void update(float time) {
/*  30 */     super.update(time);
/*  31 */     updateLookVector();
/*  32 */     updateLocationVector();
/*     */     
/*  34 */     getCamera().setFrustumPerspective(40.0F, getWidth() / getHeight(), 0.1F, 200.0F);
/*  35 */     getCamera().update();
/*  36 */     updateCharacterAngle(Timer.getTimer().getTimePerFrame());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateLookVector() {
/*  42 */     if (this.lookAtDirty) {
/*  43 */       calculateCameraPosition();
/*  44 */       this.lookAtDirty = false;
/*     */     } 
/*  46 */     getCamera().lookAt(this.lookAtVector, Vector3f.UNIT_Y);
/*     */   }
/*     */   
/*     */   private void updateLocationVector() {
/*  50 */     this.locationVector.set((float)Math.cos((this.lookAtAngle * 0.017453292F)), (float)Math.sin((this.lookAtAngle * 0.017453292F)), 0.0F);
/*  51 */     this.locationVector.normalizeLocal();
/*  52 */     this.locationVector.multLocal(this.viewDistance);
/*  53 */     this.locationVector.addLocal(this.lookAtVector);
/*     */ 
/*     */     
/*  56 */     getCamera().setLocation(this.locationVector);
/*     */   }
/*     */   
/*     */   protected void calculateCameraPosition() {
/*  60 */     if (getGeometry() == null)
/*     */       return; 
/*  62 */     getGeometry().getWorldBound().getCenter(this.lookAtVector);
/*  63 */     Vector3f volume = SpatialUtils.getBoundingVolume((Spatial)getGeometry());
/*     */     
/*  65 */     float maxLength = Math.max(Math.max(volume.getX(), volume.getY()), volume.getZ());
/*     */ 
/*     */     
/*  68 */     this.viewDistance = maxLength * 1.5F / this.zoom;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setZoom(float zoom) {
/*  73 */     this.zoom = zoom;
/*     */   }
/*     */   
/*     */   public void resetCameraPosition() {
/*  77 */     if (getGeometry() == null)
/*     */       return; 
/*  79 */     this.lookAtDirty = true;
/*  80 */     getGeometry().updateGeometricState(0.0F, true);
/*  81 */     update(0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PropNode getGeometry() {
/*  87 */     return this.geom;
/*     */   }
/*     */   
/*     */   public void setGeometry(PropNode propNode) {
/*  91 */     Node node = null;
/*  92 */     this.geom = propNode;
/*  93 */     if (propNode != null) {
/*  94 */       ZBufferState buf = DisplaySystem.getDisplaySystem().getRenderer().createZBufferState();
/*  95 */       buf.setEnabled(true);
/*  96 */       propNode.setRenderState((RenderState)buf);
/*  97 */       propNode.updateRenderState();
/*  98 */       node = new Node();
/*  99 */       node.attachChild((Spatial)propNode);
/*     */     } 
/* 101 */     super.setGeometry((Spatial)node);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGeometry(Spatial geom) {
/* 106 */     if (!(geom instanceof PropNode))
/* 107 */       throw new IllegalArgumentException("Meant to work with PropNode!"); 
/* 108 */     super.setGeometry(geom);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addWorldParticleEmitter(JopsNode jopsNode) {
/* 113 */     if (getGeometry() != null)
/* 114 */       ((Node)super.getGeometry()).attachChild((Spatial)jopsNode); 
/*     */   }
/*     */   
/*     */   public void addWorldParticles(JopsNode jopsNode) {
/* 118 */     if (getGeometry() != null)
/* 119 */       ((Node)super.getGeometry()).attachChild((Spatial)jopsNode.getParticleNode()); 
/*     */   }
/*     */   
/*     */   public void removeWorldParticles(JopsNode jopsNode) {
/* 123 */     if (getGeometry() != null) {
/* 124 */       ((Node)super.getGeometry()).detachChild((Spatial)jopsNode.getParticleNode());
/*     */     }
/*     */   }
/*     */   
/*     */   public void addDisconnectedMeshEffect(PropNode mesh) {
/* 129 */     if (getGeometry() != null)
/* 130 */       ((Node)super.getGeometry()).attachChild((Spatial)mesh); 
/*     */   }
/*     */   
/*     */   public void setLookAtAngle(float lookAtAngle) {
/* 134 */     this.lookAtAngle = lookAtAngle;
/*     */   }
/*     */   
/*     */   protected abstract void updateCharacterAngle(float paramFloat);
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\AbstactAngledGeomView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */