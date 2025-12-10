/*     */ package com.funcom.tcg.client.ui.chat;
/*     */ import com.funcom.commons.jme.bui.IrregularButton;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.peeler.BananaPeel;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.conanchat.MessageType;
/*     */ import com.funcom.tcg.client.conanchat.SanctionManager;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.BScrollPaneTcg;
/*     */ import com.funcom.tcg.client.ui.BWindowTcg;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.account.RegisterWindow;
/*     */ import com.funcom.tcg.client.ui.friend.FriendsWindow;
/*     */ import com.funcom.tcg.client.ui.hud.GameWindows;
/*     */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*     */ import com.funcom.tcg.client.ui.startmenu.MessagedBTextField;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BTextField;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BoundedRangeModel;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.event.FocusEvent;
/*     */ import com.jmex.bui.event.FocusListener;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ import com.jmex.bui.event.MouseListener;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class BasicChatWindow extends BWindow implements PartiallyNotInteractive {
/*     */   private ChatNetworkController chatNetworkController;
/*  40 */   private String OWN_PLAYER = MainGameState.getPlayerModel().getName() + " "; private ResourceManager resourceManager; private static final String CHAT_TEXT_PATH = "gui/peeler/window_chat_text.xml";
/*  41 */   private final String ERROR_PLAYER = TcgGame.getLocalizedText("chat.message.sender.error", new String[0]);
/*  42 */   private final String WARNING_PLAYER = TcgGame.getLocalizedText("chat.message.sender.warning", new String[0]);
/*  43 */   private final String MODERATOR_PLAYER = TcgGame.getLocalizedText("chat.message.sender.moderator", new String[0]);
/*     */   
/*  45 */   private final String TELL_MSG = TcgGame.getLocalizedText("chat.tellto", new String[0]);
/*  46 */   private final int CHARACTER_CAP = 60;
/*  47 */   private final int MESSAGE_CAP = 100;
/*     */   
/*     */   private BTextField chatField;
/*     */   
/*     */   private BTextField tellField;
/*     */   private BLabel moveWindowLabel;
/*     */   private IrregularButton cannedChatButton;
/*     */   private BButton tellNameButton;
/*     */   private BScrollPaneTcg textScrollPane;
/*     */   private BContainer textContainer;
/*  57 */   private ArrayList<ChatTextContainer> chatTextContainers = new ArrayList<ChatTextContainer>();
/*     */   
/*  59 */   private final int WINDOW_WIDTH = 480;
/*  60 */   private final int WINDOW_HEIGHT = 345;
/*     */   
/*  62 */   private ChatMode sendMode = ChatMode.OWN;
/*  63 */   private String tellPlayerName = "";
/*  64 */   private int pendingMessageCtr = 0;
/*     */   private boolean snapToBottom = false;
/*     */   
/*     */   public BasicChatWindow(ChatNetworkController chatNetworkController, ResourceManager resourceManager) {
/*  68 */     super(null, (BLayoutManager)new AbsoluteLayout());
/*  69 */     this.chatNetworkController = chatNetworkController;
/*  70 */     this.resourceManager = resourceManager;
/*     */     
/*  72 */     this._style = BuiUtils.createMergedClassStyleSheets(BasicChatWindow.class, new BananaResourceProvider(resourceManager));
/*     */ 
/*     */     
/*  75 */     setLocation(0, 120);
/*  76 */     setSize(480, 345);
/*     */     
/*  78 */     initComponents();
/*  79 */     initListeners();
/*     */     
/*  81 */     this.chatField.setVisible(TcgGame.isAllowFreeChat());
/*  82 */     this.chatField.requestFocus();
/*     */   }
/*     */   
/*     */   private void initComponents() {
/*  86 */     this.moveWindowLabel = new BLabel("");
/*  87 */     this.moveWindowLabel.setStyleClass("label-move");
/*  88 */     BLabel moveWindowBgdLabel = new BLabel("");
/*  89 */     moveWindowBgdLabel.setStyleClass("label-move-bgd");
/*  90 */     this.moveWindowLabel.setTooltipText(TcgGame.getLocalizedText("tooltips.chat.move", new String[0]));
/*     */ 
/*     */     
/*  93 */     int rows = 5;
/*  94 */     int scrollHeight = 250;
/*  95 */     this.textContainer = new BContainer((BLayoutManager)new TcgGridLayout(1, rows, 0, 0));
/*     */     
/*  97 */     this.textScrollPane = new BScrollPaneTcg((BComponent)this.textContainer, false, true, scrollHeight / rows);
/*  98 */     this.textContainer.setStyleClass("chatwindow-scroll");
/*  99 */     this.textScrollPane.setStyleClass("chatwindow-scroll");
/*     */     
/* 101 */     BContainer container = new BContainer();
/* 102 */     container.setLayoutManager((BLayoutManager)new AbsoluteLayout());
/* 103 */     container.setStyleClass("chatwindow-window");
/*     */     
/* 105 */     this.cannedChatButton = new IrregularButton("");
/* 106 */     this.cannedChatButton.setStyleClass("button_chat");
/* 107 */     this.cannedChatButton.setTooltipText(TcgGame.getLocalizedText("tooltips.chat.canned", new String[0]));
/*     */     
/* 109 */     this.chatField = (BTextField)new MessagedBTextField(new ChatValidator());
/* 110 */     this.chatField.setStyleClass("textfield_chat");
/* 111 */     this.chatField.setMaxLength(60);
/* 112 */     this.chatField.setTooltipText(TcgGame.getLocalizedText("tooltips.chat.send", new String[0]));
/*     */     
/* 114 */     this.tellField = (BTextField)new MessagedBTextField(new ChatValidator());
/* 115 */     this.tellField.setStyleClass("textfield_tell");
/* 116 */     this.tellField.setMaxLength(60);
/*     */     
/* 118 */     this.tellNameButton = new BButton("");
/* 119 */     this.tellNameButton.setStyleClass("tell_name");
/* 120 */     this.tellNameButton.setTooltipText(TcgGame.getLocalizedText("tooltips.chat.tell.off", new String[] { "" }));
/*     */     
/* 122 */     int buttonSize = 46;
/* 123 */     int OFFSET_Y = 5;
/*     */     
/* 125 */     container.add((BComponent)this.chatField, new Rectangle(5, OFFSET_Y, 465, buttonSize));
/* 126 */     container.add((BComponent)this.tellField, new Rectangle(5, OFFSET_Y, 465, buttonSize));
/* 127 */     container.add((BComponent)this.tellNameButton, new Rectangle(10, OFFSET_Y + 3, 80, 40));
/* 128 */     container.add((BComponent)this.textScrollPane, new Rectangle(0, OFFSET_Y + 55, 475, scrollHeight));
/*     */     
/* 130 */     container.add((BComponent)moveWindowBgdLabel, new Rectangle(0, 312, 475, 28));
/* 131 */     container.add((BComponent)this.moveWindowLabel, new Rectangle(0, 312, buttonSize, 28));
/* 132 */     container.add((BComponent)this.cannedChatButton, new Rectangle(45, 315, 27, 22));
/*     */     
/* 134 */     add((BComponent)container, new Rectangle(0, 0, 480, 345));
/*     */   }
/*     */   
/*     */   private void initListeners() {
/* 138 */     BWindowTcg.MoveWindowListener windowMovementListener = new BWindowTcg.MoveWindowListener(this);
/* 139 */     this.moveWindowLabel.addListener((ComponentListener)windowMovementListener);
/*     */ 
/*     */     
/* 142 */     this.cannedChatButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           
/*     */           public void actionPerformed(ActionEvent event)
/*     */           {
/* 147 */             MainGameState.getHudModel().cannedChatButtonAction();
/*     */           }
/*     */         });
/*     */     
/* 151 */     this.chatField.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 154 */             if (!BasicChatWindow.this.chatField.getText().trim().isEmpty()) {
/* 155 */               BasicChatWindow.this.sendMessage();
/*     */             }
/*     */           }
/*     */         });
/*     */     
/* 160 */     this.tellField.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 163 */             if (!BasicChatWindow.this.tellField.getText().trim().isEmpty()) {
/* 164 */               BasicChatWindow.this.sendMessage();
/*     */             } else {
/* 166 */               BasicChatWindow.this.tellMode("", false);
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 171 */     this.tellNameButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 174 */             BasicChatWindow.this.tellMode("", false);
/*     */           }
/*     */         });
/*     */     
/* 178 */     this.chatField.addFocusListener(new FocusListener()
/*     */         {
/*     */           public void focusGained(FocusEvent event) {
/* 181 */             BWindow window = TcgUI.getWindowFromClass(FriendsWindow.class);
/* 182 */             if (window != null) {
/* 183 */               PanelManager.getInstance().closeWindow(window);
/*     */             }
/* 185 */             window = TcgUI.getWindowFromClass(RegisterWindow.class);
/* 186 */             if (window != null) {
/* 187 */               PanelManager.getInstance().closeWindow(window);
/*     */             }
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void focusLost(FocusEvent event) {}
/*     */         });
/* 196 */     this.tellField.addFocusListener(new FocusListener()
/*     */         {
/*     */           public void focusGained(FocusEvent event) {
/* 199 */             BWindow window = TcgUI.getWindowFromClass(FriendsWindow.class);
/* 200 */             if (window != null) {
/* 201 */               MainGameState.getGuiWindowsController().toggleWindow(GameWindows.FRIENDS);
/*     */             }
/*     */           }
/*     */ 
/*     */           
/*     */           public void focusLost(FocusEvent event) {}
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendMessage() {
/* 212 */     SanctionManager sanctionManager = SanctionManager.getInstance();
/* 213 */     if (!sanctionManager.isAllowedToSend()) {
/*     */       
/* 215 */       if (sanctionManager.getSanctionMessage().length() > 0)
/* 216 */         MainGameState.addMessage(sanctionManager.displaySanctionMessage(), MessageType.moderator); 
/* 217 */       this.chatField.setText("");
/* 218 */       this.tellField.setText("");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 223 */     String modifier = "";
/* 224 */     String ownModifier = "";
/* 225 */     switch (this.sendMode) {
/*     */       case TELL:
/* 227 */         modifier = "/tell " + this.tellPlayerName + " ";
/* 228 */         ownModifier = "/tell (" + this.tellPlayerName + ") ";
/*     */         break;
/*     */       default:
/*     */         try {
/* 232 */           if (this.chatField.getText().startsWith("/tell")) {
/* 233 */             String message = this.chatField.getText();
/* 234 */             int playerIndex = "/tell ".length();
/* 235 */             this.tellPlayerName = message.substring(playerIndex, message.indexOf(" ", playerIndex));
/* 236 */             ownModifier = "/tell (" + this.tellPlayerName + ") ";
/* 237 */             modifier = "/tell " + this.tellPlayerName + " ";
/* 238 */             message = message.substring(playerIndex + this.tellPlayerName.length());
/* 239 */             this.chatField.setText(message);
/*     */           } 
/* 241 */         } catch (Exception e) {
/* 242 */           this.chatField.setText("");
/* 243 */           this.tellField.setText("");
/*     */           return;
/*     */         } 
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 251 */     this.chatNetworkController.sendMessage(modifier + (this.sendMode.equals(ChatMode.TELL) ? this.tellField.getText() : this.chatField.getText()));
/*     */     
/* 253 */     addMessage(this.OWN_PLAYER, ownModifier + (this.sendMode.equals(ChatMode.TELL) ? this.tellField.getText() : this.chatField.getText()), false);
/*     */     
/* 255 */     this.chatField.setText("");
/* 256 */     this.tellField.setText("");
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMessage(String playerId, String message, boolean canned) {
/* 261 */     if (playerId.equals(MainGameState.getPlayerModel().getName()) && !canned) {
/*     */       return;
/*     */     }
/* 264 */     ChatMode mode = ChatMode.DEFAULT;
/* 265 */     String code = "";
/*     */     try {
/* 267 */       code = playerId.substring(playerId.indexOf("#"));
/* 268 */     } catch (Exception e) {}
/*     */ 
/*     */ 
/*     */     
/* 272 */     playerId = playerId.split("#")[0];
/* 273 */     if ((playerId.trim().equals(MainGameState.getPlayerModel().getName()) || playerId.equals(TcgGame.getLocalizedText("chat.message.sender.me", new String[0]))) && canned)
/*     */     {
/* 275 */       playerId = this.OWN_PLAYER;
/*     */     }
/*     */ 
/*     */     
/* 279 */     if (code.equals(MessageType.error.getNickname())) {
/* 280 */       playerId = this.ERROR_PLAYER;
/* 281 */       mode = ChatMode.ERROR;
/* 282 */     } else if (code.equals(MessageType.warning.getNickname())) {
/* 283 */       playerId = this.WARNING_PLAYER;
/* 284 */       mode = ChatMode.WARNING;
/* 285 */     } else if (code.equals(MessageType.whisper.getNickname())) {
/* 286 */       mode = ChatMode.WHISPER;
/* 287 */       if (!isVisible()) {
/* 288 */         this.pendingMessageCtr++;
/* 289 */         MainGameState.getHudModel().chatPendingAction(this.pendingMessageCtr);
/*     */       } 
/* 291 */     } else if (code.equals(MessageType.moderator.getNickname())) {
/* 292 */       playerId = this.MODERATOR_PLAYER;
/* 293 */       mode = ChatMode.MODERATOR;
/* 294 */     } else if (message.startsWith("/tell")) {
/* 295 */       message = message.substring("/tell".length() + 1);
/* 296 */       mode = ChatMode.TELL;
/*     */     
/*     */     }
/* 299 */     else if (playerId.equals(this.OWN_PLAYER)) {
/* 300 */       mode = ChatMode.OWN;
/*     */     } 
/*     */     
/* 303 */     showMessage(playerId, message, mode);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMessage(String playerId, String message, boolean canned, int playerSourceId) {
/* 308 */     if (playerId.equals(MainGameState.getPlayerModel().getName()) && !canned) {
/*     */       return;
/*     */     }
/* 311 */     boolean keepNumber = false;
/* 312 */     ChatMode mode = ChatMode.DEFAULT;
/* 313 */     String code = "";
/*     */     try {
/* 315 */       code = playerId.substring(playerId.indexOf("#"));
/* 316 */     } catch (Exception e) {}
/*     */ 
/*     */ 
/*     */     
/* 320 */     playerId = playerId.split("#")[0];
/* 321 */     if ((playerId.equals(MainGameState.getPlayerModel().getName()) || playerId.equals(TcgGame.getLocalizedText("chat.message.sender.me", new String[0]))) && canned)
/*     */     {
/* 323 */       playerId = this.OWN_PLAYER;
/*     */     }
/*     */ 
/*     */     
/* 327 */     if (code.equals(MessageType.error.getNickname())) {
/* 328 */       playerId = this.ERROR_PLAYER;
/* 329 */       mode = ChatMode.ERROR;
/* 330 */     } else if (code.equals(MessageType.warning.getNickname())) {
/* 331 */       playerId = this.WARNING_PLAYER;
/* 332 */       mode = ChatMode.WARNING;
/* 333 */     } else if (code.equals(MessageType.whisper.getNickname())) {
/* 334 */       mode = ChatMode.WHISPER;
/* 335 */       if (!isVisible()) {
/* 336 */         this.pendingMessageCtr++;
/* 337 */         MainGameState.getHudModel().chatPendingAction(this.pendingMessageCtr);
/*     */       } 
/* 339 */       keepNumber = true;
/* 340 */     } else if (code.equals(MessageType.moderator.getNickname())) {
/* 341 */       playerId = this.MODERATOR_PLAYER;
/* 342 */       mode = ChatMode.MODERATOR;
/* 343 */     } else if (message.startsWith("/tell")) {
/* 344 */       message = message.substring("/tell".length() + 1);
/* 345 */       mode = ChatMode.TELL;
/*     */     }
/* 347 */     else if (playerId.equals(this.OWN_PLAYER)) {
/* 348 */       mode = ChatMode.OWN;
/*     */     } else {
/* 350 */       keepNumber = true;
/*     */     } 
/* 352 */     if (keepNumber) {
/* 353 */       showMessage(playerId, message, mode, playerSourceId);
/*     */     } else {
/* 355 */       showMessage(playerId, message, mode);
/*     */     } 
/*     */   }
/*     */   private void showMessage(String playerId, String message, ChatMode mode, int playerSourceId) {
/* 359 */     BananaPeel bananaPeelNames = (BananaPeel)this.resourceManager.getResource(BananaPeel.class, "gui/peeler/window_chat_text.xml", CacheType.NOT_CACHED);
/*     */     
/* 361 */     ChatTextContainer chatTextContainer = new ChatTextContainer(bananaPeelNames, playerId, message, mode, Integer.valueOf(playerSourceId));
/*     */ 
/*     */     
/* 364 */     addChatTextContainer(chatTextContainer);
/*     */   }
/*     */   
/*     */   public void showMessage(String playerId, String message, ChatMode mode) {
/* 368 */     BananaPeel bananaPeelNames = (BananaPeel)this.resourceManager.getResource(BananaPeel.class, "gui/peeler/window_chat_text.xml", CacheType.NOT_CACHED);
/*     */     
/* 370 */     ChatTextContainer chatTextContainer = new ChatTextContainer(bananaPeelNames, playerId, message, mode);
/*     */ 
/*     */     
/* 373 */     addChatTextContainer(chatTextContainer);
/*     */   }
/*     */   
/*     */   private void addChatTextContainer(ChatTextContainer chatTextContainer) {
/* 377 */     if (this.textContainer.getComponentCount() >= 100) {
/* 378 */       this.textContainer.remove(0);
/* 379 */       this.chatTextContainers.remove(0);
/*     */     } 
/* 381 */     this.textContainer.add((BComponent)chatTextContainer.getMainContainer());
/* 382 */     this.chatTextContainers.add(chatTextContainer);
/*     */     
/* 384 */     BoundedRangeModel vModel = this.textScrollPane.getVerticalViewportModel();
/* 385 */     this.snapToBottom = (vModel.getValue() == vModel.getMaximum() - vModel.getExtent());
/*     */   }
/*     */ 
/*     */   
/*     */   public void tellMode(String playerName, boolean init) {
/* 390 */     this.sendMode = init ? ChatMode.TELL : ChatMode.OWN;
/* 391 */     this.tellPlayerName = init ? playerName : "";
/* 392 */     this.tellNameButton.setVisible(init);
/* 393 */     this.tellNameButton.setText(this.tellPlayerName);
/* 394 */     this.tellNameButton.setTooltipText(TcgGame.getLocalizedText("tooltips.chat.tell.off", new String[] { this.tellPlayerName }));
/*     */     
/* 396 */     this.chatField.setText("");
/* 397 */     this.tellField.setText("");
/* 398 */     this.chatField.setVisible(!init);
/* 399 */     this.tellField.setVisible((init && TcgGame.isAllowFreeChat()));
/* 400 */     if (init) {
/* 401 */       this.tellField.requestFocus();
/*     */     } else {
/* 403 */       this.chatField.requestFocus();
/*     */     } 
/*     */   }
/*     */   
/*     */   public BTextField getChatField() {
/* 408 */     return this.chatField;
/*     */   }
/*     */   
/*     */   public BTextField getTellField() {
/* 412 */     return this.tellField;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 417 */     super.setVisible(visible);
/* 418 */     if (visible) {
/*     */       
/* 420 */       this.pendingMessageCtr = 0;
/* 421 */       MainGameState.getHudModel().chatPendingAction(this.pendingMessageCtr);
/* 422 */       resetAll();
/*     */     } 
/*     */     
/* 425 */     this.sendMode = ChatMode.OWN;
/* 426 */     this.tellPlayerName = "";
/* 427 */     this.tellField.setVisible(false);
/* 428 */     this.tellNameButton.setVisible(false);
/*     */ 
/*     */     
/* 431 */     this.OWN_PLAYER = MainGameState.getPlayerModel().getName() + " ";
/*     */     
/* 433 */     this.chatField.setVisible(TcgGame.isAllowFreeChat());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderComponent(Renderer renderer) {
/* 438 */     BoundedRangeModel vModel = this.textScrollPane.getVerticalViewportModel();
/* 439 */     super.renderComponent(renderer);
/* 440 */     if (this.snapToBottom) {
/* 441 */       vModel.setValue(vModel.getMaximum());
/* 442 */       this.snapToBottom = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHit() {
/* 448 */     int mx = MouseInput.get().getXAbsolute();
/* 449 */     int my = MouseInput.get().getYAbsolute();
/*     */     
/* 451 */     return isHit(mx, my);
/*     */   }
/*     */   
/*     */   public boolean isHit(int mx, int my) {
/* 455 */     int xBound = 130 + getLocation()[0];
/* 456 */     int yBoundTop = 50 + getLocation()[1];
/* 457 */     int yBoundBottom = 5 + getLocation()[1];
/*     */     
/* 459 */     return ((mx >= getLocation()[0] && mx <= xBound) || (my >= getLocation()[1] && my <= yBoundTop && my >= yBoundBottom));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void resetAll() {
/* 465 */     for (ChatTextContainer chatText : this.chatTextContainers) {
/* 466 */       chatText.toggleOptions(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ChatValidator
/*     */     implements MessagedBTextField.CharValidator
/*     */   {
/*     */     private ChatValidator() {}
/*     */     
/*     */     public boolean isValid(char c) {
/* 477 */       return StringUtils.isCharacter(c);
/*     */     }
/*     */   }
/*     */   
/*     */   public enum ChatMode {
/* 482 */     DEFAULT,
/* 483 */     WHISPER,
/* 484 */     TELL,
/* 485 */     ERROR,
/* 486 */     WARNING,
/* 487 */     OWN,
/* 488 */     MODERATOR;
/*     */   }
/*     */ 
/*     */   
/*     */   public class ChatTextContainer
/*     */     extends BPeelContainer
/*     */   {
/*     */     private String playerName;
/*     */     private String message;
/*     */     private BasicChatWindow.ChatMode mode;
/*     */     private BButton playerNameButton;
/*     */     private BLabel playerMessageArea;
/* 500 */     private Integer playerSourceId = null;
/*     */     
/*     */     private BButton tellButton;
/*     */     private BButton blockButton;
/*     */     private BButton cancelButton;
/*     */     
/*     */     public ChatTextContainer(BananaPeel bananaPeel, String playerName, String message, BasicChatWindow.ChatMode mode) {
/* 507 */       super("", bananaPeel);
/* 508 */       this.playerName = playerName;
/* 509 */       this.message = message;
/* 510 */       this.mode = mode;
/*     */       
/* 512 */       initComponents();
/* 513 */       initListeners();
/*     */     }
/*     */     
/*     */     public ChatTextContainer(BananaPeel bananaPeelNames, String playerId, String message, BasicChatWindow.ChatMode mode, Integer playerSourceId) {
/* 517 */       this(bananaPeelNames, playerId, message, mode);
/* 518 */       this.playerSourceId = playerSourceId;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void initComponents() {
/* 524 */       this.playerNameButton = new BButton(this.playerName);
/* 525 */       BComponent placeholder = findComponent(getMainContainer(), "player_name");
/* 526 */       overridePeelerComponent((BComponent)this.playerNameButton, placeholder);
/* 527 */       this.playerNameButton.setEnabled((!this.playerNameButton.getText().equals(BasicChatWindow.this.OWN_PLAYER) && !this.playerNameButton.getText().equals(BasicChatWindow.this.ERROR_PLAYER) && !this.playerNameButton.getText().equals(BasicChatWindow.this.WARNING_PLAYER) && !this.playerNameButton.getText().equals(BasicChatWindow.this.MODERATOR_PLAYER)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 533 */       if (this.playerNameButton.isEnabled()) this.playerNameButton.setTooltipText(TcgGame.getLocalizedText("tooltips.chat.tell", new String[0]));
/*     */       
/* 535 */       this.message = this.message.trim();
/* 536 */       this.playerMessageArea = new BLabel(this.message);
/* 537 */       placeholder = findComponent(getMainContainer(), "player_message");
/*     */       
/* 539 */       switch (this.mode) {
/*     */ 
/*     */         
/*     */         case ERROR:
/* 543 */           placeholder.setStyleClass("player_message_error");
/*     */           break;
/*     */         case TELL:
/* 546 */           placeholder.setStyleClass("player_message_tell");
/*     */           break;
/*     */         case WHISPER:
/* 549 */           placeholder.setStyleClass("player_message_whisper");
/*     */           break;
/*     */         case OWN:
/* 552 */           placeholder.setStyleClass("player_message_own");
/*     */           break;
/*     */         case MODERATOR:
/* 555 */           placeholder.setStyleClass("player_message_error");
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 560 */       overridePeelerComponent((BComponent)this.playerMessageArea, placeholder);
/*     */       
/* 562 */       this.tellButton = new BButton("TELL");
/* 563 */       placeholder = findComponent(getMainContainer(), "tell_action");
/* 564 */       overridePeelerComponent((BComponent)this.tellButton, placeholder);
/*     */       
/* 566 */       this.blockButton = new BButton("BLOCK");
/* 567 */       placeholder = findComponent(getMainContainer(), "block_action");
/* 568 */       overridePeelerComponent((BComponent)this.blockButton, placeholder);
/*     */       
/* 570 */       this.cancelButton = new BButton("X");
/* 571 */       placeholder = findComponent(getMainContainer(), "cancel_action");
/* 572 */       overridePeelerComponent((BComponent)this.cancelButton, placeholder);
/* 573 */       this.cancelButton.setTooltipText(TcgGame.getLocalizedText("tooltips.chat.cancel", new String[0]));
/*     */       
/* 575 */       toggleOptions(false);
/*     */     }
/*     */ 
/*     */     
/*     */     private void initListeners() {
/* 580 */       this.tellButton.addListener((ComponentListener)new ActionListener()
/*     */           {
/*     */             public void actionPerformed(ActionEvent event) {
/* 583 */               if (TcgGame.isAllowFreeChat() && BasicChatWindow.ChatTextContainer.this.playerSourceId != null) {
/* 584 */                 BasicChatWindow.this.tellMode(BasicChatWindow.ChatTextContainer.this.playerName, true);
/*     */               }
/* 586 */               BasicChatWindow.ChatTextContainer.this.toggleOptions(false);
/*     */             }
/*     */           });
/*     */       
/* 590 */       this.blockButton.addListener((ComponentListener)new ActionListener()
/*     */           {
/*     */             public void actionPerformed(ActionEvent event) {
/* 593 */               if (BasicChatWindow.ChatTextContainer.this.playerSourceId != null) {
/* 594 */                 MainGameState.getFriendModel().blockPlayer(BasicChatWindow.ChatTextContainer.this.playerSourceId.intValue());
/*     */               }
/* 596 */               BasicChatWindow.ChatTextContainer.this.toggleOptions(false);
/*     */             }
/*     */           });
/* 599 */       this.cancelButton.addListener((ComponentListener)new ActionListener()
/*     */           {
/*     */             public void actionPerformed(ActionEvent event) {
/* 602 */               BasicChatWindow.ChatTextContainer.this.toggleOptions(false);
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 607 */       this.playerNameButton.addListener((ComponentListener)new MouseListener()
/*     */           {
/*     */             public void mouseEntered(MouseEvent event) {}
/*     */ 
/*     */ 
/*     */             
/*     */             public void mouseExited(MouseEvent event) {}
/*     */ 
/*     */ 
/*     */             
/*     */             public void mousePressed(MouseEvent event) {
/* 618 */               if (BasicChatWindow.ChatTextContainer.this.playerNameButton.isEnabled()) {
/* 619 */                 if (event.getButton() == 0) {
/* 620 */                   if (TcgGame.isAllowFreeChat() && BasicChatWindow.ChatTextContainer.this.playerSourceId != null) {
/* 621 */                     BasicChatWindow.this.tellMode(BasicChatWindow.ChatTextContainer.this.playerName, true);
/*     */                   }
/*     */                 } else {
/* 624 */                   BasicChatWindow.ChatTextContainer.this.toggleOptions(true);
/*     */                 } 
/*     */               }
/*     */             }
/*     */ 
/*     */             
/*     */             public void mouseReleased(MouseEvent event) {}
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     public void toggleOptions(boolean visible) {
/* 636 */       if (visible) {
/* 637 */         BasicChatWindow.this.resetAll();
/*     */       }
/* 639 */       this.tellButton.setVisible(visible);
/* 640 */       this.blockButton.setVisible(visible);
/* 641 */       this.cancelButton.setVisible(visible);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setVisible(boolean visible) {
/* 646 */       super.setVisible(visible);
/* 647 */       toggleOptions(false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\chat\BasicChatWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */