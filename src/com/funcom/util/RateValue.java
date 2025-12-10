/*     */ package com.funcom.util;
/*     */ import com.funcom.commons.utils.GlobalTime;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import javax.management.openmbean.CompositeData;
/*     */ import javax.management.openmbean.CompositeDataSupport;
/*     */ import javax.management.openmbean.CompositeType;
/*     */ import javax.management.openmbean.OpenType;
/*     */ import javax.management.openmbean.SimpleType;
/*     */ 
/*     */ public class RateValue {
/*  13 */   private final AtomicInteger next = new AtomicInteger();
/*     */   protected static final int MIN_VALID_MILLIS = 500;
/*  15 */   private final AtomicLong logCount = new AtomicLong();
/*  16 */   private final AtomicLong sum = new AtomicLong();
/*     */   
/*     */   private final long[] values;
/*     */   
/*     */   private final long[] loggedAts;
/*     */   private int bufferSize;
/*  22 */   protected volatile long lastValidPeriod = TimeUnit.MINUTES.toMillis(1L);
/*     */   
/*     */   protected volatile long lastSingleEntryTime;
/*     */   
/*     */   private volatile int oldCount;
/*     */   
/*     */   public RateValue(int bufferSize) {
/*  29 */     this.bufferSize = bufferSize;
/*  30 */     this.values = new long[bufferSize];
/*  31 */     this.loggedAts = new long[bufferSize];
/*     */   }
/*     */   
/*     */   public void log(long value) {
/*     */     int index;
/*  36 */     while ((index = this.next.getAndIncrement()) >= this.bufferSize) {
/*  37 */       this.next.compareAndSet(index + 1, 0);
/*     */     }
/*     */     
/*  40 */     this.loggedAts[index] = GlobalTime.getInstance().getCurrentTime();
/*  41 */     this.values[index] = value;
/*  42 */     this.sum.addAndGet(value);
/*  43 */     this.logCount.incrementAndGet();
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeData getMBeanData() throws OpenDataException {
/*  48 */     double min = Double.POSITIVE_INFINITY;
/*  49 */     double max = Double.NEGATIVE_INFINITY;
/*     */     
/*  51 */     for (int i = this.values.length - 1; i >= 0; i--) {
/*  52 */       long value = this.values[i];
/*     */       
/*  54 */       if (value > max) {
/*  55 */         max = value;
/*     */       }
/*     */       
/*  58 */       if (value < min) {
/*  59 */         min = value;
/*     */       }
/*     */     } 
/*     */     
/*  63 */     return new CompositeDataSupport(new CompositeType("RateValue", "Statistics (may need setter to set this desc)", new String[] { "logCount", "vps", "average", "stdDeviation", "sum", "min", "max" }, new String[] { "Log Count", "Value Per Second", "Average", "Standard Deviatiom", "Sum", "Min", "Max" }, (OpenType<?>[])new OpenType[] { SimpleType.LONG, SimpleType.DOUBLE, SimpleType.DOUBLE, SimpleType.DOUBLE, SimpleType.LONG, SimpleType.DOUBLE, SimpleType.DOUBLE }), new String[] { "logCount", "vps", "average", "stdDeviation", "sum", "min", "max" }, new Object[] { Long.valueOf(getLogCount()), Double.valueOf(getValuePerSecond()), Double.valueOf(getAverage()), Double.valueOf(getStandardDeviation()), Long.valueOf(getSum()), Double.valueOf(min), Double.valueOf(max) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLogCount() {
/*  72 */     return this.logCount.longValue();
/*     */   }
/*     */   
/*     */   public double getStandardDeviation() {
/*  76 */     double avg = getAverage();
/*  77 */     double stdDeviation = 0.0D;
/*  78 */     int count = 0;
/*     */     
/*  80 */     for (int i = this.values.length - 1; i >= 0; i--) {
/*  81 */       if (this.loggedAts[i] > 0L) {
/*     */         
/*  83 */         double diff = this.values[i] - avg;
/*  84 */         stdDeviation += diff * diff;
/*  85 */         count++;
/*     */       } 
/*     */     } 
/*     */     
/*  89 */     if (count > 0) {
/*     */       
/*  91 */       stdDeviation /= (count - 1);
/*  92 */       stdDeviation = Math.sqrt(stdDeviation);
/*     */     } 
/*  94 */     return stdDeviation;
/*     */   }
/*     */   
/*     */   public long getSum() {
/*  98 */     return this.sum.longValue();
/*     */   }
/*     */   
/*     */   public double getAverage() {
/* 102 */     double ret = 0.0D;
/* 103 */     int count = 0;
/*     */     
/* 105 */     for (int i = this.values.length - 1; i >= 0; i--) {
/* 106 */       if (this.loggedAts[i] != 0L) {
/* 107 */         ret += this.values[i];
/* 108 */         count++;
/*     */       } 
/*     */     } 
/*     */     
/* 112 */     if (count > 0) {
/* 113 */       return ret / count;
/*     */     }
/*     */     
/* 116 */     return 0.0D;
/*     */   }
/*     */   
/*     */   public double getValuePerSecond() {
/* 120 */     long fromMillis = Long.MAX_VALUE;
/* 121 */     long toMillis = Long.MIN_VALUE;
/*     */     
/* 123 */     int removedCount = 0;
/* 124 */     long lastReadNonZeroTimestamp = 0L;
/*     */     
/* 126 */     long sum = 0L;
/* 127 */     int validCount = 0;
/*     */     
/* 129 */     long lastValidMillis = GlobalTime.getInstance().getCurrentTime() - this.lastValidPeriod;
/*     */ 
/*     */ 
/*     */     
/* 133 */     for (int i = this.values.length - 1; i >= 0; i--) {
/* 134 */       long loggedAt = this.loggedAts[i];
/*     */       
/* 136 */       if (loggedAt > 0L) {
/*     */         
/* 138 */         lastReadNonZeroTimestamp = loggedAt;
/*     */         
/* 140 */         if (loggedAt < lastValidMillis) {
/*     */           
/* 142 */           this.loggedAts[i] = 0L;
/* 143 */           removedCount++;
/*     */         }
/*     */         else {
/*     */           
/* 147 */           sum += this.values[i];
/* 148 */           validCount++;
/*     */           
/* 150 */           if (loggedAt > toMillis) {
/* 151 */             toMillis = loggedAt;
/*     */           }
/* 153 */           if (loggedAt < fromMillis) {
/* 154 */             fromMillis = loggedAt;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 161 */     long dataTimeGap = toMillis - fromMillis;
/* 162 */     double ret = 0.0D;
/* 163 */     if (validCount > 1 && dataTimeGap != 0L) {
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
/* 175 */       long dataDuration = dataTimeGap * validCount / (validCount - 1);
/*     */       
/* 177 */       ret = processValuePerSecond(sum, validCount, dataDuration);
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 185 */       long _lastSingleEntryTime = this.lastSingleEntryTime;
/*     */       
/* 187 */       if (removedCount + validCount == 1 && _lastSingleEntryTime == 0L) {
/*     */ 
/*     */         
/* 190 */         processLogSingleEntryTime(lastReadNonZeroTimestamp, sum, validCount, dataTimeGap);
/* 191 */       } else if (this.oldCount == 0 && validCount + removedCount == 1 && _lastSingleEntryTime != 0L) {
/*     */ 
/*     */         
/* 194 */         processLongValidTimePeriod(sum, validCount, dataTimeGap, _lastSingleEntryTime);
/*     */       }
/*     */       else {
/*     */         
/* 198 */         processEmpty(sum, validCount, dataTimeGap);
/*     */       } 
/*     */     } 
/*     */     
/* 202 */     this.oldCount = validCount;
/*     */     
/* 204 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processEmpty(long sum, int validCount, long dataTimeGap) {}
/*     */ 
/*     */   
/*     */   protected void processLongValidTimePeriod(long sum, int validCount, long dataTimeGap, long _lastSingleEntryTime) {
/* 213 */     long fromLastSingleEntryToNow = GlobalTime.getInstance().getCurrentTime() - _lastSingleEntryTime;
/* 214 */     this.lastValidPeriod = Math.max(fromLastSingleEntryToNow * 5L / 4L, 500L);
/* 215 */     this.lastSingleEntryTime = 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processLogSingleEntryTime(long lastReadNonZeroTimestamp, long sum, int validCount, long dataTimeGap) {
/* 220 */     this.lastSingleEntryTime = lastReadNonZeroTimestamp;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected double processValuePerSecond(long sum, int validCount, long dataDuration) {
/* 226 */     this.lastValidPeriod = dataDuration + dataDuration * 2L / validCount;
/* 227 */     if (this.lastValidPeriod < 500L) {
/* 228 */       this.lastValidPeriod = 500L;
/*     */     }
/*     */ 
/*     */     
/* 232 */     this.lastSingleEntryTime = 0L;
/*     */ 
/*     */     
/* 235 */     return sum * 1000.0D / dataDuration;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funco\\util\RateValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */