/*    */ package com.funcom.tcg.utils;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.IOException;
/*    */ import java.io.Reader;
/*    */ 
/*    */ public class ConfigurationUtils {
/*  7 */   private static final String[] CONFIG_LOCATIONS = new String[] { System.getProperty("user.home"), "resources/dist/config", "../config" };
/*    */ 
/*    */   
/*    */   private static final String SEARCH_DIR = "resources/dist/config";
/*    */ 
/*    */   
/*    */   public static File findConfigFile(String file) throws ServerConfigurationException {
/* 14 */     File configFile = null;
/*    */     
/* 16 */     for (String configLocation : CONFIG_LOCATIONS) {
/* 17 */       File tmp = new File(configLocation, file);
/* 18 */       if (tmp.exists() && tmp.isFile()) {
/* 19 */         configFile = tmp;
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/*    */     
/* 25 */     if (configFile == null) {
/* 26 */       File currentDir = new File("");
/* 27 */       File[] subFiles = currentDir.listFiles();
/* 28 */       if (subFiles != null) {
/* 29 */         for (File subFile : subFiles) {
/* 30 */           if (subFile.isDirectory()) {
/* 31 */             File tmp = new File(subFile, "resources/dist/config");
/* 32 */             if (tmp.exists() && tmp.isFile()) {
/* 33 */               configFile = tmp;
/*    */               
/*    */               break;
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       }
/*    */     } 
/* 41 */     if (configFile == null) {
/* 42 */       throw new ServerConfigurationException("the server config is not found: working directory=" + System.getProperty("user.dir"));
/*    */     }
/*    */ 
/*    */     
/* 46 */     return configFile;
/*    */   }
/*    */   
/*    */   public static String getConfigFileContent(String path, String charset) throws ServerConfigurationException {
/* 50 */     Reader reader = null;
/*    */     try {
/* 52 */       reader = new InputStreamReader(new FileInputStream(findConfigFile(path)), charset);
/* 53 */       char[] buffer = new char[512];
/* 54 */       StringBuilder contentBuilder = new StringBuilder();
/*    */       int read;
/* 56 */       while ((read = reader.read(buffer)) != -1) {
/* 57 */         contentBuilder.append(buffer, 0, read);
/*    */       }
/*    */       
/* 60 */       return contentBuilder.toString();
/* 61 */     } catch (FileNotFoundException e) {
/* 62 */       throw new ServerConfigurationException(e);
/* 63 */     } catch (IOException e) {
/* 64 */       throw new ServerConfigurationException(e);
/*    */     } finally {
/*    */       try {
/* 67 */         if (reader != null)
/* 68 */           reader.close(); 
/* 69 */       } catch (IOException e) {
/* 70 */         throw new ServerConfigurationException(e);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tc\\utils\ConfigurationUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */