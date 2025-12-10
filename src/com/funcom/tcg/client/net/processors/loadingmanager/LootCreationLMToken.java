/*    */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*    */ 
/*    */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*    */ import com.funcom.gameengine.jme.modular.XmlModularDescription;
/*    */ import com.funcom.gameengine.model.action.Action;
/*    */ import com.funcom.gameengine.model.input.ActionDependentMouseOver;
/*    */ import com.funcom.gameengine.model.input.DefaultActionInteractActionHandler;
/*    */ import com.funcom.gameengine.model.input.MouseOver;
/*    */ import com.funcom.gameengine.model.input.UserActionHandler;
/*    */ import com.funcom.gameengine.model.props.Creature;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import com.funcom.gameengine.model.props.Prop;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.view.AnimationMapper;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.gameengine.view.RepresentationalNode;
/*    */ import com.funcom.rpgengine2.monsters.MonsterHardness;
/*    */ import com.funcom.tcg.TcgConstants;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.actions.LootWindowAction;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.view.modular.ClientDescribedModularNode;
/*    */ import com.funcom.tcg.net.message.LootCreationMessage;
/*    */ import com.jme.scene.Spatial;
/*    */ import org.jdom.Document;
/*    */ 
/*    */ public class LootCreationLMToken extends LoadingManagerToken {
/* 28 */   private LootCreationMessage lootCreationMessage = null;
/*    */   
/*    */   public LootCreationLMToken(LootCreationMessage lootCreationMessage) {
/* 31 */     this.lootCreationMessage = lootCreationMessage;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int update() {
/* 47 */     InteractibleProp interactibleProp = new InteractibleProp(this.lootCreationMessage.getLootId(), this.lootCreationMessage.getLootOwnerName(), this.lootCreationMessage.getWorldCoordinate(), this.lootCreationMessage.getRadius());
/* 48 */     interactibleProp.addAction((Action)new LootWindowAction(this.lootCreationMessage.getContainerId(), interactibleProp));
/*    */     
/* 50 */     PropNode propNode = new PropNode((Prop)interactibleProp, 3, "", TcgGame.getDireEffectDescriptionFactory(), (UserActionHandler)new DefaultActionInteractActionHandler(interactibleProp, (Creature)MainGameState.getPlayerModel(), "loot", MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)new ActionDependentMouseOver(MainGameState.getMouseCursorSetter(), interactibleProp, MainGameState.getActionNameToCursorMapping())));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 57 */     createLootModel(propNode, MonsterHardness.values()[this.lootCreationMessage.getHardness()]);
/*    */     
/* 59 */     MainGameState.getWorld().addObject((RepresentationalNode)propNode);
/* 60 */     propNode.updateModelBound();
/* 61 */     propNode.updateRenderState();
/* 62 */     propNode.updateGeometricState(0.0F, true);
/*    */     
/* 64 */     TcgGame.getMonsterRegister().addPropNode(propNode);
/*    */     
/* 66 */     return 3;
/*    */   }
/*    */   
/*    */   private void createLootModel(PropNode propNode, MonsterHardness hardness) {
/* 70 */     String lootModel = TcgGame.getRpgLoader().getLootManager().getLootModel(hardness.toString());
/*    */     
/* 72 */     Document document = (Document)TcgGame.getResourceManager().getResource(Document.class, lootModel);
/* 73 */     XmlModularDescription modularDescription = new XmlModularDescription(document);
/*    */     
/* 75 */     ClientDescribedModularNode clientDescribedModularNode = new ClientDescribedModularNode((ModularDescription)modularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager());
/*    */ 
/*    */     
/* 78 */     clientDescribedModularNode.reloadCharacter();
/* 79 */     propNode.attachRepresentation((Spatial)clientDescribedModularNode);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\LootCreationLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */