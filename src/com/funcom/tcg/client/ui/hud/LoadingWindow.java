/*     */ package com.funcom.tcg.client.ui.hud;
/*     */ 
/*     */ import com.funcom.commons.jme.bui.PartiallyNotInteractive;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceDownloader;
/*     */ import com.funcom.gameengine.resourcemanager.downloader.ResourceDownloaderListener;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.maps.LoadingScreenPropertyReader;
/*     */ import com.funcom.util.RateValue;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jme.util.GameTaskQueueManager;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BProgressBar;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.layout.VGroupLayout;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Locale;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.Callable;
/*     */ 
/*     */ public class LoadingWindow
/*     */   extends BWindow implements ResourceDownloaderListener, PartiallyNotInteractive {
/*     */   private static final float FADE_SPEED = 0.5F;
/*     */   private BProgressBar progress;
/*     */   private BProgressBar downloadProgress;
/*     */   private boolean downloading = false;
/*     */   private boolean verifying = false;
/*     */   private String fileName;
/*     */   private NumberFormat numberFormat;
/*     */   private BContainer downloadContainer;
/*     */   private BLabel textWaitLabel;
/*     */   private BLabel textTipLabel;
/*  42 */   private BLabel[] loadingPets = new BLabel[11];
/*     */   
/*  44 */   private DismissState dismissState = DismissState.OPEN;
/*     */   
/*     */   private long dismissStartAt;
/*     */   
/*     */   private RateValue fps;
/*     */   private RateValue fpsAverage;
/*  50 */   private Random randomGenerator = new Random();
/*     */   
/*  52 */   LoadingScreenPropertyReader loadingScreenPropertyReader = MainGameState.getLoadingScreenPropertyReader();
/*     */   
/*     */   public boolean splashScreen = false;
/*     */   
/*     */   public boolean startLoadingScreen = false;
/*  57 */   private int randomPet = 0; private int totalDownloadFiles; private int minDownloadFiles;
/*     */   private int currentFile;
/*     */   
/*     */   public LoadingWindow() {
/*  61 */     super(LoadingWindow.class.getSimpleName(), null, (BLayoutManager)new AbsoluteLayout());
/*     */     
/*  63 */     BananaResourceProvider buiResourceProvider = new BananaResourceProvider(TcgGame.getResourceManager());
/*  64 */     this._style = BuiUtils.createMergedClassStyleSheets(getClass(), buiResourceProvider);
/*     */     
/*  66 */     setStyleClass("loadingscreen");
/*     */     
/*  68 */     setSize(DisplaySystem.getDisplaySystem().getWidth(), DisplaySystem.getDisplaySystem().getHeight());
/*     */     
/*  70 */     setLocation(0, 0);
/*  71 */     setLayer(100);
/*     */     
/*  73 */     createProgressArea();
/*  74 */     createDownloadProgressor();
/*  75 */     createWaitMessageContainer();
/*  76 */     createTipMessageContainer();
/*     */     
/*  78 */     setProgressEnabled(true);
/*     */     
/*  80 */     if (TcgGame.getResourceDownloader() != null) {
/*  81 */       TcgGame.getResourceDownloader().addListener(this);
/*  82 */       this.numberFormat = (NumberFormat)NumberFormat.getInstance(Locale.US).clone();
/*  83 */       this.numberFormat.setMaximumFractionDigits(2);
/*  84 */       this.numberFormat.setMaximumIntegerDigits(5);
/*     */     } 
/*     */     
/*  87 */     this.fps = new RateValue(120);
/*  88 */     this.fpsAverage = new RateValue(120);
/*     */   }
/*     */ 
/*     */   
/*     */   public BComponent getHitComponent(int mx, int my) {
/*  93 */     if (this.dismissState == DismissState.OPEN) {
/*  94 */       return super.getHitComponent(mx, my);
/*     */     }
/*  96 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHit() {
/* 102 */     return (this.dismissState == DismissState.OPEN);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Renderer renderer) {
/* 108 */     this.fps.log(1L);
/* 109 */     this.fpsAverage.log((long)this.fps.getValuePerSecond());
/* 110 */     super.render(renderer);
/*     */     
/* 112 */     if (this.dismissState == DismissState.WAIT_STABLE_FPS && (this.fpsAverage.getStandardDeviation() < 8.0D || System.currentTimeMillis() - this.dismissStartAt > 30L))
/*     */     {
/*     */       
/* 115 */       setDismissState(DismissState.FADING_OUT);
/*     */     }
/*     */     
/* 118 */     if (this.dismissState == DismissState.FADING_OUT) {
/* 119 */       float fps = (float)this.fpsAverage.getAverage();
/* 120 */       if (fps <= 1.0F) {
/* 121 */         fps = 1.0F;
/*     */       }
/* 123 */       float alpha = getAlpha() - 0.5F / fps;
/* 124 */       if (alpha < 0.0F) {
/* 125 */         alpha = 0.0F;
/*     */       }
/* 127 */       setAlpha(alpha);
/* 128 */       if (alpha == 0.0F) {
/* 129 */         GameTaskQueueManager.getManager().update(new DismissTask(this));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void dismissForReal() {
/* 135 */     if (BuiSystem.getWindow(LoadingWindow.class.getSimpleName()) != null) {
/* 136 */       super.dismiss();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void dismiss() {
/* 142 */     setDismissState(DismissState.WAIT_STABLE_FPS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomPet() {
/* 147 */     this.randomPet = (new Random()).nextInt(this.loadingPets.length);
/* 148 */     for (BLabel petLabel : this.loadingPets) {
/* 149 */       petLabel.setAlpha(0.0F);
/* 150 */       petLabel.setVisible(false);
/*     */     } 
/* 152 */     this.loadingPets[this.randomPet].setAlpha(1.0F);
/* 153 */     this.loadingPets[this.randomPet].setVisible(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 158 */     if (visible) {
/* 159 */       setDismissState(DismissState.OPEN);
/* 160 */       this.startLoadingScreen = true;
/*     */     } 
/* 162 */     super.setVisible(visible);
/* 163 */     for (BLabel petLabel : this.loadingPets) {
/* 164 */       petLabel.setVisible(false);
/*     */     }
/* 166 */     this.loadingPets[(new Random()).nextInt(this.loadingPets.length)].setVisible(visible);
/*     */   }
/*     */   
/*     */   private void setDismissState(DismissState dismissState) {
/* 170 */     if (dismissState == DismissState.OPEN) {
/* 171 */       setAlpha(1.0F);
/* 172 */       setLayer(100);
/*     */     } 
/* 174 */     this.dismissState = dismissState;
/* 175 */     this.dismissStartAt = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   private void createProgressArea() {
/* 179 */     this.progress = new BProgressBar(BProgressBar.Direction.PROGRESSDIR_EAST);
/* 180 */     this.progress.setStyleClass("loadingscreen.progress");
/* 181 */     this.progress.setProgress(0.03F);
/*     */     
/* 183 */     this.downloadProgress = new BProgressBar(BProgressBar.Direction.PROGRESSDIR_EAST);
/* 184 */     this.downloadProgress.setStyleClass("loadingscreen.progress.download");
/* 185 */     this.downloadProgress.setProgress(0.03F);
/*     */     
/* 187 */     BLabel progressBkd = new BLabel("");
/* 188 */     progressBkd.setStyleClass("loadingscreen.progress.background");
/*     */     
/* 190 */     BLabel progressGloss = new BLabel("");
/* 191 */     progressGloss.setStyleClass("loadingscreen.progress.gloss");
/*     */     
/* 193 */     for (int i = 0; i < this.loadingPets.length; i++) {
/* 194 */       this.loadingPets[i] = new BLabel("");
/* 195 */       this.loadingPets[i].setStyleClass("loadingscreen.label.pet." + (i + 1));
/* 196 */       int width = 640;
/* 197 */       int height = 480;
/* 198 */       add((BComponent)this.loadingPets[i], new Rectangle(DisplaySystem.getDisplaySystem().getWidth() / 2 - width / 2, DisplaySystem.getDisplaySystem().getHeight() / 2 - height / 2, width, height));
/*     */     } 
/*     */ 
/*     */     
/* 202 */     int progressWidth = (System.getProperty("tcg.load.w") != null) ? Integer.parseInt(System.getProperty("tcg.load.w")) : 357;
/* 203 */     int progressHeight = (System.getProperty("tcg.load.h") != null) ? Integer.parseInt(System.getProperty("tcg.load.h")) : 25;
/* 204 */     Rectangle PROGRESS_BGD_SIZE = new Rectangle(DisplaySystem.getDisplaySystem().getWidth() / 2 - progressWidth / 2, 75, progressWidth, progressHeight);
/* 205 */     Rectangle PROGRESS_SIZE = new Rectangle(DisplaySystem.getDisplaySystem().getWidth() / 2 - progressWidth / 2 + 6, 80, progressWidth - 13, progressHeight - 9);
/*     */     
/* 207 */     add((BComponent)progressBkd, PROGRESS_BGD_SIZE);
/* 208 */     add((BComponent)this.downloadProgress, PROGRESS_SIZE);
/* 209 */     add((BComponent)this.progress, PROGRESS_SIZE);
/* 210 */     add((BComponent)progressGloss, PROGRESS_SIZE);
/*     */     
/* 212 */     this.downloadProgress.setVisible(false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void createDownloadProgressor() {
/* 218 */     int width = 200;
/* 219 */     int height = 100;
/*     */ 
/*     */     
/* 222 */     this.downloadContainer = new BContainer((BLayoutManager)new VGroupLayout());
/* 223 */     this.downloadContainer.setStyleClass("loadingscreen.container");
/*     */     
/* 225 */     add((BComponent)this.downloadContainer, new Rectangle(DisplaySystem.getDisplaySystem().getWidth() / 2 - width / 2, DisplaySystem.getDisplaySystem().getHeight() / 2 - height / 2, 200, 100));
/*     */ 
/*     */ 
/*     */     
/* 229 */     this.downloadContainer.setVisible(false);
/*     */   }
/*     */   
/*     */   public void createWaitMessageContainer() {
/* 233 */     int CharSize = 20;
/* 234 */     int MaxChars = 50;
/* 235 */     int CharHeight = 38;
/*     */     
/* 237 */     this.textWaitLabel = new BLabel("");
/* 238 */     this.textWaitLabel.setStyleClass("loadingscreen.waitlabelwhite");
/* 239 */     this.textWaitLabel.setPreferredSize(MaxChars * CharSize, CharHeight);
/*     */     
/* 241 */     add((BComponent)this.textWaitLabel, new Rectangle(DisplaySystem.getDisplaySystem().getWidth() / 2 - MaxChars * CharSize / 2, 117, MaxChars * CharSize, CharHeight * 2));
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
/*     */   public void createTipMessageContainer() {
/* 253 */     int CharSize = 18;
/* 254 */     int MaxChars = 50;
/* 255 */     int CharHeight = 32;
/*     */     
/* 257 */     this.textTipLabel = new BLabel("");
/* 258 */     this.textTipLabel.setStyleClass("loadingscreen.waitlabelwhite");
/* 259 */     this.textTipLabel.setPreferredSize(MaxChars * CharSize, CharHeight);
/*     */     
/* 261 */     add((BComponent)this.textTipLabel, new Rectangle(DisplaySystem.getDisplaySystem().getWidth() / 2 - MaxChars * CharSize / 2, DisplaySystem.getDisplaySystem().getHeight() - CharHeight - 50, MaxChars * CharSize, CharHeight * 2));
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
/*     */   private void setTipMessage(String tipKeyName) {
/* 273 */     if (!tipKeyName.equals("")) {
/* 274 */       this.textTipLabel.setText(TcgGame.getLocalizedText(tipKeyName, new String[0]));
/* 275 */       this.textTipLabel.validate();
/*     */     } else {
/* 277 */       this.textTipLabel.setText("");
/*     */     } 
/*     */     
/* 280 */     this.textTipLabel.setVisible(true);
/* 281 */     this.textTipLabel.validate();
/*     */   }
/*     */   
/*     */   private void setWaitMessage(String waitMessageKeyName) {
/* 285 */     if (!waitMessageKeyName.equals("")) {
/* 286 */       this.textWaitLabel.setText(TcgGame.getLocalizedText(waitMessageKeyName, new String[0]));
/*     */     } else {
/* 288 */       this.textWaitLabel.setText("");
/*     */     } 
/*     */     
/* 291 */     this.textWaitLabel.setVisible(true);
/* 292 */     this.textWaitLabel.validate();
/*     */   }
/*     */   
/*     */   private void setRandomTipMessage() {
/* 296 */     String maxTips = this.loadingScreenPropertyReader.getProperty("maxTipsNumber");
/*     */     
/* 298 */     if (maxTips != null) {
/* 299 */       int randomIndex = this.randomGenerator.nextInt(Integer.parseInt(maxTips)) + 1;
/*     */       
/* 301 */       if (this.splashScreen) {
/* 302 */         setTipMessage("splashscreen.tip1");
/*     */       } else {
/* 304 */         setTipMessage("splashscreen.tip" + randomIndex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void hideControls() {
/* 310 */     this.textWaitLabel.setVisible(false);
/* 311 */     this.textWaitLabel.validate();
/*     */     
/* 313 */     this.textTipLabel.setVisible(false);
/* 314 */     this.textTipLabel.validate();
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
/*     */   public void loadMap(String toMapId) {
/* 334 */     if (MainGameState.getArrowWindow() != null) {
/* 335 */       MainGameState.getArrowWindow().setVisible(false);
/*     */     }
/* 337 */     if (TcgGame.isStartDuelMode()) {
/* 338 */       TcgGame.setStartDuelMode(false);
/*     */     }
/*     */     
/* 341 */     setDismissState(DismissState.OPEN);
/* 342 */     this.progress.setProgress(0.0F);
/*     */     
/* 344 */     hideControls();
/*     */     
/* 346 */     if (this.startLoadingScreen) {
/* 347 */       setWaitMessage("");
/* 348 */       setRandomTipMessage();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setProgress(float progressPercent, boolean download) {
/* 353 */     progressPercent = Math.min(1.0F, progressPercent);
/* 354 */     progressPercent = Math.max(0.0F, progressPercent);
/*     */     
/* 356 */     float diff = progressPercent % 0.032258064F;
/* 357 */     progressPercent += 0.032258064F - diff;
/*     */     
/* 359 */     progressPercent = Math.min(1.0F, progressPercent);
/*     */     
/* 361 */     this.downloadProgress.setVisible(download);
/* 362 */     if (download && this.minDownloadFiles > 0) {
/* 363 */       this.textTipLabel.setVisible(true);
/* 364 */       this.textTipLabel.validate();
/*     */       
/* 366 */       this.currentFile = (this.currentFile + 1 > this.minDownloadFiles) ? (this.minDownloadFiles - 1) : this.currentFile;
/* 367 */       this.textTipLabel.setText(TcgGame.getLocalizedText("loadingwindow.label.downloading", new String[0]) + ": " + (this.currentFile + 1) + "/" + this.minDownloadFiles);
/*     */ 
/*     */       
/* 370 */       if (progressPercent <= 0.032258F) {
/* 371 */         this.downloadProgress.setProgress(0.032258F);
/*     */       } else {
/* 373 */         this.downloadProgress.setProgress(progressPercent);
/*     */       } 
/* 375 */       this.progress.setProgress(0.0F);
/*     */     } else {
/* 377 */       if (progressPercent > this.progress.getProgress()) {
/* 378 */         this.progress.setProgress(progressPercent);
/*     */       }
/* 380 */       if (progressPercent <= 0.032258F) {
/* 381 */         this.progress.setProgress(0.032258F);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isProgressEnabled() {
/* 389 */     return this.progress.isVisible();
/*     */   }
/*     */   
/*     */   public void setProgressEnabled(boolean progressEnabled) {
/* 393 */     this.progress.setVisible(progressEnabled);
/* 394 */     layout();
/*     */   }
/*     */ 
/*     */   
/*     */   public void startedDownloading(String fileName) {
/* 399 */     this.downloading = true;
/* 400 */     this.fileName = fileName;
/* 401 */     this.downloadContainer.setVisible(true);
/* 402 */     layout();
/* 403 */     this.downloadContainer.validate();
/*     */   }
/*     */ 
/*     */   
/*     */   public void finishedDownloading() {
/* 408 */     this.downloading = false;
/* 409 */     this.downloadContainer.setVisible(false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startedVerifying(String fileName) {
/* 415 */     this.verifying = true;
/* 416 */     this.fileName = fileName;
/* 417 */     this.downloadContainer.setVisible(true);
/* 418 */     layout();
/* 419 */     this.downloadContainer.validate();
/*     */   }
/*     */ 
/*     */   
/*     */   public void finishedVerifying() {
/* 424 */     this.verifying = false;
/* 425 */     this.downloadContainer.setVisible(false);
/*     */   }
/*     */   
/*     */   public void setMinDownloadFiles(int minDownloadFiles) {
/* 429 */     this.minDownloadFiles = minDownloadFiles;
/*     */   }
/*     */   
/*     */   public void setTotalDownloadFiles(int totalDownloadFiles) {
/* 433 */     this.totalDownloadFiles = totalDownloadFiles;
/*     */   }
/*     */   
/*     */   public void updateDownloaderProgress(ResourceDownloader downloader) {
/* 437 */     if (this.downloading) {
/*     */       
/* 439 */       this.currentFile = this.totalDownloadFiles - downloader.getNumberOfDownloadRequestsQueued();
/* 440 */       float fileRatio = (this.minDownloadFiles > 0) ? (1.0F / this.minDownloadFiles) : 1.0F;
/*     */       
/* 442 */       int downloadSize = downloader.getCurrentFileDownloadSize();
/* 443 */       int downloadLength = downloader.getFileDownloadLength();
/* 444 */       float downloadPercent = downloadSize / downloadLength * 1.0F;
/*     */       
/* 446 */       float downloadProgressAmount = (downloadPercent + this.currentFile) * fileRatio;
/*     */       
/* 448 */       setProgress(downloadProgressAmount, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String formatBytes(int bytes) {
/* 453 */     float displayedBytes = bytes;
/* 454 */     if (bytes > 1024) {
/* 455 */       displayedBytes /= 1024.0F;
/* 456 */       if (displayedBytes > 1024.0F) {
/* 457 */         displayedBytes /= 1024.0F;
/* 458 */         return this.numberFormat.format(displayedBytes) + "MB";
/*     */       } 
/* 460 */       return this.numberFormat.format(displayedBytes) + "KB";
/*     */     } 
/* 462 */     return this.numberFormat.format(displayedBytes) + "B";
/*     */   }
/*     */   
/*     */   public void setSplashScreen(boolean splashScreen) {
/* 466 */     this.splashScreen = splashScreen;
/*     */   }
/*     */   
/*     */   public void setStartLoadingScreen(boolean startLoadingScreen) {
/* 470 */     this.startLoadingScreen = startLoadingScreen;
/*     */   }
/*     */   
/*     */   public enum DismissState {
/* 474 */     OPEN,
/* 475 */     WAIT_STABLE_FPS,
/* 476 */     FADING_OUT;
/*     */   }
/*     */   
/*     */   private static class DismissTask
/*     */     implements Callable<Object> {
/*     */     private LoadingWindow loadingWindow;
/*     */     
/*     */     public DismissTask(LoadingWindow loadingWindow) {
/* 484 */       this.loadingWindow = loadingWindow;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object call() throws Exception {
/* 490 */       this.loadingWindow.dismissForReal();
/* 491 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud\LoadingWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */