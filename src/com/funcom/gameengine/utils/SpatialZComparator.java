/*    */ package com.funcom.gameengine.utils;
/*    */ 
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import java.util.Collections;
/*    */ import java.util.Comparator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class SpatialZComparator implements Comparator<PropNode> {
/*    */   public SpatialZComparator() {
/* 12 */     this.mult = 1;
/*    */   }
/*    */   private int mult;
/*    */   public SpatialZComparator(boolean inverse) {
/* 16 */     if (inverse) {
/* 17 */       this.mult = -1;
/*    */     } else {
/* 19 */       this.mult = 1;
/*    */     } 
/*    */   }
/*    */   
/*    */   public int compare(PropNode o1, PropNode o2) {
/* 24 */     return (int)Math.signum((o1.getLocalTranslation().getZ() - o2.getLocalTranslation().getZ()) * this.mult - o1.getLocalTranslation().getX() - o2.getLocalTranslation().getX());
/*    */   }
/*    */ 
/*    */   
/*    */   public static PropNode getTopProp(Set<PropNode> props) {
/* 29 */     if (props == null || props.isEmpty()) {
/* 30 */       return null;
/*    */     }
/* 32 */     List<PropNode> result = new LinkedList<PropNode>(props);
/* 33 */     Collections.sort(result, new SpatialZComparator(true));
/* 34 */     return result.get(0);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengin\\utils\SpatialZComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */