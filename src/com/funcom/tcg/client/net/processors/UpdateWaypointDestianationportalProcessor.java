/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffect;
/*    */ import com.funcom.commons.dfx.DireEffectDescription;
/*    */ import com.funcom.commons.dfx.NoSuchDFXException;
/*    */ import com.funcom.gameengine.model.ResourceGetter;
/*    */ import com.funcom.gameengine.model.ResourceGetterImpl;
/*    */ import com.funcom.gameengine.model.action.Action;
/*    */ import com.funcom.gameengine.model.input.DefaultActionInteractActionHandler;
/*    */ import com.funcom.gameengine.model.input.MouseOver;
/*    */ import com.funcom.gameengine.model.input.UserActionHandler;
/*    */ import com.funcom.gameengine.model.props.Creature;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.rpgengine2.combat.UsageParams;
/*    */ import com.funcom.rpgengine2.waypoints.WaypointDestinationPortalDescription;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.actions.DestinationPortalAction;
/*    */ import com.funcom.tcg.client.actions.DestinationPortalMouseOver;
/*    */ import com.funcom.tcg.client.model.PropNodeRegister;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.net.NetworkHandler;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.UpdateWaypointDestianationportalMessage;
/*    */ import com.funcom.tcg.portals.InteractibleType;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class UpdateWaypointDestianationportalProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 35 */     UpdateWaypointDestianationportalMessage updateWaypointDestianationportalMessage = (UpdateWaypointDestianationportalMessage)message;
/*    */     
/* 37 */     PropNodeRegister propNodeRegister = TcgGame.getWaypointDestinationRegister();
/* 38 */     PropNode propNode = propNodeRegister.getPropNode(Integer.valueOf(updateWaypointDestianationportalMessage.getPropId()));
/* 39 */     if (propNode != null) {
/* 40 */       activateDestinationPortal(propNode, updateWaypointDestianationportalMessage);
/*    */     }
/*    */   }
/*    */   
/*    */   private void activateDestinationPortal(PropNode propNode, UpdateWaypointDestianationportalMessage updateWaypointDestianationportalMessage) {
/* 45 */     Creature creature = (Creature)propNode.getProp();
/* 46 */     DestinationPortalAction action = new DestinationPortalAction(updateWaypointDestianationportalMessage.getPortalId(), NetworkHandler.instance().getIOHandler(), InteractibleType.DESTINATION_PORTAL);
/* 47 */     creature.addAction((Action)action);
/*    */     
/* 49 */     WaypointDestinationPortalDescription waypointDestinationPortalDescription = TcgGame.getRpgLoader().getWaypointManager().getWaypointDestinationPortalDescription(updateWaypointDestianationportalMessage.getPortalId());
/*    */ 
/*    */     
/* 52 */     creature.addMappedDfx(action.getName(), waypointDestinationPortalDescription.getImpactDfx());
/*    */     
/* 54 */     DestinationPortalMouseOver destinationPortalMouseOver = new DestinationPortalMouseOver(MainGameState.getMouseCursorSetter(), (InteractibleProp)creature, MainGameState.getActionNameToCursorMapping());
/* 55 */     propNode.setActionHandler((UserActionHandler)new DefaultActionInteractActionHandler((InteractibleProp)creature, (Creature)MainGameState.getPlayerModel(), action.getName(), MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)destinationPortalMouseOver));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 61 */     destinationPortalMouseOver.setOwnerPropNode(propNode);
/*    */ 
/*    */     
/*    */     try {
/* 65 */       DireEffectDescription stateDFXDescription = propNode.getEffectDescriptionFactory().getDireEffectDescription(waypointDestinationPortalDescription.getActivationDfx(), true);
/* 66 */       DireEffect direEffect = stateDFXDescription.createInstance(propNode, UsageParams.EMPTY_PARAMS);
/* 67 */       propNode.addDfx(direEffect);
/* 68 */     } catch (NoSuchDFXException e) {}
/*    */ 
/*    */     
/* 71 */     propNode.initializeAllEffects((ResourceGetter)new ResourceGetterImpl(TcgGame.getResourceManager()), MainGameState.getWorld().getParticleSurface());
/*    */     
/* 73 */     propNode.updateRenderState();
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 78 */     return 48;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\UpdateWaypointDestianationportalProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */