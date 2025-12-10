/*    */ package com.funcom.rpgengine2.combat;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UsageParams
/*    */ {
/* 12 */   public static final UsageParams EMPTY_PARAMS = new UsageParams(0.0D, null, AngleType.SOURCE, 0.0F);
/*    */   
/*    */   private final double distance;
/*    */   private final WorldCoordinate sourcePositionOverride;
/*    */   private final AngleType angleType;
/*    */   private final float definedAngle;
/*    */   
/*    */   public UsageParams(double distance, WorldCoordinate sourcePositionOverride, AngleType angleType, float definedAngle) {
/* 20 */     this.distance = distance;
/* 21 */     this.sourcePositionOverride = sourcePositionOverride;
/* 22 */     this.angleType = angleType;
/* 23 */     this.definedAngle = definedAngle;
/*    */   }
/*    */   
/*    */   public double getDistance() {
/* 27 */     return this.distance;
/*    */   }
/*    */   
/*    */   public AngleType getAngleType() {
/* 31 */     return this.angleType;
/*    */   }
/*    */   
/*    */   public float getDefinedAngle() {
/* 35 */     return this.definedAngle;
/*    */   }
/*    */   
/*    */   public WorldCoordinate getSourcePositionOverride(WorldCoordinate defaultPos) {
/* 39 */     if (this.sourcePositionOverride != null) {
/* 40 */       return this.sourcePositionOverride;
/*    */     }
/* 42 */     return defaultPos;
/*    */   }
/*    */   
/*    */   public enum AngleType {
/* 46 */     DEFINED, SOURCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\combat\UsageParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */