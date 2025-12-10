/*    */ package com.funcom.gameengine.model.action;
/*    */ 
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public abstract class AbstractAction
/*    */   implements Action {
/*  8 */   protected static final Logger LOGGER = Logger.getLogger(Action.class.getName());
/*    */   private InteractibleProp parent;
/*    */   
/*    */   public void setParent(InteractibleProp parent) {
/* 12 */     this.parent = parent;
/*    */   }
/*    */   
/*    */   public InteractibleProp getParent() {
/* 16 */     return this.parent;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isClickable() {
/* 21 */     return true;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 25 */     return getClass().getSimpleName();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\action\AbstractAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */