/*    */ package com.funcom.commons.jme;
/*    */ 
/*    */ import com.jme.renderer.pass.RenderPass;
/*    */ import com.jme.scene.Spatial;
/*    */ 
/*    */ public class UpdateGeometricStatePass
/*    */   extends RenderPass {
/*    */   protected void doUpdate(float v) {
/*  9 */     for (Spatial spatial : this.spatials)
/* 10 */       spatial.updateGeometricState(v, true); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\UpdateGeometricStatePass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */