/*     */ package com.funcom.tcg.client.ui;
/*     */ import com.funcom.commons.jme.bui.HighlightedButton;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Insets;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public abstract class AbstractTcgWindow extends BWindow {
/*  20 */   private static final Logger LOG = Logger.getLogger(AbstractTcgWindow.class.getName());
/*     */   
/*     */   private static final String CLOSE_BUTTON_STYLE = "close_button";
/*     */   
/*     */   protected static final String STYLE_TITLE_LABEL = "title-label";
/*     */   
/*     */   protected final ResourceManager resourceManager;
/*     */   
/*     */   private BLabel titleLabel;
/*     */   
/*     */   private Rectangle titleLabelBounds;
/*     */   private BContainer clientArea;
/*     */   private Rectangle clientAreaBounds;
/*     */   private Rectangle closeButtonBounds;
/*     */   private final String styleClass;
/*     */   protected BButton closeButton;
/*     */   private boolean enableTitle = true;
/*     */   
/*     */   public AbstractTcgWindow(ResourceManager resourceManager) {
/*  39 */     super(null, (BLayoutManager)new AbsoluteLayout());
/*  40 */     this.resourceManager = resourceManager;
/*  41 */     BananaResourceProvider buiResourceProvider = new BananaResourceProvider(resourceManager);
/*  42 */     this._style = BuiUtils.createMergedClassStyleSheets(getClass(), buiResourceProvider);
/*     */     
/*  44 */     initClientArea();
/*  45 */     initCloseButton();
/*  46 */     initTitle();
/*  47 */     this.styleClass = getStyleClassString();
/*  48 */     setLayer(2);
/*     */   }
/*     */   
/*     */   private void initClientArea() {
/*  52 */     this.clientArea = new BContainer("client area", (BLayoutManager)new FreeAbsoluteLayout());
/*  53 */     this.clientArea.setStyleClass("clientarea");
/*  54 */     this.clientAreaBounds = new Rectangle();
/*  55 */     add((BComponent)this.clientArea, this.clientAreaBounds);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDefaultStyleClass() {
/*  60 */     return this.styleClass;
/*     */   }
/*     */   
/*     */   private String getStyleClassString() {
/*  64 */     PanelManager.Location location = PanelManager.getInstance().getLocation(getClass());
/*  65 */     switch (location) {
/*     */       case LEFT:
/*  67 */         return "tcgwindow.left";
/*     */       case RIGHT:
/*  69 */         return "tcgwindow.right";
/*     */     } 
/*  71 */     return "tcgwindow";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void layout() {
/*  76 */     if (!isValid()) {
/*  77 */       Insets insets = getInsets();
/*  78 */       layoutTitle(insets);
/*     */       
/*  80 */       this.clientAreaBounds.set(0, 0, 440 - insets.getHorizontal(), 677 - insets.getVertical() - this.titleLabelBounds.height);
/*     */ 
/*     */ 
/*     */       
/*  84 */       PanelManager.Location location = PanelManager.getInstance().getLocation(getClass());
/*  85 */       Rectangle closeBounds = location.getCloseButtonBounds();
/*  86 */       if (closeBounds != null) {
/*  87 */         this.closeButtonBounds.set(closeBounds.x, closeBounds.y, closeBounds.width, closeBounds.height);
/*     */       }
/*     */     } 
/*  90 */     super.layout();
/*     */   }
/*     */   
/*     */   private void layoutTitle(Insets insets) {
/*  94 */     if (this.enableTitle) {
/*  95 */       this.titleLabel.setEnabled(true);
/*  96 */       int fontHeight = 44;
/*  97 */       int titleY = getHeight() - fontHeight - insets.top;
/*  98 */       this.titleLabelBounds.set(0, titleY - insets.bottom, getWidth() - insets.getHorizontal(), fontHeight);
/*     */     } else {
/* 100 */       this.titleLabelBounds.set(0, 0, 0, 0);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void enableTitle(boolean enable) {
/* 105 */     this.enableTitle = enable;
/* 106 */     this.titleLabel.setEnabled(enable);
/*     */   }
/*     */   
/*     */   private void initCloseButton() {
/* 110 */     PanelManager.Location location = PanelManager.getInstance().getLocation(getClass());
/* 111 */     if (location == PanelManager.Location.CENTER)
/*     */       return; 
/* 113 */     BButton button = new StretchedImageButton();
/* 114 */     button.setStyleClass("close_button");
/* 115 */     button.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 118 */             PanelManager.getInstance().closeWindow(AbstractTcgWindow.this);
/*     */           }
/*     */         });
/* 121 */     this.closeButtonBounds = new Rectangle();
/* 122 */     add((BComponent)button, this.closeButtonBounds);
/*     */   }
/*     */   
/*     */   private void initTitle() {
/* 126 */     this.titleLabel = new BLabel("");
/* 127 */     this.titleLabel.setStyleClass("title-label");
/* 128 */     this.titleLabelBounds = new Rectangle(0, 0, 0, 0);
/* 129 */     add((BComponent)this.titleLabel, this.titleLabelBounds);
/*     */   }
/*     */   
/*     */   protected void setTitle(String title) {
/* 133 */     this.titleLabel.setText(title);
/*     */   }
/*     */   
/*     */   public BContainer getClientArea() {
/* 137 */     return this.clientArea;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addDefaultCloseButton(CloseButtonPosition closeButtonPosition) {
/* 142 */     if (closeButtonPosition == CloseButtonPosition.UPPER_RIGHT) {
/* 143 */       this.closeButton = new StretchedImageButton();
/* 144 */       int closeButtonSize = 24;
/* 145 */       int closeOffset = 45;
/* 146 */       add((BComponent)this.closeButton, new Rectangle(getWidth() - closeOffset, getHeight() - closeOffset, closeButtonSize, closeButtonSize));
/*     */     
/*     */     }
/* 149 */     else if (closeButtonPosition == CloseButtonPosition.CENTER) {
/* 150 */       this.closeButton = (BButton)new HighlightedButton();
/* 151 */       int closeButtonSize = 66;
/* 152 */       add((BComponent)this.closeButton, new Rectangle(795 - closeButtonSize, 19, closeButtonSize, closeButtonSize));
/* 153 */     } else if (closeButtonPosition == CloseButtonPosition.TOP_RIGHT) {
/* 154 */       this.closeButton = (BButton)new HighlightedButton();
/* 155 */       int closeButtonSize = 24;
/* 156 */       add((BComponent)this.closeButton, new Rectangle(getWidth() - closeButtonSize - 5, getHeight() - closeButtonSize - 5, closeButtonSize, closeButtonSize));
/* 157 */     } else if (closeButtonPosition == CloseButtonPosition.REGISTER) {
/* 158 */       this.closeButton = (BButton)new HighlightedButton();
/* 159 */       int closeButtonSize = 89;
/* 160 */       add((BComponent)this.closeButton, new Rectangle(760 - closeButtonSize, 36, closeButtonSize, closeButtonSize));
/* 161 */     } else if (closeButtonPosition == CloseButtonPosition.TOP_LEFT) {
/* 162 */       this.closeButton = (BButton)new HighlightedButton();
/* 163 */       int closeButtonSize = 24;
/* 164 */       add((BComponent)this.closeButton, new Rectangle(15, 615, closeButtonSize, closeButtonSize));
/*     */     } else {
/* 166 */       this.closeButton = (BButton)new HighlightedButton();
/* 167 */       int closeButtonSize = 89;
/* 168 */       add((BComponent)this.closeButton, new Rectangle(1024 - closeButtonSize - 5, 19, closeButtonSize, closeButtonSize));
/*     */     } 
/*     */ 
/*     */     
/* 172 */     this.closeButton.setStyleClass("close-button");
/* 173 */     this.closeButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 176 */             AbstractTcgWindow.this.close();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void close() {
/* 183 */     PanelManager.getInstance().closeWindow(this);
/*     */     
/* 185 */     if (MainGameState.getPauseModel().isPaused())
/* 186 */       MainGameState.getPauseModel().reset(); 
/*     */   }
/*     */   
/*     */   protected enum CloseButtonPosition
/*     */   {
/* 191 */     UPPER_RIGHT,
/* 192 */     CENTER,
/* 193 */     REGISTER,
/* 194 */     TOP_RIGHT,
/* 195 */     TOP_LEFT;
/*     */   }
/*     */   
/*     */   protected BButton getCloseButton() {
/* 199 */     return this.closeButton;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\AbstractTcgWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */