/*    */ package com.funcom.rpgengine2.regions;
/*    */ 
/*    */ import com.funcom.rpgengine2.items.ItemDescription;
/*    */ import com.funcom.rpgengine2.loader.DataRecords;
/*    */ import com.funcom.rpgengine2.loader.RpgLoader;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class RegionManager
/*    */ {
/* 12 */   private Map<String, RegionDescription> regionDescriptionMap = new HashMap<String, RegionDescription>();
/*    */   
/*    */   public RegionDescription getRegionDescription(String id) {
/* 15 */     return this.regionDescriptionMap.get(id);
/*    */   }
/*    */   
/*    */   public void createRegionDescriptions(DataRecords dataRecords, RpgLoader rpgLoader) {
/* 19 */     List<String[]> records = dataRecords.getRegionDescriptionFiles();
/* 20 */     for (String[] record : records) {
/* 21 */       int index = 0;
/*    */       
/* 23 */       String id = record[index++];
/* 24 */       String onEnterItemStr = record[index++];
/* 25 */       String onExitItemStr = record[index++];
/*    */       
/* 27 */       ItemDescription onEnterDescription = rpgLoader.getItemManager().getDescription(onEnterItemStr, 0);
/* 28 */       ItemDescription onExitDescription = rpgLoader.getItemManager().getDescription(onExitItemStr, 0);
/*    */       
/* 30 */       if (onEnterDescription == null) {
/* 31 */         throw new IllegalArgumentException("RegionDescription, on enter item not found: name=" + onEnterItemStr);
/*    */       }
/* 33 */       if (onExitDescription == null) {
/* 34 */         throw new IllegalArgumentException("RegionDescription, on exit item not found: name=" + onExitItemStr);
/*    */       }
/*    */       
/* 37 */       RegionDescription regionDescription = new RegionDescription(id, onEnterDescription, onExitDescription);
/* 38 */       this.regionDescriptionMap.put(id, regionDescription);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void clear() {
/* 43 */     this.regionDescriptionMap.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\regions\RegionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */