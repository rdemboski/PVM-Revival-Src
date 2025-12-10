/*     */ package com.turborilla.jops.jme;
/*     */ 
/*     */ import com.funcom.commons.PerformanceGraphNode;
/*     */ import com.funcom.commons.jme.md5importer.DisposableController;
/*     */ import com.jme.bounding.BoundingBox;
/*     */ import com.jme.bounding.BoundingVolume;
/*     */ import com.jme.math.Quaternion;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.Camera;
/*     */ import com.jme.scene.Controller;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.openmali.vecmath2.Matrix4f;
/*     */ import org.openmali.vecmath2.Point3f;
/*     */ import org.openmali.vecmath2.Quaternion4f;
/*     */ import org.softmed.jops.Generator;
/*     */ import org.softmed.jops.ParticleSystem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JopsNode
/*     */   extends Node
/*     */ {
/*     */   private Node particleNode;
/*     */   private ArrayList<ParticleGeneratorMesh> generators;
/*     */   private ParticleSystem particleSystem;
/*     */   private Camera camera;
/*     */   private float factor;
/*     */   private float offset;
/*     */   private boolean killInstantly;
/*     */   private boolean dieing = false;
/*     */   private Quaternion4f maliQuat;
/*     */   private float[] position;
/*     */   private Vector3f translation;
/*     */   private Quaternion rotation;
/*     */   
/*     */   public JopsNode(String name, boolean killInstantly) {
/*  68 */     this(name, -0.5F, -0.5F, killInstantly);
/*     */   }
/*     */   
/*     */   public JopsNode(String name, float factor, float offset, boolean killInstantly) {
/*  72 */     super(name);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     this.maliQuat = new Quaternion4f();
/*  85 */     this.position = new float[3];
/*  86 */     this.translation = new Vector3f();
/*  87 */     this.rotation = getWorldRotation(); this.factor = factor; this.offset = offset; this.killInstantly = killInstantly; init(); } public JopsNode() { this.maliQuat = new Quaternion4f(); this.position = new float[3]; this.translation = new Vector3f(); this.rotation = getWorldRotation();
/*     */     init(); }
/*     */   
/*     */   private void init() {
/*     */     this.particleNode = new Node(getName() + ":ParticleNode");
/*     */     this.generators = new ArrayList<ParticleGeneratorMesh>();
/*     */   }
/*     */   
/*     */   public void update(float tpf) {
/*  96 */     if (this.particleSystem != null && this.particleNode.getParent() != this) {
/*     */       
/*  98 */       this.translation.set(getWorldTranslation());
/*  99 */       this.rotation.set(getWorldRotation());
/*     */       
/* 101 */       this.translation = this.translation.subtract(this.particleNode.getWorldTranslation());
/*     */ 
/*     */ 
/*     */       
/* 105 */       if (getLocalScale().length() == 0.0F) {
/* 106 */         setLocalScale(1.0F);
/*     */       }
/* 108 */       this.particleNode.getLocalScale().set(getLocalScale());
/*     */       
/* 110 */       this.translation.divideLocal(getWorldScale());
/*     */ 
/*     */       
/* 113 */       this.maliQuat.set(this.rotation.x, this.rotation.y, this.rotation.z, this.rotation.w);
/* 114 */       this.particleSystem.getRotation().set(this.maliQuat);
/*     */ 
/*     */ 
/*     */       
/* 118 */       this.rotation.inverseLocal();
/* 119 */       this.rotation.mult(this.translation, this.translation);
/* 120 */       this.particleSystem.getPosition().set(this.translation.toArray(this.position));
/*     */     } 
/*     */     
/* 123 */     if (this.dieing) {
/* 124 */       if (this.generators.isEmpty() || this.particleSystem.isRemove())
/*     */       {
/* 126 */         kill();
/*     */       }
/*     */       
/* 129 */       ArrayList<ParticleGeneratorMesh> gens = (ArrayList<ParticleGeneratorMesh>)this.generators.clone();
/* 130 */       for (ParticleGeneratorMesh generator : gens) {
/* 131 */         if (!generator.hasLivingParticles()) {
/* 132 */           this.generators.remove(generator);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void endEffect() {
/* 139 */     if (this.killInstantly) {
/* 140 */       kill();
/*     */     } else {
/* 142 */       dieOut();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void kill() {
/* 147 */     dispose();
/* 148 */     removeFromParent();
/* 149 */     this.particleNode.removeFromParent();
/* 150 */     killSystem();
/*     */   }
/*     */   
/*     */   public void killSystem() {
/* 154 */     this.particleSystem.setRemove(true);
/*     */   }
/*     */   
/*     */   private void dieOut() {
/* 158 */     for (ParticleGeneratorMesh generator : this.generators) {
/* 159 */       generator.dieOut();
/*     */     }
/* 161 */     this.dieing = true;
/*     */   }
/*     */   
/*     */   private ParticleGeneratorMesh createGenerator(Generator generator) {
/* 165 */     ParticleGeneratorMesh particleGenerator = new OffsettedParticleGeneratorMesh(generator, this.factor, this.offset);
/* 166 */     particleGenerator.setModelBound((BoundingVolume)new BoundingBox());
/* 167 */     this.particleNode.attachChild((Spatial)particleGenerator);
/* 168 */     particleGenerator.setCamera(this.camera);
/* 169 */     return particleGenerator;
/*     */   }
/*     */   
/*     */   private void installGenerators() {
/* 173 */     List<Generator> particleGenerators = this.particleSystem.getGenerators();
/* 174 */     for (Generator generator : particleGenerators) {
/* 175 */       ParticleGeneratorMesh addGenerator = createGenerator(generator);
/* 176 */       if (!this.killInstantly) {
/* 177 */         this.generators.add(addGenerator);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ParticleSystem getParticleSystem() {
/* 185 */     return this.particleSystem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParticleSystem(ParticleSystem particleSystem) {
/* 192 */     this.particleSystem = particleSystem;
/* 193 */     if (particleSystem.getRotation() == null) particleSystem.setRotation(new Matrix4f(new float[] { 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     if (particleSystem.getPosition() == null) particleSystem.setPosition(new Point3f()); 
/* 202 */     installGenerators();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateGeometricState(float time, boolean initiator) {
/* 210 */     PerformanceGraphNode.startTiming(PerformanceGraphNode.TrackingStat.PARTICLES.statType);
/* 211 */     super.updateGeometricState(time, initiator);
/* 212 */     update(time);
/* 213 */     PerformanceGraphNode.endTiming(PerformanceGraphNode.TrackingStat.PARTICLES.statType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getParticleNode() {
/* 227 */     return this.particleNode;
/*     */   }
/*     */   
/*     */   public void setCamera(Camera camera) {
/* 231 */     this.camera = camera;
/*     */   }
/*     */   
/*     */   public void dispose() {
/* 235 */     for (Controller controller : getControllers()) {
/* 236 */       if (controller instanceof DisposableController)
/* 237 */         ((DisposableController)controller).dispose(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\turborilla\jops\jme\JopsNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */