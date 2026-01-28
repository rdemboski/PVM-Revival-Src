/*     */ package com.funcom.tcg.client.ui.hud;
/*     */ import com.funcom.commons.FileUtils;
/*     */ import com.funcom.gameengine.WorldCoordinate;
import com.funcom.gameengine.ai.Brain;
/*     */ import com.funcom.gameengine.ai.DeadBrain;
import com.funcom.gameengine.jme.modular.ModularDescription;
/*     */ import com.funcom.gameengine.model.chunks.ChunkWorldInfo;
/*     */ import com.funcom.gameengine.model.factories.MapObjectBuilder;
import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
import com.funcom.gameengine.view.AnimationMapper;
/*     */ import com.funcom.gameengine.view.MapObject;
/*     */ import com.funcom.gameengine.view.ModularNode;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.peeler.BananaPeel;
/*     */ import com.funcom.rpgengine2.creatures.RpgCreatureConstants;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.TcgConstants;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*     */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.Localizer;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.achievements.AchievementsWindow;
/*     */ import com.funcom.tcg.client.ui.character.CharacterEquipmentWindow;
/*     */ import com.funcom.tcg.client.ui.character.CharacterWindowModel;
/*     */ import com.funcom.tcg.client.ui.character.ClientCharacterWindowModel;
/*     */ import com.funcom.tcg.client.ui.chat.BasicChatWindow;
/*     */ import com.funcom.tcg.client.ui.chat.ChatWindow;
/*     */ import com.funcom.tcg.client.ui.duel.DuelHealthBarWindow;
/*     */ import com.funcom.tcg.client.ui.friend.FriendsWindow;
/*     */ import com.funcom.tcg.client.ui.hud2.PreviewModularDescription;
/*     */ import com.funcom.tcg.client.ui.hud2.TCGClientHudModel;
/*     */ import com.funcom.tcg.client.ui.hud2.TCGQuestWindowModel;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryWindow;
import com.funcom.tcg.client.ui.mainmenu.MainMenuModel;
/*     */ import com.funcom.tcg.client.ui.mainmenu.MainMenuWindow;
import com.funcom.tcg.client.ui.mainmenu.TCGMainMenuModel;
/*     */ import com.funcom.tcg.client.ui.maps.MapModel;
/*     */ import com.funcom.tcg.client.ui.maps.MapWindow2;
/*     */ import com.funcom.tcg.client.ui.pause.PauseModel;
/*     */ import com.funcom.tcg.client.ui.pause.PauseWindow;
/*     */ import com.funcom.tcg.client.ui.pets3.ClientPetsWindowModel;
/*     */ import com.funcom.tcg.client.ui.pets3.PetsWindow;
import com.funcom.tcg.client.ui.pets3.PetsWindowModel;
/*     */ import com.funcom.tcg.client.ui.quest2.QuestWindow2;
import com.funcom.tcg.client.ui.quest2.QuestWindowModel;
/*     */ import com.funcom.tcg.client.ui.vendor.VendorFullWindow;
/*     */ import com.funcom.tcg.net.message.RequestDynamicObjectsMessage;
/*     */ import com.funcom.tcg.net.message.RequestInventorySyncMessage;
/*     */ import com.funcom.tcg.rpg.ItemHolderType;
/*     */ import com.funcom.tcg.token.TCGWorld;
import com.jme.scene.Spatial;
/*     */ import com.jmex.bui.BWindow;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ public class TCGWindowsController implements GuiWindowsController {
/*  56 */   private static final Logger LOGGER = Logger.getLogger(TCGWindowsController.class.getName());
/*  57 */   private GameWindows tutorialWindow = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setWindowVisible(GameWindows window, boolean setVisible) {
/*  65 */     if (setVisible && !isWindowVisible(window)) {
/*  66 */       if (window.isFullScreen() && window != GameWindows.PAUSE && 
/*  67 */         !MainGameState.getPauseModel().isPaused() && (
/*  68 */         !TcgGame.isTutorialMode() || (!window.equals(GameWindows.MAP) && !window.equals(GameWindows.ACHIEVEMENTS)))) {
/*  69 */         MainGameState.getPauseModel().activatePause();
/*     */       }
/*  71 */       addWindow(window);
/*  72 */     } else if (!setVisible && isWindowVisible(window)) {
/*  73 */       if (window.isFullScreen() && window != GameWindows.PAUSE && (
/*  74 */         !TcgGame.isTutorialMode() || (!window.equals(GameWindows.MAP) && !window.equals(GameWindows.ACHIEVEMENTS)))) {
/*  75 */         MainGameState.getPauseModel().reset();
/*     */       }
/*  77 */       dismissWindow(window);
/*  78 */       if (window.equals(GameWindows.CHAT)) {
/*  79 */         dismissWindow(GameWindows.CANNED_CHAT);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void dismissWindow(GameWindows windowType) {
/*  86 */     if (windowType == GameWindows.PAUSE) {
/*  87 */       togglePauseWindow();
/*  88 */     } else if (TcgUI.isWindowOpen(windowType.getWindowClass())) {
/*  89 */       BWindow window = TcgUI.getWindowFromClass(windowType.getWindowClass());
/*  90 */       if (window != null) {
/*  91 */         PanelManager.getInstance().closeWindow(window);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addWindow(GameWindows window) {
/* 103 */     switch (window) {
/*     */       case WARDROBE:
/* 105 */         addWardrobeWindow();
/*     */         break;
/*     */       case INVENTORY:
/* 108 */         addInventoryWindow();
/*     */         break;
/*     */       case CHARACTER:
/* 111 */         addCharacterWindow();
/*     */         break;
/*     */       case MAP:
/* 114 */         addMapWindow();
/*     */         break;
/*     */       case QUEST_WINDOW:
/* 117 */         addQuestWindow();
/*     */         break;
/*     */       case MAIN_MENU:
/* 120 */         addMainMenuWindow();
/*     */         break;
/*     */       case PETS_AND_SKILLS:
/* 123 */         addPetsAndSkillsWindow();
/*     */         break;
/*     */       case CHAT:
/* 126 */         addChatWindow();
/*     */         break;
/*     */       case PAUSE:
/* 129 */         togglePauseWindow();
/*     */         break;
/*     */       case FRIENDS:
/* 132 */         addFriendsWindow();
/*     */         break;
/*     */       case CANNED_CHAT:
/* 135 */         addCannedChatWindow();
/*     */         break;
/*     */       case ACHIEVEMENTS:
/* 138 */         addAchievementsWindow();
/*     */         break;
/*     */       case DUEL_HEALTH_BARS:
/* 141 */         addDuelHealthBarWindow();
/*     */         break;
/*     */       case VENDOR:
/* 144 */         addVendorWindow();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void togglePauseWindow() {
/* 152 */     PauseModel pauseModel = MainGameState.getPauseModel();
/* 153 */     if (!TcgUI.isWindowOpen(PauseWindow.class)) {
/* 154 */       pauseModel.activatePause();
/*     */     } else {
/* 156 */       pauseModel.reset();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addFriendsWindow() {
/* 161 */     FriendsWindow friendsWindow = MainGameState.getFriendsWindow();
/* 162 */     if (friendsWindow == null) {
/*     */       
/* 164 */       BananaPeel bananaPeel = (BananaPeel)TcgGame.getResourceManager().getResource(BananaPeel.class, "gui/peeler/window_friends.xml", CacheType.NOT_CACHED);
/*     */       
/* 166 */       friendsWindow = new FriendsWindow("gui/peeler/window_friends.xml", bananaPeel, TcgGame.getResourceManager(), MainGameState.getHudModel(), MainGameState.getFriendModel());
/*     */       
/* 168 */       MainGameState.setFriendsWindow(friendsWindow);
/*     */     } 
/*     */     
/* 171 */     PanelManager.getInstance().addWindow((BWindow)friendsWindow);
/*     */   }
/*     */   
/*     */   private void addChatWindow() {
/* 175 */     BasicChatWindow chatWindow = MainGameState.getBasicChatWindow();
/* 176 */     if (chatWindow == null) {
/* 177 */       chatWindow = new BasicChatWindow(MainGameState.getChatNetworkController(), TcgGame.getResourceManager());
/* 178 */       MainGameState.setBasicChatWindow(chatWindow);
/*     */     } 
/*     */     
/* 181 */     PanelManager.getInstance().addWindow((BWindow)chatWindow);
/*     */   }
/*     */   
/*     */   private void addCannedChatWindow() {
/* 185 */     ChatWindow chatWindow = new ChatWindow(MainGameState.getChatNetworkController(), TcgGame.getResourceManager());
/*     */ 
/*     */     
/* 188 */     PanelManager.getInstance().addWindow((BWindow)chatWindow);
/*     */   }
/*     */   
/*     */   private void addVendorWindow() {
/* 192 */     VendorFullWindow vendorFullWindow = MainGameState.getVendorWindow();
/* 193 */     if (vendorFullWindow != null) {
/* 194 */       PanelManager.getInstance().addWindow((BWindow)vendorFullWindow);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean windowsOpen() {
/* 199 */     GameWindows[] windows = GameWindows.values();
/* 200 */     for (GameWindows window : windows) {
/* 201 */       if (TcgUI.isWindowOpen(window.getWindowClass()) && window != GameWindows.MAIN_MENU && window != GameWindows.PAUSE)
/*     */       {
/* 203 */         return true;
/*     */       }
/*     */     } 
/* 206 */     return false;
/*     */   }
/*     */   
/*     */   private void addMainMenuWindow() {
/* 210 */     if (!TcgUI.isWindowOpen(MainMenuWindow.class)) {
/* 211 */       MainMenuWindow mainMenuWindow = new MainMenuWindow(TcgGame.getResourceManager(), (MainMenuModel)new TCGMainMenuModel());
/* 212 */       mainMenuWindow.setLayer(4);
/* 213 */       PanelManager.getInstance().addWindow((BWindow)mainMenuWindow);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isWindowVisible(GameWindows window) {
/* 218 */     return TcgUI.isWindowOpen(window.getWindowClass());
/*     */   }
/*     */   
/*     */   private void addQuestWindow() {
/* 222 */     if (!TcgUI.isWindowOpen(QuestWindow2.class) && !TcgGame.isDueling()) {
/* 223 */       QuestWindow2 questWindow = MainGameState.getQuestWindow();
/* 224 */       if (questWindow == null) {
/* 225 */         BananaPeel bananaPeel = (BananaPeel)TcgGame.getResourceManager().getResource(BananaPeel.class, "gui/peeler/window_quest_during.xml", CacheType.NOT_CACHED);
/*     */         
/* 227 */         questWindow = new QuestWindow2("test", bananaPeel, TcgGame.getResourceManager(), (QuestWindowModel)new TCGQuestWindowModel(MainGameState.getQuestModel()), MainGameState.getToolTipManager());
/*     */         
/* 229 */         MainGameState.setQuestWindow(questWindow);
/*     */       } 
/* 231 */       PanelManager.getInstance().addWindow((BWindow)questWindow);
/* 232 */       questWindow.refresh();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addAchievementsWindow() {
/* 237 */     if (!TcgGame.isTutorialMode() && 
/* 238 */       !TcgUI.isWindowOpen(AchievementsWindow.class)) {
/* 239 */       AchievementsWindow achievementsWindow = MainGameState.getAchievementWindow();
/* 240 */       if (null == achievementsWindow) {
/* 241 */         BananaPeel bananaPeel = (BananaPeel)TcgGame.getResourceManager().getResource(BananaPeel.class, "gui/peeler/achievements_gui.xml", CacheType.NOT_CACHED);
/*     */         
/* 243 */         achievementsWindow = new AchievementsWindow(AchievementsWindow.class.getSimpleName(), bananaPeel, TcgGame.getResourceManager(), MainGameState.getQuestModel());
/* 244 */         MainGameState.setAchievementWindow(achievementsWindow);
/*     */       } 
/* 246 */       PanelManager.getInstance().addWindow((BWindow)achievementsWindow);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void addPetsAndSkillsWindow() {
/* 252 */     if (!TcgUI.isWindowOpen(PetsWindow.class)) {
/* 253 */       PetsWindow petsWindow = MainGameState.getPetsWindow();
/* 254 */       if (petsWindow == null) {
/* 255 */         ClientPetsWindowModel clientPetsWindowModel = new ClientPetsWindowModel(MainGameState.getPlayerModel(), MainGameState.getHudModel());
/*     */         
/* 257 */         petsWindow = new PetsWindow((PetsWindowModel)clientPetsWindowModel, TcgGame.getResourceManager(), MainGameState.getToolTipManager(), MainGameState.getPetRegistry(), TcgGame.getDireEffectDescriptionFactory(), (ClientPlayer)MainGameState.getPlayerModel(), clientPetsWindowModel.isSubscriber(), (Localizer)MainGameState.getInstance());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 264 */         MainGameState.setPetsWindow(petsWindow);
/*     */       } 
/* 266 */       PanelManager.getInstance().addWindow((BWindow)petsWindow);
/* 267 */       petsWindow.reinitialize();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addInventoryWindow() {
/* 272 */     LocalClientPlayer clientPlayer = MainGameState.getPlayerModel();
/* 273 */     if (!TcgUI.isWindowOpen(InventoryWindow.class)) {
/* 274 */       InventoryWindow window = MainGameState.getInventoryWindow();
/* 275 */       if (window == null) {
/* 276 */         window = new InventoryWindow(clientPlayer.getInventory(), clientPlayer);
/* 277 */         MainGameState.setInventoryWindow(window);
/* 278 */         clientPlayer.requestInventorySync();
/*     */       } 
/* 280 */       PanelManager.getInstance().addWindow((BWindow)window);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void addWardrobeWindow() {
/* 286 */     addCharacterWindow();
/*     */   }
/*     */   
/*     */   private void addCharacterWindow() {
/* 290 */     LocalClientPlayer localClientPlayer = MainGameState.getPlayerModel();
/* 291 */     if (!TcgUI.isWindowOpen(CharacterEquipmentWindow.class)) {
/* 292 */       CharacterEquipmentWindow window = MainGameState.getCharacterWindow();
/* 293 */       if (window == null) {
/* 294 */         window = new CharacterEquipmentWindow((CharacterWindowModel)new ClientCharacterWindowModel((ClientPlayer)localClientPlayer), TcgGame.getResourceManager(), MainGameState.getToolTipManager(), TcgGame.getDireEffectDescriptionFactory(), TcgGame.getVisualRegistry(), (Localizer)MainGameState.getInstance(), MainGameState.getHudModel());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 300 */         MainGameState.setCharacterWindow(window);
/*     */         
/* 302 */         RequestInventorySyncMessage inventorySyncMessage = new RequestInventorySyncMessage(ItemHolderType.EQUIPMENT_DOLL.getId(), localClientPlayer.getInventory().getId(), localClientPlayer.getId(), RpgCreatureConstants.Type.PLAYER_CREATURE_TYPE_ID.getTypeId());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 308 */           NetworkHandler.instance().getIOHandler().send((Message)inventorySyncMessage);
/* 309 */         } catch (InterruptedException e) {
/* 310 */           LOGGER.log((Priority)Level.ERROR, "Failed to send InventoryRequestMessage!", e);
/*     */         } 
/*     */       } 
/*     */       
/* 314 */       PanelManager.getInstance().addWindow((BWindow)window);
/* 315 */       window.reinitialize();
/*     */     } 
/*     */   }
/*     */   
/*     */   private PropNode createPlayerNodeCopy(ClientPlayer clientPlayer) {
/* 320 */     ClientPlayer previewPlayer = new ClientPlayer("previewPlayer", new WorldCoordinate(), (Brain)new DeadBrain(), 0.0D);
/* 321 */     PropNode propNode = new PropNode((Prop)previewPlayer, 3, "", TcgGame.getDireEffectDescriptionFactory());
/*     */ 
/*     */     
/* 324 */     PreviewModularDescription previewModularDescription = new PreviewModularDescription(clientPlayer, TcgGame.getVisualRegistry());
/*     */     
/* 326 */     ModularNode previewPlayerNode = new ModularNode((ModularDescription)previewModularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, TcgGame.getResourceManager());
/*     */ 
/*     */     
/* 329 */     previewPlayerNode.reloadCharacter();
/* 330 */     propNode.attachRepresentation((Spatial)previewPlayerNode);
/*     */     
/* 332 */     propNode.addDisposeListener(new PropNode.DisposeListener()
/*     */         {
/*     */           public void disposed(PropNode disposingNode) {
/* 335 */             ((ModularNode)disposingNode.getRepresentation()).dispose();
/*     */           }
/*     */         });
/*     */     
/* 339 */     propNode.setWorldOriginAligned(false);
/* 340 */     propNode.setAngle(0.0F);
/* 341 */     propNode.updateRenderState();
/*     */     
/* 343 */     return propNode;
/*     */   }
/*     */   
/*     */   private void addDuelHealthBarWindow() {
/* 347 */     if (!TcgUI.isWindowOpen(DuelHealthBarWindow.class)) {
/* 348 */       DuelHealthBarWindow duelHealthBarWindow = MainGameState.getDuelHealthBarWindow();
/* 349 */       if (duelHealthBarWindow == null) {
/* 350 */         duelHealthBarWindow = new DuelHealthBarWindow();
/* 351 */         MainGameState.setDuelHealthBarWindow(duelHealthBarWindow);
/*     */       } 
/* 353 */       PanelManager.getInstance().closeAll();
/* 354 */       PanelManager.getInstance().addWindow((BWindow)duelHealthBarWindow);
/*     */     }
/* 356 */     else if (MainGameState.getDuelHealthBarWindow() != null) {
/* 357 */       PanelManager.getInstance().closeWindow((BWindow)MainGameState.getDuelHealthBarWindow());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void addMapWindow() {
/* 363 */     if (!TcgGame.isTutorialMode() && 
/* 364 */       !TcgUI.isWindowOpen(MapWindow2.class)) {
/* 365 */       List<Integer> friendIds; MapWindow2 mapWindow = MainGameState.getMapWindow();
/* 366 */       PanelManager.getInstance().addWindow((BWindow)mapWindow);
/*     */ 
/*     */ 
/*     */       
/* 370 */       ChunkWorldInfo worldInfo = ((TCGWorld)MainGameState.getWorld()).getChunkWorldNode().getChunkWorldInfo();
/* 371 */       List<MapObject> mapObjects = (new MapObjectBuilder(TcgGame.getResourceGetter())).getMapObjectList(worldInfo);
/* 372 */       mapWindow.reset(new MapModel(worldInfo, mapObjects));
/*     */ 
/*     */       
/* 375 */       if (TcgGame.isChatEnabled()) {
/* 376 */         friendIds = new ArrayList<Integer>(MainGameState.getFriendModel().getFriendsList().keySet());
/*     */       } else {
/* 378 */         friendIds = new ArrayList<Integer>();
/*     */       } 
/* 380 */       RequestDynamicObjectsMessage requestDynamicObjectsMessage = new RequestDynamicObjectsMessage(MainGameState.getPlayerModel().getId(), friendIds, FileUtils.trimTailingSlashes(worldInfo.getMapId()));
/*     */ 
/*     */       
/*     */       try {
/* 384 */         NetworkHandler.instance().getIOHandler().send((Message)requestDynamicObjectsMessage);
/* 385 */       } catch (InterruptedException e) {
/* 386 */         LOGGER.log((Priority)Level.ERROR, "Failed to send InventoryRequestMessage!", e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void toggleWindow(GameWindows window) {
/* 395 */     if (TcgGame.isPetTutorial() || TcgGame.isEquipmentTutorial() || TcgGame.isDueling()) {
/*     */       
/* 397 */       if (window.equals(this.tutorialWindow)) {
/* 398 */         setWindowVisible(window, true);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 403 */     if (TcgGame.isStartDuelMode()) {
/* 404 */       TcgGame.setStartDuelMode(false);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 409 */     setWindowVisible(window, !isWindowVisible(window));
/* 410 */     TCGClientHudModel model = (TCGClientHudModel)MainGameState.getHudModel();
/* 411 */     switch (window) {
/*     */       
/*     */       case MAP:
/* 414 */         if (!TcgGame.isTutorialMode()) model.fireMapWindowToggled(); 
/*     */         break;
/*     */       case CHAT:
/* 417 */         model.fireChatWindowToggled();
/*     */         break;
/*     */       case PETS_AND_SKILLS:
/* 420 */         model.firePetsWindowToggled();
/*     */         break;
/*     */       case MAIN_MENU:
/* 423 */         model.fireOptionsWindowToggled();
/*     */         break;
/*     */       case CHARACTER:
/* 426 */         model.fireCharacterWindowToggled();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void openWindow(GameWindows window) {
/* 434 */     if (TcgGame.isPetTutorial() || TcgGame.isEquipmentTutorial())
/*     */     {
/* 436 */       if (window.equals(this.tutorialWindow)) {
/* 437 */         setWindowVisible(window, true);
/*     */         
/*     */         return;
/*     */       } 
/*     */     }
/* 442 */     setWindowVisible(window, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeAll() {
/* 447 */     PanelManager.getInstance().closeAll();
/* 448 */     if (MainGameState.getPauseModel().isPaused())
/* 449 */       MainGameState.getPauseModel().reset(); 
/*     */   }
/*     */   
/*     */   public void setTutorialWindow(GameWindows window) {
/* 453 */     this.tutorialWindow = window;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud\TCGWindowsController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */