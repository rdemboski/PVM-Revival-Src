/*     */ package com.funcom.tcg.client.ui.quest2;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.rpgengine2.quests.reward.QuestRewardData;
/*     */ import com.funcom.rpgengine2.quests.reward.QuestRewardType;
/*     */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPetDescription;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.TCGToolTipManager;
/*     */ import com.funcom.tcg.client.ui.character.EquipmentButton;
/*     */ import com.funcom.tcg.client.ui.character.EquipmentModel;
/*     */ import com.funcom.tcg.client.ui.hud.ItemCard;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*     */ import com.funcom.tcg.client.ui.pets3.AbstractPetButton;
/*     */ import com.funcom.tcg.client.ui.pets3.PetViewSelectionModel;
/*     */ import com.funcom.tcg.client.ui.pets3.PetWindowButtonModel;
/*     */ import com.funcom.tcg.client.ui.pets3.PetWindowPet;
/*     */ import com.funcom.tcg.client.ui.pets3.PetsWindowModel;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.layout.HGroupLayout;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.util.List;
/*     */ 
/*     */ public class RewardCardsContainer extends BContainer {
/*     */   private ResourceManager resourceManager;
/*     */   private TCGToolTipManager tooltipManager;
/*     */   
/*     */   public RewardCardsContainer(ResourceManager resourceManager, TCGToolTipManager tooltipManager) {
/*  33 */     this.resourceManager = resourceManager;
/*  34 */     this.tooltipManager = tooltipManager;
/*     */   }
/*     */   
/*     */   public void setCardDataList(List<QuestRewardData> questRewards) {
/*  38 */     removeAll();
/*  39 */     setLayoutManager((BLayoutManager)new HGroupLayout());
/*     */     
/*  41 */     setQuestRewardCards(questRewards);
/*     */   }
/*     */   
/*     */   private void setQuestRewardCards(List<QuestRewardData> questRewards) {
/*  45 */     for (QuestRewardData questReward : questRewards) {
/*  46 */       if (questReward.getType() == QuestRewardType.ITEM_REWARD.getId()) {
/*     */         ItemCard itemCard;
/*  48 */         ClientItem clientItem = MainGameState.getItemRegistry().getItemForClassID(questReward.getRewardId(), questReward.getTier());
/*     */         
/*  50 */         clientItem.setAmount(questReward.getAmount());
/*     */ 
/*     */         
/*  53 */         if (clientItem.getItemType().isEquipable()) {
/*  54 */           EquipmentModel model = new EquipmentModel(this.resourceManager)
/*     */             {
/*     */               public boolean isSelected() {
/*  57 */                 return false;
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               public void setSelected(boolean selected) {}
/*     */             };
/*  65 */           model.setItem((InventoryItem)clientItem);
/*  66 */           EquipmentButton equipmentButton = new EquipmentButton(this.resourceManager, this.tooltipManager, model, null, true);
/*  67 */           equipmentButton.setSize(62, 62);
/*  68 */           equipmentButton.setPreferredSize(62, 62);
/*     */         } else {
/*  70 */           itemCard = new ItemCard((InventoryItem)clientItem);
/*     */         } 
/*     */         
/*  73 */         BContainer container = new BContainer((BLayoutManager)new AbsoluteLayout());
/*  74 */         container.setPreferredSize(62, 62);
/*  75 */         container.setSize(62, 62);
/*  76 */         container.add((BComponent)itemCard, new Rectangle(container.getWidth() / 2 - itemCard.getWidth() / 2, container.getHeight() / 2 - itemCard.getHeight() / 2, itemCard.getWidth(), itemCard.getHeight()));
/*     */         
/*  78 */         add((BComponent)container); continue;
/*  79 */       }  if (questReward.getType() == QuestRewardType.PET_REWARD.getId()) {
/*  80 */         ClientPetDescription petDesc = MainGameState.getPetRegistry().getPetForClassId(questReward.getRewardId());
/*  81 */         PetWindowPet pet = new PetWindowPet(petDesc);
/*  82 */         ClientPetDescription updatedClientPetDescription = MainGameState.getPetRegistry().getPetForClassId(questReward.getRewardId() + "-upgrade");
/*  83 */         ClientPet playerPet = new ClientPet(petDesc, (updatedClientPetDescription == null) ? petDesc : updatedClientPetDescription);
/*  84 */         playerPet.setLevel(petDesc.getTier());
/*  85 */         pet.setPlayerPet(playerPet);
/*  86 */         PetWindowButtonModel buttonModel = new PetWindowButtonModel(this.resourceManager, null, null, pet)
/*     */           {
/*     */             public boolean isSelected() {
/*  89 */               return false;
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             public void setSelected(boolean selected) {}
/*     */           };
/*  97 */         AbstractPetButton button = new AbstractPetButton(buttonModel, this.tooltipManager, MainGameState.getPlayerModel().isSubscriber())
/*     */           {
/*     */             protected void addListeners() {}
/*     */           };
/*     */ 
/*     */ 
/*     */         
/* 104 */         button.wasCollected(true);
/* 105 */         button.setSize(75, 108);
/* 106 */         button.setPreferredSize(75, 108);
/*     */         
/* 108 */         BContainer container = new BContainer((BLayoutManager)new AbsoluteLayout());
/* 109 */         container.setStyleClass("reward-cards-container");
/* 110 */         container.setPreferredSize(75, 108);
/* 111 */         container.setSize(75, 108);
/* 112 */         container.add((BComponent)button, new Rectangle(container.getWidth() / 2 - button.getWidth() / 2, container.getHeight() / 2 - button.getHeight() / 2, button.getWidth(), button.getHeight()));
/*     */         
/* 114 */         add((BComponent)container);
/* 115 */         button.update(0L);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\quest2\RewardCardsContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */