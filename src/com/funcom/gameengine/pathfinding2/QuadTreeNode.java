/*     */ package com.funcom.gameengine.pathfinding2;
/*     */ 
/*     */ import com.funcom.commons.geom.RectangleWC;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ public class QuadTreeNode
/*     */ {
/*     */   private static final int CHILDREN_SIZE = 4;
/*     */   private List<QuadTreeNode> children;
/*     */   private String name;
/*     */   private boolean passable;
/*     */   private RectangleWC bounds;
/*     */   
/*     */   public QuadTreeNode() {
/*  19 */     this("UNNAMED_NODE");
/*     */   }
/*     */   
/*     */   public QuadTreeNode(String name) {
/*  23 */     this(name, new RectangleWC(new WorldCoordinate(WorldCoordinate.ZERO), new WorldCoordinate(WorldCoordinate.ZERO)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuadTreeNode(String name, RectangleWC bounds) {
/*  30 */     this.name = name;
/*  31 */     this.bounds = bounds;
/*  32 */     this.children = new ArrayList<QuadTreeNode>(4);
/*     */   }
/*     */   
/*     */   public String getName() {
/*  36 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  40 */     this.name = name;
/*     */   }
/*     */   
/*     */   public void addChild(QuadTreeNode quadTreeNode) {
/*  44 */     if (this.children.size() >= 4)
/*  45 */       throw new IllegalStateException("Quadtree can hold only 4 children"); 
/*  46 */     this.children.add(quadTreeNode);
/*     */   }
/*     */   
/*     */   public void removeChild(QuadTreeNode quadTreeNode) {
/*  50 */     this.children.remove(quadTreeNode);
/*     */   }
/*     */   
/*     */   public void clear() {
/*  54 */     this.children.clear();
/*     */   }
/*     */   
/*     */   public RectangleWC getBounds() {
/*  58 */     return this.bounds;
/*     */   }
/*     */   
/*     */   public void setBounds(RectangleWC bounds) {
/*  62 */     this.bounds = bounds;
/*     */   }
/*     */   
/*     */   public WorldCoordinate getCenter() {
/*  66 */     return this.bounds.getCenter();
/*     */   }
/*     */   
/*     */   public List<QuadTreeNode> getChildren() {
/*  70 */     return Collections.unmodifiableList(this.children);
/*     */   }
/*     */   
/*     */   public void setPassable(boolean passable) {
/*  74 */     this.passable = passable;
/*     */   }
/*     */   
/*     */   public boolean isPassable() {
/*  78 */     return this.passable;
/*     */   }
/*     */   
/*     */   public void subdivideIntoChildren() {
/*  82 */     WorldCoordinate extentHalf = (new WorldCoordinate(this.bounds.getExtent())).multLocal(0.5D);
/*     */     
/*  84 */     createBottomLeftChild(extentHalf);
/*  85 */     createBottomRightChild(extentHalf);
/*  86 */     createTopLeftChild(extentHalf);
/*  87 */     createTopRightChild(extentHalf);
/*     */   }
/*     */   
/*     */   private void createTopRightChild(WorldCoordinate extentHalf) {
/*  91 */     WorldCoordinate topRightOrigin = (new WorldCoordinate(this.bounds.getOrigin())).add(extentHalf);
/*  92 */     WorldCoordinate topRightExtent = (new WorldCoordinate(extentHalf)).multLocal(2.0D);
/*  93 */     addChild(new QuadTreeNode("child:topright", new RectangleWC(topRightOrigin, topRightExtent)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createTopLeftChild(WorldCoordinate extentHalf) {
/* 100 */     WorldCoordinate topLeftOrigin = new WorldCoordinate(this.bounds.getOrigin());
/* 101 */     (topLeftOrigin.getTileCoord()).y += (extentHalf.getTileCoord()).y;
/* 102 */     topLeftOrigin.getTileOffset().add(0.0D, extentHalf.getTileOffset().getY());
/* 103 */     topLeftOrigin.fixOffset();
/*     */     
/* 105 */     WorldCoordinate topLeftExtent = new WorldCoordinate(extentHalf);
/* 106 */     (topLeftExtent.getTileCoord()).y += (extentHalf.getTileCoord()).y;
/* 107 */     topLeftExtent.getTileOffset().add(0.0D, extentHalf.getTileOffset().getY());
/* 108 */     topLeftExtent.fixOffset();
/*     */     
/* 110 */     addChild(new QuadTreeNode("child:topleft", new RectangleWC(topLeftOrigin, topLeftExtent)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createBottomRightChild(WorldCoordinate extentHalf) {
/* 117 */     WorldCoordinate bottomRightOrigin = new WorldCoordinate(this.bounds.getOrigin());
/* 118 */     (bottomRightOrigin.getTileCoord()).x += (extentHalf.getTileCoord()).x;
/* 119 */     bottomRightOrigin.getTileOffset().add(extentHalf.getTileOffset().getX(), 0.0D);
/* 120 */     bottomRightOrigin.fixOffset();
/*     */     
/* 122 */     WorldCoordinate bottomRightExtent = new WorldCoordinate(extentHalf);
/* 123 */     (bottomRightExtent.getTileCoord()).x += (extentHalf.getTileCoord()).x;
/* 124 */     bottomRightExtent.getTileOffset().add(extentHalf.getTileOffset().getX(), 0.0D);
/* 125 */     bottomRightExtent.fixOffset();
/*     */     
/* 127 */     addChild(new QuadTreeNode("child:bottomright", new RectangleWC(bottomRightOrigin, bottomRightExtent)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createBottomLeftChild(WorldCoordinate extentHalf) {
/* 134 */     addChild(new QuadTreeNode("child:bottomleft", new RectangleWC(new WorldCoordinate(this.bounds.getOrigin()), new WorldCoordinate(extentHalf))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 141 */     return "QuadTreeNode{name='" + this.name + '\'' + ", bounds=" + this.bounds + ", passable=" + this.passable + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\QuadTreeNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */