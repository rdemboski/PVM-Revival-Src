/*     */ package com.funcom.tcg.client.net;
/*     */ 
/*     */ import com.funcom.gameengine.Updated;
/*     */ import com.funcom.server.common.GameIOHandler;
/*     */ import com.funcom.server.common.LocalGameClient;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.NetworkConfiguration;
/*     */ import com.funcom.server.common.ServerConstants;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.state.NewLoginGameState;
/*     */ import com.funcom.tcg.client.state.TcgGameState;
/*     */ import com.funcom.tcg.client.ui.inventory.Inventory;
/*     */ import com.funcom.tcg.net.PlayerStartConfig;
/*     */ import com.funcom.tcg.net.TCGGameIOHandlerFactory;
/*     */ import com.funcom.tcg.net.message.LoginRequestMessage;
/*     */ import com.jme.util.GameTaskQueueManager;
/*     */ import com.jmex.game.state.GameState;
/*     */ import com.jmex.game.state.GameStateManager;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.nio.channels.UnresolvedAddressException;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.concurrent.Callable;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetworkHandler
/*     */   implements Updated, LocalGameClient, NetworkStateMachine
/*     */ {
/*  38 */   private static final Logger LOGGER = Logger.getLogger(NetworkHandler.class.getName());
/*     */   private static NetworkHandler INSTANCE;
/*     */   
/*     */   public static synchronized NetworkHandler instance() {
/*  42 */     if (INSTANCE == null)
/*     */       try {
/*  44 */         INSTANCE = new NetworkHandler();
/*  45 */       } catch (NetworkHandlerException e) {
/*  46 */         throw new IllegalStateException(e);
/*     */       }  
/*  48 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   private NetworkHandlerState currentState;
/*     */   private GameIOHandler ioHandler;
/*     */   
/*     */   private NetworkHandler() {
/*  55 */     setState(new NotLoggedState());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setState(NetworkHandlerState networkHandlerState) {
/*  60 */     this.currentState = networkHandlerState;
/*  61 */     this.currentState.setGameIOHandler(getIOHandler());
/*  62 */     this.currentState.setNetworkStateMachine(this);
/*     */   }
/*     */   
/*     */   public void login(String username, String password, PlayerStartConfig playerStartConfig, LoginAnswer loginAnswer) {
/*     */     try {
/*  67 */       this.currentState.login(username, password, playerStartConfig, loginAnswer);
/*  68 */     } catch (StateException e) {
/*  69 */       throw new NetworkHandlerException("Something wrong happened during login: ", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void loginWithSessionId(String username, String password, String sessionId, int uid, LoginAnswer loginAnswer) {
/*  74 */     this.currentState.login(username, password, loginAnswer, sessionId, uid, TcgGame.getPublicKey());
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyDisconnected(GameIOHandler ioHandler, final LocalGameClient.DisconnectReason disconnectReason, boolean tryReconnect) {
/*  79 */     if (this.ioHandler == ioHandler) {
/*  80 */       LOGGER.log((Priority)Level.INFO, "NetworkHandler.notifyDisconnected: reason=" + disconnectReason + " reconnect=" + tryReconnect);
/*     */       
/*  82 */       if (tryReconnect) {
/*  83 */         reconnect();
/*     */       } else {
/*  85 */         GameTaskQueueManager.getManager().update(new Callable()
/*     */             {
/*     */               public Object call() throws Exception {
/*  88 */                 for (GameState gameState : GameStateManager.getInstance().getChildren()) {
/*  89 */                   if (gameState.isActive() && gameState instanceof TcgGameState)
/*     */                   {
/*  91 */                     ((TcgGameState)gameState).notifyNetworkDisconnect(disconnectReason);
/*     */                   }
/*     */                 } 
/*  94 */                 return null;
/*     */               }
/*     */             });
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void reconnect() {
/* 102 */     LOGGER.log((Priority)Level.INFO, "NetworkHandler.reconnect");
/*     */     
/* 104 */     long reconnectWaitMillis = ServerConstants.RECONNECTION_MINIMUM_DELAY + 500L;
/* 105 */     final Timer reconnectTimer = new Timer("ReconnectTimer");
/* 106 */     reconnectTimer.schedule(new TimerTask()
/*     */         {
/*     */           public void run() {
/*     */             try {
/* 110 */               NetworkHandler.this.resetIOHandler(NetworkConfiguration.instance().getServerAddress());
/* 111 */             } catch (NetworkHandlerException ex) {
/* 112 */               NetworkHandler.this.notifyReconnectionFailed();
/*     */             } finally {
/* 114 */               reconnectTimer.cancel();
/*     */             } 
/*     */           }
/*     */         },  reconnectWaitMillis);
/*     */   }
/*     */ 
/*     */   
/*     */   private void notifyReconnectionFailed() {}
/*     */ 
/*     */   
/*     */   public void update(float time) {
/* 125 */     this.currentState.update(time);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateByTimeLimit(long nanoLimit) {
/* 130 */     this.currentState.updateByTimeLimit(nanoLimit);
/*     */   }
/*     */   
/*     */   public static void close() {
/* 134 */     if (INSTANCE != null && INSTANCE.ioHandler != null) {
/* 135 */       TcgGame.setLoggedOut(true);
/* 136 */       INSTANCE.ioHandler.stop();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public GameIOHandler getIOHandler() {
/* 142 */     return this.ioHandler;
/*     */   }
/*     */   
/*     */   public void addResponseCollectedPetsProcessor(ResponseCollectedPetsProcessor processor) {
/* 146 */     this.currentState.addResponseCollectedPetsProcessor(processor);
/*     */   }
/*     */   
/*     */   public void removeResponseCollectedPetsProcessor(ResponseCollectedPetsProcessor processor) {
/* 150 */     this.currentState.removeResponseCollectedPetsProcessor(processor);
/*     */   }
/*     */   
/*     */   public void registerForInventoryUpdate(Inventory inventory) {
/* 154 */     this.currentState.registerForInventoryUpdate(inventory);
/*     */   }
/*     */   
/*     */   public void unregisterInventoryUpdates(Inventory sourceInventory) {
/* 158 */     this.currentState.unregisterForInventoryUpdate(sourceInventory);
/*     */   }
/*     */   
/*     */   public void unregisterAllForInventoryUpdate() {
/* 162 */     this.currentState.unregisterAllForInventoryUpdate();
/*     */   }
/*     */   
/*     */   public NetworkHandlerState getCurrentState() {
/* 166 */     return this.currentState;
/*     */   }
/*     */ 
/*     */   
/*     */   public void loginDifferentServer(InetSocketAddress inetSocketAddress, String userName, String plainPassword, PlayerStartConfig startConfig) {
/* 171 */     GameIOHandler oldIOHandler = this.ioHandler;
/* 172 */     resetIOHandler(inetSocketAddress);
/* 173 */     if (!(this.currentState instanceof NotLoggedState))
/* 174 */       setState(new NotLoggedState()); 
/* 175 */     NewLoginGameState loginGameState = (NewLoginGameState)GameStateManager.getInstance().getChild("login-game-state");
/*     */     
/* 177 */     login(userName, plainPassword, startConfig, (LoginAnswer)loginGameState);
/* 178 */     TcgGame.setLoggedOut(true);
/* 179 */     oldIOHandler.stop();
/*     */   }
/*     */   
/*     */   public void switchServerWhilePlaying(InetSocketAddress inetSocketAddress, String userName, String plainPassword) {
/* 183 */     GameIOHandler oldIOHandler = this.ioHandler;
/* 184 */     resetIOHandler(inetSocketAddress);
/*     */     try {
/* 186 */       this.ioHandler.start();
/* 187 */       this.ioHandler.send((Message)new LoginRequestMessage(userName, plainPassword, false));
/* 188 */     } catch (IOException ex) {
/* 189 */       throw NetworkHandlerException.ioHandlerInitializationError(ex);
/* 190 */     } catch (InterruptedException e) {
/* 191 */       throw new RuntimeException(e);
/*     */     } 
/* 193 */     TcgGame.setLoggedOut(true);
/* 194 */     oldIOHandler.stop();
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetIOHandler(InetSocketAddress inetSocketAddress) throws NetworkHandlerException {
/* 199 */     if (this.ioHandler != null) {
/* 200 */       this.ioHandler.stopNow();
/*     */     }
/* 202 */     this.ioHandler = TCGGameIOHandlerFactory.createIOHandler(this, inetSocketAddress, TcgGame.getPublicKey());
/* 203 */     this.ioHandler.setThreadPriority(8);
/*     */     try {
/* 205 */       this.ioHandler.start();
/* 206 */       this.currentState.setGameIOHandler(this.ioHandler);
/* 207 */     } catch (IOException ex) {
/* 208 */       throw NetworkHandlerException.ioHandlerInitializationError(ex);
/* 209 */     } catch (UnresolvedAddressException ex) {
/* 210 */       throw NetworkHandlerException.ioHandlerInitializationError(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\NetworkHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */