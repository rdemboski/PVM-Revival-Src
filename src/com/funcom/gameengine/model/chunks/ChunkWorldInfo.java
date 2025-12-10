/*     */ package com.funcom.gameengine.model.chunks;
/*     */ 
/*     */ import com.funcom.commons.FileUtils;
/*     */ import com.funcom.commons.MathUtils;
/*     */ import com.funcom.commons.StringUtils;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.factories.XmlChunkTags;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import com.funcom.gameengine.view.CameraConfig;
/*     */ import java.awt.Point;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
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
/*     */ public class ChunkWorldInfo
/*     */   implements XmlChunkTags
/*     */ {
/*     */   private String mapId;
/*     */   private String basePath;
/*     */   private int worldWidth;
/*     */   private int worldHeight;
/*     */   private int chunkWidth;
/*     */   private int chunkHeight;
/*     */   private int worldChunkWidth;
/*     */   private int worldChunkHeight;
/*     */   private WorldCoordinate startCoord;
/*     */   private WorldCoordinate mapCorner1Coord;
/*     */   private WorldCoordinate mapCorner2Coord;
/*     */   private boolean privateInstance;
/*     */   private int instanceCloseTimer;
/*     */   private float mapRotation;
/*     */   private float mapAspect;
/*     */   private int mapOffsetX;
/*     */   private int mapOffsetY;
/*     */   private float mapZoom;
/*  45 */   private String mapNameKey = "mapnames.unknown-map";
/*     */   private int numPlayersInInstance;
/*     */   private int numPlayersInInstanceHardCap;
/*     */   private Map<String, List<WorldCoordinate>> regionMap;
/*     */   private boolean mapPausable;
/*     */   
/*     */   public ChunkWorldInfo(String mapId) {
/*  52 */     this.mapId = mapId;
/*  53 */     this.regionMap = new HashMap<String, List<WorldCoordinate>>();
/*     */   }
/*     */   
/*     */   public void setSize(int width, int height) {
/*  57 */     this.worldWidth = width;
/*  58 */     this.worldHeight = height;
/*  59 */     this.chunkWidth = 20;
/*  60 */     this.chunkHeight = 20;
/*  61 */     calculateChunkCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBasePath(String basePath) {
/*  70 */     this.basePath = basePath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBasePath() {
/*  79 */     return this.basePath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBinaryChunkFilename(int x, int y) {
/*  90 */     return ((this.basePath == null) ? this.basePath : FileUtils.fixTailingSlashes((new File("binary", this.basePath)).getPath().replace('\\', '/'))) + BinaryChunkConfig.getBinaryChunkFilename(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBinaryChunkFilename(Point point) {
/* 100 */     return getBinaryChunkFilename(point.x, point.y);
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
/*     */   public String getChunkFilename(int x, int y) {
/* 112 */     return ((this.basePath == null) ? this.basePath : FileUtils.fixTailingSlashes(this.basePath)) + ChunkConfig.getChunkFilename(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getChunkFilename(Point point) {
/* 122 */     return getChunkFilename(point.x, point.y);
/*     */   }
/*     */   
/*     */   public void loadConfig(Document document) {
/* 126 */     loadConfig(document.getRootElement());
/*     */   }
/*     */   
/*     */   public void loadTileCache(Document document, ResourceGetter resourceGetter) {
/* 130 */     loadTileCache(document.getRootElement(), resourceGetter);
/*     */   }
/*     */   
/*     */   public void loadConfig(Element rootElement) {
/* 134 */     this.worldWidth = StringUtils.parseInt(rootElement.getChild("world-size").getChildTextTrim("width"), 0);
/* 135 */     this.worldHeight = StringUtils.parseInt(rootElement.getChild("world-size").getChildTextTrim("height"), 0);
/* 136 */     this.chunkWidth = StringUtils.parseInt(rootElement.getChild("chunk-size").getChildTextTrim("width"), 20);
/* 137 */     this.chunkHeight = StringUtils.parseInt(rootElement.getChild("chunk-size").getChildTextTrim("height"), 20);
/* 138 */     calculateChunkCount();
/*     */     
/* 140 */     if (CameraConfig.instance() != null) {
/* 141 */       float cameraAngle = StringUtils.parseFloat(rootElement.getChildTextTrim("cameraheight"), 30.0F);
/* 142 */       CameraConfig.instance().setCameraAngle(cameraAngle);
/* 143 */       float fov = StringUtils.parseFloat(rootElement.getChildTextTrim("fov"), 8.0F);
/* 144 */       CameraConfig.instance().setFov(fov);
/* 145 */       float xOffset = StringUtils.parseFloat(rootElement.getChildTextTrim("offsetx"), 0.0F);
/* 146 */       CameraConfig.instance().setOffestX(xOffset);
/* 147 */       float yOffset = StringUtils.parseFloat(rootElement.getChildTextTrim("offsety"), 0.0F);
/* 148 */       CameraConfig.instance().setOffestY(yOffset);
/*     */     } 
/*     */     
/* 151 */     this.startCoord = loadWorldCoordinate(rootElement, "start_point");
/* 152 */     this.mapCorner1Coord = loadWorldCoordinate(rootElement, "map-corner-1");
/* 153 */     this.mapCorner2Coord = loadWorldCoordinate(rootElement, "map-corner-2");
/* 154 */     Element mapNameKeyEl = rootElement.getChild("map_name_key");
/* 155 */     if (mapNameKeyEl != null)
/* 156 */       this.mapNameKey = mapNameKeyEl.getText(); 
/* 157 */     Element mapConfigElement = rootElement.getChild("map_config");
/* 158 */     if (mapConfigElement != null) {
/*     */       try {
/* 160 */         this.mapRotation = mapConfigElement.getAttribute("map_rotation").getFloatValue();
/* 161 */         this.mapAspect = mapConfigElement.getAttribute("map_aspect").getFloatValue();
/* 162 */         this.mapOffsetX = mapConfigElement.getAttribute("map_offset_x").getIntValue();
/* 163 */         this.mapOffsetY = mapConfigElement.getAttribute("map_offset_y").getIntValue();
/* 164 */         this.mapZoom = mapConfigElement.getAttribute("map_zoom").getFloatValue();
/* 165 */       } catch (DataConversionException e) {
/* 166 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 170 */     Element instanceElement = rootElement.getChild("map_instance");
/* 171 */     this.numPlayersInInstance = 20;
/* 172 */     this.numPlayersInInstanceHardCap = 40;
/* 173 */     if (instanceElement != null) {
/*     */       try {
/* 175 */         this.privateInstance = instanceElement.getAttribute("instance").getBooleanValue();
/* 176 */         this.instanceCloseTimer = instanceElement.getAttribute("instance_time_value").getIntValue();
/*     */         
/* 178 */         Attribute numPlayersAttrib = instanceElement.getAttribute("instance_max_players");
/* 179 */         if (numPlayersAttrib != null) {
/* 180 */           this.numPlayersInInstance = numPlayersAttrib.getIntValue();
/*     */         }
/*     */         
/* 183 */         Attribute numPlayersAttribHardCap = instanceElement.getAttribute("instance_max_players_hard_cap");
/* 184 */         if (numPlayersAttribHardCap != null) {
/* 185 */           this.numPlayersInInstanceHardCap = numPlayersAttribHardCap.getIntValue();
/*     */         }
/*     */       }
/* 188 */       catch (DataConversionException e) {
/* 189 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*     */     try {
/* 194 */       loadMapPausable(rootElement);
/* 195 */     } catch (DataConversionException e) {
/* 196 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadTileCache(Element rootElement, ResourceGetter resourceGetter) {
/* 204 */     resourceGetter.clearTileCache();
/*     */     
/* 206 */     List list = rootElement.getChildren("tileset");
/* 207 */     int max = list.size();
/* 208 */     Object obj = null;
/* 209 */     Element ele = null;
/*     */     
/* 211 */     for (int i = 0; i < max; i++) {
/* 212 */       obj = list.get(i);
/* 213 */       ele = (Element)obj;
/*     */       
/* 215 */       resourceGetter.getTile(ele.getAttributeValue("background"), ele.getAttributeValue("layer2"), ele.getAttributeValue("layer3"), ele.getAttributeValue("layer4"));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadMapPausable(Element rootElement) throws DataConversionException {
/* 224 */     Element mapPausableElement = rootElement.getChild("map_pausable");
/* 225 */     this.mapPausable = (mapPausableElement == null || mapPausableElement.getAttribute("pausable").getBooleanValue());
/*     */   }
/*     */   
/*     */   public void loadRegions(Element rootElement) throws DataConversionException {
/* 229 */     List<Element> children = rootElement.getChildren();
/* 230 */     for (Element child : children) {
/* 231 */       String regionID = child.getAttributeValue("name");
/* 232 */       List<WorldCoordinate> coordinateList = this.regionMap.get(regionID);
/*     */       
/* 234 */       if (coordinateList == null) {
/* 235 */         coordinateList = new ArrayList<WorldCoordinate>();
/*     */       }
/* 237 */       List<Element> regionChildren = child.getChildren();
/*     */       
/* 239 */       for (Element regionChild : regionChildren) {
/* 240 */         WorldCoordinate worldCoordinate = SpatialUtils.getElementWorldCoordinate(regionChild);
/* 241 */         coordinateList.add(worldCoordinate);
/*     */       } 
/* 243 */       this.regionMap.put(regionID, coordinateList);
/*     */     } 
/*     */   }
/*     */   
/*     */   private WorldCoordinate loadWorldCoordinate(Element rootElement, String elementName) {
/* 248 */     Element startElement = rootElement.getChild(elementName);
/* 249 */     WorldCoordinate ret = null;
/* 250 */     if (startElement != null) {
/*     */       try {
/* 252 */         ret = SpatialUtils.getElementWorldCoordinate(startElement, getMapId());
/* 253 */         ret.setMapId(this.mapId);
/* 254 */       } catch (DataConversionException e) {
/* 255 */         throw new RuntimeException(e);
/*     */       } 
/*     */     }
/* 258 */     return ret;
/*     */   }
/*     */   
/*     */   private void calculateChunkCount() {
/* 262 */     this.worldChunkWidth = this.worldWidth / this.chunkWidth + MathUtils.signum((this.worldWidth % this.chunkWidth));
/* 263 */     this.worldChunkHeight = this.worldHeight / this.chunkHeight + MathUtils.signum((this.worldHeight % this.chunkHeight));
/*     */   }
/*     */   
/*     */   public int getWorldWidth() {
/* 267 */     return this.worldWidth;
/*     */   }
/*     */   
/*     */   public int getWorldHeight() {
/* 271 */     return this.worldHeight;
/*     */   }
/*     */   
/*     */   public int getChunkWidth() {
/* 275 */     return this.chunkWidth;
/*     */   }
/*     */   
/*     */   public int getChunkHeight() {
/* 279 */     return this.chunkHeight;
/*     */   }
/*     */   
/*     */   public int getWorldChunkWidth() {
/* 283 */     return this.worldChunkWidth;
/*     */   }
/*     */   
/*     */   public int getWorldChunkHeight() {
/* 287 */     return this.worldChunkHeight;
/*     */   }
/*     */   
/*     */   public WorldCoordinate getWorldSize() {
/* 291 */     return new WorldCoordinate(this.worldWidth, this.worldHeight, 0.0D, 0.0D, this.mapId, 0);
/*     */   }
/*     */   
/*     */   public WorldCoordinate getStartCoord() {
/* 295 */     return this.startCoord;
/*     */   }
/*     */   
/*     */   public WorldCoordinate getMapCorner1Coord() {
/* 299 */     return this.mapCorner1Coord;
/*     */   }
/*     */   
/*     */   public WorldCoordinate getMapCorner2Coord() {
/* 303 */     return this.mapCorner2Coord;
/*     */   }
/*     */   
/*     */   public boolean isPrivateInstance() {
/* 307 */     return this.privateInstance;
/*     */   }
/*     */   
/*     */   public void setPrivateInstance(boolean privateInstance) {
/* 311 */     this.privateInstance = privateInstance;
/*     */   }
/*     */   
/*     */   public int getInstanceCloseTimer() {
/* 315 */     return this.instanceCloseTimer;
/*     */   }
/*     */   
/*     */   public void setInstanceCloseTimer(int instanceCloseTimer) {
/* 319 */     this.instanceCloseTimer = instanceCloseTimer;
/*     */   }
/*     */   
/*     */   public float getMapRotation() {
/* 323 */     return this.mapRotation;
/*     */   }
/*     */   
/*     */   public float getMapAspect() {
/* 327 */     return this.mapAspect;
/*     */   }
/*     */   
/*     */   public int getMapOffsetX() {
/* 331 */     return this.mapOffsetX;
/*     */   }
/*     */   
/*     */   public int getMapOffsetY() {
/* 335 */     return this.mapOffsetY;
/*     */   }
/*     */   
/*     */   public float getMapZoom() {
/* 339 */     return this.mapZoom;
/*     */   }
/*     */   
/*     */   public String getMapId() {
/* 343 */     return this.mapId;
/*     */   }
/*     */   
/*     */   public void setMapId(String mapId) {
/* 347 */     this.mapId = mapId;
/*     */   }
/*     */   
/*     */   public String getMapNameKey() {
/* 351 */     return this.mapNameKey;
/*     */   }
/*     */   
/*     */   public int getNumPlayersInInstanceSoftCap() {
/* 355 */     return this.numPlayersInInstance;
/*     */   }
/*     */   
/*     */   public int getNumPlayersInInstanceHardCap() {
/* 359 */     return this.numPlayersInInstanceHardCap;
/*     */   }
/*     */   
/*     */   public Map<String, List<WorldCoordinate>> getRegionMap() {
/* 363 */     return this.regionMap;
/*     */   }
/*     */   
/*     */   public void setMapPausable(boolean mapPausable) {
/* 367 */     this.mapPausable = mapPausable;
/*     */   }
/*     */   
/*     */   public boolean isMapPausable() {
/* 371 */     return this.mapPausable;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\chunks\ChunkWorldInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */