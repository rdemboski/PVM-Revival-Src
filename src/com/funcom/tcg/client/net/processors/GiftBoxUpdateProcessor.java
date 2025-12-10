/*     */ package com.funcom.tcg.client.net.processors;
/*     */ 
/*     */ import com.funcom.rpgengine2.quests.reward.QuestRewardData;
/*     */ import com.funcom.server.common.GameIOHandler;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientGiftBox;
/*     */ import com.funcom.tcg.client.model.rpg.GiftBoxCollection;
/*     */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*     */ import com.funcom.tcg.client.net.CreatureData;
/*     */ import com.funcom.tcg.client.net.MessageProcessor;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.Localizer;
/*     */ import com.funcom.tcg.client.ui.giftbox.GiftBoxModel;
/*     */ import com.funcom.tcg.client.ui.giftbox.GiftBoxWindow;
/*     */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*     */ import com.funcom.tcg.net.message.GiftBoxUpdateMessage;
/*     */ import com.funcom.tcg.rpg.GiftBoxDescription;
/*     */ import com.funcom.tcg.rpg.TCGGiftBoxManager;
/*     */ import com.jmex.bui.BWindow;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GiftBoxUpdateProcessor
/*     */   implements MessageProcessor, Localizer
/*     */ {
/*     */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/*  30 */     GiftBoxUpdateMessage updateMessage = (GiftBoxUpdateMessage)message;
/*     */     
/*  32 */     LocalClientPlayer localClientPlayer = MainGameState.getPlayerModel();
/*  33 */     GiftBoxCollection giftBoxCollection = localClientPlayer.getGiftBoxes();
/*     */     
/*  35 */     if (updateMessage.isActive()) {
/*  36 */       ClientGiftBox giftBox = ensureGiftBox(updateMessage, giftBoxCollection);
/*     */       
/*  38 */       giftBox.update(updateMessage.getUntilOpenMillis());
/*     */     } else {
/*     */       
/*  41 */       giftBoxCollection.remove(updateMessage.getGiftBoxId());
/*     */       
/*  43 */       if (updateMessage.getRemoveReason() == GiftBoxUpdateMessage.UpdateType.OPENED) {
/*  44 */         TCGGiftBoxManager giftBoxManager = TcgGame.getRpgLoader().getGiftBoxManager();
/*     */         
/*  46 */         GiftBoxDescription giftBoxDescription = giftBoxManager.getDescription(updateMessage.getGiftBoxDescriptionId());
/*     */         
/*  48 */         GiftBoxWindow giftBoxWindow = new GiftBoxWindow(new GiftBoxModelImpl(giftBoxDescription.getName(), updateMessage.getRewardDataList(), giftBoxDescription.getIconPathUnlocked()), TcgGame.getResourceManager());
/*     */         
/*  50 */         PanelManager.getInstance().addWindow((BWindow)giftBoxWindow);
/*  51 */         giftBoxWindow.refresh();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private ClientGiftBox ensureGiftBox(GiftBoxUpdateMessage updateMessage, GiftBoxCollection giftBoxCollection) {
/*  57 */     ClientGiftBox giftBox = giftBoxCollection.get(updateMessage.getGiftBoxId());
/*     */     
/*  59 */     if (giftBox == null) {
/*  60 */       TCGGiftBoxManager giftBoxManager = TcgGame.getRpgLoader().getGiftBoxManager();
/*  61 */       GiftBoxDescription giftBoxDescription = giftBoxManager.getDescription(updateMessage.getGiftBoxDescriptionId());
/*  62 */       giftBox = new ClientGiftBox(updateMessage.getGiftBoxId(), giftBoxDescription.getIconPathLocked(), giftBoxDescription.getIconPathUnlocked(), this);
/*     */ 
/*     */ 
/*     */       
/*  66 */       giftBoxCollection.put(giftBox);
/*     */     } 
/*     */     
/*  69 */     return giftBox;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLocalizedText(Class<?> clazz, String key, String... parameters) {
/*  74 */     return TcgGame.getLocalizedText(key, parameters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getMessageType() {
/* 112 */     return 235;
/*     */   }
/*     */   
/*     */   private static class GiftBoxModelImpl implements GiftBoxModel {
/*     */     private final List<QuestRewardData> dataList;
/*     */     private String iconPathUnlocked;
/*     */     private final String title;
/*     */     
/*     */     public GiftBoxModelImpl(String title, List<QuestRewardData> dataList, String iconPathUnlocked) {
/* 121 */       this.title = title;
/* 122 */       this.dataList = dataList;
/* 123 */       this.iconPathUnlocked = iconPathUnlocked;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getTitle() {
/* 128 */       return this.title;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<QuestRewardData> getRewardDataList() {
/* 133 */       return this.dataList;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getIcon() {
/* 138 */       return this.iconPathUnlocked;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\GiftBoxUpdateProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */