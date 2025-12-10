/*    */ package com.funcom.tcg.client.actions;
/*    */ 
/*    */ import com.funcom.gameengine.model.input.Cursor;
/*    */ import com.funcom.gameengine.model.input.MouseCursorSetter;
/*    */ import com.funcom.gameengine.model.input.MouseOver;
/*    */ import com.funcom.gameengine.view.Effects;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.state.TCGCursorSetter;
/*    */ import com.funcom.tcg.client.ui.friend.FriendModeType;
/*    */ import com.jme.renderer.ColorRGBA;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerMouseOver
/*    */   extends MouseOver
/*    */ {
/*    */   private MouseCursorSetter cursorSetter;
/*    */   private ColorRGBA tintColor;
/*    */   
/*    */   public PlayerMouseOver(MouseCursorSetter cursorSetter, ColorRGBA tintColor) {
/* 24 */     this.cursorSetter = cursorSetter;
/* 25 */     this.tintColor = tintColor;
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseEntered() {
/* 30 */     Effects propNodeEffects = getOwnerPropNode().getEffects();
/* 31 */     if (TcgGame.getAddFriendMode().equals(FriendModeType.FRIEND)) {
/* 32 */       propNodeEffects.setTintRbga(this.tintColor);
/* 33 */       propNodeEffects.tint(Effects.TintMode.ADDITIVE);
/* 34 */       MainGameState.getMouseCursorSetter().setCursor((Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_ADD_FRIEND);
/* 35 */     } else if (TcgGame.getAddFriendMode().equals(FriendModeType.BLOCKED)) {
/* 36 */       propNodeEffects.setTintRbga(this.tintColor);
/* 37 */       propNodeEffects.tint(Effects.TintMode.ADDITIVE);
/* 38 */       MainGameState.getMouseCursorSetter().setCursor((Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_BLOCK_PLAYER);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseExited() {
/* 44 */     getOwnerPropNode().getEffects().tint(Effects.TintMode.OFF);
/* 45 */     this.cursorSetter.setDefaultCursor();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\actions\PlayerMouseOver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */