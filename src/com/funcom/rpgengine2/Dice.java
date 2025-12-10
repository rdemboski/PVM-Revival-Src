/*    */ package com.funcom.rpgengine2;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class Dice
/*    */ {
/*  7 */   public static final Random RANDOM = new Random(System.nanoTime());
/*    */   
/*    */   public int roll1d6() {
/* 10 */     return roll(1, 6);
/*    */   }
/*    */   
/*    */   public int roll2d6() {
/* 14 */     return roll(2, 6);
/*    */   }
/*    */   
/*    */   public int roll3d6() {
/* 18 */     return roll(3, 6);
/*    */   }
/*    */   
/*    */   public int roll(int dices, int sides) {
/* 22 */     int sum = 0;
/* 23 */     for (int i = 0; i < dices; i++)
/* 24 */       sum += RANDOM.nextInt(sides) + 1; 
/* 25 */     return sum;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\Dice.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */