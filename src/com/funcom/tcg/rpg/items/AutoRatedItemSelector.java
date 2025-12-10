/*     */ package com.funcom.tcg.rpg.items;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.rpgengine2.Stat;
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
/*     */ import com.funcom.rpgengine2.creatures.BuffQueue;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.creatures.RpgObject;
/*     */ import com.funcom.rpgengine2.creatures.StatEffectQueue;
/*     */ import com.funcom.rpgengine2.creatures.StatSupport;
/*     */ import com.funcom.rpgengine2.creatures.SupportEvent;
/*     */ import com.funcom.rpgengine2.items.Item;
/*     */ import com.funcom.rpgengine2.items.ItemChangeListener;
/*     */ import com.funcom.rpgengine2.items.ItemHolder;
/*     */ import com.funcom.rpgengine2.items.ItemType;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ public class AutoRatedItemSelector
/*     */   implements ItemSelector, ItemChangeListener
/*     */ {
/*     */   private static final int MAX_BUFF_CHECK_DURATION = 1500;
/*     */   private static final int WAIT_UPDATE_MILLIS = 1000;
/*     */   private RpgObject owner;
/*     */   private short watchStatId;
/*     */   private List<? extends ItemHolder> itemHolders;
/*     */   private Item item;
/*     */   private ShapeDataEvaluator evaluator;
/*     */   private long nextUpdateWait;
/*     */   private ItemType watchItemType;
/*     */   
/*     */   public AutoRatedItemSelector(ShapeDataEvaluator evaluator, short watchStatId, ItemType watchItemType, List<? extends ItemHolder> itemHolders) {
/*  44 */     this.evaluator = evaluator;
/*  45 */     this.watchStatId = watchStatId;
/*  46 */     this.watchItemType = watchItemType;
/*  47 */     this.itemHolders = itemHolders;
/*     */     
/*  49 */     for (ItemHolder itemHolder : itemHolders) {
/*  50 */       itemHolder.addChangeListener(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem() {
/*  56 */     findBestItem();
/*     */     
/*  58 */     return this.item;
/*     */   }
/*     */   
/*     */   public void itemChanged(ItemHolder source, int placement, Item newItem, Item oldItem) {
/*  62 */     findBestItem();
/*     */   }
/*     */   
/*     */   private void findBestItem() {
/*  66 */     if (this.owner == null) {
/*  67 */       throw new IllegalStateException("owner is not set, please set owner");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     List<TargetProviderImpl> captureResultList = new ArrayList<TargetProviderImpl>(1);
/*  74 */     StatResultHandler recorder = new StatResultHandler((RpgEntity)this.owner);
/*  75 */     captureResultList.add(new TargetProviderImpl((RpgEntity)this.owner, recorder));
/*     */     
/*  77 */     SourceProviderImpl sourceProviderImpl = new SourceProviderImpl((RpgEntity)this.owner, recorder);
/*     */ 
/*     */     
/*  80 */     for (ItemHolder itemHolder : this.itemHolders) {
/*  81 */       for (int j = 0; j < itemHolder.getSlotCount(); j++) {
/*  82 */         Item item = itemHolder.getItem(j);
/*  83 */         if (item != null && item.getDescription().getItemType() == this.watchItemType) {
/*  84 */           recorder.start(item);
/*  85 */           item.applyAbilities(this.evaluator, (SourceProvider)sourceProviderImpl, captureResultList, UsageParams.EMPTY_PARAMS);
/*  86 */           recorder.stopAndEvaluate();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  91 */     this.item = recorder.getBestItem();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean testItem(Item item) {
/*  96 */     List<TargetProviderImpl> captureResultList = new ArrayList<TargetProviderImpl>(1);
/*  97 */     StatResultHandler recorder = new StatResultHandler((RpgEntity)this.owner);
/*  98 */     captureResultList.add(new TargetProviderImpl((RpgEntity)this.owner, recorder));
/*  99 */     SourceProviderImpl sourceProviderImpl = new SourceProviderImpl((RpgEntity)this.owner, recorder);
/*     */     
/* 101 */     if (item != null && item.getDescription().getItemType() == this.watchItemType) {
/* 102 */       recorder.start(item);
/* 103 */       item.applyAbilities(this.evaluator, (SourceProvider)sourceProviderImpl, captureResultList, UsageParams.EMPTY_PARAMS);
/* 104 */       recorder.stopAndEvaluate();
/*     */       
/* 106 */       return (item == recorder.getBestItem());
/*     */     } 
/* 108 */     return false;
/*     */   }
/*     */   
/*     */   public void setOwner(RpgObject owner) {
/* 112 */     this.owner = owner;
/*     */   }
/*     */   
/*     */   public void triggerRefresh() {
/* 116 */     if (this.nextUpdateWait < 0L) {
/* 117 */       this.nextUpdateWait = 1000L;
/*     */     }
/*     */   }
/*     */   
/*     */   public void update(long updateMillis) {
/* 122 */     if (this.nextUpdateWait >= 0L) {
/* 123 */       this.nextUpdateWait -= updateMillis;
/* 124 */       if (this.nextUpdateWait <= 0L) {
/* 125 */         findBestItem();
/*     */         
/* 127 */         this.nextUpdateWait = -1L;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private class StatResultHandler
/*     */     implements RpgEntity {
/*     */     private static final int BUFF_SCORE = 50;
/*     */     private Item bestItem;
/* 136 */     private int bestCost = Integer.MAX_VALUE;
/*     */     
/*     */     private int evaluateCost;
/*     */     private Item evaluateItem;
/*     */     private boolean evaluateChangedWatchStat;
/*     */     private int evaluateStatValueAdded;
/*     */     private RpgEntity owner;
/*     */     private StatEffectQueue statEffectCostEval;
/*     */     private BuffQueue buffCostEval;
/*     */     private ProjectileQueue projectileEval;
/*     */     
/*     */     private StatResultHandler(RpgEntity owner) {
/* 148 */       this.statEffectCostEval = new StatEffectQueue() {
/*     */           public void enqueueEffect(StatEffect statEffect) {
/* 150 */             int sum = statEffect.getSum();
/* 151 */             int sumMagnitude = sum;
/* 152 */             if (sum < 0) {
/* 153 */               sumMagnitude = -sum;
/*     */             }
/*     */             
/* 156 */             if (statEffect.getStatId().shortValue() == AutoRatedItemSelector.this.watchStatId && sum > 0) {
/*     */ 
/*     */               
/* 159 */               AutoRatedItemSelector.StatResultHandler.this.evaluateStatValueAdded += sum;
/* 160 */               AutoRatedItemSelector.StatResultHandler.this.evaluateChangedWatchStat = true;
/*     */             } 
/*     */             
/* 163 */             AutoRatedItemSelector.StatResultHandler.this.evaluateCost += sumMagnitude;
/*     */           }
/*     */         };
/*     */       
/* 167 */       this.buffCostEval = new BuffQueue() {
/*     */           public void enqueue(Buff buff) {
/* 169 */             int durationToUpdate = buff.getDuration();
/* 170 */             if (durationToUpdate > 1500) {
/* 171 */               durationToUpdate = 1500;
/*     */             }
/* 173 */             buff.update(durationToUpdate);
/* 174 */             AutoRatedItemSelector.StatResultHandler.this.evaluateCost += 50;
/*     */           }
/*     */           
/*     */           public void enqueueFinish(BuffType type, Element cureElement, String cureStatus) {
/* 178 */             AutoRatedItemSelector.StatResultHandler.this.evaluateCost += 50;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void enqueueFinnishBuff(String targetId) {}
/*     */         };
/* 186 */       this.projectileEval = new ProjectileQueue() {
/*     */           public void enqueueProjectile(Projectile projectile, WorldCoordinate startPosition, float startAngle) {
/* 188 */             AutoRatedItemSelector.StatResultHandler.this.evaluateCost += 50;
/*     */           }
/*     */         };
/*     */       this.owner = owner;
/*     */     } public <E extends com.funcom.rpgengine2.creatures.RpgQueryableSupport> E getSupport(Class<E> supportClass) {
/* 193 */       if (StatEffectQueue.class.isAssignableFrom(supportClass)) {
/* 194 */         return (E)this.statEffectCostEval;
/*     */       }
/* 196 */       if (BuffQueue.class.isAssignableFrom(supportClass)) {
/* 197 */         return (E)this.buffCostEval;
/*     */       }
/* 199 */       if (ProjectileQueue.class.isAssignableFrom(supportClass)) {
/* 200 */         return (E)this.projectileEval;
/*     */       }
/*     */       
/* 203 */       return (E)this.owner.getSupport(supportClass);
/*     */     }
/*     */ 
/*     */     
/*     */     public void fireEvent(SupportEvent event) {}
/*     */ 
/*     */     
/*     */     public int calcCritDamage(int value, RpgObject target) {
/* 211 */       return value;
/*     */     }
/*     */     
/*     */     public Item getBestItem() {
/* 215 */       return this.bestItem;
/*     */     }
/*     */     
/*     */     public void start(Item itemToEvaluate) {
/* 219 */       this.evaluateItem = itemToEvaluate;
/* 220 */       this.evaluateCost = 0;
/* 221 */       this.evaluateChangedWatchStat = false;
/* 222 */       this.evaluateStatValueAdded = 0;
/*     */     }
/*     */     
/*     */     public void stopAndEvaluate() {
/* 226 */       if (this.evaluateChangedWatchStat) {
/* 227 */         Stat targetStat = ((StatSupport)this.owner.getSupport(StatSupport.class)).getStatById(AutoRatedItemSelector.this.watchStatId);
/* 228 */         Stat maxStat = targetStat.getMax();
/*     */         
/* 230 */         if (maxStat != null) {
/* 231 */           int diffMax = this.evaluateStatValueAdded + targetStat.getSum() - maxStat.getSum();
/* 232 */           int differenceCost = diffMax * diffMax;
/*     */           
/* 234 */           if (diffMax > 0)
/*     */           {
/* 236 */             differenceCost = differenceCost * 5 / 4;
/*     */           }
/* 238 */           this.evaluateCost += differenceCost;
/*     */         } 
/*     */         
/* 241 */         if (this.evaluateCost < this.bestCost) {
/*     */           
/* 243 */           this.bestItem = this.evaluateItem;
/* 244 */           this.bestCost = this.evaluateCost;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\items\AutoRatedItemSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */