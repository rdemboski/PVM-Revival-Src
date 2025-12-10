/*    */ package com.funcom.tcg.client.ui.maps;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.CacheType;
/*    */ import com.funcom.gameengine.view.MapObject;
/*    */ import com.funcom.rpgengine2.monsters.MonsterDescription;
/*    */ import com.funcom.rpgengine2.portkey.PortkeyDescription;
/*    */ import com.funcom.rpgengine2.waypoints.WaypointDescription;
/*    */ import com.funcom.rpgengine2.waypoints.WaypointDestinationPortalDescription;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.jmex.bui.BButton;
/*    */ import com.jmex.bui.BImage;
/*    */ import com.jmex.bui.background.BBackground;
/*    */ import com.jmex.bui.background.ImageBackground;
/*    */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*    */ 
/*    */ public class MapObjectButton extends BButton {
/*    */   private MapObject mapObject;
/*    */   private BImage objectImage;
/*    */   
/*    */   public BImage getObjectImage() {
/* 21 */     return this.objectImage;
/*    */   }
/*    */   
/*    */   public MapObjectButton(MapObject mapObject) {
/* 25 */     super("");
/* 26 */     this.mapObject = mapObject;
/* 27 */     initializeLabel();
/*    */   }
/*    */   
/*    */   private void initializeLabel() {
/* 31 */     this.objectImage = (BImage)TcgGame.getResourceManager().getResource(BImage.class, "gui/icons/interface/maps/" + this.mapObject.getImagePath(), CacheType.CACHE_TEMPORARILY);
/*    */ 
/*    */     
/* 34 */     ImageBackground labelBackground = new ImageBackground(ImageBackgroundMode.SCALE_XY, this.objectImage);
/*    */     
/* 36 */     setBackground(0, (BBackground)labelBackground);
/* 37 */     setBackground(1, (BBackground)labelBackground);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTooltipText() {
/* 42 */     PortkeyDescription portkeyDescription = TcgGame.getRpgLoader().getPortkeyManager().getPortkeyDescription(this.mapObject.getDescriptionId());
/* 43 */     if (portkeyDescription != null)
/* 44 */       return portkeyDescription.getName(); 
/* 45 */     MonsterDescription monsterDescription = TcgGame.getRpgLoader().getMonsterManager().getDescription(this.mapObject.getDescriptionId());
/* 46 */     if (monsterDescription != null)
/* 47 */       return monsterDescription.getName(); 
/* 48 */     WaypointDestinationPortalDescription wayPointDestDescription = TcgGame.getRpgLoader().getWaypointManager().getWaypointDestinationPortalDescription(this.mapObject.getDescriptionId());
/* 49 */     if (wayPointDestDescription != null)
/* 50 */       return wayPointDestDescription.getName(); 
/* 51 */     WaypointDescription wayPointDescription = TcgGame.getRpgLoader().getWaypointManager().getWaypoint(this.mapObject.getDescriptionId());
/* 52 */     if (wayPointDescription != null) {
/* 53 */       return wayPointDescription.getName();
/*    */     }
/* 55 */     return this.mapObject.getDescriptionId();
/*    */   }
/*    */   
/*    */   public MapObject getMapObject() {
/* 59 */     return this.mapObject;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\maps\MapObjectButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */