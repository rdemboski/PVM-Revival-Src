/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.conanchat.SanctionManager;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.net.SanctionType;
/*    */ import com.funcom.tcg.net.message.SendSanctionResponseMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SendSanctionResponseProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 19 */     if (TcgGame.isChatEnabled()) {
/* 20 */       SendSanctionResponseMessage sendSanctionResponseMessage = (SendSanctionResponseMessage)message;
/* 21 */       SanctionType sanction = sendSanctionResponseMessage.getSanction();
/* 22 */       long sanctionUntil = sendSanctionResponseMessage.getSanctionUntil();
/* 23 */       String sanctionMessage = sendSanctionResponseMessage.getSanctionMessage();
/*    */       
/* 25 */       SanctionManager sanctionManager = SanctionManager.getInstance();
/* 26 */       sanctionManager.setCurrentSanction(sanction);
/* 27 */       sanctionManager.setSanctionedUntil(sanctionUntil);
/* 28 */       sanctionManager.setDefaultSanctionMessage(sanctionMessage);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 34 */     return 81;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\SendSanctionResponseProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */