/*    */ package com.funcom.tcg.client.net.creaturebuilders;
/*    */ import com.funcom.gameengine.model.action.Action;
/*    */ import com.funcom.gameengine.model.input.DefaultActionInteractActionHandler;
/*    */ import com.funcom.gameengine.model.input.MouseOver;
/*    */ import com.funcom.gameengine.model.input.UserActionHandler;
/*    */ import com.funcom.gameengine.model.props.Creature;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.rpgengine2.monsters.MonsterManager;
/*    */ import com.funcom.tcg.client.actions.QuestUpdateAction;
/*    */ import com.funcom.tcg.client.net.NetworkHandler;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.rpg.BaseFaction;
/*    */ import com.funcom.tcg.rpg.Faction;
/*    */ import com.jme.renderer.ColorRGBA;
/*    */ 
/*    */ public class QuestInteractibleBuilder extends AbstractModularCreatureBuilder {
/* 18 */   public static final ColorRGBA QUESTITEM_TINT_COLOR = (new ColorRGBA(ColorRGBA.cyan)).multLocal(0.25F);
/*    */   
/*    */   public QuestInteractibleBuilder(MonsterManager monsterManager) {
/* 21 */     super(monsterManager);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected UserActionHandler createActionHandler(Creature monster, CreatureCreationMessage message, PropNode propNode) {
/* 27 */     QuestUpdateAction action = new QuestUpdateAction(NetworkHandler.instance().getIOHandler(), "" + monster.getId(), monster);
/* 28 */     monster.addAction((Action)action);
/*    */     
/* 30 */     QuestInteractibleMouseOver questInteractibleMouseOver = new QuestInteractibleMouseOver(QUESTITEM_TINT_COLOR, Effects.TintMode.ADDITIVE, MainGameState.getMouseCursorSetter());
/* 31 */     questInteractibleMouseOver.setOwnerPropNode(propNode);
/*    */     
/* 33 */     return (UserActionHandler)new DefaultActionInteractActionHandler((InteractibleProp)monster, (Creature)MainGameState.getPlayerNode().getProp(), action.getName(), MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)questInteractibleMouseOver);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isBuildable(Faction faction) {
/* 39 */     return faction.equivalentTo(BaseFaction.INTERACT_KILL);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\creaturebuilders\QuestInteractibleBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */