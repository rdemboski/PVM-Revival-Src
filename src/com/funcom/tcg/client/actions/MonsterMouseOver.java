/*    */ package com.funcom.tcg.client.actions;
/*    */ 
/*    */ import com.funcom.gameengine.model.input.Cursor;
/*    */ import com.funcom.gameengine.model.input.MouseCursorSetter;
/*    */ import com.funcom.gameengine.model.input.MouseOver;
/*    */ import com.funcom.gameengine.view.Effects;
/*    */ import com.funcom.tcg.client.state.TCGCursorSetter;
/*    */ import com.jme.renderer.ColorRGBA;
/*    */ 
/*    */ public class MonsterMouseOver
/*    */   extends MouseOver
/*    */ {
/*    */   private MouseCursorSetter cursorSetter;
/*    */   private ColorRGBA tintColor;
/*    */   
/*    */   public MonsterMouseOver(MouseCursorSetter cursorSetter, ColorRGBA tintColor) {
/* 17 */     this.cursorSetter = cursorSetter;
/* 18 */     this.tintColor = tintColor;
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseEntered() {
/* 23 */     Effects propNodeEffects = getOwnerPropNode().getEffects();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 28 */     this.cursorSetter.setCursor((Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_ATTACK);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void mouseExited() {
/* 35 */     this.cursorSetter.setDefaultCursor();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\actions\MonsterMouseOver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */