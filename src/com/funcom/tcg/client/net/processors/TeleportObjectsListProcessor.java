/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TeleportObjectsListProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 18 */     MainGameState.getRefreshFactory().refreshForType(message);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 23 */     return 62;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\TeleportObjectsListProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */