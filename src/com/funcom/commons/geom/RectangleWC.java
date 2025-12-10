/*     */ package com.funcom.commons.geom;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.WorldUtils;
/*     */ import java.awt.Point;
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
/*     */ public class RectangleWC
/*     */ {
/*  22 */   protected WorldCoordinate origin = new WorldCoordinate();
/*  23 */   protected WorldCoordinate extent = new WorldCoordinate();
/*     */ 
/*     */   
/*     */   public RectangleWC(WorldCoordinate origin, WorldCoordinate extent) {
/*  27 */     this();
/*  28 */     setRect(origin, extent);
/*     */   }
/*     */   public RectangleWC() {}
/*     */   public RectangleWC(RectangleWC rectangleWC) {
/*  32 */     this();
/*  33 */     setRect(rectangleWC.getOrigin(), rectangleWC.getExtent());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RectangleWC createBoundingRectangle(WorldCoordinate point1, WorldCoordinate point2) {
/*  44 */     RectangleWC bounds = new RectangleWC(point1, point1);
/*  45 */     bounds.add(point2);
/*  46 */     return bounds;
/*     */   }
/*     */   
/*     */   public void setRect(WorldCoordinate origin, WorldCoordinate extent) {
/*  50 */     this.origin.set(origin);
/*  51 */     this.extent.set(extent);
/*  52 */     checkRectCoordinates();
/*     */   }
/*     */   
/*     */   private void checkRectCoordinates() {
/*  56 */     int horizontal = WorldUtils.compareToHorizontal(this.origin, this.extent);
/*  57 */     int vertical = WorldUtils.compareToVertical(this.origin, this.extent);
/*     */     
/*  59 */     if (horizontal > 0 || vertical > 0) {
/*  60 */       throw new IllegalArgumentException("Origin cannot be below or to the right of the extent");
/*     */     }
/*     */   }
/*     */   
/*     */   public void setOrigin(WorldCoordinate origin) {
/*  65 */     this.origin.set(origin);
/*     */   }
/*     */   
/*     */   public WorldCoordinate getOrigin() {
/*  69 */     return this.origin;
/*     */   }
/*     */   
/*     */   public WorldCoordinate getExtent() {
/*  73 */     return this.extent;
/*     */   }
/*     */   
/*     */   public void enlargeByRadius(double radius) {
/*  77 */     this.origin.addOffset(-radius, -radius);
/*  78 */     this.extent.addOffset(radius, radius);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldCoordinate getSize() {
/*  88 */     return (new WorldCoordinate(this.extent)).subtract(this.origin);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldCoordinate getHalfSize() {
/*  98 */     WorldCoordinate halfSize = getSize();
/*  99 */     halfSize.getTileOffset().setLocation(halfSize.getTileOffset().getX() * 0.5D + ((halfSize.getTileCoord()).x % 2) * 0.5D, halfSize.getTileOffset().getY() * 0.5D + ((halfSize.getTileCoord()).y % 2) * 0.5D);
/*     */ 
/*     */ 
/*     */     
/* 103 */     halfSize.getTileCoord().setLocation((halfSize.getTileCoord()).x / 2, (halfSize.getTileCoord()).y / 2);
/* 104 */     return halfSize;
/*     */   }
/*     */   
/*     */   public int outcode(WorldCoordinate wc) {
/* 108 */     int out = 0;
/*     */     
/* 110 */     if (WorldUtils.compareToHorizontal(wc, this.origin) < 0) {
/* 111 */       out |= 0x1;
/*     */     }
/* 113 */     if (WorldUtils.compareToVertical(wc, this.origin) < 0) {
/* 114 */       out |= 0x2;
/*     */     }
/* 116 */     if (WorldUtils.compareToHorizontal(wc, this.extent) > 0) {
/* 117 */       out |= 0x4;
/*     */     }
/* 119 */     if (WorldUtils.compareToVertical(wc, this.extent) > 0) {
/* 120 */       out |= 0x8;
/*     */     }
/*     */     
/* 123 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public double getWidth() {
/* 132 */     WorldCoordinate size = getSize();
/* 133 */     return (size.getTileCoord()).x + size.getTileOffset().getX();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public double getHeight() {
/* 142 */     WorldCoordinate size = getSize();
/* 143 */     return (size.getTileCoord()).y + size.getTileOffset().getY();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 147 */     return this.origin.equals(this.extent);
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
/*     */   public boolean intersects(RectangleWC other) {
/* 159 */     int originHorizontal = WorldUtils.compareToHorizontal(this.origin, other.extent);
/* 160 */     int originVertical = WorldUtils.compareToVertical(this.origin, other.extent);
/* 161 */     int extentHorizontal = WorldUtils.compareToHorizontal(this.extent, other.origin);
/* 162 */     int extentVertical = WorldUtils.compareToVertical(this.extent, other.origin);
/*     */     
/* 164 */     return (originHorizontal <= 0 && originVertical <= 0 && extentHorizontal >= 0 && extentVertical >= 0);
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
/*     */   public boolean contains(WorldCoordinate worldCoordinate) {
/* 178 */     if (WorldUtils.compareToHorizontal(worldCoordinate, this.origin) < 0) {
/* 179 */       return false;
/*     */     }
/*     */     
/* 182 */     if (WorldUtils.compareToVertical(worldCoordinate, this.origin) < 0) {
/* 183 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 187 */     if (WorldUtils.compareToHorizontal(worldCoordinate, this.extent) > 0) {
/* 188 */       return false;
/*     */     }
/*     */     
/* 191 */     return (WorldUtils.compareToVertical(worldCoordinate, this.extent) <= 0);
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
/*     */   public boolean contains(RectangleWC rectangle) {
/* 205 */     if (WorldUtils.compareToHorizontal(rectangle.getOrigin(), this.origin) <= 0) {
/* 206 */       return false;
/*     */     }
/*     */     
/* 209 */     if (WorldUtils.compareToVertical(rectangle.getOrigin(), this.origin) <= 0) {
/* 210 */       return false;
/*     */     }
/*     */     
/* 213 */     if (WorldUtils.compareToHorizontal(rectangle.getExtent(), this.extent) >= 0) {
/* 214 */       return false;
/*     */     }
/*     */     
/* 217 */     return (WorldUtils.compareToVertical(rectangle.getExtent(), this.extent) < 0);
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
/*     */   public boolean containsTile(int tilex, int tiley) {
/* 229 */     return contains(new WorldCoordinate(tilex, tiley, 0.0D, 0.0D, "UNIDENTIFIED_MAP", 0));
/*     */   }
/*     */   
/*     */   public boolean intersectsLine(LineWC lineWC) {
/* 233 */     int out1 = outcode(lineWC.getWC1());
/* 234 */     int out2 = outcode(lineWC.getWC2());
/*     */ 
/*     */     
/* 237 */     if (out1 == 0 || out2 == 0) {
/* 238 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 242 */     if ((out1 & out2) != 0) {
/* 243 */       return false;
/*     */     }
/*     */     
/* 246 */     int intWidth = (lineWC.getWC2().getTileCoord()).x - (lineWC.getWC1().getTileCoord()).x;
/* 247 */     int intHeight = (lineWC.getWC2().getTileCoord()).y - (lineWC.getWC1().getTileCoord()).y;
/* 248 */     double decWidth = lineWC.getWC2().getTileOffset().getX() - lineWC.getWC1().getTileOffset().getX();
/* 249 */     double decHeight = lineWC.getWC2().getTileOffset().getY() - lineWC.getWC1().getTileOffset().getY();
/*     */     
/* 251 */     if ((out1 & 0x5) != 0) {
/* 252 */       double factor = (intHeight + decHeight) / (intWidth + decWidth);
/*     */       
/* 254 */       if ((out1 & 0x1) != 0) {
/* 255 */         WorldCoordinate left = intersectionCoordLeft(lineWC, factor);
/* 256 */         if (contains(left)) {
/* 257 */           return true;
/*     */         }
/*     */       } else {
/* 260 */         WorldCoordinate right = intersectionCoordRight(lineWC, factor);
/* 261 */         if (contains(right)) {
/* 262 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 267 */     if ((out1 & 0xA) != 0) {
/* 268 */       double factor = (intWidth + decWidth) / (intHeight + decHeight);
/*     */       
/* 270 */       if ((out1 & 0x2) != 0) {
/* 271 */         WorldCoordinate top = intersectionCoordTop(lineWC, factor);
/* 272 */         if (contains(top)) {
/* 273 */           return true;
/*     */         }
/*     */       } else {
/* 276 */         WorldCoordinate bottom = intersectionCoordBottom(lineWC, factor);
/* 277 */         if (contains(bottom)) {
/* 278 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 283 */     return false;
/*     */   }
/*     */   
/*     */   private WorldCoordinate intersectionCoordLeft(LineWC line, double factor) {
/* 287 */     double decOut = (((this.origin.getTileCoord()).x - (line.getWC1().getTileCoord()).x) + this.origin.getTileOffset().getX() - line.getWC1().getTileOffset().getX()) * factor;
/*     */     
/* 289 */     int intOut = (int)Math.floor(decOut);
/*     */     
/* 291 */     int intPart = (line.getWC1().getTileCoord()).y + intOut;
/* 292 */     double decPart = line.getWC1().getTileOffset().getY() + decOut - intOut;
/*     */     
/* 294 */     return new WorldCoordinate((this.origin.getTileCoord()).x, intPart, this.origin.getTileOffset().getX(), decPart, line.getWC1().getMapId(), line.getWC1().getInstanceReference());
/*     */   }
/*     */ 
/*     */   
/*     */   private WorldCoordinate intersectionCoordRight(LineWC line, double factor) {
/* 299 */     double decOut = (((this.extent.getTileCoord()).x - (line.getWC1().getTileCoord()).x) + this.extent.getTileOffset().getX() - line.getWC1().getTileOffset().getX()) * factor;
/*     */     
/* 301 */     int intOut = (int)Math.floor(decOut);
/*     */     
/* 303 */     int intPart = (line.getWC1().getTileCoord()).y + intOut;
/* 304 */     double decPart = line.getWC1().getTileOffset().getY() + decOut - intOut;
/*     */     
/* 306 */     return new WorldCoordinate((this.extent.getTileCoord()).x, intPart, this.extent.getTileOffset().getX(), decPart, line.getWC1().getMapId(), line.getWC1().getInstanceReference());
/*     */   }
/*     */ 
/*     */   
/*     */   private WorldCoordinate intersectionCoordTop(LineWC line, double factor) {
/* 311 */     double decOut = (((this.origin.getTileCoord()).y - (line.getWC1().getTileCoord()).y) + this.origin.getTileOffset().getY() - line.getWC1().getTileOffset().getY()) * factor;
/*     */     
/* 313 */     int intOut = (int)Math.floor(decOut);
/*     */     
/* 315 */     int intPart = (line.getWC1().getTileCoord()).x + intOut;
/* 316 */     double decPart = line.getWC1().getTileOffset().getX() + decOut - intOut;
/*     */     
/* 318 */     return new WorldCoordinate(intPart, (this.origin.getTileCoord()).y, decPart, this.origin.getTileOffset().getY(), line.getWC1().getMapId(), line.getWC1().getInstanceReference());
/*     */   }
/*     */ 
/*     */   
/*     */   private WorldCoordinate intersectionCoordBottom(LineWC line, double factor) {
/* 323 */     double decOut = (((this.extent.getTileCoord()).y - (line.getWC1().getTileCoord()).y) + this.extent.getTileOffset().getY() - line.getWC1().getTileOffset().getY()) * factor;
/*     */     
/* 325 */     int intOut = (int)Math.floor(decOut);
/*     */     
/* 327 */     int intPart = (line.getWC1().getTileCoord()).x + intOut;
/* 328 */     double decPart = line.getWC1().getTileOffset().getX() + decOut - intOut;
/*     */     
/* 330 */     return new WorldCoordinate(intPart, (this.extent.getTileCoord()).y, decPart, this.extent.getTileOffset().getY(), line.getWC1().getMapId(), line.getWC1().getInstanceReference());
/*     */   }
/*     */ 
/*     */   
/*     */   public RectangleWC createUnion(RectangleWC rect) {
/* 335 */     RectangleWC leftRect = this;
/* 336 */     if (getOrigin().getTileCoord().getX() > rect.getOrigin().getTileCoord().getX() || (getOrigin().getTileCoord().getX() == rect.getOrigin().getTileCoord().getX() && getOrigin().getTileOffset().getX() > rect.getOrigin().getTileOffset().getX()))
/*     */     {
/*     */       
/* 339 */       leftRect = rect;
/*     */     }
/* 341 */     RectangleWC rightRect = this;
/* 342 */     if (getExtent().getTileCoord().getX() < rect.getExtent().getTileCoord().getX() || (getExtent().getTileCoord().getX() == rect.getExtent().getTileCoord().getX() && getExtent().getTileOffset().getX() < rect.getExtent().getTileOffset().getX()))
/*     */     {
/*     */       
/* 345 */       rightRect = rect;
/*     */     }
/* 347 */     RectangleWC topRect = this;
/* 348 */     if (getOrigin().getTileCoord().getY() > rect.getOrigin().getTileCoord().getY() || (getOrigin().getTileCoord().getY() == rect.getOrigin().getTileCoord().getY() && getOrigin().getTileOffset().getY() > rect.getOrigin().getTileOffset().getY()))
/*     */     {
/*     */       
/* 351 */       topRect = rect;
/*     */     }
/* 353 */     RectangleWC bottomRect = this;
/* 354 */     if (getExtent().getTileCoord().getY() < rect.getExtent().getTileCoord().getY() || (getExtent().getTileCoord().getY() == rect.getExtent().getTileCoord().getY() && getExtent().getTileOffset().getY() < rect.getExtent().getTileOffset().getY()))
/*     */     {
/*     */       
/* 357 */       bottomRect = rect;
/*     */     }
/* 359 */     WorldCoordinate unionTopLeft = new WorldCoordinate((int)leftRect.getOrigin().getTileCoord().getX(), (int)topRect.getOrigin().getTileCoord().getY(), leftRect.getOrigin().getTileOffset().getX(), topRect.getOrigin().getTileOffset().getY(), leftRect.getOrigin().getMapId(), leftRect.getOrigin().getInstanceReference());
/*     */ 
/*     */ 
/*     */     
/* 363 */     WorldCoordinate unionBottomRight = new WorldCoordinate((int)rightRect.getExtent().getTileCoord().getX(), (int)bottomRect.getExtent().getTileCoord().getY(), rightRect.getExtent().getTileOffset().getX(), bottomRect.getExtent().getTileOffset().getY(), rightRect.getOrigin().getMapId(), rightRect.getOrigin().getInstanceReference());
/*     */ 
/*     */ 
/*     */     
/* 367 */     return new RectangleWC(unionTopLeft, unionBottomRight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(WorldCoordinate worldCoordinate) {
/* 376 */     if (WorldUtils.compareToHorizontal(worldCoordinate, this.origin) < 0) {
/* 377 */       this.origin.setX(worldCoordinate);
/*     */     }
/* 379 */     if (WorldUtils.compareToVertical(worldCoordinate, this.origin) < 0) {
/* 380 */       this.origin.setY(worldCoordinate);
/*     */     }
/* 382 */     if (WorldUtils.compareToHorizontal(worldCoordinate, this.extent) > 0) {
/* 383 */       this.extent.setX(worldCoordinate);
/*     */     }
/* 385 */     if (WorldUtils.compareToVertical(worldCoordinate, this.extent) > 0) {
/* 386 */       this.extent.setY(worldCoordinate);
/*     */     }
/*     */   }
/*     */   
/*     */   public void translate(WorldCoordinate displacement) {
/* 391 */     this.origin.add(displacement);
/* 392 */     this.extent.add(displacement);
/*     */   }
/*     */   
/*     */   public void translateTiles(int x, int y) {
/* 396 */     this.origin.addTiles(x, y);
/* 397 */     this.extent.addTiles(x, y);
/*     */   }
/*     */   
/*     */   public void translateTiles(Point point) {
/* 401 */     this.origin.addTiles(point);
/* 402 */     this.extent.addTiles(point);
/*     */   }
/*     */   
/*     */   public WorldCoordinate getCenter() {
/* 406 */     WorldCoordinate ret = new WorldCoordinate(this.origin);
/* 407 */     ret.add(getHalfSize());
/* 408 */     return ret;
/*     */   }
/*     */   
/*     */   public boolean adjacent(RectangleWC box) {
/* 412 */     RectangleWC rect = this;
/* 413 */     return (((WorldUtils.compareToHorizontal(rect.getOrigin(), box.getExtent()) == 0 || WorldUtils.compareToHorizontal(rect.getExtent(), box.getOrigin()) == 0) && ((WorldUtils.compareToVertical(rect.getOrigin(), box.getOrigin()) == 1 && WorldUtils.compareToVertical(rect.getOrigin(), box.getExtent()) == -1) || (WorldUtils.compareToVertical(rect.getExtent(), box.getOrigin()) == 1 && WorldUtils.compareToVertical(rect.getExtent(), box.getExtent()) == -1) || (WorldUtils.compareToVertical(box.getOrigin(), rect.getOrigin()) == 1 && WorldUtils.compareToVertical(box.getOrigin(), rect.getExtent()) == -1) || (WorldUtils.compareToVertical(box.getExtent(), rect.getOrigin()) == 1 && WorldUtils.compareToVertical(box.getExtent(), rect.getExtent()) == -1) || (WorldUtils.compareToVertical(box.getOrigin(), rect.getOrigin()) == 0 && WorldUtils.compareToVertical(box.getExtent(), rect.getExtent()) == 0))) || ((WorldUtils.compareToVertical(rect.getOrigin(), box.getExtent()) == 0 || WorldUtils.compareToVertical(rect.getExtent(), box.getOrigin()) == 0) && ((WorldUtils.compareToHorizontal(rect.getOrigin(), box.getOrigin()) == 1 && WorldUtils.compareToHorizontal(rect.getOrigin(), box.getExtent()) == -1) || (WorldUtils.compareToHorizontal(rect.getExtent(), box.getOrigin()) == 1 && WorldUtils.compareToHorizontal(rect.getExtent(), box.getExtent()) == -1) || (WorldUtils.compareToHorizontal(box.getOrigin(), rect.getExtent()) == 1 && WorldUtils.compareToHorizontal(box.getOrigin(), rect.getExtent()) == -1) || (WorldUtils.compareToHorizontal(box.getExtent(), rect.getOrigin()) == 1 && WorldUtils.compareToHorizontal(box.getExtent(), rect.getExtent()) == -1) || (WorldUtils.compareToHorizontal(box.getOrigin(), rect.getOrigin()) == 0 && WorldUtils.compareToHorizontal(box.getExtent(), rect.getExtent()) == 0))));
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
/*     */   public boolean equals(Object o) {
/* 450 */     if (this == o) return true; 
/* 451 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 453 */     RectangleWC that = (RectangleWC)o;
/*     */     
/* 455 */     return (this.extent.equals(that.extent) && this.origin.equals(that.origin));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 462 */     int result = this.origin.hashCode();
/* 463 */     result = 31 * result + this.extent.hashCode();
/* 464 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 469 */     StringBuffer sb = new StringBuffer();
/* 470 */     sb.append("[origin=").append(this.origin).append(",extent=").append(this.extent).append("]");
/*     */     
/* 472 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\geom\RectangleWC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */