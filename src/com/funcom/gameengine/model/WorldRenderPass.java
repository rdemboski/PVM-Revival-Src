/*    */ package com.funcom.gameengine.model;
/*    */ 
/*    */ import com.funcom.commons.PerformanceGraphNode;
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jme.renderer.pass.Pass;
/*    */ import com.jme.util.Debug;
/*    */ import com.jme.util.stat.StatCollector;
/*    */ 
/*    */ public class WorldRenderPass
/*    */   extends Pass {
/*    */   private SpatializedWorld world;
/*    */   
/*    */   public WorldRenderPass(SpatializedWorld world) {
/* 14 */     if (world == null)
/* 15 */       throw new IllegalArgumentException("world = null"); 
/* 16 */     this.world = world;
/*    */   }
/*    */   
/*    */   protected void doUpdate(float v) {
/* 20 */     startTiming();
/* 21 */     this.world.updateGeometricState(v, true);
/* 22 */     endTiming();
/*    */   }
/*    */   
/*    */   public void doRender(Renderer renderer) {
/* 26 */     this.world.render(renderer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static void startTiming() {
/* 32 */     if (Debug.stats)
/* 33 */       StatCollector.startStat(PerformanceGraphNode.TrackingStat.GEOMETRIC_UPDATE.statType); 
/*    */   }
/*    */   
/*    */   private static void endTiming() {
/* 37 */     if (Debug.stats)
/* 38 */       StatCollector.endStat(PerformanceGraphNode.TrackingStat.GEOMETRIC_UPDATE.statType); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\WorldRenderPass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */