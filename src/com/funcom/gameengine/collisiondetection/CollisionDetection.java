/*     */ package com.funcom.gameengine.collisiondetection;
/*     */ 
/*     */ import com.funcom.commons.MathUtils;
/*     */ import com.funcom.commons.NearestPointWC;
/*     */ import com.funcom.commons.Vector2d;
/*     */ import com.funcom.commons.geom.LineWC;
/*     */ import com.funcom.commons.geom.LineWCHeight;
/*     */ import com.funcom.commons.geom.PolygonWC;
/*     */ import com.funcom.commons.geom.RectangleWC;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.spatial.LineNode;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CollisionDetection
/*     */ {
/*  24 */   private static final CollisionDetection INSTANCE = new CollisionDetection();
/*     */   
/*     */   private static final double PROXIMITY_TRESHOLD = 0.02D;
/*     */   
/*     */   private static final double PROXIMITY_BOUNCE = 0.01D;
/*     */   
/*     */   private static final double LINE_TRESHOLD = 0.001D;
/*     */   
/*     */   private static final double MINIMUM_SPEED = 1.0E-4D;
/*     */   private static final double MINIMUM_NORMALDIFF = 3.1D;
/*     */   
/*     */   public static CollisionDetection instance() {
/*  36 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   public CollisionResult checkCollision(InteractibleProp prop, Vector2d vec, LineNode rootLine, double radius) {
/*  40 */     return checkCollision(prop.getPosition(), radius, vec, rootLine, -1.0F);
/*     */   }
/*     */   
/*     */   public CollisionResult checkCollision(WorldCoordinate propPosition, double radius, Vector2d vec, LineNode rootLine, float collisionHeight) {
/*  44 */     PolygonWC movementRectangle = calculateMovementRectangle(propPosition, radius, vec.length(), vec.angle());
/*  45 */     Set<LineWCHeight> lines = rootLine.getLines(movementRectangle.getBoundsWC());
/*     */     
/*  47 */     if (lines.isEmpty()) {
/*  48 */       return new CollisionResult(CollisionResult.Type.NONE, vec);
/*     */     }
/*     */     
/*  51 */     LineWCHeight movementLine = new LineWCHeight(propPosition, (new WorldCoordinate(propPosition)).addOffset(vec).addOffset(vec.normalizeRet().multRet(radius)), collisionHeight);
/*  52 */     Intersection intersection = getIntersection(movementLine, lines);
/*  53 */     if (intersection.nearestPoint == null) {
/*  54 */       return new CollisionResult(CollisionResult.Type.NONE, vec);
/*     */     }
/*  56 */     Vector2d decompositionVector = decompositionLineVector(propPosition, radius, vec, intersection);
/*     */     
/*  58 */     double angleDiff = MathUtils.getAngleDiff(vec.angle(), MathUtils.getLineAngle(intersection.nearestPoint.getLine()) + 1.5707963267948966D);
/*     */     
/*  60 */     double interpolateAngleDiff = MathUtils.getAngleDiff(vec.angle(), decompositionVector.angle());
/*  61 */     if (Math.abs(interpolateAngleDiff) > 1.3707963267948966D)
/*  62 */       return new CollisionResult(CollisionResult.Type.STOPPED, null); 
/*  63 */     if (decompositionVector.length() < 1.0E-4D || Math.abs(angleDiff) > 3.1D) {
/*  64 */       return new CollisionResult(CollisionResult.Type.STOPPED, null);
/*     */     }
/*     */     
/*  67 */     movementRectangle = calculateMovementRectangle(propPosition, radius, decompositionVector.length(), decompositionVector.angle());
/*  68 */     lines = rootLine.getLines(movementRectangle.getBoundsWC());
/*  69 */     if (lines.isEmpty())
/*  70 */       return new CollisionResult(CollisionResult.Type.BLOCKED, decompositionVector); 
/*  71 */     movementLine.setLine(propPosition, (new WorldCoordinate(propPosition)).addOffset(decompositionVector).addOffset(decompositionVector.normalizeRet().multRet(radius)));
/*  72 */     Intersection nextIntersection = getIntersection(movementLine, lines);
/*  73 */     if (nextIntersection.nearestPoint == null)
/*  74 */       return new CollisionResult(CollisionResult.Type.BLOCKED, decompositionVector); 
/*  75 */     if (intersection.line == nextIntersection.line) {
/*  76 */       return new CollisionResult(CollisionResult.Type.BLOCKED, decompositionVector);
/*     */     }
/*  78 */     decompositionVector.normalize();
/*  79 */     if (intersection.nearestDistance - radius < 1.0E-4D)
/*  80 */       return new CollisionResult(CollisionResult.Type.STOPPED, null); 
/*  81 */     decompositionVector.mult(Math.min(nextIntersection.nearestDistance, intersection.nearestDistance) - radius);
/*     */ 
/*     */     
/*  84 */     WorldCoordinate newPosition = (new WorldCoordinate(propPosition)).addOffset(decompositionVector);
/*  85 */     RectangleWC propMass = new RectangleWC((new WorldCoordinate(newPosition)).addOffset(-radius, -radius), (new WorldCoordinate(newPosition)).addOffset(radius, radius));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     lines = rootLine.getLines(propMass);
/*  91 */     movementLine.setLine(propPosition, (new WorldCoordinate(propPosition)).addOffset(decompositionVector).addOffset(decompositionVector.normalizeRet().multRet(radius)));
/*  92 */     intersection = getIntersection(movementLine, lines);
/*  93 */     if (intersection.nearestPoint != null) {
/*  94 */       return new CollisionResult(CollisionResult.Type.STOPPED, null);
/*     */     }
/*  96 */     for (LineWCHeight lineWCHeight : lines) {
/*  97 */       NearestPointWC linePoint = MathUtils.nearestPointOnSegment(newPosition, (LineWC)lineWCHeight);
/*  98 */       if (linePoint.getPoint().distanceTo(newPosition) < radius) {
/*  99 */         return new CollisionResult(CollisionResult.Type.STOPPED, null);
/*     */       }
/*     */     } 
/*     */     
/* 103 */     return new CollisionResult(CollisionResult.Type.BLOCKED, decompositionVector);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector2d decompositionLineVector(WorldCoordinate propPosition, double radius, Vector2d vec, Intersection intersection) {
/*     */     double decomposeAngle;
/* 118 */     if (intersection.nearestPoint.getType() == 1) {
/* 119 */       decomposeAngle = MathUtils.getLineAngle(intersection.nearestPoint.getLine());
/*     */     } else {
/* 121 */       decomposeAngle = MathUtils.getPointAngle(propPosition, intersection.nearestPoint.getPoint()) + 1.5707963705062866D;
/*     */     } 
/*     */     
/* 124 */     Vector2d newVec = new Vector2d();
/* 125 */     double distnace = intersection.nearestDistance - radius - 0.01D;
/* 126 */     vec.decompose(decomposeAngle, Math.max(distnace, 0.0D), newVec);
/* 127 */     return newVec;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Intersection getIntersection(LineWCHeight movementLine, Set<LineWCHeight> lines) {
/* 139 */     Intersection intersection = new Intersection(null, Double.MAX_VALUE);
/* 140 */     for (LineWCHeight line : lines) {
/* 141 */       if (movementLine.intersectsLine((LineWC)line)) {
/* 142 */         NearestPointWC linePoint = MathUtils.nearestPointOnSegment(movementLine.getWC1(), (LineWC)line);
/* 143 */         double lineDist = linePoint.getPoint().distanceTo(movementLine.getWC1());
/* 144 */         if (lineDist < intersection.nearestDistance) {
/* 145 */           intersection.nearestDistance = lineDist;
/* 146 */           intersection.nearestPoint = linePoint;
/* 147 */           intersection.line = (LineWC)line;
/*     */         } 
/*     */       } 
/*     */     } 
/* 151 */     return intersection;
/*     */   }
/*     */   
/*     */   private PolygonWC calculateMovementRectangle(WorldCoordinate propPosition, double radius, double vecDist, double vecAngle) {
/* 155 */     double farOffsetX = Math.cos(vecAngle) * (vecDist + radius + 0.02D);
/* 156 */     double farOffsetY = Math.sin(vecAngle) * (vecDist + radius + 0.02D);
/* 157 */     WorldCoordinate nearLeft = (new WorldCoordinate(propPosition)).addOffset(Math.cos(vecAngle - 1.5707963267948966D) * (radius + 0.02D), Math.sin(vecAngle - 1.5707963267948966D) * (radius + 0.02D));
/*     */ 
/*     */     
/* 160 */     WorldCoordinate nearRight = (new WorldCoordinate(propPosition)).addOffset(Math.cos(vecAngle + 1.5707963267948966D) * (radius + 0.02D), Math.sin(vecAngle + 1.5707963267948966D) * (radius + 0.02D));
/*     */ 
/*     */     
/* 163 */     WorldCoordinate farLeft = (new WorldCoordinate(nearLeft)).addOffset(farOffsetX, farOffsetY);
/* 164 */     WorldCoordinate farRight = (new WorldCoordinate(nearRight)).addOffset(farOffsetX, farOffsetY);
/*     */     
/* 166 */     return new PolygonWC(new WorldCoordinate[] { nearRight, nearLeft, farLeft, farRight });
/*     */   }
/*     */   
/*     */   private Rectangle2D getLineBounds(Line2D line) {
/* 170 */     Rectangle2D rect = line.getBounds2D();
/* 171 */     if (rect.getWidth() < 0.001D || rect.getHeight() < 0.001D) {
/* 172 */       rect.setRect(rect.getX(), rect.getY(), Math.max(0.001D, rect.getWidth()), Math.max(0.001D, rect.getHeight()));
/*     */     }
/*     */     
/* 175 */     return rect;
/*     */   }
/*     */   
/*     */   public CollisionResult checkCollision(WorldCoordinate worldCoordinate, double radius, Vector2d vec, LineNode rootLine) {
/* 179 */     return checkCollision(worldCoordinate, radius, vec, rootLine, -1.0F);
/*     */   }
/*     */   
/*     */   private static class Intersection
/*     */   {
/*     */     public NearestPointWC nearestPoint;
/*     */     public double nearestDistance;
/*     */     public LineWC line;
/*     */     
/*     */     private Intersection() {}
/*     */     
/*     */     private Intersection(NearestPointWC nearestPoint, double nearestDistance) {
/* 191 */       this.nearestPoint = nearestPoint;
/* 192 */       this.nearestDistance = nearestDistance;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\collisiondetection\CollisionDetection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */