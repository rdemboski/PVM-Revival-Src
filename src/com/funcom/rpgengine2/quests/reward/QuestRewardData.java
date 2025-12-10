/*    */ package com.funcom.rpgengine2.quests.reward;
/*    */ 
/*    */ public class QuestRewardData
/*    */ {
/*    */   private final String rewardId;
/*    */   private final int amount;
/*    */   private final int type;
/*    */   private final boolean choosable;
/*    */   private final int tier;
/*    */   
/*    */   public QuestRewardData(String rewardId, int tier, int amount, int type, boolean choosable) {
/* 12 */     this.rewardId = rewardId;
/* 13 */     this.tier = tier;
/* 14 */     this.amount = amount;
/* 15 */     this.type = type;
/* 16 */     this.choosable = choosable;
/*    */   }
/*    */   
/*    */   public String getRewardId() {
/* 20 */     return this.rewardId;
/*    */   }
/*    */   public int getAmount() {
/* 23 */     return this.amount;
/*    */   }
/*    */   
/*    */   public int getType() {
/* 27 */     return this.type;
/*    */   }
/*    */   
/*    */   public boolean isChoosable() {
/* 31 */     return this.choosable;
/*    */   }
/*    */   
/*    */   public int getTier() {
/* 35 */     return this.tier;
/*    */   }
/*    */   
/*    */   public static QuestRewardData create(QuestRewardDescription reward) {
/* 39 */     return new QuestRewardData(reward.getId(), reward.getTier(), reward.getAmount(), reward.type().getId(), false);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\reward\QuestRewardData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */