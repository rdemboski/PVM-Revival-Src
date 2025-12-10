/*    */ package com.funcom.gameengine.model.chunks;
/*    */ 
/*    */ import com.jme.scene.Spatial;
/*    */ import java.util.AbstractList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ExposedSpatialList
/*    */   extends AbstractList<Spatial>
/*    */ {
/* 21 */   private Spatial[] elements = new Spatial[16];
/*    */   
/*    */   private int size;
/*    */   
/*    */   public boolean add(Spatial e) {
/* 26 */     if (this.size >= this.elements.length) {
/* 27 */       Spatial[] tmp = new Spatial[this.size * 2];
/* 28 */       System.arraycopy(this.elements, 0, tmp, 0, this.size);
/* 29 */       this.elements = tmp;
/*    */     } 
/* 31 */     this.elements[this.size++] = e;
/* 32 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public Spatial remove(int index) {
/* 37 */     Spatial oldElement = this.elements[index];
/*    */     
/* 39 */     int toCopy = this.size - index - 1;
/* 40 */     if (toCopy > 0) {
/* 41 */       System.arraycopy(this.elements, index + 1, this.elements, index, toCopy);
/*    */     }
/* 43 */     this.elements[--this.size] = null;
/*    */     
/* 45 */     return oldElement;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void removeRange(int fromIndex, int toIndex) {
/* 50 */     while (fromIndex < toIndex) {
/* 51 */       this.elements[fromIndex++] = null;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Spatial get(int index) {
/* 57 */     return this.elements[index];
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 62 */     return this.size;
/*    */   }
/*    */   
/*    */   public Spatial[] getInternalArray() {
/* 66 */     return this.elements;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\chunks\ExposedSpatialList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */