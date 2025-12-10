/*     */ package com.funcom.tcg.client.net.processors;
/*     */ import com.funcom.commons.PerformanceGraphNode;
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.commons.dfx.NoSuchDFXException;
/*     */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*     */ import com.funcom.gameengine.jme.modular.XmlModularDescription;
/*     */ import com.funcom.gameengine.model.ResourceGetterImpl;
/*     */ import com.funcom.gameengine.model.action.Action;
/*     */ import com.funcom.gameengine.model.input.DefaultActionInteractActionHandler;
/*     */ import com.funcom.gameengine.model.input.UserActionHandler;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.view.AnimationMapper;
/*     */ import com.funcom.gameengine.view.ModularNode;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.waypoints.WaypointDestinationPortalDescription;
/*     */ import com.funcom.server.common.GameIOHandler;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.TcgConstants;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.actions.DestinationPortalAction;
/*     */ import com.funcom.tcg.client.actions.DestinationPortalMouseOver;
/*     */ import com.funcom.tcg.client.net.CreatureData;
/*     */ import com.funcom.tcg.client.net.MessageProcessor;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.view.modular.ClientDescribedModularNode;
/*     */ import com.funcom.tcg.net.message.ActivateWaypointDestianationportalMessage;
/*     */ import com.funcom.tcg.portals.InteractibleType;
/*     */ import com.jme.util.Debug;
/*     */ import com.jme.util.stat.StatCollector;
/*     */ import java.util.Map;
/*     */ import org.jdom.Document;
/*     */ 
/*     */ public class ActivateWaypointDestianationportalProcessor implements MessageProcessor {
/*     */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/*  43 */     ActivateWaypointDestianationportalMessage activateWaypointDestianationportalMessage = (ActivateWaypointDestianationportalMessage)message;
/*     */     
/*  45 */     if (LoadingManager.USE) {
/*  46 */       LoadingManager.INSTANCE.submit((LoadingManagerToken)new ActivateWaypointDestinationPortalLMToken(activateWaypointDestianationportalMessage));
/*     */     }
/*     */     else {
/*     */       
/*  50 */       startTiming();
/*     */       
/*  52 */       WaypointDestinationPortalDescription waypointDestinationPortalDescription = TcgGame.getRpgLoader().getWaypointManager().getWaypointDestinationPortalDescription(activateWaypointDestianationportalMessage.getPortalId());
/*     */ 
/*     */ 
/*     */       
/*  56 */       Creature creature = new Creature(activateWaypointDestianationportalMessage.getPropId(), activateWaypointDestianationportalMessage.getName(), activateWaypointDestianationportalMessage.getName(), activateWaypointDestianationportalMessage.getWorldCoordinate(), waypointDestinationPortalDescription.getRadius());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  63 */       if (activateWaypointDestianationportalMessage.isActivated()) {
/*  64 */         addActivetedDestinationPortal(activateWaypointDestianationportalMessage, waypointDestinationPortalDescription, creature);
/*     */       } else {
/*  66 */         addInactiveDestinationPortal(activateWaypointDestianationportalMessage, waypointDestinationPortalDescription, creature);
/*     */       } 
/*  68 */       endTiming();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void startTiming() {
/*  74 */     if (Debug.stats)
/*  75 */       StatCollector.startStat(PerformanceGraphNode.TrackingStat.CREATURE_BUILDING.statType); 
/*     */   }
/*     */   
/*     */   private static void endTiming() {
/*  79 */     if (Debug.stats)
/*  80 */       StatCollector.endStat(PerformanceGraphNode.TrackingStat.CREATURE_BUILDING.statType); 
/*     */   }
/*     */   
/*     */   private void addInactiveDestinationPortal(ActivateWaypointDestianationportalMessage activateWaypointDestianationportalMessage, WaypointDestinationPortalDescription waypointDestinationPortalDescription, Creature creature) {
/*  84 */     PropNode propNode = new PropNode((Prop)creature, 3, creature.getName(), TcgGame.getDireEffectDescriptionFactory(), null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  92 */       String dfxScript = waypointDestinationPortalDescription.getIdleDFX();
/*  93 */       if (dfxScript.length() > 1) {
/*  94 */         DireEffectDescription stateDFXDescription = propNode.getEffectDescriptionFactory().getDireEffectDescription(dfxScript, true);
/*  95 */         DireEffect direEffect = stateDFXDescription.createInstance(propNode, UsageParams.EMPTY_PARAMS);
/*  96 */         propNode.addDfx(direEffect);
/*     */       } 
/*  98 */     } catch (NoSuchDFXException e) {}
/*     */ 
/*     */ 
/*     */     
/* 102 */     Document document = (Document)TcgGame.getResourceManager().getResource(Document.class, waypointDestinationPortalDescription.getMeshResource());
/* 103 */     XmlModularDescription xmlModularDescription = new XmlModularDescription(document);
/* 104 */     ClientDescribedModularNode clientDescribedModularNode = new ClientDescribedModularNode((ModularDescription)xmlModularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager());
/*     */     
/* 106 */     attachAndReloadprop(propNode, (ModularNode)clientDescribedModularNode);
/*     */   }
/*     */ 
/*     */   
/*     */   private void addActivetedDestinationPortal(ActivateWaypointDestianationportalMessage activateWaypointDestianationportalMessage, WaypointDestinationPortalDescription waypointDestinationPortalDescription, Creature creature) {
/* 111 */     DestinationPortalAction action = new DestinationPortalAction(activateWaypointDestianationportalMessage.getPortalId(), NetworkHandler.instance().getIOHandler(), InteractibleType.DESTINATION_PORTAL);
/* 112 */     creature.addAction((Action)action);
/*     */     
/* 114 */     creature.addMappedDfx(action.getName(), waypointDestinationPortalDescription.getImpactDfx());
/*     */     
/* 116 */     DestinationPortalMouseOver destinationPortalMouseOver = new DestinationPortalMouseOver(MainGameState.getMouseCursorSetter(), (InteractibleProp)creature, MainGameState.getActionNameToCursorMapping());
/*     */ 
/*     */     
/* 119 */     PropNode propNode = new PropNode((Prop)creature, 3, creature.getName(), TcgGame.getDireEffectDescriptionFactory(), (UserActionHandler)new DefaultActionInteractActionHandler((InteractibleProp)creature, (Creature)MainGameState.getPlayerModel(), action.getName(), MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)destinationPortalMouseOver));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     destinationPortalMouseOver.setOwnerPropNode(propNode);
/*     */ 
/*     */     
/*     */     try {
/* 134 */       DireEffectDescription stateDFXDescription = propNode.getEffectDescriptionFactory().getDireEffectDescription(waypointDestinationPortalDescription.getActivationDfx(), true);
/* 135 */       DireEffect direEffect = stateDFXDescription.createInstance(propNode, UsageParams.EMPTY_PARAMS);
/* 136 */       propNode.addDfx(direEffect);
/*     */     }
/* 138 */     catch (NoSuchDFXException e) {}
/*     */ 
/*     */     
/* 141 */     Document document = (Document)TcgGame.getResourceManager().getResource(Document.class, waypointDestinationPortalDescription.getMeshResource());
/* 142 */     XmlModularDescription xmlModularDescription = new XmlModularDescription(document);
/* 143 */     ClientDescribedModularNode clientDescribedModularNode = new ClientDescribedModularNode((ModularDescription)xmlModularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager());
/*     */     
/* 145 */     attachAndReloadprop(propNode, (ModularNode)clientDescribedModularNode);
/*     */   }
/*     */   
/*     */   private void attachAndReloadprop(PropNode propNode, ModularNode modularNode) {
/* 149 */     modularNode.reloadCharacter();
/* 150 */     propNode.attachRepresentation((Spatial)modularNode);
/* 151 */     propNode.initializeAllEffects((ResourceGetter)new ResourceGetterImpl(TcgGame.getResourceManager()), MainGameState.getWorld().getParticleSurface());
/*     */     
/* 153 */     MainGameState.getWorld().addObject((RepresentationalNode)propNode);
/* 154 */     TcgGame.getWaypointDestinationRegister().addPropNode(propNode);
/* 155 */     propNode.updateRenderState();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public short getMessageType() {
/* 161 */     return 46;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\ActivateWaypointDestianationportalProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */