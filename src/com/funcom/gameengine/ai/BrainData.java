/*    */ package com.funcom.gameengine.ai;
/*    */ 
/*    */ import com.funcom.gameengine.utils.SpatialUtils;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.jdom.DataConversionException;
/*    */ import org.jdom.Element;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BrainData
/*    */ {
/*    */   private String id;
/*    */   private Map<String, Object> params;
/*    */   
/*    */   public BrainData() {
/* 19 */     init("", Collections.EMPTY_MAP);
/*    */   }
/*    */   
/*    */   public BrainData(Element brainElement, String mapId) {
/* 23 */     String id = (brainElement == null) ? "" : brainElement.getAttribute("type").getValue();
/* 24 */     HashMap<String, Object> params = new HashMap<String, Object>();
/* 25 */     if (brainElement != null) {
/* 26 */       for (Object attribute : brainElement.getChildren("attribute")) {
/* 27 */         Element paramElement = (Element)attribute;
/* 28 */         String attName = paramElement.getAttributeValue("name");
/*    */         
/* 30 */         Object valueObject = valueOf(attName, paramElement, mapId);
/* 31 */         params.put(attName, valueObject);
/*    */       } 
/*    */     }
/* 34 */     init(id, params);
/*    */   }
/*    */   
/*    */   private void init(String id, Map<String, Object> params) {
/* 38 */     this.id = id;
/* 39 */     this.params = params;
/*    */   }
/*    */   
/*    */   private Object valueOf(String attName, Element element, String mapId) {
/* 43 */     if (attName.equals("point to coordinate")) {
/*    */       try {
/* 45 */         return SpatialUtils.getElementWorldCoordinate(element, mapId);
/* 46 */       } catch (DataConversionException e) {
/* 47 */         throw new RuntimeException(e);
/*    */       } 
/*    */     }
/*    */     
/* 51 */     String value = element.getAttributeValue("value");
/* 52 */     if (attName.equals("patrol name"))
/* 53 */       return value; 
/* 54 */     if (attName.equals("random attacks") || attName.equals("aura attack") || attName.equals("triggered spawnpoints") || attName.equals("spawnpoints triggered on death") || attName.equals("triggered spawnpoints1") || attName.equals("triggered spawnpoints2") || attName.equals("triggered spawnpoints3")) {
/*    */ 
/*    */ 
/*    */       
/* 58 */       if (value.isEmpty()) {
/* 59 */         return new Integer[0];
/*    */       }
/* 61 */       String[] splitValue = value.split(",");
/* 62 */       Integer[] items = new Integer[splitValue.length];
/* 63 */       for (int i = 0; i < splitValue.length; i++) {
/* 64 */         items[i] = Integer.valueOf(splitValue[i]);
/*    */       }
/* 66 */       return items;
/*    */     } 
/* 68 */     return value;
/*    */   }
/*    */   
/*    */   public String getId() {
/* 72 */     return this.id;
/*    */   }
/*    */   
/*    */   public Map<String, Object> getParams() {
/* 76 */     return this.params;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\ai\BrainData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */