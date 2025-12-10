/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
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
/*    */ public class WebserverPetMappingPropertyReader
/*    */ {
/* 18 */   private final Properties properties = new Properties();
/*    */ 
/*    */   
/*    */   public void readProperties(String file) throws IOException {
/* 22 */     this.properties.load(new BufferedInputStream(new FileInputStream(file)));
/*    */   }
/*    */   
/*    */   public String getProperty(String key) {
/* 26 */     return (String)this.properties.get(key);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\WebserverPetMappingPropertyReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */