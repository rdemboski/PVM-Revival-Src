/*     */ package com.funcom.rpgengine2.abilities;
/*     */ 
/*     */ import com.funcom.commons.Vector2d;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TriggerShape
/*     */ {
/*     */   private final float width;
/*     */   private final float height;
/*     */   private final float movementDistance;
/*     */   private final int movementTimeMillis;
/*     */   private final float offsetX;
/*     */   private final float offsetY;
/*     */   private final double movementAngle;
/*     */   private final float offsetDistance;
/*     */   private float collisionHeight;
/*     */   
/*     */   public TriggerShape(float width, float height, float movementDistance, int movementTimeMillis, double movementAngle, float offsetX, float offsetY, float offsetDistance, float collisionHeight) {
/*  22 */     this.width = width;
/*  23 */     this.height = height;
/*  24 */     this.movementDistance = movementDistance;
/*  25 */     this.movementTimeMillis = movementTimeMillis;
/*  26 */     this.offsetX = offsetX;
/*  27 */     this.offsetY = offsetY;
/*  28 */     this.movementAngle = movementAngle;
/*  29 */     this.offsetDistance = offsetDistance;
/*  30 */     this.collisionHeight = collisionHeight;
/*     */   }
/*     */   
/*     */   public double getMovementAngle() {
/*  34 */     return this.movementAngle;
/*     */   }
/*     */   
/*     */   public int getMovementTimeMillis() {
/*  38 */     return this.movementTimeMillis;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(float targetX, float targetY, float targetRadius, float sourceAngle, float fromTimeMillis, float toTimeMillis) {
/*  44 */     BaseCollisionRect collisionRect = new BaseCollisionRect(this, fromTimeMillis, toTimeMillis, this.offsetDistance);
/*     */ 
/*     */     
/*  47 */     Vector2d targetPos = new Vector2d(targetX, targetY);
/*  48 */     targetPos.rotate(-sourceAngle - this.movementAngle);
/*  49 */     targetPos.sub(this.offsetX, this.offsetY);
/*     */ 
/*     */     
/*  52 */     return collisionRect.contains(targetPos, targetRadius);
/*     */   }
/*     */   
/*     */   private float limitTime(float millis) {
/*  56 */     if (millis > this.movementTimeMillis) {
/*  57 */       millis = this.movementTimeMillis;
/*  58 */     } else if (millis < 0.0F) {
/*  59 */       millis = 0.0F;
/*     */     } 
/*     */     
/*  62 */     return millis;
/*     */   }
/*     */   
/*     */   public Vector2d[] getPositionedCorners(float sourceAngle, float fromTimeMillis, float toTimeMillis) {
/*  66 */     return _getCorners(sourceAngle, this.offsetDistance, fromTimeMillis, toTimeMillis);
/*     */   }
/*     */   
/*     */   private Vector2d[] _getCorners(float sourceAngle, float offsetDistance, float fromTimeMillis, float toTimeMillis) {
/*  70 */     BaseCollisionRect collisionRect = new BaseCollisionRect(this, fromTimeMillis, toTimeMillis, offsetDistance);
/*     */     
/*  72 */     Vector2d[] corners = { new Vector2d(collisionRect.x, collisionRect.y), new Vector2d(collisionRect.xx, collisionRect.y), new Vector2d(collisionRect.xx, collisionRect.yy), new Vector2d(collisionRect.x, collisionRect.yy) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     for (Vector2d corner : corners) {
/*  80 */       corner.add(this.offsetX, this.offsetY);
/*  81 */       corner.rotate(this.movementAngle + sourceAngle);
/*     */     } 
/*     */     
/*  84 */     return corners;
/*     */   }
/*     */   
/*     */   public Vector2d getPos(float sourceAngle, float atTimeMillis) {
/*  88 */     BaseCollisionRect collisionRect = new BaseCollisionRect(this, atTimeMillis, atTimeMillis, this.offsetDistance);
/*     */ 
/*     */ 
/*     */     
/*  92 */     Vector2d p = new Vector2d(collisionRect.travelFrom, 0.0D);
/*  93 */     p.add(this.offsetX, this.offsetY);
/*  94 */     p.rotate(this.movementAngle + sourceAngle);
/*     */     
/*  96 */     return p;
/*     */   }
/*     */   
/*     */   public Rectangle2D getPositionedBoundingRect(float sourceAngle, float fromTimeMillis, float toTimeMillis) {
/* 100 */     Vector2d[] corners = getPositionedCorners(sourceAngle, fromTimeMillis, toTimeMillis);
/*     */     
/* 102 */     double minX = Double.MAX_VALUE;
/* 103 */     double minY = Double.MAX_VALUE;
/* 104 */     double maxX = -1.7976931348623157E308D;
/* 105 */     double maxY = -1.7976931348623157E308D;
/*     */     
/* 107 */     for (Vector2d corner : corners) {
/* 108 */       if (corner.getX() < minX) {
/* 109 */         minX = corner.getX();
/*     */       }
/* 111 */       if (corner.getX() > maxX) {
/* 112 */         maxX = corner.getX();
/*     */       }
/* 114 */       if (corner.getY() < minY) {
/* 115 */         minY = corner.getY();
/*     */       }
/* 117 */       if (corner.getY() > maxY) {
/* 118 */         maxY = corner.getY();
/*     */       }
/*     */     } 
/*     */     
/* 122 */     return new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle2D getBoundingRect(float sourceAngle, float tunnelingMillis) {
/* 133 */     Vector2d[] corners = _getCorners(sourceAngle, 0.0F, 0.0F, tunnelingMillis);
/*     */     
/* 135 */     double minX = Double.MAX_VALUE;
/* 136 */     double minY = Double.MAX_VALUE;
/* 137 */     double maxX = -1.7976931348623157E308D;
/* 138 */     double maxY = -1.7976931348623157E308D;
/*     */     
/* 140 */     for (Vector2d corner : corners) {
/* 141 */       if (corner.getX() < minX) {
/* 142 */         minX = corner.getX();
/*     */       }
/* 144 */       if (corner.getX() > maxX) {
/* 145 */         maxX = corner.getX();
/*     */       }
/* 147 */       if (corner.getY() < minY) {
/* 148 */         minY = corner.getY();
/*     */       }
/* 150 */       if (corner.getY() > maxY) {
/* 151 */         maxY = corner.getY();
/*     */       }
/*     */     } 
/*     */     
/* 155 */     return new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 159 */     if (this == o) {
/* 160 */       return true;
/*     */     }
/* 162 */     if (!(o instanceof TriggerShape)) {
/* 163 */       return false;
/*     */     }
/*     */     
/* 166 */     TriggerShape that = (TriggerShape)o;
/*     */     
/* 168 */     if (Float.compare(that.height, this.height) != 0) {
/* 169 */       return false;
/*     */     }
/* 171 */     if (Double.compare(that.movementAngle, this.movementAngle) != 0) {
/* 172 */       return false;
/*     */     }
/* 174 */     if (Float.compare(that.movementDistance, this.movementDistance) != 0) {
/* 175 */       return false;
/*     */     }
/* 177 */     if (Float.compare(that.movementTimeMillis, this.movementTimeMillis) != 0) {
/* 178 */       return false;
/*     */     }
/* 180 */     if (Float.compare(that.offsetDistance, this.offsetDistance) != 0) {
/* 181 */       return false;
/*     */     }
/* 183 */     if (Float.compare(that.offsetX, this.offsetX) != 0) {
/* 184 */       return false;
/*     */     }
/* 186 */     if (Float.compare(that.offsetY, this.offsetY) != 0) {
/* 187 */       return false;
/*     */     }
/* 189 */     if (Float.compare(that.width, this.width) != 0) {
/* 190 */       return false;
/*     */     }
/*     */     
/* 193 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 199 */     int result = (this.width != 0.0F) ? Float.floatToIntBits(this.width) : 0;
/* 200 */     result = 31 * result + ((this.height != 0.0F) ? Float.floatToIntBits(this.height) : 0);
/* 201 */     result = 31 * result + ((this.movementDistance != 0.0F) ? Float.floatToIntBits(this.movementDistance) : 0);
/* 202 */     result = 31 * result + ((this.movementTimeMillis != 0.0F) ? Float.floatToIntBits(this.movementTimeMillis) : 0);
/* 203 */     result = 31 * result + ((this.offsetX != 0.0F) ? Float.floatToIntBits(this.offsetX) : 0);
/* 204 */     result = 31 * result + ((this.offsetY != 0.0F) ? Float.floatToIntBits(this.offsetY) : 0);
/* 205 */     long temp = (this.movementAngle != 0.0D) ? Double.doubleToLongBits(this.movementAngle) : 0L;
/* 206 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 207 */     result = 31 * result + ((this.offsetDistance != 0.0F) ? Float.floatToIntBits(this.offsetDistance) : 0);
/* 208 */     return result;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 212 */     float deg = (float)(this.movementAngle * 180.0D / Math.PI);
/* 213 */     return "TriggerShape{\n  width=" + this.width + ",\n  height=" + this.height + ",\n  movementDistance=" + this.movementDistance + ",\n  movementTimeMillis=" + this.movementTimeMillis + ",\n  movementAngle=" + this.movementAngle + "(~" + deg + " deg)" + ",\n  offsetX=" + this.offsetX + ",\n  offsetY=" + this.offsetY + ",\n  offsetDistance=" + this.offsetDistance + "\n}";
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
/*     */   public float getCollisionHeight() {
/* 226 */     return this.collisionHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class BaseCollisionRect
/*     */   {
/*     */     private final float travelFrom;
/*     */     
/*     */     private final float travelTo;
/*     */     private final double x;
/*     */     private final double y;
/*     */     private final double xx;
/*     */     private final double yy;
/*     */     
/*     */     private BaseCollisionRect(TriggerShape shape, float fromTimeMillis, float toTimeMillis, float offsetDistance) {
/* 241 */       fromTimeMillis = shape.limitTime(fromTimeMillis);
/* 242 */       toTimeMillis = shape.limitTime(toTimeMillis);
/*     */ 
/*     */       
/* 245 */       if (shape.movementTimeMillis != 0) {
/* 246 */         this.travelFrom = offsetDistance + shape.movementDistance * fromTimeMillis / shape.movementTimeMillis;
/* 247 */         this.travelTo = offsetDistance + shape.movementDistance * toTimeMillis / shape.movementTimeMillis;
/*     */       } else {
/* 249 */         this.travelFrom = offsetDistance + shape.movementDistance;
/* 250 */         this.travelTo = this.travelFrom;
/*     */       } 
/*     */ 
/*     */       
/* 254 */       this.x = (-shape.width / 2.0F + this.travelFrom);
/* 255 */       this.y = (-shape.height / 2.0F);
/* 256 */       this.xx = this.x + shape.width + (this.travelTo - this.travelFrom);
/* 257 */       this.yy = this.y + shape.height;
/*     */     }
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
/*     */ 
/*     */     
/*     */     private boolean contains(Vector2d targetPos, float targetRadius) {
/* 284 */       double rectLocalX = targetPos.getX();
/* 285 */       double rectLocalY = targetPos.getY();
/*     */       
/* 287 */       boolean aboveTop = (rectLocalY <= this.y);
/* 288 */       boolean belowBottom = (rectLocalY >= this.yy);
/* 289 */       boolean outsideToLeft = (rectLocalX <= this.x);
/* 290 */       boolean outsideToRight = (rectLocalX >= this.xx);
/*     */ 
/*     */       
/* 293 */       boolean outside = (aboveTop || belowBottom || outsideToLeft || outsideToRight);
/* 294 */       if (!outside)
/*     */       {
/* 296 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 300 */       if (!aboveTop && !belowBottom)
/*     */       {
/* 302 */         return (rectLocalX > this.x - targetRadius && rectLocalX < this.xx + targetRadius);
/*     */       }
/* 304 */       if (!outsideToLeft && !outsideToRight)
/*     */       {
/* 306 */         return (rectLocalY > this.y - targetRadius && rectLocalY < this.yy + targetRadius);
/*     */       }
/*     */ 
/*     */       
/* 310 */       if (outsideToLeft && aboveTop)
/*     */       {
/* 312 */         return checkDistance(rectLocalX, rectLocalY, this.x, this.y, targetRadius);
/*     */       }
/* 314 */       if (outsideToRight && aboveTop)
/*     */       {
/* 316 */         return checkDistance(rectLocalX, rectLocalY, this.xx, this.y, targetRadius);
/*     */       }
/* 318 */       if (outsideToLeft && belowBottom)
/*     */       {
/* 320 */         return checkDistance(rectLocalX, rectLocalY, this.x, this.yy, targetRadius);
/*     */       }
/*     */ 
/*     */       
/* 324 */       return checkDistance(rectLocalX, rectLocalY, this.xx, this.yy, targetRadius);
/*     */     }
/*     */     
/*     */     private static boolean checkDistance(double srcX, double srcY, double destX, double destY, double targetRadius) {
/* 328 */       double _x = srcX - destX;
/* 329 */       double _y = srcY - destY;
/* 330 */       return (_x * _x + _y * _y < targetRadius * targetRadius);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\TriggerShape.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */