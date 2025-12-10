/*    */ package com.funcom.gameengine.collisiondetection;
/*    */ 
/*    */ import com.funcom.commons.Vector2d;
/*    */ 
/*    */ public class CollisionResult
/*    */ {
/*    */   private Type type;
/*    */   private Vector2d vector;
/*    */   
/*    */   public CollisionResult() {
/* 11 */     this.vector = Vector2d.NULL;
/*    */   }
/*    */   
/*    */   public CollisionResult(Type type, Vector2d vector) {
/* 15 */     this.type = type;
/* 16 */     this.vector = vector;
/*    */   }
/*    */   
/*    */   public void setType(Type type) {
/* 20 */     this.type = type;
/*    */   }
/*    */   
/*    */   public Type getType() {
/* 24 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setVector(Vector2d vector) {
/* 28 */     this.vector = vector;
/*    */   }
/*    */ 
/*    */   
/*    */   public Vector2d getVector() {
/* 33 */     return this.vector;
/*    */   }
/*    */   
/*    */   public boolean isFailed() {
/* 37 */     return (this.type == Type.BLOCKED || this.type == Type.STOPPED);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 41 */     return getClass().getName() + "[type=" + this.type + ",vector=" + this.vector + "]";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public enum Type
/*    */   {
/* 48 */     STOPPED,
/* 49 */     BLOCKED,
/* 50 */     NONE;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\collisiondetection\CollisionResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */