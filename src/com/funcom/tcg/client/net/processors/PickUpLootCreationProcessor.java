/*     */ package com.funcom.tcg.client.net.processors;
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.commons.dfx.NoSuchDFXException;
/*     */ import com.funcom.gameengine.ai.Brain;
/*     */ import com.funcom.gameengine.jme.modular.XmlModularDescription;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.ResourceGetterImpl;
/*     */ import com.funcom.gameengine.model.action.AbstractAction;
/*     */ import com.funcom.gameengine.model.action.Action;
/*     */ import com.funcom.gameengine.model.command.Command;
/*     */ import com.funcom.gameengine.model.input.ActionDependentMouseOver;
/*     */ import com.funcom.gameengine.model.input.DefaultActionInteractActionHandler;
/*     */ import com.funcom.gameengine.model.input.MouseOver;
/*     */ import com.funcom.gameengine.model.input.UserActionHandler;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import com.funcom.gameengine.view.AnimationMapper;
/*     */ import com.funcom.gameengine.view.ModularNode;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import com.funcom.rpgengine2.pickupitems.AbstractPickUpDescription;
/*     */ import com.funcom.server.common.GameIOHandler;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.TcgConstants;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.command.ExecuteDFXCommand;
/*     */ import com.funcom.tcg.client.model.rpg.MagneticLootBrain;
/*     */ import com.funcom.tcg.client.net.CreatureData;
/*     */ import com.funcom.tcg.client.net.MessageProcessor;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.net.processors.loadingmanager.PickUpLootLMToken;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.net.message.PickUpLootCreationMessage;
/*     */ import com.funcom.tcg.net.message.PickupLootMessage;
/*     */ import com.jme.scene.Spatial;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jdom.Document;
/*     */ 
/*     */ public class PickUpLootCreationProcessor implements MessageProcessor {
/*     */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/*  47 */     PickUpLootCreationMessage pickUpLootCreationMessage = (PickUpLootCreationMessage)message;
/*     */     
/*  49 */     if (LoadingManager.USE) {
/*  50 */       LoadingManager.INSTANCE.submitByPriority((LoadingManagerToken)new PickUpLootLMToken(pickUpLootCreationMessage), PrioritizedLoadingTokenQueue.TokenPriority.PRIORITY_CREATURES);
/*     */     } else {
/*     */       
/*  53 */       PropNode creatureNode = createPropNode(pickUpLootCreationMessage);
/*     */       
/*  55 */       MainGameState.getWorld().addObject((RepresentationalNode)creatureNode);
/*  56 */       TcgGame.getMonsterRegister().addPropNode(creatureNode);
/*  57 */       creatureNode.updateRenderState();
/*     */     } 
/*     */   }
/*     */   private PropNode createPropNode(PickUpLootCreationMessage pickUpLootCreationMessage) {
/*     */     DefaultActionInteractActionHandler defaultActionInteractActionHandler;
/*  62 */     int pickUpLootId = pickUpLootCreationMessage.getPickUpLootId();
/*  63 */     AbstractPickUpDescription description = TcgGame.getRpgLoader().getPickUpManager().getDescription(pickUpLootCreationMessage.getPickupDescriptionId());
/*     */     
/*  65 */     Creature creature = new Creature(pickUpLootId, pickUpLootCreationMessage.getPickupDescriptionId(), pickUpLootCreationMessage.getPickupDescriptionId(), pickUpLootCreationMessage.getWorldCoordinate(), pickUpLootCreationMessage.getRadius());
/*     */     
/*  67 */     UserActionHandler userActionHandler = null;
/*  68 */     if (!description.isTriggerByDistance()) {
/*     */       
/*  70 */       creature.addAction((Action)new PickupAction(pickUpLootId));
/*  71 */       defaultActionInteractActionHandler = new DefaultActionInteractActionHandler((InteractibleProp)creature, (Creature)MainGameState.getPlayerModel(), "loot", MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)new ActionDependentMouseOver(MainGameState.getMouseCursorSetter(), (InteractibleProp)creature, MainGameState.getActionNameToCursorMapping()));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     if (pickUpLootCreationMessage.isLootMagnetic()) {
/*  79 */       creature.setBrain((Brain)new MagneticLootBrain(creature, pickUpLootId));
/*     */     }
/*     */     
/*  82 */     mapDFXs(creature, description);
/*     */     
/*  84 */     PropNode propNode = new PropNode((Prop)creature, 3, "", TcgGame.getDireEffectDescriptionFactory(), (UserActionHandler)defaultActionInteractActionHandler);
/*     */ 
/*     */ 
/*     */     
/*  88 */     createLootModel(propNode, description);
/*  89 */     startSpawnDFX(propNode, creature);
/*  90 */     return propNode;
/*     */   }
/*     */   
/*     */   private void startSpawnDFX(PropNode propNode, Creature creature) {
/*  94 */     String spawnDFX = "pickup-spawn";
/*  95 */     spawnDFX = creature.getMappedDfx(spawnDFX);
/*     */     try {
/*  97 */       DireEffectDescription stateDFXDescription = propNode.getEffectDescriptionFactory().getDireEffectDescription(spawnDFX, false);
/*  98 */       DireEffect dfx = stateDFXDescription.createInstance(propNode, UsageParams.EMPTY_PARAMS);
/*  99 */       ExecuteDFXCommand executeDFXCommand = new ExecuteDFXCommand(propNode, dfx, 0.0F);
/* 100 */       if (executeDFXCommand.isFinished()) {
/* 101 */         executeDFXCommand.update(0.0F);
/*     */       } else {
/* 103 */         creature.immediateCommand((Command)executeDFXCommand);
/*     */       } 
/* 105 */     } catch (NoSuchDFXException e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void mapDFXs(Creature creature, AbstractPickUpDescription description) {
/* 112 */     creature.addMappedDfx("idle", description.getIdleDFX());
/* 113 */     creature.addMappedDfx("pickup-pickedup", description.getPickUpDFX());
/* 114 */     creature.addMappedDfx("pickup-spawn", description.getSpawnDFX());
/*     */   }
/*     */   
/*     */   private void createLootModel(PropNode propNode, AbstractPickUpDescription description) {
/* 118 */     Document document = (Document)TcgGame.getResourceManager().getResource(Document.class, description.getMesh(), CacheType.CACHE_TEMPORARILY);
/* 119 */     XmlModularDescription modularDescription = new XmlModularDescription(document);
/* 120 */     ModularNode modularNode = new ModularNode((ModularDescription)modularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getResourceManager());
/*     */ 
/*     */ 
/*     */     
/* 124 */     modularNode.setTextureLoaderFactoryManager(MainGameState.getTextureLoaderFactoryManager());
/* 125 */     Map<Object, Object> runtimeParams = new HashMap<Object, Object>();
/* 126 */     runtimeParams.put(AbstractPickUpDescription.class, description);
/* 127 */     modularNode.setTextureLoaderRuntimeParams(runtimeParams);
/*     */     
/* 129 */     modularNode.reloadCharacter();
/* 130 */     propNode.attachRepresentation((Spatial)modularNode);
/* 131 */     propNode.initializeAllEffects((ResourceGetter)new ResourceGetterImpl(TcgGame.getResourceManager()), MainGameState.getWorld().getParticleSurface());
/*     */     
/* 133 */     SpatialUtils.addSquaredShadow(propNode, TcgGame.getResourceManager(), 1.1F);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getMessageType() {
/* 138 */     return 33;
/*     */   }
/*     */   
/*     */   private static class PickupAction extends AbstractAction {
/*     */     private static final String ACTION_NAME = "loot";
/*     */     private final int pickUpLootId;
/*     */     
/*     */     public PickupAction(int pickUpLootId) {
/* 146 */       this.pickUpLootId = pickUpLootId;
/*     */     }
/*     */ 
/*     */     
/*     */     public void perform(InteractibleProp invoker) {
/*     */       try {
/* 152 */         PickupLootMessage message = new PickupLootMessage(this.pickUpLootId);
/* 153 */         NetworkHandler.instance().getIOHandler().send((Message)message);
/* 154 */       } catch (InterruptedException e) {
/* 155 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 161 */       return "loot";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\PickUpLootCreationProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */