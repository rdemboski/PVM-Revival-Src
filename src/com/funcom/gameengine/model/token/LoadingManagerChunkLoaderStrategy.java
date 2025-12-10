/*    */ package com.funcom.gameengine.model.token;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.ResourceGetter;
/*    */ import com.funcom.gameengine.model.chunks.ChunkWorldNode;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.ChunkLMToken;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.PrioritizedLoadingTokenQueue;
/*    */ 
/*    */ public class LoadingManagerChunkLoaderStrategy
/*    */   implements ChunkLoaderStrategy {
/*    */   private ChunkWorldNode chunkWorldNode;
/*    */   private ChunkFetcherStrategy chunkFetcherStrategy;
/*    */   private ResourceGetter resourceGetter;
/*    */   
/*    */   public LoadingManagerChunkLoaderStrategy(ChunkWorldNode chunkWorldNode, ChunkFetcherStrategy chunkFetcherStrategy, ResourceGetter resourceGetter) {
/* 18 */     this.chunkWorldNode = chunkWorldNode;
/* 19 */     this.chunkFetcherStrategy = chunkFetcherStrategy;
/* 20 */     this.resourceGetter = resourceGetter;
/*    */   }
/*    */   
/*    */   public void process(ChunkLoaderToken chunkLoaderToken, WorldCoordinate worldCoordinate) {
/* 24 */     LoadingManager.INSTANCE.submitByPriority((LoadingManagerToken)new ChunkLMToken(chunkLoaderToken, worldCoordinate, this.chunkWorldNode, this.chunkFetcherStrategy), PrioritizedLoadingTokenQueue.TokenPriority.PRIORITY_MAP);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\LoadingManagerChunkLoaderStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */