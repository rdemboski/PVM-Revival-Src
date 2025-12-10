/*     */ package com.jmex.bui;
/*     */ 
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.BEvent;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ import com.jmex.bui.icon.BIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BButton2
/*     */   extends BLabel
/*     */   implements BConstants
/*     */ {
/*     */   public static final int DOWN = 3;
/*     */   protected boolean _armed;
/*     */   protected boolean _pressed;
/*     */   protected String _action;
/*     */   protected static final int STATE_COUNT = 4;
/*     */   
/*     */   public BButton2(String text) {
/*  26 */     this(text, "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BButton2(String text, String action) {
/*  38 */     this(text, (ActionListener)null, action);
/*     */   }
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
/*     */   public BButton2(String text, ActionListener listener, String action) {
/*  52 */     super(text);
/*  53 */     this._action = action;
/*  54 */     if (listener != null) {
/*  55 */       addListener((ComponentListener)listener);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BButton2(BIcon icon, String action) {
/*  68 */     this(icon, (ActionListener)null, action);
/*     */   }
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
/*     */   public BButton2(BIcon icon, ActionListener listener, String action) {
/*  82 */     super(icon);
/*  83 */     this._action = action;
/*  84 */     if (listener != null) {
/*  85 */       addListener((ComponentListener)listener);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAction(String action) {
/*  95 */     this._action = action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAction() {
/* 104 */     return this._action;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getState() {
/* 110 */     int state = super.getState();
/* 111 */     if (state == 2) {
/* 112 */       return state;
/*     */     }
/*     */     
/* 115 */     if (this._armed && this._pressed) {
/* 116 */       return 3;
/*     */     }
/* 118 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean dispatchEvent(BEvent event) {
/* 125 */     if (isEnabled() && event instanceof MouseEvent) {
/* 126 */       int ostate = getState();
/* 127 */       MouseEvent mev = (MouseEvent)event;
/* 128 */       switch (mev.getType()) {
/*     */         case 2:
/* 130 */           this._armed = this._pressed;
/*     */           
/* 132 */           return super.dispatchEvent(event);
/*     */         
/*     */         case 3:
/* 135 */           this._armed = false;
/*     */           
/* 137 */           return super.dispatchEvent(event);
/*     */         
/*     */         case 0:
/* 140 */           if (mev.getButton() == 0) {
/* 141 */             this._pressed = true;
/* 142 */             this._armed = true; break;
/* 143 */           }  if (mev.getButton() == 1)
/*     */           {
/*     */             
/* 146 */             this._armed = false;
/*     */           }
/*     */           break;
/*     */         
/*     */         case 1:
/* 151 */           if (this._armed && this._pressed) {
/*     */             
/* 153 */             fireAction(mev.getWhen(), mev.getModifiers());
/* 154 */             this._armed = false;
/*     */           } 
/* 156 */           this._pressed = false;
/*     */           break;
/*     */         
/*     */         default:
/* 160 */           return super.dispatchEvent(event);
/*     */       } 
/*     */ 
/*     */       
/* 164 */       int state = getState();
/* 165 */       if (state != ostate) {
/* 166 */         stateDidChange();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 172 */     return super.dispatchEvent(event);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDefaultStyleClass() {
/* 178 */     return "button";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getStateCount() {
/* 184 */     return 4;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStatePseudoClass(int state) {
/* 190 */     if (state >= 3) {
/* 191 */       return STATE_PCLASSES[state - 3];
/*     */     }
/* 193 */     return super.getStatePseudoClass(state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void configureStyle(BStyleSheet style) {
/* 200 */     super.configureStyle(style);
/*     */ 
/*     */     
/* 203 */     if (this._label.getIcon() == null) {
/* 204 */       BIcon icon = style.getIcon((BComponent)this, getStatePseudoClass(0));
/* 205 */       if (icon != null) {
/* 206 */         this._label.setIcon(icon);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fireAction(long when, int modifiers) {
/* 220 */     emitEvent((BEvent)new ActionEvent(this, when, modifiers, this._action));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 227 */   protected static final String[] STATE_PCLASSES = new String[] { "down" };
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BButton2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */