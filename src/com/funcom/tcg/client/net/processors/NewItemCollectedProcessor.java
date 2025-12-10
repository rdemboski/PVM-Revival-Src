/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.rpgengine2.items.ItemDescription;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.net.processors.loadingmanager.NewItemCollectedLMToken;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.NewItemCollectedMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class NewItemCollectedProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 21 */     NewItemCollectedMessage itemMessage = (NewItemCollectedMessage)message;
/*    */     
/* 23 */     if (LoadingManager.USE) {
/* 24 */       LoadingManager.INSTANCE.submit((LoadingManagerToken)new NewItemCollectedLMToken(itemMessage));
/*    */     } else {
/*    */       
/* 27 */       ItemDescription itemDesc = TcgGame.getRpgLoader().getItemManager().getDescription(itemMessage.getItemId(), itemMessage.getTier());
/*    */       
/* 29 */       MainGameState.getRenderPassManager().getGuiParticlesRenderPass().triggerParticleEffect("itemCollected");
/* 30 */       MainGameState.getHudModel().triggerItemPickup(itemDesc);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 36 */     return 237;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\NewItemCollectedProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */