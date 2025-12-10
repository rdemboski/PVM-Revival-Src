/*    */ package com.funcom.gameengine;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldOrigin
/*    */ {
/* 13 */   private static WorldOrigin INSTANCE = new WorldOrigin();
/*    */ 
/*    */   
/*    */   private Set<OriginListener> listeners;
/*    */ 
/*    */   
/* 19 */   private int x = 0;
/* 20 */   private int y = 0;
/*    */ 
/*    */   
/*    */   public static WorldOrigin instance() {
/* 24 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public void addListener(OriginListener listener) {
/* 28 */     if (this.listeners == null) {
/* 29 */       this.listeners = new HashSet<OriginListener>();
/*    */     }
/*    */     
/* 32 */     this.listeners.add(listener);
/*    */   }
/*    */   
/*    */   public void removeListener(OriginListener listener) {
/* 36 */     if (this.listeners == null) {
/*    */       return;
/*    */     }
/*    */     
/* 40 */     this.listeners.remove(listener);
/*    */   }
/*    */   
/*    */   public void fireOriginListener(int oldX, int oldY, int newX, int newY) {
/* 44 */     if (this.listeners == null) {
/*    */       return;
/*    */     }
/*    */     
/* 48 */     for (OriginListener listener : this.listeners) {
/* 49 */       listener.originMoved(oldX, oldY, newX, newY);
/*    */     }
/*    */   }
/*    */   
/*    */   public void setX(int x) {
/* 54 */     set(x, this.y);
/*    */   }
/*    */   
/*    */   public int getX() {
/* 58 */     return this.x;
/*    */   }
/*    */   
/*    */   public void setY(int y) {
/* 62 */     set(this.x, y);
/*    */   }
/*    */   
/*    */   public int getY() {
/* 66 */     return this.y;
/*    */   }
/*    */   
/*    */   public void set(int x, int y) {
/* 70 */     if (x != this.x || y != this.y) {
/* 71 */       int oldX = this.x;
/* 72 */       int oldY = this.y;
/* 73 */       this.x = x;
/* 74 */       this.y = y;
/* 75 */       fireOriginListener(oldX, oldY, this.x, this.y);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void viewToLogicCoordX(float viewX, WorldCoordinate logicCoord) {
/* 80 */     logicCoord.setX(this.x, 0.0D);
/* 81 */     logicCoord.addOffset(viewX, 0.0D);
/*    */   }
/*    */   
/*    */   public void viewToLogicCoordZ(float viewZ, WorldCoordinate logicCoord) {
/* 85 */     logicCoord.setY(this.y, 0.0D);
/* 86 */     logicCoord.addOffset(0.0D, viewZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 91 */     return "WorldOrigin{x=" + this.x + ", y=" + this.y + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\gameengine\WorldOrigin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */