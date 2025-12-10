/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.commons.dfx.AnimationEffectDescription;
/*    */ import com.funcom.commons.dfx.Effect;
/*    */ import com.funcom.commons.dfx.EffectHandler;
/*    */ import com.funcom.commons.jme.md5importer.JointAnimation;
/*    */ import com.funcom.commons.jme.md5importer.ModelNode;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManagerException;
/*    */ 
/*    */ public class AnimationEffectHandler
/*    */   implements EffectHandler
/*    */ {
/*    */   private RepresentationalNode source;
/*    */   private JointAnimation animation;
/*    */   private boolean revertFrameRate = false;
/*    */   private JointAnimation petAnimation;
/*    */   
/*    */   public AnimationEffectHandler(RepresentationalNode source) {
/* 19 */     this.source = source;
/*    */   }
/*    */   
/*    */   public void startEffect(Effect sourceEffect) {
/* 23 */     AnimationEffectDescription animationEffectDescription = (AnimationEffectDescription)sourceEffect.getDescription();
/* 24 */     String resource = animationEffectDescription.getResource();
/* 25 */     ModelNode modelNode = null;
/* 26 */     if (this.source.getRepresentation() instanceof ModelNode) {
/* 27 */       modelNode = (ModelNode)this.source.getRepresentation();
/* 28 */     } else if (this.source.getRepresentation() instanceof ModularNode) {
/* 29 */       modelNode = ((ModularNode)this.source.getRepresentation()).getPlayerModel();
/*    */     } 
/*    */     
/* 32 */     if (modelNode != null)
/* 33 */       if (!modelNode.getAnimationNames().contains(resource)) {
/*    */         JointAnimation jointAnimation;
/*    */         try {
/* 36 */           jointAnimation = (JointAnimation)animationEffectDescription.getResourceFetcher().getJointAnimation(resource);
/*    */         }
/* 38 */         catch (ResourceManagerException e) {
/* 39 */           throw new ResourceManagerException("problem loading animation " + resource + " for " + this.source.getName(), e);
/*    */         } 
/* 41 */         if (animationEffectDescription.isLooped()) {
/* 42 */           jointAnimation.setFrameRate(jointAnimation.getFrameRate() * animationEffectDescription.getPlaySpeed());
/*    */         } else {
/* 44 */           jointAnimation.setFrameRate((float)((jointAnimation.getFrames()).length / (animationEffectDescription.getEndTime() - animationEffectDescription.getStartTime())));
/*    */         } 
/* 46 */         modelNode.addAnimation(jointAnimation);
/* 47 */       } else if (!animationEffectDescription.isLooped() || animationEffectDescription.getPlaySpeed() != 1.0F) {
/* 48 */         this.animation = (JointAnimation)modelNode.getController().getAnimations().get(resource);
/* 49 */         this.revertFrameRate = true;
/* 50 */         if (animationEffectDescription.isLooped()) {
/* 51 */           this.animation.setFrameRate(this.animation.getFrameRate() * animationEffectDescription.getPlaySpeed());
/*    */         } else {
/* 53 */           this.animation.setFrameRate((float)((this.animation.getFrames()).length / (animationEffectDescription.getEndTime() - animationEffectDescription.getStartTime())));
/*    */         } 
/*    */         
/* 56 */         if (this.source.getRepresentation() instanceof ModularNode) {
/* 57 */           modelNode = ((ModularNode)this.source.getRepresentation()).getPetModel();
/* 58 */           if (modelNode != null) {
/* 59 */             this.petAnimation = (JointAnimation)modelNode.getController().getAnimations().get(resource);
/* 60 */             if (this.petAnimation == null) {
/* 61 */               throw new NullPointerException("No animation: " + resource + " exists for pet: " + modelNode.getName());
/*    */             }
/* 63 */             this.revertFrameRate = true;
/* 64 */             if (animationEffectDescription.isLooped()) {
/* 65 */               this.petAnimation.setFrameRate(this.petAnimation.getFrameRate() * animationEffectDescription.getPlaySpeed());
/*    */             } else {
/* 67 */               this.petAnimation.setFrameRate((float)((this.petAnimation.getFrames()).length / (animationEffectDescription.getEndTime() - animationEffectDescription.getStartTime())));
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       }  
/* 72 */     if (this.source.getAnimationPlayer() != null) {
/* 73 */       this.source.playAnimation(resource, false);
/*    */     }
/*    */   }
/*    */   
/*    */   public void endEffect(Effect sourceEffect) {
/* 78 */     if (this.revertFrameRate) {
/* 79 */       this.animation.resetFrameRate();
/* 80 */       if (this.petAnimation != null) {
/* 81 */         this.petAnimation.resetFrameRate();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isFinished() {
/* 87 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\AnimationEffectHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */