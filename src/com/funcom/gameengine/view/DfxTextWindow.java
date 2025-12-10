/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.jme.math.Vector3f;
/*    */ import com.jme.renderer.ColorRGBA;
/*    */ import com.jme.scene.Spatial;
/*    */ import com.jme.system.DisplaySystem;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class DfxTextWindow
/*    */   extends FloatingSpatials
/*    */ {
/*    */   private static final String DFXTEXT_FONT_NAME = "dfxFontAlias";
/* 14 */   private ArrayList<SpatialRow> spatialRows = new ArrayList<SpatialRow>();
/*    */   
/*    */   private int numberOfLines;
/*    */   
/*    */   private float screenX;
/*    */   private float screenY;
/*    */   private float[] colorRBGA;
/*    */   private float scale;
/*    */   private Vector3f objectPosition;
/*    */   
/*    */   public DfxTextWindow(ResourceManager resourceManager, float screenX, float screenY, int numberOfLines, float[] colorRBGA, float scale) {
/* 25 */     super(resourceManager, "dfxFontAlias");
/* 26 */     this.objectPosition = new Vector3f();
/* 27 */     this.screenX = screenX;
/* 28 */     this.screenY = screenY;
/* 29 */     this.numberOfLines = numberOfLines;
/* 30 */     this.colorRBGA = colorRBGA;
/* 31 */     this.scale = scale;
/* 32 */     this.arriveingDuration = 0.3F;
/* 33 */     this.stableDuration = 1.5F;
/* 34 */     setCenterText(true);
/*    */   }
/*    */   
/*    */   public void showText(String text) {
/* 38 */     showText(text, new ColorRGBA(this.colorRBGA[0], this.colorRBGA[1], this.colorRBGA[2], this.colorRBGA[3]), this.scale);
/*    */   }
/*    */   
/*    */   public void showText(String text, ColorRGBA color, float scale) {
/* 42 */     if (text.isEmpty())
/*    */       return; 
/* 44 */     this.objectPosition.set(DisplaySystem.getDisplaySystem().getWidth() * this.screenX, DisplaySystem.getDisplaySystem().getHeight() * this.screenY, 0.0F);
/* 45 */     showCombinedSpatialsAtScreenCoordinate(text, color, this.objectPosition, scale);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void showSpatialsAtScreenCoordinate(SpatialRow spatialRow, Vector3f screenCoords) {
/* 50 */     super.showSpatialsAtScreenCoordinate(spatialRow, screenCoords);
/*    */     
/* 52 */     while (this.spatialRows.size() >= this.numberOfLines) {
/* 53 */       SpatialRow row = this.spatialRows.get(0);
/* 54 */       row.detachAllFromParent();
/* 55 */       this.spatialRows.remove(0);
/*    */     } 
/*    */     
/* 58 */     for (SpatialRow row : this.spatialRows) {
/* 59 */       row.translateY(spatialRow.getRowHeight());
/*    */     }
/*    */     
/* 62 */     this.spatialRows.add(spatialRow);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void modifyWithRandomDisplacement(Vector3f screenCoords) {}
/*    */ 
/*    */   
/*    */   public void updateWorldData(float v) {
/* 71 */     super.updateWorldData(v);
/* 72 */     update(v);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void removeSpatial(Spatial spatial) {
/* 77 */     super.removeSpatial(spatial);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\DfxTextWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */