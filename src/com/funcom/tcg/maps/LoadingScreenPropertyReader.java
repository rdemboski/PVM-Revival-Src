/*    */ package com.funcom.tcg.maps;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.StringReader;
/*    */ import java.util.Properties;
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
/*    */ public class LoadingScreenPropertyReader
/*    */ {
/*    */   public static final String DEFAULT_BACKGROUND = "default_background";
/*    */   public static final String DEFAULT_LOADING_SCREEN = "default_loading_screen";
/*    */   public static final String DEFAULT_PROGRESS_IMAGE = "default_progressimage";
/*    */   public static final String DEFAULT_PROGRSS_BACKGROUND_IMAGE = "default_backgroundimage";
/*    */   public static final String DEFAULT_PROGRESS_OVERLAY_IMAGE = "default_overlayimage";
/* 23 */   private final Properties properties = new Properties();
/*    */ 
/*    */   
/*    */   public void readProperties(String content) throws IOException {
/* 27 */     StringReader stringReader = new StringReader(content);
/* 28 */     this.properties.load(stringReader);
/* 29 */     stringReader.close();
/*    */   }
/*    */   
/*    */   public String getProperty(String key) {
/* 33 */     return (String)this.properties.get(key);
/*    */   }
/*    */   
/*    */   public String getDefaultLoadingScreen() {
/* 37 */     return (String)this.properties.get("default_loading_screen");
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\maps\LoadingScreenPropertyReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */