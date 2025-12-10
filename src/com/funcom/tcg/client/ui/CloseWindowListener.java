/*    */ package com.funcom.tcg.client.ui;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import com.jmex.bui.BWindow;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CloseWindowListener
/*    */   extends GeneralCloseWindowListener
/*    */ {
/*    */   private InteractibleProp associatedProp;
/*    */   
/*    */   public CloseWindowListener(InteractibleProp associatedProp, Class<? extends BWindow> windowClass) {
/* 16 */     super(null, windowClass);
/* 17 */     this.associatedProp = associatedProp;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void removeListener() {
/* 22 */     this.associatedProp.removeUpdateListener(this);
/*    */   }
/*    */ 
/*    */   
/*    */   protected WorldCoordinate getCoord() {
/* 27 */     return this.associatedProp.getPosition();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\CloseWindowListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */