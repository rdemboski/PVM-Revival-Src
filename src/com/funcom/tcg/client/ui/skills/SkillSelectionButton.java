/*    */ package com.funcom.tcg.client.ui.skills;
/*    */ 
/*    */ import com.funcom.gameengine.jme.text.HTMLView2;
/*    */ import com.funcom.gameengine.model.input.Cursor;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.state.TCGCursorSetter;
/*    */ import com.funcom.tcg.client.ui.TcgUI;
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BCustomButton;
/*    */ import com.jmex.bui.BLabel;
/*    */ import com.jmex.bui.dragndrop.BDragNDrop;
/*    */ import com.jmex.bui.icon.BIcon;
/*    */ import com.jmex.bui.icon.BlankIcon;
/*    */ import com.jmex.bui.layout.BLayoutManager;
/*    */ import com.jmex.bui.layout.StackedLayout;
/*    */ 
/*    */ public class SkillSelectionButton
/*    */   extends BCustomButton {
/* 20 */   private static final BIcon blankIcon = (BIcon)new BlankIcon(48, 48);
/*    */   private BLabel skillLabel;
/*    */   private SkillListItem skillListItem;
/*    */   
/*    */   public SkillSelectionButton() {
/* 25 */     this.skillLabel = new BLabel("");
/* 26 */     this.skillLabel.setStyleClass(getDefaultStyleClass());
/* 27 */     BIcon shineIcon = TcgUI.getIconProvider().getIconShine();
/* 28 */     BLabel shineLabel = new BLabel(shineIcon);
/* 29 */     shineLabel.setStyleClass(getDefaultStyleClass() + ".shine");
/* 30 */     setLayoutManager((BLayoutManager)new StackedLayout());
/* 31 */     add((BComponent)this.skillLabel);
/* 32 */     add((BComponent)shineLabel);
/*    */   }
/*    */   
/*    */   public SkillSelectionButton(SkillListItem skillListItem) {
/* 36 */     this();
/* 37 */     setSkillListItem(skillListItem);
/*    */   }
/*    */   
/*    */   protected void stateDidChange() {
/* 41 */     if (getSkillListItem() == null || BDragNDrop.instance().isDragging()) {
/*    */       return;
/*    */     }
/* 44 */     super.stateDidChange();
/* 45 */     switch (getState()) {
/*    */       case 1:
/* 47 */         MainGameState.getMouseCursorSetter().setCursor((Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_USE);
/*    */         return;
/*    */       case 3:
/* 50 */         MainGameState.getMouseCursorSetter().setCursor((Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_USE);
/*    */         return;
/*    */     } 
/* 53 */     MainGameState.getMouseCursorSetter().setDefaultCursor();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setSkillListItem(SkillListItem skillListItem) {
/* 58 */     BIcon icon = blankIcon;
/* 59 */     this.skillListItem = skillListItem;
/* 60 */     if (skillListItem != null) {
/* 61 */       icon = TcgUI.getIconProvider().getIconForSkill(skillListItem);
/*    */     }
/* 63 */     this.skillLabel.setIcon(icon);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getDefaultStyleClass() {
/* 68 */     return "skillselectionbutton";
/*    */   }
/*    */   
/*    */   public SkillListItem getSkillListItem() {
/* 72 */     return this.skillListItem;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected BComponent createTooltipComponent(String tiptext) {
/* 83 */     return (BComponent)new HTMLView2(tiptext, TcgGame.getResourceManager());
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\skills\SkillSelectionButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */