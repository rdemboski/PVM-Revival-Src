/*    */ package com.funcom.tcg.client.net.processors;
/*    */ import com.funcom.gameengine.jme.modular.XmlModularDescription;
/*    */ import com.funcom.gameengine.model.action.Action;
/*    */ import com.funcom.gameengine.model.input.ActionDependentMouseOver;
/*    */ import com.funcom.gameengine.model.input.DefaultActionInteractActionHandler;
/*    */ import com.funcom.gameengine.model.input.UserActionHandler;
/*    */ import com.funcom.gameengine.model.props.Creature;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.view.AnimationMapper;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.gameengine.view.RepresentationalNode;
/*    */ import com.funcom.rpgengine2.monsters.MonsterHardness;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.TcgConstants;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.view.modular.ClientDescribedModularNode;
/*    */ import com.funcom.tcg.net.message.LootCreationMessage;
/*    */ import java.util.Map;
/*    */ import org.jdom.Document;
/*    */ 
/*    */ public class LootCreationProcessor implements MessageProcessor {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 29 */     LootCreationMessage lootCreationMessage = (LootCreationMessage)message;
/*    */     
/* 31 */     if (LoadingManager.USE) {
/* 32 */       LoadingManager.INSTANCE.submit((LoadingManagerToken)new LootCreationLMToken(lootCreationMessage));
/*    */     } else {
/*    */       
/* 35 */       InteractibleProp interactibleProp = new InteractibleProp(lootCreationMessage.getLootId(), lootCreationMessage.getLootOwnerName(), lootCreationMessage.getWorldCoordinate(), lootCreationMessage.getRadius());
/* 36 */       interactibleProp.addAction((Action)new LootWindowAction(lootCreationMessage.getContainerId(), interactibleProp));
/*    */       
/* 38 */       PropNode propNode = new PropNode((Prop)interactibleProp, 3, "", TcgGame.getDireEffectDescriptionFactory(), (UserActionHandler)new DefaultActionInteractActionHandler(interactibleProp, (Creature)MainGameState.getPlayerModel(), "loot", MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)new ActionDependentMouseOver(MainGameState.getMouseCursorSetter(), interactibleProp, MainGameState.getActionNameToCursorMapping())));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 45 */       createLootModel(propNode, MonsterHardness.values()[lootCreationMessage.getHardness()]);
/*    */       
/* 47 */       MainGameState.getWorld().addObject((RepresentationalNode)propNode);
/* 48 */       propNode.updateModelBound();
/* 49 */       propNode.updateRenderState();
/* 50 */       propNode.updateGeometricState(0.0F, true);
/*    */       
/* 52 */       TcgGame.getMonsterRegister().addPropNode(propNode);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void createLootModel(PropNode propNode, MonsterHardness hardness) {
/* 57 */     String lootModel = TcgGame.getRpgLoader().getLootManager().getLootModel(hardness.toString());
/*    */     
/* 59 */     Document document = (Document)TcgGame.getResourceManager().getResource(Document.class, lootModel);
/* 60 */     XmlModularDescription modularDescription = new XmlModularDescription(document);
/*    */     
/* 62 */     ClientDescribedModularNode clientDescribedModularNode = new ClientDescribedModularNode((ModularDescription)modularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager());
/*    */ 
/*    */     
/* 65 */     clientDescribedModularNode.reloadCharacter();
/* 66 */     propNode.attachRepresentation((Spatial)clientDescribedModularNode);
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 70 */     return 21;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\LootCreationProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */