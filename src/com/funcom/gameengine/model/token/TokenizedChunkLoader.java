/*    */ package com.funcom.gameengine.model.token;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.ResourceGetter;
/*    */ import com.funcom.gameengine.model.chunks.ChunkListener;
/*    */ import com.funcom.gameengine.model.chunks.ChunkNode;
/*    */ import com.funcom.gameengine.model.chunks.ChunkWorldNode;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class TokenizedChunkLoader
/*    */   implements ChunkListener {
/* 13 */   private static final Logger LOGGER = Logger.getLogger(TokenizedChunkLoader.class.getName());
/*    */   
/*    */   private ChunkBuilder chunkBuilder;
/*    */   
/*    */   private ChunkLoaderStrategy chunkLoaderStrategy;
/*    */   private ResourceGetter resourceGetter;
/*    */   private GameIOHandler ioHandler;
/*    */   private ActionFactory actionFactory;
/*    */   
/*    */   public TokenizedChunkLoader(ChunkBuilder chunkBuilder, ChunkLoaderStrategy chunkLoaderStrategy, ResourceGetter resourceGetter, GameIOHandler ioHandler, ActionFactory actionFactory) {
/* 23 */     this.chunkBuilder = chunkBuilder;
/* 24 */     this.chunkLoaderStrategy = chunkLoaderStrategy;
/* 25 */     this.resourceGetter = resourceGetter;
/* 26 */     this.ioHandler = ioHandler;
/* 27 */     this.actionFactory = actionFactory;
/*    */   }
/*    */   
/*    */   public void newChunk(String chunkPath, WorldCoordinate chunkOrigin, ChunkWorldNode chunkWorldNode) {
/* 31 */     LOGGER.info("New chunk: " + chunkPath + ", " + chunkOrigin.getTileCoord());
/*    */     
/* 33 */     ChunkLoaderToken chunkLoaderToken = new ChunkLoaderToken(this.chunkBuilder, this.chunkLoaderStrategy, chunkOrigin, this.resourceGetter, this.ioHandler, this.actionFactory);
/* 34 */     TokenRegister.instance().addToken(chunkLoaderToken);
/*    */   }
/*    */   
/*    */   public void addedChunk(ChunkNode chunkNode) {}
/*    */   
/*    */   public void removedChunk(ChunkNode chunkNode) {}
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\TokenizedChunkLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */