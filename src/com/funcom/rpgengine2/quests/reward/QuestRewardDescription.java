/*    */ package com.funcom.rpgengine2.quests.reward;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class QuestRewardDescription
/*    */ {
/*    */   private String id;
/*    */   private int tier;
/*    */   private int amount;
/*    */   private QuestRewardType type;
/*    */   private int questLevel;
/*    */   
/*    */   public QuestRewardDescription(String id, int tier, int amount, QuestRewardType type, int level) {
/* 14 */     this.id = id;
/* 15 */     this.tier = tier;
/* 16 */     this.amount = amount;
/* 17 */     this.type = type;
/* 18 */     this.questLevel = level;
/*    */   }
/*    */   
/*    */   public String getId() {
/* 22 */     return this.id;
/*    */   }
/*    */   
/*    */   public int getAmount() {
/* 26 */     return this.amount;
/*    */   }
/*    */   
/*    */   public int getTier() {
/* 30 */     return this.tier;
/*    */   }
/*    */   
/*    */   public QuestRewardType type() {
/* 34 */     return this.type;
/*    */   }
/*    */   
/*    */   public int getQuestLevel() {
/* 38 */     return this.questLevel;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\reward\QuestRewardDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */