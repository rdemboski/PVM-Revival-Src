/*     */ package com.funcom.gameengine.view.water;
/*     */ 
/*     */ import com.funcom.commons.MathUtils;
/*     */ import com.funcom.commons.Vector2d;
/*     */ import com.funcom.commons.geom.RectangleWC;
/*     */ import com.funcom.gameengine.WorldUtils;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.TexCoords;
/*     */ import com.jme.util.geom.BufferUtils;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WaterLine
/*     */   extends WaterMesh
/*     */ {
/*     */   public static final int LINE_COUNT = 4;
/*     */   public static final int SHORETYPE_NONE = 0;
/*     */   public static final int SHORETYPE_SHORE = 1;
/*     */   public static final int SHORETYPE_BOTH = 2;
/*     */   public static final int TEXTUREDISTRIBUTION_LINEDIST = 0;
/*     */   public static final int TEXTUREDISTRIBUTION_ABSOLUTE = 1;
/*     */   private List<WaterLineCoordinateSet> waterLines;
/*     */   private float height;
/*     */   private int shoreType;
/*     */   private int textureDistribution;
/*     */   private float baseTextureScale;
/*     */   private float baseTextureOffsetX;
/*     */   private float baseTextureOffsetY;
/*     */   private float overlayTextureScale;
/*     */   private float overlayTextureOffsetX;
/*     */   private float overlayTextureOffsetY;
/*     */   private float shoreTextureScale;
/*     */   private float shoreTextureOffset;
/*     */   private float shorePosition;
/*     */   private FloatBuffer vertBuf;
/*     */   private IntBuffer indexBuffer;
/*     */   private FloatBuffer colorBuf;
/*     */   private FloatBuffer baseTexs;
/*     */   private FloatBuffer overlayTexs;
/*     */   private FloatBuffer shoreTexs;
/*     */   private FloatBuffer normBuf;
/*     */   private int vertexQuantity;
/*     */   private Vector3f origin;
/*     */   
/*     */   public WaterLine() {
/*  52 */     this(0.5F);
/*     */   }
/*     */   
/*     */   public WaterLine(float height) {
/*  56 */     this(height, 0, 0, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, (List<WaterLineCoordinateSet>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WaterLine(float height, int shoreType, int textureDistribution, float baseTextureScale, float baseTextureOffsetX, float baseTextureOffsetY, float overlayTextureScale, float overlayTextureOffsetX, float overlayTextureOffsetY, float shoreTextureScale, float shoreTextureOffset, List<WaterLineCoordinateSet> waterLines) {
/*  67 */     super("WaterLine");
/*     */     
/*  69 */     this.height = height;
/*  70 */     this.shoreType = shoreType;
/*  71 */     this.textureDistribution = textureDistribution;
/*  72 */     this.baseTextureScale = baseTextureScale;
/*  73 */     this.baseTextureOffsetX = baseTextureOffsetX;
/*  74 */     this.baseTextureOffsetY = baseTextureOffsetY;
/*  75 */     this.overlayTextureScale = overlayTextureScale;
/*  76 */     this.overlayTextureOffsetX = overlayTextureOffsetX;
/*  77 */     this.overlayTextureOffsetY = overlayTextureOffsetY;
/*  78 */     this.shoreTextureScale = shoreTextureScale;
/*  79 */     this.shoreTextureOffset = shoreTextureOffset;
/*     */     
/*  81 */     this.origin = new Vector3f();
/*     */     
/*  83 */     if (waterLines != null) {
/*  84 */       setWaterLines(waterLines);
/*     */     }
/*     */   }
/*     */   
/*     */   public Class<? extends Spatial> getClassTag() {
/*  89 */     return (Class)getClass();
/*     */   }
/*     */   
/*     */   public void setWaterLines(List<WaterLineCoordinateSet> waterLines) {
/*  93 */     this.waterLines = waterLines;
/*  94 */     updateWaterLines();
/*     */   }
/*     */   
/*     */   public void updateWaterLines() {
/*  98 */     this.vertexQuantity = this.waterLines.size() * 4;
/*  99 */     setVertexCount(this.vertexQuantity);
/* 100 */     calculateWaterDistances();
/* 101 */     createBuffers();
/* 102 */     updateMesh();
/*     */   }
/*     */   
/*     */   public List<WaterLineCoordinateSet> getWaterLines() {
/* 106 */     return this.waterLines;
/*     */   }
/*     */   
/*     */   private void calculateWaterDistances() {
/* 110 */     for (int i = 0; i < this.waterLines.size(); i++) {
/* 111 */       WaterLineCoordinateSet coordinateSet = this.waterLines.get(i);
/*     */       
/* 113 */       float dist = (float)coordinateSet.shoreLineMinimum.distanceTo(coordinateSet.shoreLineMaximum);
/*     */       
/* 115 */       coordinateSet.distanceToPoint[2] = dist += (float)coordinateSet.shoreLineMaximum.distanceTo(coordinateSet.oceanLineMaximum);
/*     */       
/* 117 */       coordinateSet.distanceToPoint[3] = dist + (float)coordinateSet.oceanLineMaximum.distanceTo(coordinateSet.oceanLineMinimum);
/*     */ 
/*     */       
/* 120 */       if (i > 0) {
/* 121 */         WaterLineCoordinateSet prevCoordinateSet = this.waterLines.get(i - 1);
/* 122 */         double shoreDist = prevCoordinateSet.shoreLineMinimum.distanceTo(coordinateSet.shoreLineMinimum);
/* 123 */         double oceanDist = prevCoordinateSet.oceanLineMinimum.distanceTo(coordinateSet.oceanLineMinimum);
/* 124 */         coordinateSet.distanceToPrevious = (float)((shoreDist + oceanDist) / 2.0D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void translate(Vector2d vec) {
/* 130 */     for (WaterLineCoordinateSet coordinateSet : this.waterLines) {
/* 131 */       coordinateSet.shoreLineMinimum.addOffset(vec);
/* 132 */       coordinateSet.shoreLineMaximum.addOffset(vec);
/* 133 */       coordinateSet.oceanLineMaximum.addOffset(vec);
/* 134 */       coordinateSet.oceanLineMinimum.addOffset(vec);
/*     */     } 
/* 136 */     updateMesh();
/*     */   }
/*     */   
/*     */   public void setHeight(float height) {
/* 140 */     this.height = height;
/*     */   }
/*     */   
/*     */   public float getHeight() {
/* 144 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShoreType(int shoreType) {
/* 155 */     this.shoreType = shoreType;
/*     */   }
/*     */   
/*     */   public int getShoreType() {
/* 159 */     return this.shoreType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextureDistribution(int textureDistribution) {
/* 170 */     this.textureDistribution = textureDistribution;
/*     */   }
/*     */   
/*     */   public int getTextureDistribution() {
/* 174 */     return this.textureDistribution;
/*     */   }
/*     */   
/*     */   public void setBaseTextureScale(float baseTextureScale) {
/* 178 */     this.baseTextureScale = baseTextureScale;
/* 179 */     buildBaseTextureCoordinates();
/*     */   }
/*     */   
/*     */   public float getBaseTextureScale() {
/* 183 */     return this.baseTextureScale;
/*     */   }
/*     */   
/*     */   public void setBaseTextureOffset(float baseTextureOffsetX, float baseTextureOffsetY) {
/* 187 */     this.baseTextureOffsetX = baseTextureOffsetX;
/* 188 */     this.baseTextureOffsetY = baseTextureOffsetY;
/* 189 */     buildBaseTextureCoordinates();
/*     */   }
/*     */   
/*     */   public float getBaseTextureOffsetX() {
/* 193 */     return this.baseTextureOffsetX;
/*     */   }
/*     */   
/*     */   public float getBaseTextureOffsetY() {
/* 197 */     return this.baseTextureOffsetY;
/*     */   }
/*     */   
/*     */   public void setOverlayTextureScale(float overlayTextureScale) {
/* 201 */     this.overlayTextureScale = overlayTextureScale;
/* 202 */     buildOverlayTextureCoordinates();
/*     */   }
/*     */   
/*     */   public float getOverlayTextureScale() {
/* 206 */     return this.overlayTextureScale;
/*     */   }
/*     */   
/*     */   public void setOverlayTextureOffset(float overlayTextureOffsetX, float overlayTextureOffsetY) {
/* 210 */     this.overlayTextureOffsetX = overlayTextureOffsetX;
/* 211 */     this.overlayTextureOffsetY = overlayTextureOffsetY;
/* 212 */     buildOverlayTextureCoordinates();
/*     */   }
/*     */   
/*     */   public float getOverlayTextureOffsetX() {
/* 216 */     return this.overlayTextureOffsetX;
/*     */   }
/*     */   
/*     */   public float getOverlayTextureOffsetY() {
/* 220 */     return this.overlayTextureOffsetY;
/*     */   }
/*     */   
/*     */   public void setShoreTextureScale(float shoreTextureScale) {
/* 224 */     this.shoreTextureScale = shoreTextureScale;
/* 225 */     buildShoreTextureCoordinates();
/*     */   }
/*     */   
/*     */   public float getShoreTextureScale() {
/* 229 */     return this.shoreTextureScale;
/*     */   }
/*     */   
/*     */   public void setShoreTextureOffset(float shoreTextureOffset) {
/* 233 */     this.shoreTextureOffset = shoreTextureOffset;
/* 234 */     buildShoreTextureCoordinates();
/*     */   }
/*     */   
/*     */   public float getShoreTextureOffset() {
/* 238 */     return this.shoreTextureOffset;
/*     */   }
/*     */   
/*     */   public void setShorePosition(float shorePosition) {
/* 242 */     if (shorePosition < 0.0F || shorePosition > 1.0F) {
/* 243 */       throw new IllegalArgumentException("Shore position should be 0 < x < 1");
/*     */     }
/* 245 */     this.shorePosition = shorePosition;
/* 246 */     buildShoreTextureCoordinates();
/*     */   }
/*     */   
/*     */   public float getShorePosition() {
/* 250 */     return this.shorePosition;
/*     */   }
/*     */   
/*     */   public static RectangleWC getBounds(List<WaterLineCoordinateSet> waterLines) {
/* 254 */     boolean set = false;
/* 255 */     RectangleWC bounds = new RectangleWC();
/* 256 */     for (WaterLineCoordinateSet waterLine : waterLines) {
/* 257 */       if (!set) {
/* 258 */         bounds.setRect(waterLine.shoreLineMinimum, waterLine.shoreLineMinimum);
/* 259 */         set = true;
/*     */       } 
/*     */       
/* 262 */       bounds.add(waterLine.shoreLineMinimum);
/* 263 */       bounds.add(waterLine.shoreLineMaximum);
/* 264 */       bounds.add(waterLine.oceanLineMaximum);
/* 265 */       bounds.add(waterLine.oceanLineMinimum);
/*     */     } 
/*     */     
/* 268 */     return bounds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleWC getBounds() {
/* 277 */     if (this.waterLines == null) {
/* 278 */       return new RectangleWC();
/*     */     }
/*     */     
/* 281 */     return getBounds(this.waterLines);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void createBuffers() {
/* 287 */     this.vertBuf = BufferUtils.createVector3Buffer(this.vertBuf, this.vertexQuantity);
/* 288 */     setVertexBuffer(this.vertBuf);
/*     */     
/* 290 */     int triangleQuantity = (this.waterLines.size() - 1) * 6;
/* 291 */     setTriangleQuantity(triangleQuantity);
/* 292 */     this.indexBuffer = BufferUtils.createIntBuffer(triangleQuantity * 3);
/* 293 */     setIndexBuffer(this.indexBuffer);
/*     */     
/* 295 */     this.colorBuf = BufferUtils.createColorBuffer(this.vertexQuantity);
/* 296 */     setColorBuffer(this.colorBuf);
/*     */     
/* 298 */     this.baseTexs = BufferUtils.createVector2Buffer(this.vertexQuantity);
/* 299 */     setTextureCoords(new TexCoords(this.baseTexs), 0);
/*     */     
/* 301 */     this.overlayTexs = BufferUtils.createVector2Buffer(this.vertexQuantity);
/* 302 */     setTextureCoords(new TexCoords(this.overlayTexs), 1);
/*     */     
/* 304 */     this.shoreTexs = BufferUtils.createVector2Buffer(this.vertexQuantity);
/* 305 */     setTextureCoords(new TexCoords(this.shoreTexs), 2);
/*     */     
/* 307 */     this.normBuf = BufferUtils.createVector3Buffer(this.normBuf, this.vertexQuantity);
/* 308 */     setNormalBuffer(this.normBuf);
/*     */   }
/*     */   
/*     */   public void updateMesh() {
/* 312 */     buildVertices(0, 0);
/* 313 */     buildTriangles();
/* 314 */     buildColors();
/* 315 */     buildBaseTextureCoordinates();
/* 316 */     buildOverlayTextureCoordinates();
/* 317 */     buildShoreTextureCoordinates();
/* 318 */     buildNormals();
/*     */   }
/*     */ 
/*     */   
/*     */   private void findMeshOrigin() {
/* 323 */     Vector3f point = new Vector3f();
/* 324 */     this.origin.set(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
/* 325 */     for (int v = 0; v < this.vertexQuantity; v++) {
/* 326 */       BufferUtils.populateFromBuffer(point, this.vertBuf, v);
/* 327 */       this.origin.set(Math.min(this.origin.x, point.x), Math.min(this.origin.y, point.y), Math.min(this.origin.z, point.z));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void buildVertices(int offsetx, int offsety) {
/* 332 */     if (this.waterLines == null)
/*     */       return; 
/* 334 */     Vector3f point = new Vector3f();
/* 335 */     for (int i = 0; i < this.waterLines.size(); i++) {
/*     */       
/* 337 */       float lineHeight = this.height;
/* 338 */       if (i == 0 || i == this.waterLines.size() - 1) {
/* 339 */         lineHeight = 0.001F;
/*     */       }
/*     */       
/* 342 */       int baseVertex = i * 4;
/* 343 */       WaterLineCoordinateSet waterLine = this.waterLines.get(i);
/*     */       
/* 345 */       point.set(WorldUtils.getScreenX(waterLine.shoreLineMinimum, offsetx), 0.001F, WorldUtils.getScreenY(waterLine.shoreLineMinimum, offsety));
/*     */ 
/*     */       
/* 348 */       BufferUtils.setInBuffer(point, this.vertBuf, baseVertex);
/*     */       
/* 350 */       point.set(WorldUtils.getScreenX(waterLine.shoreLineMaximum, offsetx), lineHeight, WorldUtils.getScreenY(waterLine.shoreLineMaximum, offsety));
/*     */ 
/*     */       
/* 353 */       BufferUtils.setInBuffer(point, this.vertBuf, baseVertex + 1);
/*     */       
/* 355 */       point.set(WorldUtils.getScreenX(waterLine.oceanLineMaximum, offsetx), lineHeight, WorldUtils.getScreenY(waterLine.oceanLineMaximum, offsety));
/*     */ 
/*     */       
/* 358 */       BufferUtils.setInBuffer(point, this.vertBuf, baseVertex + 2);
/*     */       
/* 360 */       point.set(WorldUtils.getScreenX(waterLine.oceanLineMinimum, offsetx), 0.001F, WorldUtils.getScreenY(waterLine.oceanLineMinimum, offsety));
/*     */ 
/*     */       
/* 363 */       BufferUtils.setInBuffer(point, this.vertBuf, baseVertex + 3);
/*     */     } 
/*     */     
/* 366 */     findMeshOrigin();
/*     */   }
/*     */   
/*     */   private void buildTriangles() {
/* 370 */     this.indexBuffer.rewind();
/*     */ 
/*     */     
/* 373 */     for (int i = 0; i < this.waterLines.size() - 1; i++) {
/* 374 */       int dir = getWindingDirection(i);
/* 375 */       int baseVertex = i * 4;
/* 376 */       if (dir == 1) {
/*     */         
/* 378 */         this.indexBuffer.put(baseVertex + 1);
/* 379 */         this.indexBuffer.put(baseVertex + 4);
/* 380 */         this.indexBuffer.put(baseVertex);
/* 381 */         this.indexBuffer.put(baseVertex + 1);
/* 382 */         this.indexBuffer.put(baseVertex + 5);
/* 383 */         this.indexBuffer.put(baseVertex + 4);
/*     */         
/* 385 */         this.indexBuffer.put(baseVertex + 2);
/* 386 */         this.indexBuffer.put(baseVertex + 5);
/* 387 */         this.indexBuffer.put(baseVertex + 1);
/* 388 */         this.indexBuffer.put(baseVertex + 2);
/* 389 */         this.indexBuffer.put(baseVertex + 6);
/* 390 */         this.indexBuffer.put(baseVertex + 5);
/*     */         
/* 392 */         this.indexBuffer.put(baseVertex + 3);
/* 393 */         this.indexBuffer.put(baseVertex + 6);
/* 394 */         this.indexBuffer.put(baseVertex + 2);
/* 395 */         this.indexBuffer.put(baseVertex + 3);
/* 396 */         this.indexBuffer.put(baseVertex + 7);
/* 397 */         this.indexBuffer.put(baseVertex + 6);
/*     */       } else {
/*     */         
/* 400 */         this.indexBuffer.put(baseVertex);
/* 401 */         this.indexBuffer.put(baseVertex + 4);
/* 402 */         this.indexBuffer.put(baseVertex + 1);
/* 403 */         this.indexBuffer.put(baseVertex + 4);
/* 404 */         this.indexBuffer.put(baseVertex + 5);
/* 405 */         this.indexBuffer.put(baseVertex + 1);
/*     */         
/* 407 */         this.indexBuffer.put(baseVertex + 1);
/* 408 */         this.indexBuffer.put(baseVertex + 5);
/* 409 */         this.indexBuffer.put(baseVertex + 2);
/* 410 */         this.indexBuffer.put(baseVertex + 5);
/* 411 */         this.indexBuffer.put(baseVertex + 6);
/* 412 */         this.indexBuffer.put(baseVertex + 2);
/*     */         
/* 414 */         this.indexBuffer.put(baseVertex + 2);
/* 415 */         this.indexBuffer.put(baseVertex + 6);
/* 416 */         this.indexBuffer.put(baseVertex + 3);
/* 417 */         this.indexBuffer.put(baseVertex + 6);
/* 418 */         this.indexBuffer.put(baseVertex + 7);
/* 419 */         this.indexBuffer.put(baseVertex + 3);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getWindingDirection(int index) {
/* 425 */     WaterLineCoordinateSet firstLine = this.waterLines.get(index);
/* 426 */     WaterLineCoordinateSet secondLine = this.waterLines.get(index + 1);
/*     */     
/* 428 */     double orientation = MathUtils.pointOrentationToLine(secondLine.shoreLineMinimum, firstLine.shoreLineMinimum, firstLine.shoreLineMaximum);
/*     */ 
/*     */     
/* 431 */     return (orientation < 0.0D) ? 1 : 0;
/*     */   }
/*     */   
/*     */   private void buildColors() {
/* 435 */     for (int i = 0; i < this.waterLines.size(); i++) {
/* 436 */       int baseVertex = i * 4;
/* 437 */       BufferUtils.setInBuffer(new ColorRGBA(1.0F, 1.0F, 1.0F, 0.0F), this.colorBuf, baseVertex);
/* 438 */       BufferUtils.setInBuffer(new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F), this.colorBuf, baseVertex + 1);
/* 439 */       BufferUtils.setInBuffer(new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F), this.colorBuf, baseVertex + 2);
/* 440 */       BufferUtils.setInBuffer(new ColorRGBA(1.0F, 1.0F, 1.0F, 0.0F), this.colorBuf, baseVertex + 3);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildBaseTextureCoordinates() {
/* 450 */     if (this.baseTexs == null) {
/*     */       return;
/*     */     }
/*     */     
/* 454 */     this.baseTexs.clear();
/*     */     
/* 456 */     switch (this.textureDistribution) {
/*     */       case 0:
/* 458 */         buildBaseTextureCoordinatesLineDistance();
/*     */         break;
/*     */       case 1:
/* 461 */         buildBaseTextureCoordinatesAbsolute();
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void buildBaseTextureCoordinatesLineDistance() {
/* 467 */     float tx = this.baseTextureOffsetX;
/* 468 */     for (WaterLineCoordinateSet waterLine : this.waterLines) {
/* 469 */       tx += waterLine.distanceToPrevious * this.baseTextureScale;
/* 470 */       for (int y = 0; y < 4; y++) {
/* 471 */         this.baseTexs.put(tx);
/* 472 */         this.baseTexs.put(waterLine.distanceToPoint[y] * this.baseTextureScale + this.baseTextureOffsetY);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void buildBaseTextureCoordinatesAbsolute() {
/* 479 */     Vector3f point = new Vector3f();
/* 480 */     for (int v = 0; v < this.vertexQuantity; v++) {
/* 481 */       BufferUtils.populateFromBuffer(point, this.vertBuf, v);
/* 482 */       this.baseTexs.put((point.x - this.origin.x) * this.baseTextureScale + this.baseTextureOffsetX);
/* 483 */       this.baseTexs.put((point.z - this.origin.z) * this.baseTextureScale + this.baseTextureOffsetY);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void buildOverlayTextureCoordinates() {
/* 488 */     if (this.overlayTexs == null) {
/*     */       return;
/*     */     }
/*     */     
/* 492 */     this.overlayTexs.clear();
/*     */     
/* 494 */     switch (this.textureDistribution) {
/*     */       case 0:
/* 496 */         buildOverlayTextureCoordinatesLineDistance();
/*     */         break;
/*     */       case 1:
/* 499 */         buildOverlayTextureCoordinatesAbsolute();
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void buildOverlayTextureCoordinatesLineDistance() {
/* 505 */     float tx = this.overlayTextureOffsetX;
/* 506 */     for (WaterLineCoordinateSet waterLine : this.waterLines) {
/* 507 */       tx += waterLine.distanceToPrevious * this.overlayTextureScale;
/* 508 */       for (int y = 0; y < 4; y++) {
/* 509 */         this.overlayTexs.put(tx);
/* 510 */         this.overlayTexs.put(waterLine.distanceToPoint[y] * this.overlayTextureScale + this.overlayTextureOffsetY);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void buildOverlayTextureCoordinatesAbsolute() {
/* 517 */     Vector3f point = new Vector3f();
/* 518 */     for (int v = 0; v < this.vertexQuantity; v++) {
/* 519 */       BufferUtils.populateFromBuffer(point, this.vertBuf, v);
/* 520 */       this.overlayTexs.put((point.x - this.origin.x) * this.overlayTextureScale + this.overlayTextureOffsetX);
/* 521 */       this.overlayTexs.put((point.z - this.origin.z) * this.overlayTextureScale + this.overlayTextureOffsetY);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void buildShoreTextureCoordinates() {
/* 527 */     if (this.shoreTexs == null) {
/*     */       return;
/*     */     }
/*     */     
/* 531 */     this.shoreTexs.clear();
/*     */     
/* 533 */     switch (this.shoreType) {
/*     */       case 0:
/* 535 */         buildShoreTextureCoordinatesNone();
/*     */         break;
/*     */       case 1:
/* 538 */         buildShoreTextureCoordinatesShore();
/*     */         break;
/*     */       case 2:
/* 541 */         buildShoreTextureCoordinatesBoth();
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void buildShoreTextureCoordinatesNone() {
/* 547 */     float tx = this.shoreTextureOffset;
/* 548 */     for (WaterLineCoordinateSet waterLine : this.waterLines) {
/* 549 */       tx += waterLine.distanceToPrevious * this.shoreTextureScale;
/* 550 */       for (int y = 0; y < 4; y++) {
/* 551 */         this.shoreTexs.put(tx);
/* 552 */         this.shoreTexs.put(1.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void buildShoreTextureCoordinatesShore() {
/* 558 */     float tx = this.shoreTextureOffset;
/* 559 */     for (WaterLineCoordinateSet waterLine : this.waterLines) {
/* 560 */       tx += waterLine.distanceToPrevious * this.shoreTextureScale;
/*     */       
/* 562 */       this.shoreTexs.put(tx);
/* 563 */       this.shoreTexs.put(1.0F + this.shorePosition);
/*     */       
/* 565 */       this.shoreTexs.put(tx);
/* 566 */       this.shoreTexs.put(this.shorePosition);
/*     */       
/* 568 */       for (int y = 0; y < 2; y++) {
/* 569 */         this.shoreTexs.put(tx);
/* 570 */         this.shoreTexs.put(1.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void buildShoreTextureCoordinatesBoth() {
/* 576 */     float tx = this.shoreTextureOffset;
/* 577 */     for (WaterLineCoordinateSet waterLine : this.waterLines) {
/* 578 */       tx += waterLine.distanceToPrevious * this.shoreTextureScale;
/*     */       
/* 580 */       this.shoreTexs.put(tx);
/* 581 */       this.shoreTexs.put(1.0F + this.shorePosition);
/* 582 */       this.shoreTexs.put(tx);
/* 583 */       this.shoreTexs.put(this.shorePosition);
/*     */       
/* 585 */       this.shoreTexs.put(tx);
/* 586 */       this.shoreTexs.put(1.0F - this.shorePosition);
/* 587 */       this.shoreTexs.put(tx);
/* 588 */       this.shoreTexs.put(2.0F - this.shorePosition);
/*     */     } 
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
/*     */   private void buildNormals() {
/* 602 */     Vector3f rootPoint = new Vector3f();
/* 603 */     Vector3f horPoint = new Vector3f();
/* 604 */     Vector3f verPoint = new Vector3f();
/* 605 */     Vector3f tempNorm = new Vector3f();
/* 606 */     for (int x = 0; x < this.waterLines.size(); x++) {
/* 607 */       for (int y = 0; y < 4; y++) {
/* 608 */         int hor, ver; BufferUtils.populateFromBuffer(rootPoint, this.vertBuf, x * 4 + y);
/* 609 */         boolean crossNormal = true;
/*     */         
/* 611 */         if (x < this.waterLines.size() - 1) {
/* 612 */           hor = x * 4 + 4 + y;
/*     */         } else {
/* 614 */           hor = Math.max(0, x * 4 - 4 + y);
/* 615 */           crossNormal = !crossNormal;
/*     */         } 
/* 617 */         if (y < 3) {
/* 618 */           ver = x * 4 + y + 1;
/*     */         } else {
/* 620 */           ver = Math.max(0, x * 4 + y - 1);
/* 621 */           crossNormal = !crossNormal;
/*     */         } 
/*     */         
/* 624 */         BufferUtils.populateFromBuffer(horPoint, this.vertBuf, hor);
/* 625 */         BufferUtils.populateFromBuffer(verPoint, this.vertBuf, ver);
/* 626 */         if (crossNormal) {
/* 627 */           tempNorm.set(verPoint).subtractLocal(rootPoint).crossLocal(horPoint.subtractLocal(rootPoint)).normalizeLocal();
/*     */         }
/*     */         else {
/*     */           
/* 631 */           tempNorm.set(horPoint).subtractLocal(rootPoint).crossLocal(verPoint.subtractLocal(rootPoint)).normalizeLocal();
/*     */         } 
/*     */ 
/*     */         
/* 635 */         BufferUtils.setInBuffer(tempNorm, this.normBuf, x * 4 + y);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updatePlacement(int originX, int originY) {
/* 641 */     buildVertices(originX, originY);
/*     */   }
/*     */   
/*     */   public Spatial getSpatial() {
/* 645 */     return (Spatial)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContentType() {
/* 650 */     return 9;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\water\WaterLine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */