/*     */ package com.funcom.tcg.client;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.jme.util.Timer;
/*     */ import java.util.concurrent.locks.LockSupport;
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
/*     */ public class FpsManager
/*     */ {
/*     */   private static final long SEC_TO_NANO = 1000000000L;
/*     */   private Timer timer;
/*     */   private long frameStartTick;
/*  24 */   private float reservedTimePercent = 0.25F;
/*  25 */   private int minfps = 15;
/*  26 */   private Double MinSecPerFrame = Double.valueOf(1.0D / this.minfps);
/*  27 */   private int fps = this.minfps;
/*  28 */   private Double SecPerFrame = Double.valueOf(1.0D / this.fps);
/*     */   
/*  30 */   private long FPSPreviousTime = -1L;
/*     */   
/*  32 */   private long MinSleep = 2000000L;
/*  33 */   private long DefaultMinSleep = 2000000L;
/*  34 */   private long MinSleepLostFocus = 50000000L;
/*     */   private boolean HasFocus = true;
/*     */   
/*     */   public FpsManager(Timer timer) {
/*  38 */     this.timer = timer;
/*  39 */     String str = System.getProperty("FpsManager.MinSleep");
/*  40 */     if (str != null && !str.isEmpty()) {
/*  41 */       this.DefaultMinSleep = Long.parseLong(str);
/*     */     }
/*     */     
/*  44 */     str = System.getProperty("FpsManager.MinSleepLostFocus");
/*  45 */     if (str != null && !str.isEmpty()) {
/*  46 */       this.MinSleepLostFocus = Long.parseLong(str);
/*     */     }
/*     */     
/*  49 */     resetMinSleep();
/*     */   }
/*     */   
/*     */   void startFrame() {
/*  53 */     this.frameStartTick = this.timer.getTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Double getNumSecondsLeft(Double sec) {
/*  60 */     long ElapsedTicks = this.timer.getTime() - this.frameStartTick;
/*  61 */     Double dElapsedSec = Double.valueOf(Double.longBitsToDouble(ElapsedTicks) / Double.longBitsToDouble(this.timer.getResolution()));
/*  62 */     return Double.valueOf(sec.doubleValue() - dElapsedSec.doubleValue());
/*     */   }
/*     */ 
/*     */   
/*     */   long endFrame() {
/*  67 */     Double dDiffSec = getNumSecondsLeft(this.MinSecPerFrame);
/*     */ 
/*     */     
/*  70 */     dDiffSec = Double.valueOf(dDiffSec.doubleValue() - this.MinSecPerFrame.doubleValue() * this.reservedTimePercent);
/*     */ 
/*     */     
/*  73 */     if (dDiffSec.doubleValue() <= 0.0D)
/*  74 */       return 0L; 
/*  75 */     return (long)(dDiffSec.doubleValue() * 1.0E9D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFps(int fps) {
/*  80 */     if (fps <= 0) {
/*  81 */       throw new IllegalArgumentException("Frames per second cannot be less than one.");
/*     */     }
/*  83 */     this.fps = fps;
/*  84 */     this.SecPerFrame = Double.valueOf(1.0D / fps);
/*     */   }
/*     */   
/*     */   public void setMinFps(int fps) {
/*  88 */     if (fps <= 0) {
/*  89 */       throw new IllegalArgumentException("Min Frames per second cannot be less than one.");
/*     */     }
/*  91 */     this.minfps = fps;
/*  92 */     this.MinSecPerFrame = Double.valueOf(1.0D / this.minfps);
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
/*     */   public void setReservedTimePercent(float reservedTimePercent) {
/* 106 */     this.reservedTimePercent = reservedTimePercent;
/*     */   }
/*     */   
/*     */   void setMinSleep(long nanos) {
/* 110 */     this.MinSleep = nanos;
/*     */   }
/*     */   
/*     */   void setHasFocus(boolean b) {
/* 114 */     this.HasFocus = b;
/*     */   }
/*     */ 
/*     */   
/*     */   void resetMinSleep() {
/* 119 */     if (LoadingManager.INSTANCE.getAvailableProcessors() <= 1) {
/*     */ 
/*     */       
/* 122 */       this.MinSleep = this.DefaultMinSleep;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 131 */       this.MinSleep = 0L;
/*     */     } 
/*     */   }
/*     */   
/*     */   long getMinSleep() {
/* 136 */     if (this.HasFocus) {
/* 137 */       resetMinSleep();
/* 138 */       return this.MinSleep;
/*     */     } 
/*     */     
/* 141 */     return this.MinSleepLostFocus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void sleepExcessFrameTime() {
/* 150 */     long nCurrentMinSleep = getMinSleep();
/*     */     
/* 152 */     long nReservedNanos = (long)(this.SecPerFrame.doubleValue() * this.reservedTimePercent * 1.0E9D);
/* 153 */     long nNumNanosLeft = (long)(getNumSecondsLeft(this.SecPerFrame).doubleValue() * 1.0E9D);
/* 154 */     if (nNumNanosLeft < nReservedNanos) {
/* 155 */       nNumNanosLeft = nReservedNanos;
/*     */     }
/*     */     try {
/* 158 */       if (nNumNanosLeft < nCurrentMinSleep) {
/* 159 */         nNumNanosLeft = nCurrentMinSleep;
/*     */       }
/*     */       
/* 162 */       if (nNumNanosLeft <= 0L) {
/* 163 */         Thread.yield();
/*     */       } else {
/*     */         
/* 166 */         LockSupport.parkNanos(nNumNanosLeft);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 171 */     catch (Exception e) {
/* 172 */       System.err.println("Error sleeping during main loop.");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Double getFramesPerSecond() {
/* 178 */     if (this.FPSPreviousTime < 0L) {
/* 179 */       this.FPSPreviousTime = this.timer.getTime();
/*     */     }
/*     */ 
/*     */     
/* 183 */     long CurrentTime = this.timer.getTime();
/* 184 */     long ElapsedTicks = CurrentTime - this.FPSPreviousTime;
/* 185 */     this.FPSPreviousTime = CurrentTime;
/* 186 */     Double dElapsedSec = Double.valueOf(Double.longBitsToDouble(ElapsedTicks) / Double.longBitsToDouble(this.timer.getResolution()));
/*     */     
/* 188 */     return Double.valueOf(1.0D / dElapsedSec.doubleValue());
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\FpsManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */