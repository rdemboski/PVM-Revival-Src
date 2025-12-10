/*    */ package com.funcom.gameengine.model.input;
/*    */ 
/*    */ import com.funcom.gameengine.model.action.Action;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActionDependentMouseOver
/*    */   extends MouseOver
/*    */ {
/*    */   private MouseCursorSetter cursorSetter;
/*    */   private InteractibleProp interactibleProp;
/*    */   private Map<String, String> mapping;
/*    */   
/*    */   public ActionDependentMouseOver(MouseCursorSetter cursorSetter, InteractibleProp interactibleProp, Map<String, String> mapping) {
/* 24 */     this.interactibleProp = interactibleProp;
/* 25 */     this.mapping = mapping;
/* 26 */     this.cursorSetter = cursorSetter;
/*    */   }
/*    */   
/*    */   public void mouseEntered() {
/* 30 */     Action action = this.interactibleProp.getActions().iterator().next();
/* 31 */     if (action == null) {
/* 32 */       throw new IllegalArgumentException("action = null. InteractibleProp without actions instantiated (why not just using Prop?");
/*    */     }
/* 34 */     String value = this.mapping.get(action.getName());
/* 35 */     if (value != null)
/* 36 */       this.cursorSetter.setCursor(value); 
/*    */   }
/*    */   
/*    */   public void mouseExited() {
/* 40 */     this.cursorSetter.setDefaultCursor();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\input\ActionDependentMouseOver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */