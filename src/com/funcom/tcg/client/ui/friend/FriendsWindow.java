/*     */ package com.funcom.tcg.client.ui.friend;
/*     */ import com.funcom.commons.StringUtils;
/*     */ import com.funcom.commons.jme.bui.HighlightedButton;
/*     */ import com.funcom.commons.jme.bui.HighlightedRegularToggleButton;
/*     */ import com.funcom.commons.jme.bui.HighlightedToggleButton;
import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.peeler.BananaPeel;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.BPeelContainer;
/*     */ import com.funcom.tcg.client.ui.BPeelWindow;
/*     */ import com.funcom.tcg.client.ui.BScrollPaneTcg;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.hud.GameWindows;
/*     */ import com.funcom.tcg.client.ui.hud2.HudModel;
/*     */ import com.funcom.tcg.client.ui.startmenu.MessagedBTextField;
/*     */ import com.funcom.tcg.client.ui.vendor.TcgGridLayout;
/*     */ import com.funcom.tcg.net.Friend;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jmex.bui.BClickthroughLabel;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BScrollBar;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.event.FocusEvent;
/*     */ import com.jmex.bui.event.FocusListener;
/*     */ import com.jmex.bui.event.KeyEvent;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class FriendsWindow extends BPeelWindow {
/*     */   private ResourceManager resourceManager;
/*  40 */   private final int WINDOW_X = 5; private static final String FRIEND_NAME_PATH = "gui/peeler/window_friends_name.xml"; private final int WINDOW_Y = 40;
/*  41 */   private final String FRIEND_MODE_OFF = TcgGame.getLocalizedText("friend.mode.off", new String[0]);
/*  42 */   private final String FRIEND_MODE_ON = TcgGame.getLocalizedText("friend.mode.on", new String[0]);
/*  43 */   private final String BLOCKED_MODE_OFF = TcgGame.getLocalizedText("friend.mode.off.blocked", new String[0]);
/*  44 */   private final String BLOCKED_MODE_ON = TcgGame.getLocalizedText("friend.mode.on.blocked", new String[0]);
/*  45 */   private final String SEND_MSG = TcgGame.getLocalizedText("friend.msg.sendrequest", new String[0]);
/*  46 */   private final String SEND_QUERY_MSG = TcgGame.getLocalizedText("friend.query.sendrequest", new String[0]);
/*  47 */   private final String SEND_QUERY_MSG_BLOCKED = TcgGame.getLocalizedText("friend.query.sendrequest.blocked", new String[0]);
/*  48 */   private final String RECEIVE_MSG = TcgGame.getLocalizedText("friend.msg.receive", new String[0]);
/*  49 */   private final String RECEIVE_QUERY_MSG = TcgGame.getLocalizedText("friend.query.receive", new String[0]);
/*  50 */   private final String NOT_FOUND_MSG = TcgGame.getLocalizedText("friend.msg.notfound", new String[0]);
/*  51 */   private final String TRY_AGAIN_QUERY_MSG = TcgGame.getLocalizedText("friend.query.notfound", new String[0]);
/*  52 */   private final String ADD_SELF_MSG = TcgGame.getLocalizedText("friend.msg.cannotaddself", new String[0]);
/*  53 */   private final String ALREADY_MSG = TcgGame.getLocalizedText("friend.msg.alreadyfriend", new String[0]);
/*  54 */   private final String PENDING_MSG = TcgGame.getLocalizedText("friend.msg.pending.sameplayerrequest", new String[0]);
/*  55 */   private final String PENDING_QUERY_MSG = TcgGame.getLocalizedText("friend.query.pending.checkrequests", new String[0]);
/*  56 */   private final String REMOVE_MSG = TcgGame.getLocalizedText("friend.msg.removefriend", new String[0]);
/*  57 */   private final String SURE_QUERY_MSG = TcgGame.getLocalizedText("friend.query.general", new String[0]);
/*  58 */   private final String ALREADY_BLOCKED = TcgGame.getLocalizedText("friend.msg.alreadyblocked", new String[0]);
/*  59 */   private final String UNBLOCK_MSG = TcgGame.getLocalizedText("friend.msg.unblock", new String[0]);
/*  60 */   private final String UNBLOCK_ADD_MSG = TcgGame.getLocalizedText("friend.unblock.add", new String[0]);
/*  61 */   private final String UNBLOCK_ADD_QUERY_MSG = TcgGame.getLocalizedText("friend.query.unblock", new String[0]);
/*     */   
/*     */   private BLabel header;
/*     */   
/*     */   private BLabel friendNameLabel;
/*     */   
/*     */   private BLabel interactionLabel;
/*     */   
/*     */   private BLabel interactionQueryLabel;
/*     */   
/*     */   private HighlightedButton pendingRequestsButton;
/*     */   
/*     */   private HighlightedToggleButton friendModeToggleButton;
/*     */   
/*     */   private HighlightedRegularToggleButton blockedToggleButton;
/*     */   
/*     */   private HighlightedButton closeWindowButton;
/*     */   
/*     */   private HighlightedButton confirmFriendButton;
/*     */   
/*     */   private HighlightedButton rejectFriendButton;
/*     */   private HighlightedButton blockPlayerButton;
/*     */   private FriendSearchTextField searchFriendField;
/*     */   private BScrollPaneTcg friendPane;
/*     */   private BScrollPaneTcg blockedPane;
/*     */   private BContainer friendsListContainer;
/*     */   private BContainer blockedListContainer;
/*     */   private BContainer interactionContainer;
/*     */   private BClickthroughLabel searchIconLabel;
/*  90 */   private ArrayList<FriendNameContainer> friends = new ArrayList<FriendNameContainer>();
/*  91 */   private ArrayList<FriendNameContainer> blockedPlayers = new ArrayList<FriendNameContainer>();
/*  92 */   private ArrayList<FriendRequest> requests = new ArrayList<FriendRequest>();
/*     */   
/*  94 */   private Rectangle[] rejectBounds = new Rectangle[2];
/*     */   
/*     */   private HudModel hudModel;
/*     */   private FriendModel friendModel;
/*     */   private boolean friendModeActive = false;
/*  99 */   private InteractionType type = InteractionType.SEND;
/*     */   
/*     */   private int playerRequestId;
/*     */   
/*     */   private String playerRequestNickname;
/*     */   
/*     */   private FriendNameContainer removeContainer;
/*     */   
/*     */   private BScrollBar blockedBar;
/*     */   
/*     */   private BScrollBar friendBar;
/*     */   private boolean blockedMode;
/*     */   
/*     */   public FriendsWindow(String windowName, BananaPeel bananaPeel, ResourceManager resourceManager, HudModel hudModel, FriendModel friendModel) {
/* 113 */     super(windowName, bananaPeel);
/* 114 */     this.resourceManager = resourceManager;
/* 115 */     this.hudModel = hudModel;
/* 116 */     this.friendModel = friendModel;
/*     */     
/* 118 */     BananaResourceProvider buiResourceProvider = new BananaResourceProvider(resourceManager);
/*     */ 
/*     */     
/* 121 */     setLocation(5, 40);
/* 122 */     initComponents();
/* 123 */     initListeners();
/* 124 */     setLocation(0, 110);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initComponents() {
/* 131 */     this.header = new BLabel("");
/* 132 */     BComponent placeholder = findComponent((BContainer)this, "text_header");
/* 133 */     overridePeelerComponent((BComponent)this.header, placeholder);
/* 134 */     this.header.setText(TcgGame.getLocalizedText("friend.mode.friend", new String[0]));
/*     */     
/* 136 */     this.blockedToggleButton = new HighlightedRegularToggleButton();
/* 137 */     this.blockedToggleButton.setText(TcgGame.getLocalizedText("friend.mode.blocked", new String[0]));
/* 138 */     placeholder = findComponent((BContainer)this, "button_blocked");
/* 139 */     overridePeelerComponent((BComponent)this.blockedToggleButton, placeholder);
/* 140 */     this.blockedToggleButton.setTooltipText(TcgGame.getLocalizedText("tooltips.friends.blocked", new String[0]));
/*     */     
/* 142 */     this.friendModeToggleButton = new HighlightedToggleButton();
/* 143 */     placeholder = findComponent((BContainer)this, "button_addfriend");
/* 144 */     overridePeelerComponent((BComponent)this.friendModeToggleButton, placeholder);
/* 145 */     this.friendModeToggleButton.setText(this.FRIEND_MODE_OFF);
/*     */     
/* 147 */     this.closeWindowButton = new HighlightedButton();
/* 148 */     placeholder = findComponent((BContainer)this, "button_close");
/* 149 */     overridePeelerComponent((BComponent)this.closeWindowButton, placeholder);
/* 150 */     this.closeWindowButton.setTooltipText(TcgGame.getLocalizedText("tooltips.friends.close", new String[0]));
/*     */     
/* 152 */     this.searchFriendField = new FriendSearchTextField(new NickValidator());
/* 153 */     this.searchFriendField.setMaxLength(14);
/* 154 */     placeholder = findComponent((BContainer)this, "textfield_addfriend");
/* 155 */     overridePeelerComponent((BComponent)this.searchFriendField, placeholder);
/*     */     
/* 157 */     this.searchIconLabel = new BClickthroughLabel("");
/* 158 */     placeholder = findComponent((BContainer)this, "search_icon");
/* 159 */     overridePeelerComponent((BComponent)this.searchIconLabel, placeholder);
/*     */     
/* 161 */     this.pendingRequestsButton = new HighlightedButton();
/* 162 */     placeholder = findComponent((BContainer)this, "button_friendrequest");
/* 163 */     overridePeelerComponent((BComponent)this.pendingRequestsButton, placeholder);
/*     */ 
/*     */     
/* 166 */     this.friendsListContainer = new BContainer((BLayoutManager)new TcgGridLayout(1, 7, 0, 0));
/* 167 */     this.friendPane = new BScrollPaneTcg((BComponent)this.friendsListContainer, false, 40);
/* 168 */     placeholder = findComponent((BContainer)this, "container_friends");
/* 169 */     overridePeelerComponent((BComponent)this.friendPane, placeholder);
/* 170 */     this.friendBar = this.friendPane.getVerticalScrollBar();
/* 171 */     this.friendPane.remove((BComponent)this.friendPane.getVerticalScrollBar());
/* 172 */     add((BComponent)this.friendBar, new Rectangle(233, 51, 32, 280));
/*     */ 
/*     */ 
/*     */     
/* 176 */     this.blockedListContainer = new BContainer((BLayoutManager)new TcgGridLayout(1, 7, 0, 0));
/* 177 */     this.blockedPane = new BScrollPaneTcg((BComponent)this.blockedListContainer, true, 40);
/* 178 */     placeholder = findComponent((BContainer)this, "container_blocked");
/* 179 */     overridePeelerComponent((BComponent)this.blockedPane, placeholder);
/* 180 */     this.blockedBar = this.blockedPane.getVerticalScrollBar();
/* 181 */     this.blockedPane.remove((BComponent)this.blockedPane.getVerticalScrollBar());
/* 182 */     add((BComponent)this.blockedBar, new Rectangle(233, 51, 32, 280));
/*     */ 
/*     */ 
/*     */     
/* 186 */     this.interactionContainer = findContainer("container_confirm");
/*     */     
/* 188 */     this.confirmFriendButton = new HighlightedButton();
/* 189 */     placeholder = findComponent((BContainer)this, "button_msg_confirm");
/* 190 */     overridePeelerComponent((BComponent)this.confirmFriendButton, placeholder);
/* 191 */     this.confirmFriendButton.setTooltipText(TcgGame.getLocalizedText("tooltips.friends.accept", new String[0]));
/*     */     
/* 193 */     this.rejectFriendButton = new HighlightedButton();
/* 194 */     placeholder = findComponent((BContainer)this, "button_msg_cancel");
/* 195 */     overridePeelerComponent((BComponent)this.rejectFriendButton, placeholder);
/* 196 */     this.rejectFriendButton.setTooltipText(TcgGame.getLocalizedText("tooltips.friends.decline", new String[0]));
/*     */     
/* 198 */     this.rejectBounds[0] = this.rejectFriendButton.getBounds();
/* 199 */     this.rejectBounds[1] = new Rectangle((this.rejectBounds[0]).x, (this.rejectBounds[0]).y, (this.rejectBounds[0]).width, (this.rejectBounds[0]).height);
/*     */     
/* 201 */     (this.rejectBounds[1]).x = this.interactionContainer.getWidth() / 2 - (this.rejectBounds[1]).width / 2;
/*     */     
/* 203 */     this.blockPlayerButton = new HighlightedButton(TcgGame.getLocalizedText("friend.msg.button.block", new String[0]));
/* 204 */     placeholder = findComponent((BContainer)this, "button_msg_block");
/* 205 */     overridePeelerComponent((BComponent)this.blockPlayerButton, placeholder);
/*     */     
/* 207 */     this.interactionLabel = new BLabel("");
/* 208 */     placeholder = findComponent((BContainer)this, "text_msg_confirm");
/* 209 */     overridePeelerComponent((BComponent)this.interactionLabel, placeholder);
/*     */     
/* 211 */     this.interactionQueryLabel = new BLabel("");
/* 212 */     placeholder = findComponent((BContainer)this, "text_msg_query");
/* 213 */     overridePeelerComponent((BComponent)this.interactionQueryLabel, placeholder);
/*     */     
/* 215 */     this.friendNameLabel = new BLabel("");
/* 216 */     placeholder = findComponent((BContainer)this, "text_msg_name");
/* 217 */     overridePeelerComponent((BComponent)this.friendNameLabel, placeholder);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 223 */     super.setVisible(visible);
/* 224 */     toggleFriendMode(false);
/* 225 */     this.blockedMode = false;
/*     */     
/* 227 */     this.friendModeActive = false;
/* 228 */     this.friendModeToggleButton.setText(this.FRIEND_MODE_OFF);
/*     */     
/* 230 */     this.searchFriendField.setText(TcgGame.getLocalizedText("friend.msg.searchbar", new String[0]));
/*     */     
/* 232 */     this.interactionContainer.setVisible(false);
/*     */     
/* 234 */     this.blockedPane.setVisible(false);
/* 235 */     this.blockedToggleButton.setSelected(false);
/* 236 */     this.friendBar.setVisible(visible);
/* 237 */     this.blockedBar.setVisible(false);
/*     */     
/* 239 */     this.friendsListContainer.setVisible(true);
/* 240 */     this.blockedListContainer.setVisible(false);
/*     */     
/* 242 */     this.header.setText(TcgGame.getLocalizedText("friend.mode.friend", new String[0]));
/*     */     
/* 244 */     if (visible) {
/* 245 */       MainGameState.getHudModel().friendPendingAction(0);
/*     */     }
/*     */   }
/*     */   
/*     */   private void initListeners() {
/* 250 */     this.friendModel.addChangeListener(new FriendModel.ChangeListener()
/*     */         {
/*     */           public void friendsListChanged() {
/* 253 */             FriendsWindow.this.refreshPlayers();
/*     */           }
/*     */ 
/*     */           
/*     */           public void searchResult(int playerId, String nickname) {
/* 258 */             FriendsWindow.this.type = (playerId > -1) ? FriendsWindow.InteractionType.SEND : FriendsWindow.InteractionType.NOT_FOUND;
/* 259 */             FriendsWindow.this.playerRequestId = playerId;
/* 260 */             FriendsWindow.this.playerRequestNickname = nickname;
/*     */             
/* 262 */             for (FriendsWindow.FriendNameContainer container : FriendsWindow.this.friends) {
/* 263 */               if (container.getPlayerName().equals(nickname)) {
/* 264 */                 FriendsWindow.this.type = FriendsWindow.InteractionType.ALREADY_FRIEND;
/* 265 */                 FriendsWindow.this.openInteraction();
/*     */                 
/*     */                 return;
/*     */               } 
/*     */             } 
/* 270 */             for (FriendsWindow.FriendNameContainer container : FriendsWindow.this.blockedPlayers) {
/* 271 */               if (container.getPlayerName().equals(nickname)) {
/* 272 */                 FriendsWindow.this.removeContainer = container;
/* 273 */                 FriendsWindow.this.type = FriendsWindow.InteractionType.UNBLOCK_ADD;
/* 274 */                 FriendsWindow.this.openInteraction();
/*     */                 
/*     */                 return;
/*     */               } 
/*     */             } 
/* 279 */             for (FriendsWindow.FriendRequest request : FriendsWindow.this.requests) {
/* 280 */               if (nickname.equals(request.getPlayerName())) {
/* 281 */                 FriendsWindow.this.type = FriendsWindow.InteractionType.PENDING_REQUEST;
/* 282 */                 FriendsWindow.this.openInteraction();
/*     */                 
/*     */                 return;
/*     */               } 
/*     */             } 
/*     */             
/* 288 */             FriendsWindow.this.openInteraction();
/*     */           }
/*     */         });
/*     */     
/* 292 */     this.blockedToggleButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 295 */             FriendsWindow.this.blockedMode = FriendsWindow.this.blockedToggleButton.isSelected();
/* 296 */             FriendsWindow.this.toggleBlocked(FriendsWindow.this.blockedToggleButton.isSelected());
/*     */           }
/*     */         });
/*     */     
/* 300 */     this.friendModeToggleButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 303 */             FriendsWindow.this.toggleFriendMode(FriendsWindow.this.friendModeToggleButton.isSelected());
/*     */           }
/*     */         });
/*     */     
/* 307 */     this.closeWindowButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 310 */             FriendsWindow.this.hudModel.friendsButtonAction();
/*     */           }
/*     */         });
/*     */     
/* 314 */     this.searchFriendField.addFocusListener(new FocusListener()
/*     */         {
/*     */           public void focusGained(FocusEvent event) {
/* 317 */             FriendsWindow.this.searchFriendField.setText("");
/*     */           }
/*     */ 
/*     */           
/*     */           public void focusLost(FocusEvent event) {
/* 322 */             if (FriendsWindow.this.searchFriendField.getText().isEmpty()) {
/* 323 */               FriendsWindow.this.searchFriendField.setText(TcgGame.getLocalizedText("friend.msg.searchbar", new String[0]));
/*     */             }
/*     */           }
/*     */         });
/*     */     
/* 328 */     this.searchFriendField.addListener((ComponentListener)new KeyTypedListener()
/*     */         {
/*     */           public void keyPressed(KeyEvent event) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void keyReleased(KeyEvent event) {
/* 336 */             super.keyReleased(event);
/*     */           }
/*     */         });
/*     */     
/* 340 */     this.searchFriendField.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 343 */             FriendsWindow.this.searchFriend();
/*     */           }
/*     */         });
/*     */     
/* 347 */     this.pendingRequestsButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 350 */             if (FriendsWindow.this.requests.size() > 0) {
/* 351 */               FriendsWindow.this.type = FriendsWindow.InteractionType.RECEIVE;
/* 352 */               FriendsWindow.this.pendingRequestsButton.setHighlighted(false);
/* 353 */               FriendsWindow.this.openInteraction();
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 358 */     this.confirmFriendButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 361 */             FriendsWindow.this.interaction(FriendsWindow.MessageAction.CONFIRM);
/*     */           }
/*     */         });
/*     */     
/* 365 */     this.rejectFriendButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 368 */             FriendsWindow.this.interaction(FriendsWindow.MessageAction.CANCEL);
/*     */           }
/*     */         });
/*     */     
/* 372 */     this.blockPlayerButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 375 */             FriendsWindow.this.interaction(FriendsWindow.MessageAction.BLOCK);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void refreshPlayers() {
/* 381 */     this.friendsListContainer.removeAll();
/* 382 */     this.blockedListContainer.removeAll();
/* 383 */     this.friends.clear();
/* 384 */     this.blockedPlayers.clear();
/* 385 */     Map<Integer, Friend> friendsList = this.friendModel.getFriendsList();
/*     */ 
/*     */     
/* 388 */     for (Map.Entry<Integer, Friend> entrySet : friendsList.entrySet()) {
/* 389 */       BananaPeel bananaPeelNames = (BananaPeel)this.resourceManager.getResource(BananaPeel.class, "gui/peeler/window_friends_name.xml", CacheType.NOT_CACHED);
/*     */       
/* 391 */       Friend friend = entrySet.getValue();
/* 392 */       if (friend != null) {
/* 393 */         String name = friend.getNickname();
/* 394 */         name = (name != null) ? name : TcgGame.getLocalizedText("friend.mode.friend", new String[0]).substring(0, TcgGame.getLocalizedText("friend.mode.friend", new String[0]).length() - 2);
/*     */ 
/*     */ 
/*     */         
/* 398 */         FriendNameContainer friendNameContainer = new FriendNameContainer(bananaPeelNames, ((Integer)entrySet.getKey()).intValue(), name, friend.isBlocked().booleanValue(), friend.isOnline().booleanValue());
/*     */         
/* 400 */         if (!friend.isBlocked().booleanValue()) {
/* 401 */           this.friends.add(friendNameContainer);
/* 402 */           this.friendsListContainer.add((BComponent)friendNameContainer.getMainContainer());
/*     */           continue;
/*     */         } 
/* 405 */         this.blockedPlayers.add(friendNameContainer);
/* 406 */         this.blockedListContainer.add((BComponent)friendNameContainer.getMainContainer());
/*     */         continue;
/*     */       } 
/* 409 */       System.out.println("WTF?!");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void toggleFriendMode(boolean selected) {
/* 416 */     TcgGame.setAddFriendMode(selected ? (this.blockedMode ? FriendModeType.BLOCKED : FriendModeType.FRIEND) : FriendModeType.OFF);
/* 417 */     this.friendModeToggleButton.setSelected(selected);
/* 418 */     this.friendModeToggleButton.setText(selected ? (this.blockedMode ? this.BLOCKED_MODE_ON : this.FRIEND_MODE_ON) : (this.blockedMode ? this.BLOCKED_MODE_OFF : this.FRIEND_MODE_OFF));
/*     */ 
/*     */ 
/*     */     
/* 422 */     this.searchFriendField.setText(TcgGame.getLocalizedText("friend.msg.searchbar", new String[0]));
/* 423 */     this.blockedToggleButton.setEnabled(!selected);
/*     */   }
/*     */   
/*     */   private void toggleBlocked(boolean selected) {
/* 427 */     this.friendModeToggleButton.setText(selected ? this.BLOCKED_MODE_OFF : this.FRIEND_MODE_OFF);
/* 428 */     this.friendModeToggleButton.setSelected(false);
/*     */     
/* 430 */     this.blockedPane.setVisible(selected);
/* 431 */     this.header.setText(selected ? TcgGame.getLocalizedText("friend.mode.blocked", new String[0]) : TcgGame.getLocalizedText("friend.mode.friend", new String[0]));
/*     */     
/* 433 */     this.blockedToggleButton.setText(!selected ? TcgGame.getLocalizedText("friend.mode.blocked", new String[0]) : TcgGame.getLocalizedText("friend.mode.friend", new String[0]));
/*     */     
/* 435 */     this.blockedToggleButton.setTooltipText(!selected ? TcgGame.getLocalizedText("tooltips.friends.blocked", new String[0]) : TcgGame.getLocalizedText("tooltips.friends", new String[0]));
/*     */     
/* 437 */     this.blockedToggleButton.setColor(0, selected ? ColorRGBA.white : ColorRGBA.red);
/* 438 */     this.blockedToggleButton.setColor(1, selected ? ColorRGBA.white : ColorRGBA.red);
/* 439 */     this.blockedToggleButton.setColor(2, selected ? ColorRGBA.white : ColorRGBA.red);
/* 440 */     this.blockedToggleButton.setColor(4, selected ? ColorRGBA.white : ColorRGBA.red);
/* 441 */     this.blockedToggleButton.setColor(5, selected ? ColorRGBA.white : ColorRGBA.red);
/* 442 */     this.blockedToggleButton.setColor(6, selected ? ColorRGBA.white : ColorRGBA.red);
/* 443 */     this.blockedToggleButton.setSelected(selected);
/* 444 */     this.friendBar.setVisible(!selected);
/* 445 */     this.blockedBar.setVisible(selected);
/*     */     
/* 447 */     this.friendsListContainer.setVisible(!selected);
/* 448 */     this.blockedListContainer.setVisible(selected);
/*     */   }
/*     */   
/*     */   public MessagedBTextField getSearchFriendField() {
/* 452 */     return this.searchFriendField;
/*     */   }
/*     */ 
/*     */   
/*     */   private void searchFriend() {
/* 457 */     String name = this.searchFriendField.getText();
/* 458 */     if (name.isEmpty())
/*     */       return; 
/* 460 */     if (MainGameState.getPlayerModel().getName().equals(name)) {
/* 461 */       this.type = InteractionType.ADD_SELF;
/* 462 */       openInteraction();
/*     */       
/*     */       return;
/*     */     } 
/* 466 */     if (name.equals(TcgGame.getLocalizedText("friend.msg.searchbar", new String[0]))) {
/*     */       return;
/*     */     }
/*     */     
/* 470 */     boolean result = this.friendModel.searchPlayer(name);
/*     */ 
/*     */     
/* 473 */     if (!result) {
/* 474 */       System.out.println("ERROR");
/*     */     }
/*     */   }
/*     */   
/*     */   private void blockPlayer(FriendRequest player) {
/* 479 */     this.friendModel.addIdToFriendList(player.getPlayerId(), true);
/*     */   }
/*     */   
/*     */   private void blockPlayer(int playerId) {
/* 483 */     this.friendModel.addIdToFriendList(playerId, true);
/*     */   }
/*     */   
/*     */   public void addFriendRequest(int playerId, String playerName) {
/* 487 */     FriendRequest friendRequest = new FriendRequest(playerId, playerName);
/*     */ 
/*     */     
/* 490 */     for (FriendRequest request : this.requests) {
/* 491 */       if (request.getPlayerName().equals(friendRequest.getPlayerName())) {
/*     */         return;
/*     */       }
/*     */     } 
/* 495 */     for (FriendNameContainer player : this.blockedPlayers) {
/* 496 */       if (player.getPlayerName().equals(friendRequest.getPlayerName())) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 501 */     this.requests.add(friendRequest);
/* 502 */     this.pendingRequestsButton.setHighlighted(true);
/* 503 */     this.pendingRequestsButton.setText("" + this.requests.size());
/* 504 */     if (!isVisible()) {
/* 505 */       MainGameState.getHudModel().friendPendingAction(this.requests.size());
/*     */     }
/*     */   }
/*     */   
/*     */   private void openInteraction() {
/* 510 */     this.friendBar.setVisible(false);
/* 511 */     this.blockedBar.setVisible(false);
/*     */     
/* 513 */     this.friendsListContainer.setVisible(false);
/* 514 */     this.blockedListContainer.setVisible(false);
/*     */     
/* 516 */     this.interactionContainer.setVisible(true);
/*     */     
/* 518 */     this.blockPlayerButton.setVisible((this.type == InteractionType.RECEIVE));
/* 519 */     this.confirmFriendButton.setVisible((this.type == InteractionType.SEND || this.type == InteractionType.RECEIVE || this.type == InteractionType.REMOVE || this.type == InteractionType.UNBLOCK || (this.type == InteractionType.UNBLOCK_ADD && !this.blockedMode)));
/*     */ 
/*     */ 
/*     */     
/* 523 */     if (this.confirmFriendButton.isVisible()) {
/* 524 */       this.rejectFriendButton.setLocation((this.rejectBounds[0]).x, (this.rejectBounds[0]).y);
/*     */     } else {
/* 526 */       this.rejectFriendButton.setLocation((this.rejectBounds[1]).x, (this.rejectBounds[1]).y);
/*     */     } 
/*     */     
/* 529 */     this.friendNameLabel.setText(this.playerRequestNickname);
/*     */     
/* 531 */     switch (this.type) {
/*     */       case SEND:
/* 533 */         this.interactionLabel.setText(this.SEND_MSG);
/* 534 */         this.interactionQueryLabel.setText(this.blockedMode ? this.SEND_QUERY_MSG_BLOCKED : this.SEND_QUERY_MSG);
/*     */         break;
/*     */       case RECEIVE:
/* 537 */         this.interactionLabel.setText(this.RECEIVE_MSG);
/* 538 */         this.interactionQueryLabel.setText(this.RECEIVE_QUERY_MSG);
/* 539 */         if (this.requests.size() > 0) {
/* 540 */           this.friendNameLabel.setText(((FriendRequest)this.requests.get(0)).getPlayerName());
/*     */         }
/*     */         break;
/*     */       case NOT_FOUND:
/* 544 */         this.interactionLabel.setText(this.NOT_FOUND_MSG);
/* 545 */         this.interactionQueryLabel.setText(this.TRY_AGAIN_QUERY_MSG);
/*     */         break;
/*     */       case ALREADY_FRIEND:
/* 548 */         this.interactionLabel.setText(this.ALREADY_MSG);
/* 549 */         this.interactionQueryLabel.setText(this.TRY_AGAIN_QUERY_MSG);
/*     */         break;
/*     */       case ADD_SELF:
/* 552 */         this.interactionLabel.setText(this.ADD_SELF_MSG);
/* 553 */         this.friendNameLabel.setText("");
/* 554 */         this.interactionQueryLabel.setText(this.TRY_AGAIN_QUERY_MSG);
/*     */         break;
/*     */       case PENDING_REQUEST:
/* 557 */         this.interactionLabel.setText(this.PENDING_MSG);
/* 558 */         this.interactionQueryLabel.setText(this.PENDING_QUERY_MSG);
/*     */         break;
/*     */       case REMOVE:
/* 561 */         this.interactionLabel.setText(this.REMOVE_MSG);
/* 562 */         this.friendNameLabel.setText(this.removeContainer.getPlayerName());
/* 563 */         this.interactionQueryLabel.setText(this.SURE_QUERY_MSG);
/*     */         break;
/*     */       case UNBLOCK:
/* 566 */         this.interactionLabel.setText(this.UNBLOCK_MSG);
/* 567 */         this.friendNameLabel.setText(this.removeContainer.getPlayerName());
/* 568 */         this.interactionQueryLabel.setText(this.SURE_QUERY_MSG);
/*     */         break;
/*     */       case UNBLOCK_ADD:
/* 571 */         this.interactionLabel.setText(this.blockedMode ? this.ALREADY_BLOCKED : this.UNBLOCK_ADD_MSG);
/* 572 */         this.friendNameLabel.setText(this.removeContainer.getPlayerName());
/* 573 */         this.interactionQueryLabel.setText(this.TRY_AGAIN_QUERY_MSG);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void interaction(MessageAction action) {
/* 581 */     this.interactionContainer.setVisible(false);
/*     */     
/* 583 */     FriendRequest friendRequest = null;
/* 584 */     if (this.type.equals(InteractionType.RECEIVE) && this.requests.size() > 0) {
/* 585 */       friendRequest = this.requests.get(0);
/* 586 */       this.requests.remove(0);
/* 587 */       this.pendingRequestsButton.setText((this.requests.size() > 0) ? ("" + this.requests.size()) : "");
/*     */     } 
/*     */ 
/*     */     
/* 591 */     if (!action.equals(MessageAction.CONFIRM)) {
/* 592 */       this.searchFriendField.setText(TcgGame.getLocalizedText("friend.msg.searchbar", new String[0]));
/* 593 */       if (this.type.equals(InteractionType.RECEIVE) && action.equals(MessageAction.BLOCK)) {
/* 594 */         blockPlayer(friendRequest);
/*     */       }
/* 596 */       if (TcgGame.isAddFriendMode()) {
/* 597 */         toggleFriendMode(false);
/*     */       }
/* 599 */       if (this.blockedMode) { this.blockedBar.setVisible(true); }
/* 600 */       else { this.friendBar.setVisible(true); }
/*     */       
/*     */       return;
/*     */     } 
/* 604 */     switch (this.type) {
/*     */       case SEND:
/* 606 */         if (!this.blockedMode) {
/* 607 */           this.friendModel.sendFriendRequestToPlayer(Integer.valueOf(this.playerRequestId)); break;
/*     */         } 
/* 609 */         blockPlayer(this.playerRequestId);
/*     */         break;
/*     */       
/*     */       case RECEIVE:
/* 613 */         this.playerRequestId = (friendRequest != null) ? friendRequest.getPlayerId() : -1;
/* 614 */         if (this.playerRequestId == -1)
/* 615 */           return;  this.friendModel.getChatMessageListener().acceptFriendResponse(this.playerRequestId);
/*     */         break;
/*     */       case REMOVE:
/* 618 */         this.friendModel.removeFriend(this.removeContainer.getPlayerId());
/*     */         break;
/*     */       case UNBLOCK_ADD:
/* 621 */         this.friendModel.sendFriendRequestToPlayer(Integer.valueOf(this.playerRequestId));
/*     */       case UNBLOCK:
/* 623 */         this.friendModel.removeFriend(this.removeContainer.getPlayerId());
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 633 */     if (TcgGame.isAddFriendMode()) {
/* 634 */       toggleFriendMode(false);
/*     */     }
/* 636 */     if (this.blockedMode) { this.blockedBar.setVisible(true); }
/* 637 */     else { this.friendBar.setVisible(true); }
/*     */   
/*     */   }
/*     */   
/*     */   class NickValidator
/*     */     implements MessagedBTextField.CharValidator
/*     */   {
/*     */     public boolean isValid(char c) {
/* 645 */       return StringUtils.isCharacterNameChar(c);
/*     */     }
/*     */   }
/*     */   
/*     */   enum InteractionType {
/* 650 */     SEND,
/* 651 */     RECEIVE,
/* 652 */     NOT_FOUND,
/* 653 */     ALREADY_FRIEND,
/* 654 */     ADD_SELF,
/* 655 */     PENDING_REQUEST,
/* 656 */     REMOVE,
/* 657 */     UNBLOCK,
/* 658 */     UNBLOCK_ADD;
/*     */   }
/*     */   
/*     */   enum MessageAction {
/* 662 */     CONFIRM,
/* 663 */     CANCEL,
/* 664 */     BLOCK;
/*     */   }
/*     */ 
/*     */   
/*     */   private class FriendNameContainer
/*     */     extends BPeelContainer
/*     */   {
/*     */     private int playerId;
/*     */     
/*     */     private String playerName;
/*     */     private boolean online;
/*     */     private BLabel friendNameLabel;
/*     */     private HighlightedButton chatButton;
/*     */     private HighlightedButton removeFriendButton;
/*     */     private boolean blocked;
/*     */     private FriendNameContainer INSTANCE;
/*     */     
/*     */     public FriendNameContainer(BananaPeel bananaPeel, int playerId, String playerName, boolean blocked, boolean online) {
/* 682 */       super("", bananaPeel);
/* 683 */       this.INSTANCE = this;
/* 684 */       this.playerId = playerId;
/* 685 */       this.playerName = playerName;
/* 686 */       this.blocked = blocked;
/* 687 */       this.online = online;
/* 688 */       initComponents();
/* 689 */       initListeners();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void initComponents() {
/* 695 */       this.friendNameLabel = new BLabel(this.playerName);
/* 696 */       BComponent placeholder = findComponent(getMainContainer(), "label_friend_name");
/* 697 */       overridePeelerComponent((BComponent)this.friendNameLabel, placeholder);
/*     */       
/* 699 */       if (!this.online) {
/* 700 */         this.friendNameLabel.setAlpha(0.5F);
/*     */       }
/*     */       
/* 703 */       this.chatButton = new HighlightedButton();
/* 704 */       placeholder = findComponent(getMainContainer(), "button_chat");
/* 705 */       overridePeelerComponent((BComponent)this.chatButton, placeholder);
/* 706 */       this.chatButton.setTooltipText(TcgGame.getLocalizedText("tooltips.chat.tell", new String[0]));
/*     */       
/* 708 */       if (this.blocked || !TcgGame.isAllowFreeChat()) {
/* 709 */         this.chatButton.getParent().remove((BComponent)this.chatButton);
/*     */       }
/*     */       
/* 712 */       this.removeFriendButton = new HighlightedButton();
/* 713 */       placeholder = findComponent(getMainContainer(), "button_remove");
/* 714 */       overridePeelerComponent((BComponent)this.removeFriendButton, placeholder);
/* 715 */       this.removeFriendButton.setTooltipText(TcgGame.getLocalizedText("tooltips.friends." + (this.blocked ? "unblock" : "remove"), new String[0]));
/*     */     }
/*     */     
/*     */     public int getPlayerId() {
/* 719 */       return this.playerId;
/*     */     }
/*     */     
/*     */     public String getPlayerName() {
/* 723 */       return this.playerName;
/*     */     }
/*     */     
/*     */     public boolean isBlocked() {
/* 727 */       return this.blocked;
/*     */     }
/*     */     
/*     */     public boolean isOnline() {
/* 731 */       return this.online;
/*     */     }
/*     */     
/*     */     private void initListeners() {
/* 735 */       this.removeFriendButton.addListener((ComponentListener)new ActionListener()
/*     */           {
/*     */             public void actionPerformed(ActionEvent event)
/*     */             {
/* 739 */               FriendsWindow.this.type = FriendsWindow.FriendNameContainer.this.blocked ? FriendsWindow.InteractionType.UNBLOCK : FriendsWindow.InteractionType.REMOVE;
/* 740 */               FriendsWindow.this.removeContainer = FriendsWindow.FriendNameContainer.this.INSTANCE;
/* 741 */               FriendsWindow.this.openInteraction();
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 746 */       this.chatButton.addListener((ComponentListener)new ActionListener()
/*     */           {
/*     */             public void actionPerformed(ActionEvent event)
/*     */             {
/* 750 */               BWindow window = TcgUI.getWindowFromClass(GameWindows.CHAT.getWindowClass());
/* 751 */               if (MainGameState.getBasicChatWindow() != null && window == null) {
/* 752 */                 MainGameState.getHudModel().chatButtonAction();
/*     */               }
/* 754 */               MainGameState.getBasicChatWindow().tellMode(FriendsWindow.FriendNameContainer.this.playerName, true);
/*     */               
/* 756 */               window = TcgUI.getWindowFromClass(FriendsWindow.class);
/* 757 */               if (window != null) {
/* 758 */                 MainGameState.getGuiWindowsController().toggleWindow(GameWindows.FRIENDS);
/*     */               }
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     public HighlightedButton getChatButton() {
/* 766 */       return this.chatButton;
/*     */     }
/*     */     
/*     */     public BLabel getFriendNameLabel() {
/* 770 */       return this.friendNameLabel;
/*     */     }
/*     */     
/*     */     public HighlightedButton getRemoveFriendButton() {
/* 774 */       return this.removeFriendButton;
/*     */     }
/*     */   }
/*     */   
/*     */   private class FriendRequest
/*     */   {
/*     */     private int playerId;
/*     */     private String playerName;
/*     */     
/*     */     FriendRequest(int playerId, String playerName) {
/* 784 */       this.playerId = playerId;
/* 785 */       this.playerName = playerName;
/*     */     }
/*     */     
/*     */     public int getPlayerId() {
/* 789 */       return this.playerId;
/*     */     }
/*     */     
/*     */     public String getPlayerName() {
/* 793 */       return this.playerName;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\friend\FriendsWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */