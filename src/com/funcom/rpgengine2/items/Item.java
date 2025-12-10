/*     */ package com.funcom.rpgengine2.items;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.commons.geom.RectangleWC;
/*     */ import com.funcom.commons.utils.GlobalTime;
/*     */ import com.funcom.rpgengine2.abilities.Ability;
/*     */ import com.funcom.rpgengine2.abilities.AbilityHolder;
/*     */ import com.funcom.rpgengine2.abilities.ActiveAbility;
/*     */ import com.funcom.rpgengine2.abilities.ShapedAbility;
/*     */ import com.funcom.rpgengine2.abilities.SourceProvider;
/*     */ import com.funcom.rpgengine2.abilities.TargetProvider;
/*     */ import com.funcom.rpgengine2.combat.Shape;
/*     */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.creatures.DFXSupport;
/*     */ import com.funcom.rpgengine2.creatures.ItemUsageSupport;
/*     */ import com.funcom.rpgengine2.creatures.MapSupportable;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.creatures.RpgSourceProviderEntity;
/*     */ import com.funcom.rpgengine2.creatures.StatSupport;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Item
/*     */   implements AbilityHolder
/*     */ {
/*  32 */   private static final Logger LOG = Logger.getLogger(Item.class.getName());
/*     */   
/*     */   private ItemDescription description;
/*     */   protected RpgSourceProviderEntity owner;
/*  36 */   private int amount = 1;
/*     */   private long cooldownUntilMillis;
/*  38 */   private String costId = "";
/*     */   private int costAmount;
/*     */   private ItemHolder holder;
/*     */   private double rotation;
/*     */   
/*     */   protected Item(ItemDescription description) {
/*  44 */     this.description = description;
/*     */   }
/*     */   
/*  47 */   private static float MAX_MONSTER_RADIUS = 0.0F;
/*     */   
/*     */   public static void setMaxMonsterRadius(float radius) {
/*  50 */     MAX_MONSTER_RADIUS = radius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DireEffect checkAndStart(UsageParams usageParams) {
/*  60 */     RpgSourceProviderEntity _owner = this.owner;
/*     */     
/*  62 */     if (_owner != null) {
/*  63 */       DFXSupport dfxSupport = (DFXSupport)_owner.getSupport(DFXSupport.class);
/*     */       
/*  65 */       if (dfxSupport != null) {
/*  66 */         ItemUsageSupport usageSupport = (ItemUsageSupport)_owner.getSupport(ItemUsageSupport.class);
/*     */         
/*  68 */         boolean canUse = true;
/*  69 */         CastTimeSupport castTimeSupport = (CastTimeSupport)_owner.getSupport(CastTimeSupport.class);
/*     */         
/*  71 */         long currentTime = GlobalTime.getInstance().getCurrentTime();
/*  72 */         if (usageSupport != null) {
/*  73 */           canUse = usageSupport.checkUseItemPermission(this, currentTime);
/*     */         }
/*  75 */         if (castTimeSupport != null && castTimeSupport.isCasting()) {
/*  76 */           return null;
/*     */         }
/*  78 */         if (canUse && isReady()) {
/*  79 */           if (this.description.getCastTime() > 0.0F) {
/*  80 */             castTimeSupport.startCastTime(this, usageParams);
/*     */           } else {
/*  82 */             return triggerEffect(usageParams, currentTime);
/*     */           } 
/*     */         }
/*     */       } else {
/*  86 */         LOG.fatal("Missing " + DFXSupport.class.getSimpleName() + ": owner=" + _owner);
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     return null;
/*     */   }
/*     */   
/*     */   public DireEffect start(UsageParams usageParams) {
/*  94 */     RpgSourceProviderEntity _owner = this.owner;
/*     */     
/*  96 */     if (_owner != null) {
/*  97 */       DFXSupport dfxSupport = (DFXSupport)_owner.getSupport(DFXSupport.class);
/*     */       
/*  99 */       long currentTime = GlobalTime.getInstance().getCurrentTime();
/* 100 */       if (dfxSupport != null) {
/* 101 */         triggerEffect(usageParams, currentTime);
/*     */       } else {
/* 103 */         LOG.fatal("Missing " + DFXSupport.class.getSimpleName() + ": owner=" + _owner);
/*     */       } 
/*     */     } 
/*     */     
/* 107 */     return null;
/*     */   }
/*     */   
/*     */   public DireEffect triggerEffect(UsageParams usageParams, long currentTime) {
/* 111 */     ItemUsageSupport usageSupport = (ItemUsageSupport)getOwner().getSupport(ItemUsageSupport.class);
/* 112 */     DFXSupport dfxSupport = (DFXSupport)getOwner().getSupport(DFXSupport.class);
/* 113 */     this.cooldownUntilMillis = currentTime + this.description.getLocalCooldownMillis();
/* 114 */     RpgSourceProviderEntity oldOwner = getOwner();
/* 115 */     if (usageSupport != null) {
/* 116 */       usageSupport.notifyItemUsed(this, oldOwner, currentTime);
/*     */     }
/*     */     
/* 119 */     DireEffect ret = getDescription().getDfxDescription().createInstance(this, usageParams);
/* 120 */     dfxSupport.add(ret);
/*     */ 
/*     */     
/* 123 */     if (getDescription().isConsumable()) {
/* 124 */       decrementAmount(1);
/*     */     }
/*     */     
/* 127 */     return ret;
/*     */   }
/*     */   
/*     */   public boolean checkAndUse(ShapeDataEvaluator evaluator, List<? extends TargetProvider> targetProviders, UsageParams usageParams) {
/* 131 */     boolean canUse = true;
/*     */     
/* 133 */     ItemUsageSupport usageSupport = null;
/*     */     
/* 135 */     long currentTime = GlobalTime.getInstance().getCurrentTime();
/* 136 */     if (this.owner != null) {
/* 137 */       usageSupport = (ItemUsageSupport)this.owner.getSupport(ItemUsageSupport.class);
/* 138 */       if (usageSupport != null) {
/* 139 */         canUse = usageSupport.checkUseItemPermission(this, currentTime);
/*     */       }
/*     */     } 
/*     */     
/* 143 */     if (canUse) {
/* 144 */       RpgSourceProviderEntity oldOwner = this.owner;
/*     */       
/* 146 */       boolean used = use(evaluator, (SourceProvider)this.owner, targetProviders, true, true, usageParams);
/*     */       
/* 148 */       if (used && 
/* 149 */         usageSupport != null) {
/* 150 */         usageSupport.notifyItemUsed(this, oldOwner, currentTime);
/*     */       }
/*     */ 
/*     */       
/* 154 */       return used;
/*     */     } 
/*     */     
/* 157 */     return false;
/*     */   }
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
/*     */   
/*     */   public void applyAbilities(ShapeDataEvaluator evaluator, SourceProvider ownerProvider, List<? extends TargetProvider> targetProviders, UsageParams usageParams) {
/* 171 */     use(evaluator, ownerProvider, targetProviders, false, false, usageParams);
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyAbilities(String dfxReference, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, List<? extends TargetProvider> targetProviders, UsageParams usageParams) {
/* 176 */     int ownerLevel = 1;
/* 177 */     StatSupport statSupport = (StatSupport)this.owner.getSupport(StatSupport.class);
/* 178 */     if (statSupport != null) {
/* 179 */       ownerLevel = statSupport.getLevel();
/*     */     }
/* 181 */     List<Ability> abilities = this.description.getAbilities();
/* 182 */     int size = abilities.size();
/* 183 */     for (int i = 0; i < size; i++) {
/* 184 */       Ability ability = abilities.get(i);
/*     */       
/* 186 */       int levelFrom = ability.getLevelFrom();
/* 187 */       int levelTo = ability.getLevelTo();
/* 188 */       if (ability instanceof ActiveAbility && ownerLevel >= levelFrom && ownerLevel <= levelTo && dfxReference.equals(ability.getDFXReference()))
/*     */       {
/*     */ 
/*     */         
/* 192 */         ((ActiveAbility)ability).useIfHit(this, evaluator, sourceProvider, targetProviders, usageParams);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 199 */     return this.description.getId();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean use(ShapeDataEvaluator evaluator, SourceProvider sourceProvider, List<? extends TargetProvider> targetProviders, boolean useCooldownRule, boolean decrementAmount, UsageParams usageParams) {
/* 204 */     if (!useCooldownRule || isReady()) {
/* 205 */       int ownerLevel = ((StatSupport)this.owner.getSupport(StatSupport.class)).getLevel();
/* 206 */       List<Ability> abilities = this.description.getAbilities();
/* 207 */       int size = abilities.size();
/* 208 */       for (int i = 0; i < size; i++) {
/* 209 */         Ability ability = abilities.get(i);
/*     */         
/* 211 */         if (ownerLevel >= ability.getLevelFrom() && ownerLevel <= ability.getLevelTo() && ability instanceof ActiveAbility)
/*     */         {
/*     */ 
/*     */           
/* 215 */           ((ActiveAbility)ability).useIfHit(this, evaluator, sourceProvider, targetProviders, usageParams);
/*     */         }
/*     */       } 
/*     */       
/* 219 */       if (decrementAmount && 
/* 220 */         getDescription().isConsumable()) {
/* 221 */         decrementAmount(1);
/*     */       }
/*     */ 
/*     */       
/* 225 */       if (useCooldownRule)
/*     */       {
/* 227 */         this.cooldownUntilMillis = GlobalTime.getInstance().getCurrentTime() + this.description.getLocalCooldownMillis();
/*     */       }
/*     */       
/* 230 */       return true;
/*     */     } 
/*     */     
/* 233 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isReady() {
/* 237 */     return (this.cooldownUntilMillis <= GlobalTime.getInstance().getCurrentTime() + (this.description.getLocalCooldownMillis() / 20));
/*     */   }
/*     */   
/*     */   public long remainingCooldown() {
/* 241 */     return Math.max(this.cooldownUntilMillis - GlobalTime.getInstance().getCurrentTime(), 0L);
/*     */   }
/*     */   
/*     */   public boolean canHit(ShapeDataEvaluator evaluator, RpgEntity target, UsageParams usageParams) {
/* 245 */     int ownerLevel = ((StatSupport)this.owner.getSupport(StatSupport.class)).getLevel();
/* 246 */     List<Ability> abilities = this.description.getAbilities();
/* 247 */     int size = abilities.size();
/* 248 */     for (int i = 0; i < size; i++) {
/* 249 */       Ability ability = abilities.get(i);
/*     */       
/* 251 */       if (ownerLevel >= ability.getLevelFrom() && ownerLevel <= ability.getLevelTo() && ability instanceof ActiveAbility) {
/*     */ 
/*     */         
/* 254 */         boolean canHit = ((ActiveAbility)ability).canHit(this, evaluator, (RpgEntity)this.owner, target, usageParams);
/* 255 */         if (canHit) {
/* 256 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 261 */     return false;
/*     */   }
/*     */   
/*     */   public ItemDescription getDescription() {
/* 265 */     return this.description;
/*     */   }
/*     */   
/*     */   public void setOwner(RpgSourceProviderEntity owner) {
/* 269 */     this.owner = owner;
/*     */   }
/*     */   
/*     */   public RpgSourceProviderEntity getOwner() {
/* 273 */     return this.owner;
/*     */   }
/*     */   
/*     */   public void setHolder(ItemHolder holder) {
/* 277 */     this.holder = holder;
/*     */   }
/*     */   
/*     */   public ItemHolder getHolder() {
/* 281 */     return this.holder;
/*     */   }
/*     */   
/*     */   public int getAmount() {
/* 285 */     return this.amount;
/*     */   }
/*     */   
/*     */   public void incrementAmount(int amount) {
/* 289 */     if (getAmount() + amount <= this.description.getStackSize()) {
/* 290 */       this.amount += amount;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setAmount(int amount) {
/* 295 */     amount = Math.min(amount, this.description.getStackSize());
/* 296 */     this.amount = amount;
/*     */   }
/*     */   
/*     */   public void decrementAmount(int toDecrement) {
/* 300 */     this.amount -= toDecrement;
/*     */     
/* 302 */     if (this.holder != null && this.amount <= 0) {
/* 303 */       this.holder.removeItem(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public String getCostId() {
/* 308 */     return this.costId;
/*     */   }
/*     */   
/*     */   public void setCostId(String costId) {
/* 312 */     this.costId = costId;
/*     */   }
/*     */   
/*     */   public int getCostAmount() {
/* 316 */     return this.costAmount;
/*     */   }
/*     */   
/*     */   public void setCostAmount(int costAmount) {
/* 320 */     this.costAmount = costAmount;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 324 */     StringBuffer sb = new StringBuffer();
/* 325 */     sb.append("[").append("description=").append(this.description).append(",owner=").append(this.owner).append(",amount=").append(this.amount).append(",cooldownUntilMillis=").append(this.cooldownUntilMillis).append(",costId=").append(this.costId).append(",costAmount=").append(this.costAmount).append("]");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 333 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleWC getBoundsByOwner() {
/* 343 */     RpgSourceProviderEntity _owner = this.owner;
/* 344 */     if (_owner != null) {
/* 345 */       MapSupportable mapSupport = (MapSupportable)_owner.getSupport(MapSupportable.class);
/* 346 */       if (mapSupport != null) {
/*     */         
/* 348 */         RectangleWC ret = new RectangleWC();
/*     */         
/* 350 */         List<Ability> abilities = this.description.getAbilities();
/* 351 */         int size = abilities.size();
/* 352 */         for (int i = 0; i < size; i++) {
/* 353 */           Ability ability = abilities.get(i);
/* 354 */           if (ability instanceof ShapedAbility) {
/* 355 */             Shape shape = ((ShapedAbility)ability).getShape();
/* 356 */             shape.extendBounds(ret);
/*     */           } 
/*     */         } 
/*     */         
/* 360 */         ret.enlargeByRadius(MAX_MONSTER_RADIUS);
/* 361 */         ret.translate(mapSupport.getPosition());
/*     */         
/* 363 */         return ret;
/*     */       } 
/*     */     } 
/*     */     
/* 367 */     return null;
/*     */   }
/*     */   
/*     */   public double getRange() {
/* 371 */     List<Ability> abilities = this.description.getAbilities();
/* 372 */     double minRange = Double.MAX_VALUE;
/* 373 */     for (Ability ability : abilities) {
/* 374 */       if (ability instanceof ShapedAbility) {
/* 375 */         ShapedAbility shapedAbility = (ShapedAbility)ability;
/* 376 */         double range = shapedAbility.getShape().getRange();
/* 377 */         minRange = Math.min(minRange, range);
/*     */       } 
/*     */     } 
/* 380 */     return minRange;
/*     */   }
/*     */   
/*     */   public boolean isRanged() {
/* 384 */     List<Ability> abilities = this.description.getAbilities();
/* 385 */     for (Ability ability : abilities) {
/* 386 */       if (ability instanceof ShapedAbility) {
/* 387 */         ShapedAbility shapedAbility = (ShapedAbility)ability;
/* 388 */         if (shapedAbility.getShape().isMelee()) {
/* 389 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 393 */     return true;
/*     */   }
/*     */   
/*     */   public void setCurrentDirection(double rotation) {
/* 397 */     this.rotation = rotation;
/*     */   }
/*     */   
/*     */   public double getCurrentRotation() {
/* 401 */     return this.rotation;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\items\Item.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */