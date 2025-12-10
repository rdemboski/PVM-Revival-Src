/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ import com.funcom.commons.localization.JavaLocalization;
/*    */ import com.funcom.rpgengine2.loader.FieldMap;
/*    */ import com.funcom.rpgengine2.quests.reward.QuestRewardDescription;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GiftBoxDescription
/*    */ {
/*    */   private final String id;
/*    */   private final String iconPathLocked;
/*    */   private final String iconPathUnlocked;
/*    */   private final String name;
/*    */   private final long lockedMillis;
/*    */   private final List<QuestRewardDescription> rewards;
/*    */   
/*    */   public GiftBoxDescription(String id, String iconPathLocked, String iconPathUnlocked, String name, long lockedMillis, List<QuestRewardDescription> rewards) {
/* 21 */     this.id = id;
/* 22 */     this.iconPathLocked = iconPathLocked;
/* 23 */     this.iconPathUnlocked = iconPathUnlocked;
/* 24 */     this.name = name;
/* 25 */     this.lockedMillis = lockedMillis;
/* 26 */     this.rewards = Collections.unmodifiableList(rewards);
/*    */   }
/*    */   
/*    */   public String getId() {
/* 30 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getIconPathLocked() {
/* 34 */     return this.iconPathLocked;
/*    */   }
/*    */   
/*    */   public String getIconPathUnlocked() {
/* 38 */     return this.iconPathUnlocked;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 42 */     return JavaLocalization.getInstance().getLocalizedRPGText(this.name);
/*    */   }
/*    */   
/*    */   public long getLockedMillis() {
/* 46 */     return this.lockedMillis;
/*    */   }
/*    */   
/*    */   public List<QuestRewardDescription> getRewards() {
/* 50 */     return this.rewards;
/*    */   }
/*    */ 
/*    */   
/*    */   public static GiftBoxDescription create(FieldMap fieldMap, List<QuestRewardDescription> rewards) {
/* 55 */     return new GiftBoxDescription(fieldMap.get(GiftBoxFields.ID), fieldMap.get(GiftBoxFields.ICON_PATH_LOCKED), fieldMap.get(GiftBoxFields.ICON_PATH_UNLOCKED), fieldMap.get(GiftBoxFields.NAME), fieldMap.getLong(GiftBoxFields.LOCKED_SECONDS) * 1000L, rewards);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\GiftBoxDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */