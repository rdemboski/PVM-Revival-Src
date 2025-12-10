/*     */ package com.funcom.tcg.client.ui.pause;
/*     */ 
/*     */ import com.funcom.gameengine.Updated;
/*     */ import com.funcom.gameengine.view.DfxTextWindowManager;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.net.message.PauseStatusMessage;
/*     */ import com.funcom.tcg.token.TCGWorld;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class PauseModelImpl implements PauseModel, Updated {
/*  19 */   private Logger LOGGER = Logger.getLogger(PauseModelImpl.class);
/*     */   
/*     */   private boolean paused;
/*     */   private boolean instant;
/*     */   private List<PauseModel.ChangeListener> changeListeners;
/*     */   
/*     */   public PauseModelImpl() {
/*  26 */     this.changeListeners = new ArrayList<PauseModel.ChangeListener>();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addChangeListener(PauseModel.ChangeListener changeListener) {
/*  31 */     this.changeListeners.add(changeListener);
/*     */   }
/*     */   
/*     */   private void fireProgressBarUpdate() {
/*  35 */     for (PauseModel.ChangeListener changeListener : this.changeListeners) {
/*  36 */       if (this.instant) {
/*  37 */         fireProgressBarComplete(); continue;
/*     */       } 
/*  39 */       changeListener.progressBarUpdate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void fireProgressBarComplete() {
/*  45 */     for (PauseModel.ChangeListener changeListener : this.changeListeners) {
/*  46 */       changeListener.completeProgressBar();
/*     */     }
/*     */   }
/*     */   
/*     */   public void fireClose() {
/*  51 */     Iterator<PauseModel.ChangeListener> listenerIterator = this.changeListeners.iterator();
/*  52 */     while (listenerIterator.hasNext()) {
/*  53 */       ((PauseModel.ChangeListener)listenerIterator.next()).close();
/*  54 */       listenerIterator.remove();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPaused() {
/*  60 */     return this.paused;
/*     */   }
/*     */ 
/*     */   
/*     */   public void pauseRejected() {
/*  65 */     reset();
/*  66 */     DfxTextWindowManager.instance().getWindow("pause").showText(getPauseRejectedText());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPauseText() {
/*  71 */     return TcgGame.getLocalizedText("pauseWindow.pausing", new String[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPauseCompleteText() {
/*  77 */     return TcgGame.getLocalizedText("pauseWindow.complete", new String[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPauseRejectedText() {
/*  82 */     return TcgGame.getLocalizedText("pauseWindow.rejected", new String[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmPause() {
/*  87 */     fireProgressBarComplete();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/*  92 */     if (!TcgGame.isPetTutorial() && !TcgGame.isEquipmentTutorial()) {
/*  93 */       this.paused = false;
/*  94 */       this.instant = false;
/*  95 */       updateServer();
/*  96 */       fireClose();
/*  97 */       MainGameState.getMainHud().getPauseButton().setSelected(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void activatePause() {
/* 103 */     MainGameState.getMainHud().getPauseButton().setSelected(isPauseAllowed());
/* 104 */     if (isPauseAllowed()) {
/* 105 */       this.paused = true;
/* 106 */       this.instant = false;
/* 107 */       updateServer();
/* 108 */       PauseWindow pauseWindow = new PauseWindow(TcgGame.getResourceManager(), this, this.instant);
/* 109 */       BuiSystem.addWindow((BWindow)pauseWindow);
/*     */     } else {
/* 111 */       pauseRejected();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void instantPause() {
/* 117 */     MainGameState.getMainHud().getPauseButton().setSelected(isPauseAllowed());
/* 118 */     if (isPauseAllowed()) {
/* 119 */       this.paused = true;
/* 120 */       this.instant = true;
/* 121 */       updateServer();
/* 122 */       PauseWindow pauseWindow = new PauseWindow(TcgGame.getResourceManager(), this, this.instant);
/* 123 */       BuiSystem.addWindow((BWindow)pauseWindow);
/*     */     } else {
/*     */       
/* 126 */       pauseRejected();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isPauseAllowed() {
/* 132 */     return (((TCGWorld)MainGameState.getWorld()).getChunkWorldNode().getChunkWorldInfo().isMapPausable() && !TcgGame.isDueling());
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(float time) {
/* 137 */     fireProgressBarUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateServer() {
/*     */     try {
/* 143 */       NetworkHandler.instance().getIOHandler().send((Message)new PauseStatusMessage(this.paused));
/* 144 */     } catch (InterruptedException e) {
/* 145 */       this.LOGGER.error("Failed to send PauseStatusMessage!", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\pause\PauseModelImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */