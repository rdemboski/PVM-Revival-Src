/*    */ package com.funcom.gameengine.pathfinding2;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ public abstract class AbstractPathGraph
/*    */   implements PathGraph
/*    */ {
/*    */   protected PathGraph parent;
/*    */   protected Set<PathGraph> children;
/*    */   
/*    */   protected void setParent(PathGraph parent) {
/* 15 */     this.parent = parent;
/*    */   }
/*    */   
/*    */   public PathGraph getParent() {
/* 19 */     return this.parent;
/*    */   }
/*    */   
/*    */   public boolean hasParent() {
/* 23 */     return (this.parent != null);
/*    */   }
/*    */   
/*    */   public void addChildGraph(PathGraph pathGraph) {
/* 27 */     if (this.children == null)
/* 28 */       this.children = new HashSet<PathGraph>(); 
/* 29 */     this.children.add(pathGraph);
/*    */   }
/*    */   
/*    */   public void removeChildGraph(PathGraph pathGraph) {
/* 33 */     if (this.children != null)
/* 34 */       this.children.remove(pathGraph); 
/*    */   }
/*    */   
/*    */   public void removeAllChildren() {
/* 38 */     if (this.children != null)
/* 39 */       this.children.clear(); 
/*    */   }
/*    */   
/*    */   public Set<PathGraph> getChildren() {
/* 43 */     return this.children;
/*    */   }
/*    */   
/*    */   public boolean isLeaf() {
/* 47 */     return (this.children == null || this.children.isEmpty());
/*    */   }
/*    */   
/*    */   public PathGraph toChildGraphForCoord(WorldCoordinate worldCoordinate) {
/* 51 */     if (isLeaf()) {
/* 52 */       throw new IllegalStateException("This path graph is a leaf!");
/*    */     }
/* 54 */     for (PathGraph child : this.children) {
/* 55 */       if (child.toGraphNode(worldCoordinate) != null)
/* 56 */         return child; 
/* 57 */     }  return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\AbstractPathGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */