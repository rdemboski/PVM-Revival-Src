/*   */ package com.jmex.bui.layout;
/*   */ 
/*   */ import com.jmex.bui.BComponent;
/*   */ 
/*   */ public class AbsoluteButChangeableLayout extends AbsoluteLayout {
/*   */   public void setConstraints(BComponent component, Object constraints) {
/* 7 */     this._spots.remove(component);
/* 8 */     this._spots.put(component, constraints);
/*   */   }
/*   */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\layout\AbsoluteButChangeableLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */