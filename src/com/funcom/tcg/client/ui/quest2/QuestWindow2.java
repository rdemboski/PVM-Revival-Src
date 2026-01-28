/*     */ package com.funcom.tcg.client.ui.quest2;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.peeler.BananaPeel;
/*     */ import com.funcom.rpgengine2.quests.reward.QuestRewardData;
/*     */ import com.funcom.rpgengine2.quests.reward.QuestRewardType;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPetDescription;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.BPeelWindow;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.client.ui.PeelWindowEvent;
/*     */ import com.funcom.tcg.client.ui.TCGToolTipManager;
/*     */ import com.funcom.tcg.client.ui.character.EquipmentButton;
/*     */ import com.funcom.tcg.client.ui.character.EquipmentModel;
/*     */ import com.funcom.tcg.client.ui.hud.ItemCard;
/*     */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*     */ import com.funcom.tcg.client.ui.pets3.AbstractPetButton;
/*     */ import com.funcom.tcg.client.ui.pets3.PetViewSelectionModel;
/*     */ import com.funcom.tcg.client.ui.pets3.PetWindowButtonModel;
/*     */ import com.funcom.tcg.client.ui.pets3.PetWindowPet;
/*     */ import com.funcom.tcg.client.ui.pets3.PetsWindowModel;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import com.jmex.bui.background.ImageBackground;
/*     */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.layout.CenterLayout;
/*     */ import com.jmex.bui.layout.HGroupLayout;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class QuestWindow2 extends BPeelWindow {
/*     */   private ResourceManager resourceManager;
/*     */   private TCGToolTipManager tooltipManager;
/*     */   private QuestWindowModel model;
/*     */   private BLabel questNameLabel;
/*     */   private BLabel questDescriptionLabel;
/*     */   private BLabel notOnQuestLabel;
/*  48 */   private BComponent[] missionIcons = new BComponent[4];
/*  49 */   private BLabel[] missionDescriptions = new BLabel[4];
/*  50 */   private BComponent[] missionChecks = new BComponent[4];
/*     */   
/*     */   private BComponent currencyIconLabel;
/*     */   private BLabel currencyLabel;
/*     */   private BComponent petTokenIconLabel;
/*     */   private BLabel petTokenLabel;
/*     */   private BLabel dailyQuestLabel;
/*     */   private BContainer rewardsContainer;
/*     */   private BComponent goalsHeader;
/*     */   private BComponent rewardsHeader;
/*     */   
/*     */   public QuestWindow2(String windowName, BananaPeel bananaPeel, ResourceManager resourceManager, QuestWindowModel model, TCGToolTipManager tooltipManager) {
/*  62 */     super(windowName, bananaPeel);
/*  63 */     setLayer(3);
/*  64 */     this.resourceManager = resourceManager;
/*  65 */     this.model = model;
/*  66 */     this.tooltipManager = tooltipManager;
/*     */     
/*  68 */     BananaResourceProvider buiResourceProvider = new BananaResourceProvider(resourceManager);
/*  69 */     this._style = BuiUtils.createStyleSheet("/peeler/window_quest_during.bss", buiResourceProvider);
/*     */     
/*  71 */     this.questNameLabel = (BLabel)findComponent((BContainer)this, "text_questname");
/*  72 */     this.questDescriptionLabel = (BLabel)findComponent((BContainer)this, "text_questdescription");
/*  73 */     this.notOnQuestLabel = (BLabel)findComponent((BContainer)this, "text_notonquest");
/*  74 */     this.dailyQuestLabel = (BLabel)findComponent((BContainer)this, "text_daily");
/*     */     
/*  76 */     this.currencyIconLabel = findComponent((BContainer)this, "reward_icon_currency");
/*  77 */     this.currencyLabel = (BLabel)findComponent((BContainer)this, "text_currencyamount_coin");
/*     */     
/*  79 */     this.petTokenIconLabel = findComponent((BContainer)this, "reward_pettoken");
/*  80 */     this.petTokenLabel = (BLabel)findComponent((BContainer)this, "text_currencyamount_token");
/*     */     
/*  82 */     this.missionIcons[0] = findComponent((BContainer)this, "quest_icon01");
/*  83 */     this.missionIcons[1] = findComponent((BContainer)this, "quest_icon02");
/*  84 */     this.missionIcons[2] = findComponent((BContainer)this, "quest_icon03");
/*  85 */     this.missionIcons[3] = findComponent((BContainer)this, "quest_icon04");
/*     */     
/*  87 */     this.missionDescriptions[0] = (BLabel)findComponent((BContainer)this, "text_goal01");
/*  88 */     this.missionDescriptions[1] = (BLabel)findComponent((BContainer)this, "text_goal02");
/*  89 */     this.missionDescriptions[2] = (BLabel)findComponent((BContainer)this, "text_goal03");
/*  90 */     this.missionDescriptions[3] = (BLabel)findComponent((BContainer)this, "text_goal04");
/*     */     
/*  92 */     this.missionChecks[0] = findComponent((BContainer)this, "quest_checkbox01");
/*  93 */     this.missionChecks[1] = findComponent((BContainer)this, "quest_checkbox02");
/*  94 */     this.missionChecks[2] = findComponent((BContainer)this, "quest_checkbox03");
/*  95 */     this.missionChecks[3] = findComponent((BContainer)this, "quest_checkbox04");
/*     */     
/*  97 */     this.rewardsContainer = (BContainer)findComponent((BContainer)this, "cont_reward");
/*  98 */     this.rewardsContainer.setLayoutManager((BLayoutManager)new HGroupLayout());
/*     */     
/* 100 */     this.goalsHeader = findComponent((BContainer)this, "text_goalsheader");
/* 101 */     this.rewardsHeader = findComponent((BContainer)this, "text_rewardsheader");
/*     */     
/* 103 */     Map<String, PeelWindowEvent[]> eventsWireMap = (Map)new HashMap<String, PeelWindowEvent>();
/* 104 */     eventsWireMap.put("button_accept", new PeelWindowEvent[] { new PeelWindowEvent("buttonOkPressed", PeelWindowEvent.PeelEventType.ACTION) });
/*     */     
/* 106 */     wireEvents(eventsWireMap, this);
/* 107 */     findComponent((BContainer)this, "button_accept").setTooltipText(TcgGame.getLocalizedText("tooltips.quest.ok", new String[0]));
/*     */   }
/*     */   
/*     */   public void refresh() {
/* 111 */     this.model.refresh();
/* 112 */     prepare(this.model.hasQuest());
/* 113 */     if (this.model.hasQuest()) {
/* 114 */       setQuestRewardCards(this.model.getQuestRewards());
/* 115 */       setQuestData(this.model.getQuestName(), this.model.getQuestText());
/* 116 */       refreshMissions();
/*     */     } else {
/* 118 */       clear();
/*     */     } 
/* 120 */     if (this.model.getDailyQuestLimit() == this.model.getDailyQuestMax()) {
/* 121 */       this.dailyQuestLabel.setText(TcgGame.getLocalizedText("questwindow.during.daily.nonecomplete", new String[0]));
/* 122 */     } else if (this.model.getDailyQuestMax() != 0) {
/* 123 */       this.dailyQuestLabel.setText(String.format(TcgGame.getLocalizedText("questwindow.during.daily.during", new String[0]), new Object[] { Integer.valueOf(this.model.getDailyQuestLimit()), Integer.valueOf(this.model.getDailyQuestMax()) }));
/*     */     }
/*     */     else {
/*     */       
/* 127 */       this.dailyQuestLabel.setText(TcgGame.getLocalizedText("questwindow.during.daily.allcomplete", new String[0]));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void clear() {
/* 132 */     for (int i = 0; i < 4; i++) {
/* 133 */       setMissionVisible(i, false);
/*     */     }
/*     */   }
/*     */   
/*     */   private void refreshMissions() {
/* 138 */     List<MissionObjective> missions = this.model.getCurrentMissionObjectives();
/* 139 */     for (int i = 0; i < 4; i++) {
/* 140 */       if (missions.size() > i) {
/*     */         
/* 142 */         setMissionVisible(i, true);
/* 143 */         this.missionChecks[i].setEnabled(!((MissionObjective)missions.get(i)).isCompleted());
/* 144 */         this.missionDescriptions[i].setText(((MissionObjective)missions.get(i)).getObjectiveText());
/* 145 */         BImage elementImage = (BImage)this.resourceManager.getResource(BImage.class, ((MissionObjective)missions.get(i)).getObjectiveIconPath());
/* 146 */         ImageBackground imgBackground = new ImageBackground(ImageBackgroundMode.SCALE_XY, elementImage);
/* 147 */         this.missionIcons[i].setBackground(0, (BBackground)imgBackground);
/* 148 */         this.missionIcons[i].setBackground(1, (BBackground)imgBackground);
/*     */       } else {
/*     */         
/* 151 */         setMissionVisible(i, false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setMissionVisible(int i, boolean visible) {
/* 157 */     this.missionDescriptions[i].setVisible(visible);
/* 158 */     this.missionIcons[i].setVisible(visible);
/* 159 */     this.missionChecks[i].setVisible(visible);
/*     */   }
/*     */   
/*     */   private void setQuestData(String questName, String questText) {
/* 163 */     this.questNameLabel.setText(questName);
/* 164 */     this.questDescriptionLabel.setText(questText);
/*     */   }
/*     */   
/*     */   private void setQuestRewardCards(List<QuestRewardData> questRewards) {
/* 168 */     for (QuestRewardData questReward : questRewards) {
/* 169 */       if (questReward.getType() == QuestRewardType.ITEM_REWARD.getId()) {
/* 170 */         ItemCard itemCard = null; if (questReward.getRewardId().equals("coin")) {
/* 171 */           setCurrencyVisible(true);
/* 172 */           this.currencyLabel.setText(String.valueOf(questReward.getAmount())); continue;
/* 173 */         }  if (questReward.getRewardId().equals("pet-token")) {
/* 174 */           setTokensVisible(true);
/* 175 */           this.petTokenLabel.setText(String.valueOf(questReward.getAmount()));
/*     */           continue;
/*     */         } 
/* 178 */         ClientItem clientItem = MainGameState.getItemRegistry().getItemForClassID(questReward.getRewardId(), questReward.getTier());
/*     */         
/* 180 */         clientItem.setAmount(questReward.getAmount());
/*     */ 
/*     */         
/* 183 */         if (clientItem.getItemType().isEquipable()) {
/* 184 */           EquipmentModel model = new EquipmentModel(this.resourceManager)
/*     */             {
/*     */               public boolean isSelected() {
/* 187 */                 return false;
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               public void setSelected(boolean selected) {}
/*     */             };
/* 195 */           model.setItem((InventoryItem)clientItem);
/* 196 */           EquipmentButton equipmentButton = new EquipmentButton(this.resourceManager, this.tooltipManager, model, null, true);
/* 197 */           equipmentButton.setSize(62, 62);
/* 198 */           equipmentButton.setPreferredSize(62, 62);
/*     */         } else {
/* 200 */           itemCard = new ItemCard((InventoryItem)clientItem);
/*     */         } 
/*     */         
/* 203 */         BContainer container = new BContainer((BLayoutManager)new CenterLayout());
/* 204 */         container.setSize(62, 62);
/* 205 */         container.add((BComponent)itemCard);
/* 206 */         this.rewardsContainer.add((BComponent)container); continue;
/*     */       } 
/* 208 */       if (questReward.getType() == QuestRewardType.PET_REWARD.getId()) {
/* 209 */         ClientPetDescription petDesc = MainGameState.getPetRegistry().getPetForClassId(questReward.getRewardId());
/* 210 */         PetWindowPet pet = new PetWindowPet(petDesc);
/* 211 */         ClientPetDescription updatedClientPetDescription = MainGameState.getPetRegistry().getPetForClassId(questReward.getRewardId() + "-upgrade");
/* 212 */         ClientPet playerPet = new ClientPet(petDesc, (updatedClientPetDescription == null) ? petDesc : updatedClientPetDescription);
/* 213 */         playerPet.setLevel(petDesc.getTier());
/* 214 */         pet.setPlayerPet(playerPet);
/* 215 */         PetWindowButtonModel buttonModel = new PetWindowButtonModel(this.resourceManager, null, null, pet)
/*     */           {
/*     */             public boolean isSelected() {
/* 218 */               return false;
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             public void setSelected(boolean selected) {}
/*     */           };
/* 226 */         AbstractPetButton button = new AbstractPetButton(buttonModel, this.tooltipManager, MainGameState.getPlayerModel().isSubscriber())
/*     */           {
/*     */             protected void addListeners() {}
/*     */           };
/*     */ 
/*     */ 
/*     */         
/* 233 */         button.wasCollected(true);
/* 234 */         button.setSize(72, 103);
/* 235 */         button.setPreferredSize(72, 103);
/*     */         
/* 237 */         BContainer container = new BContainer((BLayoutManager)new CenterLayout());
/* 238 */         container.setSize(87, 103);
/* 239 */         container.add((BComponent)button);
/* 240 */         this.rewardsContainer.add((BComponent)container);
/* 241 */         button.update(0L);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setTokensVisible(boolean visible) {
/* 247 */     this.petTokenIconLabel.setVisible(visible);
/* 248 */     this.petTokenLabel.setVisible(visible);
/*     */   }
/*     */   
/*     */   private void setCurrencyVisible(boolean visible) {
/* 252 */     this.currencyIconLabel.setVisible(visible);
/* 253 */     this.currencyLabel.setVisible(visible);
/*     */   }
/*     */   
/*     */   private void prepare(boolean hasQuests) {
/* 257 */     this.questNameLabel.setVisible(hasQuests);
/* 258 */     this.questDescriptionLabel.setVisible(hasQuests);
/* 259 */     this.goalsHeader.setVisible(hasQuests);
/* 260 */     this.rewardsHeader.setVisible(hasQuests);
/*     */     
/* 262 */     setCurrencyVisible(false);
/* 263 */     setTokensVisible(false);
/*     */     
/* 265 */     this.rewardsContainer.removeAll();
/* 266 */     this.rewardsContainer.setVisible(hasQuests);
/*     */     
/* 268 */     this.notOnQuestLabel.setVisible(!hasQuests);
/*     */   }
/*     */ 
/*     */   
/*     */   public void buttonOkPressed(BPeelWindow window, BComponent component) {
/* 273 */     close();
/* 274 */     if (MainGameState.getPauseModel().isPaused()) {
/* 275 */       MainGameState.getPauseModel().reset();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void close() {
/* 281 */     PanelManager.getInstance().closeWindow((BWindow)this);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\quest2\QuestWindow2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */