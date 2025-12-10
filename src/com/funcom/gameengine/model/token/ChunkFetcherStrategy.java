/*    */ package com.funcom.gameengine.model.token;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.ResourceGetter;
/*    */ import com.funcom.gameengine.model.chunks.ChunkNode;
/*    */ import com.funcom.gameengine.model.chunks.ChunkWorldInfo;
/*    */ import com.funcom.gameengine.model.chunks.ChunkWorldNode;
/*    */ import com.funcom.gameengine.model.chunks.ClientChunkNode;
/*    */ import com.funcom.gameengine.model.chunks.ManagedChunkNode;
/*    */ import com.funcom.gameengine.resourcemanager.CacheType;
/*    */ import com.funcom.gameengine.resourcemanager.loaders.BinaryLoader;
/*    */ import java.awt.Point;
/*    */ import java.io.File;
/*    */ import java.nio.ByteBuffer;
/*    */ import org.jdom.Document;
/*    */ 
/*    */ 
/*    */ public class ChunkFetcherStrategy
/*    */ {
/*    */   public ResourceGetter resourceGetter;
/*    */   
/*    */   public ChunkFetcherStrategy(ResourceGetter resourceGetter) {
/* 23 */     this.resourceGetter = resourceGetter;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChunkNode createChunkNode(WorldCoordinate worldCoordinate) {
/* 33 */     return (ChunkNode)new ClientChunkNode(worldCoordinate);
/*    */   }
/*    */   
/*    */   public ManagedChunkNode createManagedNode(ChunkWorldNode chunkWorldNode) {
/* 37 */     return new ManagedChunkNode(chunkWorldNode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Document getChunkDocument(ChunkWorldInfo chunkWorldInfo, Point point) {
/* 47 */     String chunkPath = chunkWorldInfo.getChunkFilename(point);
/* 48 */     return this.resourceGetter.getDocument(chunkPath, CacheType.NOT_CACHED);
/*    */   }
/*    */   
/*    */   public Document getManagedDocument(String managedFile) {
/* 52 */     return this.resourceGetter.getDocument(managedFile, CacheType.NOT_CACHED);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Document getBinaryChunkDocument(ChunkWorldInfo chunkWorldInfo, Point point) {
/* 62 */     String chunkPath = chunkWorldInfo.getBinaryChunkFilename(point);
/* 63 */     ByteBuffer blob = this.resourceGetter.getBlob(chunkPath, CacheType.CACHE_TEMPORARILY);
/* 64 */     return BinaryLoader.convertBlobToMap(blob, this.resourceGetter);
/*    */   }
/*    */   
/*    */   public Document getBinaryManagedDocument(String managedFile, String basePath) {
/* 68 */     ByteBuffer mapBlob = this.resourceGetter.getBlob((new File("binary", managedFile)).getPath().replace('\\', '/'));
/* 69 */     return BinaryLoader.convertBlobToMap(mapBlob, this.resourceGetter);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\ChunkFetcherStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */