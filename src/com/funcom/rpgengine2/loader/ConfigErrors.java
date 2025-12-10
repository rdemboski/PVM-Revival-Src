/*     */ package com.funcom.rpgengine2.loader;
/*     */ 
/*     */ import com.funcom.commons.utils.ApplicationRelativePathUtil;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConfigErrors
/*     */ {
/*  24 */   private static final Logger LOG = Logger.getLogger(ConfigErrors.class.getName());
/*     */   
/*  26 */   private static final boolean SEND_MESSAGE = "true".equalsIgnoreCase(System.getProperty("tcg.debug.showrpgerrormesssage"));
/*     */   
/*  28 */   private final Map<String, AtomicInteger> errors = Collections.synchronizedMap(new HashMap<String, AtomicInteger>());
/*     */   private BufferedWriter logWriter;
/*  30 */   private AtomicInteger modCount = new AtomicInteger();
/*     */   
/*     */   private BufferedWriter getLog() {
/*  33 */     if (this.logWriter == null) {
/*  34 */       File logsDir = new File(ApplicationRelativePathUtil.getSystemDir());
/*  35 */       if (!logsDir.exists()) {
/*  36 */         logsDir.mkdirs();
/*     */       }
/*  38 */       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
/*  39 */       String logname = "rpg_errors_" + format.format(new Date()) + ".txt";
/*  40 */       File logFile = new File(logsDir, logname);
/*     */       try {
/*  42 */         this.logWriter = new BufferedWriter(new FileWriter(logFile));
/*  43 */       } catch (IOException e) {
/*  44 */         throw new RuntimeException("Cannot create rpg error log: " + logFile.getAbsolutePath());
/*     */       } 
/*     */     } 
/*     */     
/*  48 */     return this.logWriter;
/*     */   }
/*     */   
/*     */   public String getMessage() {
/*  52 */     String message = "";
/*     */     
/*  54 */     synchronized (this.errors) {
/*  55 */       for (Map.Entry<String, AtomicInteger> entry : this.errors.entrySet()) {
/*  56 */         message = message + (String)entry.getKey() + ": " + entry.getValue() + "\n";
/*     */       }
/*     */     } 
/*     */     
/*  60 */     return message.trim();
/*     */   }
/*     */   
/*     */   public void clear() {
/*  64 */     this.errors.clear();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  68 */     return this.errors.isEmpty();
/*     */   }
/*     */   
/*     */   public void addError(String typeId, String msg) {
/*     */     AtomicInteger count;
/*  73 */     synchronized (this.errors) {
/*  74 */       count = this.errors.get(typeId);
/*  75 */       if (count == null) {
/*  76 */         count = new AtomicInteger();
/*  77 */         this.errors.put(typeId, count);
/*     */       } 
/*     */     } 
/*  80 */     count.incrementAndGet();
/*     */     
/*  82 */     String msgLine = "*** " + typeId + ": " + msg;
/*  83 */     LOG.error(msgLine);
/*     */     
/*  85 */     log(msgLine);
/*     */     
/*  87 */     this.modCount.incrementAndGet();
/*     */   }
/*     */   
/*     */   private void log(String msgLine) {
/*  91 */     BufferedWriter out = getLog();
/*  92 */     if (out != null) {
/*     */       try {
/*  94 */         out.append(msgLine);
/*  95 */         out.newLine();
/*  96 */         out.flush();
/*  97 */       } catch (IOException e) {
/*  98 */         e.printStackTrace();
/*     */         
/* 100 */         try { out.close(); }
/* 101 */         catch (IOException ignore) {  }
/*     */         finally
/* 103 */         { this.logWriter = null; }
/*     */       
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public int getModCount() {
/* 110 */     return this.modCount.get();
/*     */   }
/*     */   
/*     */   public boolean sendMessage() {
/* 114 */     return SEND_MESSAGE;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loader\ConfigErrors.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */