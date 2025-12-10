/*     */ package com.funcom.gameengine.view.water;
/*     */ 
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
/*     */ public class WaterPond
/*     */   extends WaterMesh
/*     */ {
/*     */   private static final int LINE_COUNT = 2;
/*     */   private float height;
/*     */   private boolean useShoreTexture;
/*     */   private float baseTextureScale;
/*     */   private float baseTextureOffsetX;
/*     */   private float baseTextureOffsetY;
/*     */   private float overlayTextureScale;
/*     */   private float overlayTextureOffsetX;
/*     */   private float overlayTextureOffsetY;
/*     */   private float shoreTextureScale;
/*     */   private float shoreTextureOffset;
/*     */   private float shorePosition;
/*     */   private List<WaterPondCoordinateSet> waterLines;
/*     */   private int vertexQuantity;
/*     */   private int centerVertex;
/*     */   private FloatBuffer vertBuf;
/*     */   private IntBuffer indexBuffer;
/*     */   private FloatBuffer baseTexs;
/*     */   private FloatBuffer overlayTexs;
/*     */   private FloatBuffer shoreTexs;
/*     */   private FloatBuffer colorBuf;
/*     */   private FloatBuffer normBuf;
/*     */   private Vector3f origin;
/*     */   
/*     */   public WaterPond() {
/*  46 */     this(0.5F);
/*     */   }
/*     */   
/*     */   public WaterPond(float height) {
/*  50 */     this(height, false, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, (List<WaterPondCoordinateSet>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WaterPond(float height, boolean useShoreTexture, float baseTextureScale, float baseTextureOffsetX, float baseTextureOffsetY, float overlayTextureScale, float overlayTextureOffsetX, float overlayTextureOffsetY, float shoreTextureScale, float shoreTextureOffset, List<WaterPondCoordinateSet> waterLines) {
/*  61 */     super("WaterPond");
/*     */     
/*  63 */     this.height = height;
/*  64 */     this.useShoreTexture = useShoreTexture;
/*  65 */     this.baseTextureScale = baseTextureScale;
/*  66 */     this.baseTextureOffsetX = baseTextureOffsetX;
/*  67 */     this.baseTextureOffsetY = baseTextureOffsetY;
/*  68 */     this.overlayTextureScale = overlayTextureScale;
/*  69 */     this.overlayTextureOffsetX = overlayTextureOffsetX;
/*  70 */     this.overlayTextureOffsetY = overlayTextureOffsetY;
/*  71 */     this.shoreTextureScale = shoreTextureScale;
/*  72 */     this.shoreTextureOffset = shoreTextureOffset;
/*     */     
/*  74 */     this.origin = new Vector3f();
/*     */     
/*  76 */     if (waterLines != null) {
/*  77 */       setWaterLines(waterLines);
/*     */     }
/*     */   }
/*     */   
/*     */   public Class<? extends Spatial> getClassTag() {
/*  82 */     return (Class)getClass();
/*     */   }
/*     */   
/*     */   public void setWaterLines(List<WaterPondCoordinateSet> waterLines) {
/*  86 */     this.waterLines = waterLines;
/*  87 */     updateWaterLines();
/*     */   }
/*     */   
/*     */   public void updateWaterLines() {
/*  91 */     this.vertexQuantity = this.waterLines.size() * 2 + 1;
/*  92 */     setVertexCount(this.vertexQuantity);
/*  93 */     createBuffers();
/*  94 */     updateMesh();
/*     */   }
/*     */   
/*     */   public List<WaterPondCoordinateSet> getWaterLines() {
/*  98 */     return this.waterLines;
/*     */   }
/*     */   
/*     */   public void setHeight(float height) {
/* 102 */     this.height = height;
/*     */   }
/*     */   
/*     */   public float getHeight() {
/* 106 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUseShoreTexture(boolean useShoreTexture) {
/* 117 */     this.useShoreTexture = useShoreTexture;
/*     */   }
/*     */   
/*     */   public boolean isUseShoreTexture() {
/* 121 */     return this.useShoreTexture;
/*     */   }
/*     */   
/*     */   public void setBaseTextureScale(float baseTextureScale) {
/* 125 */     this.baseTextureScale = baseTextureScale;
/* 126 */     buildBaseTextureCoordinates();
/*     */   }
/*     */   
/*     */   public float getBaseTextureScale() {
/* 130 */     return this.baseTextureScale;
/*     */   }
/*     */   
/*     */   public void setBaseTextureOffset(float baseTextureOffsetX, float baseTextureOffsetY) {
/* 134 */     this.baseTextureOffsetX = baseTextureOffsetX;
/* 135 */     this.baseTextureOffsetY = baseTextureOffsetY;
/* 136 */     buildBaseTextureCoordinates();
/*     */   }
/*     */   
/*     */   public float getBaseTextureOffsetX() {
/* 140 */     return this.baseTextureOffsetX;
/*     */   }
/*     */   
/*     */   public float getBaseTextureOffsetY() {
/* 144 */     return this.baseTextureOffsetY;
/*     */   }
/*     */   
/*     */   public void setOverlayTextureScale(float overlayTextureScale) {
/* 148 */     this.overlayTextureScale = overlayTextureScale;
/* 149 */     buildOverlayTextureCoordinates();
/*     */   }
/*     */   
/*     */   public float getOverlayTextureScale() {
/* 153 */     return this.overlayTextureScale;
/*     */   }
/*     */   
/*     */   public void setOverlayTextureOffset(float overlayTextureOffsetX, float overlayTextureOffsetY) {
/* 157 */     this.overlayTextureOffsetX = overlayTextureOffsetX;
/* 158 */     this.overlayTextureOffsetY = overlayTextureOffsetY;
/* 159 */     buildOverlayTextureCoordinates();
/*     */   }
/*     */   
/*     */   public float getOverlayTextureOffsetX() {
/* 163 */     return this.overlayTextureOffsetX;
/*     */   }
/*     */   
/*     */   public float getOverlayTextureOffsetY() {
/* 167 */     return this.overlayTextureOffsetY;
/*     */   }
/*     */   
/*     */   public void setShoreTextureScale(float shoreTextureScale) {
/* 171 */     this.shoreTextureScale = shoreTextureScale;
/* 172 */     buildShoreTextureCoordinates();
/*     */   }
/*     */   
/*     */   public float getShoreTextureScale() {
/* 176 */     return this.shoreTextureScale;
/*     */   }
/*     */   
/*     */   public void setShoreTextureOffset(float shoreTextureOffset) {
/* 180 */     this.shoreTextureOffset = shoreTextureOffset;
/* 181 */     buildShoreTextureCoordinates();
/*     */   }
/*     */   
/*     */   public float getShoreTextureOffset() {
/* 185 */     return this.shoreTextureOffset;
/*     */   }
/*     */   
/*     */   public void setShorePosition(float shorePosition) {
/* 189 */     if (shorePosition < 0.0F || shorePosition > 1.0F) {
/* 190 */       throw new IllegalArgumentException("Shore position should be 0 < x < 1");
/*     */     }
/* 192 */     this.shorePosition = shorePosition;
/* 193 */     buildShoreTextureCoordinates();
/*     */   }
/*     */   
/*     */   public float getShorePosition() {
/* 197 */     return this.shorePosition;
/*     */   }
/*     */   
/*     */   public static RectangleWC getBounds(List<WaterPondCoordinateSet> waterLines) {
/* 201 */     boolean set = false;
/* 202 */     RectangleWC bounds = new RectangleWC();
/* 203 */     for (WaterPondCoordinateSet waterLine : waterLines) {
/* 204 */       if (!set) {
/* 205 */         bounds.setRect(waterLine.innerPoint, waterLine.innerPoint);
/* 206 */         set = true;
/*     */       } 
/*     */       
/* 209 */       bounds.add(waterLine.innerPoint);
/* 210 */       bounds.add(waterLine.outerPoint);
/*     */     } 
/*     */     
/* 213 */     return bounds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleWC getBounds() {
/* 222 */     if (this.waterLines == null) {
/* 223 */       return new RectangleWC();
/*     */     }
/*     */     
/* 226 */     return getBounds(this.waterLines);
/*     */   }
/*     */   
/*     */   public void translate(Vector2d vec) {
/* 230 */     for (WaterPondCoordinateSet coordinateSet : this.waterLines) {
/* 231 */       coordinateSet.innerPoint.addOffset(vec);
/* 232 */       coordinateSet.outerPoint.addOffset(vec);
/*     */     } 
/* 234 */     updateMesh();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void createBuffers() {
/* 240 */     this.vertBuf = BufferUtils.createVector3Buffer(this.vertBuf, this.vertexQuantity);
/* 241 */     setVertexBuffer(this.vertBuf);
/*     */     
/* 243 */     int triangleQuantity = this.waterLines.size() * 3;
/* 244 */     setTriangleQuantity(triangleQuantity);
/* 245 */     this.indexBuffer = BufferUtils.createIntBuffer(triangleQuantity * 3);
/* 246 */     setIndexBuffer(this.indexBuffer);
/*     */     
/* 248 */     this.colorBuf = BufferUtils.createColorBuffer(this.vertexQuantity);
/* 249 */     setColorBuffer(this.colorBuf);
/*     */     
/* 251 */     this.baseTexs = BufferUtils.createVector2Buffer(this.vertexQuantity);
/* 252 */     setTextureCoords(new TexCoords(this.baseTexs), 0);
/*     */     
/* 254 */     this.overlayTexs = BufferUtils.createVector2Buffer(this.vertexQuantity);
/* 255 */     setTextureCoords(new TexCoords(this.overlayTexs), 1);
/*     */     
/* 257 */     this.shoreTexs = BufferUtils.createVector2Buffer(this.vertexQuantity);
/* 258 */     setTextureCoords(new TexCoords(this.shoreTexs), 2);
/*     */     
/* 260 */     this.normBuf = BufferUtils.createVector3Buffer(this.normBuf, this.vertexQuantity);
/* 261 */     setNormalBuffer(this.normBuf);
/*     */   }
/*     */   
/*     */   public void updateMesh() {
/* 265 */     buildVertices(0, 0);
/* 266 */     buildTriangles();
/* 267 */     buildColors();
/* 268 */     buildBaseTextureCoordinates();
/* 269 */     buildOverlayTextureCoordinates();
/* 270 */     buildShoreTextureCoordinates();
/* 271 */     buildNormals();
/*     */   }
/*     */ 
/*     */   
/*     */   private void findMeshOrigin() {
/* 276 */     Vector3f point = new Vector3f();
/* 277 */     this.origin.set(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
/* 278 */     for (int v = 0; v < this.vertexQuantity; v++) {
/* 279 */       BufferUtils.populateFromBuffer(point, this.vertBuf, v);
/* 280 */       this.origin.set(Math.min(this.origin.x, point.x), Math.min(this.origin.y, point.y), Math.min(this.origin.z, point.z));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildVertices(int offsetx, int offsety) {
/* 291 */     if (this.waterLines == null)
/*     */       return; 
/* 293 */     Vector3f sum = new Vector3f();
/* 294 */     Vector3f point = new Vector3f();
/* 295 */     for (int i = 0; i < this.waterLines.size(); i++) {
/* 296 */       int baseVertex = i * 2;
/* 297 */       WaterPondCoordinateSet waterLine = this.waterLines.get(i);
/*     */       
/* 299 */       point.set(WorldUtils.getScreenX(waterLine.outerPoint, offsetx), 0.001F, WorldUtils.getScreenY(waterLine.outerPoint, offsety));
/*     */ 
/*     */       
/* 302 */       BufferUtils.setInBuffer(point, this.vertBuf, baseVertex);
/* 303 */       sum.addLocal(point);
/*     */       
/* 305 */       point.set(WorldUtils.getScreenX(waterLine.innerPoint, offsetx), this.height, WorldUtils.getScreenY(waterLine.innerPoint, offsety));
/*     */ 
/*     */       
/* 308 */       BufferUtils.setInBuffer(point, this.vertBuf, baseVertex + 1);
/* 309 */       sum.addLocal(point);
/*     */     } 
/*     */ 
/*     */     
/* 313 */     sum.divideLocal(this.waterLines.size() * 2.0F);
/* 314 */     this.centerVertex = this.waterLines.size() * 2;
/* 315 */     sum.setY(this.height);
/* 316 */     BufferUtils.setInBuffer(sum, this.vertBuf, this.centerVertex);
/*     */     
/* 318 */     findMeshOrigin();
/*     */   }
/*     */ 
/*     */   
/*     */   private void buildTriangles() {
/* 323 */     if (this.waterLines.size() < 2) {
/*     */       return;
/*     */     }
/* 326 */     this.indexBuffer.rewind();
/*     */ 
/*     */     
/* 329 */     for (int i = 0; i < this.waterLines.size() - 1; i++) {
/* 330 */       int j = i * 2;
/*     */       
/* 332 */       this.indexBuffer.put(j + 1);
/* 333 */       this.indexBuffer.put(j + 2);
/* 334 */       this.indexBuffer.put(j);
/* 335 */       this.indexBuffer.put(j + 1);
/* 336 */       this.indexBuffer.put(j + 3);
/* 337 */       this.indexBuffer.put(j + 2);
/*     */       
/* 339 */       this.indexBuffer.put(j + 3);
/* 340 */       this.indexBuffer.put(j + 1);
/* 341 */       this.indexBuffer.put(this.centerVertex);
/*     */     } 
/*     */ 
/*     */     
/* 345 */     int baseVertex = this.waterLines.size() * 2 - 2;
/*     */     
/* 347 */     this.indexBuffer.put(baseVertex + 1);
/* 348 */     this.indexBuffer.put(0);
/* 349 */     this.indexBuffer.put(baseVertex);
/* 350 */     this.indexBuffer.put(baseVertex + 1);
/* 351 */     this.indexBuffer.put(1);
/* 352 */     this.indexBuffer.put(0);
/*     */     
/* 354 */     this.indexBuffer.put(1);
/* 355 */     this.indexBuffer.put(baseVertex + 1);
/* 356 */     this.indexBuffer.put(this.centerVertex);
/*     */   }
/*     */   
/*     */   private void buildColors() {
/* 360 */     for (int i = 0; i < this.waterLines.size(); i++) {
/* 361 */       int baseVertex = i * 2;
/* 362 */       BufferUtils.setInBuffer(new ColorRGBA(1.0F, 1.0F, 1.0F, 0.0F), this.colorBuf, baseVertex);
/* 363 */       BufferUtils.setInBuffer(new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F), this.colorBuf, baseVertex + 1);
/*     */     } 
/*     */ 
/*     */     
/* 367 */     BufferUtils.setInBuffer(new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F), this.colorBuf, this.centerVertex);
/*     */   }
/*     */ 
/*     */   
/*     */   private void buildBaseTextureCoordinates() {
/* 372 */     if (this.baseTexs == null) {
/*     */       return;
/*     */     }
/*     */     
/* 376 */     this.baseTexs.clear();
/*     */ 
/*     */     
/* 379 */     Vector3f point = new Vector3f();
/* 380 */     for (int v = 0; v < this.vertexQuantity; v++) {
/* 381 */       BufferUtils.populateFromBuffer(point, this.vertBuf, v);
/* 382 */       this.baseTexs.put((point.x - this.origin.x) * this.baseTextureScale + this.baseTextureOffsetX);
/* 383 */       this.baseTexs.put((point.z - this.origin.z) * this.baseTextureScale + this.baseTextureOffsetY);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void buildOverlayTextureCoordinates() {
/* 388 */     if (this.overlayTexs == null) {
/*     */       return;
/*     */     }
/*     */     
/* 392 */     this.overlayTexs.clear();
/*     */ 
/*     */     
/* 395 */     Vector3f point = new Vector3f();
/* 396 */     for (int v = 0; v < this.vertexQuantity; v++) {
/* 397 */       BufferUtils.populateFromBuffer(point, this.vertBuf, v);
/* 398 */       this.overlayTexs.put((point.x - this.origin.x) * this.overlayTextureScale + this.overlayTextureOffsetX);
/* 399 */       this.overlayTexs.put((point.z - this.origin.z) * this.overlayTextureScale + this.overlayTextureOffsetY);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void buildShoreTextureCoordinates() {
/* 405 */     if (this.shoreTexs == null) {
/*     */       return;
/*     */     }
/*     */     
/* 409 */     this.shoreTexs.clear();
/*     */     
/* 411 */     if (this.useShoreTexture) {
/* 412 */       for (int x = 0; x < this.waterLines.size(); x++) {
/* 413 */         this.shoreTexs.put(x * this.shoreTextureScale + this.shoreTextureOffset);
/* 414 */         this.shoreTexs.put(1.0F + this.shorePosition);
/* 415 */         this.shoreTexs.put(x * this.shoreTextureScale + this.shoreTextureOffset);
/* 416 */         this.shoreTexs.put(this.shorePosition);
/*     */       } 
/*     */     } else {
/* 419 */       for (WaterPondCoordinateSet waterLine : this.waterLines) {
/* 420 */         this.shoreTexs.put(0.0F);
/* 421 */         this.shoreTexs.put(1.0F);
/* 422 */         this.shoreTexs.put(0.0F);
/* 423 */         this.shoreTexs.put(1.0F);
/*     */       } 
/*     */     } 
/*     */     
/* 427 */     this.shoreTexs.put(0.0F);
/* 428 */     this.shoreTexs.put(1.0F);
/*     */   }
/*     */   
/*     */   private void buildNormals() {
/* 432 */     if (this.waterLines.size() < 2) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 437 */     Vector3f rootPoint = new Vector3f();
/* 438 */     Vector3f horPoint = new Vector3f();
/* 439 */     Vector3f verPoint = new Vector3f();
/* 440 */     Vector3f tempNorm = new Vector3f();
/* 441 */     for (int x = 0; x < this.waterLines.size(); x++) {
/* 442 */       for (int y = 0; y < 2; y++) {
/* 443 */         int hor, ver; BufferUtils.populateFromBuffer(rootPoint, this.vertBuf, x * 2 + y);
/* 444 */         boolean crossNormal = true;
/*     */         
/* 446 */         if (x < this.waterLines.size() - 1) {
/* 447 */           hor = x * 2 + 2 + y;
/*     */         } else {
/* 449 */           hor = x * 2 - 2 + y;
/* 450 */           crossNormal = !crossNormal;
/*     */         } 
/* 452 */         if (y < 1) {
/* 453 */           ver = x * 2 + y + 1;
/*     */         } else {
/* 455 */           ver = x * 2 + y - 1;
/* 456 */           crossNormal = !crossNormal;
/*     */         } 
/*     */         
/* 459 */         BufferUtils.populateFromBuffer(horPoint, this.vertBuf, hor);
/* 460 */         BufferUtils.populateFromBuffer(verPoint, this.vertBuf, ver);
/* 461 */         if (crossNormal) {
/* 462 */           tempNorm.set(verPoint).subtractLocal(rootPoint).crossLocal(horPoint.subtractLocal(rootPoint)).normalizeLocal();
/*     */         }
/*     */         else {
/*     */           
/* 466 */           tempNorm.set(horPoint).subtractLocal(rootPoint).crossLocal(verPoint.subtractLocal(rootPoint)).normalizeLocal();
/*     */         } 
/*     */ 
/*     */         
/* 470 */         BufferUtils.setInBuffer(tempNorm, this.normBuf, x * 2 + y);
/*     */       } 
/*     */     } 
/*     */     
/* 474 */     tempNorm.set(0.0F, 1.0F, 0.0F);
/* 475 */     BufferUtils.setInBuffer(tempNorm, this.normBuf, this.waterLines.size() * 2);
/*     */   }
/*     */   
/*     */   public void updatePlacement(int originX, int originY) {
/* 479 */     buildVertices(originX, originY);
/*     */   }
/*     */   
/*     */   public Spatial getSpatial() {
/* 483 */     return (Spatial)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContentType() {
/* 488 */     return 10;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\water\WaterPond.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */