/*    */ package com.funcom.rpgengine2.quests.reward;
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum QuestRewardType
/*    */ {
/*  7 */   TEST_REWARD(-1), ITEM_REWARD(0), PET_REWARD(1), STAT_REWARD(2), PORTAL_REWARD(3), GIFT_BOX_REWARD(4), LOOT_GROUP_REWARD(5);
/*    */   
/*    */   private int id;
/*    */   
/*    */   QuestRewardType(int id) {
/* 12 */     this.id = id;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 16 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\reward\QuestRewardType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */