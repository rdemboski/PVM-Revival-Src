/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Faction
/*    */ {
/*    */   private TCGFaction baseFaction;
/*    */   
/*    */   public Faction(TCGFaction baseFaction) {
/* 10 */     this.baseFaction = baseFaction;
/*    */   }
/*    */   
/*    */   public TCGFaction getBaseFaction() {
/* 14 */     return this.baseFaction;
/*    */   }
/*    */   
/*    */   public boolean equivalentTo(Object o) {
/* 18 */     if (this == o) return true; 
/* 19 */     if (o == null || !(o instanceof Faction)) return false;
/*    */     
/* 21 */     Faction faction = (Faction)o;
/*    */     
/* 23 */     if (this.baseFaction != faction.baseFaction) return false;
/*    */     
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 30 */     if (this == o) return true; 
/* 31 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 33 */     Faction faction = (Faction)o;
/*    */     
/* 35 */     if (this.baseFaction != faction.baseFaction) return false;
/*    */     
/* 37 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 42 */     return (this.baseFaction != null) ? this.baseFaction.hashCode() : 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\Faction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */