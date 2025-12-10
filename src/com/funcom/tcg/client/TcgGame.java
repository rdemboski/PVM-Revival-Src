/*      */ package com.funcom.tcg.client;
import com.funcom.audio.DataLoader;
/*      */ import com.funcom.audio.SoundSystemException;
/*      */ import com.funcom.audio.SoundSystemFactory;
/*      */ import com.funcom.commons.MessageBox;
/*      */ import com.funcom.commons.PerformanceGraphNode;
/*      */ import com.funcom.commons.dfx.DireEffectDescription;
/*      */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*      */ import com.funcom.commons.dfx.DireEffectResourceLoader;
/*      */ import com.funcom.commons.dfx.EffectDescriptionFactory;
/*      */ import com.funcom.commons.dfx.EffectHandlerFactory;
/*      */ import com.funcom.commons.jme.JMETimerSystem;
/*      */ import com.funcom.commons.jme.TcgPolledRootNode;
/*      */ import com.funcom.commons.localization.JavaLocalization;
/*      */ import com.funcom.commons.utils.ApplicationRelativePathUtil;
/*      */ import com.funcom.commons.utils.GlobalTime;
/*      */ import com.funcom.commons.utils.TimeSystem;
/*      */ import com.funcom.errorhandling.CrashDataProvider;
/*      */ import com.funcom.errorhandling.DoomsdayErrorHandler;
/*      */ import com.funcom.gameengine.Updated;
/*      */ import com.funcom.gameengine.audio.ResourceDataLoader;
/*      */ import com.funcom.gameengine.input.ButtonStateTracker;
/*      */ import com.funcom.gameengine.model.ResourceGetter;
/*      */ import com.funcom.gameengine.model.ResourceGetterImpl;
/*      */ import com.funcom.gameengine.model.input.Cursor;
import com.funcom.gameengine.model.token.GameTokenProcessor;
/*      */ import com.funcom.gameengine.model.token.TokenProcessor;
/*      */ import com.funcom.gameengine.resourcemanager.CacheType;
/*      */ import com.funcom.gameengine.resourcemanager.ResourceDownloader;
/*      */ import com.funcom.gameengine.resourcemanager.ResourceLoader;
/*      */ import com.funcom.gameengine.resourcemanager.ResourceManager;
import com.funcom.gameengine.resourcemanager.loaders.JopsParticleLoader;
import com.funcom.gameengine.resourcemanager.loaders.PeelerLoader;
/*      */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*      */ import com.funcom.gameengine.resourcemanager.loadingmanager.SoundDestroyCallable;
/*      */ import com.funcom.gameengine.resourcemanager.loadingmanager.SoundInitCallable;
/*      */ import com.funcom.gameengine.resourcemanager.loadingmanager.SoundSetDataLoaderCallable;
/*      */ import com.funcom.gameengine.resourcemanager.loadingmanager.SoundSetMuteCallable;
/*      */ import com.funcom.gameengine.resourcemanager.loadingmanager.SoundUpdateCallable;
import com.funcom.gameengine.utils.LoadingScreenListener;
/*      */ import com.funcom.gameengine.view.CameraConfig;
import com.funcom.gameengine.view.DfxTextWindowManager;
/*      */ import com.funcom.gameengine.view.TargetedEffectNode;
import com.funcom.gameengine.view.XmlEffectDescriptionFactory;
/*      */ import com.funcom.rpgengine2.items.PlayerDescription;
/*      */ import com.funcom.rpgengine2.loader.ConfigErrors;
/*      */ import com.funcom.server.common.NetworkConfiguration;
import com.funcom.tcg.client.dfx.ClientDFXResourceLoader;
import com.funcom.tcg.client.dfx.GuiParticleHandlerFactory;
/*      */ import com.funcom.tcg.client.errorhandling.ActivePetDataProvider;
/*      */ import com.funcom.tcg.client.errorhandling.PetSlotsDataProvider;
import com.funcom.tcg.client.errorhandling.VersionDataProvider;
/*      */ import com.funcom.tcg.client.errorhandling.WorldCoordinateCrashDataProvider;
/*      */ import com.funcom.tcg.client.metrics.HttpMetrics;
/*      */ import com.funcom.tcg.client.model.PropNodeRegister;
/*      */ import com.funcom.tcg.client.model.rpg.VisualRegistry;
/*      */ import com.funcom.tcg.client.net.NetworkHandler;
/*      */ import com.funcom.tcg.client.net.processors.loadingmanager.DuelCountdown;
/*      */ import com.funcom.tcg.client.rpg.TCGClientRpgLoader;
/*      */ import com.funcom.tcg.client.state.MainGameState;
import com.funcom.tcg.client.state.NewLoginGameState;
/*      */ import com.funcom.tcg.client.state.TCGCursorSetter;
/*      */ import com.funcom.tcg.client.state.WebStartGameState;
import com.funcom.tcg.client.ui.BuiUtils;
/*      */ import com.funcom.tcg.client.ui.Localizer;
/*      */ import com.funcom.tcg.client.ui.TcgUI;
import com.funcom.tcg.client.ui.duel.DuelAcceptWindow;
/*      */ import com.funcom.tcg.client.ui.friend.FriendModeType;
/*      */ import com.funcom.tcg.client.ui.giftbox.HudInfoSetWindow;
/*      */ import com.funcom.tcg.client.ui.hud.GameWindows;
/*      */ import com.funcom.tcg.client.ui.hud.LoadingWindow;
/*      */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*      */ import com.funcom.tcg.client.ui.hud.QuitWindow;
/*      */ import com.funcom.tcg.client.ui.hud.TCGWindowsController;
/*      */ import com.funcom.tcg.client.ui.hud2.QuestHudWindow;
/*      */ import com.funcom.tcg.factories.ResourceManagerFactory;
/*      */ import com.funcom.tcg.net.message.LoginResponseMessage;
/*      */ import com.funcom.tcg.rpg.AbstractTCGRpgLoader;
/*      */ import com.funcom.tcg.utils.DevelopmentTimeUtils;
/*      */ import com.jme.app.AbstractGame;
/*      */ import com.jme.image.Image;
/*      */ import com.jme.input.KeyInput;
/*      */ import com.jme.input.MouseInput;
/*      */ import com.jme.input.controls.Binding;
/*      */ import com.jme.input.controls.GameControl;
/*      */ import com.jme.input.controls.GameControlManager;
/*      */ import com.jme.input.controls.binding.KeyboardBinding;
import com.jme.input.joystick.JoystickInput;
/*      */ import com.jme.renderer.Camera;
/*      */ import com.jme.renderer.ColorRGBA;
/*      */ import com.jme.renderer.Renderer;
/*      */ import com.jme.system.DisplaySystem;
/*      */ import com.jme.system.GameSettings;
/*      */ import com.jme.system.PropertiesGameSettings;
/*      */ import com.jme.util.Debug;
/*      */ import com.jme.util.GameTaskQueueManager;
import com.jme.util.NanoTimer;
import com.jme.util.TextureManager;
/*      */ import com.jme.util.Timer;
/*      */ import com.jme.util.stat.StatCollector;
/*      */ import com.jmex.bui.BRootNode;
/*      */ import com.jmex.bui.BStyleSheet;
/*      */ import com.jmex.bui.BWindow;
/*      */ import com.jmex.bui.BuiSystem;
import com.jmex.bui.bss.BStyleSheetUtil;
/*      */ import com.jmex.game.state.GameState;
/*      */ import com.jmex.game.state.GameStateManager;
/*      */ import com.turborilla.jops.jme.ParticleProcessor;
import com.turborilla.jops.jme.ParticleTextureLoader;
/*      */ import com.turborilla.jops.jme.ParticleTextureLoaderInstance;
/*      */ import com.turborilla.jops.jme.ResourceManagerParticleTextureLoader;
/*      */ import java.awt.Desktop;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.StringReader;
/*      */ import java.net.InetSocketAddress;
import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.security.PublicKey;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.Properties;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.prefs.Preferences;

import org.apache.log4j.Level;
/*      */ import org.apache.log4j.Logger;
/*      */ import org.lwjgl.opengl.DisplayMode;
/*      */ 
/*      */ public class TcgGame extends TcgFixedFramerateGame implements Localizer {
/*  116 */   private boolean debugEnabled = ("true".equalsIgnoreCase(System.getProperty("tcg.debug.enablekeys")) && (new File(System.getenv("USERPROFILE") + File.separator + ".funcom" + File.separator + "tcg" + File.separator + "pinkcats.arecool.pvm")).exists());
/*      */   
/*      */   private static final boolean PERFORMANCE_TEST = false;
/*      */   public static final String DEFUALT_USER_NAME = "123trial123";
/*      */   private static final String DEFAULT_SERVER_DOMAIN = "pvm-live-eu";
/*      */   private static final String PROP_FULLSCREEN_SELECTED = "tcg.fullscreenselected";
/*      */   private static final String PROP_WINDOWED_SELECTED = "tcg.windowmodeselected";
/*      */   private static final String RENDERER_ID = "LWJGL";
/*      */   private static final String ARG_DOWNLOAD_RESOURCES = "download.resources";
/*  125 */   public static final Logger LOGGER = Logger.getLogger(TcgGame.class);
/*      */   
/*      */   private static final int FRAMERATE = 60;
/*      */   
/*      */   private static final int FRAMERATE_PERFORMANCE_TEST = 300;
/*      */   private static final int MINIMAL_FRAMERATE = 45;
/*      */   private static final int MINIMAL_FRAMERATE_PERFORMANCE_TEST = 45;
/*      */   private static final int PARTICLE_CACLULATIONS_PER_SECOND = 31;
/*      */   public static final String GAME_ICON = "gui/v3/general/PvMIcon_32.png";
/*      */   private static TcgGame INSTANCE;
/*  135 */   private String userName = "123trial123";
/*      */   
/*      */   private TokenProcessor tokenProcessor;
/*      */   
/*      */   private ResourceManager resourceManager;
/*      */   
/*      */   private PropNodeRegister propNodeRegister;
/*      */   private PropNodeRegister monsterRegister;
/*      */   private TcgPreferences tcgPreferences;
/*      */   private LoginResponseMessage loginMessage;
/*      */   private boolean loginFinished;
/*      */   private ParticleProcessor particleProcessor;
/*      */   private ResourceGetter resourceGetter;
/*      */   private GameControlManager gameControlManager;
/*      */   private ButtonStateTracker rootGameKeysTracker;
/*      */   private DireEffectDescriptionFactory effectDescriptionFactory;
/*      */   private Collection<String> resourceRoots;
/*      */   private static ColorRGBA backgroundColor;
/*      */   private static boolean backgroundColorChanged = false;
/*      */   private final LoadingScreenController loadingScreenController;
/*      */   private Thread resourceDownloaderThread;
/*      */   private ResourceDownloader resourceDownloader;
/*      */   private VisualRegistry visualRegistry;
/*      */   private AbstractTCGRpgLoader rpgLoader;
/*      */   private PropNodeRegister customPortalRegister;
/*      */   private PropNodeRegister returnPointRegister;
/*      */   private PropNodeRegister townPortalRegister;
/*      */   private PropNodeRegister waypointDestinationRegister;
/*      */   private PropNodeRegister waypointRegister;
/*      */   private PropNodeRegister vendorRegister;
/*      */   private List<TargetedEffectNode> targetEffectMap;
/*      */   private PublicKey publicKey;
/*      */   private String serverDomain;
/*      */   private static String versionText;
/*      */   private static final long MIN_PROCESS_TIME = 1666666L;
/*  170 */   private int petTutorialId = 0;
/*      */   
/*      */   private boolean petTutorial = false;
/*      */   
/*      */   private boolean equipmentTutorial = false;
/*      */   private boolean tutorialMode = false;
/*      */   private boolean allowSave = true;
/*      */   private boolean loggedOut = false;
/*  178 */   private FriendModeType addFriendMode = FriendModeType.OFF;
/*      */   private boolean startDuelMode = false;
/*      */   private boolean dueling = false;
/*      */   private ConfigErrorWindow configErrorWindow;
/*      */   private static ResourceBundle bundle;
/*      */   
/*      */   static {
/*  185 */     Locale locale = (System.getProperty("tcg.locale") != null && !System.getProperty("tcg.locale").equals("")) ? new Locale(System.getProperty("tcg.locale")) : null;
/*      */ 
/*      */     
/*  188 */     bundle = (locale != null) ? ResourceBundle.getBundle("languages.gameplay-text", locale) : ResourceBundle.getBundle("languages.gameplay-text");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public TcgGame() {
/*  194 */     INSTANCE = this;
/*  195 */     LOGGER.setLevel(Level.INFO);
/*  196 */     Thread.currentThread().setPriority(10);
/*  197 */     initResolutionsHelper();
/*  198 */     LOGGER.info("Initializing localization - starting");
/*  199 */     initLocalization();
/*  200 */     LOGGER.info("Initializing localization - complete");
/*  201 */     this.loadingScreenController = new LoadingScreenController();
/*  202 */     printBuildVersion();
/*  203 */     TcgJme.updateSplashProgress(30);
/*      */     
/*  205 */     if (DisplayResolutionHelper.getInstance().currentResolutionIsLow()) {
/*  206 */       HttpMetrics.postEvent(HttpMetrics.Event.TOO_LOW_RESOLUTION);
/*  207 */       TcgJme.showDialog(ConfigErrorType.LOW_RESOLUTION);
/*  208 */       this.finished = true;
/*  209 */       quit();
/*      */     } 
/*      */     
/*  212 */     if (LoadingManager.USE) {
/*  213 */       LOGGER.info("Initializing loading maanger - starting");
/*  214 */       LoadingManager.INSTANCE.start(new TcgAchaDataFeeder());
/*  215 */       LoadingManager.INSTANCE.setReservedTime(Double.valueOf(0.001D));
/*  216 */       LOGGER.info("Initializing loading maanger - complete");
/*      */     } 
/*  218 */     TcgJme.updateSplashProgress(35);
/*      */   }
/*      */   
/*      */   private void initResolutionsHelper() {
/*  222 */     DisplayResolutionHelper.init();
/*      */   }
/*      */   
/*      */   private void initLocalization() {
/*  226 */     String stringLocale = System.getProperty("tcg.locale");
/*      */     
/*      */     int i;
/*      */     
/*  230 */     for (i = 0; i < (TcgLocale.values()).length && 
/*  231 */       !TcgLocale.values()[i].getLocale().equals(stringLocale); i++);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  236 */     if (i >= (TcgLocale.values()).length) {
/*  237 */       stringLocale = "en";
/*      */     }
/*      */     
/*  240 */     if (stringLocale != null) {
/*  241 */       Locale locale = new Locale(stringLocale.toLowerCase());
/*  242 */       Locale.setDefault(locale);
/*      */     } 
/*      */     
/*  245 */     JavaLocalization.init(ResourceBundle.getBundle("languages.rpg-text"));
/*      */   }
/*      */ 
/*      */   
/*      */   private static void printBuildVersion() {
/*  250 */     String version = getVersionText();
/*  251 */     System.out.println(version);
/*  252 */     System.err.println(version);
/*  253 */     LOGGER.info(version);
/*      */   }
/*      */   
/*      */   public static String getVersionText() {
/*  257 */     if (versionText == null) {
/*      */       
/*  259 */       versionText = "Unknown Version";
/*  260 */       InputStream stream = TcgGame.class.getResourceAsStream("/META-INF/build_number.properties");
/*  261 */       if (stream != null) {
/*      */         try {
/*  263 */           Properties versionProps = new Properties();
/*  264 */           versionProps.load(stream);
/*      */           
/*  266 */           long svnRevision = -1L;
/*  267 */           for (Object keyObj : versionProps.keySet()) {
/*  268 */             String key = keyObj.toString();
/*      */             
/*  270 */             if (key.startsWith("build.vcs.number.")) {
/*  271 */               String value = versionProps.getProperty(key);
/*  272 */               long tmp = Long.parseLong(value);
/*  273 */               if (svnRevision < tmp) {
/*  274 */                 svnRevision = tmp;
/*      */               }
/*      */             } 
/*      */           } 
/*      */           
/*  279 */           versionText = "#" + svnRevision + " b" + versionProps.getProperty("build.number") + " d" + versionProps.getProperty("timestamp");
/*      */         }
/*  281 */         catch (Exception e) {
/*  282 */           versionText = "Error Getting Version";
/*  283 */           e.printStackTrace();
/*      */         } finally {
/*      */           try {
/*  286 */             stream.close();
/*  287 */           } catch (IOException ignore) {}
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  292 */       versionText = "Version: " + versionText;
/*      */     } 
/*      */     
/*  295 */     return versionText;
/*      */   }
/*      */   
/*      */   public void addCrashDataProviders(DoomsdayErrorHandler handler) {
/*  299 */     handler.addCrashDataProvider((CrashDataProvider)new VersionDataProvider());
/*  300 */     handler.addCrashDataProvider((CrashDataProvider)new WorldCoordinateCrashDataProvider());
/*  301 */     handler.addCrashDataProvider((CrashDataProvider)new ActivePetDataProvider());
/*  302 */     handler.addCrashDataProvider((CrashDataProvider)new PetSlotsDataProvider());
/*      */   }
/*      */   
/*      */   public void setResourceRoots(Collection<String> resourceRoots) {
/*  306 */     if (this.resourceRoots == null)
/*  307 */       this.resourceRoots = new HashSet<String>(); 
/*  308 */     this.resourceRoots.clear();
/*  309 */     this.resourceRoots.addAll(resourceRoots);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected GameSettings getNewSettings() {
/*  315 */     File systemdir = new File(ApplicationRelativePathUtil.getSystemDir());
/*  316 */     if (!systemdir.exists()) {
/*  317 */       systemdir.mkdir();
/*      */     }
/*      */     
/*  320 */     PropertiesGameSettings gameSettings = new PropertiesGameSettings(ApplicationRelativePathUtil.getSystemDir() + "/properties.cfg");
/*      */     
/*  322 */     gameSettings.load();
/*  323 */     autoSelectMode(gameSettings);
/*      */     
/*  325 */     return (GameSettings)gameSettings;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void autoSelectMode(PropertiesGameSettings gameSettings) {
/*  333 */     DisplayMode displayMode = null;
/*      */     
/*  335 */     if (gameSettings.isNew()) {
/*  336 */       displayMode = DisplayResolutionHelper.getInstance().getBootResolution();
/*  337 */       if (displayMode == null)
/*      */       {
/*  339 */         displayMode = DisplayResolutionHelper.getInstance().getCloserValidDisplayMode(gameSettings.getWidth(), gameSettings.getHeight(), gameSettings.getDepth(), gameSettings.getFrequency());
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  344 */       displayMode = DisplayResolutionHelper.getInstance().getCloserValidDisplayMode(gameSettings.getWidth(), gameSettings.getHeight(), gameSettings.getDepth(), gameSettings.getFrequency());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  350 */     if (displayMode != null) {
/*  351 */       gameSettings.setFrequency(displayMode.getFrequency());
/*  352 */       gameSettings.setRenderer("LWJGL");
/*  353 */       gameSettings.setWidth(displayMode.getWidth());
/*  354 */       gameSettings.setHeight(displayMode.getHeight());
/*  355 */       gameSettings.setDepth(displayMode.getBitsPerPixel());
/*  356 */       gameSettings.setDepthBits(24);
/*      */       
/*  358 */       gameSettings.setIsNew(false);
/*  359 */       gameSettings.setBoolean("tcg.windowmodeselected", true);
/*      */       
/*      */       try {
/*  362 */         gameSettings.save();
/*  363 */       } catch (IOException e) {
/*  364 */         System.err.println("Cannot save screen settings");
/*  365 */         e.printStackTrace();
/*      */       } 
/*      */     } else {
/*  368 */       gameSettings.setIsNew(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initSystem() {
/*  375 */     LOGGER.info("System init starting");
/*  376 */     Timer.setTimer((Timer)new NanoTimer());
/*  377 */     JMETimerSystem timeSystem = new JMETimerSystem();
/*  378 */     GlobalTime.setInstance((TimeSystem)timeSystem);
/*      */ 
/*      */     
/*  381 */     NetworkConfiguration.instance();
/*      */     
/*  383 */     initResourceManager(this.resourceRoots);
/*      */     
/*  385 */     LOGGER.info("Initializing display - starting");
/*  386 */     initDisplay();
/*  387 */     LOGGER.info("Initializing display - complete");
/*  388 */     TcgJme.updateSplashProgress(50);
/*      */     
/*  390 */     LOGGER.info("Initializing camera - starting");
/*  391 */     initCamera();
/*  392 */     LOGGER.info("Initializing camera - complete");
/*      */ 
/*      */     
/*  395 */     GameStateManager.create();
/*  396 */     LOGGER.info("Initializing controls - starting");
/*  397 */     initControls();
/*  398 */     LOGGER.info("Initializing controls - complete");
/*  399 */     startOpenTokenProcessor();
/*  400 */     LOGGER.info("Initializing preferences/registers - starting");
/*  401 */     this.tcgPreferences = new TcgPreferences(Preferences.userNodeForPackage(TcgGame.class));
/*  402 */     this.propNodeRegister = new PropNodeRegister();
/*  403 */     this.monsterRegister = new PropNodeRegister();
/*  404 */     this.returnPointRegister = new PropNodeRegister();
/*  405 */     this.townPortalRegister = new PropNodeRegister();
/*  406 */     this.customPortalRegister = new PropNodeRegister();
/*  407 */     this.waypointDestinationRegister = new PropNodeRegister();
/*  408 */     this.waypointRegister = new PropNodeRegister();
/*  409 */     this.vendorRegister = new PropNodeRegister();
/*  410 */     this.targetEffectMap = new ArrayList<TargetedEffectNode>();
/*  411 */     LOGGER.info("Initializing preferences/registers - complete");
/*  412 */     TcgJme.updateSplashProgress(55);
/*  413 */     LOGGER.info("Initializing particle manager - starting");
/*  414 */     initParticleManager();
/*  415 */     LOGGER.info("Initializing particle manager - complete");
/*  416 */     LOGGER.info("Initializing BUI - starting");
/*  417 */     initBui();
/*  418 */     LOGGER.info("Initializing BUI - complete");
/*  419 */     LOGGER.info("Initializing DFX Factory - starting");
/*  420 */     initDFXFactory();
/*  421 */     LOGGER.info("Initializing DFX Factory - complete");
/*      */     
/*  423 */     LOGGER.info("Initializing Resource Downloader - starting");
/*  424 */     initResourceDownloader();
/*  425 */     LOGGER.info("Initializing Resource Downloader - complete");
/*  426 */     TcgJme.updateSplashProgress(70);
/*      */ 
/*      */ 
/*      */     
/*  430 */     LOGGER.info("Initializing Render Stats - starting");
/*  431 */     if (Debug.stats) {
/*  432 */       StatCollector.init(1000L, 200);
/*  433 */       for (PerformanceGraphNode.TrackingStat trackingStat : PerformanceGraphNode.TrackingStat.values()) {
/*  434 */         if (trackingStat.isTimed) {
/*  435 */           StatCollector.addTimedStat(trackingStat.statType);
/*      */         }
/*      */       } 
/*  438 */       startTiming(PerformanceGraphNode.TrackingStat.OTHER);
/*      */     } 
/*  440 */     LOGGER.info("Initializing Render Stats - complete");
/*      */     
/*  442 */     LOGGER.info("Initializing Sound system - starting");
/*      */     
/*  444 */     if (LoadingManager.USE) {
/*  445 */       LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundSetDataLoaderCallable(new ResourceDataLoader(this.resourceManager, "audio/fmod")));
/*  446 */       LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundInitCallable());
/*      */     } else {
/*  448 */       SoundSystemFactory.getSoundSystem().setDataLoader((DataLoader)new ResourceDataLoader(this.resourceManager, "audio/fmod"));
/*      */       try {
/*  450 */         SoundSystemFactory.getSoundSystem().init();
/*  451 */       } catch (SoundSystemException e) {
/*  452 */         LOGGER.error("Cannot init sound system, sound disabled.");
/*  453 */         e.printStackTrace();
/*      */       } 
/*      */     } 
/*  456 */     LOGGER.info("Initializing Sound system - complete");
/*  457 */     TcgJme.updateSplashProgress(75);
/*  458 */     TcgUI.useProductionImplementation(this.resourceManager);
/*      */     
/*  460 */     LOGGER.info("Initializing Visual Registry - starting");
/*  461 */     this.visualRegistry = new VisualRegistry();
/*  462 */     this.visualRegistry.readAllData(this.resourceManager);
/*  463 */     LOGGER.info("Initializing Visual Registry - complete");
/*      */ 
/*      */     
/*  466 */     LOGGER.info("Initializing RPG Load - starting");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  471 */     this.rpgLoader = (AbstractTCGRpgLoader)new TCGClientRpgLoader(new ConfigErrors(), this.resourceManager);
/*      */     try {
/*  473 */       this.rpgLoader.load();
/*  474 */     } catch (IOException e) {
/*  475 */       throw new RuntimeException("Error loading rpg engine");
/*      */     } 
/*  477 */     LOGGER.info("Initializing RPG Load - complete");
/*  478 */     TcgJme.updateSplashProgress(85);
/*      */     
/*  480 */     if (LoadingManager.USE) {
/*  481 */       LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundSetMuteCallable(!this.tcgPreferences.getMusic(), !this.tcgPreferences.getSound()));
/*      */     } else {
/*      */       
/*  484 */       SoundSystemFactory.getSoundSystem().setMusicMute(!this.tcgPreferences.getMusic());
/*  485 */       SoundSystemFactory.getSoundSystem().setSfxMute(!this.tcgPreferences.getSound());
/*      */     } 
/*      */     
/*  488 */     TcgJme.updateSplashProgress(90);
/*      */     
/*  490 */     loadPublicKey();
/*      */     
/*  492 */     LOGGER.info("System init done");
/*      */   }
/*      */   
/*      */   private void loadPublicKey() {
/*  496 */     this.publicKey = (PublicKey)this.resourceManager.getResource(PublicKey.class, "configuration/public.key");
/*      */   }
/*      */   
/*      */   private void initResourceDownloader() {
/*  500 */     if (Boolean.getBoolean("download.resources")) {
/*  501 */       this.resourceDownloader = new ResourceDownloader(this.resourceManager);
/*  502 */       this.resourceDownloaderThread = new Thread((Runnable)this.resourceDownloader, "Resource Downloading Thread");
/*  503 */       this.resourceDownloaderThread.setPriority(2);
/*  504 */       this.resourceDownloaderThread.start();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void initDFXFactory() {
/*  509 */     this.effectDescriptionFactory = new DireEffectDescriptionFactory((DireEffectResourceLoader)new ClientDFXResourceLoader(getResourceManager(), getParticleProcessor()));
/*      */     
/*  511 */     this.effectDescriptionFactory.setDescriptionFactory((EffectDescriptionFactory)new XmlEffectDescriptionFactory(this.effectDescriptionFactory, (EffectHandlerFactory)new GuiParticleHandlerFactory()));
/*      */ 
/*      */     
/*  514 */     this.effectDescriptionFactory.putDefaultDireEffectDescription(new DireEffectDescription(""));
/*      */   }
/*      */   
/*      */   private void initControls() {
/*  518 */     MouseInput.get().setCursorVisible(true);
/*      */     
/*  520 */     this.gameControlManager = new GameControlManager();
/*  521 */     GameControl exitControl = this.gameControlManager.addControl(Controls.CID_EXIT.id);
/*  522 */     KeyboardBinding binding = new KeyboardBinding(Controls.CID_EXIT.keyCode);
/*  523 */     exitControl.addBinding((Binding)binding);
/*      */     
/*  525 */     this.rootGameKeysTracker = new ButtonStateTracker(new GameControl[] { exitControl })
/*      */       {
/*      */         protected void pressed(GameControl gameControl)
/*      */         {
/*  529 */           if (Controls.CID_EXIT.is(gameControl.getName()))
/*      */           {
/*  531 */             if ((KeyInput.get().isKeyDown(56) || KeyInput.get().isKeyDown(184)) && (
/*  532 */               !TcgGame.isPlaying() || MainGameState.isPlayerRegistered())) {
/*  533 */               TcgGame.finishGame();
/*      */             }
/*      */           }
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   private void initBui() {
/*  542 */     TcgPolledRootNode tcgPolledRootNode = new TcgPolledRootNode(Timer.getTimer());
/*  543 */     tcgPolledRootNode.setModalShade(new ColorRGBA(0.0F, 0.0F, 0.0F, 0.5F));
/*  544 */     BStyleSheet emptyStyleSheet = BStyleSheetUtil.getStyleSheet(new StringReader(""));
/*  545 */     BuiSystem.init((BRootNode)tcgPolledRootNode, emptyStyleSheet);
/*  546 */     BuiUtils.initDefaultStyleSheetParsers();
/*      */   }
/*      */   
/*      */   public void initDisplay() {
/*  550 */     LOGGER.info("Display init starting");
/*      */     
/*  552 */     boolean displaySucceeded = false;
/*  553 */     boolean terminatedLoop = false;
/*  554 */     boolean userCancelled = false;
/*      */     
/*  556 */     this.finished = false;
/*  557 */     this.settings = getNewSettings();
/*      */     
/*  559 */     if ((this.settings.isNew() && this.configShowMode == AbstractGame.ConfigShowMode.ShowIfNoConfig) || this.configShowMode == AbstractGame.ConfigShowMode.AlwaysShow) {
/*      */       
/*  561 */       HttpMetrics.postEvent(HttpMetrics.Event.DRIVER_ISSUE);
/*  562 */       TcgJme.showDialog(ConfigErrorType.DRIVER_ISSUE);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  582 */     while (!terminatedLoop) {
/*      */       try {
/*  584 */         this.display = DisplaySystem.getDisplaySystem();
/*  585 */         this.display.setMinSamples(this.settings.getSamples());
/*  586 */         this.display.setMinDepthBits(this.settings.getDepthBits());
/*  587 */         this.display.setTitle(getLocalizedText("mainwindow.title", new String[0]) + (this.debugEnabled ? " - 6.4" : ""));
/*      */ 
/*      */ 
/*      */         
/*  591 */         if ("false".equalsIgnoreCase(System.getProperty("Game.VSYNCH"))) {
/*  592 */           this.display.setVSyncEnabled(false);
/*      */         } else {
/*  594 */           this.display.setVSyncEnabled(true);
/*      */         } 
/*      */         
/*  597 */         Image applicationImage = (Image)this.resourceManager.getResource(Image.class, "gui/v3/general/PvMIcon_32.png", CacheType.NOT_CACHED);
/*  598 */         this.display.setIcon(new Image[] { applicationImage });
/*      */         
/*  600 */         if ((new DisplayModeHelper("LWJGL")).initDisplay(this.settings)) {
/*  601 */           displaySucceeded = true;
/*  602 */           terminatedLoop = true;
/*  603 */           TcgOptimization.apply(this.display);
/*  604 */           backgroundColor = ColorRGBA.black;
/*  605 */           this.display.getRenderer().setBackgroundColor(backgroundColor);
/*      */           continue;
/*      */         } 
/*  608 */         displaySucceeded = false;
/*  609 */         HttpMetrics.postEvent(HttpMetrics.Event.DRIVER_ISSUE);
/*  610 */         TcgJme.showDialog(ConfigErrorType.DRIVER_ISSUE);
/*      */       }
/*  612 */       catch (Throwable t) {
/*  613 */         displaySucceeded = false;
/*  614 */         terminatedLoop = true;
/*  615 */         userCancelled = false;
/*  616 */         throw new RuntimeException("Exception on initDisplay", t);
/*      */       } 
/*      */     } 
/*      */     
/*  620 */     if (!userCancelled) {
/*  621 */       if (displaySucceeded) {
/*  622 */         LOGGER.info("Display init succeeded");
/*  623 */         HttpMetrics.postEvent(HttpMetrics.Event.DISPLAY_LOADED);
/*      */       } else {
/*  625 */         MessageBox.ok(getLocalizedText("text.messagebox.initdisplayfail", new String[0]), getLocalizedText("caption.messagebox.initdisplayfail", new String[0]));
/*      */         
/*  627 */         LOGGER.info("Display init failed");
/*  628 */         throw new RuntimeException("Exception on initDisplay");
/*      */       } 
/*      */     } else {
/*  631 */       LOGGER.info("User cancelled");
/*  632 */       System.exit(1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void initCamera() {
/*  638 */     Camera camera = this.display.getRenderer().createCamera(this.display.getWidth(), this.display.getHeight());
/*  639 */     PvMCameraConfig.createInstance();
/*  640 */     CameraConfig.instance().setCamera(camera);
/*      */     
/*  642 */     camera.update();
/*  643 */     this.display.getRenderer().setCamera(camera);
/*      */   }
/*      */   
/*      */   private void startOpenTokenProcessor() {
/*  647 */     this.tokenProcessor = new TokenProcessor();
/*      */     
/*  649 */     if (LoadingManager.USE) {
/*  650 */       LoadingManager.INSTANCE.mTokenProcessor = this.tokenProcessor;
/*      */     } else {
/*  652 */       Thread thread = new Thread((Runnable)this.tokenProcessor, "Token Executor");
/*  653 */       thread.setPriority(8);
/*  654 */       thread.setDaemon(true);
/*  655 */       thread.start();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initParticleManager() {
/*  663 */     this.particleProcessor = new ParticleProcessor();
/*  664 */     this.particleProcessor.setCalculationsPerSecond(31);
/*      */     
/*  666 */     ParticleTextureLoaderInstance.setLoader((ParticleTextureLoader)new ResourceManagerParticleTextureLoader(this.resourceManager));
/*  667 */     this.resourceManager.addTypeLoader((ResourceLoader)new JopsParticleLoader());
/*      */   }
/*      */   
/*      */   private void initResourceManager(Collection<String> configurationFilePath) {
/*  671 */     this.resourceManager = ResourceManagerFactory.createForClient(configurationFilePath);
/*  672 */     this.resourceManager.addTypeLoader((ResourceLoader)new PeelerLoader());
/*      */     
/*  674 */     this.resourceGetter = (ResourceGetter)new ResourceGetterImpl(this.resourceManager);
/*      */     
/*  676 */     this.loadingScreenController.addListener((LoadingScreenListener)this.resourceManager);
/*      */ 
/*      */     
/*  679 */     String bootmap = DevelopmentTimeUtils.getBootMapPath();
/*  680 */     if (bootmap != null) {
/*  681 */       DevelopmentTimeUtils.setupForExternalMaps(this.resourceManager, bootmap);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void update(float interpolation) {
/*  691 */     Timer timer = Timer.getTimer();
/*      */     
/*  693 */     endTiming(PerformanceGraphNode.TrackingStat.OTHER);
/*      */     
/*  695 */     if (Debug.stats) {
/*  696 */       StatCollector.update();
/*      */     }
/*      */     
/*  699 */     startTiming(PerformanceGraphNode.TrackingStat.OTHER);
/*      */     
/*  701 */     startTiming(PerformanceGraphNode.TrackingStat.UPDATE_QUEUE);
/*  702 */     GameTaskQueueManager.getManager().getQueue("update").execute();
/*  703 */     this.rootGameKeysTracker.update(timer.getTimePerFrame());
/*  704 */     endTiming(PerformanceGraphNode.TrackingStat.UPDATE_QUEUE);
/*      */ 
/*      */     
/*  707 */     startTiming(PerformanceGraphNode.TrackingStat.PARTICLES);
/*  708 */     this.particleProcessor.process(timer.getTimePerFrame());
/*  709 */     endTiming(PerformanceGraphNode.TrackingStat.PARTICLES);
/*      */     
/*  711 */     GameStateManager.getInstance().update(timer.getTimePerFrame());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  717 */     startTiming(PerformanceGraphNode.TrackingStat.LOADING_SCREEN);
/*  718 */     this.loadingScreenController.checkChunkLoadingStatus(interpolation);
/*  719 */     endTiming(PerformanceGraphNode.TrackingStat.LOADING_SCREEN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void startTiming(PerformanceGraphNode.TrackingStat trackingStat) {
/*  727 */     if (Debug.stats) {
/*  728 */       StatCollector.startStat(trackingStat.statType);
/*      */     }
/*      */   }
/*      */   
/*      */   private static void endTiming(PerformanceGraphNode.TrackingStat trackingStat) {
/*  733 */     if (Debug.stats) {
/*  734 */       StatCollector.endStat(trackingStat.statType);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateEndFrame(long frameNanosLeft) {
/*  740 */     long start = System.nanoTime();
/*      */     
/*  742 */     startTiming(PerformanceGraphNode.TrackingStat.GAME_TOKEN);
/*  743 */     GameTokenProcessor.instance().processByTimeLimit(frameNanosLeft);
/*  744 */     endTiming(PerformanceGraphNode.TrackingStat.GAME_TOKEN);
/*  745 */     frameNanosLeft -= System.nanoTime() - start;
/*  746 */     frameNanosLeft = Math.max(frameNanosLeft, 1666666L);
/*      */     
/*  748 */     updateMainGameStateEndFrame(frameNanosLeft);
/*      */     
/*  750 */     if (LoadingManager.USE) {
/*  751 */       LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundUpdateCallable());
/*      */     } else {
/*      */       
/*  754 */       SoundSystemFactory.getSoundSystem().update();
/*      */     } 
/*      */ 
/*      */     
/*  758 */     if (LoadingManager.USE) {
/*  759 */       frameNanosLeft -= System.nanoTime() - start;
/*  760 */       if (frameNanosLeft < 0L) {
/*  761 */         frameNanosLeft = 0L;
/*      */       }
/*      */       
/*  764 */       int x = 0;
/*  765 */       int y = 0;
/*  766 */       if (MainGameState.getPlayerNode() != null && MainGameState.getPlayerModel() != null) {
/*  767 */         x = MainGameState.getPlayerModel().getPosition().getX().intValue();
/*  768 */         y = MainGameState.getPlayerModel().getPosition().getY().intValue();
/*      */       } 
/*  770 */       LoadingManager.INSTANCE.update(frameNanosLeft, x, y);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateMainGameStateEndFrame(long frameNanosLeft) {
/*  775 */     ArrayList<GameState> gameStates = GameStateManager.getInstance().getChildren();
/*      */     
/*  777 */     for (int i = gameStates.size() - 1; i >= 0; i--) {
/*  778 */       GameState gameState = gameStates.get(i);
/*      */       
/*  780 */       if (gameState.isActive() && gameState instanceof MainGameState) {
/*  781 */         ((MainGameState)gameState).updateEndFrame(frameNanosLeft);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void render(float interpolation) {
/*  788 */     startTiming(PerformanceGraphNode.TrackingStat.GLOBAL_RENDER_CALL);
/*  789 */     Renderer r = this.display.getRenderer();
/*  790 */     r.clearBuffers();
/*      */     
/*  792 */     if (backgroundColorChanged) {
/*  793 */       r.setBackgroundColor(backgroundColor);
/*  794 */       backgroundColorChanged = false;
/*      */     } 
/*      */     
/*  797 */     GameStateManager.getInstance().render(interpolation);
/*  798 */     GameTaskQueueManager.getManager().getQueue("render").execute();
/*  799 */     endTiming(PerformanceGraphNode.TrackingStat.GLOBAL_RENDER_CALL);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initGame() {
/*  811 */     this.fpsManager.setFps(60);
/*  812 */     this.fpsManager.setMinFps(45);
/*  813 */     this.fpsManager.setReservedTimePercent(0.001F);
/*      */ 
/*      */     
/*  816 */     GameStateManager.getInstance().attachChild((GameState)new NewLoginGameState());
/*  817 */     GameStateManager.getInstance().attachChild((GameState)new MainGameState());
/*      */     
/*  819 */     if (NetworkConfiguration.instance().isNetworking()) {
/*  820 */       if (System.getProperty("tcg.webstart") != null && System.getProperty("tcg.webstart").equalsIgnoreCase("true")) {
/*      */         
/*  822 */         String cid = System.getProperty("cid");
/*  823 */         String sid = System.getProperty("sid");
/*  824 */         String uid = System.getProperty("uid");
/*  825 */         if (cid == null || sid == null || uid == null) {
/*      */           try {
/*  827 */             Desktop.getDesktop().browse(new URI(WebStartGameState.getHomeURL()));
/*  828 */           } catch (IOException e) {
/*  829 */             e.printStackTrace();
/*  830 */           } catch (URISyntaxException e) {
/*  831 */             e.printStackTrace();
/*      */           } 
/*      */         }
/*  834 */         GameStateManager.getInstance().attachChild((GameState)new WebStartGameState(cid, sid, Integer.parseInt(uid)));
/*  835 */         GameStateManager.getInstance().activateChildNamed("webstart-game-state");
/*      */       } else {
/*  837 */         GameStateManager.getInstance().activateChildNamed("login-game-state");
/*      */       } 
/*      */     } else {
/*  840 */       throw new IllegalStateException("Game cannot be run without specifying server parameters.");
/*      */     } 
/*      */   }
/*      */   public static TcgGame getInstance() {
/*  844 */     return INSTANCE;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void reinit() {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void cleanup() {
/*  854 */     StatCollector.cleanup();
/*      */     
/*  856 */     stopResourceDownloader();
/*  857 */     NetworkHandler.close();
/*      */     
/*  859 */     if (LoadingManager.USE) {
/*  860 */       LoadingManager.INSTANCE.destroy();
/*  861 */       LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundDestroyCallable());
/*      */     } else {
/*  863 */       this.tokenProcessor.stop();
/*  864 */       SoundSystemFactory.getSoundSystem().shutdown();
/*      */     } 
/*      */     
/*  867 */     GameStateManager.getInstance().cleanup();
/*      */ 
/*      */     
/*  870 */     MouseInput.get().removeListeners();
/*  871 */     MouseInput.destroyIfInitalized();
/*  872 */     JoystickInput.destroyIfInitalized();
/*  873 */     KeyInput.destroyIfInitalized();
/*      */     
/*  875 */     TextureManager.doTextureCleanup();
/*      */   }
/*      */ 
/*      */   
/*      */   public void addQuitDialog() {
/*  880 */     if (isPlaying()) {
/*      */       
/*  882 */       MainGameState.getGuiWindowsController().toggleWindow(GameWindows.MAIN_MENU);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  897 */     else if (!isPetTutorial() && !isEquipmentTutorial()) {
/*      */       
/*  899 */       BWindow existingWindow = TcgUI.getWindowFromClass(QuitWindow.class);
/*  900 */       if (existingWindow == null) {
/*  901 */         QuitWindow window = new QuitWindow(this.resourceManager, false);
/*  902 */         window.setLayer(103);
/*  903 */         BuiSystem.getRootNode().addWindow((BWindow)window);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public String getAppId() {
/*  910 */     return "tcg";
/*      */   }
/*      */   
/*      */   private void stopResourceDownloader() {
/*  914 */     if (this.resourceDownloader != null)
/*  915 */       this.resourceDownloader.stop(); 
/*      */   }
/*      */   
/*      */   public static void finishGame() {
/*  919 */     INSTANCE.finish();
/*      */   }
/*      */   
/*      */   public static AbstractTCGRpgLoader getRpgLoader() {
/*  923 */     return INSTANCE.rpgLoader;
/*      */   }
/*      */   
/*      */   public static VisualRegistry getVisualRegistry() {
/*  927 */     return INSTANCE.visualRegistry;
/*      */   }
/*      */   
/*      */   public static ResourceManager getResourceManager() {
/*  931 */     return INSTANCE.resourceManager;
/*      */   }
/*      */   
/*      */   public static ResourceGetter getResourceGetter() {
/*  935 */     return INSTANCE.resourceGetter;
/*      */   }
/*      */   
/*      */   public static Thread getResourceDownloaderThread() {
/*  939 */     return INSTANCE.resourceDownloaderThread;
/*      */   }
/*      */   
/*      */   public static ResourceDownloader getResourceDownloader() {
/*  943 */     return INSTANCE.resourceDownloader;
/*      */   }
/*      */   
/*      */   public static TcgPreferences getPreferences() {
/*  947 */     return INSTANCE.tcgPreferences;
/*      */   }
/*      */   
/*      */   public static PropNodeRegister getPropNodeRegister() {
/*  951 */     return INSTANCE.propNodeRegister;
/*      */   }
/*      */   
/*      */   public static PropNodeRegister getMonsterRegister() {
/*  955 */     return INSTANCE.monsterRegister;
/*      */   }
/*      */   
/*      */   public static LoginResponseMessage getLoginResponse() {
/*  959 */     return INSTANCE.loginMessage;
/*      */   }
/*      */   
/*      */   public static void setLoginResponse(LoginResponseMessage responseMessage) {
/*  963 */     INSTANCE.loginMessage = responseMessage;
/*      */   }
/*      */   
/*      */   public static boolean isLoginFinished() {
/*  967 */     return INSTANCE.loginFinished;
/*      */   }
/*      */   
/*      */   public static void setLoginFinished(boolean finished) {
/*  971 */     INSTANCE.loginFinished = finished;
/*      */   }
/*      */   
/*      */   public static ParticleProcessor getParticleProcessor() {
/*  975 */     return INSTANCE.particleProcessor;
/*      */   }
/*      */   
/*      */   public static GameControlManager getGameControlManager() {
/*  979 */     return INSTANCE.gameControlManager;
/*      */   }
/*      */   
/*      */   public static DireEffectDescriptionFactory getDireEffectDescriptionFactory() {
/*  983 */     return INSTANCE.effectDescriptionFactory;
/*      */   }
/*      */   
/*      */   public static GameSettings getGameSettings() {
/*  987 */     return INSTANCE.settings;
/*      */   }
/*      */   
/*      */   public static void setBackgroundColor(ColorRGBA color) {
/*  991 */     if (!backgroundColor.equals(color)) {
/*  992 */       backgroundColor = color;
/*  993 */       backgroundColorChanged = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLocalizedText(Class<?> clazz, String key, String... parameters) {
/* 1000 */     return getLocalizedText(key, parameters);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getLocalizedTextNoWarning(String key, String... parameters) {
/* 1007 */     if (key.isEmpty()) {
/* 1008 */       return "";
/*      */     }
/*      */     String result;
/*      */     try {
/* 1012 */      result = MessageFormat.format(bundle.getString(key), (Object[])parameters);
/* 1013 */     } catch (MissingResourceException e) {
/* 1014 */       return "";
/*      */     } 
/* 1016 */     result = maleFemaleParsing(result);
/* 1017 */     return result;
/*      */   }
/*      */   
/*      */   private static String maleFemaleParsing(String result) {
/* 1021 */     boolean male = (MainGameState.getInstance() == null || MainGameState.getPlayerNode() == null || MainGameState.getPlayerModel().getPlayerDescription().getGender() == PlayerDescription.Gender.MALE);
/*      */     
/* 1023 */     while (result.contains("#name{")) {
/* 1024 */       int beginIndex = result.indexOf("#name{");
/* 1025 */       String subString = result.substring(beginIndex, result.indexOf("}", beginIndex) + 1);
/*      */       
/* 1027 */       int endIndex = 0; int i;
/* 1028 */       for (i = 6; i < subString.length(); i++) {
/* 1029 */         if (subString.charAt(i) == (male ? 'm' : 'f') && subString.charAt(i + 1) == ':') {
/* 1030 */           i += 2;
/* 1031 */           while (subString.charAt(i) == ' ')
/* 1032 */             i++; 
/* 1033 */           beginIndex = i;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1037 */       for (i = beginIndex; i < subString.length(); i++) {
/* 1038 */         if (subString.charAt(i) == '|' || subString.charAt(i) == '}') {
/* 1039 */           endIndex = i;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1043 */       if (beginIndex < endIndex) {
/* 1044 */         result = result.replace(subString, subString.substring(beginIndex, endIndex)); continue;
/*      */       } 
/* 1046 */       result = result.replace(subString, "ERROR: m/f incorrectly formated");
/*      */     } 
/* 1048 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getLocalizedText(String key, String... parameters) {
/* 1055 */     if (key.isEmpty()) {
/* 1056 */       return "";
/*      */     }
/*      */     String str;
/*      */     try {
/* 1060 */       str = MessageFormat.format(bundle.getString(key), (Object[])parameters);
/* 1061 */     } catch (MissingResourceException e) {
/* 1062 */       str = "MISSING LOCALIZATION KEY: \"" + key + "\" in gameplay sheet";
/* 1063 */       if (System.getProperty("tcg.loclog") != null && System.getProperty("tcg.loclog").equals("true")) {
/* 1064 */         LOGGER.error(str);
/*      */       }
/*      */     } 
/* 1067 */     str = maleFemaleParsing(str);
/* 1068 */     return str;
/*      */   }
/*      */   
/*      */   public static boolean isChatEnabled() {
/* 1072 */     return (getLoginResponse().getChatClientId() != -1L && checkChatSeverAddress());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean checkChatSeverAddress() {
/* 1081 */     if (NetworkConfiguration.instance().getChatHost().equals(NetworkConfiguration.instance().getHost()) && NetworkConfiguration.instance().getChatPort() == NetworkConfiguration.instance().getPort())
/*      */     {
/* 1083 */       return false; } 
/* 1084 */     return true;
/*      */   }
/*      */   
/*      */   public static LoadingScreenController getLoadingScreenController() {
/* 1088 */     return INSTANCE.loadingScreenController;
/*      */   }
/*      */   
/* 1091 */   static int newServerPort = 7000;
/*      */   
/*      */   public static void debugChangeServer() {
/* 1094 */     LoadingWindow loadingWindow = MainGameState.getLoadingWindow();
/* 1095 */     loadingWindow.loadMap("");
/* 1096 */     BuiSystem.addWindow((BWindow)loadingWindow);
/* 1097 */     NetworkHandler.instance().switchServerWhilePlaying(new InetSocketAddress("localhost", newServerPort), MainGameState.getPlayerModel().getName(), "");
/*      */   }
/*      */ 
/*      */   
/*      */   public static PropNodeRegister getCustomPortalRegister() {
/* 1102 */     return INSTANCE.customPortalRegister;
/*      */   }
/*      */   
/*      */   public static PropNodeRegister getReturnPointRegister() {
/* 1106 */     return INSTANCE.returnPointRegister;
/*      */   }
/*      */   
/*      */   public static PropNodeRegister getTownPortalRegister() {
/* 1110 */     return INSTANCE.townPortalRegister;
/*      */   }
/*      */   
/*      */   public static PropNodeRegister getWaypointDestinationRegister() {
/* 1114 */     return INSTANCE.waypointDestinationRegister;
/*      */   }
/*      */   
/*      */   public static PropNodeRegister getWaypointRegister() {
/* 1118 */     return INSTANCE.waypointRegister;
/*      */   }
/*      */   
/*      */   public static PropNodeRegister getVendorRegister() {
/* 1122 */     return INSTANCE.vendorRegister;
/*      */   }
/*      */   
/*      */   public static List<TargetedEffectNode> getTargetEffectRegister() {
/* 1126 */     return INSTANCE.targetEffectMap;
/*      */   }
/*      */   
/*      */   public static void setServerDomain(String serverDomain) {
/* 1130 */     INSTANCE.serverDomain = serverDomain;
/*      */   }
/*      */   
/*      */   public static String getServerDomain() {
/* 1134 */     if (INSTANCE != null && INSTANCE.serverDomain != null && !INSTANCE.serverDomain.isEmpty()) {
/* 1135 */       return INSTANCE.serverDomain;
/*      */     }
/* 1137 */     return "pvm-live-eu";
/*      */   }
/*      */   
/*      */   public static PublicKey getPublicKey() {
/* 1141 */     return INSTANCE.publicKey;
/*      */   }
/*      */   
/*      */   public static boolean isPlaying() {
/* 1145 */     for (GameState gameState : GameStateManager.getInstance().getChildren()) {
/* 1146 */       if ("main-game-state".equals(gameState.getName()) && gameState.isActive()) {
/* 1147 */         return true;
/*      */       }
/*      */     } 
/* 1150 */     return false;
/*      */   }
/*      */   
/*      */   public static String getUserName() {
/* 1154 */     return INSTANCE.userName;
/*      */   }
/*      */   
/*      */   public static void setUserName(String userName) {
/* 1158 */     INSTANCE.userName = userName;
/* 1159 */     getPreferences().saveUserName(userName);
/*      */   }
/*      */   
/*      */   public static boolean isPetTutorial() {
/* 1163 */     return INSTANCE.petTutorial;
/*      */   }
/*      */   
/*      */   public static void setPetTutorial(boolean petTutorial) {
/* 1167 */     INSTANCE.petTutorial = petTutorial;
/* 1168 */     if (MainGameState.getGuiWindowsController() != null && MainGameState.getGuiWindowsController() instanceof TCGWindowsController)
/*      */     {
/* 1170 */       ((TCGWindowsController)MainGameState.getGuiWindowsController()).setTutorialWindow(petTutorial ? GameWindows.PETS_AND_SKILLS : null);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isEquipmentTutorial() {
/* 1176 */     return INSTANCE.equipmentTutorial;
/*      */   }
/*      */   
/*      */   public static void setEquipmentTutorial(boolean equipmentTutorial) {
/* 1180 */     INSTANCE.equipmentTutorial = equipmentTutorial;
/* 1181 */     if (MainGameState.getGuiWindowsController() != null && MainGameState.getGuiWindowsController() instanceof TCGWindowsController)
/*      */     {
/* 1183 */       ((TCGWindowsController)MainGameState.getGuiWindowsController()).setTutorialWindow(equipmentTutorial ? GameWindows.CHARACTER : null);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isTutorialMode() {
/* 1189 */     return INSTANCE.tutorialMode;
/*      */   }
/*      */   
/*      */   public static void setTutorialMode(boolean tutorialMode) {
/* 1193 */     INSTANCE.tutorialMode = tutorialMode;
/* 1194 */     if (MainGameState.getMainHud() != null) {
/* 1195 */       MainGameState.getMainHud().getMapButton().setEnabled(!tutorialMode);
/* 1196 */       MainGameState.getMainHud().getAchievementsButton().setEnabled(!tutorialMode);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void setDueling(boolean dueling, int opponentId) {
/* 1201 */     INSTANCE.dueling = dueling;
/* 1202 */     if (dueling) {
/* 1203 */       setStartDuelMode(false);
/* 1204 */       MainGameState.getGuiWindowsController().openWindow(GameWindows.DUEL_HEALTH_BARS);
/* 1205 */       MainGameState.getDuelHealthBarWindow().setOpponentId(opponentId);
/* 1206 */       MainGameState.getInstance().addToUpdateList((Updated)new DuelCountdown());
/* 1207 */       MainGameState.getInstance().addToUpdateList((Updated)MainGameState.getDuelHealthBarWindow());
/*      */     } else {
/* 1209 */       if (MainGameState.getDuelHealthBarWindow() != null) {
/* 1210 */         PanelManager.getInstance().closeWindow((BWindow)MainGameState.getDuelHealthBarWindow());
/* 1211 */         MainGameState.getInstance().removeFromUpdateList((Updated)MainGameState.getDuelHealthBarWindow());
/* 1212 */         MainGameState.setDuelHealthBarWindow(null);
/*      */       } 
/* 1214 */       MainGameState.getMainHud().getDuelButton().setSelected(dueling);
/*      */     } 
/*      */     
/* 1217 */     MainGameState.getMainHud().getDuelCancelButton().setVisible(false);
/*      */     
/* 1219 */     BWindow window = BuiSystem.getWindow(HudInfoSetWindow.class.getSimpleName());
/* 1220 */     if (window != null) {
/* 1221 */       window.setVisible(!dueling);
/*      */     }
/* 1223 */     window = BuiSystem.getWindow(QuestHudWindow.class.getSimpleName());
/* 1224 */     if (window != null) {
/* 1225 */       window.setVisible(!dueling);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isDueling() {
/* 1231 */     return INSTANCE.dueling;
/*      */   }
/*      */   
/*      */   public static boolean isAddFriendMode() {
/* 1235 */     boolean addFriend = !INSTANCE.addFriendMode.equals(FriendModeType.OFF);
/* 1236 */     return addFriend;
/*      */   }
/*      */ 
/*      */   
/*      */   public static FriendModeType getAddFriendMode() {
/* 1241 */     return INSTANCE.addFriendMode;
/*      */   }
/*      */   
/*      */   public static void setAddFriendMode(FriendModeType friendMode) {
/* 1245 */     INSTANCE.addFriendMode = friendMode;
/* 1246 */     if (!friendMode.equals(FriendModeType.OFF)) {
/* 1247 */       setStartDuelMode(false);
/*      */     }
/*      */   }
/*      */   
/*      */   public static boolean isStartDuelMode() {
/* 1252 */     return INSTANCE.startDuelMode;
/*      */   }
/*      */   
/*      */   public static void setStartDuelMode(boolean startDuelMode) {
/* 1256 */     if (BuiSystem.getWindow(DuelAcceptWindow.class.getSimpleName()) != null) {
/*      */       return;
/*      */     }
/*      */     
/* 1260 */     INSTANCE.startDuelMode = startDuelMode;
/* 1261 */     MainGameState.getMouseCursorSetter().setCursor(startDuelMode ? (Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_DUEL : (Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_WALK);
/* 1262 */     if (startDuelMode) {
/* 1263 */       setAddFriendMode(FriendModeType.OFF);
/* 1264 */       DfxTextWindowManager.instance().getWindow("main").showText(getLocalizedText("duel.select.opponent", new String[0]));
/*      */     } 
/* 1266 */     MainGameState.getMainHud().getDuelButton().setSelected(startDuelMode);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAllowSave() {
/* 1271 */     return INSTANCE.allowSave;
/*      */   }
/*      */   
/*      */   public static void setAllowSave(boolean allowSave) {
/* 1275 */     INSTANCE.allowSave = allowSave;
/*      */   }
/*      */   
/*      */   public static int getPetTutorialId() {
/* 1279 */     return INSTANCE.petTutorialId;
/*      */   }
/*      */   
/*      */   public static void setPetTutorialId(int petTutorialId) {
/* 1283 */     INSTANCE.petTutorialId = petTutorialId;
/*      */   }
/*      */   
/*      */   public static boolean isAllowFreeChat() {
/* 1287 */     return !MainGameState.isCannedChat();
/*      */   }
/*      */   
/*      */   public static boolean isLoggedOut() {
/* 1291 */     return INSTANCE.loggedOut;
/*      */   }
/*      */   
/*      */   public static void setLoggedOut(boolean loggedOut) {
/* 1295 */     INSTANCE.loggedOut = loggedOut;
/*      */   }
/*      */   
/*      */   enum TcgLocale {
/* 1299 */     EN("en"),
/* 1300 */     FR("fr"),
/* 1301 */     NO("no"),
/* 1302 */     ZH("zh");
/*      */     
/* 1304 */     String locale = "";
/*      */     
/*      */     TcgLocale(String locale) {
/* 1307 */       this.locale = locale;
/*      */     }
/*      */     
/*      */     public String getLocale() {
/* 1311 */       return this.locale;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\TcgGame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */