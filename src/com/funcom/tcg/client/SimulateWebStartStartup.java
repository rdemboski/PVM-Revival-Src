/*    */ package com.funcom.tcg.client;
/*    */ 
/*    */ import java.io.FileNotFoundException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SimulateWebStartStartup
/*    */ {
/*    */   public static void main(String[] args) throws FileNotFoundException {
/* 13 */     SimulateWebStartStartup simulateWebStartStartup = new SimulateWebStartStartup();
/* 14 */     simulateWebStartStartup.setSystemProperties();
/* 15 */     simulateWebStartStartup.startupTcgJme(args);
/*    */   }
/*    */   
/*    */   private void startupTcgJme(String[] args) throws FileNotFoundException {
/* 19 */     TcgJme.main(args);
/*    */   }
/*    */   
/*    */   private void setSystemProperties() {
/* 23 */     System.setProperty("-resconfig=", "/config/alien_resource_configuration_client.xml");
/*    */     
/* 25 */     System.setProperty("network.host", "195.110.28.50");
/* 26 */     System.setProperty("tcg.local.path", "/.funcom/pvm/prod");
/* 27 */     System.setProperty("tcg.binaryresourcepath", "/resources_ab");
/* 28 */     System.setProperty("tcg.resourcepath", "/resources");
/* 29 */     System.setProperty("tcg.patch", "true");
/* 30 */     System.setProperty("-nosound", "true");
/* 31 */     System.setProperty("sid", "06306293cd5b4f68b9b75b619171aaf8");
/* 32 */     System.setProperty("uid", "5");
/* 33 */     System.setProperty("cid", "204");
/* 34 */     System.setProperty("tcg.webstart", "true");
/* 35 */     System.setProperty("live", "false");
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\SimulateWebStartStartup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */