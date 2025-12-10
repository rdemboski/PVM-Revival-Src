/*    */ package com.jmex.bui.dragndrop;
/*    */ 
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BLabel;
/*    */ import com.jmex.bui.BWindow;
/*    */ import com.jmex.bui.BuiSystem;
/*    */ import com.jmex.bui.background.BBackground;
/*    */ import com.jmex.bui.background.BlankBackground;
/*    */ import com.jmex.bui.icon.BIcon;
/*    */ import com.jmex.bui.layout.BLayoutManager;
/*    */ import com.jmex.bui.layout.BorderLayout;
/*    */ 
/*    */ public class BDragIconWindow extends BWindow {
/*    */   public BDragIconWindow() {
/* 15 */     super("dragging-notification-window", BuiSystem.getStyle(), (BLayoutManager)new BorderLayout());
/* 16 */     setBackground(0, (BBackground)new BlankBackground());
/* 17 */     this.iconLabel = new BLabel("");
/* 18 */     add((BComponent)this.iconLabel, BorderLayout.CENTER);
/* 19 */     setLayer(2147483647);
/*    */   }
/*    */   private BLabel iconLabel;
/*    */   public void setIcon(BIcon icon) {
/* 23 */     this.iconLabel.setIcon(icon);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\dragndrop\BDragIconWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */