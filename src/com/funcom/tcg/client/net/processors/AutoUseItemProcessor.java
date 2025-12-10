/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*    */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.AutoUseItemMessage;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class AutoUseItemProcessor
/*    */   implements MessageProcessor
/*    */ {
/* 18 */   private static final Logger LOGGER = Logger.getLogger(AutoUseItemProcessor.class.getName());
/*    */   
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 21 */     AutoUseItemMessage itemMessage = (AutoUseItemMessage)message;
/*    */     
/* 23 */     LocalClientPlayer localClientPlayer = MainGameState.getPlayerModel();
/*    */ 
/*    */ 
/*    */     
/* 27 */     ClientItem item = MainGameState.getItemRegistry().getItemForClassID(itemMessage.getItemDescriptionId(), itemMessage.getTier());
/* 28 */     localClientPlayer.setGlobalCooldown(item.getGlobalCooldown());
/* 29 */     item.executeUseDFX((ClientPlayer)localClientPlayer);
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 33 */     return 220;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\AutoUseItemProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */