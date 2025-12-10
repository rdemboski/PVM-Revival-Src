/*     */ package com.funcom.gameengine;
/*     */ 
/*     */ import com.funcom.commons.Operator;
/*     */ import com.funcom.commons.Vector2d;
/*     */ import java.awt.Point;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class WorldCoordinate
/*     */   implements Cloneable {
/*  10 */   public static final WorldCoordinate ZERO = new WorldCoordinate(0, 0, 0.0D, 0.0D, "", 0);
/*     */   public static final String DUMMY_MAP = "UNIDENTIFIED_MAP";
/*     */   public static final int DUMMY_INSTANCE = 0;
/*  13 */   private static final Logger LOGGER = Logger.getLogger(WorldCoordinate.class);
/*     */   
/*     */   private Point tileCoord;
/*     */   private Vector2d tileOffset;
/*     */   private String mapId;
/*     */   private int instanceReference;
/*     */   
/*     */   public WorldCoordinate() {
/*  21 */     this(ZERO);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldCoordinate(int tileX, int tileY, double offsetX, double offsetY, String mapId, int instanceReference) {
/*  28 */     this(new Point(tileX, tileY), new Vector2d(offsetX, offsetY), mapId, instanceReference);
/*  29 */     fixOffset();
/*     */   }
/*     */   
/*     */   public WorldCoordinate(Point tileCoord, Vector2d tileOffset, String mapId, int instanceReference) {
/*  33 */     this.tileCoord = new Point(tileCoord);
/*  34 */     this.tileOffset = new Vector2d(tileOffset);
/*  35 */     this.mapId = mapId;
/*  36 */     checkMapId();
/*  37 */     this.instanceReference = instanceReference;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldCoordinate(WorldCoordinate original) {
/*  46 */     this((Point)original.tileCoord.clone(), original.tileOffset.clone(), original.getMapId(), original.getInstanceReference());
/*     */   }
/*     */   
/*     */   public WorldCoordinate(WCComponent xComponent, WCComponent yComponent, String mapId, int instanceReference) {
/*  50 */     this();
/*  51 */     this.mapId = mapId;
/*  52 */     checkMapId();
/*  53 */     this.instanceReference = instanceReference;
/*  54 */     set(xComponent, yComponent);
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
/*     */   @Operator
/*     */   public WorldCoordinate add(WorldCoordinate displacement) {
/*  71 */     addTiles((displacement.getTileCoord()).x, (displacement.getTileCoord()).y);
/*  72 */     addOffset(displacement.getTileOffset());
/*  73 */     return this;
/*     */   }
/*     */   
/*     */   @Operator
/*     */   public WorldCoordinate addTiles(int x, int y) {
/*  78 */     this.tileCoord.translate(x, y);
/*     */     
/*  80 */     return this;
/*     */   }
/*     */   
/*     */   @Operator
/*     */   public WorldCoordinate addTiles(Point point) {
/*  85 */     return addTiles(point.x, point.y);
/*     */   }
/*     */   
/*     */   @Operator
/*     */   public WorldCoordinate addOffset(Vector2d offset) {
/*  90 */     return addOffset(offset.getX(), offset.getY());
/*     */   }
/*     */   
/*     */   @Operator
/*     */   public WorldCoordinate addOffset(double offsetX, double offsetY) {
/*  95 */     this.tileOffset.add(offsetX, offsetY);
/*  96 */     fixOffset();
/*  97 */     return this;
/*     */   }
/*     */   
/*     */   @Operator
/*     */   public WorldCoordinate addHorizontal(WorldCoordinate other) {
/* 102 */     this.tileCoord.translate(other.tileCoord.x, 0);
/* 103 */     this.tileOffset.add(other.tileOffset.getX(), 0.0D);
/* 104 */     return this;
/*     */   }
/*     */   
/*     */   @Operator
/*     */   public WorldCoordinate addVertical(WorldCoordinate other) {
/* 109 */     this.tileCoord.translate(0, other.tileCoord.y);
/* 110 */     this.tileOffset.add(0.0D, other.tileOffset.getY());
/* 111 */     return this;
/*     */   }
/*     */   
/*     */   @Operator
/*     */   public WorldCoordinate subtract(WorldCoordinate coordinate) {
/* 116 */     this.tileCoord.x -= coordinate.tileCoord.x;
/* 117 */     this.tileCoord.y -= coordinate.tileCoord.y;
/* 118 */     this.tileOffset.setX(this.tileOffset.getX() - coordinate.tileOffset.getX());
/* 119 */     this.tileOffset.setY(this.tileOffset.getY() - coordinate.tileOffset.getY());
/* 120 */     fixOffset();
/* 121 */     return this;
/*     */   }
/*     */   
/*     */   @Operator
/*     */   public WorldCoordinate subtractTiles(int x, int y) {
/* 126 */     this.tileCoord.translate(-x, -y);
/* 127 */     return this;
/*     */   }
/*     */   
/*     */   @Operator
/*     */   public WorldCoordinate subtractTiles(Point point) {
/* 132 */     return subtractTiles(point.x, point.y);
/*     */   }
/*     */   
/*     */   @Operator
/*     */   public WorldCoordinate subtractHorizontal(WorldCoordinate other) {
/* 137 */     this.tileCoord.translate(-other.tileCoord.x, 0);
/* 138 */     this.tileOffset.sub(other.tileOffset.getX(), 0.0D);
/* 139 */     return this;
/*     */   }
/*     */   
/*     */   @Operator
/*     */   public WorldCoordinate subtractVertical(WorldCoordinate other) {
/* 144 */     this.tileCoord.translate(0, -other.tileCoord.y);
/* 145 */     this.tileOffset.sub(0.0D, other.tileOffset.getY());
/* 146 */     return this;
/*     */   }
/*     */   
/*     */   public double dotProduct(WorldCoordinate other) {
/* 150 */     double tpx = (this.tileCoord.x + this.tileOffset.getX()) * (other.tileCoord.x + other.tileOffset.getX());
/* 151 */     double tpy = (this.tileCoord.y + this.tileOffset.getY()) * (other.tileCoord.y + other.tileOffset.getY());
/* 152 */     return tpx + tpy;
/*     */   }
/*     */   
/*     */   @Operator
/*     */   public WorldCoordinate multLocal(double v) {
/* 157 */     double tpx = (this.tileCoord.x + this.tileOffset.getX()) * v;
/* 158 */     double tpy = (this.tileCoord.y + this.tileOffset.getY()) * v;
/* 159 */     this.tileCoord.setLocation(floor(tpx), floor(tpy));
/* 160 */     this.tileOffset.set(tpx - this.tileCoord.x, tpy - this.tileCoord.y);
/* 161 */     return this;
/*     */   }
/*     */   
/*     */   public double length() {
/* 165 */     double tpLen = Math.pow(this.tileCoord.x + this.tileOffset.getX(), 2.0D) + Math.pow(this.tileCoord.y + this.tileOffset.getY(), 2.0D);
/* 166 */     return Math.sqrt(tpLen);
/*     */   }
/*     */   
/*     */   public double distanceTo(WorldCoordinate otherCoord) {
/* 170 */     return (new WorldCoordinate(this)).subtract(otherCoord).length();
/*     */   }
/*     */   
/*     */   public double angle() {
/* 174 */     double x = this.tileCoord.x + this.tileOffset.getX();
/* 175 */     double y = this.tileCoord.y + this.tileOffset.getY();
/* 176 */     return Math.atan2(y, x);
/*     */   }
/*     */   
/*     */   public double angleTo(WorldCoordinate otherCoord) {
/* 180 */     return (new WorldCoordinate(otherCoord)).subtract(this).angle();
/*     */   }
/*     */   
/*     */   @Operator
/*     */   public WorldCoordinate normalize() {
/* 185 */     double length = length();
/* 186 */     this.tileOffset.setX((this.tileCoord.x + this.tileOffset.getX()) / length);
/* 187 */     this.tileOffset.setY((this.tileCoord.y + this.tileOffset.getY()) / length);
/* 188 */     this.tileCoord.x = 0;
/* 189 */     this.tileCoord.y = 0;
/* 190 */     fixOffset();
/* 191 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void fixOffset() {
/* 196 */     this.tileCoord.x += floor(this.tileOffset.getX());
/* 197 */     this.tileCoord.y += floor(this.tileOffset.getY());
/*     */ 
/*     */     
/* 200 */     this.tileOffset.setX(this.tileOffset.getX() - floor(this.tileOffset.getX()));
/* 201 */     this.tileOffset.setY(this.tileOffset.getY() - floor(this.tileOffset.getY()));
/*     */   }
/*     */   
/*     */   private int floor(double d) {
/* 205 */     int id = (int)d;
/* 206 */     return (d >= 0.0D || d == id) ? id : (-((int)-d) - 1);
/*     */   }
/*     */   
/*     */   public Point getTileCoord() {
/* 210 */     return this.tileCoord;
/*     */   }
/*     */   
/*     */   public Vector2d getTileOffset() {
/* 214 */     return this.tileOffset;
/*     */   }
/*     */   
/*     */   @Operator
/*     */   public WorldCoordinate set(WorldCoordinate position) {
/* 219 */     this.tileCoord.setLocation(position.getTileCoord());
/* 220 */     this.tileOffset.setLocation(position.getTileOffset());
/* 221 */     this.mapId = position.mapId;
/* 222 */     checkMapId();
/* 223 */     return this;
/*     */   }
/*     */   
/*     */   @Operator
/*     */   public WorldCoordinate set(int x, int y, double offsetX, double offsetY) {
/* 228 */     this.tileCoord.setLocation(x, y);
/* 229 */     this.tileOffset.setLocation(offsetX, offsetY);
/* 230 */     return this;
/*     */   }
/*     */   
/*     */   @Operator
/*     */   public WorldCoordinate setX(WorldCoordinate position) {
/* 235 */     this.tileCoord.x = (position.getTileCoord()).x;
/* 236 */     this.tileOffset.setX(position.getTileOffset().getX());
/* 237 */     return this;
/*     */   }
/*     */   
/*     */   @Operator
/*     */   public WorldCoordinate setY(WorldCoordinate position) {
/* 242 */     this.tileCoord.y = (position.getTileCoord()).y;
/* 243 */     this.tileOffset.setY(position.getTileOffset().getY());
/* 244 */     return this;
/*     */   }
/*     */   
/*     */   @Operator
/*     */   public WorldCoordinate setX(int x, double offsetX) {
/* 249 */     this.tileCoord.x = x;
/* 250 */     this.tileOffset.setX(offsetX);
/* 251 */     return this;
/*     */   }
/*     */   
/*     */   @Operator
/*     */   public WorldCoordinate setY(int y, double offsetY) {
/* 256 */     this.tileCoord.y = y;
/* 257 */     this.tileOffset.setY(offsetY);
/* 258 */     return this;
/*     */   }
/*     */   
/*     */   public boolean equalsTile(WorldCoordinate coord) {
/* 262 */     return coord.getTileCoord().equals(this.tileCoord);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 267 */     if (this == o) return true; 
/* 268 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 270 */     WorldCoordinate that = (WorldCoordinate)o;
/*     */     
/* 272 */     if (this.instanceReference != that.instanceReference) return false; 
/* 273 */     if ((this.mapId != null) ? !this.mapId.equals(that.mapId) : (that.mapId != null)) return false; 
/* 274 */     if ((this.tileCoord != null) ? !this.tileCoord.equals(that.tileCoord) : (that.tileCoord != null)) return false; 
/* 275 */     if ((this.tileOffset != null) ? !this.tileOffset.equals(that.tileOffset) : (that.tileOffset != null)) return false;
/*     */     
/* 277 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 282 */     int result = (this.tileCoord != null) ? this.tileCoord.hashCode() : 0;
/* 283 */     result = 31 * result + ((this.tileOffset != null) ? this.tileOffset.hashCode() : 0);
/* 284 */     result = 31 * result + ((this.mapId != null) ? this.mapId.hashCode() : 0);
/* 285 */     result = 31 * result + this.instanceReference;
/* 286 */     return result;
/*     */   }
/*     */   
/*     */   @Operator
/*     */   public WorldCoordinate copy(WorldCoordinate coord) {
/* 291 */     coord.tileCoord.setLocation(this.tileCoord);
/* 292 */     coord.tileOffset.set(this.tileOffset);
/* 293 */     coord.mapId = this.mapId;
/* 294 */     checkMapId();
/* 295 */     coord.instanceReference = this.instanceReference;
/* 296 */     return coord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTileX(int tileX) {
/* 303 */     this.tileCoord.x = tileX;
/*     */   }
/*     */   
/*     */   public void setTileY(int tileY) {
/* 307 */     this.tileCoord.y = tileY;
/*     */   }
/*     */   
/*     */   public void setTileOffX(double tileOffX) {
/* 311 */     this.tileOffset.setX(tileOffX);
/* 312 */     fixOffset();
/*     */   }
/*     */   
/*     */   public void setTileOffY(double tileOffY) {
/* 316 */     this.tileOffset.setY(tileOffY);
/* 317 */     fixOffset();
/*     */   }
/*     */   
/*     */   public int getTileX() {
/* 321 */     return this.tileCoord.x;
/*     */   }
/*     */   
/*     */   public int getTileY() {
/* 325 */     return this.tileCoord.y;
/*     */   }
/*     */   
/*     */   public double getTileOffX() {
/* 329 */     return this.tileOffset.getX();
/*     */   }
/*     */   
/*     */   public double getTileOffY() {
/* 333 */     return this.tileOffset.getY();
/*     */   }
/*     */   
/*     */   public void setMapId(String mapId) {
/* 337 */     this.mapId = mapId;
/* 338 */     checkMapId();
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   private void checkMapId() {}
/*     */ 
/*     */   
/*     */   public void setInstanceReference(int instanceReference) {
/* 347 */     this.instanceReference = instanceReference;
/*     */   }
/*     */   
/*     */   public String getMapId() {
/* 351 */     return this.mapId;
/*     */   }
/*     */   
/*     */   public int getInstanceReference() {
/* 355 */     return this.instanceReference;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldCoordinate clone() {
/*     */     try {
/* 361 */       WorldCoordinate wc = (WorldCoordinate)super.clone();
/* 362 */       wc.tileCoord = (Point)this.tileCoord.clone();
/* 363 */       wc.tileOffset = this.tileOffset.clone();
/* 364 */       wc.instanceReference = this.instanceReference;
/* 365 */       wc.mapId = this.mapId;
/* 366 */       checkMapId();
/* 367 */       return wc;
/* 368 */     } catch (CloneNotSupportedException e) {
/* 369 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 374 */     return "(" + this.tileCoord.x + ", " + this.tileCoord.y + ", " + this.tileOffset.getX() + ", " + this.tileOffset.getY() + ", " + "'" + this.mapId + "', " + this.instanceReference + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WCComponent getX() {
/* 384 */     return new WCComponent((int)this.tileCoord.getX(), this.tileOffset.getX());
/*     */   }
/*     */   
/*     */   public WCComponent getY() {
/* 388 */     return new WCComponent((int)this.tileCoord.getY(), this.tileOffset.getY());
/*     */   }
/*     */   
/*     */   public void set(WCComponent xComponent, WCComponent yComponent) {
/* 392 */     this.tileCoord.setLocation(xComponent.getTileCoord(), yComponent.getTileCoord());
/* 393 */     this.tileOffset.setLocation(xComponent.getTileOffset(), yComponent.getTileOffset());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rotate(double angle) {
/* 401 */     double cosAngle = Math.cos(angle);
/* 402 */     double sinAngle = Math.sin(angle);
/* 403 */     double x = this.tileCoord.getX() + this.tileOffset.getX();
/* 404 */     double y = this.tileCoord.getY() + this.tileOffset.getY();
/* 405 */     setX(0, cosAngle * x - sinAngle * y);
/* 406 */     setY(0, sinAngle * x + cosAngle * y);
/* 407 */     fixOffset();
/*     */   }
/*     */   
/*     */   @Operator
/*     */   public WorldCoordinate floor() {
/* 412 */     this.tileOffset.set(0.0D, 0.0D);
/* 413 */     return this;
/*     */   }
/*     */   
/*     */   @Operator
/*     */   public WorldCoordinate ceil() {
/* 418 */     this.tileOffset.set(0.0D, 0.0D);
/* 419 */     this.tileCoord.x++;
/* 420 */     this.tileCoord.y++;
/* 421 */     return this;
/*     */   }
/*     */   
/*     */   public WorldCoordinate orthogonal() {
/* 425 */     WorldCoordinate ret = new WorldCoordinate(this);
/* 426 */     ret.orthogonalLocal();
/* 427 */     return ret;
/*     */   }
/*     */   
/*     */   public void orthogonalLocal() {
/* 431 */     this.tileCoord.x ^= this.tileCoord.y;
/* 432 */     this.tileCoord.y = this.tileCoord.x ^ this.tileCoord.y;
/* 433 */     this.tileCoord.x ^= this.tileCoord.y;
/* 434 */     this.tileOffset.set(this.tileOffset.getY(), this.tileOffset.getX());
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\gameengine\WorldCoordinate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */