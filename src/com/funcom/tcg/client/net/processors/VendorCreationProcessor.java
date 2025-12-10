/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.gameengine.view.RepresentationalNode;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.net.processors.loadingmanager.VendorLMToken;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.VendorCreationMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class VendorCreationProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 21 */     if (LoadingManager.USE) {
/* 22 */       LoadingManager.INSTANCE.submit((LoadingManagerToken)new VendorLMToken((VendorCreationMessage)message));
/*    */     } else {
/*    */       
/* 25 */       PropNode creatureNode = MainGameState.getNodeFactory().createForType((VendorCreationMessage)message);
/*    */       
/* 27 */       MainGameState.getWorld().addObject((RepresentationalNode)creatureNode);
/* 28 */       TcgGame.getVendorRegister().addPropNode(creatureNode);
/* 29 */       creatureNode.updateRenderState();
/*    */     } 
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 34 */     return 22;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\VendorCreationProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */