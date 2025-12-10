/*     */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.commons.dfx.NoSuchDFXException;
/*     */ import com.funcom.gameengine.model.command.Command;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.creatures.RpgCreatureConstants;
/*     */ import com.funcom.rpgengine2.pickupitems.AbstractPickUpDescription;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.command.DieCommand;
/*     */ import com.funcom.tcg.client.model.PropNodeRegister;
/*     */ import com.funcom.tcg.client.net.CreatureData;
/*     */ import com.funcom.tcg.net.message.CreatureDeletionMessage;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CreatureDeletionLMToken
/*     */   extends LoadingManagerToken
/*     */ {
/*  31 */   CreatureDeletionMessage deletionMessage = null;
/*  32 */   Map<Integer, CreatureData> creatureDataMap = null;
/*  33 */   Map<Integer, CreatureData> playerDataMap = null;
/*     */   
/*     */   public CreatureDeletionLMToken(CreatureDeletionMessage deletionMessage, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap) {
/*  36 */     this.deletionMessage = deletionMessage;
/*  37 */     this.creatureDataMap = creatureDataMap;
/*  38 */     this.playerDataMap = playerDataMap;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int update() throws Exception {
/*  44 */     int identity = this.deletionMessage.getCreatureId();
/*  45 */     int creatureType = this.deletionMessage.getCreatureType();
/*  46 */     PropNodeRegister propNodeRegister = TcgGame.getPropNodeRegister();
/*     */     
/*  48 */     if (creatureType == RpgCreatureConstants.Type.PLAYER_CREATURE_TYPE_ID.getTypeId()) {
/*  49 */       deleteClientCreature(this.playerDataMap, this.deletionMessage, identity, propNodeRegister);
/*  50 */     } else if (creatureType == RpgCreatureConstants.Type.MONSTER_CREATURE_TYPE_ID.getTypeId()) {
/*  51 */       deleteClientCreature(this.creatureDataMap, this.deletionMessage, identity, TcgGame.getMonsterRegister());
/*  52 */     } else if (creatureType == RpgCreatureConstants.Type.LOOT_CREATURE_TYPE_ID.getTypeId()) {
/*  53 */       deleteLootCreature(identity, TcgGame.getMonsterRegister());
/*  54 */     } else if (creatureType == RpgCreatureConstants.Type.WALK_OVER_LOOT_CREATURE_TYPE.getTypeId()) {
/*  55 */       deletePickUpLoot(this.deletionMessage, identity, TcgGame.getMonsterRegister());
/*     */     } else {
/*  57 */       throw new IllegalStateException("Unknown creature type scheduled for deletion");
/*     */     } 
/*     */     
/*  60 */     return 3;
/*     */   }
/*     */   
/*     */   private void deletePickUpLoot(CreatureDeletionMessage deletionMessage, int identity, PropNodeRegister propNodeRegister) {
/*  64 */     PropNode propNode = propNodeRegister.getPropNode(Integer.valueOf(identity));
/*  65 */     if (propNode != null) {
/*  66 */       propNodeRegister.removePropNode(propNode);
/*  67 */       if (deletionMessage.isDead()) {
/*  68 */         printPickupText(propNode);
/*  69 */         propNode.getEffects().removeAllParticles();
/*  70 */         Creature creature = (Creature)propNode.getProp();
/*  71 */         String deathDFX = "pickup-pickedup";
/*  72 */         deathDFX = creature.getMappedDfx(deathDFX);
/*     */         
/*     */         try {
/*  75 */           DireEffectDescription stateDFXDescription = propNode.getEffectDescriptionFactory().getDireEffectDescription(deathDFX, false);
/*     */           
/*  77 */           if (!stateDFXDescription.isEmpty()) {
/*  78 */             DireEffect dfx = stateDFXDescription.createInstance(propNode, UsageParams.EMPTY_PARAMS);
/*  79 */             creature.immediateCommand((Command)new DieCommand(propNode, dfx, null, null, 0.0F));
/*     */           } else {
/*  81 */             removeCreature(propNode);
/*     */           } 
/*  83 */         } catch (NoSuchDFXException e) {
/*  84 */           removeCreature(propNode);
/*     */         } 
/*     */       } else {
/*  87 */         removeCreature(propNode);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void removeCreature(PropNode propNode) {
/*  93 */     propNode.removeFromParent();
/*  94 */     propNode.getEffects().removeAllParticles();
/*     */   }
/*     */ 
/*     */   
/*     */   private void printPickupText(PropNode propNode) {
/*  99 */     Creature creature = (Creature)propNode.getProp();
/* 100 */     AbstractPickUpDescription lootDesc = TcgGame.getRpgLoader().getPickUpManager().getDescription(creature.getName());
/*     */     
/* 102 */     if (isGoldLoot(lootDesc)) {
/* 103 */       propNode.printCoinPickupText(parseValue(lootDesc));
/* 104 */     } else if (isXpLoot(lootDesc)) {
/* 105 */       propNode.printXpPickupText(parseValue(lootDesc));
/*     */     } 
/*     */   }
/*     */   
/*     */   private String parseValue(AbstractPickUpDescription lootDesc) {
/* 110 */     String id = lootDesc.getId();
/* 111 */     int afterLastMinus = id.lastIndexOf("-") + 1;
/* 112 */     return id.substring(afterLastMinus, id.length());
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isGoldLoot(AbstractPickUpDescription lootDesc) {
/* 117 */     return lootDesc.getId().startsWith("pickup-coin");
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isXpLoot(AbstractPickUpDescription lootDesc) {
/* 122 */     return lootDesc.getId().startsWith("pickup-xp");
/*     */   }
/*     */   
/*     */   private void deleteLootCreature(int identity, PropNodeRegister propNodeRegister) {
/* 126 */     PropNode propNode = propNodeRegister.getPropNode(Integer.valueOf(identity));
/* 127 */     if (propNode != null) {
/* 128 */       propNodeRegister.removePropNode(propNode);
/* 129 */       propNode.removeFromParent();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void deleteClientCreature(Map<Integer, CreatureData> creatureDataMap, CreatureDeletionMessage deletionMessage, int identity, PropNodeRegister propNodeRegister) {
/* 134 */     PropNode propNode = propNodeRegister.getPropNode(Integer.valueOf(identity));
/* 135 */     if (propNode != null) {
/* 136 */       propNodeRegister.removePropNode(propNode);
/* 137 */       if (deletionMessage.isDead()) {
/* 138 */         propNode.getProp().setDead(true);
/*     */         
/* 140 */         LoadingManager.INSTANCE.submit(new AbstractDeathLMToken(propNode, deletionMessage.getElement(), deletionMessage.getImpact(), propNodeRegister));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 161 */         removeCreature(propNode);
/*     */       } 
/*     */     } else {
/* 164 */       int i = 0;
/*     */     } 
/* 166 */     creatureDataMap.remove(Integer.valueOf(identity));
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\CreatureDeletionLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */