/*     */ package com.funcom.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class URLDesktopIconBuilder
/*     */ {
/*     */   private static final String REGQUERY = "reg query ";
/*     */   private static final String REGSTR_TOKEN = "REG_SZ";
/*     */   private static final String CMD_DESKTOP_FIND = "reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\" /v DESKTOP";
/*     */   private static final String VM_PARAM_LINK_NAME = "linkName";
/*     */   private static final String VM_PARAM_URL_HTTP_HOMEPAGE = "urlHttpHomepage";
/*     */   private static final String INTERNET_SHORTCUT_TAG = "[InternetShortcut]";
/*     */   private static final String URL_TAG = "URL=";
/*     */   
/*     */   public void makeDesktopIcon() {
/*  26 */     if (validOs()) {
/*     */       try {
/*  28 */         String desktopPath = null;
/*     */         
/*  30 */         Process process = Runtime.getRuntime().exec("reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\" /v DESKTOP");
/*  31 */         OutputReader reader = new OutputReader(process.getInputStream());
/*     */         
/*  33 */         reader.start();
/*  34 */         process.waitFor();
/*  35 */         reader.join();
/*  36 */         String result = reader.getResult();
/*     */         
/*  38 */         int res = result.indexOf("REG_SZ");
/*  39 */         if (res >= 0) {
/*  40 */           desktopPath = result.substring(res + "REG_SZ".length()).trim();
/*     */         }
/*     */         
/*  43 */         makeFileOnDesktop(desktopPath);
/*     */       }
/*  45 */       catch (IOException e) {
/*  46 */         e.printStackTrace();
/*  47 */       } catch (InterruptedException e) {
/*  48 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean validOs() {
/*  55 */     String osName = System.getProperty("os.name").toLowerCase();
/*  56 */     return (osName.indexOf("win") >= 0);
/*     */   }
/*     */   
/*     */   private void makeFileOnDesktop(String desktopPath) throws IOException {
/*  60 */     String linkName = System.getProperty("linkName");
/*  61 */     String urlHttpHomepage = System.getProperty("urlHttpHomepage");
/*     */     
/*  63 */     if (desktopPath != null && linkName != null && urlHttpHomepage != null) {
/*  64 */       File file = new File(desktopPath + File.separator + linkName);
/*  65 */       if (!file.exists()) {
/*  66 */         FileOutputStream fileOutputStream = new FileOutputStream(file);
/*     */         
/*  68 */         PrintStream printStream = new PrintStream(fileOutputStream);
/*  69 */         printStream.println("[InternetShortcut]");
/*  70 */         printStream.println("URL=" + urlHttpHomepage);
/*  71 */         printStream.close();
/*  72 */         fileOutputStream.close();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private class OutputReader extends Thread {
/*     */     private InputStream inputStream;
/*     */     private StringBuffer stringBuffer;
/*     */     
/*     */     public OutputReader(InputStream inputStream) {
/*  82 */       this.inputStream = inputStream;
/*  83 */       this.stringBuffer = new StringBuffer();
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       try {
/*     */         int b;
/*  90 */         while ((b = this.inputStream.read()) != -1) {
/*  91 */           this.stringBuffer.append((char)b);
/*     */         }
/*  93 */       } catch (IOException e) {
/*  94 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*     */     public String getResult() {
/*  99 */       return this.stringBuffer.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 107 */     URLDesktopIconBuilder urlDesktopIconBuilder = new URLDesktopIconBuilder();
/* 108 */     urlDesktopIconBuilder.makeDesktopIcon();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funco\\util\URLDesktopIconBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */