/*    */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.CacheType;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.peeler.BananaPeel;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.rpg.ClientQuestData;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*    */ import com.funcom.tcg.client.ui.hud.QuestPickUpWindow2;
/*    */ import com.funcom.tcg.client.ui.hud.QuestPickupModel;
/*    */ import com.funcom.tcg.client.ui.hud2.TCGQuestPickupModel;
/*    */ import com.jmex.bui.BWindow;
/*    */ import java.util.concurrent.Callable;
/*    */ import java.util.concurrent.Future;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConfirmDialogLMToken
/*    */   extends LoadingManagerToken
/*    */ {
/* 26 */   ClientQuestData clientQuestData = null;
/* 27 */   Future BananaPeelFuture = null;
/*    */   
/*    */   public ConfirmDialogLMToken(ClientQuestData clientQuestData) {
/* 30 */     this.clientQuestData = clientQuestData;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean processRequestAssets() throws Exception {
/* 35 */     Callable<BananaPeel> callable = new BananaPeelCallable();
/* 36 */     this.BananaPeelFuture = (Future)LoadingManager.INSTANCE.submitCallable(callable);
/*    */     
/* 38 */     return true;
/*    */   }
/*    */   
/*    */   public boolean processWaitingAssets() throws Exception {
/* 42 */     return (this.BananaPeelFuture == null || this.BananaPeelFuture.isDone());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean processGame() throws Exception {
/* 47 */     BananaPeel bananaPeel = null;
/* 48 */     if (this.BananaPeelFuture != null && !this.BananaPeelFuture.isCancelled()) {
/* 49 */       bananaPeel = (BananaPeel)this.BananaPeelFuture.get();
/*    */     } else {
/*    */       
/* 52 */       bananaPeel = (BananaPeel)TcgGame.getResourceManager().getResource(BananaPeel.class, "gui/peeler/window_quest_accept.xml", CacheType.NOT_CACHED);
/*    */     } 
/*    */     
/* 55 */     this.BananaPeelFuture = null;
/*    */     
/* 57 */     if (bananaPeel != null) {
/* 58 */       QuestPickUpWindow2 pickUpWindow2 = new QuestPickUpWindow2("accept window", bananaPeel, TcgGame.getResourceManager(), (QuestPickupModel)new TCGQuestPickupModel(MainGameState.getQuestModel(), this.clientQuestData), MainGameState.getToolTipManager(), MainGameState.getPlayerNode().getPosition().clone());
/*    */ 
/*    */ 
/*    */       
/* 62 */       PanelManager.getInstance().addWindow((BWindow)pickUpWindow2);
/* 63 */       pickUpWindow2.refresh(true);
/*    */     } 
/*    */     
/* 66 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public class BananaPeelCallable
/*    */     implements Callable<BananaPeel>
/*    */   {
/*    */     public BananaPeel call() {
/* 76 */       return (BananaPeel)TcgGame.getResourceManager().getResource(BananaPeel.class, "gui/peeler/window_quest_accept.xml", CacheType.NOT_CACHED);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\ConfirmDialogLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */