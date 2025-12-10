/*    */ package com.funcom.gameengine.view.water;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.utils.SpatialUtils;
/*    */ import java.util.Arrays;
/*    */ import org.jdom.DataConversionException;
/*    */ import org.jdom.Element;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WaterLineCoordinateSet
/*    */ {
/*    */   public WorldCoordinate shoreLineMinimum;
/*    */   public WorldCoordinate shoreLineMaximum;
/*    */   public WorldCoordinate oceanLineMaximum;
/*    */   public WorldCoordinate oceanLineMinimum;
/*    */   public float distanceToPrevious;
/*    */   public float[] distanceToPoint;
/*    */   
/*    */   public WaterLineCoordinateSet(WorldCoordinate shoreLineMinimum, WorldCoordinate shoreLineMaximum, WorldCoordinate oceanLineMaximum, WorldCoordinate oceanLineMinimum) {
/* 22 */     this.shoreLineMinimum = shoreLineMinimum;
/* 23 */     this.shoreLineMaximum = shoreLineMaximum;
/* 24 */     this.oceanLineMaximum = oceanLineMaximum;
/* 25 */     this.oceanLineMinimum = oceanLineMinimum;
/* 26 */     this.distanceToPrevious = 0.0F;
/* 27 */     this.distanceToPoint = new float[4];
/* 28 */     Arrays.fill(this.distanceToPoint, 0.0F);
/*    */   }
/*    */   
/*    */   public WaterLineCoordinateSet(Element coordSet) throws DataConversionException {
/* 32 */     this(SpatialUtils.getElementWorldCoordinate(coordSet.getChild("shoreline-min")), SpatialUtils.getElementWorldCoordinate(coordSet.getChild("shoreline-max")), SpatialUtils.getElementWorldCoordinate(coordSet.getChild("oceanline-max")), SpatialUtils.getElementWorldCoordinate(coordSet.getChild("oceanline-min")));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addTiles(int x, int y) {
/* 41 */     this.shoreLineMinimum.addTiles(x, y);
/* 42 */     this.shoreLineMaximum.addTiles(x, y);
/* 43 */     this.oceanLineMaximum.addTiles(x, y);
/* 44 */     this.oceanLineMinimum.addTiles(x, y);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\water\WaterLineCoordinateSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */