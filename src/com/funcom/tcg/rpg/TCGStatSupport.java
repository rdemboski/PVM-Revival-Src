/*     */ package com.funcom.tcg.rpg;
/*     */ 
/*     */ import com.funcom.rpgengine2.Stat;
/*     */ import com.funcom.rpgengine2.StatCollection;
/*     */ import com.funcom.rpgengine2.combat.RpgStatus;
/*     */ import com.funcom.rpgengine2.creatures.LifeCycleAware;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.creatures.StatSupport;
/*     */ import com.funcom.rpgengine2.creatures.StatusSupport;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TCGStatSupport
/*     */   extends StatSupport
/*     */   implements LifeCycleAware
/*     */ {
/*  19 */   private static final Stat ZERO = new Stat(Short.valueOf(-32768), 0);
/*     */   
/*     */   protected Stat levelStat;
/*     */   
/*  23 */   protected List<Stat> derivedStats = new ArrayList<Stat>();
/*     */   private StatusSupport statusSupport;
/*     */   
/*     */   public TCGStatSupport() {
/*  27 */     super(Short.valueOf((short)20));
/*     */   }
/*     */   
/*     */   public void init() {
/*  31 */     initDerivedStats();
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispose() {}
/*     */   
/*     */   protected void initDerivedStats() {
/*  38 */     this.levelStat = new LevelStat(Short.valueOf((short)20), this, TCGLevelingEvaluator.INSTANCE);
/*  39 */     this.derivedStats.add(this.levelStat);
/*  40 */     this.derivedStats.add(new LevelingLimitStat(Short.valueOf((short)21), this, LevelingLimitStat.Limit.START, TCGLevelingEvaluator.INSTANCE));
/*     */     
/*  42 */     this.derivedStats.add(new LevelingLimitStat(Short.valueOf((short)22), this, LevelingLimitStat.Limit.END, TCGLevelingEvaluator.INSTANCE));
/*     */ 
/*     */     
/*  45 */     putDerivedStats(this.statCollection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putStats(StatCollection stats) {
/*  56 */     putDerivedStats(stats);
/*  57 */     super.putStats(stats);
/*     */   }
/*     */   
/*     */   public void initMaxStats() {
/*  61 */     Stat maxxp = this.statCollection.get(Short.valueOf((short)19));
/*  62 */     this.statCollection.get(Short.valueOf((short)0)).setMax(maxxp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetUsableStats() {
/*  72 */     Stat health = this.statCollection.get(Short.valueOf((short)12));
/*  73 */     Stat maxHealth = this.statCollection.get(Short.valueOf((short)11));
/*  74 */     if (health != null) {
/*  75 */       health.setMin(ZERO);
/*  76 */       if (maxHealth != null) {
/*  77 */         maxHealth.setMin(ZERO);
/*  78 */         health.setMax(maxHealth);
/*  79 */         health.setBase(maxHealth.getSum());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  84 */     Stat mana = this.statCollection.get(Short.valueOf((short)14));
/*  85 */     Stat maxMana = this.statCollection.get(Short.valueOf((short)13));
/*     */     
/*  87 */     if (mana != null) {
/*  88 */       mana.setMin(ZERO);
/*  89 */       if (maxMana != null) {
/*  90 */         maxMana.setMin(ZERO);
/*  91 */         mana.setMax(maxMana);
/*  92 */         mana.setBase(maxMana.getSum());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void putDerivedStats(StatCollection targetStats) {
/*  98 */     int size = this.derivedStats.size();
/*  99 */     for (int i = 0; i < size; i++) {
/* 100 */       targetStats.put(this.derivedStats.get(i));
/*     */     }
/*     */   }
/*     */   
/*     */   public int getLevel() {
/* 105 */     return this.levelStat.getSum();
/*     */   }
/*     */   
/*     */   public boolean isDestroyed() {
/* 109 */     if (this.statusSupport.getRpgStatuses().contains(RpgStatus.NO_DEATH)) {
/* 110 */       return false;
/*     */     }
/*     */     
/* 113 */     return (getHealth() <= 0);
/*     */   }
/*     */   
/*     */   public int getHealth() {
/* 117 */     Stat stat = this.statCollection.get(Short.valueOf((short)12));
/* 118 */     return stat.getSum();
/*     */   }
/*     */   
/*     */   public int getMaxHealth() {
/* 122 */     Stat stat = this.statCollection.get(Short.valueOf((short)11));
/* 123 */     return stat.getSum();
/*     */   }
/*     */   
/*     */   public int getMana() {
/* 127 */     Stat stat = this.statCollection.get(Short.valueOf((short)14));
/* 128 */     return stat.getSum();
/*     */   }
/*     */   
/*     */   public boolean hasLives() {
/* 132 */     Stat lives = getStatById((short)1);
/*     */     
/* 134 */     return (lives.getSum() > 0);
/*     */   }
/*     */   public void setStatusSupport(StatusSupport statusSupport) {
/* 137 */     this.statusSupport = statusSupport;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetModifiers(RpgEntity owner) {
/* 142 */     super.resetModifiers(owner);
/*     */ 
/*     */     
/* 145 */     Stat health = this.statCollection.get(Short.valueOf((short)12));
/* 146 */     if (health != null)
/* 147 */       health.setBase(health.getBase()); 
/* 148 */     Stat mana = this.statCollection.get(Short.valueOf((short)14));
/* 149 */     if (mana != null)
/* 150 */       mana.setBase(mana.getBase()); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\TCGStatSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */