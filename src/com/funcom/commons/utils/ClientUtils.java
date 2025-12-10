/*    */ package com.funcom.commons.utils;
/*    */ 
/*    */ import com.funcom.tcg.client.ui.Localizer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientUtils
/*    */ {
/*    */   public static String toTimeString(long totalSecs, Localizer localizer, Class clazz) {
/* 17 */     StringBuilder buf = new StringBuilder();
/* 18 */     double calcMins = totalSecs / 60.0D;
/* 19 */     double calcHours = Math.floor(calcMins) / 60.0D;
/* 20 */     double calcDays = Math.floor(calcHours) / 24.0D;
/*    */ 
/*    */     
/* 23 */     long totalMins = (long)Math.floor(calcMins);
/*    */ 
/*    */     
/* 26 */     long totalHours = (long)Math.floor(calcHours);
/* 27 */     long totalDays = (long)Math.floor(calcDays);
/*    */     
/* 29 */     if (totalDays > 0L) {
/* 30 */       buf.append(totalDays).append(":");
/*    */     }
/* 32 */     if (totalHours % 24L > 0L || totalDays > 0L) {
/* 33 */       if (totalHours % 24L < 10L) {
/* 34 */         buf.append("0");
/*    */       }
/* 36 */       buf.append(totalHours % 24L).append(":");
/*    */     } 
/* 38 */     if (totalMins % 60L >= 0L) {
/* 39 */       if (totalMins % 60L < 10L) {
/* 40 */         buf.append("0");
/*    */       }
/* 42 */       buf.append(totalMins % 60L).append(":");
/*    */     } 
/* 44 */     if (totalSecs % 60L >= 0L || totalSecs == 0L) {
/* 45 */       if (totalSecs % 60L < 10L) {
/* 46 */         buf.append("0");
/*    */       }
/* 48 */       buf.append(totalSecs % 60L);
/*    */     } 
/*    */     
/* 51 */     return buf.toString().trim();
/*    */   }
/*    */   
/*    */   public static long calcPassedSeconds(long millis, long updatedAt) {
/* 55 */     return (millis - System.currentTimeMillis() - updatedAt + 999L) / 1000L;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\common\\utils\ClientUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */