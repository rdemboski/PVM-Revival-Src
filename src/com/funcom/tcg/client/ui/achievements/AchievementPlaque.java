/*     */ package com.funcom.tcg.client.ui.achievements;
/*     */ 
/*     */ import com.funcom.commons.jme.bui.HighlightedToggleButton;
/*     */ import com.funcom.commons.localization.JavaLocalization;
/*     */ import com.funcom.gameengine.resourcemanager.NoLocatorException;
/*     */ import com.funcom.peeler.BananaPeel;
/*     */ import com.funcom.rpgengine2.SkillId;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.quests.reward.QuestRewardData;
/*     */ import com.funcom.rpgengine2.quests.reward.QuestRewardType;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientObjectiveTracker;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPetDescription;
/*     */ import com.funcom.tcg.client.model.rpg.ClientQuestData;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.BPeelContainer;
/*     */ import com.funcom.tcg.net.message.ClaimQuestRewardsMessage;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BClickthroughLabel;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BProgressBar;
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import com.jmex.bui.background.ImageBackground;
/*     */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ 
/*     */ public class AchievementPlaque
/*     */   extends BPeelContainer {
/*     */   private ClientQuestData questData;
/*     */   private BButton achievementButton;
/*     */   private BProgressBar achievementProgress;
/*     */   private int maxAmount;
/*     */   private BClickthroughLabel achievementTitle;
/*     */   private BClickthroughLabel progressGlossLabel;
/*     */   private BClickthroughLabel progressBgdLabel;
/*     */   private BClickthroughLabel achievementIcon;
/*     */   private BClickthroughLabel achievementIconBgd;
/*     */   private BClickthroughLabel rewardIcon;
/*     */   private BClickthroughLabel rewardTrophy;
/*     */   private HighlightedToggleButton rewardIconBgd;
/*     */   private BClickthroughLabel[] objectiveLabels;
/*     */   private HighlightedToggleButton expandedToggle;
/*     */   private boolean expanded = false;
/*     */   private boolean hasReward = false;
/*     */   private final int expandedHeight;
/*  55 */   private final int originalHeight = 89;
/*     */   
/*     */   private BContainer parent;
/*     */   private BClickthroughLabel achievementDescription;
/*     */   
/*     */   public AchievementPlaque() {
/*  61 */     super("", null);
/*  62 */     this.expandedHeight = 200;
/*     */   }
/*     */   
/*     */   public AchievementPlaque(BContainer parent, String windowName, BananaPeel bananaPeel, ClientQuestData questData) {
/*  66 */     super(windowName, bananaPeel);
/*  67 */     this.questData = questData;
/*  68 */     this.expandedHeight = 114 + 25 * questData.getQuestObjectives().size();
/*  69 */     this.parent = parent;
/*     */     
/*  71 */     getMainContainer().setLayoutManager((BLayoutManager)new AbsoluteLayout());
/*     */     
/*  73 */     this.hasReward = (questData.getQuestRewards().size() > 0);
/*     */     
/*  75 */     initComponents();
/*  76 */     initListeners();
/*     */     
/*  78 */     updateQuestData(questData);
/*     */   }
/*     */   
/*     */   private void initComponents() {
/*  82 */     this.objectiveLabels = new BClickthroughLabel[this.questData.getQuestObjectives().size()];
/*     */ 
/*     */ 
/*     */     
/*  86 */     this.achievementButton = new BButton("");
/*  87 */     BComponent placeholder = findComponent(getMainContainer(), "achievement_button");
/*  88 */     overridePeelerComponent((BComponent)this.achievementButton, placeholder);
/*  89 */     if (this.parent instanceof AchievementsWindow) {
/*  90 */       this.achievementButton.setTooltipText(TcgGame.getLocalizedText("tooltips.achievements." + (this.expanded ? "collapse" : "expand"), new String[0]));
/*     */     } else {
/*  92 */       this.achievementButton.setTooltipText(TcgGame.getLocalizedText("tooltips.achievements.view", new String[0]));
/*     */     } 
/*     */     
/*  95 */     String questName = this.questData.getQuestName().isEmpty() ? this.questData.getQuestId().replace("_", " ") : this.questData.getQuestName();
/*  96 */     this.achievementTitle = new BClickthroughLabel(questName);
/*  97 */     placeholder = findComponent(getMainContainer(), "achievement_title");
/*  98 */     overridePeelerComponent((BComponent)this.achievementTitle, placeholder);
/*     */     
/* 100 */     this.achievementDescription = new BClickthroughLabel(this.questData.getQuestText());
/* 101 */     placeholder = findComponent(getMainContainer(), "achievement_description");
/* 102 */     overridePeelerComponent((BComponent)this.achievementDescription, placeholder);
/*     */     
/* 104 */     this.achievementButton.setAlpha(this.questData.isCompleted() ? 1.0F : 0.5F);
/*     */ 
/*     */     
/* 107 */     this.progressBgdLabel = new BClickthroughLabel("");
/* 108 */     this.progressBgdLabel.setStyleClass("progress_bar_bgd");
/* 109 */     this.progressBgdLabel.setSize(684, 15);
/*     */     
/* 111 */     this.achievementProgress = new BProgressBar();
/* 112 */     this.achievementProgress.setClickthrough(true);
/* 113 */     this.achievementProgress.setStyleClass("achievement_progress");
/* 114 */     this.achievementProgress.setSize(684, 15);
/*     */     
/* 116 */     this.progressGlossLabel = new BClickthroughLabel("");
/* 117 */     this.progressGlossLabel.setStyleClass("progress_bar_gloss");
/* 118 */     this.progressGlossLabel.setSize(684, 15);
/*     */     
/* 120 */     this.rewardTrophy = new BClickthroughLabel("");
/* 121 */     placeholder = findComponent(getMainContainer(), "gift_button");
/* 122 */     overridePeelerComponent((BComponent)this.rewardTrophy, placeholder);
/*     */     
/* 124 */     this.rewardIconBgd = new HighlightedToggleButton("");
/* 125 */     placeholder = findComponent(getMainContainer(), "gift_icon_bgd");
/* 126 */     overridePeelerComponent((BComponent)this.rewardIconBgd, placeholder);
/*     */     
/* 128 */     this.rewardIcon = new BClickthroughLabel("");
/* 129 */     placeholder = findComponent(getMainContainer(), "gift_icon");
/* 130 */     overridePeelerComponent((BComponent)this.rewardIcon, placeholder);
/*     */     
/* 132 */     if (this.parent instanceof AchievementsWindow) {
/* 133 */       this.expandedToggle = new HighlightedToggleButton("");
/* 134 */       placeholder = findComponent(getMainContainer(), "expanded_button");
/* 135 */       overridePeelerComponent((BComponent)this.expandedToggle, placeholder);
/* 136 */       this.expandedToggle.setClickthrough(true);
/* 137 */       this.expandedToggle.setText(this.expanded ? "-" : "+");
/*     */     } 
/*     */     
/* 140 */     this.achievementIconBgd = new BClickthroughLabel("");
/* 141 */     placeholder = findComponent(getMainContainer(), "achievement_image_bgd");
/* 142 */     overridePeelerComponent((BComponent)this.achievementIconBgd, placeholder);
/*     */     
/* 144 */     this.achievementIcon = new BClickthroughLabel();
/* 145 */     placeholder = findComponent(getMainContainer(), "achievement_image");
/* 146 */     overridePeelerComponent((BComponent)this.achievementIcon, placeholder);
/*     */     
/* 148 */     String itemIconPath = this.questData.getIcon();
/* 149 */     BImage achievementIconImage = null;
/*     */     
/*     */     try {
/* 152 */       String rewardTooltip = "";
/* 153 */       achievementIconImage = (BImage)TcgGame.getResourceManager().getResource(BImage.class, itemIconPath);
/*     */       
/* 155 */       if (this.hasReward) {
/* 156 */         QuestRewardData rewardData = this.questData.getQuestRewards().get(0);
/* 157 */         String iconPath = "";
/* 158 */         if (rewardData.getType() == QuestRewardType.ITEM_REWARD.getId()) {
/* 159 */           ItemDescription itemDescription = TcgGame.getRpgLoader().getItemManager().getDescription(new SkillId(rewardData.getRewardId(), rewardData.getTier()));
/* 160 */           iconPath = itemDescription.getIcon();
/* 161 */           rewardTooltip = itemDescription.getName();
/* 162 */           if (itemDescription.getId().equals("coin")) {
/* 163 */             iconPath = iconPath.replace("_item.png", "_background_item.png");
/* 164 */             rewardTooltip = rewardTooltip + ((System.getProperty("tcg.locale") != null && System.getProperty("tcg.locale").equals("no")) ? "er" : "s") + " (" + rewardData.getAmount() + ")";
/*     */           }
/*     */         
/*     */         }
/* 168 */         else if (rewardData.getType() == QuestRewardType.PET_REWARD.getId()) {
/* 169 */           ClientPetDescription petDesc = MainGameState.getPetRegistry().getPetForClassId(rewardData.getRewardId());
/* 170 */           iconPath = petDesc.getIcon();
/* 171 */           rewardTooltip = JavaLocalization.getInstance().getLocalizedRPGText(petDesc.getName());
/*     */         } 
/*     */         
/* 174 */         BImage rewardIconImage = (BImage)TcgGame.getResourceManager().getResource(BImage.class, iconPath);
/*     */         
/* 176 */         ImageBackground rewardIconImgBgd = new ImageBackground(ImageBackgroundMode.SCALE_XY, rewardIconImage);
/* 177 */         getMainContainer().remove((BComponent)this.rewardIcon);
/* 178 */         this.rewardIcon.setBackground(0, (BBackground)rewardIconImgBgd);
/* 179 */         this.rewardIcon.setBackground(1, (BBackground)rewardIconImgBgd);
/* 180 */         getMainContainer().add((BComponent)this.rewardIcon, new Rectangle(this.rewardIcon.getX(), this.rewardIcon.getY(), this.rewardIcon.getWidth(), this.rewardIcon.getHeight()));
/*     */         
/* 182 */         this.rewardIconBgd.setTooltipText(rewardTooltip);
/*     */       }
/*     */     
/*     */     }
/* 186 */     catch (NoLocatorException e) {
/* 187 */       e.printStackTrace();
/* 188 */       throw new RuntimeException("Missing image for destination portal: " + itemIconPath);
/*     */     } 
/*     */     
/* 191 */     ImageBackground imageBackground = new ImageBackground(ImageBackgroundMode.SCALE_XY, achievementIconImage);
/* 192 */     getMainContainer().remove((BComponent)this.achievementIcon);
/* 193 */     this.achievementIcon.setBackground(0, (BBackground)imageBackground);
/* 194 */     this.achievementIcon.setBackground(1, (BBackground)imageBackground);
/* 195 */     getMainContainer().add((BComponent)this.achievementIcon, new Rectangle(this.achievementIcon.getX(), this.achievementIcon.getY(), this.achievementIcon.getWidth(), this.achievementIcon.getHeight()));
/*     */ 
/*     */     
/* 198 */     for (int i = 0; i < this.questData.getQuestObjectives().size(); i++) {
/* 199 */       ClientObjectiveTracker objective = this.questData.getQuestObjectives().get(i);
/* 200 */       this.objectiveLabels[i] = new BClickthroughLabel(objective.isCompleted() ? objective.getObjective().getCompletedObjectiveText() : objective.getObjective().getUnCompletedObjectiveText());
/*     */ 
/*     */       
/* 203 */       this.objectiveLabels[i].setEnabled(objective.isCompleted());
/* 204 */       this.objectiveLabels[i].setStyleClass("achievement_objective");
/*     */     } 
/*     */     
/* 207 */     this.achievementProgress.setProgress(0.0F);
/*     */   }
/*     */   
/*     */   public void resize() {
/* 211 */     int expandOffset = this.expandedHeight - 89;
/*     */     
/* 213 */     getMainContainer().remove((BComponent)this.achievementButton);
/* 214 */     getMainContainer().remove((BComponent)this.achievementTitle);
/* 215 */     getMainContainer().remove((BComponent)this.achievementDescription);
/* 216 */     getMainContainer().remove((BComponent)this.achievementIconBgd);
/* 217 */     getMainContainer().remove((BComponent)this.achievementIcon);
/* 218 */     getMainContainer().remove((BComponent)this.rewardTrophy);
/* 219 */     getMainContainer().remove((BComponent)this.rewardIcon);
/* 220 */     getMainContainer().remove((BComponent)this.rewardIconBgd);
/*     */ 
/*     */     
/* 223 */     getMainContainer().add((BComponent)this.achievementButton, new Rectangle(0, 0, this.achievementButton.getWidth(), getPixelHeight()));
/*     */     
/* 225 */     getMainContainer().add((BComponent)this.achievementTitle, new Rectangle(70, 40 + (this.expanded ? 1 : 0) * expandOffset, this.achievementTitle.getWidth(), this.achievementTitle.getHeight()));
/*     */     
/* 227 */     getMainContainer().add((BComponent)this.achievementDescription, new Rectangle(70, 10 + (this.expanded ? 1 : 0) * expandOffset, this.achievementDescription.getWidth(), this.achievementDescription.getHeight()));
/*     */     
/* 229 */     getMainContainer().add((BComponent)this.achievementIconBgd, new Rectangle(9, 19 + (this.expanded ? 1 : 0) * expandOffset, this.achievementIconBgd.getWidth(), this.achievementIconBgd.getHeight()));
/*     */     
/* 231 */     getMainContainer().add((BComponent)this.achievementIcon, new Rectangle(10, 20 + (this.expanded ? 1 : 0) * expandOffset, this.achievementIcon.getWidth(), this.achievementIcon.getHeight()));
/*     */ 
/*     */     
/* 234 */     if (this.hasReward) {
/* 235 */       getMainContainer().add((BComponent)this.rewardTrophy, new Rectangle(615, 20 + (this.expanded ? 1 : 0) * expandOffset, this.rewardTrophy.getWidth(), this.rewardTrophy.getHeight()));
/*     */       
/* 237 */       getMainContainer().add((BComponent)this.rewardIconBgd, new Rectangle(550, 14 + (this.expanded ? 1 : 0) * expandOffset, this.rewardIconBgd.getWidth(), this.rewardIconBgd.getHeight()));
/*     */       
/* 239 */       getMainContainer().add((BComponent)this.rewardIcon, new Rectangle(557, 20 + (this.expanded ? 1 : 0) * expandOffset, this.rewardIcon.getWidth(), this.rewardIcon.getHeight()));
/*     */     } 
/*     */ 
/*     */     
/* 243 */     if (this.expandedToggle != null) {
/* 244 */       this.expandedToggle.setSelected(this.expanded);
/* 245 */       this.expandedToggle.setText(this.expanded ? "-" : "+");
/* 246 */       getMainContainer().remove((BComponent)this.expandedToggle);
/* 247 */       getMainContainer().add((BComponent)this.expandedToggle, new Rectangle(670, 10 + (this.expanded ? 1 : 0) * expandOffset, this.expandedToggle.getWidth(), this.expandedToggle.getHeight()));
/*     */     } 
/*     */ 
/*     */     
/* 251 */     if (this.expanded) {
/* 252 */       getMainContainer().add((BComponent)this.progressBgdLabel, new Rectangle(9, 9, this.progressBgdLabel.getWidth(), this.progressBgdLabel.getHeight()));
/* 253 */       getMainContainer().add((BComponent)this.achievementProgress, new Rectangle(9, 9, this.achievementProgress.getWidth(), this.achievementProgress.getHeight()));
/* 254 */       getMainContainer().add((BComponent)this.progressGlossLabel, new Rectangle(9, 9, this.progressGlossLabel.getWidth(), this.progressGlossLabel.getHeight()));
/*     */       
/* 256 */       for (int i = this.objectiveLabels.length - 1; i >= 0; i--) {
/*     */         
/* 258 */         int yRatio = this.objectiveLabels.length - 1 - i;
/* 259 */         getMainContainer().add((BComponent)this.objectiveLabels[i], new Rectangle(this.achievementDescription.getX(), 40 + yRatio * 25, 600, 25));
/*     */       } 
/*     */     } else {
/* 262 */       getMainContainer().remove((BComponent)this.progressBgdLabel);
/* 263 */       getMainContainer().remove((BComponent)this.achievementProgress);
/* 264 */       getMainContainer().remove((BComponent)this.progressGlossLabel);
/* 265 */       for (BClickthroughLabel objectiveLabel : this.objectiveLabels) {
/*     */         
/* 267 */         if (getMainContainer().getComponentIndex((BComponent)objectiveLabel) >= 0) {
/* 268 */           getMainContainer().remove((BComponent)objectiveLabel);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void initListeners() {
/* 276 */     this.rewardIconBgd.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 279 */             AchievementPlaque.this.rewardIconBgd.setSelected(((AchievementPlaque.this.parent instanceof AchievementsWindow && AchievementPlaque.this.hasReward && AchievementPlaque.this.questData.isToClaim()) || !AchievementPlaque.this.rewardIconBgd.isSelected()));
/* 280 */             if (AchievementPlaque.this.hasReward && AchievementPlaque.this.questData.isToClaim() && AchievementPlaque.this.parent instanceof AchievementsWindow) {
/* 281 */               ClaimQuestRewardsMessage message = new ClaimQuestRewardsMessage(AchievementPlaque.this.questData.getQuestId());
/*     */               try {
/* 283 */                 NetworkHandler.instance().getIOHandler().send((Message)message);
/* 284 */               } catch (InterruptedException e) {
/* 285 */                 throw new IllegalStateException(e);
/*     */               } 
/* 287 */               MainGameState.getAchievementWindow().claimReward(AchievementPlaque.this.questData);
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 292 */     this.achievementButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 295 */             if (AchievementPlaque.this.parent instanceof AchievementsWindow) {
/* 296 */               AchievementPlaque.this.expanded = !AchievementPlaque.this.expanded;
/* 297 */               AchievementPlaque.this.resize();
/* 298 */               MainGameState.getAchievementWindow().resetView(false);
/*     */               
/* 300 */               AchievementPlaque.this.achievementButton.setTooltipText(TcgGame.getLocalizedText("tooltips.achievements." + (AchievementPlaque.this.expanded ? "collapse" : "expand"), new String[0]));
/*     */             } else {
/* 302 */               MainGameState.getAchievementWindow().scrollTo(AchievementPlaque.this.questData);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void updateQuestData(ClientQuestData questData) {
/* 309 */     this.questData = questData;
/*     */     
/* 311 */     this.achievementButton.setAlpha(questData.isCompleted() ? 1.0F : 0.5F);
/* 312 */     this.achievementIcon.setAlpha(questData.isCompleted() ? 1.0F : 0.5F);
/* 313 */     this.rewardIcon.setAlpha(questData.isCompleted() ? 1.0F : 0.5F);
/* 314 */     this.rewardIconBgd.setAlpha(questData.isCompleted() ? 1.0F : 0.5F);
/* 315 */     this.rewardTrophy.setAlpha(questData.isCompleted() ? 1.0F : 0.5F);
/*     */     
/* 317 */     this.achievementTitle.setEnabled(questData.isCompleted());
/* 318 */     this.achievementDescription.setEnabled(questData.isCompleted());
/* 319 */     this.rewardTrophy.setEnabled((questData.isCompleted() && !questData.isClaimed() && this.parent instanceof AchievementsWindow));
/*     */     
/* 321 */     this.rewardIconBgd.setClickthrough(!(this.parent instanceof AchievementsWindow));
/*     */     
/* 323 */     this.hasReward = (questData.getQuestRewards().size() > 0);
/* 324 */     if (!this.hasReward) {
/* 325 */       getMainContainer().remove((BComponent)this.rewardTrophy);
/* 326 */       getMainContainer().remove((BComponent)this.rewardIconBgd);
/* 327 */       getMainContainer().remove((BComponent)this.rewardIcon);
/* 328 */     } else if (questData.isCompleted() && questData.isClaimed()) {
/* 329 */       this.rewardIconBgd.setSelected(true);
/*     */     } 
/*     */     
/* 332 */     this.maxAmount = 0;
/* 333 */     int currentAmount = 0;
/* 334 */     for (int i = 0; i < questData.getQuestObjectives().size(); i++) {
/* 335 */       ClientObjectiveTracker objective = questData.getQuestObjectives().get(i);
/* 336 */       this.maxAmount += objective.getCompleteValue();
/* 337 */       currentAmount += objective.getTrackAmount();
/*     */       
/* 339 */       String text = objective.isCompleted() ? objective.getObjective().getCompletedObjectiveText() : objective.getObjective().getUnCompletedObjectiveText();
/* 340 */       int trackAmount = objective.getTrackAmount();
/* 341 */       trackAmount = (trackAmount > objective.getCompleteValue()) ? objective.getCompleteValue() : trackAmount;
/* 342 */       this.objectiveLabels[i].setText(text + " - (" + trackAmount + "/" + objective.getCompleteValue() + ")");
/* 343 */       this.objectiveLabels[i].setEnabled(objective.isCompleted());
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 348 */       float progress = currentAmount / this.maxAmount;
/* 349 */       progress = (progress < 0.0F) ? 0.0F : ((progress > 1.0F) ? 1.0F : progress);
/* 350 */       this.achievementProgress.setProgress(progress);
/* 351 */       if (this.maxAmount > 1) {
/* 352 */         String iconTotal = String.valueOf(this.maxAmount);
/* 353 */         if (this.maxAmount >= 1000) {
/* 354 */           if (this.maxAmount >= 1000000) {
/* 355 */             iconTotal = String.valueOf((this.maxAmount / 1000000) + "M");
/*     */           } else {
/* 357 */             iconTotal = String.valueOf((this.maxAmount / 1000) + "K");
/*     */           } 
/*     */         }
/* 360 */         this.achievementIcon.setText(String.valueOf(iconTotal));
/*     */       } 
/* 362 */     } catch (IllegalArgumentException e) {
/* 363 */       System.out.println("Quest Name: " + questData.getQuestName() + ", max: " + this.maxAmount + ", current" + currentAmount);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 369 */     super.setVisible(visible);
/*     */   }
/*     */   
/*     */   public int getPixelHeight() {
/* 373 */     return this.expanded ? this.expandedHeight : 89;
/*     */   }
/*     */   
/*     */   public ClientQuestData getQuestData() {
/* 377 */     return this.questData;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\achievements\AchievementPlaque.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */