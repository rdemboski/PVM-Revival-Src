/*    */ package com.funcom.gameengine.utils;
/*    */ 
/*    */ import com.funcom.commons.PerformanceGraphNode;
/*    */ import com.jme.renderer.pass.RenderPass;
/*    */ import com.jme.scene.Spatial;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PerformanceGraphRenderPass
/*    */   extends RenderPass
/*    */ {
/* 15 */   private static final Logger LOGGER = Logger.getLogger(PerformanceGraphRenderPass.class);
/*    */   
/*    */   private PerformanceGraphNode graphNode;
/*    */   
/*    */   public PerformanceGraphRenderPass(PerformanceGraphNode graphNode) {
/* 20 */     this.graphNode = graphNode;
/* 21 */     add((Spatial)graphNode);
/*    */   }
/*    */   
/*    */   protected void doUpdate(float tpf) {
/* 25 */     this.graphNode.updateGeometricState(tpf, true);
/*    */   }
/*    */   
/*    */   public void setTrackingStats(PerformanceGraphNode.TrackingStat... trackingStats) {
/* 29 */     this.graphNode.setTrackingStats(trackingStats);
/*    */   }
/*    */   
/*    */   public PerformanceGraphNode.TrackingStat[] getTrackingStats() {
/* 33 */     return this.graphNode.getTrackingStats();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengin\\utils\PerformanceGraphRenderPass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */