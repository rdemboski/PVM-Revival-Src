/*    */ package com.funcom.gameengine.input;
/*    */ 
/*    */ import com.funcom.gameengine.Updated;
/*    */ import com.jme.input.controls.GameControl;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ public abstract class ButtonStateTracker
/*    */   implements Updated
/*    */ {
/*    */   private Set<GameControl> pastDown;
/*    */   private Set<GameControl> trackedControls;
/*    */   
/*    */   public ButtonStateTracker(GameControl[] gameControls) {
/* 16 */     this.pastDown = new HashSet<GameControl>();
/* 17 */     this.trackedControls = new HashSet<GameControl>(Arrays.asList(gameControls));
/*    */   }
/*    */   
/*    */   public void update(float time) {
/* 21 */     for (GameControl gameControl : this.trackedControls) {
/* 22 */       if (isDown(gameControl) && !wasDown(gameControl)) {
/* 23 */         pressed(gameControl); continue;
/* 24 */       }  if (!isDown(gameControl) && wasDown(gameControl))
/* 25 */         released(gameControl); 
/*    */     } 
/* 27 */     rememberWhoisDown();
/*    */   }
/*    */   
/*    */   private void rememberWhoisDown() {
/* 31 */     this.pastDown.clear();
/* 32 */     for (GameControl gameControl : this.trackedControls) {
/* 33 */       if (isDown(gameControl)) {
/* 34 */         this.pastDown.add(gameControl);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void pressed(GameControl gameControl) {}
/*    */   
/*    */   protected void released(GameControl gameControl) {}
/*    */   
/*    */   private boolean wasDown(GameControl gameControl) {
/* 44 */     return this.pastDown.contains(gameControl);
/*    */   }
/*    */   
/*    */   private boolean isDown(GameControl gameControl) {
/* 48 */     return (gameControl.getValue() != 0.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\input\ButtonStateTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */