/*     */ package com.funcom.tcg.client.view.modular;
/*     */ import com.funcom.commons.jme.md5importer.JointAnimation;
/*     */ import com.funcom.commons.jme.md5importer.ModelNode;
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.Joint;
/*     */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.view.AnimationMapper;
/*     */ import com.funcom.gameengine.view.AnimationPlayer;
/*     */ import com.funcom.gameengine.view.FollowJoint;
/*     */ import com.funcom.gameengine.view.ModularNode;
/*     */ import com.funcom.tcg.client.model.rpg.VisualRegistry;
/*     */ import com.jme.bounding.BoundingVolume;
/*     */ import com.jme.math.Quaternion;
/*     */ import com.jme.math.TransformMatrix;
/*     */ import com.jme.scene.Spatial;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class ClientDescribedModularNode extends ModularNode {
/*     */   private final VisualRegistry visualRegistry;
/*     */   private ModelNode petNode;
/*     */   
/*     */   public ClientDescribedModularNode(ModularDescription modularDescription, AnimationMapper animationMapper, Quaternion rotation, float scale, VisualRegistry visualRegistry, ResourceManager resourceManager) {
/*  23 */     super(modularDescription, animationMapper, rotation, scale, resourceManager);
/*  24 */     this.visualRegistry = visualRegistry;
/*  25 */     animationMapper.setAnimationPlayer((AnimationPlayer)new ClientModularAnimationPlayer());
/*  26 */     setCullHint(Spatial.CullHint.Dynamic);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getPathFromMeshData(String partName) {
/*  31 */     String meshPath = null;
/*  32 */     VisualRegistry.MeshData meshData = this.visualRegistry.getDefaultMeshPathForClassID(partName);
/*  33 */     if (meshData != null)
/*  34 */       meshPath = meshData.getMeshPath(); 
/*  35 */     return meshPath;
/*     */   }
/*     */   
/*     */   public void activePetChanged() {
/*  39 */     ModularDescription.Part petModel = this.modularDescription.getPetModel();
/*  40 */     if (petModel != null) {
/*  41 */       if (this.petNode != null)
/*  42 */         detachChild((Spatial)this.petNode); 
/*  43 */       this.petNode = loadPart(petModel.getMeshPath(), petModel);
/*  44 */       attachChild((Spatial)this.petNode);
/*  45 */       this.petNode.updateRenderState();
/*     */ 
/*     */       
/*  48 */       reloadAnimations();
/*     */       
/*  50 */       this.playerModel.updateRenderState();
/*     */     } 
/*     */   }
/*     */   public void reloadAnimations() {
/*  54 */     Set<ModularDescription.Animation> animations = this.modularDescription.getAnimations();
/*  55 */     for (ModularDescription.Animation animation : animations) {
/*  56 */       if (animation.getPetAnimationPath() != null) {
/*  57 */         JointAnimation petAnimation = getAnimation(animation, animation.getPetAnimationPath());
/*  58 */         petAnimation.setInitialFrameRate(animation.getPetFrameRate());
/*  59 */         if (this.petNode != null) {
/*  60 */           this.petNode.addAnimation(petAnimation);
/*     */         }
/*     */       } 
/*     */     } 
/*  64 */     super.reloadAnimations();
/*     */   }
/*     */ 
/*     */   
/*     */   public void partChanged(String partName) {
/*  69 */     super.partChanged(partName);
/*  70 */     activePetChanged();
/*     */   }
/*     */   
/*     */   public FollowJoint getFollowJoint(String jointName) {
/*  74 */     if (this.petNode != null) {
/*  75 */       int index = this.petNode.getJointIndex(jointName);
/*  76 */       TransformMatrix transformMatrix = new TransformMatrix();
/*  77 */       if (index >= 0) {
/*  78 */         Joint joint = this.petNode.getJoint(jointName);
/*  79 */         transformMatrix = joint.getTransform();
/*     */       } 
/*  81 */       return new FollowJoint(transformMatrix, getLocalRotation());
/*     */     } 
/*  83 */     return super.getFollowJoint(jointName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected BoundingVolume getUntransformedModelBounds() {
/*  89 */     BoundingVolume ret = super.getUntransformedModelBounds();
/*  90 */     if (this.petNode != null) {
/*  91 */       this.petNode.getModelBounds(ret);
/*     */     }
/*  93 */     return ret;
/*     */   }
/*     */   
/*     */   public void reloadAll() {
/*  97 */     reloadCharacter();
/*  98 */     activePetChanged();
/*  99 */     updateGeometricState(0.0F, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelNode getPetModel() {
/* 104 */     return this.petNode;
/*     */   }
/*     */   private class ClientModularAnimationPlayer extends ModularNode.ModularAnimationPlayer { private ClientModularAnimationPlayer() {
/* 107 */       super();
/*     */     }
/*     */     
/*     */     protected void runAnimation(String animationName) {
/* 111 */       super.runAnimation(animationName);
/* 112 */       if (ClientDescribedModularNode.this.petNode != null && 
/* 113 */         ClientDescribedModularNode.this.petNode.getAnimationNames().contains(animationName))
/* 114 */         ClientDescribedModularNode.this.petNode.getController().setActiveAnimation(animationName, 0.05F); 
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\view\modular\ClientDescribedModularNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */