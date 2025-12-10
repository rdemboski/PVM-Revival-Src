/*     */ package com.funcom.tcg.client;
/*     */ 
/*     */ import com.funcom.audio.SoundSystemFactory;
/*     */ import com.funcom.gameengine.model.command.Command;
/*     */ import com.funcom.gameengine.model.command.IdleCommand;
/*     */ import com.funcom.gameengine.model.token.GameTokenProcessor;
/*     */ import com.funcom.gameengine.model.token.TokenRegister;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.SoundUpdateCallable;
/*     */ import com.funcom.gameengine.utils.LoadingScreenListener;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.metrics.HttpMetrics;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.hud.LoadingWindow;
/*     */ import com.funcom.tcg.client.view.modular.ClientDescribedModularNode;
/*     */ import com.funcom.tcg.net.message.LoadingCompleteMessage;
/*     */ import com.funcom.tcg.token.TCGWorld;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jme.util.GameTaskQueueManager;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import org.lwjgl.opengl.Display;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LoadingScreenController
/*     */ {
/*     */   public static final float RESOURCE_LOADING_TIMER = 0.5F;
/*     */   public static final long GAME_TOKEN_TIMER = 5000000L;
/*  36 */   private List<LoadingScreenListener> listeners = new ArrayList<LoadingScreenListener>();
/*     */   private TCGWorld world;
/*     */   private long time;
/*     */   private boolean splashScreen = false;
/*     */   
/*     */   public void checkChunkLoadingStatus_TESTFMODCRASH(float interpolation) {
/*  42 */     if (MainGameState.getPlayerNode() != null && this.world.isFullLoading() && TokenRegister.instance().getGameQueueSize() > 0 && this.world.getChunkWorldNode().getQueuedChunksSize() > 0) {
/*     */ 
/*     */ 
/*     */       
/*  46 */       String loadMapName = this.world.getMapName();
/*     */       
/*     */       while (true) {
/*  49 */         dispatchLoadingStartedEvent(loadMapName);
/*  50 */         updateSoundSystem();
/*  51 */         dispatchLoadingFinishedEvent(loadMapName);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateSoundSystem() {
/*  58 */     int millis = 50;
/*  59 */     for (int i = 0; i < 100; i++) {
/*  60 */       if (LoadingManager.USE) {
/*  61 */         LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundUpdateCallable());
/*     */       } else {
/*     */         
/*  64 */         SoundSystemFactory.getSoundSystem().update();
/*     */       } 
/*     */       try {
/*  67 */         Thread.sleep(50L);
/*  68 */       } catch (InterruptedException ignore) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkChunkLoadingStatus(float interpolation) {
/*  74 */     if (MainGameState.getPlayerNode() != null && this.world.isFullLoading() && hasDataToLoad()) {
/*  75 */       LoadingWindow loadingWindow = ensureValidLoadingWindow();
/*     */       
/*  77 */       if (loadingWindow == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  83 */       loadingWindow.setSplashScreen(this.splashScreen);
/*     */       
/*  85 */       String loadMapName = this.world.getMapName();
/*     */ 
/*     */ 
/*     */       
/*  89 */       loadingWindow.setStartLoadingScreen(true);
/*     */       
/*  91 */       loadingWindow.loadMap(loadMapName);
/*     */       
/*  93 */       dispatchLoadingStartedEvent(loadMapName);
/*     */       
/*  95 */       int originalQueueSize = TokenRegister.instance().getGameQueueSize() + TokenRegister.instance().getOpenQueueSize();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 101 */       while (hasDataToLoad() || !LoadingManager.INSTANCE.isEmpty()) {
/*     */         
/* 103 */         if (Display.isCloseRequested()) {
/* 104 */           System.exit(0);
/*     */         }
/*     */         
/* 107 */         processLoading();
/*     */ 
/*     */         
/* 110 */         originalQueueSize = updateLoadingProgress(interpolation, loadingWindow, originalQueueSize);
/*     */         
/* 112 */         if (LoadingManager.USE) {
/* 113 */           LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundUpdateCallable());
/*     */         } else {
/*     */           
/* 116 */           SoundSystemFactory.getSoundSystem().update();
/*     */         } 
/*     */         
/* 119 */         Thread.yield();
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 124 */       this.splashScreen = false;
/* 125 */       loadingWindow.setSplashScreen(this.splashScreen);
/*     */       
/* 127 */       loadingWindow.setStartLoadingScreen(false);
/*     */       
/* 129 */       loadingWindow.dismiss();
/*     */       
/* 131 */       this.world.resetLoadingScreenNeeded();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 137 */       Spatial spatial = MainGameState.getPlayerNode().getRepresentation();
/* 138 */       ClientDescribedModularNode playerModularNode = (ClientDescribedModularNode)spatial;
/* 139 */       playerModularNode.activePetChanged();
/* 140 */       playerModularNode.reloadAll();
/*     */       
/* 142 */       setPlayerToIdleState();
/*     */       
/* 144 */       dispatchLoadingFinishedEvent(loadMapName);
/*     */       
/* 146 */       sendLoadingCompleteMessage();
/* 147 */       HttpMetrics.postEvent(HttpMetrics.Event.GAME_LOADED);
/* 148 */       if (TcgUI.isWindowOpen(LoadingWindow.class)) {
/* 149 */         loadingWindow.dismiss();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void processLoading() {
/* 156 */     GameTokenProcessor.instance().processByTimeLimit(5000000L);
/* 157 */     TcgGame.getParticleProcessor().process(0.5F);
/*     */     
/* 159 */     GameTaskQueueManager.getManager().getQueue("render").execute();
/*     */     
/* 161 */     if (LoadingManager.USE) {
/* 162 */       LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundUpdateCallable());
/*     */     }
/*     */     else {
/*     */       
/* 166 */       SoundSystemFactory.getSoundSystem().update();
/*     */     } 
/*     */ 
/*     */     
/* 170 */     if (LoadingManager.USE) {
/*     */ 
/*     */       
/* 173 */       LoadingManager.distanceLoading(false);
/*     */       
/* 175 */       if (MainGameState.getInstance() != null && MainGameState.getPlayerNode() != null && MainGameState.getPlayerModel() != null) {
/*     */ 
/*     */ 
/*     */         
/* 179 */         LoadingManager.INSTANCE.update(10000000L, 0, 0);
/*     */       
/*     */       }
/* 182 */       else if (LoadingManager.DEBUG_INFO) {
/* 183 */         System.out.printf("LoadingScreenController:LoadingManager - waiting for player model to be loaded.\n", new Object[0]);
/*     */       } 
/*     */ 
/*     */       
/* 187 */       LoadingManager.distanceLoading(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private LoadingWindow ensureValidLoadingWindow() {
/*     */     LoadingWindow loadingWindow;
/* 194 */     if (TcgUI.isWindowOpen(LoadingWindow.class)) {
/* 195 */       loadingWindow = (LoadingWindow)TcgUI.getWindowFromClass(LoadingWindow.class);
/*     */     } else {
/* 197 */       loadingWindow = MainGameState.getLoadingWindow();
/* 198 */       BuiSystem.addWindow((BWindow)loadingWindow);
/*     */     } 
/*     */     
/* 201 */     if (loadingWindow.isValid()) {
/* 202 */       return loadingWindow;
/*     */     }
/* 204 */     return null;
/*     */   }
/*     */   
/*     */   private boolean hasDataToLoad() {
/* 208 */     return (TokenRegister.instance().getGameQueueSize() > 0 || TokenRegister.instance().getOpenQueueSize() > 0 || this.world.getChunkWorldNode().getQueuedChunksSize() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setPlayerToIdleState() {
/* 215 */     MainGameState.getPlayerModel().immediateCommand((Command)new IdleCommand());
/*     */   }
/*     */   
/*     */   private void dispatchLoadingStartedEvent(String mapName) {
/* 219 */     this.time = System.nanoTime();
/* 220 */     for (LoadingScreenListener listener : this.listeners) {
/* 221 */       listener.notifyLoadingScreenStarted(mapName);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void dispatchLoadingFinishedEvent(String loadMapName) {
/* 227 */     for (LoadingScreenListener listener : this.listeners) {
/* 228 */       listener.notifyLoadingScreenFinished(loadMapName);
/*     */     }
/*     */   }
/*     */   
/*     */   private void sendLoadingCompleteMessage() {
/* 233 */     LoadingCompleteMessage loadingCompleteMessage = new LoadingCompleteMessage(MainGameState.getPlayerModel().getId());
/*     */     try {
/* 235 */       NetworkHandler.instance().getIOHandler().send((Message)loadingCompleteMessage);
/* 236 */     } catch (InterruptedException e) {
/* 237 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int updateLoadingProgress(float interpolation, LoadingWindow loadingWindow, int originalQueueSize) {
/* 244 */     int queueSizeNow = TokenRegister.instance().getGameQueueSize() + TokenRegister.instance().getOpenQueueSize();
/*     */     
/* 246 */     if (LoadingManager.USE) {
/* 247 */       queueSizeNow = (int)(queueSizeNow + LoadingManager.INSTANCE.getQueueSize());
/*     */     }
/*     */     
/* 250 */     if (queueSizeNow > originalQueueSize) {
/* 251 */       originalQueueSize = queueSizeNow;
/* 252 */       return originalQueueSize;
/*     */     } 
/*     */     
/* 255 */     float processedInFloatPercent = (originalQueueSize - queueSizeNow) / originalQueueSize;
/* 256 */     loadingWindow.setProgress(processedInFloatPercent, false);
/*     */ 
/*     */     
/* 259 */     MainGameState.getRenderPassManager().getBuiRenderPass().renderPass(DisplaySystem.getDisplaySystem().getRenderer());
/* 260 */     Display.update();
/*     */     
/* 262 */     return originalQueueSize;
/*     */   }
/*     */   
/*     */   public void addListener(LoadingScreenListener listener) {
/* 266 */     this.listeners.add(listener);
/*     */   }
/*     */   
/*     */   public void clearListeners() {
/* 270 */     this.listeners.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorld(TCGWorld world) {
/* 279 */     this.world = world;
/* 280 */     this.splashScreen = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\LoadingScreenController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */