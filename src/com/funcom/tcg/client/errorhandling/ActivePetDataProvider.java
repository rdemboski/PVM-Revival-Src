/*    */ package com.funcom.tcg.client.errorhandling;
/*    */ 
/*    */ import com.funcom.errorhandling.AbstractCrashDataProvider;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ 
/*    */ 
/*    */ public class ActivePetDataProvider
/*    */   extends AbstractCrashDataProvider
/*    */ {
/*    */   public String getName() {
/* 11 */     return "ActivePet";
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getValue() {
/* 16 */     if (MainGameState.isStateInitialized() && MainGameState.getPlayerModel() != null) {
/* 17 */       return MainGameState.getPlayerModel().getActivePet();
/*    */     }
/* 19 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\errorhandling\ActivePetDataProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */