/*    */ package com.funcom.gameengine.resourcemanager.loaders;
/*    */ 
/*    */ import com.funcom.gameengine.jme.TileQuadCached;
/*    */ import com.funcom.gameengine.resourcemanager.LoadException;
/*    */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*    */ import com.jme.system.DisplaySystem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CachedTileDataLoader
/*    */   extends AbstractLoader
/*    */ {
/*    */   public CachedTileDataLoader() {
/* 15 */     super(TileQuadCached.CachedTileData.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/* 20 */     managedResource.setResource(new TileQuadCached.CachedTileData());
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getUnloadInfo(ManagedResource<?> managedResource) {
/* 25 */     TileQuadCached.CachedTileData cachedTileData = (TileQuadCached.CachedTileData)managedResource.getResource();
/*    */     
/* 27 */     if (cachedTileData != null && cachedTileData.hasDisplayListId()) {
/* 28 */       return Integer.valueOf(cachedTileData.getDisplayListId());
/*    */     }
/*    */     
/* 31 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void unloadData(ManagedResource<?> managedResource, Object unloadInfo) {
/* 36 */     if (unloadInfo instanceof Integer) {
/* 37 */       Integer displayListIdInt = (Integer)unloadInfo;
/* 38 */       DisplaySystem.getDisplaySystem().getRenderer().releaseDisplayList(displayListIdInt.intValue());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\CachedTileDataLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */