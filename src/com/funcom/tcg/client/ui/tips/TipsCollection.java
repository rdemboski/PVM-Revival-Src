/*    */ package com.funcom.tcg.client.ui.tips;
/*    */ import com.funcom.rpgengine2.items.ItemDescription;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.rpg.GiftBoxCollection;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.hud2.AbstractHudModel;
/*    */ import com.funcom.tcg.client.ui.hud2.HudModel;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class TipsCollection {
/* 15 */   private final Map<String, TipInfoModel> tips = new HashMap<String, TipInfoModel>();
/* 16 */   private final List<String> activatedTips = new ArrayList<String>();
/*    */   private GiftBoxCollection.ChangeListener giftBoxChangeListener;
/*    */   private String userName;
/*    */   private AbstractHudModel.ChangeListenerAdapter changeListener;
/*    */   
/*    */   public TipsCollection(Map<String, Boolean> tipMap, String userName) {
/* 22 */     this.userName = userName;
/* 23 */     Set<String> keySet = tipMap.keySet();
/* 24 */     for (String key : keySet) {
/* 25 */       if (((Boolean)tipMap.get(key)).booleanValue()) {
/* 26 */         this.activatedTips.add(key); continue;
/*    */       } 
/* 28 */       if (!TcgGame.isTutorialMode()) this.tips.put(key, new TipInfoModel(key));
/*    */     
/*    */     } 
/*    */     
/* 32 */     put("tutorial.description.entersgame");
/*    */     
/* 34 */     this.changeListener = new AbstractHudModel.ChangeListenerAdapter()
/*    */       {
/*    */         public void itemPickedUp(ItemDescription itemDesc) {
/* 37 */           if (itemDesc.getItemType().isEquipable()) {
/* 38 */             MainGameState.getTips().put("tutorial.description.foundarmor");
/*    */           }
/*    */         }
/*    */         
/*    */         public void townPortalVisibilityChanged(boolean visible) {
/* 43 */           if (visible)
/* 44 */             MainGameState.getTips().put("tutorial.description.townportal"); 
/*    */         }
/*    */       };
/* 47 */     MainGameState.getHudModel().addChangeListener((HudModel.ChangeListener)this.changeListener);
/*    */   }
/*    */   
/*    */   public void dismiss() {
/* 51 */     MainGameState.getHudModel().removeChangeListener((HudModel.ChangeListener)this.changeListener);
/*    */   }
/*    */   
/*    */   public void put(String tutorialText) {
/* 55 */     if (!this.activatedTips.contains(tutorialText) && !this.tips.keySet().contains(tutorialText) && !TcgGame.isTutorialMode()) {
/*    */       
/* 57 */       this.tips.put(tutorialText, new TipInfoModel(tutorialText));
/* 58 */       fireChanged();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void remove(String tutorialText) {
/* 63 */     this.tips.remove(tutorialText);
/* 64 */     this.activatedTips.add(tutorialText);
/* 65 */     fireChanged();
/*    */   }
/*    */   
/*    */   private void fireChanged() {
/* 69 */     if (this.giftBoxChangeListener != null) {
/* 70 */       this.giftBoxChangeListener.contentChanged();
/*    */     }
/*    */     
/* 73 */     HashMap<String, Boolean> tipPrefs = new HashMap<String, Boolean>();
/* 74 */     for (String tip : this.activatedTips) {
/* 75 */       tipPrefs.put(tip, Boolean.valueOf(true));
/*    */     }
/* 77 */     for (String tip : this.tips.keySet()) {
/* 78 */       tipPrefs.put(tip, Boolean.valueOf(false));
/*    */     }
/* 80 */     TcgGame.getPreferences().saveTips(tipPrefs, this.userName);
/*    */   }
/*    */   
/*    */   public void setGiftBoxChangeListener(GiftBoxCollection.ChangeListener giftBoxChangeListener) {
/* 84 */     this.giftBoxChangeListener = giftBoxChangeListener;
/*    */   }
/*    */   
/*    */   public Collection<TipInfoModel> getAll() {
/* 88 */     return this.tips.values();
/*    */   }
/*    */   
/*    */   public void setUserName(String userName) {
/* 92 */     this.userName = userName;
/* 93 */     fireChanged();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\tips\TipsCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */