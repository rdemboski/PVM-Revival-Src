/*    */ package com.funcom.rpgengine2.loot;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MonsterGroupLoot
/*    */ {
/*    */   private String groupId;
/*    */   private int minDrops;
/*    */   private int range;
/*    */   private String relatedQuestId;
/*    */   
/*    */   public MonsterGroupLoot(String groupId, int minDrops, int range, String relatedQuestId) {
/* 13 */     this.groupId = groupId;
/* 14 */     this.minDrops = minDrops;
/* 15 */     this.range = range;
/* 16 */     this.relatedQuestId = relatedQuestId;
/*    */   }
/*    */   
/*    */   public String getGroupId() {
/* 20 */     return this.groupId;
/*    */   }
/*    */   
/*    */   public int getMinDrops() {
/* 24 */     return this.minDrops;
/*    */   }
/*    */   
/*    */   public int getRange() {
/* 28 */     return this.range;
/*    */   }
/*    */   
/*    */   public String getRelatedQuestId() {
/* 32 */     return this.relatedQuestId;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loot\MonsterGroupLoot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */