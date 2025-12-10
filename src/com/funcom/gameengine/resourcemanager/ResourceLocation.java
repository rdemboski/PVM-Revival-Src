/*    */ package com.funcom.gameengine.resourcemanager;
/*    */ 
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class ResourceLocation
/*    */ {
/*  7 */   private static final Logger LOG = Logger.getLogger(ResourceLocation.class.getName());
/*    */   
/*    */   private static String resourceBase;
/*    */ 
/*    */   
/*    */   public static String getBinaryResourceBase() {
/* 13 */     if (resourceBase == null) {
/*    */       
/* 15 */       String tmp = System.getProperty("tcg.binaryresourcepath");
/*    */       
/* 17 */       if (tmp == null) {
/* 18 */         tmp = (System.getenv("TCG_RESOURCES") == null) ? (System.getProperty("tcg.resourcepath") + "") : System.getenv("TCG_RESOURCES");
/*    */       }
/*    */       
/* 21 */       LOG.info("*** Binary Resource Base: " + tmp);
/*    */       
/* 23 */       resourceBase = tmp;
/*    */     } 
/*    */     
/* 26 */     return resourceBase;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\ResourceLocation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */