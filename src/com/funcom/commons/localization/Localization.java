/*     */ package com.funcom.commons.localization;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ public class Localization
/*     */ {
/*     */   private static Localization defaultLocalization;
/*     */   private boolean exceptionOnMissingElements;
/*     */   
/*     */   public static Localization getDefaultLocalization() {
/*  14 */     return defaultLocalization;
/*     */   }
/*     */   
/*     */   public static void setDefaultLocalization(Localization defaultLocalization) {
/*  18 */     Localization.defaultLocalization = defaultLocalization;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isExceptionOnMissingElements() {
/*  24 */     return this.exceptionOnMissingElements;
/*     */   }
/*     */   
/*     */   public void setExceptionOnMissingElements(boolean exceptionOnMissingElements) {
/*  28 */     this.exceptionOnMissingElements = exceptionOnMissingElements;
/*     */   }
/*     */   
/*     */   public String formatString(String string, Map<String, Object> data) throws LocalizationException {
/*  32 */     return formatString(string, data, null);
/*     */   }
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
/*     */   
/*     */   public String formatString(String string, Map<String, Object> data, Set<String> missingElements) throws LocalizationException {
/*  48 */     if (missingElements != null) {
/*  49 */       missingElements.clear();
/*     */     }
/*     */     
/*  52 */     if (string == null || string.length() == 0) {
/*  53 */       return string;
/*     */     }
/*     */     
/*  56 */     if (string.indexOf('[') == -1 && string.indexOf('<') == -1)
/*     */     {
/*  58 */       return string;
/*     */     }
/*     */     
/*  61 */     String line = string;
/*  62 */     StringBuilder result = new StringBuilder();
/*     */ 
/*     */     
/*  65 */     if (data != null) {
/*  66 */       while (line.indexOf('[') != -1) {
/*     */         String index;
/*  68 */         int start = line.indexOf('[');
/*  69 */         char beforeStart = ' ';
/*     */         
/*  71 */         if (start > 0) {
/*  72 */           beforeStart = line.charAt(start - 1);
/*     */         }
/*     */         
/*  75 */         result.append(line.substring(0, start));
/*  76 */         line = line.substring(start + 1);
/*     */ 
/*     */         
/*  79 */         int end = line.indexOf(']');
/*     */ 
/*     */         
/*  82 */         if (end == -1) {
/*  83 */           index = line.substring(0);
/*     */         } else {
/*  85 */           index = line.substring(0, end);
/*     */         } 
/*     */ 
/*     */         
/*  89 */         if (end != -1) {
/*  90 */           line = line.substring(end + 1);
/*     */         }
/*     */ 
/*     */         
/*  94 */         if (beforeStart == '\\') {
/*  95 */           result.setCharAt(result.length() - 1, '[');
/*  96 */           result.append(index);
/*  97 */           result.append(']');
/*     */           continue;
/*     */         } 
/* 100 */         Object value = data.get(index);
/*     */ 
/*     */         
/* 103 */         if (value != null) {
/* 104 */           String arg = value.toString();
/* 105 */           if (arg.isEmpty())
/* 106 */             arg = "0"; 
/* 107 */           result.append(arg); continue;
/* 108 */         }  if (missingElements != null) {
/* 109 */           missingElements.add("[" + index + "]"); continue;
/* 110 */         }  if (this.exceptionOnMissingElements) {
/* 111 */           throw new LocalizationException("Missing variable: name=" + index);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 116 */       result.append(line);
/* 117 */       string = result.toString();
/*     */     } 
/*     */ 
/*     */     
/* 121 */     line = string;
/* 122 */     result = new StringBuilder();
/*     */     
/* 124 */     while (line.indexOf('<') != -1) {
/*     */       
/* 126 */       int start = line.indexOf('<');
/* 127 */       char beforeStart = ' ';
/*     */       
/* 129 */       if (start > 0) {
/* 130 */         beforeStart = line.charAt(start - 1);
/*     */       }
/*     */       
/* 133 */       result.append(line.substring(0, start));
/*     */       
/* 135 */       line = line.substring(start + 1);
/*     */ 
/*     */       
/* 138 */       int end = line.indexOf('>');
String index;
/*     */ 
/*     */       
/* 141 */       if (end == -1) {
/* 142 */         index = line.substring(0);
/*     */       } else {
/* 144 */         index = line.substring(0, end);
/*     */       } 
/*     */ 
/*     */       
/* 148 */       if (end != -1) {
/* 149 */         line = line.substring(end + 1);
/*     */       }
/*     */ 
/*     */       
/* 153 */       if (beforeStart == '\\') {
/* 154 */         result.setCharAt(result.length() - 1, '<');
/* 155 */         result.append(index);
/* 156 */         result.append('>');
/*     */         continue;
/*     */       } 
/* 159 */       index = formatString(index, data, missingElements);
/*     */       
/* 161 */       if (index != null) {
/* 162 */         result.append(index); continue;
/* 163 */       }  if (missingElements != null) {
/* 164 */         missingElements.add("<" + index + ">"); continue;
/* 165 */       }  if (this.exceptionOnMissingElements) {
/* 166 */         throw new LocalizationException("Missing variable: name=" + index);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 171 */     if (this.exceptionOnMissingElements && missingElements != null && missingElements.size() > 0)
/*     */     {
/*     */       
/* 174 */       throw new LocalizationException("Missing variable(s): name(s)=" + Arrays.toString(missingElements.toArray()));
/*     */     }
/*     */     
/* 177 */     result.append(line);
/* 178 */     string = result.toString();
/*     */     
/* 180 */     return string;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\localization\Localization.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */