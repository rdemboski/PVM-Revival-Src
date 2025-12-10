/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.TcgChatFriendRequestMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class TcgChatFriendRequestProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 16 */     TcgChatFriendRequestMessage tcgChatFriendRequestMessage = (TcgChatFriendRequestMessage)message;
/* 17 */     MainGameState.getChatNetworkController().newFriendRequest(tcgChatFriendRequestMessage.getSourceId(), tcgChatFriendRequestMessage.getClientName());
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 22 */     return 58;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\TcgChatFriendRequestProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */