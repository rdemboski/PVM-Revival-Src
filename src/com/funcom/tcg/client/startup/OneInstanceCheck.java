/*    */ package com.funcom.tcg.client.startup;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.RandomAccessFile;
/*    */ import java.nio.channels.FileChannel;
/*    */ import java.nio.channels.FileLock;
/*    */ import java.nio.channels.OverlappingFileLockException;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OneInstanceCheck
/*    */ {
/* 18 */   private static final Logger LOGGER = Logger.getLogger(OneInstanceCheck.class.getName());
/* 19 */   private boolean allowMultipleInstances = "true".equalsIgnoreCase(System.getProperty("tcg.debug.multipleInstances"));
/*    */   
/*    */   private File file;
/*    */   
/*    */   private FileChannel channel;
/*    */   
/*    */   private FileLock lock;
/*    */   private static final String LOCK_PATH = ".funcom/pvm/lock/PetsVsMonsters.tmp";
/*    */   
/*    */   public boolean isAppActive() {
/* 29 */     if (this.allowMultipleInstances) {
/* 30 */       return false;
/*    */     }
/*    */     try {
/* 33 */       if (System.getProperty("tcg.plugin.path") != null) {
/* 34 */         this.file = new File(System.getProperty("tcg.plugin.path"), ".funcom/pvm/lock/PetsVsMonsters.tmp");
/*    */       } else {
/* 36 */         this.file = new File(System.getProperty("user.home"), ".funcom/pvm/lock/PetsVsMonsters.tmp");
/* 37 */       }  if (!this.file.exists()) {
/* 38 */         File parentFile = this.file.getParentFile();
/* 39 */         if (parentFile != null) {
/* 40 */           parentFile.mkdirs();
/*    */         }
/* 42 */         this.file.createNewFile();
/*    */       } 
/* 44 */       this.channel = (new RandomAccessFile(this.file, "rw")).getChannel();
/*    */       
/*    */       try {
/* 47 */         this.lock = this.channel.tryLock();
/*    */       }
/* 49 */       catch (OverlappingFileLockException e) {
/*    */         
/* 51 */         return true;
/*    */       } 
/*    */       
/* 54 */       if (this.lock == null)
/*    */       {
/* 56 */         return true;
/*    */       }
/*    */       
/* 59 */       Runtime.getRuntime().addShutdownHook(new Thread()
/*    */           {
/*    */             public void run() {
/* 62 */               OneInstanceCheck.this.closeLock();
/* 63 */               OneInstanceCheck.this.deleteFile();
/*    */             }
/*    */           });
/* 66 */       return false;
/*    */     
/*    */     }
/* 69 */     catch (Exception e) {
/* 70 */       LOGGER.warn("Failure when checking if the game is already running");
/* 71 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   private void closeLock() {
/*    */     try {
/* 77 */       this.lock.release();
/*    */     }
/* 79 */     catch (Exception e) {
/* 80 */       LOGGER.warn("Failure when releasing the lock");
/*    */     } 
/*    */     try {
/* 83 */       this.channel.close();
/*    */     }
/* 85 */     catch (Exception e) {
/* 86 */       LOGGER.warn("Failure when closing channel");
/*    */     } 
/*    */   }
/*    */   
/*    */   private void deleteFile() {
/*    */     try {
/* 92 */       this.file.delete();
/*    */     }
/* 94 */     catch (Exception e) {
/* 95 */       LOGGER.warn("Failure when deleting PetsVsMonsters.tmp file");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\startup\OneInstanceCheck.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */