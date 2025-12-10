/*    */ package com.jmex.bui;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jmex.bui.text.BTextFactory;
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
/*    */ public class TcgLabel
/*    */   extends Label
/*    */ {
/*    */   public TcgLabel(BTextComponent container) {
/* 19 */     super(container);
/*    */   }
/*    */   
/*    */   private boolean isAbsolutePosRequired() {
/* 23 */     BTextFactory bTextFactory = this._container.getTextFactory();
/* 24 */     return bTextFactory instanceof TcgBitmapTextFactory;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(Renderer renderer, int x, int y, int contWidth, int contHeight, float alpha) {
/* 29 */     if (isAbsolutePosRequired()) {
/* 30 */       int absoluteX = this._container.getAbsoluteX();
/* 31 */       int absoluteY = this._container.getAbsoluteY();
/*    */       
/* 33 */       this._tx += absoluteX;
/* 34 */       this._ty += absoluteY;
/* 35 */       renderNoTranslate(renderer, contWidth, contHeight, alpha);
/* 36 */       this._tx -= absoluteX;
/* 37 */       this._ty -= absoluteY;
/*    */     } else {
/* 39 */       super.render(renderer, x, y, contWidth, contHeight, alpha);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void renderNoTranslate(Renderer renderer, int contWidth, int contHeight, float alpha) {
/* 44 */     if (this._icon != null) {
/* 45 */       this._icon.render(renderer, this._ix, this._iy, alpha);
/*    */     }
/* 47 */     if (this._config != null && this._config.glyphs != null) {
/* 48 */       renderText(renderer, contWidth, contHeight, alpha);
/*    */     }
/*    */   }
/*    */   
/*    */   public void copyFrom(Label oldLabel) {
/* 53 */     this._value = oldLabel._value;
/*    */     
/* 55 */     this._orient = oldLabel._orient;
/* 56 */     this._gap = oldLabel._gap;
/* 57 */     this._fit = oldLabel._fit;
/*    */     
/* 59 */     this._icon = oldLabel._icon;
/* 60 */     this._ix = oldLabel._ix;
/* 61 */     this._iy = oldLabel._iy;
/*    */     
/* 63 */     this._config = oldLabel._config;
/* 64 */     this._tx = oldLabel._tx;
/* 65 */     this._ty = oldLabel._ty;
/* 66 */     this._alpha = oldLabel._alpha;
/*    */     
/* 68 */     this._prefconfig = oldLabel._prefconfig;
/* 69 */     this._prefsize = oldLabel._prefsize;
/*    */     
/* 71 */     this._srect = oldLabel._srect;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\TcgLabel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */