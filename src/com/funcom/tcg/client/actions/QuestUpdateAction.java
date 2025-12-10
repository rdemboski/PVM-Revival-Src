/*    */ package com.funcom.tcg.client.actions;
/*    */ 
/*    */ import com.funcom.gameengine.model.action.AbstractAction;
/*    */ import com.funcom.gameengine.model.props.Creature;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.InteractiblePropActionInvokedMessage;
/*    */ import com.funcom.tcg.portals.InteractibleType;
/*    */ 
/*    */ public class QuestUpdateAction
/*    */   extends AbstractAction {
/*    */   private GameIOHandler gameIOHandler;
/*    */   
/*    */   public QuestUpdateAction(GameIOHandler gameIOHandler, String interactiblePropId, Creature monster) {
/* 17 */     this.gameIOHandler = gameIOHandler;
/* 18 */     this.interactiblePropId = interactiblePropId;
/* 19 */     this.monster = monster;
/*    */   }
/*    */   private String interactiblePropId; private Creature monster;
/*    */   
/*    */   public void perform(InteractibleProp invoker) {
/*    */     try {
/* 25 */       this.gameIOHandler.send((Message)new InteractiblePropActionInvokedMessage(MainGameState.getPlayerModel().getId(), this.interactiblePropId, InteractibleType.QUEST_ITEM));
/* 26 */     } catch (InterruptedException e) {
/* 27 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\actions\QuestUpdateAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */