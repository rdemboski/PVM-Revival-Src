/*    */ package com.funcom.rpgengine2.quests;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum QuestType
/*    */ {
/* 10 */   QUEST(0),
/* 11 */   PROXIMITY(1),
/* 12 */   MISSION(2),
/* 13 */   ACHIEVEMENT(3);
/*    */   private int type;
/*    */   
/*    */   QuestType(int type) {
/* 17 */     this.type = type;
/*    */   }
/*    */   
/*    */   public int getType() {
/* 21 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\QuestType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */