/*      */ package com.funcom.tcg.client.state;
/*      */ import com.funcom.audio.SoundSystemFactory;
/*      */ import com.funcom.commons.FileUtils;
/*      */ import com.funcom.commons.PerformanceGraphNode;
import com.funcom.commons.configuration.Configuration;
/*      */ import com.funcom.commons.configuration.ExtProperties;
import com.funcom.commons.jme.TcgFont3D;
import com.funcom.commons.jme.bui.IrregularWindow;
/*      */ import com.funcom.commons.utils.GlobalTime;
import com.funcom.gameengine.GameLoop;
/*      */ import com.funcom.gameengine.OriginListener;
/*      */ import com.funcom.gameengine.Updated;
/*      */ import com.funcom.gameengine.WorldCoordinate;
/*      */ import com.funcom.gameengine.WorldOrigin;
import com.funcom.gameengine.ai.Brain;
/*      */ import com.funcom.gameengine.breadcrumbs.BreadcrumbManager;
/*      */ import com.funcom.gameengine.collisiondetection.CollisionDataProvider;
import com.funcom.gameengine.conanchat.ChatClient;
import com.funcom.gameengine.conanchat.DefaultChatUser;
/*      */ import com.funcom.gameengine.debug.TimeStamper;
/*      */ import com.funcom.gameengine.input.AfkShutdownHandler;
/*      */ import com.funcom.gameengine.input.ButtonStateTracker;
import com.funcom.gameengine.jme.DrawPassState;
import com.funcom.gameengine.jme.DrawPassType;
import com.funcom.gameengine.jme.ParticlePassNode;
import com.funcom.gameengine.jme.PassNodeForward;
/*      */ import com.funcom.gameengine.jme.PassNodeRoot;
/*      */ import com.funcom.gameengine.jme.RuntimeContentPassNode;
import com.funcom.gameengine.jme.ZBufferNode;
import com.funcom.gameengine.jme.modular.ModularDescription;
import com.funcom.gameengine.model.DfxTextRenderPass;
/*      */ import com.funcom.gameengine.model.ResourceGetter;
/*      */ import com.funcom.gameengine.model.ResourceGetterImpl;
import com.funcom.gameengine.model.SpatializedWorld;
/*      */ import com.funcom.gameengine.model.World;
import com.funcom.gameengine.model.WorldRenderPass;
/*      */ import com.funcom.gameengine.model.chunks.ChunkListener;
import com.funcom.gameengine.model.chunks.ChunkWorldNode;
/*      */ import com.funcom.gameengine.model.chunks.ManagedChunkNode;
import com.funcom.gameengine.model.input.MouseCursorSetter;
import com.funcom.gameengine.model.props.MultipleTargetsModel;
import com.funcom.gameengine.model.props.Prop;
/*      */ import com.funcom.gameengine.model.token.ActionFactory;
import com.funcom.gameengine.model.token.ChunkBuilder;
import com.funcom.gameengine.model.token.ChunkFetcherStrategy;
import com.funcom.gameengine.model.token.ChunkLoaderStrategy;
import com.funcom.gameengine.model.token.ChunkTokenFactory;
/*      */ import com.funcom.gameengine.model.token.FactoryChunkBuilder;
import com.funcom.gameengine.model.token.LoadingManagerChunkLoaderStrategy;
import com.funcom.gameengine.model.token.NormalChunkLoaderStrategy;
/*      */ import com.funcom.gameengine.model.token.TokenizedChunkLoader;
/*      */ import com.funcom.gameengine.resourcemanager.CacheType;
/*      */ import com.funcom.gameengine.resourcemanager.ResourceDownloader;
/*      */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*      */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*      */ import com.funcom.gameengine.resourcemanager.loadingmanager.SoundInverseIOStateCallable;
/*      */ import com.funcom.gameengine.spatial.BeaconFactory;
import com.funcom.gameengine.spatial.LineNode;
/*      */ import com.funcom.gameengine.utils.LoadingScreenListener;
import com.funcom.gameengine.utils.PerformanceGraphRenderPass;
import com.funcom.gameengine.utils.SpatialUtils;
import com.funcom.gameengine.utils.XmltoBinaryMapCompiler;
import com.funcom.gameengine.view.AnimationMapper;
import com.funcom.gameengine.view.AudioPlacementManager;
/*      */ import com.funcom.gameengine.view.BasicEffectsNode;
import com.funcom.gameengine.view.BuiRenderPass;
/*      */ import com.funcom.gameengine.view.CameraConfig;
/*      */ import com.funcom.gameengine.view.CollisionRenderer;
import com.funcom.gameengine.view.DfxTextWindowManager;
/*      */ import com.funcom.gameengine.view.OverheadIcons;
/*      */ import com.funcom.gameengine.view.PropNode;
import com.funcom.gameengine.view.RepresentationalNode;
/*      */ import com.funcom.gameengine.view.TextWindowsLoader;
/*      */ import com.funcom.gameengine.view.TextureLoader;
/*      */ import com.funcom.gameengine.view.TextureLoaderManager;
/*      */ import com.funcom.gameengine.view.TutorialTextWindowLoader;
/*      */ import com.funcom.gameengine.view.particles.GuiParticleJoint;
/*      */ import com.funcom.gameengine.view.particles.GuiParticleJointFactory;
import com.funcom.gameengine.view.particles.GuiParticlesConfiguration;
/*      */ import com.funcom.gameengine.view.particles.GuiParticlesRenderPass;
/*      */ import com.funcom.peeler.BananaPeel;
/*      */ import com.funcom.rpgengine2.Stat;
/*      */ import com.funcom.rpgengine2.loader.RpgLoader;
/*      */ import com.funcom.server.common.LocalGameClient;
/*      */ import com.funcom.server.common.NetworkConfiguration;
import com.funcom.tcg.TcgConstants;
/*      */ import com.funcom.tcg.client.Controls;
/*      */ import com.funcom.tcg.client.TcgGame;
/*      */ import com.funcom.tcg.client.TcgJme;
import com.funcom.tcg.client.audio.BackgroundSoundPlayer;
/*      */ import com.funcom.tcg.client.audio.SoundDataCleaner;
import com.funcom.tcg.client.breadcrumbs.BreadcrumbsUpdateListener;
/*      */ import com.funcom.tcg.client.conanchat.ClientChatUser;
/*      */ import com.funcom.tcg.client.conanchat.ConanChatOutput;
import com.funcom.tcg.client.conanchat.MessageType;
/*      */ import com.funcom.tcg.client.conanchat.TcgChatOutPut;
/*      */ import com.funcom.tcg.client.conanchat.TcgDummyChatUser;
/*      */ import com.funcom.tcg.client.controllers.ScreenCenterController;
import com.funcom.tcg.client.model.UserInputBrain;
/*      */ import com.funcom.tcg.client.model.rpg.BuffRegistry;
/*      */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*      */ import com.funcom.tcg.client.model.rpg.ClientPetDescription;
/*      */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
import com.funcom.tcg.client.model.rpg.EquipChangeListener;
import com.funcom.tcg.client.model.rpg.EquipedItemDfxHandler;
/*      */ import com.funcom.tcg.client.model.rpg.ItemRegistry;
/*      */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*      */ import com.funcom.tcg.client.model.rpg.PetRegistry;
/*      */ import com.funcom.tcg.client.model.rpg.PlayerEventsListener;
import com.funcom.tcg.client.model.rpg.RegistryFactory;
/*      */ import com.funcom.tcg.client.model.ui.HoverObjectInfoProvider;
/*      */ import com.funcom.tcg.client.net.NetworkHandler;
import com.funcom.tcg.client.net.NetworkHandlerState;
import com.funcom.tcg.client.net.NotLoggedState;
/*      */ import com.funcom.tcg.client.net.TcgNodeFactory;
/*      */ import com.funcom.tcg.client.net.TcgRefreshFactory;
/*      */ import com.funcom.tcg.client.net.projectiles.ProjectilesFactory;
import com.funcom.tcg.client.token.ClientChunkTokenFactory;
/*      */ import com.funcom.tcg.client.token.XmlActionFactory;
/*      */ import com.funcom.tcg.client.ui.DebugWindow;
import com.funcom.tcg.client.ui.Localizer;
import com.funcom.tcg.client.ui.TCGDialog;
/*      */ import com.funcom.tcg.client.ui.TCGToolTipManager;
/*      */ import com.funcom.tcg.client.ui.TcgUI;
/*      */ import com.funcom.tcg.client.ui.account.AccountButtonWindow;
/*      */ import com.funcom.tcg.client.ui.achievements.AchievementNotificationWindow;
/*      */ import com.funcom.tcg.client.ui.achievements.AchievementsWindow;
/*      */ import com.funcom.tcg.client.ui.character.CharacterEquipmentWindow;
/*      */ import com.funcom.tcg.client.ui.chat.BasicChatWindow;
/*      */ import com.funcom.tcg.client.ui.chat.ChatListener;
/*      */ import com.funcom.tcg.client.ui.chat.ChatNetworkController;
/*      */ import com.funcom.tcg.client.ui.chat.ChatOutput;
/*      */ import com.funcom.tcg.client.ui.chat.ChatUIController;
/*      */ import com.funcom.tcg.client.ui.duel.DuelAcceptWindow;
/*      */ import com.funcom.tcg.client.ui.duel.DuelHealthBarWindow;
/*      */ import com.funcom.tcg.client.ui.event.DebugEvent;
import com.funcom.tcg.client.ui.event.DebugWindowListener;
/*      */ import com.funcom.tcg.client.ui.friend.FriendModel;
/*      */ import com.funcom.tcg.client.ui.friend.FriendsWindow;
/*      */ import com.funcom.tcg.client.ui.friend.TcgFriendModel;
import com.funcom.tcg.client.ui.giftbox.HudInfoSetModel;
/*      */ import com.funcom.tcg.client.ui.giftbox.HudInfoSetWindow;
import com.funcom.tcg.client.ui.hud.GameWindows;
/*      */ import com.funcom.tcg.client.ui.hud.GuiWindowsController;
/*      */ import com.funcom.tcg.client.ui.hud.LoadingWindow;
/*      */ import com.funcom.tcg.client.ui.hud.MainHud;
/*      */ import com.funcom.tcg.client.ui.hud.NoahTutorialWindow;
/*      */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*      */ import com.funcom.tcg.client.ui.hud.RespawnWindow;
import com.funcom.tcg.client.ui.hud.TCGWindowsController;
/*      */ import com.funcom.tcg.client.ui.hud.TutorialArrowWindow;
/*      */ import com.funcom.tcg.client.ui.hud2.CurrencyWindow;
/*      */ import com.funcom.tcg.client.ui.hud2.ExperienceBarWindow;
/*      */ import com.funcom.tcg.client.ui.hud2.HudModel;
/*      */ import com.funcom.tcg.client.ui.hud2.QuestHudModel;
/*      */ import com.funcom.tcg.client.ui.hud2.QuestHudWindow;
/*      */ import com.funcom.tcg.client.ui.hud2.ResourceDownloadWindow;
/*      */ import com.funcom.tcg.client.ui.hud2.TCGClientHudModel;
/*      */ import com.funcom.tcg.client.ui.hud2.TCGGoToObjectiveModel;
/*      */ import com.funcom.tcg.client.ui.hud2.TCGHudInfoSetModel;
/*      */ import com.funcom.tcg.client.ui.hud2.TopButtonWindow;
/*      */ import com.funcom.tcg.client.ui.inventory.InventoryWindow;
/*      */ import com.funcom.tcg.client.ui.maps.MapWindow2;
import com.funcom.tcg.client.ui.maps.MapWindowModel;
import com.funcom.tcg.client.ui.maps.MapWindowModelImpl;
/*      */ import com.funcom.tcg.client.ui.pause.PauseModel;
import com.funcom.tcg.client.ui.pause.PauseModelImpl;
/*      */ import com.funcom.tcg.client.ui.pets3.PetsWindow;
/*      */ import com.funcom.tcg.client.ui.quest.QuestModel;
import com.funcom.tcg.client.ui.quest.TutorialQuestListener;
/*      */ import com.funcom.tcg.client.ui.quest2.QuestWindow2;
/*      */ import com.funcom.tcg.client.ui.reward.RewardWindowController;
/*      */ import com.funcom.tcg.client.ui.skills.SkillListModelImpl;
/*      */ import com.funcom.tcg.client.ui.tips.TipsCollection;
/*      */ import com.funcom.tcg.client.ui.vendor.VendorFullWindow;
import com.funcom.tcg.client.view.ArmourItemTextureLoader;
import com.funcom.tcg.client.view.ItemDescriptionTextureLoader;
import com.funcom.tcg.client.view.PetTextureLoader;
import com.funcom.tcg.client.view.ScrollItemTextureLoader;
/*      */ import com.funcom.tcg.client.view.modular.ClientDescribedModularNode;
import com.funcom.tcg.client.view.modular.PetEventListener;
/*      */ import com.funcom.tcg.client.view.modular.PlayerModularDescription;
/*      */ import com.funcom.tcg.maps.LoadingScreenPropertyReader;
/*      */ import com.funcom.tcg.net.DefaultSubscriptionState;
/*      */ import com.funcom.tcg.net.message.LoginResponseMessage;
/*      */ import com.funcom.tcg.portals.TownPortalPropertyReader;
/*      */ import com.funcom.tcg.rpg.AbstractTCGRpgLoader;
import com.funcom.tcg.token.TCGWorld;
import com.funcom.tcg.utils.DevelopmentTimeUtils;
import com.funcom.util.DebugManager;
/*      */ import com.jme.input.KeyInput;
/*      */ import com.jme.input.controls.Binding;
/*      */ import com.jme.input.controls.GameControl;
/*      */ import com.jme.input.controls.GameControlManager;
/*      */ import com.jme.input.controls.binding.KeyboardBinding;
import com.jme.renderer.ColorRGBA;
/*      */ import com.jme.renderer.Renderer;
import com.jme.renderer.pass.Pass;
/*      */ import com.jme.scene.Controller;
/*      */ import com.jme.scene.Node;
/*      */ import com.jme.scene.Spatial;
/*      */ import com.jme.scene.state.CullState;
import com.jme.scene.state.RenderState;
/*      */ import com.jme.scene.state.ZBufferState;
/*      */ import com.jme.system.DisplaySystem;
/*      */ import com.jme.util.Debug;
/*      */ import com.jme.util.stat.StatCollector;
/*      */ import com.jmex.bui.BRootNode;
/*      */ import com.jmex.bui.BWindow;
/*      */ import com.jmex.bui.BuiSystem;
import com.jmex.bui.dragndrop.BDragNDrop;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.editors.swing.pass.BloomPassEditor;
/*      */ import com.jmex.effects.glsl.BloomRenderPass;
/*      */ import com.jmex.game.state.GameStateManager;

import java.awt.Container;
/*      */ import java.io.IOException;
/*      */ import java.io.StringReader;
/*      */ import java.nio.channels.UnresolvedAddressException;
/*      */ import java.util.ArrayList;
import java.util.HashMap;
/*      */ import java.util.Iterator;
import java.util.LinkedList;
/*      */ import java.util.List;
import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
/*      */ import java.util.prefs.Preferences;
/*      */ import javax.swing.JFrame;
/*      */ import org.apache.log4j.Level;
/*      */ import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
/*      */ import org.jdom.Document;
import org.jdom.JDOMException;
import com.funcom.gameengine.resourcemanager.loadingmanager.SoundReleaseInactiveDataCallable;
/*      */ import org.lwjgl.opengl.Display;
/*      */ 
/*      */ public class MainGameState extends TcgGameState implements DebugWindowListener, CollisionDataProvider, GameLoop, Localizer {
/*  161 */   private static final Logger LOGGER = Logger.getLogger(MainGameState.class.getName());
/*      */   
/*      */   public static final String STATE_NAME = "main-game-state";
/*      */   
/*      */   private static MainGameState INSTANCE;
/*      */   
/*      */   private static final String MAIN_HUD_PATH = "gui/peeler/main_hud.xml";
/*      */   
/*      */   private static final String FRIENDS_WINDOW_PATH = "gui/peeler/window_friends.xml";
/*      */   
/*      */   private boolean showCollision;
/*      */   
/*      */   private boolean silentExit = false;
/*      */   
/*      */   private LoadingWindow loadingWindow;
/*      */   private DebugWindow debugWindow;
/*      */   private CollisionRenderer collisionRenderer;
/*      */   private PropNode playerNode;
/*      */   private float playerScale;
/*      */   private ChunkWorldNode chunkWorldNode;
/*      */   private TcgNodeFactory tcgNodeFactory;
/*      */   private TcgRefreshFactory tcgRefreshFactory;
/*      */   private ItemRegistry itemRegistry;
/*      */   private PetRegistry petRegistry;
/*      */   private BuffRegistry buffRegistry;
/*      */   private HoverObjectInfoProvider hoverObjectInfoProvider;
/*      */   private TCGWorld world;
/*      */   private TCGToolTipManager toolTipManager;
/*      */   private double maxInteractionDistance;
/*      */   private RespawnWindow respawnWindow;
/*      */   private MapWindow2 mapWindow;
/*      */   private ProjectilesFactory projectileFactory;
/*      */   private GuiParticlesRenderPass guiParticlesRenderPass;
/*      */   private TCGCursorSetter cursorSetter;
/*      */   private TCGGameControlsController gameControlsController;
/*      */   private Map<String, String> actionToCursorMapping;
/*      */   private TownPortalPropertyReader townPortalPropertyReader;
/*      */   private LoadingScreenPropertyReader loadingScreenPropertyReader;
/*      */   private QuestModel questModel;
/*      */   private QuestHudModel questHudModel;
/*      */   private FriendModel friendModel;
/*      */   private ChatUIController chatUIController;
/*      */   private ChatNetworkController chatNetworkController;
/*      */   private ChatClient chatClient;
/*      */   private TokenizedChunkLoader chunkLoader;
/*      */   private GuiWindowsController windowsController;
/*      */   private QuestWindow2 questWindow;
/*      */   private CharacterEquipmentWindow characterWindow;
/*      */   private InventoryWindow inventoryWindow;
/*      */   private TextureLoaderManager textureLoaderManager;
/*      */   private RewardWindowController rewardWindowController;
/*      */   public static final int FULLSCREEN_LAYER = 1;
/*      */   public static final int HUD_LAYER = 2;
/*      */   private static final int DEATH_LAYER = 3;
/*      */   public static final int MAIN_MENU_LAYER = 4;
/*      */   public static final int DIALOG_LAYER = 101;
/*      */   public static final int QUIT_DIALOG_LAYER = 103;
/*      */   public static final int LOADING_LAYER = 100;
/*      */   private static SkillListModelImpl skillListModel;
/*      */   private List<Updated> updateClients;
/*      */   private List<Updated> updateClientsToRemove;
/*      */   private VendorFullWindow vendorWindow;
/*      */   private List<String> visitedMaps;
/*  224 */   private List<String> accessKeys = new ArrayList<String>();
/*  225 */   private List<Long> accessKeyExpireTimes = new ArrayList<Long>();
/*      */   private BreadcrumbManager breadcrumbManager;
/*      */   private PauseModel pauseModel;
/*      */   private PetsWindow petsWindow;
/*      */   private AchievementsWindow achievementWindow;
/*      */   private AccountButtonWindow accountButtonWindow;
/*      */   private TCGClientHudModel hudModel;
/*      */   private TipsCollection tips;
/*      */   private PetEventListener petEventsListener;
/*      */   private NoahTutorialWindow noahTutorialWindow;
/*      */   private TutorialArrowWindow arrowWindow;
/*      */   private TopButtonWindow topButtonWindow;
/*      */   private BasicChatWindow basicChatWindow;
/*      */   private FriendsWindow friendsWindow;
/*      */   private CurrencyWindow currencyWindow;
/*      */   private AchievementNotificationWindow achievementNotificationWindow;
/*      */   private DuelHealthBarWindow duelHealthBarWindow;
/*      */   private MainHud mainHud;
/*      */   private BackgroundSoundPlayer backgroundSoundPlayer;
/*      */   private AfkShutdownHandler afkShutdownHandler;
/*      */   private SoundDataCleaner soundDataCleaner;
/*      */   private TCGGoToObjectiveModel tcgGoToObjectiveModel;
/*      */   private BreadcrumbsUpdateListener breadcrumbListener;
/*      */   
/*      */   public MainGameState() {
/*  250 */     INSTANCE = this;
/*      */     
/*  252 */     initializeMouseCursorSetter();
/*  253 */     initializeLoadingScreenPropertyReader();
/*  254 */     initLoadingWindow();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  260 */     return "main-game-state";
/*      */   }
/*      */   
/*      */   public void addToUpdateList(Updated updated) {
/*  264 */     this.updateClients.add(updated);
/*      */   }
/*      */   
/*      */   public void removeFromUpdateList(Updated updated) {
/*  268 */     this.updateClientsToRemove.add(updated);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void initialize() {
/*  273 */     initSystem();
/*  274 */     initGame();
/*  275 */     initTextureLoaderFactoryManager();
/*      */   }
/*      */   
/*      */   public void cleanup() {
/*  279 */     if (this.chatClient != null) {
/*  280 */       this.chatClient.shutdown();
/*      */     }
/*      */   }
/*      */   
/*      */   protected void activated() {
/*  285 */     createPlayerCharacter();
/*  286 */     initializeSkillListModel();
/*  287 */     String mapName = initializeMap();
/*  288 */     initializeHudModel();
/*  289 */     initializeTips();
/*  290 */     initializeMainWindow();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  295 */     resetChunkFactory();
/*  296 */     initializeToolTipManager();
/*  297 */     initializeBreadcrumbs();
/*  298 */     initializeBeacons();
/*      */ 
/*      */ 
/*      */     
/*  302 */     this.world.setPlayerNode(this.playerNode);
/*  303 */     this.chunkWorldNode.setCoordinateReference(this.playerNode.getPosition());
/*      */     
/*  305 */     DfxTextWindowManager.instance().setPlayer((RepresentationalNode)this.playerNode);
/*      */ 
/*      */     
/*  308 */     checkBinaryFiles(mapName);
/*  309 */     this.world.loadMap(mapName, NetworkHandler.instance().getIOHandler());
/*      */     
/*  311 */     configureCameraController();
/*      */     
/*  313 */     this.world.updateRenderState();
/*      */   }
/*      */   
/*      */   protected void deactivated() {
/*  317 */     this.accessKeys.clear();
/*  318 */     this.accessKeyExpireTimes.clear();
/*  319 */     TcgGame.setLoginResponse(null);
/*  320 */     if (LoadingManager.USE)
/*  321 */       LoadingManager.INSTANCE.actuallyClearLoadingTokens(); 
/*  322 */     removeAllButLoadingScreenWindow();
/*  323 */     this.world.cleanupRuntimeContent(false);
/*  324 */     this.world.getChunkWorldNode().getChunkWorldInfo().setMapId(null);
/*      */     
/*  326 */     this.playerNode = null;
/*  327 */     initLoadingWindow();
/*  328 */     BuiSystem.getRootNode().removeAllWindows();
/*  329 */     this.questModel.clearQuests();
/*  330 */     this.backgroundSoundPlayer.reset();
/*  331 */     if (LoadingManager.USE) {
/*  332 */       LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundReleaseInactiveDataCallable());
/*      */     } else {
/*  334 */       SoundSystemFactory.getSoundSystem().releaseInactiveData();
/*  335 */     }  NetworkHandler.instance().setState((NetworkHandlerState)new NotLoggedState());
/*  336 */     this.tcgGoToObjectiveModel.dismiss();
/*  337 */     this.breadcrumbManager.reset();
/*  338 */     this.questModel.removeChangeListener((QuestModel.QuestChangeListener)this.breadcrumbListener);
/*  339 */     this.tips.dismiss();
/*      */     
/*  341 */     TcgGame.getLoadingScreenController().clearListeners();
/*  342 */     TcgGame.getLoadingScreenController().addListener((LoadingScreenListener)this.soundDataCleaner);
/*  343 */     TcgGame.getLoadingScreenController().addListener((LoadingScreenListener)this.backgroundSoundPlayer);
/*  344 */     TcgGame.getLoadingScreenController().addListener((LoadingScreenListener)TcgGame.getResourceManager());
/*      */     
/*  346 */     this.characterWindow = null;
/*  347 */     if (this.petsWindow != null)
/*  348 */       this.petsWindow.dismiss(); 
/*  349 */     this.petsWindow = null;
/*  350 */     if (this.achievementWindow != null)
/*  351 */       this.achievementWindow.dismiss(); 
/*  352 */     this.achievementWindow = null;
/*      */     
/*  354 */     if (this.currencyWindow != null) {
/*  355 */       this.currencyWindow.dismiss();
/*      */     }
/*  357 */     this.currencyWindow = null;
/*  358 */     this.cursorSetter.setDefaultCursor();
/*  359 */     TcgGame.setLoginFinished(false);
/*  360 */     GameStateManager.getInstance().activateChildNamed("login-game-state");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void notifyNetworkDisconnect(LocalGameClient.DisconnectReason reason) {
/*  368 */     GameStateManager.getInstance().deactivateChildNamed("main-game-state");
/*  369 */     GameStateManager.getInstance().activateChildNamed("login-game-state");
/*      */     
/*  371 */     if (!this.silentExit) {
/*  372 */       String titleKey, messageKey; if (reason == LocalGameClient.DisconnectReason.CLIENT_QUIT) {
/*  373 */         titleKey = "ingame.error.afk.title";
/*  374 */         messageKey = "ingame.error.afk.text";
/*  375 */       } else if (reason == LocalGameClient.DisconnectReason.SERVER_SHUTDOWN) {
/*  376 */         titleKey = "ingame.server.shutdown.title";
/*  377 */         messageKey = "ingame.server.shutdown.text";
/*  378 */       } else if (reason == LocalGameClient.DisconnectReason.LOGGED_IN_FROM_ANOTHER_CLIENT) {
/*  379 */         titleKey = "ingame.relogged.title";
/*  380 */         messageKey = "ingame.relogged.text";
/*      */       } else {
/*  382 */         titleKey = "loginwindow.networkerrordialog.title";
/*  383 */         messageKey = "ingame.error.lostconnection.text";
/*      */       } 
/*  385 */       TCGDialog.showMessage(titleKey, messageKey, this, null);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  390 */     TcgGame.setLoggedOut(true);
/*  391 */     this.afkShutdownHandler.resetHasShutdown();
/*      */     
/*  393 */     this.silentExit = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getLocalizedText(Class<?> clazz, String key, String... parameters) {
/*  398 */     return TcgGame.getLocalizedText(key, parameters);
/*      */   }
/*      */   
/*      */   private void initSystem() {
/*  402 */     ResourceManager resourceManager = TcgGame.getResourceManager();
/*  403 */     TcgFont3D.getFont();
/*      */     
/*  405 */     PassNodeRoot worldRootNode = new PassNodeRoot("world-node");
/*  406 */     worldRootNode.setRenderQueueMode(2);
/*  407 */     addBackCulling((Node)worldRootNode);
/*  408 */     addZBuffer((Node)worldRootNode);
/*      */     
/*  410 */     PassNodeRoot textRootNode = new PassNodeRoot("text-node");
/*  411 */     textRootNode.setRenderQueueMode(2);
/*  412 */     addBackCulling((Node)textRootNode);
/*  413 */     addZBuffer((Node)textRootNode);
/*      */     
/*  415 */     setMapWindow(new MapWindow2(resourceManager, (MapWindowModel)new MapWindowModelImpl()));
/*      */ 
/*      */     
/*  418 */     this.world = new TCGWorld(TcgGame.getResourceManager(), (ResourceGetter)new ResourceGetterImpl(resourceManager), TcgGame.getResourceDownloader());
/*      */     
/*  420 */     this.world.setWorldNode(worldRootNode);
/*  421 */     this.world.setTextNode(textRootNode);
/*      */ 
/*      */     
/*  424 */     this.world.setChuckWorldRoot((Node)new PassNodeForward("ChuckWorldRoot"));
/*      */ 
/*      */     
/*  427 */     RuntimeContentPassNode runtimeContentPassNode = new RuntimeContentPassNode();
/*  428 */     this.world.setRuntimeContentNode(runtimeContentPassNode);
/*      */ 
/*      */     
/*  431 */     this.world.setParticleNode((Node)new ParticlePassNode("ParticleContent", new DrawPassState(DrawPassType.TRANSPARENT_CONTENT)));
/*      */ 
/*      */     
/*  434 */     TcgGame.getLoadingScreenController().setWorld(this.world);
/*      */ 
/*      */     
/*  437 */     this.soundDataCleaner = new SoundDataCleaner();
/*  438 */     TcgGame.getLoadingScreenController().addListener((LoadingScreenListener)this.soundDataCleaner);
/*  439 */     this.backgroundSoundPlayer = new BackgroundSoundPlayer(resourceManager);
/*  440 */     TcgGame.getLoadingScreenController().addListener((LoadingScreenListener)this.backgroundSoundPlayer);
/*      */ 
/*      */     
/*  443 */     worldRootNode.attachChild((Spatial)ZBufferNode.INSTANCE);
/*      */     
/*  445 */     getPassManager().setWorldRenderPass(new WorldRenderPass((SpatializedWorld)this.world));
/*  446 */     getPassManager().setBuiRenderPass(new BuiRenderPass(BuiSystem.getRootNode()));
/*  447 */     getPassManager().add((Pass)new DfxTextRenderPass((Node)textRootNode));
/*  448 */     BuiSystem.getRootNode().setTooltipTimeout(0.5F);
/*      */     
/*  450 */     this.guiParticlesRenderPass = new GuiParticlesRenderPass(GuiParticlesConfiguration.read());
/*  451 */     getPassManager().setGuiParticlesRenderPass(this.guiParticlesRenderPass);
/*      */ 
/*      */     
/*  454 */     boolean enableBloom = false;
/*  455 */     if (enableBloom) {
/*  456 */       BloomRenderPass bloomRenderPass = new BloomRenderPass(DisplaySystem.getDisplaySystem().getRenderer().getCamera(), 1);
/*      */       
/*  458 */       if (bloomRenderPass.isSupported()) {
/*  459 */         bloomRenderPass.add((Spatial)worldRootNode);
/*  460 */         bloomRenderPass.setUseCurrentScene(true);
/*      */         
/*  462 */         getPassManager().setBloomRenderPass(bloomRenderPass);
/*      */ 
/*      */         
/*  465 */         JFrame bloomEditor = new JFrame("Bloom editor");
/*  466 */         bloomEditor.setContentPane((Container)new BloomPassEditor(bloomRenderPass));
/*  467 */         bloomEditor.pack();
/*  468 */         bloomEditor.setVisible(true);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  474 */     if (Debug.stats) {
/*  475 */       getPassManager().setPerformanceGraphRenderPass(new PerformanceGraphRenderPass(new PerformanceGraphNode()));
/*      */     }
/*      */ 
/*      */     
/*  479 */     this.updateClients = new LinkedList<Updated>();
/*  480 */     this.updateClientsToRemove = new CopyOnWriteArrayList<Updated>();
/*      */ 
/*      */     
/*  483 */     addSomeDebugCommands();
/*      */ 
/*      */     
/*  486 */     this.tcgNodeFactory = new TcgNodeFactory();
/*  487 */     this.tcgRefreshFactory = new TcgRefreshFactory();
/*      */     
/*  489 */     AbstractTCGRpgLoader rpgLoader = TcgGame.getRpgLoader();
/*  490 */     this.projectileFactory = new ProjectilesFactory(rpgLoader);
/*      */     
/*  492 */     TcgNodeFactory.addDefaultBuilders(this.tcgNodeFactory, (RpgLoader)rpgLoader);
/*  493 */     TcgRefreshFactory.addDefaultRefreshers(this.tcgRefreshFactory, (RpgLoader)rpgLoader);
/*  494 */     this.maxInteractionDistance = rpgLoader.getMaxInteractionDistance();
/*  495 */     initializeToolTipManagerRpg(resourceManager, (RpgLoader)rpgLoader);
/*      */ 
/*      */     
/*  498 */     CameraConfig.instance().setPerspectiveProjection();
/*  499 */     addToUpdateList((Updated)this.mapWindow);
/*      */     
/*  501 */     this.rewardWindowController = new RewardWindowController();
/*  502 */     addToUpdateList((Updated)this.rewardWindowController);
/*      */     
/*  504 */     initAwayFromKeyboardShutdown();
/*      */     
/*  506 */     initializeMouseCursorSetter();
/*  507 */     initializeActionsToCursorMapping();
/*      */     
/*  509 */     this.itemRegistry = RegistryFactory.createItemRegistry((RpgLoader)TcgGame.getRpgLoader());
/*  510 */     this.petRegistry = RegistryFactory.createPetRegistry(TcgGame.getResourceManager(), TcgGame.getVisualRegistry(), getItemRegistry());
/*      */     
/*  512 */     initializeBuffRegistry();
/*  513 */     initializePlayerInput();
/*  514 */     initializeQuestModel();
/*  515 */     createTextWindows();
/*  516 */     chunkSpecificSetup();
/*  517 */     setupCollisionRenderer();
/*  518 */     initializeTownportalsProperties();
/*  519 */     initializePauseModel();
/*      */   }
/*      */   
/*      */   private void initAwayFromKeyboardShutdown() {
/*  523 */     String afkValue = System.getProperty("tcg.afkshutdown");
/*  524 */     if (afkValue == null || Boolean.valueOf(afkValue).booleanValue()) {
/*      */       
/*  526 */       this.afkShutdownHandler = new AfkShutdownHandler(new Runnable()
/*      */           {
/*      */             public void run()
/*      */             {
/*  530 */               TcgGame.setLoggedOut(true);
/*  531 */               NetworkHandler.instance().getIOHandler().stop();
/*      */             }
/*      */           });
/*      */ 
/*      */       
/*  536 */       addToUpdateList((Updated)this.afkShutdownHandler);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void initTextureLoaderFactoryManager() {
/*  541 */     ResourceManager resourceManager = TcgGame.getResourceManager();
/*      */     
/*  543 */     this.textureLoaderManager = new TextureLoaderManager();
/*      */     
/*  545 */     AbstractTCGRpgLoader rpgLoader = TcgGame.getRpgLoader();
/*  546 */     this.textureLoaderManager.registerLoader("scrollitem-loader", (TextureLoader)new ScrollItemTextureLoader(resourceManager, rpgLoader.getItemManager()));
/*      */     
/*  548 */     this.textureLoaderManager.registerLoader("pet-loader", (TextureLoader)new PetTextureLoader(resourceManager, this.petRegistry));
/*  549 */     this.textureLoaderManager.registerLoader("itemdesc-loader", (TextureLoader)new ItemDescriptionTextureLoader(resourceManager));
/*  550 */     this.textureLoaderManager.registerLoader("armor-loader", (TextureLoader)new ArmourItemTextureLoader(resourceManager));
/*      */   }
/*      */   
/*      */   private void addZBuffer(Node rootNode) {
/*  554 */     ZBufferState buf = DisplaySystem.getDisplaySystem().getRenderer().createZBufferState();
/*  555 */     buf.setEnabled(true);
/*  556 */     buf.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);
/*  557 */     rootNode.setRenderState((RenderState)buf);
/*      */   }
/*      */   
/*      */   private void addBackCulling(Node rootNode) {
/*  561 */     CullState cs = DisplaySystem.getDisplaySystem().getRenderer().createCullState();
/*  562 */     cs.setCullFace(CullState.Face.Back);
/*  563 */     cs.setEnabled(true);
/*  564 */     rootNode.setRenderState((RenderState)cs);
/*      */   }
/*      */   
/*      */   private void addSomeDebugCommands() {
/*  568 */     GameControlManager gcm = TcgGame.getGameControlManager();
/*      */     
/*  570 */     GameControl serverControl = gcm.addControl(Controls.CID_DEBUG_SERVER.id);
/*  571 */     serverControl.addBinding((Binding)new KeyboardBinding(67));
/*      */     
/*  573 */     this.updateClients.add(new ButtonStateTracker(Controls.controlConstantsToGameControls(gcm, new Controls[] { Controls.CID_DEBUG_SERVER }))
/*      */         {
/*      */           
/*      */           protected void pressed(GameControl gameControl)
/*      */           {
/*  578 */             if (PanelManager.getInstance().isKeyInputWindowOpen()) {
/*      */               return;
/*      */             }
/*      */             
/*  582 */             if (Controls.CID_DEBUG_SERVER.is(gameControl))
/*      */             {
/*  584 */               if (KeyInput.get().isKeyDown(56) && KeyInput.get().isKeyDown(54)) {
/*  585 */                 String address = NetworkHandler.instance().getIOHandler().getTargetAddres().toString();
/*  586 */                 if (MainGameState.getBasicChatWindow() != null) {
/*  587 */                   MainGameState.getBasicChatWindow().showMessage("System", address, BasicChatWindow.ChatMode.MODERATOR);
/*      */                 }
/*      */               } 
/*      */             }
/*      */           }
/*      */         });
/*      */     
/*  594 */     if (!DebugManager.getInstance().isDebugEnabled())
/*      */       return; 
/*  596 */     gcm.addControl(Controls.CID_DEBUG_CRITICAL.id).addBinding((Binding)new KeyboardBinding(Controls.CID_DEBUG_CRITICAL.keyCode));
/*  597 */     gcm.addControl(Controls.CID_DEBUG_DAMAGE.id).addBinding((Binding)new KeyboardBinding(Controls.CID_DEBUG_DAMAGE.keyCode));
/*  598 */     gcm.addControl(Controls.CID_DEBUG_HEAL.id).addBinding((Binding)new KeyboardBinding(Controls.CID_DEBUG_HEAL.keyCode));
/*  599 */     gcm.addControl(Controls.CID_DEBUG_QUESTSWITCH.id).addBinding((Binding)new KeyboardBinding(Controls.CID_DEBUG_QUESTSWITCH.keyCode));
/*  600 */     gcm.addControl(Controls.CID_DEBUG_OPENDEBUGWINDOW.id).addBinding((Binding)new KeyboardBinding(Controls.CID_DEBUG_OPENDEBUGWINDOW.keyCode));
/*  601 */     gcm.addControl(Controls.CID_DEBUG_OPEN_INVENTORY.id).addBinding((Binding)new KeyboardBinding(Controls.CID_DEBUG_OPEN_INVENTORY.keyCode));
/*  602 */     gcm.addControl(Controls.CID_DEBUG_SCENE_MONITOR.id).addBinding((Binding)new KeyboardBinding(Controls.CID_DEBUG_SCENE_MONITOR.keyCode));
/*  603 */     gcm.addControl(Controls.CID_DEBUG_DFXTEXT.id).addBinding((Binding)new KeyboardBinding(Controls.CID_DEBUG_DFXTEXT.keyCode));
/*  604 */     gcm.addControl(Controls.CID_DEBUG_TOGGLESOUNDSYSTEMIO.id).addBinding((Binding)new KeyboardBinding(Controls.CID_DEBUG_TOGGLESOUNDSYSTEMIO.keyCode));
/*      */     
/*  606 */     gcm.addControl(Controls.CID_DEBUG_TOGGLETIMESTAMPING.id).addBinding((Binding)new KeyboardBinding(Controls.CID_DEBUG_TOGGLETIMESTAMPING.keyCode));
/*  607 */     this.debugWindow = new DebugWindow();
/*  608 */     this.debugWindow.addDebugListener(this);
/*  609 */     this.debugWindow.setState(1, true);
/*  610 */     this.debugWindow.setState(3, false);
/*  611 */     addToUpdateList((Updated)this.debugWindow);
/*      */     
/*  613 */     this.updateClients.add(new ButtonStateTracker(Controls.controlConstantsToGameControls(gcm, Controls.getDebugControlConstants()))
/*      */         {
/*      */           
/*      */           protected void pressed(GameControl gameControl)
/*      */           {
/*  618 */             if (PanelManager.getInstance().isKeyInputWindowOpen()) {
/*      */               return;
/*      */             }
/*      */             
/*  622 */             if (Controls.CID_DEBUG_CRITICAL.is(gameControl)) {
/*  623 */               MainGameState.getPlayerNode().critFloatingText(30);
/*  624 */             } else if (Controls.CID_DEBUG_DAMAGE.is(gameControl)) {
/*  625 */               MainGameState.getPlayerNode().damageFloatingText(314);
/*  626 */             } else if (Controls.CID_DEBUG_HEAL.is(gameControl)) {
/*  627 */               MainGameState.getPlayerNode().healFloatingText(43);
/*  628 */             } else if (Controls.CID_DEBUG_QUESTSWITCH.is(gameControl)) {
/*      */               
/*  630 */               BasicEffectsNode basicEffectsNode = MainGameState.getPlayerNode().getBasicEffectsNode();
/*  631 */               if (basicEffectsNode.getState() == OverheadIcons.State.NONE)
/*  632 */               { basicEffectsNode.setState(OverheadIcons.State.QUEST_OFFER_HIGHER); }
/*  633 */               else if (basicEffectsNode.getState() == OverheadIcons.State.QUEST_OFFER_HIGHER)
/*  634 */               { basicEffectsNode.setState(OverheadIcons.State.QUEST_HANDING_HIGHER); }
/*  635 */               else if (basicEffectsNode.getState() == OverheadIcons.State.QUEST_HANDING_HIGHER)
/*  636 */               { basicEffectsNode.setState(OverheadIcons.State.VENDOR); }
/*      */               else
/*  638 */               { basicEffectsNode.setState(OverheadIcons.State.NONE); } 
/*  639 */             } else if (Controls.CID_DEBUG_OPEN_INVENTORY.is(gameControl)) {
/*  640 */               MainGameState.this.windowsController.toggleWindow(GameWindows.INVENTORY);
/*  641 */             } else if (Controls.CID_DEBUG_OPENDEBUGWINDOW.is(gameControl)) {
/*  642 */               if (!MainGameState.this.debugWindow.isShowing()) {
/*  643 */                 PanelManager.getInstance().addWindow((BWindow)MainGameState.this.debugWindow);
/*      */               }
/*  645 */             } else if (!Controls.CID_DEBUG_SCENE_MONITOR.is(gameControl)) {
/*      */ 
/*      */               
/*  648 */               if (Controls.CID_DEBUG_DFXTEXT.is(gameControl)) {
/*      */ 
/*      */ 
/*      */                 
/*  652 */                 int reward1 = (int)(20.0D * Math.random());
/*  653 */                 MainGameState.this.rewardWindowController.addReward(Integer.valueOf(reward1));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  659 */                 Object[] reward = MainGameState.this.petRegistry.getAllPets().toArray();
/*  660 */                 ClientPetDescription clientPetDescription = (ClientPetDescription)reward[(int)(Math.random() * (reward.length - 1))];
/*  661 */                 MainGameState.this.rewardWindowController.addReward(new ClientPet(clientPetDescription, clientPetDescription));
/*      */               }
/*  663 */               else if (Controls.CID_DEBUG_TOGGLESOUNDSYSTEMIO.is(gameControl)) {
/*      */                 
/*  665 */                 boolean newState = true;
/*  666 */                 if (LoadingManager.USE) {
/*  667 */                   LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundInverseIOStateCallable());
/*      */                 } else {
/*  669 */                   newState = !SoundSystemFactory.getSoundSystem().isIOEnabled();
/*  670 */                   SoundSystemFactory.getSoundSystem().setIOEnabled(newState);
/*  671 */                   System.err.println("Toggled SoundSystem io: " + (newState ? "enabled" : "disabled"));
/*      */                 } 
/*  673 */               } else if (Controls.CID_DEBUG_TOGGLETIMESTAMPING.is(gameControl)) {
/*  674 */                 TimeStamper.INSTANCE.enable(!TimeStamper.INSTANCE.isStampingEnabled());
/*      */               } 
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   private void initGame() {}
/*      */ 
/*      */   
/*      */   private String initializeMap() {
/*  686 */     String mapName, bootMapPath = TcgGame.getLoginResponse().getMapId();
/*      */     
/*  688 */     if (bootMapPath == null) {
/*  689 */       mapName = TcgJme.getDefaultPlayerPosition().getMapId();
/*      */     } else {
/*  691 */       mapName = DevelopmentTimeUtils.getPathName(bootMapPath);
/*      */     } 
/*  693 */     TcgGame.setTutorialMode(mapName.contains("tutorial"));
/*      */     
/*  695 */     return mapName;
/*      */   }
/*      */   
/*      */   private void initializeTips() {
/*  699 */     String userName = "PvM" + TcgGame.getLoginResponse().getInventoryId();
/*  700 */     this.tips = new TipsCollection(TcgGame.getPreferences().getTips(userName), userName);
/*      */   }
/*      */   
/*      */   private void initializeHudModel() {
/*  704 */     if (this.hudModel == null) {
/*  705 */       this.hudModel = new TCGClientHudModel(getPlayerModel());
/*      */     } else {
/*  707 */       this.hudModel.setClientPlayer(getPlayerModel());
/*      */     } 
/*      */   }
/*      */   
/*      */   private void initializeGuiParticles() {
/*  712 */     ExtProperties eventHooks = (ExtProperties)TcgGame.getResourceManager().getResource(ExtProperties.class, "gui_particles/eventhooks.props");
/*      */     
/*  714 */     GuiParticleJointFactory factory = new GuiParticleJointFactory(TcgGame.getResourceManager(), DisplaySystem.getDisplaySystem().getRenderer().getCamera());
/*      */     
/*  716 */     GuiParticleJoint petCollectedParticle = factory.createFromPath(eventHooks.getProperty("PET_COLLECTED"));
/*  717 */     GuiParticleJoint itemCollectedParticle = factory.createFromPath(eventHooks.getProperty("ITEM_COLLECTED"));
/*      */     
/*  719 */     GuiParticlesRenderPass renderPass = getPassManager().getGuiParticlesRenderPass();
/*  720 */     renderPass.addGuiParticleJoint(petCollectedParticle);
/*  721 */     renderPass.addGuiParticleJoint(itemCollectedParticle);
/*      */   }
/*      */   
/*      */   private void initializeBeacons() {
/*  725 */     this.tcgGoToObjectiveModel = new TCGGoToObjectiveModel(getQuestModel());
/*      */     
/*  727 */     BeaconFactory beaconFactory = new BeaconFactory(TcgGame.getDireEffectDescriptionFactory(), getWorld());
/*      */     
/*  729 */     beaconFactory.setModel((MultipleTargetsModel)this.tcgGoToObjectiveModel);
/*      */     
/*  731 */     TcgGame.getLoadingScreenController().addListener((LoadingScreenListener)this.tcgGoToObjectiveModel);
/*  732 */     getPlayerNode().addController(new Controller()
/*      */         {
/*      */           public void update(float time) {
/*  735 */             MainGameState.this.tcgGoToObjectiveModel.getClosestToPlayer();
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   private void initializePauseModel() {
/*  741 */     setPauseModel((PauseModel)new PauseModelImpl());
/*  742 */     this.updateClients.add(this.pauseModel);
/*      */   }
/*      */   
/*      */   private void initializeSkillListModel() {
/*  746 */     skillListModel = new SkillListModelImpl((ClientPlayer)getPlayerModel());
/*      */   }
/*      */   
/*      */   private void refreshWindows() {
/*  750 */     this.basicChatWindow = null;
/*  751 */     this.friendsWindow = null;
/*  752 */     this.noahTutorialWindow = null;
/*      */     
/*  754 */     TcgGame.setLoggedOut(false);
/*      */   }
/*      */   
/*      */   public void initializeMainWindow() {
/*  758 */     if (TcgGame.isLoggedOut()) {
/*  759 */       refreshWindows();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  769 */     boolean isDead = false;
/*  770 */     if (BuiSystem.getRootNode().getAllWindows().contains(this.respawnWindow)) {
/*  771 */       isDead = true;
/*      */     }
/*  773 */     removeAllButLoadingScreenWindow();
/*  774 */     getGuiParticlesRenderPass().removeAllGuiParticleJoints();
/*  775 */     initializeGuiParticles();
/*  776 */     LocalClientPlayer playerModel = getPlayerModel();
/*  777 */     TcgGame.getLoadingScreenController().addListener((LoadingScreenListener)this.hudModel);
/*  778 */     ResourceManager resourceManager = TcgGame.getResourceManager();
/*  779 */     if (this.respawnWindow != null) {
/*  780 */       removeFromUpdateList((Updated)this.respawnWindow);
/*      */     }
/*  782 */     this.respawnWindow = new RespawnWindow();
/*  783 */     addToUpdateList((Updated)this.respawnWindow);
/*  784 */     this.respawnWindow.setLayer(3);
/*      */     
/*  786 */     if (isDead) {
/*  787 */       BuiSystem.getRootNode().addWindow((BWindow)this.respawnWindow);
/*      */     }
/*  789 */     initMonsterTooltip();
/*      */     
/*  791 */     IrregularWindow.setNonProcessedEventsNotifier(this.gameControlsController);
/*  792 */     addToUpdateList((Updated)PanelManager.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  797 */     BananaPeel bananaPeel = (BananaPeel)resourceManager.getResource(BananaPeel.class, "gui/peeler/main_hud.xml", CacheType.NOT_CACHED);
/*  798 */     this.mainHud = new MainHud("gui/peeler/main_hud.xml", bananaPeel, resourceManager, (HudModel)this.hudModel, this.toolTipManager);
/*      */ 
/*      */     
/*  801 */     bananaPeel = (BananaPeel)resourceManager.getResource(BananaPeel.class, "gui/peeler/achievements_subsections.xml", CacheType.NOT_CACHED);
/*      */     
/*  803 */     this.achievementNotificationWindow = new AchievementNotificationWindow(this.questModel, bananaPeel);
/*  804 */     this.achievementNotificationWindow.setLocation(DisplaySystem.getDisplaySystem().getWidth() / 2 - 350, 115);
/*      */     
/*  806 */     this.achievementNotificationWindow.setVisible(false);
/*      */ 
/*      */     
/*  809 */     ExperienceBarWindow experienceBarWindow = new ExperienceBarWindow((HudModel)this.hudModel, resourceManager);
/*      */     
/*  811 */     TCGHudInfoSetModel hudInfoSetModel = new TCGHudInfoSetModel(playerModel.getGiftBoxes(), playerModel.getInventory(), this.tips);
/*      */     
/*  813 */     HudInfoSetWindow hudInfoSetWindow = new HudInfoSetWindow(HudInfoSetWindow.class.getSimpleName(), (HudInfoSetModel)hudInfoSetModel, resourceManager);
/*      */     
/*  815 */     hudInfoSetWindow.setLocation(0, experienceBarWindow.getY() - experienceBarWindow.getHeight() / 2 - hudInfoSetWindow.getHeight());
/*      */ 
/*      */     
/*  818 */     int tokens = 0;
/*  819 */     int coins = 0;
/*  820 */     if (this.currencyWindow != null) {
/*  821 */       tokens = Integer.parseInt(this.currencyWindow.getTokenTotal().getText());
/*  822 */       coins = Integer.parseInt(this.currencyWindow.getCoinTotal().getText());
/*      */     } 
/*      */     
/*  825 */     this.currencyWindow = new CurrencyWindow(resourceManager, playerModel.getInventory(), 88, DisplaySystem.getDisplaySystem().getHeight() - 91, coins, tokens);
/*      */ 
/*      */     
/*  828 */     int tbwWidth = 110;
/*  829 */     int tbwHeight = 50;
/*  830 */     int rightTBWOffset = 10;
/*  831 */     int topTBWOffset = 6;
/*      */     
/*  833 */     this.topButtonWindow = new TopButtonWindow((HudModel)this.hudModel, resourceManager, this.toolTipManager);
/*  834 */     this.topButtonWindow.setBounds(DisplaySystem.getDisplaySystem().getWidth() - tbwWidth - rightTBWOffset, DisplaySystem.getDisplaySystem().getHeight() - tbwHeight - topTBWOffset, tbwWidth, tbwHeight);
/*      */ 
/*      */ 
/*      */     
/*  838 */     int accountWindowHeight = 54;
/*  839 */     int accountWindowY = this.topButtonWindow.getY() - accountWindowHeight - rightTBWOffset;
/*      */ 
/*      */     
/*  842 */     int rightAccountOffset = 10;
/*  843 */     this.accountButtonWindow = new AccountButtonWindow(playerModel, resourceManager, new TcgRegistrationHandler(this), this);
/*      */     
/*  845 */     this.accountButtonWindow.setLocation(DisplaySystem.getDisplaySystem().getWidth() - this.accountButtonWindow.getWidth() - rightAccountOffset, accountWindowY);
/*      */ 
/*      */     
/*  848 */     this.accountButtonWindow.setVisible(TcgGame.isAllowSave());
/*      */     
/*  850 */     int noahWidth = 650;
/*      */     
/*  852 */     if (this.noahTutorialWindow == null) {
/*  853 */       this.noahTutorialWindow = new NoahTutorialWindow(resourceManager, false);
/*  854 */       this.noahTutorialWindow.setVisible(TcgGame.isTutorialMode());
/*      */     } 
/*  856 */     this.noahTutorialWindow.setBounds(DisplaySystem.getDisplaySystem().getWidth() / 2 - noahWidth / 2 + 10, 115, noahWidth, 120);
/*      */ 
/*      */     
/*  859 */     if (this.arrowWindow == null) {
/*  860 */       this.arrowWindow = new TutorialArrowWindow(resourceManager, 0, 0);
/*  861 */       this.arrowWindow.setVisible(false);
/*      */     } 
/*      */     
/*  864 */     if (TcgGame.isChatEnabled()) {
/*  865 */       if (this.basicChatWindow == null) {
/*  866 */         this.basicChatWindow = new BasicChatWindow(getChatNetworkController(), TcgGame.getResourceManager());
/*      */         
/*  868 */         this.basicChatWindow.setVisible(false);
/*  869 */         this.basicChatWindow.setLayer(0);
/*      */       } 
/*  871 */       BuiSystem.addWindow((BWindow)this.basicChatWindow);
/*      */       
/*  873 */       if (this.friendsWindow == null) {
/*  874 */         bananaPeel = (BananaPeel)resourceManager.getResource(BananaPeel.class, "gui/peeler/window_friends.xml", CacheType.NOT_CACHED);
/*  875 */         this.friendsWindow = new FriendsWindow("gui/peeler/window_friends.xml", bananaPeel, resourceManager, (HudModel)this.hudModel, this.friendModel);
/*  876 */         this.friendsWindow.setVisible(false);
/*  877 */         this.friendsWindow.setLayer(2);
/*      */       } 
/*  879 */       BuiSystem.addWindow((BWindow)this.friendsWindow);
/*      */     } 
/*  881 */     if (this.questHudModel != null)
/*  882 */       this.questHudModel.dismiss(); 
/*  883 */     this.questHudModel = new QuestHudModel(getQuestModel());
/*  884 */     setQuestHudModel(this.questHudModel);
/*  885 */     this.questHudModel.refresh();
/*  886 */     QuestHudWindow questHudWindow = new QuestHudWindow(this.questHudModel, resourceManager);
/*  887 */     questHudWindow.setLocation(0, experienceBarWindow.getY() - experienceBarWindow.getHeight() / 2 - hudInfoSetWindow.getHeight());
/*      */ 
/*      */ 
/*      */     
/*  891 */     ResourceDownloadWindow rdWindow = new ResourceDownloadWindow(resourceManager);
/*  892 */     addToUpdateList((Updated)rdWindow);
/*  893 */     rdWindow.setLocation(0, experienceBarWindow.getY() - rdWindow.getHeight());
/*  894 */     BuiSystem.addWindow((BWindow)rdWindow);
/*      */ 
/*      */     
/*  897 */     this.windowsController = (GuiWindowsController)new TCGWindowsController();
/*      */ 
/*      */     
/*  900 */     if (System.getProperty("tcg.nohud") == null || !System.getProperty("tcg.nohud").equals(String.valueOf(true))) {
/*  901 */       BuiSystem.addWindow((BWindow)this.mainHud);
/*  902 */       BuiSystem.addWindow((BWindow)this.achievementNotificationWindow);
/*  903 */       BuiSystem.addWindow((BWindow)experienceBarWindow);
/*  904 */       BuiSystem.addWindow((BWindow)hudInfoSetWindow);
/*  905 */       BuiSystem.addWindow((BWindow)this.currencyWindow);
/*  906 */       BuiSystem.addWindow((BWindow)this.accountButtonWindow);
/*  907 */       BuiSystem.addWindow((BWindow)this.noahTutorialWindow);
/*  908 */       BuiSystem.addWindow((BWindow)this.arrowWindow);
/*      */       
/*  910 */       BuiSystem.addWindow((BWindow)questHudWindow);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void removeAllButLoadingScreenWindow() {
/*  915 */     BRootNode guiRoot = BuiSystem.getRootNode();
/*      */     
/*  917 */     clearPeelerWindows(guiRoot);
/*  918 */     List<BWindow> allWindows = new ArrayList<BWindow>(guiRoot.getAllWindows());
/*      */     
/*  920 */     for (int ii = allWindows.size() - 1; ii >= 0; ii--) {
/*  921 */       if (!(allWindows.get(ii) instanceof LoadingWindow) && !(allWindows.get(ii) instanceof DuelAcceptWindow)) {
/*  922 */         ((BWindow)allWindows.get(ii)).dismiss();
/*      */       }
/*      */       
/*  925 */       if (allWindows.get(ii) instanceof DuelAcceptWindow) {
/*  926 */         DuelAcceptWindow window = (DuelAcceptWindow)allWindows.get(ii);
/*  927 */         window.setLocation(DisplaySystem.getDisplaySystem().getWidth() / 2 - window.getWidth() / 2, DisplaySystem.getDisplaySystem().getHeight() / 2 - window.getHeight() / 2);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void clearPeelerWindows(BRootNode guiRoot) {
/*  933 */     if (this.petsWindow != null) {
/*  934 */       guiRoot.removeWindow((BWindow)this.petsWindow);
/*      */     }
/*  936 */     if (this.questWindow != null) {
/*  937 */       guiRoot.removeWindow((BWindow)this.questWindow);
/*      */     }
/*  939 */     if (this.achievementWindow != null) {
/*  940 */       guiRoot.removeWindow((BWindow)this.achievementWindow);
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkBinaryFiles(String mapName) {
/*  945 */     if (TcgGame.getResourceManager().isUseJars())
/*      */       return;  try {
/*  947 */       (new XmltoBinaryMapCompiler()).checkFileAge(mapName);
/*  948 */     } catch (JDOMException e) {
/*  949 */       e.printStackTrace();
/*  950 */     } catch (IOException e) {
/*  951 */       e.printStackTrace();
/*      */     } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initializeQuestModel() {
/*  968 */     if (this.questModel == null) {
/*  969 */       this.questModel = new QuestModel();
/*  970 */       setQuestModel(this.questModel);
/*  971 */       this.questModel.addChangeListener((QuestModel.QuestChangeListener)new TutorialQuestListener());
/*      */     } 
/*      */   }
/*      */   
/*      */   private void initializeBreadcrumbs() {
/*  976 */     this.breadcrumbManager = new BreadcrumbManager(TcgGame.getResourceManager(), (Prop)getPlayerModel());
/*      */ 
/*      */     
/*  979 */     this.breadcrumbManager.setWorld((World)this.world);
/*  980 */     this.breadcrumbManager.setDireEffectDescriptionFactory(TcgGame.getDireEffectDescriptionFactory());
/*  981 */     this.breadcrumbManager.setChunkWorldNode(this.chunkWorldNode);
/*      */     
/*  983 */     this.breadcrumbListener = new BreadcrumbsUpdateListener(this.chunkWorldNode, this.breadcrumbManager, (ClientPlayer)getPlayerModel());
/*  984 */     this.questModel.addChangeListener((QuestModel.QuestChangeListener)this.breadcrumbListener);
/*      */   }
/*      */ 
/*      */   
/*      */   private void initializeTownportalsProperties() {
/*  989 */     this.townPortalPropertyReader = new TownPortalPropertyReader();
/*      */     try {
/*  991 */       this.townPortalPropertyReader.readProperties((String)TcgGame.getResourceManager().getResource(String.class, "configuration/portals/townportals.properties"));
/*      */     }
/*  993 */     catch (IOException e) {
/*  994 */       e.printStackTrace();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void initializeLoadingScreenPropertyReader() {
/*  999 */     this.loadingScreenPropertyReader = new LoadingScreenPropertyReader();
/*      */     try {
/* 1001 */       this.loadingScreenPropertyReader.readProperties((String)TcgGame.getResourceManager().getResource(String.class, "configuration/loadingscreen/loadingscreen.properties"));
/*      */     }
/* 1003 */     catch (IOException e) {
/* 1004 */       e.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void initializeActionsToCursorMapping() {
/* 1010 */     this.actionToCursorMapping = new HashMap<String, String>();
/* 1011 */     StringReader stringReader = null;
/*      */     try {
/* 1013 */       Properties p = new Properties();
/* 1014 */       stringReader = new StringReader((String)TcgGame.getResourceManager().getResource(String.class, "configuration/cursor/action_to_cursor_mapping.properties"));
/*      */       
/* 1016 */       p.load(stringReader);
/* 1017 */       for (Object oKey : p.keySet()) {
/* 1018 */         String key = (String)oKey;
/* 1019 */         this.actionToCursorMapping.put(key, p.getProperty(key));
/*      */       } 
/* 1021 */     } catch (IOException e) {
/* 1022 */       throw new IllegalStateException(e);
/*      */     } finally {
/* 1024 */       if (stringReader != null)
/* 1025 */         stringReader.close(); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void initializeMouseCursorSetter() {
/* 1030 */     this.cursorSetter = new TCGCursorSetter();
/* 1031 */     this.cursorSetter.setDefaultCursor();
/* 1032 */     BDragNDrop.instance().addDragNDropListener(this.cursorSetter);
/*      */   }
/*      */   
/*      */   private void initializeBuffRegistry() {
/* 1036 */     this.buffRegistry = new BuffRegistry();
/* 1037 */     this.buffRegistry.readData();
/*      */   }
/*      */   
/*      */   private void resetChunkFactory() {
/* 1041 */     if (this.chunkLoader != null) {
/* 1042 */       this.chunkWorldNode.removeChunkListener((ChunkListener)this.chunkLoader);
/* 1043 */       if (LoadingManager.USE) {
/* 1044 */         this.chunkWorldNode.removeChunkListener(LoadingManager.INSTANCE.getDistanceQueue());
/*      */       }
/* 1046 */       this.chunkLoader = null;
/*      */     } 
/*      */     
/* 1049 */     ResourceGetterImpl resourceGetterImpl = new ResourceGetterImpl(TcgGame.getResourceManager());
/*      */     
/* 1051 */     FactoryChunkBuilder factoryChunkBuilder = new FactoryChunkBuilder((ChunkTokenFactory)new ClientChunkTokenFactory(TcgGame.getDireEffectDescriptionFactory(), TcgGame.getResourceManager()));
/*      */ 
/*      */ 
/*      */     
/* 1055 */     if (LoadingManager.USE) {
/* 1056 */       this.chunkLoader = new TokenizedChunkLoader((ChunkBuilder)factoryChunkBuilder, (ChunkLoaderStrategy)new LoadingManagerChunkLoaderStrategy(this.chunkWorldNode, new ChunkFetcherStrategy((ResourceGetter)resourceGetterImpl), (ResourceGetter)resourceGetterImpl), (ResourceGetter)resourceGetterImpl, NetworkHandler.instance().getIOHandler(), (ActionFactory)new XmlActionFactory((ResourceGetter)resourceGetterImpl, NetworkHandler.instance().getIOHandler()));
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1063 */       this.chunkLoader = new TokenizedChunkLoader((ChunkBuilder)factoryChunkBuilder, (ChunkLoaderStrategy)new NormalChunkLoaderStrategy(this.chunkWorldNode, new ChunkFetcherStrategy((ResourceGetter)resourceGetterImpl)), (ResourceGetter)resourceGetterImpl, NetworkHandler.instance().getIOHandler(), (ActionFactory)new XmlActionFactory((ResourceGetter)resourceGetterImpl, NetworkHandler.instance().getIOHandler()));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1071 */     this.chunkWorldNode.addChunkListener((ChunkListener)this.chunkLoader);
/* 1072 */     if (LoadingManager.USE) {
/* 1073 */       this.chunkWorldNode.addChunkListener(LoadingManager.INSTANCE.getDistanceQueue());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void setupCollisionRenderer() {
/* 1079 */     this.collisionRenderer = new CollisionRenderer();
/* 1080 */     this.collisionRenderer.setChunkWorldNode(this.chunkWorldNode);
/*      */   }
/*      */   
/*      */   private void chunkSpecificSetup() {
/* 1084 */     this.chunkWorldNode = new ChunkWorldNode(null);
/*      */     
/* 1086 */     ManagedChunkNode managedChunkNode = new ManagedChunkNode(this.chunkWorldNode);
/* 1087 */     this.chunkWorldNode.setManagedChunkNode(managedChunkNode);
/* 1088 */     WorldOrigin.instance().addListener((OriginListener)managedChunkNode);
/* 1089 */     this.world.setChunkWorldNode(this.chunkWorldNode);
/*      */   }
/*      */ 
/*      */   
/*      */   private void initializeToolTipManagerRpg(ResourceManager resourceManager, RpgLoader rpgLoader) {
/* 1094 */     this.toolTipManager = new TCGToolTipManager(resourceManager, this);
/* 1095 */     this.toolTipManager.setItemManager(rpgLoader.getItemManager());
/* 1096 */     this.toolTipManager.setElementManager(rpgLoader.getElementManager());
/*      */   }
/*      */   
/*      */   private void initializeToolTipManager() {
/* 1100 */     this.toolTipManager.setPetRegistry(this.petRegistry);
/* 1101 */     this.toolTipManager.setItemRegistry(this.itemRegistry);
/* 1102 */     this.toolTipManager.setClientPlayer((ClientPlayer)getPlayerModel());
/*      */   }
/*      */   
/*      */   private void configureCameraController() {
/* 1106 */     ScreenCenterController camController = new ScreenCenterController();
/* 1107 */     camController.setPropNode(this.playerNode);
/* 1108 */     this.playerNode.addController((Controller)camController);
/*      */   }
/*      */   
/*      */   private void initializePlayerInput() {
/* 1112 */     this.gameControlsController = new TCGGameControlsController(TcgGame.getGameControlManager(), (SpatializedWorld)this.world);
/* 1113 */     this.updateClients.add(this.gameControlsController);
/*      */   }
/*      */   
/*      */   private void createPlayerCharacter() {
/* 1117 */     WorldCoordinate position = TcgJme.getDefaultPlayerPosition();
/* 1118 */     CameraConfig.instance().moveCameraTo(position);
/* 1119 */     LocalClientPlayer clientPlayer = new LocalClientPlayer(TcgGame.getLoginResponse().getNick(), TcgGame.getLoginResponse().getEmail(), position, (Brain)new UserInputBrain(this, this.gameControlsController, this.gameControlsController.getContextualControl(), this.gameControlsController.getPrimarySkill(), this.gameControlsController.getSecondarySkill(), this.gameControlsController.getForceAttack(), this.gameControlsController.getPet1(), this.gameControlsController.getPet2(), this.gameControlsController.getPet3()), 0.6000000238418579D);
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
/* 1131 */     clientPlayer.setFaction(TcgGame.getLoginResponse().getFaction());
/* 1132 */     clientPlayer.registerForPetMessageProcessing();
/* 1133 */     clientPlayer.setStat(Short.valueOf((short)10), new Stat(Short.valueOf((short)10), Stat.floatToInt(5.0F)));
/* 1134 */     clientPlayer.setStat(Short.valueOf((short)23), new Stat(Short.valueOf((short)23), Stat.floatToInt(200.0F)));
/*      */ 
/*      */     
/* 1137 */     LoginResponseMessage loginMsg = TcgGame.getLoginResponse();
/* 1138 */     clientPlayer.syncPlayerProperties(loginMsg);
/*      */ 
/*      */     
/* 1141 */     this.visitedMaps = TcgGame.getLoginResponse().getMapsVisited();
/* 1142 */     downloadJarsNeeded();
/*      */ 
/*      */     
/* 1145 */     this.playerNode = new PropNode((Prop)clientPlayer, 3, "", TcgGame.getDireEffectDescriptionFactory());
/*      */     
/* 1147 */     this.playerNode.setCullHint(Spatial.CullHint.Never);
/* 1148 */     PlayerModularDescription playerModularDescription = new PlayerModularDescription((ClientPlayer)clientPlayer, TcgGame.getVisualRegistry());
/* 1149 */     ClientDescribedModularNode modularNode = new ClientDescribedModularNode((ModularDescription)playerModularDescription, (AnimationMapper)this.playerNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getVisualRegistry(), TcgGame.getResourceManager());
/*      */ 
/*      */     
/* 1152 */     modularNode.reloadCharacter();
/* 1153 */     this.playerNode.attachRepresentation((Spatial)modularNode);
/* 1154 */     this.petEventsListener = new PetEventListener(modularNode);
/* 1155 */     clientPlayer.addPlayerEventsListener((PlayerEventsListener)this.petEventsListener);
/*      */     
/* 1157 */     SpatialUtils.addShadow(this.playerNode, TcgGame.getResourceManager());
/* 1158 */     this.playerNode.updateGeometricState(0.0F, true);
/* 1159 */     this.playerNode.initializeAllEffects((ResourceGetter)new ResourceGetterImpl(TcgGame.getResourceManager()), getWorld().getParticleSurface());
/*      */     
/* 1161 */     this.playerNode.setDamageIndicator("-");
/* 1162 */     this.playerNode.setHealIndicator("+");
/* 1163 */     this.playerNode.setGainManaIndicator("+");
/* 1164 */     this.playerNode.enableManaGainFloatingText(true);
/* 1165 */     this.playerNode.setDamageTextColor(ColorRGBA.red.clone());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1171 */     ClientPlayerEventsHandler clientPlayerEventsHandler = new ClientPlayerEventsHandler((ClientPlayer)clientPlayer, this.playerNode);
/* 1172 */     clientPlayer.addPlayerEventsListener((PlayerEventsListener)clientPlayerEventsHandler);
/* 1173 */     clientPlayer.getSubscriptionState().addListener(clientPlayerEventsHandler);
/* 1174 */     ClientPlayerPetDfxHandler playerEventsListener = new ClientPlayerPetDfxHandler((ClientPlayer)clientPlayer, this.playerNode);
/* 1175 */     TcgGame.getLoadingScreenController().addListener(playerEventsListener);
/* 1176 */     clientPlayer.addPlayerEventsListener((PlayerEventsListener)playerEventsListener);
/* 1177 */     clientPlayer.getEquipDoll().addChangeListener((EquipChangeListener)new EquipedItemDfxHandler(this.playerNode));
/*      */ 
/*      */     
/* 1180 */     clientPlayer.setActivePetFromClassId(loginMsg.getActivePetClassId());
/*      */     
/* 1182 */     clientPlayer.addSupport(this.questModel);
/*      */     
/* 1184 */     this.playerScale = this.playerNode.getLocalScale().length();
/*      */ 
/*      */     
/* 1187 */     TcgGame.getPropNodeRegister().addPropNode(this.playerNode);
/*      */     
/* 1189 */     AudioPlacementManager.getInstance().setReferenceCoord(this.playerNode.getPosition());
/*      */ 
/*      */     
/* 1192 */     this.chatUIController = new ChatUIController(this);
/* 1193 */     if (TcgGame.isChatEnabled()) {
/* 1194 */       String tcgChatProp = System.getProperty("tcg.chat");
/* 1195 */       if (tcgChatProp != null && Boolean.parseBoolean(tcgChatProp)) {
/* 1196 */         TcgDummyChatUser tcgDummyChatUser = new TcgDummyChatUser(TcgGame.getLoginResponse().getChatClientId(), TcgGame.getLoginResponse().getChatClientCookie());
/*      */         
/* 1198 */         TcgChatOutPut tcgChatOutPut = new TcgChatOutPut(tcgDummyChatUser);
/*      */         
/* 1200 */         ResourceManager resourceManager = TcgGame.getResourceManager();
/* 1201 */         Document chatMessageResource = (Document)resourceManager.getResourceByLocale(Document.class, "configuration/chatmessages.xml", CacheType.NOT_CACHED);
/*      */ 
/*      */         
/* 1204 */         this.chatNetworkController = new ChatNetworkController((ChatOutput)tcgChatOutPut, chatMessageResource, this.chatUIController);
/*      */         
/* 1206 */         TcgFriendModel tcgFriendModel = new TcgFriendModel((ChatListener)this.chatNetworkController);
/* 1207 */         setFriendModel((FriendModel)tcgFriendModel);
/*      */       } else {
/* 1209 */         ClientChatUser chatUser = new ClientChatUser(TcgGame.getLoginResponse().getChatClientId(), TcgGame.getLoginResponse().getChatClientCookie());
/*      */         
/* 1211 */         Preferences clientConfiguration = Configuration.instance().getClientConfiguration();
/* 1212 */         this.chatClient = new ChatClient((DefaultChatUser)chatUser, clientConfiguration.get("chat.hostname", NetworkConfiguration.instance().getChatHost()), Integer.parseInt(clientConfiguration.get("chat.port", "" + NetworkConfiguration.instance().getChatPort())));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1218 */         clientPlayer.setChatController(new ClientChatController(this.chatClient));
/*      */         
/* 1220 */         ConanChatOutput output = new ConanChatOutput(this.chatClient);
/* 1221 */         ResourceManager resourceManager = TcgGame.getResourceManager();
/* 1222 */         Document chatMessageResource = (Document)resourceManager.getResourceByLocale(Document.class, "configuration/chatmessages.xml", CacheType.NOT_CACHED);
/*      */ 
/*      */         
/* 1225 */         this.chatNetworkController = new ChatNetworkController((ChatOutput)output, chatMessageResource, this.chatUIController);
/* 1226 */         ((ClientChatUser)this.chatClient.getChatUser()).setChatNetworkController(this.chatNetworkController);
/*      */         
/* 1228 */         TcgFriendModel tcgFriendModel = new TcgFriendModel((ChatListener)this.chatNetworkController);
/* 1229 */         setFriendModel((FriendModel)tcgFriendModel);
/*      */ 
/*      */         
/*      */         try {
/* 1233 */           this.chatClient.establishConnection();
/* 1234 */           chatUser.logon();
/* 1235 */         } catch (IOException e) {
/* 1236 */           LOGGER.log((Priority)Level.ERROR, "Server said that chat is enabled, but connection failed - chat server is down, waiting for server to reinitialize connection...", e);
/*      */         
/*      */         }
/* 1239 */         catch (UnresolvedAddressException e) {
/* 1240 */           LOGGER.log((Priority)Level.ERROR, "Server said that chat is enabled, but connection failed - chat server is down, waiting for server to reinitialize connection...", e);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updatePetSize(ClientPet activePet) {}
/*      */ 
/*      */ 
/*      */   
/*      */   private void downloadJarsNeeded() {
/* 1253 */     ResourceDownloader downloader = TcgGame.getResourceDownloader();
/* 1254 */     if (downloader != null) {
/*      */       LoadingWindow loadingWindow;
/* 1256 */       if (TcgUI.isWindowOpen(LoadingWindow.class)) {
/* 1257 */         loadingWindow = (LoadingWindow)TcgUI.getWindowFromClass(LoadingWindow.class);
/* 1258 */         if (!loadingWindow.isValid()) {
/*      */ 
/*      */           
/* 1261 */           BuiSystem.addWindow((BWindow)loadingWindow);
/* 1262 */           loadingWindow.validate();
/*      */         } 
/*      */       } else {
/*      */         
/* 1266 */         loadingWindow = getLoadingWindow();
/* 1267 */         BuiSystem.addWindow((BWindow)loadingWindow);
/*      */       } 
/* 1269 */       for (String path : this.visitedMaps) {
/* 1270 */         String str1 = FileUtils.trimTailingSlashes(path) + ".jar";
/* 1271 */         downloader.downloadMapAndDependencies(str1);
/*      */       } 
/* 1273 */       String jarNeeded = FileUtils.trimTailingSlashes(TcgGame.getLoginResponse().getMapId()) + ".jar";
/*      */ 
/*      */       
/* 1276 */       downloader.downloadMapAndDependencies(jarNeeded);
/* 1277 */       TcgGame.getResourceDownloaderThread().setPriority(10);
/*      */       
/* 1279 */       loadingWindow.setMinDownloadFiles(downloader.getJarIndex(jarNeeded) - 1);
/* 1280 */       loadingWindow.setMinDownloadFiles(downloader.getJarIndex(jarNeeded) - 1);
/* 1281 */       loadingWindow.setTotalDownloadFiles(downloader.getNumberOfDownloadRequestsQueued());
/* 1282 */       loadingWindow.setMinDownloadFiles(downloader.getJarIndex(jarNeeded) - 1);
/*      */       
/* 1284 */       while (!downloader.isCompletelyDownloaded(jarNeeded)) {
/* 1285 */         loadingWindow.updateDownloaderProgress(downloader);
/* 1286 */         float RESOURCE_LOADING_TIMER = 0.7F;
/* 1287 */         GameStateManager.getInstance().render(RESOURCE_LOADING_TIMER);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1292 */         if (Display.isCreated()) {
/* 1293 */           Display.update();
/*      */         }
/*      */         
/* 1296 */         if (Display.isCloseRequested()) {
/* 1297 */           System.exit(0);
/*      */         }
/*      */       } 
/*      */       
/* 1301 */       loadingWindow.updateDownloaderProgress(downloader);
/* 1302 */       TcgGame.getResourceDownloaderThread().setPriority(1);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void initMonsterTooltip() {
/* 1307 */     this.hoverObjectInfoProvider = new HoverObjectInfoProvider();
/*      */   }
/*      */ 
/*      */   
/*      */   private void createTextWindows() {
/* 1312 */     Document doc = TcgGame.getResourceGetter().getDocument("xml_layout/dfx_windows.xml", CacheType.NOT_CACHED);
/* 1313 */     TextWindowsLoader windowLoader = new TextWindowsLoader(TcgGame.getResourceManager());
/* 1314 */     this.world.addDfxTextFactories(windowLoader.loadWindows(doc));
/*      */     
/* 1316 */     Document tutorialDoc = TcgGame.getResourceGetter().getDocument("xml_layout/tutorial_dfx_windows.xml", CacheType.NOT_CACHED);
/*      */     
/* 1318 */     TutorialTextWindowLoader tutorialTextWindowLoader = new TutorialTextWindowLoader(TcgGame.getResourceManager());
/* 1319 */     this.world.addDfxTextFactories(tutorialTextWindowLoader.loadWindows(tutorialDoc));
/*      */   }
/*      */   
/*      */   public void update(float tpf) {
/* 1323 */     long beforeTime = GlobalTime.getInstance().getCurrentTime();
/* 1324 */     GlobalTime.getInstance().run();
/* 1325 */     long deltaT = GlobalTime.getInstance().getCurrentTime() - beforeTime;
/* 1326 */     if (beforeTime == 0L) {
/* 1327 */       deltaT = 0L;
/*      */     }
/* 1329 */     float dT = (float)deltaT / 1000.0F;
/*      */     
/* 1331 */     if (Debug.stats) {
/* 1332 */       StatCollector.addStat(PerformanceGraphNode.TrackingStat.FPS.statType, (1000L / deltaT));
/*      */     }
/*      */     
/* 1335 */     startTiming(PerformanceGraphNode.TrackingStat.CLIENTS_UPDATE);
/* 1336 */     for (Updated updateClient : this.updateClients)
/* 1337 */       updateClient.update(dT); 
/* 1338 */     Iterator<Updated> removeIterator = this.updateClientsToRemove.iterator();
/* 1339 */     while (removeIterator.hasNext()) {
/* 1340 */       Updated updated = removeIterator.next();
/* 1341 */       this.updateClients.remove(updated);
/* 1342 */       this.updateClientsToRemove.remove(updated);
/*      */     } 
/* 1344 */     endTiming(PerformanceGraphNode.TrackingStat.CLIENTS_UPDATE);
/*      */     
/* 1346 */     startTiming(PerformanceGraphNode.TrackingStat.CHUNK_MANAGEMENT);
/* 1347 */     this.chunkWorldNode.checkWorldOriginUpdate();
/* 1348 */     endTiming(PerformanceGraphNode.TrackingStat.CHUNK_MANAGEMENT);
/*      */     
/* 1350 */     startTiming(PerformanceGraphNode.TrackingStat.LOGIC_UPDATE);
/* 1351 */     getPassManager().updatePasses(dT);
/* 1352 */     endTiming(PerformanceGraphNode.TrackingStat.LOGIC_UPDATE);
/*      */     
/* 1354 */     startTiming(PerformanceGraphNode.TrackingStat.NETWORK_HANDLING);
/* 1355 */     NetworkHandler.instance().update(dT);
/* 1356 */     endTiming(PerformanceGraphNode.TrackingStat.NETWORK_HANDLING);
/*      */     
/* 1358 */     startTiming(PerformanceGraphNode.TrackingStat.BREADCRUMBS);
/* 1359 */     if (this.breadcrumbManager.isInitialized())
/* 1360 */       this.breadcrumbManager.update(dT); 
/* 1361 */     endTiming(PerformanceGraphNode.TrackingStat.BREADCRUMBS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void startTiming(PerformanceGraphNode.TrackingStat trackingStat) {
/* 1368 */     if (Debug.stats)
/* 1369 */       StatCollector.startStat(trackingStat.statType); 
/*      */   }
/*      */   
/*      */   private static void endTiming(PerformanceGraphNode.TrackingStat trackingStat) {
/* 1373 */     if (Debug.stats)
/* 1374 */       StatCollector.endStat(trackingStat.statType); 
/*      */   }
/*      */   
/*      */   public void updateEndFrame(long frameNanosLeft) {
/* 1378 */     startTiming(PerformanceGraphNode.TrackingStat.NETWORK_HANDLING);
/* 1379 */     NetworkHandler.instance().updateByTimeLimit(frameNanosLeft);
/* 1380 */     endTiming(PerformanceGraphNode.TrackingStat.NETWORK_HANDLING);
/*      */   }
/*      */ 
/*      */   
/*      */   public void render(float tpf) {
/* 1385 */     Renderer r = DisplaySystem.getDisplaySystem().getRenderer();
/* 1386 */     getPassManager().renderPasses(r);
/* 1387 */     renderDebug(r);
/*      */   }
/*      */   
/*      */   private void renderDebug(Renderer r) {
/* 1391 */     if (this.showCollision)
/* 1392 */       this.collisionRenderer.renderLines(r); 
/*      */   }
/*      */   public void debugStateChanged(DebugEvent e) {
/*      */     float zoomFactor;
/* 1396 */     switch (e.getType()) {
/*      */       case 1:
/* 1398 */         if (e.getState()) {
/* 1399 */           CameraConfig.instance().setPerspectiveProjection(); break;
/*      */         } 
/* 1401 */         CameraConfig.instance().setParallelProjection();
/*      */         break;
/*      */       
/*      */       case 4:
/* 1405 */         (this.world.getDebugRenderFlags()).showBounds = e.getState();
/*      */         break;
/*      */       case 5:
/* 1408 */         (this.world.getDebugRenderFlags()).showNormals = e.getState();
/*      */         break;
/*      */       case 6:
/* 1411 */         this.showCollision = e.getState();
/* 1412 */         if (this.showCollision) {
/* 1413 */           WorldOrigin.instance().addListener((OriginListener)this.collisionRenderer);
/* 1414 */           this.chunkWorldNode.addChunkListener((ChunkListener)this.collisionRenderer); break;
/*      */         } 
/* 1416 */         WorldOrigin.instance().removeListener((OriginListener)this.collisionRenderer);
/* 1417 */         this.chunkWorldNode.removeChunkListener((ChunkListener)this.collisionRenderer);
/*      */         break;
/*      */       
/*      */       case 12:
/* 1421 */         if (e.getState()) {
/* 1422 */           JFrame frame = new JFrame("Pathgraph");
/*      */         }
/*      */         break;
/*      */ 
/*      */       
/*      */       case 7:
/* 1428 */         TcgGame.debugChangeServer();
/*      */         break;
/*      */       case 9:
/* 1431 */         TcgGame.finishGame();
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 10:
/* 1437 */         zoomFactor = (float)(Math.exp(e.getValue() * 1.6D) - 0.95D);
/* 1438 */         CameraConfig.instance().setZoomFactor(zoomFactor * 50.0F);
/*      */         break;
/*      */       case 11:
/* 1441 */         this.playerNode.setLocalScale(((float)e.getValue() * 2.0F + 0.1F) * this.playerScale);
/*      */         break;
/*      */       case 15:
/* 1444 */         TcgGame.getDireEffectDescriptionFactory().setCacheDFX(e.getState());
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static World getWorld() {
/* 1450 */     return (World)INSTANCE.world;
/*      */   }
/*      */   
/*      */   public LineNode getCollisionRoot() {
/* 1454 */     return this.chunkWorldNode.getWorldLineNode();
/*      */   }
/*      */   
/*      */   public static PropNode getPlayerNode() {
/* 1458 */     return INSTANCE.playerNode;
/*      */   }
/*      */   
/*      */   public static LocalClientPlayer getPlayerModel() {
/* 1462 */     return (LocalClientPlayer)getPlayerNode().getProp();
/*      */   }
/*      */   
/*      */   public static CollisionDataProvider getCollisionDataProvider() {
/* 1466 */     return INSTANCE;
/*      */   }
/*      */   
/*      */   public static MainGameState getInstance() {
/* 1470 */     return INSTANCE;
/*      */   }
/*      */   
/*      */   public static TcgNodeFactory getNodeFactory() {
/* 1474 */     return INSTANCE.tcgNodeFactory;
/*      */   }
/*      */   
/*      */   public static ItemRegistry getItemRegistry() {
/* 1478 */     return INSTANCE.itemRegistry;
/*      */   }
/*      */   
/*      */   public static PetRegistry getPetRegistry() {
/* 1482 */     return INSTANCE.petRegistry;
/*      */   }
/*      */   
/*      */   public static BuffRegistry getBuffRegistry() {
/* 1486 */     return INSTANCE.buffRegistry;
/*      */   }
/*      */   
/*      */   public static boolean isStateCreated() {
/* 1490 */     return (INSTANCE != null);
/*      */   }
/*      */   
/*      */   public static boolean isStateInitialized() {
/* 1494 */     return (isStateCreated() && INSTANCE.isInitialized());
/*      */   }
/*      */   
/*      */   public static GuiWindowsController getGuiWindowsController() {
/* 1498 */     return INSTANCE.windowsController;
/*      */   }
/*      */   
/*      */   public static void setRespawnWindow(RespawnWindow respawnWindow) {
/* 1502 */     INSTANCE.respawnWindow = respawnWindow;
/*      */   }
/*      */   
/*      */   public static RespawnWindow getRespawnWindow() {
/* 1506 */     return INSTANCE.respawnWindow;
/*      */   }
/*      */   
/*      */   public static MapWindow2 getMapWindow() {
/* 1510 */     return INSTANCE.mapWindow;
/*      */   }
/*      */   
/*      */   public static void setMapWindow(MapWindow2 mapWindow) {
/* 1514 */     INSTANCE.mapWindow = mapWindow;
/*      */   }
/*      */ 
/*      */   
/*      */   public static PetsWindow getPetsWindow() {
/* 1519 */     return INSTANCE.petsWindow;
/*      */   }
/*      */   
/*      */   public static void setPetsWindow(PetsWindow petsWindow) {
/* 1523 */     INSTANCE.petsWindow = petsWindow;
/*      */   }
/*      */   
/*      */   public static AchievementsWindow getAchievementWindow() {
/* 1527 */     return INSTANCE.achievementWindow;
/*      */   }
/*      */   
/*      */   public static void setAchievementWindow(AchievementsWindow achievementWindow) {
/* 1531 */     INSTANCE.achievementWindow = achievementWindow;
/*      */   }
/*      */   
/*      */   public static LoadingWindow getLoadingWindow() {
/* 1535 */     return INSTANCE.loadingWindow;
/*      */   }
/*      */   
/*      */   public static TCGToolTipManager getToolTipManager() {
/* 1539 */     return INSTANCE.toolTipManager;
/*      */   }
/*      */   
/*      */   public static TcgRefreshFactory getRefreshFactory() {
/* 1543 */     return INSTANCE.tcgRefreshFactory;
/*      */   }
/*      */   
/*      */   public static double getMaxInteractionDistance() {
/* 1547 */     return INSTANCE.maxInteractionDistance;
/*      */   }
/*      */   
/*      */   public static ProjectilesFactory getProjectileFactory() {
/* 1551 */     return INSTANCE.projectileFactory;
/*      */   }
/*      */   
/*      */   public static GuiParticlesRenderPass getGuiParticlesRenderPass() {
/* 1555 */     return INSTANCE.guiParticlesRenderPass;
/*      */   }
/*      */   
/*      */   public static MouseCursorSetter getMouseCursorSetter() {
/* 1559 */     return INSTANCE.cursorSetter;
/*      */   }
/*      */   
/*      */   public static TCGGameControlsController getTcgGameControlsController() {
/* 1563 */     return INSTANCE.gameControlsController;
/*      */   }
/*      */   
/*      */   public static Map<String, String> getActionNameToCursorMapping() {
/* 1567 */     return INSTANCE.actionToCursorMapping;
/*      */   }
/*      */   
/*      */   public static HoverObjectInfoProvider getHoverInfoProvider() {
/* 1571 */     return INSTANCE.hoverObjectInfoProvider;
/*      */   }
/*      */   
/*      */   public static QuestModel getQuestModel() {
/* 1575 */     return INSTANCE.questModel;
/*      */   }
/*      */   
/*      */   public static void setQuestModel(QuestModel questModel) {
/* 1579 */     INSTANCE.questModel = questModel;
/*      */   }
/*      */   
/*      */   public static FriendModel getFriendModel() {
/* 1583 */     return INSTANCE.friendModel;
/*      */   }
/*      */   
/*      */   public static void setFriendModel(FriendModel friendModel) {
/* 1587 */     INSTANCE.friendModel = friendModel;
/*      */   }
/*      */   
/*      */   public static TownPortalPropertyReader getTownPortalPropertyReader() {
/* 1591 */     return INSTANCE.townPortalPropertyReader;
/*      */   }
/*      */   
/*      */   public static LoadingScreenPropertyReader getLoadingScreenPropertyReader() {
/* 1595 */     return INSTANCE.loadingScreenPropertyReader;
/*      */   }
/*      */   
/*      */   public static ChatUIController getChatUIController() {
/* 1599 */     return INSTANCE.chatUIController;
/*      */   }
/*      */   
/*      */   public static ChatNetworkController getChatNetworkController() {
/* 1603 */     return INSTANCE.chatNetworkController;
/*      */   }
/*      */   
/*      */   public static void resetCurrentChunkFactory() {
/* 1607 */     INSTANCE.resetChunkFactory();
/*      */   }
/*      */   
/*      */   public static QuestWindow2 getQuestWindow() {
/* 1611 */     return INSTANCE.questWindow;
/*      */   }
/*      */   
/*      */   public static void setQuestWindow(QuestWindow2 questWindow) {
/* 1615 */     INSTANCE.questWindow = questWindow;
/*      */   }
/*      */   
/*      */   public static CharacterEquipmentWindow getCharacterWindow() {
/* 1619 */     return INSTANCE.characterWindow;
/*      */   }
/*      */   
/*      */   public static void setCharacterWindow(CharacterEquipmentWindow characterWindow) {
/* 1623 */     INSTANCE.characterWindow = characterWindow;
/*      */   }
/*      */   
/*      */   public static InventoryWindow getInventoryWindow() {
/* 1627 */     return INSTANCE.inventoryWindow;
/*      */   }
/*      */   
/*      */   public static void setInventoryWindow(InventoryWindow inventoryWindow) {
/* 1631 */     INSTANCE.inventoryWindow = inventoryWindow;
/*      */   }
/*      */   
/*      */   public static NoahTutorialWindow getNoahTutorialWindow() {
/* 1635 */     return INSTANCE.noahTutorialWindow;
/*      */   }
/*      */   
/*      */   public static void setNoahTutorialWindow(NoahTutorialWindow noahWindow) {
/* 1639 */     INSTANCE.noahTutorialWindow = noahWindow;
/*      */   }
/*      */   
/*      */   public static TutorialArrowWindow getArrowWindow() {
/* 1643 */     return INSTANCE.arrowWindow;
/*      */   }
/*      */   
/*      */   public static void setArrowWindow(TutorialArrowWindow arrowWindow) {
/* 1647 */     INSTANCE.arrowWindow = arrowWindow;
/*      */   }
/*      */   
/*      */   public static FriendsWindow getFriendsWindow() {
/* 1651 */     return INSTANCE.friendsWindow;
/*      */   }
/*      */   
/*      */   public static void setFriendsWindow(FriendsWindow window) {
/* 1655 */     INSTANCE.friendsWindow = window;
/*      */   }
/*      */   
/*      */   public static BasicChatWindow getBasicChatWindow() {
/* 1659 */     return INSTANCE.basicChatWindow;
/*      */   }
/*      */   
/*      */   public static void addMessage(String message, MessageType messageType) {
/* 1663 */     if (getBasicChatWindow() != null) {
/* 1664 */       getBasicChatWindow().addMessage(messageType.getNickname(), message, false);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void setBasicChatWindow(BasicChatWindow window) {
/* 1669 */     INSTANCE.basicChatWindow = window;
/*      */   }
/*      */   
/*      */   public static void showNewNoahTutorialWindow() {
/* 1673 */     int noahWidth = 650;
/* 1674 */     INSTANCE.noahTutorialWindow = new NoahTutorialWindow(TcgGame.getResourceManager(), true);
/*      */     
/* 1676 */     INSTANCE.noahTutorialWindow.setBounds(DisplaySystem.getDisplaySystem().getWidth() / 2 - noahWidth / 2 + 10, 115, noahWidth, 120);
/*      */     
/* 1678 */     BuiSystem.addWindow((BWindow)INSTANCE.noahTutorialWindow);
/*      */   }
/*      */   
/*      */   public static TextureLoaderManager getTextureLoaderFactoryManager() {
/* 1682 */     return INSTANCE.textureLoaderManager;
/*      */   }
/*      */   
/*      */   public static RewardWindowController getRewardWindowController() {
/* 1686 */     return INSTANCE.rewardWindowController;
/*      */   }
/*      */   
/*      */   public void initLoadingWindow() {
/* 1690 */     if (this.loadingWindow != null && BuiSystem.getWindow(LoadingWindow.class.getSimpleName()) != null) {
/* 1691 */       BuiSystem.removeWindow((BWindow)this.loadingWindow);
/*      */     }
/* 1693 */     this.loadingWindow = new LoadingWindow();
/* 1694 */     this.loadingWindow.setLayer(100);
/* 1695 */     this.loadingWindow.setAlpha(0.0F);
/* 1696 */     this.loadingWindow.dismiss();
/*      */   }
/*      */   
/*      */   public static SkillListModelImpl getSkillListModel() {
/* 1700 */     return skillListModel;
/*      */   }
/*      */   
/*      */   public static VendorFullWindow getVendorWindow() {
/* 1704 */     return INSTANCE.vendorWindow;
/*      */   }
/*      */   
/*      */   public static void setVendorWindow(VendorFullWindow vendorWindow) {
/* 1708 */     INSTANCE.vendorWindow = vendorWindow;
/*      */   }
/*      */   
/*      */   public static TcgBasicPassManager getRenderPassManager() {
/* 1712 */     return INSTANCE.getPassManager();
/*      */   }
/*      */   
/*      */   public static QuestHudModel getQuestHudModel() {
/* 1716 */     return INSTANCE.questHudModel;
/*      */   }
/*      */   
/*      */   public static void setQuestHudModel(QuestHudModel questHudModel) {
/* 1720 */     INSTANCE.questHudModel = questHudModel;
/*      */   }
/*      */   
/*      */   public static List<String> getVisitedMaps() {
/* 1724 */     return INSTANCE.visitedMaps;
/*      */   }
/*      */   
/*      */   public static void setVisitedMaps(List<String> visitedMaps) {
/* 1728 */     INSTANCE.visitedMaps = visitedMaps;
/*      */   }
/*      */   
/*      */   public static void setPauseModel(PauseModel pauseModel) {
/* 1732 */     INSTANCE.pauseModel = pauseModel;
/*      */   }
/*      */   
/*      */   public static PauseModel getPauseModel() {
/* 1736 */     return INSTANCE.pauseModel;
/*      */   }
/*      */   
/*      */   public static HudModel getHudModel() {
/* 1740 */     return (HudModel)INSTANCE.hudModel;
/*      */   }
/*      */   
/*      */   public static TipsCollection getTips() {
/* 1744 */     return INSTANCE.tips;
/*      */   }
/*      */   
/*      */   public static boolean isSilentExit() {
/* 1748 */     return INSTANCE.silentExit;
/*      */   }
/*      */   
/*      */   public static void setSilentExit(boolean silentExit) {
/* 1752 */     INSTANCE.silentExit = silentExit;
/*      */   }
/*      */   
/*      */   public static List<String> getAccessKeys() {
/* 1756 */     return INSTANCE.accessKeys;
/*      */   }
/*      */   
/*      */   public static List<Long> getAccessKeyExpireTimes() {
/* 1760 */     return INSTANCE.accessKeyExpireTimes;
/*      */   }
/*      */   
/*      */   public static boolean isPlayerRegistered() {
/* 1764 */     if (getPlayerNode() != null) {
/* 1765 */       LocalClientPlayer playerModel = getPlayerModel();
/* 1766 */       if (playerModel != null) {
/* 1767 */         DefaultSubscriptionState defaultSubscriptionState = playerModel.getSubscriptionState();
/* 1768 */         if (defaultSubscriptionState != null) {
/* 1769 */           return defaultSubscriptionState.isRegistered();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1774 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isPlayerSubscriber() {
/* 1778 */     if (getPlayerNode() != null) {
/* 1779 */       LocalClientPlayer playerModel = getPlayerModel();
/* 1780 */       if (playerModel != null) {
/* 1781 */         DefaultSubscriptionState defaultSubscriptionState = playerModel.getSubscriptionState();
/* 1782 */         if (defaultSubscriptionState != null) {
/* 1783 */           return defaultSubscriptionState.isSubscriber();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1788 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isCannedChat() {
/* 1792 */     if (getPlayerNode() != null) {
/* 1793 */       LocalClientPlayer playerModel = getPlayerModel();
/* 1794 */       if (playerModel != null) {
/* 1795 */         DefaultSubscriptionState defaultSubscriptionState = playerModel.getSubscriptionState();
/* 1796 */         if (defaultSubscriptionState != null) {
/* 1797 */           return defaultSubscriptionState.isCannedChat();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1802 */     return false;
/*      */   }
/*      */   
/*      */   public static AccountButtonWindow getAccountButtonWindow() {
/* 1806 */     return INSTANCE.accountButtonWindow;
/*      */   }
/*      */   
/*      */   public static MainHud getMainHud() {
/* 1810 */     return INSTANCE.mainHud;
/*      */   }
/*      */   
/*      */   public static TopButtonWindow getTopButtonWindow() {
/* 1814 */     return INSTANCE.topButtonWindow;
/*      */   }
/*      */   
/*      */   public static CurrencyWindow getCurrencyWindow() {
/* 1818 */     return INSTANCE.currencyWindow;
/*      */   }
/*      */   
/*      */   public static DuelHealthBarWindow getDuelHealthBarWindow() {
/* 1822 */     return INSTANCE.duelHealthBarWindow;
/*      */   }
/*      */   
/*      */   public static AchievementNotificationWindow getAchievementNotificationWindow() {
/* 1826 */     return INSTANCE.achievementNotificationWindow;
/*      */   }
/*      */   
/*      */   public static void setDuelHealthBarWindow(DuelHealthBarWindow duelHealthBarWindow) {
/* 1830 */     INSTANCE.duelHealthBarWindow = duelHealthBarWindow;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void showRegisterWindow(String callSite) {
/* 1837 */     if (this.accountButtonWindow != null) {
/* 1838 */       this.accountButtonWindow.showSaveCharacterWindow(callSite);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void showSubscribeWindow() {
/* 1843 */     if (INSTANCE.accountButtonWindow != null) {
/* 1844 */       INSTANCE.accountButtonWindow.showSubscribeWindow();
/*      */     }
/*      */   }
/*      */   
/*      */   public static BloomRenderPass getBloomPass() {
/* 1849 */     return INSTANCE.getPassManager().getBloomRenderPass();
/*      */   }
/*      */   
/*      */   public static BreadcrumbManager getBreadcrumbManager() {
/* 1853 */     return INSTANCE.breadcrumbManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public static AfkShutdownHandler getAfkShutdownHandler() {
/* 1858 */     return INSTANCE.afkShutdownHandler;
/*      */   }
/*      */   
/*      */   private static class FinishGameAction
/*      */     implements ActionListener
/*      */   {
/*      */     public void actionPerformed(ActionEvent event) {
/* 1865 */       TcgGame.finishGame();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\state\MainGameState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */