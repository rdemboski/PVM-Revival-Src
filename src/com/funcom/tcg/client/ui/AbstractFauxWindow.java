/*     */ package com.funcom.tcg.client.ui;
/*     */ 
/*     */ import com.funcom.commons.jme.bui.HighlightedButton;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BClickthroughLabel;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BStyleSheet;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ 
/*     */ public abstract class AbstractFauxWindow
/*     */   extends BWindow {
/*     */   protected static final int TITLE_HEIGHT = 32;
/*     */   protected BClickthroughLabel titleLabel;
/*     */   
/*     */   public AbstractFauxWindow(BStyleSheet style, BLayoutManager layout) {
/*  27 */     super(style, layout);
/*     */     
/*  29 */     createComponents();
/*  30 */     layoutComponents();
/*  31 */     setLayer(1);
/*     */   }
/*     */   protected BContainer fauxWindow; protected BButton closeButton; protected static final double FLASH_SPEED = 2.0D;
/*     */   public AbstractFauxWindow(String name, BStyleSheet style, BLayoutManager layout) {
/*  35 */     super(name, style, layout);
/*     */     
/*  37 */     createComponents();
/*  38 */     layoutComponents();
/*  39 */     setLayer(1);
/*     */   }
/*     */   
/*     */   protected abstract int getWindowHeight();
/*     */   
/*     */   protected abstract int getWindowWidth();
/*     */   
/*     */   protected abstract int getWindowOffset();
/*     */   
/*     */   private void createComponents() {
/*  49 */     this.fauxWindow = new BContainer((BLayoutManager)new FreeAbsoluteLayout());
/*  50 */     this.fauxWindow.setStyleClass("faux-window");
/*  51 */     this.fauxWindow.setSize(getWindowWidth(), getWindowHeight());
/*     */     
/*  53 */     this.titleLabel = new BClickthroughLabel("");
/*  54 */     this.titleLabel.setStyleClass("title-label");
/*  55 */     this.fauxWindow.add((BComponent)this.titleLabel, new Rectangle(0, getWindowHeight() - 32, this.fauxWindow.getWidth(), 32));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addDefaultCloseButton(CloseButtonPosition closeButtonPosition) {
/*  61 */     if (closeButtonPosition == CloseButtonPosition.UPPER_RIGHT) {
/*  62 */       this.closeButton = new StretchedImageButton();
/*  63 */       int closeButtonSize = 24;
/*  64 */       int closeOffset = 45;
/*  65 */       this.fauxWindow.add((BComponent)this.closeButton, new Rectangle(getWindowWidth() - closeOffset, getWindowHeight() - closeOffset, closeButtonSize, closeButtonSize));
/*     */     
/*     */     }
/*  68 */     else if (closeButtonPosition == CloseButtonPosition.CENTER) {
/*  69 */       this.closeButton = (BButton)new HighlightedButton();
/*  70 */       int closeButtonSize = 66;
/*  71 */       this.fauxWindow.add((BComponent)this.closeButton, new Rectangle(795 - closeButtonSize, 19, closeButtonSize, closeButtonSize));
/*  72 */     } else if (closeButtonPosition == CloseButtonPosition.TOP_RIGHT) {
/*  73 */       this.closeButton = (BButton)new HighlightedButton();
/*  74 */       int closeButtonSize = 24;
/*  75 */       this.fauxWindow.add((BComponent)this.closeButton, new Rectangle(1024 - closeButtonSize - 5, getWindowHeight() - closeButtonSize - 5, closeButtonSize, closeButtonSize));
/*  76 */     } else if (closeButtonPosition == CloseButtonPosition.REGISTER) {
/*  77 */       this.closeButton = (BButton)new HighlightedButton();
/*  78 */       int closeButtonSize = 89;
/*  79 */       this.fauxWindow.add((BComponent)this.closeButton, new Rectangle(760 - closeButtonSize, 36, closeButtonSize, closeButtonSize));
/*     */     } else {
/*  81 */       this.closeButton = (BButton)new HighlightedButton();
/*  82 */       int closeButtonSize = 89;
/*  83 */       this.fauxWindow.add((BComponent)this.closeButton, new Rectangle(1024 - closeButtonSize - 5, 19, closeButtonSize, closeButtonSize));
/*     */     } 
/*     */ 
/*     */     
/*  87 */     this.closeButton.setStyleClass("close-button");
/*  88 */     this.closeButton.setTooltipText(TcgGame.getLocalizedText("tooltips.friends.close", new String[0]));
/*  89 */     this.closeButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/*  92 */             AbstractFauxWindow.this.close();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void close() {
/*  99 */     PanelManager.getInstance().closeWindow(this);
/*     */     
/* 101 */     if (MainGameState.getPauseModel().isPaused())
/* 102 */       MainGameState.getPauseModel().reset(); 
/*     */   }
/*     */   
/*     */   private void layoutComponents() {
/* 106 */     int width = DisplaySystem.getDisplaySystem().getWidth();
/* 107 */     int height = DisplaySystem.getDisplaySystem().getHeight();
/* 108 */     if (width != getWidth() || height != getHeight()) {
/* 109 */       removeAll();
/*     */     } else {
/*     */       return;
/*     */     } 
/* 113 */     setSize(width, height);
/* 114 */     int centerX = width / 2;
/* 115 */     int centerY = height / 2;
/* 116 */     add((BComponent)this.fauxWindow, new Rectangle(centerX - getWindowWidth() / 2, centerY - getWindowHeight() / 2 + getWindowOffset(), getWindowWidth(), getWindowHeight()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 123 */     super.setVisible(visible);
/* 124 */     if (visible)
/* 125 */       layoutComponents(); 
/*     */   }
/*     */   
/*     */   protected static class NoFocusLabel
/*     */     extends BLabel
/*     */   {
/*     */     public NoFocusLabel() {
/* 132 */       super("");
/*     */     }
/*     */ 
/*     */     
/*     */     public BComponent getHitComponent(int mx, int my) {
/* 137 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */   protected enum CloseButtonPosition {
/* 142 */     UPPER_RIGHT,
/* 143 */     CENTER,
/* 144 */     REGISTER,
/* 145 */     TOP_RIGHT;
/*     */   }
/*     */   
/*     */   protected BButton getCloseButton() {
/* 149 */     return this.closeButton;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\AbstractFauxWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */