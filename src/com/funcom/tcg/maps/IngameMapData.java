/*    */ package com.funcom.tcg.maps;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IngameMapData
/*    */ {
/*    */   public float rotationDegrees;
/*    */   public float aspect;
/*    */   public float zoom;
/*    */   public int offsetX;
/*    */   public int offsetY;
/* 14 */   public WorldCoordinate extentMin = new WorldCoordinate();
/* 15 */   public WorldCoordinate extentMax = new WorldCoordinate();
/*    */ 
/*    */   
/*    */   public String toString() {
/* 19 */     return "IngameMapData{rotationDegrees=" + this.rotationDegrees + ", aspect=" + this.aspect + ", zoom=" + this.zoom + ", offsetX=" + this.offsetX + ", offsetY=" + this.offsetY + ", extentMin=" + this.extentMin + ", extentMax=" + this.extentMax + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\maps\IngameMapData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */