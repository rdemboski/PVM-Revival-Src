/*     */ package com.funcom.gameengine.resourcemanager;
/*     */ 
/*     */ import com.jme.system.DisplaySystem;
/*     */ //import com.sun.corba.se.impl.orbutil.concurrent.Mutex;
/*     */ import org.lwjgl.LWJGLException;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.Drawable;
/*     */ import org.lwjgl.opengl.Pbuffer;
/*     */ import org.lwjgl.opengl.PixelFormat;
/*     */ import org.lwjgl.opengl.SharedDrawable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DrawableContextManager
/*     */ {
/*  21 */   public static boolean THREADED_OPENGL = "true".equalsIgnoreCase(System.getProperty("DrawableContextManager.THREADED_OPENGL"));
/*  22 */   public static boolean USE_MUTEX = "true".equalsIgnoreCase(System.getProperty("DrawableContextManager.USE_MUTEX"));
/*  23 */   public static boolean SHARED_CONTEXT = !"false".equalsIgnoreCase(System.getProperty("DrawableContextManager.SHARED_CONTEXT"));
/*  24 */   public static boolean PBUFFER_CONTEXT = !"false".equalsIgnoreCase(System.getProperty("DrawableContextManager.PBUFFER_CONTEXT"));
/*     */   
/*  26 */   private static Drawable threadContext = null;
/*  27 */   private static Drawable mainContext = null;
/*  28 */   private static boolean switchingEnabled = THREADED_OPENGL;
/*     */   
/*  30 */   //static Mutex mutex = new Mutex();
/*     */   
/*     */   public static boolean create() {
/*  33 */     if (!THREADED_OPENGL) {
/*  34 */       return false;
/*     */     }
/*  36 */     mainContext = Display.getDrawable();
/*     */     
/*  38 */     if (threadContext == null && SHARED_CONTEXT) {
/*     */       
/*     */       try {
/*  41 */         threadContext = (Drawable)new SharedDrawable(Display.getDrawable());
/*  42 */         System.out.printf("DrawableContextManager.create - SharedDrawable.\n", new Object[0]);
/*     */       }
/*  44 */       catch (LWJGLException e) {
/*     */         
/*  46 */         System.out.printf("Warning - drawable context not supported.\n", new Object[0]);
/*  47 */         e.printStackTrace();
/*  48 */         threadContext = null;
/*     */       } 
/*     */     }
/*     */     
/*  52 */     if (threadContext == null && PBUFFER_CONTEXT) {
/*  53 */       DisplaySystem d = DisplaySystem.getDisplaySystem();
/*     */ 
/*     */       
/*     */       try {
/*  57 */         threadContext = (Drawable)new Pbuffer(d.getWidth(), d.getHeight(), new PixelFormat(), Display.getDrawable());
/*     */         
/*  59 */         System.out.printf("DrawableContextManager.create - Pbuffer.\n", new Object[0]);
/*     */       }
/*  61 */       catch (LWJGLException ee) {
/*  62 */         System.out.printf("Warning - pbuffer not supported (%d - %d - %d - %d - %d).\n", new Object[] { Integer.valueOf(d.getWidth()), Integer.valueOf(d.getHeight()), Integer.valueOf(d.getMinAlphaBits()), Integer.valueOf(d.getBitDepth()), Integer.valueOf(d.getMinStencilBits()) });
/*     */         
/*  64 */         ee.printStackTrace();
/*  65 */         threadContext = null;
/*  66 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/*  70 */     return true;
/*     */   }
/*     */   
/*     */   public static void destroy() {
/*  74 */     if (mainContext != null)
/*     */     {
/*  76 */       mainContext = null;
/*     */     }
/*     */     
/*  79 */     if (threadContext != null) {
/*     */       try {
/*  81 */         threadContext.releaseContext();
/*  82 */         threadContext.destroy();
/*  83 */         threadContext = null;
/*     */       }
/*  85 */       catch (LWJGLException e) {
/*  86 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static void enable() {
/*  92 */     if (threadContext != null)
/*  93 */       switchingEnabled = true; 
/*     */   }
/*     */   
/*     */   public static void disable() {
/*  97 */     if (threadContext != null)
/*  98 */       switchingEnabled = false; 
/*     */   }
/*     */   
/*     */   public static boolean isSwitchingEnabled() {
/* 102 */     return switchingEnabled;
/*     */   }
/*     */   
/*     */   public static boolean set(long timeout) {
/* 106 */     if (!switchingEnabled) {
/* 107 */       return true;
/*     */     }
/* 109 */     if (!lock(timeout)) {
/* 110 */       return false;
/*     */     }
/*     */     try {
/* 113 */       if (Thread.currentThread().getName().compareTo("main") == 0) {
/* 114 */         if (mainContext != null && !mainContext.isCurrent()) {
/* 115 */           mainContext.makeCurrent();
/*     */         }
/*     */       }
/* 118 */       else if (threadContext != null && !mainContext.isCurrent()) {
/* 119 */         threadContext.makeCurrent();
/*     */       } 
/* 121 */     } catch (LWJGLException e) {
/* 122 */       System.out.printf("Error - drawable context couldn't be made current.\n", new Object[0]);
/* 123 */       e.printStackTrace();
/* 124 */       return false;
/*     */     } 
/* 126 */     return true;
/*     */   }
/*     */   
/*     */   public static void unset() {
/* 130 */     if (!switchingEnabled)
/*     */       return; 
/*     */     try {
/* 133 */       if (Thread.currentThread().getName().compareTo("main") == 0) {
/* 134 */         if (mainContext != null && mainContext.isCurrent()) {
/* 135 */           mainContext.releaseContext();
/*     */         }
/*     */       }
/* 138 */       else if (threadContext != null && threadContext.isCurrent()) {
/* 139 */         threadContext.releaseContext();
/*     */       }
/*     */     
/* 142 */     } catch (LWJGLException e) {
/* 143 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 146 */     unlock();
/*     */   }
/*     */   
/*     */   public static synchronized boolean lock(long timeout) {
/* 150 */     if (!THREADED_OPENGL) {
/* 151 */       return true;
/*     */     }
/* 153 */     if (USE_MUTEX) {
/*     */       //try {
/* 155 */         if (timeout == -1L) {
/* 156 */           //mutex.acquire();
/*     */         } else {
/* 158 */           //mutex.attempt(timeout);
/*     */         }
// /* 160 */       } catch (InterruptedException e) {
// /* 161 */         return false;
// /*     */       } 
/*     */     }
/* 164 */     return true;
/*     */   }
/*     */   
/*     */   public static void unlock() {
/* 168 */     if (!THREADED_OPENGL) {
/*     */       return;
/*     */     }
/* 171 */     //if (USE_MUTEX)
/* 172 */       //mutex.release(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\DrawableContextManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */