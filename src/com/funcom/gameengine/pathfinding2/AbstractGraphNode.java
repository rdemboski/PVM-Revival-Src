/*    */ package com.funcom.gameengine.pathfinding2;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractGraphNode
/*    */   implements PathGraphNode
/*    */ {
/* 13 */   protected final Set<PathGraphNode> neighbors = new HashSet<PathGraphNode>();
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<PathGraphNode> getNeighbors() {
/* 18 */     return this.neighbors;
/*    */   }
/*    */   
/*    */   public void addNeighbor(PathGraphNode neighbor) {
/* 22 */     if (neighbor == null)
/* 23 */       throw new IllegalArgumentException("You can't add 'null' as neighbor!"); 
/* 24 */     if (neighbor.equals(this)) {
/* 25 */       throw new IllegalArgumentException("You can't add 'this' as neighbor!");
/*    */     }
/* 27 */     this.neighbors.add(neighbor);
/*    */   }
/*    */   
/*    */   public void removeAllNeighbors(Collection<WorldBoundsGraphNode> encompassedNodes) {
/* 31 */     this.neighbors.removeAll(encompassedNodes);
/*    */   }
/*    */   
/*    */   public void setNeighbors(Set<? extends PathGraphNode> neighbors) {
/* 35 */     this.neighbors.clear();
/* 36 */     this.neighbors.addAll(neighbors);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\AbstractGraphNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */