/*     */ package com.funcom.tcg.client.ui.hud;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
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
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
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
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.layout.CenterLayout;
/*     */ import com.jmex.bui.layout.HGroupLayout;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class QuestFinishWindow2
/*     */   extends BPeelWindow {
/*     */   private ResourceManager resourceManager;
/*     */   private TCGToolTipManager tooltipManager;
/*     */   private QuestFinishModel model;
/*     */   private BLabel questNameLabel;
/*     */   private BLabel questDescriptionLabel;
/*     */   
/*     */   public QuestFinishWindow2(String windowName, BananaPeel bananaPeel, ResourceManager resourceManager, QuestFinishModel model, TCGToolTipManager tooltipManager, WorldCoordinate worldCoordinate) {
/*  50 */     super(windowName, bananaPeel);
/*  51 */     if (TcgGame.isStartDuelMode()) {
/*  52 */       TcgGame.setStartDuelMode(false);
/*     */     }
/*  54 */     this.resourceManager = resourceManager;
/*  55 */     this.model = model;
/*  56 */     this.tooltipManager = tooltipManager;
/*  57 */     setLocation(0, -30);
/*     */ 
/*     */     
/*  60 */     this.closeListener = new GeneralCloseWindowListener(worldCoordinate, QuestFinishWindow2.class);
/*  61 */     ((InteractibleProp)MainGameState.getPlayerNode().getProp()).addUpdateListener((UpdateListener)this.closeListener);
/*     */     
/*  63 */     BananaResourceProvider buiResourceProvider = new BananaResourceProvider(resourceManager);
/*  64 */     this._style = BuiUtils.createStyleSheet("/peeler/window_quest_finish.bss", buiResourceProvider);
/*     */     
/*  66 */     this.questNameLabel = (BLabel)findComponent((BContainer)this, "text_questname");
/*  67 */     this.questDescriptionLabel = (BLabel)findComponent((BContainer)this, "text_questdescription");
/*     */     
/*  69 */     this.currencyIconLabel = findComponent((BContainer)this, "reward_icon_currency");
/*  70 */     this.currencyLabel = (BLabel)findComponent((BContainer)this, "text_currencyamount_coin");
/*     */     
/*  72 */     this.petTokenIconLabel = findComponent((BContainer)this, "reward_pettoken");
/*  73 */     this.petTokenLabel = (BLabel)findComponent((BContainer)this, "text_currencyamount_token");
/*     */     
/*  75 */     this.rewardsContainer = (BContainer)findComponent((BContainer)this, "cont_reward");
/*  76 */     this.rewardsContainer.setLayoutManager((BLayoutManager)new HGroupLayout());
/*     */     
/*  78 */     Map<String, PeelWindowEvent[]> eventsWireMap = (Map)new HashMap<String, PeelWindowEvent>();
/*  79 */     eventsWireMap.put("button_accept", new PeelWindowEvent[] { new PeelWindowEvent("accept", PeelWindowEvent.PeelEventType.ACTION) });
/*     */     
/*  81 */     wireEvents(eventsWireMap, this);
/*  82 */     findComponent((BContainer)this, "button_accept").setTooltipText(TcgGame.getLocalizedText("tooltips.quest.ok", new String[0]));
/*     */   }
/*     */   private BComponent currencyIconLabel; private BLabel currencyLabel; private BComponent petTokenIconLabel; private BLabel petTokenLabel; private BContainer rewardsContainer; private GeneralCloseWindowListener closeListener;
/*     */   
/*     */   public void setVisible(boolean visible) {
/*  87 */     super.setVisible(visible);
/*  88 */     if (!visible) {
/*  89 */       ((InteractibleProp)MainGameState.getPlayerNode().getProp()).removeUpdateListener((UpdateListener)this.closeListener);
/*     */     }
/*     */   }
/*     */   
/*     */   public void refresh() {
/*  94 */     this.model.refresh();
/*  95 */     prepare();
/*  96 */     setQuestRewardCards(this.model.getQuestRewards());
/*  97 */     setQuestData(this.model.getQuestName(), this.model.getQuestText());
/*     */   }
/*     */   
/*     */   private void setQuestData(String questName, String questText) {
/* 101 */     this.questNameLabel.setText(questName);
/* 102 */     this.questDescriptionLabel.setText(questText);
/*     */   }
/*     */   
/*     */   private void setQuestRewardCards(List<QuestRewardData> questRewards) {
/* 106 */     for (QuestRewardData questReward : questRewards) {
/* 107 */       if (questReward.getType() == QuestRewardType.ITEM_REWARD.getId()) {
/* 108 */         ItemCard itemCard; if (questReward.getRewardId().equals("coin")) {
/* 109 */           setCurrencyVisible(true);
/* 110 */           this.currencyLabel.setText(String.valueOf(questReward.getAmount())); continue;
/* 111 */         }  if (questReward.getRewardId().equals("pet-token")) {
/* 112 */           setTokensVisible(true);
/* 113 */           this.petTokenLabel.setText(String.valueOf(questReward.getAmount()));
/*     */           continue;
/*     */         } 
/* 116 */         ClientItem clientItem = MainGameState.getItemRegistry().getItemForClassID(questReward.getRewardId(), questReward.getTier());
/*     */         
/* 118 */         clientItem.setAmount(questReward.getAmount());
/*     */ 
/*     */         
/* 121 */         if (clientItem.getItemType().isEquipable()) {
/* 122 */           EquipmentModel model = new EquipmentModel(this.resourceManager)
/*     */             {
/*     */               public boolean isSelected() {
/* 125 */                 return false;
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               public void setSelected(boolean selected) {}
/*     */             };
/* 133 */           model.setItem((InventoryItem)clientItem);
/* 134 */           EquipmentButton equipmentButton = new EquipmentButton(this.resourceManager, this.tooltipManager, model, null, true);
/* 135 */           equipmentButton.setSize(62, 62);
/* 136 */           equipmentButton.setPreferredSize(62, 62);
/*     */         } else {
/* 138 */           itemCard = new ItemCard((InventoryItem)clientItem);
/*     */         } 
/*     */         
/* 141 */         BContainer container = new BContainer((BLayoutManager)new CenterLayout());
/* 142 */         container.setSize(62, 62);
/* 143 */         container.add((BComponent)itemCard);
/* 144 */         this.rewardsContainer.add((BComponent)container); continue;
/*     */       } 
/* 146 */       if (questReward.getType() == QuestRewardType.PET_REWARD.getId()) {
/* 147 */         ClientPetDescription clientPetDescription = MainGameState.getPetRegistry().getPetForClassId(questReward.getRewardId());
/* 148 */         PetWindowPet pet = new PetWindowPet(clientPetDescription);
/* 149 */         ClientPetDescription updatedClientPetDescription = MainGameState.getPetRegistry().hasPetForClassId(questReward.getRewardId() + "-upgrade") ? MainGameState.getPetRegistry().getPetForClassId(questReward.getRewardId() + "-upgrade") : clientPetDescription;
/* 150 */         ClientPet playerPet = new ClientPet(clientPetDescription, (updatedClientPetDescription == null) ? clientPetDescription : updatedClientPetDescription);
/* 151 */         playerPet.setLevel(clientPetDescription.getTier());
/* 152 */         pet.setPlayerPet(playerPet);
/* 153 */         PetWindowButtonModel buttonModel = new PetWindowButtonModel(this.resourceManager, null, null, pet)
/*     */           {
/*     */             public boolean isSelected() {
/* 156 */               return false;
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             public void setSelected(boolean selected) {}
/*     */           };
/* 164 */         AbstractPetButton button = new AbstractPetButton(buttonModel, this.tooltipManager, MainGameState.getPlayerModel().isSubscriber())
/*     */           {
/*     */             protected void addListeners() {}
/*     */           };
/*     */ 
/*     */ 
/*     */         
/* 171 */         button.wasCollected(true);
/* 172 */         button.setSize(72, 103);
/* 173 */         button.setPreferredSize(72, 103);
/*     */         
/* 175 */         BContainer container = new BContainer((BLayoutManager)new CenterLayout());
/* 176 */         container.setSize(87, 103);
/* 177 */         container.add((BComponent)button);
/* 178 */         this.rewardsContainer.add((BComponent)container);
/* 179 */         button.update(0L);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setTokensVisible(boolean visible) {
/* 185 */     this.petTokenIconLabel.setVisible(visible);
/* 186 */     this.petTokenLabel.setVisible(visible);
/*     */   }
/*     */   
/*     */   private void setCurrencyVisible(boolean visible) {
/* 190 */     this.currencyIconLabel.setVisible(visible);
/* 191 */     this.currencyLabel.setVisible(visible);
/*     */   }
/*     */   
/*     */   private void prepare() {
/* 195 */     setCurrencyVisible(false);
/* 196 */     setTokensVisible(false);
/*     */     
/* 198 */     this.rewardsContainer.removeAll();
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(BPeelWindow window, BComponent component) {
/* 203 */     this.model.finish();
/* 204 */     if (BuiSystem.getRootNode().getAllWindows().contains(window))
/* 205 */       BuiSystem.removeWindow((BWindow)window); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud\QuestFinishWindow2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */