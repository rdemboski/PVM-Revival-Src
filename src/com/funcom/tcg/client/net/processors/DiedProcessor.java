/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.PrioritizedLoadingTokenQueue;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.net.processors.loadingmanager.DiedLMToken;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.hud.RespawnWindow;
/*    */ import com.funcom.tcg.net.message.DiedMessage;
/*    */ import com.jmex.bui.BWindow;
/*    */ import com.jmex.bui.BuiSystem;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class DiedProcessor
/*    */   extends AbstractDeathHandler
/*    */   implements MessageProcessor {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 25 */     boolean isRespawnShowing = false;
/* 26 */     if (MainGameState.getRespawnWindow() != null) {
/*    */       
/* 28 */       int windows = BuiSystem.getRootNode().getWindowCount();
/* 29 */       for (int i = 0; i < windows; i++) {
/* 30 */         if (BuiSystem.getRootNode().getWindow(i) == MainGameState.getRespawnWindow()) {
/*    */           
/* 32 */           isRespawnShowing = true;
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/*    */     } 
/* 38 */     if (!isRespawnShowing) {
/* 39 */       DiedMessage diedMessage = (DiedMessage)message;
/* 40 */       LocalClientPlayer localClientPlayer = MainGameState.getPlayerModel();
/* 41 */       localClientPlayer.die();
/*    */       
/* 43 */       if (LoadingManager.USE) {
/* 44 */         LoadingManager.INSTANCE.submitByPriority((LoadingManagerToken)new DiedLMToken(diedMessage, MainGameState.getPlayerNode(), diedMessage.getKilledByElement(), diedMessage.getImpact(), TcgGame.getPropNodeRegister()), PrioritizedLoadingTokenQueue.TokenPriority.PRIORITY_CREATURES);
/*    */       }
/*    */       else {
/*    */         
/* 48 */         PropNode corpse = createCorpse(MainGameState.getPlayerNode(), diedMessage.getKilledByElement(), diedMessage.getImpact());
/* 49 */         TcgGame.getPropNodeRegister().removePropNode(MainGameState.getPlayerNode());
/* 50 */         TcgGame.getPropNodeRegister().addPropNode(corpse);
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/* 55 */       RespawnWindow respawnWindow = MainGameState.getRespawnWindow();
/* 56 */       respawnWindow.refresh();
/* 57 */       BuiSystem.addWindow((BWindow)respawnWindow);
/* 58 */       MainGameState.getTips().put("tutorial.description.defeated");
/*    */     } 
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 63 */     return 207;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\DiedProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */