/*    */ package com.funcom.commons.localization;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConnectionParams
/*    */ {
/*    */   private String dbDriver;
/*    */   private String dbUrl;
/*    */   private String dbName;
/*    */   private String dbUserName;
/*    */   private String dbPassord;
/*    */   
/*    */   public ConnectionParams(String dbDriver, String dbUrl, String dbName, String dbUserName, String dbPassord) {
/* 15 */     this.dbDriver = dbDriver;
/* 16 */     this.dbUrl = dbUrl;
/* 17 */     this.dbName = dbName;
/* 18 */     this.dbUserName = dbUserName;
/* 19 */     this.dbPassord = dbPassord;
/*    */   }
/*    */   
/*    */   public String getDbDriver() {
/* 23 */     return this.dbDriver;
/*    */   }
/*    */   
/*    */   public String getDbUrl() {
/* 27 */     return this.dbUrl;
/*    */   }
/*    */   
/*    */   public String getDbName() {
/* 31 */     return this.dbName;
/*    */   }
/*    */   
/*    */   public String getDbUserName() {
/* 35 */     return this.dbUserName;
/*    */   }
/*    */   
/*    */   public String getDbPassord() {
/* 39 */     return this.dbPassord;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\localization\ConnectionParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */