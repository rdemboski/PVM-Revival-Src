/*    */ package com.funcom.commons;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.jme.math.FastMath;
/*    */ import com.jme.math.Vector2f;
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
/*    */ 
/*    */ public class FastMathUtils
/*    */ {
/*    */   public static Vector2f bezierQuadraticInterpolation(float t, Vector2f returnVector, Vector2f p0, Vector2f p1, Vector2f p2) {
/* 23 */     float firstTerm = FastMath.pow(1.0F - t, 2.0F);
/* 24 */     float secondTerm = 2.0F * (1.0F - t) * t;
/* 25 */     float thirdTerm = FastMath.pow(t, 2.0F);
/* 26 */     returnVector.zero();
/* 27 */     returnVector.set(p0.mult(firstTerm)).addLocal(p1.mult(secondTerm)).addLocal(p2.mult(thirdTerm));
/*    */ 
/*    */ 
/*    */     
/* 31 */     return returnVector;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static Vector2f toVector2f(WorldCoordinate coordinate) {
/* 37 */     return toVector2f(new Vector2f(), coordinate);
/*    */   }
/*    */   
/*    */   public static Vector2f toVector2f(Vector2f returnVector, WorldCoordinate coordinate) {
/* 41 */     returnVector.setX(coordinate.getTileX() + (float)coordinate.getTileOffX());
/* 42 */     returnVector.setY(coordinate.getTileY() + (float)coordinate.getTileOffY());
/* 43 */     return returnVector;
/*    */   }
/*    */   
/*    */   public static WorldCoordinate toWorldCoordinate(Vector2f vec) {
/* 47 */     return toWorldCoordinate(new WorldCoordinate(), vec);
/*    */   }
/*    */   
/*    */   public static WorldCoordinate toWorldCoordinate(WorldCoordinate returnCoord, Vector2f vec) {
/* 51 */     int tileX = (int)FastMath.floor(vec.getX());
/* 52 */     int tileY = (int)FastMath.floor(vec.getY());
/* 53 */     returnCoord.setTileX(tileX);
/* 54 */     returnCoord.setTileY(tileY);
/* 55 */     returnCoord.setTileOffX((vec.getX() - tileX));
/* 56 */     returnCoord.setTileOffY((vec.getY() - tileY));
/* 57 */     return returnCoord;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\FastMathUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */