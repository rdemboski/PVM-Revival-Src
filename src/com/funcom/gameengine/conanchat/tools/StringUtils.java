/*     */ package com.funcom.gameengine.conanchat.tools;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StringUtils
/*     */ {
/*  14 */   private static final Logger LOGGER = Logger.getLogger(StringUtils.class);
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
/*     */   public static String styleC2CamelHumps(String input) {
/*  28 */     StringReader sr = new StringReader(fixInvalidCharacters(input));
/*  29 */     StringBuilder sb = new StringBuilder();
/*     */     try {
/*     */       int character;
/*  32 */       while ((character = sr.read()) != -1) {
/*  33 */         if (character == 95) {
/*  34 */           character = sr.read();
/*  35 */           sb.append(Character.toUpperCase((char)character)); continue;
/*     */         } 
/*  37 */         sb.append((char)character);
/*     */       } 
/*  39 */     } catch (IOException e) {
/*  40 */       LOGGER.error("ERROR IN TRANSFORMING STRING, RETURNING INPUT!!");
/*  41 */       return input;
/*     */     } 
/*  43 */     return fixInvalidIdentifiers(sb.toString());
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
/*     */   public static String styleConstants2CamelHumps(String input) {
/*  55 */     StringReader sr = new StringReader(fixInvalidCharacters(input));
/*  56 */     StringBuilder sb = new StringBuilder();
/*     */     try {
/*     */       int character;
/*  59 */       while ((character = sr.read()) != -1) {
/*  60 */         if (character == 95) {
/*  61 */           character = sr.read();
/*  62 */           sb.append((char)character); continue;
/*     */         } 
/*  64 */         sb.append(Character.toLowerCase((char)character));
/*     */       } 
/*  66 */     } catch (IOException e) {
/*  67 */       LOGGER.error("ERROR IN TRANSFORMING STRING, RETURNING INPUT!!");
/*  68 */       return input;
/*     */     } 
/*  70 */     return fixInvalidIdentifiers(sb.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String capitalizeFirst(String input) {
/*  81 */     return Character.toString(Character.toUpperCase(input.charAt(0))) + input.substring(1, input.length());
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
/*     */   public static String fixInvalidCharacters(String input) {
/*  94 */     StringReader sr = new StringReader(input.trim());
/*  95 */     StringBuilder sb = new StringBuilder();
/*     */     
/*     */     try {
/*  98 */       int character = sr.read();
/*  99 */       if (!Character.isJavaIdentifierStart(character)) {
/* 100 */         LOGGER.error("Invalid character encountered at start: " + (char)character);
/* 101 */         character = sr.read();
/*     */       } 
/*     */ 
/*     */       
/*     */       do {
/* 106 */         if (!Character.isJavaIdentifierPart(character))
/* 107 */         { LOGGER.error("Invalid character encountered: " + (char)character);
/* 108 */           if (character == 32 || character == 45) {
/* 109 */             sb.append(Character.toUpperCase((char)sr.read()));
/*     */           } }
/*     */         else
/* 112 */         { sb.append((char)character); } 
/* 113 */       } while ((character = sr.read()) != -1);
/*     */     }
/* 115 */     catch (IOException e) {
/* 116 */       throw new IllegalStateException("ERROR IN FIXING nonallowed characters: " + input);
/*     */     } 
/* 118 */     return sb.toString();
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
/*     */   public static String fixInvalidIdentifiers(String input) {
/* 130 */     String trimmed = input.trim();
/* 131 */     if ("class".equals(trimmed)) {
/* 132 */       return "clazz";
/*     */     }
/* 134 */     return input;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 138 */     System.out.println("Stringutils styleC2CamelHumps visual test:");
/* 139 */     System.out.println("string_utils_rulez = " + styleC2CamelHumps("string_utils_rulez"));
/* 140 */     System.out.println("code_blocks_from_c = " + styleC2CamelHumps("code_blocks_from_c"));
/* 141 */     System.out.println("");
/* 142 */     System.out.println("Stringutils styleConstants2CamelHumps visual test:");
/* 143 */     System.out.println("SOME_CONSTANT_STRING = " + styleConstants2CamelHumps("SOME_CONSTANT_STRING"));
/* 144 */     System.out.println("ANOTHER_POWERFULLY_LONG_CONSTANT_STRING = " + styleConstants2CamelHumps("ANOTHER_POWERFULLY_LONG_CONSTANT_STRING"));
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\tools\StringUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */