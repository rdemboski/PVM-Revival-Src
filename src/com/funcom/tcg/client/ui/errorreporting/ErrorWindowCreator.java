/*    */ package com.funcom.tcg.client.ui.errorreporting;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.CacheType;
/*    */ import com.funcom.peeler.BananaPeel;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.jmex.bui.BWindow;
/*    */ import com.jmex.bui.BuiSystem;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ErrorWindowCreator
/*    */ {
/*    */   private static ErrorWindowCreator INSTANCE;
/* 15 */   private ArrayList<String> iconErrors = new ArrayList<String>();
/*    */   
/*    */   public static ErrorWindowCreator instance() {
/* 18 */     if (INSTANCE == null) {
/* 19 */       INSTANCE = new ErrorWindowCreator();
/*    */     }
/* 21 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public void createIconErrorMessage(String title, String errorMessage) {
/* 25 */     if (!this.iconErrors.contains(errorMessage)) {
/* 26 */       createErrorMessage(title, errorMessage);
/* 27 */       this.iconErrors.add(errorMessage);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void createErrorMessage(String title, String errorMessage) {
/* 32 */     String infoWindowPath = "gui/peeler/window_tutorial_popup.xml";
/* 33 */     BananaPeel bananaPeel = (BananaPeel)TcgGame.getResourceManager().getResource(BananaPeel.class, infoWindowPath, CacheType.NOT_CACHED);
/* 34 */     ErrorWindow errorPopUp = new ErrorWindow(title, errorMessage, bananaPeel, TcgGame.getResourceManager());
/* 35 */     popUpWindow(errorPopUp);
/*    */   }
/*    */   
/*    */   private void popUpWindow(ErrorWindow errorPopUp) {
/* 39 */     errorPopUp.setSize(400, 250);
/* 40 */     errorPopUp.center();
/* 41 */     BuiSystem.addWindow((BWindow)errorPopUp);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\errorreporting\ErrorWindowCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */