/*     */ package com.funcom.gameengine.utils;
/*     */ 
/*     */ import com.funcom.commons.utils.DimensionFloat;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.WorldOrigin;
/*     */ import com.funcom.gameengine.WorldUtils;
/*     */ import com.funcom.gameengine.controller.ShadowController;
/*     */ import com.funcom.gameengine.controller.SquareShadowController;
/*     */ import com.funcom.gameengine.jme.BlendMode;
/*     */ import com.funcom.gameengine.jme.DecalConfig;
/*     */ import com.funcom.gameengine.jme.DecalQuad;
/*     */ import com.funcom.gameengine.jme.ObjectiveArrowDecal;
/*     */ import com.funcom.gameengine.jme.ShadowDecal;
/*     */ import com.funcom.gameengine.jme.TileQuad;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManagerException;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.texture.AsyncTexture2D;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.view.CameraConfig;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.gameengine.view.TransparentAlphaState;
/*     */ import com.jme.bounding.BoundingBox;
/*     */ import com.jme.bounding.BoundingCapsule;
/*     */ import com.jme.bounding.BoundingSphere;
/*     */ import com.jme.bounding.BoundingVolume;
/*     */ import com.jme.bounding.OrientedBoundingBox;
/*     */ import com.jme.image.Image;
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.math.FastMath;
/*     */ import com.jme.math.LineSegment;
/*     */ import com.jme.math.Vector2f;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.Controller;
/*     */ import com.jme.scene.Geometry;
/*     */ import com.jme.scene.Line;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.TexCoords;
/*     */ import com.jme.scene.shape.Quad;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.scene.state.ZBufferState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jme.util.geom.BufferUtils;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jdom.Attribute;
/*     */ import org.jdom.DataConversionException;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpatialUtils
/*     */ {
/*     */   public static final String ARROW_IMAGE = "effects/world/direction_arrows/direction_arrow_quest_001.png";
/*     */   public static final String SHADOW_IMAGE = "decals/global/shadows/shadow_characterblob.png";
/*     */   public static final float LINE_HEIGHT = 0.005F;
/*     */   public static final float TILE_CUBE_SIZE = 1.0F;
/*  69 */   private static final Vector3f[] tileNormals = new Vector3f[] { new Vector3f(0.0F, 1.0F, 0.0F), new Vector3f(0.0F, 1.0F, 0.0F), new Vector3f(0.0F, 1.0F, 0.0F), new Vector3f(0.0F, 1.0F, 0.0F) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   private static final Vector3f[] entNormals = new Vector3f[] { new Vector3f(0.0F, 0.0F, 1.0F), new Vector3f(0.0F, 0.0F, 1.0F), new Vector3f(0.0F, 0.0F, 1.0F), new Vector3f(0.0F, 0.0F, 1.0F) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   private static final ColorRGBA[] colors = null;
/*     */ 
/*     */   
/*  85 */   private static final Vector2f[] texCoords = new Vector2f[] { new Vector2f(0.0F, 1.0F), new Vector2f(0.0F, 0.0F), new Vector2f(1.0F, 1.0F), new Vector2f(1.0F, 0.0F) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   private static final int[] indexes = new int[] { 0, 1, 2, 2, 1, 3 };
/*     */ 
/*     */ 
/*     */   
/*  97 */   private static final Vector3f[] lineNormals = new Vector3f[] { Vector3f.UNIT_Y, Vector3f.UNIT_Y };
/*     */ 
/*     */ 
/*     */   
/* 101 */   private static final Map<String, DecalConfig> DECAL_CONFIGS = new HashMap<String, DecalConfig>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static FloatBuffer tileVertexBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 114 */     Vector3f[] tileVertexes = { new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(0.0F, 0.0F, 1.0F), new Vector3f(1.0F, 0.0F, 0.0F), new Vector3f(1.0F, 0.0F, 1.0F) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     tileVertexBuffer = BufferUtils.createFloatBuffer(tileVertexes);
/* 122 */   } private static FloatBuffer tileNormalBuffer = BufferUtils.createFloatBuffer(tileNormals);
/* 123 */   private static FloatBuffer tileColorBuffer = BufferUtils.createFloatBuffer(colors);
/* 124 */   private static FloatBuffer tileTextureMappingBuffer = BufferUtils.createFloatBuffer(texCoords);
/* 125 */   private static FloatBuffer decalTextureMappingBuffer = BufferUtils.createFloatBuffer(texCoords);
/* 126 */   private static IntBuffer tileIndicesBuffer = BufferUtils.createIntBuffer(indexes);
/* 127 */   private static BoundingBox tileBounds = new BoundingBox(new Vector3f(0.5F, 0.0F, 0.5F), 0.5F, 0.0F, 0.5F);
/*     */ 
/*     */   
/* 130 */   private static FloatBuffer entityNormalBuffer = BufferUtils.createFloatBuffer(entNormals);
/*     */   private static final float BILLBOARD_IMAGE_SCALE = 0.005F;
/*     */   
/*     */   public static void setupTileQuad(TileQuad tileQuad, int x, int y) {
/* 134 */     tileQuad.setName("Tile_" + x + "_" + y);
/* 135 */     tileQuad.reconstruct(tileVertexBuffer, tileNormalBuffer, tileColorBuffer, new TexCoords(tileTextureMappingBuffer), tileIndicesBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     tileQuad.setModelBound((BoundingVolume)tileBounds);
/* 142 */     tileQuad.setLocalTranslation(x, 0.0F, y);
/* 143 */     tileQuad.setCullHint(Spatial.CullHint.Dynamic);
/*     */   }
/*     */   
/*     */   private static DecalQuad initDecalQuadImageSized(DecalQuad decalQuad, ResourceGetter resourceGetter, String textureResourcePath) {
/* 147 */     Texture t = resourceGetter.getTexture(textureResourcePath, CacheType.CACHE_TEMPORARILY);
/*     */     
/* 149 */     DimensionFloat size = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 157 */       if (LoadingManager.USE_DDS_FORMAT) {
/* 158 */         String resDesc = textureResourcePath.toLowerCase().replace(".png", ".desc");
/* 159 */         ByteBuffer buf = resourceGetter.getBlob(resDesc);
/* 160 */         byte[] bytearr = new byte[buf.remaining()];
/* 161 */         buf.get(bytearr);
/* 162 */         String s = new String(bytearr);
/* 163 */         String[] split = s.split(",");
/*     */         try {
/* 165 */           int texWidth = Integer.parseInt(split[2]);
/* 166 */           int texHeight = Integer.parseInt(split[3]);
/* 167 */           size = getBillboardRenderSize(texWidth, texHeight);
/*     */         }
/* 169 */         catch (Exception ee) {
/* 170 */           size = null;
/*     */         }
/*     */       
/*     */       } 
/* 174 */     } catch (Exception e) {
/* 175 */       size = null;
/*     */     } 
/*     */     
/* 178 */     if (size == null) {
/* 179 */       size = getBillboardRenderSize(t);
/*     */     }
/*     */     
/* 182 */     DecalQuad ret = initDecalQuad(decalQuad, t, size);
/*     */ 
/*     */     
/* 185 */     String parentPath = getParentPath(textureResourcePath);
/* 186 */     DecalConfig decalConfig = getDecalConfig(resourceGetter, parentPath);
/*     */     
/* 188 */     ret.setConfig(decalConfig);
/*     */     
/* 190 */     return ret;
/*     */   }
/*     */   
/*     */   private static DecalQuad initDecalQuad(DecalQuad decalQuad, Texture t, DimensionFloat size) {
/* 194 */     TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/* 195 */     ts.setTexture(t);
/*     */ 
/*     */     
/* 198 */     Vector3f[] vertices = { new Vector3f(-size.getWidth() / 2.0F, 0.0F, -size.getHeight() / 2.0F), new Vector3f(-size.getWidth() / 2.0F, 0.0F, size.getHeight() / 2.0F), new Vector3f(size.getWidth() / 2.0F, 0.0F, -size.getHeight() / 2.0F), new Vector3f(size.getWidth() / 2.0F, 0.0F, size.getHeight() / 2.0F) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     decalQuad.reconstruct(BufferUtils.createFloatBuffer(vertices), tileNormalBuffer, tileColorBuffer, new TexCoords(decalTextureMappingBuffer), tileIndicesBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 211 */     decalQuad.setModelBound((BoundingVolume)new BoundingBox());
/* 212 */     decalQuad.updateModelBound();
/* 213 */     decalQuad.setRenderState((RenderState)ts);
/*     */ 
/*     */     
/* 216 */     decalQuad.setRenderQueueMode(3);
/*     */     
/* 218 */     ZBufferState zBufferState = DisplaySystem.getDisplaySystem().getRenderer().createZBufferState();
/* 219 */     zBufferState.setWritable(false);
/* 220 */     decalQuad.setRenderState((RenderState)zBufferState);
/*     */     
/* 222 */     decalQuad.setCullHint(Spatial.CullHint.Dynamic);
/*     */     
/* 224 */     return decalQuad;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static DecalQuad createDecalQuad(String name, ResourceGetter resourceGetter, String textureResourcePath) {
/* 230 */     DecalQuad tileQuad = new DecalQuad(name);
/* 231 */     initDecalQuadImageSized(tileQuad, resourceGetter, textureResourcePath);
/*     */     
/* 233 */     return tileQuad;
/*     */   }
/*     */   
/*     */   public static DecalQuad createShadowQuad(String name, Texture texture, DimensionFloat size) {
/* 237 */     ShadowDecal shadowDecal = new ShadowDecal(name);
/*     */     
/* 239 */     initDecalQuad((DecalQuad)shadowDecal, texture, size);
/*     */     
/* 241 */     return (DecalQuad)shadowDecal;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static DecalQuad createObjectiveArrowQuad(String name, Texture texture, DimensionFloat size) {
/* 247 */     ObjectiveArrowDecal objectiveArrowDecal = new ObjectiveArrowDecal(name);
/* 248 */     initDecalQuad((DecalQuad)objectiveArrowDecal, texture, size);
/* 249 */     return (DecalQuad)objectiveArrowDecal;
/*     */   }
/*     */   
/*     */   public static Collection<DecalConfig> getDecalConfigs() {
/* 253 */     return DECAL_CONFIGS.values();
/*     */   }
/*     */   
/*     */   public static void clearDecalConfigs() {
/* 257 */     DECAL_CONFIGS.clear();
/*     */   }
/*     */   
/*     */   public static DecalConfig getDecalConfig(ResourceGetter resourceGetter, String decalDirPath) {
/* 261 */     DecalConfig decalConfig = DECAL_CONFIGS.get(decalDirPath);
/*     */     
/* 263 */     if (decalConfig == null) {
/* 264 */       decalConfig = new DecalConfig();
/*     */       
/* 266 */       Document document = null;
/*     */       try {
/* 268 */         document = resourceGetter.getDocument(decalDirPath + "/" + "decalConfig.xml", CacheType.NOT_CACHED);
/* 269 */       } catch (ResourceManagerException e) {}
/*     */ 
/*     */       
/* 272 */       if (document != null) {
/* 273 */         String blendModeStr = getChildText(document, "blendmode");
/* 274 */         if (blendModeStr != null) {
/* 275 */           decalConfig.setBlendMode(BlendMode.valueOf(blendModeStr.trim().toUpperCase()));
/*     */         }
/*     */         
/* 278 */         String layerIdStr = getChildText(document, "layerid");
/* 279 */         if (layerIdStr != null) {
/* 280 */           int layerId = Integer.parseInt(layerIdStr);
/* 281 */           decalConfig.setLayerId(layerId);
/*     */         } 
/*     */       } 
/*     */       
/* 285 */       DECAL_CONFIGS.put(decalDirPath, decalConfig);
/*     */     } 
/* 287 */     return decalConfig;
/*     */   }
/*     */   
/*     */   public static String getChildText(Document document, String childName) {
/* 291 */     Element child = document.getRootElement().getChild(childName);
/* 292 */     if (child != null) {
/* 293 */       return child.getText();
/*     */     }
/* 295 */     return null;
/*     */   }
/*     */   
/*     */   private static String getParentPath(String resourcePath) {
/* 299 */     if (resourcePath.contains("\\")) {
/* 300 */       resourcePath = resourcePath.replace('\\', '/');
/*     */     }
/* 302 */     int last = resourcePath.lastIndexOf('/');
/*     */     
/* 304 */     if (last != -1) {
/* 305 */       return resourcePath.substring(0, last);
/*     */     }
/*     */     
/* 308 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public static Quad createTileQuad(String name, int x, int y, float width, float height) {
/* 313 */     Vector3f[] vertices = { new Vector3f(x * width, 0.0F, y * height), new Vector3f(x * width, 0.0F, y * height + height), new Vector3f(x * width + width, 0.0F, y * height), new Vector3f(x * width + width, 0.0F, y * height + height) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 320 */     Quad tileQuad = new Quad(name);
/* 321 */     tileQuad.reconstruct(BufferUtils.createFloatBuffer(vertices), tileNormalBuffer, tileColorBuffer, new TexCoords(tileTextureMappingBuffer), tileIndicesBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 327 */     tileQuad.setModelBound((BoundingVolume)new BoundingBox());
/* 328 */     tileQuad.updateModelBound();
/*     */     
/* 330 */     return tileQuad;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Quad createOriginGroundQuad(String name, float width, float height) {
/* 335 */     Vector3f[] vertices = { new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(0.0F, 0.0F, height), new Vector3f(width, 0.0F, 0.0F), new Vector3f(width, 0.0F, height) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 342 */     Quad tileQuad = new Quad(name);
/* 343 */     tileQuad.reconstruct(BufferUtils.createFloatBuffer(vertices), tileNormalBuffer, tileColorBuffer, new TexCoords(tileTextureMappingBuffer), tileIndicesBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 349 */     tileQuad.setModelBound((BoundingVolume)new BoundingBox());
/* 350 */     tileQuad.updateModelBound();
/*     */     
/* 352 */     return tileQuad;
/*     */   }
/*     */   
/*     */   public static Quad createPropQuad(String name, float width, float height) {
/* 356 */     float width2 = width / 2.0F;
/*     */     
/* 358 */     Vector3f[] entVertexes = { new Vector3f(-width2, height, 0.0F), new Vector3f(-width2, 0.0F, 0.0F), new Vector3f(width2, height, 0.0F), new Vector3f(width2, 0.0F, 0.0F) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 365 */     Quad propQuad = new ExternalConstructedQuad(name);
/* 366 */     propQuad.reconstruct(BufferUtils.createFloatBuffer(entVertexes), entityNormalBuffer, tileColorBuffer, new TexCoords(tileTextureMappingBuffer), tileIndicesBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 372 */     propQuad.setModelBound((BoundingVolume)new BoundingBox());
/* 373 */     propQuad.updateModelBound();
/*     */     
/* 375 */     return propQuad;
/*     */   }
/*     */   
/*     */   public static Quad createPropQuad(String name, float width, float height, TextureState ts) {
/* 379 */     Quad propQuad = createPropQuad(name, width, height);
/* 380 */     propQuad.setRenderState((RenderState)ts);
/* 381 */     propQuad.setRenderState((RenderState)TransparentAlphaState.get());
/*     */ 
/*     */ 
/*     */     
/* 385 */     propQuad.setRenderQueueMode(3);
/*     */     
/* 387 */     return propQuad;
/*     */   }
/*     */   
/*     */   public static Quad createPropQuad(String name, float width, float height, ColorRGBA color) {
/* 391 */     ColorRGBA[] colors = { color, color, color, color };
/* 392 */     Quad propQuad = createPropQuad(name, width, height);
/*     */     
/* 394 */     if (color.a < 1.0F) {
/* 395 */       propQuad.setRenderState((RenderState)TransparentAlphaState.get());
/*     */     }
/*     */     
/* 398 */     propQuad.setColorBuffer(BufferUtils.createFloatBuffer(colors));
/* 399 */     return propQuad;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Quad createGroundQuad(String name, float radius, TextureState textureState) {
/* 404 */     Vector3f[] vertices = { new Vector3f(-radius, 0.0F, -radius), new Vector3f(-radius, 0.0F, radius), new Vector3f(radius, 0.0F, -radius), new Vector3f(radius, 0.0F, radius) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 411 */     Quad quad = new Quad(name);
/* 412 */     quad.reconstruct(BufferUtils.createFloatBuffer(vertices), tileNormalBuffer, tileColorBuffer, new TexCoords(tileTextureMappingBuffer), tileIndicesBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 418 */     quad.setModelBound((BoundingVolume)new BoundingBox());
/* 419 */     quad.updateModelBound();
/*     */ 
/*     */     
/* 422 */     quad.setRenderState((RenderState)textureState);
/* 423 */     quad.setRenderState((RenderState)TransparentAlphaState.get());
/*     */     
/* 425 */     return quad;
/*     */   }
/*     */   
/*     */   public static Line createGroundLine(String name, ColorRGBA color) {
/* 429 */     ColorRGBA[] lineColors = { color, color };
/*     */     
/* 431 */     Line line = new Line(name);
/* 432 */     line.setNormalBuffer(BufferUtils.createFloatBuffer(lineNormals));
/* 433 */     line.setColorBuffer(BufferUtils.createFloatBuffer(lineColors));
/*     */     
/* 435 */     positionLine(line, new WorldCoordinate(), new WorldCoordinate());
/*     */     
/* 437 */     return line;
/*     */   }
/*     */   
/*     */   public static void positionLine(Line line, WorldCoordinate startPoint, WorldCoordinate endPoint) {
/* 441 */     int offsetx = WorldOrigin.instance().getX();
/* 442 */     int offsety = WorldOrigin.instance().getY();
/*     */     
/* 444 */     Vector3f[] vertex = { new Vector3f(WorldUtils.getScreenX(startPoint, offsetx), 0.005F, WorldUtils.getScreenY(startPoint, offsety)), new Vector3f(WorldUtils.getScreenX(endPoint, offsetx), 0.005F, WorldUtils.getScreenY(endPoint, offsety)) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 453 */     line.setVertexBuffer(BufferUtils.createFloatBuffer(vertex));
/* 454 */     line.generateIndices();
/*     */   }
/*     */   
/*     */   public static void setElementWorldCoordinate(Element element, WorldCoordinate wc) {
/* 458 */     int tx = (wc.getTileCoord()).x;
/* 459 */     int ty = (wc.getTileCoord()).y;
/* 460 */     float ox = (float)wc.getTileOffset().getX();
/* 461 */     float oy = (float)wc.getTileOffset().getY();
/* 462 */     element.setAttribute("x", Integer.toString(tx));
/* 463 */     element.setAttribute("y", Integer.toString(ty));
/* 464 */     element.setAttribute("x-offset", Float.toString(ox));
/* 465 */     element.setAttribute("y-offset", Float.toString(oy));
/*     */   }
/*     */   
/*     */   public static WorldCoordinate getElementWorldCoordinate(Element element) throws DataConversionException {
/* 469 */     return getElementWorldCoordinate(element, null);
/*     */   }
/*     */   
/*     */   public static WorldCoordinate getElementWorldCoordinate(Element element, String mapId) throws DataConversionException {
/* 473 */     int tx = element.getAttribute("x").getIntValue();
/* 474 */     int ty = element.getAttribute("y").getIntValue();
/* 475 */     float ox = element.getAttribute("x-offset").getFloatValue();
/* 476 */     float oy = element.getAttribute("y-offset").getFloatValue();
/* 477 */     return new WorldCoordinate(tx, ty, ox, oy, mapId, 0);
/*     */   }
/*     */   
/*     */   public static float[] getColor(Element staticElement) throws DataConversionException {
/* 481 */     float[] tintColor = new float[4];
/* 482 */     Attribute red = staticElement.getAttribute("red");
/* 483 */     if (red != null) {
/* 484 */       tintColor[0] = red.getFloatValue();
/* 485 */       tintColor[1] = staticElement.getAttribute("green").getFloatValue();
/* 486 */       tintColor[2] = staticElement.getAttribute("blue").getFloatValue();
/* 487 */       tintColor[3] = staticElement.getAttribute("alpha").getFloatValue();
/*     */     } 
/* 489 */     return tintColor;
/*     */   }
/*     */   
/*     */   public static void translateElementWorldCoordinate(Element element, int x, int y) throws DataConversionException {
/* 493 */     WorldCoordinate wc = getElementWorldCoordinate(element);
/* 494 */     wc.addTiles(x, y);
/* 495 */     setElementWorldCoordinate(element, wc);
/*     */   }
/*     */   
/*     */   public static DimensionFloat getBillboardRenderSize(int imageWidth, int imageHeight) {
/* 499 */     float angleDiff = FastMath.cos(CameraConfig.instance().getCameraAngle() * 0.017453292F);
/* 500 */     float width = imageWidth * 0.005F;
/* 501 */     float height = imageHeight * 0.005F / angleDiff;
/* 502 */     return new DimensionFloat(width, height);
/*     */   }
/*     */   
/*     */   public static DimensionFloat getBillboardRenderSize(Image image) {
/* 506 */     return getBillboardRenderSize(image.getWidth(), image.getHeight());
/*     */   }
/*     */   
/*     */   public static DimensionFloat getBillboardRenderSize(Texture texture) {
/* 510 */     if (texture instanceof AsyncTexture2D) {
/* 511 */       AsyncTexture2D asyncTexture2D = (AsyncTexture2D)texture;
/* 512 */       return getBillboardRenderSize(asyncTexture2D.getOriginalWidth(), asyncTexture2D.getOriginalHeight());
/*     */     } 
/* 514 */     return getBillboardRenderSize(texture.getImage());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ColorRGBA convertToColorRGBA(float[] colorArray) {
/* 524 */     if (colorArray == null) {
/* 525 */       return null;
/*     */     }
/* 527 */     if (colorArray.length != 4) {
/* 528 */       throw new IllegalArgumentException("RGBA color consists of 4 floats, found: " + colorArray.length);
/*     */     }
/*     */     
/* 531 */     return new ColorRGBA(colorArray[0], colorArray[1], colorArray[2], colorArray[3]);
/*     */   }
/*     */   
/*     */   public static Vector3f getBoundingVolume(Spatial spatial) {
/* 535 */     Vector3f volume = new Vector3f();
/* 536 */     BoundingVolume boundingVolume = spatial.getWorldBound();
/*     */     
/* 538 */     if (boundingVolume instanceof BoundingBox) {
/* 539 */       BoundingBox boundingBox = (BoundingBox)boundingVolume;
/* 540 */       boundingBox.getExtent(volume);
/* 541 */       volume.multLocal(2.0F);
/* 542 */     } else if (boundingVolume instanceof BoundingCapsule) {
/* 543 */       BoundingCapsule boundingCapsule = (BoundingCapsule)boundingVolume;
/* 544 */       LineSegment segment = boundingCapsule.getLineSegment();
/* 545 */       float diameter = (boundingCapsule.getRadius() + segment.getExtent()) * 2.0F;
/* 546 */       volume.set(diameter, diameter, diameter);
/* 547 */     } else if (boundingVolume instanceof BoundingSphere) {
/* 548 */       BoundingSphere boundingSphere = (BoundingSphere)boundingVolume;
/* 549 */       float diameter = boundingSphere.getRadius() * 2.0F;
/* 550 */       volume.set(diameter, diameter, diameter);
/* 551 */     } else if (boundingVolume instanceof OrientedBoundingBox) {
/* 552 */       OrientedBoundingBox orientedBoundingBox = (OrientedBoundingBox)boundingVolume;
/* 553 */       volume.set(orientedBoundingBox.getExtent());
/* 554 */       volume.multLocal(2.0F);
/*     */     } else {
/* 556 */       throw new IllegalStateException("Volment calculation not implemented for " + boundingVolume.getClass().getName());
/*     */     } 
/*     */     
/* 559 */     return volume;
/*     */   }
/*     */   
/*     */   public static void setTintingEnabled(Spatial spatial, ColorRGBA tintRgba, boolean enabled) {
/* 563 */     if (spatial instanceof Node) {
/* 564 */       Node node = (Node)spatial;
/* 565 */       List<Spatial> children = node.getChildren();
/* 566 */       if (children != null)
/* 567 */         for (Spatial object : children) {
/* 568 */           setTintingEnabled(object, tintRgba, enabled);
/*     */         } 
/* 570 */     } else if (spatial instanceof Geometry) {
/* 571 */       Geometry geometry = (Geometry)spatial;
/* 572 */       geometry.setDefaultColor(enabled ? tintRgba : ColorRGBA.white);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setTintingAdditiveEnabled(Spatial spatial, ColorRGBA tintRgba, boolean enabled) {
/* 577 */     if (spatial instanceof Node) {
/* 578 */       Node node = (Node)spatial;
/* 579 */       List<Spatial> children = node.getChildren();
/* 580 */       if (children != null)
/* 581 */         for (Spatial object : children) {
/* 582 */           setTintingAdditiveEnabled(object, tintRgba, enabled);
/*     */         } 
/* 584 */     } else if (spatial instanceof Geometry) {
/* 585 */       Geometry geometry = (Geometry)spatial;
/* 586 */       TextureState textureState = (TextureState)geometry.getRenderState(5);
/* 587 */       if (textureState != null) {
/* 588 */         Texture texture = textureState.getTexture();
/* 589 */         if (texture != null) {
/* 590 */           if (enabled) {
/* 591 */             texture = texture.createSimpleClone();
/* 592 */             textureState.setTexture(texture);
/* 593 */             texture.setApply(Texture.ApplyMode.Combine);
/* 594 */             texture.setCombineFuncRGB(Texture.CombinerFunctionRGB.Add);
/* 595 */             texture.setCombineFuncAlpha(Texture.CombinerFunctionAlpha.Replace);
/*     */             
/* 597 */             texture.setCombineSrc0RGB(Texture.CombinerSource.CurrentTexture);
/* 598 */             texture.setCombineOp0RGB(Texture.CombinerOperandRGB.SourceColor);
/*     */             
/* 600 */             texture.setCombineSrc1RGB(Texture.CombinerSource.Constant);
/* 601 */             texture.setCombineOp1RGB(Texture.CombinerOperandRGB.SourceColor);
/*     */             
/* 603 */             texture.setCombineOp0Alpha(Texture.CombinerOperandAlpha.SourceAlpha);
/*     */             
/* 605 */             texture.setBlendColor(tintRgba);
/*     */           } else {
/* 607 */             texture.setApply(Texture.ApplyMode.Modulate);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static TexCoords getTileTextureCoords() {
/* 615 */     return new TexCoords(tileTextureMappingBuffer);
/*     */   }
/*     */   
/*     */   public static void addShadow(PropNode propNode, ResourceManager resourceManager) {
/* 619 */     DecalQuad testShadow = createShadowQuad(resourceManager);
/*     */     
/* 621 */     propNode.attachChild((Spatial)testShadow);
/*     */     
/* 623 */     propNode.addController((Controller)new ShadowController((Spatial)testShadow, propNode));
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
/*     */   public static void addSquaredShadow(PropNode propNode, ResourceManager resourceManager, float scale) {
/* 640 */     DecalQuad testShadow = createShadowQuad(resourceManager);
/*     */     
/* 642 */     propNode.attachChild((Spatial)testShadow);
/*     */     
/* 644 */     propNode.addController((Controller)new SquareShadowController((Spatial)testShadow, propNode, scale));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static DecalQuad createShadowQuad(ResourceManager resourceManager) {
/* 650 */     DimensionFloat size = new DimensionFloat(1.0F, 1.0F);
/* 651 */     Texture texture = (Texture)resourceManager.getResource(Texture.class, "decals/global/shadows/shadow_characterblob.png", CacheType.CACHE_PERMANENTLY);
/* 652 */     DecalQuad testShadow = createShadowQuad("shadow", texture, size);
/* 653 */     testShadow.setRenderState((RenderState)TransparentAlphaState.get());
/* 654 */     return testShadow;
/*     */   }
/*     */   
/*     */   private static DecalQuad createObjectiveArrowQuad(ResourceManager resourceManager) {
/* 658 */     float dim = 2.625F;
/* 659 */     DimensionFloat size = new DimensionFloat(dim, dim);
/* 660 */     Texture texture = (Texture)resourceManager.getResource(Texture.class, "effects/world/direction_arrows/direction_arrow_quest_001.png", CacheType.CACHE_PERMANENTLY);
/* 661 */     DecalQuad testShadow = createObjectiveArrowQuad("arrow", texture, size);
/* 662 */     testShadow.setRenderState((RenderState)TransparentAlphaState.get());
/* 663 */     return testShadow;
/*     */   }
/*     */   
/*     */   private static class ExternalConstructedQuad extends Quad {
/*     */     public ExternalConstructedQuad(String name) {
/* 668 */       super(name);
/*     */     }
/*     */     
/*     */     public void updateGeometry(float width, float height) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengin\\utils\SpatialUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */