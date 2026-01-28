/*     */ package com.funcom.gameengine.conanchat;
/*     */ import com.funcom.gameengine.conanchat.datatypes.Data;
/*     */ import com.funcom.gameengine.conanchat.datatypes.Integer32;
import com.funcom.gameengine.conanchat.datatypes.Integer40;
import com.funcom.gameengine.conanchat.datatypes.StringDatatype;
/*     */ import com.funcom.gameengine.conanchat.handlers.ConanChatMessageHandler;
/*     */ import com.funcom.gameengine.conanchat.handlers.NotHandledException;
/*     */ import com.funcom.gameengine.conanchat.handlers.ReflectiveCallMessageHandler;
import com.funcom.gameengine.conanchat.packets2.BuddyAdd;
/*     */ import com.funcom.gameengine.conanchat.packets2.BuddyAdded;
/*     */ import com.funcom.gameengine.conanchat.packets2.BuddyRem;
/*     */ import com.funcom.gameengine.conanchat.packets2.ChatMessage;
/*     */ import com.funcom.gameengine.conanchat.packets2.ClientName;
/*     */ import com.funcom.gameengine.conanchat.packets2.GroupJoin;
import com.funcom.gameengine.conanchat.packets2.GroupMessage;
/*     */ import com.funcom.gameengine.conanchat.packets2.GroupMessageServer;
/*     */ import com.funcom.gameengine.conanchat.packets2.GroupPart;
import com.funcom.gameengine.conanchat.packets2.LoginFailure;
/*     */ import com.funcom.gameengine.conanchat.packets2.LoginResponse;
/*     */ import com.funcom.gameengine.conanchat.packets2.MessageAnonVicinity;
/*     */ import com.funcom.gameengine.conanchat.packets2.MessagePrivate;
/*     */ import com.funcom.gameengine.conanchat.packets2.MessageSystemLocal;
import com.funcom.gameengine.conanchat.packets2.Ping;

/*     */ import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ public class DefaultChatUser {
/*  24 */   private static final Logger LOGGER = Logger.getLogger(DefaultChatUser.class.getName());
/*     */   
/*     */   private static final long PROTOCOL_VERSION = 0L;
/*     */   
/*     */   private OutgoingQueue outgoingQueue;
/*     */   private boolean logged;
/*     */   private ConanChatMessageHandler handlers;
/*     */   private long clientId;
/*     */   private long clientCookie;
/*     */   private Map<Long, String> idNickMap;
/*     */   public static final String CONF_REQUEST_BUDDY = "reqBuddy";
/*     */   public static final String CONF_RESPONSE_BUDDY = "resBuddy";
/*     */   public static final String REMOVE_BUDDY = "removeBuddy";
/*     */   
/*     */   public DefaultChatUser(long clientId, long clientCookie) {
/*  39 */     this.clientId = clientId;
/*  40 */     this.clientCookie = clientCookie;
/*  41 */     this.idNickMap = new HashMap<Long, String>();
/*     */     
/*  43 */     this.handlers = (ConanChatMessageHandler)new ReflectiveCallMessageHandler((short)5, "message_loginok", this, (ConanChatMessageHandler)new ReflectiveCallMessageHandler((short)6, "message_loginfailure", this, (ConanChatMessageHandler)new ReflectiveCallMessageHandler((short)20, "message_clientname", this, (ConanChatMessageHandler)new ReflectiveCallMessageHandler((short)30, "message_messageprivate", this, (ConanChatMessageHandler)new ReflectiveCallMessageHandler((short)37, "message_messagesystemlocal", this, (ConanChatMessageHandler)new ReflectiveCallMessageHandler((short)35, "message_messageanonvicinity", this, (ConanChatMessageHandler)new ReflectiveCallMessageHandler((short)60, "message_groupjoin", this, (ConanChatMessageHandler)new ReflectiveCallMessageHandler((short)65, "message_groupmessage", this, (ConanChatMessageHandler)new ReflectiveCallMessageHandler((short)41, "message_buddyremoved", this, (ConanChatMessageHandler)new ReflectiveCallMessageHandler((short)40, "message_buddyadded", this, (ConanChatMessageHandler)new ReflectiveCallMessageHandler((short)100, "message_ping", this, (ConanChatMessageHandler)new ReflectiveCallMessageHandler((short)61, "message_grouppart", this))))))))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutgoingQueue(OutgoingQueue outgoingQueue) {
/*  60 */     this.outgoingQueue = outgoingQueue;
/*     */   }
/*     */   
/*     */   protected void checkLogged() {
/*  64 */     if (!this.logged)
/*  65 */       LOGGER.warn("Admin is not authenticated, and trying to send a message that requires it!"); 
/*     */   }
/*     */   
/*     */   public boolean isLogged() {
/*  69 */     return this.logged;
/*     */   }
/*     */   
/*     */   protected void setLogged() {
/*  73 */     this.logged = true;
/*     */   }
/*     */   
/*     */   public void logon() {
/*  77 */     if (isLogged()) {
/*  78 */       LOGGER.log((Priority)Level.WARN, "Already logged on!");
/*     */       
/*     */       return;
/*     */     } 
/*  82 */     queueMessage((ChatMessage)new LoginResponse(new Integer32(0L), new Integer32(this.clientId), new Integer32(this.clientCookie)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void relogin() {
/*  90 */     if (isLogged()) {
/*  91 */       LOGGER.log((Priority)Level.INFO, "Already logged on, and relogging...");
/*     */     } else {
/*  93 */       LOGGER.log((Priority)Level.WARN, "Relogging, but not logged on!");
/*     */     } 
/*  95 */     queueMessage((ChatMessage)new LoginResponse(new Integer32(0L), new Integer32(this.clientId), new Integer32(this.clientCookie)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void messageFriendRequest(int id) {
/* 103 */     messagePrivate(id, "reqBuddy");
/*     */   }
/*     */   
/*     */   public void messageFriendRemove(int id) {
/* 107 */     messagePrivate(id, "removeBuddy");
/*     */   }
/*     */   
/*     */   public void addFriend(int id) {
/* 111 */     queueMessage((ChatMessage)new BuddyAdd(new Integer32(id)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeFriend(int id) {
/* 117 */     queueMessage((ChatMessage)new BuddyRem(new Integer32(id)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void messagePrivate(long toClientId, String message) {
/* 123 */     queueMessage((ChatMessage)new MessagePrivate(new Integer32(toClientId), new StringDatatype(message), new Data(new byte[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void messageGroup(long groupId, String message, byte[] extraData) {
/* 132 */     this.outgoingQueue.queueMessage((ChatMessage)new GroupMessage(new Integer40(groupId), new StringDatatype(message), new Data(extraData)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void messageReceived(ChatMessage chatMessage) {
/* 145 */     LOGGER.debug("Message received: " + chatMessage.getMessageType());
/*     */     try {
/* 147 */       this.handlers.handle(chatMessage);
/* 148 */     } catch (NotHandledException e) {
/*     */ 
/*     */       
/* 151 */       LOGGER.log((Priority)Level.ERROR, "ERROR: Message improperly handled: " + e.getUnhandledMessage(), (Throwable)e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void queueMessage(ChatMessage message) {
/* 156 */     this.outgoingQueue.queueMessage(message);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 161 */     return "DefaultChatUser{clientId=" + this.clientId + ", clientCookie=" + this.clientCookie + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void message_loginok(ChatMessage chatMessage) {
/* 178 */     this.logged = true;
/* 179 */     LOGGER.info(this.clientId + " logged on.");
/*     */   }
/*     */   
/*     */   public void message_loginfailure(ChatMessage chatMessage) {
/* 183 */     this.logged = false;
/* 184 */     LOGGER.error(this.clientId + " FAILED to logon, ErrorMessage: " + ((LoginFailure)chatMessage).getErrorMessage());
/*     */   }
/*     */   
/*     */   public void message_clientname(ChatMessage chatMessage) {
/* 188 */     ClientName clientName = (ClientName)chatMessage;
/* 189 */     long id = clientName.getClientid().getValue();
/* 190 */     String nick = clientName.getClientname().getValue();
/* 191 */     this.idNickMap.put(Long.valueOf(id), nick);
/* 192 */     LOGGER.info(String.format("ClientName: %d/%s", new Object[] { Long.valueOf(id), nick }));
/*     */   }
/*     */   
/*     */   public void message_messageprivate(ChatMessage chatMessage) {
/* 196 */     MessagePrivate messagePrivate = (MessagePrivate)chatMessage;
/* 197 */     String nick = this.idNickMap.containsKey(Long.valueOf(messagePrivate.getClientid().getValue())) ? this.idNickMap.get(Long.valueOf(messagePrivate.getClientid().getValue())) : "unknown!";
/* 198 */     LOGGER.info(String.format("%s whispers: %s", new Object[] { nick, messagePrivate.getMessageBody().getValue() }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void message_messagesystemlocal(ChatMessage chatMessage) {
/* 203 */     MessageSystemLocal messageSystemLocal = (MessageSystemLocal)chatMessage;
/* 204 */     LOGGER.info(String.format("MessageSystemLocal: %s %s %s %s", new Object[] { Long.valueOf(messageSystemLocal.getClientid().getValue()), Long.valueOf(messageSystemLocal.getWindowid().getValue()), Long.valueOf(messageSystemLocal.getMessageHash().getValue()), Arrays.toString(messageSystemLocal.getArgsToTheFormatString().getValue()) }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void message_messageanonvicinity(ChatMessage chatMessage) {
/* 213 */     MessageAnonVicinity messageAnonVicinity = (MessageAnonVicinity)chatMessage;
/* 214 */     LOGGER.info(String.format("MessageAnonVicinity: %s %s %s", new Object[] { messageAnonVicinity.getSendername().getValue(), messageAnonVicinity.getMessageBody().getValue(), Arrays.toString(messageAnonVicinity.getExtraData().getValue()) }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void message_groupjoin(ChatMessage chatMessage) {
/* 222 */     GroupJoin groupJoin = (GroupJoin)chatMessage;
/* 223 */     LOGGER.info(String.format("GroupJoin: %s %s %s %s", new Object[] { Long.valueOf(groupJoin.getGroupid().getValue()), groupJoin.getGroupname().getValue(), Long.valueOf(groupJoin.getGroupflags().getValue()), Arrays.toString(groupJoin.getExtraData().getValue()) }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void message_groupmessage(ChatMessage chatMessage) {
/* 232 */     GroupMessageServer groupMessage = (GroupMessageServer)chatMessage;
/* 233 */     LOGGER.info(String.format("GroupMessageSERVER: %s %s %s", new Object[] { Long.valueOf(groupMessage.getClientid().getValue()), Long.valueOf(groupMessage.getGroupid().getValue()), groupMessage.getMessageBody().getValue(), Arrays.toString(groupMessage.getExtraData().getValue()) }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void message_buddyadded(ChatMessage chatMessage) {
/* 242 */     BuddyAdded buddyAdded = (BuddyAdded)chatMessage;
/* 243 */     LOGGER.info(String.format("BuddyAdded: %s %s %s", new Object[] { Long.valueOf(buddyAdded.getClientid().getValue()), Integer.valueOf(buddyAdded.getOnlineStatus().getIntValue()), Long.valueOf(buddyAdded.getRunningplayfieldlastonlinetime().getValue()) }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void message_buddyremoved(ChatMessage chatMessage) {
/* 251 */     BuddyRem buddyRemoved = (BuddyRem)chatMessage;
/* 252 */     LOGGER.info(String.format("BuddyRemoved: %s", new Object[] { Long.valueOf(buddyRemoved.getClientid().getValue()) }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void message_ping(ChatMessage chatMessage) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void message_grouppart(ChatMessage chatMessage) {
/* 263 */     GroupPart groupPart = (GroupPart)chatMessage;
/* 264 */     LOGGER.info(String.format("GroupPart: %s", new Object[] { Long.valueOf(groupPart.getGroupid().getValue()) }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void messageFriendResponse(int id) {}
/*     */ 
/*     */   
/*     */   public void setClientCookie(long clientCookie) {
/* 273 */     this.clientCookie = clientCookie;
/*     */   }
/*     */   
/*     */   public void setClientId(long clientId) {
/* 277 */     this.clientId = clientId;
/*     */   }
/*     */   
/*     */   public void messagePing() {
/* 281 */     this.outgoingQueue.queueMessage((ChatMessage)new Ping(new Data("p".getBytes())));
/*     */   }
/*     */   
/*     */   public void addFriendWithNickname(long clientid, String nickname) {}
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\DefaultChatUser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */