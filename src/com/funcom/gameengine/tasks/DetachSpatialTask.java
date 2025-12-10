/*    */ package com.funcom.gameengine.tasks;
/*    */ 
/*    */ import com.jme.scene.Spatial;
/*    */ import java.util.concurrent.Callable;
/*    */ 
/*    */ public class DetachSpatialTask
/*    */   implements Callable
/*    */ {
/*    */   private Spatial spatial;
/*    */   
/*    */   public DetachSpatialTask(Spatial spatial) {
/* 12 */     this.spatial = spatial;
/*    */   }
/*    */   
/*    */   public Object call() throws Exception {
/* 16 */     if (this.spatial.getParent() != null) {
/* 17 */       this.spatial.getParent().detachChild(this.spatial);
/*    */     }
/* 19 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\tasks\DetachSpatialTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */