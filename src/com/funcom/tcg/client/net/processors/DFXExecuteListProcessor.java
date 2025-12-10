/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.net.message.DFXExecuteListMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DFXExecuteListProcessor
/*    */   extends DFXExecuteProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 18 */     DFXExecuteListMessage dfxExecuteMessage = (DFXExecuteListMessage)message;
/* 19 */     DFXExecuteListMessage.DfxIterator iterator = dfxExecuteMessage.getDfxIterator();
/*    */     
/* 21 */     while (iterator.hasMoreIDs()) {
/* 22 */       int id = iterator.nextID();
/* 23 */       String dfx = iterator.getDfxId();
/* 24 */       float time = iterator.getLocalTime();
/* 25 */       int creatureType = iterator.getCreatureType();
/*    */       
/* 27 */       processTarget(id, dfx, time, creatureType);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 33 */     return 229;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\DFXExecuteListProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */