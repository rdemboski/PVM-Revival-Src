/*    */ package com.funcom.gameengine;
/*    */ 
/*    */ import java.awt.geom.Point2D;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldUtils
/*    */ {
/*    */   public static float getScreenX(WorldCoordinate worldCoordinate, int offset) {
/* 20 */     return (float)(((worldCoordinate.getTileCoord()).x - offset) + worldCoordinate.getTileOffset().getX());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static float getScreenY(WorldCoordinate worldCoordinate, int offset) {
/* 32 */     return (float)(((worldCoordinate.getTileCoord()).y - offset) + worldCoordinate.getTileOffset().getY());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static float getScreenX(WorldCoordinate worldCoordinate) {
/* 43 */     return getScreenX(worldCoordinate, WorldOrigin.instance().getX());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static float getScreenY(WorldCoordinate worldCoordinate) {
/* 53 */     return getScreenY(worldCoordinate, WorldOrigin.instance().getY());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Point2D getScreenPoint(WorldCoordinate worldCoordinate) {
/* 63 */     return new Point2D.Double(getScreenX(worldCoordinate), getScreenY(worldCoordinate));
/*    */   }
/*    */   
/*    */   public static int compareToHorizontal(WorldCoordinate wc1, WorldCoordinate wc2) {
/* 67 */     if ((wc1.getTileCoord()).x < (wc2.getTileCoord()).x || ((wc1.getTileCoord()).x == (wc2.getTileCoord()).x && wc1.getTileOffset().getX() < wc2.getTileOffset().getX()))
/*    */     {
/*    */       
/* 70 */       return -1;
/*    */     }
/*    */     
/* 73 */     if ((wc1.getTileCoord()).x > (wc2.getTileCoord()).x || ((wc1.getTileCoord()).x == (wc2.getTileCoord()).x && wc1.getTileOffset().getX() > wc2.getTileOffset().getX()))
/*    */     {
/*    */       
/* 76 */       return 1;
/*    */     }
/*    */     
/* 79 */     return 0;
/*    */   }
/*    */   
/*    */   public static int compareToVertical(WorldCoordinate wc1, WorldCoordinate wc2) {
/* 83 */     if ((wc1.getTileCoord()).y < (wc2.getTileCoord()).y || ((wc1.getTileCoord()).y == (wc2.getTileCoord()).y && wc1.getTileOffset().getY() < wc2.getTileOffset().getY()))
/*    */     {
/*    */       
/* 86 */       return -1;
/*    */     }
/*    */     
/* 89 */     if ((wc1.getTileCoord()).y > (wc2.getTileCoord()).y || ((wc1.getTileCoord()).y == (wc2.getTileCoord()).y && wc1.getTileOffset().getY() > wc2.getTileOffset().getY()))
/*    */     {
/*    */       
/* 92 */       return 1;
/*    */     }
/*    */     
/* 95 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\gameengine\WorldUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */