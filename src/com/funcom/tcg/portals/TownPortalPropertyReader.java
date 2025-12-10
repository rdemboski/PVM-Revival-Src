/*    */ package com.funcom.tcg.portals;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
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
/*    */ public class TownPortalPropertyReader
/*    */ {
/*    */   private static final String X_COORDINATE = "x-coordinate";
/*    */   private static final String Y_COORDINATE = "y-coordinate";
/*    */   private static final String HOME_TOWN = "hometown";
/*    */   public static final String TOWN_PORTAL_XML = "town_portal_xml";
/*    */   public static final String RETURN_POINT_XML = "return_point_xml";
/*    */   public static final String TOWN_PORTAL_OTHER_XML = "town_portal_other_xml";
/*    */   public static final String TOWN_PORTAL_DFX = "town_portal_dfx";
/*    */   public static final String TOWN_PORTAL_OTHER_DFX = "town_portal_other_dfx";
/*    */   public static final String RETURN_POINT_DFX = "return_point_dfx";
/* 25 */   private final Properties properties = new Properties();
/*    */ 
/*    */   
/*    */   public void readProperties(String content) throws IOException {
/* 29 */     StringReader stringReader = new StringReader(content);
/* 30 */     this.properties.load(stringReader);
/* 31 */     stringReader.close();
/*    */   }
/*    */   
/*    */   public String getProperty(String key) {
/* 35 */     return (String)this.properties.get(key);
/*    */   }
/*    */   
/*    */   public WorldCoordinate loadHomeTownProperties() {
/* 39 */     WorldCoordinate wc = new WorldCoordinate();
/* 40 */     wc.addOffset(Double.parseDouble(this.properties.getProperty("x-coordinate")), Double.parseDouble(this.properties.getProperty("y-coordinate")));
/* 41 */     wc.setMapId(this.properties.getProperty("hometown"));
/* 42 */     return wc;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\portals\TownPortalPropertyReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */