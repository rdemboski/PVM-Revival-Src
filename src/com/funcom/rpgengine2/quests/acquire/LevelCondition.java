/*    */ package com.funcom.rpgengine2.quests.acquire;
/*    */ 
/*    */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*    */ import com.funcom.rpgengine2.creatures.StatSupport;
/*    */ 
/*    */ 
/*    */ public class LevelCondition
/*    */   implements AcquireQuestCondition
/*    */ {
/*    */   private int level;
/*    */   
/*    */   public LevelCondition(int level) {
/* 13 */     this.level = level;
/*    */   }
/*    */   
/*    */   public boolean canAquire(RpgEntity entity) {
/* 17 */     return (((StatSupport)entity.getSupport(StatSupport.class)).getLevel() >= this.level);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\acquire\LevelCondition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */