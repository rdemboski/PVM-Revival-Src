/*    */ package com.funcom.rpgengine2.portals;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.rpgengine2.loader.DataRecords;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class CustomPortalManager
/*    */ {
/* 11 */   private Map<String, CustomPortalDescription> customPortalDescriptionMap = new HashMap<String, CustomPortalDescription>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CustomPortalDescription getCustomPortalDescriptionById(String id) {
/* 17 */     return this.customPortalDescriptionMap.get(id);
/*    */   }
/*    */ 
/*    */   
/*    */   public void createCustomPortals(DataRecords dataRecords) {
/* 22 */     for (String[] records : dataRecords.getCustomPortals()) {
/* 23 */       int index = 0;
/* 24 */       String id = records[index++];
/* 25 */       String name = records[index++];
/* 26 */       String destMap = records[index++];
/* 27 */       int destX = Integer.parseInt(records[index++]);
/* 28 */       int destY = Integer.parseInt(records[index++]);
/* 29 */       WorldCoordinate wc = new WorldCoordinate(destX, destY, 0.0D, 0.0D, destMap, 0);
/* 30 */       boolean townPortalAbilityActivation = Boolean.parseBoolean(records[index++]);
/* 31 */       int dropX = 0;
/* 32 */       int dropY = 0;
/*    */       try {
/* 34 */         dropX = Integer.parseInt(records[index++]);
/* 35 */         dropY = Integer.parseInt(records[index++]);
/* 36 */       } catch (NumberFormatException e) {}
/*    */ 
/*    */ 
/*    */       
/* 40 */       CustomPortalDescription customPortalDescription = new CustomPortalDescription(id, name, wc, townPortalAbilityActivation, dropX, dropY);
/* 41 */       this.customPortalDescriptionMap.put(id, customPortalDescription);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void clear() {
/* 46 */     this.customPortalDescriptionMap.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\portals\CustomPortalManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */