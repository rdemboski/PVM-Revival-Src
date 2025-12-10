/*     */ package com.funcom.tcg.client.ui.achievements;
/*     */ 
/*     */ import com.funcom.commons.jme.bui.HighlightedButton;
/*     */ import com.funcom.gameengine.resourcemanager.NoLocatorException;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.peeler.BananaPeel;
/*     */ import com.funcom.rpgengine2.SkillId;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.quests.reward.QuestRewardData;
/*     */ import com.funcom.rpgengine2.quests.reward.QuestRewardType;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientObjectiveTracker;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPetDescription;
/*     */ import com.funcom.tcg.client.model.rpg.ClientQuestData;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.BPeelWindow;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.quest.QuestModel;
/*     */ import com.funcom.util.DebugManager;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.util.Timer;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BClickthroughLabel;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import com.jmex.bui.background.ImageBackground;
/*     */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AchievementNotificationWindow
/*     */   extends BPeelWindow
/*     */   implements QuestModel.QuestChangeListener
/*     */ {
/*  44 */   private boolean noAchieveNotify = (System.getProperty("tcg.achieve.nonotify") != null && System.getProperty("tcg.achieve.nonotify").equals(String.valueOf(true)) && DebugManager.getInstance().isDebugEnabled());
/*     */   
/*     */   private List<ClientQuestData> achievementsReceived;
/*  47 */   private int viewIndex = 0;
/*  48 */   private int maxAmount = 0;
/*     */   
/*     */   private QuestModel questModel;
/*     */   
/*     */   private BButton achievementButton;
/*     */   private BClickthroughLabel achievementTitle;
/*     */   private BClickthroughLabel achievementIcon;
/*     */   private BClickthroughLabel achievementIconBgd;
/*     */   private BClickthroughLabel rewardIcon;
/*     */   private HighlightedButton rewardButton;
/*     */   private HighlightedButton rewardIconBgd;
/*     */   private AchievementPlaque INSTANCE;
/*     */   private BClickthroughLabel achievementDescription;
/*     */   private float elapsed;
/*  62 */   private final float onTime = 3.0F; private final float offTime = 1.0F;
/*     */   
/*     */   private boolean on = false;
/*     */   
/*     */   private boolean hasReward = false;
/*     */   
/*     */   public AchievementNotificationWindow(QuestModel questModel, BananaPeel bananaPeel) {
/*  69 */     super("", bananaPeel);
/*  70 */     this._style = BuiUtils.createStyleSheet("/peeler/achievements_gui.bss", new BananaResourceProvider(TcgGame.getResourceManager()));
/*     */     
/*  72 */     this.questModel = questModel;
/*  73 */     initComponents();
/*  74 */     initListeners();
/*     */     
/*  76 */     this.achievementsReceived = new ArrayList<ClientQuestData>();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initComponents() {
/*  82 */     this.achievementButton = new BButton("");
/*  83 */     BComponent placeholder = findComponent((BContainer)this, "achievement_button");
/*  84 */     overridePeelerComponent((BComponent)this.achievementButton, placeholder);
/*     */     
/*  86 */     this.achievementTitle = new BClickthroughLabel("");
/*  87 */     placeholder = findComponent((BContainer)this, "achievement_title");
/*  88 */     overridePeelerComponent((BComponent)this.achievementTitle, placeholder);
/*     */     
/*  90 */     this.achievementDescription = new BClickthroughLabel("");
/*  91 */     placeholder = findComponent((BContainer)this, "achievement_description");
/*  92 */     overridePeelerComponent((BComponent)this.achievementDescription, placeholder);
/*     */     
/*  94 */     this.achievementIconBgd = new BClickthroughLabel("");
/*  95 */     placeholder = findComponent((BContainer)this, "achievement_image_bgd");
/*  96 */     overridePeelerComponent((BComponent)this.achievementIconBgd, placeholder);
/*     */     
/*  98 */     this.achievementIcon = new BClickthroughLabel();
/*  99 */     placeholder = findComponent((BContainer)this, "achievement_image");
/* 100 */     overridePeelerComponent((BComponent)this.achievementIcon, placeholder);
/*     */     
/* 102 */     this.rewardButton = new HighlightedButton("");
/* 103 */     placeholder = findComponent((BContainer)this, "gift_button");
/* 104 */     overridePeelerComponent((BComponent)this.rewardButton, placeholder);
/* 105 */     this.rewardButton.setClickthrough(true);
/*     */     
/* 107 */     this.rewardIconBgd = new HighlightedButton("");
/* 108 */     placeholder = findComponent((BContainer)this, "gift_icon_bgd");
/* 109 */     overridePeelerComponent((BComponent)this.rewardIconBgd, placeholder);
/* 110 */     this.rewardIconBgd.setClickthrough(true);
/*     */     
/* 112 */     this.rewardIcon = new BClickthroughLabel("");
/* 113 */     placeholder = findComponent((BContainer)this, "gift_icon");
/* 114 */     overridePeelerComponent((BComponent)this.rewardIcon, placeholder);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initListeners() {
/* 119 */     this.achievementButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 122 */             MainGameState.getHudModel().achievementsButtonAction();
/*     */           }
/*     */         });
/*     */     
/* 126 */     this.questModel.addChangeListener(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void dismiss() {
/* 131 */     super.dismiss();
/* 132 */     this.questModel.removeChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rewardClaimed(QuestModel questModel, ClientQuestData quest) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void achievementAdded(QuestModel questModel, ClientQuestData mission) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void achievementCompleted(QuestModel questModel, ClientQuestData mission) {
/* 147 */     if (questModel.isAcceptAchievements()) {
/* 148 */       this.achievementsReceived.add(mission);
/* 149 */       TcgUI.getUISoundPlayer().play("Achievement");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void achievementUpdated(QuestModel questModel, ClientQuestData mission) {}
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
/*     */   
/*     */   public void questAdded(QuestModel questModel, ClientQuestData quest) {}
/*     */ 
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
/*     */   public void render(Renderer renderer) {
/* 190 */     super.render(renderer);
/*     */     
/* 192 */     if (!this.noAchieveNotify && 
/* 193 */       this.achievementsReceived.size() > 0 && !TcgGame.isTutorialMode()) {
/* 194 */       this.elapsed += Timer.getTimer().getTimePerFrame();
/*     */       
/* 196 */       if (this.elapsed >= (this.on ? 3.0F : 1.0F)) {
/* 197 */         this.elapsed = 0.0F;
/* 198 */         if (isVisible()) {
/* 199 */           this.achievementsReceived.remove(0);
/* 200 */           setVisible(false);
/* 201 */           this.on = false;
/*     */         } else {
/* 203 */           this.on = true;
/* 204 */           setVisible((this.achievementsReceived.size() > 0));
/* 205 */           updateQuestData(this.achievementsReceived.get(0));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateQuestData(ClientQuestData questData) {
/* 213 */     if (MainGameState.getNoahTutorialWindow() != null) {
/* 214 */       setLayer(MainGameState.getNoahTutorialWindow().getLayer() + 1);
/*     */     } else {
/* 216 */       setLayer(2);
/*     */     } 
/*     */     
/* 219 */     this.hasReward = (questData.getQuestRewards().size() > 0);
/*     */     
/* 221 */     this.rewardButton.setVisible(this.hasReward);
/* 222 */     this.rewardIconBgd.setVisible(this.hasReward);
/* 223 */     this.rewardIcon.setVisible(this.hasReward);
/*     */     
/* 225 */     String questName = questData.getQuestName().isEmpty() ? questData.getQuestId().replace("_", " ") : questData.getQuestName();
/* 226 */     this.achievementTitle.setText(questName);
/* 227 */     this.achievementDescription.setText(questData.getQuestText());
/*     */     
/* 229 */     String itemIconPath = questData.getIcon();
/* 230 */     BImage achievementIconImage = null;
/*     */     try {
/* 232 */       achievementIconImage = (BImage)TcgGame.getResourceManager().getResource(BImage.class, itemIconPath);
/*     */       
/* 234 */       if (this.hasReward)
/*     */       {
/* 236 */         QuestRewardData rewardData = questData.getQuestRewards().get(0);
/* 237 */         String iconPath = "";
/* 238 */         if (rewardData.getType() == QuestRewardType.ITEM_REWARD.getId()) {
/* 239 */           ItemDescription itemDescription = TcgGame.getRpgLoader().getItemManager().getDescription(new SkillId(rewardData.getRewardId(), rewardData.getTier()));
/* 240 */           iconPath = itemDescription.getIcon();
/* 241 */           if (itemDescription.getId().equals("coin"))
/* 242 */             iconPath = iconPath.replace("_item.png", "_background_item.png"); 
/* 243 */         } else if (rewardData.getType() == QuestRewardType.PET_REWARD.getId()) {
/* 244 */           ClientPetDescription petDesc = MainGameState.getPetRegistry().getPetForClassId(rewardData.getRewardId());
/* 245 */           iconPath = petDesc.getIcon();
/*     */         } 
/*     */         
/* 248 */         BImage rewardIconImage = (BImage)TcgGame.getResourceManager().getResource(BImage.class, iconPath);
/*     */         
/* 250 */         ImageBackground rewardIconImgBgd = new ImageBackground(ImageBackgroundMode.SCALE_XY, rewardIconImage);
/* 251 */         if (this.rewardIcon.getParent() != null) {
/* 252 */           this.rewardIcon.getParent().remove((BComponent)this.rewardIcon);
/*     */         }
/* 254 */         this.rewardIcon.setBackground(0, (BBackground)rewardIconImgBgd);
/* 255 */         this.rewardIcon.setBackground(1, (BBackground)rewardIconImgBgd);
/* 256 */         add((BComponent)this.rewardIcon, new Rectangle(this.rewardIcon.getX(), this.rewardIcon.getY(), this.rewardIcon.getWidth(), this.rewardIcon.getHeight()));
/*     */       }
/*     */     
/* 259 */     } catch (NoLocatorException e) {
/*     */       
/* 261 */       e.printStackTrace();
/* 262 */       throw new RuntimeException("Missing image for destination portal: " + itemIconPath);
/*     */     } 
/*     */     
/* 265 */     ImageBackground imageBackground = new ImageBackground(ImageBackgroundMode.SCALE_XY, achievementIconImage);
/* 266 */     if (this.achievementIcon.getParent() != null) {
/* 267 */       this.achievementIcon.getParent().remove((BComponent)this.achievementIcon);
/*     */     }
/* 269 */     this.achievementIcon.setBackground(0, (BBackground)imageBackground);
/* 270 */     this.achievementIcon.setBackground(1, (BBackground)imageBackground);
/* 271 */     add((BComponent)this.achievementIcon, new Rectangle(this.achievementIcon.getX(), this.achievementIcon.getY(), this.achievementIcon.getWidth(), this.achievementIcon.getHeight()));
/*     */     
/* 273 */     this.maxAmount = 0;
/* 274 */     int currentAmount = 0;
/* 275 */     for (int i = 0; i < questData.getQuestObjectives().size(); i++) {
/* 276 */       ClientObjectiveTracker objective = questData.getQuestObjectives().get(i);
/* 277 */       this.maxAmount += objective.getCompleteValue();
/*     */     } 
/*     */     
/*     */     try {
/* 281 */       if (this.maxAmount > 1) {
/* 282 */         String iconTotal = String.valueOf(this.maxAmount);
/* 283 */         if (this.maxAmount >= 1000) {
/* 284 */           if (this.maxAmount >= 1000000) {
/* 285 */             iconTotal = String.valueOf((this.maxAmount / 1000000) + "M");
/*     */           } else {
/* 287 */             iconTotal = String.valueOf((this.maxAmount / 1000) + "K");
/*     */           } 
/*     */         }
/* 290 */         this.achievementIcon.setText(String.valueOf(iconTotal));
/*     */       } else {
/* 292 */         this.achievementIcon.setText("");
/*     */       } 
/* 294 */     } catch (IllegalArgumentException e) {
/* 295 */       System.out.println("Quest Name: " + questData.getQuestName() + ", max: " + this.maxAmount + ", current" + currentAmount);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\achievements\AchievementNotificationWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */