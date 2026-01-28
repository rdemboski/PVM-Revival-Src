/*    */ package com.funcom.rpgengine2.loader;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FieldMap
/*    */   extends HashMap<Object, String>
/*    */ {
/*    */   private String lastGottenKeyString;
/*    */   
/*    */   public FieldMap(Object[] keys, String[] values) {
/* 13 */     if (keys.length > values.length) {
/* 14 */       String itemId = "";
/* 15 */       if (values.length > 0) {
/* 16 */         itemId = values[0];
/*    */       }
/* 18 */       throw new IllegalArgumentException("keys and values must have same size: keyCount=" + keys.length + " valueCount=" + values.length + " itemId=" + itemId);
/*    */     } 
/*    */     
            for (int i = 0; i < keys.length; i++) {
                put(keys[i], values[i]);
            }
/*    */   }
/*    */ 
/*    */   
/*    */   public String get(Object key) {
/* 28 */     if (key == null) {
/* 29 */       this.lastGottenKeyString = null;
/*    */     } else {
/* 31 */       this.lastGottenKeyString = key.toString();
/*    */     } 
/*    */     
/* 34 */     return super.get(key);
/*    */   }
/*    */   
/*    */   public long getLong(Object key) {
/* 38 */     String str = get(key);
/*    */     
/*    */     try {
/* 41 */       return Long.parseLong(str);
/* 42 */     } catch (NumberFormatException e) {
/* 43 */       throw new RuntimeException("Error parsing number(long) for key: key=" + key, e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean getBoolean(String errorMessage, Object key) {
/* 48 */     String str = get(key);
/*    */     
/* 50 */     if (str != null) {
/* 51 */       return Boolean.parseBoolean(str);
/*    */     }
/* 53 */     throw new NullPointerException("missing value, " + errorMessage + ": key=" + key);
/*    */   }
/*    */   
/*    */   public int getInt(Object key, int defaultValue) {
/* 57 */     String str = get(key);
/*    */     
/* 59 */     if (!str.isEmpty()) {
/*    */       try {
/* 61 */         return Integer.parseInt(str);
/* 62 */       } catch (NumberFormatException e) {
/* 63 */         throw new RuntimeException("error parsing '" + key + "'", e);
/*    */       } 
/*    */     }
/*    */     
/* 67 */     return defaultValue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getLastGottenKeyString() {
/* 76 */     return this.lastGottenKeyString;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loader\FieldMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */