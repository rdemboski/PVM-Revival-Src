/*    */ package com.funcom.tcg.client.errorhandling;
/*    */ 
/*    */ import com.funcom.errorhandling.AbstractCrashDataProvider;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PetSlotsDataProvider
/*    */   extends AbstractCrashDataProvider
/*    */ {
/*    */   public String getName() {
/* 13 */     return "PetSlots";
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getValue() {
/* 18 */     if (MainGameState.isStateInitialized() && MainGameState.getPlayerModel() != null) {
/* 19 */       return MainGameState.getPlayerModel().getPetSlots();
/*    */     }
/* 21 */     return null;
/*    */   }
/*    */   
/*    */   public String getValueAsString() {
/* 25 */     return (getValue() != null) ? Arrays.toString((Object[])MainGameState.getPlayerModel().getPetSlots()) : "null";
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\errorhandling\PetSlotsDataProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */