/*     */ package com.funcom.tcg.client.net.processors;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.commons.dfx.NoSuchDFXException;
/*     */ import com.funcom.gameengine.model.command.Command;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.PrioritizedLoadingTokenQueue;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.creatures.RpgCreatureConstants;
/*     */ import com.funcom.rpgengine2.pickupitems.AbstractPickUpDescription;
/*     */ import com.funcom.server.common.GameIOHandler;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.command.DieCommand;
/*     */ import com.funcom.tcg.client.model.PropNodeRegister;
/*     */ import com.funcom.tcg.client.net.CreatureData;
/*     */ import com.funcom.tcg.client.net.MessageProcessor;
/*     */ import com.funcom.tcg.client.net.processors.loadingmanager.CreatureDeletionLMToken;
/*     */ import com.funcom.tcg.net.message.CreatureDeletionMessage;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class CreatureDeletionProcessor
/*     */   extends AbstractDeathHandler
/*     */   implements MessageProcessor {
/*     */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/*  30 */     CreatureDeletionMessage deletionMessage = (CreatureDeletionMessage)message;
/*     */     
/*  32 */     if (LoadingManager.USE) {
/*     */       
/*  34 */       LoadingManager.INSTANCE.submitByPriority((LoadingManagerToken)new CreatureDeletionLMToken(deletionMessage, creatureDataMap, playerDataMap), PrioritizedLoadingTokenQueue.TokenPriority.PRIORITY_CREATURES);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  39 */       int identity = deletionMessage.getCreatureId();
/*  40 */       int creatureType = deletionMessage.getCreatureType();
/*  41 */       PropNodeRegister propNodeRegister = TcgGame.getPropNodeRegister();
/*     */       
/*  43 */       if (creatureType == RpgCreatureConstants.Type.PLAYER_CREATURE_TYPE_ID.getTypeId()) {
/*  44 */         deleteClientCreature(playerDataMap, deletionMessage, identity, propNodeRegister);
/*  45 */       } else if (creatureType == RpgCreatureConstants.Type.MONSTER_CREATURE_TYPE_ID.getTypeId()) {
/*  46 */         deleteClientCreature(creatureDataMap, deletionMessage, identity, TcgGame.getMonsterRegister());
/*  47 */       } else if (creatureType == RpgCreatureConstants.Type.LOOT_CREATURE_TYPE_ID.getTypeId()) {
/*  48 */         deleteLootCreature(identity, TcgGame.getMonsterRegister());
/*  49 */       } else if (creatureType == RpgCreatureConstants.Type.WALK_OVER_LOOT_CREATURE_TYPE.getTypeId()) {
/*  50 */         deletePickUpLoot(deletionMessage, identity, TcgGame.getMonsterRegister());
/*     */       } else {
/*  52 */         throw new IllegalStateException("Unknown creature type scheduled for deletion");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void deletePickUpLoot(CreatureDeletionMessage deletionMessage, int identity, PropNodeRegister propNodeRegister) {
/*  58 */     PropNode propNode = propNodeRegister.getPropNode(Integer.valueOf(identity));
/*  59 */     if (propNode != null) {
/*  60 */       propNodeRegister.removePropNode(propNode);
/*  61 */       if (deletionMessage.isDead()) {
/*  62 */         printPickupText(propNode);
/*  63 */         propNode.getEffects().removeAllParticles();
/*  64 */         Creature creature = (Creature)propNode.getProp();
/*  65 */         String deathDFX = "pickup-pickedup";
/*  66 */         deathDFX = creature.getMappedDfx(deathDFX);
/*     */         
/*     */         try {
/*  69 */           DireEffectDescription stateDFXDescription = propNode.getEffectDescriptionFactory().getDireEffectDescription(deathDFX, false);
/*     */           
/*  71 */           if (!stateDFXDescription.isEmpty()) {
/*  72 */             DireEffect dfx = stateDFXDescription.createInstance(propNode, UsageParams.EMPTY_PARAMS);
/*  73 */             creature.immediateCommand((Command)new DieCommand(propNode, dfx, null, null, 0.0F));
/*     */           } else {
/*  75 */             removeCreature(propNode);
/*     */           } 
/*  77 */         } catch (NoSuchDFXException e) {
/*  78 */           removeCreature(propNode);
/*     */         } 
/*     */       } else {
/*  81 */         removeCreature(propNode);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void printPickupText(PropNode propNode) {
/*  88 */     Creature creature = (Creature)propNode.getProp();
/*  89 */     AbstractPickUpDescription lootDesc = TcgGame.getRpgLoader().getPickUpManager().getDescription(creature.getName());
/*     */     
/*  91 */     if (isGoldLoot(lootDesc)) {
/*  92 */       propNode.printCoinPickupText(parseValue(lootDesc));
/*  93 */     } else if (isXpLoot(lootDesc)) {
/*  94 */       propNode.printXpPickupText(parseValue(lootDesc));
/*     */     } 
/*     */   }
/*     */   
/*     */   private String parseValue(AbstractPickUpDescription lootDesc) {
/*  99 */     String id = lootDesc.getId();
/* 100 */     int afterLastMinus = id.lastIndexOf("-") + 1;
/* 101 */     return id.substring(afterLastMinus, id.length());
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isGoldLoot(AbstractPickUpDescription lootDesc) {
/* 106 */     return lootDesc.getId().startsWith("pickup-coin");
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isXpLoot(AbstractPickUpDescription lootDesc) {
/* 111 */     return lootDesc.getId().startsWith("pickup-xp");
/*     */   }
/*     */   
/*     */   private void deleteLootCreature(int identity, PropNodeRegister propNodeRegister) {
/* 115 */     PropNode propNode = propNodeRegister.getPropNode(Integer.valueOf(identity));
/* 116 */     if (propNode != null) {
/* 117 */       propNodeRegister.removePropNode(propNode);
/* 118 */       propNode.removeFromParent();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void deleteClientCreature(Map<Integer, CreatureData> creatureDataMap, CreatureDeletionMessage deletionMessage, int identity, PropNodeRegister propNodeRegister) {
/* 123 */     PropNode propNode = propNodeRegister.getPropNode(Integer.valueOf(identity));
/* 124 */     if (propNode != null) {
/* 125 */       propNodeRegister.removePropNode(propNode);
/* 126 */       if (deletionMessage.isDead()) {
/* 127 */         propNode.getProp().setDead(true);
/* 128 */         PropNode corpse = createCorpse(propNode, deletionMessage.getElement(), deletionMessage.getImpact());
/* 129 */         if (corpse != null && propNode.getProp() instanceof com.funcom.tcg.client.model.rpg.ClientPlayer)
/* 130 */           propNodeRegister.addPropNode(corpse); 
/*     */       } else {
/* 132 */         removeCreature(propNode);
/*     */       } 
/*     */     } 
/* 135 */     creatureDataMap.remove(Integer.valueOf(identity));
/*     */   }
/*     */   
/*     */   public short getMessageType() {
/* 139 */     return 12;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\CreatureDeletionProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */