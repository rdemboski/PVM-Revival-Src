/*    */ package com.funcom.gameengine.jme;
/*    */ 
/*    */ import com.funcom.gameengine.view.ContentIndentifiable;
/*    */ import com.jme.scene.shape.Quad;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileQuad
/*    */   extends Quad
/*    */   implements ContentIndentifiable
/*    */ {
/*    */   public void updateGeometry(float width, float height) {}
/*    */   
/*    */   public int getContentType() {
/* 18 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void initialize(float width, float height) {
/* 23 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\TileQuad.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */