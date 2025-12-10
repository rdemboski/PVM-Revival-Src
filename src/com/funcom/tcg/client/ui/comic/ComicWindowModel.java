/*     */ package com.funcom.tcg.client.ui.comic;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.tcg.client.metrics.HttpMetrics;
/*     */ import com.funcom.tcg.client.ui.startmenu.StartMenuWizard;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.icon.BIcon;
/*     */ import com.jmex.bui.icon.BlankIcon;
/*     */ import com.jmex.bui.icon.ImageIcon;
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ComicWindowModel
/*     */ {
/*     */   public static final String COMICS_PROPERTIES = "configuration/comics/comics.properties";
/*     */   private BLabel comicLabel;
/*     */   private BLabel comicTextLabel;
/*  26 */   private int currentPage = 0;
/*  27 */   private List<String> comics = new ArrayList<String>();
/*  28 */   private List<Boolean> comicTextsExist = new ArrayList<Boolean>();
/*     */   private ResourceManager resourceManager;
/*     */   private StartMenuWizard startMenuWizard;
/*     */   
/*     */   public ComicWindowModel(ResourceManager resourceManager, StartMenuWizard startMenuWizard) {
/*  33 */     this.resourceManager = resourceManager;
/*  34 */     this.startMenuWizard = startMenuWizard;
/*  35 */     Properties properties = new Properties();
/*     */     
/*  37 */     StringReader stringReader = new StringReader((String)resourceManager.getResource(String.class, "configuration/comics/comics.properties", CacheType.NOT_CACHED));
/*     */     
/*     */     try {
/*  40 */       properties.load(stringReader);
/*  41 */     } catch (IOException e) {
/*  42 */       throw new RuntimeException(e);
/*     */     } 
/*  44 */     String comicsList = properties.getProperty("list.default");
/*  45 */     String[] comicsFiles = comicsList.split(",");
/*  46 */     for (String file : comicsFiles) {
/*  47 */       this.comics.add(file);
/*  48 */       String textFile = file.replace("artwork.jpg", "lettering_" + Locale.getDefault().getLanguage() + ".png");
/*  49 */       this.comicTextsExist.add(Boolean.valueOf(resourceManager.exists(textFile)));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reset() {
/*  54 */     this.currentPage = 0;
/*  55 */     showNewPage();
/*     */   }
/*     */   
/*     */   public void setComicLabel(BLabel comicLabel) {
/*  59 */     this.comicLabel = comicLabel;
/*  60 */     showNewPage();
/*     */   }
/*     */   
/*     */   public int getCurrentPage() {
/*  64 */     return this.currentPage + 1;
/*     */   }
/*     */   
/*     */   public int getTotalPages() {
/*  68 */     return this.comics.size();
/*     */   }
/*     */   
/*     */   public void prev() {
/*  72 */     if (this.currentPage == 0) {
/*  73 */       this.startMenuWizard.comicBack();
/*     */       return;
/*     */     } 
/*  76 */     this.currentPage--;
/*  77 */     showNewPage();
/*     */   }
/*     */ 
/*     */   
/*     */   public void next() {
/*  82 */     if (this.currentPage == getTotalPages() - 1) {
/*  83 */       finishedComic();
/*     */       
/*     */       return;
/*     */     } 
/*  87 */     this.currentPage++;
/*  88 */     showNewPage();
/*     */   }
/*     */ 
/*     */   
/*     */   public void goToPage(int pageNumber) {
/*  93 */     if (pageNumber >= 0 && pageNumber < getTotalPages()) {
/*     */       
/*  95 */       this.currentPage = pageNumber;
/*  96 */       showNewPage();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void skip() {
/* 101 */     HttpMetrics.postEvent(HttpMetrics.Event.SKIP_COMIC);
/* 102 */     finishedComic();
/*     */   }
/*     */   
/*     */   private void finishedComic() {
/* 106 */     this.startMenuWizard.showCharacterCreation();
/*     */   }
/*     */   
/*     */   private void showNewPage() {
/* 110 */     String file = this.comics.get(this.currentPage);
/* 111 */     ImageIcon imageIcon = new ImageIcon((BImage)this.resourceManager.getResource(BImage.class, file));
/* 112 */     this.comicLabel.setIcon((BIcon)imageIcon);
/* 113 */     if (((Boolean)this.comicTextsExist.get(this.currentPage)).booleanValue()) {
/* 114 */       String textFile = file.replace("artwork.jpg", "lettering_" + Locale.getDefault().getLanguage() + ".png");
/* 115 */       BImage text = (BImage)this.resourceManager.getResource(BImage.class, textFile);
/* 116 */       this.comicTextLabel.setIcon((BIcon)new ImageIcon(text));
/*     */     } else {
/* 118 */       this.comicTextLabel.setIcon((BIcon)new BlankIcon(1, 1));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setComicTextLabel(BLabel comicTextLabel) {
/* 123 */     this.comicTextLabel = comicTextLabel;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\comic\ComicWindowModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */