/*     */ package com.funcom.tcg.client.net.processors;
/*     */ 
/*     */ import com.funcom.commons.Vector2d;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import com.funcom.server.common.GameIOHandler;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.net.CreatureData;
/*     */ import com.funcom.tcg.client.net.MessageProcessor;
/*     */ import com.funcom.tcg.client.net.processors.loadingmanager.ProjectileUpdateLMToken;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.net.message.ProjectileUpdateMessage;
/*     */ import com.jme.bounding.BoundingBox;
/*     */ import com.jme.bounding.BoundingVolume;
/*     */ import com.jme.scene.Line;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.util.geom.BufferUtils;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ public class ProjectileUpdateProcessor
/*     */   implements MessageProcessor
/*     */ {
/*  33 */   private Map<Integer, PropNode> debugMap = new HashMap<Integer, PropNode>();
/*     */   
/*     */   int debugId;
/*     */   
/*     */   public ProjectileUpdateProcessor() {
/*  38 */     this.debugId = -1000;
/*     */   }
/*     */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/*  41 */     ProjectileUpdateMessage projectileUpdateMessage = (ProjectileUpdateMessage)message;
/*     */     
/*  43 */     if (LoadingManager.USE) {
/*  44 */       LoadingManager.INSTANCE.submit((LoadingManagerToken)new ProjectileUpdateLMToken(projectileUpdateMessage, ioHandler, creatureDataMap, playerDataMap));
/*     */     }
/*     */     else {
/*     */       
/*  48 */       List<ProjectileUpdateMessage.NewProjectileData> newProjectileDatas = projectileUpdateMessage.getNewDataList();
/*     */       
/*  50 */       for (ProjectileUpdateMessage.NewProjectileData projectileData : newProjectileDatas) {
/*  51 */         MainGameState.getProjectileFactory().createProjectile(projectileData);
/*     */       }
/*     */       
/*  54 */       List<ProjectileUpdateMessage.ProjectileUpdateData> dataList = projectileUpdateMessage.getProjectileUpdateDataList();
/*  55 */       if (dataList != null && !dataList.isEmpty()) {
/*  56 */         for (ProjectileUpdateMessage.ProjectileUpdateData projectileUpdateData : dataList) {
/*  57 */           MainGameState.getProjectileFactory().updateProjectile(projectileUpdateData);
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/*  62 */     List<ProjectileUpdateMessage.DebugProjectileData> debugList = projectileUpdateMessage.getDebugDataList();
/*  63 */     if (debugList != null && !debugList.isEmpty()) {
/*  64 */       int size = debugList.size();
/*  65 */       for (int i = 0; i < size; i++) {
/*  66 */         ProjectileUpdateMessage.DebugProjectileData data = debugList.get(i);
/*     */         
/*  68 */         processDebugData(data);
/*     */       } 
/*     */     } 
/*     */     
/*  72 */     List<ProjectileUpdateMessage.RemovedProjectileData> removedDataList = projectileUpdateMessage.getRemovedDataList();
/*     */     
/*  74 */     for (ProjectileUpdateMessage.RemovedProjectileData removedProjectileData : removedDataList) {
/*  75 */       MainGameState.getProjectileFactory().killProjectile(removedProjectileData);
/*     */       
/*  77 */       PropNode node = this.debugMap.remove(Integer.valueOf(removedProjectileData.getId()));
/*  78 */       if (node != null) {
/*  79 */         node.removeFromParent();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processDebugData(ProjectileUpdateMessage.DebugProjectileData data) {
/*  85 */     PropNode node = this.debugMap.get(Integer.valueOf(data.getId()));
/*  86 */     WorldCoordinate dataPosition = reconstructPosition(data);
/*     */     
/*  88 */     if (node == null) {
/*  89 */       Creature vendorCreature = new Creature(this.debugId--, "Debug Projectile", "Debug Projectile", dataPosition, 0.0D);
/*     */       
/*  91 */       node = new PropNode((Prop)vendorCreature, 3, "?", TcgGame.getDireEffectDescriptionFactory());
/*     */ 
/*     */ 
/*     */       
/*  95 */       FloatBuffer vertices = BufferUtils.createVector3Buffer(4);
/*  96 */       FloatBuffer normals = BufferUtils.createVector3Buffer(4);
/*     */       
/*  98 */       Vector2d[] localCorners = data.getLocalCorners();
/*     */       
/* 100 */       putCoord(vertices, (float)localCorners[0].getX(), 0.0F, (float)localCorners[0].getY());
/* 101 */       putCoord(vertices, (float)localCorners[1].getX(), 0.0F, (float)localCorners[1].getY());
/* 102 */       putCoord(vertices, (float)localCorners[2].getX(), 0.0F, (float)localCorners[2].getY());
/* 103 */       putCoord(vertices, (float)localCorners[3].getX(), 0.0F, (float)localCorners[3].getY());
/* 104 */       putCoord(normals, 0.0F, 1.0F, 0.0F);
/* 105 */       putCoord(normals, 0.0F, 1.0F, 0.0F);
/* 106 */       putCoord(normals, 0.0F, 1.0F, 0.0F);
/* 107 */       putCoord(normals, 0.0F, 1.0F, 0.0F);
/*     */ 
/*     */       
/* 110 */       Line spatial = new Line("TriggerShape");
/* 111 */       spatial.setMode(Line.Mode.Loop);
/* 112 */       spatial.reconstruct(vertices, normals, null, null);
/* 113 */       node.attachRepresentation((Spatial)spatial);
/*     */       
/* 115 */       node.setModelBound((BoundingVolume)new BoundingBox());
/* 116 */       node.updateModelBound();
/* 117 */       node.updateRenderState();
/*     */       
/* 119 */       MainGameState.getWorld().addObject((RepresentationalNode)node);
/* 120 */       this.debugMap.put(Integer.valueOf(data.getId()), node);
/*     */     } else {
/* 122 */       node.getProp().setPosition(dataPosition);
/*     */     } 
/*     */   }
/*     */   
/*     */   private WorldCoordinate reconstructPosition(ProjectileUpdateMessage.DebugProjectileData data) {
/* 127 */     WorldCoordinate worldCoordinate = data.getPos();
/* 128 */     worldCoordinate.setMapId(MainGameState.getPlayerModel().getPosition().getMapId());
/* 129 */     worldCoordinate.setInstanceReference(MainGameState.getPlayerModel().getPosition().getInstanceReference());
/* 130 */     return worldCoordinate;
/*     */   }
/*     */   
/*     */   private void putCoord(FloatBuffer buffer, float x, float y, float z) {
/* 134 */     buffer.put(x);
/* 135 */     buffer.put(y);
/* 136 */     buffer.put(z);
/*     */   }
/*     */   
/*     */   public short getMessageType() {
/* 140 */     return 224;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\ProjectileUpdateProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */