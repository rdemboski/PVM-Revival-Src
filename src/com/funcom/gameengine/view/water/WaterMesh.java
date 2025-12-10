/*    */ package com.funcom.gameengine.view.water;
/*    */ 
/*    */ import com.funcom.commons.Vector2d;
/*    */ import com.funcom.commons.geom.RectangleWC;
/*    */ import com.funcom.gameengine.model.chunks.ManageableSpatial;
/*    */ import com.jme.scene.TriMesh;
/*    */ 
/*    */ public abstract class WaterMesh
/*    */   extends TriMesh
/*    */   implements ManageableSpatial {
/*    */   public static final float MINIMUM_Y = 0.001F;
/*    */   protected static final int WINDING_RIGHT = 0;
/*    */   protected static final int WINDING_LEFT = 1;
/*    */   
/*    */   protected WaterMesh(String name) {
/* 16 */     super(name);
/*    */   }
/*    */   
/*    */   public abstract void setHeight(float paramFloat);
/*    */   
/*    */   public abstract float getHeight();
/*    */   
/*    */   public abstract void setBaseTextureScale(float paramFloat);
/*    */   
/*    */   public abstract float getBaseTextureScale();
/*    */   
/*    */   public abstract void setBaseTextureOffset(float paramFloat1, float paramFloat2);
/*    */   
/*    */   public abstract float getBaseTextureOffsetX();
/*    */   
/*    */   public abstract float getBaseTextureOffsetY();
/*    */   
/*    */   public abstract void setOverlayTextureScale(float paramFloat);
/*    */   
/*    */   public abstract float getOverlayTextureScale();
/*    */   
/*    */   public abstract void setOverlayTextureOffset(float paramFloat1, float paramFloat2);
/*    */   
/*    */   public abstract float getOverlayTextureOffsetX();
/*    */   
/*    */   public abstract float getOverlayTextureOffsetY();
/*    */   
/*    */   public abstract void setShorePosition(float paramFloat);
/*    */   
/*    */   public abstract float getShorePosition();
/*    */   
/*    */   public abstract void setShoreTextureScale(float paramFloat);
/*    */   
/*    */   public abstract float getShoreTextureScale();
/*    */   
/*    */   public abstract void setShoreTextureOffset(float paramFloat);
/*    */   
/*    */   public abstract float getShoreTextureOffset();
/*    */   
/*    */   public abstract RectangleWC getBounds();
/*    */   
/*    */   public abstract void translate(Vector2d paramVector2d);
/*    */   
/*    */   public abstract void updateWaterLines();
/*    */   
/*    */   public void translate(int x, int y) {
/* 62 */     translate(new Vector2d(x, y));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\water\WaterMesh.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */