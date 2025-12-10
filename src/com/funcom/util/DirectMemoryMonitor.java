/*     */ package com.funcom.util;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DirectMemoryMonitor
/*     */   implements Runnable
/*     */ {
/*  14 */   private static DirectMemoryMonitor INSTANCE = new DirectMemoryMonitor();
/*     */   
/*  16 */   private static Map<String, AllocationData> countMap = new ConcurrentHashMap<String, AllocationData>();
/*     */   private static Field maxMemoryField;
/*     */   private static Field reservedMemoryField;
/*     */   
/*     */   static {
/*     */     try {
/*  22 */       Class<?> cl = Class.forName("java.nio.Bits");
/*  23 */       maxMemoryField = cl.getDeclaredField("maxMemory");
/*  24 */       maxMemoryField.setAccessible(true);
/*  25 */       reservedMemoryField = cl.getDeclaredField("reservedMemory");
/*  26 */       reservedMemoryField.setAccessible(true);
/*  27 */     } catch (Exception e) {
/*  28 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String logStart() {
/*  33 */     StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
/*  34 */     StringBuilder buf = new StringBuilder(256);
/*     */     
/*  36 */     int length = stackTraceElements.length;
/*  37 */     for (int i = 4; i < length; i++) {
/*  38 */       StackTraceElement element = stackTraceElements[i];
/*  39 */       buf.append(element.getClassName()).append(".").append(element.getMethodName()).append("  ");
/*     */     } 
/*  41 */     String trace = buf.toString();
/*     */     
/*  43 */     synchronized (countMap) {
/*  44 */       AllocationData data = countMap.get(trace);
/*  45 */       if (data == null) {
/*  46 */         data = new AllocationData(stackTraceElements);
/*  47 */         countMap.put(trace, data);
/*     */       } 
/*  49 */       data.counter.incrementAndGet();
/*  50 */       data.useCount.incrementAndGet();
/*     */       try {
/*  52 */         data.memAt.set(((Long)reservedMemoryField.get((Object)null)).longValue());
/*  53 */       } catch (IllegalAccessException e) {
/*  54 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/*  58 */     return "s";
/*     */   }
/*     */   
/*     */   public static String logEnd() {
/*  62 */     StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
/*  63 */     StringBuilder buf = new StringBuilder(256);
/*     */     
/*  65 */     int length = stackTraceElements.length;
/*  66 */     for (int i = 4; i < length; i++) {
/*  67 */       StackTraceElement element = stackTraceElements[i];
/*  68 */       buf.append(element.getClassName()).append(".").append(element.getMethodName()).append("  ");
/*     */     } 
/*  70 */     String trace = buf.toString();
/*     */     
/*  72 */     synchronized (countMap) {
/*  73 */       AllocationData data = countMap.get(trace);
/*  74 */       data.counter.incrementAndGet();
/*  75 */       data.useCount.incrementAndGet();
/*     */       
/*     */       try {
/*  78 */         long reservedMemory = ((Long)reservedMemoryField.get((Object)null)).longValue();
/*  79 */         long increase = reservedMemory - data.memAt.get();
/*  80 */         data.totalUse.addAndGet(increase);
/*  81 */       } catch (IllegalAccessException e) {
/*  82 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/*  86 */     return "e";
/*     */   }
/*     */   
/*     */   private static class AllocationData
/*     */   {
/*     */     private final StackTraceElement[] stackTraceElements;
/*  92 */     public AtomicInteger counter = new AtomicInteger();
/*  93 */     public AtomicLong useCount = new AtomicLong();
/*  94 */     public AtomicLong totalUse = new AtomicLong();
/*  95 */     public AtomicLong memAt = new AtomicLong();
/*     */     
/*     */     public AllocationData(StackTraceElement[] stackTraceElements) {
/*  98 */       this.stackTraceElements = stackTraceElements;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 103 */       return "AllocationData{totalUse=" + this.totalUse + ", counter=" + this.counter + ", useCount=" + this.useCount + ", memAt=" + this.memAt + '}';
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/* 115 */       long lastMaxMem = 0L;
/* 116 */       long lastReservedMem = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       while (true) {
/* 122 */         long maxMemory = ((Long)maxMemoryField.get((Object)null)).longValue();
/* 123 */         long reservedMemory = ((Long)reservedMemoryField.get((Object)null)).longValue();
/*     */         
/* 125 */         System.out.println("maxMemory = " + maxMemory + " delta=" + (maxMemory - lastMaxMem));
/* 126 */         System.out.println("reservedMemory = " + reservedMemory + " delta=" + (reservedMemory - lastReservedMem));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 132 */         lastMaxMem = maxMemory;
/* 133 */         lastReservedMem = reservedMemory;
/*     */         
/* 135 */         sleep(5000);
/*     */       } 
/* 137 */     } catch (IllegalAccessException e) {
/* 138 */       e.printStackTrace();
/*     */       return;
/*     */     } 
/*     */   }
/*     */   private void sleep(int millis) {
/*     */     try {
/* 144 */       Thread.sleep(millis);
/* 145 */     } catch (InterruptedException e) {
/* 146 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void start() {
/* 151 */     Thread t = new Thread(this, getClass().getSimpleName());
/* 152 */     t.setDaemon(true);
/* 153 */     t.start();
/*     */   }
/*     */   
/*     */   public static void show() {
/* 157 */     INSTANCE.start();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funco\\util\DirectMemoryMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */