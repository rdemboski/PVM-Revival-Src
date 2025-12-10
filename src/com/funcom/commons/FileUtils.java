/*     */ package com.funcom.commons;
/*     */ 
/*     */ import java.io.File;
/*     */ 
/*     */ public final class FileUtils {
/*     */   public static String filenameWithoutExtension(String filename) {
/*   7 */     int indexOfDot = filename.lastIndexOf(".");
/*   8 */     if (indexOfDot == -1)
/*   9 */       return ""; 
/*  10 */     return filename.substring(0, indexOfDot);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String fixTailingSlashes(String path) {
/*  20 */     if (path.endsWith("/") || path.endsWith("\\")) {
/*  21 */       return path;
/*     */     }
/*  23 */     return path.concat("/");
/*     */   }
/*     */   
/*     */   public static String trimTailingSlashes(String path) {
/*  27 */     if (path != null) {
/*  28 */       while (path.endsWith("/") || path.endsWith("\\")) {
/*  29 */         path = path.substring(0, path.length() - 1);
/*     */       }
/*     */     }
/*  32 */     return path;
/*     */   }
/*     */   
/*     */   public static String extension(String path) {
/*  36 */     int indexOfDot = path.lastIndexOf(".");
/*  37 */     if (indexOfDot == -1)
/*  38 */       return ""; 
/*  39 */     return path.substring(indexOfDot + 1);
/*     */   }
/*     */   
/*     */   public static String extension(File file) {
/*  43 */     return extension(file.getName());
/*     */   }
/*     */   
/*     */   public static String prefix(String path) {
/*  47 */     int indexOfDot = path.lastIndexOf(".");
/*  48 */     if (indexOfDot == -1)
/*  49 */       return ""; 
/*  50 */     return path.substring(0, indexOfDot);
/*     */   }
/*     */   
/*     */   public static String prefix(File file) {
/*  54 */     return prefix(file.getName());
/*     */   }
/*     */   
/*     */   public static String getParentPath(String path) {
/*  58 */     path = path.replace('\\', '/');
/*  59 */     int slashIndex = path.lastIndexOf('/');
/*  60 */     if (slashIndex >= 0) {
/*  61 */       return path.substring(0, slashIndex);
/*     */     }
/*     */     
/*  64 */     return "";
/*     */   }
/*     */   
/*     */   public static String makeAbsolute(String path) {
/*  68 */     if (isAbsoluteFixNeeded(path)) {
/*  69 */       char[] buf = new char[path.length()];
/*  70 */       int charIndex = 0;
/*  71 */       char prev = path.charAt(0);
/*  72 */       if (prev == '\\') {
/*  73 */         prev = '/';
/*     */       }
/*  75 */       for (int i = 1, len = path.length(); i < len; i++) {
/*  76 */         char current = path.charAt(i);
/*  77 */         if (current == '\\') {
/*  78 */           current = '/';
/*     */         }
/*     */         
/*  81 */         switch (prev) {
/*     */           case '.':
/*  83 */             if (current == '.') {
/*  84 */               prev = Character.MIN_VALUE;
/*  85 */               current = Character.MIN_VALUE;
/*     */               
/*  87 */               if (charIndex == 0) {
/*  88 */                 throw new IllegalArgumentException("too many parent parts '..'");
/*     */               }
/*  90 */               int newLen = charIndex - 2;
/*  91 */               while (newLen > 0 && buf[newLen] != '/' && buf[newLen] != '\\')
/*     */               {
/*     */                 
/*  94 */                 newLen--;
/*     */               }
/*  96 */               if (newLen >= 0) {
/*  97 */                 charIndex = newLen;
/*     */               }
/*     */             } 
/*     */             break;
/*     */           
/*     */           case '/':
/* 103 */             if (current == '/') {
/* 104 */               prev = Character.MIN_VALUE;
/* 105 */               current = Character.MIN_VALUE; break;
/* 106 */             }  if (charIndex == 0) {
/* 107 */               prev = Character.MIN_VALUE;
/*     */             }
/*     */             break;
/*     */         } 
/*     */         
/* 112 */         if (prev != '\000') {
/* 113 */           buf[charIndex++] = prev;
/*     */         }
/* 115 */         prev = current;
/*     */       } 
/* 117 */       if (prev != '\000') {
/* 118 */         buf[charIndex++] = prev;
/*     */       }
/*     */       
/* 121 */       return new String(buf, 0, charIndex);
/*     */     } 
/* 123 */     return path;
/*     */   }
/*     */   
/*     */   private static boolean isAbsoluteFixNeeded(String path) {
/* 127 */     int len = path.length();
/*     */     
/* 129 */     if (len > 1) {
/* 130 */       char prev = path.charAt(0);
/* 131 */       for (int i = 1; i < len; i++) {
/* 132 */         char current = path.charAt(i);
/*     */         
/* 134 */         if (prev == '\\' || (prev == '/' && current == '/') || (prev == '.' && current == '.'))
/*     */         {
/*     */           
/* 137 */           return true;
/*     */         }
/*     */         
/* 140 */         prev = current;
/*     */       } 
/*     */       
/* 143 */       return false;
/*     */     } 
/*     */     
/* 146 */     return false;
/*     */   }
/*     */   
/*     */   public static File getAssociatedFile(File file, FileType fileType) {
/* 150 */     String filePrefix = prefix(file);
/* 151 */     File directory = file.getParentFile();
/* 152 */     File[] siblings = directory.listFiles();
/* 153 */     for (File sibling : siblings) {
/* 154 */       if (FileType.isFileType(sibling, fileType)) {
/* 155 */         String siblingPrefix = prefix(sibling);
/* 156 */         if (filePrefix.equalsIgnoreCase(siblingPrefix)) {
/* 157 */           return sibling;
/*     */         }
/*     */       } 
/*     */     } 
/* 161 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\FileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */