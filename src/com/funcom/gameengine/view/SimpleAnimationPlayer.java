/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.commons.jme.md5importer.ModelNode;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ public class SimpleAnimationPlayer
/*    */   implements AnimationPlayer
/*    */ {
/*    */   private RepresentationalNode representationalNode;
/*    */   
/*    */   public SimpleAnimationPlayer(RepresentationalNode representationalNode) {
/* 13 */     this.representationalNode = representationalNode;
/*    */   }
/*    */ 
/*    */   
/*    */   public void play(String animationName, boolean override) {
/* 18 */     if (this.representationalNode.getRepresentation() instanceof ModelNode) {
/* 19 */       ((ModelNode)this.representationalNode.getRepresentation()).getController().setActiveAnimation(animationName);
/*    */     }
/*    */   }
/*    */   
/*    */   public Set<String> getAnimationNames() {
/* 24 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\SimpleAnimationPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */