/*     */ package com.funcom.gameengine.pathfinding2;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ public class PathResult
/*     */ {
/*     */   private List<PathSection> pathSections;
/*     */   private WorldCoordinate start;
/*     */   private WorldCoordinate end;
/*     */   
/*     */   public PathResult(WorldCoordinate start, List<WorldCoordinate> end, AStarNode path) {
/*  27 */     if (start == null)
/*  28 */       throw new IllegalArgumentException("start = null"); 
/*  29 */     if (end == null)
/*  30 */       throw new IllegalArgumentException("end = null"); 
/*  31 */     if (path == null)
/*  32 */       throw new IllegalArgumentException("path = null"); 
/*  33 */     if (!start.getMapId().equals(((WorldCoordinate)end.get(0)).getMapId())) {
/*  34 */       throw new IllegalArgumentException("Maps for world coordinates differ!");
/*     */     }
/*  36 */     this.start = start;
/*  37 */     this.end = end.get(0);
/*  38 */     this.pathSections = new ArrayList<PathSection>(1);
/*  39 */     this.pathSections.add(new PathSection(start, end, path));
/*     */   }
/*     */   
/*     */   public PathResult(WorldCoordinate start, WorldCoordinate end, List<PathSection> pathSections) {
/*  43 */     if (start == null)
/*  44 */       throw new IllegalArgumentException("start = null"); 
/*  45 */     if (end == null)
/*  46 */       throw new IllegalArgumentException("end = null"); 
/*  47 */     this.start = start;
/*  48 */     this.end = end;
/*  49 */     this.pathSections = new ArrayList<PathSection>(pathSections.size());
/*  50 */     this.pathSections.addAll(pathSections);
/*     */   }
/*     */   
/*     */   public boolean isComplete() {
/*  54 */     for (PathSection pathSection : this.pathSections) {
/*  55 */       if (!pathSection.isPathCalculated())
/*  56 */         return false; 
/*  57 */     }  return true;
/*     */   }
/*     */   
/*     */   public List<PathSection> getPathSections() {
/*  61 */     return this.pathSections;
/*     */   }
/*     */   
/*     */   public WorldCoordinate getStart() {
/*  65 */     return this.start;
/*     */   }
/*     */   
/*     */   public WorldCoordinate getEnd() {
/*  69 */     return this.end;
/*     */   }
/*     */   
/*     */   public static class PathSection {
/*     */     private WorldCoordinate start;
/*     */     private List<WorldCoordinate> end;
/*     */     private AStarNode path;
/*     */     
/*     */     public PathSection(WorldCoordinate start, List<WorldCoordinate> end) {
/*  78 */       if (start == null)
/*  79 */         throw new IllegalArgumentException("start = null"); 
/*  80 */       if (end == null)
/*  81 */         throw new IllegalArgumentException("end = null"); 
/*  82 */       for (WorldCoordinate endPos : end) {
/*  83 */         if (!start.getMapId().equals(endPos.getMapId()))
/*  84 */           throw new IllegalArgumentException("Map section endpoints HAVE TO lie on same map! Start='" + start + "', end='" + end + "'"); 
/*  85 */       }  this.start = start;
/*  86 */       this.end = end;
/*     */     }
/*     */     
/*     */     public PathSection(WorldCoordinate start, WorldCoordinate end) {
/*  90 */       if (start == null)
/*  91 */         throw new IllegalArgumentException("start = null"); 
/*  92 */       if (end == null)
/*  93 */         throw new IllegalArgumentException("end = null"); 
/*  94 */       if (!start.getMapId().equals(end.getMapId()))
/*  95 */         throw new IllegalArgumentException("Map section endpoints HAVE TO lie on same map! Start='" + start + "', end='" + end + "'"); 
/*  96 */       this.start = start;
/*  97 */       this.end = new LinkedList<WorldCoordinate>();
/*  98 */       this.end.add(end);
/*     */     }
/*     */     
/*     */     public PathSection(WorldCoordinate start, List<WorldCoordinate> end, AStarNode path) {
/* 102 */       if (start == null)
/* 103 */         throw new IllegalArgumentException("start = null"); 
/* 104 */       if (end == null)
/* 105 */         throw new IllegalArgumentException("end = null"); 
/* 106 */       if (!start.getMapId().equals(((WorldCoordinate)end.get(0)).getMapId()))
/* 107 */         throw new IllegalArgumentException("Map section endpoints HAVE TO lie on same map! Start='" + start + "', end='" + end + "'"); 
/* 108 */       if (path == null)
/* 109 */         throw new IllegalArgumentException("path = null"); 
/* 110 */       this.start = start;
/* 111 */       this.end = end;
/* 112 */       this.path = path;
/*     */     }
/*     */     
/*     */     public String getMapId() {
/* 116 */       return this.start.getMapId();
/*     */     }
/*     */     
/*     */     public WorldCoordinate getStart() {
/* 120 */       return this.start;
/*     */     }
/*     */     
/*     */     public WorldCoordinate getClosestEnd(WorldCoordinate start) {
/* 124 */       WorldCoordinate currentlyClosest = this.end.get(0);
/* 125 */       for (int i = 1; i < this.end.size(); i++) {
/* 126 */         if (((WorldCoordinate)this.end.get(i)).distanceTo(start) < currentlyClosest.distanceTo(start))
/* 127 */           currentlyClosest = this.end.get(i); 
/*     */       } 
/* 129 */       return currentlyClosest;
/*     */     }
/*     */     
/*     */     public List<WorldCoordinate> getEnds() {
/* 133 */       return this.end;
/*     */     }
/*     */     
/*     */     public AStarNode getPath() {
/* 137 */       return this.path;
/*     */     }
/*     */     
/*     */     public void setPath(AStarNode path) {
/* 141 */       this.path = path;
/*     */     }
/*     */     
/*     */     public boolean isPathCalculated() {
/* 145 */       return (this.path != null);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 149 */       return "PathSection{start=" + this.start + ", end=" + this.end + '}';
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\PathResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */