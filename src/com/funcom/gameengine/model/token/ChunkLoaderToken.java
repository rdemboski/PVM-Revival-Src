/*     */ package com.funcom.gameengine.model.token;
/*     */ 
/*     */ import com.funcom.commons.StringUtils;
/*     */ import com.funcom.commons.jme.cpolygon.CPoint2D;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.action.Action;
/*     */ import com.funcom.gameengine.model.chunks.ChunkNode;
/*     */ import com.funcom.gameengine.model.factories.LoadException;
/*     */ import com.funcom.gameengine.model.factories.XmlMk6Tags;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.spatial.LineNode;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import com.funcom.gameengine.view.water.WaterLineCoordinateSet;
/*     */ import com.funcom.gameengine.view.water.WaterPondCoordinateSet;
/*     */ import com.funcom.server.common.GameIOHandler;
/*     */ import java.awt.Point;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import org.jdom.Attribute;
/*     */ import org.jdom.DataConversionException;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkLoaderToken
/*     */   implements Token, XmlMk6Tags
/*     */ {
/*     */   private final ChunkBuilder chunkBuilder;
/*     */   private final ChunkLoaderStrategy chunkLoaderStrategy;
/*     */   private final WorldCoordinate worldCoordinate;
/*     */   private final ResourceGetter resourceGetter;
/*     */   private final GameIOHandler ioHandler;
/*     */   private final ActionFactory actionFactory;
/*     */   
/*     */   public ChunkLoaderToken(ChunkBuilder chunkBuilder, ChunkLoaderStrategy chunkLoaderStrategy, WorldCoordinate worldCoordinate, ResourceGetter resourceGetter, GameIOHandler ioHandler, ActionFactory actionFactory) {
/*  43 */     this.chunkBuilder = chunkBuilder;
/*  44 */     this.chunkLoaderStrategy = chunkLoaderStrategy;
/*  45 */     this.worldCoordinate = worldCoordinate;
/*  46 */     this.resourceGetter = resourceGetter;
/*  47 */     this.ioHandler = ioHandler;
/*  48 */     this.actionFactory = actionFactory;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkLoaderToken(ChunkBuilder chunkBuilder, ChunkLoaderStrategy chunkLoaderStrategy, ResourceGetter resourceGetter, GameIOHandler ioHandler, ActionFactory actionFactory) {
/*  53 */     this(chunkBuilder, chunkLoaderStrategy, new WorldCoordinate(), resourceGetter, ioHandler, actionFactory);
/*     */   }
/*     */   
/*     */   public Token.TokenType getTokenType() {
/*  57 */     return Token.TokenType.OPEN;
/*     */   }
/*     */   
/*     */   public void process() {
/*  61 */     this.chunkLoaderStrategy.process(this, this.worldCoordinate);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processTileElement(Element tileElement, TokenTargetNode tokenTargetNode, Point tileCoord) {
/*     */     int x, y;
/*     */     try {
/*  69 */       x = tileElement.getAttribute("x").getIntValue();
/*  70 */       y = tileElement.getAttribute("y").getIntValue();
/*  71 */     } catch (DataConversionException e) {
/*  72 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/*  75 */     Element layersElement = tileElement.getChild("layers");
/*     */     
/*  77 */     if (layersElement == null) {
/*     */       return;
/*     */     }
/*  80 */     Element xmlElement = tileElement.getChild("xml");
/*     */     
/*  82 */     String backgroundResourceName = layersElement.getAttributeValue("background");
/*  83 */     String layer2 = layersElement.getAttributeValue("layer2");
/*  84 */     String layer3 = layersElement.getAttributeValue("layer3");
/*  85 */     String layer4 = layersElement.getAttributeValue("layer4");
/*     */     
/*  87 */     String xmlBackground = xmlElement.getAttributeValue("background");
/*  88 */     String xmlCorner1 = xmlElement.getAttributeValue("corner1");
/*  89 */     String xmlCorner2 = xmlElement.getAttributeValue("corner2");
/*  90 */     String xmlCorner3 = xmlElement.getAttributeValue("corner3");
/*  91 */     String xmlCorner4 = xmlElement.getAttributeValue("corner4");
/*     */     
/*  93 */     this.chunkBuilder.createLayeredTextureTile(x, y, backgroundResourceName, layer2, layer3, layer4, xmlBackground, xmlCorner1, xmlCorner2, xmlCorner3, xmlCorner4, tokenTargetNode, tileCoord, this.resourceGetter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processStaticObjectElement(Element staticElement, String mapId, TokenTargetNode tokenTargetNode, Point tileCoord) throws LoadException {
/*     */     try {
/* 101 */       WorldCoordinate coord = SpatialUtils.getElementWorldCoordinate(staticElement, mapId);
/* 102 */       coord.addTiles(tileCoord);
/*     */       
/* 104 */       float scale = staticElement.getAttribute("scale").getFloatValue();
/* 105 */       float angle = staticElement.getAttribute("rotation").getFloatValue();
/*     */       
/* 107 */       float z = 0.0F;
/*     */       
/* 109 */       Attribute zAtt = staticElement.getAttribute("z-value");
/* 110 */       if (zAtt != null) {
/* 111 */         z = zAtt.getFloatValue();
/*     */       }
/*     */       
/* 114 */       Element resourceElement = staticElement.getChild("resource");
/* 115 */       String resourceName = resourceElement.getAttributeValue("name");
/* 116 */       String name = (staticElement.getAttribute("name") == null) ? "" : staticElement.getAttribute("name").getValue();
/* 117 */       Prop prop = new Prop(name, coord);
/*     */       
/* 119 */       float[] tintColor = getTintColor(staticElement);
/*     */       
/* 121 */       this.chunkBuilder.createStaticObject(prop, coord, scale, angle, z, resourceName, tintColor, tokenTargetNode, tileCoord, this.resourceGetter);
/*     */     
/*     */     }
/* 124 */     catch (DataConversionException e) {
/* 125 */       throw new LoadException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void processInteractibleObjectElement(Element interactibleElement, String mapId, TokenTargetNode tokenTargetNode, Point tileCoord) throws LoadException {
/*     */     try {
/* 131 */       WorldCoordinate coord = SpatialUtils.getElementWorldCoordinate(interactibleElement, mapId);
/* 132 */       coord.addTiles(tileCoord);
/*     */       
/* 134 */       float scale = 0.0F;
/* 135 */       float angle = 0.0F;
/* 136 */       Attribute scaleAtt = interactibleElement.getAttribute("scale");
/* 137 */       Attribute angleAtt = interactibleElement.getAttribute("rotation");
/* 138 */       if (scaleAtt != null)
/* 139 */         scale = scaleAtt.getFloatValue(); 
/* 140 */       if (angleAtt != null) {
/* 141 */         angle = angleAtt.getFloatValue();
/*     */       }
/* 143 */       Element interactibleLinkElement = interactibleElement.getChild("resource");
/* 144 */       String interactibleFile = interactibleLinkElement.getAttributeValue("name");
/*     */       
/* 146 */       Document doc = this.resourceGetter.getDocument(interactibleFile, CacheType.CACHE_TEMPORARILY);
/* 147 */       Element root = doc.getRootElement();
/*     */       
/* 149 */       Element resourceElement = root.getChild("resource");
/* 150 */       String resourceName = resourceElement.getAttributeValue("name");
/* 151 */       String name = (interactibleElement.getAttribute("name") == null) ? "" : interactibleElement.getAttribute("name").getValue();
/*     */       
/* 153 */       double radius = StringUtils.parseDouble(interactibleElement.getAttributeValue("radius"), 0.6000000238418579D);
/* 154 */       InteractibleProp prop = new InteractibleProp(-1, name, coord, radius);
/*     */       
/* 156 */       String rpgId = null;
/* 157 */       List<Element> actionNodes = root.getChildren("action");
/* 158 */       for (Element actionElement : actionNodes) {
/* 159 */         Action action = this.actionFactory.createAction(name, actionElement);
/* 160 */         prop.addAction(action);
/* 161 */         Element child = actionElement.getChild("id");
/* 162 */         if (child != null) {
/* 163 */           rpgId = child.getTextTrim();
/*     */         }
/*     */       } 
/*     */       
/* 167 */       float[] tintColor = getTintColor(interactibleElement);
/*     */       
/* 169 */       String defaultActionName = root.getChild("action").getAttributeValue("type");
/* 170 */       this.chunkBuilder.createInteractibleObject(prop, defaultActionName, coord, scale, angle, resourceName, interactibleFile, tintColor, tokenTargetNode, tileCoord, this.resourceGetter, rpgId);
/*     */     }
/* 172 */     catch (DataConversionException e) {
/* 173 */       throw new LoadException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void processSpawnPointElement(Element spawnElement, String mapId, TokenTargetNode tokenTargetNode, Point tileCoord) throws LoadException {
/*     */     WorldCoordinate ccord;
/*     */     try {
/* 180 */       ccord = SpatialUtils.getElementWorldCoordinate(spawnElement, mapId);
/* 181 */     } catch (DataConversionException e) {
/* 182 */       throw new LoadException(e);
/*     */     } 
/* 184 */     ccord.addTiles(tileCoord);
/*     */     
/* 186 */     float angle = 0.0F;
/*     */     try {
/* 188 */       angle = spawnElement.getAttribute("rotation").getFloatValue();
/* 189 */     } catch (DataConversionException ignored) {}
/*     */ 
/*     */     
/* 192 */     String name = spawnElement.getAttribute("name").getValue();
/* 193 */     Element resourceElement = spawnElement.getChild("resource");
/* 194 */     if (resourceElement == null) {
/* 195 */       throw new LoadException("Missing resource element");
/*     */     }
/*     */     
/* 198 */     String resourceName = resourceElement.getAttribute("name").getValue();
/*     */     
/* 200 */     int timerValue = 0;
/*     */     try {
/* 202 */       timerValue = (spawnElement.getAttribute("timer") == null) ? 1 : spawnElement.getAttribute("timer").getIntValue();
/*     */     }
/* 204 */     catch (DataConversionException e) {
/* 205 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 208 */     boolean triggered = false;
/* 209 */     int triggerId = 0;
/*     */     try {
/* 211 */       triggerId = (spawnElement.getAttribute("triggered-id") == null) ? 1 : spawnElement.getAttribute("triggered-id").getIntValue();
/*     */       
/* 213 */       triggered = (spawnElement.getAttribute("triggered") != null && spawnElement.getAttribute("triggered").getBooleanValue());
/* 214 */     } catch (DataConversionException e) {
/* 215 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 218 */     boolean lootIsMagnetic = false;
/*     */     try {
/* 220 */       lootIsMagnetic = (spawnElement.getAttribute("loot-is-magnetic") != null && spawnElement.getAttribute("loot-is-magnetic").getBooleanValue());
/* 221 */     } catch (DataConversionException e) {
/* 222 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 225 */     Element brainElement = spawnElement.getChild("brain");
/* 226 */     String brain = (brainElement == null) ? "" : brainElement.getAttribute("type").getValue();
/* 227 */     HashMap<String, Object> brainParameters = new HashMap<String, Object>();
/* 228 */     if (brainElement != null) {
/* 229 */       getBrainParameters(brainElement, brainParameters, mapId);
/*     */     }
/*     */     
/* 232 */     this.chunkBuilder.createSpawnPoint(ccord, angle, name, resourceName, tokenTargetNode, tileCoord, timerValue, triggered, triggerId, brain, brainParameters, lootIsMagnetic);
/*     */   }
/*     */   
/*     */   public static void getBrainParameters(Element brainElement, HashMap<String, Object> brainParameters, String mapId) throws LoadException {
/* 236 */     for (Object attribute : brainElement.getChildren("attribute")) {
/* 237 */       String attName = ((Element)attribute).getAttributeValue("name");
/* 238 */       if (attName.equals("point to coordinate")) {
/*     */         WorldCoordinate ptCoord;
/*     */         try {
/* 241 */           ptCoord = SpatialUtils.getElementWorldCoordinate((Element)attribute, mapId);
/* 242 */         } catch (DataConversionException e) {
/* 243 */           throw new LoadException(e);
/*     */         } 
/* 245 */         brainParameters.put(attName, ptCoord); continue;
/*     */       } 
/* 247 */       brainParameters.put(attName, ((Element)attribute).getAttributeValue("value"));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void processVendorElement(Element vendorElement, String mapId, TokenTargetNode tokenTargetNode, Point tileCoord) throws LoadException {
/*     */     WorldCoordinate coord;
/*     */     float angle;
/*     */     try {
/* 256 */       coord = SpatialUtils.getElementWorldCoordinate(vendorElement, mapId);
/* 257 */       angle = vendorElement.getAttribute("rotation").getFloatValue();
/* 258 */     } catch (DataConversionException e) {
/* 259 */       throw new LoadException(e);
/*     */     } 
/* 261 */     coord.addTiles(tileCoord);
/*     */     
/* 263 */     String name = vendorElement.getAttribute("name").getValue();
/* 264 */     Element resourceElement = vendorElement.getChild("resource");
/* 265 */     if (resourceElement == null) {
/* 266 */       throw new LoadException("Missing resource element");
/*     */     }
/*     */     
/* 269 */     String resourceName = resourceElement.getAttribute("name").getValue();
/* 270 */     this.chunkBuilder.createVendor(coord, name, resourceName, tokenTargetNode, tileCoord, angle);
/*     */   }
/*     */ 
/*     */   
/*     */   public void processCheckPoints(Element checkpointElement, ChunkNode chunkNode, Point tileCoord) {
/*     */     try {
/* 276 */       String mapId = chunkNode.getChunkWorldNode().getChunkWorldInfo().getMapId();
/* 277 */       WorldCoordinate coord = SpatialUtils.getElementWorldCoordinate(checkpointElement, mapId);
/* 278 */       coord.addTiles(tileCoord);
/*     */       
/* 280 */       Element resourceElement = checkpointElement.getChild("resource");
/* 281 */       String resourceName = resourceElement.getAttributeValue("name");
/*     */       
/* 283 */       float angle = checkpointElement.getAttribute("rotation").getFloatValue();
/* 284 */       float width = checkpointElement.getAttribute("width").getFloatValue();
/* 285 */       float height = checkpointElement.getAttribute("height").getFloatValue();
/*     */       
/* 287 */       this.chunkBuilder.createCheckpoint(resourceName, coord, chunkNode, tileCoord, angle, width, height);
/*     */     }
/* 289 */     catch (DataConversionException e) {
/* 290 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void processMeshObjectElement(Element meshElement, String mapId, TokenTargetNode tokenTargetNode, Point tileCoord) throws LoadException {
/*     */     float scale, angle, tintColor[];
/*     */     WorldCoordinate coord;
/* 297 */     float z = 0.0F;
/*     */     
/*     */     try {
/* 300 */       scale = meshElement.getAttribute("scale").getFloatValue();
/* 301 */       angle = meshElement.getAttribute("rotation").getFloatValue();
/*     */       
/* 303 */       Attribute zAtt = meshElement.getAttribute("z-value");
/* 304 */       if (zAtt != null) {
/* 305 */         z = zAtt.getFloatValue();
/*     */       }
/*     */       
/* 308 */       tintColor = getTintColor(meshElement);
/* 309 */     } catch (DataConversionException e) {
/* 310 */       throw new LoadException(e);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 315 */       coord = SpatialUtils.getElementWorldCoordinate(meshElement, mapId);
/* 316 */     } catch (DataConversionException e) {
/* 317 */       throw new LoadException(e);
/*     */     } 
/* 319 */     coord.addTiles(tileCoord);
/*     */     
/* 321 */     Element resourceElement = meshElement.getChild("resource");
/* 322 */     String resourceName = resourceElement.getAttributeValue("name");
/* 323 */     String name = meshElement.getAttributeValue("name");
/*     */     
/* 325 */     this.chunkBuilder.createMeshObject(coord, scale, angle, z, name, resourceName, tintColor, tokenTargetNode, tileCoord, this.resourceGetter);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processMergedMeshObjectElement(ArrayList<Element> meshElements, String mapId, TokenTargetNode tokenTargetNode, Point tileCoord) throws LoadException, DataConversionException {
/* 331 */     if (LoadingManager.USE_MERGED_MESHES) {
/* 332 */       this.chunkBuilder.createMergedMeshObject(meshElements, tokenTargetNode, this.resourceGetter);
/*     */     } else {
/*     */       
/* 335 */       for (Element meshElement : meshElements) {
/*     */         float scale, angle, tintColor[];
/*     */         WorldCoordinate coord;
/* 338 */         float z = 0.0F;
/*     */         
/*     */         try {
/* 341 */           scale = meshElement.getAttribute("scale").getFloatValue();
/* 342 */           angle = meshElement.getAttribute("rotation").getFloatValue();
/*     */           
/* 344 */           Attribute zAtt = meshElement.getAttribute("z-value");
/* 345 */           if (zAtt != null) {
/* 346 */             z = zAtt.getFloatValue();
/*     */           }
/*     */           
/* 349 */           tintColor = getTintColor(meshElement);
/* 350 */         } catch (DataConversionException e) {
/* 351 */           throw new LoadException(e);
/*     */         } 
/*     */ 
/*     */         
/*     */         try {
/* 356 */           coord = SpatialUtils.getElementWorldCoordinate(meshElement, mapId);
/* 357 */         } catch (DataConversionException e) {
/* 358 */           throw new LoadException(e);
/*     */         } 
/* 360 */         coord.addTiles(tileCoord);
/*     */         
/* 362 */         Element resourceElement = meshElement.getChild("resource");
/* 363 */         String resourceName = resourceElement.getAttributeValue("name");
/* 364 */         String name = meshElement.getAttributeValue("name");
/*     */         
/* 366 */         this.chunkBuilder.createMeshObject(coord, scale, angle, z, name, resourceName, tintColor, tokenTargetNode, tileCoord, this.resourceGetter);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void processCollisionNodeElement(Element collisionElement, String mapId, TokenTargetNode tokenTargetNode, Point tileCoord) throws LoadException {
/*     */     try {
/* 374 */       String ident = collisionElement.getAttributeValue("num");
/* 375 */       WorldCoordinate coord = SpatialUtils.getElementWorldCoordinate(collisionElement, mapId);
/* 376 */       this.chunkBuilder.createCollisionNode(coord, ident, tokenTargetNode, tileCoord, this.resourceGetter);
/* 377 */     } catch (DataConversionException e) {
/* 378 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void processCollisionLineElement(Element collisionElement, TokenTargetNode tokenTargetNode, LineNode lineRoot, Point tileCoord) throws LoadException {
/* 383 */     String startIdent = collisionElement.getAttributeValue("node1");
/* 384 */     String endIdent = collisionElement.getAttributeValue("node2");
/* 385 */     double height = 0.0D;
/* 386 */     if (collisionElement.getAttribute("height") != null) {
/* 387 */       String heightStr = collisionElement.getAttributeValue("height");
/* 388 */       height = Double.parseDouble(heightStr);
/*     */     } 
/*     */     
/* 391 */     this.chunkBuilder.createCollisionLine(startIdent, endIdent, height, lineRoot, tokenTargetNode, tileCoord, this.resourceGetter);
/*     */   }
/*     */   
/*     */   public void processPatrolElement(Element patrolElement, String mapId, TokenTargetNode tokenTargetNode, LineNode lineRoot, Point tileCoord) throws LoadException {
/* 395 */     String patrolName = patrolElement.getAttributeValue("name");
/* 396 */     List<Element> nodes = patrolElement.getChildren("patrol-node");
/* 397 */     for (Element node : nodes) {
/*     */       try {
/* 399 */         WorldCoordinate coord = SpatialUtils.getElementWorldCoordinate(node, mapId);
/* 400 */         String ident = node.getAttributeValue("num");
/* 401 */         float delay = Float.valueOf(node.getAttributeValue("pause")).floatValue();
/* 402 */         this.chunkBuilder.createPatrolNode(coord, ident, patrolName, delay, tokenTargetNode, tileCoord, this.resourceGetter);
/* 403 */       } catch (DataConversionException e) {
/* 404 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/*     */     
/* 408 */     List<Element> lines = patrolElement.getChildren("patrol-line");
/* 409 */     for (Element line : lines) {
/* 410 */       String startIdent = line.getAttributeValue("node1");
/* 411 */       String endIdent = line.getAttributeValue("node2");
/* 412 */       this.chunkBuilder.createPatrolLine(startIdent, endIdent, lineRoot, patrolName, tokenTargetNode, tileCoord, this.resourceGetter);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void processWaterLineElement(Element waterLineElement, TokenTargetNode tokenTargetNode, Point tileCoord) throws LoadException {
/*     */     try {
/* 419 */       List<WaterLineCoordinateSet> waterLines = new ArrayList<WaterLineCoordinateSet>();
/* 420 */       List<Element> coordSets = waterLineElement.getChildren("coordinate-set");
/* 421 */       for (Element coordSet : coordSets) {
/* 422 */         waterLines.add(new WaterLineCoordinateSet(coordSet));
/*     */       }
/*     */       
/* 425 */       float height = waterLineElement.getAttribute("height").getFloatValue();
/* 426 */       float transparency = waterLineElement.getAttribute("transparency").getFloatValue();
/* 427 */       int shoreType = waterLineElement.getAttribute("shore-type").getIntValue();
/* 428 */       int textureDistribution = waterLineElement.getAttribute("distribution-type").getIntValue();
/*     */       
/* 430 */       Element baseEl = waterLineElement.getChild("basetexture");
/* 431 */       float baseTextureScale = baseEl.getAttribute("scale").getFloatValue();
/* 432 */       float baseSpeedX = baseEl.getAttribute("speed-x").getFloatValue();
/* 433 */       float baseSpeedY = baseEl.getAttribute("speed-y").getFloatValue();
/* 434 */       String baseTexture = baseEl.getChild("resource").getAttributeValue("name");
/*     */       
/* 436 */       Element overlayEl = waterLineElement.getChild("overlaytexture");
/* 437 */       float overlayTextureScale = overlayEl.getAttribute("scale").getFloatValue();
/* 438 */       float overlaySpeedX = overlayEl.getAttribute("speed-x").getFloatValue();
/* 439 */       float overlaySpeedY = overlayEl.getAttribute("speed-y").getFloatValue();
/* 440 */       String overlayTexture = overlayEl.getChild("resource").getAttributeValue("name");
/*     */       
/* 442 */       Element shoreEl = waterLineElement.getChild("shoretexture");
/* 443 */       float shoreTextureScale = shoreEl.getAttribute("scale").getFloatValue();
/* 444 */       float shoreTextureOffset = shoreEl.getAttribute("offset").getFloatValue();
/* 445 */       float shoreSpeed = shoreEl.getAttribute("speed").getFloatValue();
/* 446 */       String shoreTexture = shoreEl.getChild("resource").getAttributeValue("name");
/*     */       
/* 448 */       this.chunkBuilder.createWaterLine(height, transparency, shoreType, textureDistribution, baseTextureScale, overlayTextureScale, shoreTextureScale, shoreTextureOffset, waterLines, baseSpeedX, baseSpeedY, overlaySpeedX, overlaySpeedY, shoreSpeed, baseTexture, overlayTexture, shoreTexture, tokenTargetNode, tileCoord, this.resourceGetter);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 455 */     catch (DataConversionException e) {
/* 456 */       throw new LoadException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void processWaterPondElement(Element waterPondElement, TokenTargetNode tokenTargetNode, Point tileCoord) throws LoadException {
/*     */     try {
/* 463 */       List<WaterPondCoordinateSet> waterLines = new ArrayList<WaterPondCoordinateSet>();
/* 464 */       List<Element> coordSets = waterPondElement.getChildren("coordinate-set");
/* 465 */       for (Element coordSet : coordSets) {
/* 466 */         waterLines.add(new WaterPondCoordinateSet(coordSet));
/*     */       }
/*     */       
/* 469 */       float height = waterPondElement.getAttribute("height").getFloatValue();
/* 470 */       float transparency = waterPondElement.getAttribute("transparency").getFloatValue();
/* 471 */       boolean useShoreTexture = (waterPondElement.getAttribute("shore-type").getIntValue() == 1);
/*     */       
/* 473 */       Element baseEl = waterPondElement.getChild("basetexture");
/* 474 */       float baseTextureScale = baseEl.getAttribute("scale").getFloatValue();
/* 475 */       float baseSpeedX = baseEl.getAttribute("speed-x").getFloatValue();
/* 476 */       float baseSpeedY = baseEl.getAttribute("speed-y").getFloatValue();
/* 477 */       String baseTexture = baseEl.getChild("resource").getAttributeValue("name");
/*     */       
/* 479 */       Element overlayEl = waterPondElement.getChild("overlaytexture");
/* 480 */       float overlayTextureScale = overlayEl.getAttribute("scale").getFloatValue();
/* 481 */       float overlaySpeedX = overlayEl.getAttribute("speed-x").getFloatValue();
/* 482 */       float overlaySpeedY = overlayEl.getAttribute("speed-y").getFloatValue();
/* 483 */       String overlayTexture = overlayEl.getChild("resource").getAttributeValue("name");
/*     */       
/* 485 */       Element shoreEl = waterPondElement.getChild("shoretexture");
/* 486 */       float shoreTextureScale = shoreEl.getAttribute("scale").getFloatValue();
/* 487 */       float shoreTextureOffset = shoreEl.getAttribute("offset").getFloatValue();
/* 488 */       float shoreSpeed = shoreEl.getAttribute("speed").getFloatValue();
/* 489 */       String shoreTexture = shoreEl.getChild("resource").getAttributeValue("name");
/*     */       
/* 491 */       this.chunkBuilder.createWaterPond(height, transparency, useShoreTexture, baseTextureScale, overlayTextureScale, shoreTextureScale, shoreTextureOffset, waterLines, baseSpeedX, baseSpeedY, overlaySpeedX, overlaySpeedY, shoreSpeed, baseTexture, overlayTexture, shoreTexture, tokenTargetNode, tileCoord, this.resourceGetter);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 498 */     catch (DataConversionException e) {
/* 499 */       throw new LoadException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void processAreaElement(Element areaElement, TokenTargetNode tokenTargetNode, Point tileCoord) {
/* 504 */     List<Element> collisionNodeElements = areaElement.getChildren("collision-node");
/* 505 */     List<CPoint2D> points = new ArrayList<CPoint2D>();
/*     */     
/*     */     try {
/* 508 */       for (Element collisionNodeElement : collisionNodeElements) {
/* 509 */         CPoint2D cPoint2D = new CPoint2D((collisionNodeElement.getAttribute("x").getIntValue() + collisionNodeElement.getAttribute("x-offset").getFloatValue()), (collisionNodeElement.getAttribute("y").getIntValue() + collisionNodeElement.getAttribute("y-offset").getFloatValue()));
/*     */         
/* 511 */         points.add(cPoint2D);
/*     */       }
/*     */     
/* 514 */     } catch (DataConversionException e) {
/* 515 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 518 */     Prop prop = new Prop("area", new WorldCoordinate(0, 0, 0.0D, 0.0D, "UNIDENTIFIED_MAP", 0));
/* 519 */     this.chunkBuilder.createArea(prop, points, tokenTargetNode, tileCoord, this.resourceGetter);
/*     */   }
/*     */   
/*     */   public void processDecalElement(Element decalElement, String mapId, TokenTargetNode tokenTargetNode, Point tileCoord) throws LoadException {
/* 523 */     DecalParameters parameters = createDecalParameters(decalElement, mapId, tokenTargetNode, tileCoord);
/* 524 */     this.chunkBuilder.createDecal(parameters.getProp(), parameters.getCoord(), parameters.getScale(), parameters.getAngle(), parameters.getOrderIndex(), parameters.getResourceName(), parameters.getTintColor(), parameters.getTokenTargetNode(), parameters.getTileCoord(), this.resourceGetter);
/*     */   }
/*     */   
/*     */   protected DecalParameters createDecalParameters(Element decalElement, String mapId, TokenTargetNode tokenTargetNode, Point tileCoord) throws LoadException {
/*     */     try {
/* 529 */       float scale = 1.0F;
/* 530 */       float angle = 0.0F;
/* 531 */       int orderIndex = 0;
/*     */       
/* 533 */       WorldCoordinate coord = SpatialUtils.getElementWorldCoordinate(decalElement, mapId);
/* 534 */       coord.addTiles(tileCoord);
/*     */       
/* 536 */       Attribute scaleAttribute = decalElement.getAttribute("scale");
/* 537 */       Attribute angleAttribute = decalElement.getAttribute("rotation");
/* 538 */       Attribute indexAttribute = decalElement.getAttribute("index");
/*     */       
/* 540 */       if (scaleAttribute != null) {
/* 541 */         scale = scaleAttribute.getFloatValue();
/*     */       }
/* 543 */       if (angleAttribute != null) {
/* 544 */         angle = angleAttribute.getFloatValue();
/*     */       }
/* 546 */       if (indexAttribute != null) {
/* 547 */         orderIndex = indexAttribute.getIntValue();
/*     */       }
/*     */       
/* 550 */       Element resourceElement = decalElement.getChild("resource");
/* 551 */       String resourceName = resourceElement.getAttributeValue("name");
/* 552 */       String name = (decalElement.getAttribute("name") == null) ? "" : decalElement.getAttribute("name").getValue();
/* 553 */       Prop prop = new Prop(name, coord);
/*     */       
/* 555 */       float[] tintColor = getTintColor(decalElement);
/*     */       
/* 557 */       return new DecalParameters(prop, coord, scale, angle, orderIndex, resourceName, tintColor, tokenTargetNode, tileCoord);
/*     */     }
/* 559 */     catch (DataConversionException e) {
/* 560 */       throw new LoadException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public float[] getTintColor(Element staticElement) throws DataConversionException {
/* 565 */     Element tintElement = staticElement.getChild("tint_color");
/* 566 */     if (tintElement == null) {
/* 567 */       return null;
/*     */     }
/*     */     
/* 570 */     return SpatialUtils.getColor(tintElement);
/*     */   }
/*     */   
/*     */   public void processParticleElement(Element particleElement, String mapId, TokenTargetNode tokenTargetNode, Point tileCoord) throws LoadException {
/*     */     try {
/* 575 */       WorldCoordinate coord = SpatialUtils.getElementWorldCoordinate(particleElement, mapId);
/* 576 */       coord.addTiles(tileCoord);
/*     */       
/* 578 */       float scale = particleElement.getAttribute("scale").getFloatValue();
/* 579 */       float angle = particleElement.getAttribute("rotation").getFloatValue();
/*     */       
/* 581 */       float z = 0.0F;
/*     */       
/* 583 */       Attribute zAtt = particleElement.getAttribute("z-value");
/* 584 */       if (zAtt != null) {
/* 585 */         z = zAtt.getFloatValue();
/*     */       }
/*     */       
/* 588 */       Element resourceElement = particleElement.getChild("resource");
/* 589 */       String resourceName = resourceElement.getAttributeValue("name");
/* 590 */       String name = (particleElement.getAttribute("name") == null) ? "" : particleElement.getAttribute("name").getValue();
/*     */ 
/*     */ 
/*     */       
/* 594 */       this.chunkBuilder.createParticleObject(name, coord, scale, angle, z, resourceName, tokenTargetNode, tileCoord, this.resourceGetter);
/*     */     }
/* 596 */     catch (DataConversionException e) {
/* 597 */       throw new LoadException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void processQuestGoToPropElements(Element element, ChunkNode chunkNode, Point tileCoord) {
/*     */     try {
/* 604 */       String mapId = chunkNode.getChunkWorldNode().getChunkWorldInfo().getMapId();
/* 605 */       WorldCoordinate coord = SpatialUtils.getElementWorldCoordinate(element, mapId);
/* 606 */       coord.addTiles(tileCoord);
/*     */       
/* 608 */       float angle = element.getAttribute("rotation").getFloatValue();
/*     */       
/* 610 */       Element resourceElement = element.getChild("resource");
/* 611 */       String resourceName = resourceElement.getAttributeValue("name");
/*     */       
/* 613 */       boolean shown = false;
/* 614 */       Attribute attrShown = element.getAttribute("shown");
/* 615 */       if (attrShown != null) {
/* 616 */         shown = attrShown.getBooleanValue();
/*     */       }
/*     */       
/* 619 */       this.chunkBuilder.createQuestGoToProp(resourceName, coord, chunkNode, tileCoord, angle, shown);
/*     */     }
/* 621 */     catch (DataConversionException e) {
/* 622 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private class DecalParameters
/*     */   {
/*     */     private TokenTargetNode tokenTargetNode;
/*     */     private Point tileCoord;
/*     */     private float scale;
/*     */     private float angle;
/*     */     private int orderIndex;
/*     */     private WorldCoordinate coord;
/*     */     private String resourceName;
/*     */     private Prop prop;
/*     */     private float[] tintColor;
/*     */     
/*     */     private DecalParameters(Prop prop, WorldCoordinate coord, float scale, float angle, int orderIndex, String resourceName, float[] tintColor, TokenTargetNode tokenTargetNode, Point tileCoord) {
/* 640 */       this.tokenTargetNode = tokenTargetNode;
/* 641 */       this.tileCoord = tileCoord;
/* 642 */       this.scale = scale;
/* 643 */       this.angle = angle;
/* 644 */       this.orderIndex = orderIndex;
/* 645 */       this.coord = coord;
/* 646 */       this.resourceName = resourceName;
/* 647 */       this.prop = prop;
/* 648 */       this.tintColor = tintColor;
/*     */     }
/*     */     
/*     */     public TokenTargetNode getTokenTargetNode() {
/* 652 */       return this.tokenTargetNode;
/*     */     }
/*     */     
/*     */     public Point getTileCoord() {
/* 656 */       return this.tileCoord;
/*     */     }
/*     */     
/*     */     public float getScale() {
/* 660 */       return this.scale;
/*     */     }
/*     */     
/*     */     public float getAngle() {
/* 664 */       return this.angle;
/*     */     }
/*     */     
/*     */     public int getOrderIndex() {
/* 668 */       return this.orderIndex;
/*     */     }
/*     */     
/*     */     public WorldCoordinate getCoord() {
/* 672 */       return this.coord;
/*     */     }
/*     */     
/*     */     public String getResourceName() {
/* 676 */       return this.resourceName;
/*     */     }
/*     */     
/*     */     public Prop getProp() {
/* 680 */       return this.prop;
/*     */     }
/*     */     
/*     */     public float[] getTintColor() {
/* 684 */       return this.tintColor;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\ChunkLoaderToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */