/*    */ package com.funcom.errorhandling;
/*    */ 
/*    */ public class DefaultDataFeeder
/*    */   implements AchaDoomsdayErrorHandler.AchaBugreportDataFeeder
/*    */ {
/*    */   private String username;
/*    */   private String email;
/*    */   private String universe;
/*    */   
/*    */   public DefaultDataFeeder() {
/* 11 */     this("NO_USERNAME", "NO_EMAIL", "NO_UNIVERSE");
/*    */   }
/*    */   
/*    */   public DefaultDataFeeder(String username, String email, String universe) {
/* 15 */     this.username = username;
/* 16 */     this.email = email;
/* 17 */     this.universe = universe;
/*    */   }
/*    */   
/*    */   public String getUsername() {
/* 21 */     return this.username;
/*    */   }
/*    */   
/*    */   public String getEmail() {
/* 25 */     return this.email;
/*    */   }
/*    */   
/*    */   public String getUniverse() {
/* 29 */     return this.universe;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\errorhandling\DefaultDataFeeder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */