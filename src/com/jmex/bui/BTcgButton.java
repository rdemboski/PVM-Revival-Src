/*     */ package com.jmex.bui;
/*     */ 
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.BEvent;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ 
/*     */ 
/*     */ public abstract class BTcgButton
/*     */   extends BContainer
/*     */ {
/*     */   public static final int DOWN = 3;
/*     */   protected static final int STATE_COUNT = 4;
/*  13 */   protected static final String[] STATE_PCLASSES = new String[] { "down" };
/*     */   
/*     */   protected boolean armed;
/*     */   
/*     */   protected boolean pressed;
/*     */   protected String action;
/*     */   
/*     */   public int getState() {
/*  21 */     int state = super.getState();
/*  22 */     if (state == 2) {
/*  23 */       return state;
/*     */     }
/*     */     
/*  26 */     if (this.armed && this.pressed) {
/*  27 */       return 3;
/*     */     }
/*  29 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean dispatchEvent(BEvent event) {
/*  36 */     if (isEnabled() && event instanceof MouseEvent) {
/*  37 */       int ostate = getState();
/*  38 */       MouseEvent mev = (MouseEvent)event;
/*  39 */       switch (mev.getType()) {
/*     */         case 2:
/*  41 */           return mouseEntered(mev);
/*     */         
/*     */         case 3:
/*  44 */           return mouseExited(mev);
/*     */         
/*     */         case 0:
/*  47 */           mousePressed(mev);
/*     */           break;
/*     */         
/*     */         case 1:
/*  51 */           mouseReleased(mev);
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  59 */       int state = getState();
/*  60 */       if (state != ostate) {
/*  61 */         stateDidChange();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  67 */     return super.dispatchEvent(event);
/*     */   }
/*     */   
/*     */   public boolean dispatchEventSuper(BEvent event) {
/*  71 */     return super.dispatchEvent(event);
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void mouseReleased(MouseEvent paramMouseEvent);
/*     */ 
/*     */   
/*     */   protected abstract void mousePressed(MouseEvent paramMouseEvent);
/*     */ 
/*     */   
/*     */   protected abstract boolean mouseExited(MouseEvent paramMouseEvent);
/*     */ 
/*     */   
/*     */   protected abstract boolean mouseEntered(MouseEvent paramMouseEvent);
/*     */ 
/*     */   
/*     */   public void setAction(String action) {
/*  88 */     this.action = action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAction() {
/*  97 */     return this.action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fireAction(long when, int modifiers) {
/* 108 */     emitEvent((BEvent)new ActionEvent(this, when, modifiers, this.action));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getStateCount() {
/* 114 */     return 4;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStatePseudoClass(int state) {
/* 120 */     if (state >= 3) {
/* 121 */       return STATE_PCLASSES[state - 3];
/*     */     }
/* 123 */     return super.getStatePseudoClass(state);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BTcgButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */