/*     */ package com.funcom.tcg.client.net.processors;
/*     */ import com.funcom.commons.PerformanceGraphNode;
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.commons.dfx.NoSuchDFXException;
/*     */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*     */ import com.funcom.gameengine.jme.modular.XmlModularDescription;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.ResourceGetterImpl;
/*     */ import com.funcom.gameengine.model.action.Action;
/*     */ import com.funcom.gameengine.model.input.DefaultActionInteractActionHandler;
/*     */ import com.funcom.gameengine.model.input.MouseOver;
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
/*     */ import com.funcom.server.common.GameIOHandler;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.TcgConstants;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.actions.TownPortalMouseOver;
/*     */ import com.funcom.tcg.client.actions.ZoneAction;
/*     */ import com.funcom.tcg.client.net.CreatureData;
/*     */ import com.funcom.tcg.client.net.MessageProcessor;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.net.processors.loadingmanager.ActivatePortalProcessorLMToken;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.view.modular.ClientDescribedModularNode;
/*     */ import com.funcom.tcg.net.message.ActivatePortalMessage;
/*     */ import com.funcom.tcg.portals.InteractibleType;
/*     */ import com.funcom.tcg.portals.TownPortalPropertyReader;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.util.Debug;
/*     */ import com.jme.util.stat.StatCollector;
/*     */ import java.util.Map;
/*     */ import org.jdom.Document;
import com.funcom.gameengine.jme.modular.XmlModularDescription;
/*     */ 
/*     */ public class ActivatePortalProcessor implements MessageProcessor {
/*     */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/*  47 */     ActivatePortalMessage activatePortalMessage = (ActivatePortalMessage)message;
/*     */     
/*  49 */     if (LoadingManager.USE) {
/*  50 */       LoadingManager.INSTANCE.submit((LoadingManagerToken)new ActivatePortalProcessorLMToken(activatePortalMessage));
/*     */     }
/*     */     else {
/*     */       
/*  54 */       startTiming();
/*     */       
/*  56 */       Creature creature = new Creature(activatePortalMessage.getPortalId(), activatePortalMessage.getPortalName(), activatePortalMessage.getPortalName(), activatePortalMessage.getDestinationCoords(), activatePortalMessage.getRadius());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  64 */       PropNode propNode = createPropNode(activatePortalMessage, creature);
/*     */       try {
/*  66 */         addDfx(propNode, activatePortalMessage);
/*  67 */       } catch (NoSuchDFXException e) {
/*  68 */         e.printStackTrace();
/*     */       } 
/*     */       
/*  71 */       ModularNode modularNode = getModularNode(activatePortalMessage, propNode);
/*  72 */       modularNode.reloadCharacter();
/*  73 */       propNode.attachRepresentation((Spatial)modularNode);
/*  74 */       propNode.initializeAllEffects((ResourceGetter)new ResourceGetterImpl(TcgGame.getResourceManager()), MainGameState.getWorld().getParticleSurface());
/*     */ 
/*     */       
/*  77 */       MainGameState.getWorld().addObject((RepresentationalNode)propNode);
/*  78 */       switch (activatePortalMessage.getPortalType()) {
/*     */         case CUSTOM_PORTAL:
/*  80 */           TcgGame.getCustomPortalRegister().addPropNode(propNode);
/*     */           break;
/*     */         case RETURN_POINT:
/*  83 */           TcgGame.getReturnPointRegister().addPropNode(propNode);
/*     */           break;
/*     */         case TOWN_PORTAL:
/*  86 */           TcgGame.getTownPortalRegister().addPropNode(propNode);
/*     */           break;
/*     */       } 
/*  89 */       propNode.updateRenderState();
/*  90 */       endTiming();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void startTiming() {
/*  96 */     if (Debug.stats)
/*  97 */       StatCollector.startStat(PerformanceGraphNode.TrackingStat.CREATURE_BUILDING.statType); 
/*     */   }
/*     */   
/*     */   private static void endTiming() {
/* 101 */     if (Debug.stats)
/* 102 */       StatCollector.endStat(PerformanceGraphNode.TrackingStat.CREATURE_BUILDING.statType); 
/*     */   }
/*     */   
/*     */   private PropNode createPropNode(ActivatePortalMessage activatePortalMessage, Creature prop) {
/*     */     PropNode propNode;
/* 107 */     if (activatePortalMessage.getPlayerId() == MainGameState.getPlayerModel().getId()) {
/*     */       
/* 109 */       ZoneAction zoneAction = addAction((InteractibleProp)prop, activatePortalMessage);
/*     */       
/* 111 */       TownPortalMouseOver mouseOver = new TownPortalMouseOver(MainGameState.getMouseCursorSetter(), prop, MainGameState.getActionNameToCursorMapping());
/* 112 */       propNode = new PropNode((Prop)prop, 3, prop.getName(), TcgGame.getDireEffectDescriptionFactory(), (UserActionHandler)new DefaultActionInteractActionHandler((InteractibleProp)prop, (Creature)MainGameState.getPlayerModel(), zoneAction.getName(), MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)mouseOver));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 122 */       mouseOver.setOwnerPropNode(propNode);
/*     */     } else {
/* 124 */       propNode = new PropNode((Prop)prop, 3, prop.getName(), TcgGame.getDireEffectDescriptionFactory());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 129 */     return propNode;
/*     */   }
/*     */   
/*     */   private ZoneAction addAction(InteractibleProp prop, ActivatePortalMessage activatePortalMessage) {
/* 133 */     ZoneAction action = new ZoneAction(prop.getName(), NetworkHandler.instance().getIOHandler(), activatePortalMessage.getPortalType());
/* 134 */     prop.addAction((Action)action);
/* 135 */     return action;
/*     */   }
/*     */   
/*     */   private void addDfx(PropNode propNode, ActivatePortalMessage activatePortalMessage) throws NoSuchDFXException {
/*     */     DireEffectDescription description;
/* 140 */     TownPortalPropertyReader portalPropertyReader = MainGameState.getTownPortalPropertyReader();
/* 141 */     if (activatePortalMessage.getPlayerId() == MainGameState.getPlayerModel().getId()) {
/*     */       
/* 143 */       switch (activatePortalMessage.getPortalType()) {
/*     */         case RETURN_POINT:
/* 145 */           description = TcgGame.getDireEffectDescriptionFactory().getDireEffectDescription(portalPropertyReader.getProperty("return_point_dfx"), true);
/*     */           break;
/*     */         case TOWN_PORTAL:
/* 148 */           description = TcgGame.getDireEffectDescriptionFactory().getDireEffectDescription(portalPropertyReader.getProperty("town_portal_dfx"), true);
/*     */           break;
/*     */         default:
/* 151 */           description = TcgGame.getDireEffectDescriptionFactory().getDireEffectDescription(portalPropertyReader.getProperty("town_portal_dfx"), true);
/*     */           break;
/*     */       } 
/*     */     
/*     */     } else {
/* 156 */       description = TcgGame.getDireEffectDescriptionFactory().getDireEffectDescription(portalPropertyReader.getProperty("town_portal_other_dfx"), true);
/*     */     } 
/* 158 */     DireEffect effect = description.createInstance(propNode, UsageParams.EMPTY_PARAMS);
/* 159 */     propNode.addDfx(effect);
/*     */   }
/*     */   
/*     */   private ModularNode getModularNode(ActivatePortalMessage activatePortalMessage, PropNode propNode) {
/* 163 */     TownPortalPropertyReader portalPropertyReader = MainGameState.getTownPortalPropertyReader();
/*     */     
/* 165 */     switch (activatePortalMessage.getPortalType())
/*     */     { case CUSTOM_PORTAL:
/* 167 */         Document document = (Document)TcgGame.getResourceManager().getResource(Document.class, portalPropertyReader.getProperty("town_portal_xml"));
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
/* 180 */         XmlModularDescription xmlModularDescription = new XmlModularDescription(document);
/* 181 */         return (ModularNode)new ClientDescribedModularNode((ModularDescription)xmlModularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager());case RETURN_POINT: document = (Document)TcgGame.getResourceManager().getResource(Document.class, portalPropertyReader.getProperty("return_point_xml")); xmlModularDescription = new XmlModularDescription(document); return (ModularNode)new ClientDescribedModularNode((ModularDescription)xmlModularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager());case TOWN_PORTAL: document = (Document)TcgGame.getResourceManager().getResource(Document.class, portalPropertyReader.getProperty("town_portal_xml")); xmlModularDescription = new XmlModularDescription(document); return (ModularNode)new ClientDescribedModularNode((ModularDescription)xmlModularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager()); }  Document document = (Document)TcgGame.getResourceManager().getResource(Document.class, portalPropertyReader.getProperty("town_portal_other_xml")); XmlModularDescription xmlModularDescription = new XmlModularDescription(document); return (ModularNode)new ClientDescribedModularNode((ModularDescription)xmlModularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager());
/*     */   }
/*     */ 
/*     */   
/*     */   public short getMessageType() {
/* 186 */     return 37;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\ActivatePortalProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */