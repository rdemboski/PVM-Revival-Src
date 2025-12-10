/*    */ package com.funcom.tcg.client.ui;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import com.funcom.gameengine.model.props.UpdateListener;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*    */ import com.jmex.bui.BWindow;
/*    */ 
/*    */ 
/*    */ public class GeneralCloseWindowListener
/*    */   implements UpdateListener
/*    */ {
/*    */   private WorldCoordinate wc;
/*    */   private Class<? extends BWindow> windowClass;
/*    */   
/*    */   public GeneralCloseWindowListener(WorldCoordinate wc, Class<? extends BWindow> windowClass) {
/* 18 */     this.wc = wc;
/* 19 */     this.windowClass = windowClass;
/*    */   }
/*    */   
/*    */   public void updated() {
/* 23 */     if (getCoord().distanceTo(MainGameState.getPlayerNode().getPosition()) > MainGameState.getMaxInteractionDistance()) {
/* 24 */       removeListener();
/* 25 */       BWindow bWindow = TcgUI.getWindowFromClass(this.windowClass);
/* 26 */       if (bWindow != null)
/* 27 */         PanelManager.getInstance().closeWindow(bWindow); 
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void removeListener() {
/* 32 */     ((InteractibleProp)MainGameState.getPlayerNode().getProp()).removeUpdateListener(this);
/*    */   }
/*    */   
/*    */   protected WorldCoordinate getCoord() {
/* 36 */     return this.wc;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\GeneralCloseWindowListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */