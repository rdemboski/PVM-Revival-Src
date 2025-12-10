/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.view.TargetedEffectNode;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.net.message.UpdateTargetEffectMessage;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class UpdateTargetEffectProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 18 */     UpdateTargetEffectMessage updateTargetEffectMessage = (UpdateTargetEffectMessage)message;
/* 19 */     List<TargetedEffectNode> register = TcgGame.getTargetEffectRegister();
/* 20 */     for (TargetedEffectNode node : register) {
/* 21 */       if (node.getId() == updateTargetEffectMessage.getId().intValue()) {
/* 22 */         node.playDfx(updateTargetEffectMessage.getDfxId());
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 30 */     return 66;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\UpdateTargetEffectProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */