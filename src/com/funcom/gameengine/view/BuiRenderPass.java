/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.commons.PerformanceGraphNode;
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jme.renderer.pass.RenderPass;
/*    */ import com.jme.scene.Spatial;
/*    */ import com.jme.util.Debug;
/*    */ import com.jme.util.stat.StatCollector;
/*    */ import com.jmex.bui.BRootNode;
/*    */ 
/*    */ public class BuiRenderPass
/*    */   extends RenderPass {
/*    */   private BRootNode bRootNode;
/*    */   
/*    */   public BuiRenderPass(BRootNode bRootNode) {
/* 16 */     if (bRootNode == null)
/* 17 */       throw new IllegalArgumentException("bRootNode = null"); 
/* 18 */     this.bRootNode = bRootNode;
/* 19 */     bRootNode.setLightCombineMode(Spatial.LightCombineMode.Off);
/*    */   }
/*    */   
/*    */   protected void doUpdate(float v) {
/* 23 */     startTiming();
/* 24 */     this.bRootNode.updateGeometricState(v, true);
/* 25 */     endTiming();
/*    */   }
/*    */ 
/*    */   
/*    */   private static void startTiming() {
/* 30 */     if (Debug.stats)
/* 31 */       StatCollector.startStat(PerformanceGraphNode.TrackingStat.BUI_UPDATE.statType); 
/*    */   }
/*    */   
/*    */   private static void endTiming() {
/* 35 */     if (Debug.stats)
/* 36 */       StatCollector.endStat(PerformanceGraphNode.TrackingStat.BUI_UPDATE.statType); 
/*    */   }
/*    */   
/*    */   public void doRender(Renderer renderer) {
/* 40 */     renderer.draw((Spatial)this.bRootNode);
/* 41 */     renderer.renderQueue();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\BuiRenderPass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */