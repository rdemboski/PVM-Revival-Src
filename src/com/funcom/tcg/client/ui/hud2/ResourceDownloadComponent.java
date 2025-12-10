/*     */ package com.funcom.tcg.client.ui.hud2;
/*     */ 
/*     */ import com.funcom.gameengine.Updated;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceDownloader;
/*     */ import com.funcom.gameengine.resourcemanager.downloader.ResourceDownloaderListener;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BPartialProgressBar;
/*     */ import com.jmex.bui.BProgressBar;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class ResourceDownloadComponent
/*     */   extends BContainer
/*     */   implements ResourceDownloaderListener, Updated {
/*     */   private boolean downloading = false;
/*  22 */   private float progress = 0.0F;
/*     */   private static final String STYLE_BAR1 = "bar1";
/*     */   private static final String STYLE_BAR2 = "bar2";
/*     */   private BPartialProgressBar bar1;
/*     */   private BPartialProgressBar bar2;
/*     */   private BLabel percentLabel;
/*     */   private NumberFormat numberFormat;
/*     */   
/*     */   public ResourceDownloadComponent() {
/*  31 */     super(null, (BLayoutManager)new AbsoluteLayout());
/*     */     
/*  33 */     this.bar1 = new BPartialProgressBar(BProgressBar.Direction.PROGRESSDIR_SOUTH);
/*  34 */     this.bar1.setStyleClass("bar1");
/*  35 */     this.bar1.setProgress(0.0F);
/*  36 */     this.bar1.setSecondProgress(0.0F);
/*  37 */     add((BComponent)this.bar1, new Rectangle(0, 0, 12, 24));
/*     */     
/*  39 */     this.bar2 = new BPartialProgressBar(BProgressBar.Direction.PROGRESSDIR_NORTH);
/*  40 */     this.bar2.setStyleClass("bar2");
/*  41 */     this.bar2.setProgress(0.0F);
/*  42 */     this.bar2.setSecondProgress(0.0F);
/*  43 */     add((BComponent)this.bar2, new Rectangle(12, 0, 12, 24));
/*     */     
/*  45 */     this.percentLabel = new BLabel("test");
/*  46 */     this.percentLabel.setStyleClass("label.percent");
/*  47 */     this.percentLabel.setSize(64, 24);
/*  48 */     add((BComponent)this.percentLabel, new Rectangle(24, 0, 64, 24));
/*     */     
/*  50 */     setSize(64, 24);
/*  51 */     setVisible(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startedDownloading(String fileName) {
/*  56 */     this.downloading = true;
/*  57 */     setVisible(this.downloading);
/*     */   }
/*     */ 
/*     */   
/*     */   public void finishedDownloading() {
/*  62 */     this.downloading = false;
/*  63 */     setVisible(this.downloading);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/*  68 */     super.setVisible(visible);
/*  69 */     if (getParent() != null) {
/*  70 */       getParent().invalidate();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startedVerifying(String fileName) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void finishedVerifying() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void wasAdded() {
/*  86 */     super.wasAdded();
/*  87 */     ResourceDownloader downloader = TcgGame.getResourceDownloader();
/*  88 */     if (downloader != null) {
/*  89 */       downloader.addListener(this);
/*  90 */       this.numberFormat = (NumberFormat)NumberFormat.getInstance(Locale.US).clone();
/*  91 */       this.numberFormat.setMaximumFractionDigits(2);
/*  92 */       this.numberFormat.setMaximumIntegerDigits(3);
/*     */       
/*  94 */       this.downloading = (downloader.getFileDownloadLength() > 0);
/*  95 */       setVisible(this.downloading);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void wasRemoved() {
/* 101 */     super.wasRemoved();
/* 102 */     this.downloading = false;
/*     */     
/* 104 */     if (TcgGame.getResourceDownloader() != null) {
/* 105 */       TcgGame.getResourceDownloader().removeListener(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(float time) {
/* 111 */     if (!this.downloading)
/*     */       return; 
/* 113 */     ResourceDownloader downloader = TcgGame.getResourceDownloader();
/* 114 */     float downloadPercent = downloader.getCurrentFileDownloadSize() / downloader.getFileDownloadLength() * 1.0F;
/* 115 */     this.percentLabel.setText(this.numberFormat.format((downloadPercent * 100.0F)) + "%");
/* 116 */     this.percentLabel.validate();
/*     */     
/* 118 */     float percentShown = 0.2F;
/* 119 */     this.progress += time * 2.0F;
/* 120 */     if (this.progress > 1.0F) {
/* 121 */       this.progress %= 1.0F;
/*     */     }
/* 123 */     this.progress = Math.max(0.0F, this.progress);
/*     */     
/* 125 */     if (this.progress > 0.5F) {
/* 126 */       if (this.progress - percentShown < 0.5F) {
/* 127 */         this.bar1.setProgress(1.0F);
/* 128 */         this.bar1.setSecondProgress(Math.min(1.0F, (this.progress - percentShown) * 2.0F));
/*     */       } else {
/* 130 */         this.bar1.setProgress(0.0F);
/* 131 */         this.bar1.setSecondProgress(0.0F);
/*     */       } 
/* 133 */       this.bar2.setProgress(Math.max(0.0F, this.progress - 0.5F) * 2.0F);
/* 134 */       this.bar2.setSecondProgress(Math.max(0.0F, this.progress - percentShown - 0.5F) * 2.0F);
/*     */     } else {
/* 136 */       if (this.progress - percentShown < 0.0F) {
/* 137 */         this.bar2.setProgress(1.0F);
/* 138 */         this.bar2.setSecondProgress((1.0F + this.progress - percentShown - 0.5F) * 2.0F);
/*     */       } else {
/* 140 */         this.bar2.setProgress(0.0F);
/* 141 */         this.bar2.setSecondProgress(0.0F);
/*     */       } 
/* 143 */       this.bar1.setProgress(Math.min(1.0F, this.progress * 2.0F));
/* 144 */       this.bar1.setSecondProgress(Math.max(0.0F, (this.progress - percentShown) * 2.0F));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\ResourceDownloadComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */