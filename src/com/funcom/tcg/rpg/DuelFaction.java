/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ 
/*    */ public class DuelFaction
/*    */   extends Faction
/*    */ {
/*    */   private int fightGroup;
/*    */   private int team;
/*    */   
/*    */   public DuelFaction(TCGFaction baseFaction, int fightGroup, int team) {
/* 11 */     super(baseFaction);
/* 12 */     this.fightGroup = fightGroup;
/* 13 */     this.team = team;
/*    */   }
/*    */   
/*    */   public int getFightGroup() {
/* 17 */     return this.fightGroup;
/*    */   }
/*    */   
/*    */   public int getTeam() {
/* 21 */     return this.team;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equivalentTo(Object o) {
/* 26 */     if (this == o) return true; 
/* 27 */     boolean superEquals = super.equivalentTo(o);
/* 28 */     if (!superEquals) return false; 
/* 29 */     if (!(o instanceof DuelFaction)) return superEquals;
/*    */     
/* 31 */     DuelFaction that = (DuelFaction)o;
/*    */     
/* 33 */     if (this.fightGroup != that.fightGroup) return superEquals; 
/* 34 */     if (this.team != that.team) return false;
/*    */     
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 41 */     if (this == o) return true; 
/* 42 */     if (!(o instanceof DuelFaction)) return false; 
/* 43 */     if (!super.equals(o)) return false;
/*    */     
/* 45 */     DuelFaction that = (DuelFaction)o;
/*    */     
/* 47 */     if (this.fightGroup != that.fightGroup) return false; 
/* 48 */     if (this.team != that.team) return false;
/*    */     
/* 50 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 55 */     int result = super.hashCode();
/* 56 */     result = 31 * result + this.fightGroup;
/* 57 */     result = 31 * result + this.team;
/* 58 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\DuelFaction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */