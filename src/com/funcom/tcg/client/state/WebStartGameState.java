/*     */ package com.funcom.tcg.client.state;
/*     */ 
/*     */ import com.funcom.server.common.LocalGameClient;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.net.LoginAnswer;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.net.NetworkHandlerException;
/*     */ import com.funcom.tcg.client.ui.login.LoginModel;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jme.util.GameTaskQueueManager;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import com.jmex.game.state.GameStateManager;
/*     */ import java.awt.Desktop;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.concurrent.Callable;
/*     */ 
/*     */ public class WebStartGameState extends TcgGameState implements LoginAnswer, LoginModel {
/*     */   public static final String STATE_NAME = "webstart-game-state";
/*     */   
/*     */   public static String getHomeURL() {
/*  26 */     String devHomeUrl = "http://casual2.funcom.com";
/*  27 */     String liveHomeUrl = "http://pvm.funcom.com";
/*  28 */     boolean liveLaunch = Boolean.parseBoolean(System.getProperty("live", "true"));
/*  29 */     return liveLaunch ? "http://pvm.funcom.com" : "http://casual2.funcom.com";
/*     */   }
/*     */   
/*     */   private URI url;
/*     */   private String cid;
/*     */   private String sid;
/*     */   private int uid;
/*     */   
/*     */   public WebStartGameState(String cid, String sid, int uid) {
/*  38 */     this.cid = cid;
/*  39 */     this.sid = sid;
/*  40 */     this.uid = uid;
/*     */     
/*     */     try {
/*  43 */       this.url = new URI(getHomeURL());
/*  44 */     } catch (URISyntaxException e) {
/*  45 */       throw new RuntimeException(e.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  51 */     return "webstart-game-state";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initialize() {
/*  56 */     loginCommitted(this.cid, this.sid);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanup() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void activated() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deactivated() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyNetworkDisconnect(LocalGameClient.DisconnectReason reason) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(float v) {}
/*     */ 
/*     */   
/*     */   public void render(float v) {
/*  81 */     DisplaySystem.getDisplaySystem().getRenderer().draw((Spatial)BuiSystem.getRootNode());
/*     */   }
/*     */ 
/*     */   
/*     */   public void loginSuccesful(int clientIdentity) {
/*  86 */     GameTaskQueueManager.getManager().update(new Callable() {
/*     */           public Object call() throws Exception {
/*  88 */             GameStateManager.getInstance().deactivateChildNamed("webstart-game-state");
/*  89 */             GameStateManager.getInstance().activateChildNamed("main-game-state");
/*  90 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void loginFailed(String reason) {
/*  97 */     redirectUserToWebPage();
/*     */   }
/*     */ 
/*     */   
/*     */   public void timeout() {
/* 102 */     redirectUserToWebPage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void retryDifferentServer(InetSocketAddress socketAddres) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void loginFailedNeedCharacter() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDefaultUsername() {
/* 117 */     return TcgGame.getPreferences().loadUserName();
/*     */   }
/*     */ 
/*     */   
/*     */   public void loginCommitted(String cid, String sessionId) {
/*     */     try {
/* 123 */       NetworkHandler.instance().loginWithSessionId(cid, "", sessionId, this.uid, this);
/* 124 */       BuiSystem.getRootNode().addWindow((BWindow)MainGameState.getLoadingWindow());
/* 125 */     } catch (NetworkHandlerException e) {
/* 126 */       redirectUserToWebPage();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loginCancelled() {}
/*     */ 
/*     */   
/*     */   public void redirectUserToWebPage() {
/*     */     try {
/* 136 */       Desktop.getDesktop().browse(this.url);
/* 137 */     } catch (IOException e) {
/* 138 */       throw new RuntimeException(e.toString());
/*     */     } 
/*     */     
/* 141 */     NetworkHandler.instance().getIOHandler().stopNow();
/* 142 */     TcgGame.finishGame();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\state\WebStartGameState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */