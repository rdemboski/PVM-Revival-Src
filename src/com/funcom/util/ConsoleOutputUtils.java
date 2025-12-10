/*    */ package com.funcom.util;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConsoleOutputUtils
/*    */ {
/*    */   private static final String CONSOLEFILE = "consolefile";
/*    */   private static boolean outputFileInstalled;
/*    */   
/*    */   public static synchronized void setupFileOutput() {
/* 21 */     String consoleOutFilename = System.getProperty("consolefile");
/*    */     
/* 23 */     if (!outputFileInstalled && consoleOutFilename != null && !consoleOutFilename.isEmpty()) {
/*    */       
/* 25 */       File consoleOutFile = new File(consoleOutFilename);
/*    */       
/*    */       try {
/* 28 */         File parent = consoleOutFile.getParentFile();
/* 29 */         if (parent != null) {
/* 30 */           parent.mkdirs();
/*    */         }
/* 32 */         PrintStream file = new PrintStream(new FileOutputStream(consoleOutFile, true), true);
/*    */         
/* 34 */         DuplicatedPrintStream duplicatedStd = new DuplicatedPrintStream(file, System.out);
/* 35 */         DuplicatedPrintStream duplicatedErr = new DuplicatedPrintStream(file, System.err);
/*    */         
/* 37 */         System.setOut(duplicatedStd);
/* 38 */         System.setErr(duplicatedErr);
/*    */         
/* 40 */         System.err.println("------------------------------------------------------------------------\n---\n--- Console output to file: " + consoleOutFile.getAbsolutePath() + "\n" + "---\n" + "------------------------------------------------------------------------");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 46 */         outputFileInstalled = true;
/* 47 */       } catch (FileNotFoundException e) {
/* 48 */         System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n!!!\n!!! Error setting up console output to file: " + consoleOutFile.getAbsolutePath() + "\n" + "!!!\n" + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 53 */         e.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funco\\util\ConsoleOutputUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */