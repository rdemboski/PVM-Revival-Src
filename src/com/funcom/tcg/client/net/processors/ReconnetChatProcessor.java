/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.ChatClient;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.conanchat.ClientChatUser;
/*    */ import com.funcom.tcg.client.conanchat.MessageType;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.TcgUI;
/*    */ import com.funcom.tcg.client.ui.hud.NoChatServerWindow;
/*    */ import com.funcom.tcg.net.message.ReconnectToChatMessage;
/*    */ import com.jmex.bui.BWindow;
/*    */ import com.jmex.bui.BuiSystem;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Level;
/*    */ import org.apache.log4j.Priority;
/*    */ 
/*    */ public class ReconnetChatProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/*    */     try {
/* 27 */       ChatClient client = MainGameState.getPlayerModel().getChatController().getChatClient();
/* 28 */       client.reconnect();
/*    */       
/* 30 */       ReconnectToChatMessage reconnectToChatMessage = (ReconnectToChatMessage)message;
/* 31 */       ClientChatUser clientChatUser = (ClientChatUser)client.getChatUser();
/* 32 */       clientChatUser.setClientId(reconnectToChatMessage.getChatClientId());
/* 33 */       clientChatUser.setClientCookie(reconnectToChatMessage.getChatClientCookie());
/* 34 */       clientChatUser.relogin();
/*    */       
/* 36 */       if (!MainGameState.getPlayerModel().getChatController().isChatControllerEnabled())
/*    */       {
/* 38 */         MainGameState.addMessage(TcgGame.getLocalizedText("dialog.chat.enabled", new String[0]), MessageType.info);
/*    */       }
/*    */       
/* 41 */       MainGameState.getPlayerModel().getChatController().enableUpdates();
/*    */     
/*    */     }
/* 44 */     catch (IOException e) {
/* 45 */       LOGGER.log((Priority)Level.ERROR, "GameServer sent chat reconnection message, but we failed reconnecting. Waiting for another GameServer notification...", e);
/* 46 */     } catch (NullPointerException e) {
/* 47 */       LOGGER.log((Priority)Level.ERROR, "Cannot reconnect to the chat server, no chat controller found.");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 54 */     return 55;
/*    */   }
/*    */   
/*    */   public void showChatEnabledDialog() {
/* 58 */     BWindow existingWindow = TcgUI.getWindowFromClass(NoChatServerWindow.class);
/* 59 */     if (existingWindow == null) {
/* 60 */       NoChatServerWindow window = new NoChatServerWindow(TcgGame.getResourceManager(), true);
/* 61 */       window.setLayer(103);
/* 62 */       BuiSystem.getRootNode().addWindow((BWindow)window);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\ReconnetChatProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */