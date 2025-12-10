/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.jme.scene.state.MaterialState;
/*    */ import com.jme.system.DisplaySystem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TransparentMaterialState
/*    */ {
/*    */   public static MaterialState get(float alpha) {
/* 13 */     MaterialState materialState = DisplaySystem.getDisplaySystem().getRenderer().createMaterialState();
/* 14 */     return modify(materialState, alpha);
/*    */   }
/*    */   
/*    */   public static MaterialState modify(MaterialState materialState, float alpha) {
/* 18 */     (materialState.getAmbient()).a = alpha;
/* 19 */     (materialState.getEmissive()).a = alpha;
/* 20 */     (materialState.getDiffuse()).a = alpha;
/* 21 */     (materialState.getSpecular()).a = alpha;
/* 22 */     return materialState;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\TransparentMaterialState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */