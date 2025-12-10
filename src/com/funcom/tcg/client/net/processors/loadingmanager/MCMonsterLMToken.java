/*    */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*    */ 
/*    */ import com.funcom.gameengine.model.input.MouseOver;
/*    */ import com.funcom.gameengine.model.input.UserActionHandler;
/*    */ import com.funcom.gameengine.model.props.Creature;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.rpgengine2.speach.SpeachMapping;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.actions.MonsterMouseOver;
/*    */ import com.funcom.tcg.client.controllers.BarkingController;
/*    */ import com.funcom.tcg.client.model.MonsterActionHandler;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.CreatureCreationMessage;
/*    */ import com.funcom.tcg.rpg.BaseFaction;
/*    */ import com.funcom.tcg.rpg.Faction;
/*    */ import com.jme.renderer.ColorRGBA;
/*    */ import com.jme.scene.Controller;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCMonsterLMToken
/*    */   extends AbstractModularCreatureLMToken
/*    */ {
/* 26 */   private static final ColorRGBA MONSTER_TINT_COLOR = new ColorRGBA(0.5F, 0.25F, 0.25F, 1.0F);
/*    */   
/*    */   public MCMonsterLMToken(CreatureCreationMessage creatureMessage) {
/* 29 */     super(creatureMessage);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean canBuild(Faction faction) throws Exception {
/* 34 */     return (faction.equivalentTo(BaseFaction.MONSTER) || faction.equivalentTo(BaseFaction.FREE_FOR_ALL) || (faction.equivalentTo(BaseFaction.PLAYER) && !faction.equivalentTo(MainGameState.getPlayerModel().getFaction())));
/*    */   }
/*    */   
/*    */   protected UserActionHandler createActionHandler(Creature monster, CreatureCreationMessage message, PropNode propNode) {
/* 38 */     SpeachMapping speachMapping = TcgGame.getRpgLoader().getSpeachManager().getSpeachDescritpionForNpc(monster.getMonsterId());
/* 39 */     if (speachMapping != null && 
/* 40 */       speachMapping.isBarks())
/*    */     {
/* 42 */       propNode.addController((Controller)new BarkingController(propNode, speachMapping));
/*    */     }
/*    */ 
/*    */     
/* 46 */     MonsterMouseOver monsterMouseOver = new MonsterMouseOver(MainGameState.getMouseCursorSetter(), MONSTER_TINT_COLOR);
/* 47 */     monsterMouseOver.setOwnerPropNode(propNode);
/* 48 */     return (UserActionHandler)new MonsterActionHandler(monster, MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)monsterMouseOver);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\MCMonsterLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */