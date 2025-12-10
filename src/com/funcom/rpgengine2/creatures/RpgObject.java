/*     */ package com.funcom.rpgengine2.creatures;
/*     */ 
/*     */ import com.funcom.rpgengine2.abilities.TargetProvider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RpgObject
/*     */   implements RpgTargetProviderEntity, RpgSourceProviderEntity
/*     */ {
/*  18 */   private volatile LifeCycle lifeCycle = LifeCycle.NEW;
/*     */   
/*  20 */   protected final List<SupportEventListener> eventListeners = new ArrayList<SupportEventListener>(5);
/*  21 */   protected final Map<Class, Object> supportMap = (Map)new HashMap<Class<?>, Object>();
/*  22 */   protected final List<UpdateEntry> updateEntries = new ArrayList<UpdateEntry>();
/*  23 */   protected final List<LifeCycleAware> lifeCycleAwares = new ArrayList<LifeCycleAware>(1);
/*     */ 
/*     */   
/*     */   public void addSupport(Object support) {
/*  27 */     if (this.lifeCycle != LifeCycle.NEW)
/*     */     {
/*  29 */       throw new IllegalStateException("cannot add support: state=" + this.lifeCycle);
/*     */     }
/*     */ 
/*     */     
/*  33 */     Class<?> tmp = support.getClass();
/*  34 */     mapSupports(tmp, support);
/*     */ 
/*     */     
/*  37 */     if (support instanceof RpgUpdateable) {
/*  38 */       RpgUpdateable updateable = (RpgUpdateable)support;
/*     */       
/*  40 */       int[] updatePriorities = updateable.getUpdatePriorities();
/*  41 */       for (int updatePriority : updatePriorities) {
/*  42 */         this.updateEntries.add(new UpdateEntry(updatePriority, updateable));
/*     */       }
/*     */       
/*  45 */       Collections.sort(this.updateEntries);
/*     */     } 
/*     */ 
/*     */     
/*  49 */     if (support instanceof LifeCycleAware) {
/*  50 */       this.lifeCycleAwares.add((LifeCycleAware)support);
/*     */     }
/*     */ 
/*     */     
/*  54 */     if (support instanceof SupportEventListener) {
/*  55 */       this.eventListeners.add((SupportEventListener)support);
/*     */     }
/*     */   }
/*     */   
/*     */   private void mapSupports(Class<?> tmp, Object support) {
/*  60 */     while (tmp != null) {
/*     */       
/*  62 */       Class[] interfaces = tmp.getInterfaces();
/*  63 */       for (Class<RpgQueryableSupport> anInterface : interfaces) {
/*  64 */         if (anInterface != RpgQueryableSupport.class && RpgQueryableSupport.class.isAssignableFrom(anInterface))
/*     */         {
/*  66 */           mapSupports(anInterface, support);
/*     */         }
/*     */       } 
/*     */       
/*  70 */       if (RpgQueryableSupport.class.isAssignableFrom(tmp)) {
/*  71 */         _mapSupport(tmp, support);
/*  72 */         tmp = tmp.getSuperclass();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void _mapSupport(Class supportKey, Object support) {
/*  80 */     if (this.supportMap.containsKey(supportKey)) {
/*  81 */       throw new IllegalStateException("Duplicate RpgSupport mapping: duplicatedKeyClass=" + supportKey + " alreadyMappedObjectClass=" + this.supportMap.get(supportKey).getClass() + " triedToMapToObjectClass=" + support.getClass());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  86 */     this.supportMap.put(supportKey, support);
/*     */   }
/*     */   
/*     */   public <E extends RpgQueryableSupport> E getSupport(Class<E> supportClass) {
/*  90 */     if (this.lifeCycle == LifeCycle.NEW) {
/*  91 */       throw new IllegalStateException("cannot get support: state=" + this.lifeCycle + " class=" + getClass());
/*     */     }
/*  93 */     return getSupportIgnoreLifeCycle(supportClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public <E extends RpgQueryableSupport> E getSupportIgnoreLifeCycle(Class<E> supportClass) {
/*  98 */     return (E)this.supportMap.get(supportClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() throws Exception {
/* 109 */     if (this.lifeCycle != LifeCycle.NEW) {
/* 110 */       throw new IllegalStateException("cannot init: state=" + this.lifeCycle);
/*     */     }
/*     */     
/* 113 */     int size = this.lifeCycleAwares.size();
/* 114 */     for (int i = 0; i < size; i++) {
/* 115 */       ((LifeCycleAware)this.lifeCycleAwares.get(i)).init();
/*     */     }
/*     */     
/* 118 */     this.lifeCycle = LifeCycle.READY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 125 */     if (this.lifeCycle != LifeCycle.DISPOSED) {
/* 126 */       int size = this.lifeCycleAwares.size();
/* 127 */       for (int i = size - 1; i >= 0; i--) {
/* 128 */         ((LifeCycleAware)this.lifeCycleAwares.get(i)).dispose();
/*     */       }
/*     */       
/* 131 */       this.lifeCycle = LifeCycle.DISPOSED;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(long updateMillis) {
/* 142 */     int updateableCount = this.updateEntries.size();
/* 143 */     for (int i = 0; i < updateableCount; i++) {
/* 144 */       UpdateEntry updateEntry = this.updateEntries.get(i);
/* 145 */       updateEntry.updateable.update(updateEntry.updatePriority, updateMillis);
/*     */     } 
/*     */   }
/*     */   
/*     */   public RpgEntity getSourceObject() {
/* 150 */     return this;
/*     */   }
/*     */   
/*     */   public RpgEntity getSourceHandler() {
/* 154 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyHit(TargetProvider targetProvider) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireEvent(SupportEvent event) {
/* 164 */     int size = this.eventListeners.size();
/* 165 */     for (int i = 0; i < size; i++) {
/* 166 */       ((SupportEventListener)this.eventListeners.get(i)).processEvent(event);
/*     */     }
/*     */   }
/*     */   
/*     */   public RpgEntity getTargetHandler() {
/* 171 */     return this;
/*     */   }
/*     */   
/*     */   public RpgEntity getTargetObject() {
/* 175 */     return this;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 179 */     return getClass() + "@" + Integer.toHexString(System.identityHashCode(this)) + "{" + "supportMap=" + this.supportMap + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class UpdateEntry
/*     */     implements Comparable
/*     */   {
/*     */     private final int updatePriority;
/*     */ 
/*     */     
/*     */     private final RpgUpdateable updateable;
/*     */ 
/*     */     
/*     */     public UpdateEntry(int updatePriority, RpgUpdateable updateable) {
/* 194 */       this.updatePriority = updatePriority;
/* 195 */       this.updateable = updateable;
/*     */     }
/*     */     
/*     */     public int compareTo(Object o) {
/* 199 */       UpdateEntry other = (UpdateEntry)o;
/*     */       
/* 201 */       return this.updatePriority - other.updatePriority;
/*     */     }
/*     */   }
/*     */   
/*     */   private enum LifeCycle {
/* 206 */     NEW, READY, DISPOSED;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\RpgObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */