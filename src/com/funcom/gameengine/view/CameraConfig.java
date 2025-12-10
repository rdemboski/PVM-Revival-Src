/*     */ package com.funcom.gameengine.view;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.WorldUtils;
/*     */ import com.jme.math.FastMath;
/*     */ import com.jme.math.Matrix4f;
/*     */ import com.jme.math.Quaternion;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.Camera;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
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
/*     */ public abstract class CameraConfig
/*     */ {
/*     */   public static final float INV_ZOOM = 3.5F;
/*     */   public static final float DEFAULT_CAMERA_ANGLE = 30.0F;
/*     */   public static final float OFFSET_ANGLE = 45.0F;
/*  34 */   protected float offsetAngle = 45.0F;
/*     */ 
/*     */   
/*     */   public static final float DEFAULT_FIELD_OF_VIEW = 8.0F;
/*     */   
/*     */   public static final float PERSPECTIVE_DISTANCE_CORRECTION = 1.8F;
/*     */   
/*     */   public static final float PARALLELL_FRUSTRUM_CORRECTION = 0.009F;
/*     */   
/*     */   public static final float FAR_OFFSET = 2.5F;
/*     */   
/*     */   protected static CameraConfig INSTANCE;
/*     */   
/*     */   protected float cameraAngle;
/*     */   
/*     */   private DisplaySystem display;
/*     */   
/*     */   protected Camera camera;
/*     */   
/*     */   protected WorldCoordinate cameraCoordinate;
/*     */   
/*     */   protected float zoomFactor;
/*     */   
/*     */   private Set<CameraListener> listeners;
/*     */   
/*     */   protected Quaternion cameraRotation;
/*     */   
/*     */   protected Matrix4f modelViewMatrix;
/*     */   
/*     */   private Matrix4f projectionMatrix;
/*     */   
/*     */   private float fov;
/*     */   
/*  67 */   protected CameraShakeEffect cameraShakeEffect = null;
/*     */   protected float xOffset;
/*     */   protected float yOffset;
/*     */   
/*     */   protected CameraConfig() {
/*  72 */     this.cameraAngle = 30.0F;
/*  73 */     this.fov = 8.0F;
/*  74 */     this.cameraCoordinate = new WorldCoordinate();
/*  75 */     this.display = DisplaySystem.getDisplaySystem();
/*  76 */     this.zoomFactor = 0.35F;
/*  77 */     this.cameraRotation = new Quaternion();
/*  78 */     updateCameraRotation();
/*     */   }
/*     */   
/*     */   public static CameraConfig instance() {
/*  82 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   public void addListener(CameraListener listener) {
/*  86 */     if (this.listeners == null) {
/*  87 */       this.listeners = new HashSet<CameraListener>();
/*     */     }
/*     */     
/*  90 */     this.listeners.add(listener);
/*     */   }
/*     */   
/*     */   public void removeListener(CameraListener listener) {
/*  94 */     if (this.listeners == null) {
/*     */       return;
/*     */     }
/*     */     
/*  98 */     this.listeners.remove(listener);
/*     */   }
/*     */   
/*     */   public void fireCameraMovedListener(WorldCoordinate worldCoordinate) {
/* 102 */     if (this.listeners == null) {
/*     */       return;
/*     */     }
/*     */     
/* 106 */     for (CameraListener listener : this.listeners) {
/* 107 */       listener.cameraMoved(worldCoordinate);
/*     */     }
/*     */   }
/*     */   
/*     */   public void fireCameraZoomListener(float zoomFactor) {
/* 112 */     if (this.listeners == null) {
/*     */       return;
/*     */     }
/*     */     
/* 116 */     for (CameraListener listener : this.listeners) {
/* 117 */       listener.cameraZoomChanged(zoomFactor);
/*     */     }
/*     */   }
/*     */   
/*     */   public float getAspect() {
/* 122 */     return this.display.getWidth() / this.display.getHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void updateCameraRotation();
/*     */ 
/*     */   
/*     */   public void setPerspectiveProjection() {
/* 131 */     float aspect = getAspect();
/*     */     
/* 133 */     this.camera.setParallelProjection(false);
/*     */     
/* 135 */     float znear = 0.1F;
/* 136 */     float zfar = calculateFarPlane();
/* 137 */     this.camera.setFrustumPerspective(this.fov, aspect, znear, zfar);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     float f = znear / this.camera.getFrustumTop();
/* 144 */     this.projectionMatrix = new Matrix4f(f / aspect, 0.0F, 0.0F, 0.0F, 0.0F, f, 0.0F, 0.0F, 0.0F, 0.0F, (zfar + znear) / (znear - zfar), 2.0F * zfar * znear / (znear - zfar), 0.0F, 0.0F, -1.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     this.camera.update();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParallelProjection() {
/* 158 */     this.camera.setParallelProjection(true);
/* 159 */     float znear = 0.0F;
/* 160 */     float zfar = calculateFarPlane();
/* 161 */     float left = getLeftParallelProjection();
/* 162 */     float right = getRightParallelProjection();
/* 163 */     float bottom = getBottomParallelProjection();
/* 164 */     float top = getTopParallelProjection();
/*     */     
/* 166 */     this.camera.setFrustum(znear, zfar, left, right, bottom, top);
/*     */ 
/*     */ 
/*     */     
/* 170 */     this.projectionMatrix = new Matrix4f(2.0F / (right - left), 0.0F, 0.0F, -(right + left) / (right - left), 0.0F, 2.0F / (top - bottom), 0.0F, -(top + bottom) / (top - bottom), 0.0F, 0.0F, -2.0F / (zfar - znear), -(zfar + znear) / (zfar - znear), 0.0F, 0.0F, 0.0F, 1.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     this.camera.update();
/*     */   }
/*     */   
/*     */   protected float getTopParallelProjection() {
/* 179 */     return this.display.getHeight() * 0.009F * this.zoomFactor;
/*     */   }
/*     */   
/*     */   protected float getBottomParallelProjection() {
/* 183 */     return -this.display.getHeight() * 0.009F * this.zoomFactor;
/*     */   }
/*     */   
/*     */   protected float getRightParallelProjection() {
/* 187 */     return this.display.getWidth() * 0.009F * this.zoomFactor;
/*     */   }
/*     */   
/*     */   protected float getLeftParallelProjection() {
/* 191 */     return -this.display.getWidth() * 0.009F * this.zoomFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float calculateFarPlane() {
/* 201 */     float height = getCameraHeight();
/* 202 */     float topAngle = 90.0F - this.cameraAngle + this.fov / 2.0F;
/* 203 */     topAngle = Math.abs(topAngle);
/* 204 */     for (; topAngle > 90.0F; topAngle -= 90.0F);
/* 205 */     float far = height / FastMath.cos(topAngle * 0.017453292F);
/*     */     
/* 207 */     return far * 2.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAbsoluteDistance() {
/* 216 */     return this.zoomFactor * 1.8F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCameraHeight() {
/* 226 */     return FastMath.sin(this.cameraAngle * 0.017453292F) * getAbsoluteDistance();
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
/*     */   public void moveCameraTo(WorldCoordinate worldCoordinate) {
/* 238 */     this.cameraCoordinate.set(worldCoordinate);
/* 239 */     updateCameraPosition();
/* 240 */     fireCameraMovedListener(worldCoordinate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateCameraPosition() {
/* 247 */     if (this.camera == null)
/*     */       return; 
/* 249 */     float x = WorldUtils.getScreenX(this.cameraCoordinate);
/* 250 */     float y = WorldUtils.getScreenY(this.cameraCoordinate);
/* 251 */     if (this.cameraShakeEffect != null) {
/* 252 */       this.cameraShakeEffect.update();
/* 253 */       x += this.cameraShakeEffect.getXOffset();
/* 254 */       y += this.cameraShakeEffect.getYOffset();
/*     */     } 
/* 256 */     x += this.xOffset;
/* 257 */     y += this.yOffset;
/* 258 */     float distance = getAbsoluteDistance();
/* 259 */     Vector3f cameraRelative = new Vector3f(0.0F, 0.0F, distance);
/* 260 */     Vector3f cameraPosition = this.cameraRotation.mult(cameraRelative).add(x, 0.0F, y);
/*     */     
/* 262 */     this.camera.setLocation(cameraPosition);
/*     */     
/* 264 */     Vector3f center = new Vector3f(x, 0.0F, y);
/* 265 */     Vector3f up = Vector3f.UNIT_Y;
/* 266 */     this.camera.lookAt(center, up);
/* 267 */     this.camera.update();
/*     */ 
/*     */     
/* 270 */     Vector3f L = center.subtract(cameraPosition).normalize();
/* 271 */     Vector3f S = L.cross(up).normalize();
/* 272 */     Vector3f Un = S.cross(L);
/*     */     
/* 274 */     Matrix4f rotationMatrix = new Matrix4f(S.x, S.y, S.z, 0.0F, Un.x, Un.y, Un.z, 0.0F, -L.x, -L.y, -L.z, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 280 */     Matrix4f translationMatrix = new Matrix4f(1.0F, 0.0F, 0.0F, -cameraPosition.x, 0.0F, 1.0F, 0.0F, -cameraPosition.y, 0.0F, 0.0F, 1.0F, -cameraPosition.z, 0.0F, 0.0F, 0.0F, 1.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 285 */     this.modelViewMatrix = rotationMatrix.mult(translationMatrix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateProjection() {
/* 293 */     if (this.camera.isParallelProjection()) {
/* 294 */       setParallelProjection();
/*     */     } else {
/* 296 */       setPerspectiveProjection();
/*     */     } 
/*     */ 
/*     */     
/* 300 */     updateCameraPosition();
/*     */   }
/*     */   
/*     */   public WorldCoordinate getCameraCoordinate() {
/* 304 */     return this.cameraCoordinate;
/*     */   }
/*     */   
/*     */   public void setCamera(Camera camera) {
/* 308 */     this.camera = camera;
/*     */   }
/*     */   
/*     */   public boolean isParallel() {
/* 312 */     return this.camera.isParallelProjection();
/*     */   }
/*     */   
/*     */   public boolean isPerspective() {
/* 316 */     return !isParallel();
/*     */   }
/*     */   
/*     */   public void setZoomFactor(float zoomFactor) {
/* 320 */     this.zoomFactor = zoomFactor;
/* 321 */     updateProjection();
/* 322 */     fireCameraZoomListener(zoomFactor);
/*     */   }
/*     */   
/*     */   public float getZoomFactor() {
/* 326 */     return this.zoomFactor;
/*     */   }
/*     */   
/*     */   public void setCameraAngle(float cameraAngle) {
/* 330 */     this.cameraAngle = cameraAngle;
/* 331 */     updateCameraRotation();
/*     */   }
/*     */   
/*     */   public float getCameraAngle() {
/* 335 */     return this.cameraAngle;
/*     */   }
/*     */   
/*     */   public Matrix4f getModelViewMatrix() {
/* 339 */     return this.modelViewMatrix;
/*     */   }
/*     */   
/*     */   public Matrix4f getProjectionMatrix() {
/* 343 */     return this.projectionMatrix;
/*     */   }
/*     */   
/*     */   public void setFov(float fov) {
/* 347 */     this.fov = fov;
/*     */   }
/*     */   
/*     */   public float getFov() {
/* 351 */     return this.fov;
/*     */   }
/*     */   
/*     */   public void startShakeEffect(CameraShakeEffect cameraShakeEffect) {
/* 355 */     this.cameraShakeEffect = cameraShakeEffect;
/*     */   }
/*     */   
/*     */   public void endShakeEffect() {
/* 359 */     this.cameraShakeEffect = null;
/*     */   }
/*     */   
/*     */   public void setOffestX(float xOffset) {
/* 363 */     this.xOffset = xOffset;
/*     */   }
/*     */   
/*     */   public void setOffestY(float yOffset) {
/* 367 */     this.yOffset = yOffset;
/*     */   }
/*     */   
/*     */   public float getXOffset() {
/* 371 */     return this.xOffset;
/*     */   }
/*     */   
/*     */   public float getYOffset() {
/* 375 */     return this.yOffset;
/*     */   }
/*     */   
/*     */   public float getOffsetAngle() {
/* 379 */     return this.offsetAngle;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOffsetAngle(float offsetAngle) {
/* 384 */     this.offsetAngle = offsetAngle;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\CameraConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */