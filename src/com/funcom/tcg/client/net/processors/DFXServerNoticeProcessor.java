/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.view.DfxTextWindowManager;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.net.message.DFXServerNoticeMessage;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class DFXServerNoticeProcessor
/*    */   implements MessageProcessor
/*    */ {
/* 17 */   private static final Logger LOGGER = Logger.getLogger(DFXServerNoticeProcessor.class);
/*    */   
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 20 */     DFXServerNoticeMessage serverNoticeMessage = (DFXServerNoticeMessage)message;
/* 21 */     LOGGER.debug("Server notice received: " + serverNoticeMessage.getMessage());
/* 22 */     String notice = TcgGame.getLocalizedTextNoWarning(serverNoticeMessage.getMessage(), null);
/* 23 */     if (!notice.isEmpty())
/* 24 */       DfxTextWindowManager.instance().getWindow("main").showText(notice); 
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 28 */     return 69;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\DFXServerNoticeProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */