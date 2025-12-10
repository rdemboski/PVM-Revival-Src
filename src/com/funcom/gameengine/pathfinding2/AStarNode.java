/*     */ package com.funcom.gameengine.pathfinding2;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AStarNode
/*     */   implements Comparable<AStarNode>
/*     */ {
/*     */   private static final float HEURISTICS_MODIFIER = 0.55F;
/*     */   private PathGraphNode graphNode;
/*     */   private AStarNode parent;
/*     */   private float g;
/*     */   private Set<AStarNode> neighbors;
/*     */   private float movementCost;
/*     */   
/*     */   public AStarNode(PathGraphNode graphNode) {
/*  42 */     if (graphNode == null)
/*  43 */       throw new IllegalArgumentException("graphNode = null"); 
/*  44 */     this.graphNode = graphNode;
/*     */   }
/*     */   
/*     */   public AStarNode getParent() {
/*  48 */     return this.parent;
/*     */   }
/*     */   
/*     */   public void setParent(AStarNode parent) {
/*  52 */     this.parent = parent;
/*     */   }
/*     */   
/*     */   public PathGraphNode getGraphNode() {
/*  56 */     return this.graphNode;
/*     */   }
/*     */   
/*     */   public void recalculateCost(AStarNode endPoint, HeuristicsAlgorithm heuristicsAlgorithm) {
/*  60 */     this.g = this.graphNode.recalculateCost(getParent(), endPoint);
/*  61 */     this.movementCost = this.g + heuristicsAlgorithm.calculateCost(this, endPoint) * 0.55F;
/*     */   }
/*     */   
/*     */   public Set<AStarNode> getNeighbors() {
/*  65 */     if (this.neighbors != null) {
/*  66 */       return this.neighbors;
/*     */     }
/*  68 */     this.neighbors = new HashSet<AStarNode>();
/*  69 */     for (PathGraphNode node : this.graphNode.getNeighbors())
/*  70 */       this.neighbors.add(new AStarNode(node)); 
/*  71 */     return this.neighbors;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(AStarNode o) {
/*  76 */     return (int)Math.signum(this.movementCost - o.movementCost);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  82 */     if (this == o) return true; 
/*  83 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/*  85 */     AStarNode aStarNode = (AStarNode)o;
/*     */     
/*  87 */     if (!this.graphNode.equals(aStarNode.graphNode)) return false;
/*     */     
/*  89 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  94 */     return this.graphNode.hashCode();
/*     */   }
/*     */   
/*     */   public String toString() {
/*  98 */     return String.format("AStarNode{graphNode=%s, g=%s, movementCost='%s'}", new Object[] { this.graphNode, Float.valueOf(this.g), Float.valueOf(this.movementCost) });
/*     */   }
/*     */   
/*     */   public boolean hasParent() {
/* 102 */     return (this.parent != null);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\AStarNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */