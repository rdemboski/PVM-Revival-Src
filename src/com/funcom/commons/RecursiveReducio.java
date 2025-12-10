/*     */ package com.funcom.commons;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RecursiveReducio
/*     */ {
/*     */   public static void main(String[] args) throws InterruptedException {
/*  15 */     long start = System.currentTimeMillis();
/*     */     
/*  17 */     if (args.length < 2) {
/*  18 */       printUsage();
/*  19 */       System.exit(1);
/*     */     } 
/*     */ 
/*     */     
/*  23 */     File file = new File(args[0]);
/*  24 */     if (!file.exists() || !file.isDirectory()) {
/*  25 */       printUsage();
/*  26 */       System.exit(1);
/*     */     } 
/*     */ 
/*     */     
/*  30 */     float quality = 0.0F;
/*     */     try {
/*  32 */       quality = Float.parseFloat(args[1]);
/*  33 */     } catch (NumberFormatException e) {
/*  34 */       printUsage();
/*  35 */       System.exit(1);
/*     */     } 
/*     */ 
/*     */     
/*  39 */     boolean deleteOriginal = false;
/*  40 */     if (args.length > 2) {
/*  41 */       deleteOriginal = "deleteoriginal".equals(args[2]);
/*     */     }
/*  43 */     ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
/*     */     
/*  45 */     depthFirst(file, quality, deleteOriginal, executorService);
/*     */     
/*  47 */     waitUntilFinished(executorService);
/*  48 */     long used = System.currentTimeMillis() - start;
/*  49 */     System.out.println("(used " + (used / 1000.0D) + ")");
/*     */   }
/*     */   
/*     */   private static void waitUntilFinished(ExecutorService executorService) throws InterruptedException {
/*  53 */     System.out.println("RecursiveReducio.waitUntilFinished");
/*  54 */     long start = System.currentTimeMillis();
/*  55 */     executorService.shutdown();
/*  56 */     executorService.awaitTermination(2147483647L, TimeUnit.DAYS);
/*  57 */     long waited = System.currentTimeMillis() - start;
/*  58 */     System.out.println("(waited " + (waited / 1000.0D) + ")");
/*     */   }
/*     */   
/*     */   private static void printUsage() {
/*  62 */     System.out.println("Usage params: <root-dir> <jpeg-quality>");
/*     */   }
/*     */   
/*     */   private static void depthFirst(File directory, float quality, boolean deleteOriginal, ExecutorService executorService) {
/*  66 */     File[] files = directory.listFiles();
/*  67 */     List<File> dirs = new ArrayList<File>();
/*  68 */     for (File file : files) {
/*  69 */       if (file.isDirectory()) {
/*  70 */         dirs.add(file);
/*  71 */       } else if ("png".equals(FileUtils.extension(file))) {
/*  72 */         executorService.execute(new ReducioJob(file, quality, deleteOriginal));
/*     */       } 
/*     */     } 
/*  75 */     for (File dir : dirs)
/*  76 */       depthFirst(dir, quality, deleteOriginal, executorService); 
/*     */   }
/*     */   
/*     */   private static class ReducioJob
/*     */     implements Runnable {
/*     */     private final File file;
/*     */     private final float quality;
/*     */     private final boolean deleteOriginal;
/*     */     
/*     */     public ReducioJob(File file, float quality, boolean deleteOriginal) {
/*  86 */       this.file = file;
/*  87 */       this.quality = quality;
/*  88 */       this.deleteOriginal = deleteOriginal;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/*  93 */       System.out.println("Processing: " + this.file.getAbsolutePath());
/*  94 */       Reducio.main(new String[] { this.file.getAbsolutePath(), String.valueOf(this.quality) });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  99 */       if (this.deleteOriginal && !this.file.delete())
/* 100 */         System.err.println("WARNING: Failed to delete file: " + this.file); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\RecursiveReducio.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */