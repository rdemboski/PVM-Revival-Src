/*    */ package com.funcom.commons.utils;
/*    */ 
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class GlobalTime {
/*  6 */   private static final Logger LOGGER = Logger.getLogger(GlobalTime.class);
/*    */   private static TimeSystem instance;
/*    */   
/*    */   public static TimeSystem getInstance() {
/* 10 */     if (instance == null) {
/* 11 */       instance = new RealSystemTime();
/*    */     }
/*    */ 
/*    */     
/* 15 */     return instance;
/*    */   }
/*    */   
/*    */   public static void setInstance(TimeSystem instance) {
/* 19 */     GlobalTime.instance = instance;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\common\\utils\GlobalTime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */