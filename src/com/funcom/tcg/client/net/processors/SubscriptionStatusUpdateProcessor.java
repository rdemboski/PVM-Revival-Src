/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.SubscriptionStatusUpdateMessage;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class SubscriptionStatusUpdateProcessor
/*    */   implements MessageProcessor
/*    */ {
/* 16 */   private static final Logger LOGGER = Logger.getLogger(SubscriptionStatusUpdateProcessor.class);
/*    */   
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 19 */     SubscriptionStatusUpdateMessage statusUpdateMessage = (SubscriptionStatusUpdateMessage)message;
/* 20 */     LOGGER.debug("Subscription status update, new status: " + statusUpdateMessage.getNewFlags());
/* 21 */     MainGameState.getPlayerModel().getSubscriptionState().setFlags(statusUpdateMessage.getNewFlags());
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 25 */     return 70;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\SubscriptionStatusUpdateProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */