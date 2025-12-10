/*     */ package com.funcom.tcg.client.ui.maps;
/*     */ 
/*     */ import com.funcom.commons.FileUtils;
/*     */ import com.funcom.gameengine.model.chunks.ChunkWorldInfo;
/*     */ import com.funcom.gameengine.view.MapObject;
/*     */ import com.funcom.gameengine.view.TeleportMapObject;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.maps.IngameMapData;
/*     */ import com.funcom.tcg.net.message.DynamicObjectsListMessage;
/*     */ import com.funcom.tcg.net.message.RequestDynamicObjectsMessage;
/*     */ import com.funcom.tcg.net.message.TeleportObjectsListMessage;
/*     */ import com.jme.math.Vector2f;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MapModel
/*     */ {
/*  29 */   private static final Logger LOGGER = Logger.getLogger(MapModel.class.getName());
/*     */   
/*     */   private String mapImagePath;
/*     */   private ChunkWorldInfo chunkWorldInfo;
/*     */   private List<MapObject> staticMapObjectList;
/*     */   private List<MapObject> dynamicObjectList;
/*     */   private List<TeleportMapObject> teleportObjectList;
/*     */   private IngameMapData ingameMapData;
/*     */   
/*     */   public MapModel(ChunkWorldInfo chunkWorldInfo, List<MapObject> mapObjectList) {
/*  39 */     this.mapImagePath = "interface/maps/" + chunkWorldInfo.getBasePath() + "mapimage.jpg";
/*  40 */     this.chunkWorldInfo = chunkWorldInfo;
/*  41 */     this.staticMapObjectList = mapObjectList;
/*  42 */     this.ingameMapData = createIngameMapData();
/*  43 */     this.dynamicObjectList = new ArrayList<MapObject>();
/*  44 */     this.teleportObjectList = new ArrayList<TeleportMapObject>();
/*     */   }
/*     */   
/*     */   private IngameMapData createIngameMapData() {
/*  48 */     this.ingameMapData = new IngameMapData();
/*  49 */     this.ingameMapData.aspect = this.chunkWorldInfo.getMapAspect();
/*  50 */     this.ingameMapData.offsetX = this.chunkWorldInfo.getMapOffsetX();
/*  51 */     this.ingameMapData.offsetY = this.chunkWorldInfo.getMapOffsetY();
/*  52 */     this.ingameMapData.rotationDegrees = this.chunkWorldInfo.getMapRotation();
/*  53 */     this.ingameMapData.extentMax = this.chunkWorldInfo.getMapCorner2Coord();
/*  54 */     this.ingameMapData.extentMin = this.chunkWorldInfo.getMapCorner1Coord();
/*  55 */     this.ingameMapData.zoom = this.chunkWorldInfo.getMapZoom();
/*  56 */     return this.ingameMapData;
/*     */   }
/*     */   
/*     */   public String getMapImagePath() {
/*  60 */     return this.mapImagePath;
/*     */   }
/*     */   
/*     */   public Vector2f getGameplayMapSizeInTiles() {
/*  64 */     return new Vector2f(this.chunkWorldInfo.getWorldWidth(), this.chunkWorldInfo.getWorldHeight());
/*     */   }
/*     */   
/*     */   public List<MapObject> getStaticMapObjectList() {
/*  68 */     return this.staticMapObjectList;
/*     */   }
/*     */   
/*     */   public List<MapObject> getDynamicObjectList() {
/*  72 */     return this.dynamicObjectList;
/*     */   }
/*     */   
/*     */   public List<TeleportMapObject> getTeleportObjectList() {
/*  76 */     return this.teleportObjectList;
/*     */   }
/*     */   
/*     */   public IngameMapData getIngameMapData() {
/*  80 */     return this.ingameMapData;
/*     */   }
/*     */   
/*     */   public void resetDynamicObjects(Message message) {
/*  84 */     DynamicObjectsListMessage dynamicObjectsListMessage = (DynamicObjectsListMessage)message;
/*  85 */     List<MapObject> mapObjects = dynamicObjectsListMessage.getMapObjectList();
/*  86 */     this.dynamicObjectList.clear();
/*  87 */     this.dynamicObjectList.addAll(mapObjects);
/*     */   }
/*     */   
/*     */   public void resetTeleportObjects(Message message) {
/*  91 */     TeleportObjectsListMessage dynamicObjectsListMessage = (TeleportObjectsListMessage)message;
/*  92 */     List<TeleportMapObject> mapObjects = dynamicObjectsListMessage.getMapObjectList();
/*  93 */     this.teleportObjectList.clear();
/*  94 */     this.teleportObjectList.addAll(mapObjects);
/*     */   }
/*     */   
/*     */   public String getNameKey() {
/*  98 */     return this.chunkWorldInfo.getMapNameKey();
/*     */   }
/*     */   
/*     */   public String getMapId() {
/* 102 */     return this.chunkWorldInfo.getMapId();
/*     */   }
/*     */   
/*     */   public void requestDynamicMessageUpdate() {
/*     */     List<Integer> friendIds;
/* 107 */     if (TcgGame.isChatEnabled()) {
/* 108 */       friendIds = new ArrayList<Integer>(MainGameState.getFriendModel().getFriendsList().keySet());
/*     */     } else {
/* 110 */       friendIds = new ArrayList<Integer>();
/*     */     } 
/* 112 */     RequestDynamicObjectsMessage requestDynamicObjectsMessage = new RequestDynamicObjectsMessage(MainGameState.getPlayerModel().getId(), friendIds, FileUtils.trimTailingSlashes(this.chunkWorldInfo.getMapId()));
/*     */     try {
/* 114 */       NetworkHandler.instance().getIOHandler().send((Message)requestDynamicObjectsMessage);
/*     */     }
/* 116 */     catch (InterruptedException e) {
/* 117 */       LOGGER.log((Priority)Level.ERROR, "Failed to send RequestDynamicObjectsMessage!", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ChunkWorldInfo getChunkWorldInfo() {
/* 122 */     return this.chunkWorldInfo;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\maps\MapModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */