/*    */ package com.funcom.gameengine.view.lighting;
/*    */ 
/*    */ import com.jme.light.LightNode;
/*    */ import com.jme.scene.Spatial;
/*    */ import com.jme.scene.shape.Sphere;
/*    */ import com.jme.scene.state.RenderState;
/*    */ import com.jme.scene.state.WireframeState;
/*    */ import com.jme.system.DisplaySystem;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LightNodeWithMarker
/*    */   extends LightNode
/*    */ {
/*    */   private static boolean showMarkers = false;
/* 20 */   private static List<LightNodeWithMarker> objectList = new LinkedList<LightNodeWithMarker>();
/*    */   private Sphere lightSphere;
/*    */   
/*    */   public LightNodeWithMarker() {
/* 24 */     debugUpdate();
/* 25 */     objectList.add(this);
/*    */   }
/*    */   
/*    */   public LightNodeWithMarker(String name) {
/* 29 */     super(name);
/* 30 */     debugUpdate();
/* 31 */     objectList.add(this);
/*    */   }
/*    */   
/*    */   public static void setShowMarkers(boolean showMarkers) {
/* 35 */     LightNodeWithMarker.showMarkers = showMarkers;
/* 36 */     notifyObjects();
/*    */   }
/*    */   
/*    */   private static void notifyObjects() {
/* 40 */     for (LightNodeWithMarker lightMarkerNode : objectList) {
/* 41 */       lightMarkerNode.debugUpdate();
/*    */     }
/*    */   }
/*    */   
/*    */   private void debugUpdate() {
/* 46 */     if (showMarkers) {
/* 47 */       attachChild(getLightMesh());
/*    */     } else {
/* 49 */       detachChild(getLightMesh());
/*    */     } 
/*    */   }
/*    */   private Spatial getLightMesh() {
/* 53 */     if (this.lightSphere != null) {
/* 54 */       return (Spatial)this.lightSphere;
/*    */     }
/*    */ 
/*    */     
/* 58 */     WireframeState ws = DisplaySystem.getDisplaySystem().getRenderer().createWireframeState();
/* 59 */     ws.setEnabled(true);
/*    */     
/* 61 */     this.lightSphere = new Sphere("light-sphere-marker", 5, 5, 0.2F);
/* 62 */     this.lightSphere.setRenderQueueMode(3);
/* 63 */     this.lightSphere.setRenderState((RenderState)ws);
/* 64 */     this.lightSphere.setLightCombineMode(Spatial.LightCombineMode.Off);
/* 65 */     this.lightSphere.updateRenderState();
/* 66 */     return (Spatial)this.lightSphere;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\lighting\LightNodeWithMarker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */