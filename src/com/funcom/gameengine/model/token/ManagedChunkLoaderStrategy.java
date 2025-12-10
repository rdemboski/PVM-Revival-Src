/*    */ package com.funcom.gameengine.model.token;
/*    */ 
/*    */ import com.funcom.commons.FileUtils;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.chunks.ChunkWorldInfo;
/*    */ import com.funcom.gameengine.model.chunks.ChunkWorldNode;
/*    */ import com.funcom.gameengine.model.chunks.ManagedChunkNode;
/*    */ import com.funcom.gameengine.model.factories.LoadException;
/*    */ import com.funcom.gameengine.spatial.LineNode;
/*    */ import java.awt.Point;
/*    */ import java.util.List;
/*    */ import org.jdom.Document;
/*    */ import org.jdom.Element;
/*    */ 
/*    */ public class ManagedChunkLoaderStrategy
/*    */   implements ChunkLoaderStrategy {
/*    */   private ChunkWorldNode chunkWorldNode;
/*    */   private ChunkFetcherStrategy chunkFetcherStrategy;
/*    */   private String managedFile;
/*    */   
/*    */   public ManagedChunkLoaderStrategy(ChunkWorldNode chunkWorldNode, ChunkFetcherStrategy chunkFetcherStrategy, String managedFile) {
/* 22 */     this.chunkWorldNode = chunkWorldNode;
/* 23 */     this.chunkFetcherStrategy = chunkFetcherStrategy;
/* 24 */     this.managedFile = managedFile;
/*    */   }
/*    */ 
/*    */   
/*    */   public void process(ChunkLoaderToken chunkLoaderToken, WorldCoordinate worldCoordinate) {
/* 29 */     ManagedChunkNode managedChunkNode = this.chunkWorldNode.getManagedChunkNode();
/* 30 */     ChunkWorldInfo chunkWorldInfo = this.chunkWorldNode.getChunkWorldInfo();
/* 31 */     Document document = this.chunkFetcherStrategy.getBinaryManagedDocument(FileUtils.fixTailingSlashes(this.chunkWorldNode.getBasePath()) + this.managedFile, chunkWorldInfo.getBasePath());
/*    */ 
/*    */     
/* 34 */     Element root = document.getRootElement();
/*    */     
/* 36 */     LineNode lineRoot = new LineNode(new WorldCoordinate(0, 0, 0.0D, 0.0D, chunkWorldInfo.getMapId(), 0), new WorldCoordinate(this.chunkWorldNode.getWorldWidth(), this.chunkWorldNode.getWorldHeight(), 0.0D, 0.0D, chunkWorldInfo.getMapId(), 0));
/*    */ 
/*    */ 
/*    */     
/* 40 */     managedChunkNode.setLineRoot(lineRoot);
/*    */     
/* 42 */     Point tileCoord = new Point(0, 0);
/*    */ 
/*    */     
/*    */     try {
/* 46 */       String mapId = chunkWorldInfo.getMapId();
/* 47 */       List<Element> collisionNodeElements = root.getChildren("collision-node");
/* 48 */       for (Element collisionElement : collisionNodeElements) {
/* 49 */         chunkLoaderToken.processCollisionNodeElement(collisionElement, mapId, (TokenTargetNode)managedChunkNode, tileCoord);
/*    */       }
/*    */       
/* 52 */       List<Element> collisionElements = root.getChildren("collision-line");
/* 53 */       for (Element collisionElement : collisionElements) {
/* 54 */         chunkLoaderToken.processCollisionLineElement(collisionElement, (TokenTargetNode)managedChunkNode, lineRoot, tileCoord);
/*    */       }
/*    */       
/* 57 */       List<Element> waterLineElements = root.getChildren("water-line");
/* 58 */       for (Element waterLineElement : waterLineElements) {
/* 59 */         chunkLoaderToken.processWaterLineElement(waterLineElement, (TokenTargetNode)managedChunkNode, tileCoord);
/*    */       }
/*    */       
/* 62 */       List<Element> waterPondElements = root.getChildren("water-pond");
/* 63 */       for (Element waterPondElement : waterPondElements) {
/* 64 */         chunkLoaderToken.processWaterPondElement(waterPondElement, (TokenTargetNode)managedChunkNode, tileCoord);
/*    */       }
/*    */       
/* 67 */       List<Element> areaElements = root.getChildren("area");
/* 68 */       for (Element areaElement : areaElements) {
/* 69 */         chunkLoaderToken.processAreaElement(areaElement, (TokenTargetNode)managedChunkNode, worldCoordinate.getTileCoord());
/*    */       }
/*    */       
/* 72 */       List<Element> patrolElements = root.getChildren("patrol");
/* 73 */       for (Element patrolElement : patrolElements) {
/* 74 */         chunkLoaderToken.processPatrolElement(patrolElement, mapId, (TokenTargetNode)managedChunkNode, lineRoot, worldCoordinate.getTileCoord());
/*    */       }
/*    */     }
/* 77 */     catch (LoadException e) {
/* 78 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\ManagedChunkLoaderStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */