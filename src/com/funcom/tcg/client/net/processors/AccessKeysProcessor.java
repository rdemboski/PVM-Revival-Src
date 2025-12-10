/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.AccessKeysMessage;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AccessKeysProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 20 */     AccessKeysMessage accessKeysMessage = (AccessKeysMessage)message;
/* 21 */     List<String> newAccessKeys = accessKeysMessage.getAccessKeys();
/* 22 */     List<String> accessKeys = MainGameState.getAccessKeys();
/* 23 */     List<Long> accessKeyExpireTimes = MainGameState.getAccessKeyExpireTimes();
/* 24 */     accessKeys.clear();
/* 25 */     accessKeyExpireTimes.clear();
/* 26 */     accessKeys.addAll(newAccessKeys);
/* 27 */     accessKeyExpireTimes.addAll(accessKeysMessage.getExpireTimes());
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 32 */     return 254;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\AccessKeysProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */