/*    */ package com.funcom.tcg.client.ui.startmenu;
/*    */ 
/*    */ import com.funcom.gameengine.model.input.Cursor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.state.TCGCursorSetter;
/*    */ import com.jmex.bui.BPasswordField;
/*    */ import com.jmex.bui.event.ComponentListener;
/*    */ import com.jmex.bui.event.MouseAdapter;
/*    */ import com.jmex.bui.event.MouseEvent;
/*    */ 
/*    */ public class HoverPasswordField extends BPasswordField {
/*    */   public HoverPasswordField() {
/* 13 */     initListeners();
/*    */   }
/*    */   
/*    */   public HoverPasswordField(int maxLength) {
/* 17 */     super(maxLength);
/* 18 */     initListeners();
/*    */   }
/*    */   
/*    */   public HoverPasswordField(String text) {
/* 22 */     super(text);
/* 23 */     initListeners();
/*    */   }
/*    */   
/*    */   public HoverPasswordField(String text, int maxLength) {
/* 27 */     super(text, maxLength);
/* 28 */     initListeners();
/*    */   }
/*    */   
/*    */   private void initListeners() {
/* 32 */     addListener((ComponentListener)new MouseAdapter()
/*    */         {
/*    */           public void mouseEntered(MouseEvent event) {
/* 35 */             super.mouseEntered(event);
/* 36 */             MainGameState.getMouseCursorSetter().setCursor((Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_TEXT);
/*    */           }
/*    */ 
/*    */           
/*    */           public void mouseExited(MouseEvent event) {
/* 41 */             super.mouseExited(event);
/* 42 */             MainGameState.getMouseCursorSetter().setCursor((Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_WALK);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\startmenu\HoverPasswordField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */