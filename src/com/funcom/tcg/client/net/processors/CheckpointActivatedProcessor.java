/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.commons.localization.JavaLocalization;
/*    */ import com.funcom.gameengine.view.DfxTextWindowManager;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.net.message.CheckpointActivatedMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CheckpointActivatedProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 23 */     CheckpointActivatedMessage checkpointActivatedMessage = (CheckpointActivatedMessage)message;
/* 24 */     DfxTextWindowManager.instance().getWindow("checkpoint").showText(JavaLocalization.getInstance().getLocalizedRPGText(checkpointActivatedMessage.getDfxText()));
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 29 */     return 50;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\CheckpointActivatedProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */