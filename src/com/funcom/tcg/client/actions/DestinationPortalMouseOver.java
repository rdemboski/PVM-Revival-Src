/*    */ package com.funcom.tcg.client.actions;
/*    */ 
/*    */ import com.funcom.gameengine.model.action.Action;
/*    */ import com.funcom.gameengine.model.input.MouseCursorSetter;
/*    */ import com.funcom.gameengine.model.input.MouseOver;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DestinationPortalMouseOver
/*    */   extends MouseOver
/*    */ {
/*    */   private final MouseCursorSetter cursorSetter;
/*    */   private final InteractibleProp interactibleProp;
/*    */   private final Map<String, String> mapping;
/*    */   
/*    */   public DestinationPortalMouseOver(MouseCursorSetter cursorSetter, InteractibleProp interactibleProp, Map<String, String> mapping) {
/* 22 */     this.interactibleProp = interactibleProp;
/* 23 */     this.mapping = mapping;
/* 24 */     this.cursorSetter = cursorSetter;
/*    */   }
/*    */   
/*    */   public void mouseEntered() {
/* 28 */     Action action = this.interactibleProp.getActions().iterator().next();
/* 29 */     if (action == null) {
/* 30 */       throw new IllegalArgumentException("action = null. InteractibleProp without actions instantiated (why not just using Prop?");
/*    */     }
/* 32 */     String value = this.mapping.get(action.getName());
/* 33 */     if (value != null)
/* 34 */       this.cursorSetter.setCursor(value); 
/*    */   }
/*    */   
/*    */   public void mouseExited() {
/* 38 */     this.cursorSetter.setDefaultCursor();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\actions\DestinationPortalMouseOver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */