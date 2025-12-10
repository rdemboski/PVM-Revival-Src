/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ public class PickUpConversionDescription {
/*    */   private final int coins;
/*    */   private final int xp;
/*    */   private final int tokens;
/*    */   
/*    */   public PickUpConversionDescription(int coins, int xp, int tokens) {
/*  9 */     this.coins = coins;
/* 10 */     this.xp = xp;
/* 11 */     this.tokens = tokens;
/*    */   }
/*    */   
/*    */   public int getCoins() {
/* 15 */     return this.coins;
/*    */   }
/*    */   
/*    */   public int getXp() {
/* 19 */     return this.xp;
/*    */   }
/*    */   
/*    */   public int getTokens() {
/* 23 */     return this.tokens;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\PickUpConversionDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */