/*     */ package com.funcom.rpgengine2.dfx;
/*     */ import com.funcom.commons.dfx.Effect;
/*     */ import com.funcom.commons.dfx.EffectHandler;
/*     */ import com.funcom.commons.geom.RectangleWC;
/*     */ import com.funcom.rpgengine2.abilities.SourceProvider;
/*     */ import com.funcom.rpgengine2.abilities.TargetProvider;
/*     */ import com.funcom.rpgengine2.abilities.TargetProviderImpl;
/*     */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.creatures.MapSupportable;
/*     */ import com.funcom.rpgengine2.creatures.RpgSourceProviderEntity;
/*     */ import com.funcom.rpgengine2.items.Item;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ public class ItemDFXHandler implements EffectHandler {
/*  21 */   private static final Logger LOG = Logger.getLogger(ItemDFXHandler.class.getName());
/*     */   
/*     */   private List<TargetProvider> selfTargetProvider;
/*     */   private final Item item;
/*     */   private final RpgSourceProviderEntity owner;
/*     */   private final ShapeDataEvaluator evaluator;
/*     */   private final UsageParams usageParams;
/*     */   
/*     */   public ItemDFXHandler(Item item, ShapeDataEvaluator evaluator, UsageParams usageParams) {
/*  30 */     this.item = item;
/*  31 */     this.evaluator = evaluator;
/*  32 */     this.usageParams = usageParams;
/*  33 */     this.owner = item.getOwner();
/*  34 */     ArrayList<TargetProvider> providerArrayList = new ArrayList<TargetProvider>(1);
/*  35 */     providerArrayList.add((TargetProvider)this.owner);
/*  36 */     this.selfTargetProvider = Collections.unmodifiableList(providerArrayList);
/*     */   }
/*     */   
/*     */   public void startEffect(Effect sourceEffect) {
/*  40 */     String dfxReference = sourceEffect.getDescription().getResource();
/*     */     
/*  42 */     RpgSourceProviderEntity oldOwner = this.item.getOwner();
/*  43 */     this.item.setOwner(this.owner);
/*     */     
/*  45 */     if (this.owner != null) {
/*  46 */       MapSupportable mapSupportable = (MapSupportable)this.owner.getSupport(MapSupportable.class);
/*  47 */       if (mapSupportable != null) {
/*     */ 
/*     */         
/*  50 */         RectangleWC bounds = this.item.getBoundsByOwner();
/*     */         
/*  52 */         if (bounds != null) {
/*     */           
/*  54 */           List<? extends TargetProvider> targetProviders = mapSupportable.findObjects(bounds);
/*     */           
/*  56 */           targetProviders = ensureSelfInTargets((List)targetProviders);
/*     */ 
/*     */           
/*  59 */           this.item.applyAbilities(dfxReference, this.evaluator, (SourceProvider)this.owner, targetProviders, this.usageParams);
/*     */           
/*  61 */           if (LOG.isEnabledFor((Priority)Level.INFO)) {
/*  62 */             LOG.info("Applied DFX Reference: itemId=" + this.item.getId() + " reference=" + dfxReference);
/*     */           }
/*     */         } else {
/*  65 */           LOG.warn("Could not get item bounds for item:\nitem=" + this.item);
/*     */         } 
/*     */       } else {
/*     */         
/*  69 */         LOG.warn("Cannot use item for entity without map support:\nitem=" + this.item + "\nentity=" + this.owner);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  74 */       LOG.warn("Cannot use item without owner:\nitem=" + this.item);
/*     */     } 
/*     */ 
/*     */     
/*  78 */     this.item.setOwner(oldOwner);
/*     */   }
/*     */   
/*     */   private List<TargetProvider> ensureSelfInTargets(List<TargetProvider> targetProviders) {
/*  82 */     if (targetProviders.isEmpty()) {
/*  83 */       return this.selfTargetProvider;
/*     */     }
/*  85 */     boolean foundSelf = false;
/*  86 */     for (TargetProvider targetProvider : targetProviders) {
/*  87 */       if (targetProvider.getTargetObject() == this.owner.getSourceObject()) {
/*  88 */         foundSelf = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  93 */     if (!foundSelf) {
/*  94 */       TargetProviderImpl targetProviderImpl = new TargetProviderImpl(this.owner.getSourceObject(), this.owner.getSourceHandler());
/*  95 */       targetProviders.add(targetProviderImpl);
/*     */     } 
/*  97 */     return targetProviders;
/*     */   }
/*     */ 
/*     */   
/*     */   public void endEffect(Effect sourceEffect) {}
/*     */ 
/*     */   
/*     */   public boolean isFinished() {
/* 105 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\dfx\ItemDFXHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */