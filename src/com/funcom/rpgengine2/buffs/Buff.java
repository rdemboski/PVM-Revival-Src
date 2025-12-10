/*     */ package com.funcom.rpgengine2.buffs;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.rpgengine2.abilities.Ability;
/*     */ import com.funcom.rpgengine2.abilities.ActiveAbility;
/*     */ import com.funcom.rpgengine2.abilities.BuffCreator;
/*     */ import com.funcom.rpgengine2.abilities.BuffType;
/*     */ import com.funcom.rpgengine2.abilities.SourceProvider;
/*     */ import com.funcom.rpgengine2.abilities.SourceProviderImpl;
/*     */ import com.funcom.rpgengine2.abilities.TargetProvider;
/*     */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.creatures.MapSupport;
/*     */ import com.funcom.rpgengine2.items.Item;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Buff
/*     */ {
/*     */   private final int duration;
/*     */   private final ShapeDataEvaluator evaluator;
/*     */   private final SourceProvider buffGiver;
/*     */   private final TargetProvider buffOwner;
/*     */   private final boolean infinite;
/*     */   
/*     */   public Buff(Item parentItem, BuffCreator creator, ShapeDataEvaluator evaluator, SourceProvider buffGiver, TargetProvider buffOwner, boolean infinite) {
/*  30 */     this.parentItem = parentItem;
/*  31 */     this.creator = creator;
/*  32 */     this.evaluator = evaluator;
/*  33 */     this.buffGiver = buffGiver;
/*  34 */     this.buffOwner = buffOwner;
/*  35 */     this.infinite = infinite;
/*     */     
/*  37 */     this.timer = this.duration = creator.getDurationMillis();
/*     */     
/*  39 */     DireEffectDescription dfxDescription = creator.getBuffDfx();
/*  40 */     if (dfxDescription != null) {
/*     */       
/*  42 */       this.dfx = dfxDescription.createInstance(parentItem, UsageParams.EMPTY_PARAMS);
/*     */     } else {
/*  44 */       this.dfx = null;
/*     */     } 
/*     */   }
/*     */   private final BuffCreator creator; private final Item parentItem; private final DireEffect dfx; private volatile int activeAbilityTimer; private volatile int timer;
/*     */   private volatile boolean manualFinish;
/*     */   
/*     */   public boolean isFinished() {
/*  51 */     return (this.manualFinish || this.timer <= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(long millis) {
/*  56 */     if (isFinished()) {
/*     */       return;
/*     */     }
/*     */     
/*  60 */     long updateSegment = millis;
/*     */     
/*  62 */     int delayMillis = this.creator.getUpdateDelayMillis();
/*  63 */     if (delayMillis > 0 && updateSegment > delayMillis)
/*     */     {
/*  65 */       updateSegment = delayMillis;
/*     */     }
/*     */     
/*  68 */     while (millis > 0L) {
/*  69 */       if (this.activeAbilityTimer <= 0) {
/*  70 */         updateActiveAbilities();
/*  71 */         this.activeAbilityTimer = delayMillis;
/*     */       } 
/*     */       
/*  74 */       millis -= updateSegment;
/*     */       
/*  76 */       if (!this.infinite) {
/*  77 */         this.timer = (int)(this.timer - updateSegment);
/*     */       }
/*  79 */       this.activeAbilityTimer = (int)(this.activeAbilityTimer - updateSegment);
/*     */       
/*  81 */       if (millis < delayMillis) {
/*  82 */         delayMillis = (int)millis;
/*     */       }
/*     */       
/*  85 */       if (isFinished()) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateActiveAbilities() {
/*  92 */     List<Ability> abilities = this.creator.getAbilities();
/*  93 */     List<? extends TargetProvider> targetProviders = ((MapSupport)this.buffOwner.getTargetObject().getSupport(MapSupport.class)).getAll();
/*     */     
/*  95 */     int count = abilities.size();
/*  96 */     for (int i = 0; i < count; i++) {
/*  97 */       Ability ability = abilities.get(i);
/*  98 */       if (ability instanceof ActiveAbility) {
/*  99 */         SourceProviderImpl buffProvider = new SourceProviderImpl(this.buffOwner.getTargetObject(), this.buffGiver.getSourceHandler());
/* 100 */         ((ActiveAbility)ability).useIfHit(this.parentItem, this.evaluator, (SourceProvider)buffProvider, targetProviders, UsageParams.EMPTY_PARAMS);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getTimer() {
/* 106 */     if (!this.manualFinish) {
/* 107 */       return this.timer;
/*     */     }
/* 109 */     return 0;
/*     */   }
/*     */   
/*     */   public int getDuration() {
/* 113 */     return this.duration;
/*     */   }
/*     */   
/*     */   public List<Ability> getAbilities() {
/* 117 */     return this.creator.getAbilities();
/*     */   }
/*     */   
/*     */   public BuffType getType() {
/* 121 */     return this.creator.getType();
/*     */   }
/*     */   
/*     */   public String getId() {
/* 125 */     return this.creator.getId();
/*     */   }
/*     */   
/*     */   public BuffCreator getCreator() {
/* 129 */     return this.creator;
/*     */   }
/*     */   
/*     */   public DireEffect getDfx() {
/* 133 */     return this.dfx;
/*     */   }
/*     */   
/*     */   public SourceProvider getBuffGiver() {
/* 137 */     return this.buffGiver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void finish() {
/* 144 */     this.manualFinish = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\buffs\Buff.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */