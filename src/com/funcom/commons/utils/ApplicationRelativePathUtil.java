/*    */ package com.funcom.commons.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ApplicationRelativePathUtil
/*    */ {
/*    */   private static volatile String applicationId;
/* 12 */   private static final String USER_HOME_DIR = System.getProperty("user.home");
/*    */   
/*    */   public static void init(String applicationId) {
/* 15 */     if (ApplicationRelativePathUtil.applicationId != null) {
/* 16 */       throw new IllegalStateException("already initialized");
/*    */     }
/* 18 */     ApplicationRelativePathUtil.applicationId = applicationId;
/*    */   }
/*    */   
/*    */   public static String getSystemDir() {
/* 22 */     if (applicationId == null) {
/* 23 */       throw new IllegalStateException("not initialized");
/*    */     }
/* 25 */     if (System.getProperty("tcg.plugin.path") != null) {
/* 26 */       return System.getProperty("tcg.plugin.path");
/*    */     }
/*    */     
/* 29 */     return (System.getProperty("tcg.local.path") != null) ? System.getProperty("tcg.local.path") : (USER_HOME_DIR + "/.funcom/" + applicationId);
/*    */   }
/*    */   
/*    */   public static String getApplicationRelativeBasePath(String relPath) {
/* 33 */     return (System.getProperty("tcg.local.path") == null) ? relPath : (System.getProperty("tcg.local.path") + "/" + relPath);
/*    */   }
/*    */   
/*    */   public static String getApplicationRelativeResourcePath(String resPath) {
/* 37 */     String prop = System.getProperty("tcg.resourcepath");
/* 38 */     return (prop == null) ? resPath : (prop + "/" + resPath);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\common\\utils\ApplicationRelativePathUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */