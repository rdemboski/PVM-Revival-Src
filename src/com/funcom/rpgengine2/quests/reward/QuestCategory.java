/*    */ package com.funcom.rpgengine2.quests.reward;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum QuestCategory
/*    */ {
/* 11 */   NONE(0),
/* 12 */   GENERAL(1),
/* 13 */   PETS(2),
/* 14 */   UPGRADES(3),
/* 15 */   ZONES(4),
/* 16 */   MONSTERS(5),
/* 17 */   ITEMS(6);
/*    */   private int type;
/*    */   
/*    */   QuestCategory(int type) {
/* 21 */     this.type = type;
/*    */   }
/*    */   
/*    */   public int getType() {
/* 25 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\reward\QuestCategory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */