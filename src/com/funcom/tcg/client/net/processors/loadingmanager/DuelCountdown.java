/*    */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*    */ 
/*    */ import com.funcom.gameengine.Updated;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.duel.DuelHealthBarWindow;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DuelCountdown
/*    */   implements Updated
/*    */ {
/* 15 */   private float timeLeft = 5.0F;
/*    */   private boolean lessThan4;
/*    */   private boolean lessThan3;
/*    */   
/*    */   public void update(float time) {
/* 20 */     this.timeLeft -= time;
/* 21 */     DuelHealthBarWindow duelHealthBarWindow = MainGameState.getDuelHealthBarWindow();
/* 22 */     if (!this.lessThan4 && this.timeLeft <= 4.0F) {
/* 23 */       this.lessThan4 = true;
/* 24 */       if (duelHealthBarWindow != null)
/* 25 */         duelHealthBarWindow.updateCountdown(4); 
/* 26 */     } else if (!this.lessThan3 && this.timeLeft <= 3.0F) {
/* 27 */       this.lessThan3 = true;
/* 28 */       if (duelHealthBarWindow != null)
/* 29 */         duelHealthBarWindow.updateCountdown(3); 
/* 30 */     } else if (!this.lessThan2 && this.timeLeft <= 2.0F) {
/* 31 */       this.lessThan2 = true;
/* 32 */       if (duelHealthBarWindow != null)
/* 33 */         duelHealthBarWindow.updateCountdown(2); 
/* 34 */     } else if (!this.lessThan1 && this.timeLeft <= 1.0F) {
/* 35 */       this.lessThan1 = true;
/* 36 */       if (duelHealthBarWindow != null)
/* 37 */         duelHealthBarWindow.updateCountdown(1); 
/* 38 */     } else if (!this.lessThan0 && this.timeLeft <= 0.0F) {
/* 39 */       this.lessThan0 = true;
/* 40 */       if (duelHealthBarWindow != null)
/* 41 */         duelHealthBarWindow.updateCountdown(0); 
/* 42 */     } else if (this.lessThan0 && this.timeLeft <= -1.0F) {
/* 43 */       if (duelHealthBarWindow != null)
/* 44 */         duelHealthBarWindow.updateCountdown(-1); 
/* 45 */       MainGameState.getInstance().removeFromUpdateList(this);
/*    */     } 
/*    */   }
/*    */   
/*    */   private boolean lessThan2;
/*    */   private boolean lessThan1;
/*    */   private boolean lessThan0;
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\DuelCountdown.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */