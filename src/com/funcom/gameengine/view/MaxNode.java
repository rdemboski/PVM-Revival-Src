/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.jme.scene.Node;
/*    */ import com.jme.scene.Spatial;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class MaxNode
/*    */   extends Node
/*    */ {
/*    */   public MaxNode() {
/* 15 */     super("MAX node: null");
/*    */   }
/*    */   
/*    */   public int attachChild(Spatial child) {
/* 19 */     setName("MAX node: " + child.getName());
/* 20 */     return super.attachChild(child);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\MaxNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */