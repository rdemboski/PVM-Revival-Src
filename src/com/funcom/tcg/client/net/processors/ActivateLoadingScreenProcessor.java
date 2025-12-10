/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.TcgUI;
/*    */ import com.funcom.tcg.client.ui.hud.LoadingWindow;
/*    */ import com.funcom.tcg.net.message.ActivateLoadingScreenMessage;
/*    */ import com.jmex.bui.BWindow;
/*    */ import com.jmex.bui.BuiSystem;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ActivateLoadingScreenProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 19 */     ActivateLoadingScreenMessage activateLoadingScreenMessage = (ActivateLoadingScreenMessage)message;
/* 20 */     LoadingWindow loadingWindow = MainGameState.getLoadingWindow();
/*    */ 
/*    */     
/* 23 */     if (TcgUI.isWindowOpen(LoadingWindow.class)) {
/* 24 */       BuiSystem.removeWindow((BWindow)loadingWindow);
/*    */     }
/* 26 */     loadingWindow.randomPet();
/* 27 */     loadingWindow.loadMap(activateLoadingScreenMessage.getMapId());
/* 28 */     BuiSystem.addWindow((BWindow)loadingWindow);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 33 */     return 49;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\ActivateLoadingScreenProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */