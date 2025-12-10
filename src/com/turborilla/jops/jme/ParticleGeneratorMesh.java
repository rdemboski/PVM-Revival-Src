/*     */ package com.turborilla.jops.jme;
/*     */ import com.funcom.util.DebugManager;
/*     */ import com.jme.bounding.BoundingBox;
/*     */ import com.jme.bounding.BoundingVolume;
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.math.FastMath;
/*     */ import com.jme.math.Quaternion;
/*     */ import com.jme.math.Vector2f;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.Camera;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.scene.Controller;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.TexCoords;
/*     */ import com.jme.scene.TriMesh;
/*     */ import com.jme.scene.state.BlendState;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.scene.state.ZBufferState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jme.util.geom.BufferUtils;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.softmed.jops.Generator;
/*     */ import org.softmed.jops.GeneratorBehaviour;
/*     */ import org.softmed.jops.Particle;
/*     */ 
/*     */ public class ParticleGeneratorMesh extends TriMesh {
/*  35 */   private static final Logger LOGGER = Logger.getLogger(ParticleGeneratorMesh.class.getName());
/*     */   
/*     */   private static long lastBlendFuncWarning;
/*     */   private Generator generator;
/*     */   private FloatBuffer vertexBuffer;
/*     */   private FloatBuffer textureBuffer;
/*     */   private FloatBuffer colorBuffer;
/*     */   private IntBuffer indicesBuffer;
/*     */   private Camera camera;
/*  44 */   private Quaternion worldRotation = new Quaternion();
/*     */   
/*  46 */   private Vector3f position = new Vector3f();
/*  47 */   private Vector3f cUp = new Vector3f();
/*  48 */   private Vector3f cRight = new Vector3f();
/*  49 */   private Vector3f up = new Vector3f();
/*  50 */   private Vector3f right = new Vector3f();
/*  51 */   private Vector3f mUp = new Vector3f();
/*  52 */   private Vector3f mRight = new Vector3f();
/*  53 */   private Vector3f topLeft = new Vector3f();
/*  54 */   private Vector3f bottomLeft = new Vector3f();
/*  55 */   private Vector3f bottomRight = new Vector3f();
/*  56 */   private Vector2f bl = new Vector2f();
/*  57 */   private Vector2f br = new Vector2f();
/*  58 */   private Vector2f tl = new Vector2f();
/*  59 */   private ColorRGBA color = new ColorRGBA();
/*     */   
/*     */   public ParticleGeneratorMesh(Generator generator) {
/*  62 */     this.generator = generator;
/*  63 */     init();
/*     */   }
/*     */   
/*     */   public ParticleGeneratorMesh(String name, Generator generator) {
/*  67 */     super(name);
/*  68 */     this.generator = generator;
/*  69 */     init();
/*     */   }
/*     */   
/*     */   public Class<? extends Spatial> getClassTag() {
/*  73 */     return (Class)getClass();
/*     */   }
/*     */   
/*     */   private void init() {
/*  77 */     setupAlphaState();
/*  78 */     setupTextureState();
/*  79 */     setupZBufferState();
/*  80 */     setRenderQueueMode(3);
/*  81 */     setLightCombineMode(Spatial.LightCombineMode.Off);
/*  82 */     setTextureCombineMode(Spatial.TextureCombineMode.Replace);
/*     */     
/*  84 */     GeneratorBehaviour generatorBehaviour = this.generator.getGb();
/*  85 */     int numTriangles = generatorBehaviour.getNumber();
/*  86 */     int numVertices = 3 * numTriangles;
/*     */     
/*  88 */     setupVertexColorTextureBuffers(numVertices);
/*  89 */     setupIndicesBuffer(numTriangles);
/*  90 */     configureCustomTriangleBatch();
/*  91 */     updateRenderState();
/*  92 */     addController(new ParticleUpdateController(this));
/*     */ 
/*     */     
/*  95 */     setModelBound((BoundingVolume)new BoundingBox(new Vector3f(), 0.1F, 0.1F, 0.1F));
/*     */   }
/*     */   
/*     */   private void setupIndicesBuffer(int numTriangles) {
/*  99 */     this.indicesBuffer = BufferUtils.createIntBuffer(numTriangles * 3);
/* 100 */     int[] temp = new int[numTriangles * 3];
/* 101 */     for (int i = 0; i < numTriangles; i++) {
/* 102 */       temp[i * 3] = i * 3 + 2;
/* 103 */       temp[i * 3 + 1] = i * 3 + 1;
/* 104 */       temp[i * 3 + 2] = i * 3;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 109 */     this.indicesBuffer.put(temp);
/* 110 */     this.indicesBuffer.flip();
/*     */   }
/*     */   
/*     */   private void setupVertexColorTextureBuffers(int numVertices) {
/* 114 */     this.vertexBuffer = BufferUtils.createVector3Buffer(numVertices);
/* 115 */     this.textureBuffer = BufferUtils.createVector2Buffer(numVertices);
/* 116 */     this.colorBuffer = BufferUtils.createColorBuffer(numVertices);
/* 117 */     float[] vert = new float[numVertices * 3];
/* 118 */     float[] text = new float[numVertices * 2];
/* 119 */     float[] color = new float[numVertices * 4];
/* 120 */     Arrays.fill(color, 1.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     this.vertexBuffer.put(vert);
/* 127 */     this.textureBuffer.put(text);
/* 128 */     this.colorBuffer.put(color);
/* 129 */     this.vertexBuffer.flip();
/* 130 */     this.textureBuffer.flip();
/* 131 */     this.colorBuffer.flip();
/*     */   }
/*     */   
/*     */   private void configureCustomTriangleBatch() {
/* 135 */     setVertexBuffer(this.vertexBuffer);
/* 136 */     setTextureCoords(new TexCoords(this.textureBuffer), 0);
/* 137 */     setColorBuffer(this.colorBuffer);
/* 138 */     setIndexBuffer(this.indicesBuffer);
/*     */   }
/*     */   
/*     */   private void setupZBufferState() {
/* 142 */     ZBufferState zs = DisplaySystem.getDisplaySystem().getRenderer().createZBufferState();
/* 143 */     zs.setEnabled(true);
/* 144 */     zs.setWritable(false);
/* 145 */     setRenderState((RenderState)zs);
/*     */   }
/*     */   
/*     */   private void setupTextureState() {
/* 149 */     TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/* 150 */     ts.setEnabled(true);
/* 151 */     String textureName = this.generator.getRender().getTextureName();
/* 152 */     Texture tex = ParticleTextureLoaderInstance.getInstance().loadTexture(textureName);
/*     */     
/* 154 */     ts.setTexture(tex);
/* 155 */     setRenderState((RenderState)ts);
/*     */   }
/*     */   
/*     */   private void setupAlphaState() {
/* 159 */     BlendState as = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
/* 160 */     as.setEnabled(true);
/* 161 */     as.setBlendEnabled(true);
/* 162 */     as.setSourceFunction(ConversionUtil.toMonkeyBlendFactorSourceFunction(this.generator.getRender().getSourceFactor()));
/* 163 */     BlendState.DestinationFunction destinationFunction = ConversionUtil.toMonkeyBlendFactorDestinationFunction(this.generator.getRender().getDestinationFactor());
/* 164 */     Level level = Level.WARN;
/* 165 */     if (DebugManager.getInstance().isDebugEnabled()) {
/* 166 */       level = Level.ERROR;
/*     */     }
/* 168 */     if (destinationFunction == BlendState.DestinationFunction.DestinationAlpha) {
/* 169 */       destinationFunction = BlendState.DestinationFunction.One;
/* 170 */       if (System.currentTimeMillis() - lastBlendFuncWarning > 10000L) {
/* 171 */         LOGGER.log((Priority)level, "PARTICLE USING DestinationAlpha, NOT SUPPORTED ON INTEL. SETTING TO 'ONE'");
/* 172 */         lastBlendFuncWarning = System.currentTimeMillis();
/*     */       } 
/* 174 */     } else if (destinationFunction == BlendState.DestinationFunction.OneMinusDestinationAlpha && 
/* 175 */       System.currentTimeMillis() - lastBlendFuncWarning > 10000L) {
/* 176 */       LOGGER.log((Priority)level, "PARTICLE USING OneMinusDestinationAlpha, _probably_ NOT SUPPORTED ON INTEL. IF PARTICLE LOOKS WRONG, THEN YOU KNOW WHY :)");
/* 177 */       lastBlendFuncWarning = System.currentTimeMillis();
/*     */     } 
/*     */     
/* 180 */     if (destinationFunction == BlendState.DestinationFunction.DestinationAlpha) {
/* 181 */       destinationFunction = BlendState.DestinationFunction.One;
/*     */     }
/* 183 */     as.setDestinationFunction(destinationFunction);
/*     */     
/* 185 */     as.setTestEnabled(true);
/* 186 */     as.setReference(0.01F);
/* 187 */     as.setTestFunction(BlendState.TestFunction.GreaterThan);
/* 188 */     setRenderState((RenderState)as);
/*     */   }
/*     */   
/*     */   private void buildParticles() {
/* 192 */     List<Particle> particles = this.generator.getParticles();
/* 193 */     if (!this.generator.isAbsoluteParticleAngle()) {
/* 194 */       setCameraVectors();
/*     */     }
/* 196 */     int alive = 0;
/* 197 */     this.vertexBuffer.clear();
/* 198 */     float[] vertArray = new float[particles.size() * 9];
/* 199 */     float[] colorArray = new float[particles.size() * 12];
/* 200 */     float[] textArray = new float[particles.size() * 6];
/*     */     
/* 202 */     for (Particle particle : particles) {
/* 203 */       if (particle.life < 0.0F && this.generator.isKillParticles()) {
/*     */         continue;
/*     */       }
/* 206 */       int vIndex = alive * 3;
/* 207 */       buildTriangle(vertArray, vIndex, particle);
/* 208 */       setColor(colorArray, vIndex, particle);
/* 209 */       setTextureCoords(textArray, vIndex, particle);
/* 210 */       alive++;
/*     */     } 
/* 212 */     if (alive > 0) {
/* 213 */       this.vertexBuffer.put(vertArray);
/* 214 */       this.colorBuffer.position(0);
/* 215 */       this.colorBuffer.put(colorArray);
/* 216 */       this.textureBuffer.position(0);
/* 217 */       this.textureBuffer.put(textArray);
/* 218 */       this.bound.computeFromPoints(this.vertexBuffer);
/*     */     } 
/* 220 */     this.indicesBuffer.limit(alive * 3);
/* 221 */     setIndexBuffer(this.indicesBuffer);
/* 222 */     this.vertexBuffer.limit(alive * 3 * 3);
/*     */     
/* 224 */     updateWorldBound();
/*     */   }
/*     */   
/*     */   public boolean hasLivingParticles() {
/* 228 */     if (!this.generator.isKillParticles()) return true; 
/* 229 */     for (Particle particle : this.generator.getParticles()) {
/* 230 */       if (particle.life > 0.0F) {
/* 231 */         return true;
/*     */       }
/*     */     } 
/* 234 */     return false;
/*     */   }
/*     */   
/*     */   public void dieOut() {
/* 238 */     this.generator.setRegenerateParticles(false);
/*     */   }
/*     */   
/*     */   private void setCameraVectors() {
/* 242 */     if (this.camera != null) {
/* 243 */       this.cUp.set(this.camera.getUp());
/* 244 */       this.cRight.set(this.camera.getLeft());
/* 245 */       this.cUp.normalizeLocal().multLocal(0.5F);
/* 246 */       this.cRight.normalizeLocal().multLocal(-0.5F);
/*     */     } else {
/* 248 */       this.cUp.set(0.0F, 0.5F, 0.0F);
/* 249 */       this.cRight.set(0.5F, 0.0F, 0.0F);
/*     */     } 
/* 251 */     this.worldRotation.set(getWorldRotation());
/* 252 */     this.worldRotation.inverseLocal();
/* 253 */     this.worldRotation.multLocal(this.cUp);
/* 254 */     this.worldRotation.multLocal(this.cRight);
/*     */   }
/*     */   
/*     */   private void buildTriangle(float[] vb, int vIndex, Particle particle) {
/* 258 */     if (this.generator.isAbsoluteParticleAngle()) {
/* 259 */       buildOrientedTriangle(vb, vIndex, particle);
/*     */     } else {
/* 261 */       buildBillboardTriangle(vb, vIndex, particle);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void buildOrientedTriangle(float[] vb, int vIndex, Particle particle) {
/* 266 */     float size = particle.size;
/* 267 */     float width = particle.width * 0.5F;
/* 268 */     float height = particle.height * 0.5F;
/*     */     
/* 270 */     float correctedVAngle = particle.angleV - 1.5707964F;
/*     */     
/* 272 */     this.position.set(particle.position.getX(), particle.position.getY(), particle.position.getZ());
/*     */     
/* 274 */     float sinCV = FastMath.sin(correctedVAngle);
/* 275 */     float sinH = FastMath.sin(particle.angleH);
/* 276 */     float sinV = FastMath.sin(particle.angleV);
/* 277 */     float cosH = FastMath.cos(particle.angleH);
/*     */     
/* 279 */     this.up.setX(cosH * sinCV);
/* 280 */     this.up.setZ(sinH * sinCV);
/* 281 */     this.up.setY(FastMath.cos(correctedVAngle));
/*     */     
/* 283 */     this.right.setX(cosH * sinV);
/* 284 */     this.right.setZ(sinH * sinV);
/* 285 */     this.right.setY(FastMath.cos(particle.angleV));
/*     */     
/* 287 */     this.right.crossLocal(this.up);
/*     */     
/* 289 */     constructTriangle(vb, vIndex, size, width, height);
/*     */   }
/*     */ 
/*     */   
/*     */   private void buildBillboardTriangle(float[] vb, int vIndex, Particle particle) {
/* 294 */     float size = particle.size;
/* 295 */     float width = particle.width * 0.5F;
/* 296 */     float height = particle.height * 0.5F;
/*     */     
/* 298 */     this.position.set(particle.position.getX(), particle.position.getY(), particle.position.getZ());
/*     */     
/* 300 */     this.up.set(this.cUp);
/* 301 */     this.right.set(this.cRight);
/*     */     
/* 303 */     constructTriangle(vb, vIndex, size, width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void constructTriangle(float[] vb, int vIndex, float size, float width, float height) {
/* 310 */     this.up.multLocal(height);
/* 311 */     this.right.multLocal(width);
/*     */     
/* 313 */     this.mUp.set(this.up);
/* 314 */     this.mUp.multLocal(-0.5F);
/* 315 */     this.mRight.set(this.right);
/* 316 */     this.mRight.multLocal(-0.5F);
/*     */     
/* 318 */     this.mUp.addLocal(this.mRight);
/*     */     
/* 320 */     this.topLeft.set(this.up).subtractLocal(this.right).subtractLocal(this.mUp);
/* 321 */     this.up.multLocal(-1.0F);
/* 322 */     this.bottomLeft.set(this.up).subtractLocal(this.right).subtractLocal(this.mUp);
/* 323 */     this.bottomRight.set(this.up).addLocal(this.right).subtractLocal(this.mUp);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 332 */     this.topLeft.multLocal(size).addLocal(this.position);
/* 333 */     this.bottomLeft.multLocal(size).addLocal(this.position);
/* 334 */     this.bottomRight.multLocal(size).addLocal(this.position);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 339 */     int pos = vIndex * 3;
/* 340 */     BufferUtils.insertVector3f(vb, this.bottomLeft, pos);
/* 341 */     BufferUtils.insertVector3f(vb, this.bottomRight, pos + 3);
/* 342 */     BufferUtils.insertVector3f(vb, this.topLeft, pos + 6);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setColor(float[] cb, int vIndex, Particle particle) {
/* 347 */     this.color.set(particle.color.getRed(), particle.color.getGreen(), particle.color.getBlue(), particle.alpha);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 352 */     vIndex *= 4;
/* 353 */     BufferUtils.insertColor(this.color, cb, vIndex);
/* 354 */     BufferUtils.insertColor(this.color, cb, vIndex + 4);
/* 355 */     BufferUtils.insertColor(this.color, cb, vIndex + 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setTextureCoords(float[] tb, int vIndex, Particle particle) {
/* 363 */     float texWidth = 1.0F / particle.texWidth;
/* 364 */     float texHeight = 1.0F / particle.texHeight;
/*     */     
/* 366 */     this.bl.set(-0.5F, -0.5F);
/* 367 */     this.br.set(1.5F, -0.5F);
/* 368 */     this.tl.set(-0.5F, 1.5F);
/*     */     
/* 370 */     rotate2f(this.bl, this.br, this.tl, particle.angle);
/*     */     
/* 372 */     float bias = 0.5F;
/*     */     
/* 374 */     this.bl.set(this.bl.x * texWidth + 0.5F, this.bl.y * texHeight + 0.5F);
/* 375 */     this.br.set(this.br.x * texWidth + 0.5F, this.br.y * texHeight + 0.5F);
/* 376 */     this.tl.set(this.tl.x * texWidth + 0.5F, this.tl.y * texHeight + 0.5F);
/*     */     
/* 378 */     BufferUtils.insertVertex(this.bl, tb, vIndex);
/* 379 */     BufferUtils.insertVertex(this.br, tb, vIndex + 1);
/* 380 */     BufferUtils.insertVertex(this.tl, tb, vIndex + 2);
/*     */   }
/*     */ 
/*     */   
/*     */   private void rotate2f(Vector2f v1, Vector2f v2, Vector2f v3, float angle) {
/* 385 */     float cosAngle = FastMath.cos(angle);
/* 386 */     float sinAngle = FastMath.sin(angle);
/* 387 */     v1.set(v1.x * cosAngle - v1.y * sinAngle, v1.y * cosAngle + v1.x * sinAngle);
/*     */ 
/*     */     
/* 390 */     v2.set(v2.x * cosAngle - v2.y * sinAngle, v2.y * cosAngle + v2.x * sinAngle);
/*     */ 
/*     */     
/* 393 */     v3.set(v3.x * cosAngle - v3.y * sinAngle, v3.y * cosAngle + v3.x * sinAngle);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCamera(Camera camera) {
/* 399 */     this.camera = camera;
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(Renderer r) {
/* 404 */     if (this.triangleQuantity > 0)
/*     */     {
/* 406 */       super.draw(r);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ParticleUpdateController extends Controller {
/*     */     private ParticleGeneratorMesh particleGeneratorMesh;
/*     */     
/*     */     private ParticleUpdateController(ParticleGeneratorMesh particleGeneratorMesh) {
/* 414 */       this.particleGeneratorMesh = particleGeneratorMesh;
/*     */     }
/*     */     
/*     */     public void update(float v) {
/* 418 */       this.particleGeneratorMesh.buildParticles();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\turborilla\jops\jme\ParticleGeneratorMesh.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */