/*     */ package com.funcom.tcg.client.ui.achievements;
/*     */ 
/*     */ import com.funcom.commons.jme.bui.HighlightedButton;
/*     */ import com.funcom.commons.jme.bui.HighlightedToggleButton;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.peeler.BananaPeel;
/*     */ import com.funcom.rpgengine2.quests.QuestDescription;
/*     */ import com.funcom.rpgengine2.quests.objectives.QuestObjective;
/*     */ import com.funcom.rpgengine2.quests.reward.QuestCategory;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientObjectiveTracker;
/*     */ import com.funcom.tcg.client.model.rpg.ClientQuestData;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.BPeelWindow;
/*     */ import com.funcom.tcg.client.ui.BScrollPaneTcg;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*     */ import com.funcom.tcg.client.ui.quest.QuestModel;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class AchievementsWindow extends BPeelWindow implements QuestModel.QuestChangeListener {
/*     */   private int OFFSET_X;
/*     */   private int OFFSET_Y;
/*     */   private QuestModel questModel;
/*     */   private BContainer achievementsListContainer;
/*  43 */   private QuestCategory activeCategory = QuestCategory.NONE;
/*     */   
/*     */   private BScrollPaneTcg achievementsScrollPane;
/*     */   private HashMap<QuestCategory, List<AchievementPlaque>> sortedAchievements;
/*     */   private HashMap<String, AchievementPlaque> achievementPlaques;
/*  48 */   private int[] maxes = new int[(QuestCategory.values()).length]; private HighlightedToggleButton[] categoryButtons; private AchievementSummary achievementSummary; private Set<ClientQuestData> achievementsData;
/*     */   
/*     */   public AchievementsWindow(String windowName, BananaPeel bananaPeel, ResourceManager resourceManager, QuestModel questModel) {
/*  51 */     super(windowName, bananaPeel);
/*     */     
/*  53 */     this._style = BuiUtils.createStyleSheet("/peeler/achievements_gui.bss", new BananaResourceProvider(resourceManager));
/*  54 */     this.questModel = questModel;
/*     */     
/*  56 */     this.achievementPlaques = new HashMap<String, AchievementPlaque>();
/*  57 */     this.sortedAchievements = new HashMap<QuestCategory, List<AchievementPlaque>>();
/*  58 */     for (QuestCategory category : QuestCategory.values()) {
/*  59 */       this.sortedAchievements.put(category, new ArrayList<AchievementPlaque>());
/*     */     }
/*     */     
/*  62 */     setLayer(1);
/*     */     
/*  64 */     initComponents();
/*  65 */     addDefaultCloseButton();
/*  66 */     initListeners();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initComponents() {
/*  72 */     BLabel titleLabel = new BLabel(TcgGame.getLocalizedText("achievements.window.title", new String[0]));
/*  73 */     BComponent placeholder = findComponent((BContainer)this, "window_title");
/*  74 */     overridePeelerComponent((BComponent)titleLabel, placeholder);
/*     */ 
/*     */ 
/*     */     
/*  78 */     this.achievementsListContainer = new BContainer((BLayoutManager)new AbsoluteLayout());
/*  79 */     this.achievementsListContainer.setStyleClass("achievements_list");
/*     */     
/*  81 */     this.achievementsScrollPane = new BScrollPaneTcg((BComponent)this.achievementsListContainer, true, 25);
/*  82 */     placeholder = findComponent((BContainer)this, "achievements_list");
/*  83 */     overridePeelerComponent((BComponent)this.achievementsScrollPane, placeholder);
/*     */ 
/*     */     
/*  86 */     this.categoryButtons = new HighlightedToggleButton[(QuestCategory.values()).length];
/*     */     
/*  88 */     for (int i = 0; i < (QuestCategory.values()).length; i++) {
/*  89 */       String categoryName = QuestCategory.values()[i].toString().toLowerCase();
/*  90 */       categoryName = categoryName.equals(QuestCategory.NONE.toString().toLowerCase()) ? "summary" : categoryName;
/*     */       
/*  92 */       this.categoryButtons[i] = new HighlightedToggleButton(TcgGame.getLocalizedText("achievements.window.category." + categoryName, new String[0]));
/*  93 */       placeholder = findComponent((BContainer)this, categoryName + "_button");
/*  94 */       overridePeelerComponent((BComponent)this.categoryButtons[i], placeholder);
/*  95 */       this.categoryButtons[i].addListener((ComponentListener)new AchievementViewListener(QuestCategory.values()[i]));
/*     */     } 
/*  97 */     this.categoryButtons[QuestCategory.NONE.getType()].setSelected(true);
/*     */     
/*  99 */     buildAchievementsData();
/* 100 */     initSummary();
/*     */     
/* 102 */     addAchievements();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initSummary() {
/* 108 */     this.achievementSummary = new AchievementSummary((BananaPeel)TcgGame.getResourceManager().getResource(BananaPeel.class, "gui/peeler/achievements_summary_gui.xml", CacheType.NOT_CACHED), this.maxes);
/*     */     
/* 110 */     BContainer bContainer = findContainer("achievements_sub_container");
/* 111 */     overridePeelerComponent((BComponent)this.achievementSummary.getMainContainer(), (BComponent)bContainer);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addDefaultCloseButton() {
/* 116 */     HighlightedButton closeButton = new HighlightedButton();
/* 117 */     int closeButtonSize = 24;
/* 118 */     add((BComponent)closeButton, new Rectangle(1024 - closeButtonSize - 5, getHeight() - closeButtonSize - 5, closeButtonSize, closeButtonSize));
/*     */ 
/*     */     
/* 121 */     closeButton.setStyleClass("close-button");
/* 122 */     closeButton.setTooltipText(TcgGame.getLocalizedText("tooltips.friends.close", new String[0]));
/* 123 */     closeButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 126 */             AchievementsWindow.this.close();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   protected void close() {
/* 132 */     PanelManager.getInstance().closeWindow((BWindow)this);
/* 133 */     if (MainGameState.getPauseModel().isPaused()) {
/* 134 */       MainGameState.getPauseModel().reset();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void dismiss() {
/* 140 */     super.dismiss();
/* 141 */     this.questModel.removeChangeListener(this);
/*     */   }
/*     */   
/*     */   private void buildAchievementsData() {
/* 145 */     this.achievementsData = new HashSet<ClientQuestData>();
/*     */     
/* 147 */     Iterator<String> iterator = TcgGame.getRpgLoader().getQuestManager().getAchievements().iterator();
/* 148 */     while (iterator.hasNext()) {
/*     */       
/* 150 */       String questId = iterator.next();
/*     */       
/* 152 */       QuestDescription questDescription = TcgGame.getRpgLoader().getQuestManager().getQuestDescription(questId);
/* 153 */       if (questDescription == null) {
/*     */         continue;
/*     */       }
/* 156 */       List<ClientObjectiveTracker> questObjStrings = new ArrayList<ClientObjectiveTracker>();
/* 157 */       for (int i = 0; i < questDescription.getQuestObjectives().size(); i++) {
/* 158 */         QuestObjective questObjective = questDescription.getQuestObjectives().get(i);
/* 159 */         int amount = questObjective.getAmount();
/* 160 */         int current = 0;
/* 161 */         if (this.questModel.hasCompletedQuest(questId)) {
/* 162 */           current = amount;
/*     */         } else {
/* 164 */           ClientQuestData questDescriptionByIds = this.questModel.getQuestDescriptionByIds(questId);
/* 165 */           if (questDescriptionByIds != null) {
/* 166 */             ClientObjectiveTracker clientObjectiveTracker = questDescriptionByIds.getQuestObjectives().get(i);
/* 167 */             if (clientObjectiveTracker != null)
/* 168 */               current = clientObjectiveTracker.getTrackAmount(); 
/*     */           } 
/*     */         } 
/* 171 */         ClientObjectiveTracker tracker = new ClientObjectiveTracker(questDescription.getId(), questObjective.getObjectiveId(), current, amount, questObjective);
/*     */ 
/*     */         
/* 174 */         questObjStrings.add(tracker);
/*     */       } 
/*     */       
/* 177 */       this.maxes[questDescription.getQuestCategory().getType()] = this.maxes[questDescription.getQuestCategory().getType()] + 1;
/* 178 */       this.maxes[QuestCategory.NONE.getType()] = this.maxes[QuestCategory.NONE.getType()] + 1;
/*     */       
/* 180 */       ClientQuestData client = new ClientQuestData(questId, questDescription.getName(), questDescription.getQuestType().getType(), questDescription.getQuestCategory().getType(), questDescription.getQuestCategoryId(), questObjStrings, questDescription.getQuestRewardDataContainerList(), this.questModel.hasCompletedQuest(questId), this.questModel.hasUnclaimedReward(questId), "fixme!", "fixme!", questDescription.getIconPath(), questDescription.getQuestText(), (questDescription.getMissionCoordinate() != null) ? questDescription.getMissionCoordinate().getMapId() : null, questDescription.getNextQuest());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 190 */       client.setClaimed(!this.questModel.hasUnclaimedReward(questId));
/*     */       
/* 192 */       this.achievementsData.add(client);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addAchievements() {
/* 201 */     Iterator<ClientQuestData> iterator = this.achievementsData.iterator();
/* 202 */     while (iterator.hasNext()) {
/* 203 */       ClientQuestData data = iterator.next();
/* 204 */       if (data != null) {
/* 205 */         ((List)this.sortedAchievements.get(QuestCategory.values()[data.getQuestCategory()])).add(null);
/*     */       }
/*     */     } 
/*     */     
/* 209 */     iterator = this.achievementsData.iterator();
/* 210 */     while (iterator.hasNext()) {
/* 211 */       ClientQuestData data = iterator.next();
/* 212 */       if (data != null) {
/* 213 */         BananaPeel plaqueBananaPeel = (BananaPeel)TcgGame.getResourceManager().getResource(BananaPeel.class, "gui/peeler/achievements_subsections.xml", CacheType.NOT_CACHED);
/*     */         
/* 215 */         AchievementPlaque achievementPlaque = new AchievementPlaque((BContainer)this, "Achievements Plaque", plaqueBananaPeel, data);
/* 216 */         ((List<AchievementPlaque>)this.sortedAchievements.get(QuestCategory.values()[data.getQuestCategory()])).set(data.getQuestCategoryId(), achievementPlaque);
/*     */         
/* 218 */         if (data.isCompleted()) {
/* 219 */           plaqueBananaPeel = (BananaPeel)TcgGame.getResourceManager().getResource(BananaPeel.class, "gui/peeler/achievements_subsections.xml", CacheType.NOT_CACHED);
/*     */           
/* 221 */           AchievementPlaque latestPlaque = new AchievementPlaque((BContainer)this.achievementSummary, "Achievements Plaque", plaqueBananaPeel, data);
/* 222 */           this.achievementSummary.updateLatest(latestPlaque);
/* 223 */           this.achievementSummary.updateProgress(QuestCategory.values()[data.getQuestCategory()]);
/*     */         } 
/* 225 */         this.achievementPlaques.put(data.getQuestId(), achievementPlaque);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void initListeners() {
/* 232 */     this.questModel.addChangeListener(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void rewardClaimed(QuestModel questModel, ClientQuestData questData) {
/* 237 */     for (AchievementPlaque plaque : this.sortedAchievements.get(QuestCategory.values()[questData.getQuestCategory()])) {
/* 238 */       if (plaque.getQuestData().getQuestId().equals(questData.getQuestId())) {
/* 239 */         plaque.updateQuestData(questData);
/*     */         break;
/*     */       } 
/*     */     } 
/* 243 */     for (AchievementPlaque plaque : this.achievementSummary.getLatestPlaques()) {
/* 244 */       if (plaque.getQuestData().getQuestId().equals(questData.getQuestId())) {
/* 245 */         plaque.updateQuestData(questData);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void achievementAdded(QuestModel questModel, ClientQuestData mission) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void achievementCompleted(QuestModel questModel, ClientQuestData mission) {
/* 258 */     AchievementPlaque plaque = this.achievementPlaques.get(mission.getQuestId());
/* 259 */     mission.setToClaim((mission.getQuestRewards().size() > 0));
/* 260 */     plaque.updateQuestData(mission);
/*     */     
/* 262 */     BananaPeel plaqueBananaPeel = (BananaPeel)TcgGame.getResourceManager().getResource(BananaPeel.class, "gui/peeler/achievements_subsections.xml", CacheType.NOT_CACHED);
/*     */     
/* 264 */     AchievementPlaque latestPlaque = new AchievementPlaque((BContainer)this.achievementSummary, "Achievements Plaque", plaqueBananaPeel, plaque.getQuestData());
/* 265 */     this.achievementSummary.updateLatest(latestPlaque);
/* 266 */     this.achievementSummary.updateProgress(QuestCategory.values()[mission.getQuestCategory()]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void achievementUpdated(QuestModel questModel, ClientQuestData mission) {
/* 271 */     ((AchievementPlaque)this.achievementPlaques.get(mission.getQuestId())).updateQuestData(mission);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void missionAdded(QuestModel questModel, ClientQuestData mission) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void missionCompleted(QuestModel questModel, ClientQuestData mission) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void missionUpdated(QuestModel questModel, ClientQuestData mission) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void questAdded(QuestModel questModel, ClientQuestData quest) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void questCompleted(QuestModel questModel, ClientQuestData quest) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void questUpdated(QuestModel questModel, ClientQuestData quest) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void openAchievementView(QuestCategory category) {
/* 304 */     this.activeCategory = category;
/*     */     
/* 306 */     for (int i = 0; i < (QuestCategory.values()).length; i++) {
/* 307 */       this.categoryButtons[i].setSelected(category.equals(QuestCategory.values()[i]));
/*     */     }
/*     */     
/* 310 */     this.achievementSummary.setVisible(category.equals(QuestCategory.NONE));
/* 311 */     this.achievementsScrollPane.setVisible(!category.equals(QuestCategory.NONE));
/*     */     
/* 313 */     if (!category.equals(QuestCategory.NONE)) resetView(true); 
/*     */   }
/*     */   
/*     */   public void scrollTo(ClientQuestData data) {
/* 317 */     QuestCategory category = QuestCategory.values()[data.getQuestCategory()];
/* 318 */     openAchievementView(category);
/* 319 */     int index = 0;
/* 320 */     for (int i = 0; i < ((List)this.sortedAchievements.get(category)).size(); i++) {
/* 321 */       if (((AchievementPlaque)((List<AchievementPlaque>)this.sortedAchievements.get(category)).get(i)).getQuestData().getQuestId().equals(data.getQuestId())) {
/* 322 */         index = i;
/*     */         break;
/*     */       } 
/*     */     } 
/* 326 */     int scrollToValue = 0;
/* 327 */     for (int j = 0; j < index; j++) {
/* 328 */       scrollToValue += ((AchievementPlaque)((List<AchievementPlaque>)this.sortedAchievements.get(category)).get(j)).getPixelHeight();
/*     */     }
/* 330 */     this.achievementsScrollPane.getVerticalViewportModel().setValue(scrollToValue);
/*     */   }
/*     */   
/*     */   public void resetView(boolean resetScroll) {
/* 334 */     this.achievementsListContainer.removeAll();
/* 335 */     int scrollValue = resetScroll ? 0 : this.achievementsScrollPane.getVerticalViewportModel().getValue();
/* 336 */     int nextY = 0;
/* 337 */     for (int i = ((List)this.sortedAchievements.get(this.activeCategory)).size() - 1; i >= 0; i--) {
/* 338 */       AchievementPlaque plaque = ((List<AchievementPlaque>)this.sortedAchievements.get(this.activeCategory)).get(i);
/* 339 */       this.achievementsListContainer.add((BComponent)plaque.getMainContainer(), new Rectangle(0, nextY, 710, plaque.getPixelHeight()));
/* 340 */       plaque.setVisible(true);
/* 341 */       nextY += plaque.getPixelHeight() + 5;
/*     */     } 
/* 343 */     this.achievementsScrollPane.getVerticalViewportModel().setValue(scrollValue);
/*     */   }
/*     */   
/*     */   public void claimReward(ClientQuestData questData) {
/* 347 */     this.questModel.claimReward(questData);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 352 */     super.setVisible(visible);
/*     */     
/* 354 */     if (visible) {
/* 355 */       openAchievementView(QuestCategory.NONE);
/* 356 */       MainGameState.getQuestModel().setAchievementsPending(0);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\achievements\AchievementsWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */