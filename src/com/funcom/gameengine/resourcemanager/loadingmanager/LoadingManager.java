/*     */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*     */ 
/*     */ import com.funcom.errorhandling.AchaDoomsdayErrorHandler;
/*     */ import com.funcom.errorhandling.DoomsdayErrorHandler;
/*     */ import com.funcom.gameengine.debug.TimeStamper;
/*     */ import com.funcom.gameengine.inspector.HardwareInspector;
/*     */ import com.funcom.gameengine.inspector.HardwareSpec;
/*     */ import com.funcom.gameengine.model.chunks.ChunkListener;
/*     */ import com.funcom.gameengine.model.token.TokenProcessor;
/*     */ import com.funcom.gameengine.model.token.TokenRegister;
/*     */ import com.jme.util.NanoTimer;
/*     */ import com.jme.util.Timer;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.util.AbstractQueue;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.pleasantnightmare.noraxidium.util.CrashWindow;
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
/*     */ public enum LoadingManager
/*     */ {
/*  65 */   INSTANCE; AchaDoomsdayErrorHandler.AchaBugreportDataFeeder mDataFeeder; LinkedList<LoadingManagerToken> mCurrentThreadedQueue; ThreadPoolExecutor mThreadedExecutor; BlockingQueue<Runnable> mThreadedWorkQueue; private Double mDistanceFactor; private Double mFarDistance; private Double mNearDistance; private long mMaxToStealLowSpec; private long mMaxToSteal; private long mToSteal; private static Double AVAILABLE_PROCESSORS_TIME_STEP; private long mAvailableProcessorsTimeout;
/*     */   private volatile int mAvailableProcessors;
/*     */   public boolean mForceAllLoading;
/*     */   private ArrayList<LoadingManagerToken> mDelayedTokens;
/*     */   private LoadingManagerToken mCurrent;
/*     */   private long mReservedTicks;
/*     */   private volatile boolean mProcessing;
/*     */   private Timer mTimer;
/*     */   private ThreadedLoadingTokenQueue mSoundThreadedQueue;
/*     */   private ThreadedLoadingTokenQueue mThreadedQueue;
/*     */   private DistanceLoadingTokenQueue<LoadingManagerToken> mDistanceLoadingQueue;
/*     */   private PrioritizedLoadingTokenQueue<LoadingManagerToken> mPriorityLoadingQueue;
/*     */   private AbstractQueue mLoadingQueue;
/*     */   public TokenProcessor mTokenProcessor;
/*     */   public static boolean TILE_TEXTURE_ATLAS;
/*     */   public static Double LOADING_TIMEOUT;
/*     */   public static float MAX_TIMELAG_SOUNDPLAYING;
/*     */   public static boolean USE_MERGED_MESHES;
/*     */   public static boolean USE_DDS_FORMAT;
/*     */   public static boolean USE_LIP;
/*     */   public static boolean THREADED_SOUND;
/*     */   public static boolean DISTANCE_LOADING;
/*     */   public static boolean LINEAR_STEAL;
/*     */   public static boolean ADAPTABLE_FPS;
/*     */   public static boolean DEBUG_INFO;
/*     */   public static boolean MULTITHREADED;
/*     */   public static boolean USE;
/*     */   public static boolean CRASH_ON_EXCEPTION;
/*     */   
/*  94 */   LoadingManager() { this.mTokenProcessor = null;
/*  95 */     this.mLoadingQueue = null;
/*  96 */     this.mPriorityLoadingQueue = null;
/*  97 */     this.mDistanceLoadingQueue = null;
/*  98 */     this.mThreadedQueue = null;
/*  99 */     this.mSoundThreadedQueue = null;
/*     */     
/* 101 */     this.mTimer = (Timer)new NanoTimer();
/* 102 */     this.mProcessing = false;
/* 103 */     this.mReservedTicks = 0L;
/* 104 */     this.mCurrent = null;
/* 105 */     this.mDelayedTokens = null;
/*     */     
/* 107 */     this.mForceAllLoading = false;
/*     */     
/* 109 */     this.mAvailableProcessors = 1;
/* 110 */     this.mAvailableProcessorsTimeout = 0L;
/*     */ 
/*     */     
/* 113 */     this.mToSteal = 0L;
/* 114 */     this.mMaxToSteal = 15000000L;
/* 115 */     this.mMaxToStealLowSpec = 25000000L;
/* 116 */     this.mNearDistance = Double.valueOf(0.0D);
/* 117 */     this.mFarDistance = Double.valueOf(40.0D);
/* 118 */     this.mDistanceFactor = Double.valueOf(1.0D);
/*     */ 
/*     */     
/* 121 */     this.mThreadedWorkQueue = null;
/* 122 */     this.mThreadedExecutor = null;
/*     */     
/* 124 */     this.mCurrentThreadedQueue = null;
/*     */     
/* 126 */     this.mDataFeeder = null; }
/*     */ 
/*     */   
/*     */   public void start(AchaDoomsdayErrorHandler.AchaBugreportDataFeeder dataFeeder) {
/* 130 */     this.mDataFeeder = dataFeeder;
/*     */     
/* 132 */     updateAvailableProcessors(this.mTimer.getTime());
/*     */     
/* 134 */     String str = System.getProperty("LoadingManager.MaxToSteal");
/* 135 */     if (str != null && !str.isEmpty()) {
/* 136 */       this.mMaxToSteal = Long.parseLong(str);
/*     */     }
/*     */     
/* 139 */     str = System.getProperty("LoadingManager.MaxToStealLowSpec");
/* 140 */     if (str != null && !str.isEmpty()) {
/* 141 */       this.mMaxToStealLowSpec = Long.parseLong(str);
/*     */     }
/*     */     
/*     */     try {
/* 145 */       AchaDoomsdayErrorHandler errorHandler = new AchaDoomsdayErrorHandler(this.mDataFeeder);
/* 146 */       HardwareSpec thisSystem = HardwareInspector.instance().createForThisSystem(errorHandler);
/*     */       
/* 148 */       if (thisSystem.getCpuSpeed() < 1500) {
/* 149 */         this.mMaxToSteal = this.mMaxToStealLowSpec;
/*     */       }
/* 151 */     } catch (Exception e) {}
/*     */     
/* 153 */     str = System.getProperty("LoadingManager.NearDistance");
/* 154 */     if (str != null && !str.isEmpty()) {
/* 155 */       this.mNearDistance = Double.valueOf(Double.parseDouble(str));
/*     */     }
/* 157 */     str = System.getProperty("LoadingManager.FarDistance");
/* 158 */     if (str != null && !str.isEmpty()) {
/* 159 */       this.mFarDistance = Double.valueOf(Double.parseDouble(str));
/*     */     }
/* 161 */     str = System.getProperty("LoadingManager.DistanceFactor");
/* 162 */     if (str != null && !str.isEmpty()) {
/* 163 */       this.mDistanceFactor = Double.valueOf(Double.parseDouble(str));
/*     */     }
/*     */     
/* 166 */     this.mPriorityLoadingQueue = new PrioritizedLoadingTokenQueue<LoadingManagerToken>();
/* 167 */     this.mDistanceLoadingQueue = new DistanceLoadingTokenQueue<LoadingManagerToken>();
/* 168 */     this.mLoadingQueue = new ConcurrentLinkedQueue();
/* 169 */     this.mDelayedTokens = new ArrayList<LoadingManagerToken>();
/*     */     
/* 171 */     if (THREADED_SOUND) {
/* 172 */       this.mSoundThreadedQueue = new ThreadedLoadingTokenQueue();
/* 173 */       Thread thread = new Thread(this.mSoundThreadedQueue, "Loading Manager Sound");
/*     */       
/* 175 */       thread.setDaemon(true);
/* 176 */       thread.start();
/*     */     } 
/*     */     
/* 179 */     if (getAvailableProcessors() <= 1 || !MULTITHREADED) {
/*     */ 
/*     */ 
/*     */       
/* 183 */       this.mThreadedQueue = new ThreadedLoadingTokenQueue();
/* 184 */       Thread thread = new Thread(this.mThreadedQueue, "Loading Manager");
/*     */       
/* 186 */       thread.setDaemon(true);
/* 187 */       thread.start();
/*     */     } else {
/*     */       
/* 190 */       this.mThreadedWorkQueue = new LinkedBlockingQueue<Runnable>();
/* 191 */       this.mThreadedExecutor = new ThreadPoolExecutor(getAvailableProcessors() * 2, getAvailableProcessors() * 2, 0L, TimeUnit.SECONDS, this.mThreadedWorkQueue);
/* 192 */       this.mCurrentThreadedQueue = new LinkedList<LoadingManagerToken>();
/*     */     } 
/*     */     
/* 195 */     this.mCurrent = null;
/* 196 */     this.mProcessing = true;
/* 197 */     this.mToSteal = 0L;
/*     */   } static { CRASH_ON_EXCEPTION = !"false".equalsIgnoreCase(System.getProperty("LoadingManager.CRASH_ON_EXCEPTION")); USE = !"false".equalsIgnoreCase(System.getProperty("LoadingManager.USE")); MULTITHREADED = "true".equalsIgnoreCase(System.getProperty("LoadingManager.MULTITHREADED")); DEBUG_INFO = "true".equalsIgnoreCase(System.getProperty("LoadingManager.DEBUG_INFO")); ADAPTABLE_FPS = !"false".equalsIgnoreCase(System.getProperty("LoadingManager.ADAPTABLE_FPS")); LINEAR_STEAL = !"false".equalsIgnoreCase(System.getProperty("LoadingManager.LINEAR_STEAL")); DISTANCE_LOADING = !"false".equalsIgnoreCase(System.getProperty("LoadingManager.DISTANCE_LOADING")); THREADED_SOUND = !"false".equalsIgnoreCase(System.getProperty("LoadingManager.THREADED_SOUND")); USE_LIP = "true".equalsIgnoreCase(System.getProperty("LoadingManager.USE_LIP")); USE_DDS_FORMAT = !"false".equalsIgnoreCase(System.getProperty("LoadingManager.USE_DDS_FORMAT")); USE_MERGED_MESHES = "true".equalsIgnoreCase(System.getProperty("LoadingManager.USE_MERGED_MESHES")); if (USE_LIP)
/*     */       System.loadLibrary("LIPInterfaceDLL");  MAX_TIMELAG_SOUNDPLAYING = 1.0F; LOADING_TIMEOUT = Double.valueOf(10.0D);
/*     */     TILE_TEXTURE_ATLAS = "true".equalsIgnoreCase(System.getProperty("LoadingManager.USE_ATLASES"));
/* 201 */     AVAILABLE_PROCESSORS_TIME_STEP = Double.valueOf(4.0D); } public void destroy() { this.mProcessing = false;
/*     */     
/* 203 */     if (this.mThreadedQueue != null) {
/* 204 */       this.mThreadedQueue.stop();
/* 205 */       this.mThreadedQueue.clear();
/* 206 */       this.mThreadedQueue = null;
/*     */     } 
/*     */     
/* 209 */     if (this.mSoundThreadedQueue != null) {
/* 210 */       this.mSoundThreadedQueue.stop();
/* 211 */       this.mSoundThreadedQueue.clear();
/* 212 */       this.mSoundThreadedQueue = null;
/*     */     } 
/*     */     
/* 215 */     if (this.mLoadingQueue != null) {
/* 216 */       this.mLoadingQueue.clear();
/* 217 */       this.mLoadingQueue = null;
/*     */     } 
/*     */     
/* 220 */     if (this.mDelayedTokens != null) {
/* 221 */       this.mDelayedTokens.clear();
/* 222 */       this.mDelayedTokens = null;
/*     */     } 
/*     */ 
/*     */     
/* 226 */     if (this.mTokenProcessor != null) {
/* 227 */       this.mTokenProcessor.stop();
/* 228 */       this.mTokenProcessor = null;
/*     */     } 
/*     */     
/* 231 */     this.mCurrent = null; }
/*     */ 
/*     */   
/*     */   public void actuallyClearLoadingTokens() {
/* 235 */     if (this.mLoadingQueue != null) {
/* 236 */       this.mLoadingQueue.clear();
/*     */     }
/* 238 */     if (this.mPriorityLoadingQueue != null) {
/* 239 */       this.mPriorityLoadingQueue.clear();
/*     */     }
/* 241 */     clearLoadingTokens();
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
/*     */   public void clearLoadingTokens() {
/* 256 */     if (this.mDistanceLoadingQueue != null) {
/* 257 */       this.mDistanceLoadingQueue.clear();
/*     */     }
/* 259 */     if (this.mThreadedQueue != null) {
/* 260 */       this.mThreadedQueue.clear();
/*     */ 
/*     */       
/* 263 */       while (this.mThreadedQueue.isProcessing()) { 
/* 264 */         try { Thread.sleep(1L); } catch (Exception e) {} }
/*     */     
/*     */     } 
/*     */     
/* 268 */     if (this.mSoundThreadedQueue != null) {
/* 269 */       this.mSoundThreadedQueue.clear();
/*     */ 
/*     */       
/* 272 */       while (this.mSoundThreadedQueue.isProcessing()) { 
/* 273 */         try { Thread.sleep(1L); } catch (Exception e) {} }
/*     */     
/*     */     } 
/*     */     
/* 277 */     if (this.mDelayedTokens != null) {
/* 278 */       this.mDelayedTokens.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   public long getQueueSize() {
/* 283 */     long lSize = 0L;
/* 284 */     if (this.mLoadingQueue != null) {
/* 285 */       lSize += this.mLoadingQueue.size();
/*     */     }
/* 287 */     if (this.mPriorityLoadingQueue != null) {
/* 288 */       lSize += this.mPriorityLoadingQueue.size();
/*     */     }
/* 290 */     if (this.mDistanceLoadingQueue != null) {
/* 291 */       lSize += this.mDistanceLoadingQueue.size();
/*     */     }
/* 293 */     if (this.mThreadedQueue != null) {
/* 294 */       lSize += this.mThreadedQueue.size();
/*     */     }
/* 296 */     if (this.mSoundThreadedQueue != null) {
/* 297 */       lSize += this.mSoundThreadedQueue.size();
/*     */     }
/* 299 */     if (this.mDelayedTokens != null) {
/* 300 */       lSize += this.mDelayedTokens.size();
/*     */     }
/* 302 */     return lSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReservedTime(Double dSec) {
/* 309 */     if (dSec.doubleValue() >= 0.0D && this.mTimer != null) {
/* 310 */       Double d = Double.valueOf(this.mTimer.getResolution() * dSec.doubleValue());
/* 311 */       this.mReservedTicks = d.longValue();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void update(long nanosLeft, int camPosX, int camPosY) {
/*     */     try {
/* 317 */       this.mForceAllLoading = (nanosLeft < 0L);
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
/* 332 */       if (!this.mProcessing) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 337 */       long nStartTick = this.mTimer.getTime();
/* 338 */       long nCurrentTick = nStartTick;
/* 339 */       long nMaxTick = Math.max(nanosLeft, this.mReservedTicks) / 2L;
/* 340 */       long nEndTick = nCurrentTick + nMaxTick;
/* 341 */       nEndTick += this.mToSteal / 2L;
/*     */ 
/*     */ 
/*     */       
/* 345 */       updateAvailableProcessors(nCurrentTick);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 352 */       if (!this.mForceAllLoading && this.mTokenProcessor != null) {
/* 353 */         while (this.mProcessing && nCurrentTick < nEndTick && this.mTokenProcessor.runonce()) {
/* 354 */           nCurrentTick = this.mTimer.getTime();
/*     */         }
/*     */       }
/*     */       
/* 358 */       nEndTick += nMaxTick + this.mToSteal / 2L;
/*     */ 
/*     */ 
/*     */       
/*     */       do {
/* 363 */         if (this.mForceAllLoading && this.mTokenProcessor != null) {
/* 364 */           this.mTokenProcessor.runonce();
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 369 */         if (getCurrentToken(camPosX, camPosY)) {
/* 370 */           this.mCurrent = updateToken(this.mCurrent);
/*     */ 
/*     */         
/*     */         }
/* 374 */         else if (this.mDelayedTokens.size() > 0) {
/* 375 */           LoadingManagerToken token = this.mDelayedTokens.get(0);
/* 376 */           if (updateToken(token) == null) {
/* 377 */             this.mDelayedTokens.remove(token);
/* 378 */             token = null;
/*     */           }
/*     */         
/* 381 */         } else if (this.mCurrentThreadedQueue == null) {
/* 382 */           this.mCurrent = getNearestToken(camPosX, camPosY);
/* 383 */           if (this.mCurrent == null)
/* 384 */             break;  this.mCurrent = updateToken(this.mCurrent);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 389 */         if (this.mCurrentThreadedQueue != null) {
/* 390 */           LoadingManagerToken token = null;
/*     */           
/* 392 */           while (this.mCurrentThreadedQueue.size() < this.mThreadedExecutor.getMaximumPoolSize()) {
/* 393 */             token = getNearestToken(camPosX, camPosY);
/* 394 */             if (token == null)
/* 395 */               break;  this.mCurrentThreadedQueue.push(token);
/* 396 */             token = null;
/*     */           } 
/*     */ 
/*     */           
/* 400 */           int nNumTokens = this.mCurrentThreadedQueue.size();
/*     */           
/* 402 */           int nCurrentToken = 0;
/* 403 */           for (int n = 0; n < nNumTokens; n++) {
/* 404 */             token = this.mCurrentThreadedQueue.get(nCurrentToken);
/* 405 */             nCurrentToken++;
/* 406 */             if (updateToken(token) == null) {
/* 407 */               this.mCurrentThreadedQueue.remove(token);
/* 408 */               token = null;
/* 409 */               nCurrentToken--;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 414 */         nCurrentTick = this.mTimer.getTime();
/* 415 */       } while (this.mProcessing && !isEmpty() && (nCurrentTick < nEndTick || this.mForceAllLoading));
/*     */       
/* 417 */       if (DEBUG_INFO) {
/* 418 */         Double dElapsedSec = Double.valueOf(Double.longBitsToDouble(nCurrentTick - nStartTick) / Double.longBitsToDouble(this.mTimer.getResolution()));
/* 419 */         System.out.printf("LoadingManager - Elapsed: %s sec and stole %d ms.\n", new Object[] { dElapsedSec.toString(), Long.valueOf(this.mToSteal / 1000000L) });
/*     */       }
/*     */     
/* 422 */     } catch (Exception e) {
/*     */       
/* 424 */       sendCrash(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void sendCrash(Exception e) {
/* 431 */     if (CRASH_ON_EXCEPTION) {
/* 432 */       String locale = "en";
/* 433 */       String message = "";
/* 434 */       String sendButtonText = "";
/* 435 */       String noButtonText = "";
/* 436 */       if (System.getProperty("tcg.locale") != null) {
/* 437 */         locale = System.getProperty("tcg.locale");
/*     */       }
/* 439 */       if (locale.equals("en")) {
/* 440 */         message = "<html><i>CRASH</i><br><br><br>Help Dr Noah,<br>Send a Report!<html>";
/* 441 */         sendButtonText = "SEND!";
/* 442 */         noButtonText = "No Thanks";
/* 443 */       } else if (locale.equals("fr")) {
/* 444 */         message = "<html><i>CRASH</i><br><br><br>Aide le Dr Noah à<br>envoyer un rapport !<html>";
/* 445 */         sendButtonText = "ENVOI!";
/* 446 */         noButtonText = "Non Merci";
/* 447 */       } else if (locale.equals("no")) {
/* 448 */         message = "<html><i>CRASH</i><br><br><br>Hjelp Dr. Noah<br>Send en rapport!</html>";
/* 449 */         sendButtonText = "SEND!";
/* 450 */         noButtonText = "Ingen Takk";
/*     */       } 
/*     */       
/* 453 */       System.out.printf("LoadingManager Error: '%s'\n.", new Object[] { e.toString() });
/* 454 */       e.printStackTrace();
/*     */       
/* 456 */       AchaDoomsdayErrorHandler errorHandler = new AchaDoomsdayErrorHandler(this.mDataFeeder);
/* 457 */       CrashWindow crashDialog = new CrashWindow(message, sendButtonText, noButtonText, Thread.currentThread(), e, getBufferFromExceptionStacktrace(e).toString(), (DoomsdayErrorHandler)errorHandler);
/*     */ 
/*     */       
/* 460 */       crashDialog.setVisible(true);
/*     */       
/* 462 */       while (crashDialog.isEnabled()) {
/*     */         try {
/* 464 */           Thread.sleep(200L);
/*     */         }
/* 466 */         catch (Exception ee) {
/* 467 */           System.out.printf("Fatal Error while displaying a crash window: '%s'\n", new Object[] { ee.getMessage() });
/*     */           
/* 469 */           ee.printStackTrace();
/* 470 */           destroy();
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 475 */       System.out.printf("LoadingManager: %s.\n", new Object[] { e.toString() });
/* 476 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected StringBuffer getBufferFromExceptionStacktrace(Throwable e) {
/* 481 */     StringWriter stringWriter = null;
/*     */     try {
/* 483 */       stringWriter = new StringWriter(500);
/* 484 */       PrintWriter printWriter = new PrintWriter(stringWriter);
/* 485 */       e.printStackTrace(printWriter);
/* 486 */       printWriter.flush();
/* 487 */       stringWriter.close();
/* 488 */     } catch (IOException e1) {
/* 489 */       System.err.println("WARN: Failed to close DoomsdayErrorHandler StringWriter!");
/*     */     } 
/* 491 */     return stringWriter.getBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> submitSoundCallable(Callable<?> a) {
/* 496 */     if (this.mSoundThreadedQueue != null && THREADED_SOUND == true) {
/* 497 */       return this.mSoundThreadedQueue.submit(a);
/*     */     }
/*     */     
/*     */     try {
/* 501 */       if (a.getClass().toString().compareTo("com.funcom.gameengine.resourcemanager.loadingmanager.SoundUpdateCallable") == 0) {
/* 502 */         a = a;
/*     */       }
/* 504 */       a.call();
/*     */     }
/* 506 */     catch (Exception e) {
/* 507 */       sendCrash(e);
/*     */     } 
/*     */ 
/*     */     
/* 511 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean submit(LoadingManagerToken a) {
/* 516 */     if (this.mLoadingQueue != null) {
/* 517 */       this.mLoadingQueue.add(a);
/* 518 */       return true;
/*     */     } 
/* 520 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean submitByPriority(LoadingManagerToken a, PrioritizedLoadingTokenQueue.TokenPriority Priority) {
/* 528 */     if (this.mDelayedTokens != null && a.getClass().getName().compareTo("com.funcom.gameengine.resourcemanager.loadingmanager.ChunkLMToken") == 0) {
/* 529 */       this.mDelayedTokens.add(a);
/* 530 */       return true;
/*     */     } 
/*     */     
/* 533 */     if (this.mPriorityLoadingQueue != null) {
/* 534 */       this.mPriorityLoadingQueue.add(a, Priority);
/* 535 */       return true;
/*     */     } 
/*     */     
/* 538 */     return false;
/*     */   }
/*     */   
/*     */   public ChunkListener getDistanceQueue() {
/* 542 */     return this.mDistanceLoadingQueue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean submitByDistance(LoadingManagerToken a, int x, int y, Object node) {
/* 549 */     if (this.mDistanceLoadingQueue != null) {
/* 550 */       this.mDistanceLoadingQueue.add(a, x, y, node);
/* 551 */       return true;
/*     */     } 
/* 553 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> submitCallable(Callable<?> a) {
/* 558 */     if (this.mThreadedQueue != null) {
/* 559 */       return this.mThreadedQueue.submit(a);
/*     */     }
/* 561 */     if (this.mThreadedExecutor != null) {
/* 562 */       return this.mThreadedExecutor.submit(a);
/*     */     }
/* 564 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 569 */     return (this.mCurrent == null && (this.mPriorityLoadingQueue == null || this.mPriorityLoadingQueue.isEmpty()) && (this.mDistanceLoadingQueue == null || this.mDistanceLoadingQueue.isEmpty()) && (this.mDelayedTokens == null || this.mDelayedTokens.isEmpty()) && (this.mCurrentThreadedQueue == null || this.mCurrentThreadedQueue.isEmpty()) && (this.mLoadingQueue == null || this.mLoadingQueue.isEmpty()) && (this.mThreadedQueue == null || this.mThreadedQueue.isEmpty()) && (this.mSoundThreadedQueue == null || this.mSoundThreadedQueue.isEmpty()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean getCurrentToken(int camPosX, int camPosY) {
/* 580 */     if (this.mCurrent == null && 
/* 581 */       !getPriorityToken() && 
/* 582 */       !getToken()) {
/* 583 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 588 */     if (DEBUG_INFO && this.mCurrent != null) {
/* 589 */       System.out.printf("LoadingManager - Processing task '%s'.\n", new Object[] { this.mCurrent.getClass().getName() });
/*     */     }
/* 591 */     return true;
/*     */   }
/*     */   
/*     */   private boolean getToken() {
/* 595 */     if (this.mLoadingQueue != null) {
/* 596 */       this.mCurrent = (LoadingManagerToken) this.mLoadingQueue.peek();
/* 597 */       if (this.mCurrent != null) {
/* 598 */         this.mLoadingQueue.remove(this.mCurrent);
/*     */       }
/*     */     } 
/* 601 */     return (this.mCurrent != null);
/*     */   }
/*     */   
/*     */   private boolean getPriorityToken() {
/* 605 */     if (this.mPriorityLoadingQueue != null) {
/* 606 */       this.mCurrent = this.mPriorityLoadingQueue.getToken();
/*     */     }
/* 608 */     return (this.mCurrent != null);
/*     */   }
/*     */   
/*     */   private LoadingManagerToken getNearestToken(int x, int y) {
/* 612 */     LoadingManagerToken token = null;
/* 613 */     if (this.mDistanceLoadingQueue != null) {
/* 614 */       if (this.mForceAllLoading || !DISTANCE_LOADING) {
/* 615 */         token = this.mDistanceLoadingQueue.getToken();
/*     */       } else {
/*     */         
/* 618 */         token = this.mDistanceLoadingQueue.getNearestToken(x, y);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 623 */         if (ADAPTABLE_FPS) {
/* 624 */           if (token != null) {
/*     */ 
/*     */ 
/*     */             
/* 628 */             long nDistance = this.mDistanceLoadingQueue.getLastNearestDistance();
/* 629 */             Double dDistance = Double.valueOf((nDistance - this.mNearDistance.doubleValue()) / (this.mFarDistance.doubleValue() - this.mNearDistance.doubleValue()));
/* 630 */             if (dDistance.doubleValue() > 1.0D) { dDistance = Double.valueOf(1.0D); }
/* 631 */             else if (dDistance.doubleValue() < 0.0D) { dDistance = Double.valueOf(0.0D); }
/*     */             
/* 633 */             Double dToSteal = Double.valueOf(0.0D);
/* 634 */             if (LINEAR_STEAL) {
/* 635 */               dToSteal = Double.valueOf(1.0D - dDistance.doubleValue());
/*     */             }
/*     */             else {
/*     */               
/* 639 */               dToSteal = Double.valueOf(1.0D / Math.exp(dDistance.doubleValue() * this.mDistanceFactor.doubleValue()));
/*     */             } 
/* 641 */             this.mToSteal = (long)(dToSteal.doubleValue() * this.mMaxToSteal);
/*     */           } else {
/*     */             
/* 644 */             this.mToSteal = 0L;
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/* 649 */     return token;
/*     */   }
/*     */   
/*     */   public ThreadedLoadingTokenQueue getThreadedQueue() {
/* 653 */     return this.mThreadedQueue;
/*     */   }
/*     */   
/*     */   public int getAvailableProcessors() {
/* 657 */     return this.mAvailableProcessors;
/*     */   }
/*     */   
/*     */   private void updateAvailableProcessors(long nCurrentTick) {
/* 661 */     if (nCurrentTick >= this.mAvailableProcessorsTimeout) {
/* 662 */       Double d = Double.valueOf(this.mTimer.getResolution() * AVAILABLE_PROCESSORS_TIME_STEP.doubleValue());
/* 663 */       this.mAvailableProcessorsTimeout = nCurrentTick + d.longValue();
/* 664 */       this.mAvailableProcessors = Runtime.getRuntime().availableProcessors();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private LoadingManagerToken updateToken(LoadingManagerToken token) throws Exception {
/* 670 */     switch (token.update()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 680 */         if (getAvailableProcessors() <= 1) {
/* 681 */           Thread.yield();
/*     */         }
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/* 690 */         if (DEBUG_INFO) {
/* 691 */           System.out.printf("LoadingManager - Done loading task '%s' (GT%d/Pr%d/Lo%d/Di%d left).\n", new Object[] { token.getClass().getName(), Integer.valueOf(TokenRegister.instance().getOpenQueueSize()), Integer.valueOf(this.mPriorityLoadingQueue.size()), Integer.valueOf(this.mLoadingQueue.size()), Integer.valueOf(this.mDistanceLoadingQueue.size()) });
/*     */         }
/*     */         
/* 694 */         TimeStamper.INSTANCE.event("FPS", "LM_" + token.getClass().getName());
/* 695 */         token = null;
/*     */         break;
/*     */     } 
/* 698 */     return token;
/*     */   }
/*     */   
/*     */   public static void distanceLoading(boolean b) {
/* 702 */     DISTANCE_LOADING = b;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\LoadingManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */