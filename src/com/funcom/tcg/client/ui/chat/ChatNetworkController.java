/*     */ package com.funcom.tcg.client.ui.chat;
/*     */ import com.funcom.gameengine.view.PropNode;
import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
import com.funcom.tcg.client.conanchat.ChatClientsInterface;
import com.funcom.tcg.client.conanchat.MessageType;
/*     */ import com.funcom.tcg.client.conanchat.SanctionManager;
import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.net.Friend;
/*     */ import com.funcom.tcg.net.message.InternalChatMessage;
/*     */ import com.funcom.tcg.net.message.SearchPlayerByNicknameRequestMessage;
import com.funcom.tcg.net.message.SearchPlayerToTellRequestMessage;

import java.util.HashMap;
/*     */ import java.util.Iterator;
import java.util.LinkedHashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;

import org.jdom.Attribute;
import org.jdom.DataConversionException;
import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ public class ChatNetworkController implements ChatListener {
/*     */   public static final String ELM_CHAT_MESSAGE = "cm";
/*     */   public static final String ELM_FRIEND_MESSAGE = "friend";
/*     */   public static final String ATTR_CHAT_MESSAGE = "message";
/*     */   public static final String ATTR_CHAT_ID = "id";
/*     */   public static final String ATTR_ACTION_ADD = "add";
/*     */   public static final String CONST_ID_DELIM = "#ID#";
/*     */   public static final String CONST_TELL_DELIM = "#whisper";
/*     */   private Document chatMessageResource;
/*     */   private ChatOutput chatOutput;
/*     */   private ChatUIController chatUiController;
/*     */   private Map<Short, Element> elementIdMap;
/*     */   
/*     */   public enum MessageFrom {
/*  30 */     freeChat,
/*  31 */     InternalCannedCHat,
/*  32 */     siteCasting;
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
/*     */ 
/*     */   
/*     */   public ChatNetworkController(ChatOutput chatOutput, Document chatMessageResource, ChatUIController chatUiController) {
/*  51 */     this.chatOutput = chatOutput;
/*  52 */     this.chatMessageResource = chatMessageResource;
/*  53 */     this.chatUiController = chatUiController;
/*     */     
/*  55 */     this.elementIdMap = new LinkedHashMap<Short, Element>();
/*  56 */     initializeElementIds();
/*     */   }
/*     */   
/*     */   private void initializeElementIds() {
/*  60 */     Element rootElement = this.chatMessageResource.getRootElement();
/*  61 */     validateChildrenIds(rootElement);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void validateChildrenIds(Element element) {
/*  67 */     if (element.getChildren().size() > 0) {
/*  68 */       List<Element> children = element.getChildren();
/*  69 */       for (Element child : children) {
/*  70 */         String attributeValue = child.getAttributeValue("id");
/*  71 */         short attributeId = Short.parseShort(attributeValue);
/*  72 */         this.elementIdMap.put(Short.valueOf(attributeId), child);
/*     */         
/*  74 */         if (child.getChildren().size() > 0)
/*  75 */           validateChildrenIds(child); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Element getRootElement() {
/*  81 */     return this.chatMessageResource.getRootElement();
/*     */   }
/*     */ 
/*     */   
/*     */   public void acceptFriendResponse(int id) {
/*  86 */     this.chatOutput.sendFriendResponse(id);
/*  87 */     this.chatOutput.addFriend(id);
/*     */   }
/*     */   
/*     */   public Map<Integer, PropNode> getPlayersInView() {
/*  91 */     Map<Integer, PropNode> playersInView = new HashMap<Integer, PropNode>();
/*  92 */     List<PropNode> propNodes = TcgGame.getPropNodeRegister().getPropList();
/*  93 */     synchronized (propNodes) {
/*  94 */       Iterator<PropNode> iterator = propNodes.iterator();
/*  95 */       while (iterator.hasNext()) {
/*  96 */         PropNode propNode = iterator.next();
/*  97 */         if (propNode.getProp() instanceof ClientPlayer) {
/*  98 */           playersInView.put(Integer.valueOf(((ClientPlayer)propNode.getProp()).getExternalChatId()), propNode);
/*     */         }
/*     */       } 
/*     */     } 
/* 102 */     return playersInView;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean searchPlayerRequest(String nickname) {
/* 107 */     if (nickname.equalsIgnoreCase(MainGameState.getPlayerNode().getProp().getName())) {
/* 108 */       return false;
/*     */     }
/*     */     
/* 111 */     SearchPlayerByNicknameRequestMessage message = new SearchPlayerByNicknameRequestMessage(nickname);
/*     */     try {
/* 113 */       NetworkHandler.instance().getIOHandler().send((Message)message);
/* 114 */     } catch (InterruptedException e) {
/* 115 */       e.printStackTrace();
/* 116 */       return false;
/*     */     } 
/* 118 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean searchPlayerToTellRequest(String nickname, String message, String name) {
/* 123 */     if (nickname.equalsIgnoreCase(MainGameState.getPlayerNode().getProp().getName())) {
/* 124 */       return false;
/*     */     }
/*     */     
/* 127 */     SearchPlayerToTellRequestMessage tellRequestMessage = new SearchPlayerToTellRequestMessage(nickname, message, name);
/*     */     try {
/* 129 */       NetworkHandler.instance().getIOHandler().send((Message)tellRequestMessage);
/* 130 */     } catch (InterruptedException e) {
/* 131 */       e.printStackTrace();
/* 132 */       return false;
/*     */     } 
/* 134 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sendMessage(Element mainMenuElement, Element messageElement) {
/* 145 */     SanctionManager sanctionManager = SanctionManager.getInstance();
/* 146 */     if (!sanctionManager.isAllowedToSend()) {
/*     */       
/* 148 */       if (sanctionManager.getSanctionMessage().length() > 0)
/* 149 */         MainGameState.addMessage(sanctionManager.displaySanctionMessage(), MessageType.moderator); 
/* 150 */       return true;
/*     */     } 
/* 152 */     LinkedList<Element> list = new LinkedList<Element>();
/* 153 */     list.addLast(messageElement);
/*     */     
/* 155 */     String messageElementId = messageElement.getAttributeValue("id");
/* 156 */     LinkedList<Element> outPutMessageElements = buildMessage(mainMenuElement, messageElement, list);
/*     */     
/* 158 */     StringBuffer sb = new StringBuffer();
/* 159 */     for (Element outPutMessageElement : outPutMessageElements) {
/* 160 */       if (shouldElementBeAdded(outPutMessageElement)) {
/* 161 */         sb.append(outPutMessageElement.getAttributeValue("message")).append(" ");
/*     */       }
/*     */     } 
/* 164 */     if (sb.length() > 0) {
/* 165 */       int chatId = (int)TcgGame.getLoginResponse().getChatClientId();
/* 166 */       String message = MainGameState.getPlayerModel().getName() + ":" + "#ID#" + messageElementId;
/*     */       
/* 168 */       if (TcgGame.isAllowFreeChat()) {
/* 169 */         this.chatOutput.sendMessage(chatId, message);
/*     */       } else {
/*     */         
/* 172 */         InternalChatMessage messageToSend = new InternalChatMessage(chatId, message);
/*     */         try {
/* 174 */           NetworkHandler.instance().getIOHandler().send((Message)messageToSend);
/* 175 */         } catch (InterruptedException e) {
/* 176 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 183 */       return true;
/*     */     } 
/* 185 */     return false;
/*     */   }
/*     */   
/*     */   public boolean sendMessage(String message) {
/* 189 */     this.chatOutput.sendMessage((int)TcgGame.getLoginResponse().getChatClientId(), MainGameState.getPlayerModel().getName() + ":" + message);
/*     */     
/* 191 */     return true;
/*     */   }
/*     */   
/*     */   public void newMessageRecieved(int playerSourceID, String chatMessage, MessageFrom messageFrom) {
/* 195 */     String ownName = MainGameState.getPlayerModel().getName();
/* 196 */     SanctionManager sanctionManager = SanctionManager.getInstance();
/*     */     
/* 198 */     if (MainGameState.getFriendModel() != null) {
/* 199 */       Map<Integer, Friend> map = MainGameState.getFriendModel().getFriendsList();
/* 200 */       if (map != null) {
/* 201 */         Friend friend = map.get(Integer.valueOf(playerSourceID));
/* 202 */         if (friend != null && 
/* 203 */           friend.isBlocked().booleanValue()) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 210 */     if (sanctionManager.isAllowedToReceive())
/*     */     {
/* 212 */       if ((MessageFrom.InternalCannedCHat == messageFrom && !TcgGame.isAllowFreeChat()) || (MessageFrom.freeChat == messageFrom && TcgGame.isAllowFreeChat()) || MessageFrom.siteCasting == messageFrom) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 217 */         Map<Integer, PropNode> otherPlayers = getPlayersInView();
/* 218 */         PropNode player = otherPlayers.get(Integer.valueOf(playerSourceID));
/*     */ 
/*     */         
/* 221 */         int delimIndex = chatMessage.indexOf("#ID#");
/* 222 */         boolean canned = false;
/* 223 */         if (delimIndex > 0) {
/* 224 */           canned = true;
/* 225 */           String playerNickname = chatMessage.substring(0, delimIndex);
/*     */           
/* 227 */           Element element = getElementByMessageId(chatMessage);
/* 228 */           LinkedList<Element> parentElements = new LinkedList<Element>();
/* 229 */           parentElements.addFirst(element);
/* 230 */           getParentElements(element, parentElements);
/*     */           
/* 232 */           StringBuffer sb = new StringBuffer();
/*     */           
/* 234 */           for (Element parentElement : parentElements) {
/* 235 */             String value = parentElement.getAttributeValue("add");
/* 236 */             if (value == null || !value.equalsIgnoreCase("false")) {
/* 237 */               sb.append(parentElement.getAttributeValue("message") + " ");
/*     */             }
/*     */           } 
/*     */           
/* 241 */           if (ownName.equals(playerNickname.substring(0, playerNickname.length() - 1))) {
/* 242 */             player = otherPlayers.get(Integer.valueOf(0));
/* 243 */             chatMessage = TcgGame.getLocalizedText("chat.message.sender.me", new String[0]) + ": " + sb.toString();
/*     */           } else {
/* 245 */             chatMessage = playerNickname + " " + sb.toString();
/*     */           } 
/* 247 */           if (player != null && !chatMessage.contains("#whisper")) {
/* 248 */             this.chatUiController.createChatBubble(chatMessage, chatMessage, player);
/*     */           }
/*     */         } else {
/* 251 */           String bubbleText = chatMessage;
/* 252 */           delimIndex = chatMessage.indexOf(":");
/* 253 */           if (delimIndex > 0) {
/* 254 */             String messageName = chatMessage.substring(0, delimIndex);
/* 255 */             if (messageName.equals(ownName)) {
/* 256 */               bubbleText = TcgGame.getLocalizedText("chat.message.sender.me", new String[0]) + ": " + chatMessage.substring(delimIndex + 1);
/*     */               
/* 258 */               player = otherPlayers.get(Integer.valueOf(0));
/*     */             } else {
/* 260 */               bubbleText = chatMessage.substring(0, delimIndex) + ": " + chatMessage.substring(delimIndex + 1);
/*     */             } 
/*     */           } 
/* 263 */           if (player != null && !bubbleText.contains("#whisper") && !bubbleText.equals("reqBuddy") && !bubbleText.equals("resBuddy") && !bubbleText.equals("removeBuddy"))
/*     */           {
/*     */ 
/*     */             
/* 267 */             this.chatUiController.createChatBubble(bubbleText, bubbleText, player);
/*     */           }
/*     */         } 
/*     */         
/* 271 */         if (MainGameState.getBasicChatWindow() != null) {
/* 272 */           delimIndex = chatMessage.indexOf(":");
/* 273 */           if (delimIndex > 0) {
/* 274 */             String playerName = chatMessage.substring(0, delimIndex);
/* 275 */             chatMessage = chatMessage.substring(delimIndex + 1, chatMessage.length()).replace("\n", "");
/* 276 */             MainGameState.getBasicChatWindow().addMessage(playerName, chatMessage, canned, playerSourceID);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private LinkedList<Element> buildMessage(Element mainMenuElement, Element chatElement, LinkedList<Element> parentElements) {
/* 285 */     Element parentElement = chatElement.getParentElement();
/* 286 */     if (parentElement == null || chatElement.equals(mainMenuElement))
/* 287 */       return parentElements; 
/* 288 */     if (parentElement.equals(mainMenuElement)) {
/* 289 */       parentElements.addFirst(parentElement);
/* 290 */       return parentElements;
/*     */     } 
/* 292 */     parentElements.addFirst(parentElement);
/* 293 */     return buildMessage(mainMenuElement, parentElement, parentElements);
/*     */   }
/*     */ 
/*     */   
/*     */   private Element getElementByMessageId(String message) {
/* 298 */     int delimIndex = message.indexOf("#ID#");
/* 299 */     String messageId = message.substring(delimIndex + "#ID#".length(), message.length());
/* 300 */     short elementId = Short.parseShort(messageId);
/* 301 */     return this.elementIdMap.get(Short.valueOf(elementId));
/*     */   }
/*     */   
/*     */   private void getParentElements(Element element, LinkedList<Element> parentElements) {
/* 305 */     Element parentElement = element.getParentElement();
/* 306 */     if (parentElement != null && parentElement.getAttributeValue("message") != null) {
/* 307 */       parentElements.addFirst(parentElement);
/* 308 */       getParentElements(parentElement, parentElements);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean shouldElementBeAdded(Element outPutMessageElement) {
/* 313 */     Attribute addAttribute = outPutMessageElement.getAttribute("add");
/*     */     try {
/* 315 */       if (addAttribute == null || addAttribute.getBooleanValue()) {
/* 316 */         return true;
/*     */       }
/* 318 */     } catch (DataConversionException e) {
/* 319 */       e.printStackTrace();
/*     */     } 
/* 321 */     return false;
/*     */   }
/*     */   
/*     */   public void sendFriendRequest(int id) {
/* 325 */     this.chatOutput.sendFriendRequest(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendFriendRemove(int id) {
/* 330 */     this.chatOutput.sendFriendRemove(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeFriend(int playerIdD) {
/* 335 */     this.chatOutput.removeFriend(playerIdD);
/*     */   }
/*     */   
/*     */   public String getClientNameFromId(long friendClientid) {
/* 339 */     ChatClientsInterface clientChatUser = (ChatClientsInterface)this.chatOutput.getChatUser();
/* 340 */     Map<Long, String> map = clientChatUser.getChatClients();
/* 341 */     return map.get(Long.valueOf(friendClientid));
/*     */   }
/*     */   
/*     */   public void newFriendRequest(long friendClientid, String friendClientName) {
/* 345 */     if (friendClientName == null) {
/* 346 */       friendClientName = getClientNameFromId(friendClientid);
/*     */     }
/*     */     
/* 349 */     if (MainGameState.getFriendsWindow() != null) {
/* 350 */       MainGameState.getFriendsWindow().addFriendRequest((int)friendClientid, friendClientName);
/*     */     }
/*     */   }
/*     */   
/*     */   public ChatOutput getChatOutput() {
/* 355 */     return this.chatOutput;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendPrivateMessage(String message, int playerId, String name, String tellToNickname) {
/* 360 */     if (playerId != -1) {
/* 361 */       this.chatOutput.getChatUser().messagePrivate(playerId, String.format("%s%s: %s", new Object[] { name, MessageType.whisper.getNickname(), message }));
/*     */     } else {
/*     */       
/* 364 */       MainGameState.addMessage(TcgGame.getLocalizedText("chat.error.private.usernotfound", new String[] { tellToNickname }), MessageType.error);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendGroupMessage(long groupId, String message, byte[] extraData) {
/* 369 */     this.chatOutput.getChatUser().messageGroup(groupId, message, extraData);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\chat\ChatNetworkController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */