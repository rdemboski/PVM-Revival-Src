/*     */ package com.funcom.tcg.client.ui;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BStyleSheet;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.BEvent;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.layout.BorderLayout;
/*     */ import com.jmex.bui.util.Dimension;
/*     */ 
/*     */ 
/*     */ public class TCGDialog
/*     */   extends AbstractTcgWindow
/*     */ {
/*     */   private Localizer localizer;
/*     */   private BLabel messageLabel;
/*     */   private BButton butOk;
/*     */   private BButton butCancel;
/*     */   private boolean customListener = false;
/*     */   
/*     */   public TCGDialog(ResourceManager resourceManager, String title, String message, Options options, String[] buttonKeys, boolean modal, Localizer localizer) {
/*  30 */     super(resourceManager);
/*  31 */     this.localizer = localizer;
/*     */     
/*  33 */     configureStyle(getStyleSheet());
/*  34 */     Dimension preferredSize = getPreferredSize(-1, -1);
/*  35 */     setSize(440, 677);
/*     */     
/*  37 */     setTitle(title);
/*  38 */     BContainer clientArea = getClientArea();
/*  39 */     clientArea.setLayoutManager((BLayoutManager)new BorderLayout());
/*  40 */     this.messageLabel = new BLabel(message, "messagelabel");
/*  41 */     clientArea.add((BComponent)this.messageLabel, BorderLayout.CENTER);
/*  42 */     setModal(modal);
/*     */     
/*  44 */     setLayer(101);
/*     */     
/*  46 */     CloseHandler listener = new CloseHandler();
/*  47 */     if (options == Options.OK) {
/*  48 */       this.butOk = new BButton(localizer.getLocalizedText(getClass(), buttonKeys[0], new String[0]));
/*  49 */       this.butOk.addListener((ComponentListener)listener);
/*  50 */       clientArea.add((BComponent)this.butOk, BorderLayout.SOUTH);
/*  51 */     } else if (options == Options.OK_CANCEL) {
/*  52 */       BContainer buttonContainer = new BContainer((BLayoutManager)new BorderLayout());
/*     */       
/*  54 */       this.butOk = new BButton(localizer.getLocalizedText(getClass(), buttonKeys[0], new String[0]));
/*  55 */       this.butOk.addListener((ComponentListener)listener);
/*  56 */       buttonContainer.add((BComponent)this.butOk, BorderLayout.WEST);
/*     */       
/*  58 */       this.butCancel = new BButton(localizer.getLocalizedText(getClass(), buttonKeys[1], new String[0]));
/*  59 */       this.butCancel.addListener((ComponentListener)listener);
/*  60 */       buttonContainer.add((BComponent)this.butCancel, BorderLayout.EAST);
/*     */       
/*  62 */       clientArea.add((BComponent)buttonContainer, BorderLayout.SOUTH);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBounds(int x, int y, int width, int height) {
/*  68 */     super.setBounds(x, y, width, height);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configureStyle(BStyleSheet style) {
/*  73 */     super.configureStyle(style);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void layout() {
/*  78 */     super.layout();
/*     */   }
/*     */ 
/*     */   
/*     */   public void validate() {
/*  83 */     super.validate();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void wasAdded() {
/*  88 */     super.wasAdded();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDefaultStyleClass() {
/*  93 */     return "tcgdialog";
/*     */   }
/*     */   
/*     */   public void setMessage(String message) {
/*  97 */     this.messageLabel.setText(message);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void wasRemoved() {
/* 102 */     super.wasRemoved();
/*     */     
/* 104 */     if (this.customListener)
/* 105 */       this.butOk.dispatchEvent((BEvent)new ActionEvent(this, 0L, 0, "default")); 
/*     */   }
/*     */   
/*     */   public void addOptionListener(Options options, ComponentListener listener) {
/* 109 */     if (options != Options.NONE) {
/* 110 */       this.butOk.addListener(listener);
/* 111 */       if (options == Options.OK)
/* 112 */         this.customListener = true; 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static TCGDialog showMessage(String titleKey, String textKey, Localizer localizer, ActionListener closeListener) {
/* 117 */     return showMessage(titleKey, textKey, localizer, Options.OK, closeListener);
/*     */   }
/*     */   
/*     */   public static TCGDialog showMessage(String titleKey, String textKey, Localizer localizer, Options options, ActionListener closeListener) {
/* 121 */     return showMessage(titleKey, textKey, localizer, options, (String[])null, closeListener);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static TCGDialog showMessage(String titleKey, String textKey, Localizer localizer, Options options, String[] buttonKeys, ActionListener closeListener) {
/* 127 */     if (buttonKeys == null) {
/* 128 */       switch (options) {
/*     */ 
/*     */         
/*     */         case OK:
/* 132 */           buttonKeys = new String[] { "common.ok.text" };
/*     */           break;
/*     */         case OK_CANCEL:
/* 135 */           buttonKeys = new String[] { "common.ok.text", "common.cancel.text" };
/*     */           break;
/*     */       } 
/*     */     
/*     */     }
/* 140 */     TCGDialog dialog = new TCGDialog(TcgGame.getResourceManager(), TcgGame.getLocalizedText(titleKey, new String[0]), TcgGame.getLocalizedText(textKey, new String[0]), options, buttonKeys, true, localizer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     if (closeListener != null) {
/* 147 */       dialog.addOptionListener(options, (ComponentListener)closeListener);
/*     */     }
/* 149 */     PanelManager.getInstance().addWindow(dialog);
/*     */     
/* 151 */     return dialog;
/*     */   }
/*     */   
/*     */   public enum Options {
/* 155 */     NONE, OK, OK_CANCEL; }
/*     */   
/*     */   private class CloseHandler implements ActionListener {
/*     */     private CloseHandler() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent event) {
/* 161 */       TCGDialog.this.customListener = false;
/* 162 */       PanelManager.getInstance().closeWindow(TCGDialog.this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\TCGDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */