/*    */ package com.funcom.gameengine.model.chunks;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.jme.scene.Node;
/*    */ import com.jme.scene.Spatial;
/*    */ import com.turborilla.jops.jme.JopsNode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnvironmentalParticleRemover
/*    */   implements ChunkListener
/*    */ {
/*    */   public void newChunk(String chunkPath, WorldCoordinate chunkOrigin, ChunkWorldNode chunkWorldNode) {}
/*    */   
/*    */   public void addedChunk(ChunkNode chunkNode) {}
/*    */   
/*    */   public void removedChunk(ChunkNode chunkNode) {
/* 23 */     removeParticles(chunkNode);
/*    */   }
/*    */   
/*    */   private void removeParticles(Node node) {
/* 27 */     if (node != null && node.getQuantity() > 0)
/* 28 */       for (Spatial spatial : node.getChildren()) {
/* 29 */         if (spatial instanceof JopsNode) {
/* 30 */           ((JopsNode)spatial).killSystem();
/*    */           continue;
/*    */         } 
/* 33 */         if (spatial instanceof Node)
/* 34 */           removeParticles((Node)spatial); 
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\chunks\EnvironmentalParticleRemover.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */