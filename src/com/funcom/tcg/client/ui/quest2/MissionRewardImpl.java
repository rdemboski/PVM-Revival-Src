/*    */ package com.funcom.tcg.client.ui.quest2;
/*    */ 
/*    */ 
/*    */ public class MissionRewardImpl
/*    */   implements MissionReward
/*    */ {
/*    */   private String iconPath;
/*    */   private int number;
/*    */   
/*    */   public MissionRewardImpl(String iconPath, int number) {
/* 11 */     this.iconPath = iconPath;
/* 12 */     this.number = number;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIconPath() {
/* 17 */     return this.iconPath;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getNumber() {
/* 22 */     return this.number;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\quest2\MissionRewardImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */