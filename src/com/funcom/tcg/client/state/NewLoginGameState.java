/*     */ package com.funcom.tcg.client.state;
/*     */ 
/*     */ import com.funcom.commons.FileUtils;
/*     */ import com.funcom.commons.utils.GlobalTime;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.server.common.LocalGameClient;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.NetworkConfiguration;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.metrics.HttpMetrics;
/*     */ import com.funcom.tcg.client.model.rpg.VisualRegistry;
/*     */ import com.funcom.tcg.client.net.LoginAnswer;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.net.NetworkHandlerException;
/*     */ import com.funcom.tcg.client.ui.Localizer;
/*     */ import com.funcom.tcg.client.ui.TCGDialog;
/*     */ import com.funcom.tcg.client.ui.hud.LoadingWindow;
/*     */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*     */ import com.funcom.tcg.client.ui.startmenu.StartMenuModel;
/*     */ import com.funcom.tcg.client.ui.startmenu.StartMenuModelImpl;
/*     */ import com.funcom.tcg.client.ui.startmenu.StartMenuStartGameListener;
/*     */ import com.funcom.tcg.client.ui.startmenu.StartMenuWizard;
/*     */ import com.funcom.tcg.net.AccountResult;
/*     */ import com.funcom.tcg.net.PlayerStartConfig;
/*     */ import com.funcom.tcg.net.message.CreateCharacterRequestMessage;
/*     */ import com.funcom.tcg.net.message.CreateCharacterResponseMessage;
/*     */ import com.funcom.tcg.net.message.ServerStatesRequestMessage;
/*     */ import com.funcom.tcg.net.message.ServerStatesResponseMessage;
/*     */ import com.funcom.tcg.rpg.AbstractTCGRpgLoader;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jme.util.GameTaskQueueManager;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.game.state.GameStateManager;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.Locale;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NewLoginGameState
/*     */   extends TcgGameState
/*     */   implements Localizer, StartMenuStartGameListener, LoginAnswer
/*     */ {
/*     */   public static final String STATE_NAME = "login-game-state";
/*     */   public static final String LOCALIZATIONKEY_ERROR_NONAME = "startmenu.model.errors.noname";
/*     */   private StartMenuWizard wizard;
/*     */   private StartMenuModel menuModel;
/*  55 */   private ExecutorService executorService = Executors.newSingleThreadExecutor();
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  60 */     return "login-game-state";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initialize() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanup() {}
/*     */ 
/*     */   
/*     */   protected void activated() {
/*  73 */     ResourceManager resourceManager = TcgGame.getResourceManager();
/*  74 */     VisualRegistry visualRegistry = TcgGame.getVisualRegistry();
/*  75 */     AbstractTCGRpgLoader rpgLoader = TcgGame.getRpgLoader();
/*     */     
/*  77 */     if (this.menuModel == null) {
/*  78 */       this.menuModel = (StartMenuModel)new StartMenuModelImpl(resourceManager, visualRegistry, rpgLoader.getStartUpManager());
/*     */     }
/*     */     
/*  81 */     this.menuModel.setCharactersName(getDefaultUsername());
/*     */     
/*  83 */     if (this.wizard == null) {
/*  84 */       this.wizard = new StartMenuWizard(this.menuModel, this, resourceManager, visualRegistry, this, BuiSystem.getRootNode());
/*     */     }
/*     */     
/*  87 */     this.wizard.startWizard();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void deactivated() {
/*  92 */     this.wizard.stopWizard();
/*  93 */     this.wizard.killWizard();
/*  94 */     this.wizard = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyNetworkDisconnect(LocalGameClient.DisconnectReason reason) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(float tpf) {
/* 104 */     GlobalTime.getInstance().run();
/* 105 */     BuiSystem.getRootNode().updateGeometricState(tpf, true);
/* 106 */     this.wizard.update(tpf);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(float v) {
/* 111 */     DisplaySystem.getDisplaySystem().getRenderer().draw((Spatial)BuiSystem.getRootNode());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLocalizedText(Class<?> clazz, String key, String... parameters) {
/* 116 */     return TcgGame.getLocalizedText(key, parameters);
/*     */   }
/*     */ 
/*     */   
/*     */   public void createCharacter() {
/* 121 */     TCGDialog waitDialog = TCGDialog.showMessage("charcreatewindow.pleasewait.title", "charcreatewindow.creatingcharacter.text", this, TCGDialog.Options.NONE, null);
/*     */ 
/*     */ 
/*     */     
/* 125 */     PanelManager.getInstance().addWindow((BWindow)waitDialog);
/*     */ 
/*     */ 
/*     */     
/* 129 */     CreateCharacterRequestMessage message = new CreateCharacterRequestMessage(this.menuModel.getCharactersName(), this.menuModel.getPassword(), this.menuModel.getParentsEmail(), Locale.getDefault().getLanguage());
/*     */ 
/*     */ 
/*     */     
/* 133 */     message.encrypt(TcgGame.getPublicKey());
/*     */     
/* 135 */     AsyncMessageSender<CreateCharacterResponseMessage> sender = new AsyncMessageSender<CreateCharacterResponseMessage>((Message)message, CreateCharacterResponseMessage.class);
/*     */     
/* 137 */     sender.startSend();
/*     */     
/* 139 */     AsyncCreateCharacterTask task = new AsyncCreateCharacterTask(this.wizard, this, waitDialog, sender);
/* 140 */     task.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void showNewPlayerWizard() {
/* 145 */     TCGDialog waitDialog = TCGDialog.showMessage("charcreatewindow.pleasewait.title", "charcreatewindow.newcharactermsg", this, TCGDialog.Options.NONE, null);
/*     */ 
/*     */     
/* 148 */     waitDialog.setBounds(DisplaySystem.getDisplaySystem().getWidth() / 2 - waitDialog.getWidth() / 2, DisplaySystem.getDisplaySystem().getWidth() / 2 - 100, waitDialog.getWidth(), 200);
/*     */ 
/*     */ 
/*     */     
/* 152 */     AsyncMessageSender<ServerStatesResponseMessage> sender = new AsyncMessageSender<ServerStatesResponseMessage>((Message)new ServerStatesRequestMessage(), ServerStatesResponseMessage.class);
/*     */     
/* 154 */     sender.startSend();
/*     */     
/* 156 */     AbstractResponseTask task = new AsyncNewCharacterTask(this.wizard, this, waitDialog, sender);
/* 157 */     task.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void showLoginWizard() {
/* 162 */     this.wizard.showLogin();
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetStartMenuWizard() {
/* 167 */     this.wizard.stopWizard();
/* 168 */     this.wizard.startWizard();
/*     */   }
/*     */ 
/*     */   
/*     */   public void loginByCreation() {
/* 173 */     LoadingWindow loadingWindow = MainGameState.getLoadingWindow();
/* 174 */     BuiSystem.getRootNode().addWindow((BWindow)loadingWindow);
/* 175 */     loadingWindow.setVisible(true);
/* 176 */     PlayerStartConfig startConfig = this.menuModel.makeStartConfig();
/* 177 */     startConfig = new PlayerStartConfig(startConfig.getPlayerDescription(), startConfig.getStartingPet(), startConfig.getTorsoId(), startConfig.getLegsId(), true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 183 */     this.menuModel.setPlayerStartConfig(startConfig);
/* 184 */     this.menuModel.setCharactersName("trial");
/* 185 */     this.menuModel.setPassword("");
/* 186 */     startGame("TRIAL", "", startConfig);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void login() {
/* 192 */     if (!this.menuModel.getCharactersName().isEmpty()) {
/* 193 */       LoadingWindow loadingWindow = MainGameState.getLoadingWindow();
/* 194 */       BuiSystem.getRootNode().addWindow((BWindow)loadingWindow);
/* 195 */       loadingWindow.setVisible(true);
/* 196 */       saveLoginName();
/* 197 */       HttpMetrics.postEvent(HttpMetrics.Event.HAVE_CHARACTER);
/* 198 */       startGame(this.menuModel.getCharactersName(), this.menuModel.getPasswordHash(), null);
/*     */     } else {
/*     */       
/* 201 */       HttpMetrics.postEvent(HttpMetrics.Event.LOGIN_FAILED);
/* 202 */       TCGDialog.showMessage("loginwindow.faileddialog.title", "startmenu.model.errors.noname", this, null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void startGame(String charactersName, String password, PlayerStartConfig playerStartConfig) {
/* 209 */     this.wizard.stopWizard();
/* 210 */     loginCommitted(charactersName, password, playerStartConfig);
/* 211 */     HttpMetrics.postEvent(HttpMetrics.Event.CLICK_PLAY);
/*     */   }
/*     */   
/*     */   private void saveLoginName() {
/* 215 */     TcgGame.setUserName(this.menuModel.getCharactersName());
/*     */   }
/*     */   
/*     */   public String getDefaultUsername() {
/* 219 */     return TcgGame.getPreferences().loadUserName();
/*     */   }
/*     */   
/*     */   private void loginCommitted(String charactersName, String password, PlayerStartConfig playerStartConfig) {
/*     */     try {
/* 224 */       NetworkHandler.instance().resetIOHandler(NetworkConfiguration.instance().getServerAddress());
/* 225 */       NetworkHandler.instance().login(charactersName, password, playerStartConfig, this);
/* 226 */     } catch (NetworkHandlerException e) {
/* 227 */       TCGDialog.showMessage("loginwindow.networkerrordialog.title", "loginwindow.networkerrordialog.text", this, new CloseLoadingWindowAction(this.wizard));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loginCancelled() {
/* 234 */     GameTaskQueueManager.getManager().update(new Callable()
/*     */         {
/*     */           public Object call() throws Exception {
/* 237 */             TCGDialog.showMessage("loginwindow.cancelleddialog.title", "loginwindow.cancelleddialog.text", NewLoginGameState.this, new NewLoginGameState.CloseLoadingWindowAction(NewLoginGameState.this.wizard));
/*     */ 
/*     */             
/* 240 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loginSuccesful(int clientIdentity) {
/* 249 */     GameTaskQueueManager.getManager().update(new Callable()
/*     */         {
/*     */           public Object call() throws Exception {
/* 252 */             GameStateManager.getInstance().deactivateChildNamed("login-game-state");
/* 253 */             GameStateManager.getInstance().activateChildNamed("main-game-state");
/* 254 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loginFailed(final String messageKey) {
/* 262 */     GameTaskQueueManager.getManager().update(new Callable()
/*     */         {
/*     */           public Object call() throws Exception {
/* 265 */             TCGDialog.showMessage("loginwindow.faileddialog.title", messageKey, NewLoginGameState.this, new NewLoginGameState.CloseLoadingWindowAction(NewLoginGameState.this.wizard));
/*     */             
/* 267 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void timeout() {
/* 275 */     GameTaskQueueManager.getManager().render(new Callable()
/*     */         {
/*     */           public Object call() throws Exception {
/* 278 */             TCGDialog.showMessage("loginwindow.timeoutdialog.title", "loginwindow.timeoutdialog.text", NewLoginGameState.this, new NewLoginGameState.CloseLoadingWindowAction(NewLoginGameState.this.wizard));
/*     */ 
/*     */             
/* 281 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void retryDifferentServer(InetSocketAddress socketAddress) {
/* 288 */     NetworkHandler.instance().loginDifferentServer(socketAddress, this.menuModel.getCharactersName(), this.menuModel.getPasswordHash(), this.menuModel.getStartConfig());
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
/*     */   public void loginFailedNeedCharacter() {
/* 302 */     throw new RuntimeException("login failed no character data set.");
/*     */   }
/*     */   
/*     */   private static class CloseLoadingWindowAction
/*     */     implements ActionListener {
/*     */     private StartMenuWizard wizard;
/*     */     
/*     */     public CloseLoadingWindowAction(StartMenuWizard wizard) {
/* 310 */       this.wizard = wizard;
/*     */     }
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent event) {
/* 315 */       BuiSystem.getRootNode().removeWindow((BWindow)MainGameState.getLoadingWindow());
/* 316 */       this.wizard.startWizard();
/* 317 */       this.wizard.showLogin();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class AsyncCreateCharacterTask
/*     */     extends AbstractCreateCharacterTask
/*     */   {
/*     */     private AsyncCreateCharacterTask(StartMenuWizard wizard, Localizer localizer, TCGDialog waitDialog, AsyncMessageSender<? extends Message> sender) {
/* 325 */       super(wizard, localizer, waitDialog, sender);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onOk(Message message) {
/* 330 */       CreateCharacterResponseMessage responseMessage = (CreateCharacterResponseMessage)message;
/* 331 */       AccountResult response = responseMessage.getResult();
/*     */       
/* 333 */       if (response == AccountResult.OK) {
/* 334 */         this.wizard.playerDataAccepted();
/*     */       } else {
/* 336 */         onError(response);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class AsyncNewCharacterTask
/*     */     extends AbstractCreateCharacterTask
/*     */   {
/*     */     private AsyncNewCharacterTask(StartMenuWizard wizard, Localizer localizer, TCGDialog waitDialog, AsyncMessageSender<? extends Message> sender) {
/* 345 */       super(wizard, localizer, waitDialog, sender);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onOk(Message message) {
/* 350 */       ServerStatesResponseMessage responseMessage = (ServerStatesResponseMessage)message;
/* 351 */       if (responseMessage.isCharacterCreateEnabled()) {
/* 352 */         String startMapId = FileUtils.trimTailingSlashes(responseMessage.getStartMap());
/* 353 */         this.wizard.setStartMap(startMapId);
/* 354 */         this.wizard.showNewPlayerWizard();
/*     */       } else {
/* 356 */         onError(AccountResult.ERROR_CREATION_DISABLED);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public StartMenuModel getMenuModel() {
/* 362 */     return this.menuModel;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\state\NewLoginGameState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */