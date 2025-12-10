/*     */ package com.funcom.tcg.client.ui.vendor;
/*     */ 
/*     */ import com.funcom.commons.jme.bui.HighlightedButton;
/*     */ import com.funcom.commons.jme.bui.IrregularButton;
/*     */ import com.funcom.commons.localization.JavaLocalization;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.model.props.UpdateListener;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.peeler.BananaPeel;
/*     */ import com.funcom.rpgengine2.equipment.ArchType;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.items.ItemType;
/*     */ import com.funcom.rpgengine2.speach.SpeachContext;
/*     */ import com.funcom.rpgengine2.speach.SpeachMapping;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.BPeelWindow;
/*     */ import com.funcom.tcg.client.ui.BScrollPaneTcg;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.client.ui.ClientStatCalc;
/*     */ import com.funcom.tcg.client.ui.CloseWindowListener;
/*     */ import com.funcom.tcg.client.ui.GuiStatType;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*     */ import com.funcom.tcg.client.ui.hud2.AbstractHudModel;
/*     */ import com.funcom.tcg.client.ui.hud2.HudModel;
/*     */ import com.funcom.tcg.client.ui.inventory.Inventory;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BClickthroughLabel;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BProgressBar;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Insets;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.awt.Point;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VendorFullWindow
/*     */   extends BPeelWindow
/*     */   implements Inventory.ChangeListener
/*     */ {
/*     */   private ResourceManager resourceManager;
/*     */   private VendorFullWindow INSTANCE;
/*     */   private CloseWindowListener closeListener;
/*     */   private BLabel myCoinsTotalLabel;
/*     */   private BLabel buyCoinsTotalLabel;
/*     */   private BLabel sellCoinsTotalLabel;
/*     */   private BLabel myTokensTotalLabel;
/*     */   private BLabel buyTokensTotalLabel;
/*     */   private BLabel vendorTitleLabel;
/*     */   private BScrollPaneTcg buyContentsScrollPane;
/*     */   private BScrollPaneTcg sellContentsScrollPane;
/*     */   private BContainer buyItemContainer;
/*     */   private BContainer sellItemContainer;
/*     */   private BContainer statsContainer;
/*     */   private BContainer buyStatsContainer;
/*     */   private BLabel memberOnlyLabel;
/*     */   private BLabel itemIconLabel;
/*     */   private BLabel itemNameLabel;
/*     */   private BLabel itemBuyNameLabel;
/*  80 */   private List<SellInventoryItem> sellItemList = new ArrayList<SellInventoryItem>(); private BLabel itemBuyDescription; private BLabel itemValueLabel; private BLabel itemBuyValueLabel; private BProgressBar[] itemProgresses; private BLabel[] itemProgressGloss; private BContainer confirmContainer; private BContainer infoContainer; private BButton confirmButton; private BButton cancelButton; private BLabel priceConfirmLabel; private BLabel messageConfirmLabel; private BLabel previewLevel; private BLabel previewBuyLevel; private BButton sellButton; private BButton buyButton; private BButton undoSellButton; private BButton undoBuyButton; private VendorModel model; private VendorItemList activeList; private VendorChangeListener changeListener;
/*  81 */   private List<VendorItemButton> buyItemList = new ArrayList<VendorItemButton>();
/*  82 */   private int sellAmountCoins = 0;
/*     */   private int buyAmountCoins;
/*     */   private int buyAmountTokens;
/*     */   private ClientItem hoverSellItem;
/*     */   private VendorModelItem hoverBuyItem;
/*     */   private boolean added = false;
/*     */   private BImage icon;
/*     */   private boolean transactionBuy = false;
/*     */   private BClickthroughLabel buyButtonArrow;
/*     */   private BClickthroughLabel sellButtonArrow;
/*     */   
/*     */   public VendorFullWindow(String windowName, BananaPeel bananaPeel, ResourceManager resourceManager, VendorModel model) {
/*  94 */     super(windowName, bananaPeel);
/*  95 */     this.INSTANCE = this;
/*  96 */     this.resourceManager = resourceManager;
/*     */     
/*  98 */     BananaResourceProvider buiResourceProvider = new BananaResourceProvider(resourceManager);
/*  99 */     this._style = BuiUtils.createStyleSheet("/peeler/vendor_window.bss", buiResourceProvider);
/*     */ 
/*     */     
/* 102 */     initComponents(model);
/* 103 */     initListeners();
/*     */     
/* 105 */     MainGameState.getPlayerModel().getInventory().addChangeListener(this);
/*     */     
/* 107 */     setModel(model);
/*     */   }
/*     */   
/*     */   private void updateActiveList() {
/* 111 */     this.activeList.updateAvailableItems();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initComponents(VendorModel model) {
/* 118 */     this.myCoinsTotalLabel = new BLabel("0");
/* 119 */     BComponent placeholder = findComponent((BContainer)this, "my_coins_total");
/* 120 */     overridePeelerComponent((BComponent)this.myCoinsTotalLabel, placeholder);
/*     */     
/* 122 */     this.buyCoinsTotalLabel = new BLabel("0");
/* 123 */     placeholder = findComponent((BContainer)this, "buy_coins_total");
/* 124 */     overridePeelerComponent((BComponent)this.buyCoinsTotalLabel, placeholder);
/*     */     
/* 126 */     this.sellCoinsTotalLabel = new BLabel("0");
/* 127 */     placeholder = findComponent((BContainer)this, "sell_coins_total");
/* 128 */     overridePeelerComponent((BComponent)this.sellCoinsTotalLabel, placeholder);
/*     */     
/* 130 */     this.myTokensTotalLabel = new BLabel("0");
/* 131 */     placeholder = findComponent((BContainer)this, "pettoken_total");
/* 132 */     overridePeelerComponent((BComponent)this.myTokensTotalLabel, placeholder);
/*     */     
/* 134 */     this.buyTokensTotalLabel = new BLabel("0");
/* 135 */     placeholder = findComponent((BContainer)this, "buy_tokens_total");
/* 136 */     overridePeelerComponent((BComponent)this.buyTokensTotalLabel, placeholder);
/*     */ 
/*     */     
/* 139 */     BContainer buyContainer = new BContainer();
/* 140 */     BContainer bContainer5 = findContainer("buy_container");
/* 141 */     overridePeelerComponent((BComponent)buyContainer, (BComponent)bContainer5);
/*     */     
/* 143 */     BClickthroughLabel buyBorder = new BClickthroughLabel("");
/* 144 */     BComponent bComponent5 = findComponent((BContainer)this, "buy_border");
/* 145 */     overridePeelerComponent((BComponent)buyBorder, bComponent5);
/*     */     
/* 147 */     this.buyItemContainer = new BContainer((BLayoutManager)new TcgGridLayout(4, 7, 0, 0));
/* 148 */     this.buyItemContainer.setStyleClass("vendorwindow.itemlist");
/*     */     
/* 150 */     this.activeList = new VendorItemListBuy(this.buyItemContainer, model);
/*     */     
/* 152 */     this.buyContentsScrollPane = new BScrollPaneTcg((BComponent)this.buyItemContainer, true, 72);
/* 153 */     bComponent5 = findComponent((BContainer)this, "buy_container_list");
/* 154 */     overridePeelerComponent((BComponent)this.buyContentsScrollPane, bComponent5);
/*     */     
/* 156 */     this.vendorTitleLabel = new BLabel(model.getName());
/* 157 */     bComponent5 = findComponent((BContainer)this, "buy_vendor_title");
/* 158 */     overridePeelerComponent((BComponent)this.vendorTitleLabel, bComponent5);
/*     */     
/* 160 */     this.undoBuyButton = new BButton("");
/* 161 */     bComponent5 = findComponent((BContainer)this, "undo_buy");
/* 162 */     overridePeelerComponent((BComponent)this.undoBuyButton, bComponent5);
/*     */ 
/*     */     
/* 165 */     BContainer sellContainer = new BContainer();
/* 166 */     BContainer bContainer4 = findContainer("sell_container");
/* 167 */     overridePeelerComponent((BComponent)sellContainer, (BComponent)bContainer4);
/*     */     
/* 169 */     BClickthroughLabel sellBorder = new BClickthroughLabel("");
/* 170 */     BComponent bComponent4 = findComponent((BContainer)this, "sell_border");
/* 171 */     overridePeelerComponent((BComponent)sellBorder, bComponent4);
/*     */     
/* 173 */     BLabel inventoryTitleLabel = new BLabel(TcgGame.getLocalizedText("vendorwindow.sell.title", new String[0]));
/* 174 */     bComponent4 = findComponent((BContainer)this, "sell_vendor_title");
/* 175 */     overridePeelerComponent((BComponent)inventoryTitleLabel, bComponent4);
/*     */     
/* 177 */     this.sellItemContainer = new BContainer((BLayoutManager)new TcgGridLayout(4, 7, 0, 0));
/* 178 */     this.sellItemContainer.setStyleClass("sell_container_item");
/*     */     
/* 180 */     this.sellContentsScrollPane = new BScrollPaneTcg((BComponent)this.sellItemContainer, true, 72);
/* 181 */     bComponent4 = findComponent((BContainer)this, "sell_container_list");
/* 182 */     overridePeelerComponent((BComponent)this.sellContentsScrollPane, bComponent4);
/*     */     
/* 184 */     this.undoSellButton = new BButton("");
/* 185 */     bComponent4 = findComponent((BContainer)this, "undo_sell");
/* 186 */     overridePeelerComponent((BComponent)this.undoSellButton, bComponent4);
/*     */ 
/*     */     
/* 189 */     this.buyStatsContainer = new BContainer();
/* 190 */     BContainer bContainer3 = findContainer("buy_stats_container");
/* 191 */     overridePeelerComponent((BComponent)this.buyStatsContainer, (BComponent)bContainer3);
/*     */     
/* 193 */     BLabel itemBuyIconLabel = new BLabel("");
/* 194 */     BComponent bComponent3 = findComponent((BContainer)this, "preview_buy_icon");
/* 195 */     overridePeelerComponent((BComponent)itemBuyIconLabel, bComponent3);
/*     */     
/* 197 */     this.itemBuyNameLabel = new BLabel("");
/* 198 */     bComponent3 = findComponent((BContainer)this, "preview_buy_name");
/* 199 */     overridePeelerComponent((BComponent)this.itemBuyNameLabel, bComponent3);
/*     */     
/* 201 */     this.itemBuyValueLabel = new BLabel("0");
/* 202 */     bComponent3 = findComponent((BContainer)this, "preview_buy_coins");
/* 203 */     overridePeelerComponent((BComponent)this.itemBuyValueLabel, bComponent3);
/*     */     
/* 205 */     this.previewBuyLevel = new BLabel("");
/* 206 */     bComponent3 = findComponent((BContainer)this, "preview_buy_level");
/* 207 */     overridePeelerComponent((BComponent)this.previewBuyLevel, bComponent3);
/*     */     
/* 209 */     this.itemBuyDescription = new BLabel("");
/* 210 */     bComponent3 = findComponent((BContainer)this, "preview_buy_description");
/* 211 */     overridePeelerComponent((BComponent)this.itemBuyDescription, bComponent3);
/*     */ 
/*     */     
/* 214 */     this.statsContainer = new BContainer();
/* 215 */     BContainer bContainer2 = findContainer("stats_container");
/* 216 */     overridePeelerComponent((BComponent)this.statsContainer, (BComponent)bContainer2);
/*     */     
/* 218 */     this.itemIconLabel = new BLabel("");
/* 219 */     BComponent bComponent2 = findComponent((BContainer)this, "preview_icon");
/* 220 */     overridePeelerComponent((BComponent)this.itemIconLabel, bComponent2);
/*     */     
/* 222 */     this.previewLevel = new BLabel("");
/* 223 */     bComponent2 = findComponent((BContainer)this, "preview_level");
/* 224 */     overridePeelerComponent((BComponent)this.previewLevel, bComponent2);
/*     */ 
/*     */     
/* 227 */     this.memberOnlyLabel = new BLabel("");
/* 228 */     bComponent2 = findComponent((BContainer)this, "members_only");
/* 229 */     overridePeelerComponent((BComponent)this.memberOnlyLabel, bComponent2);
/* 230 */     this.memberOnlyLabel.setVisible(false);
/* 231 */     this.memberOnlyLabel.setStyleClass("members_only." + ((System.getProperty("tcg.locale") != null) ? System.getProperty("tcg.locale") : "en"));
/*     */     
/* 233 */     this.itemNameLabel = new BLabel("ITEM");
/* 234 */     bComponent2 = findComponent((BContainer)this, "preview_name");
/* 235 */     overridePeelerComponent((BComponent)this.itemNameLabel, bComponent2);
/*     */     
/* 237 */     this.itemValueLabel = new BLabel("0");
/* 238 */     bComponent2 = findComponent((BContainer)this, "preview_coins");
/* 239 */     overridePeelerComponent((BComponent)this.itemValueLabel, bComponent2);
/*     */     
/* 241 */     this.itemProgresses = new BProgressBar[(GuiStatType.values()).length];
/* 242 */     this.itemProgressGloss = new BLabel[(GuiStatType.values()).length];
/* 243 */     for (int i = 0; i < (GuiStatType.values()).length; i++) {
/* 244 */       String guiStatString = GuiStatType.values()[i].toString().toLowerCase();
/*     */       
/* 246 */       this.itemProgresses[i] = new BProgressBar();
/* 247 */       bComponent2 = findComponent((BContainer)this, "preview_" + guiStatString + "_bar");
/* 248 */       overridePeelerComponent((BComponent)this.itemProgresses[i], bComponent2);
/*     */       
/* 250 */       this.itemProgressGloss[i] = new BLabel("");
/* 251 */       bComponent2 = findComponent((BContainer)this, "preview_" + guiStatString + "_gloss");
/* 252 */       overridePeelerComponent((BComponent)this.itemProgressGloss[i], bComponent2);
/*     */       
/* 254 */       bComponent2 = findComponent((BContainer)this, "preview_" + guiStatString);
/* 255 */       bComponent2.setTooltipText(TcgGame.getLocalizedText("charwindow.info." + guiStatString, new String[0]));
/*     */     } 
/*     */     
/* 258 */     this.sellButton = new BButton(TcgGame.getLocalizedText("vendorwindow.sell.button", new String[0]));
/* 259 */     bComponent2 = findComponent((BContainer)this, "sell_button");
/* 260 */     overridePeelerComponent((BComponent)this.sellButton, bComponent2);
/*     */     
/* 262 */     this.sellButtonArrow = new BClickthroughLabel("");
/* 263 */     bComponent2 = findComponent((BContainer)this, "sell_button_arrow");
/* 264 */     overridePeelerComponent((BComponent)this.sellButtonArrow, bComponent2);
/*     */     
/* 266 */     this.buyButton = new BButton(TcgGame.getLocalizedText("vendorwindow.buy.button", new String[0]));
/* 267 */     bComponent2 = findComponent((BContainer)this, "buy_button");
/* 268 */     overridePeelerComponent((BComponent)this.buyButton, bComponent2);
/*     */     
/* 270 */     this.buyButtonArrow = new BClickthroughLabel("");
/* 271 */     bComponent2 = findComponent((BContainer)this, "buy_button_arrow");
/* 272 */     overridePeelerComponent((BComponent)this.buyButtonArrow, bComponent2);
/*     */     
/* 274 */     this.infoContainer = new BContainer();
/* 275 */     BContainer bContainer1 = findContainer("info_container");
/* 276 */     overridePeelerComponent((BComponent)this.infoContainer, (BComponent)bContainer1);
/*     */     
/* 278 */     this.confirmContainer = new BContainer();
/* 279 */     bContainer1 = findContainer("container_confirm");
/* 280 */     overridePeelerComponent((BComponent)this.confirmContainer, (BComponent)bContainer1);
/*     */     
/* 282 */     this.confirmButton = new BButton("");
/* 283 */     BComponent bComponent1 = findComponent((BContainer)this, "button_msg_confirm");
/* 284 */     overridePeelerComponent((BComponent)this.confirmButton, bComponent1);
/*     */     
/* 286 */     this.cancelButton = new BButton("");
/* 287 */     bComponent1 = findComponent((BContainer)this, "button_msg_cancel");
/* 288 */     overridePeelerComponent((BComponent)this.cancelButton, bComponent1);
/*     */     
/* 290 */     this.messageConfirmLabel = (BLabel)new BButton(TcgGame.getLocalizedText("vendorwindow.sell.confirm", new String[0]));
/* 291 */     bComponent1 = findComponent((BContainer)this, "text_msg_confirm");
/* 292 */     overridePeelerComponent((BComponent)this.messageConfirmLabel, bComponent1);
/*     */     
/* 294 */     this.priceConfirmLabel = (BLabel)new BButton("");
/* 295 */     bComponent1 = findComponent((BContainer)this, "text_msg_name");
/* 296 */     overridePeelerComponent((BComponent)this.priceConfirmLabel, bComponent1);
/*     */     
/* 298 */     BLabel queryLabel = new BLabel(TcgGame.getLocalizedText("vendorwindow.sell.query", new String[0]));
/* 299 */     bComponent1 = findComponent((BContainer)this, "text_msg_query");
/* 300 */     overridePeelerComponent((BComponent)queryLabel, bComponent1);
/*     */     
/* 302 */     resetConfirmContainer();
/* 303 */     resetStatsContainer();
/* 304 */     resetBuyStatsContainer();
/*     */     
/* 306 */     this.buyCoinsTotalLabel.setVisible(false);
/* 307 */     this.sellCoinsTotalLabel.setVisible(false);
/* 308 */     this.buyTokensTotalLabel.setVisible(false);
/*     */     
/* 310 */     toggleActionButtons(false, false);
/* 311 */     toggleActionButtons(true, false);
/*     */     
/* 313 */     addDefaultCloseButton();
/*     */   }
/*     */ 
/*     */   
/*     */   private void toggleActionButtons(boolean buy, boolean enabled) {
/* 318 */     if (buy) {
/* 319 */       this.buyButton.setEnabled(enabled);
/* 320 */       this.buyButton.setAlpha(enabled ? 1.0F : 0.5F);
/* 321 */       this.buyButtonArrow.setAlpha(enabled ? 1.0F : 0.85F);
/*     */     } else {
/* 323 */       this.sellButton.setEnabled(enabled);
/* 324 */       this.sellButton.setAlpha(enabled ? 1.0F : 0.5F);
/* 325 */       this.sellButtonArrow.setAlpha(enabled ? 1.0F : 0.85F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addDefaultCloseButton() {
/* 331 */     HighlightedButton highlightedButton = new HighlightedButton();
/* 332 */     highlightedButton.setTooltipText(TcgGame.getLocalizedText("tooltips.friends.close", new String[0]));
/* 333 */     int closeButtonSize = 24;
/* 334 */     add((BComponent)highlightedButton, new Rectangle(1024 - closeButtonSize - 5, getHeight() - closeButtonSize - 5, closeButtonSize, closeButtonSize));
/*     */ 
/*     */     
/* 337 */     highlightedButton.setStyleClass("close-button");
/* 338 */     highlightedButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 341 */             VendorFullWindow.this.close();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void addInfoButton() {
/* 348 */     IrregularButton irregularButton = new IrregularButton("");
/* 349 */     irregularButton.setStyleClass("info_button");
/* 350 */     int closeButtonSize = 54;
/* 351 */     add((BComponent)irregularButton, new Rectangle(5, getHeight() - 5, closeButtonSize, closeButtonSize));
/*     */     
/* 353 */     irregularButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 356 */             TcgUI.getUISoundPlayer().play("ClickForward");
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   protected void close() {
/* 362 */     updateBuyList(true, (VendorItemButton)null);
/* 363 */     updateSellList(true, (SellInventoryItem)null);
/* 364 */     PanelManager.getInstance().closeWindow((BWindow)this);
/* 365 */     if (MainGameState.getPauseModel().isPaused()) {
/* 366 */       MainGameState.getPauseModel().reset();
/*     */     }
/*     */   }
/*     */   
/*     */   private void initListeners() {
/* 371 */     AbstractHudModel.ChangeListenerAdapter listener = new AbstractHudModel.ChangeListenerAdapter()
/*     */       {
/*     */         public void petPickedUp()
/*     */         {
/* 375 */           VendorFullWindow.this.updateActiveList();
/*     */         }
/*     */       };
/* 378 */     MainGameState.getHudModel().addChangeListener((HudModel.ChangeListener)listener);
/*     */     
/* 380 */     this.changeListener = new VendorChangeListener();
/*     */     
/* 382 */     this.sellButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 385 */             VendorFullWindow.this.resetBuyStatsContainer();
/* 386 */             VendorFullWindow.this.resetStatsContainer();
/* 387 */             VendorFullWindow.this.memberOnlyLabel.setVisible(false);
/*     */             
/* 389 */             VendorFullWindow.this.messageConfirmLabel.setText(TcgGame.getLocalizedText("vendorwindow.sell.confirm", new String[0]));
/* 390 */             VendorFullWindow.this.confirmContainer.setVisible(true);
/* 391 */             VendorFullWindow.this.infoContainer.setVisible(false);
/* 392 */             VendorFullWindow.this.transactionBuy = false;
/*     */             
/* 394 */             String message = VendorFullWindow.this.messageConfirmLabel.getText();
/* 395 */             int saleItemsTotal = VendorFullWindow.this.sellItemList.size();
/* 396 */             message = message.replace("###", "" + saleItemsTotal);
/* 397 */             VendorFullWindow.this.messageConfirmLabel.setText(message);
/*     */             
/* 399 */             VendorFullWindow.this.priceConfirmLabel.setText("" + VendorFullWindow.this.sellAmountCoins);
/*     */           }
/*     */         });
/*     */     
/* 403 */     this.undoBuyButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 406 */             VendorFullWindow.this.buyItemContainer.setVisible(true);
/* 407 */             VendorFullWindow.this.updateBuyList(true, (VendorItemButton)null);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 412 */     this.undoSellButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 415 */             VendorFullWindow.this.sellItemContainer.setVisible(true);
/* 416 */             VendorFullWindow.this.updateSellList(true, (SellInventoryItem)null);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 421 */     this.buyButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 424 */             VendorFullWindow.this.resetBuyStatsContainer();
/* 425 */             VendorFullWindow.this.resetStatsContainer();
/* 426 */             VendorFullWindow.this.memberOnlyLabel.setVisible(false);
/*     */             
/* 428 */             VendorFullWindow.this.messageConfirmLabel.setText(TcgGame.getLocalizedText("vendorwindow.buy.confirm", new String[0]));
/* 429 */             VendorFullWindow.this.confirmContainer.setVisible(true);
/* 430 */             VendorFullWindow.this.infoContainer.setVisible(false);
/* 431 */             VendorFullWindow.this.transactionBuy = true;
/*     */             
/* 433 */             String message = VendorFullWindow.this.messageConfirmLabel.getText();
/* 434 */             int buyItemsTotal = VendorFullWindow.this.buyItemList.size();
/* 435 */             message = message.replace("###", "" + buyItemsTotal);
/* 436 */             VendorFullWindow.this.messageConfirmLabel.setText(message);
/*     */             
/* 438 */             VendorFullWindow.this.priceConfirmLabel.setText("" + VendorFullWindow.this.buyAmountCoins);
/*     */           }
/*     */         });
/*     */     
/* 442 */     this.confirmButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 445 */             if (VendorFullWindow.this.transactionBuy) {
/* 446 */               VendorFullWindow.this.buy();
/*     */             } else {
/* 448 */               VendorFullWindow.this.sell();
/*     */             } 
/* 450 */             VendorFullWindow.this.resetConfirmContainer();
/* 451 */             VendorFullWindow.this.resetStatsContainer();
/*     */           }
/*     */         });
/*     */     
/* 455 */     this.cancelButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 458 */             VendorFullWindow.this.resetConfirmContainer();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void resetConfirmContainer() {
/* 464 */     this.confirmContainer.setVisible(false);
/* 465 */     this.infoContainer.setVisible(true);
/* 466 */     this.messageConfirmLabel.setText(TcgGame.getLocalizedText("vendorwindow.sell.confirm", new String[0]));
/* 467 */     this.priceConfirmLabel.setText("");
/*     */   }
/*     */   
/*     */   private void resetBuyStatsContainer() {
/* 471 */     this.buyStatsContainer.setVisible(false);
/*     */     
/* 473 */     this.itemBuyValueLabel.setText("0");
/* 474 */     this.itemBuyNameLabel.setText("");
/* 475 */     this.hoverSellItem = null;
/* 476 */     this.hoverBuyItem = null;
/* 477 */     setHoverIcon((BImage)null);
/*     */   }
/*     */   
/*     */   private void resetStatsContainer() {
/* 481 */     this.statsContainer.setVisible(false);
/*     */     
/* 483 */     this.itemValueLabel.setText("0");
/* 484 */     this.itemNameLabel.setText("");
/* 485 */     this.hoverSellItem = null;
/* 486 */     this.hoverBuyItem = null;
/* 487 */     setHoverIcon((BImage)null);
/*     */   }
/*     */   
/*     */   private void buy() {
/* 491 */     for (VendorItemButton buyItem : this.buyItemList) {
/* 492 */       this.model.buyItem(buyItem.getItem());
/* 493 */       buyItem.setToBuy(false);
/*     */     } 
/* 495 */     this.buyItemList.clear();
/* 496 */     this.buyAmountCoins = 0;
/* 497 */     this.buyAmountTokens = 0;
/* 498 */     this.buyCoinsTotalLabel.setText("" + this.buyAmountCoins);
/* 499 */     this.buyTokensTotalLabel.setText("" + this.buyAmountTokens);
/*     */     
/* 501 */     this.buyCoinsTotalLabel.setVisible((this.buyAmountCoins > 0));
/* 502 */     this.buyTokensTotalLabel.setVisible((this.buyAmountTokens > 0));
/*     */     
/* 504 */     toggleActionButtons(true, (this.buyItemList.size() > 0));
/*     */   }
/*     */   
/*     */   private void sell() {
/* 508 */     Inventory inventory = MainGameState.getPlayerModel().getInventory();
/*     */     
/* 510 */     for (SellInventoryItem sellItem : this.sellItemList) {
/* 511 */       this.model.sellItem(inventory.getId(), Inventory.TYPE_INVENTORY, inventory.getSlotForItem((InventoryItem)sellItem.getItem()));
/* 512 */       this.sellItemContainer.remove((BComponent)sellItem);
/*     */     } 
/* 514 */     this.sellItemList.clear();
/* 515 */     this.sellAmountCoins = 0;
/* 516 */     this.sellCoinsTotalLabel.setText("" + this.sellAmountCoins);
/*     */     
/* 518 */     this.sellCoinsTotalLabel.setVisible((this.sellAmountCoins > 0));
/* 519 */     toggleActionButtons(false, (this.sellItemList.size() > 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void slotChanged(Inventory inventory, int slotId, InventoryItem oldItem, InventoryItem newItem) {
/* 524 */     if ((newItem != null && newItem.getItemType().equals(ItemType.CRYSTAL)) || (oldItem != null && oldItem.getItemType().equals(ItemType.CRYSTAL))) {
/*     */       int coinAmount;
/*     */       
/*     */       String type;
/* 528 */       if (newItem == null) {
/* 529 */         ClientItem clientItem = (ClientItem)oldItem;
/* 530 */         type = clientItem.getItemDescription().getId();
/* 531 */         coinAmount = 0;
/*     */       } else {
/* 533 */         ClientItem clientItem = (ClientItem)newItem;
/* 534 */         type = clientItem.getItemDescription().getId();
/* 535 */         coinAmount = clientItem.getAmount();
/*     */       } 
/* 537 */       updateItem(coinAmount, type);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateBuyList(boolean buy, VendorItemButton buyItem) {
/* 542 */     if (buyItem != null) {
/* 543 */       if (buy) {
/* 544 */         if (!this.buyItemList.contains(buyItem)) {
/* 545 */           this.buyItemList.add(buyItem);
/* 546 */           this.buyAmountCoins += ((PriceDesc)buyItem.getItem().getPrice().iterator().next()).getAmount();
/*     */           
/* 548 */           if (buyItem.getItem() instanceof VendorModelItemVendorAdapter) {
/* 549 */             ClientItem clientItem = ((VendorModelItemVendorAdapter)buyItem.getItem()).getClientItem();
/* 550 */             if (clientItem.getItemType().equals(ItemType.CRYSTAL) && clientItem.getItemDescription().getId().contains("token")) {
/* 551 */               this.buyAmountTokens += buyItem.getItem().getItemAmount();
/*     */             }
/*     */           }
/*     */         
/*     */         } 
/* 556 */       } else if (this.buyItemList.contains(buyItem)) {
/* 557 */         this.buyItemList.remove(buyItem);
/* 558 */         this.buyAmountCoins -= ((PriceDesc)buyItem.getItem().getPrice().iterator().next()).getAmount();
/* 559 */         if (buyItem.getItem() instanceof VendorModelItemVendorAdapter) {
/* 560 */           ClientItem clientItem = ((VendorModelItemVendorAdapter)buyItem.getItem()).getClientItem();
/* 561 */           if (clientItem.getItemType().equals(ItemType.CRYSTAL) && clientItem.getItemDescription().getId().contains("token")) {
/* 562 */             this.buyAmountTokens -= buyItem.getItem().getItemAmount();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 568 */       for (VendorItemButton clearItem : this.buyItemList) {
/* 569 */         clearItem.setToBuy(false);
/*     */       }
/* 571 */       this.buyItemList.clear();
/* 572 */       this.buyAmountCoins = 0;
/* 573 */       this.buyAmountTokens = 0;
/*     */     } 
/* 575 */     this.buyAmountCoins = (this.buyAmountCoins < 0) ? 0 : this.buyAmountCoins;
/* 576 */     this.buyAmountTokens = (this.buyAmountTokens < 0) ? 0 : this.buyAmountTokens;
/* 577 */     this.buyCoinsTotalLabel.setText("-" + this.buyAmountCoins);
/* 578 */     this.buyTokensTotalLabel.setText("+" + this.buyAmountTokens);
/* 579 */     this.buyCoinsTotalLabel.setVisible((this.buyAmountCoins > 0));
/* 580 */     this.buyTokensTotalLabel.setVisible((this.buyAmountTokens > 0));
/* 581 */     toggleActionButtons(true, (this.buyItemList.size() > 0));
/*     */     
/* 583 */     this.activeList.updateItemListAffordable();
/* 584 */     this.activeList.updateAvailableItems();
/*     */   }
/*     */   
/*     */   private void updateSellList(boolean sell, SellInventoryItem sellItem) {
/* 588 */     if (sellItem != null) {
/* 589 */       if (sell) {
/* 590 */         if (!this.sellItemList.contains(sellItem)) {
/* 591 */           this.sellItemList.add(sellItem);
/* 592 */           this.sellAmountCoins += sellItem.getItem().getValueAmount();
/*     */         }
/*     */       
/* 595 */       } else if (this.sellItemList.contains(sellItem)) {
/* 596 */         this.sellItemList.remove(sellItem);
/* 597 */         this.sellAmountCoins -= sellItem.getItem().getValueAmount();
/*     */       } 
/*     */     } else {
/*     */       
/* 601 */       for (SellInventoryItem clearItem : this.sellItemList) {
/* 602 */         clearItem.setToSell(false);
/*     */       }
/* 604 */       this.sellItemList.clear();
/* 605 */       this.sellAmountCoins = 0;
/*     */     } 
/* 607 */     this.sellAmountCoins = (this.sellAmountCoins < 0) ? 0 : this.sellAmountCoins;
/* 608 */     this.sellCoinsTotalLabel.setText("+" + this.sellAmountCoins);
/* 609 */     this.sellCoinsTotalLabel.setVisible((this.sellAmountCoins > 0));
/* 610 */     toggleActionButtons(false, (this.sellItemList.size() > 0));
/*     */   }
/*     */   
/*     */   private void updateItem(int coinAmount, String type) {
/* 614 */     if (type.equals("coin")) {
/* 615 */       this.myCoinsTotalLabel.setText(coinAmount + "");
/* 616 */     } else if (type.equals("pet-token")) {
/* 617 */       this.myTokensTotalLabel.setText(coinAmount + "");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void clientItemDropped(ClientItem clientItem, int inventoryId, int slotId) {
/* 622 */     if (clientItem == null || isCurrency(clientItem)) {
/*     */       return;
/*     */     }
/*     */     
/* 626 */     this.model.sellItem(inventoryId, Inventory.TYPE_INVENTORY, slotId);
/*     */   }
/*     */   
/*     */   private boolean isCurrency(ClientItem clientItem) {
/* 630 */     return (clientItem.getValueClassId() == null || clientItem.getValueClassId().isEmpty());
/*     */   }
/*     */   
/*     */   public VendorModel getModel() {
/* 634 */     return this.model;
/*     */   }
/*     */   
/*     */   public void setModel(VendorModel model) {
/* 638 */     updateBuyList(true, (VendorItemButton)null);
/* 639 */     updateSellList(true, (SellInventoryItem)null);
/*     */     
/* 641 */     if (this.model != null) {
/*     */       
/* 643 */       PropNode node = TcgGame.getVendorRegister().getPropNode(Integer.valueOf(this.model.getCreatureId()));
/* 644 */       if (node != null) {
/* 645 */         InteractibleProp oldProp = (InteractibleProp)node.getProp();
/* 646 */         if (oldProp != null) {
/* 647 */           oldProp.removeUpdateListener((UpdateListener)this.closeListener);
/* 648 */           this.closeListener = null;
/*     */         } 
/*     */       } 
/*     */       
/* 652 */       this.model.disposeModel();
/*     */     } 
/* 654 */     this.model = model;
/*     */     
/* 656 */     InteractibleProp vendorProp = (InteractibleProp)TcgGame.getVendorRegister().getPropNode(Integer.valueOf(model.getCreatureId())).getProp();
/* 657 */     this.closeListener = new CloseWindowListener(vendorProp, VendorFullWindow.class);
/* 658 */     vendorProp.addUpdateListener((UpdateListener)this.closeListener);
/*     */     
/* 660 */     this.vendorTitleLabel.setText(model.getName());
/* 661 */     model.addChangeListener(this.changeListener);
/* 662 */     this.activeList = new VendorItemListBuy(this.buyItemContainer, model);
/* 663 */     this.activeList.containerSelected();
/*     */     
/* 665 */     this.sellItemContainer.removeAll();
/*     */     
/* 667 */     Iterator<InventoryItem> itemIterator = MainGameState.getPlayerModel().getInventory().iterator();
/* 668 */     HashMap<ItemType, ArrayList<SellInventoryItem>> typeItemMap = new HashMap<ItemType, ArrayList<SellInventoryItem>>();
/* 669 */     while (itemIterator.hasNext()) {
/* 670 */       InventoryItem item = itemIterator.next();
/* 671 */       if (item == null) {
/*     */         continue;
/*     */       }
/* 674 */       switch (item.getItemType()) {
/*     */         case CRYSTAL:
/* 676 */           if (item.getClassId().equals("coin")) {
/* 677 */             this.myCoinsTotalLabel.setText("" + item.getAmount()); continue;
/* 678 */           }  if (item.getClassId().equals("pet-token")) {
/* 679 */             this.myTokensTotalLabel.setText("" + item.getAmount());
/*     */           }
/*     */ 
/*     */         
/*     */         case EQUIP_HEAD:
/*     */         case EQUIP_TORSO:
/*     */         case EQUIP_LEGS:
/*     */         case EQUIP_BACK:
/*     */         case EQUIP_TRINKET:
/*     */         case EQUIP_SUBSCRIBER:
/* 689 */           if (!(item instanceof ClientItem) || (
/* 690 */             (ClientItem)item).getItemDescription().getId().equals("eq-starter-head")) {
/*     */             continue;
/*     */           }
/* 693 */           if (item.getValueAmount() <= 0) {
/*     */             continue;
/*     */           }
/*     */           
/* 697 */           if (!MainGameState.getPlayerModel().getEquipDoll().getItems().contains(item)) {
/* 698 */             SellInventoryItem sellItem = new SellInventoryItem((ClientItem)item, false, model);
/* 699 */             if (typeItemMap.get(item.getItemType()) == null) {
/* 700 */               typeItemMap.put(item.getItemType(), new ArrayList<SellInventoryItem>());
/*     */             }
/*     */             
/* 703 */             ((ArrayList<SellInventoryItem>)typeItemMap.get(item.getItemType())).add(sellItem);
/*     */           } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 712 */     for (ItemType type : typeItemMap.keySet()) {
/* 713 */       for (SellInventoryItem item : typeItemMap.get(type)) {
/* 714 */         this.sellItemContainer.add((BComponent)item);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 721 */     super.setVisible(visible);
/* 722 */     if (!visible) {
/* 723 */       this.buyContentsScrollPane.getVerticalScrollBar().getModel().setValue(0);
/* 724 */       this.sellContentsScrollPane.getVerticalScrollBar().getModel().setValue(0);
/*     */       
/* 726 */       PropNode node = TcgGame.getVendorRegister().getPropNode(Integer.valueOf(this.model.getCreatureId()));
/* 727 */       if (node != null) {
/* 728 */         Creature vendorProp = (Creature)node.getProp();
/* 729 */         if (vendorProp != null) {
/* 730 */           SpeachMapping speachMapping = TcgGame.getRpgLoader().getSpeachManager().getSpeachDescritpionForNpc(vendorProp.getMonsterId());
/*     */           
/* 732 */           if (speachMapping != null) {
/* 733 */             String key = speachMapping.getRandomSpeachForContext(SpeachContext.VENDOR_CLOSE);
/* 734 */             if (key != null) {
/* 735 */               String text = JavaLocalization.getInstance().getLocalizedRPGText(key);
/* 736 */               MainGameState.getChatUIController().createChatBubble(text, text, node);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 741 */       this.model.removeChangeListener(this.changeListener);
/* 742 */       this.model.disposeModel();
/*     */     } 
/* 744 */     resetConfirmContainer();
/* 745 */     resetStatsContainer();
/* 746 */     resetBuyStatsContainer();
/* 747 */     this.memberOnlyLabel.setVisible(false);
/*     */     
/* 749 */     this.activeList.updateAvailableItems();
/*     */ 
/*     */     
/* 752 */     this.buyCoinsTotalLabel.setVisible(false);
/* 753 */     this.buyTokensTotalLabel.setVisible(false);
/* 754 */     this.sellCoinsTotalLabel.setVisible(false);
/*     */   }
/*     */   
/*     */   public int getBuyAmountCoins() {
/* 758 */     return this.buyAmountCoins;
/*     */   }
/*     */   
/*     */   private class VendorChangeListener
/*     */     implements VendorModel.ChangeListener {
/*     */     private VendorChangeListener() {}
/*     */     
/*     */     public void vendorItemsChanged() {}
/*     */     
/*     */     public void playerCurrencyChanged(String classId, int amount) {
/* 768 */       VendorFullWindow.this.activeList.currencyChanged(classId, amount);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void itemSold(VendorModelItem vendorModelItem) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void newItemInInventory(String classId, int tier) {
/* 778 */       VendorFullWindow.this.activeList.updateAvailableItems();
/* 779 */       TcgUI.getUISoundPlayer().play("TransactionBuying");
/*     */     }
/*     */ 
/*     */     
/*     */     public void buyItem(boolean buy, VendorItemButton item) {
/* 784 */       VendorFullWindow.this.updateBuyList(buy, item);
/* 785 */       VendorFullWindow.this.resetConfirmContainer();
/*     */     }
/*     */ 
/*     */     
/*     */     public void sellItem(boolean sell, SellInventoryItem item) {
/* 790 */       VendorFullWindow.this.updateSellList(sell, item);
/* 791 */       VendorFullWindow.this.resetConfirmContainer();
/*     */     }
/*     */ 
/*     */     
/*     */     public void buyItemHoverEnter(VendorModelItem item) {
/* 796 */       VendorFullWindow.this.buyStatsContainer.setVisible(!VendorFullWindow.this.confirmContainer.isVisible());
/* 797 */       if (!VendorFullWindow.this.buyStatsContainer.isVisible()) {
/*     */         return;
/*     */       }
/* 800 */       VendorFullWindow.this.hoverBuyItem = item;
/* 801 */       if (item instanceof VendorModelItemVendorAdapter) {
/* 802 */         ClientItem clientItem = ((VendorModelItemVendorAdapter)item).getClientItem();
/* 803 */         if (clientItem.getItemType().isEquipable()) {
/* 804 */           sellItemHoverEnter(clientItem); return;
/*     */         } 
/* 806 */         if (!clientItem.getItemType().equals(ItemType.POTION) && !clientItem.getItemType().equals(ItemType.CRYSTAL)) {
/* 807 */           VendorFullWindow.this.previewBuyLevel.setText(TcgGame.getLocalizedText("rewardwindow.level", new String[0]) + ": " + item.getLevel());
/*     */         }
/*     */       } 
/*     */       
/* 811 */       VendorFullWindow.this.buyStatsContainer.setVisible(!VendorFullWindow.this.confirmContainer.isVisible());
/* 812 */       VendorFullWindow.this.statsContainer.setVisible(!VendorFullWindow.this.buyStatsContainer.isVisible());
/* 813 */       if (!VendorFullWindow.this.buyStatsContainer.isVisible()) {
/*     */         return;
/*     */       }
/*     */       
/* 817 */       VendorFullWindow.this.memberOnlyLabel.setVisible((item.isSubscriberOnly() && !MainGameState.isPlayerSubscriber()));
/*     */       
/* 819 */       VendorFullWindow.this.itemBuyValueLabel.setText("" + ((PriceDesc)item.getPrice().iterator().next()).getAmount());
/*     */       
/* 821 */       if (!VendorFullWindow.this.activeList.isAffordable(item)) { VendorFullWindow.this.itemBuyValueLabel.setColor(0, new ColorRGBA(1.0F, 0.0F, 0.0F, 1.0F)); }
/* 822 */       else { VendorFullWindow.this.itemBuyValueLabel.setColor(0, new ColorRGBA(1.0F, 1.0F, 0.4F, 1.0F)); }
/*     */       
/* 824 */       String name = item.getName();
/* 825 */       if (name.contains("MISSING LOCALIZATION KEY:")) name = name.substring(name.indexOf(":"));
/*     */       
/* 827 */       VendorFullWindow.this.itemBuyNameLabel.setText(name + "" + ((item.getItemAmount() > 1) ? (" - " + item.getItemAmount()) : ""));
/*     */       
/* 829 */       String itemIconPath = item.getIcon();
/* 830 */       VendorFullWindow.this.setHoverIcon((BImage)VendorFullWindow.this.resourceManager.getResource(BImage.class, itemIconPath));
/*     */       
/* 832 */       VendorFullWindow.this.itemBuyDescription.setText(!item.getItemDescriptionText().isEmpty() ? JavaLocalization.getInstance().getLocalizedRPGText(item.getItemDescriptionText()) : "");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void sellItemHoverEnter(ClientItem item) {
/* 839 */       VendorFullWindow.this.statsContainer.setVisible(!VendorFullWindow.this.confirmContainer.isVisible());
/* 840 */       if (!VendorFullWindow.this.statsContainer.isVisible()) {
/*     */         return;
/*     */       }
/*     */       
/* 844 */       VendorFullWindow.this.memberOnlyLabel.setVisible((item.isSubscriberOnly() && !MainGameState.isPlayerSubscriber()));
/*     */       
/* 846 */       VendorFullWindow.this.previewLevel.setText(TcgGame.getLocalizedText("rewardwindow.level", new String[0]).trim() + ": " + item.getLevel());
/*     */       
/* 848 */       if (item.equals(VendorFullWindow.this.hoverBuyItem)) {
/* 849 */         VendorFullWindow.this.itemValueLabel.setText("" + ((PriceDesc)VendorFullWindow.this.hoverBuyItem.getPrice().iterator().next()).getAmount());
/*     */       } else {
/* 851 */         VendorFullWindow.this.itemValueLabel.setText("" + item.getValueAmount());
/*     */       } 
/*     */       
/* 854 */       String name = item.getName();
/* 855 */       VendorFullWindow.this.itemNameLabel.setText("" + name);
/*     */       
/* 857 */       String itemIconPath = item.getIcon();
/* 858 */       VendorFullWindow.this.setHoverIcon((BImage)VendorFullWindow.this.resourceManager.getResource(BImage.class, itemIconPath));
/*     */       
/* 860 */       VendorFullWindow.this.hoverSellItem = item;
/*     */ 
/*     */       
/* 863 */       ItemDescription desc = TcgGame.getRpgLoader().getItemManager().getDescription(item.getClassId(), item.getTier());
/* 864 */       double[] itemStats = ClientStatCalc.getAbilityAmounts(desc.getArchType(), item.getLevel(), 5);
/* 865 */       double[] maxStats = ClientStatCalc.getAbilityAmounts(new ArchType("", 1.0D, 1.0D, 1.0D, 1.0D, 1.0D), MainGameState.getPlayerModel().getStatSupport().getLevel(), 5);
/*     */ 
/*     */       
/* 868 */       for (int i = 0; i < itemStats.length; i++) {
/* 869 */         float value = (float)(itemStats[i] / maxStats[i]);
/*     */         
/* 871 */         value = (value > 1.0F) ? 1.0F : ((value < 0.0F) ? 0.0F : value);
/* 872 */         VendorFullWindow.this.itemProgresses[i].setProgress(value);
/* 873 */         VendorFullWindow.this.itemProgressGloss[i].setText("" + (int)itemStats[i]);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void ItemHoverExit() {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHoverIcon(BImage icon) {
/* 887 */     if (this.added) {
/* 888 */       if (this.icon != null) {
/* 889 */         this.icon.release();
/*     */       }
/* 891 */       if (icon != null) {
/* 892 */         icon.reference();
/*     */       }
/*     */     } 
/* 895 */     this.icon = icon;
/*     */   }
/*     */   
/*     */   protected int getIconSize() {
/* 899 */     Insets insets = this.itemIconLabel.getInsets();
/*     */     
/* 901 */     int w = this.itemIconLabel.getWidth() - insets.getHorizontal();
/* 902 */     int h = this.itemIconLabel.getHeight() - insets.getVertical();
/* 903 */     return Math.min(w, h);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderComponent(Renderer renderer) {
/* 908 */     super.renderComponent(renderer);
/*     */     
/* 910 */     if ((this.hoverSellItem != null || this.hoverBuyItem != null) && !this.confirmContainer.isVisible()) {
/* 911 */       Point pos = new Point(this.itemIconLabel.getAbsoluteX() - getAbsoluteX(), this.itemIconLabel.getAbsoluteY() - getAbsoluteY());
/*     */       
/* 913 */       int size = getIconSize();
/*     */       
/* 915 */       renderIcon(renderer, pos, size);
/*     */       
/* 917 */       if (this.memberOnlyLabel.isVisible()) this.memberOnlyLabel.render(renderer); 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean renderIcon(Renderer renderer, Point pos, int size) {
/* 922 */     if (this.icon != null) {
/* 923 */       this.icon.render(renderer, pos.x, pos.y, size, size, getAlpha());
/*     */       
/* 925 */       return true;
/*     */     } 
/* 927 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void wasAdded() {
/* 932 */     this.added = true;
/* 933 */     super.wasAdded();
/* 934 */     if (this.icon != null) {
/* 935 */       this.icon.reference();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void wasRemoved() {
/* 941 */     if (this.icon != null) {
/* 942 */       this.icon.release();
/*     */     }
/* 944 */     super.wasRemoved();
/* 945 */     this.added = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\vendor\VendorFullWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */