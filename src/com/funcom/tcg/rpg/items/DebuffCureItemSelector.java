/*     */ package com.funcom.tcg.rpg.items;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.rpgengine2.StatEffect;
/*     */ import com.funcom.rpgengine2.abilities.BuffType;
/*     */ import com.funcom.rpgengine2.abilities.SourceProvider;
/*     */ import com.funcom.rpgengine2.abilities.SourceProviderImpl;
/*     */ import com.funcom.rpgengine2.abilities.TargetProviderImpl;
/*     */ import com.funcom.rpgengine2.abilities.projectile.Projectile;
/*     */ import com.funcom.rpgengine2.abilities.projectile.ProjectileQueue;
/*     */ import com.funcom.rpgengine2.buffs.Buff;
/*     */ import com.funcom.rpgengine2.combat.Element;
/*     */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.creatures.BuffListener;
/*     */ import com.funcom.rpgengine2.creatures.BuffQueue;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.creatures.RpgObject;
/*     */ import com.funcom.rpgengine2.creatures.StatEffectQueue;
/*     */ import com.funcom.rpgengine2.creatures.SupportEvent;
/*     */ import com.funcom.rpgengine2.items.Item;
/*     */ import com.funcom.rpgengine2.items.ItemChangeListener;
/*     */ import com.funcom.rpgengine2.items.ItemHolder;
/*     */ import com.funcom.rpgengine2.items.ItemType;
/*     */ import com.funcom.tcg.rpg.TCGBuffSupport;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class DebuffCureItemSelector
/*     */   implements ItemSelector, ItemChangeListener, BuffListener
/*     */ {
/*     */   private static final int WAIT_UPDATE_MILLIS = 300;
/*     */   private RpgEntity owner;
/*     */   private List<? extends ItemHolder> itemHolders;
/*     */   private Item item;
/*     */   private ShapeDataEvaluator evaluator;
/*     */   private long nextUpdateWait;
/*     */   private ItemType watchItemType;
/*     */   
/*     */   public DebuffCureItemSelector(ShapeDataEvaluator evaluator, ItemType watchItemType, List<? extends ItemHolder> itemHolders) {
/*  41 */     this.evaluator = evaluator;
/*  42 */     this.watchItemType = watchItemType;
/*  43 */     this.itemHolders = itemHolders;
/*     */     
/*  45 */     for (ItemHolder itemHolder : itemHolders) {
/*  46 */       itemHolder.addChangeListener(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem() {
/*  52 */     findBestItem();
/*     */     
/*  54 */     return this.item;
/*     */   }
/*     */   
/*     */   public void itemChanged(ItemHolder source, int placement, Item newItem, Item oldItem) {
/*  58 */     findBestItem();
/*     */   }
/*     */   
/*     */   private void findBestItem() {
/*  62 */     if (this.owner == null) {
/*  63 */       throw new IllegalStateException("owner is not set, please set owner");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     List<TargetProviderImpl> captureResultList = new ArrayList<TargetProviderImpl>(1);
/*  70 */     StatResultHandler recorder = new StatResultHandler();
/*  71 */     captureResultList.add(new TargetProviderImpl(this.owner, recorder));
/*     */     
/*  73 */     SourceProviderImpl sourceProviderImpl = new SourceProviderImpl(this.owner, recorder);
/*     */ 
/*     */ 
/*     */     
/*  77 */     for (ItemHolder itemHolder : this.itemHolders) {
/*  78 */       for (int j = 0; j < itemHolder.getSlotCount(); j++) {
/*  79 */         Item item = itemHolder.getItem(j);
/*  80 */         if (item != null && item.getDescription().getItemType() == this.watchItemType) {
/*  81 */           recorder.start(item);
/*  82 */           item.applyAbilities(this.evaluator, (SourceProvider)sourceProviderImpl, captureResultList, UsageParams.EMPTY_PARAMS);
/*  83 */           recorder.stopAndEvaluate();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  88 */     this.item = recorder.getBestItem();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean testItem(Item item) {
/*  93 */     List<TargetProviderImpl> captureResultList = new ArrayList<TargetProviderImpl>(1);
/*  94 */     StatResultHandler recorder = new StatResultHandler();
/*  95 */     captureResultList.add(new TargetProviderImpl(this.owner, recorder));
/*     */     
/*  97 */     SourceProviderImpl sourceProviderImpl = new SourceProviderImpl(this.owner, recorder);
/*     */     
/*  99 */     if (item != null && item.getDescription().getItemType() == this.watchItemType) {
/* 100 */       recorder.start(item);
/* 101 */       item.applyAbilities(this.evaluator, (SourceProvider)sourceProviderImpl, captureResultList, UsageParams.EMPTY_PARAMS);
/* 102 */       recorder.stopAndEvaluate();
/* 103 */       return (recorder.getBestItem() == item);
/*     */     } 
/* 105 */     return false;
/*     */   }
/*     */   
/*     */   public void setOwner(RpgEntity owner) {
/* 109 */     this.owner = owner;
/*     */   }
/*     */   
/*     */   public void triggerRefresh() {
/* 113 */     if (this.nextUpdateWait < 0L) {
/* 114 */       this.nextUpdateWait = 300L;
/*     */     }
/*     */   }
/*     */   
/*     */   public void update(long updateMillis) {
/* 119 */     if (this.nextUpdateWait >= 0L) {
/* 120 */       this.nextUpdateWait -= updateMillis;
/* 121 */       if (this.nextUpdateWait <= 0L) {
/* 122 */         findBestItem();
/*     */         
/* 124 */         this.nextUpdateWait = -1L;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void buffAdded(Buff buffAdded) {
/* 130 */     triggerRefresh();
/*     */   }
/*     */   
/*     */   public void buffRemoved(Buff buffRemoved) {
/* 134 */     this.item = null;
/*     */   }
/*     */   
/*     */   private class StatResultHandler
/*     */     implements RpgEntity {
/*     */     private static final int BUFF_SCORE = 50;
/*     */     private Item bestItem;
/* 141 */     private int bestCost = Integer.MAX_VALUE;
/*     */     
/*     */     private int evaluateCost;
/*     */     private Item evaluateItem;
/*     */     private boolean evaluateChangedWatchStat;
/*     */     
/* 147 */     private StatEffectQueue statEffectCostEval = new StatEffectQueue() {
/*     */         public void enqueueEffect(StatEffect statEffect) {
/* 149 */           int sum = statEffect.getSum();
/* 150 */           int sumMagnitude = sum;
/* 151 */           if (sum < 0) {
/* 152 */             sumMagnitude = -sum;
/*     */           }
/*     */           
/* 155 */           DebuffCureItemSelector.StatResultHandler.this.evaluateCost += sumMagnitude;
/* 156 */           DebuffCureItemSelector.StatResultHandler.this.evaluateCost += 50;
/*     */         }
/*     */       };
/*     */     
/* 160 */     private BuffQueue buffCostEval = new BuffQueue() {
/*     */         public void enqueue(Buff buff) {
/* 162 */           buff.update(buff.getDuration());
/* 163 */           DebuffCureItemSelector.StatResultHandler.this.evaluateCost += 50;
/*     */         }
/*     */         
/*     */         public void enqueueFinish(BuffType type, Element cureElement, String cureStatus) {
/* 167 */           boolean curesCurrentDebuff = false;
/*     */           
/* 169 */           if (type == BuffType.DEBUFF) {
/* 170 */             List<Buff> buffList = ((TCGBuffSupport)DebuffCureItemSelector.this.owner.getSupport(TCGBuffSupport.class)).getBuffList(type);
/* 171 */             if (buffList != null) {
/* 172 */               for (Buff buff : buffList) {
/* 173 */                 if (buff != null && buff.getCreator().matchCureCriterias(cureElement, cureStatus)) {
/* 174 */                   curesCurrentDebuff = true;
/*     */                 }
/*     */               } 
/*     */             }
/*     */           } 
/*     */           
/* 180 */           if (curesCurrentDebuff) {
/* 181 */             if (cureElement == null && cureStatus == null) {
/*     */               
/* 183 */               DebuffCureItemSelector.StatResultHandler.this.evaluateCost += 150;
/* 184 */             } else if (cureElement != null && cureStatus == null) {
/*     */               
/* 186 */               DebuffCureItemSelector.StatResultHandler.this.evaluateCost += 100;
/* 187 */             } else if (cureElement == null && cureStatus != null) {
/*     */               
/* 189 */               DebuffCureItemSelector.StatResultHandler.this.evaluateCost += 50;
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 196 */             DebuffCureItemSelector.StatResultHandler.this.evaluateChangedWatchStat = true;
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public void enqueueFinnishBuff(String targetId) {}
/*     */       };
/*     */ 
/*     */     
/* 205 */     private ProjectileQueue projectileEval = new ProjectileQueue() {
/*     */         public void enqueueProjectile(Projectile projectile, WorldCoordinate startPosition, float startAngle) {
/* 207 */           DebuffCureItemSelector.StatResultHandler.this.evaluateCost += 50;
/*     */         }
/*     */       };
/*     */     
/*     */     public <E extends com.funcom.rpgengine2.creatures.RpgQueryableSupport> E getSupport(Class<E> supportClass) {
/* 212 */       if (StatEffectQueue.class.isAssignableFrom(supportClass)) {
/* 213 */         return (E)this.statEffectCostEval;
/*     */       }
/* 215 */       if (BuffQueue.class.isAssignableFrom(supportClass)) {
/* 216 */         return (E)this.buffCostEval;
/*     */       }
/* 218 */       if (ProjectileQueue.class.isAssignableFrom(supportClass)) {
/* 219 */         return (E)this.projectileEval;
/*     */       }
/*     */       
/* 222 */       return (E)DebuffCureItemSelector.this.owner.getSupport(supportClass);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fireEvent(SupportEvent event) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int calcCritDamage(int value, RpgObject target) {
/* 234 */       return value;
/*     */     }
/*     */     
/*     */     public Item getBestItem() {
/* 238 */       return this.bestItem;
/*     */     }
/*     */     
/*     */     public void start(Item itemToEvaluate) {
/* 242 */       this.evaluateItem = itemToEvaluate;
/* 243 */       this.evaluateCost = 0;
/* 244 */       this.evaluateChangedWatchStat = false;
/*     */     }
/*     */     
/*     */     public void stopAndEvaluate() {
/* 248 */       if (this.evaluateChangedWatchStat && this.evaluateCost < this.bestCost) {
/*     */ 
/*     */         
/* 251 */         this.bestItem = this.evaluateItem;
/* 252 */         this.bestCost = this.evaluateCost;
/*     */       } 
/*     */     }
/*     */     
/*     */     private StatResultHandler() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\items\DebuffCureItemSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */