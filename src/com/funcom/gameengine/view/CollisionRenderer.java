/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.commons.geom.LineWC;
/*    */ import com.funcom.gameengine.OriginListener;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.chunks.ChunkListener;
/*    */ import com.funcom.gameengine.model.chunks.ChunkNode;
/*    */ import com.funcom.gameengine.model.chunks.ChunkWorldNode;
/*    */ import com.jme.math.Vector3f;
/*    */ import com.jme.renderer.ColorRGBA;
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jme.scene.Line;
/*    */ import com.jme.scene.Node;
/*    */ import com.jme.scene.Spatial;
/*    */ 
/*    */ 
/*    */ public class CollisionRenderer
/*    */   implements OriginListener, ChunkListener
/*    */ {
/*    */   private ChunkWorldNode chunkWorldNode;
/* 21 */   private Node renderNode = new Node("Collision lines");
/*    */ 
/*    */   
/*    */   public void setChunkWorldNode(ChunkWorldNode chunkWorldNode) {
/* 25 */     this.chunkWorldNode = chunkWorldNode;
/* 26 */     updateRenderNode();
/*    */   }
/*    */   
/*    */   public void updateRenderNode() {
/* 30 */     this.renderNode.unlock();
/* 31 */     this.renderNode.detachAllChildren();
/*    */     
/* 33 */     if (this.chunkWorldNode == null) {
/*    */       return;
/*    */     }
/*    */     
/* 37 */     for (LineWC lineWC : this.chunkWorldNode.getAllAvailableCollisionLines()) {
/* 38 */       Vector3f[] vertex = { new Vector3f((float)lineWC.getX1(), 0.0F, (float)lineWC.getY1()), new Vector3f((float)lineWC.getX2(), 0.0F, (float)lineWC.getY2()) };
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 43 */       Vector3f[] normal = { new Vector3f(0.0F, 1.0F, 0.0F), new Vector3f(0.0F, 1.0F, 0.0F) };
/* 44 */       ColorRGBA[] color = { ColorRGBA.red, ColorRGBA.red };
/*    */       
/* 46 */       Line line = new Line("Line", vertex, normal, color, null);
/* 47 */       this.renderNode.attachChild((Spatial)line);
/*    */     } 
/*    */     
/* 50 */     this.renderNode.lock();
/*    */   }
/*    */   
/*    */   public void renderLines(Renderer r) {
/* 54 */     r.draw((Spatial)this.renderNode);
/*    */   }
/*    */   
/*    */   public void originMoved(int oldX, int oldY, int newX, int newY) {
/* 58 */     updateRenderNode();
/*    */   }
/*    */ 
/*    */   
/*    */   public void newChunk(String chunkPath, WorldCoordinate chunkOrigin, ChunkWorldNode chunkWorldNode) {}
/*    */ 
/*    */   
/*    */   public void addedChunk(ChunkNode chunkNode) {
/* 66 */     updateRenderNode();
/*    */   }
/*    */   
/*    */   public void removedChunk(ChunkNode chunkNode) {
/* 70 */     updateRenderNode();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\CollisionRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */