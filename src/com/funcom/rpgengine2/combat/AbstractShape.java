/*     */ package com.funcom.rpgengine2.combat;
/*     */ 
/*     */ import com.funcom.commons.geom.RectangleWC;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.rpgengine2.RPGFactionFilterType;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.creatures.TargetValidationSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractShape
/*     */   implements Shape
/*     */ {
/*     */   private static final double TWO_PI = 6.283185307179586D;
/*     */   protected String id;
/*     */   protected String name;
/*     */   protected double offsetX;
/*     */   protected double offsetY;
/*     */   protected double angleStartDegrees;
/*     */   protected double angleEndDegrees;
/*     */   protected double distance;
/*     */   private boolean isMelee;
/*     */   private RPGFactionFilterType rpgFactionFilterType;
/*     */   protected double angleStart;
/*     */   protected double angleEnd;
/*     */   protected WorldCoordinate upperLeftCorner;
/*     */   protected WorldCoordinate lowerRightCorner;
/*     */   
/*     */   public void init(String shapeId, String shapeName, float offsetX, float offsetY, float angleStartDegrees, float angleEndDegrees, float distance, boolean isMelee, RPGFactionFilterType rpgFactionFilterType) {
/*  31 */     this.id = shapeId;
/*  32 */     this.name = shapeName;
/*  33 */     this.offsetX = offsetX;
/*  34 */     this.offsetY = offsetY;
/*  35 */     this.angleStartDegrees = angleStartDegrees;
/*  36 */     this.angleEndDegrees = angleEndDegrees;
/*  37 */     this.distance = distance;
/*  38 */     this.isMelee = isMelee;
/*  39 */     this.rpgFactionFilterType = rpgFactionFilterType;
/*     */     
/*  41 */     this.angleStart = (float)(Math.PI * angleStartDegrees / 180.0D);
/*  42 */     this.angleEnd = (float)(Math.PI * angleEndDegrees / 180.0D);
/*     */     
/*  44 */     this.angleStart = fixAngle(this.angleStart);
/*  45 */     this.angleEnd = fixAngle(this.angleEnd);
/*     */     
/*  47 */     float cornerDistance = Math.abs(distance) + 4.0F;
/*  48 */     float cornerOffset = Math.max(Math.abs(offsetX), Math.abs(offsetY));
/*     */     
/*  50 */     this.upperLeftCorner = new WorldCoordinate();
/*  51 */     this.upperLeftCorner.addOffset(-cornerDistance, -cornerDistance);
/*  52 */     this.upperLeftCorner.addOffset(-cornerOffset, -cornerOffset);
/*  53 */     this.lowerRightCorner = new WorldCoordinate();
/*  54 */     this.lowerRightCorner.addOffset(cornerDistance, cornerDistance);
/*  55 */     this.lowerRightCorner.addOffset(cornerOffset, cornerOffset);
/*     */   }
/*     */   
/*     */   protected static double fixAngle(double angle) {
/*  59 */     if (angle >= 6.283185307179586D) {
/*  60 */       angle %= 6.283185307179586D;
/*     */     } else {
/*  62 */       while (angle < 0.0D) {
/*  63 */         angle += 6.283185307179586D;
/*     */       }
/*     */     } 
/*  66 */     return angle;
/*     */   }
/*     */   
/*     */   public String getId() {
/*  70 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  74 */     return this.name;
/*     */   }
/*     */   
/*     */   public double getOffsetX() {
/*  78 */     return this.offsetX;
/*     */   }
/*     */   
/*     */   public double getOffsetY() {
/*  82 */     return this.offsetY;
/*     */   }
/*     */   
/*     */   public double getAngleStartDegrees() {
/*  86 */     return this.angleStartDegrees;
/*     */   }
/*     */   
/*     */   public double getAngleEndDegrees() {
/*  90 */     return this.angleEndDegrees;
/*     */   }
/*     */   
/*     */   public double getDistance() {
/*  94 */     return this.distance;
/*     */   }
/*     */   
/*     */   public boolean isValidTarget(RpgEntity source, RpgEntity target) {
/*  98 */     TargetValidationSupport validationSupport = (TargetValidationSupport)source.getSupport(TargetValidationSupport.class);
/*  99 */     return (validationSupport != null && validationSupport.isValidTarget(target, this.rpgFactionFilterType));
/*     */   }
/*     */   
/*     */   protected static boolean isWithinArc(double startAngle, double endAngle, double angleToCheck) {
/* 103 */     startAngle = fixAngle(startAngle);
/* 104 */     endAngle = fixAngle(endAngle);
/* 105 */     angleToCheck = fixAngle(angleToCheck);
/*     */     
/* 107 */     if (startAngle < endAngle)
/*     */     {
/* 109 */       return (angleToCheck >= startAngle && angleToCheck < endAngle);
/*     */     }
/*     */ 
/*     */     
/* 113 */     return (angleToCheck >= startAngle || angleToCheck < endAngle);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void extendBounds(RectangleWC bounds) {
/* 119 */     bounds.add(this.upperLeftCorner);
/* 120 */     bounds.add(this.lowerRightCorner);
/*     */   }
/*     */   
/*     */   public double getRange() {
/* 124 */     return this.distance;
/*     */   }
/*     */   public boolean isMelee() {
/* 127 */     return this.isMelee;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\combat\AbstractShape.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */