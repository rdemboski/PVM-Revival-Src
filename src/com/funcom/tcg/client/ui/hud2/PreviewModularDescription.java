/*    */ package com.funcom.tcg.client.ui.hud2;
/*    */ 
/*    */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*    */ import com.funcom.tcg.client.model.rpg.VisualRegistry;
/*    */ import com.funcom.tcg.client.view.modular.PlayerModularDescription;
/*    */ import com.funcom.tcg.client.view.modular.part.DefinedAnimation;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class PreviewModularDescription extends PlayerModularDescription {
/*    */   private boolean characterMounted;
/*    */   
/*    */   public PreviewModularDescription(ClientPlayer clientPlayer, VisualRegistry visualRegistry) {
/* 16 */     super(clientPlayer, visualRegistry);
/* 17 */     this.characterMounted = clientPlayer.isCharacterMounted();
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isCharacterMountedWithPet() {
/* 22 */     return (this.clientPlayer.getActivePet() != null && this.characterMounted);
/*    */   }
/*    */   
/*    */   public void setCharacterMounted(boolean characterMounted) {
/* 26 */     this.characterMounted = characterMounted;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<ModularDescription.Animation> getAnimations() {
/* 32 */     Set<ModularDescription.Animation> animations = new HashSet<ModularDescription.Animation>();
/* 33 */     Map<String, VisualRegistry.AnimationData> playerAnimations = this.visualRegistry.getDefaultAnimations();
/* 34 */     for (String animationName : playerAnimations.keySet()) {
/* 35 */       animations.add(new DefinedAnimation(animationName, ((VisualRegistry.AnimationData)playerAnimations.get(animationName)).getAnimation(this.clientPlayer.getPlayerDescription().getGender())));
/*    */     }
/* 37 */     return animations;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\PreviewModularDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */