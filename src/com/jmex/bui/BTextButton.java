/*    */ package com.jmex.bui;
/*    */ 
/*    */ import com.jmex.bui.event.ActionListener;
/*    */ import com.jmex.bui.icon.BIcon;
/*    */ import com.jmex.bui.util.Dimension;
/*    */ 
/*    */ 
/*    */ public class BTextButton
/*    */   extends BButton
/*    */ {
/*    */   private static final int BORDER_WIDTH = 25;
/*    */   private static final int BORDER_HIGHT = 25;
/*    */   
/*    */   public BTextButton(String s) {
/* 15 */     super(s);
/*    */   }
/*    */   
/*    */   public BTextButton(String s, String s1) {
/* 19 */     super(s, s1);
/*    */   }
/*    */   
/*    */   public BTextButton(String s, ActionListener actionListener, String s1) {
/* 23 */     super(s, actionListener, s1);
/*    */   }
/*    */   
/*    */   public BTextButton(BIcon bIcon, String s) {
/* 27 */     super(bIcon, s);
/*    */   }
/*    */   
/*    */   public BTextButton(BIcon bIcon, ActionListener actionListener, String s) {
/* 31 */     super(bIcon, actionListener, s);
/*    */   }
/*    */   
/*    */   protected Dimension computePreferredSize(int i, int i1) {
/* 35 */     Dimension normalSize = super.computePreferredSize(i, i1);
/* 36 */     normalSize = new Dimension(normalSize.width + 25, normalSize.height + 25);
/* 37 */     return normalSize;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BTextButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */