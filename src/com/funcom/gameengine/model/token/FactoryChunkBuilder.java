/*     */ package com.funcom.gameengine.model.token;
/*     */ 
/*     */ import com.funcom.commons.geom.LineWCHeight;
/*     */ import com.funcom.commons.jme.cpolygon.CPoint2D;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.chunks.ChunkNode;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.spatial.LineNode;
/*     */ import com.funcom.gameengine.view.water.WaterLineCoordinateSet;
/*     */ import com.funcom.gameengine.view.water.WaterPondCoordinateSet;
/*     */ import java.awt.Point;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ 
/*     */ public class FactoryChunkBuilder
/*     */   implements ChunkBuilder
/*     */ {
/*     */   private ChunkTokenFactory chunkTokenFactory;
/*  25 */   private HashMap<String, WorldCoordinate> collisionNodes = new HashMap<String, WorldCoordinate>();
/*     */   
/*     */   public FactoryChunkBuilder(ChunkTokenFactory chunkTokenFactory) {
/*  28 */     this.chunkTokenFactory = chunkTokenFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createLayeredTextureTile(int x, int y, String backgroundResourceName, String layer2ResourceName, String layer3ResourceName, String layer4ResourceName, String xmlBackground, String xmlCorner1, String xmlCorner2, String xmlCorner3, String xmlCorner4, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/*  37 */     Token token = this.chunkTokenFactory.createLayeredTextureTileToken(x, y, backgroundResourceName, layer2ResourceName, layer3ResourceName, layer4ResourceName, xmlBackground, xmlCorner1, xmlCorner2, xmlCorner3, xmlCorner4, tokenTargetNode, tileCoord, resourceGetter);
/*     */ 
/*     */     
/*  40 */     TokenRegister.instance().addToken(token);
/*     */   }
/*     */   
/*     */   public void createStaticObject(Prop prop, WorldCoordinate coord, float scale, float angle, float z, String resourceName, float[] tintColor, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/*  44 */     Token token = this.chunkTokenFactory.createStaticObjectToken(prop, coord, scale, angle, z, resourceName, tintColor, tokenTargetNode, tileCoord, resourceGetter);
/*  45 */     if (token != null) {
/*  46 */       TokenRegister.instance().addToken(token);
/*     */     }
/*     */   }
/*     */   
/*     */   public void createInteractibleObject(InteractibleProp prop, String defaultActionName, WorldCoordinate coord, float scale, float angle, String resourceName, String xmlResourceName, float[] tintColor, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter, String rpgId) {
/*  51 */     Token token = this.chunkTokenFactory.createInteractibleObjectToken(prop, defaultActionName, coord, scale, angle, resourceName, xmlResourceName, tintColor, tokenTargetNode, tileCoord, resourceGetter, rpgId);
/*  52 */     if (token != null) {
/*  53 */       TokenRegister.instance().addToken(token);
/*     */     }
/*     */   }
/*     */   
/*     */   public void createMeshObject(WorldCoordinate coord, float scale, float angle, float z, String name, String resourceName, float[] tintColor, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/*  58 */     Token token = this.chunkTokenFactory.createMeshObjectToken(coord, scale, angle, z, name, resourceName, tintColor, tokenTargetNode, tileCoord, resourceGetter);
/*  59 */     if (token != null) {
/*  60 */       TokenRegister.instance().addToken(token);
/*     */     }
/*     */   }
/*     */   
/*     */   public void createMergedMeshObject(ArrayList<Element> meshElements, TokenTargetNode tokenTargetNode, ResourceGetter resourceGetter) {
/*  65 */     Token token = this.chunkTokenFactory.createMergedMeshObjectToken(meshElements, tokenTargetNode, resourceGetter);
/*  66 */     if (token != null) {
/*  67 */       TokenRegister.instance().addToken(token);
/*     */     }
/*     */   }
/*     */   
/*     */   public void createWaterLine(float height, float transparency, int shoreType, int textureDistribution, float baseTextureScale, float overlayTextureScale, float shoreTextureScale, float shoreTextureOffset, List<WaterLineCoordinateSet> waterLines, float baseSpeedX, float baseSpeedY, float overlaySpeedX, float overlaySpeedY, float shoreSpeed, String baseTexture, String overlayTexture, String shoreTexture, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/*  72 */     Token token = this.chunkTokenFactory.createWaterLineToken(height, transparency, shoreType, textureDistribution, baseTextureScale, overlayTextureScale, shoreTextureScale, shoreTextureOffset, waterLines, baseSpeedX, baseSpeedY, overlaySpeedX, overlaySpeedY, shoreSpeed, baseTexture, overlayTexture, shoreTexture, tokenTargetNode, tileCoord, resourceGetter);
/*  73 */     TokenRegister.instance().addToken(token);
/*     */   }
/*     */   
/*     */   public void createWaterPond(float height, float transparency, boolean useShoreTexture, float baseTextureScale, float overlayTextureScale, float shoreTextureScale, float shoreTextureOffset, List<WaterPondCoordinateSet> pondPoints, float baseSpeedX, float baseSpeedY, float overlaySpeedX, float overlaySpeedY, float shoreSpeed, String baseTexture, String overlayTexture, String shoreTexture, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/*  77 */     Token token = this.chunkTokenFactory.createWaterPondToken(height, transparency, useShoreTexture, baseTextureScale, overlayTextureScale, shoreTextureScale, shoreTextureOffset, pondPoints, baseSpeedX, baseSpeedY, overlaySpeedX, overlaySpeedY, shoreSpeed, baseTexture, overlayTexture, shoreTexture, tokenTargetNode, tileCoord, resourceGetter);
/*  78 */     TokenRegister.instance().addToken(token);
/*     */   }
/*     */   
/*     */   public void createDecal(Prop prop, WorldCoordinate coord, float scale, float angle, int orderIndex, String resourceName, float[] tintColor, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/*  82 */     Token token = this.chunkTokenFactory.createDecalToken(prop, coord, scale, angle, orderIndex, resourceName, tintColor, tokenTargetNode, tileCoord, resourceGetter);
/*  83 */     if (token != null) {
/*  84 */       TokenRegister.instance().addToken(token);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void createParticleObject(String name, WorldCoordinate coord, float scale, float angle, float z, String resourceName, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/*  90 */     Token token = this.chunkTokenFactory.createParticleObjectToken(name, coord, scale, angle, z, resourceName, tokenTargetNode, tileCoord, resourceGetter);
/*  91 */     if (token != null) {
/*  92 */       TokenRegister.instance().addToken(token);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createCheckpoint(String name, WorldCoordinate worldCoordinate, ChunkNode chunkNode, Point tileCoord, float angle, float width, float height) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createQuestGoToProp(String resourceName, WorldCoordinate coord, ChunkNode chunkNode, Point tileCoord, float angle, boolean shown) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createCollisionNode(WorldCoordinate coord, String ident, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/* 113 */     coord.addTiles(tileCoord);
/* 114 */     this.collisionNodes.put(ident, coord);
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
/*     */   public void createCollisionLine(String startIdent, String endIdent, double height, LineNode lineRoot, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/* 126 */     WorldCoordinate startCoord = this.collisionNodes.get(startIdent);
/* 127 */     WorldCoordinate endCoord = this.collisionNodes.get(endIdent);
/* 128 */     LineWCHeight line = new LineWCHeight(startCoord, endCoord, height);
/* 129 */     lineRoot.addLine(line);
/*     */   }
/*     */ 
/*     */   
/*     */   public void createArea(Prop prop, List<CPoint2D> points, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/* 134 */     Token token = this.chunkTokenFactory.createAreaToken(prop, points, tokenTargetNode, tileCoord, resourceGetter);
/* 135 */     TokenRegister.instance().addToken(token);
/*     */   }
/*     */   
/*     */   public void createSpawnPoint(WorldCoordinate wc, float angle, String name, String resourceId, TokenTargetNode tokenTargetNode, Point tileCoord, int timerValue, boolean triggered, int triggerId, String brain, Map<String, Object> brainParameters, boolean lootIsMagnetic) {}
/*     */   
/*     */   public void createVendor(WorldCoordinate wc, String name, String resourceId, TokenTargetNode tokenTargetNode, Point tileCoord, float angle) {}
/*     */   
/*     */   public void createPatrolNode(WorldCoordinate coord, String ident, String patrolName, float delay, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {}
/*     */   
/*     */   public void createPatrolLine(String startIdent, String endIdent, LineNode lineRoot, String patrolName, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {}
/*     */   
/*     */   public void createTeleportProp(String name, WorldCoordinate interactiblePropCoord, String destinationMap, WorldCoordinate destinationCoord, String id) {}
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\FactoryChunkBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */