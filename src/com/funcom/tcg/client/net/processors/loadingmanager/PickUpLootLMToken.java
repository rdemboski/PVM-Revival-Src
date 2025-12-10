/*     */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.commons.dfx.NoSuchDFXException;
/*     */ import com.funcom.gameengine.ai.Brain;
/*     */ import com.funcom.gameengine.jme.modular.ModularDescription;
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
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import com.funcom.gameengine.view.AnimationMapper;
/*     */ import com.funcom.gameengine.view.ModularNode;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.pickupitems.AbstractPickUpDescription;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.TcgConstants;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.command.ExecuteDFXCommand;
/*     */ import com.funcom.tcg.client.model.rpg.MagneticLootBrain;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.net.message.PickUpLootCreationMessage;
/*     */ import com.funcom.tcg.net.message.PickupLootMessage;
/*     */ import com.jme.scene.Spatial;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jdom.Document;
/*     */ 
/*     */ public class PickUpLootLMToken
/*     */   extends LoadingManagerToken {
/*  46 */   private PickUpLootCreationMessage pickUpLootCreationMessage = null;
/*     */   
/*     */   public PickUpLootLMToken(PickUpLootCreationMessage pickUpLootCreationMessage) {
/*  49 */     this.pickUpLootCreationMessage = pickUpLootCreationMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   public int update() {
/*     */     DefaultActionInteractActionHandler defaultActionInteractActionHandler;
/*  55 */     int pickUpLootId = this.pickUpLootCreationMessage.getPickUpLootId();
/*  56 */     AbstractPickUpDescription description = TcgGame.getRpgLoader().getPickUpManager().getDescription(this.pickUpLootCreationMessage.getPickupDescriptionId());
/*     */     
/*  58 */     Creature creature = new Creature(pickUpLootId, this.pickUpLootCreationMessage.getPickupDescriptionId(), this.pickUpLootCreationMessage.getPickupDescriptionId(), this.pickUpLootCreationMessage.getWorldCoordinate(), this.pickUpLootCreationMessage.getRadius());
/*     */     
/*  60 */     UserActionHandler userActionHandler = null;
/*  61 */     if (!description.isTriggerByDistance()) {
/*     */       
/*  63 */       creature.addAction((Action)new PickupAction(pickUpLootId));
/*  64 */       defaultActionInteractActionHandler = new DefaultActionInteractActionHandler((InteractibleProp)creature, (Creature)MainGameState.getPlayerModel(), "loot", MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)new ActionDependentMouseOver(MainGameState.getMouseCursorSetter(), (InteractibleProp)creature, MainGameState.getActionNameToCursorMapping()));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  71 */     if (this.pickUpLootCreationMessage.isLootMagnetic()) {
/*  72 */       creature.setBrain((Brain)new MagneticLootBrain(creature, pickUpLootId));
/*     */     }
/*     */     
/*  75 */     mapDFXs(creature, description);
/*     */     
/*  77 */     PropNode propNode = new PropNode((Prop)creature, 3, "", TcgGame.getDireEffectDescriptionFactory(), (UserActionHandler)defaultActionInteractActionHandler);
/*     */ 
/*     */ 
/*     */     
/*  81 */     createLootModel(propNode, description);
/*  82 */     startSpawnDFX(propNode, creature);
/*     */     
/*  84 */     MainGameState.getWorld().addObject((RepresentationalNode)propNode);
/*  85 */     TcgGame.getMonsterRegister().addPropNode(propNode);
/*  86 */     propNode.updateRenderState();
/*     */ 
/*     */     
/*  89 */     return 3;
/*     */   }
/*     */   
/*     */   private void createLootModel(PropNode propNode, AbstractPickUpDescription description) {
/*  93 */     Document document = (Document)TcgGame.getResourceManager().getResource(Document.class, description.getMesh(), CacheType.CACHE_TEMPORARILY);
/*  94 */     XmlModularDescription modularDescription = new XmlModularDescription(document);
/*  95 */     ModularNode modularNode = new ModularNode((ModularDescription)modularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getResourceManager());
/*     */ 
/*     */ 
/*     */     
/*  99 */     modularNode.setTextureLoaderFactoryManager(MainGameState.getTextureLoaderFactoryManager());
/* 100 */     Map<Object, Object> runtimeParams = new HashMap<Object, Object>();
/* 101 */     runtimeParams.put(AbstractPickUpDescription.class, description);
/* 102 */     modularNode.setTextureLoaderRuntimeParams(runtimeParams);
/*     */     
/* 104 */     modularNode.reloadCharacter();
/* 105 */     propNode.attachRepresentation((Spatial)modularNode);
/* 106 */     propNode.initializeAllEffects((ResourceGetter)new ResourceGetterImpl(TcgGame.getResourceManager()), MainGameState.getWorld().getParticleSurface());
/*     */     
/* 108 */     SpatialUtils.addSquaredShadow(propNode, TcgGame.getResourceManager(), 1.1F);
/*     */   }
/*     */   
/*     */   private void startSpawnDFX(PropNode propNode, Creature creature) {
/* 112 */     String spawnDFX = "pickup-spawn";
/* 113 */     spawnDFX = creature.getMappedDfx(spawnDFX);
/*     */     try {
/* 115 */       DireEffectDescription stateDFXDescription = propNode.getEffectDescriptionFactory().getDireEffectDescription(spawnDFX, false);
/* 116 */       DireEffect dfx = stateDFXDescription.createInstance(propNode, UsageParams.EMPTY_PARAMS);
/* 117 */       ExecuteDFXCommand executeDFXCommand = new ExecuteDFXCommand(propNode, dfx, 0.0F);
/* 118 */       if (executeDFXCommand.isFinished()) {
/* 119 */         executeDFXCommand.update(0.0F);
/*     */       } else {
/* 121 */         creature.immediateCommand((Command)executeDFXCommand);
/*     */       } 
/* 123 */     } catch (NoSuchDFXException e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void mapDFXs(Creature creature, AbstractPickUpDescription description) {
/* 130 */     creature.addMappedDfx("idle", description.getIdleDFX());
/* 131 */     creature.addMappedDfx("pickup-pickedup", description.getPickUpDFX());
/* 132 */     creature.addMappedDfx("pickup-spawn", description.getSpawnDFX());
/*     */   }
/*     */   
/*     */   private static class PickupAction extends AbstractAction {
/*     */     private static final String ACTION_NAME = "loot";
/*     */     private final int pickUpLootId;
/*     */     
/*     */     public PickupAction(int pickUpLootId) {
/* 140 */       this.pickUpLootId = pickUpLootId;
/*     */     }
/*     */ 
/*     */     
/*     */     public void perform(InteractibleProp invoker) {
/*     */       try {
/* 146 */         PickupLootMessage message = new PickupLootMessage(this.pickUpLootId);
/* 147 */         NetworkHandler.instance().getIOHandler().send((Message)message);
/* 148 */       } catch (InterruptedException e) {
/* 149 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 155 */       return "loot";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\PickUpLootLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */