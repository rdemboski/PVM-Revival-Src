/*    */ package com.funcom.commons.geom;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class LineWCHeight
/*    */   extends LineWC
/*    */ {
/*  9 */   public static final Logger LOGGER = Logger.getLogger(LineWCHeight.class);
/*    */   
/*    */   private double height;
/*    */ 
/*    */   
/*    */   public LineWCHeight() {
/* 15 */     this.height = 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean intersectsLine(LineWC line) {
/* 20 */     if (line instanceof LineWCHeight) {
/* 21 */       return (super.intersectsLine(line) && ((LineWCHeight)line).getHeight() > getHeight());
/*    */     }
/* 23 */     return super.intersectsLine(line);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getHeight() {
/* 29 */     return this.height;
/*    */   }
/*    */   
/*    */   public LineWCHeight(WorldCoordinate startCoord, WorldCoordinate endCoord, double height) {
/* 33 */     super(startCoord, endCoord);
/* 34 */     this.height = height;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\geom\LineWCHeight.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */