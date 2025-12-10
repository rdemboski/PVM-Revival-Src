/*     */ package com.funcom.tcg.client.ui.startmenu;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceDownloader;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.peeler.BananaPeel;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.TcgJme;
/*     */ import com.funcom.tcg.client.metrics.HttpMetrics;
/*     */ import com.funcom.tcg.client.model.rpg.VisualRegistry;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.Localizer;
/*     */ import com.funcom.tcg.client.ui.TCGDialog;
/*     */ import com.funcom.tcg.client.ui.comic.ComicWindow;
/*     */ import com.funcom.tcg.client.ui.comic.ComicWindowModel;
/*     */ import com.funcom.tcg.client.ui.hud2.ResourceDownloadWindow;
/*     */ import com.funcom.tcg.client.ui.mainmenu.OptionsWindow;
/*     */ import com.funcom.util.Browser;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BRootNode;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.io.IOException;
/*     */ import javax.swing.SwingUtilities;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.DisplayMode;
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
/*     */ public class StartMenuWizard
/*     */   implements StartMenuListener
/*     */ {
/*     */   private final StartMenuModel menuModel;
/*     */   private LoginMethodWindow loginMethodWindow;
/*     */   private LoginWindow loginWindow;
/*     */   private EulaWindow eulaWindow;
/*  55 */   private OptionsWindow optionsWindow = null;
/*  56 */   private CharacterCreatorWindow characterCreatorWindow = null;
/*     */   private StartMenuStartGameListener startGameListener;
/*  58 */   private ComicWindow comicWindow = null;
/*     */   
/*     */   private ResourceManager resourceManager;
/*     */   
/*     */   private VisualRegistry visualRegistry;
/*     */   
/*     */   private Localizer localizer;
/*     */   private BRootNode bRootNode;
/*     */   private BWindow currentWindow;
/*     */   private String startMapId;
/*     */   private ResourceDownloadWindow resourceDownloadWindow;
/*  69 */   private Rectangle backButtonSize = new Rectangle(10, 10, 54, 54);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StartMenuWizard(StartMenuModel menuModel, StartMenuStartGameListener startGameListener, ResourceManager resourceManager, VisualRegistry visualRegistry, Localizer localizer, BRootNode bRootNode) {
/*  75 */     this.menuModel = menuModel;
/*  76 */     this.startGameListener = startGameListener;
/*  77 */     this.resourceManager = resourceManager;
/*  78 */     this.visualRegistry = visualRegistry;
/*  79 */     this.localizer = localizer;
/*  80 */     this.bRootNode = bRootNode;
/*     */ 
/*     */     
/*  83 */     TcgGame.LOGGER.info("Initializing Start windows - starting");
/*  84 */     initializeWindows();
/*  85 */     TcgGame.LOGGER.info("Initializing Start windows - complete");
/*     */     
/*  87 */     SwingUtilities.invokeLater(new Runnable()
/*     */         {
/*     */           public void run() {
/*  90 */             if (TcgJme.getSplash() != null) {
/*  91 */               TcgJme.getSplash().setDismissState(true);
/*  92 */               TcgJme.getSplash().setVisible(false);
/*  93 */               TcgJme.getSplash().dispose();
/*  94 */               HttpMetrics.postEvent(HttpMetrics.Event.SPLASH_DISPOSED);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initializeWindows() {
/* 103 */     this.loginMethodWindow = new LoginMethodWindow(this.menuModel, this, this.startGameListener, this.resourceManager, this.localizer);
/* 104 */     this.loginWindow = new LoginWindow(this.menuModel, this, this.startGameListener, this.resourceManager);
/* 105 */     this.eulaWindow = new EulaWindow(this, this.resourceManager);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startWizard() {
/* 110 */     this.currentWindow = this.loginMethodWindow;
/* 111 */     this.bRootNode.addWindow(this.currentWindow);
/*     */     
/* 113 */     HttpMetrics.postEvent(HttpMetrics.Event.MAIN_MENU_LOADED);
/*     */ 
/*     */     
/* 116 */     if (System.getProperty("tcg.skiplogin") == null || System.getProperty("tcg.skiplogin").equals("true"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopWizard() {
/* 123 */     if (this.currentWindow != null) {
/* 124 */       this.bRootNode.removeWindow(this.currentWindow);
/* 125 */       this.currentWindow = null;
/*     */     } 
/*     */     
/* 128 */     if (this.resourceDownloadWindow != null) {
/* 129 */       this.bRootNode.removeWindow((BWindow)this.resourceDownloadWindow);
/*     */     }
/*     */   }
/*     */   
/*     */   private void stepTo(BWindow bWindow) {
/* 134 */     this.currentWindow.setVisible(false);
/* 135 */     this.bRootNode.removeWindow(this.currentWindow);
/* 136 */     this.bRootNode.addWindow(bWindow);
/* 137 */     this.currentWindow = bWindow;
/* 138 */     this.currentWindow.setVisible(true);
/*     */     
/* 140 */     if (this.resourceDownloadWindow != null) {
/* 141 */       this.bRootNode.removeWindow((BWindow)this.resourceDownloadWindow);
/* 142 */       this.bRootNode.addWindow((BWindow)this.resourceDownloadWindow);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void showNewPlayerWizard() {
/* 147 */     stepTo(this.eulaWindow);
/* 148 */     startMapDownloading();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void eulaAccepted() {
/* 154 */     HttpMetrics.postEvent(HttpMetrics.Event.ACCEPT_TOS);
/* 155 */     if (this.comicWindow == null)
/* 156 */       this.comicWindow = new ComicWindow(new ComicWindowModel(this.resourceManager, this), this, this.resourceManager, this.localizer); 
/* 157 */     stepTo((BWindow)this.comicWindow);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void startMapDownloading() {
/* 165 */     ResourceDownloader downloader = TcgGame.getResourceDownloader();
/* 166 */     if (downloader != null) {
/* 167 */       this.resourceDownloadWindow = new ResourceDownloadWindow(this.resourceManager);
/* 168 */       this.resourceDownloadWindow.setLocation(10, DisplaySystem.getDisplaySystem().getHeight() - 40 - this.resourceDownloadWindow.getHeight());
/*     */       
/* 170 */       this.bRootNode.addWindow((BWindow)this.resourceDownloadWindow);
/* 171 */       downloader.downloadMapAndDependencies(this.startMapId + ".jar");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void eulaDeclined() {
/* 177 */     TCGDialog.showMessage("eulawindow.error.pleaseaccept.title", "eulawindow.error.pleaseaccept.text", this.localizer, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void forgotPasswordForm() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playerDataAccepted() {
/* 190 */     this.startGameListener.loginByCreation();
/*     */   }
/*     */ 
/*     */   
/*     */   public void characterVisualsCreated() {}
/*     */ 
/*     */   
/*     */   public void showLogin() {
/* 198 */     stepTo(this.loginWindow);
/*     */   }
/*     */   
/*     */   public void showCharacterCreation() {
/* 202 */     if (this.characterCreatorWindow == null) {
/* 203 */       BananaPeel bananaPeel = (BananaPeel)this.resourceManager.getResource(BananaPeel.class, "gui/peeler/window_char_creation.xml", CacheType.NOT_CACHED);
/*     */       
/* 205 */       this.characterCreatorWindow = new CharacterCreatorWindow("gui/peeler/window_char_creation.xml", bananaPeel, this.resourceManager, this.menuModel, this.visualRegistry, this, this.startGameListener);
/*     */     } 
/*     */ 
/*     */     
/* 209 */     stepTo((BWindow)this.characterCreatorWindow);
/* 210 */     HttpMetrics.postEvent(HttpMetrics.Event.CHARACTER_CREATION);
/*     */   }
/*     */ 
/*     */   
/*     */   public void eulaBack() {
/* 215 */     stepTo(this.loginMethodWindow);
/*     */   }
/*     */ 
/*     */   
/*     */   public void eulaTos() {
/*     */     try {
/* 221 */       Browser.openUrl(TcgGame.getLocalizedText("text.window.legal.toslink", new String[0]));
/* 222 */     } catch (IOException e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void eulaPP() {
/*     */     try {
/* 230 */       Browser.openUrl(TcgGame.getLocalizedText("text.window.legal.pplink", new String[0]));
/* 231 */     } catch (IOException e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void showOptions() {
/* 238 */     BananaPeel bananaPeel = (BananaPeel)this.resourceManager.getResource(BananaPeel.class, "gui/peeler/window_options.xml", CacheType.NOT_CACHED);
/* 239 */     this.optionsWindow = new OptionsWindow("gui/peeler/window_options.xml", bananaPeel, this.resourceManager, this, null);
/* 240 */     stepTo((BWindow)this.optionsWindow);
/*     */   }
/*     */ 
/*     */   
/*     */   public void optionsBack() {
/* 245 */     stepTo(this.loginMethodWindow);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loginBack() {
/* 250 */     stepTo(this.loginMethodWindow);
/*     */   }
/*     */ 
/*     */   
/*     */   public void comicBack() {
/* 255 */     stepTo(this.eulaWindow);
/*     */   }
/*     */ 
/*     */   
/*     */   public void characterCreationBack() {
/* 260 */     stepTo((BWindow)this.comicWindow);
/*     */   }
/*     */ 
/*     */   
/*     */   public void playerDataBack() {
/* 265 */     stepTo((BWindow)this.characterCreatorWindow);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStartMap(String startMapId) {
/* 270 */     this.startMapId = startMapId;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rectangle getBackButtonSize() {
/* 275 */     return this.backButtonSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRes(DisplayMode resolution, boolean fullscreen) {
/* 280 */     DisplaySystem display = DisplaySystem.getDisplaySystem();
/*     */     
/* 282 */     display.recreateWindow(resolution.getWidth(), resolution.getHeight(), resolution.getBitsPerPixel(), resolution.getFrequency(), fullscreen);
/*     */ 
/*     */ 
/*     */     
/* 286 */     if (!Display.isFullscreen()) {
/*     */       
/* 288 */       int x = (Toolkit.getDefaultToolkit().getScreenSize()).width - resolution.getWidth() >> 1;
/* 289 */       int y = (Toolkit.getDefaultToolkit().getScreenSize()).height - resolution.getHeight() >> 1;
/* 290 */       Display.setLocation(x, y);
/*     */     } 
/*     */     
/*     */     try {
/* 294 */       TcgGame.getGameSettings().setFullscreen(Display.isFullscreen());
/* 295 */       TcgGame.getGameSettings().setWidth(resolution.getWidth());
/* 296 */       TcgGame.getGameSettings().setHeight(resolution.getHeight());
/* 297 */       TcgGame.getGameSettings().setFrequency(resolution.getFrequency());
/* 298 */       TcgGame.getGameSettings().setDepth(resolution.getBitsPerPixel());
/* 299 */       TcgGame.getGameSettings().save();
/* 300 */     } catch (IOException e) {
/* 301 */       e.printStackTrace();
/*     */     } 
/* 303 */     initializeWindows();
/* 304 */     MainGameState.getInstance().initLoadingWindow();
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
/*     */   public void update(float tpf) {
/* 341 */     if (this.resourceDownloadWindow != null) {
/* 342 */       this.resourceDownloadWindow.update(tpf);
/*     */     }
/*     */   }
/*     */   
/*     */   public void killWizard() {
/* 347 */     if (this.bRootNode.getAllWindows().contains(this.characterCreatorWindow))
/* 348 */       this.characterCreatorWindow.dismiss(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\startmenu\StartMenuWizard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */