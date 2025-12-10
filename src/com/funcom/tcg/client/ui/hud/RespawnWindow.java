/*     */ package com.funcom.tcg.client.ui.hud;
/*     */ import com.funcom.commons.utils.GlobalTime;
/*     */ import com.funcom.gameengine.Updated;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*     */ import com.funcom.tcg.net.message.RespawnMessage;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BButton2;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BLabel2;
import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ 
/*     */ public class RespawnWindow extends BWindow implements Updated {
/*  26 */   private final int WINDOW_WIDTH = 380;
/*  27 */   private final int WINDOW_HEIGHT = 270;
/*     */   private BLabel label;
/*     */   private BButton2 butLife;
/*     */   private BButton2 butSafeZone;
/*  31 */   private long toActivate = 0L;
/*     */   
/*     */   public RespawnWindow() {
/*  34 */     super(TcgGame.getLocalizedText("respawnwindow.title", new String[0]), BuiUtils.createMergedClassStyleSheets(RespawnWindow.class, new BananaResourceProvider(TcgGame.getResourceManager())), (BLayoutManager)new AbsoluteLayout());
/*     */ 
/*     */ 
/*     */     
/*  38 */     if (TcgGame.isStartDuelMode()) {
/*  39 */       TcgGame.setStartDuelMode(false);
/*     */     }
/*     */     
/*  42 */     setStyleClass("respawnwindow");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  47 */     setSize(DisplaySystem.getDisplaySystem().getWidth(), DisplaySystem.getDisplaySystem().getHeight());
/*  48 */     setLocation(0, 0);
/*     */     
/*  50 */     layoutComponents();
/*     */   }
/*     */   
/*     */   public void refresh() {
/*  54 */     this.label.setText(TcgGame.getLocalizedText("respawnwindow.text", new String[0]));
/*  55 */     String lifeSpawnButtonText = TcgGame.getLocalizedText("respawnwindow.liferespawnbutton.title", new String[0]);
/*  56 */     Integer levelSum = MainGameState.getPlayerModel().getStatSum(Short.valueOf((short)20));
/*  57 */     int cost = Math.round((levelSum.intValue() * levelSum.intValue()));
/*  58 */     String costString = "" + cost;
/*  59 */     lifeSpawnButtonText = lifeSpawnButtonText.replace("[number]", costString);
/*  60 */     this.butLife.setText(lifeSpawnButtonText);
/*  61 */     this.butLife.setEnabled(false);
/*  62 */     this.butSafeZone.setEnabled(false);
/*  63 */     this.toActivate = GlobalTime.getInstance().getCurrentTime() + 3000L;
/*     */   }
/*     */   
/*     */   private void enable() {
/*  67 */     Integer levelSum = MainGameState.getPlayerModel().getStatSum(Short.valueOf((short)20));
/*  68 */     int cost = Math.round((levelSum.intValue() * levelSum.intValue()));
/*  69 */     boolean hasCoins = false;
/*  70 */     for (InventoryItem item : MainGameState.getPlayerModel().getInventory()) {
/*  71 */       if (item != null && item.getClassId().equals("coin")) {
/*  72 */         if (item.getAmount() >= cost) {
/*  73 */           hasCoins = true;
/*     */         }
/*     */         break;
/*     */       } 
/*     */     } 
/*  78 */     this.butLife.setEnabled(hasCoins);
/*  79 */     this.butSafeZone.setEnabled(true);
/*     */   }
/*     */   
/*     */   private void layoutComponents() {
/*  83 */     BContainer container = new BContainer((BLayoutManager)new AbsoluteLayout());
/*  84 */     container.setStyleClass("respawnwindow.innerwindow");
/*     */ 
/*     */     
/*  87 */     add((BComponent)container, new Rectangle(DisplaySystem.getDisplaySystem().getWidth() / 2 - 190, DisplaySystem.getDisplaySystem().getHeight() / 2 - 135, 380, 270));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     initializeChoiceButtons(container);
/*     */   }
/*     */   
/*     */   private void initializeChoiceButtons(BContainer container) {
/* 111 */     int butWidth = 80;
/* 112 */     int butHeight = 80;
/* 113 */     int labelHeight = 75;
/*     */     
/* 115 */     this.label = (BLabel)new BLabel2(TcgGame.getLocalizedText("respawnwindow.text", new String[0]));
/* 116 */     this.label.setStyleClass("respawnwindow.label");
/* 117 */     this.label.setFit(BLabel.Fit.WRAP);
/*     */     
/* 119 */     container.add((BComponent)this.label, new Rectangle(0, 195, 380, 75));
/*     */ 
/*     */ 
/*     */     
/* 123 */     this.butLife = new BButton2("");
/* 124 */     this.butLife.setStyleClass("button.life");
/* 125 */     this.butLife.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/*     */             try {
/* 129 */               NetworkHandler.instance().getIOHandler().send((Message)new RespawnMessage(RespawnMessage.RespawnType.IN_PLACE_WITH_LIFE));
/* 130 */             } catch (InterruptedException e) {
/* 131 */               throw new RuntimeException(e);
/*     */             } 
/*     */           }
/*     */         });
/* 135 */     container.add((BComponent)this.butLife, new Rectangle(40, 110, 300, 80));
/*     */ 
/*     */ 
/*     */     
/* 139 */     this.butSafeZone = new BButton2(TcgGame.getLocalizedText("respawnwindow.safezonerespawnbutton.title", new String[0]));
/* 140 */     this.butSafeZone.setStyleClass("button");
/* 141 */     container.add((BComponent)this.butSafeZone, new Rectangle(40, 25, 300, 80));
/* 142 */     this.butSafeZone.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 145 */             TcgUI.getUISoundPlayer().play("ClickForward");
/*     */             try {
/* 147 */               NetworkHandler.instance().getIOHandler().send((Message)new RespawnMessage(RespawnMessage.RespawnType.SAFE_ZONE));
/* 148 */             } catch (InterruptedException e) {
/* 149 */               throw new RuntimeException(e);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(float time) {
/* 157 */     if (this.toActivate != 0L && GlobalTime.getInstance().getCurrentTime() > this.toActivate) {
/* 158 */       this.toActivate = 0L;
/* 159 */       enable();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud\RespawnWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */