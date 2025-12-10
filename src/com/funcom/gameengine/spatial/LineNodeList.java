/*     */ package com.funcom.gameengine.spatial;
/*     */ 
/*     */ import com.funcom.commons.geom.LineWC;
/*     */ import com.funcom.commons.geom.RectangleWC;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LineNodeList
/*     */   extends RectangleWC
/*     */ {
/*     */   public static final int BUCKET_CAPACITY = 10;
/*     */   private List<LineNodeList> children;
/*     */   private List<LineWC> lines;
/*     */   
/*     */   public LineNodeList(WorldCoordinate origin, WorldCoordinate extent) {
/*  23 */     super(origin, extent);
/*     */   }
/*     */   
/*     */   public void addLine(LineWC line) {
/*  27 */     if (this.lines == null) {
/*  28 */       this.lines = new ArrayList<LineWC>();
/*     */     }
/*     */     
/*  31 */     this.lines.add(line);
/*     */   }
/*     */   
/*     */   public List<LineWC> getLines(RectangleWC r) {
/*  35 */     List<LineWC> result = new ArrayList<LineWC>();
/*     */     
/*  37 */     if (this.lines != null) {
/*  38 */       for (int i = 0; i < this.lines.size(); i++) {
/*  39 */         LineWC line = this.lines.get(i);
/*  40 */         if (line.intersects(r)) {
/*  41 */           result.add(line);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  46 */     if (this.children != null) {
/*  47 */       for (int i = 0; i < this.children.size(); i++) {
/*  48 */         LineNodeList child = this.children.get(i);
/*  49 */         result.addAll(child.getLines(r));
/*     */       } 
/*     */     }
/*  52 */     return result;
/*     */   }
/*     */   
/*     */   public List<LineWC> getAllLines() {
/*  56 */     List<LineWC> result = new ArrayList<LineWC>();
/*     */     
/*  58 */     if (this.lines != null) {
/*  59 */       result.addAll(this.lines);
/*     */     }
/*     */     
/*  62 */     if (this.children != null) {
/*  63 */       for (int i = 0; i < this.children.size(); i++) {
/*  64 */         LineNodeList child = this.children.get(i);
/*  65 */         result.addAll(child.getAllLines());
/*     */       } 
/*     */     }
/*     */     
/*  69 */     return result;
/*     */   }
/*     */   
/*     */   public void addChildNode(LineNodeList lineNode) {
/*  73 */     if (this.children == null) {
/*  74 */       this.children = new ArrayList<LineNodeList>();
/*     */     }
/*     */     
/*  77 */     this.children.add(lineNode);
/*     */   }
/*     */   
/*     */   public int getChildCount() {
/*  81 */     return (this.children == null) ? 0 : this.children.size();
/*     */   }
/*     */   
/*     */   public int getLineCount() {
/*  85 */     return (this.lines == null) ? 0 : this.lines.size();
/*     */   }
/*     */   
/*     */   public int getTotalLineCount() {
/*  89 */     int count = getLineCount();
/*     */     
/*  91 */     if (this.children != null) {
/*  92 */       for (int i = 0; i < this.children.size(); i++) {
/*  93 */         LineNodeList child = this.children.get(i);
/*  94 */         count += child.getTotalLineCount();
/*     */       } 
/*     */     }
/*     */     
/*  98 */     return count;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 152 */     return getClass().getName() + "[o=" + this.origin + ",e=" + this.extent + ",s=" + getSize() + ",c=" + getChildCount() + ",l=" + getLineCount() + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printSize(String prefix) {
/* 161 */     System.out.println(prefix + this);
/*     */     
/* 163 */     if (this.children != null)
/* 164 */       for (LineNodeList child : this.children)
/* 165 */         child.printSize(prefix + " ");  
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\spatial\LineNodeList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */