/*    */ package com.funcom.tcg.client.conanchat;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.DefaultChatUser;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class TcgDummyChatUser
/*    */   extends DefaultChatUser implements ChatClientsInterface {
/*    */   private Map<Long, String> chatClients;
/*    */   
/*    */   public TcgDummyChatUser(long clientId, long clientCookie) {
/* 12 */     super(clientId, clientCookie);
/* 13 */     this.chatClients = new HashMap<Long, String>();
/*    */   }
/*    */ 
/*    */   
/*    */   public void addFriendWithNickname(long clientid, String nickname) {
/* 18 */     this.chatClients.put(Long.valueOf(clientid), nickname);
/*    */   }
/*    */   
/*    */   public Map<Long, String> getChatClients() {
/* 22 */     return this.chatClients;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\conanchat\TcgDummyChatUser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */