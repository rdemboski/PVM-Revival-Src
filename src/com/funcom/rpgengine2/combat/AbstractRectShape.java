/*    */ package com.funcom.rpgengine2.combat;
/*    */ 
/*    */ import com.funcom.commons.Vector2d;
/*    */ import com.funcom.commons.geom.RectangleWC;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.rpgengine2.RPGFactionFilterType;
/*    */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*    */ import com.funcom.rpgengine2.creatures.TargetValidationSupport;
/*    */ import com.funcom.rpgengine2.items.Item;
/*    */ import java.awt.geom.Rectangle2D;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractRectShape
/*    */   implements Shape
/*    */ {
/*    */   protected String id;
/*    */   protected String name;
/*    */   protected double width;
/*    */   protected double height;
/*    */   protected double offsetX;
/*    */   protected double offsetY;
/*    */   private RPGFactionFilterType rpgFactionFilterType;
/*    */   protected WorldCoordinate upperLeftCorner;
/*    */   protected WorldCoordinate lowerRightCorner;
/*    */   
/*    */   public void init(String id, String name, double width, double height, double offsetX, double offsetY, RPGFactionFilterType rpgFactionFilterType) {
/* 29 */     this.id = id;
/* 30 */     this.name = name;
/* 31 */     this.width = width;
/* 32 */     this.height = height;
/* 33 */     this.offsetX = offsetX;
/* 34 */     this.offsetY = offsetY;
/* 35 */     this.rpgFactionFilterType = rpgFactionFilterType;
/*    */     
/* 37 */     double maxLength = Math.max(width, height);
/* 38 */     double maxOffset = Math.max(offsetX, offsetY);
/*    */     
/* 40 */     this.upperLeftCorner = new WorldCoordinate();
/* 41 */     this.upperLeftCorner.addOffset(-maxLength, -maxLength);
/* 42 */     this.upperLeftCorner.addOffset(-maxOffset, -maxOffset);
/* 43 */     this.lowerRightCorner = new WorldCoordinate();
/* 44 */     this.lowerRightCorner.addOffset(maxLength, maxLength);
/* 45 */     this.lowerRightCorner.addOffset(maxOffset, maxOffset);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 50 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 55 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public void extendBounds(RectangleWC bounds) {
/* 60 */     bounds.add(this.upperLeftCorner);
/* 61 */     bounds.add(this.lowerRightCorner);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getRange() {
/* 66 */     return this.width;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isMelee() {
/* 71 */     return false;
/*    */   }
/*    */   
/*    */   public Vector2d[] getLocalCorners(double angle) {
/* 75 */     Vector2d[] corners = { new Vector2d(this.offsetX, -this.offsetY - this.height / 2.0D), new Vector2d(this.offsetX + this.width, -this.offsetY - this.height / 2.0D), new Vector2d(this.offsetX + this.width, -this.offsetY + this.height / 2.0D), new Vector2d(this.offsetX, -this.offsetY + this.height / 2.0D) };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 82 */     for (Vector2d corner : corners) {
/* 83 */       corner.rotate(angle);
/*    */     }
/*    */     
/* 86 */     return corners;
/*    */   }
/*    */   
/*    */   public boolean isValidTarget(RpgEntity source, RpgEntity target) {
/* 90 */     TargetValidationSupport validationSupport = (TargetValidationSupport)source.getSupport(TargetValidationSupport.class);
/* 91 */     return (validationSupport != null && validationSupport.isValidTarget(target, this.rpgFactionFilterType));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canHit(Item item, ShapeDataEvaluator evaluator, RpgEntity source, RpgEntity target, UsageParams usageParams) {
/* 96 */     Rectangle2D rect = new Rectangle2D.Double(this.offsetX, -this.height / 2.0D + this.offsetY, this.width, this.height);
/* 97 */     return evaluator.contains(source, target, rect, usageParams);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\combat\AbstractRectShape.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */