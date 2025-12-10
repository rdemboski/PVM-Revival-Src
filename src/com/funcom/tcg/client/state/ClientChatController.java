/*     */ package com.funcom.tcg.client.state;
/*     */ 
/*     */ import com.funcom.gameengine.conanchat.ChatClient;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.conanchat.MessageType;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.hud.NoChatServerWindow;
/*     */ import com.jme.scene.Controller;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import java.io.IOException;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ public class ClientChatController extends Controller {
/*  19 */   private static final Logger LOGGER = Logger.getLogger(ClientChatController.class.getName());
/*     */   
/*     */   private ChatClient chatClient;
/*     */   
/*     */   private boolean enabled;
/*     */   private boolean activated;
/*     */   private Timer pingTask;
/*  26 */   private final int PING_DELAY = 1800000;
/*  27 */   private final int PING_INIT_DELAY = 1800000;
/*     */   
/*     */   public ClientChatController(ChatClient chatClient) {
/*  30 */     this.activated = false;
/*  31 */     if (chatClient == null) {
/*  32 */       LOGGER.log((Priority)Level.ERROR, "Failure in creating chat client: chatclient = null, disabling until server reinitiates");
/*  33 */       this.enabled = false;
/*     */       return;
/*     */     } 
/*  36 */     this.chatClient = chatClient;
/*  37 */     this.enabled = true;
/*     */     
/*  39 */     this.pingTask = new Timer();
/*  40 */     this.pingTask.scheduleAtFixedRate(new PingTask(this), 1800000L, 1800000L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(float time) {
/*     */     try {
/*  47 */       if (this.enabled && this.activated)
/*  48 */         this.chatClient.update(); 
/*  49 */     } catch (IllegalStateException e) {
/*  50 */       handleChatExceptions(e);
/*     */     }
/*  52 */     catch (IOException e) {
/*  53 */       handleChatExceptions(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void showNoChatDialog() {
/*  58 */     BWindow existingWindow = TcgUI.getWindowFromClass(NoChatServerWindow.class);
/*  59 */     if (existingWindow == null) {
/*  60 */       NoChatServerWindow window = new NoChatServerWindow(TcgGame.getResourceManager(), false);
/*  61 */       window.setLayer(103);
/*  62 */       BuiSystem.getRootNode().addWindow((BWindow)window);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void enableUpdates() {
/*  67 */     this.enabled = true;
/*     */   }
/*     */   
/*     */   public void activate() {
/*  71 */     this.activated = true;
/*     */   }
/*     */   
/*     */   public ChatClient getChatClient() {
/*  75 */     return this.chatClient;
/*     */   }
/*     */   
/*     */   private void handleChatExceptions(Exception e) {
/*  79 */     LOGGER.log((Priority)Level.ERROR, "Failure in updating chat client, disabling until server reinitiates...", e);
/*  80 */     this.enabled = false;
/*     */     
/*  82 */     MainGameState.addMessage(TcgGame.getLocalizedText("dialog.chat.disabled", new String[0]), MessageType.error);
/*     */   }
/*     */   
/*     */   public boolean isChatControllerEnabled() {
/*  86 */     return this.enabled;
/*     */   }
/*     */ 
/*     */   
/*     */   class PingTask
/*     */     extends TimerTask
/*     */   {
/*     */     private ClientChatController clientChatController;
/*     */ 
/*     */     
/*     */     PingTask(ClientChatController clientChatController) {
/*  97 */       this.clientChatController = clientChatController;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 102 */       if (this.clientChatController.isChatControllerEnabled())
/* 103 */         this.clientChatController.getChatClient().ping(); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\state\ClientChatController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */