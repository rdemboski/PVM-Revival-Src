/*    */ package com.funcom.gameengine;
/*    */ 
/*    */ import com.funcom.gameengine.model.props.Prop;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class ZComparator
/*    */   implements Comparator<Prop>
/*    */ {
/*    */   private int mult;
/*    */   
/*    */   public ZComparator() {
/* 12 */     this.mult = 1;
/*    */   }
/*    */   
/*    */   public ZComparator(boolean inverse) {
/* 16 */     if (inverse) {
/* 17 */       this.mult = -1;
/*    */     } else {
/* 19 */       this.mult = 1;
/*    */     } 
/*    */   }
/*    */   
/*    */   public int compare(Prop o1, Prop o2) {
/* 24 */     WorldCoordinate o1Pos = o1.getPosition();
/* 25 */     WorldCoordinate o2Pos = o2.getPosition();
/*    */ 
/*    */     
/* 28 */     int tileCompare = (o1Pos.getTileCoord()).y - (o2Pos.getTileCoord()).y;
/* 29 */     if (tileCompare != 0) {
/* 30 */       return (int)Math.signum(tileCompare) * this.mult;
/*    */     }
/*    */     
/* 33 */     double offsetCompare = o1Pos.getTileOffset().getY() - o2Pos.getTileOffset().getY();
/* 34 */     return (int)Math.signum(offsetCompare) * this.mult;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\ZComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */