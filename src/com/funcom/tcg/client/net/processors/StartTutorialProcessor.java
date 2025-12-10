/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.quest.TutorialQuestListener;
/*    */ import com.funcom.tcg.net.message.StartTutorialMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StartTutorialProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   private boolean pet = true;
/*    */   
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 23 */     if (message.getMessageType() == 79 && 
/* 24 */       message instanceof StartTutorialMessage) {
/* 25 */       this.pet = ((StartTutorialMessage)message).isPetTutorial();
/* 26 */       if (MainGameState.getPauseModel() != null) {
/* 27 */         MainGameState.getPauseModel().instantPause();
/*    */       }
/* 29 */       MainGameState.showNewNoahTutorialWindow();
/* 30 */       if (this.pet) {
/* 31 */         TutorialQuestListener.highlightPetButton(1);
/*    */       } else {
/* 33 */         TutorialQuestListener.highlightEquipmentButton();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 41 */     return 79;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\StartTutorialProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */