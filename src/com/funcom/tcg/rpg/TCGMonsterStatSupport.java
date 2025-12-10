/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ import com.funcom.rpgengine2.Stat;
/*    */ import com.funcom.rpgengine2.monsters.MonsterDescription;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TCGMonsterStatSupport
/*    */   extends TCGStatSupport
/*    */ {
/*    */   private final MonsterDescription description;
/*    */   
/*    */   public TCGMonsterStatSupport(MonsterDescription description) {
/* 14 */     this.description = description;
/*    */   }
/*    */   
/*    */   protected void initDerivedStats() {
/* 18 */     this.levelStat = new Stat(Short.valueOf((short)20), this.description.getLevel());
/* 19 */     this.derivedStats.add(this.levelStat);
/* 20 */     putDerivedStats(this.statCollection);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\TCGMonsterStatSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */