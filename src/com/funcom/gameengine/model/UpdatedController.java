/*    */ package com.funcom.gameengine.model;
/*    */ 
/*    */ import com.funcom.gameengine.Updated;
/*    */ import com.jme.scene.Controller;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UpdatedController
/*    */   extends Controller
/*    */ {
/*    */   private Updated updated;
/*    */   
/*    */   public UpdatedController(Updated updated) {
/* 15 */     this.updated = updated;
/*    */   }
/*    */   
/*    */   public void update(float v) {
/* 19 */     this.updated.update(v);
/*    */   }
/*    */   
/*    */   public Updated getUpdated() {
/* 23 */     return this.updated;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\UpdatedController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */