/*     */ package com.funcom.tcg.client.ui.duel;
/*     */ 
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.net.message.DuelResponseMessage;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ 
/*     */ public class DuelAcceptWindow
/*     */   extends BWindow
/*     */ {
/*     */   public static final int WINDOW_WIDTH = 400;
/*     */   public static final int WINDOW_HEIGHT = 425;
/*     */   private static final int buttonWidth = 160;
/*     */   private static final int buttonHeight = 54;
/*     */   private static final int duelWidth = 364;
/*     */   private static final int labelWidth = 330;
/*  31 */   public static final Rectangle LABEL_CONSTRAINTS = new Rectangle(35, 260, 330, 150);
/*     */   
/*  33 */   public static final Rectangle OK_BUTTON_CONSTRAINTS = new Rectangle(35, 200, 160, 54);
/*     */   
/*  35 */   public static final Rectangle CANCEL_BUTTON_CONSTRAINTS = new Rectangle(205, 200, 160, 54);
/*     */   
/*  37 */   public static final Rectangle DUEL_LABEL_CONSTRAINTS = new Rectangle(18, 19, 364, 170);
/*     */   
/*     */   private BButton cancelButton;
/*     */   
/*     */   private BButton okButton;
/*     */   
/*     */   private BLabel messageLabel;
/*  44 */   private int opponentId = 0; private int duelId = 0;
/*     */   private DuelAcceptWindow INSTANCE;
/*     */   
/*     */   public DuelAcceptWindow(int opponentId, int duelId) {
/*  48 */     super(DuelAcceptWindow.class.getSimpleName(), null, (BLayoutManager)new AbsoluteLayout());
/*  49 */     this.INSTANCE = this;
/*     */     
/*  51 */     this.opponentId = opponentId;
/*  52 */     this.duelId = duelId;
/*     */     
/*  54 */     this._style = BuiUtils.createMergedClassStyleSheets(DuelAcceptWindow.class, new BananaResourceProvider(TcgGame.getResourceManager()));
/*  55 */     setSize(400, 425);
/*  56 */     setLocation(DisplaySystem.getDisplaySystem().getWidth() / 2 - 200, DisplaySystem.getDisplaySystem().getHeight() / 2 - 212);
/*     */ 
/*     */     
/*  59 */     initComponents();
/*  60 */     initListeners();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initComponents() {
/*  66 */     BLabel duelLabel = new BLabel("");
/*  67 */     duelLabel.setStyleClass("duel.label");
/*     */     
/*  69 */     String okText = TcgGame.getLocalizedText("duelwindow.accept", new String[0]), cancelText = TcgGame.getLocalizedText("duelwindow.decline", new String[0]);
/*     */     
/*  71 */     String oppponentName = (TcgGame.getPropNodeRegister().getPropNode(Integer.valueOf(this.opponentId)) != null) ? TcgGame.getPropNodeRegister().getPropNode(Integer.valueOf(this.opponentId)).toString() : "Opponent";
/*     */     
/*  73 */     String messageText = oppponentName + " " + TcgGame.getLocalizedText("duelwindow.challenge", new String[0]);
/*     */     
/*  75 */     this.messageLabel = new BLabel(messageText);
/*  76 */     this.messageLabel.setStyleClass("message.label");
/*     */     
/*  78 */     this.okButton = new BButton(okText);
/*  79 */     this.okButton.setStyleClass("button");
/*     */     
/*  81 */     this.cancelButton = new BButton(cancelText);
/*  82 */     this.cancelButton.setStyleClass("button");
/*     */     
/*  84 */     add((BComponent)duelLabel, DUEL_LABEL_CONSTRAINTS);
/*  85 */     add((BComponent)this.messageLabel, LABEL_CONSTRAINTS);
/*  86 */     add((BComponent)this.okButton, OK_BUTTON_CONSTRAINTS);
/*  87 */     add((BComponent)this.cancelButton, CANCEL_BUTTON_CONSTRAINTS);
/*     */   }
/*     */   
/*     */   private void initListeners() {
/*  91 */     this.okButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/*     */             try {
/*  95 */               NetworkHandler.instance().getIOHandler().send((Message)new DuelResponseMessage(DuelAcceptWindow.this.duelId, (short)0));
/*  96 */             } catch (InterruptedException e) {
/*  97 */               e.printStackTrace();
/*     */             } 
/*  99 */             BuiSystem.getRootNode().removeWindow(DuelAcceptWindow.this.INSTANCE);
/*     */           }
/*     */         });
/* 102 */     this.cancelButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/*     */             try {
/* 106 */               NetworkHandler.instance().getIOHandler().send((Message)new DuelResponseMessage(DuelAcceptWindow.this.duelId, (short)1));
/* 107 */             } catch (InterruptedException e) {
/* 108 */               e.printStackTrace();
/*     */             } 
/* 110 */             BuiSystem.getRootNode().removeWindow(DuelAcceptWindow.this.INSTANCE);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\duel\DuelAcceptWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */