/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.net.message.ActivePetUpdateMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class ActivePetUpdateProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 19 */     ActivePetUpdateMessage activePetUpdateMessage = (ActivePetUpdateMessage)message;
/*    */     
/* 21 */     PropNode prop = TcgGame.getPropNodeRegister().getPropNode(Integer.valueOf(activePetUpdateMessage.getCreatureId()));
/*    */ 
/*    */     
/* 24 */     if (prop != null && prop.getProp() instanceof ClientPlayer) {
/* 25 */       ClientPlayer otherPlayer = (ClientPlayer)prop.getProp();
/*    */       
/* 27 */       otherPlayer.setActivePetFromClassId(activePetUpdateMessage.getActivePetClassId());
/* 28 */       ClientPet activePet = otherPlayer.getActivePet();
/* 29 */       if (activePetUpdateMessage.getPetLevel() >= 40) {
/* 30 */         activePet.setLevel(activePetUpdateMessage.getPetLevel());
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 36 */     return 213;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\ActivePetUpdateProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */