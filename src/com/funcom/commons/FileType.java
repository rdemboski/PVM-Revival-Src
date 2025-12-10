/*    */ package com.funcom.commons;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.HashSet;
/*    */ import java.util.Properties;
/*    */ import java.util.Set;
/*    */ 
/*    */ public enum FileType
/*    */ {
/* 12 */   TEXT,
/* 13 */   IMAGE,
/* 14 */   XML,
/* 15 */   SOUND,
/* 16 */   JME;
/*    */   
/*    */   private Set<String> extensions;
/*    */   
/*    */   FileType() {
/* 21 */     this.extensions = new HashSet<String>();
/*    */   }
/*    */ 
/*    */   
/*    */   static {
/* 26 */     configure();
/*    */   }
/*    */   
/*    */   private static void configure() {
/* 30 */     InputStream configFile = null;
/*    */     try {
/* 32 */       configFile = FileType.class.getClassLoader().getResourceAsStream("com/funcom/commons/filetype.config.props");
/* 33 */       Properties p = new Properties();
/* 34 */       p.load(configFile);
/* 35 */       for (String key : p.stringPropertyNames()) {
/* 36 */         FileType ft = getFileTypeByName(key);
/* 37 */         Set<String> extensions = getExtensions(p, key);
/* 38 */         ft.extensions.addAll(extensions);
/*    */       } 
/* 40 */     } catch (IOException e) {
/* 41 */       throw new IllegalStateException(e);
/*    */     } finally {
/*    */       try {
/* 44 */         if (configFile != null)
/* 45 */           configFile.close(); 
/* 46 */       } catch (IOException e) {
/* 47 */         System.err.println("class FileType, line 44: FAILED TO CLOSE STREAM!");
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private static Set<String> getExtensions(Properties p, String key) {
/* 53 */     String value = p.getProperty(key);
/* 54 */     String[] exts = value.split(",");
/* 55 */     Set<String> returnable = new HashSet<String>();
/* 56 */     for (String ext : exts)
/* 57 */       returnable.add(ext.trim()); 
/* 58 */     return returnable;
/*    */   }
/*    */   
/*    */   public static FileType getFileTypeByName(String key) {
/* 62 */     FileType[] constants = FileType.class.getEnumConstants();
/* 63 */     for (FileType constant : constants) {
/* 64 */       if (constant.name().equals(key))
/* 65 */         return constant; 
/* 66 */     }  throw new IllegalStateException("Properties-defined constant not found: " + key);
/*    */   }
/*    */   
/*    */   public static FileType getFileType(String path) {
/* 70 */     String ext = FileUtils.extension(path).trim().toLowerCase();
/*    */     
/* 72 */     for (FileType fileType : values()) {
/* 73 */       if (fileType.extensions.contains(ext))
/* 74 */         return fileType; 
/*    */     } 
/* 76 */     return null;
/*    */   }
/*    */   
/*    */   public static FileType getFileType(File file) {
/* 80 */     return getFileType(file.getName());
/*    */   }
/*    */   
/*    */   public static boolean isFileType(File file, FileType fileType) {
/* 84 */     FileType actualFileType = getFileType(file);
/* 85 */     return (actualFileType != null && actualFileType.equals(fileType));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\FileType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */