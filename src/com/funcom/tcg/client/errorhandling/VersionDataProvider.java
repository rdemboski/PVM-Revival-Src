/*    */ package com.funcom.tcg.client.errorhandling;
/*    */ 
/*    */ import com.funcom.errorhandling.AbstractCrashDataProvider;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ 
/*    */ public class VersionDataProvider
/*    */   extends AbstractCrashDataProvider {
/*    */   public String getName() {
/*  9 */     return "version";
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getValue() {
/* 14 */     return TcgGame.getVersionText();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\errorhandling\VersionDataProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */