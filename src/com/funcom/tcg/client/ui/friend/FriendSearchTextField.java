/*    */ package com.funcom.tcg.client.ui.friend;
/*    */ 
/*    */ import com.funcom.tcg.client.ui.startmenu.MessagedBTextField;
/*    */ import com.jmex.bui.event.BEvent;
/*    */ import com.jmex.bui.event.ComponentListener;
/*    */ import com.jmex.bui.event.KeyEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FriendSearchTextField
/*    */   extends MessagedBTextField
/*    */ {
/*    */   public FriendSearchTextField(MessagedBTextField.CharValidator validator) {
/* 16 */     super(validator);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean dispatchEvent(BEvent event) {
/* 21 */     if (event instanceof KeyEvent && 
/* 22 */       this._listeners != null) {
/* 23 */       for (ComponentListener listener : this._listeners) {
/* 24 */         if (listener instanceof KeyTypedListener) {
/* 25 */           ((KeyTypedListener)listener).keyPressed((KeyEvent)event);
/*    */         }
/*    */       } 
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 32 */     return super.dispatchEvent(event);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\friend\FriendSearchTextField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */