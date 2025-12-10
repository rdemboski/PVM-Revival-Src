/*    */ package com.funcom.gameengine.model;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ 
/*    */ public class QuestGoToProp extends InteractibleProp {
/*    */   private boolean show = false;
/*    */   
/*    */   public QuestGoToProp(int id, String name, WorldCoordinate worldCoordinate) {
/* 10 */     super(id, name, worldCoordinate, 0.0D);
/*    */   }
/*    */   
/*    */   public boolean isShow() {
/* 14 */     return this.show;
/*    */   }
/*    */   
/*    */   public void setShow(boolean show) {
/* 18 */     this.show = show;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\QuestGoToProp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */