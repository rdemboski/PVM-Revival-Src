/*     */ package com.funcom.tcg.client.conanchat;
/*     */ 
/*     */ import com.funcom.gameengine.conanchat.ChatClient;
/*     */ import com.funcom.gameengine.conanchat.DefaultChatUser;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.chat.ChatOutput;
/*     */ import com.funcom.tcg.net2.NetworkInterfaceUtil;
/*     */ import com.funcom.tcg.token.TCGWorld;
/*     */ import java.net.InetAddress;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConanChatOutput
/*     */   implements ChatOutput
/*     */ {
/*  22 */   private static final Pattern COMMAND_WITH_ARGS_REGEX = Pattern.compile("^([a-zA-Z0-9]+?):/(.+?) ([a-zA-Z0-9]+?) (.+?)$");
/*     */   
/*     */   private static final int REGEXGROUP_NAME = 1;
/*     */   
/*     */   private static final int REGEXGROUP_COMMAND = 2;
/*     */   private static final int REGEXGROUP_ARGS = 3;
/*     */   private static final int REGEXGROUP_MESSAGE = 4;
/*     */   private ChatClient chatClient;
/*     */   
/*     */   public ConanChatOutput(ChatClient chatClient) {
/*  32 */     if (chatClient == null)
/*  33 */       throw new IllegalArgumentException("chatClient = null"); 
/*  34 */     this.chatClient = chatClient;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendMessage(int playerSourceID, String message) {
/*  39 */     Matcher commandArgsMatcher = COMMAND_WITH_ARGS_REGEX.matcher(message);
/*     */     
/*  41 */     if (commandArgsMatcher.matches()) {
/*  42 */       processCommand(commandArgsMatcher.group(1), commandArgsMatcher.group(2), commandArgsMatcher.group(3), commandArgsMatcher.group(4));
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/*  48 */       sendMessage(message);
/*     */     } 
/*     */   }
/*     */   private void processCommand(String name, String command, String args, String message) {
/*  52 */     if ("tell".equals(command)) {
/*  53 */       PropNode pc = TcgGame.getPropNodeRegister().getPropNodeByName(args);
/*  54 */       if (pc != null) {
/*  55 */         long id = ((ClientPlayer)pc.getProp()).getExternalChatId();
/*  56 */         MainGameState.getChatNetworkController().sendPrivateMessage(message, (int)id, name, args);
/*     */         
/*     */         return;
/*     */       } 
/*  60 */       MainGameState.getChatNetworkController().searchPlayerToTellRequest(args, message, name);
/*     */       return;
/*     */     } 
/*  63 */     MainGameState.addMessage(TcgGame.getLocalizedText("chat.error.unknowncommand", new String[] { command }), MessageType.error);
/*     */   }
/*     */   
/*     */   private void sendMessage(String message) {
/*  67 */     InetAddress inetAddress = NetworkHandler.instance().getIOHandler().getInetAddress();
/*  68 */     if (inetAddress == null) {
/*     */       return;
/*     */     }
/*  71 */     String mapId = ((TCGWorld)MainGameState.getWorld()).getChunkWorldNode().getChunkWorldInfo().getMapId();
/*  72 */     int mapInstanceId = ((TCGWorld)MainGameState.getWorld()).getChunkWorldNode().getMapInstanceId();
/*  73 */     int socketPort = NetworkHandler.instance().getIOHandler().getTargetPort();
/*     */     
/*  75 */     if ("127.0.0.1".equals(inetAddress.getHostName()) || "localhost".equals(inetAddress.getHostName())) {
/*  76 */       inetAddress = NetworkInterfaceUtil.getRealInetAddressForLocalHost();
/*     */     }
/*     */     
/*  79 */     Long groupId = getGroupId(mapId, Integer.valueOf(mapInstanceId), inetAddress, socketPort);
/*     */     
/*  81 */     MainGameState.getChatNetworkController().sendGroupMessage(groupId.longValue(), message, new byte[0]);
/*     */   }
/*     */   
/*     */   public void sendFriendRequest(int id) {
/*  85 */     this.chatClient.getChatUser().messageFriendRequest(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendFriendRemove(int id) {
/*  90 */     this.chatClient.getChatUser().messageFriendRemove(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendFriendResponse(int id) {
/*  95 */     this.chatClient.getChatUser().messageFriendResponse(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFriend(int id) {
/* 100 */     this.chatClient.getChatUser().addFriend(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public DefaultChatUser getChatUser() {
/* 105 */     return this.chatClient.getChatUser();
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeFriend(int id) {
/* 110 */     this.chatClient.getChatUser().removeFriend(id);
/*     */   }
/*     */   
/*     */   private Long getGroupId(String mapId, Integer mapInstanceId, InetAddress inetAddress, int socketPort) {
/* 114 */     String mapKey = String.format("%s_%s_%s_%s", new Object[] { mapId, mapInstanceId.toString(), inetAddress.getHostAddress(), Integer.valueOf(socketPort) });
/* 115 */     return Long.valueOf(mapKey.hashCode());
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\conanchat\ConanChatOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */