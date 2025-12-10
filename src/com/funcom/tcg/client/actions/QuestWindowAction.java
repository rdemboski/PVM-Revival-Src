/*    */ package com.funcom.tcg.client.actions;
/*    */ 
/*    */ import com.funcom.gameengine.model.action.AbstractAction;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import com.funcom.gameengine.resourcemanager.CacheType;
/*    */ import com.funcom.peeler.BananaPeel;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.net.NetworkHandler;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*    */ import com.funcom.tcg.client.ui.hud.QuestFinishModel;
/*    */ import com.funcom.tcg.client.ui.hud.QuestFinishWindow2;
/*    */ import com.funcom.tcg.net.message.RequestQuestMessage;
/*    */ import com.jmex.bui.BWindow;
/*    */ 
/*    */ 
/*    */ public class QuestWindowAction
/*    */   extends AbstractAction
/*    */ {
/*    */   public static final String ACTION_NAME = "quest-window";
/*    */   private short questCompletion;
/*    */   private String questId;
/*    */   private int questGiverId;
/*    */   private String questHandIn;
/*    */   
/*    */   public QuestWindowAction(short questCompletion, String questId, int questGiverId, String questHandIn) {
/* 28 */     this.questCompletion = questCompletion;
/*    */     
/* 30 */     this.questId = questId;
/* 31 */     this.questGiverId = questGiverId;
/* 32 */     this.questHandIn = questHandIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 37 */     return "quest-window";
/*    */   }
/*    */   
/*    */   public void perform(InteractibleProp invoker) {
/* 41 */     if (this.questCompletion == 0) {
/* 42 */       RequestQuestMessage requestQuestMessage = new RequestQuestMessage(this.questId);
/*    */       try {
/* 44 */         NetworkHandler.instance().getIOHandler().send((Message)requestQuestMessage);
/* 45 */       } catch (InterruptedException e) {
/* 46 */         e.printStackTrace();
/*    */       } 
/* 48 */     } else if (this.questCompletion == 2) {
/*    */ 
/*    */       
/* 51 */       BananaPeel bananaPeel = (BananaPeel)TcgGame.getResourceManager().getResource(BananaPeel.class, "gui/peeler/window_quest_finish.xml", CacheType.NOT_CACHED);
/* 52 */       QuestFinishWindow2 questFinishWindow2 = new QuestFinishWindow2("finish quest window", bananaPeel, TcgGame.getResourceManager(), new QuestFinishModel(MainGameState.getQuestModel(), this.questGiverId, this.questHandIn), MainGameState.getToolTipManager(), getParent().getPosition().clone());
/*    */ 
/*    */       
/* 55 */       PanelManager.getInstance().addWindow((BWindow)questFinishWindow2);
/* 56 */       questFinishWindow2.refresh();
/*    */     } 
/*    */   }
/*    */   
/*    */   public short getQuestCompletion() {
/* 61 */     return this.questCompletion;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\actions\QuestWindowAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */