/*     */ package com.funcom.tcg.rpg;
/*     */ 
/*     */ import com.funcom.commons.geom.RectangleWC;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.rpgengine2.TargetProviderMap;
/*     */ import com.funcom.rpgengine2.abilities.TargetProvider;
/*     */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.creatures.RpgObject;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.util.List;
/*     */ 
/*     */ public class TCGShapeDataEvaluator
/*     */   implements ShapeDataEvaluator
/*     */ {
/*  17 */   public static final TCGShapeDataEvaluator INSTANCE = new TCGShapeDataEvaluator();
/*     */   
/*     */   public double evalAngleLocal(double sourceAngle, RpgEntity source, RpgEntity target, double offsetX, double offsetY, UsageParams usageParams) {
/*  20 */     WorldCoordinate offsetCoord = new WorldCoordinate(0, 0, offsetX, offsetY, "UNIDENTIFIED_MAP", 0);
/*  21 */     offsetCoord.rotate(sourceAngle);
/*     */     
/*  23 */     WorldCoordinate targetPos = getPos(target);
/*  24 */     WorldCoordinate sourcePos = usageParams.getSourcePositionOverride(getPos(source));
/*  25 */     WorldCoordinate sourceWithOffset = (new WorldCoordinate(sourcePos)).add(offsetCoord);
/*     */     
/*  27 */     double dX = (targetPos.getTileCoord()).x + targetPos.getTileOffset().getX() - (sourceWithOffset.getTileCoord()).x - sourceWithOffset.getTileOffset().getX();
/*     */     
/*  29 */     double dY = (targetPos.getTileCoord()).y + targetPos.getTileOffset().getY() - (sourceWithOffset.getTileCoord()).y - sourceWithOffset.getTileOffset().getY();
/*     */ 
/*     */     
/*  32 */     double rawAngle = Math.atan2(dY, dX);
/*     */     
/*  34 */     return rawAngle - sourceAngle;
/*     */   }
/*     */   
/*     */   public double evalDistance(RpgEntity source, RpgEntity target, double offsetX, double offsetY, UsageParams usageParams) {
/*  38 */     TCGRpgObject src = (TCGRpgObject)source;
/*  39 */     TCGRpgObject trg = (TCGRpgObject)target;
/*  40 */     float sourceAngle = src.getAngle();
/*  41 */     WorldCoordinate targetPos = trg.getPosition();
/*  42 */     WorldCoordinate srcPos = usageParams.getSourcePositionOverride(src.getPosition());
/*     */     
/*  44 */     return evalDistance(sourceAngle, srcPos, targetPos, offsetX, offsetY);
/*     */   }
/*     */   
/*     */   private double evalDistance(double sourceAngle, WorldCoordinate sourcePos, WorldCoordinate targetPos, double offsetX, double offsetY) {
/*  48 */     WorldCoordinate offsetCoord = new WorldCoordinate(0, 0, offsetX, offsetY, "UNIDENTIFIED_MAP", 0);
/*  49 */     offsetCoord.rotate(sourceAngle);
/*     */     
/*  51 */     WorldCoordinate srcWithOffset = (new WorldCoordinate(sourcePos)).add(offsetCoord);
/*     */     
/*  53 */     double dX = (targetPos.getTileCoord()).x + targetPos.getTileOffset().getX() - (srcWithOffset.getTileCoord()).x - srcWithOffset.getTileOffset().getX();
/*     */     
/*  55 */     double dY = (targetPos.getTileCoord()).y + targetPos.getTileOffset().getY() - (srcWithOffset.getTileCoord()).y - srcWithOffset.getTileOffset().getY();
/*     */ 
/*     */     
/*  58 */     return Math.sqrt(dX * dX + dY * dY);
/*     */   }
/*     */   
/*     */   public float getAngle(RpgEntity source) {
/*  62 */     TCGRpgObject src = (TCGRpgObject)source;
/*     */     
/*  64 */     return src.getAngle();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getRadius(RpgEntity source) {
/*  69 */     TCGRpgObject src = (TCGRpgObject)source;
/*  70 */     return src.getRadius();
/*     */   }
/*     */   
/*     */   public WorldCoordinate getPos(RpgEntity source) {
/*  74 */     TCGRpgObject src = (TCGRpgObject)source;
/*  75 */     return src.getPosition();
/*     */   }
/*     */   
/*     */   public Object getId(RpgEntity source) {
/*  79 */     TCGRpgObject src = (TCGRpgObject)source;
/*     */     
/*  81 */     return src.getObjectId();
/*     */   }
/*     */   
/*     */   public List<? extends TargetProvider> findTargets(RpgObject source, Rectangle2D boundingRect) {
/*  85 */     TCGRpgObject src = (TCGRpgObject)source;
/*  86 */     RectangleWC rect = new RectangleWC();
/*     */     
/*  88 */     rect.getOrigin().set(src.getPosition()).addOffset(boundingRect.getX(), boundingRect.getY());
/*     */ 
/*     */ 
/*     */     
/*  92 */     rect.getExtent().set(rect.getOrigin()).addOffset(boundingRect.getWidth(), boundingRect.getHeight());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     TargetProviderMap map = src.getCurrentMap();
/*  98 */     return map.findObjects(rect);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(RpgEntity source, RpgEntity target, Rectangle2D boundingRect, UsageParams usageParams) {
/* 103 */     TCGRpgObject src = (TCGRpgObject)source;
/* 104 */     RectangleWC rect = new RectangleWC();
/*     */     
/* 106 */     WorldCoordinate srcPosition = usageParams.getSourcePositionOverride(src.getPosition());
/* 107 */     rect.getOrigin().set(srcPosition).addOffset(boundingRect.getX(), boundingRect.getY());
/*     */ 
/*     */ 
/*     */     
/* 111 */     rect.getExtent().set(rect.getOrigin()).addOffset(boundingRect.getWidth(), boundingRect.getHeight());
/*     */ 
/*     */ 
/*     */     
/* 115 */     TCGRpgObject trg = (TCGRpgObject)target;
/*     */     
/* 117 */     WorldCoordinate worldCoordinate = new WorldCoordinate(0, 0, evalDistance(src.getAngle(), srcPosition, trg.getPosition(), 0.0D, 0.0D), 0.0D, "", -1);
/* 118 */     double angle = -1.0D * evalAngleLocal(src.getAngle(), source, target, 0.0D, 0.0D, usageParams);
/*     */     
/* 120 */     worldCoordinate.rotate(angle);
/* 121 */     worldCoordinate.add(srcPosition);
/*     */     
/* 123 */     return rect.contains(worldCoordinate);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\TCGShapeDataEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */