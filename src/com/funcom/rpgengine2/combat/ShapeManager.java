/*    */ package com.funcom.rpgengine2.combat;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShapeManager
/*    */ {
/* 10 */   private Map<String, Shape> shapes = new HashMap<String, Shape>();
/*    */   
/*    */   public synchronized void putShape(Shape shape) {
/* 13 */     String id = shape.getId();
/*    */     
/* 15 */     if (id == null) {
/* 16 */       throw new NullPointerException("Shape Id cannot be null");
/*    */     }
/*    */     
/* 19 */     if (this.shapes.containsKey(id)) {
/* 20 */       throw new IllegalArgumentException("Duplicate shape id: id=" + id);
/*    */     }
/*    */     
/* 23 */     this.shapes.put(id, shape);
/*    */   }
/*    */   
/*    */   public synchronized void clearData() {
/* 27 */     this.shapes.clear();
/*    */   }
/*    */   
/*    */   public synchronized Shape getShape(String id) {
/* 31 */     return this.shapes.get(id);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\combat\ShapeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */