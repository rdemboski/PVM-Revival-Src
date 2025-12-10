/*     */ package com.funcom.commons;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.math.BigInteger;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class StringUtils
/*     */ {
/*     */   public static int parseInt(String s, int defaultValue) {
/*     */     try {
/*  24 */       return Integer.parseInt(s);
/*     */     }
/*  26 */     catch (NumberFormatException ex) {
/*     */ 
/*     */ 
/*     */       
/*  30 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float parseFloat(String s, float defaultValue) {
/*  42 */     if (s == null) {
/*  43 */       return defaultValue;
/*     */     }
/*     */     try {
/*  46 */       return Float.parseFloat(s);
/*     */     }
/*  48 */     catch (NumberFormatException ex) {
/*     */ 
/*     */ 
/*     */       
/*  52 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double parseDouble(String s, double defaultValue) {
/*  64 */     if (s == null) {
/*  65 */       return defaultValue;
/*     */     }
/*     */     try {
/*  68 */       return Double.parseDouble(s);
/*     */     }
/*  70 */     catch (NumberFormatException ex) {
/*     */ 
/*     */ 
/*     */       
/*  74 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String formatNumber(double number, int digits, NumberFormat format) {
/*  85 */     format.setMinimumFractionDigits(digits);
/*  86 */     format.setMaximumFractionDigits(digits);
/*  87 */     return format.format(number);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String repeatString(String original, int count) {
/*  98 */     String result = "";
/*  99 */     for (int i = 0; i < count; i++) {
/* 100 */       result = result + original;
/*     */     }
/* 102 */     return result;
/*     */   }
/*     */   public static String simplePatternToRegexp(String simpleWildcardStr) {
/*     */     String patternRegexp;
/* 106 */     simpleWildcardStr = simpleWildcardStr.trim();
/*     */     
/* 108 */     if ("".equals(simpleWildcardStr)) {
/* 109 */       patternRegexp = "";
/*     */     } else {
/* 111 */       StringTokenizer tok = new StringTokenizer(simpleWildcardStr, "*", true);
/* 112 */       patternRegexp = "";
/* 113 */       while (tok.hasMoreTokens()) {
/* 114 */         String token = tok.nextToken();
/* 115 */         if ("*".equals(token)) {
/* 116 */           patternRegexp = patternRegexp + ".*"; continue;
/*     */         } 
/* 118 */         patternRegexp = patternRegexp + Pattern.quote(token);
/*     */       } 
/*     */     } 
/*     */     
/* 122 */     return "^" + patternRegexp + "$";
/*     */   }
/*     */   
/*     */   public static String hashString(String string) {
/*     */     try {
/* 127 */       MessageDigest m = MessageDigest.getInstance("MD5");
/* 128 */       byte[] input = string.getBytes("UTF-8");
/* 129 */       m.update(input, 0, input.length);
/* 130 */       BigInteger i = new BigInteger(1, m.digest());
/* 131 */       return String.format("%1$032X", new Object[] { i });
/* 132 */     } catch (NoSuchAlgorithmException e) {
/* 133 */       throw new RuntimeException("should not happen", e);
/* 134 */     } catch (UnsupportedEncodingException e) {
/* 135 */       throw new RuntimeException("should not happen", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean equals(String str1, String str2) {
/* 140 */     if (str1 != null && str2 != null) {
/* 141 */       return str1.equals(str2);
/*     */     }
/* 143 */     return (str1 == null && str2 == null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isEmailFormat(String parentsEmailStr) {
/* 148 */     return parentsEmailStr.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
/*     */   }
/*     */   
/*     */   public static boolean isEmailChar(char c) {
/* 152 */     return (Character.isLetter(c) || Character.isDigit(c) || ".@!#$%&'*+/=?^_`{|}~-".indexOf(c) >= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isCharacterNameFormat(String nameToCheck) {
/* 158 */     if (nameToCheck.isEmpty()) {
/* 159 */       return false;
/*     */     }
/*     */     
/* 162 */     boolean numericStarted = false;
/* 163 */     for (int i = 0; i < nameToCheck.length(); i++) {
/* 164 */       if (!isCharacterNameChar(nameToCheck.charAt(i)))
/* 165 */         return false; 
/* 166 */       if (!numericStarted && isCharacterNameNumeric(nameToCheck.charAt(i))) {
/* 167 */         if (i < 4)
/* 168 */           return false; 
/* 169 */         numericStarted = true;
/* 170 */       } else if (numericStarted && !isCharacterNameNumeric(nameToCheck.charAt(i))) {
/* 171 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 175 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isCharacterNameChar(char c) {
/* 180 */     return ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9'));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isCharacter(char c) {
/* 185 */     return ((c >= ' ' && c <= '') || (c >= 'À' && c <= 'ÿ'));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isCharacterNameNumeric(char c) {
/* 190 */     return (c >= '0' && c <= '9');
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\StringUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */