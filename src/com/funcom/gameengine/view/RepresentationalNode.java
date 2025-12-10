/*     */ package com.funcom.gameengine.view;
/*     */ 
/*     */ import com.funcom.audio.Sound;
/*     */ import com.funcom.commons.dfx.DFXCollection;
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*     */ import com.funcom.commons.dfx.NoSuchDFXException;
/*     */ import com.funcom.commons.jme.md5importer.ModelNode;
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.Joint;
/*     */ import com.funcom.gameengine.jme.DrawPassState;
/*     */ import com.funcom.gameengine.jme.DrawPassType;
/*     */ import com.funcom.gameengine.jme.PassDrawable;
/*     */ import com.funcom.gameengine.model.ParticleSurface;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.jme.math.Quaternion;
/*     */ import com.jme.math.TransformMatrix;
/*     */ import com.jme.renderer.Camera;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.scene.Controller;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.scene.state.ZBufferState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ public class RepresentationalNode
/*     */   extends Node
/*     */   implements AnimationMapper, PassDrawable, ContentIndentifiable
/*     */ {
/*     */   private static ZBufferState zBufferReadOnly;
/*     */   private Effects effects;
/*     */   private Spatial representation;
/*     */   private boolean runsDfxs = true;
/*     */   private DFXCollection dfxCollection;
/*     */   private float scale;
/*     */   
/*     */   public RepresentationalNode(String s, DireEffectDescriptionFactory effectDescriptionFactory, int contentType) {
/*  44 */     super(s);
/*  45 */     this.dfxCollection = new CheckedDFXCollection();
/*  46 */     this.scale = 1.0F;
/*  47 */     this.sounds = Collections.synchronizedList(new ArrayList<Sound>());
/*     */     
/*  49 */     initializeDFX();
/*  50 */     setAnimationPlayer(new SimpleAnimationPlayer(this));
/*  51 */     addController(new RepresentationUpdateController(this));
/*  52 */     this.effectDescriptionFactory = effectDescriptionFactory;
/*  53 */     this.contentType = contentType;
/*     */   }
/*     */   protected DireEffectDescriptionFactory effectDescriptionFactory; protected AnimationPlayer animationPlayer; protected String playingAnimationName; private boolean transparent; protected List<Sound> sounds; protected int contentType;
/*     */   
/*     */   protected void setParent(Node parent) {
/*  58 */     super.setParent(parent);
/*     */     
/*  60 */     if (parent == null)
/*     */     {
/*  62 */       killDfxAll();
/*     */     }
/*     */   }
/*     */   
/*     */   private void initializeDFX() {
/*  67 */     this.effects = new Effects(this);
/*     */   }
/*     */   
/*     */   public Effects getEffects() {
/*  71 */     return this.effects;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScale(float scale) {
/*  80 */     this.scale = scale;
/*  81 */     if (this.effects != null)
/*  82 */       this.effects.scale(scale); 
/*  83 */     updateWorldScale();
/*     */   }
/*     */   
/*     */   public void initializeEffects(ParticleSurface ps) {
/*  87 */     this.effects.setParticleSurface(ps);
/*     */   }
/*     */   
/*     */   public void attachRepresentation(Spatial spatial) {
/*  91 */     attachChild(spatial);
/*  92 */     this.representation = spatial;
/*  93 */     resetScale();
/*     */   }
/*     */   
/*     */   public Spatial getRepresentation() {
/*  97 */     return this.representation;
/*     */   }
/*     */   
/*     */   public void resetScale() {
/* 101 */     setScale(getScale());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getScale() {
/* 110 */     return this.scale;
/*     */   }
/*     */ 
/*     */   
/*     */   public DireEffectDescriptionFactory getEffectDescriptionFactory() {
/* 115 */     return this.effectDescriptionFactory;
/*     */   }
/*     */   
/*     */   public void addDfx(DireEffect effect) {
/* 119 */     this.dfxCollection.addDFX(effect);
/*     */   }
/*     */   
/*     */   public boolean hasDfxRunning() {
/* 123 */     return !this.dfxCollection.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean hasDfxRunning(String id) {
/* 127 */     return this.dfxCollection.runningDfx(id);
/*     */   }
/*     */   
/*     */   public boolean hasNonStandardAnimationRunning() {
/* 131 */     return this.dfxCollection.hasNonStandardAnimationRunning();
/*     */   }
/*     */   
/*     */   public void killDfx(String id) {
/* 135 */     this.dfxCollection.killDfx(id);
/*     */   }
/*     */   
/*     */   public void killDfxAll() {
/* 139 */     this.dfxCollection.killAll();
/*     */     
/* 141 */     getEffects().killAllParticles();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playDfx(String stateName) {
/*     */     try {
/* 149 */       DireEffectDescription stateDFXDescription = getEffectDescriptionFactory().getDireEffectDescription(stateName, false);
/* 150 */       if (!stateDFXDescription.isEmpty()) {
/* 151 */         addDfx(stateDFXDescription.createInstance(this, UsageParams.EMPTY_PARAMS));
/*     */       }
/* 153 */     } catch (NoSuchDFXException e) {
/* 154 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void playDfx(Element dfx) {
/* 159 */     DireEffectDescription dfxDesc = getEffectDescriptionFactory().getDireEffectDescription(dfx, "subDfx", true);
/* 160 */     if (!dfxDesc.isEmpty()) {
/* 161 */       addDfx(dfxDesc.createInstance(this, UsageParams.EMPTY_PARAMS));
/*     */     }
/*     */   }
/*     */   
/*     */   public void playAnimation(String name, boolean override) {
/* 166 */     if (this.animationPlayer == null) {
/* 167 */       throw new IllegalStateException("No animation player defined! Animation play requested: " + name);
/*     */     }
/* 169 */     this.playingAnimationName = name;
/*     */     
/* 171 */     this.animationPlayer.play(name, override);
/*     */   }
/*     */ 
/*     */   
/*     */   public AnimationPlayer getAnimationPlayer() {
/* 176 */     return this.animationPlayer;
/*     */   }
/*     */   
/*     */   public void setAnimationPlayer(AnimationPlayer animationPlayer) {
/* 180 */     this.animationPlayer = animationPlayer;
/*     */   }
/*     */   
/*     */   public String getPlayingAnimationName() {
/* 184 */     return this.playingAnimationName;
/*     */   }
/*     */   
/*     */   public FollowJoint getFollowJoint(String jointName) {
/* 188 */     if (this.representation instanceof ModelNode) {
/* 189 */       ModelNode modelNode = (ModelNode)this.representation;
/* 190 */       int index = modelNode.getJointIndex(jointName);
/* 191 */       TransformMatrix transformMatrix = new TransformMatrix();
/* 192 */       if (index >= 0) {
/* 193 */         Joint joint = modelNode.getJoint(jointName);
/* 194 */         transformMatrix = joint.getTransform();
/*     */       } 
/* 196 */       return new FollowJoint(transformMatrix, new Quaternion());
/* 197 */     }  if (this.representation instanceof ModularNode) {
/* 198 */       ModularNode targetNode = (ModularNode)this.representation;
/* 199 */       return targetNode.getFollowJoint(jointName);
/*     */     } 
/* 201 */     return null;
/*     */   }
/*     */   
/*     */   public Node getAncestor() {
/* 205 */     Node node = this;
/* 206 */     while (node.getParent() != null)
/* 207 */       node = node.getParent(); 
/* 208 */     return node;
/*     */   }
/*     */   
/*     */   public int getContentType() {
/* 212 */     return this.contentType;
/*     */   }
/*     */   
/*     */   private static class RepresentationUpdateController extends Controller {
/*     */     private RepresentationalNode node;
/*     */     
/*     */     private RepresentationUpdateController(RepresentationalNode node) {
/* 219 */       this.node = node;
/*     */     }
/*     */     
/*     */     public void update(float time) {
/* 223 */       this.node.update(time);
/*     */     }
/*     */   }
/*     */   
/*     */   private void update(float time) {
/* 228 */     if (this.runsDfxs)
/* 229 */       this.dfxCollection.update(time); 
/* 230 */     updateSounds();
/*     */     
/* 232 */     boolean currentlyTransparent = getEffects().isTransparent();
/* 233 */     if (this.transparent != currentlyTransparent) {
/* 234 */       if (currentlyTransparent) {
/* 235 */         if (zBufferReadOnly == null) {
/* 236 */           zBufferReadOnly = DisplaySystem.getDisplaySystem().getRenderer().createZBufferState();
/* 237 */           zBufferReadOnly.setEnabled(true);
/* 238 */           zBufferReadOnly.setWritable(false);
/*     */         } 
/* 240 */         setRenderState((RenderState)zBufferReadOnly);
/*     */       } else {
/* 242 */         clearRenderState(7);
/*     */       } 
/* 244 */       updateRenderState();
/*     */       
/* 246 */       this.transparent = currentlyTransparent;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setRunsDfxs(boolean runsDfxs) {
/* 251 */     this.runsDfxs = runsDfxs;
/*     */   }
/*     */   
/*     */   protected void updateSounds() {
/* 255 */     synchronized (this.sounds) {
/* 256 */       Iterator<Sound> iterator = this.sounds.iterator();
/* 257 */       while (iterator.hasNext()) {
/* 258 */         Sound sound = iterator.next();
/* 259 */         if (sound.isFinished()) {
/* 260 */           iterator.remove();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void registerSound(Sound sound) {
/* 267 */     this.sounds.add(sound);
/*     */   }
/*     */   
/*     */   protected void drawChild(Renderer r, DrawPassState drawPassState) {
/* 271 */     if (this.children == null) {
/*     */       return;
/*     */     }
/*     */     
/* 275 */     switch (drawPassState.getType()) {
/*     */       case TRANSPARENT_CONTENT:
/* 277 */         for (i = 0, cSize = this.children.size(); i < cSize; i++) {
/* 278 */           Spatial child = this.children.get(i);
/* 279 */           if (child instanceof com.turborilla.jops.jme.JopsNode) {
/* 280 */             child.onDraw(r);
/*     */           }
/*     */         } 
/*     */         return;
/*     */     } 
/*     */ 
/*     */     
/* 287 */     for (int i = 0, cSize = this.children.size(); i < cSize; i++) {
/* 288 */       Spatial child = this.children.get(i);
/* 289 */       if (!(child instanceof com.turborilla.jops.jme.JopsNode)) {
/* 290 */         child.onDraw(r);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDrawByState(Renderer r, DrawPassState drawPassState) {
/* 299 */     Spatial.CullHint cm = getCullHint();
/* 300 */     if (cm == Spatial.CullHint.Always) {
/* 301 */       setLastFrustumIntersection(Camera.FrustumIntersect.Outside); return;
/*     */     } 
/* 303 */     if (cm == Spatial.CullHint.Never) {
/* 304 */       setLastFrustumIntersection(Camera.FrustumIntersect.Intersects);
/* 305 */       drawChild(r, drawPassState);
/*     */       
/*     */       return;
/*     */     } 
/* 309 */     Camera camera = r.getCamera();
/* 310 */     int state = camera.getPlaneState();
/*     */ 
/*     */     
/* 313 */     this.frustrumIntersects = (this.parent != null) ? this.parent.getLastFrustumIntersection() : Camera.FrustumIntersect.Intersects;
/*     */ 
/*     */     
/* 316 */     if (cm == Spatial.CullHint.Dynamic && this.frustrumIntersects == Camera.FrustumIntersect.Intersects)
/*     */     {
/* 318 */       this.frustrumIntersects = camera.contains(this.worldBound);
/*     */     }
/*     */     
/* 321 */     if (this.frustrumIntersects != Camera.FrustumIntersect.Outside) {
/* 322 */       drawChild(r, drawPassState);
/*     */     }
/* 324 */     camera.setPlaneState(state);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\RepresentationalNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */