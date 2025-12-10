/*    */ package com.funcom.rpgengine2.equipment;
/*    */ 
/*    */ 
/*    */ public class ArchType
/*    */ {
/*    */   private double health;
/*    */   private double mana;
/*    */   private double attack;
/*    */   
/*    */   public ArchType(String archtypeId, double health, double mana, double attack, double defense, double crit) {
/* 11 */     this.archtypeId = archtypeId;
/* 12 */     this.defense = defense;
/* 13 */     this.health = health;
/* 14 */     this.mana = mana;
/* 15 */     this.attack = attack;
/* 16 */     this.crit = crit;
/*    */   }
/*    */   private double defense; private double crit; private String archtypeId;
/*    */   public String getArchtypeId() {
/* 20 */     return this.archtypeId;
/*    */   }
/*    */   
/*    */   public double getAttack() {
/* 24 */     return this.attack;
/*    */   }
/*    */   
/*    */   public double getCrit() {
/* 28 */     return this.crit;
/*    */   }
/*    */   
/*    */   public double getDefense() {
/* 32 */     return this.defense;
/*    */   }
/*    */   
/*    */   public double getHealth() {
/* 36 */     return this.health;
/*    */   }
/*    */   
/*    */   public double getMana() {
/* 40 */     return this.mana;
/*    */   }
/*    */   
/*    */   public int count() {
/* 44 */     return (int)(this.health + this.mana + this.attack + this.crit + this.defense);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\equipment\ArchType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */