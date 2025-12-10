/*    */ package com.funcom.rpgengine2.portals;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ 
/*    */ public class CustomPortalDescription {
/*    */   private String id;
/*    */   private String name;
/*    */   private WorldCoordinate destinationCoordinate;
/*    */   private boolean townPortalAbilityActivation;
/*    */   private int dropX;
/*    */   private int dropY;
/*    */   
/*    */   public CustomPortalDescription(String id, String name, WorldCoordinate destinationCoordinate, boolean townPortalAbilityActivation, int dropX, int dropY) {
/* 14 */     this.id = id;
/* 15 */     this.name = name;
/* 16 */     this.destinationCoordinate = destinationCoordinate;
/* 17 */     this.townPortalAbilityActivation = townPortalAbilityActivation;
/* 18 */     this.dropX = dropX;
/* 19 */     this.dropY = dropY;
/*    */   }
/*    */   
/*    */   public String getId() {
/* 23 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 27 */     return this.name;
/*    */   }
/*    */   
/*    */   public WorldCoordinate getDestinationCoordinate() {
/* 31 */     return this.destinationCoordinate;
/*    */   }
/*    */   
/*    */   public boolean isTownPortalAbilityActivation() {
/* 35 */     return this.townPortalAbilityActivation;
/*    */   }
/*    */   
/*    */   public int getDropX() {
/* 39 */     return this.dropX;
/*    */   }
/*    */   
/*    */   public int getDropY() {
/* 43 */     return this.dropY;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\portals\CustomPortalDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */