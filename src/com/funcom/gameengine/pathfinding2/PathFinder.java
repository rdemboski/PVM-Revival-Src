/*     */ package com.funcom.gameengine.pathfinding2;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PathFinder
/*     */ {
/*     */   private PriorityQueue<AStarNode> openSet;
/*     */   private Set<AStarNode> closedSet;
/*     */   private Statistics statistics;
/*     */   private boolean calculateStatistics;
/*     */   private HeuristicsAlgorithm heuristicsAlgorithm;
/*     */   
/*     */   public PathFinder(HeuristicsAlgorithm heuristicsAlgorithm) {
/*  23 */     if (heuristicsAlgorithm == null)
/*  24 */       throw new IllegalArgumentException("heuristicsAlgorithm = null"); 
/*  25 */     this.heuristicsAlgorithm = heuristicsAlgorithm;
/*     */     
/*  27 */     this.openSet = new PriorityQueue<AStarNode>();
/*  28 */     this.closedSet = new HashSet<AStarNode>();
/*  29 */     this.statistics = new Statistics();
/*  30 */     this.calculateStatistics = false;
/*     */   }
/*     */   
/*     */   public HeuristicsAlgorithm getHeuristicsAlgorithm() {
/*  34 */     return this.heuristicsAlgorithm;
/*     */   }
/*     */   
/*     */   public void setHeuristicsAlgorithm(HeuristicsAlgorithm heuristicsAlgorithm) {
/*  38 */     if (heuristicsAlgorithm == null)
/*  39 */       throw new IllegalArgumentException("heuristicsAlgorithm = null"); 
/*  40 */     this.heuristicsAlgorithm = heuristicsAlgorithm;
/*     */   }
/*     */   
/*     */   public void enableStatistics(boolean enable) {
/*  44 */     this.calculateStatistics = enable;
/*     */   }
/*     */   
/*     */   public AStarNode find(PathGraphNode start, PathGraphNode end) throws NoPathException {
/*  48 */     if (start == null)
/*  49 */       throw new NoPathException("Start node is null; probably couldn't find node that contains WorldCoordinate on pathgraph!"); 
/*  50 */     if (end == null) {
/*  51 */       throw new NoPathException("End node is null; probably couldn't find node that contains WorldCoordinate on pathgraph!");
/*     */     }
/*  53 */     this.openSet.clear();
/*  54 */     this.closedSet.clear();
/*     */     
/*  56 */     AStarNode currentNode = new AStarNode(start);
/*  57 */     AStarNode endNode = new AStarNode(end);
/*     */     
/*  59 */     if (this.calculateStatistics) {
/*  60 */       this.statistics.start(currentNode);
/*     */     }
/*     */     
/*  63 */     while (!currentNode.equals(endNode)) {
/*  64 */       this.closedSet.add(currentNode);
/*  65 */       recalculateCurrentCostsTowardsEnd(currentNode, endNode);
/*  66 */       addNeighborsForSearching(currentNode);
/*  67 */       currentNode = this.openSet.poll();
/*  68 */       if (currentNode == null) {
/*  69 */         throw new NoPathException(currentNode, endNode);
/*     */       }
/*     */     } 
/*  72 */     if (this.calculateStatistics)
/*  73 */       this.statistics.end(currentNode); 
/*  74 */     return currentNode;
/*     */   }
/*     */   
/*     */   private void recalculateCurrentCostsTowardsEnd(AStarNode currentNode, AStarNode endNode) {
/*  78 */     for (AStarNode aStarNode : currentNode.getNeighbors()) {
/*  79 */       if (!this.closedSet.contains(aStarNode)) {
/*  80 */         aStarNode.setParent(currentNode);
/*  81 */         aStarNode.recalculateCost(endNode, this.heuristicsAlgorithm);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addNeighborsForSearching(AStarNode currentNode) {
/*  87 */     for (AStarNode neighbouringNode : currentNode.getNeighbors()) {
/*  88 */       if (!this.closedSet.contains(neighbouringNode) && !this.openSet.contains(currentNode))
/*  89 */         this.openSet.add(neighbouringNode); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set<AStarNode> getClosedSet() {
/*  94 */     return this.closedSet;
/*     */   }
/*     */   
/*     */   public Statistics getStatistics() {
/*  98 */     return this.statistics;
/*     */   }
/*     */   
/*     */   public class Statistics {
/*     */     private int nodesSearched;
/*     */     private float timeInSecs;
/*     */     private boolean done;
/*     */     private long startTime;
/*     */     private int optimalPathLength;
/*     */     private AStarNode startNode;
/*     */     
/*     */     public void start(AStarNode startNode) {
/* 110 */       this.startNode = startNode;
/* 111 */       this.startTime = System.nanoTime();
/* 112 */       this.nodesSearched = 0;
/* 113 */       this.timeInSecs = 0.0F;
/* 114 */       this.optimalPathLength = 0;
/* 115 */       this.done = false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void end(AStarNode currentNode) {
/* 122 */       this.done = true;
/* 123 */       this.timeInSecs = (float)(System.nanoTime() - this.startTime) * 1.0E-6F;
/* 124 */       this.nodesSearched = PathFinder.this.closedSet.size();
/* 125 */       AStarNode node = currentNode;
/*     */       
/* 127 */       while (node != null && !node.equals(this.startNode)) {
/* 128 */         this.optimalPathLength++;
/* 129 */         node = node.getParent();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 135 */       if (!this.done) {
/* 136 */         return String.format("Path not found!", new Object[0]);
/*     */       }
/* 138 */       return String.format("Optimal path found. Optimal path length: %d, Nodes searched: %d, time spend: %s msecs", new Object[] { Integer.valueOf(this.optimalPathLength), Integer.valueOf(this.nodesSearched), Float.valueOf(this.timeInSecs) });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\PathFinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */