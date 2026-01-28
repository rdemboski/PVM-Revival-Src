/*     */ package com.funcom.tcg.rpg;
import com.funcom.commons.geom.RectangleWC;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.rpgengine2.StatEffect;
/*     */ import com.funcom.rpgengine2.abilities.TargetProvider;
/*     */ import com.funcom.rpgengine2.combat.RpgStatus;
/*     */ import com.funcom.rpgengine2.creatures.ImmunitySupport;
/*     */ import com.funcom.rpgengine2.creatures.MapSupport;
import com.funcom.rpgengine2.creatures.OwnedRpgObject;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.creatures.RpgSourceProviderEntity;
/*     */ import com.funcom.rpgengine2.creatures.StatEffectSupport;
/*     */ import com.funcom.rpgengine2.creatures.StatusSupport;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ public class TCGStatEffectSupport extends StatEffectSupport {
/*  19 */   private static final Logger LOG = Logger.getLogger(TCGStatEffectSupport.class.getName());
/*     */ 
/*     */   
/*  22 */   private static final TCGDamageFilter DAMAGE_FILTER = new TCGDamageFilter();
/*     */   
/*     */   private final TCGStatEffectListener listener;
/*     */   
/*     */   private final RpgSourceProviderEntity owner;
/*     */   
/*     */   private final StatusSupport statusSupport;
/*     */   
/*     */   private final ImmunitySupport immunitySupport;
/*     */   
/*     */   private final double range;
/*     */   
/*     */   private int damageDone;
/*     */   
/*     */   private boolean critDone;
/*     */   
/*     */   private WeakReference<TCGStatEffectListener> attackListenerRef;
/*     */   
/*     */   public TCGStatEffectSupport(TCGStatEffectListener listener, RpgSourceProviderEntity owner, TCGStatSupport statSupport, StatusSupport statusSupport, ImmunitySupport immunitySupport, double range) {
/*  41 */     super(statSupport);
/*  42 */     this.listener = listener;
/*  43 */     this.owner = owner;
/*  44 */     this.statusSupport = statusSupport;
/*  45 */     this.immunitySupport = immunitySupport;
/*  46 */     this.range = range;
/*     */   }
/*     */   
/*     */   private TCGStatSupport getStatSupport() {
/*  50 */     return (TCGStatSupport)this.statSupport;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void sendDamageMessage() {
/*  55 */     if (this.damageDone < 0 || this.critDone) {
/*  56 */       TCGStatEffectListener attackListener = null;
/*  57 */       if (this.attackListenerRef != null) {
/*  58 */         attackListener = this.attackListenerRef.get();
/*     */       }
/*     */       
/*  61 */       if (attackListener != null) {
/*  62 */         attackListener.enqueueDamagedOther(this.owner, this.damageDone, this.critDone);
/*     */       }
/*  64 */       this.damageDone = 0;
/*  65 */       this.critDone = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void applyEffect(StatEffect statEffect) {
/*     */     int oldMana, newMana;
/*  77 */     switch (statEffect.getStatId().shortValue()) {
/*     */       case 12:
/*  79 */         if (statEffect.getSum() < 0) {
/*  80 */           if (this.statusSupport.getStatus(RpgStatus.INVULNERABLE)) {
/*  81 */             LOG.info("All damage resisted due to status INVULNERABLE");
/*     */ 
/*     */           
/*     */           }
/*  85 */           else if (!getStatSupport().isDestroyed()) {
/*  86 */             String elementInLowerCase = statEffect.getElement().name().toLowerCase();
/*  87 */             if (this.immunitySupport.isImmune(elementInLowerCase)) {
/*  88 */               this.immunitySupport.dispatchEvent(new ImmunitySupport.ImmuneEvent(statEffect.getSourceProvider()));
/*     */             } else {
/*  90 */               applyDamage(statEffect);
/*     */             } 
/*     */           } 
/*     */         } else {
/*  94 */           super.applyEffect(statEffect);
/*     */         } 
/*     */         return;
/*     */ 
/*     */       
/*     */       case 0:
/* 100 */         if (!getStatSupport().isDestroyed()) {
/*     */           
/* 102 */           int oldLevel = getStatSupport().getLevel();
/* 103 */           super.applyEffect(statEffect);
/* 104 */           int newLevel = getStatSupport().getLevel();
/*     */           
/* 106 */           if (oldLevel != newLevel) {
/* 107 */             this.listener.notifyNewLevel(oldLevel, newLevel);
/*     */           }
/*     */         } 
/*     */         return;
/*     */       
/*     */       case 14:
/* 113 */         oldMana = getStatSupport().getMana();
/* 114 */         super.applyEffect(statEffect);
/* 115 */         newMana = getStatSupport().getMana();
/* 116 */         this.listener.notifyManaUsed(oldMana, newMana);
/*     */         return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     super.applyEffect(statEffect);
/* 128 */     LOG.log((Priority)Level.INFO, this + ": effect=" + statEffect + " xp=" + this.statSupport.getStatById((short)0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void applyDamage(StatEffect statEffect) {
/* 134 */     boolean isInfo = LOG.isEnabledFor((Priority)Level.DEBUG);
/* 135 */     String logBeforeDamageFilter = null;
/* 136 */     if (isInfo) {
/* 137 */       logBeforeDamageFilter = statEffect.toString();
/*     */     }
/* 139 */     DAMAGE_FILTER.filter(statEffect);
/* 140 */     if (isInfo) {
/* 141 */       LOG.info("PRE-PROCESS EFFECT (resistance):\n beforeResistance=" + logBeforeDamageFilter + "\n afterResistance=" + statEffect.toString());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 146 */     super.applyEffect(statEffect);
/*     */     
/* 148 */     RpgEntity attacker = statEffect.getSourceProvider().getSourceHandler();
/* 149 */     this.listener.handleAttackedByOther(attacker, statEffect);
/*     */     
/* 151 */     boolean isDestroyed = getStatSupport().isDestroyed();
/* 152 */     if (isDestroyed)
/*     */     {
/* 154 */       this.listener.handleKilledByOther(attacker, statEffect);
/*     */     }
/*     */ 
/*     */     
/* 158 */     RpgEntity xptarget = attacker;
/*     */     
/* 160 */     while (xptarget instanceof OwnedRpgObject) {
/* 161 */       xptarget = ((OwnedRpgObject)xptarget).getOwner();
/*     */     }
/*     */     
/* 164 */     if (xptarget instanceof TCGStatEffectListener) {
/* 165 */       TCGStatEffectListener attackListener = (TCGStatEffectListener)xptarget;
/* 166 */       this.attackListenerRef = new WeakReference<TCGStatEffectListener>(attackListener);
/*     */ 
/*     */       
/* 169 */       this.damageDone += statEffect.getSum();
/* 170 */       this.critDone |= statEffect.hasAbilityValue("crit");
/* 171 */       if (isDestroyed) {
/* 172 */         MapSupport mapSupport = (MapSupport)this.owner.getSupport(MapSupport.class);
/* 173 */         WorldCoordinate position = mapSupport.getPosition();
/* 174 */         List<? extends TargetProvider> objects = mapSupport.findObjects(new RectangleWC(position.clone().addOffset(-(this.range / 2.0D), -(this.range / 2.0D)), position.clone().addOffset(this.range / 2.0D, this.range / 2.0D)));
/*     */ 
/*     */         
/* 177 */         for (TargetProvider target : objects) {
/* 178 */           if (target.getTargetObject() instanceof TCGStatEffectListener && target.getTargetObject() != xptarget) {
/* 179 */             TCGStatEffectListener nearbyPlayer = (TCGStatEffectListener)target.getTargetObject();
/* 180 */             nearbyPlayer.enqueueKilledOther(this.owner);
/*     */           } 
/*     */         } 
/*     */         
/* 184 */         attackListener.enqueueKilledOther(this.owner);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\TCGStatEffectSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */