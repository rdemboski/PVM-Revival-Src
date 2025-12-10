/*     */ package com.funcom.tcg.client.ui.account;
/*     */ import com.funcom.commons.jme.bui.HighlightedButton;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.peeler.BananaPeel;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.metrics.HttpMetrics;
/*     */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.Localizer;
/*     */ import com.funcom.tcg.client.ui.hud.SubscribeWindow;
/*     */ import com.funcom.tcg.net.DefaultSubscriptionState;
/*     */ import com.funcom.tcg.net.SubscriptionState;
/*     */ import com.funcom.tcg.net.message.AccountSubscribeTokenRequestMessage;
/*     */ import com.jme.input.MouseInput;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BStyleSheet;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ import com.jmex.bui.event.MouseListener;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ 
/*     */ public class AccountButtonWindow extends BWindow implements PartiallyNotInteractive {
/*  31 */   String REGISTER_WINDOW_PATH = "gui/peeler/window_save.xml";
/*     */   
/*     */   private RegisterWindow registerWindow;
/*  34 */   private final int WINDOW_WIDTH = 220; private final int WINDOW_HEIGHT = 54;
/*     */   
/*     */   private static final String LOCALIZATIONKEY_REGISTER = "accountregister.button";
/*     */   
/*     */   private static final String LOCALIZATIONKEY_SUBSCRIBE = "accountsubscribe.button";
/*     */   
/*     */   private static final String STYLEPREFIX_REGISTER = "register";
/*     */   
/*     */   private static final String STYLEPREFIX_SUBSCRIBE = "subscribe";
/*     */   
/*     */   private final LocalClientPlayer player;
/*     */   
/*     */   private final ResourceManager resourceManager;
/*     */   private final AccountRegistrationHandler registrationHandler;
/*     */   private final Localizer localizer;
/*     */   private DefaultSubscriptionState.ChangeListener changeListener;
/*     */   private HighlightedButton subscribeButton;
/*     */   private HighlightedButton catFaceLabel;
/*     */   
/*     */   public AccountButtonWindow(LocalClientPlayer player, ResourceManager resourceManager, AccountRegistrationHandler registrationHandler, Localizer localizer) {
/*  54 */     super(null, (BLayoutManager)new AbsoluteLayout());
/*  55 */     this.player = player;
/*  56 */     this.resourceManager = resourceManager;
/*  57 */     this.registrationHandler = registrationHandler;
/*  58 */     this.localizer = localizer;
/*  59 */     BananaResourceProvider buiResourceProvider = new BananaResourceProvider(resourceManager);
/*  60 */     this._style = BuiUtils.createMergedClassStyleSheets(getClass(), buiResourceProvider);
/*     */     
/*  62 */     String locale = (System.getProperty("tcg.locale") != null) ? System.getProperty("tcg.locale") : "";
/*     */     
/*  64 */     int localizationBuffer = locale.equals("en") ? 75 : 0;
/*  65 */     setSize(220 + localizationBuffer, 54);
/*     */     
/*  67 */     ActionListener buttonHandler = new ActionListener()
/*     */       {
/*     */         public void actionPerformed(ActionEvent event) {
/*  70 */           if (!MainGameState.isPlayerRegistered()) {
/*  71 */             AccountButtonWindow.this.showSaveCharacterWindow("SAVE_CHARACTER_BUTTON");
/*     */           } else {
/*     */             try {
/*  74 */               NetworkHandler.instance().getIOHandler().send((Message)new AccountSubscribeTokenRequestMessage());
/*  75 */             } catch (InterruptedException e) {
/*  76 */               e.printStackTrace();
/*     */             } 
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/*  82 */     MouseListener mouseHandler = new MouseListener()
/*     */       {
/*     */         public void mouseEntered(MouseEvent event) {
/*  85 */           AccountButtonWindow.this.catFaceLabel.setFakeHover(true);
/*     */         }
/*     */ 
/*     */         
/*     */         public void mouseExited(MouseEvent event) {
/*  90 */           AccountButtonWindow.this.catFaceLabel.setFakeHover(false);
/*     */         }
/*     */ 
/*     */         
/*     */         public void mousePressed(MouseEvent event) {
/*  95 */           AccountButtonWindow.this.catFaceLabel.setFakeHover(false);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void mouseReleased(MouseEvent event) {}
/*     */       };
/* 104 */     this.subscribeButton = new HighlightedButton("SUBSCRIBE!");
/* 105 */     this.subscribeButton.setStyleClass("button.subscribe" + (locale.equals("fr") ? ".fr" : ""));
/* 106 */     add((BComponent)this.subscribeButton, new Rectangle(0, 0, getWidth(), 54));
/*     */     
/* 108 */     this.subscribeButton.addListener((ComponentListener)buttonHandler);
/* 109 */     this.subscribeButton.addListener((ComponentListener)mouseHandler);
/*     */     
/* 111 */     this.catFaceLabel = new HighlightedButton();
/* 112 */     this.catFaceLabel.setStyleClass("catface");
/* 113 */     this.catFaceLabel.setClickthrough(true);
/* 114 */     add((BComponent)this.catFaceLabel, new Rectangle(5, 4, 45, 45));
/*     */     
/* 116 */     refreshButton((SubscriptionState)player.getSubscriptionState());
/*     */     
/* 118 */     this.changeListener = new DefaultSubscriptionState.ChangeListener()
/*     */       {
/*     */         public void subscriptionStateChanged(DefaultSubscriptionState subscriptionState) {
/* 121 */           AccountButtonWindow.this.refreshButton((SubscriptionState)subscriptionState);
/*     */         }
/*     */       };
/* 124 */     player.getSubscriptionState().addListener(this.changeListener);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configureStyle(BStyleSheet style) {
/* 129 */     super.configureStyle(style);
/*     */   }
/*     */   
/*     */   public void showSaveCharacterWindow(String callSite) {
/* 133 */     if (!this.player.getSubscriptionState().isRegistered()) {
/* 134 */       MainGameState.getAfkShutdownHandler().setOverride(true);
/* 135 */       if (this.registerWindow == null) {
/* 136 */         BananaPeel bananaPeel = (BananaPeel)TcgGame.getResourceManager().getResource(BananaPeel.class, this.REGISTER_WINDOW_PATH, CacheType.NOT_CACHED);
/*     */         
/* 138 */         this.registerWindow = new RegisterWindow(this.REGISTER_WINDOW_PATH, bananaPeel, TcgGame.getResourceManager(), this.registrationHandler, this.localizer);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 143 */       PanelManager.getInstance().addWindow((BWindow)this.registerWindow);
/*     */       
/* 145 */       HttpMetrics.postEvent(new HttpMetrics.Event(HttpMetrics.Event.SAVE_CHARACTER_CLICKED, callSite));
/*     */       
/* 147 */       if (!MainGameState.getPauseModel().isPaused()) {
/* 148 */         MainGameState.getPauseModel().instantPause();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void dismiss() {
/* 155 */     super.dismiss();
/* 156 */     this.player.getSubscriptionState().removeListener(this.changeListener);
/*     */   }
/*     */   
/*     */   public void showSubscribeWindow() {
/* 160 */     if (!this.player.getSubscriptionState().isSubscriber()) {
/*     */       
/* 162 */       SubscribeWindow window = new SubscribeWindow(TcgGame.getResourceManager(), MainGameState.isPlayerRegistered(), "subscribedialog.button.subscribe", "subscribedialog.button.cancel", "subscribedialog.message");
/*     */ 
/*     */       
/* 165 */       window.setLayer(101);
/* 166 */       BuiSystem.getRootNode().addWindow((BWindow)window);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHit() {
/* 173 */     int mx = MouseInput.get().getXAbsolute();
/* 174 */     int my = MouseInput.get().getYAbsolute();
/* 175 */     BComponent comp = getHitComponent(mx, my);
/* 176 */     return (comp != null && comp != this && comp.isEnabled());
/*     */   }
/*     */ 
/*     */   
/*     */   public BComponent getHitComponent(int mx, int my) {
/* 181 */     BComponent hitComponent = super.getHitComponent(mx, my);
/* 182 */     if (hitComponent != this) {
/* 183 */       return hitComponent;
/*     */     }
/* 185 */     return null;
/*     */   }
/*     */   
/*     */   private void refreshButton(SubscriptionState subscriptionState) {
/* 189 */     boolean subscriber = subscriptionState.isSubscriber();
/* 190 */     boolean registered = subscriptionState.isRegistered();
/*     */     
/* 192 */     if (!subscriber) {
/* 193 */       if (registered) {
/* 194 */         this.subscribeButton.setText(this.localizer.getLocalizedText(getClass(), "accountsubscribe.button", new String[0]));
/*     */       } else {
/* 196 */         this.subscribeButton.setText(this.localizer.getLocalizedText(getClass(), "accountregister.button", new String[0]));
/*     */       } 
/*     */     } else {
/* 199 */       removeAll();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class RenderOnlyLabel extends BLabel {
/*     */     public RenderOnlyLabel(String text, String styleClass) {
/* 205 */       super(text, styleClass);
/*     */     }
/*     */ 
/*     */     
/*     */     public BComponent getHitComponent(int mx, int my) {
/* 210 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\account\AccountButtonWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */