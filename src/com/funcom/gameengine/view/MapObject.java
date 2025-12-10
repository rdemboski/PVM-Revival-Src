/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ 
/*    */ 
/*    */ public class MapObject
/*    */ {
/*    */   private String descriptionId;
/*    */   private WorldCoordinate worldCoordinate;
/*    */   private String imagePath;
/*    */   
/*    */   public MapObject(String descriptionId, WorldCoordinate worldCoordinate, String imagePath) {
/* 13 */     this.descriptionId = descriptionId;
/* 14 */     this.worldCoordinate = worldCoordinate;
/* 15 */     this.imagePath = imagePath;
/*    */   }
/*    */   
/*    */   public String getDescriptionId() {
/* 19 */     return this.descriptionId;
/*    */   }
/*    */   
/*    */   public WorldCoordinate getWorldCoordinate() {
/* 23 */     return this.worldCoordinate;
/*    */   }
/*    */   
/*    */   public void setWorldCoordinate(WorldCoordinate worldCoordinate) {
/* 27 */     this.worldCoordinate = worldCoordinate;
/*    */   }
/*    */   
/*    */   public String getImagePath() {
/* 31 */     return this.imagePath;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 36 */     return "MapObject{descriptionId='" + this.descriptionId + '\'' + ", worldCoordinate=" + this.worldCoordinate + ", imagePath='" + this.imagePath + '\'' + '}';
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum MapObjectType
/*    */   {
/* 45 */     VENDOR_TYPE(1, "vendoricon.png"),
/* 46 */     MAP_PORTAL_TYPE(2, "mapportal.png"),
/* 47 */     QUESTGIVER_TYPE(3, "available_quest.png"),
/* 48 */     QUESTGIVER_TYPE_COMPLETE(3, "quest_ending.png"),
/* 49 */     PLAYER_TYPE(5, "maparrow.png"),
/* 50 */     FRIEND_TYPE(6, "friends.png"),
/* 51 */     QUEST_OBJECTIVE_TYPE(7, "target.png");
/*    */     
/*    */     int id;
/*    */     
/*    */     String icon;
/*    */     
/*    */     MapObjectType(int id, String icon) {
/* 58 */       this.id = id;
/* 59 */       this.icon = icon;
/*    */     }
/*    */     
/*    */     public int getId() {
/* 63 */       return this.id;
/*    */     }
/*    */     
/*    */     public String getIcon() {
/* 67 */       return this.icon;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\MapObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */