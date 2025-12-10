/*     */ package com.funcom.commons.dfx;
/*     */ 
/*     */ import com.funcom.commons.utils.GlobalTime;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DireEffectDescription
/*     */ {
/*  18 */   private static final Logger LOG = Logger.getLogger(DireEffectDescription.class.getName());
/*  19 */   private static final Map<String, LogEntry> logFrequences = new HashMap<String, LogEntry>();
/*     */   
/*     */   private final String id;
/*     */   private final List<EffectDescription> effectDescriptions;
/*  23 */   private double duration = -1.0D;
/*     */   
/*     */   public DireEffectDescription(String id) {
/*  26 */     this.id = id;
/*  27 */     this.effectDescriptions = new ArrayList<EffectDescription>();
/*     */   }
/*     */   
/*     */   public String getId() {
/*  31 */     return this.id;
/*     */   }
/*     */   
/*     */   public List<EffectDescription> getEffectDescriptions() {
/*  35 */     return this.effectDescriptions;
/*     */   }
/*     */ 
/*     */   
/*     */   public <E extends EffectDescription> List<E> getEffectDescriptions(Class<E> byThisClass) {
/*  40 */     List<E> ret = new ArrayList<E>();
/*     */     
/*  42 */     int size = this.effectDescriptions.size();
/*  43 */     for (int i = 0; i < size; i++) {
/*  44 */       EffectDescription description = this.effectDescriptions.get(i);
/*  45 */       if (byThisClass.isAssignableFrom(description.getClass())) {
/*  46 */         ret.add((E)description);
/*     */       }
/*     */     } 
/*     */     
/*  50 */     return ret;
/*     */   }
/*     */   
/*     */   public void addEffect(EffectDescription effect) {
/*  54 */     this.effectDescriptions.add(effect);
/*     */   }
/*     */   
/*     */   public double getDuration() {
/*  58 */     if (this.duration < 0.0D)
/*  59 */       this.duration = calculateDuration(); 
/*  60 */     return this.duration;
/*     */   }
/*     */   
/*     */   public double calculateDuration() {
/*  64 */     double duration = 0.0D;
/*  65 */     for (EffectDescription description : this.effectDescriptions) {
/*  66 */       if (description instanceof AnimationEffectDescription || description instanceof RpgKeepAliveEffectDescription)
/*  67 */         duration = Math.max(duration, description.getEndTime()); 
/*     */     } 
/*  69 */     return duration;
/*     */   }
/*     */ 
/*     */   
/*     */   public DireEffect createInstance(Object instanceSource, Object instanceData) {
/*  74 */     int size = this.effectDescriptions.size();
/*  75 */     List<Effect> instanceEffects = new ArrayList<Effect>(size);
/*     */     
/*  77 */     for (int i = 0; i < size; i++) {
/*  78 */       EffectDescription effectDescription = this.effectDescriptions.get(i);
/*     */ 
/*     */       
/*  81 */       Effect effect = effectDescription.createInstance(instanceSource, instanceData);
/*  82 */       instanceEffects.add(effect);
/*     */     } 
/*     */     
/*  85 */     if (LOG.isEnabledFor((Priority)Level.INFO)) {
/*  86 */       LogEntry logEntry = logFrequences.get(getId());
/*  87 */       if (logEntry == null) {
/*  88 */         logEntry = new LogEntry(getId());
/*  89 */         logFrequences.put(getId(), logEntry);
/*     */       } 
/*     */       
/*  92 */       logEntry.logNewInstance();
/*     */       
/*  94 */       if (logEntry.isDelayPassed()) {
/*  95 */         logEntry.printLog();
/*     */       }
/*     */     } 
/*  98 */     return new DireEffect(this, instanceEffects);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 102 */     return this.effectDescriptions.isEmpty();
/*     */   }
/*     */   
/*     */   private static class LogEntry
/*     */   {
/*     */     private String id;
/*     */     private int countSinceLast;
/*     */     private long timeAtLast;
/*     */     private int delay;
/*     */     
/*     */     public LogEntry(String id) {
/* 113 */       this.id = id;
/* 114 */       this.delay = 1000;
/*     */       
/* 116 */       if (id.indexOf("/idle.") >= 0 || id.indexOf("/move.") >= 0)
/*     */       {
/* 118 */         this.delay = 30000;
/*     */       }
/*     */     }
/*     */     
/*     */     public void logNewInstance() {
/* 123 */       this.countSinceLast++;
/*     */     }
/*     */     
/*     */     public boolean isDelayPassed() {
/* 127 */       return (GlobalTime.getInstance().getCurrentTime() - this.timeAtLast >= this.delay);
/*     */     }
/*     */     
/*     */     public void printLog() {
/* 131 */       DireEffectDescription.LOG.info("New DFX Instance: " + this.id + " (" + this.countSinceLast + " time(s))");
/* 132 */       this.countSinceLast = 0;
/* 133 */       this.timeAtLast = GlobalTime.getInstance().getCurrentTime();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\dfx\DireEffectDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */