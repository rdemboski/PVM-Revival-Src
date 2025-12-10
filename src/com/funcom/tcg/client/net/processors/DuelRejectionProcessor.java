/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.view.DfxTextWindowManager;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.duel.DuelAcceptWindow;
/*    */ import com.funcom.tcg.net.message.DuelRejectionMessage;
/*    */ import com.funcom.tcg.net.message.duels.DuelRejectionType;
/*    */ import com.jmex.bui.BWindow;
/*    */ import com.jmex.bui.BuiSystem;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DuelRejectionProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/*    */     BWindow window;
/* 25 */     DuelRejectionMessage duelRejectionMessage = (DuelRejectionMessage)message;
/* 26 */     DuelRejectionType rejectionType = DuelRejectionType.values()[duelRejectionMessage.getResponseType()];
/* 27 */     switch (rejectionType) {
/*    */       case ALREADY_DUELING:
/* 29 */         DfxTextWindowManager.instance().getWindow("main").showText(TcgGame.getLocalizedText("duel.rejected.dueling.you", new String[0]));
/*    */         break;
/*    */       case PLAYER_DUELING:
/* 32 */         DfxTextWindowManager.instance().getWindow("main").showText(TcgGame.getLocalizedText("duel.rejected.dueling.other", new String[0]));
/*    */         break;
/*    */       case NOT_ON_MAP:
/* 35 */         DfxTextWindowManager.instance().getWindow("main").showText(TcgGame.getLocalizedText("duel.rejected.map", new String[0]));
/*    */         break;
/*    */       case DECLINED:
/* 38 */         DfxTextWindowManager.instance().getWindow("main").showText(TcgGame.getLocalizedText("duel.rejected.declined", new String[0]));
/*    */         break;
/*    */       case BLOCKED:
/* 41 */         DfxTextWindowManager.instance().getWindow("main").showText(TcgGame.getLocalizedText("duel.rejected.declined", new String[0]));
/*    */         break;
/*    */       case OFFLINE:
/* 44 */         DfxTextWindowManager.instance().getWindow("main").showText(TcgGame.getLocalizedText("duel.rejected.offline", new String[0]));
/*    */         break;
/*    */       case DUEL_CANCELLED:
/* 47 */         window = BuiSystem.getWindow(DuelAcceptWindow.class.getSimpleName());
/* 48 */         if (window != null) {
/* 49 */           BuiSystem.removeWindow(window);
/*    */         }
/* 51 */         DfxTextWindowManager.instance().getWindow("main").showText(TcgGame.getLocalizedText("duel.request.cancelled", new String[0]));
/*    */         break;
/*    */       default:
/* 54 */         DfxTextWindowManager.instance().getWindow("main").showText(TcgGame.getLocalizedText("duel.rejected.unknown", new String[0]));
/*    */         break;
/*    */     } 
/* 57 */     if (!rejectionType.equals(DuelRejectionType.ALREADY_DUELING)) {
/* 58 */       MainGameState.getMainHud().getDuelCancelButton().setVisible(false);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 64 */     return 249;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\DuelRejectionProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */