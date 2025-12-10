/*    */ package com.funcom.gameengine.view.water;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.utils.SpatialUtils;
/*    */ import org.jdom.DataConversionException;
/*    */ import org.jdom.Element;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WaterPondCoordinateSet
/*    */ {
/*    */   public WorldCoordinate outerPoint;
/*    */   public WorldCoordinate innerPoint;
/*    */   
/*    */   public WaterPondCoordinateSet(WorldCoordinate outerPoint, WorldCoordinate innerPoint) {
/* 16 */     this.outerPoint = outerPoint;
/* 17 */     this.innerPoint = innerPoint;
/*    */   }
/*    */   
/*    */   public WaterPondCoordinateSet(Element coordSet) throws DataConversionException {
/* 21 */     this(SpatialUtils.getElementWorldCoordinate(coordSet.getChild("outer")), SpatialUtils.getElementWorldCoordinate(coordSet.getChild("inner")));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addTiles(int x, int y) {
/* 28 */     this.outerPoint.addTiles(x, y);
/* 29 */     this.innerPoint.addTiles(x, y);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\water\WaterPondCoordinateSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */