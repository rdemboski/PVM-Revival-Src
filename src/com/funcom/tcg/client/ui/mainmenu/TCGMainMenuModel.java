/*     */ package com.funcom.tcg.client.ui.mainmenu;
/*     */ 
/*     */ import com.funcom.audio.SoundSystemFactory;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.SoundSetMuteCallable;
/*     */ import com.funcom.gameengine.view.CameraConfig;
/*     */ import com.funcom.peeler.BananaPeel;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*     */ import com.funcom.tcg.client.ui.hud.PreQuitWindow;
/*     */ import com.funcom.tcg.client.ui.hud.QuitWindow;
/*     */ import com.funcom.tcg.client.ui.pause.PauseWindow;
/*     */ import com.funcom.util.Browser;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import java.awt.Toolkit;
/*     */ import java.io.IOException;
/*     */ import java.util.concurrent.Callable;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.DisplayMode;
/*     */ 
/*     */ 
/*     */ public class TCGMainMenuModel
/*     */   implements MainMenuModel
/*     */ {
/*  32 */   private static final Logger LOG = Logger.getLogger(TCGMainMenuModel.class.getName());
/*     */   
/*     */   private OptionsWindow optionsWindow;
/*     */   
/*     */   public void cancel() {
/*  37 */     TcgUI.getUISoundPlayer().play("ClickBackward");
/*  38 */     PanelManager.getInstance().closeAll();
/*     */   }
/*     */ 
/*     */   
/*     */   public void music() {
/*  43 */     TcgUI.getUISoundPlayer().play("ClickForward");
/*  44 */     boolean muteMusic = SoundSystemFactory.getSoundSystem().isMusicMute();
/*  45 */     if (LoadingManager.USE) {
/*  46 */       boolean muteSfx = SoundSystemFactory.getSoundSystem().isSfxMute();
/*  47 */       LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundSetMuteCallable(!muteMusic, !muteSfx));
/*     */     } else {
/*  49 */       SoundSystemFactory.getSoundSystem().setMusicMute(!muteMusic);
/*  50 */       TcgGame.getPreferences().saveMusic(!muteMusic);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void sfx() {
/*  56 */     TcgUI.getUISoundPlayer().play("ClickForward");
/*  57 */     boolean muteSfx = SoundSystemFactory.getSoundSystem().isSfxMute();
/*  58 */     if (LoadingManager.USE) {
/*  59 */       boolean muteMusic = SoundSystemFactory.getSoundSystem().isMusicMute();
/*  60 */       LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundSetMuteCallable(!muteMusic, !muteSfx));
/*     */     } else {
/*  62 */       SoundSystemFactory.getSoundSystem().setSfxMute(!muteSfx);
/*  63 */       TcgGame.getPreferences().saveSound(!muteSfx);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void fullscreen() {
/*  69 */     TcgUI.getUISoundPlayer().play("ClickForward");
/*  70 */     DisplaySystem display = DisplaySystem.getDisplaySystem();
/*     */ 
/*     */ 
/*     */     
/*  74 */     display.recreateWindow(display.getWindowWidth(), display.getWindowHeight(), display.getBitDepth(), display.getFrequency(), !Display.isFullscreen());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  83 */       TcgGame.getGameSettings().setFullscreen(Display.isFullscreen());
/*  84 */       TcgGame.getGameSettings().setWidth(display.getWindowWidth());
/*  85 */       TcgGame.getGameSettings().setHeight(display.getWindowHeight());
/*  86 */       TcgGame.getGameSettings().setFrequency(display.getFrequency());
/*  87 */       TcgGame.getGameSettings().setDepth(display.getBitDepth());
/*  88 */       TcgGame.getGameSettings().save();
/*  89 */     } catch (IOException e) {
/*  90 */       LOG.error("Cannot save screen settings");
/*  91 */       e.printStackTrace();
/*     */     } 
/*  93 */     if (!TcgUI.isWindowOpen(OptionsWindow.class)) {
/*  94 */       PanelManager.getInstance().closeWindow((BWindow)this.optionsWindow);
/*     */     }
/*     */     
/*  97 */     CameraConfig.instance().updateProjection();
/*  98 */     MainGameState.getInstance().initializeMainWindow();
/*  99 */     MainGameState.getInstance().initLoadingWindow();
/* 100 */     MainGameState.getHoverInfoProvider().reset();
/*     */     
/* 102 */     if (MainGameState.getPauseModel().isPaused() && !TcgUI.isWindowOpen(PauseWindow.class)) {
/* 103 */       PauseWindow pauseWindow = new PauseWindow(TcgGame.getResourceManager(), MainGameState.getPauseModel(), false);
/* 104 */       BuiSystem.addWindow((BWindow)pauseWindow);
/* 105 */       MainGameState.getPauseModel().confirmPause();
/*     */     } 
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
/*     */   public void quitToLogin() {
/* 120 */     exit(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void quit() {
/* 125 */     exit(false);
/*     */   }
/*     */   
/*     */   private void exit(boolean quitToLogin) {
/* 129 */     if (TcgGame.isPlaying() && !MainGameState.isPlayerRegistered()) {
/*     */       
/* 131 */       BWindow existingWindow = TcgUI.getWindowFromClass(PreQuitWindow.class);
/* 132 */       if (existingWindow == null) {
/* 133 */         PreQuitWindow window = new PreQuitWindow(TcgGame.getResourceManager(), quitToLogin);
/* 134 */         window.setLayer(103);
/* 135 */         BuiSystem.getRootNode().addWindow((BWindow)window);
/*     */       } 
/*     */     } else {
/* 138 */       BWindow existingWindow = TcgUI.getWindowFromClass(QuitWindow.class);
/*     */       
/* 140 */       if (existingWindow == null) {
/* 141 */         TcgUI.getUISoundPlayer().play("ClickBackward");
/* 142 */         QuitWindow window = new QuitWindow(TcgGame.getResourceManager(), quitToLogin);
/* 143 */         window.setLayer(103);
/* 144 */         BuiSystem.getRootNode().addWindow((BWindow)window);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMusicLbl() {
/* 151 */     return TcgGame.getLocalizedText("mainmenuwindow.music", new String[0]) + ": " + (SoundSystemFactory.getSoundSystem().isMusicMute() ? TcgGame.getLocalizedText("mainmenuwindow.sound.off", new String[0]) : TcgGame.getLocalizedText("mainmenuwindow.sound.on", new String[0]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSfxLbl() {
/* 158 */     return TcgGame.getLocalizedText("mainmenuwindow.sfx", new String[0]) + ": " + (SoundSystemFactory.getSoundSystem().isSfxMute() ? TcgGame.getLocalizedText("mainmenuwindow.sound.off", new String[0]) : TcgGame.getLocalizedText("mainmenuwindow.sound.on", new String[0]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFullscreenLbl() {
/* 165 */     DisplaySystem display = DisplaySystem.getDisplaySystem();
/* 166 */     return display.isFullScreen() ? TcgGame.getLocalizedText("mainmenuwindow.windowed", new String[0]) : TcgGame.getLocalizedText("mainmenuwindow.fullscreen", new String[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void options() {
/* 172 */     TcgUI.getUISoundPlayer().play("ClickForward");
/* 173 */     if (!TcgUI.isWindowOpen(OptionsWindow.class)) {
/* 174 */       ResourceManager resourceManager = TcgGame.getResourceManager();
/* 175 */       BananaPeel bananaPeel = (BananaPeel)TcgGame.getResourceManager().getResource(BananaPeel.class, "gui/peeler/window_options.xml", CacheType.NOT_CACHED);
/*     */ 
/*     */       
/* 178 */       this.optionsWindow = new OptionsWindow("gui/peeler/window_options.xml", bananaPeel, resourceManager, null, this);
/*     */       
/* 180 */       PanelManager.getInstance().addWindow((BWindow)this.optionsWindow);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void help() {
/* 186 */     TcgUI.getUISoundPlayer().play("ClickForward");
/*     */     try {
/* 188 */       Browser.openUrl(TcgGame.getLocalizedText("helpwindow.actualweblink", new String[0]));
/* 189 */     } catch (IOException e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mainMenu() {
/* 195 */     TcgUI.getUISoundPlayer().play("ClickBackward");
/* 196 */     PanelManager.getInstance().closeAll();
/* 197 */     MainMenuWindow mainMenuWindow = new MainMenuWindow(TcgGame.getResourceManager(), this);
/* 198 */     PanelManager.getInstance().addWindow((BWindow)mainMenuWindow);
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
/*     */   public void setRes(DisplayMode resolution, boolean fullscreen) {
/* 247 */     DisplaySystem display = DisplaySystem.getDisplaySystem();
/*     */     
/* 249 */     display.recreateWindow(resolution.getWidth(), resolution.getHeight(), resolution.getBitsPerPixel(), resolution.getFrequency(), fullscreen);
/*     */ 
/*     */ 
/*     */     
/* 253 */     if (!Display.isFullscreen()) {
/*     */       
/* 255 */       int x = (Toolkit.getDefaultToolkit().getScreenSize()).width - resolution.getWidth() >> 1;
/* 256 */       int y = (Toolkit.getDefaultToolkit().getScreenSize()).height - resolution.getHeight() >> 1;
/* 257 */       Display.setLocation(x, y);
/*     */     } 
/*     */     
/*     */     try {
/* 261 */       TcgGame.getGameSettings().setFullscreen(Display.isFullscreen());
/* 262 */       TcgGame.getGameSettings().setWidth(resolution.getWidth());
/* 263 */       TcgGame.getGameSettings().setHeight(resolution.getHeight());
/* 264 */       TcgGame.getGameSettings().setFrequency(resolution.getFrequency());
/* 265 */       TcgGame.getGameSettings().setDepth(resolution.getBitsPerPixel());
/* 266 */       TcgGame.getGameSettings().save();
/* 267 */     } catch (IOException e) {
/* 268 */       LOG.error("Cannot save screen settings");
/* 269 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 272 */     CameraConfig.instance().updateProjection();
/* 273 */     MainGameState.getInstance().initializeMainWindow();
/* 274 */     MainGameState.getInstance().initLoadingWindow();
/* 275 */     MainGameState.getHoverInfoProvider().reset();
/*     */     
/* 277 */     if (MainGameState.getPauseModel().isPaused() && !TcgUI.isWindowOpen(PauseWindow.class)) {
/* 278 */       PauseWindow pauseWindow = new PauseWindow(TcgGame.getResourceManager(), MainGameState.getPauseModel(), false);
/* 279 */       BuiSystem.addWindow((BWindow)pauseWindow);
/* 280 */       MainGameState.getPauseModel().confirmPause();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\mainmenu\TCGMainMenuModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */