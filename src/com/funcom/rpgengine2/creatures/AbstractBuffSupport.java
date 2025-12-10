/*     */ package com.funcom.rpgengine2.creatures;
/*     */ 
/*     */ import com.funcom.rpgengine2.abilities.BuffType;
/*     */ import com.funcom.rpgengine2.buffs.Buff;
/*     */ import com.funcom.rpgengine2.combat.Element;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractBuffSupport
/*     */   implements BuffSupportable, SupportEventListener
/*     */ {
/*  18 */   private static final int[] UPDATE_PRIORITIES = new int[] { 499999, 500001 };
/*     */   
/*  20 */   protected List<Buff> pendingBuffs = Collections.synchronizedList(new ArrayList<Buff>());
/*  21 */   protected List<BuffFinishEvent> pendingBuffFinishEvents = Collections.synchronizedList(new ArrayList<BuffFinishEvent>());
/*  22 */   protected List<String> pendingBuffIds = Collections.synchronizedList(new ArrayList<String>());
/*     */   
/*  24 */   protected List<BuffListener> listeners = new ArrayList<BuffListener>(1);
/*     */   private PassiveAbilityHandlingSupport passiveAbilityHandlingSupport;
/*     */   private StatSupport statSupport;
/*     */   private RpgEntity owner;
/*     */   
/*     */   public AbstractBuffSupport(PassiveAbilityHandlingSupport passiveAbilityHandlingSupport, StatSupport statSupport, RpgEntity owner) {
/*  30 */     this.passiveAbilityHandlingSupport = passiveAbilityHandlingSupport;
/*  31 */     this.statSupport = statSupport;
/*  32 */     this.owner = owner;
/*     */   }
/*     */   
/*     */   public void addBuffListener(BuffListener listener) {
/*  36 */     this.listeners.add(listener);
/*     */   }
/*     */   
/*     */   public void removeBuffListener(BuffListener listener) {
/*  40 */     this.listeners.remove(listener);
/*     */   }
/*     */   
/*     */   public void enqueueFinish(BuffType type, Element cureElement, String cureStatus) {
/*  44 */     this.pendingBuffFinishEvents.add(new BuffFinishEvent(type, cureElement, cureStatus));
/*     */   }
/*     */ 
/*     */   
/*     */   public void enqueueFinnishBuff(String buffId) {
/*  49 */     this.pendingBuffIds.add(buffId);
/*     */   }
/*     */   
/*     */   protected void fireBuffAdded(Buff buffAdded) {
/*  53 */     int size = this.listeners.size();
/*  54 */     for (int i = 0; i < size; i++) {
/*  55 */       BuffListener buffListener = this.listeners.get(i);
/*  56 */       buffListener.buffAdded(buffAdded);
/*     */     } 
/*     */     
/*  59 */     this.passiveAbilityHandlingSupport.addPassiveAbilities(buffAdded.getAbilities(), true);
/*  60 */     this.statSupport.resetModifiers(this.owner);
/*     */   }
/*     */   
/*     */   protected void fireBuffRemoved(Buff buffRemoved) {
/*  64 */     int size = this.listeners.size();
/*  65 */     for (int i = 0; i < size; i++) {
/*  66 */       BuffListener buffListener = this.listeners.get(i);
/*  67 */       buffListener.buffRemoved(buffRemoved);
/*     */     } 
/*     */     
/*  70 */     this.passiveAbilityHandlingSupport.removePassiveAbilities(buffRemoved.getAbilities());
/*  71 */     this.statSupport.resetModifiers(this.owner);
/*     */   }
/*     */   
/*     */   public void enqueue(Buff buffToAdd) {
/*  75 */     this.pendingBuffs.add(buffToAdd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processPending() {
/*  83 */     while (!this.pendingBuffs.isEmpty()) {
/*  84 */       processPendingBuff(this.pendingBuffs.remove(0));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void processPendingBuff(Buff paramBuff);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract List<Iterator<? extends Buff>> getBuffsIteratorList();
/*     */ 
/*     */   
/*     */   public int[] getUpdatePriorities() {
/*  98 */     return UPDATE_PRIORITIES;
/*     */   }
/*     */   
/*     */   public void processEvent(SupportEvent event) {
/* 102 */     if (event.getType() == PassiveAbilityHandlingSupport.EventType.ADD_PASSIVE_ABILITIES) {
/* 103 */       List<Iterator<? extends Buff>> list = getBuffsIteratorList();
/* 104 */       for (Iterator<? extends Buff> iterator : list) {
/* 105 */         while (iterator.hasNext()) {
/* 106 */           Buff buff = iterator.next();
/*     */           
/* 108 */           this.passiveAbilityHandlingSupport.addPassiveAbilities(buff.getAbilities(), true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void update(int priority, long updateMillis) {
/* 115 */     if (priority < 500000) {
/*     */ 
/*     */ 
/*     */       
/* 119 */       processPending();
/*     */ 
/*     */       
/* 122 */       List<Iterator<? extends Buff>> list = getBuffsIteratorList();
/* 123 */       for (Iterator<? extends Buff> iterator : list) {
/* 124 */         while (iterator.hasNext()) {
/* 125 */           Buff buff = iterator.next();
/* 126 */           buff.update(updateMillis);
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 132 */       removeFinishedBuffs();
/* 133 */       removeFinishedBuffIds();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void removeFinishedBuffIds() {
/* 138 */     int finishSize = this.pendingBuffIds.size();
/* 139 */     List<Iterator<? extends Buff>> list = getBuffsIteratorList();
/* 140 */     for (Iterator<? extends Buff> iterator : list) {
/* 141 */       while (iterator.hasNext()) {
/* 142 */         Buff buff = iterator.next();
/*     */         
/* 144 */         for (int j = finishSize - 1; j >= 0; j--) {
/* 145 */           String pendingBuffId = this.pendingBuffIds.get(j);
/* 146 */           if (buff.getId().equalsIgnoreCase(pendingBuffId)) {
/* 147 */             buff.finish();
/*     */           }
/*     */         } 
/*     */         
/* 151 */         if (buff.isFinished()) {
/* 152 */           iterator.remove();
/* 153 */           fireBuffRemoved(buff);
/*     */         } 
/*     */       } 
/*     */     } 
/* 157 */     for (int i = finishSize - 1; i >= 0; i--) {
/* 158 */       this.pendingBuffIds.remove(i);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void removeFinishedBuffs() {
/* 163 */     int finishSize = this.pendingBuffFinishEvents.size();
/* 164 */     List<Iterator<? extends Buff>> list = getBuffsIteratorList();
/* 165 */     for (Iterator<? extends Buff> iterator : list) {
/* 166 */       while (iterator.hasNext()) {
/* 167 */         Buff buff = iterator.next();
/*     */ 
/*     */         
/* 170 */         for (int j = finishSize - 1; j >= 0; j--) {
/* 171 */           BuffFinishEvent buffFinish = this.pendingBuffFinishEvents.get(j);
/* 172 */           if (buff.getType() == buffFinish.type && 
/* 173 */             buff.getCreator().matchCureCriterias(buffFinish.cureElement, buffFinish.cureStatus)) {
/* 174 */             buff.finish();
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 180 */         if (buff.isFinished()) {
/* 181 */           iterator.remove();
/* 182 */           fireBuffRemoved(buff);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 189 */     for (int i = finishSize - 1; i >= 0; i--) {
/* 190 */       this.pendingBuffFinishEvents.remove(i);
/*     */     }
/*     */   }
/*     */   
/*     */   public void reset() {
/* 195 */     List<Iterator<? extends Buff>> list = getBuffsIteratorList();
/* 196 */     for (Iterator<? extends Buff> iterator : list) {
/* 197 */       while (iterator.hasNext()) {
/* 198 */         Buff buff = iterator.next();
/*     */         
/* 200 */         iterator.remove();
/* 201 */         fireBuffRemoved(buff);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class BuffFinishEvent {
/*     */     private final BuffType type;
/*     */     private final Element cureElement;
/*     */     private final String cureStatus;
/*     */     
/*     */     public BuffFinishEvent(BuffType type, Element cureElement, String cureStatus) {
/* 212 */       this.type = type;
/* 213 */       this.cureElement = cureElement;
/* 214 */       this.cureStatus = cureStatus;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\AbstractBuffSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */