/*     */ package com.funcom.gameengine.model.token;
/*     */ 
/*     */ import com.funcom.gameengine.model.token.logging.TokenLoggerParameter;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TokenProcessor
/*     */   implements Runnable
/*     */ {
/*  23 */   private static final Map<Class<?>, TimeData> TIMINGS = new HashMap<Class<?>, TimeData>();
/*     */   
/*     */   private static final boolean PROFILE_TOKENS = false;
/*  26 */   private static final Logger CSV_LOGGER = Logger.getLogger(TokenProcessor.class.getName() + "_csv");
/*     */   private boolean running;
/*     */   private boolean isYielding;
/*     */   private boolean isWaiting;
/*     */   private boolean paused;
/*     */   private static long printSec;
/*     */   
/*     */   public void stop() {
/*  34 */     this.running = false;
/*     */     
/*  36 */     TokenRegister.instance().addToken(new DummyOpenToken());
/*     */   }
/*     */   
/*     */   public void setPaused(boolean paused) {
/*  40 */     this.isYielding = false;
/*  41 */     this.paused = paused;
/*     */   }
/*     */   
/*     */   public boolean isRunning() {
/*  45 */     return this.running;
/*     */   }
/*     */   
/*     */   public boolean isYielding() {
/*  49 */     return this.isYielding;
/*     */   }
/*     */   
/*     */   public boolean isWaiting() {
/*  53 */     return this.isWaiting;
/*     */   }
/*     */   
/*     */   public void run() {
/*  57 */     this.running = true;
/*  58 */     while (this.running) {
/*  59 */       if (this.paused) {
/*  60 */         this.isYielding = true;
/*  61 */         Thread.yield(); continue;
/*     */       } 
/*     */       try {
/*  64 */         this.isWaiting = true;
/*  65 */         Token token = TokenRegister.instance().takeOpenToken();
/*  66 */         this.isWaiting = false;
/*  67 */         long startNanos = System.nanoTime();
/*  68 */         token.process();
/*  69 */         long endNanos = System.nanoTime();
/*     */         
/*  71 */         profile(token, startNanos, endNanos);
/*     */         
/*  73 */         CSV_LOGGER.log((Priority)Level.INFO, new TokenLoggerParameter(token.getClass(), token.getTokenType(), endNanos - startNanos, startNanos));
/*  74 */         if (endNanos - startNanos > 50000000L)
/*  75 */           System.out.println(token.getClass().getSimpleName() + " " + (endNanos - startNanos)); 
/*  76 */       } catch (InterruptedException e) {
/*  77 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean runonce() {
/*     */     try {
/*  85 */       this.isWaiting = true;
/*  86 */       if (TokenRegister.instance().peekOpenToken() == null) {
/*  87 */         return false;
/*     */       }
/*  89 */       Token token = TokenRegister.instance().takeOpenToken();
/*  90 */       this.isWaiting = false;
/*  91 */       token.process();
/*  92 */     } catch (InterruptedException e) {
/*  93 */       throw new RuntimeException(e);
/*     */     } 
/*  95 */     return true;
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
/*     */   
/*     */   public static void profile(Token token, long startNanos, long endNanos) {}
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
/*     */   private static class TimeData
/*     */   {
/* 145 */     static NumberFormat format = new DecimalFormat("##########################.###############");
/* 146 */     AtomicLong used = new AtomicLong();
/* 147 */     AtomicLong times = new AtomicLong();
/* 148 */     volatile long max = Long.MIN_VALUE;
/* 149 */     volatile long min = Long.MAX_VALUE;
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 154 */       return "TimeData{used=" + this.used + ", times=" + this.times + ", avg=" + format.format(getAverage()) + ", max=" + this.max + ", min=" + this.min + '}';
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double getAverage() {
/* 164 */       return this.used.get() / this.times.get();
/*     */     }
/*     */     
/*     */     private TimeData() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\TokenProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */