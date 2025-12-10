/*     */ package com.funcom.tcg.client.net.creaturebuilders;
/*     */ 
/*     */ import com.funcom.commons.PerformanceGraphNode;
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.commons.dfx.NoSuchDFXException;
/*     */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*     */ import com.funcom.gameengine.jme.modular.XmlModularDescription;
/*     */ import com.funcom.gameengine.model.input.UserActionHandler;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManagerException;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import com.funcom.gameengine.view.AnimationMapper;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.monsters.MonsterDescription;
/*     */ import com.funcom.rpgengine2.monsters.MonsterManager;
/*     */ import com.funcom.tcg.TcgConstants;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.view.modular.ClientDescribedModularNode;
/*     */ import com.funcom.tcg.net.message.CreatureCreationMessage;
/*     */ import com.funcom.tcg.rpg.CreatureVisualDescription;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.util.Debug;
/*     */ import com.jme.util.stat.StatCollector;
/*     */ import org.jdom.Document;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractModularCreatureBuilder
/*     */   implements CreatureBuilder
/*     */ {
/*     */   protected final MonsterManager monsterManager;
/*     */   
/*     */   public AbstractModularCreatureBuilder(MonsterManager monsterManager) {
/*  42 */     this.monsterManager = monsterManager;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void startTiming() {
/*  47 */     if (Debug.stats)
/*  48 */       StatCollector.startStat(PerformanceGraphNode.TrackingStat.CREATURE_BUILDING.statType); 
/*     */   }
/*     */   
/*     */   private static void endTiming() {
/*  52 */     if (Debug.stats)
/*  53 */       StatCollector.endStat(PerformanceGraphNode.TrackingStat.CREATURE_BUILDING.statType); 
/*     */   }
/*     */   
/*     */   public PropNode build(CreatureCreationMessage message) {
/*  57 */     startTiming();
/*  58 */     Creature monster = new Creature(message.getCreatureId(), message.getName(), message.getType(), message.getCoord(), message.getRadius());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  66 */     monster.getPosition().setMapId(MainGameState.getPlayerModel().getPosition().getMapId());
/*  67 */     monster.getPosition().setInstanceReference(MainGameState.getPlayerModel().getPosition().getInstanceReference());
/*     */     
/*  69 */     MonsterDescription description = this.monsterManager.getDescription(message.getType());
/*  70 */     CreatureVisualDescription monsterVisuals = TcgGame.getVisualRegistry().getCreatureVisualForClassId(description.getModelXml());
/*     */     
/*  72 */     if (monsterVisuals == null) {
/*  73 */       System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n!!!\n!!! NO MONSTER VISUAL FOR MODEl: " + description.getModelXml() + "\n!!!\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
/*     */ 
/*     */       
/*  76 */       monsterVisuals = new CreatureVisualDescription();
/*  77 */       monsterVisuals.setAlwaysOnDfx("");
/*  78 */       monsterVisuals.setDeathDfx("");
/*  79 */       monsterVisuals.setIcon("");
/*  80 */       monsterVisuals.setId(description.getModelXml());
/*  81 */       monsterVisuals.setIdleDfx("");
/*  82 */       monsterVisuals.setInteractDfx("");
/*  83 */       monsterVisuals.setWalkDfx("");
/*  84 */       monsterVisuals.setXmlDocumentPath(description.getModelXml() + ".xml");
/*     */     } 
/*     */     
/*  87 */     monster.addMappedDfx("move", monsterVisuals.getWalkDfx());
/*  88 */     monster.addMappedDfx("idle", monsterVisuals.getIdleDfx());
/*  89 */     monster.addMappedDfx("interact", monsterVisuals.getInteractDfx());
/*  90 */     monster.addMappedDfx("die", monsterVisuals.getDeathDfx());
/*  91 */     PropNode propNode = buildModel(monster, monsterVisuals);
/*  92 */     propNode.setAngle(message.getAngle());
/*     */     
/*  94 */     if (!monsterVisuals.getAlwaysOnDfx().isEmpty()) {
/*     */       try {
/*  96 */         DireEffectDescription alwaysOnDfxDescription = propNode.getEffectDescriptionFactory().getDireEffectDescription(monsterVisuals.getAlwaysOnDfx(), false);
/*  97 */         if (!alwaysOnDfxDescription.isEmpty()) {
/*  98 */           DireEffect dfx = alwaysOnDfxDescription.createInstance(propNode, UsageParams.EMPTY_PARAMS);
/*  99 */           propNode.addDfx(dfx);
/*     */         } 
/* 101 */       } catch (NoSuchDFXException e) {}
/*     */     }
/*     */     
/* 104 */     UserActionHandler actionHandler = null;
/* 105 */     actionHandler = createActionHandler(monster, message, propNode);
/* 106 */     propNode.setActionHandler(actionHandler);
/* 107 */     endTiming();
/* 108 */     return propNode;
/*     */   }
/*     */   
/*     */   protected abstract UserActionHandler createActionHandler(Creature paramCreature, CreatureCreationMessage paramCreatureCreationMessage, PropNode paramPropNode);
/*     */   
/*     */   private PropNode buildModel(Creature creature, CreatureVisualDescription monsterVisuals) {
/* 114 */     String xmlDocumentPath = monsterVisuals.getXmlDocumentPath();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 119 */       Document document = (Document)TcgGame.getResourceManager().getResource(Document.class, xmlDocumentPath, CacheType.CACHE_TEMPORARILY);
/* 120 */       XmlModularDescription modularDescription = new XmlModularDescription(document);
/*     */       
/* 122 */       PropNode propNode = new PropNode((Prop)creature, 3, "", TcgGame.getDireEffectDescriptionFactory());
/* 123 */       ClientDescribedModularNode clientDescribedModularNode = new ClientDescribedModularNode((ModularDescription)modularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager());
/*     */ 
/*     */       
/* 126 */       clientDescribedModularNode.reloadCharacter();
/* 127 */       propNode.attachRepresentation((Spatial)clientDescribedModularNode);
/* 128 */       if (monsterVisuals.hasShadow()) {
/* 129 */         SpatialUtils.addShadow(propNode, TcgGame.getResourceManager());
/*     */       }
/* 131 */       propNode.initializeAllEffects(TcgGame.getResourceGetter(), MainGameState.getWorld().getParticleSurface());
/*     */       
/* 133 */       return propNode;
/* 134 */     } catch (ResourceManagerException e) {
/* 135 */       throw new RuntimeException("Error loading creature with modular description: " + xmlDocumentPath + ", " + monsterVisuals, e);
/* 136 */     } catch (Exception e) {
/* 137 */       throw new RuntimeException("Error loading creature with modular description: " + xmlDocumentPath, e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\creaturebuilders\AbstractModularCreatureBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */