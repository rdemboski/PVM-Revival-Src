/*    */ package com.funcom.gameengine.pathfinding2;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MapConnectivityGraph
/*    */   extends AbstractPathGraph
/*    */ {
/*    */   private Map<String, MapConnectionNode> mapConnectionNodes;
/*    */   
/*    */   public MapConnectivityGraph() {
/* 18 */     this.mapConnectionNodes = new HashMap<String, MapConnectionNode>();
/*    */   }
/*    */   
/*    */   public MapConnectivityGraph(Collection<MapConnectionNode> nodes) {
/* 22 */     this.mapConnectionNodes = new HashMap<String, MapConnectionNode>();
/* 23 */     for (MapConnectionNode node : nodes)
/* 24 */       this.mapConnectionNodes.put(node.getMapName(), node); 
/*    */   }
/*    */   
/*    */   public MapConnectivityGraph(Map<String, MapConnectionNode> mapConnectionNodes) {
/* 28 */     this.mapConnectionNodes = new HashMap<String, MapConnectionNode>();
/* 29 */     this.mapConnectionNodes.putAll(mapConnectionNodes);
/*    */   }
/*    */   
/*    */   public void addNode(MapConnectionNode mapConnectionNode) {
/* 33 */     if (this.mapConnectionNodes.put(mapConnectionNode.getMapName(), mapConnectionNode) != null)
/* 34 */       throw new IllegalStateException("Overwriting existing node: " + mapConnectionNode); 
/*    */   }
/*    */   
/*    */   public MapConnectionNode toGraphNode(String mapName) {
/* 38 */     if (mapName == null) {
/* 39 */       throw new IllegalArgumentException("mapName = null");
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 45 */     return this.mapConnectionNodes.get(mapName);
/*    */   }
/*    */   
/*    */   public MapConnectionNode toGraphNode(WorldCoordinate worldCoordinate) {
/* 49 */     return toGraphNode(worldCoordinate.getMapId());
/*    */   }
/*    */   
/*    */   public Collection<MapConnectionNode> getAllNodes() {
/* 53 */     return this.mapConnectionNodes.values();
/*    */   }
/*    */   
/*    */   public int getSize() {
/* 57 */     return this.mapConnectionNodes.size();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 61 */     return "MapConnectivityGraph{mapConnectionNodes=" + this.mapConnectionNodes + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\MapConnectivityGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */