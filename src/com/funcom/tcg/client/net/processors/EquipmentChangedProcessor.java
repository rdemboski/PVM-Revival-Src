/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.PrioritizedLoadingTokenQueue;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.net.processors.loadingmanager.EquipmentChangedLMToken;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.EquipmentChangedMessage;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Level;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.Priority;
/*    */ 
/*    */ 
/*    */ public class EquipmentChangedProcessor
/*    */   implements MessageProcessor
/*    */ {
/* 26 */   private static final Logger LOGGER = Logger.getLogger(EquipmentChangedProcessor.class.getName());
/*    */   
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 29 */     EquipmentChangedMessage equipmentChangedMessage = (EquipmentChangedMessage)message;
/* 30 */     LOGGER.log((Priority)Level.INFO, "equipmentChangedMessage = " + equipmentChangedMessage);
/*    */     
/* 32 */     if (LoadingManager.USE) {
/* 33 */       LoadingManager.INSTANCE.submitByPriority((LoadingManagerToken)new EquipmentChangedLMToken(equipmentChangedMessage), PrioritizedLoadingTokenQueue.TokenPriority.PRIORITY_CREATURES);
/*    */     } else {
/*    */       
/* 36 */       PropNode prop = TcgGame.getPropNodeRegister().getPropNode(Integer.valueOf(equipmentChangedMessage.getPlayerId()));
/* 37 */       if (prop != null) {
/* 38 */         ClientPlayer otherPlayer = (ClientPlayer)prop.getProp();
/*    */         
/* 40 */         if (equipmentChangedMessage.isEquipped()) {
/* 41 */           ClientItem item = MainGameState.getItemRegistry().getItemForClassID(equipmentChangedMessage.getItemId(), equipmentChangedMessage.getTier());
/* 42 */           otherPlayer.getEquipDoll().setItem(item);
/*    */         } else {
/* 44 */           otherPlayer.getEquipDoll().removeItem(equipmentChangedMessage.getPlacement());
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 51 */     return 216;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\EquipmentChangedProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */