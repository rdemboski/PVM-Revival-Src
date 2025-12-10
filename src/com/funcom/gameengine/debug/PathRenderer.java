/*    */ package com.funcom.gameengine.debug;
/*    */ 
/*    */ import com.funcom.gameengine.view.LineInfo;
/*    */ import com.jme.renderer.ColorRGBA;
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jme.scene.Node;
/*    */ import com.jme.scene.Spatial;
/*    */ import java.awt.geom.Point2D;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathRenderer
/*    */ {
/* 16 */   private Node renderNode = new Node();
/*    */ 
/*    */   
/*    */   public void renderLines(Renderer r) {
/* 20 */     r.draw((Spatial)this.renderNode);
/*    */   }
/*    */   
/*    */   public void updatePath(List<Point2D> path) {
/* 24 */     this.renderNode.detachAllChildren();
/* 25 */     LineInfo lines = new LineInfo();
/* 26 */     Point2D crossSize = new Point2D.Double(0.1D, 0.2D);
/* 27 */     for (Point2D point2D : path) {
/* 28 */       lines.addLine(point2D.getX() + crossSize.getX(), point2D.getY() + crossSize.getY(), point2D.getX() - crossSize.getX(), point2D.getY() - crossSize.getY(), ColorRGBA.magenta);
/* 29 */       lines.addLine(point2D.getX() + crossSize.getX(), point2D.getY() - crossSize.getY(), point2D.getX() - crossSize.getX(), point2D.getY() + crossSize.getY(), ColorRGBA.magenta);
/*    */     } 
/* 31 */     this.renderNode.attachChild((Spatial)lines.getLine());
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\debug\PathRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */