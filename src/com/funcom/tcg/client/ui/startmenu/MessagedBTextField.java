/*     */ package com.funcom.tcg.client.ui.startmenu;
/*     */ 
/*     */ import com.funcom.gameengine.model.input.Cursor;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.state.TCGCursorSetter;
/*     */ import com.jmex.bui.BTextField;
/*     */ import com.jmex.bui.event.BEvent;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.event.KeyEvent;
/*     */ import com.jmex.bui.event.MouseAdapter;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ import com.jmex.bui.text.Document;
/*     */ 
/*     */ public class MessagedBTextField
/*     */   extends BTextField
/*     */ {
/*  17 */   private static final Document EMPTY_REPRESENTATION = new Document(); static {
/*  18 */     EMPTY_REPRESENTATION.insert(0, " ");
/*     */   }
/*     */   
/*  21 */   private String emptyDisplayText = "";
/*     */ 
/*     */   
/*     */   private boolean hasFocus;
/*     */ 
/*     */   
/*     */   private final CharValidator charValidator;
/*     */ 
/*     */   
/*     */   public MessagedBTextField() {
/*  31 */     this((CharValidator)null);
/*     */   }
/*     */   
/*     */   public MessagedBTextField(CharValidator charValidator) {
/*  35 */     this.charValidator = charValidator;
/*  36 */     initListeners();
/*     */   }
/*     */   
/*     */   private void initListeners() {
/*  40 */     addListener((ComponentListener)new MouseAdapter()
/*     */         {
/*     */           public void mouseEntered(MouseEvent event) {
/*  43 */             super.mouseEntered(event);
/*  44 */             MainGameState.getMouseCursorSetter().setCursor((Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_TEXT);
/*     */           }
/*     */ 
/*     */           
/*     */           public void mouseExited(MouseEvent event) {
/*  49 */             super.mouseExited(event);
/*  50 */             MainGameState.getMouseCursorSetter().setCursor((Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_WALK);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public String getEmptyDisplayText() {
/*  56 */     return this.emptyDisplayText;
/*     */   } public static interface CharValidator {
/*     */     boolean isValid(char param1Char); }
/*     */   public void setEmptyDisplayText(String emptyDisplayText) {
/*  60 */     this.emptyDisplayText = emptyDisplayText;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void recreateGlyphs() {
/*  66 */     Document old = null;
/*  67 */     if (this._text.getLength() == 0) {
/*  68 */       old = this._text;
/*  69 */       this._text = EMPTY_REPRESENTATION;
/*     */     } 
/*     */     
/*  72 */     if (this._text.getText() == null) {
/*  73 */       this._text.setText(" ");
/*     */     }
/*  75 */     super.recreateGlyphs();
/*     */     
/*  77 */     if (old != null) {
/*  78 */       this._text = old;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void gainedFocus() {
/*  84 */     this.hasFocus = true;
/*  85 */     super.gainedFocus();
/*  86 */     recreateGlyphs();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void lostFocus() {
/*  91 */     this.hasFocus = false;
/*  92 */     super.lostFocus();
/*  93 */     recreateGlyphs();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDisplayText() {
/*  98 */     if (!this.hasFocus && this._text == EMPTY_REPRESENTATION) {
/*  99 */       return this.emptyDisplayText;
/*     */     }
/* 101 */     return super.getDisplayText();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean dispatchEvent(BEvent event) {
/* 106 */     if (event instanceof KeyEvent) {
/* 107 */       KeyEvent kev = (KeyEvent)event;
/*     */       
/* 109 */       int modifiers = kev.getModifiers();
/*     */       
/* 111 */       if (kev.getType() == 0) {
/* 112 */         int keyCode = kev.getKeyCode();
/* 113 */         switch (this._keymap.lookupMapping(modifiers, keyCode)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case 0:
/*     */           case 1:
/*     */           case 2:
/*     */           case 3:
/*     */           case 4:
/*     */           case 5:
/*     */           case 6:
/*     */           case 7:
/*     */           case 8:
/* 154 */             return super.dispatchEvent(event);
/*     */         } 
/*     */         char c = kev.getKeyChar();
/*     */         if (this.charValidator != null) {
/*     */           if (this.charValidator.isValid(c)) {
/*     */             if (c == '@') {
/*     */               String text = String.valueOf(c);
/*     */               if (this._text.insert(this._cursp, text))
/*     */                 setCursorPos(this._cursp + 1); 
/*     */               return true;
/*     */             } 
/*     */             return super.dispatchEvent(event);
/*     */           } 
/*     */           if (c != '\t')
/*     */             return true; 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */
return hasFocus;   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\startmenu\MessagedBTextField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */