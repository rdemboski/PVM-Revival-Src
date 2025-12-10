/*    */ package com.funcom.tcg.maps;
/*    */ 
/*    */ import com.funcom.commons.Vector2d;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ 
/*    */ public class IngameMapUtils
/*    */ {
/*    */   public static void convertToWorldMap(IngameMapData mapData, int ingameMapWidth, int ingameMapHeight, Vector2d pos, WorldCoordinate resultBuf) {
/*  9 */     double sourceAngle = mapData.rotationDegrees * Math.PI / 180.0D;
/* 10 */     double cos = Math.cos(-sourceAngle);
/* 11 */     double sin = Math.sin(-sourceAngle);
/* 12 */     WorldCoordinate center = new WorldCoordinate();
/* 13 */     center.set(mapData.extentMin).add(mapData.extentMax).multLocal(0.5D);
/*    */     
/* 15 */     pos.add(-ingameMapWidth / 2.0D, -ingameMapHeight / 2.0D);
/* 16 */     pos.mult(1.0D / ingameMapWidth);
/* 17 */     pos.mult(1.0D / mapData.zoom);
/* 18 */     double worldMapWidth = mapData.extentMax.getX().doubleValue() - mapData.extentMin.getX().doubleValue();
/* 19 */     double x = pos.getX() * worldMapWidth;
/* 20 */     double y = pos.getY() * worldMapWidth;
/*    */     
/* 22 */     y *= mapData.aspect;
/*    */     
/* 24 */     x -= mapData.offsetX;
/* 25 */     y -= mapData.offsetY;
/*    */     
/* 27 */     double localX = x * cos - y * sin;
/* 28 */     double localY = x * sin + y * cos;
/*    */     
/* 30 */     localX += center.getX().doubleValue();
/* 31 */     localY += center.getY().doubleValue();
/*    */     
/* 33 */     resultBuf.set(0, 0, 0.0D, 0.0D);
/* 34 */     resultBuf.addOffset(localX, localY);
/*    */   }
/*    */   
/*    */   public static void convertToIngameMap(IngameMapData mapData, int ingameMapWidth, int ingameMapHeight, WorldCoordinate wc, Vector2d resultBuf) {
/* 38 */     double sourceAngle = mapData.rotationDegrees * Math.PI / 180.0D;
/* 39 */     double cos = Math.cos(sourceAngle);
/* 40 */     double sin = Math.sin(sourceAngle);
/*    */     
/* 42 */     WorldCoordinate center = new WorldCoordinate();
/* 43 */     center.set(mapData.extentMin).add(mapData.extentMax).multLocal(0.5D);
/*    */     
/* 45 */     double localX = wc.getX().doubleValue() - center.getX().doubleValue();
/* 46 */     double localY = wc.getY().doubleValue() - center.getY().doubleValue();
/*    */     
/* 48 */     double x = localX * cos - localY * sin;
/* 49 */     double y = localX * sin + localY * cos;
/*    */     
/* 51 */     x += mapData.offsetX;
/* 52 */     y += mapData.offsetY;
/*    */     
/* 54 */     y /= mapData.aspect;
/*    */     
/* 56 */     double worldMapWidth = mapData.extentMax.getX().doubleValue() - mapData.extentMin.getX().doubleValue();
/* 57 */     x /= worldMapWidth;
/* 58 */     y /= worldMapWidth;
/*    */     
/* 60 */     x *= mapData.zoom;
/* 61 */     y *= mapData.zoom;
/*    */ 
/*    */     
/* 64 */     resultBuf.set(x * ingameMapWidth + ingameMapWidth / 2.0D, y * ingameMapWidth + ingameMapHeight / 2.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\maps\IngameMapUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */