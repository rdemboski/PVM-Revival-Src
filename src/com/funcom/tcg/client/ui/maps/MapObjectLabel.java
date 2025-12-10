/*    */ package com.funcom.tcg.client.ui.maps;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.CacheType;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.gameengine.view.MapObject;
/*    */ import com.funcom.rpgengine2.monsters.MonsterDescription;
/*    */ import com.funcom.rpgengine2.portkey.PortkeyDescription;
/*    */ import com.funcom.rpgengine2.vendor.VendorDescription;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.jmex.bui.BImage;
/*    */ import com.jmex.bui.BLabel;
/*    */ import com.jmex.bui.background.BBackground;
/*    */ import com.jmex.bui.background.ImageBackground;
/*    */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*    */ 
/*    */ public class MapObjectLabel
/*    */   extends BLabel
/*    */ {
/*    */   public static final int WIDTH = 35;
/*    */   public static final int HEIGHT = 35;
/*    */   private MapObject mapObject;
/*    */   private BImage objectImage;
/*    */   
/*    */   public MapObjectLabel(MapObject mapObject, ResourceManager resourceManager) {
/* 25 */     super("");
/* 26 */     this.mapObject = mapObject;
/* 27 */     initializeLabel(resourceManager);
/*    */   }
/*    */ 
/*    */   
/*    */   public BImage getObjectImage() {
/* 32 */     return this.objectImage;
/*    */   }
/*    */   
/*    */   private void initializeLabel(ResourceManager resourceManager) {
/* 36 */     this.objectImage = (BImage)resourceManager.getResource(BImage.class, "gui/icons/interface/maps/" + this.mapObject.getImagePath(), CacheType.CACHE_TEMPORARILY);
/*    */ 
/*    */     
/* 39 */     ImageBackground labelBackground = new ImageBackground(ImageBackgroundMode.SCALE_XY, this.objectImage);
/*    */     
/* 41 */     setBackground(0, (BBackground)labelBackground);
/* 42 */     setBackground(1, (BBackground)labelBackground);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTooltipText() {
/* 47 */     PortkeyDescription portkeyDescription = TcgGame.getRpgLoader().getPortkeyManager().getPortkeyDescription(this.mapObject.getDescriptionId());
/* 48 */     if (portkeyDescription != null)
/* 49 */       return portkeyDescription.getName(); 
/* 50 */     MonsterDescription monsterDescription = TcgGame.getRpgLoader().getMonsterManager().getDescription(this.mapObject.getDescriptionId());
/* 51 */     if (monsterDescription != null)
/* 52 */       return monsterDescription.getName(); 
/* 53 */     VendorDescription vendorDescription = TcgGame.getRpgLoader().getVendorManager().getVendorDescription(this.mapObject.getDescriptionId());
/* 54 */     if (vendorDescription != null) {
/* 55 */       return vendorDescription.getName();
/*    */     }
/* 57 */     return this.mapObject.getDescriptionId();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\maps\MapObjectLabel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */