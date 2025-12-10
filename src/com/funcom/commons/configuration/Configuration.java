/*    */ package com.funcom.commons.configuration;
/*    */ 
/*    */ import java.util.prefs.Preferences;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Configuration
/*    */ {
/* 13 */   private static final Configuration INSTANCE = new Configuration();
/*    */   
/*    */   private static final String ROOT = "funcom/tcg";
/*    */   private static final String CLIENT = "client";
/*    */   private static final String SERVER = "server";
/*    */   private static final String TOOL = "utils";
/*    */   
/*    */   public static Configuration instance() {
/* 21 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Preferences getProjectRoot() {
/* 28 */     return Preferences.userRoot().node("funcom/tcg");
/*    */   }
/*    */   
/*    */   public Preferences getGlobalConfiguration() {
/* 32 */     return getProjectRoot();
/*    */   }
/*    */   
/*    */   public Preferences getClientConfiguration() {
/* 36 */     return getProjectRoot().node("client");
/*    */   }
/*    */   
/*    */   public Preferences getServerConfiguration() {
/* 40 */     return getProjectRoot().node("server");
/*    */   }
/*    */   
/*    */   public Preferences getToolConfiguration() {
/* 44 */     return getProjectRoot().node("utils");
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\configuration\Configuration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */