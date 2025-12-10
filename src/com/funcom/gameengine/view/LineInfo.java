/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.jme.math.Vector3f;
/*    */ import com.jme.renderer.ColorRGBA;
/*    */ import com.jme.scene.Line;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LineInfo
/*    */ {
/* 16 */   private ArrayList<Vector3f> vertex = new ArrayList<Vector3f>();
/* 17 */   private ArrayList<Vector3f> normal = new ArrayList<Vector3f>();
/* 18 */   private ArrayList<ColorRGBA> color = new ArrayList<ColorRGBA>();
/*    */ 
/*    */   
/*    */   public void addLine(double x1, double y1, double x2, double y2, ColorRGBA color) {
/* 22 */     this.vertex.add(new Vector3f((float)x1, 0.0F, (float)y1));
/* 23 */     this.vertex.add(new Vector3f((float)x2, 0.0F, (float)y2));
/* 24 */     this.normal.add(new Vector3f(0.0F, 1.0F, 0.0F));
/* 25 */     this.normal.add(new Vector3f(0.0F, 1.0F, 0.0F));
/* 26 */     this.color.add(color);
/* 27 */     this.color.add(color);
/*    */   }
/*    */   
/*    */   public Line getLine() {
/* 31 */     return new Line("Line", this.vertex.<Vector3f>toArray(new Vector3f[1]), this.normal.<Vector3f>toArray(new Vector3f[1]), this.color.<ColorRGBA>toArray(new ColorRGBA[1]), null);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\LineInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */