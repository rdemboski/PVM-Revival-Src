/*    */ package com.funcom.tcg.client.view.modular;
/*    */ 
/*    */ import com.funcom.commons.jme.md5importer.JointAnimation;
/*    */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.gameengine.view.AnimationMapper;
/*    */ import com.funcom.gameengine.view.ModularNode;
/*    */ import com.jme.math.Quaternion;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PreviewPetModularNode
/*    */   extends ModularNode
/*    */ {
/*    */   public PreviewPetModularNode(ModularDescription modularDescription, AnimationMapper animationMapper, Quaternion rotation, float scale, ResourceManager resourceManager) {
/* 18 */     super(modularDescription, animationMapper, rotation, scale, resourceManager);
/*    */   }
/*    */ 
/*    */   
/*    */   public void reloadAnimations() {
/* 23 */     Set<ModularDescription.Animation> animations = this.modularDescription.getAnimations();
/* 24 */     for (ModularDescription.Animation animation : animations) {
/* 25 */       if (animation.getPetAnimationPath() != null) {
/* 26 */         JointAnimation petAnimation = getAnimation(animation, animation.getPetAnimationPath());
/* 27 */         petAnimation.setInitialFrameRate(animation.getPetFrameRate());
/* 28 */         this.animations.put(petAnimation.getName(), petAnimation);
/* 29 */         this.playerModel.addAnimation(petAnimation);
/*    */       } 
/*    */     } 
/*    */     
/* 33 */     this.animationMapper.playAnimation("idle", true);
/* 34 */     updateGeometricState(0.0F, true);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\view\modular\PreviewPetModularNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */