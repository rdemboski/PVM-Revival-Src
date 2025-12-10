/*    */ package com.funcom.tcg.client.net.creaturebuilders;
/*    */ 
/*    */ import com.funcom.gameengine.model.input.Cursor;
/*    */ import com.funcom.gameengine.model.input.MouseCursorSetter;
/*    */ import com.funcom.gameengine.model.input.TintMouseOver;
/*    */ import com.funcom.gameengine.view.Effects;
/*    */ import com.funcom.tcg.client.state.TCGCursorSetter;
/*    */ import com.jme.renderer.ColorRGBA;
/*    */ 
/*    */ public class QuestInteractibleMouseOver
/*    */   extends TintMouseOver
/*    */ {
/*    */   private MouseCursorSetter cursorSetter;
/*    */   
/*    */   public QuestInteractibleMouseOver(ColorRGBA tintColor, Effects.TintMode tintMode, MouseCursorSetter cursorSetter) {
/* 16 */     super(tintColor, tintMode);
/* 17 */     this.cursorSetter = cursorSetter;
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseEntered() {
/* 22 */     super.mouseEntered();
/*    */     
/* 24 */     this.cursorSetter.setCursor((Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_QUEST_INTERACT);
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseExited() {
/* 29 */     super.mouseExited();
/*    */     
/* 31 */     this.cursorSetter.setDefaultCursor();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\creaturebuilders\QuestInteractibleMouseOver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */