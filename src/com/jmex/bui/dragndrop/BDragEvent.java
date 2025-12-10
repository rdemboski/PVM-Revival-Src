/*    */ package com.jmex.bui.dragndrop;
/*    */ 
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BuiSystem;
/*    */ import com.jmex.bui.event.BEvent;
/*    */ 
/*    */ public class BDragEvent
/*    */   extends BEvent {
/*    */   private Object draggedObject;
/*    */   
/*    */   public BDragEvent(BComponent source, Object draggedObject) {
/* 12 */     super(source, BuiSystem.getRootNode().getTickStamp());
/* 13 */     this.draggedObject = draggedObject;
/*    */   }
/*    */   
/*    */   public BComponent getSource() {
/* 17 */     return (BComponent)super.getSource();
/*    */   }
/*    */   
/*    */   public Object getDraggedObject() {
/* 21 */     return this.draggedObject;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 25 */     StringBuilder builder = new StringBuilder();
/* 26 */     builder.append("BDragEvent@").append(hashCode()).append("{");
/* 27 */     builder.append("source = ").append(getSource()).append(", ");
/* 28 */     builder.append("draggedObject = ").append(this.draggedObject).append("}");
/* 29 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\dragndrop\BDragEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */