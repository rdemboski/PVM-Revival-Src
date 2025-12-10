/*     */ package com.funcom.tcg.client.ui.maps;
/*     */ 
/*     */ import com.funcom.commons.FileUtils;
/*     */ import com.funcom.commons.Vector2d;
/*     */ import com.funcom.commons.utils.GlobalTime;
/*     */ import com.funcom.gameengine.Updated;
/*     */ import com.funcom.gameengine.model.chunks.ChunkWorldInfo;
/*     */ import com.funcom.gameengine.model.factories.MapObjectBuilder;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManagerException;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.BinaryLoader;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.gameengine.view.MapObject;
/*     */ import com.funcom.gameengine.view.TeleportMapObject;
/*     */ import com.funcom.peeler.BananaPeel;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientQuestData;
/*     */ import com.funcom.tcg.client.net.creaturerefresher.CreatureRefresher;
/*     */ import com.funcom.tcg.client.ui.AbstractFauxWindow;
/*     */ import com.funcom.tcg.client.ui.AbstractLargeWindow;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.client.ui.TcgBContainer;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.quest.QuestModel;
/*     */ import com.funcom.tcg.maps.IngameMapUtils;
/*     */ import com.jme.math.Quaternion;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BClickthroughLabel;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BMenuItem;
/*     */ import com.jmex.bui.BPanningPane;
/*     */ import com.jmex.bui.BPopupMenu;
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import com.jmex.bui.background.ImageBackground;
/*     */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.layout.AbsoluteButChangeableLayout;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.layout.GridLayout;
/*     */ import com.jmex.bui.layout.TableLayout;
/*     */ import com.jmex.bui.util.Dimension;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.io.File;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.jdom.Document;
/*     */ 
/*     */ public class MapWindow2 extends AbstractLargeWindow implements CreatureRefresher, Updated {
/*     */   private boolean showingWorldMap;
/*     */   private MapModel mapModel;
/*     */   private MapWindowModel windowModel;
/*     */   private BLabel mapLabel;
/*     */   private MapRegionLabel regionLabel;
/*     */   private WorldMapComponent2 worldMapComponent;
/*     */   private List<BComponent> teleportLabels;
/*     */   private List<BComponent> dynamicLabels;
/*     */   private List<BComponent> questObjectiveLabels;
/*     */   private List<BComponent> staticLabels;
/*     */   public static final String DEFAULT_IMAGE_PATH = "gui/interface/maps/test-map.png";
/*     */   private static final String INFO_LABEL_STYLE = "info_label";
/*     */   private BContainer mapContainer;
/*     */   private BPanningPane panner;
/*  73 */   private Dimension scrollDir = new Dimension(0, 0);
/*     */   private float scrollDistX;
/*     */   private float scrollDistY;
/*     */   private MapObjectLabel playerLbl;
/*     */   private boolean playerCentered = true;
/*  78 */   private float scaleFactor = 1.0F;
/*     */   
/*     */   private BImage mapImage;
/*     */   
/*     */   private AbsoluteButChangeableLayout mapContainerLayout;
/*     */   private static Rectangle MAP_SCROLLER_BOUNDS;
/*     */   private long lastRefreshOnDynamicObjects;
/*     */   private TcgBContainer tcgBContainer;
/*     */   private ResourceManager resourceManager;
/*     */   private RegionQuestChangeListenerImpl regionQuestChangeListener;
/*     */   public BButton worldMapButton;
/*     */   public BContainer infoContainer;
/*     */   private BLabel hoverLabel;
/*     */   private boolean failedInit = false;
/*     */   private Vector2d mapObjectCoordsTemp;
/*     */   
/*     */   public MapWindow2(ResourceManager resourceManager, MapWindowModel windowModel) {
/*  95 */     super(null, (BLayoutManager)new AbsoluteLayout());
/*  96 */     this.mapObjectCoordsTemp = new Vector2d();
/*  97 */     this.resourceManager = resourceManager;
/*  98 */     this.windowModel = windowModel;
/*  99 */     BananaResourceProvider buiResourceProvider = new BananaResourceProvider(resourceManager);
/*     */ 
/*     */     
/* 102 */     this.teleportLabels = new ArrayList<BComponent>();
/* 103 */     this.dynamicLabels = new ArrayList<BComponent>();
/* 104 */     this.questObjectiveLabels = new ArrayList<BComponent>();
/* 105 */     this.staticLabels = new ArrayList<BComponent>();
/* 106 */     this.mapLabel = new BLabel("");
/* 107 */     this.mapLabel.setName("mapLabel");
/* 108 */     this.regionLabel = new MapRegionLabel();
/* 109 */     addDefaultCloseButton(AbstractFauxWindow.CloseButtonPosition.TOP_RIGHT);
/* 110 */     this._style = BuiUtils.createMergedClassStyleSheets(getClass(), buiResourceProvider);
/* 111 */     createComponents(512);
/*     */     
/* 113 */     initializeWorldMap(resourceManager, buiResourceProvider);
/*     */   }
/*     */   
/*     */   private void initializeWorldMap(ResourceManager resourceManager, BananaResourceProvider buiResourceProvider) {
/*     */     BananaPeel bananaPeel;
/*     */     try {
/* 119 */       bananaPeel = (BananaPeel)TcgGame.getResourceManager().getResource(BananaPeel.class, "gui/peeler/window_worldmap.xml", CacheType.NOT_CACHED);
/*     */     }
/* 121 */     catch (Exception e) {
/* 122 */       this.failedInit = true;
/* 123 */       TcgGame.LOGGER.info("Initialing of Map Window failed - no resources found");
/*     */       return;
/*     */     } 
/* 126 */     this.worldMapComponent = new WorldMapComponent2(bananaPeel, resourceManager, this.hoverLabel, this);
/* 127 */     this.failedInit = false;
/*     */   }
/*     */   
/*     */   public MapWindowModel getWindowModel() {
/* 131 */     return this.windowModel;
/*     */   }
/*     */   
/*     */   private void addPlayerIcon() {
/* 135 */     this.mapContainer.remove((BComponent)this.playerLbl);
/* 136 */     if (this.windowModel.getMapId().equals(this.mapModel.getMapId())) {
/* 137 */       MapObject playerMapObj = new MapObject(this.windowModel.getName(), this.windowModel.getPlayerPosition(), MapObject.MapObjectType.PLAYER_TYPE.getIcon());
/*     */       
/* 139 */       this.playerLbl = (MapObjectLabel)addMapObject(playerMapObj, (List<BComponent>)null);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void createComponents(int centerX) {
/* 144 */     int mapWidth = 955;
/* 145 */     int mapHeight = 505;
/*     */     
/* 147 */     this.mapContainerLayout = new AbsoluteButChangeableLayout();
/* 148 */     this.mapContainer = new BContainer((BLayoutManager)this.mapContainerLayout);
/* 149 */     this.mapContainer.setName("mapContainer");
/*     */     
/* 151 */     this.panner = new BPanningPane((BComponent)this.mapContainer);
/* 152 */     this.panner.setName("panner");
/* 153 */     this.tcgBContainer = new TcgBContainer();
/* 154 */     int stupidBoarder = 22;
/* 155 */     MAP_SCROLLER_BOUNDS = new Rectangle(stupidBoarder, stupidBoarder, mapWidth, mapHeight);
/* 156 */     this.tcgBContainer.getContentPane().add((BComponent)this.panner, MAP_SCROLLER_BOUNDS);
/*     */ 
/*     */     
/* 159 */     Rectangle MAP_CONTAINER_BOUNDS = new Rectangle(centerX - mapWidth / 2 - stupidBoarder, 678 - mapHeight - stupidBoarder - 25, mapWidth + stupidBoarder * 2, mapHeight + stupidBoarder * 2);
/*     */ 
/*     */     
/* 162 */     this.fauxWindow.add((BComponent)this.tcgBContainer, MAP_CONTAINER_BOUNDS);
/*     */     
/* 164 */     this.worldMapButton = new BButton("");
/* 165 */     this.worldMapButton.setStyleClass("map-button");
/* 166 */     this.worldMapButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 169 */             TcgUI.getUISoundPlayer().play("ClickForward");
/* 170 */             MapWindow2.this.showingWorldMap = true;
/* 171 */             MapWindow2.this.worldMapButton.setVisible(false);
/* 172 */             MapWindow2.this.infoContainer.setVisible(false);
/* 173 */             MapWindow2.this.hoverLabel.setVisible(true);
/* 174 */             MapWindow2.this.mapContainer.removeAll();
/* 175 */             MapWindow2.this.worldMapComponent.setVisible(true);
/* 176 */             MapWindow2.this.worldMapComponent.updateButtons();
/* 177 */             MapWindow2.this.titleLabel.setText(MapWindow2.this.windowModel.getLocalizer().getLocalizedText(MapWindow2.class, "mapwindow.worldmap", new String[0]));
/* 178 */             MapWindow2.this.mapContainer.add((BComponent)MapWindow2.this.worldMapComponent, new Rectangle(0, 0, 955, 505));
/*     */           }
/*     */         });
/* 181 */     this.fauxWindow.add((BComponent)this.worldMapButton, new Rectangle(centerX - mapWidth / 2, 547, 155, 131));
/*     */ 
/*     */     
/* 184 */     this.infoContainer = createInfoContainer();
/* 185 */     this.fauxWindow.add((BComponent)this.infoContainer, new Rectangle(200, 60, 1024, 80));
/*     */     
/* 187 */     this.hoverLabel = new BLabel("");
/* 188 */     this.hoverLabel.setStyleClass("hover.label.map");
/* 189 */     this.fauxWindow.add((BComponent)this.hoverLabel, new Rectangle(centerX - mapWidth / 4, 678 - mapHeight - 100 - 20, mapWidth / 2, 100));
/*     */ 
/*     */     
/* 192 */     this.hoverLabel.setVisible(false);
/*     */   }
/*     */   
/*     */   private BContainer createInfoContainer() {
/* 196 */     BContainer infoContainer = new BContainer((BLayoutManager)new GridLayout(4));
/* 197 */     BContainer firstContainer = new BContainer((BLayoutManager)new TableLayout(4));
/* 198 */     BContainer secondContainer = new BContainer((BLayoutManager)new TableLayout(4));
/*     */     
/* 200 */     BLabel youIconLabel = new BLabel("");
/* 201 */     youIconLabel.setStyleClass("you-icon-label");
/* 202 */     firstContainer.add((BComponent)youIconLabel);
/*     */     
/* 204 */     BLabel youLabel = new BLabel(this.windowModel.getLocalizer().getLocalizedText(MapWindow2.class, "mapwindow.you", new String[0]));
/* 205 */     youLabel.setStyleClass("info_label");
/* 206 */     firstContainer.add((BComponent)youLabel);
/*     */     
/* 208 */     BLabel friendIconLabel = new BLabel("");
/* 209 */     friendIconLabel.setStyleClass("friend-icon-label");
/* 210 */     firstContainer.add((BComponent)friendIconLabel);
/*     */     
/* 212 */     BLabel friendLabel = new BLabel(this.windowModel.getLocalizer().getLocalizedText(MapWindow2.class, "mapwindow.friends", new String[0]));
/* 213 */     friendLabel.setStyleClass("info_label");
/* 214 */     firstContainer.add((BComponent)friendLabel);
/*     */     
/* 216 */     BLabel objectiveIconLabel = new BLabel("");
/* 217 */     objectiveIconLabel.setStyleClass("objective-icon-label");
/* 218 */     secondContainer.add((BComponent)objectiveIconLabel);
/*     */     
/* 220 */     BLabel objectiveLabel = new BLabel(this.windowModel.getLocalizer().getLocalizedText(MapWindow2.class, "mapwindow.missionobjective", new String[0]));
/*     */     
/* 222 */     objectiveLabel.setStyleClass("info_label");
/* 223 */     secondContainer.add((BComponent)objectiveLabel);
/*     */     
/* 225 */     BLabel portalIconLabel = new BLabel("");
/* 226 */     portalIconLabel.setStyleClass("portal-icon-label");
/* 227 */     firstContainer.add((BComponent)portalIconLabel);
/*     */     
/* 229 */     BLabel portalLabel = new BLabel(this.windowModel.getLocalizer().getLocalizedText(MapWindow2.class, "mapwindow.portals", new String[0]));
/* 230 */     portalLabel.setStyleClass("info_label");
/* 231 */     firstContainer.add((BComponent)portalLabel);
/*     */     
/* 233 */     BLabel questEndIconLabel = new BLabel("");
/* 234 */     questEndIconLabel.setStyleClass("quest-handin-icon-label");
/* 235 */     secondContainer.add((BComponent)questEndIconLabel);
/*     */     
/* 237 */     BLabel questEndLabel = new BLabel(this.windowModel.getLocalizer().getLocalizedText(MapWindow2.class, "mapwindow.questending", new String[0]));
/*     */     
/* 239 */     questEndLabel.setStyleClass("info_label");
/* 240 */     secondContainer.add((BComponent)questEndLabel);
/*     */     
/* 242 */     BLabel shopIconLabel = new BLabel("");
/* 243 */     shopIconLabel.setStyleClass("shop-icon-label");
/* 244 */     firstContainer.add((BComponent)shopIconLabel);
/*     */     
/* 246 */     BLabel shopLabel = new BLabel(this.windowModel.getLocalizer().getLocalizedText(MapWindow2.class, "mapwindow.shops", new String[0]));
/* 247 */     shopLabel.setStyleClass("info_label");
/* 248 */     firstContainer.add((BComponent)shopLabel);
/*     */     
/* 250 */     BLabel availableQuestIconLabel = new BLabel("");
/* 251 */     availableQuestIconLabel.setStyleClass("available-quest-icon-label");
/* 252 */     secondContainer.add((BComponent)availableQuestIconLabel);
/*     */     
/* 254 */     BLabel availableQuestLabel = new BLabel(this.windowModel.getLocalizer().getLocalizedText(MapWindow2.class, "mapwindow.availablequest", new String[0]));
/*     */     
/* 256 */     availableQuestLabel.setStyleClass("info_label");
/* 257 */     secondContainer.add((BComponent)availableQuestLabel);
/*     */     
/* 259 */     infoContainer.add((BComponent)firstContainer);
/* 260 */     infoContainer.add((BComponent)secondContainer);
/*     */     
/* 262 */     return infoContainer;
/*     */   }
/*     */   
/*     */   private void scaleMap() {
/* 266 */     float newWidth = this.mapImage.getImageWidth() * this.scaleFactor;
/* 267 */     float newHeight = this.mapImage.getImageHeight() * this.scaleFactor;
/* 268 */     this.mapContainerLayout.setConstraints((BComponent)this.mapLabel, new Rectangle(0, 0, (int)newWidth, (int)newHeight));
/* 269 */     this.mapContainer.setBounds(0, 0, (int)newWidth, (int)newHeight);
/* 270 */     clearDynamicLabels();
/* 271 */     addDynamicObjects();
/* 272 */     clearTeleportLabels();
/* 273 */     addTeleportObjects();
/* 274 */     clearStaticLabels();
/* 275 */     addStaticObjects();
/* 276 */     clearQuestObjectiveLabels();
/* 277 */     addQuestObjectiveLabels();
/* 278 */     scaleRegion();
/*     */   }
/*     */   
/*     */   private void addQuestObjectiveLabels() {
/* 282 */     List<MapObject> mapDynamicObjects = this.windowModel.getQuestObjectivesList();
/* 283 */     for (MapObject mapObject : mapDynamicObjects) {
/* 284 */       addMapObject(mapObject, this.questObjectiveLabels);
/*     */     }
/*     */   }
/*     */   
/*     */   private void scaleRegion() {
/* 289 */     if (this.regionLabel.getRegionID() != null && !this.regionLabel.getRegionID().isEmpty()) {
/* 290 */       float newWidth = this.mapImage.getImageWidth() * this.scaleFactor;
/* 291 */       float newHeight = this.mapImage.getImageHeight() * this.scaleFactor;
/* 292 */       this.mapContainerLayout.setConstraints((BComponent)this.regionLabel, new Rectangle(0, 0, (int)newWidth, (int)newHeight));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addRegionLabel() {
/* 297 */     if (this.regionQuestChangeListener == null) {
/* 298 */       this.regionQuestChangeListener = new RegionQuestChangeListenerImpl();
/* 299 */       this.windowModel.addQuestChangeListener(this.regionQuestChangeListener);
/*     */     } 
/*     */     
/* 302 */     float newImageWidth = this.mapImage.getImageWidth() * this.scaleFactor;
/* 303 */     float newImageHeight = this.mapImage.getImageHeight() * this.scaleFactor;
/*     */     
/* 305 */     this.mapContainer.remove((BComponent)this.regionLabel);
/*     */     
/* 307 */     String regionID = this.windowModel.getQuestRegionID();
/* 308 */     if (regionID != null && !regionID.isEmpty())
/*     */     {
/* 310 */       if (this.regionLabel.getRegionID().equalsIgnoreCase(regionID)) {
/* 311 */         this.mapContainer.add((BComponent)this.regionLabel, new Rectangle(0, 0, (int)newImageWidth, (int)newImageHeight));
/*     */       } else {
/* 313 */         this.mapContainer.add((BComponent)this.regionLabel.createNewRegionImage(this.mapModel, regionID, newImageHeight, newImageWidth), new Rectangle(0, 0, (int)newImageWidth, (int)newImageHeight));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refresh(Message message) {
/* 322 */     if (this.showingWorldMap)
/*     */       return; 
/* 324 */     this.lastRefreshOnDynamicObjects = GlobalTime.getInstance().getCurrentTime();
/* 325 */     if (message instanceof com.funcom.tcg.net.message.DynamicObjectsListMessage) {
/* 326 */       this.mapModel.resetDynamicObjects(message);
/* 327 */       clearDynamicLabels();
/* 328 */       addDynamicObjects();
/*     */       
/* 330 */       clearQuestObjectiveLabels();
/* 331 */       addQuestObjectiveLabels();
/*     */     } else {
/* 333 */       this.mapModel.resetTeleportObjects(message);
/* 334 */       clearTeleportLabels();
/* 335 */       addTeleportObjects();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void clearTeleportLabels() {
/* 340 */     removeLabels(this.teleportLabels);
/*     */   }
/*     */   
/*     */   private void addTeleportObjects() {
/* 344 */     List<TeleportMapObject> mapDynamicObjects = this.mapModel.getTeleportObjectList();
/* 345 */     for (TeleportMapObject mapObject : mapDynamicObjects) {
/* 346 */       addTeleportMapObject(mapObject, this.teleportLabels);
/*     */     }
/*     */   }
/*     */   
/*     */   private void addTeleportMapObject(final TeleportMapObject mapObject, List<BComponent> labelList) {
/* 351 */     MapObjectButton mapObjectLabel = new MapObjectButton((MapObject)mapObject);
/*     */     
/* 353 */     Vector2d mapObjectCoords = new Vector2d();
/*     */     
/* 355 */     IngameMapUtils.convertToIngameMap(this.mapModel.getIngameMapData(), this.mapContainer.getWidth(), this.mapContainer.getHeight(), mapObject.getWorldCoordinate(), mapObjectCoords);
/*     */ 
/*     */     
/* 358 */     mapObjectCoords.setY(this.mapContainer.getHeight() - mapObjectCoords.getY());
/*     */     
/* 360 */     final Rectangle constraints = new Rectangle((int)mapObjectCoords.getX() - 17, (int)mapObjectCoords.getY() - 17, 35, 35);
/*     */ 
/*     */     
/* 363 */     if (constraints.x > 0 && constraints.y > 0) {
/*     */       
/* 365 */       mapObjectLabel.addListener((ComponentListener)new ActionListener()
/*     */           {
/*     */             public void actionPerformed(ActionEvent event) {
/* 368 */               List<String> dests = mapObject.getDestinationMaps();
/* 369 */               if (dests.size() == 1) {
/* 370 */                 if (MapWindow2.this.windowModel.hasNotVisitedMap(FileUtils.trimTailingSlashes(dests.get(0)))) {
/*     */                   return;
/*     */                 }
/* 373 */                 final String destMap = (String)dests.get(0) + "/";
/* 374 */                 final ChunkWorldInfo info = new ChunkWorldInfo(destMap);
/* 375 */                 if (info.getMapId().equals(MapWindow2.this.mapModel.getMapId()))
/*     */                   return; 
/* 377 */                 info.setBasePath(destMap);
/* 378 */                 String mapConfigName = destMap + "config.bunk";
/*     */                 try {
/* 380 */                   ByteBuffer blob = (ByteBuffer)MapWindow2.this.resourceManager.getResource(ByteBuffer.class, (new File("binary", mapConfigName)).getPath().replace('\\', '/'), CacheType.NOT_CACHED);
/*     */ 
/*     */                   
/* 383 */                   Document mapConfig = BinaryLoader.convertBlobToMap(blob, MapWindow2.this.windowModel.getResourceGetter());
/* 384 */                   info.loadConfig(mapConfig);
/* 385 */                   List<MapObject> mapObjects = (new MapObjectBuilder(MapWindow2.this.windowModel.getResourceGetter())).getMapObjectList(info);
/* 386 */                   MapWindow2.this.reset(new MapModel(info, mapObjects));
/* 387 */                 } catch (ResourceManagerException e) {}
/*     */               
/*     */               }
/*     */               else {
/*     */                 
/* 392 */                 BPopupMenu menu = new BPopupMenu(MapWindow2.this.tcgBContainer.getWindow());
/* 393 */                 for (String dest : dests) {
/* 394 */                   if (MapWindow2.this.windowModel.hasNotVisitedMap(FileUtils.trimTailingSlashes(dest)))
/*     */                     continue; 
/* 396 */                   final String destMap = dest + "/";
/* 397 */                   final ChunkWorldInfo info = new ChunkWorldInfo(destMap);
/* 398 */                   if (info.getMapId().equals(MapWindow2.this.mapModel.getMapId()))
/*     */                     return; 
/* 400 */                   info.setBasePath(destMap);
/* 401 */                   String mapConfigName = destMap + "config.bunk";
/*     */                   try {
/* 403 */                     ByteBuffer blob = (ByteBuffer)MapWindow2.this.resourceManager.getResource(ByteBuffer.class, (new File("binary", mapConfigName)).getPath().replace('\\', '/'), CacheType.NOT_CACHED);
/*     */ 
/*     */                     
/* 406 */                     Document mapConfig = BinaryLoader.convertBlobToMap(blob, MapWindow2.this.windowModel.getResourceGetter());
/* 407 */                     info.loadConfig(mapConfig);
/*     */                     
/* 409 */                     BMenuItem item = new BMenuItem(MapWindow2.this.windowModel.getLocalizer().getLocalizedText(MapWindow2.class, info.getMapNameKey(), new String[0]), destMap);
/*     */                     
/* 411 */                     menu.addListener((ComponentListener)new ActionListener()
/*     */                         {
/*     */                           public void actionPerformed(ActionEvent event) {
/* 414 */                             if (event.getAction().equals(destMap)) {
/* 415 */                               List<MapObject> mapObjects = (new MapObjectBuilder(MapWindow2.this.windowModel.getResourceGetter())).getMapObjectList(info);
/*     */                               
/* 417 */                               MapWindow2.this.reset(new MapModel(info, mapObjects));
/*     */                             } 
/*     */                           }
/*     */                         });
/*     */                     
/* 422 */                     menu.addMenuItem(item);
/*     */                   }
/* 424 */                   catch (ResourceManagerException e) {}
/*     */                 } 
/*     */ 
/*     */ 
/*     */                 
/* 429 */                 menu.popup(MapWindow2.this.mapContainer.getAbsoluteX() + constraints.x, MapWindow2.this.mapContainer.getAbsoluteY() + constraints.y, false);
/*     */               } 
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 435 */       this.mapContainer.add((BComponent)mapObjectLabel, constraints);
/* 436 */       labelList.add(mapObjectLabel);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void clearDynamicLabels() {
/* 441 */     removeLabels(this.dynamicLabels);
/*     */   }
/*     */   
/*     */   private void clearQuestObjectiveLabels() {
/* 445 */     removeLabels(this.questObjectiveLabels);
/*     */   }
/*     */   
/*     */   private void removeLabels(List<BComponent> labels) {
/* 449 */     for (BComponent dynamicLabel : labels) {
/* 450 */       this.mapContainer.remove(dynamicLabel);
/*     */     }
/* 452 */     labels.clear();
/*     */   }
/*     */   
/*     */   private void addDynamicObjects() {
/* 456 */     List<MapObject> mapDynamicObjects = this.mapModel.getDynamicObjectList();
/* 457 */     for (MapObject mapObject : mapDynamicObjects) {
/* 458 */       addMapObject(mapObject, this.dynamicLabels);
/*     */     }
/*     */   }
/*     */   
/*     */   private BComponent addMapObject(MapObject mapObject, List<BComponent> labelList) {
/* 463 */     MapObjectLabel mapObjectLabel = new MapObjectLabel(mapObject, this.resourceManager);
/* 464 */     Vector2d mapObjectCoords = new Vector2d();
/*     */     
/* 466 */     IngameMapUtils.convertToIngameMap(this.mapModel.getIngameMapData(), this.mapContainer.getWidth(), this.mapContainer.getHeight(), mapObject.getWorldCoordinate(), mapObjectCoords);
/*     */ 
/*     */     
/* 469 */     mapObjectCoords.setY(this.mapContainer.getHeight() - mapObjectCoords.getY());
/*     */     
/* 471 */     Rectangle constraints = new Rectangle((int)mapObjectCoords.getX() - 17, (int)mapObjectCoords.getY() - 17, 35, 35);
/*     */ 
/*     */     
/* 474 */     if (constraints.x > 0 && constraints.y > 0) {
/* 475 */       this.mapContainer.add((BComponent)mapObjectLabel, constraints);
/* 476 */       if (labelList != null)
/* 477 */         labelList.add(mapObjectLabel); 
/*     */     } 
/* 479 */     return (BComponent)mapObjectLabel;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRefreshable(short type) {
/* 484 */     return (type == 35 || type == 62);
/*     */   }
/*     */   
/*     */   public void reset(MapModel newMapModel) {
/* 488 */     this.hoverLabel.setVisible(false);
/* 489 */     this.infoContainer.setVisible(true);
/* 490 */     clearMapImage();
/* 491 */     updateMapImage(newMapModel);
/*     */   }
/*     */   
/*     */   private void clearMapImage() {
/* 495 */     this.mapLabel.setIcon(null);
/*     */   }
/*     */   
/*     */   private void updateMapImage(MapModel mapModel) {
/* 499 */     this.showingWorldMap = false;
/* 500 */     this.worldMapButton.setVisible(true);
/* 501 */     this.mapModel = mapModel;
/* 502 */     this.titleLabel.setText(this.windowModel.getLocalizer().getLocalizedText(MapWindow2.class, mapModel.getNameKey(), new String[0]));
/* 503 */     this.mapContainer.remove((BComponent)this.mapLabel);
/* 504 */     this.mapContainer.remove((BComponent)this.worldMapComponent);
/*     */     try {
/* 506 */       this.mapImage = (BImage)this.resourceManager.getResource(BImage.class, "gui/" + this.mapModel.getMapImagePath(), CacheType.CACHE_TEMPORARILY);
/* 507 */     } catch (ResourceManagerException e) {
/* 508 */       this.mapImage = (BImage)this.resourceManager.getResource(BImage.class, "gui/interface/maps/test-map.png", CacheType.CACHE_TEMPORARILY);
/*     */     } 
/* 510 */     ImageBackground imageBackground = new ImageBackground(ImageBackgroundMode.SCALE_XY, this.mapImage);
/* 511 */     this.mapLabel.setBackground(0, (BBackground)imageBackground);
/* 512 */     this.mapLabel.setBackground(1, (BBackground)imageBackground);
/* 513 */     this.mapContainer.add((BComponent)this.mapLabel, new Rectangle());
/* 514 */     this.scaleFactor = Math.max(MAP_SCROLLER_BOUNDS.width / this.mapImage.getImageWidth(), MAP_SCROLLER_BOUNDS.height / this.mapImage.getImageHeight());
/*     */     
/* 516 */     scaleMap();
/* 517 */     addRegionLabel();
/* 518 */     addPlayerIcon();
/*     */   }
/*     */   
/*     */   private void clearStaticLabels() {
/* 522 */     removeLabels(this.staticLabels);
/*     */   }
/*     */   
/*     */   private void addStaticObjects() {
/* 526 */     List<MapObject> mapObjects = this.mapModel.getStaticMapObjectList();
/* 527 */     for (MapObject mapObject : mapObjects) {
/* 528 */       addMapObject(mapObject, this.staticLabels);
/*     */     }
/*     */   }
/*     */   
/*     */   public void clear() {
/* 533 */     clearMapImage();
/* 534 */     clearStaticLabels();
/* 535 */     clearDynamicLabels();
/* 536 */     clearTeleportLabels();
/* 537 */     clearQuestObjectiveLabels();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 542 */     if (this.failedInit) {
/* 543 */       initializeWorldMap(this.resourceManager, new BananaResourceProvider(this.resourceManager));
/*     */     }
/* 545 */     super.setVisible(visible);
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(float time) {
/* 550 */     if (!isVisible() || !isValid() || this.showingWorldMap) {
/*     */       return;
/*     */     }
/* 553 */     if (GlobalTime.getInstance().getCurrentTime() - this.lastRefreshOnDynamicObjects >= 3000L) {
/*     */       
/* 555 */       this.lastRefreshOnDynamicObjects = GlobalTime.getInstance().getCurrentTime();
/* 556 */       this.mapModel.requestDynamicMessageUpdate();
/* 557 */       addPlayerIcon();
/*     */     } 
/*     */     
/* 560 */     this.scrollDistY += this.scrollDir.getHeight() * time;
/* 561 */     this.scrollDistX += this.scrollDir.getWidth() * time;
/* 562 */     this.panner.setYOffset(this.panner.getYOffset() + (int)this.scrollDistY);
/* 563 */     this.panner.setXOffset(this.panner.getXOffset() + (int)this.scrollDistX);
/* 564 */     this.scrollDistX -= (int)this.scrollDistX;
/* 565 */     this.scrollDistY -= (int)this.scrollDistY;
/* 566 */     updatePlayerIcon();
/* 567 */     if (this.playerCentered) {
/* 568 */       centerOnPlayer();
/*     */     }
/*     */   }
/*     */   
/*     */   private void centerOnPlayer() {
/* 573 */     this.panner.setXOffset(-((this.playerLbl.getBounds()).x - (this.panner.getBounds()).width / 2));
/* 574 */     this.panner.setYOffset(-((this.playerLbl.getBounds()).y - (this.panner.getBounds()).height / 2));
/*     */   }
/*     */   
/*     */   private void updatePlayerIcon() {
/* 578 */     if (this.playerLbl == null) {
/*     */       return;
/*     */     }
/* 581 */     this.mapObjectCoordsTemp.setNull();
/* 582 */     IngameMapUtils.convertToIngameMap(this.mapModel.getIngameMapData(), this.mapContainer.getWidth(), this.mapContainer.getHeight(), this.windowModel.getPlayerPosition(), this.mapObjectCoordsTemp);
/*     */ 
/*     */     
/* 585 */     this.mapObjectCoordsTemp.setY(this.mapContainer.getHeight() - this.mapObjectCoordsTemp.getY());
/* 586 */     Quaternion rotation = (new Quaternion()).fromAngleAxis((float)(-this.windowModel.getPlayerAngle() - Math.toRadians(135.0D)), new Vector3f(0.0F, 0.0F, 1.0F));
/*     */     
/* 588 */     this.playerLbl.getObjectImage().setLocalRotation(rotation);
/*     */     
/* 590 */     this.playerLbl.setBounds((int)this.mapObjectCoordsTemp.getX() - 17, (int)this.mapObjectCoordsTemp.getY() - 17, 35, 35);
/*     */   }
/*     */   
/*     */   private class RegionQuestChangeListenerImpl extends QuestModel.QuestChangeAdapter {
/*     */     private RegionQuestChangeListenerImpl() {}
/*     */     
/*     */     public void questAdded(QuestModel questModel, ClientQuestData quest) {
/* 597 */       MapWindow2.this.addRegionLabel();
/*     */     }
/*     */ 
/*     */     
/*     */     public void missionAdded(QuestModel questModel, ClientQuestData mission) {
/* 602 */       MapWindow2.this.addRegionLabel();
/*     */     }
/*     */ 
/*     */     
/*     */     public void questCompleted(QuestModel questModel, ClientQuestData quest) {
/* 607 */       MapWindow2.this.addRegionLabel();
/*     */     }
/*     */ 
/*     */     
/*     */     public void missionCompleted(QuestModel questModel, ClientQuestData mission) {
/* 612 */       MapWindow2.this.addRegionLabel();
/*     */     }
/*     */ 
/*     */     
/*     */     public void questUpdated(QuestModel questModel, ClientQuestData quest) {
/* 617 */       MapWindow2.this.addRegionLabel();
/*     */     }
/*     */ 
/*     */     
/*     */     public void missionUpdated(QuestModel questModel, ClientQuestData mission) {
/* 622 */       MapWindow2.this.addRegionLabel();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\maps\MapWindow2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */