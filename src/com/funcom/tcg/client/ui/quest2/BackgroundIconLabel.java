/*    */ package com.funcom.tcg.client.ui.quest2;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.jmex.bui.BImage;
/*    */ import com.jmex.bui.BLabel;
/*    */ import com.jmex.bui.background.BBackground;
/*    */ import com.jmex.bui.background.ImageBackground;
/*    */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*    */ import sun.reflect.generics.reflectiveObjects.NotImplementedException;
/*    */ 
/*    */ public class BackgroundIconLabel
/*    */   extends BLabel
/*    */ {
/*    */   private ImageBackground imgBackground;
/*    */   private ResourceManager resourceManager;
/*    */   
/*    */   private BackgroundIconLabel() {
/* 18 */     super("");
/* 19 */     throw new NotImplementedException();
/*    */   }
/*    */   
/*    */   public BackgroundIconLabel(ResourceManager resourceManager) {
/* 23 */     super("");
/* 24 */     this.resourceManager = resourceManager;
/*    */   }
/*    */   
/*    */   public BackgroundIconLabel(ResourceManager resourceManager, String backgroundPath) {
/* 28 */     super("");
/* 29 */     this.resourceManager = resourceManager;
/* 30 */     setImage(backgroundPath);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void wasRemoved() {
/* 36 */     super.wasRemoved();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setImage(String backgroundPath) {
/* 41 */     BImage elementImage = (BImage)this.resourceManager.getResource(BImage.class, backgroundPath);
/* 42 */     this.imgBackground = new ImageBackground(ImageBackgroundMode.SCALE_XY, elementImage);
/*    */     
/* 44 */     setBackground(0, (BBackground)this.imgBackground);
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 49 */     setBackground(0, null);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\quest2\BackgroundIconLabel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */