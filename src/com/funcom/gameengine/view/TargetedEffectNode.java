/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*    */ import com.funcom.gameengine.model.props.Prop;
/*    */ 
/*    */ public class TargetedEffectNode
/*    */   extends PropNode {
/*    */   public TargetedEffectNode(Prop prop, int contentType, String resourceName, DireEffectDescriptionFactory effectDescriptionFactory, int id) {
/*  9 */     super(prop, contentType, resourceName, effectDescriptionFactory);
/* 10 */     this.id = id;
/*    */   }
/*    */   private int id;
/*    */   public int getId() {
/* 14 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\TargetedEffectNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */