/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.ui.errorreporting.ErrorWindowCreator;
/*    */ import com.funcom.tcg.net.message.ErrorMessage;
/*    */ import java.util.Map;
/*    */ import java.util.MissingResourceException;
/*    */ 
/*    */ public class ErrorProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 17 */     ErrorMessage errorMessage = (ErrorMessage)message;
/*    */     
/* 19 */     ErrorWindowCreator.instance().createErrorMessage(getLocalizedText(errorMessage.getTitle()), getLocalizedText(errorMessage.getErrorMessage()));
/*    */   }
/*    */ 
/*    */   
/*    */   private String getLocalizedText(String key) {
/*    */     try {
/* 25 */       return TcgGame.getLocalizedText(key, new String[0]);
/* 26 */     } catch (MissingResourceException ignore) {
/* 27 */       return key;
/*    */     } 
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 32 */     return 29;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\ErrorProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */