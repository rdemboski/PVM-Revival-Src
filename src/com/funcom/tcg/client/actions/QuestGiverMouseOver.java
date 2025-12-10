/*    */ package com.funcom.tcg.client.actions;
/*    */ 
/*    */ import com.funcom.gameengine.model.input.Cursor;
/*    */ import com.funcom.gameengine.model.input.MouseCursorSetter;
/*    */ import com.funcom.gameengine.model.input.MouseOver;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import com.funcom.gameengine.view.Effects;
/*    */ import com.funcom.tcg.client.state.TCGCursorSetter;
/*    */ import com.jme.renderer.ColorRGBA;
/*    */ 
/*    */ 
/*    */ public class QuestGiverMouseOver
/*    */   extends MouseOver
/*    */ {
/*    */   private MouseCursorSetter cursorSetter;
/*    */   private QuestWindowAction questWindowAction;
/*    */   private ColorRGBA tintColor;
/*    */   
/*    */   public QuestGiverMouseOver(MouseCursorSetter cursorSetter, InteractibleProp monster, ColorRGBA tintColor) {
/* 20 */     if (monster.hasAction(QuestWindowAction.class))
/* 21 */       this.questWindowAction = (QuestWindowAction)monster.getActionByClass(QuestWindowAction.class); 
/* 22 */     this.cursorSetter = cursorSetter;
/* 23 */     this.tintColor = tintColor;
/*    */   }
/*    */   
/*    */   public void mouseEntered() {
/* 27 */     if (this.questWindowAction == null) {
/*    */       return;
/*    */     }
/* 30 */     Effects propNodeEffects = getOwnerPropNode().getEffects();
/* 31 */     propNodeEffects.setTintRbga(this.tintColor);
/* 32 */     propNodeEffects.tint(Effects.TintMode.ADDITIVE);
/*    */     
/* 34 */     if (this.questWindowAction.getQuestCompletion() == 0)
/* 35 */       this.cursorSetter.setCursor((Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_QUEST_NOT_STARTED); 
/* 36 */     if (this.questWindowAction.getQuestCompletion() == 2)
/* 37 */       this.cursorSetter.setCursor((Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_QUEST_COMPLETE); 
/*    */   }
/*    */   
/*    */   public void mouseExited() {
/* 41 */     getOwnerPropNode().getEffects().tint(Effects.TintMode.OFF);
/* 42 */     this.cursorSetter.setDefaultCursor();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\actions\QuestGiverMouseOver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */