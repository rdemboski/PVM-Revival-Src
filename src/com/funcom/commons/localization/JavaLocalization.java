/*    */ package com.funcom.commons.localization;
/*    */ 
/*    */ import java.util.MissingResourceException;
/*    */ import java.util.ResourceBundle;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class JavaLocalization {
/*  8 */   private static final Logger LOG = Logger.getLogger(JavaLocalization.class.getName());
/*    */   
/*    */   private static JavaLocalization instance;
/*    */   
/*    */   private JavaLocalization(ResourceBundle bundle) {
/* 13 */     this.bundle = bundle;
/*    */   }
/*    */   private boolean male = true; private ResourceBundle bundle;
/*    */   public static void init(ResourceBundle bundle) {
/* 17 */     instance = new JavaLocalization(bundle);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getLocalizedRPGText(String key) {
/*    */     String str;
/* 24 */     if (key.isEmpty()) {
/* 25 */       return "";
/*    */     }
/*    */     try {
/* 28 */       str = this.bundle.getString(key);
/* 29 */     } catch (MissingResourceException e) {
/* 30 */       str = "MLK: \"" + key + "\"";
/* 31 */       if (System.getProperty("tcg.loclog") != null && System.getProperty("tcg.loclog").equals("true")) {
/* 32 */         LOG.error(str);
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 37 */     while (str.contains("#name{")) {
/* 38 */       int beginIndex = str.indexOf("#name{");
/* 39 */       String subString = str.substring(beginIndex, str.indexOf("}", beginIndex) + 1);
/*    */       
/* 41 */       int endIndex = 0; int i;
/* 42 */       for (i = 6; i < subString.length(); i++) {
/* 43 */         if (subString.charAt(i) == (this.male ? 'm' : 'f') && subString.charAt(i + 1) == ':') {
/* 44 */           i += 2;
/* 45 */           while (subString.charAt(i) == ' ')
/* 46 */             i++; 
/* 47 */           beginIndex = i;
/*    */           break;
/*    */         } 
/*    */       } 
/* 51 */       for (i = beginIndex; i < subString.length(); i++) {
/* 52 */         if (subString.charAt(i) == '|' || subString.charAt(i) == '}') {
/* 53 */           endIndex = i;
/*    */           break;
/*    */         } 
/*    */       } 
/* 57 */       if (beginIndex < endIndex) {
/* 58 */         str = str.replace(subString, subString.substring(beginIndex, endIndex)); continue;
/*    */       } 
/* 60 */       str = str.replace(subString, "ERROR: m/f incorrectly formated");
/*    */     } 
/* 62 */     return str;
/*    */   }
/*    */   
/*    */   public void setMale(boolean male) {
/* 66 */     this.male = male;
/*    */   }
/*    */   
/*    */   public static JavaLocalization getInstance() {
/* 70 */     return instance;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\localization\JavaLocalization.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */