/*    */ package com.funcom.gameengine.pathfinding2;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MapConnectionNode
/*    */   extends AbstractGraphNode
/*    */ {
/* 16 */   private static final Logger LOGGER = Logger.getLogger(MapConnectionNode.class);
/*    */   
/*    */   private String mapName;
/*    */   private Map<MapConnectionNode, List<PortalDefinition>> destinationDefinitions;
/*    */   
/*    */   public MapConnectionNode(String mapName) {
/* 22 */     if (mapName == null)
/* 23 */       throw new IllegalArgumentException("mapName = null"); 
/* 24 */     this.mapName = mapName;
/* 25 */     this.destinationDefinitions = new HashMap<MapConnectionNode, List<PortalDefinition>>();
/*    */   }
/*    */   
/*    */   public List<PortalDefinition> getPortalsTo(MapConnectionNode mapConnectionNode) {
/* 29 */     if (mapConnectionNode == null)
/* 30 */       throw new IllegalArgumentException("mapConnectionNode = null"); 
/* 31 */     if (!this.neighbors.contains(mapConnectionNode)) {
/* 32 */       throw new IllegalStateException("Not in neighbors list: " + mapConnectionNode);
/*    */     }
/* 34 */     return this.destinationDefinitions.get(mapConnectionNode);
/*    */   }
/*    */   
/*    */   public String getMapName() {
/* 38 */     return this.mapName;
/*    */   }
/*    */   
/*    */   public void definePortal(MapConnectionNode mapConnectionNode, PortalDefinition portalDefinition) {
/* 42 */     if (mapConnectionNode == null)
/* 43 */       throw new IllegalArgumentException("mapConnectionNode = null"); 
/* 44 */     if (!this.neighbors.contains(mapConnectionNode)) {
/* 45 */       throw new IllegalStateException("Not in neighbors list: " + mapConnectionNode);
/*    */     }
/* 47 */     List<PortalDefinition> definitionList = this.destinationDefinitions.get(mapConnectionNode);
/* 48 */     if (definitionList == null) {
/* 49 */       definitionList = new LinkedList<PortalDefinition>();
/* 50 */       this.destinationDefinitions.put(mapConnectionNode, definitionList);
/*    */     } 
/*    */     
/* 53 */     definitionList.add(portalDefinition);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float recalculateCost(AStarNode parentAStarNode, AStarNode aStarEndPoint) {
/* 65 */     if (parentAStarNode == null)
/* 66 */       return 0.0F; 
/* 67 */     return parentAStarNode.getGraphNode().recalculateCost(parentAStarNode.getParent(), aStarEndPoint) + 1.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 72 */     return "MapConnectionNode{mapName='" + this.mapName + '\'' + '}';
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<PortalDefinition> getPortalDefinitions() {
/* 78 */     List<PortalDefinition> aggregator = new LinkedList<PortalDefinition>();
/* 79 */     for (List<PortalDefinition> portalDefinitions : this.destinationDefinitions.values()) {
/* 80 */       aggregator.addAll(portalDefinitions);
/*    */     }
/*    */     
/* 83 */     return aggregator;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\MapConnectionNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */