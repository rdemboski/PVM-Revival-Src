/*     */ package com.funcom.tcg.client.ui.hud;
/*     */ import com.funcom.gameengine.WorldCoordinate;
import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.model.props.UpdateListener;
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
import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.client.ui.GeneralCloseWindowListener;
/*     */ import com.funcom.tcg.client.ui.PeelWindowEvent;
/*     */ import com.funcom.tcg.client.ui.TCGToolTipManager;
/*     */ import com.funcom.tcg.client.ui.character.EquipmentButton;
/*     */ import com.funcom.tcg.client.ui.character.EquipmentModel;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*     */ import com.funcom.tcg.client.ui.pets3.AbstractPetButton;
/*     */ import com.funcom.tcg.client.ui.pets3.PetViewSelectionModel;
/*     */ import com.funcom.tcg.client.ui.pets3.PetWindowButtonModel;
/*     */ import com.funcom.tcg.client.ui.pets3.PetWindowPet;
/*     */ import com.funcom.tcg.client.ui.pets3.PetsWindowModel;
/*     */ import com.funcom.tcg.client.ui.quest2.MissionObjective;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BWindow;
import com.jmex.bui.BuiSystem;
import com.jmex.bui.background.BBackground;
/*     */ import com.jmex.bui.background.ImageBackground;
import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.layout.CenterLayout;
import com.jmex.bui.layout.HGroupLayout;

import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class QuestPickUpWindow2 extends BPeelWindow {
/*     */   private ResourceManager resourceManager;
/*     */   private TCGToolTipManager tooltipManager;
/*  41 */   private BComponent[] missionIcons = new BComponent[4]; private QuestPickupModel model; private BLabel questNameLabel; private BLabel questDescriptionLabel;
/*  42 */   private BLabel[] missionDescriptions = new BLabel[4];
/*  43 */   private BComponent[] missionChecks = new BComponent[4];
/*     */   
/*     */   private BComponent currencyIconLabel;
/*     */   private BLabel currencyLabel;
/*     */   private BComponent petTokenIconLabel;
/*     */   private BLabel petTokenLabel;
/*     */   private BContainer rewardsContainer;
/*     */   private BComponent acceptQuestButton;
/*     */   private BComponent switchQuestButton;
/*     */   private BComponent keepQuestButton;
/*     */   private GeneralCloseWindowListener closeListener;
/*     */   
/*     */   public QuestPickUpWindow2(String windowName, BananaPeel bananaPeel, ResourceManager resourceManager, QuestPickupModel model, TCGToolTipManager tooltipManager, WorldCoordinate worldCoordinate) {
/*  56 */     super(windowName, bananaPeel);
/*  57 */     if (TcgGame.isStartDuelMode()) {
/*  58 */       TcgGame.setStartDuelMode(false);
/*     */     }
/*     */     
/*  61 */     setLayer(3);
/*  62 */     this.resourceManager = resourceManager;
/*  63 */     this.model = model;
/*  64 */     this.tooltipManager = tooltipManager;
/*  65 */     setLocation(0, -30);
/*     */     
/*  67 */     this.closeListener = new GeneralCloseWindowListener(worldCoordinate, QuestPickUpWindow2.class);
/*  68 */     ((InteractibleProp)MainGameState.getPlayerNode().getProp()).addUpdateListener((UpdateListener)this.closeListener);
/*     */     
/*  70 */     BananaResourceProvider buiResourceProvider = new BananaResourceProvider(resourceManager);
/*  71 */     this._style = BuiUtils.createStyleSheet("/peeler/window_quest_accept.bss", buiResourceProvider);
/*     */     
/*  73 */     this.questNameLabel = (BLabel)findComponent((BContainer)this, "text_questname");
/*  74 */     this.questDescriptionLabel = (BLabel)findComponent((BContainer)this, "text_questdescription");
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
/* 100 */     this.acceptQuestButton = findComponent((BContainer)this, "button_acceptquest");
/* 101 */     this.switchQuestButton = findComponent((BContainer)this, "button_getnewquest");
/* 102 */     this.keepQuestButton = findComponent((BContainer)this, "button_keepquest");
/*     */     
/* 104 */     Map<String, PeelWindowEvent[]> eventsWireMap = (Map)new HashMap<String, PeelWindowEvent>();
/* 105 */     eventsWireMap.put("button_acceptquest", new PeelWindowEvent[] { new PeelWindowEvent("accept", PeelWindowEvent.PeelEventType.ACTION) });
/*     */     
/* 107 */     eventsWireMap.put("button_getnewquest", new PeelWindowEvent[] { new PeelWindowEvent("accept", PeelWindowEvent.PeelEventType.ACTION) });
/*     */     
/* 109 */     eventsWireMap.put("button_keepquest", new PeelWindowEvent[] { new PeelWindowEvent("close", PeelWindowEvent.PeelEventType.ACTION) });
/*     */     
/* 111 */     wireEvents(eventsWireMap, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 116 */     super.setVisible(visible);
/* 117 */     if (!visible) {
/* 118 */       ((InteractibleProp)MainGameState.getPlayerNode().getProp()).removeUpdateListener((UpdateListener)this.closeListener);
/*     */     }
/*     */   }
/*     */   
/*     */   public void refresh(boolean allowForOtherQuests) {
/* 123 */     this.model.refresh();
/* 124 */     prepare((allowForOtherQuests && this.model.hasOtherQuest()));
/* 125 */     setQuestRewardCards(this.model.getQuestRewards());
/* 126 */     setQuestData(this.model.getQuestName(), this.model.getQuestText());
/* 127 */     refreshMissions();
/*     */   }
/*     */   
/*     */   private void refreshMissions() {
/* 131 */     List<MissionObjective> missions = this.model.getCurrentMissionObjectives();
/* 132 */     for (int i = 0; i < 4; i++) {
/* 133 */       if (missions.size() > i) {
/*     */         
/* 135 */         setMissionVisible(i, true);
/* 136 */         this.missionChecks[i].setEnabled(!((MissionObjective)missions.get(i)).isCompleted());
/* 137 */         this.missionDescriptions[i].setText(((MissionObjective)missions.get(i)).getObjectiveText());
/* 138 */         BImage elementImage = (BImage)this.resourceManager.getResource(BImage.class, ((MissionObjective)missions.get(i)).getObjectiveIconPath());
/* 139 */         ImageBackground imgBackground = new ImageBackground(ImageBackgroundMode.SCALE_XY, elementImage);
/* 140 */         this.missionIcons[i].setBackground(0, (BBackground)imgBackground);
/* 141 */         this.missionIcons[i].setBackground(1, (BBackground)imgBackground);
/*     */       } else {
/*     */         
/* 144 */         setMissionVisible(i, false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setMissionVisible(int i, boolean visible) {
/* 150 */     this.missionDescriptions[i].setVisible(visible);
/* 151 */     this.missionIcons[i].setVisible(visible);
/* 152 */     this.missionChecks[i].setVisible(visible);
/*     */   }
/*     */   
/*     */   private void setQuestData(String questName, String questText) {
/* 156 */     this.questNameLabel.setText(questName);
/* 157 */     this.questDescriptionLabel.setText(questText);
/*     */   }
/*     */   
/*     */   private void setTokensVisible(boolean visible) {
/* 161 */     this.petTokenIconLabel.setVisible(visible);
/* 162 */     this.petTokenLabel.setVisible(visible);
/*     */   }
/*     */   
/*     */   private void setCurrencyVisible(boolean visible) {
/* 166 */     this.currencyIconLabel.setVisible(visible);
/* 167 */     this.currencyLabel.setVisible(visible);
/*     */   }
/*     */   
/*     */   private void prepare(boolean hasQuests) {
/* 171 */     setCurrencyVisible(false);
/* 172 */     setTokensVisible(false);
/*     */     
/* 174 */     this.rewardsContainer.removeAll();
/*     */     
/* 176 */     this.acceptQuestButton.setVisible(!hasQuests);
/* 177 */     this.switchQuestButton.setVisible(hasQuests);
/* 178 */     this.keepQuestButton.setVisible(hasQuests);
/*     */   }
/*     */   
/*     */   private void setQuestRewardCards(List<QuestRewardData> questRewards) {
/* 182 */     for (QuestRewardData questReward : questRewards) {
/* 183 */       if (questReward.getType() == QuestRewardType.ITEM_REWARD.getId()) {
/* 184 */         ItemCard itemCard = null; if (questReward.getRewardId().equals("coin")) {
/* 185 */           setCurrencyVisible(true);
/* 186 */           this.currencyLabel.setText(String.valueOf(questReward.getAmount())); continue;
/* 187 */         }  if (questReward.getRewardId().equals("pet-token")) {
/* 188 */           setTokensVisible(true);
/* 189 */           this.petTokenLabel.setText(String.valueOf(questReward.getAmount()));
/*     */           continue;
/*     */         } 
/* 192 */         ClientItem clientItem = MainGameState.getItemRegistry().getItemForClassID(questReward.getRewardId(), questReward.getTier());
/*     */         
/* 194 */         clientItem.setAmount(questReward.getAmount());
/*     */ 
/*     */         
/* 197 */         if (clientItem.getItemType().isEquipable()) {
/* 198 */           EquipmentModel model = new EquipmentModel(this.resourceManager)
/*     */             {
/*     */               public boolean isSelected() {
/* 201 */                 return false;
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               public void setSelected(boolean selected) {}
/*     */             };
/* 209 */           model.setItem((InventoryItem)clientItem);
/* 210 */           EquipmentButton equipmentButton = new EquipmentButton(this.resourceManager, this.tooltipManager, model, null, true);
/* 211 */           equipmentButton.setSize(62, 62);
/* 212 */           equipmentButton.setPreferredSize(62, 62);
/*     */         } else {
/* 214 */           itemCard = new ItemCard((InventoryItem)clientItem);
/*     */         } 
/*     */         
/* 217 */         BContainer container = new BContainer((BLayoutManager)new CenterLayout());
/* 218 */         container.setSize(62, 62);
/* 219 */         container.add((BComponent)itemCard);
/* 220 */         this.rewardsContainer.add((BComponent)container); continue;
/*     */       } 
/* 222 */       if (questReward.getType() == QuestRewardType.PET_REWARD.getId()) {
/* 223 */         ClientPetDescription petDesc = MainGameState.getPetRegistry().getPetForClassId(questReward.getRewardId());
/* 224 */         PetWindowPet pet = new PetWindowPet(petDesc);
/* 225 */         ClientPetDescription updatedClientPetDescription = MainGameState.getPetRegistry().getPetForClassId(questReward.getRewardId() + "-upgrade");
/* 226 */         ClientPet playerPet = new ClientPet(petDesc, (updatedClientPetDescription == null) ? petDesc : updatedClientPetDescription);
/* 227 */         playerPet.setLevel(petDesc.getTier());
/* 228 */         pet.setPlayerPet(playerPet);
/* 229 */         PetWindowButtonModel buttonModel = new PetWindowButtonModel(this.resourceManager, null, null, pet)
/*     */           {
/*     */             public boolean isSelected() {
/* 232 */               return false;
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             public void setSelected(boolean selected) {}
/*     */           };
/* 240 */         AbstractPetButton button = new AbstractPetButton(buttonModel, this.tooltipManager, MainGameState.getPlayerModel().isSubscriber())
/*     */           {
/*     */             protected void addListeners() {}
/*     */           };
/*     */ 
/*     */ 
/*     */         
/* 247 */         button.wasCollected(true);
/* 248 */         button.setSize(72, 103);
/* 249 */         button.setPreferredSize(72, 103);
/*     */         
/* 251 */         BContainer container = new BContainer((BLayoutManager)new CenterLayout());
/* 252 */         container.setSize(87, 103);
/* 253 */         container.add((BComponent)button);
/* 254 */         this.rewardsContainer.add((BComponent)container);
/* 255 */         button.update(0L);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(BPeelWindow window, BComponent component) {
/* 262 */     this.model.questAccepted();
/* 263 */     PanelManager.getInstance().closeWindow((BWindow)this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close(BPeelWindow window, BComponent component) {
/* 269 */     if (BuiSystem.getRootNode().getAllWindows().contains(window))
/* 270 */       BuiSystem.removeWindow((BWindow)window); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud\QuestPickUpWindow2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */