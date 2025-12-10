/*    */ package com.funcom.tcg.client.errorhandling;
/*    */ 
/*    */ import com.funcom.errorhandling.AbstractCrashDataProvider;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldCoordinateCrashDataProvider
/*    */   extends AbstractCrashDataProvider
/*    */ {
/*    */   public String getName() {
/* 13 */     return "WorldCoordinate";
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getValue() {
/* 18 */     if (MainGameState.isStateInitialized() && MainGameState.getPlayerModel() != null) {
/* 19 */       return MainGameState.getPlayerModel().getPosition();
/*    */     }
/* 21 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\errorhandling\WorldCoordinateCrashDataProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */