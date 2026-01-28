/*     */ package com.funcom.errorhandling;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.nio.CharBuffer;
import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Category;
/*     */ import org.apache.log4j.FileAppender;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.pleasantnightmare.noraxidium.util.CrashWindow;
/*     */ 
/*     */ public abstract class DoomsdayErrorHandler implements Thread.UncaughtExceptionHandler {
/*  15 */   private static final boolean SEND_ERROR = !"false".equalsIgnoreCase(System.getProperty("tcg.senderror"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  22 */   private List<CrashDataProvider> crashDataProviders = new LinkedList<CrashDataProvider>();
/*     */   protected String dxdiagData;
/*     */   
/*     */   public final void uncaughtException(Thread t, Throwable e) {
/*  26 */     e.printStackTrace();
/*     */     
/*  28 */     if (SEND_ERROR) {
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
/*  41 */       String locale = "en";
/*  42 */       String message = "";
/*  43 */       String sendButtonText = "";
/*  44 */       String noButtonText = "";
/*  45 */       if (System.getProperty("tcg.locale") != null) {
/*  46 */         locale = System.getProperty("tcg.locale");
/*     */       }
/*  48 */       if (locale.equals("en")) {
/*  49 */         message = "<html><i>CRASH</i><br><br><br>Help Dr Noah,<br>Send a Report!<html>";
/*  50 */         sendButtonText = "SEND!";
/*  51 */         noButtonText = "No Thanks";
/*  52 */       } else if (locale.equals("fr")) {
/*  53 */         message = "<html><i>CRASH</i><br><br><br>Aide le Dr Noah à<br>envoyer un rapport !<html>";
/*  54 */         sendButtonText = "ENVOI!";
/*  55 */         noButtonText = "Non Merci";
/*  56 */       } else if (locale.equals("no")) {
/*  57 */         message = "<html><i>CRASH</i><br><br><br>Hjelp Dr. Noah<br>Send en rapport!</html>";
/*  58 */         sendButtonText = "SEND!";
/*  59 */         noButtonText = "Ingen Takk";
/*     */       } 
/*  61 */       CrashWindow crashDialog = new CrashWindow(message, sendButtonText, noButtonText, t, e, getBufferFromExceptionStacktrace(e).toString(), this);
/*     */       
/*  63 */       crashDialog.setVisible(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void sendReports(Thread paramThread, Throwable paramThrowable);
/*     */ 
/*     */   
/*     */   public void addCrashDataProvider(CrashDataProvider crashDataProvider) {
/*  73 */     this.crashDataProviders.add(crashDataProvider);
/*     */   }
/*     */   
/*     */   public List<CrashDataProvider> getCrashDataProviders() {
/*  77 */     return this.crashDataProviders;
/*     */   }
/*     */   
/*     */   protected StringBuffer getBufferFromExceptionStacktrace(Throwable e) {
/*  81 */     StringWriter stringWriter = null;
/*     */     try {
/*  83 */       stringWriter = new StringWriter(500);
/*  84 */       PrintWriter printWriter = new PrintWriter(stringWriter);
/*  85 */       e.printStackTrace(printWriter);
/*  86 */       printWriter.flush();
/*  87 */       stringWriter.close();
/*  88 */     } catch (IOException e1) {
/*  89 */       System.err.println("WARN: Failed to close DoomsdayErrorHandler StringWriter!");
/*     */     } 
/*  91 */     return stringWriter.getBuffer();
/*     */   }
/*     */   
/*     */   public void setDxdiagData(String dxdiagData) {
/*  95 */     this.dxdiagData = dxdiagData;
/*     */   }
/*     */   
/*     */   public static String getLogs() {
/*  99 */     String logs = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 104 */       Logger logger = Logger.getLogger("root");
/* 105 */       Category parentLogger = logger.getParent();
/* 106 */       FileAppender appender = (FileAppender)parentLogger.getAppender("READABLE_FILE");
/* 107 */       File file = new File(appender.getFile());
/*     */       
/* 109 */       FileInputStream stream = new FileInputStream(file.getPath());
/* 110 */       InputStreamReader reader = new InputStreamReader(stream);
/*     */       
/* 112 */       CharBuffer buf = CharBuffer.allocate(stream.available());
/* 113 */       int numRead = reader.read(buf.array());
/* 114 */       if (numRead != 0) {
/* 115 */         logs = buf.toString();
/*     */       }
/*     */       
/* 118 */       reader.close();
/* 119 */       stream.close();
/*     */     }
/* 121 */     catch (Exception e) {
/*     */       
/* 123 */       System.out.printf("Error in DoomsdayErrorHandler::getLogs - %s.\n", new Object[] { e.toString() });
/* 124 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 127 */     return logs;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\errorhandling\DoomsdayErrorHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */