/*     */ package com.funcom.tcg.client.ui.achievements;
/*     */ 
/*     */ import com.funcom.peeler.BananaPeel;
/*     */ import com.funcom.rpgengine2.quests.reward.QuestCategory;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.ui.BPeelContainer;
/*     */ import com.funcom.tcg.client.ui.vendor.TcgGridLayout;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BClickthroughLabel;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BProgressBar;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ public class AchievementSummary
/*     */   extends BPeelContainer
/*     */ {
/*     */   private int[] amounts;
/*     */   private final int[] maxes;
/*     */   private BLabel[] bgds;
/*     */   private BLabel[] totals;
/*     */   private BProgressBar[] progressBars;
/*     */   private BButton[] glossButtons;
/*     */   private List<AchievementPlaque> latestPlaques;
/*     */   private BContainer latestContainer;
/*     */   
/*     */   public AchievementSummary(BananaPeel bananaPeel, int[] maxes) {
/*  33 */     super("", bananaPeel);
/*     */     
/*  35 */     this.amounts = new int[(QuestCategory.values()).length];
/*  36 */     this.latestPlaques = new ArrayList<AchievementPlaque>();
/*  37 */     this.maxes = maxes;
/*     */     
/*  39 */     initComponents();
/*  40 */     initListeners();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initComponents() {
/*  46 */     BLabel latest = new BLabel(TcgGame.getLocalizedText("achievements.window.title.most.recent", new String[0]));
/*  47 */     BComponent placeholder = findComponent(getMainContainer(), "newest_title");
/*  48 */     overridePeelerComponent((BComponent)latest, placeholder);
/*     */     
/*  50 */     this.latestContainer = new BContainer((BLayoutManager)new TcgGridLayout(1, 6, 0, 0));
/*  51 */     placeholder = findComponent(getMainContainer(), "latest_container");
/*  52 */     overridePeelerComponent((BComponent)this.latestContainer, placeholder);
/*     */     
/*  54 */     this.bgds = new BLabel[(QuestCategory.values()).length];
/*  55 */     this.progressBars = new BProgressBar[(QuestCategory.values()).length];
/*  56 */     this.glossButtons = new BButton[(QuestCategory.values()).length];
/*  57 */     this.totals = (BLabel[])new BClickthroughLabel[(QuestCategory.values()).length];
/*     */     
/*  59 */     for (int i = 0; i < (QuestCategory.values()).length; i++) {
/*  60 */       String categoryName = QuestCategory.values()[i].toString().toLowerCase();
/*  61 */       if (categoryName.equals(QuestCategory.NONE.toString().toLowerCase())) {
/*  62 */         categoryName = "summary";
/*     */       }
/*  64 */       this.bgds[i] = new BLabel("");
/*  65 */       placeholder = findComponent(getMainContainer(), categoryName + "_progress_bgd");
/*  66 */       overridePeelerComponent((BComponent)this.bgds[i], placeholder);
/*     */       
/*  68 */       this.progressBars[i] = new BProgressBar();
/*  69 */       placeholder = findComponent(getMainContainer(), categoryName + "_progress");
/*  70 */       overridePeelerComponent((BComponent)this.progressBars[i], placeholder);
/*  71 */       this.progressBars[i].setProgress(0.0F);
/*  72 */       this.progressBars[i].setVisible(false);
/*     */       
/*  74 */       this.glossButtons[i] = new BButton(TcgGame.getLocalizedText("achievements.window.category." + categoryName, new String[0]));
/*  75 */       placeholder = findComponent(getMainContainer(), categoryName + "_progress_glow");
/*  76 */       overridePeelerComponent((BComponent)this.glossButtons[i], placeholder);
/*  77 */       this.glossButtons[i].addListener((ComponentListener)new AchievementViewListener(QuestCategory.values()[i]));
/*  78 */       if (i > 0) this.glossButtons[i].setTooltipText(TcgGame.getLocalizedText("tooltips.achievements.category.view", new String[0]));
/*     */       
/*  80 */       this.totals[i] = (BLabel)new BClickthroughLabel("0/" + this.maxes[i]);
/*  81 */       placeholder = findComponent(getMainContainer(), categoryName + "_progress_total");
/*  82 */       overridePeelerComponent((BComponent)this.totals[i], placeholder);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initListeners() {}
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/*  92 */     super.setVisible(visible);
/*  93 */     getMainContainer().setVisible(visible);
/*  94 */     for (BProgressBar progressBar : this.progressBars) {
/*  95 */       progressBar.setVisible((progressBar.getProgress() > 0.0F));
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateLatest(AchievementPlaque plaque) {
/* 100 */     if (this.latestContainer.getComponentCount() > 2) {
/* 101 */       this.latestContainer.remove(this.latestContainer.getComponentCount() - 1);
/* 102 */       this.latestPlaques.remove(this.latestContainer.getComponentCount() - 1);
/*     */     } 
/* 104 */     this.latestContainer.add(0, (BComponent)plaque.getMainContainer());
/* 105 */     this.latestPlaques.add(0, plaque);
/*     */   }
/*     */   
/*     */   public void incrementAmount(int category) {
/* 109 */     this.amounts[QuestCategory.NONE.getType()] = this.amounts[QuestCategory.NONE.getType()] + 1;
/* 110 */     this.amounts[category] = this.amounts[category] + 1;
/*     */   }
/*     */   
/*     */   public void updateProgress(QuestCategory category) {
/* 114 */     incrementAmount(category.getType());
/*     */     
/* 116 */     float progress = this.amounts[category.getType()] / this.maxes[category.getType()];
/* 117 */     progress = (progress > 1.0F) ? 1.0F : ((progress < 0.0F) ? 0.0F : progress);
/* 118 */     this.progressBars[category.getType()].setVisible((progress > 0.0F));
/* 119 */     this.progressBars[category.getType()].setProgress(progress);
/* 120 */     this.totals[category.getType()].setText(this.amounts[category.getType()] + "/" + this.maxes[category.getType()]);
/*     */ 
/*     */     
/* 123 */     progress = this.amounts[QuestCategory.NONE.getType()] / this.maxes[QuestCategory.NONE.getType()];
/* 124 */     this.progressBars[QuestCategory.NONE.getType()].setProgress(progress);
/* 125 */     this.progressBars[QuestCategory.NONE.getType()].setVisible((progress > 0.0F));
/* 126 */     this.totals[QuestCategory.NONE.getType()].setText(this.amounts[QuestCategory.NONE.getType()] + "/" + this.maxes[QuestCategory.NONE.getType()]);
/*     */   }
/*     */   
/*     */   public List<AchievementPlaque> getLatestPlaques() {
/* 130 */     return this.latestPlaques;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\achievements\AchievementSummary.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */