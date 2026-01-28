/*     */ package com.funcom.commons.jme.md5importer;
/*     */ 
/*     */ import com.funcom.commons.jme.md5importer.controller.JointController;
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.Joint;
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.Mesh;
/*     */ import com.jme.bounding.BoundingBox;
/*     */ import com.jme.bounding.BoundingVolume;
/*     */ import com.jme.renderer.Camera;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.scene.Controller;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.TriMesh;
/*     */ import com.jme.util.export.InputCapsule;
/*     */ import com.jme.util.export.JMEExporter;
/*     */ import com.jme.util.export.JMEImporter;
/*     */ import com.jme.util.export.OutputCapsule;
/*     */ import com.jme.util.export.Savable;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.atomic.AtomicLong;
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
/*     */ public class ModelNode
/*     */   extends Node
/*     */   implements Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = -2799207065296472869L;
/*     */   private boolean update;
/*     */   private boolean dependent;
/*     */   private Joint[] joints;
/*     */   private Mesh[] meshes;
/*     */   private ArrayList<TriMesh> skin;
/*     */   private JointController controller;
/*     */   private Set<String> animationNames;
/*     */   private List<ModelNode> dependentModels;
/*  53 */   private Set<JointsListener> jointsListeners = new HashSet<JointsListener>();
/*     */ 
/*     */   
/*     */   private Camera lastRenderedCamera;
/*     */ 
/*     */   
/*     */   public ModelNode() {
/*  60 */     this("");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelNode(String name) {
/*  69 */     super(name);
/*  70 */     this.skin = new ArrayList<TriMesh>();
/*  71 */     this.animationNames = new HashSet<String>();
/*  72 */     this.dependentModels = new ArrayList<ModelNode>();
/*  73 */     setNormalsMode(Spatial.NormalsMode.AlwaysNormalize);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize() {
/*  78 */     if (!this.dependent) {
/*  79 */       processJoints();
/*     */     }
/*  81 */     for (TriMesh triMesh : this.skin) {
/*  82 */       detachChild((Spatial)triMesh);
/*     */     }
/*  84 */     this.skin.clear();
/*     */     
/*  86 */     initializeMeshes();
/*     */     
/*  88 */     for (Mesh mesh : this.meshes) {
/*  89 */       this.skin.add(mesh.getTrimesh());
/*     */     }
/*     */   }
/*     */   
/*     */   public void initializeMeshes() {
/*  94 */     for (Mesh mesh : this.meshes) {
/*  95 */       mesh.generateBatch();
/*  96 */       attachChild((Spatial)mesh.getTrimesh());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addJointsListener(JointsListener jointsListener) {
/* 101 */     this.jointsListeners.add(jointsListener);
/*     */   }
/*     */   
/*     */   public void removeJointsListener(JointsListener jointsListener) {
/* 105 */     this.jointsListeners.remove(jointsListener);
/*     */   }
/*     */ 
/*     */   
/*     */   private void processJoints() {
/* 110 */     for (Joint joint : this.joints) {
/* 111 */       joint.processRelative();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateGeometricState(float time, boolean initiator) {
/* 123 */     if (this.update) {
/* 124 */       boolean doSkinning = updateFrustrumIntersection();
/* 125 */       if (doSkinning) {
/* 126 */         if (!this.dependent)
/*     */         {
/* 128 */           processJoints();
/*     */         }
/*     */         
/* 131 */         for (Mesh mesh : this.meshes)
/*     */         {
/* 133 */           mesh.updateBatch();
/*     */         }
/*     */       } 
/*     */       
/* 137 */       this.update = false;
/*     */     } 
/* 139 */     super.updateGeometricState(time, initiator);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNormalsMode(Spatial.NormalsMode mode) {
/* 144 */     super.setNormalsMode(mode);
/*     */   }
/*     */   
/*     */   private boolean updateFrustrumIntersection() {
/* 148 */     if (this.lastRenderedCamera == null) {
/* 149 */       return true;
/*     */     }
/*     */     
/* 152 */     Spatial.CullHint cm = getCullHint();
/*     */     
/* 154 */     if (cm == Spatial.CullHint.Always) {
/* 155 */       setLastFrustumIntersection(Camera.FrustumIntersect.Outside);
/* 156 */       return false;
/* 157 */     }  if (cm == Spatial.CullHint.Never) {
/* 158 */       setLastFrustumIntersection(Camera.FrustumIntersect.Intersects);
/* 159 */       return true;
/*     */     } 
/* 161 */     int state = this.lastRenderedCamera.getPlaneState();
/*     */ 
/*     */     
/* 164 */     this.frustrumIntersects = (this.parent != null) ? this.parent.getLastFrustumIntersection() : Camera.FrustumIntersect.Intersects;
/*     */     
/* 166 */     if (cm == Spatial.CullHint.Dynamic && this.frustrumIntersects == Camera.FrustumIntersect.Intersects)
/*     */     {
/* 168 */       this.frustrumIntersects = this.lastRenderedCamera.contains(this.worldBound);
/*     */     }
/*     */     
/* 171 */     this.lastRenderedCamera.setPlaneState(state);
/* 172 */     return (this.frustrumIntersects != Camera.FrustumIntersect.Outside);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLastFrustumIntersection(Camera.FrustumIntersect intersects) {
/* 178 */     super.setLastFrustumIntersection(intersects);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDraw(Renderer r) {
/* 183 */     this.lastRenderedCamera = r.getCamera();
/* 184 */     super.onDraw(r);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getJointIndex(String jointName) {
/* 194 */     int jointIndex = -1;
/* 195 */     for (int i = 0; i < this.joints.length && jointIndex == -1; i++) {
/* 196 */       if (this.joints[i].getName().equals(jointName)) jointIndex = i; 
/*     */     } 
/* 198 */     return jointIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attachChild(ModelNode node, String jointName) {
/* 208 */     attachChild(node, getJointIndex(jointName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attachChild(ModelNode node, int jointIndex) {
/* 218 */     getRootJoint(node).setNodeParent(jointIndex);
/* 219 */     attachChild((Spatial)node);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attachDependent(ModelNode node) {
/* 230 */     node.dependent = true;
/* 231 */     node.setJoints(this.joints);
/* 232 */     attachChild((Spatial)node);
/*     */     
/* 234 */     if (!this.dependentModels.contains(node)) {
/* 235 */       this.dependentModels.add(node);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void detachDependent(ModelNode node) {
/* 245 */     node.dependent = false;
/* 246 */     detachChild((Spatial)node);
/* 247 */     this.dependentModels.remove(node);
/*     */   }
/*     */ 
/*     */   
/*     */   public void detachAllDependent() {
/* 252 */     for (ModelNode dependentModel : this.dependentModels) {
/* 253 */       dependentModel.dependent = false;
/* 254 */       detachChild((Spatial)dependentModel);
/*     */     } 
/* 256 */     this.dependentModels.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Joint getRootJoint(ModelNode node) {
/* 266 */     for (int i = 0; i < (node.getJoints()).length; i++) {
/* 267 */       if (node.getJoint(i).getParent() < 0) return node.getJoint(i); 
/*     */     } 
/* 269 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void flagUpdate() {
/* 274 */     this.update = true;
/* 275 */     for (int i = 0, dependentModelsSize = this.dependentModels.size(); i < dependentModelsSize; i++) {
/* 276 */       ((ModelNode)this.dependentModels.get(i)).flagUpdate();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJoints(Joint[] joints) {
/* 286 */     this.joints = joints;
/* 287 */     for (JointsListener jointsListener : this.jointsListeners) {
/* 288 */       jointsListener.jointsUpdated();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMeshes(Mesh[] meshes) {
/* 298 */     this.meshes = meshes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Joint[] getJoints() {
/* 307 */     return this.joints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Joint getJoint(int index) {
/* 317 */     return this.joints[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Joint getJoint(String jointName) {
/* 328 */     int index = getJointIndex(jointName);
/* 329 */     if (index < 0 || index >= this.joints.length) {
/* 330 */       throw new IllegalArgumentException("Unknown joint name " + jointName);
/*     */     }
/* 332 */     return this.joints[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Mesh getMesh(int index) {
/* 342 */     return this.meshes[index];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getClassTag() {
/* 348 */     return ModelNode.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(JMEImporter im) throws IOException {
/* 353 */     super.read(im);
/* 354 */     InputCapsule ic = im.getCapsule((Savable)this);
/* 355 */     this.dependent = ic.readBoolean("Dependent", false);
/*     */     
/* 357 */     Savable[] temp = ic.readSavableArray("Joints", null);
/* 358 */     this.joints = new Joint[temp.length]; int i;
/* 359 */     for (i = 0; i < temp.length; i++) {
/* 360 */       this.joints[i] = (Joint)temp[i];
/*     */     }
/* 362 */     temp = ic.readSavableArray("Meshes", null);
/* 363 */     this.meshes = new Mesh[temp.length];
/* 364 */     for (i = 0; i < temp.length; i++) {
/* 365 */       this.meshes[i] = (Mesh)temp[i];
/*     */     }
/* 367 */     this.skin = ic.readSavableArrayList("Skin", null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(JMEExporter ex) throws IOException {
/* 372 */     super.write(ex);
/* 373 */     OutputCapsule oc = ex.getCapsule((Savable)this);
/* 374 */     oc.write(this.dependent, "Dependent", false);
/* 375 */     oc.write((Savable[])this.joints, "Joints", null);
/* 376 */     oc.write((Savable[])this.meshes, "Meshes", null);
/* 377 */     oc.writeSavableArrayList(this.skin, "Skin", null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAnimation(JointAnimation animation) {
/* 386 */     if (this.controller == null) {
/* 387 */       this.controller = new JointController(this.joints);
/* 388 */       this.controller.setRepeatType(1);
/* 389 */       addController((Controller)this.controller);
/*     */     } 
/* 391 */     this.controller.addAnimation(animation);
/* 392 */     this.animationNames.add(animation.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAnimation(String name, JointAnimation animation) {
/* 402 */     if (this.controller == null) {
/* 403 */       this.controller = new JointController(this.joints);
/* 404 */       this.controller.setRepeatType(1);
/* 405 */       addController((Controller)this.controller);
/*     */     } 
/* 407 */     this.controller.addAnimation(name, animation);
/* 408 */     this.animationNames.add(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAllAnimations() {
/* 413 */     this.controller.removeAllAnimations();
/*     */   }
/*     */ 
/*     */   
/*     */   public Node getNode() {
/* 418 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playAnimation(String name) {
/* 427 */     if (this.animationNames.contains(name)) {
/* 428 */       this.controller.setActiveAnimation(name);
/*     */     }
/*     */   }
/*     */   
/*     */   public Set<String> getAnimationNames() {
/* 433 */     return this.animationNames;
/*     */   }
/*     */   
/*     */   public Mesh[] getMeshes() {
/* 437 */     return this.meshes;
/*     */   }
/*     */   
/*     */   public ModelNode clone() {
/* 441 */     ModelNode clone = new ModelNode(this.name);
/* 442 */     clone.setNormalsMode(getNormalsMode());
/* 443 */     Joint[] newJoints = new Joint[this.joints.length];
/* 444 */     for (int i = 0; i < this.joints.length; i++) {
/* 445 */       newJoints[i] = this.joints[i].clone(clone);
/*     */     }
/* 447 */     clone.setJoints(newJoints);
/*     */     
/* 449 */     Mesh[] newMeshes = new Mesh[this.meshes.length];
/* 450 */     for (int j = 0; j < this.meshes.length; j++) {
/* 451 */       newMeshes[j] = this.meshes[j].simpleClone(clone);
/*     */     }
/* 453 */     clone.setMeshes(newMeshes);
/*     */     
/* 455 */     for (Mesh mesh : clone.meshes) {
/*     */       
/* 457 */       clone.skin.add(mesh.getTrimesh());
/* 458 */       clone.attachChild((Spatial)mesh.getTrimesh());
/*     */     } 
/*     */     
/* 461 */     for (ModelNode node : this.dependentModels) {
/* 462 */       clone.attachDependent(node.clone());
/*     */     }
/*     */     
/* 465 */     clone.setRenderQueueMode(getRenderQueueMode());
/* 466 */     clone.setCullHint(getLocalCullHint());
/* 467 */     return clone;
/*     */   }
/*     */   
/* 470 */   static AtomicLong usedMem = new AtomicLong();
/* 471 */   static AtomicLong usedTime = new AtomicLong();
/* 472 */   static AtomicLong count = new AtomicLong();
/*     */   
/*     */   public ModelNode cloneOLD() {
/* 475 */     ModelNode clone = new ModelNode(this.name);
/* 476 */     clone.setNormalsMode(getNormalsMode());
/* 477 */     Joint[] newJoints = new Joint[this.joints.length];
/* 478 */     for (int i = 0; i < this.joints.length; i++) {
/* 479 */       newJoints[i] = this.joints[i].clone(clone);
/*     */     }
/* 481 */     clone.setJoints(newJoints);
/*     */     
/* 483 */     Mesh[] newMeshes = new Mesh[this.meshes.length];
/* 484 */     for (int j = 0; j < this.meshes.length; j++) {
/* 485 */       newMeshes[j] = this.meshes[j].clone(clone);
/*     */     }
/* 487 */     clone.setMeshes(newMeshes);
/*     */     
/* 489 */     clone.initialize();
/*     */     
/* 491 */     for (ModelNode node : this.dependentModels) {
/* 492 */       clone.attachDependent(node.clone());
/*     */     }
/*     */     
/* 495 */     clone.setRenderQueueMode(getRenderQueueMode());
/* 496 */     clone.setCullHint(getLocalCullHint());
/* 497 */     return clone;
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
/*     */ 
/*     */   
/*     */   private long getMemUsed() {
/* 544 */     return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
/*     */   }
/*     */   public BoundingVolume getModelBounds(BoundingVolume bounds) {
/*     */     BoundingBox boundingBox = null;
/* 548 */     if (bounds == null) {
/* 549 */       boundingBox = new BoundingBox();
/*     */     }
/* 551 */     for (TriMesh mesh : this.skin) {
/* 552 */       boundingBox.mergeLocal(mesh.getModelBound());
/*     */     }
/* 554 */     for (ModelNode node : this.dependentModels) {
/* 555 */       node.getModelBounds((BoundingVolume)boundingBox);
/*     */     }
/*     */     
/* 558 */     return boundingBox.transform(getLocalRotation(), getLocalTranslation(), getLocalScale());
/*     */   }
/*     */ 
/*     */   
/*     */   public JointController getController() {
/* 563 */     return this.controller;
/*     */   }
/*     */   
/*     */   public ModelNode cloneStatic() {
/* 567 */     ModelNode clone = new ModelNode(this.name);
/* 568 */     clone.setNormalsMode(getNormalsMode());
/* 569 */     Joint[] newJoints = new Joint[this.joints.length];
/* 570 */     for (int i = 0; i < this.joints.length; i++) {
/* 571 */       newJoints[i] = this.joints[i].cloneStatic(clone);
/*     */     }
/* 573 */     clone.setJoints(newJoints);
/*     */     
/* 575 */     Mesh[] newMeshes = new Mesh[this.meshes.length];
/* 576 */     for (int j = 0; j < this.meshes.length; j++) {
/* 577 */       newMeshes[j] = this.meshes[j].cloneStatic(clone);
/*     */     }
/* 579 */     clone.setMeshes(newMeshes);
/*     */ 
/*     */     
/* 582 */     for (Mesh mesh : clone.meshes) {
/* 583 */       clone.skin.add(mesh.getTrimesh());
/* 584 */       clone.attachChild((Spatial)mesh.getTrimesh());
/*     */     } 
/*     */     
/* 587 */     for (ModelNode node : this.dependentModels) {
/* 588 */       clone.attachDependent(node.cloneStatic());
/*     */     }
/*     */     
/* 591 */     clone.setRenderQueueMode(getRenderQueueMode());
/* 592 */     clone.setCullHint(getLocalCullHint());
/* 593 */     return clone;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\ModelNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */