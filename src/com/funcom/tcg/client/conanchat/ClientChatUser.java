/*     */ package com.funcom.tcg.client.conanchat;
/*     */ 
/*     */ import com.funcom.gameengine.conanchat.DefaultChatUser;
/*     */ import com.funcom.gameengine.conanchat.datatypes.Integer32;
/*     */ import com.funcom.gameengine.conanchat.packets2.BuddyAdded;
/*     */ import com.funcom.gameengine.conanchat.packets2.BuddyRem;
/*     */ import com.funcom.gameengine.conanchat.packets2.ChatMessage;
/*     */ import com.funcom.gameengine.conanchat.packets2.ClientName;
/*     */ import com.funcom.gameengine.conanchat.packets2.GroupJoin;
/*     */ import com.funcom.gameengine.conanchat.packets2.GroupMessageServer;
/*     */ import com.funcom.gameengine.conanchat.packets2.LoginFailure;
/*     */ import com.funcom.gameengine.conanchat.packets2.MessageAnonVicinity;
/*     */ import com.funcom.gameengine.conanchat.packets2.MessagePrivate;
/*     */ import com.funcom.gameengine.conanchat.packets2.MessageSystemLocal;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.chat.ChatNetworkController;
/*     */ import com.funcom.tcg.net.SanctionType;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientChatUser
/*     */   extends DefaultChatUser
/*     */   implements ChatClientsInterface
/*     */ {
/*  31 */   private static final Logger LOGGER = Logger.getLogger(ClientChatUser.class);
/*     */   private ChatNetworkController chatNetworkController;
/*     */   private Map<Long, String> chatClients;
/*  34 */   private final int CRISP_NET_MODERATOR_WS_ID = 2;
/*     */   
/*     */   public ClientChatUser(long clientId, long clientCookie) {
/*  37 */     super(clientId, clientCookie);
/*  38 */     this.chatClients = new HashMap<Long, String>();
/*     */   }
/*     */ 
/*     */   
/*     */   public void message_loginok(ChatMessage chatMessage) {
/*  43 */     LOGGER.info("Logged to chat server.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void message_loginfailure(ChatMessage chatMessage) {
/*  48 */     LOGGER.error("Failed to login to chat server!" + ((LoginFailure)chatMessage).getErrorMessage());
/*     */   }
/*     */ 
/*     */   
/*     */   public void message_clientname(ChatMessage chatMessage) {
/*  53 */     ClientName cn = (ClientName)chatMessage;
/*  54 */     this.chatClients.put(Long.valueOf(cn.getClientid().getValue()), cn.getClientname().getValue());
/*  55 */     LOGGER.info(String.format("ClientName: %d/%s", new Object[] { Long.valueOf(cn.getClientid().getValue()), cn.getClientname().getValue() }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void message_messageprivate(ChatMessage chatMessage) {
/*  61 */     SanctionManager sanctionManager = SanctionManager.getInstance();
/*  62 */     MessagePrivate mp = (MessagePrivate)chatMessage;
/*  63 */     Integer32 clientid = mp.getClientid();
/*  64 */     String clientName = this.chatClients.get(Long.valueOf(clientid.getValue()));
/*     */ 
/*     */     
/*  67 */     String[] message = mp.getMessageBody().getValue().split(":");
/*     */     
/*  69 */     if (message.length > 1 && MessageType.moderator.getNickname().equals(message[0])) {
/*  70 */       MainGameState.addMessage(TcgGame.getLocalizedText(message[1], new String[0]), MessageType.moderator);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  75 */     if (clientid.getValue() == 2L) {
/*     */       
/*  77 */       String command = "";
/*  78 */       String sanctionMessage = "";
/*  79 */       String duration = "0";
/*     */ 
/*     */       
/*  82 */       String[] netModerator = mp.getMessageBody().getValue().split("\\|");
/*  83 */       if (netModerator.length > 0)
/*  84 */         command = netModerator[0]; 
/*  85 */       if (netModerator.length > 1)
/*  86 */         sanctionMessage = netModerator[1]; 
/*  87 */       if (netModerator.length > 2)
/*  88 */         duration = netModerator[2]; 
/*  89 */       sanctionManager.addSanction(command, sanctionMessage, duration);
/*     */       
/*  91 */       if (SanctionType.WARN.toString().equalsIgnoreCase(command)) {
/*  92 */         if (sanctionMessage.length() > 0)
/*  93 */           MainGameState.addMessage(sanctionMessage, MessageType.moderator); 
/*     */       } else {
/*  95 */         sanctionManager.addSanction(command, sanctionMessage, duration);
/*     */         
/*  97 */         if (sanctionManager.getSanctionMessage().length() > 0) {
/*  98 */           MainGameState.addMessage(sanctionManager.displaySanctionMessage(), MessageType.moderator);
/*     */         }
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 104 */     LOGGER.info("PRIVATE " + clientName + " whispers: " + mp.getMessageBody().getValue());
/* 105 */     if (sanctionManager.isAllowedToReceive()) {
/* 106 */       this.chatNetworkController.newMessageRecieved((int)mp.getClientid().getValue(), mp.getMessageBody().getValue(), ChatNetworkController.MessageFrom.freeChat);
/*     */     }
/* 108 */     if (mp.getMessageBody().getValue().equals("reqBuddy")) {
/* 109 */       this.chatNetworkController.newFriendRequest(clientid.getValue(), clientName);
/* 110 */     } else if (mp.getMessageBody().getValue().equals("resBuddy")) {
/* 111 */       addFriend((int)clientid.getValue());
/* 112 */       MainGameState.getFriendModel().fireDfxFriendAdded((int)clientid.getValue());
/* 113 */     } else if (mp.getMessageBody().getValue().equals("removeBuddy")) {
/* 114 */       removeFriend((int)clientid.getValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void message_messagesystemlocal(ChatMessage chatMessage) {
/* 120 */     MessageSystemLocal msl = (MessageSystemLocal)chatMessage;
/* 121 */     LOGGER.info("MessageSystemLocal!");
/*     */   }
/*     */ 
/*     */   
/*     */   public void message_messageanonvicinity(ChatMessage chatMessage) {
/* 126 */     MessageAnonVicinity mav = (MessageAnonVicinity)chatMessage;
/* 127 */     LOGGER.info(mav.getSendername().getValue() + ": " + mav.getMessageBody().getValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public void message_groupjoin(ChatMessage chatMessage) {
/* 132 */     GroupJoin gj = (GroupJoin)chatMessage;
/* 133 */     LOGGER.info("You are now member of " + gj.getGroupname().getValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public void message_groupmessage(ChatMessage chatMessage) {
/* 138 */     SanctionManager sanctionManager = SanctionManager.getInstance();
/* 139 */     GroupMessageServer gm = (GroupMessageServer)chatMessage;
/* 140 */     LOGGER.info("Message received: " + gm.getMessageBody().getValue());
/* 141 */     if (sanctionManager.isAllowedToReceive()) {
/* 142 */       this.chatNetworkController.newMessageRecieved((int)gm.getClientid().getValue(), gm.getMessageBody().getValue(), ChatNetworkController.MessageFrom.freeChat);
/*     */     }
/*     */   }
/*     */   
/*     */   public void message_buddyadded(ChatMessage chatMessage) {
/* 147 */     BuddyAdded buddyAdded = (BuddyAdded)chatMessage;
/* 148 */     LOGGER.error("Friend added, ID: " + buddyAdded.getClientid().getValue());
/* 149 */     MainGameState.getFriendModel().addIdToFriendList((int)buddyAdded.getClientid().getValue(), false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void message_buddyremoved(ChatMessage chatMessage) {
/* 154 */     BuddyRem buddyRemoved = (BuddyRem)chatMessage;
/* 155 */     MainGameState.getFriendModel().removeFriend((int)buddyRemoved.getClientid().getValue());
/*     */   }
/*     */   
/*     */   public void setChatNetworkController(ChatNetworkController chatNetworkController) {
/* 159 */     this.chatNetworkController = chatNetworkController;
/*     */   }
/*     */   
/*     */   public Map<Long, String> getChatClients() {
/* 163 */     return this.chatClients;
/*     */   }
/*     */   
/*     */   public void messageFriendResponse(int id) {
/* 167 */     messagePrivate(id, "resBuddy");
/* 168 */     MainGameState.getFriendModel().fireDfxFriendAdded(id);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\conanchat\ClientChatUser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */