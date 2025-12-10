/*     */ package com.funcom.tcg.client.net.processors;
/*     */ import com.funcom.commons.PerformanceGraphNode;
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.commons.dfx.NoSuchDFXException;
/*     */ import com.funcom.gameengine.jme.modular.XmlModularDescription;
/*     */ import com.funcom.gameengine.model.ResourceGetterImpl;
/*     */ import com.funcom.gameengine.model.action.Action;
/*     */ import com.funcom.gameengine.model.input.ActionDependentMouseOver;
/*     */ import com.funcom.gameengine.model.input.DefaultActionInteractActionHandler;
/*     */ import com.funcom.gameengine.model.input.MouseOver;
/*     */ import com.funcom.gameengine.model.input.UserActionHandler;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.view.AnimationMapper;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import com.funcom.rpgengine2.waypoints.WaypointDescription;
/*     */ import com.funcom.server.common.GameIOHandler;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.TcgConstants;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.actions.WaypointWindowAction;
/*     */ import com.funcom.tcg.client.net.CreatureData;
/*     */ import com.funcom.tcg.client.net.MessageProcessor;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.view.modular.ClientDescribedModularNode;
/*     */ import com.funcom.tcg.net.message.ActivateWaypointMessage;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.util.Debug;
/*     */ import com.jme.util.stat.StatCollector;
/*     */ import java.util.Map;
/*     */ import org.jdom.Document;
/*     */ 
/*     */ public class ActivateWaypointProcessor implements MessageProcessor {
/*     */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/*  39 */     if (LoadingManager.USE) {
/*  40 */       ActivateWaypointMessage activateWaypointMessage = (ActivateWaypointMessage)message;
/*  41 */       LoadingManager.INSTANCE.submit((LoadingManagerToken)new ActivateWaypointLMToken(activateWaypointMessage));
/*     */     } else {
/*     */       
/*  44 */       startTiming();
/*  45 */       ActivateWaypointMessage activateWaypointMessage = (ActivateWaypointMessage)message;
/*     */       
/*  47 */       String[] portalsId = activateWaypointMessage.getDestinationPortalsId();
/*  48 */       boolean[] portalsLockingStatus = activateWaypointMessage.getDestianPortalsLockingStatus();
/*     */       
/*  50 */       WaypointDescription waypointDescription = TcgGame.getRpgLoader().getWaypointManager().getWaypoint(activateWaypointMessage.getWaypointID());
/*     */       
/*  52 */       Creature creature = new Creature(activateWaypointMessage.getPropId(), waypointDescription.getId(), waypointDescription.getId(), waypointDescription.getWorldCoordinate(), waypointDescription.getRadius());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  61 */       WaypointWindowAction waypointWindowAction = new WaypointWindowAction(waypointDescription, portalsId, portalsLockingStatus);
/*  62 */       creature.addAction((Action)waypointWindowAction);
/*     */       
/*  64 */       String dfxResource = waypointDescription.getDfxImpactResource();
/*  65 */       if (dfxResource != null && dfxResource.length() > 1) {
/*  66 */         creature.addMappedDfx(waypointWindowAction.getName(), dfxResource);
/*     */       }
/*     */       
/*  69 */       PropNode propNode = new PropNode((Prop)creature, 3, creature.getName(), TcgGame.getDireEffectDescriptionFactory(), (UserActionHandler)new DefaultActionInteractActionHandler((InteractibleProp)creature, (Creature)MainGameState.getPlayerModel(), waypointWindowAction.getName(), MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)new ActionDependentMouseOver(MainGameState.getMouseCursorSetter(), (InteractibleProp)creature, MainGameState.getActionNameToCursorMapping())));
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
/*     */ 
/*     */       
/*     */       try {
/*  83 */         DireEffectDescription dfxDescription = propNode.getEffectDescriptionFactory().getDireEffectDescription(waypointDescription.getDfxIdleResource(), true);
/*  84 */         DireEffect direEffect = dfxDescription.createInstance(propNode, UsageParams.EMPTY_PARAMS);
/*  85 */         propNode.addDfx(direEffect);
/*  86 */       } catch (NoSuchDFXException e) {
/*  87 */         e.printStackTrace();
/*     */       } 
/*     */ 
/*     */       
/*  91 */       Document document = (Document)TcgGame.getResourceManager().getResource(Document.class, waypointDescription.getMeshResource());
/*  92 */       XmlModularDescription xmlModularDescription = new XmlModularDescription(document);
/*  93 */       ClientDescribedModularNode clientDescribedModularNode = new ClientDescribedModularNode((ModularDescription)xmlModularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager());
/*     */       
/*  95 */       clientDescribedModularNode.reloadCharacter();
/*  96 */       propNode.attachRepresentation((Spatial)clientDescribedModularNode);
/*  97 */       propNode.initializeAllEffects((ResourceGetter)new ResourceGetterImpl(TcgGame.getResourceManager()), MainGameState.getWorld().getParticleSurface());
/*     */       
/*  99 */       MainGameState.getWorld().addObject((RepresentationalNode)propNode);
/* 100 */       TcgGame.getWaypointRegister().addPropNode(propNode);
/* 101 */       propNode.updateRenderState();
/*     */       
/* 103 */       endTiming();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void startTiming() {
/* 109 */     if (Debug.stats)
/* 110 */       StatCollector.startStat(PerformanceGraphNode.TrackingStat.CREATURE_BUILDING.statType); 
/*     */   }
/*     */   
/*     */   private static void endTiming() {
/* 114 */     if (Debug.stats) {
/* 115 */       StatCollector.endStat(PerformanceGraphNode.TrackingStat.CREATURE_BUILDING.statType);
/*     */     }
/*     */   }
/*     */   
/*     */   public short getMessageType() {
/* 120 */     return 47;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\ActivateWaypointProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */