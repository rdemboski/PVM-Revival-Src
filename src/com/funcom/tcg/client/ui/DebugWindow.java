/*      */ package com.funcom.tcg.client.ui;
/*      */ import com.funcom.audio.SoundSystemFactory;
/*      */ import com.funcom.commons.PerformanceGraphNode;
/*      */ import com.funcom.commons.configuration.CSVWriter;
import com.funcom.gameengine.Updated;
/*      */ import com.funcom.gameengine.WorldCoordinate;
/*      */ import com.funcom.gameengine.debug.TimeStamper;
import com.funcom.gameengine.jme.ZBufferNode;
/*      */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
import com.funcom.gameengine.resourcemanager.loadingmanager.SoundSetIOEnabledCallable;
import com.funcom.gameengine.resourcemanager.loadingmanager.SoundSetAuditionEnabledCallable;
import com.funcom.gameengine.resourcemanager.loadingmanager.SoundSetProfileEnabledCallable;
/*      */ import com.funcom.gameengine.utils.PerformanceGraphRenderPass;
/*      */ import com.funcom.server.common.GameIOHandler;
/*      */ import com.funcom.server.common.Message;
import com.funcom.tcg.client.TcgGame;
/*      */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*      */ import com.funcom.tcg.client.net.NetworkHandler;
/*      */ import com.funcom.tcg.client.state.MainGameState;
/*      */ import com.funcom.tcg.client.ui.event.DebugEvent;
/*      */ import com.funcom.tcg.client.ui.event.DebugWindowListener;
import com.funcom.tcg.net.message.JoinPvpQueueMessage;
import com.funcom.tcg.net.message.VerifyTownPortalActivationMessage;
/*      */ import com.jme.util.Debug;
/*      */ import com.jme.util.stat.MultiStatSample;
/*      */ import com.jme.util.stat.StatAssetLog;
/*      */ import com.jme.util.stat.StatCollector;
import com.jme.util.stat.StatListener;
/*      */ import com.jme.util.stat.StatType;
/*      */ import com.jme.util.stat.StatValue;
/*      */ import com.jmex.bui.BButton;
/*      */ import com.jmex.bui.BCheckBox;
/*      */ import com.jmex.bui.BComponent;
/*      */ import com.jmex.bui.BContainer;
/*      */ import com.jmex.bui.BLabel;
import com.jmex.bui.BScrollPane;
/*      */ import com.jmex.bui.BSlider;
import com.jmex.bui.BWindow;
import com.jmex.bui.enumeratedConstants.Orientation;
/*      */ import com.jmex.bui.event.ActionEvent;
/*      */ import com.jmex.bui.event.ActionListener;
/*      */ import com.jmex.bui.event.ChangeEvent;
/*      */ import com.jmex.bui.event.ChangeListener;
/*      */ import com.jmex.bui.event.ComponentListener;
/*      */ import com.jmex.bui.layout.BLayoutManager;
/*      */ import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GridLayout;
import com.jmex.bui.layout.GroupLayout;

/*      */ import java.io.File;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
import java.net.SocketAddress;
/*      */ import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
/*      */ import java.util.Date;
import java.util.HashSet;
/*      */ import java.util.Iterator;
import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
import java.util.Set;
/*      */ import java.util.Timer;
import java.util.TimerTask;
/*      */ import java.util.concurrent.Callable;
import java.util.logging.Logger;
/*      */ 
/*      */ public class DebugWindow extends AbstractTcgWindow implements Updated {
/*   47 */   private static final Logger LOGGER = Logger.getLogger(DebugWindow.class.getName());
/*      */   
/*      */   public static final int DEBUG_PERSPECTIVE = 1;
/*      */   
/*      */   public static final int DEBUG_WIRESTATE = 2;
/*      */   
/*      */   public static final int DEBUG_LIGHTS = 3;
/*      */   
/*      */   public static final int DEBUG_BOUNDS = 4;
/*      */   public static final int DEBUG_NORMALS = 5;
/*      */   public static final int DEBUG_COLLISION = 6;
/*      */   public static final int DEBUG_RELOAD = 7;
/*      */   public static final int DEBUG_QUIT = 9;
/*      */   public static final int DEBUG_ZOOM = 10;
/*      */   public static final int DEBUG_SIZE = 11;
/*      */   public static final int DEBUG_PATHGRAPH = 12;
/*      */   public static final int DEBUG_STATS = 14;
/*      */   public static final int DEBUG_CACHE_DFX = 15;
/*      */   private Set<DebugWindowListener> listeners;
/*      */   private BCheckBox perspectiveCheck;
/*      */   private BCheckBox wireframeCheck;
/*      */   private BCheckBox lightsCheck;
/*      */   private BCheckBox boundsCheck;
/*      */   private BCheckBox normalsCheck;
/*      */   private BCheckBox collisionCheck;
/*      */   private BCheckBox pathGraphCheck;
/*      */   private BCheckBox statsCheck;
/*      */   private BCheckBox zBufferCheck;
/*      */   private BCheckBox cacheDFX;
/*      */   private BCheckBox soundProfile;
/*      */   private BCheckBox soundAudition;
/*      */   private BCheckBox soundIo;
/*      */   private BLabel memoryFreeLabel;
/*      */   private BLabel memoryTotalLabel;
/*      */   private BLabel memoryMaxLabel;
/*      */   private BLabel serverLabel;
/*      */   private BLabel framerateLabel;
/*   84 */   private float timeAccum = 0.0F;
/*   85 */   private int frames = 0;
/*      */   
/*      */   private BLabel positionLabel;
/*      */   
/*      */   private BWindow testWindow;
/*      */   private BCheckBox performanceDumpCheck;
/*      */   private BCheckBox performanceGraphCheck;
/*      */   private BCheckBox[] performanceGraphStatsChecks;
/*      */   private BCheckBox assetLoggingDumpCheck;
/*      */   private BCheckBox debugTimerCheck;
/*      */   private Timer dumpFileTimer;
/*      */   
/*      */   public enum sampleKeys
/*      */   {
/*   99 */     other(1),
/*  100 */     modularLoadTime(2),
/*  101 */     loadingScreen(3),
/*  102 */     logicUpdate(4),
/*  103 */     textureLoadTime(5),
/*  104 */     joints(6),
/*  105 */     move_to(7),
/*  106 */     _triCount(8),
/*  107 */     breadcrumbs(9),
/*  108 */     chunkManagement(10),
/*  109 */     renderCall(11),
/*  110 */     turn_to(12),
/*  111 */     creatureBuilding(13),
/*  112 */     _geomCount(14),
/*  113 */     reloadModular(15),
/*  114 */     geoUpdate(16),
/*  115 */     updateClients(17),
/*  116 */     animationLoadTime(18),
/*  117 */     fps(19),
/*  118 */     _timedOther(20),
/*  119 */     _timedRenderer(21),
/*  120 */     sleep(22),
/*  121 */     gameToken(23),
/*  122 */     _vertCount(24),
/*  123 */     _texBind(25),
/*  124 */     managed(26),
/*  125 */     updateQueue(27),
/*  126 */     batch(28),
/*  127 */     sound(29),
/*  128 */     buiUpdate(30),
/*  129 */     networkHandler(31),
/*  130 */     _frames(32),
/*  131 */     particles(33);
/*      */     
/*      */     private int index;
/*      */     
/*      */     sampleKeys(int index) {
/*  136 */       this.index = index;
/*      */     }
/*      */     
/*      */     public int getIndex() {
/*  140 */       return this.index;
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
/*      */   public DebugWindow() {
/*  152 */     super(TcgGame.getResourceManager());
/*      */ 
/*      */ 
/*      */     
/*  156 */     setTitle("Debug");
/*      */     
/*  158 */     getClientArea().setLayoutManager((BLayoutManager)new GridLayout(2, 1));
/*  159 */     BContainer leftContainer = new BContainer((BLayoutManager)GroupLayout.makeVStretch());
/*  160 */     BContainer rightContainer = new BContainer((BLayoutManager)GroupLayout.makeVStretch());
/*  161 */     BContainer mainPane = new BContainer((BLayoutManager)new BorderLayout());
/*  162 */     mainPane.add((BComponent)leftContainer, BorderLayout.SOUTH);
/*  163 */     mainPane.add((BComponent)rightContainer, BorderLayout.NORTH);
/*  164 */     getClientArea().setLayoutManager((BLayoutManager)new BorderLayout());
/*  165 */     getClientArea().add((BComponent)new BScrollPane((BComponent)mainPane), BorderLayout.CENTER);
/*      */     
/*  167 */     setupDebugTiming(leftContainer);
/*      */     
/*  169 */     this.perspectiveCheck = new BCheckBox("Perspective");
/*  170 */     this.perspectiveCheck.setSelected(false);
/*  171 */     leftContainer.add((BComponent)this.perspectiveCheck);
/*      */     
/*  173 */     this.wireframeCheck = new BCheckBox("Wireframe");
/*  174 */     this.wireframeCheck.setSelected(false);
/*  175 */     leftContainer.add((BComponent)this.wireframeCheck);
/*      */     
/*  177 */     this.lightsCheck = new BCheckBox("Lights");
/*  178 */     leftContainer.add((BComponent)this.lightsCheck);
/*      */     
/*  180 */     this.boundsCheck = new BCheckBox("Bounding boxes");
/*  181 */     leftContainer.add((BComponent)this.boundsCheck);
/*      */     
/*  183 */     this.normalsCheck = new BCheckBox("Normals");
/*  184 */     leftContainer.add((BComponent)this.normalsCheck);
/*      */     
/*  186 */     this.collisionCheck = new BCheckBox("Collisions");
/*  187 */     leftContainer.add((BComponent)this.collisionCheck);
/*      */     
/*  189 */     this.pathGraphCheck = new BCheckBox("Path Graph");
/*  190 */     leftContainer.add((BComponent)this.pathGraphCheck);
/*      */     
/*  192 */     this.statsCheck = new BCheckBox("Render stats");
/*  193 */     this.statsCheck.setSelected(false);
/*  194 */     leftContainer.add((BComponent)this.statsCheck);
/*      */     
/*  196 */     this.zBufferCheck = new BCheckBox("View ZBuffer");
/*  197 */     this.zBufferCheck.setSelected(false);
/*  198 */     leftContainer.add((BComponent)this.zBufferCheck);
/*      */     
/*  200 */     this.cacheDFX = new BCheckBox("DFX cache");
/*  201 */     this.cacheDFX.setSelected(true);
/*  202 */     leftContainer.add((BComponent)this.cacheDFX);
/*      */     
/*  204 */     this.soundProfile = new BCheckBox("Sound System Profiling");
/*  205 */     leftContainer.add((BComponent)this.soundProfile);
/*      */     
/*  207 */     this.soundAudition = new BCheckBox("Sound System Audition/Tweak");
/*  208 */     leftContainer.add((BComponent)this.soundAudition);
/*      */     
/*  210 */     this.soundIo = new BCheckBox("Sound System IO");
/*  211 */     this.soundIo.setSelected(true);
/*  212 */     this.soundIo.setTooltipText("To replace data. Turn off, then copy files, and turn on.");
/*  213 */     leftContainer.add((BComponent)this.soundIo);
/*      */     
/*  215 */     setupPerformanceDump(leftContainer);
/*  216 */     setupAssetLoggingDump(leftContainer);
/*  217 */     setupPerformanceGraph(leftContainer);
/*  218 */     setupPerformanceGraphStatsSelectors(leftContainer);
/*      */     
/*  220 */     BLabel spaceLabel = new BLabel("");
/*  221 */     spaceLabel.setSize(10, 10);
/*  222 */     leftContainer.add((BComponent)spaceLabel);
/*      */     
/*  224 */     BButton queueButton = new BButton("Duel queue");
/*  225 */     leftContainer.add((BComponent)queueButton);
/*      */     
/*  227 */     BButton reloadButton = new BButton("Reload player");
/*  228 */     leftContainer.add((BComponent)reloadButton);
/*      */     
/*  230 */     BButton townPortalButton = new BButton("Add town portal");
/*  231 */     leftContainer.add((BComponent)townPortalButton);
/*      */     
/*  233 */     BButton gcButton = new BButton("Garbage collect");
/*  234 */     leftContainer.add((BComponent)gcButton);
/*      */     
/*  236 */     BButton quitButton = new BButton("Quit game");
/*  237 */     leftContainer.add((BComponent)quitButton);
/*      */     
/*  239 */     BLabel zoomLabel = new BLabel("Zoom");
/*  240 */     rightContainer.add((BComponent)zoomLabel);
/*  241 */     final BSlider zoomSlider = new BSlider(Orientation.HORIZONTAL, 1, 1000, 100);
/*  242 */     zoomSlider.setPreferredSize(100, 30);
/*  243 */     rightContainer.add((BComponent)zoomSlider);
/*      */     
/*  245 */     BLabel sizeLabel = new BLabel("Character size:");
/*  246 */     rightContainer.add((BComponent)sizeLabel);
/*  247 */     final BSlider sizeSlider = new BSlider(Orientation.HORIZONTAL, 0, 1000, 50);
/*  248 */     sizeSlider.setPreferredSize(100, 30);
/*  249 */     rightContainer.add((BComponent)sizeSlider);
/*      */     
/*  251 */     BContainer memoryFreeContainer = new BContainer((BLayoutManager)new BorderLayout());
/*  252 */     memoryFreeContainer.add((BComponent)new BLabel("Mem free:"), BorderLayout.WEST);
/*  253 */     this.memoryFreeLabel = new BLabel("");
/*  254 */     this.memoryFreeLabel.setStyleClass("label_rightaligned");
/*  255 */     memoryFreeContainer.add((BComponent)this.memoryFreeLabel, BorderLayout.CENTER);
/*  256 */     rightContainer.add((BComponent)memoryFreeContainer);
/*      */     
/*  258 */     BContainer memoryTotalContainer = new BContainer((BLayoutManager)new BorderLayout());
/*  259 */     memoryTotalContainer.add((BComponent)new BLabel("Mem total:"), BorderLayout.WEST);
/*  260 */     this.memoryTotalLabel = new BLabel("");
/*  261 */     this.memoryTotalLabel.setStyleClass("label_rightaligned");
/*  262 */     memoryTotalContainer.add((BComponent)this.memoryTotalLabel, BorderLayout.CENTER);
/*  263 */     rightContainer.add((BComponent)memoryTotalContainer);
/*      */     
/*  265 */     BContainer memoryMaxContainer = new BContainer((BLayoutManager)new BorderLayout());
/*  266 */     memoryMaxContainer.add((BComponent)new BLabel("Mem max:"), BorderLayout.WEST);
/*  267 */     this.memoryMaxLabel = new BLabel("");
/*  268 */     this.memoryMaxLabel.setStyleClass("label_rightaligned");
/*  269 */     memoryMaxContainer.add((BComponent)this.memoryMaxLabel, BorderLayout.CENTER);
/*  270 */     rightContainer.add((BComponent)memoryMaxContainer);
/*      */     
/*  272 */     BContainer serverContainer = new BContainer((BLayoutManager)new BorderLayout());
/*  273 */     serverContainer.add((BComponent)new BLabel("Server:"), BorderLayout.WEST);
/*  274 */     this.serverLabel = new BLabel("");
/*  275 */     this.serverLabel.setStyleClass("label_rightaligned");
/*  276 */     serverContainer.add((BComponent)this.serverLabel, BorderLayout.CENTER);
/*  277 */     rightContainer.add((BComponent)serverContainer);
/*      */     
/*  279 */     BContainer frameRateContainer = new BContainer((BLayoutManager)new BorderLayout());
/*  280 */     frameRateContainer.add((BComponent)new BLabel("RENDER_TIME:"), BorderLayout.WEST);
/*  281 */     this.framerateLabel = new BLabel("");
/*  282 */     this.framerateLabel.setStyleClass("label_rightaligned");
/*  283 */     frameRateContainer.add((BComponent)this.framerateLabel, BorderLayout.CENTER);
/*  284 */     rightContainer.add((BComponent)frameRateContainer);
/*      */     
/*  286 */     BContainer positionContainer = new BContainer((BLayoutManager)new BorderLayout());
/*  287 */     positionContainer.add((BComponent)new BLabel("Coordinates:"), BorderLayout.WEST);
/*  288 */     this.positionLabel = new BLabel("");
/*  289 */     this.positionLabel.setStyleClass("label_rightaligned");
/*  290 */     positionContainer.add((BComponent)this.positionLabel, BorderLayout.CENTER);
/*  291 */     rightContainer.add((BComponent)positionContainer);
/*      */     
/*  293 */     this.perspectiveCheck.addListener((ComponentListener)new ActionListener() {
/*      */           public void actionPerformed(ActionEvent selectionChangedEvent) {
/*  295 */             DebugEvent event = new DebugEvent(1);
/*  296 */             event.setState(DebugWindow.this.perspectiveCheck.isSelected());
/*  297 */             DebugWindow.this.fireDebugEvent(event);
/*      */           }
/*      */         });
/*      */     
/*  301 */     this.wireframeCheck.addListener((ComponentListener)new ActionListener() {
/*      */           public void actionPerformed(ActionEvent selectionChangedEvent) {
/*  303 */             DebugEvent event = new DebugEvent(2);
/*  304 */             event.setState(DebugWindow.this.wireframeCheck.isSelected());
/*  305 */             DebugWindow.this.fireDebugEvent(event);
/*      */           }
/*      */         });
/*      */     
/*  309 */     this.lightsCheck.addListener((ComponentListener)new ActionListener() {
/*      */           public void actionPerformed(ActionEvent selectionChangedEvent) {
/*  311 */             DebugEvent event = new DebugEvent(3);
/*  312 */             event.setState(DebugWindow.this.lightsCheck.isSelected());
/*  313 */             DebugWindow.this.fireDebugEvent(event);
/*      */           }
/*      */         });
/*      */     
/*  317 */     this.boundsCheck.addListener((ComponentListener)new ActionListener() {
/*      */           public void actionPerformed(ActionEvent selectionChangedEvent) {
/*  319 */             DebugEvent event = new DebugEvent(4);
/*  320 */             event.setState(DebugWindow.this.boundsCheck.isSelected());
/*  321 */             DebugWindow.this.fireDebugEvent(event);
/*      */           }
/*      */         });
/*      */     
/*  325 */     this.normalsCheck.addListener((ComponentListener)new ActionListener() {
/*      */           public void actionPerformed(ActionEvent selectionChangedEvent) {
/*  327 */             DebugEvent event = new DebugEvent(5);
/*  328 */             event.setState(DebugWindow.this.normalsCheck.isSelected());
/*  329 */             DebugWindow.this.fireDebugEvent(event);
/*      */           }
/*      */         });
/*      */     
/*  333 */     this.collisionCheck.addListener((ComponentListener)new ActionListener() {
/*      */           public void actionPerformed(ActionEvent selectionChangedEvent) {
/*  335 */             DebugEvent event = new DebugEvent(6);
/*  336 */             event.setState(DebugWindow.this.collisionCheck.isSelected());
/*  337 */             DebugWindow.this.fireDebugEvent(event);
/*      */           }
/*      */         });
/*      */     
/*  341 */     this.pathGraphCheck.addListener((ComponentListener)new ActionListener() {
/*      */           public void actionPerformed(ActionEvent selectionChangedEvent) {
/*  343 */             DebugEvent event = new DebugEvent(12);
/*  344 */             event.setState(DebugWindow.this.pathGraphCheck.isSelected());
/*  345 */             DebugWindow.this.fireDebugEvent(event);
/*      */           }
/*      */         });
/*      */     
/*  349 */     this.statsCheck.addListener((ComponentListener)new ActionListener() {
/*      */           public void actionPerformed(ActionEvent selectionChangedEvent) {
/*  351 */             DebugEvent event = new DebugEvent(14);
/*  352 */             event.setState(DebugWindow.this.statsCheck.isSelected());
/*  353 */             DebugWindow.this.fireDebugEvent(event);
/*      */           }
/*      */         });
/*      */     
/*  357 */     this.zBufferCheck.addListener((ComponentListener)new ActionListener() {
/*      */           public void actionPerformed(ActionEvent selectionChangedEvent) {
/*  359 */             ZBufferNode.INSTANCE.setVisible(DebugWindow.this.zBufferCheck.isSelected());
/*      */           }
/*      */         });
/*      */     
/*  363 */     this.cacheDFX.addListener((ComponentListener)new ActionListener() {
/*      */           public void actionPerformed(ActionEvent actionEvent) {
/*  365 */             DebugEvent event = new DebugEvent(15);
/*  366 */             event.setState(DebugWindow.this.cacheDFX.isSelected());
/*  367 */             DebugWindow.this.fireDebugEvent(event);
/*      */           }
/*      */         });
/*      */     
/*  371 */     this.soundProfile.addListener((ComponentListener)new ActionListener() {
/*      */           public void actionPerformed(ActionEvent event) {
/*  373 */             if (LoadingManager.USE) {
/*  374 */               LoadingManager.INSTANCE.submitSoundCallable(new SoundSetProfileEnabledCallable(((BCheckBox)event.getSource()).isSelected()));
/*      */             } else {
/*  376 */               SoundSystemFactory.getSoundSystem().setProfileEnabled(((BCheckBox)event.getSource()).isSelected());
/*      */             } 
/*      */           }
/*      */         });
/*  380 */     this.soundAudition.addListener((ComponentListener)new ActionListener() {
/*      */           public void actionPerformed(ActionEvent event) {
/*  382 */             if (LoadingManager.USE) {
/*  383 */               LoadingManager.INSTANCE.submitSoundCallable(new SoundSetAuditionEnabledCallable(((BCheckBox)event.getSource()).isSelected()));
/*      */             } else {
/*  385 */               SoundSystemFactory.getSoundSystem().setAuditionEnabled(((BCheckBox)event.getSource()).isSelected());
/*      */             } 
/*      */           }
/*      */         });
/*  389 */     this.soundIo.addListener((ComponentListener)new ActionListener() {
/*      */           public void actionPerformed(ActionEvent event) {
/*  391 */             if (LoadingManager.USE) {
/*  392 */               LoadingManager.INSTANCE.submitSoundCallable(new SoundSetIOEnabledCallable(((BCheckBox)event.getSource()).isSelected()));
/*      */             } else {
/*  394 */               SoundSystemFactory.getSoundSystem().setIOEnabled(((BCheckBox)event.getSource()).isSelected());
/*      */             } 
/*      */           }
/*      */         });
/*  398 */     queueButton.addListener((ComponentListener)new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent event) {
/*      */             try {
/*  402 */               NetworkHandler.instance().getIOHandler().send((Message)new JoinPvpQueueMessage("pvp_graybox"));
/*  403 */             } catch (InterruptedException e) {
/*  404 */               e.printStackTrace();
/*      */             } 
/*      */           }
/*      */         });
/*      */     
/*  409 */     reloadButton.addListener((ComponentListener)new ActionListener() {
/*      */           public void actionPerformed(ActionEvent selectionChangedEvent) {
/*  411 */             DebugEvent event = new DebugEvent(7);
/*  412 */             event.setState(false);
/*  413 */             DebugWindow.this.fireDebugEvent(event);
/*      */           }
/*      */         });
/*      */     
/*  417 */     townPortalButton.addListener((ComponentListener)new ActionListener() {
/*      */           public void actionPerformed(ActionEvent actionEvent) {
/*      */             try {
/*  420 */               LocalClientPlayer localClientPlayer = MainGameState.getPlayerModel();
/*  421 */               NetworkHandler.instance().getIOHandler().send((Message)new VerifyTownPortalActivationMessage(localClientPlayer.getId(), localClientPlayer.getPosition()));
/*      */             
/*      */             }
/*  424 */             catch (InterruptedException e) {
/*  425 */               e.printStackTrace();
/*      */             } 
/*      */           }
/*      */         });
/*      */     
/*  430 */     gcButton.addListener((ComponentListener)new ActionListener() {
/*      */           public void actionPerformed(ActionEvent event) {
/*  432 */             DebugWindow.LOGGER.info("Running System.runFinalization()");
/*  433 */             System.runFinalization();
/*  434 */             DebugWindow.LOGGER.info("Running System.gc()");
/*  435 */             System.gc();
/*      */           }
/*      */         });
/*      */     
/*  439 */     quitButton.addListener((ComponentListener)new ActionListener() {
/*      */           public void actionPerformed(ActionEvent selectionChangedEvent) {
/*  441 */             DebugEvent event = new DebugEvent(9);
/*  442 */             event.setState(false);
/*  443 */             DebugWindow.this.fireDebugEvent(event);
/*      */           }
/*      */         });
/*      */     
/*  447 */     zoomSlider.getModel().addChangeListener(new ChangeListener() {
/*      */           public void stateChanged(ChangeEvent e) {
/*  449 */             DebugEvent event = new DebugEvent(10);
/*  450 */             event.setValue(zoomSlider.getModel().getRatio());
/*  451 */             DebugWindow.this.fireDebugEvent(event);
/*      */           }
/*      */         });
/*      */     
/*  455 */     sizeSlider.getModel().addChangeListener(new ChangeListener() {
/*      */           public void stateChanged(ChangeEvent e) {
/*  457 */             DebugEvent event = new DebugEvent(11);
/*  458 */             event.setValue((sizeSlider.getModel().getRatio() * 10.0F));
/*  459 */             DebugWindow.this.fireDebugEvent(event);
/*      */           }
/*      */         });
/*  462 */     addDefaultCloseButton(AbstractTcgWindow.CloseButtonPosition.TOP_LEFT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupPerformanceDump(BContainer leftContainer) {
/*  470 */     this.performanceDumpCheck = new BCheckBox("Dump performance to file");
/*  471 */     this.performanceDumpCheck.setSelected(false);
/*  472 */     this.performanceDumpCheck.setEnabled(Debug.stats);
/*  473 */     leftContainer.add((BComponent)this.performanceDumpCheck);
/*      */     
/*  475 */     this.performanceDumpCheck.addListener((ComponentListener)new ActionListener()
/*      */         {
/*      */           private CSVWriter csvWriter;
/*      */           
/*  479 */           private DebugWindow.DumpStatListener statListener = new DebugWindow.DumpStatListener(20000);
/*      */           
/*      */           private static final int DUMP_CAPTURING_PERIOD = 1000;
/*      */ 
/*      */           
/*      */           public void actionPerformed(ActionEvent event) {
/*  485 */             boolean isSelected = ((BCheckBox)event.getSource()).isSelected();
/*      */             
/*  487 */             if (isSelected) {
/*      */               try {
/*  489 */                 this.csvWriter = createCSVWriter();
/*  490 */                 this.statListener.setWriter(this.csvWriter);
/*  491 */                 writeHeaders(this.csvWriter);
/*      */ 
/*      */                 
/*  494 */                 DebugWindow.this.dumpFileTimer = new Timer();
/*  495 */                 DebugWindow.this.dumpFileTimer.scheduleAtFixedRate(new DebugWindow.CaptureData(this.statListener), 0L, 1000L);
/*      */                 
/*  497 */                 StatCollector.addStatListener(this.statListener);
/*  498 */               } catch (IOException e) {
/*  499 */                 throw new IllegalStateException(e);
/*      */               } 
/*      */             } else {
/*      */               
/*  503 */               DebugWindow.this.dumpFileTimer.cancel();
/*      */               
/*  505 */               this.statListener.dumpData();
/*  506 */               this.statListener.unsetWriter();
/*      */ 
/*      */               
/*  509 */               if (this.csvWriter != null) {
/*      */                 try {
/*  511 */                   this.csvWriter.close();
/*  512 */                 } catch (IOException e) {
/*  513 */                   throw new IllegalStateException(e);
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           }
/*      */           
/*      */           private void writeHeaders(CSVWriter writer) throws IOException {
/*  520 */             writer.writeField("Time", true);
/*      */             
/*  522 */             for (DebugWindow.sampleKeys item : DebugWindow.sampleKeys.values()) {
/*  523 */               writer.writeField(item.toString(), false);
/*      */             }
/*      */ 
/*      */             
/*  527 */             writer.writeField("X Coordinate", false);
/*  528 */             writer.writeField("Y Coordinate", false);
/*      */             
/*  530 */             writer.writeLine(new String[0]);
/*      */           }
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
/*      */           private CSVWriter createCSVWriter() throws IOException {
/*  545 */             Date now = new Date(System.currentTimeMillis());
/*  546 */             String timestamp = (new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss")).format(now);
/*      */             
/*  548 */             File dumpFile = new File(System.getProperty("user.home"), ".tcg/" + timestamp + "_performancedump.csv");
/*  549 */             if (dumpFile.createNewFile()) {
/*  550 */               return new CSVWriter(new FileWriter(dumpFile));
/*      */             }
/*  552 */             throw new IllegalStateException("Failed to create dump file!");
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   private void setupDebugTiming(BContainer leftContainer) {
/*  558 */     this.debugTimerCheck = new BCheckBox("Timer Debug");
/*  559 */     this.debugTimerCheck.setSelected(false);
/*  560 */     this.debugTimerCheck.setEnabled(Debug.stats);
/*  561 */     leftContainer.add((BComponent)this.debugTimerCheck);
/*      */     
/*  563 */     this.debugTimerCheck.addListener((ComponentListener)new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent event) {
/*  566 */             boolean isSelected = ((BCheckBox)event.getSource()).isSelected();
/*      */             
/*  568 */             if (isSelected) {
/*      */               
/*  570 */               TimeStamper.INSTANCE.enable(true);
/*      */             }
/*      */             else {
/*      */               
/*  574 */               TimeStamper.INSTANCE.enable(false);
/*  575 */               TimeStamper.INSTANCE.printToFile(null);
/*  576 */               TimeStamper.INSTANCE.clear();
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   private void setupAssetLoggingDump(BContainer leftContainer) {
/*  584 */     this.assetLoggingDumpCheck = new BCheckBox("Asset Loading Logging to file");
/*  585 */     this.assetLoggingDumpCheck.setSelected(false);
/*  586 */     this.assetLoggingDumpCheck.setEnabled(Debug.stats);
/*  587 */     leftContainer.add((BComponent)this.assetLoggingDumpCheck);
/*      */     
/*  589 */     this.assetLoggingDumpCheck.addListener((ComponentListener)new ActionListener()
/*      */         {
/*      */           private CSVWriter csvWriter;
/*      */           
/*      */           public void actionPerformed(ActionEvent event) {
/*  594 */             boolean isSelected = ((BCheckBox)event.getSource()).isSelected();
/*      */             
/*  596 */             if (isSelected) {
/*      */               try {
/*  598 */                 this.csvWriter = createCSVWriter();
/*  599 */                 writeHeaders(this.csvWriter);
/*  600 */                 StatCollector.setAssetLoggingEnabled(true);
/*  601 */               } catch (IOException e) {
/*  602 */                 throw new IllegalStateException(e);
/*      */               } 
/*      */             } else {
/*  605 */               StatCollector.setAssetLoggingEnabled(false);
/*      */ 
/*      */ 
/*      */               
/*  609 */               List<StatAssetLog> log = StatCollector.getAssetLogBuffer();
/*      */               
/*      */               try {
/*  612 */                 if (log.size() > 0) {
/*  613 */                   long StartTime = ((StatAssetLog)log.get(0)).StartTime;
/*  614 */                   int n = 0;
/*  615 */                   int nMax = log.size();
/*  616 */                   for (; n < nMax; n++) {
/*  617 */                     StatAssetLog l = log.get(n);
/*  618 */                     this.csvWriter.writeLine(new String[] { String.valueOf(l.StartTime - StartTime), String.valueOf(l.EndTime - StartTime), String.valueOf(l.EndTime - l.StartTime), l.Name });
/*      */                   
/*      */                   }
/*      */                 
/*      */                 }
/*      */               
/*      */               }
/*  625 */               catch (IOException e) {
/*  626 */                 throw new IllegalStateException(e);
/*      */               } 
/*      */ 
/*      */               
/*  630 */               if (this.csvWriter != null) {
/*      */                 try {
/*  632 */                   this.csvWriter.close();
/*  633 */                 } catch (IOException e) {
/*  634 */                   throw new IllegalStateException(e);
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           }
/*      */           
/*      */           private void writeHeaders(CSVWriter writer) throws IOException {
/*  641 */             this.csvWriter.writeField("StartTime (ms)", true);
/*  642 */             this.csvWriter.writeField("EndTime (ms)", false);
/*  643 */             this.csvWriter.writeField("ElapsedTime (ms)", false);
/*  644 */             this.csvWriter.writeField("AssetName", false);
/*  645 */             this.csvWriter.writeLine(new String[0]);
/*      */           }
/*      */ 
/*      */           
/*      */           private CSVWriter createCSVWriter() throws IOException {
/*  650 */             Date now = new Date(System.currentTimeMillis());
/*  651 */             String timestamp = (new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss")).format(now);
/*      */             
/*  653 */             File dumpFile = new File(System.getProperty("user.home"), ".tcg/" + timestamp + "_performancedump_assetlog.csv");
/*      */             
/*  655 */             if (dumpFile.createNewFile()) {
/*  656 */               return new CSVWriter(new FileWriter(dumpFile));
/*      */             }
/*  658 */             throw new IllegalStateException("Failed to create dump file!");
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupPerformanceGraph(BContainer leftContainer) {
/*      */     String title;
/*  670 */     if (Debug.stats) {
/*  671 */       title = "Performance graph";
/*      */     } else {
/*  673 */       title = "Performance graph - DISABLED, -Djme.stats=true missing!";
/*      */     } 
/*      */     
/*  676 */     this.performanceGraphCheck = new BCheckBox(title);
/*  677 */     this.performanceGraphCheck.setSelected(false);
/*  678 */     this.performanceGraphCheck.setEnabled(Debug.stats);
/*  679 */     leftContainer.add((BComponent)this.performanceGraphCheck);
/*      */     
/*  681 */     this.performanceGraphCheck.addListener((ComponentListener)new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent event) {
/*  684 */             boolean isSelected = ((BCheckBox)event.getSource()).isSelected();
/*  685 */             MainGameState.getRenderPassManager().getPerformanceGraphRenderPass().setEnabled(isSelected);
/*      */             
/*  687 */             for (BCheckBox performanceGraphStatsCheck : DebugWindow.this.performanceGraphStatsChecks) {
/*  688 */               performanceGraphStatsCheck.setEnabled(isSelected);
/*      */             }
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupPerformanceGraphStatsSelectors(BContainer container) {
/*  699 */     PerformanceGraphNode.TrackingStat[] trackStatEnums = PerformanceGraphNode.TrackingStat.values();
/*  700 */     this.performanceGraphStatsChecks = new BCheckBox[trackStatEnums.length];
/*  701 */     final PerformanceGraphRenderPass graphRenderPass = MainGameState.getRenderPassManager().getPerformanceGraphRenderPass();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  706 */     ActionListener statCheckBoxListener = new ActionListener()
/*      */       {
/*      */         public void actionPerformed(ActionEvent event)
/*      */         {
/*  710 */           List<PerformanceGraphNode.TrackingStat> statsToTrack = new LinkedList<PerformanceGraphNode.TrackingStat>();
/*  711 */           for (BCheckBox checkBox : DebugWindow.this.performanceGraphStatsChecks) {
/*  712 */             if (checkBox.isSelected()) {
/*  713 */               statsToTrack.add((PerformanceGraphNode.TrackingStat)checkBox.getProperty("statEnumValue"));
/*      */             }
/*      */           } 
/*      */           
/*  717 */           PerformanceGraphNode.TrackingStat[] trackingStatsArray = statsToTrack.<PerformanceGraphNode.TrackingStat>toArray(new PerformanceGraphNode.TrackingStat[statsToTrack.size()]);
/*      */           
/*  719 */           graphRenderPass.setTrackingStats(trackingStatsArray);
/*      */         }
/*      */       };
/*      */ 
/*      */     
/*  724 */     int i = -1;
/*  725 */     for (PerformanceGraphNode.TrackingStat trackStatEnum : trackStatEnums) {
/*  726 */       BCheckBox checkBox = new BCheckBox(trackStatEnum.niceName);
/*  727 */       checkBox.setEnabled(this.performanceGraphCheck.isEnabled());
/*  728 */       if (checkBox.isEnabled()) {
/*  729 */         checkBox.setProperty("statEnumValue", trackStatEnum);
/*  730 */         checkBox.setSelected((Arrays.binarySearch((Object[])graphRenderPass.getTrackingStats(), trackStatEnum) >= 0));
/*  731 */         checkBox.addListener((ComponentListener)statCheckBoxListener);
/*      */       } 
/*      */       
/*  734 */       container.add((BComponent)checkBox);
/*  735 */       this.performanceGraphStatsChecks[++i] = checkBox;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setState(int debugType, boolean state) {
/*  744 */     switch (debugType) {
/*      */       case 1:
/*  746 */         this.perspectiveCheck.setSelected(state);
/*      */         break;
/*      */       
/*      */       case 2:
/*  750 */         this.wireframeCheck.setSelected(state);
/*      */         break;
/*      */       
/*      */       case 3:
/*  754 */         this.lightsCheck.setSelected(state);
/*      */         break;
/*      */       
/*      */       case 4:
/*  758 */         this.boundsCheck.setSelected(state);
/*      */         break;
/*      */       
/*      */       case 5:
/*  762 */         this.normalsCheck.setSelected(state);
/*      */         break;
/*      */       
/*      */       case 6:
/*  766 */         this.collisionCheck.setSelected(state);
/*      */         break;
/*      */       
/*      */       case 12:
/*  770 */         this.pathGraphCheck.setSelected(state);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addDebugListener(DebugWindowListener listener) {
/*  780 */     if (this.listeners == null) {
/*  781 */       this.listeners = new HashSet<DebugWindowListener>();
/*      */     }
/*  783 */     this.listeners.add(listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fireDebugEvent(DebugEvent e) {
/*  791 */     if (this.listeners == null) {
/*      */       return;
/*      */     }
/*  794 */     for (DebugWindowListener listener : this.listeners) {
/*  795 */       listener.debugStateChanged(e);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getPerspective() {
/*  804 */     return this.perspectiveCheck.isSelected();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dismiss() {
/*  813 */     super.dismiss();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void update(float time) {
/*  822 */     if (!isVisible()) {
/*      */       return;
/*      */     }
/*      */     
/*  826 */     this.frames++;
/*  827 */     this.timeAccum += time;
/*      */     
/*  829 */     if (this.timeAccum > 0.5F) {
/*  830 */       this.memoryFreeLabel.setText(String.valueOf(Runtime.getRuntime().freeMemory()));
/*  831 */       this.memoryTotalLabel.setText(String.valueOf(Runtime.getRuntime().totalMemory()));
/*  832 */       this.memoryMaxLabel.setText(String.valueOf(Runtime.getRuntime().maxMemory()));
/*  833 */       GameIOHandler gameIOHandler = NetworkHandler.instance().getIOHandler();
/*  834 */       if (gameIOHandler != null) {
/*  835 */         SocketAddress addres = gameIOHandler.getTargetAddres();
/*  836 */         if (addres != null) {
/*  837 */           this.serverLabel.setText(addres.toString());
/*      */         }
/*      */       } 
/*  840 */       this.framerateLabel.setText("" + (this.frames / this.timeAccum));
/*  841 */       this.timeAccum = 0.0F;
/*  842 */       this.frames = 0;
/*      */       
/*  844 */       WorldCoordinate position = MainGameState.getPlayerModel().getPosition();
/*  845 */       this.positionLabel.setText(" X: " + position.getTileCoord().getX() + " Y: " + position.getTileCoord().getY());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class DumpStatListener
/*      */     implements StatListener
/*      */   {
/*      */     private CSVWriter writer;
/*      */     
/*  856 */     private int samplesNumber = 20000;
/*      */     
/*  858 */     private List<Double[]> writingBuffer = (List)new ArrayList<Double>();
/*      */ 
/*      */     
/*      */     private static final int DUMPFILE_COLUMNS = 36;
/*      */ 
/*      */     
/*      */     private static final int ACTUALTIME_COL = 0;
/*      */ 
/*      */     
/*      */     private static final int X_POSITION_COL = 34;
/*      */ 
/*      */     
/*      */     private static final int Y_POSITION_COL = 35;
/*      */ 
/*      */     
/*      */     public DumpStatListener() {}
/*      */ 
/*      */     
/*      */     public DumpStatListener(int bufferSamplesNumber) {
/*  877 */       this.samplesNumber = bufferSamplesNumber;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setBufferSamplesNumber(int samplesNumber) {
/*  885 */       this.samplesNumber = samplesNumber;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeSample(Double[] sample) {
/*      */       try {
/*  894 */         this.writer.writeField(toStringNullSafe(sample[0]), true);
/*      */         
/*  896 */         for (int i = 1; i < sample.length; i++) {
/*  897 */           this.writer.writeField(toStringNullSafe(sample[i]), false);
/*      */         }
/*      */         
/*  900 */         this.writer.writeLine(new String[0]);
/*      */       }
/*  902 */       catch (IOException e) {
/*  903 */         throw new IllegalStateException(e);
/*      */       } 
/*      */     }
/*      */     
/*      */     private String toStringNullSafe(Double value) {
/*  908 */       if (value != null) {
/*  909 */         return String.valueOf(value);
/*      */       }
/*  911 */       return "0";
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void cleanBuffer() {
/*  937 */       this.writingBuffer.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void dumpData() {
/*  946 */       Iterator<Double[]> iter = this.writingBuffer.iterator();
/*      */       
/*  948 */       while (iter.hasNext()) {
/*  949 */         writeSample((Double[])iter.next());
/*      */       }
/*      */ 
/*      */       
/*  953 */       cleanBuffer();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean bufferIsFull() {
/*  961 */       if (this.writingBuffer.size() >= this.samplesNumber) {
/*  962 */         return true;
/*      */       }
/*  964 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void insertSampleInBuffer(Double[] sample) {
/*  973 */       this.writingBuffer.add(sample);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public CSVWriter getWriter() {
/*  981 */       return this.writer;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setWriter(CSVWriter writer) {
/*  989 */       this.writer = writer;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void statsUpdated() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void captureData() {
/* 1007 */       if (bufferIsFull()) {
/* 1008 */         dumpData();
/*      */       }
/*      */       
/* 1011 */       List<MultiStatSample> history = StatCollector.getHistorical();
/* 1012 */       MultiStatSample latest = history.get(history.size() - 1);
/*      */       
/* 1014 */       Double[] orderedSampleKeys = new Double[36];
/* 1015 */       orderedSampleKeys[0] = Double.valueOf(latest.actualTime);
/*      */ 
/*      */       
/* 1018 */       for (Map.Entry<StatType, StatValue> entry : (Iterable<Map.Entry<StatType, StatValue>>)latest.values.entrySet()) {
/* 1019 */         DebugWindow.sampleKeys key = DebugWindow.sampleKeys.valueOf(((StatType)entry.getKey()).getStatName());
/* 1020 */         orderedSampleKeys[key.getIndex()] = Double.valueOf(((StatValue)entry.getValue()).average);
/*      */       } 
/*      */ 
/*      */       
/* 1024 */       WorldCoordinate position = MainGameState.getPlayerModel().getPosition();
/* 1025 */       orderedSampleKeys[34] = Double.valueOf(position.getTileCoord().getX());
/* 1026 */       orderedSampleKeys[35] = Double.valueOf(position.getTileCoord().getY());
/*      */       
/* 1028 */       insertSampleInBuffer(orderedSampleKeys);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void unsetWriter() {
/* 1055 */       this.writer = null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class CaptureData
/*      */     extends TimerTask
/*      */   {
/*      */     private DebugWindow.DumpStatListener statListener;
/*      */ 
/*      */     
/*      */     CaptureData(DebugWindow.DumpStatListener statListener) {
/* 1068 */       this.statListener = statListener;
/*      */     }
/*      */ 
/*      */     
/*      */     public void run() {
/* 1073 */       this.statListener.captureData();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\DebugWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */