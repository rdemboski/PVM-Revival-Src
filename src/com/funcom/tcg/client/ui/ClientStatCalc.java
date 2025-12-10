/*     */ package com.funcom.tcg.client.ui;
/*     */ 
/*     */ import com.funcom.rpgengine2.combat.Element;
/*     */ import com.funcom.rpgengine2.equipment.ArchType;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientStatCalc
/*     */ {
/*     */   public static double[] getAbilityAmounts(ArchType archtype, int baseLevel, int slots) {
/*  19 */     if (archtype == null) {
/*  20 */       return new double[] { -1.0D, -1.0D, -1.0D, -1.0D, -1.0D };
/*     */     }
/*  22 */     int numStats = archtype.count();
/*  23 */     double level = baseLevel;
/*  24 */     double gear_slots = slots;
/*     */     
/*  26 */     double[] abilityAmounts = new double[5];
/*     */     
/*  28 */     if (archtype.getHealth() > 0.0D) {
/*  29 */       double health_a = 6.0D;
/*  30 */       double health_b = 8.0D;
/*  31 */       double health_c = 80.0D;
/*     */       
/*  33 */       double max_hp_goal = 1.0D;
/*  34 */       double config_gear_hp = 0.4D;
/*     */       
/*  36 */       double hp = (6.0D * Math.pow(level, 2.0D) + 8.0D * level + 80.0D) * 1.0D;
/*     */       
/*  38 */       double amount = Math.round(hp * 0.4D / gear_slots);
/*  39 */       amount *= archtype.getHealth();
/*  40 */       abilityAmounts[0] = amount;
/*     */     } 
/*  42 */     if (archtype.getMana() > 0.0D) {
/*  43 */       double mana_a = 3.0D;
/*  44 */       double mana_b = 4.0D;
/*  45 */       double mana_c = 40.0D;
/*     */       
/*  47 */       double max_mp_goal = 1.0D;
/*  48 */       double config_gear_mp = 0.4D;
/*     */       
/*  50 */       double mp = (3.0D * Math.pow(level, 2.0D) + 4.0D * level + 40.0D) * 1.0D;
/*     */       
/*  52 */       double amount = Math.round(mp * 0.4D / gear_slots);
/*  53 */       amount *= archtype.getMana();
/*  54 */       abilityAmounts[1] = amount;
/*     */     } 
/*  56 */     if (archtype.getDefense() > 0.0D) {
/*  57 */       int armor_a = 15;
/*  58 */       double mitigation = 0.7D;
/*  59 */       double c = (15 * baseLevel);
/*     */       
/*  61 */       double config_gear_armor = 0.5D;
/*     */       
/*  63 */       double amount = Math.round(c * 0.7D / 0.30000000000000004D / gear_slots);
/*  64 */       amount *= 0.5D;
/*     */       
/*  66 */       amount *= archtype.getDefense();
/*  67 */       abilityAmounts[2] = amount;
/*     */     } 
/*  69 */     if (archtype.getAttack() > 0.0D) {
/*  70 */       double k = 20.0D;
/*  71 */       double max_attack_goal = 0.8D;
/*  72 */       double attack_constant = 16.0D;
/*     */       
/*  74 */       double attack_for_level = 16.0D * level / 20.0D;
/*     */       
/*  76 */       double config_gear_attack = 0.5D;
/*     */       
/*  78 */       double amount = Math.round(attack_for_level * 0.5D * 100.0D / gear_slots);
/*  79 */       amount *= archtype.getAttack();
/*  80 */       abilityAmounts[3] = amount;
/*     */     } 
/*  82 */     if (archtype.getCrit() > 0.0D) {
/*  83 */       double k = 20.0D;
/*  84 */       double max_crit_goal = 0.6D;
/*  85 */       double crit_constant = 12.0D;
/*     */       
/*  87 */       double crit_for_level = 12.0D * level / 20.0D;
/*  88 */       double config_gear_crit = 0.5D;
/*     */       
/*  90 */       double amount = Math.round(crit_for_level * 0.5D * 100.0D / gear_slots);
/*  91 */       amount *= archtype.getCrit();
/*     */       
/*  93 */       abilityAmounts[4] = amount;
/*     */     } 
/*     */     
/*  96 */     return abilityAmounts;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getAttack(ArchType archtype, int baseLevel, int slots) {
/* 101 */     if (archtype == null) {
/* 102 */       return -1.0D;
/*     */     }
/* 104 */     int numStats = archtype.count();
/* 105 */     double level = baseLevel;
/* 106 */     double gear_slots = slots;
/*     */     
/* 108 */     double amount = 0.0D;
/* 109 */     if (archtype.getAttack() > 0.0D) {
/* 110 */       double k = 20.0D;
/* 111 */       double max_attack_goal = 0.8D;
/* 112 */       double attack_constant = 16.0D;
/*     */       
/* 114 */       double attack_for_level = 16.0D * level / 20.0D;
/*     */       
/* 116 */       double config_gear_attack = 0.5D;
/*     */       
/* 118 */       amount = Math.round(attack_for_level * 0.5D * 100.0D / gear_slots);
/* 119 */       amount *= archtype.getAttack();
/*     */     } 
/* 121 */     return amount;
/*     */   }
/*     */   
/*     */   public static double[] getStats(Map<Object, Integer> itemStats) {
/* 125 */     double[] stats = new double[5];
/*     */     
/* 127 */     short armorKey = (short)(100 + Element.MONSTER.ordinal());
/*     */     
/* 129 */     stats[0] = (itemStats.get(Short.valueOf((short)11)) != null) ? ((Integer)itemStats.get(Short.valueOf((short)11))).intValue() : 0.0D;
/* 130 */     stats[1] = (itemStats.get(Short.valueOf((short)13)) != null) ? ((Integer)itemStats.get(Short.valueOf((short)13))).intValue() : 0.0D;
/* 131 */     stats[2] = (itemStats.get(Short.valueOf(armorKey)) != null) ? ((Integer)itemStats.get(Short.valueOf(armorKey))).intValue() : 0.0D;
/* 132 */     stats[3] = (itemStats.get(Short.valueOf((short)29)) != null) ? ((Integer)itemStats.get(Short.valueOf((short)29))).intValue() : 0.0D;
/* 133 */     stats[4] = (itemStats.get(Short.valueOf((short)17)) != null) ? ((Integer)itemStats.get(Short.valueOf((short)17))).intValue() : 0.0D;
/* 134 */     return stats;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\ClientStatCalc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */