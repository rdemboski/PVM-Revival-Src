/*    */ package com.funcom.tcg.client.actions;
/*    */ 
/*    */ import com.funcom.gameengine.model.action.AbstractAction;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TalkAction
/*    */   extends AbstractAction
/*    */ {
/*    */   public static final String ACTION_NAME = "talk";
/*    */   
/*    */   public void perform(InteractibleProp invoker) {}
/*    */   
/*    */   public boolean isClickable() {
/* 20 */     return false;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 24 */     return "talk";
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\actions\TalkAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */