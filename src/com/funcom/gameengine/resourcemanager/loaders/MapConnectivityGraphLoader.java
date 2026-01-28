/*    */ package com.funcom.gameengine.resourcemanager.loaders;
/*    */ 
/*    */ import com.funcom.gameengine.pathfinding2.MapConnectivityGraph;
/*    */ import com.funcom.gameengine.pathfinding2.MapConnectivityGraphSerializer;
/*    */ import com.funcom.gameengine.resourcemanager.LoadException;
/*    */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MapConnectivityGraphLoader
/*    */   extends AbstractLoader
/*    */ {
/*    */   public MapConnectivityGraphLoader() {
/* 17 */     super(MapConnectivityGraph.class);
/*    */   }
/*    */   
           @SuppressWarnings("unchecked")
/*    */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/* 21 */     BufferedInputStream bis = null;
/*    */     try {
/* 23 */       bis = new BufferedInputStream(getFileInputStream(managedResource.getName()));
/* 24 */       MapConnectivityGraph mapConnectivityGraph = MapConnectivityGraphSerializer.load(bis);
/* 25 */       ((ManagedResource<MapConnectivityGraph>)managedResource).setResource(mapConnectivityGraph);
/* 26 */     } catch (IOException e) {
/* 27 */       throw new LoadException(getResourceManager(), managedResource, e);
/*    */     } finally {
/* 29 */       closeSafely(bis, managedResource);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\MapConnectivityGraphLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */