/*    */ package com.funcom.commons;
/*    */ 
/*    */ import com.funcom.commons.geom.LineWC;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ 
/*    */ 
/*    */ public class NearestPointWC
/*    */ {
/*    */   public static final int ENDPOINT = 0;
/*    */   public static final int LINE = 1;
/*    */   private WorldCoordinate point;
/*    */   private LineWC line;
/*    */   private int type;
/*    */   
/*    */   public NearestPointWC(WorldCoordinate point, LineWC line, int type) {
/* 16 */     this.point = point;
/* 17 */     this.line = line;
/* 18 */     this.type = type;
/*    */   }
/*    */   
/*    */   public WorldCoordinate getPoint() {
/* 22 */     return this.point;
/*    */   }
/*    */   
/*    */   public LineWC getLine() {
/* 26 */     return this.line;
/*    */   }
/*    */   
/*    */   public int getType() {
/* 30 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\NearestPointWC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */