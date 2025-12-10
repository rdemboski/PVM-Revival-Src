/*    */ package com.funcom.gameengine.model.token;
/*    */ 
/*    */ import com.funcom.commons.FileUtils;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.ai.patrol.Patrol;
/*    */ import com.funcom.gameengine.ai.patrol.PatrolInformation;
/*    */ import com.funcom.gameengine.model.chunks.ChunkWorldInfo;
/*    */ import com.funcom.gameengine.spatial.LineNode;
/*    */ import com.funcom.gameengine.utils.SpatialUtils;
/*    */ import java.awt.Point;
/*    */ import java.util.List;
/*    */ import org.jdom.DataConversionException;
/*    */ import org.jdom.Document;
/*    */ import org.jdom.Element;
/*    */ 
/*    */ public class ServerManagedStragey
/*    */   implements ChunkLoaderStrategy
/*    */ {
/*    */   private ChunkWorldInfo chunkWorldInfo;
/*    */   private ChunkFetcherStrategy chunkFetcherStrategy;
/*    */   private ChunkBuilder chunkBuilder;
/*    */   private LineNode lineRoot;
/*    */   
/*    */   public ServerManagedStragey(ChunkWorldInfo chunkWorldInfo, ChunkFetcherStrategy chunkFetcherStrategy, ChunkBuilder chunkBuilder, LineNode lineRoot) {
/* 25 */     this.chunkWorldInfo = chunkWorldInfo;
/* 26 */     this.chunkFetcherStrategy = chunkFetcherStrategy;
/* 27 */     this.chunkBuilder = chunkBuilder;
/* 28 */     this.lineRoot = lineRoot;
/*    */   }
/*    */   
/*    */   public void process(ChunkLoaderToken chunkLoaderToken, WorldCoordinate worldCoordinate) {
/* 32 */     Document document = this.chunkFetcherStrategy.getBinaryManagedDocument(FileUtils.fixTailingSlashes(this.chunkWorldInfo.getBasePath()) + "managed.bunk", this.chunkWorldInfo.getBasePath());
/*    */ 
/*    */ 
/*    */     
/* 36 */     Element root = document.getRootElement();
/* 37 */     TokenTargetNode dummyTargetNode = new TokenTargetNode.Dummy();
/*    */ 
/*    */     
/*    */     try {
/* 41 */       List<Element> collisionNodeElements = root.getChildren("collision-node");
/* 42 */       for (Element collisionElement : collisionNodeElements) {
/* 43 */         String ident = collisionElement.getAttributeValue("num");
/* 44 */         WorldCoordinate coord = SpatialUtils.getElementWorldCoordinate(collisionElement, this.chunkWorldInfo.getMapId());
/* 45 */         this.chunkBuilder.createCollisionNode(coord, ident, dummyTargetNode, worldCoordinate.getTileCoord(), null);
/*    */       } 
/*    */       
/* 48 */       List<Element> collisionElements = root.getChildren("collision-line");
/* 49 */       for (Element collisionElement : collisionElements) {
/* 50 */         String startIdent = collisionElement.getAttributeValue("node1");
/* 51 */         String endIdent = collisionElement.getAttributeValue("node2");
/* 52 */         double height = 0.0D;
/* 53 */         if (collisionElement.getAttribute("height") != null) {
/* 54 */           String heightStr = collisionElement.getAttributeValue("height");
/* 55 */           height = Double.parseDouble(heightStr);
/*    */         } 
/*    */         
/* 58 */         this.chunkBuilder.createCollisionLine(startIdent, endIdent, height, this.lineRoot, dummyTargetNode, worldCoordinate.getTileCoord(), null);
/*    */       } 
/*    */       
/* 61 */       List<Element> patrolElements = root.getChildren("patrol");
/* 62 */       for (Element patrolElement : patrolElements) {
/* 63 */         processPatrolElement(patrolElement, dummyTargetNode, worldCoordinate.getTileCoord(), this.lineRoot);
/*    */       }
/*    */     }
/* 66 */     catch (DataConversionException e) {
/* 67 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void processPatrolElement(Element patrolElement, TokenTargetNode tokenTargetNode, Point tileCoord, LineNode lineRoot) {
/* 74 */     String patrolName = patrolElement.getAttributeValue("name");
/* 75 */     Patrol patrol = new Patrol(patrolName);
/* 76 */     PatrolInformation.instance().addPatrol(this.chunkWorldInfo.getBasePath(), patrol);
/*    */     
/* 78 */     List<Element> nodes = patrolElement.getChildren("patrol-node");
/* 79 */     for (Element node : nodes) {
/*    */       try {
/* 81 */         WorldCoordinate coord = SpatialUtils.getElementWorldCoordinate(node, this.chunkWorldInfo.getMapId());
/* 82 */         String ident = node.getAttributeValue("num");
/* 83 */         float delay = Float.valueOf(node.getAttributeValue("pause")).floatValue();
/* 84 */         this.chunkBuilder.createPatrolNode(coord, ident, patrolName, delay, tokenTargetNode, tileCoord, null);
/* 85 */       } catch (DataConversionException e) {
/* 86 */         throw new RuntimeException(e);
/*    */       } 
/*    */     } 
/*    */     
/* 90 */     List<Element> lines = patrolElement.getChildren("patrol-line");
/* 91 */     for (Element line : lines) {
/* 92 */       String startIdent = line.getAttributeValue("node1");
/* 93 */       String endIdent = line.getAttributeValue("node2");
/* 94 */       this.chunkBuilder.createPatrolLine(startIdent, endIdent, lineRoot, patrolName, tokenTargetNode, tileCoord, null);
/*    */     } 
/*    */     
/* 97 */     patrol.clearPatrolNodes();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\ServerManagedStragey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */