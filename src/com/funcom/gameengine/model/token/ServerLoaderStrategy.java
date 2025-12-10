/*     */ package com.funcom.gameengine.model.token;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.ai.BrainData;
/*     */ import com.funcom.gameengine.ai.patrol.Patrol;
/*     */ import com.funcom.gameengine.ai.patrol.PatrolInformation;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.chunks.ChunkNode;
/*     */ import com.funcom.gameengine.model.chunks.ChunkWorldInfo;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.spatial.LineNode;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Point;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.jdom.Attribute;
/*     */ import org.jdom.DataConversionException;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ public class ServerLoaderStrategy
/*     */   implements ChunkLoaderStrategy
/*     */ {
/*  26 */   private static final Logger LOG = Logger.getLogger(ServerLoaderStrategy.class.getName());
/*     */   
/*     */   private ChunkWorldInfo chunkWorldInfo;
/*     */   private ChunkFetcherStrategy chunkFetcherStrategy;
/*     */   private ChunkBuilder chunkBuilder;
/*     */   private LineNode lineRoot;
/*     */   private ResourceManager resourceManager;
/*     */   private int defaultSpawnpointTimer;
/*     */   private ResourceGetter serverResourceGetter;
/*     */   
/*     */   public ServerLoaderStrategy(ChunkWorldInfo chunkWorldInfo, ChunkFetcherStrategy chunkFetcherStrategy, ChunkBuilder chunkBuilder, LineNode lineRoot, ResourceManager resourceManager, int defaultSpawnpointTimer, ResourceGetter serverResourceGetter) {
/*  37 */     this.chunkWorldInfo = chunkWorldInfo;
/*  38 */     this.chunkFetcherStrategy = chunkFetcherStrategy;
/*  39 */     this.chunkBuilder = chunkBuilder;
/*  40 */     this.lineRoot = lineRoot;
/*  41 */     this.resourceManager = resourceManager;
/*  42 */     this.defaultSpawnpointTimer = defaultSpawnpointTimer;
/*  43 */     this.serverResourceGetter = serverResourceGetter;
/*     */   }
/*     */   
/*     */   public void process(ChunkLoaderToken chunkLoaderToken, WorldCoordinate worldCoordinate) {
/*  47 */     ChunkNode chunkNode = this.chunkFetcherStrategy.createChunkNode(worldCoordinate);
/*  48 */     Document document = this.chunkFetcherStrategy.getBinaryChunkDocument(this.chunkWorldInfo, chunkNode.getChunkNumber());
/*  49 */     Element root = document.getRootElement();
/*     */     
/*  51 */     Dimension size = getMapSize(root);
/*  52 */     chunkNode.setLineRoot(this.lineRoot);
/*  53 */     chunkNode.setSize(size.width, size.height);
/*     */     
/*     */     try {
/*  56 */       String mapId = this.chunkWorldInfo.getMapId();
/*     */       
/*  58 */       List<Element> collisionNodeElements = root.getChildren("collision-node");
/*  59 */       for (Element collisionElement : collisionNodeElements) {
/*  60 */         processCollisionNodes(worldCoordinate, mapId, (TokenTargetNode)chunkNode, collisionElement);
/*     */       }
/*     */       
/*  63 */       List<Element> collisionElements = root.getChildren("collision-line");
/*  64 */       for (Element collisionElement : collisionElements) {
/*  65 */         processCollisionLines(worldCoordinate, (TokenTargetNode)chunkNode, collisionElement);
/*     */       }
/*     */       
/*  68 */       List<Element> patrolElements = root.getChildren("patrol");
/*  69 */       for (Element patrolElement : patrolElements) {
/*  70 */         processPatrolElement(patrolElement, mapId, (TokenTargetNode)chunkNode, worldCoordinate.getTileCoord());
/*     */       }
/*     */       
/*  73 */       List<Element> spawnElements = root.getChildren("spawn-point");
/*  74 */       for (Element spawnPointElement : spawnElements) {
/*  75 */         processSpawnPoints(worldCoordinate, (TokenTargetNode)chunkNode, spawnPointElement, mapId);
/*     */       }
/*     */       
/*  78 */       List<Element> vendorElements = root.getChildren("vendor");
/*  79 */       for (Element vendortElement : vendorElements) {
/*  80 */         processVendors(worldCoordinate, (TokenTargetNode)chunkNode, vendortElement);
/*     */       }
/*     */       
/*  83 */       List<Element> interactiveElements = root.getChildren("interactible-object");
/*  84 */       for (Element interactibleElement : interactiveElements) {
/*  85 */         processInteractibleProps(worldCoordinate, mapId, interactibleElement);
/*     */       }
/*  87 */       List<Element> checkpointElements = root.getChildren("check_point");
/*  88 */       for (Element checkpointElement : checkpointElements) {
/*  89 */         processCheckPoints(checkpointElement, mapId, chunkNode, worldCoordinate.getTileCoord());
/*     */       }
/*     */       
/*  92 */       List<Element> questGoToPropElements = root.getChildren("quest-goto-prop");
/*  93 */       for (Element questGoToPropElement : questGoToPropElements) {
/*  94 */         processQuestGoToPropElements(questGoToPropElement, mapId, chunkNode, worldCoordinate.getTileCoord());
/*     */       }
/*     */     }
/*  97 */     catch (DataConversionException e) {
/*  98 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void processQuestGoToPropElements(Element questGoToPropElement, String mapId, ChunkNode chunkNode, Point tileCoord) {
/*     */     try {
/* 105 */       WorldCoordinate worldCoordinate = SpatialUtils.getElementWorldCoordinate(questGoToPropElement, mapId);
/* 106 */       worldCoordinate.setMapId(this.chunkWorldInfo.getMapId());
/* 107 */       worldCoordinate.addTiles(tileCoord);
/*     */       
/* 109 */       Element resourceElement = questGoToPropElement.getChild("resource");
/* 110 */       String resourceName = resourceElement.getAttributeValue("name");
/*     */       
/* 112 */       float angle = questGoToPropElement.getAttribute("rotation").getFloatValue();
/*     */       
/* 114 */       boolean shown = false;
/* 115 */       Attribute attrShown = questGoToPropElement.getAttribute("shown");
/* 116 */       if (attrShown != null) {
/* 117 */         shown = attrShown.getBooleanValue();
/*     */       }
/*     */       
/* 120 */       this.chunkBuilder.createQuestGoToProp(resourceName, worldCoordinate, chunkNode, tileCoord, angle, shown);
/* 121 */     } catch (DataConversionException e) {
/* 122 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void processCheckPoints(Element checkpointElement, String mapId, ChunkNode chunkNode, Point tileCoord) {
/*     */     try {
/* 130 */       WorldCoordinate coord = SpatialUtils.getElementWorldCoordinate(checkpointElement, mapId);
/* 131 */       coord.setMapId(this.chunkWorldInfo.getMapId());
/* 132 */       coord.addTiles(tileCoord);
/*     */       
/* 134 */       Element resourceElement = checkpointElement.getChild("resource");
/* 135 */       String resourceName = resourceElement.getAttributeValue("name");
/*     */       
/* 137 */       float angle = checkpointElement.getAttribute("rotation").getFloatValue();
/* 138 */       float width = checkpointElement.getAttribute("width").getFloatValue();
/* 139 */       float height = checkpointElement.getAttribute("height").getFloatValue();
/*     */       
/* 141 */       this.chunkBuilder.createCheckpoint(resourceName, coord, chunkNode, tileCoord, angle, width, height);
/*     */     }
/* 143 */     catch (DataConversionException e) {
/* 144 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processCollisionLines(WorldCoordinate worldCoordinate, TokenTargetNode chunkNode, Element collisionElement) {
/* 149 */     String startIdent = collisionElement.getAttributeValue("node1");
/* 150 */     String endIdent = collisionElement.getAttributeValue("node2");
/* 151 */     double height = 0.0D;
/* 152 */     if (collisionElement.getAttribute("height") != null) {
/* 153 */       String heightStr = collisionElement.getAttributeValue("height");
/* 154 */       height = Double.parseDouble(heightStr);
/*     */     } 
/*     */     
/* 157 */     this.chunkBuilder.createCollisionLine(startIdent, endIdent, height, this.lineRoot, chunkNode, worldCoordinate.getTileCoord(), null);
/*     */   }
/*     */   
/*     */   private void processCollisionNodes(WorldCoordinate worldCoordinate, String mapId, TokenTargetNode chunkNode, Element collisionElement) throws DataConversionException {
/* 161 */     String ident = collisionElement.getAttributeValue("num");
/* 162 */     WorldCoordinate coord = SpatialUtils.getElementWorldCoordinate(collisionElement, mapId);
/* 163 */     this.chunkBuilder.createCollisionNode(coord, ident, chunkNode, worldCoordinate.getTileCoord(), null);
/*     */   }
/*     */   
/*     */   private void processSpawnPoints(WorldCoordinate worldCoordinate, TokenTargetNode chunkNode, Element spawnPointElement, String mapId) throws DataConversionException {
/* 167 */     int x = spawnPointElement.getAttribute("x").getIntValue();
/* 168 */     int y = spawnPointElement.getAttribute("y").getIntValue();
/* 169 */     double xOffset = spawnPointElement.getAttribute("x-offset").getDoubleValue();
/* 170 */     double yOffset = spawnPointElement.getAttribute("y-offset").getDoubleValue();
/* 171 */     float angle = spawnPointElement.getAttribute("rotation").getFloatValue();
/* 172 */     WorldCoordinate wc = new WorldCoordinate(x, y, xOffset, yOffset, this.chunkWorldInfo.getMapId(), 0);
/*     */     
/* 174 */     String name = (spawnPointElement.getAttribute("name") == null) ? "" : spawnPointElement.getAttribute("name").getValue();
/*     */ 
/*     */     
/* 177 */     BrainData brainData = parseBrainData(spawnPointElement, mapId, name);
/*     */     
/* 179 */     int timerValue = (spawnPointElement.getAttribute("timer") == null) ? this.defaultSpawnpointTimer : spawnPointElement.getAttribute("timer").getIntValue();
/*     */ 
/*     */     
/* 182 */     boolean triggered = false;
/* 183 */     int triggerId = 0;
/*     */     try {
/* 185 */       triggerId = (spawnPointElement.getAttribute("triggered-id") == null) ? 1 : spawnPointElement.getAttribute("triggered-id").getIntValue();
/*     */       
/* 187 */       triggered = (spawnPointElement.getAttribute("triggered") != null && spawnPointElement.getAttribute("triggered").getBooleanValue());
/* 188 */     } catch (DataConversionException e) {
/* 189 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 192 */     boolean lootIsMagnetic = false;
/*     */     try {
/* 194 */       lootIsMagnetic = (spawnPointElement.getAttribute("loot-is-magnetic") != null && spawnPointElement.getAttribute("loot-is-magnetic").getBooleanValue());
/* 195 */     } catch (DataConversionException e) {
/* 196 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 199 */     String resourceID = spawnPointElement.getChild("resource").getAttribute("name").getValue();
/* 200 */     this.chunkBuilder.createSpawnPoint(wc, angle, name, resourceID, chunkNode, worldCoordinate.getTileCoord(), timerValue, triggered, triggerId, brainData.getId(), brainData.getParams(), lootIsMagnetic);
/*     */   }
/*     */   
/*     */   private BrainData parseBrainData(Element spawnPointElement, String mapId, String name) {
/*     */     BrainData brainData;
/* 205 */     Element brainElement = spawnPointElement.getChild("brain");
/* 206 */     Element brainFileElement = spawnPointElement.getChild("brainfile");
/*     */     
/* 208 */     if (brainElement != null && brainFileElement != null) {
/* 209 */       throw new RuntimeException("spawnpoint has both external brain file and embedded brain params: spawnpointName=" + name);
/*     */     }
/*     */ 
/*     */     
/* 213 */     if (brainFileElement != null) {
/* 214 */       String brainFilePath = brainFileElement.getAttributeValue("src");
/* 215 */       Document brainFileDoc = (Document)this.resourceManager.getResource(Document.class, brainFilePath);
/* 216 */       brainElement = brainFileDoc.getRootElement();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 221 */     if (brainElement != null) {
/* 222 */       brainData = new BrainData(brainElement, mapId);
/*     */     } else {
/* 224 */       brainData = new BrainData();
/*     */     } 
/*     */     
/* 227 */     return brainData;
/*     */   }
/*     */   
/*     */   private void processVendors(WorldCoordinate worldCoordinate, TokenTargetNode chunkNode, Element vendortElement) throws DataConversionException {
/* 231 */     int x = vendortElement.getAttribute("x").getIntValue();
/* 232 */     int y = vendortElement.getAttribute("y").getIntValue();
/* 233 */     double xOffset = vendortElement.getAttribute("x-offset").getDoubleValue();
/* 234 */     double yOffset = vendortElement.getAttribute("y-offset").getDoubleValue();
/* 235 */     float angle = vendortElement.getAttribute("rotation").getFloatValue();
/* 236 */     WorldCoordinate wc = new WorldCoordinate(x, y, xOffset, yOffset, this.chunkWorldInfo.getMapId(), 0);
/*     */     
/* 238 */     String name = (vendortElement.getAttribute("name") == null) ? "" : vendortElement.getAttribute("name").getValue();
/*     */ 
/*     */     
/* 241 */     String resourceID = vendortElement.getChild("resource").getAttribute("name").getValue();
/* 242 */     this.chunkBuilder.createVendor(wc, name, resourceID, chunkNode, worldCoordinate.getTileCoord(), angle);
/*     */   }
/*     */   
/*     */   private void processInteractibleProps(WorldCoordinate worldCoordinate, String mapId, Element interactibleElement) throws DataConversionException {
/* 246 */     WorldCoordinate interactiblePropCoord = SpatialUtils.getElementWorldCoordinate(interactibleElement, mapId);
/* 247 */     interactiblePropCoord.addTiles(worldCoordinate.getTileCoord());
/*     */     
/* 249 */     String propName = interactibleElement.getAttributeValue("name");
/*     */     
/* 251 */     Element interactibleLinkElement = interactibleElement.getChild("resource");
/* 252 */     String resourceName = interactibleLinkElement.getAttributeValue("name");
/*     */     
/* 254 */     Document doc = this.serverResourceGetter.getDocument(resourceName, CacheType.CACHE_TEMPORARILY);
/* 255 */     Element rootElement = doc.getRootElement();
/*     */     
/* 257 */     Element actionElement = rootElement.getChild("action");
/* 258 */     String destinationMap = actionElement.getChild("filename").getTextTrim();
/*     */     
/* 260 */     Element destElement = actionElement.getChild("destination");
/* 261 */     WorldCoordinate destinationCoord = SpatialUtils.getElementWorldCoordinate(destElement, mapId);
/*     */     
/* 263 */     Element child = actionElement.getChild("id");
/* 264 */     String id = null;
/* 265 */     if (child != null) {
/* 266 */       id = child.getTextTrim();
/*     */     }
/*     */     
/* 269 */     String actionType = actionElement.getAttributeValue("type");
/* 270 */     if ("zone".equalsIgnoreCase(actionType)) {
/* 271 */       this.chunkBuilder.createTeleportProp(propName, interactiblePropCoord, destinationMap, destinationCoord, id);
/*     */     } else {
/* 273 */       throw new DataConversionException("ServerLoaderStrategy --> processInteractibleProps: ", "No builder is made for this actionType: " + actionType);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void processPatrolElement(Element patrolElement, String mapId, TokenTargetNode tokenTargetNode, Point tileCoord) {
/* 278 */     String patrolName = patrolElement.getAttributeValue("name");
/* 279 */     Patrol patrol = new Patrol(patrolName);
/* 280 */     PatrolInformation.instance().addPatrol(this.chunkWorldInfo.getBasePath(), patrol);
/*     */     
/* 282 */     List<Element> nodes = patrolElement.getChildren("patrol-node");
/* 283 */     for (Element node : nodes) {
/*     */       try {
/* 285 */         WorldCoordinate coord = SpatialUtils.getElementWorldCoordinate(node, mapId);
/* 286 */         String ident = node.getAttributeValue("num");
/* 287 */         float delay = Float.valueOf(node.getAttributeValue("pause")).floatValue();
/* 288 */         this.chunkBuilder.createPatrolNode(coord, ident, patrolName, delay, tokenTargetNode, tileCoord, null);
/* 289 */       } catch (DataConversionException e) {
/* 290 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/*     */     
/* 294 */     List<Element> lines = patrolElement.getChildren("patrol-line");
/* 295 */     for (Element line : lines) {
/* 296 */       String startIdent = line.getAttributeValue("node1");
/* 297 */       String endIdent = line.getAttributeValue("node2");
/* 298 */       this.chunkBuilder.createPatrolLine(startIdent, endIdent, this.lineRoot, patrolName, tokenTargetNode, tileCoord, null);
/*     */     } 
/*     */     
/* 301 */     patrol.clearPatrolNodes();
/*     */   }
/*     */   
/*     */   private Dimension getMapSize(Element root) {
/* 305 */     Element infoElement = root.getChild("info");
/* 306 */     Element chunkSizeElement = infoElement.getChild("chunk-size");
/*     */     try {
/* 308 */       return new Dimension(chunkSizeElement.getAttribute("width").getIntValue(), chunkSizeElement.getAttribute("height").getIntValue());
/*     */     }
/* 310 */     catch (DataConversionException e) {
/* 311 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\ServerLoaderStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */