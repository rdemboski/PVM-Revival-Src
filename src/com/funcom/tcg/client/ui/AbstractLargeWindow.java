/*    */ package com.funcom.tcg.client.ui;
/*    */ 
/*    */ import com.jmex.bui.BStyleSheet;
/*    */ import com.jmex.bui.layout.BLayoutManager;
/*    */ 
/*    */ public abstract class AbstractLargeWindow extends AbstractFauxWindow {
/*  7 */   protected final int FAUX_WINDOW_WIDTH = 1024;
/*  8 */   protected final int FAUX_WINDOW_HEIGHT = 710;
/*  9 */   protected final int FAUX_WINDOW_OFFSET = 29;
/*    */   
/*    */   public AbstractLargeWindow(String name, BStyleSheet style, BLayoutManager layout) {
/* 12 */     super(name, style, layout);
/*    */   }
/*    */   
/*    */   public AbstractLargeWindow(BStyleSheet style, BLayoutManager layout) {
/* 16 */     super(style, layout);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getWindowHeight() {
/* 21 */     return 710;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getWindowWidth() {
/* 26 */     return 1024;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getWindowOffset() {
/* 31 */     return 29;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\AbstractLargeWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */