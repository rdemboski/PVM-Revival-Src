/*     */ package com.funcom.gameengine.spatial;
/*     */ 
/*     */ import com.funcom.commons.geom.LineWC;
/*     */ import com.funcom.commons.geom.LineWCHeight;
/*     */ import com.funcom.commons.geom.RectangleWC;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ public class LineNode
/*     */   extends RectangleWC
/*     */ {
/*     */   public static final int BUCKET_CAPACITY = 10;
/*     */   private Set<LineNode> children;
/*     */   private Set<LineWCHeight> lines;
/*     */   private LineGrid lineGrid;
/*     */   
/*     */   public LineNode(WorldCoordinate origin, WorldCoordinate extent) {
/*  20 */     super(origin, extent);
/*     */   }
/*     */   
/*     */   public void addLine(LineWCHeight line) {
/*  24 */     if (this.lines == null) {
/*  25 */       this.lines = new HashSet<LineWCHeight>();
/*     */     }
/*  27 */     if (this.lineGrid == null) {
/*  28 */       this.lineGrid = new LineGrid();
/*     */     }
/*     */     
/*  31 */     this.lines.add(line);
/*  32 */     this.lineGrid.put(line);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<LineWCHeight> getLines(RectangleWC r) {
/*     */     Set<LineWCHeight> result;
/*  38 */     if (this.lineGrid != null) {
/*  39 */       result = this.lineGrid.getLines(r);
/*     */     } else {
/*  41 */       result = new HashSet<LineWCHeight>();
/*     */     } 
/*     */     
/*  44 */     if (this.children != null) {
/*  45 */       for (LineNode child : this.children) {
/*  46 */         if (child != null) {
/*  47 */           result.addAll(child.getLines(r));
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  52 */     return result;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public Set<LineWC> getLinesOLD(RectangleWC r) {
/*  57 */     Set<LineWC> result = new HashSet<LineWC>();
/*     */     
/*  59 */     if (this.lines != null) {
/*  60 */       for (LineWCHeight lineWCHeight : this.lines) {
/*  61 */         if (lineWCHeight.intersects(r)) {
/*  62 */           result.add(lineWCHeight);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  67 */     if (this.children != null) {
/*  68 */       for (LineNode child : this.children) {
/*  69 */         if (child != null) {
/*  70 */           result.addAll(child.getLines(r));
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  75 */     return result;
/*     */   }
/*     */   
/*     */   public Set<LineWC> getAllLines() {
/*  79 */     Set<LineWC> result = new HashSet<LineWC>();
/*     */     
/*  81 */     if (this.lines != null) {
/*  82 */       result.addAll(this.lines);
/*     */     }
/*     */     
/*  85 */     if (this.children != null) {
/*  86 */       for (LineNode child : this.children) {
/*  87 */         result.addAll(child.getAllLines());
/*     */       }
/*     */     }
/*     */     
/*  91 */     return result;
/*     */   }
/*     */   
/*     */   public void addChildNode(LineNode lineNode) {
/*  95 */     if (this.children == null) {
/*  96 */       this.children = new HashSet<LineNode>();
/*     */     }
/*     */     
/*  99 */     this.children.add(lineNode);
/*     */   }
/*     */   
/*     */   public int getChildCount() {
/* 103 */     return (this.children == null) ? 0 : this.children.size();
/*     */   }
/*     */   
/*     */   public int getLineCount() {
/* 107 */     return (this.lines == null) ? 0 : this.lines.size();
/*     */   }
/*     */   
/*     */   public int getTotalLineCount() {
/* 111 */     int count = getLineCount();
/*     */     
/* 113 */     if (this.children != null) {
/* 114 */       for (LineNode child : this.children) {
/* 115 */         count += child.getTotalLineCount();
/*     */       }
/*     */     }
/*     */     
/* 119 */     return count;
/*     */   }
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
/*     */   public void clearChildren() {
/* 165 */     this.children = null;
/* 166 */     this.lines = null;
/* 167 */     this.lineGrid = null;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 171 */     return getClass().getName() + "[o=" + this.origin + ",e=" + this.extent + ",s=" + getSize() + ",c=" + getChildCount() + ",l=" + getLineCount() + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printSize(String prefix) {
/* 180 */     System.out.println(prefix + this);
/*     */     
/* 182 */     if (this.children != null)
/* 183 */       for (LineNode child : this.children)
/* 184 */         child.printSize(prefix + " ");  
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\spatial\LineNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */