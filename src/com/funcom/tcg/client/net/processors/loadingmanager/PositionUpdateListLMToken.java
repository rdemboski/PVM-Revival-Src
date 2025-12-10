/*     */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.rpgengine2.creatures.RpgCreatureConstants;
/*     */ import com.funcom.server.common.GameIOHandler;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.controllers.InterpolationController;
/*     */ import com.funcom.tcg.client.model.PropNodeRegister;
/*     */ import com.funcom.tcg.client.net.CreatureData;
/*     */ import com.funcom.tcg.client.net.GameOperationState;
/*     */ import com.funcom.tcg.net.message.PositionUpdateListMessage;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PositionUpdateListLMToken
/*     */   extends LoadingManagerToken
/*     */ {
/*  24 */   PositionUpdateListMessage posListMsg = null;
/*  25 */   GameIOHandler ioHandler = null;
/*  26 */   Map<Integer, CreatureData> creatureDataMap = null;
/*  27 */   Map<Integer, CreatureData> playerDataMap = null;
/*     */   
/*     */   private Map<Integer, CreatureData> unknownCreatureDataMap;
/*     */   private Map<Integer, CreatureData> unknownPlayerDataMap;
/*     */   
/*     */   public PositionUpdateListLMToken(PositionUpdateListMessage posListMsg, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/*  33 */     this.posListMsg = posListMsg;
/*  34 */     this.ioHandler = ioHandler;
/*  35 */     this.creatureDataMap = creatureDataMap;
/*  36 */     this.playerDataMap = playerDataMap;
/*  37 */     this.unknownCreatureDataMap = unknownCreatureDataMap;
/*  38 */     this.unknownPlayerDataMap = unknownPlayerDataMap;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int update() throws Exception {
/*  44 */     PositionUpdateListMessage.PositionIterator posIterator = this.posListMsg.getPositionIterator();
/*  45 */     if (posIterator != null) {
/*  46 */       while (posIterator.hasMoreIDs()) {
/*  47 */         CreatureData data, unknownData; PropNode propNode; int id = posIterator.nextID();
/*     */         
/*  49 */         int creatureType = posIterator.getCreatureType();
/*     */ 
/*     */ 
/*     */         
/*  53 */         if (creatureType == RpgCreatureConstants.Type.PLAYER_CREATURE_TYPE_ID.getTypeId()) {
/*  54 */           PropNodeRegister propNodeRegister = TcgGame.getPropNodeRegister();
/*  55 */           propNode = propNodeRegister.getPropNode(Integer.valueOf(id));
/*  56 */           data = this.playerDataMap.get(Integer.valueOf(id));
/*  57 */           unknownData = this.unknownPlayerDataMap.get(Integer.valueOf(id));
/*     */         } else {
/*  59 */           PropNodeRegister propNodeRegister = TcgGame.getMonsterRegister();
/*  60 */           propNode = propNodeRegister.getPropNode(Integer.valueOf(id));
/*  61 */           data = this.creatureDataMap.get(Integer.valueOf(id));
/*  62 */           unknownData = this.unknownCreatureDataMap.get(Integer.valueOf(id));
/*     */         } 
/*     */         
/*  65 */         if (propNode != null && data != null) {
/*  66 */           if (unknownData != null) {
/*  67 */             data.getNetworkCoord().add(unknownData.getNetworkCoord());
/*  68 */             if (creatureType == RpgCreatureConstants.Type.PLAYER_CREATURE_TYPE_ID.getTypeId()) {
/*  69 */               this.unknownPlayerDataMap.remove(Integer.valueOf(id));
/*     */             } else {
/*  71 */               this.unknownCreatureDataMap.remove(Integer.valueOf(id));
/*     */             } 
/*     */           } 
/*  74 */           WorldCoordinate worldCoordinate = data.getNetworkCoord();
/*  75 */           float f1 = data.getNetworkAngle();
/*  76 */           posIterator.evalPosition(worldCoordinate);
/*  77 */           float f2 = posIterator.getAngle();
/*     */           
/*  79 */           updateNetworkCoord(propNode, worldCoordinate, f2);
/*     */           continue;
/*     */         } 
/*  82 */         if (unknownData == null) {
/*  83 */           unknownData = new CreatureData(new WorldCoordinate(), 0.0F);
/*  84 */           if (creatureType == RpgCreatureConstants.Type.PLAYER_CREATURE_TYPE_ID.getTypeId()) {
/*  85 */             this.unknownPlayerDataMap.put(Integer.valueOf(id), unknownData);
/*     */           } else {
/*  87 */             this.unknownCreatureDataMap.put(Integer.valueOf(id), unknownData);
/*     */           } 
/*     */         } 
/*  90 */         WorldCoordinate prevCoord = unknownData.getNetworkCoord();
/*  91 */         float prevAngle = unknownData.getNetworkAngle();
/*  92 */         posIterator.evalPosition(prevCoord);
/*  93 */         float recentAngle = posIterator.getAngle();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  98 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateNetworkCoord(PropNode prop, WorldCoordinate prevCoord, float angle) {
/* 103 */     InterpolationController cont = GameOperationState.findInterpolationController(prop);
/* 104 */     if (cont == null) {
/*     */       
/* 106 */       prop.getPosition().set(prevCoord);
/* 107 */       prop.setAngle(angle);
/*     */     } else {
/* 109 */       cont.moveToPosition(prevCoord, angle);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\PositionUpdateListLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */