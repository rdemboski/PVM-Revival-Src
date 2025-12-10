/*     */ package com.funcom.gameengine.view;
/*     */ 
/*     */ import com.funcom.gameengine.WorldOrigin;
/*     */ import com.funcom.gameengine.model.ParticleSurface;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import com.funcom.gameengine.view.particles.Md5JointFollowController;
/*     */ import com.jme.math.Quaternion;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.Controller;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.turborilla.jops.jme.JopsNode;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import org.softmed.jops.Generator;
/*     */ import org.softmed.jops.ParticleSystem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Effects
/*     */ {
/*     */   private static final String GROUND_PARTICLE_NAME = "ground-particle-node";
/*     */   private static final String BONE_PARTICLE_NAME = "bone-following-particle-node";
/*     */   private static final String DISCONNECTED_EMITTER_PARTICLE_NAME = "disconnected-particle-node";
/*     */   private RepresentationalNode target;
/*     */   private ColorRGBA tintRgba;
/*     */   private ParticleSurface particleSurface;
/*  30 */   private TintMode tintMode = TintMode.OFF;
/*     */   
/*     */   private boolean tintLock;
/*  33 */   private final ColorRGBA flashRgba = new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   
/*  35 */   private final float BASE_FLASH_RATE = 0.1F;
/*  36 */   private float flashTime = 0.1F;
/*     */   
/*     */   public Effects(RepresentationalNode target) {
/*  39 */     if (target == null)
/*  40 */       throw new IllegalArgumentException("target = null"); 
/*  41 */     this.target = target;
/*  42 */     this.tintRgba = new ColorRGBA();
/*     */   }
/*     */   
/*     */   public void setParticleSurface(ParticleSurface particleSurface) {
/*  46 */     this.particleSurface = particleSurface;
/*     */   }
/*     */   
/*     */   public void setTintRgba(float r, float g, float b, float a) {
/*  50 */     this.tintRgba.set(r, g, b, a);
/*     */   }
/*     */   
/*     */   public void setTintRbga(ColorRGBA color) {
/*  54 */     if (this.tintLock) {
/*  55 */       throw new IllegalStateException("tint is locked");
/*     */     }
/*  57 */     this.tintRgba.set(color);
/*     */   }
/*     */   
/*     */   public ColorRGBA getTintRgba() {
/*  61 */     return this.tintRgba;
/*     */   }
/*     */   
/*     */   public JopsNode addGroundParticleLocal(ParticleSystem particleSystem, Vector3f translation, float scale, Quaternion rotation, boolean killInstantly) {
/*  65 */     JopsNode particleNode = createGroundParticleNode(particleSystem, translation, scale, rotation, false, killInstantly);
/*  66 */     particleNode.attachChild((Spatial)particleNode.getParticleNode());
/*  67 */     this.target.attachChild((Spatial)particleNode);
/*  68 */     return particleNode;
/*     */   }
/*     */   
/*     */   public JopsNode addGroundParticleWorld(ParticleSystem particleSystem, Vector3f translation, float scale, Quaternion rotation, boolean killInstantly) {
/*  72 */     if (this.particleSurface == null)
/*  73 */       throw new RuntimeException("Cannot do this without setting in the particlesurface instance"); 
/*  74 */     JopsNode particleNode = createWorldGroundParticleNode(particleSystem, translation, scale, rotation, false, killInstantly);
/*  75 */     this.particleSurface.addWorldParticles(particleNode);
/*  76 */     this.target.attachChild((Spatial)particleNode);
/*  77 */     return particleNode;
/*     */   }
/*     */   
/*     */   private JopsNode createGroundParticleNode(ParticleSystem particleSystem, Vector3f translation, float scale, Quaternion rotation, boolean disconnectedEmitter, boolean killInstantly) {
/*     */     JopsNode jopsNode;
/*  82 */     if (disconnectedEmitter) {
/*  83 */       jopsNode = new JopsNode("disconnected-particle-node", killInstantly);
/*     */     } else {
/*  85 */       jopsNode = new JopsNode("ground-particle-node", killInstantly);
/*     */     } 
/*  87 */     jopsNode.setLocalTranslation(translation);
/*  88 */     jopsNode.setLocalScale(scale);
/*  89 */     jopsNode.setLocalRotation(rotation);
/*  90 */     jopsNode.setCamera(DisplaySystem.getDisplaySystem().getRenderer().getCamera());
/*  91 */     jopsNode.setParticleSystem(particleSystem);
/*  92 */     return jopsNode;
/*     */   }
/*     */   
/*     */   private JopsNode createWorldGroundParticleNode(ParticleSystem particleSystem, Vector3f offset, float scale, Quaternion rotation, boolean disconnectedEmitter, boolean killInstantly) {
/*  96 */     Quaternion rot = rotation.mult(this.target.getLocalRotation());
/*  97 */     Vector3f rotatedOffset = rot.mult(offset);
/*  98 */     Vector3f actualTranslation = disconnectedEmitter ? rotatedOffset.add(this.target.getLocalTranslation()).add(WorldOrigin.instance().getX(), 0.0F, WorldOrigin.instance().getY()) : rotatedOffset;
/*  99 */     return createGroundParticleNode(particleSystem, actualTranslation, scale, rot, disconnectedEmitter, killInstantly);
/*     */   }
/*     */   
/*     */   public JopsNode addBoneParticleLocal(ParticleSystem particleSystem, String jointName, Vector3f offset, float scale, Quaternion rotation, boolean killInstantly) {
/* 103 */     JopsNode jopsNode = createBoneFollowingParticle(particleSystem, jointName, offset, scale, rotation, killInstantly);
/* 104 */     jopsNode.attachChild((Spatial)jopsNode.getParticleNode());
/* 105 */     this.target.attachChild((Spatial)jopsNode);
/* 106 */     return jopsNode;
/*     */   }
/*     */   
/*     */   public JopsNode addBoneParticleWorld(ParticleSystem particleSystem, String jointName, Vector3f offset, float scale, Quaternion rotation, boolean killInstantly) {
/* 110 */     if (this.particleSurface == null)
/* 111 */       throw new RuntimeException("Cannot do this without setting in the World instance"); 
/* 112 */     JopsNode jopsNode = createBoneFollowingParticle(particleSystem, jointName, offset, scale, rotation, killInstantly);
/* 113 */     this.particleSurface.addWorldParticles(jopsNode);
/* 114 */     this.target.attachChild((Spatial)jopsNode);
/* 115 */     return jopsNode;
/*     */   }
/*     */   
/*     */   public JopsNode addDisconnectedGroundParticleWorld(ParticleSystem particleSystem, Vector3f offset, float scale, Quaternion rotation, boolean killInstantly) {
/* 119 */     if (this.particleSurface == null)
/* 120 */       throw new RuntimeException("Cannot do this without setting in the World instance"); 
/* 121 */     JopsNode particleNode = createWorldGroundParticleNode(particleSystem, offset, scale, rotation, true, killInstantly);
/* 122 */     particleNode.attachChild((Spatial)particleNode.getParticleNode());
/* 123 */     this.particleSurface.addWorldParticleEmitter(particleNode);
/* 124 */     return particleNode;
/*     */   }
/*     */   
/*     */   public JopsNode addDisconnectedBoneParticleWorld(ParticleSystem particleSystem, String jointName, Vector3f offset, float scale, Quaternion rotation, boolean killInstantly) {
/* 128 */     if (this.particleSurface == null)
/* 129 */       throw new RuntimeException("Cannot do this without setting in the World instance"); 
/* 130 */     JopsNode jopsNode = new JopsNode("disconnected-particle-node", killInstantly);
/* 131 */     jopsNode.setLocalScale(scale);
/* 132 */     jopsNode.setCamera(DisplaySystem.getDisplaySystem().getRenderer().getCamera());
/* 133 */     jopsNode.setParticleSystem(particleSystem);
/*     */     
/* 135 */     Md5JointFollowController followController = new Md5JointFollowController((Spatial)jopsNode, this.target.getFollowJoint(jointName));
/* 136 */     followController.setRotationDisplacement(rotation);
/* 137 */     followController.setTranslationDisplacement(offset);
/* 138 */     followController.setSwitchCoordSystem(true);
/* 139 */     followController.setTranslateScale(this.target.getRepresentation().getLocalScale());
/* 140 */     followController.update(0.0F);
/*     */     
/* 142 */     this.particleSurface.addWorldParticleEmitter(jopsNode);
/* 143 */     return jopsNode;
/*     */   }
/*     */ 
/*     */   
/*     */   private JopsNode createBoneFollowingParticle(ParticleSystem particleSystem, String jointName, Vector3f offset, float scale, Quaternion rotation, boolean killInstantly) {
/* 148 */     JopsNode jopsNode = new JopsNode("bone-following-particle-node", killInstantly);
/* 149 */     jopsNode.setLocalScale(scale);
/* 150 */     jopsNode.setCamera(DisplaySystem.getDisplaySystem().getRenderer().getCamera());
/* 151 */     jopsNode.setParticleSystem(particleSystem);
/*     */     
/* 153 */     Md5JointFollowController followController = new Md5JointFollowController((Spatial)jopsNode, this.target.getFollowJoint(jointName));
/* 154 */     followController.setRotationDisplacement(rotation);
/* 155 */     followController.setTranslationDisplacement(offset);
/* 156 */     followController.setSwitchCoordSystem(true);
/*     */     
/* 158 */     followController.setTranslateScale(this.target.getRepresentation().getLocalScale());
/* 159 */     jopsNode.addController((Controller)followController);
/*     */     
/* 161 */     return jopsNode;
/*     */   }
/*     */   
/*     */   public void removeAllBoneParticles() {
/* 165 */     if (this.target.getChildren() != null)
/* 166 */       for (Spatial spatial : new ArrayList(this.target.getChildren())) {
/* 167 */         if (spatial.getName().equals("bone-following-particle-node") && 
/* 168 */           spatial instanceof JopsNode) {
/* 169 */           JopsNode jopsNode = (JopsNode)spatial;
/* 170 */           removeParticle(jopsNode);
/*     */         } 
/*     */       }  
/*     */   }
/*     */   public void removeAllGroundParticles() {
/* 175 */     if (this.target.getChildren() != null)
/* 176 */       for (Spatial spatial : new ArrayList(this.target.getChildren())) {
/* 177 */         if (spatial.getName().equals("ground-particle-node") && 
/* 178 */           spatial instanceof JopsNode) {
/* 179 */           JopsNode jopsNode = (JopsNode)spatial;
/* 180 */           removeParticle(jopsNode);
/*     */         } 
/*     */       }  
/*     */   }
/*     */   public void removeAllParticles() {
/* 185 */     if (this.target.getChildren() != null)
/* 186 */       for (Spatial spatial : new ArrayList(this.target.getChildren())) {
/* 187 */         if (spatial instanceof JopsNode) {
/* 188 */           JopsNode jopsNode = (JopsNode)spatial;
/* 189 */           removeParticle(jopsNode);
/*     */         } 
/*     */       }  
/*     */   }
/*     */   public void killAllParticles() {
/* 194 */     if (this.target.getChildren() != null)
/* 195 */       for (Spatial spatial : new ArrayList(this.target.getChildren())) {
/* 196 */         if (spatial instanceof JopsNode) {
/* 197 */           JopsNode jopsNode = (JopsNode)spatial;
/* 198 */           jopsNode.kill();
/*     */         } 
/*     */       }  
/*     */   }
/*     */   public void removeVisibleParticles() {
/* 203 */     if (this.target.getChildren() != null)
/* 204 */       for (Spatial spatial : new ArrayList(this.target.getChildren())) {
/* 205 */         if (spatial instanceof JopsNode) {
/* 206 */           JopsNode jopsNode = (JopsNode)spatial;
/* 207 */           Collection<Generator> generators = jopsNode.getParticleSystem().getGenerators();
/* 208 */           for (Generator gen : generators)
/* 209 */             gen.getParticles().clear(); 
/*     */         } 
/*     */       }  
/*     */   }
/*     */   
/*     */   public void tint(TintMode tintMode) {
/* 215 */     if (this.tintLock) {
/* 216 */       throw new IllegalStateException("tint is locked");
/*     */     }
/*     */     
/* 219 */     if (tintMode == null) {
/* 220 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 223 */     this.tintMode = tintMode;
/* 224 */     switch (this.tintMode) {
/*     */       case OFF:
/* 226 */         SpatialUtils.setTintingEnabled(this.target.getRepresentation(), this.tintRgba, false);
/* 227 */         SpatialUtils.setTintingAdditiveEnabled(this.target.getRepresentation(), this.tintRgba, false);
/*     */         break;
/*     */       
/*     */       case MODULATE:
/* 231 */         SpatialUtils.setTintingEnabled(this.target.getRepresentation(), this.tintRgba, true);
/*     */         break;
/*     */       
/*     */       case FLASH:
/* 235 */         SpatialUtils.setTintingAdditiveEnabled(this.target.getRepresentation(), this.flashRgba, true);
/*     */         break;
/*     */       case ADDITIVE:
/* 238 */         SpatialUtils.setTintingAdditiveEnabled(this.target.getRepresentation(), this.tintRgba, true);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void scale(float value) {
/* 244 */     Spatial representation = this.target.getRepresentation();
/* 245 */     if (representation != null)
/* 246 */       representation.setLocalScale(value); 
/*     */   }
/*     */   
/*     */   public RepresentationalNode getTarget() {
/* 250 */     return this.target;
/*     */   }
/*     */   
/*     */   public void setTarget(RepresentationalNode target) {
/* 254 */     this.target = target;
/*     */   }
/*     */   
/*     */   public void removeEffect(Spatial spatial) {
/* 258 */     this.target.detachChild(spatial);
/*     */   }
/*     */   
/*     */   public void removeParticle(JopsNode jopsNode) {
/*     */     try {
/* 263 */       jopsNode.endEffect();
/* 264 */     } catch (NullPointerException e) {
/* 265 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public ParticleSurface getParticleSurface() {
/* 270 */     return this.particleSurface;
/*     */   }
/*     */   
/*     */   public boolean isTransparent() {
/* 274 */     return (this.tintMode == TintMode.MODULATE && this.tintRgba.a < 1.0D);
/*     */   }
/*     */   
/*     */   public void lockTint() {
/* 278 */     this.tintLock = true;
/*     */   }
/*     */   
/*     */   public boolean isTintLock() {
/* 282 */     return this.tintLock;
/*     */   }
/*     */   
/*     */   public TintMode getTintMode() {
/* 286 */     return this.tintMode;
/*     */   }
/*     */   
/*     */   public float getFlashTime() {
/* 290 */     return this.flashTime;
/*     */   }
/*     */   
/*     */   public void setFlashTime(float flashTime) {
/* 294 */     this.flashTime = flashTime;
/*     */   }
/*     */   
/*     */   public void resetFlashTime() {
/* 298 */     this.flashTime = 0.1F;
/*     */   }
/*     */   
/*     */   public enum TintMode {
/* 302 */     OFF, MODULATE, ADDITIVE, FLASH;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\Effects.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */