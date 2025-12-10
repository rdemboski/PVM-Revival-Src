/*     */ package com.funcom.tcg.client.net;
/*     */ 
/*     */ import com.funcom.commons.utils.GlobalTime;
/*     */ import com.funcom.server.common.GameIOHandler;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.NetworkConfiguration;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.state.NewLoginGameState;
/*     */ import com.funcom.tcg.net.PlayerStartConfig;
/*     */ import com.funcom.tcg.net.message.LoginRequestMessage;
/*     */ import com.funcom.tcg.net.message.LoginResponseMessage;
/*     */ import com.funcom.tcg.net.message.RedirectMessage;
/*     */ import com.funcom.util.DebugManager;
/*     */ import com.jmex.game.state.GameState;
/*     */ import com.jmex.game.state.GameStateManager;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.security.PublicKey;
/*     */ import java.util.Queue;
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
/*     */ public class NotLoggedState
/*     */   extends AbstractNetworkHandlerState
/*     */ {
/*  32 */   public static final String STATE_NAME = NotLoggedState.class.getSimpleName();
/*     */   private int timeoutLimitMillis;
/*     */   private boolean allowRedirect = true;
/*     */   
/*     */   public NotLoggedState() {
/*  37 */     super(STATE_NAME);
/*  38 */     this.timeoutLimitMillis = 100000;
/*     */   }
/*     */   
/*     */   public int getTimeoutLimitMillis() {
/*  42 */     return this.timeoutLimitMillis;
/*     */   }
/*     */   
/*     */   public void setTimeoutLimitMillis(int timeoutLimitMillis) {
/*  46 */     this.timeoutLimitMillis = timeoutLimitMillis;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void login(String username, String password, PlayerStartConfig playerStartConfig, LoginAnswer loginAnswer) throws StateException {
/*  53 */     if (loginAnswer == null) {
/*  54 */       throw new IllegalArgumentException("loginAnswer = null");
/*     */     }
/*     */     try {
/*  57 */       LoginRequestMessage loginRequestMessage = new LoginRequestMessage(username, password, playerStartConfig, this.allowRedirect);
/*     */ 
/*     */ 
/*     */       
/*  61 */       if (DebugManager.getInstance().isDebugEnabled()) {
/*     */         
/*  63 */         String pass = "HASHED: \"" + loginRequestMessage.getPassword() + "\"";
/*  64 */         for (GameState state : GameStateManager.getInstance().getChildren()) {
/*  65 */           if (state instanceof NewLoginGameState) {
/*  66 */             pass = pass + " NORMAL:\"" + ((NewLoginGameState)state).getMenuModel().getPassword() + "\"";
/*     */           }
/*     */         } 
/*  69 */         TcgGame.LOGGER.info("Logging in with username: \"" + loginRequestMessage.getNick() + "\"| password(s): " + pass);
/*     */       } 
/*     */       
/*  72 */       getIoHandler().send((Message)loginRequestMessage);
/*  73 */       this.allowRedirect = false;
/*  74 */     } catch (InterruptedException ex) {
/*  75 */       throw new StateException(ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     Thread responseWaitingThread = new Thread(new LoginResponseWaiting(getIoHandler(), loginAnswer, getNetworkStateMachine(), this.timeoutLimitMillis), "Waiting-for-login-response");
/*  87 */     responseWaitingThread.setDaemon(true);
/*  88 */     responseWaitingThread.start();
/*     */   }
/*     */   
/*     */   public void login(String username, String password, LoginAnswer loginAnswer, String sessionId, int userId, PublicKey publicKey) throws StateException {
/*  92 */     if (loginAnswer == null) {
/*  93 */       throw new IllegalArgumentException("loginAnswer = null");
/*     */     }
/*     */     try {
/*  96 */       LoginRequestMessage message = new LoginRequestMessage(username, password, sessionId, userId, this.allowRedirect);
/*     */       
/*  98 */       getIoHandler().send((Message)message);
/*  99 */       this.allowRedirect = false;
/* 100 */     } catch (InterruptedException ex) {
/* 101 */       throw new StateException(ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     Thread responseWaitingThread = new Thread(new LoginResponseWaiting(getIoHandler(), loginAnswer, getNetworkStateMachine(), this.timeoutLimitMillis), "Waiting-for-login-response");
/* 113 */     responseWaitingThread.setDaemon(true);
/* 114 */     responseWaitingThread.start();
/*     */   }
/*     */   
/*     */   private class LoginResponseWaiting implements Runnable {
/*     */     private LoginAnswer loginAnswer;
/*     */     private GameIOHandler ioHandler;
/*     */     private NetworkStateMachine networkStateMachine;
/*     */     private long timeoutMillis;
/*     */     
/*     */     private LoginResponseWaiting(GameIOHandler ioHandler, LoginAnswer loginAnswer, NetworkStateMachine networkStateMachine, long timeoutMillis) {
/* 124 */       this.loginAnswer = loginAnswer;
/* 125 */       this.ioHandler = ioHandler;
/* 126 */       this.networkStateMachine = networkStateMachine;
/* 127 */       this.timeoutMillis = timeoutMillis;
/*     */     }
/*     */     
/*     */     public void run() {
/* 131 */       Message response = waitForLoginResponse();
/*     */       
/* 133 */       if (response == null) {
/* 134 */         this.loginAnswer.timeout();
/*     */       }
/* 136 */       else if (response instanceof LoginResponseMessage) {
/* 137 */         handleLoginResponseMessage((LoginResponseMessage)response);
/* 138 */         if (DebugManager.getInstance().isDebugEnabled()) {
/* 139 */           LoginResponseMessage loginResponseMessage = (LoginResponseMessage)response;
/* 140 */           TcgGame.LOGGER.info("LOGIN RESULT: " + loginResponseMessage.getResult() + " :::: Login Response: " + loginResponseMessage.printResponse());
/*     */         } 
/* 142 */       } else if (response instanceof RedirectMessage) {
/* 143 */         handleRedirectMessage((RedirectMessage)response);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void handleRedirectMessage(RedirectMessage redirectMessage) {
/* 149 */       InetSocketAddress newServer = redirectMessage.getNewServer();
/* 150 */       if (newServer.getPort() != NetworkConfiguration.instance().getPort())
/* 151 */         newServer = new InetSocketAddress(newServer.getAddress(), NetworkConfiguration.instance().getPort()); 
/* 152 */       this.loginAnswer.retryDifferentServer(newServer);
/*     */     }
/*     */     
/*     */     private void handleLoginResponseMessage(LoginResponseMessage responseMessage) {
/* 156 */       if (!responseMessage.isOK()) {
/* 157 */         LoginResponseMessage.ResponseResult responseResult = responseMessage.getResult();
/* 158 */         switch (responseResult) {
/*     */           case ERROR_USER_PWD:
/* 160 */             this.loginAnswer.loginFailed("loginwindow.error.userpwd.text");
/*     */             break;
/*     */           case ERROR_NEED_CREATE_CHARACTER:
/* 163 */             this.loginAnswer.loginFailedNeedCharacter();
/*     */             break;
/*     */           case ERROR_SYSTEM_ERROR:
/* 166 */             this.loginAnswer.loginFailed("loginwindow.error.system.text");
/*     */             break;
/*     */           case ERROR_INVALID_SESSIONID:
/* 169 */             this.loginAnswer.loginFailed("loginwindow.error.invalidsession.text");
/*     */             break;
/*     */           case ERROR_BANNED:
/* 172 */             this.loginAnswer.loginFailed("loginwindow.error.banned.text");
/*     */             break;
/*     */           case ERROR_CREATION_DISABLED:
/* 175 */             this.loginAnswer.loginFailed("loginwindow.error.creationdisabled.text");
/*     */             break;
/*     */         } 
/*     */       } else {
/* 179 */         this.networkStateMachine.setState(new GameOperationState());
/* 180 */         TcgGame.setLoginResponse(responseMessage);
/* 181 */         TcgGame.setServerDomain(responseMessage.getServerDomain());
/* 182 */         this.loginAnswer.loginSuccesful(responseMessage.getAcceptedId().intValue());
/*     */       } 
/*     */     }
/*     */     
/*     */     private Message waitForLoginResponse() {
/* 187 */       long timeoutStart = GlobalTime.getInstance().getCurrentTime();
/*     */       
/* 189 */       while (!isTimeout(timeoutStart)) {
/* 190 */         Queue<Message> inputMessages = this.ioHandler.getInputMessages();
/* 191 */         Message message = inputMessages.poll();
/* 192 */         if (message == null) {
/*     */           try {
/* 194 */             Thread.sleep(50L);
/* 195 */           } catch (InterruptedException ignore) {}
/*     */           
/*     */           continue;
/*     */         } 
/* 199 */         if (!(message instanceof LoginResponseMessage) && !(message instanceof RedirectMessage))
/*     */         {
/* 201 */           throw new RuntimeException("Illegal message type at login time: " + message.getClass().getName() + ", tostring: " + message);
/*     */         }
/* 203 */         return message;
/*     */       } 
/* 205 */       return null;
/*     */     }
/*     */     
/*     */     private boolean isTimeout(long timeoutStart) {
/* 209 */       return (GlobalTime.getInstance().getCurrentTime() - timeoutStart > this.timeoutMillis);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\NotLoggedState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */