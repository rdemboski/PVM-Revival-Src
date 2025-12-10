/*     */ package com.funcom.gameengine.pathfinding2;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HierarchicalPathFinder
/*     */ {
/*  15 */   private HeuristicsAlgorithm manhattanDistance = new ManhattanDistance();
/*  16 */   private HeuristicsAlgorithm mapsConnectivity = new MapNameHeuristics();
/*  17 */   private PathFinder pathFinder = new PathFinder(this.manhattanDistance);
/*     */   private PathGraph rootPathGraph;
/*     */   
/*     */   public void setRootPathGraph(PathGraph rootPathGraph) {
/*  21 */     this.rootPathGraph = rootPathGraph;
/*     */   }
/*     */   
/*     */   public PathGraph getRootPathGraph() {
/*  25 */     return this.rootPathGraph;
/*     */   }
/*     */   
/*     */   public PathResult find(WorldCoordinate start, WorldCoordinate currentEnd, List<WorldCoordinate> allEnds) throws NoPathException {
/*  29 */     if (this.rootPathGraph == null) {
/*  30 */       throw new IllegalStateException("We don't have root pathgraph!");
/*     */     }
/*     */     
/*  33 */     if (this.rootPathGraph.isLeaf()) {
/*  34 */       return new PathResult(start, allEnds, pathAlongSameGraph(start, currentEnd, this.rootPathGraph));
/*     */     }
/*  36 */     if (start.getMapId().equals(currentEnd.getMapId())) {
/*  37 */       return new PathResult(start, allEnds, pathAlongSameGraph(start, currentEnd, this.rootPathGraph.toChildGraphForCoord(start)));
/*     */     }
/*  39 */     return new PathResult(start, currentEnd, multilevelPathfinding(start, currentEnd));
/*     */   }
/*     */ 
/*     */   
/*     */   public void find(PathResult.PathSection pathSection) throws NoPathException {
/*  44 */     if (pathSection == null)
/*  45 */       throw new IllegalArgumentException("pathSection = null"); 
/*  46 */     PathGraph childPathGraph = this.rootPathGraph.toChildGraphForCoord(pathSection.getStart());
/*  47 */     AStarNode path = pathAlongSameGraph(pathSection.getStart(), pathSection.getClosestEnd(pathSection.getStart()), childPathGraph);
/*  48 */     pathSection.setPath(path);
/*     */   }
/*     */   
/*     */   private AStarNode pathAlongSameGraph(WorldCoordinate start, WorldCoordinate end, PathGraph pathGraph) throws NoPathException {
/*  52 */     this.pathFinder.setHeuristicsAlgorithm(this.manhattanDistance);
/*     */     
/*  54 */     PathGraphNode startNode = pathGraph.toGraphNode(start);
/*  55 */     PathGraphNode endNode = pathGraph.toGraphNode(end);
/*     */     
/*  57 */     return this.pathFinder.find(startNode, endNode);
/*     */   }
/*     */ 
/*     */   
/*     */   private List<PathResult.PathSection> multilevelPathfinding(WorldCoordinate start, WorldCoordinate end) throws NoPathException {
/*  62 */     PathGraphNode startGraphNode = this.rootPathGraph.toGraphNode(start);
/*  63 */     if (startGraphNode == null) {
/*  64 */       throw new NoPathException("startGraphNode is null, can't find world coordinate on root graph: " + start);
/*     */     }
/*  66 */     PathGraphNode endGraphNode = this.rootPathGraph.toGraphNode(end);
/*  67 */     if (endGraphNode == null) {
/*  68 */       throw new NoPathException("endGraphNode is null, can't find world coordinate on root graph: " + end);
/*     */     }
/*  70 */     this.pathFinder.setHeuristicsAlgorithm(this.mapsConnectivity);
/*  71 */     AStarNode mapRoute = this.pathFinder.find(startGraphNode, endGraphNode);
/*     */ 
/*     */ 
/*     */     
/*  75 */     List<MapConnectionNode> mapsToVisit = new ArrayList<MapConnectionNode>();
/*  76 */     while (mapRoute.hasParent()) {
/*  77 */       mapsToVisit.add((MapConnectionNode)mapRoute.getGraphNode());
/*  78 */       mapRoute = mapRoute.getParent();
/*     */     } 
/*  80 */     mapsToVisit.add((MapConnectionNode)mapRoute.getGraphNode());
/*  81 */     Collections.reverse(mapsToVisit);
/*     */ 
/*     */     
/*  84 */     List<PathResult.PathSection> sections = new ArrayList<PathResult.PathSection>();
/*  85 */     WorldCoordinate iterationStartPoint = start;
/*  86 */     for (int i = 0; i < mapsToVisit.size() - 1; i++) {
/*  87 */       MapConnectionNode node = mapsToVisit.get(i);
/*  88 */       MapConnectionNode nextNode = mapsToVisit.get(i + 1);
/*     */       
/*  90 */       List<PortalDefinition> portalDefinitions = node.getPortalsTo(nextNode);
/*  91 */       DistanceComparator distanceComparator = new DistanceComparator(end);
/*  92 */       Collections.sort(portalDefinitions, distanceComparator);
/*     */       
/*  94 */       List<WorldCoordinate> endPoints = new LinkedList<WorldCoordinate>();
/*  95 */       for (PortalDefinition portalDef : portalDefinitions) {
/*  96 */         endPoints.addAll(portalDef.getPositions());
/*     */       }
/*     */       
/*  99 */       sections.add(new PathResult.PathSection(iterationStartPoint, endPoints));
/*     */ 
/*     */ 
/*     */       
/* 103 */       iterationStartPoint = ((PortalDefinition)portalDefinitions.get(0)).getDestination();
/*     */     } 
/*     */ 
/*     */     
/* 107 */     sections.add(new PathResult.PathSection(iterationStartPoint, end));
/*     */     
/* 109 */     return sections;
/*     */   }
/*     */   
/*     */   public void enableStatistics(boolean enableStatistics) {
/* 113 */     this.pathFinder.enableStatistics(enableStatistics);
/*     */   }
/*     */   
/*     */   public PathFinder.Statistics getStatistics() {
/* 117 */     return this.pathFinder.getStatistics();
/*     */   }
/*     */   
/*     */   private static class DistanceComparator implements Comparator<PortalDefinition> {
/*     */     private WorldCoordinate reference;
/*     */     
/*     */     private DistanceComparator(WorldCoordinate reference) {
/* 124 */       if (reference == null)
/* 125 */         throw new IllegalArgumentException("reference = null"); 
/* 126 */       this.reference = reference;
/*     */     }
/*     */     
/*     */     public int compare(PortalDefinition o1, PortalDefinition o2) {
/* 130 */       double o1Dist = ((WorldCoordinate)o1.getPositions().get(0)).distanceTo(this.reference);
/* 131 */       double o2Dist = ((WorldCoordinate)o2.getPositions().get(0)).distanceTo(this.reference);
/*     */       
/* 133 */       if (o1Dist < o2Dist)
/* 134 */         return -1; 
/* 135 */       if (o1Dist > o2Dist) {
/* 136 */         return 1;
/*     */       }
/* 138 */       return 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\HierarchicalPathFinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */