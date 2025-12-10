/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.view.DfxTextWindowManager;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.errorreporting.ErrorWindowCreator;
/*    */ import com.funcom.tcg.net.message.DailyQuestNumberUpdateMessage;
/*    */ import java.util.Map;
/*    */ import java.util.MissingResourceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DailyQuestUpdatedNumberProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 23 */     DailyQuestNumberUpdateMessage numberMessage = (DailyQuestNumberUpdateMessage)message;
/* 24 */     int limit = numberMessage.getCurrentNumber();
/*    */     
/* 26 */     int oldLimit = MainGameState.getQuestModel().getDailyQuestLimit();
/*    */     
/* 28 */     if (oldLimit > limit) {
/* 29 */       if (limit == 0) {
/*    */         
/* 31 */         ErrorWindowCreator.instance().createErrorMessage("", getLocalizedText("daily.quests.none.left"));
/*    */       } else {
/*    */         
/* 34 */         String text = getLocalizedText("daily.quests.update");
/* 35 */         text = text.replace("[X]", String.valueOf(limit));
/* 36 */         text = text.replace("[Y]", String.valueOf(TcgGame.getRpgLoader().getDailyQuestLimit()));
/* 37 */         DfxTextWindowManager.instance().getWindow("main").showText(text);
/*    */       } 
/*    */     }
/*    */     
/* 41 */     MainGameState.getQuestModel().setDailyQuestLimit(limit);
/*    */   }
/*    */   
/*    */   private String getLocalizedText(String key) {
/*    */     try {
/* 46 */       return TcgGame.getLocalizedText(key, new String[0]);
/* 47 */     } catch (MissingResourceException ignore) {
/* 48 */       return key;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 54 */     return 241;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\DailyQuestUpdatedNumberProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */