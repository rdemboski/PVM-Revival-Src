/*    */ package com.funcom.rpgengine2.quests.objectives;
/*    */ 
/*    */ 
/*    */ public enum ObjectiveType
/*    */ {
/*  6 */   KILL(1),
/*  7 */   COLLECT(2),
/*  8 */   GO_TO(3),
/*  9 */   FINISH_QUEST(4),
/* 10 */   HAND_IN(5),
/* 11 */   TUTORIAL(6),
/* 12 */   TOTAL_QUESTS(7),
/* 13 */   TOTAL_DAMAGE_TAKEN(8),
/* 14 */   TOTAL_DEATHS(9),
/* 15 */   TOTAL_EQUIPMENT(10),
/* 16 */   TOTAL_KILLS(11),
/* 17 */   SAVE_CHAR(12),
/* 18 */   BECOME_MEMBER(13),
/* 19 */   LEVEL_UP(14),
/* 20 */   SPECIAL_USED(15),
/* 21 */   USE_ITEM(16),
/* 22 */   TOTAL_DAMAGE_DEALT(17),
/* 23 */   TOTAL_PETS(18),
/* 24 */   COLLECT_PET(19),
/* 25 */   PET_LEVEL(20),
/* 26 */   TOTAL_DUEL_WINS(21);
/*    */   
/*    */   private int id;
/*    */ 
/*    */   
/*    */   ObjectiveType(int id) {
/* 32 */     this.id = id;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 36 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\objectives\ObjectiveType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */