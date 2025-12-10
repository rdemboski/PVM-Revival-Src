/*    */ package com.funcom.tcg.client.ui;
/*    */ 
/*    */ import com.funcom.util.SizeCheckedArrayList;
/*    */ import com.jmex.bui.event.ChangeEvent;
/*    */ import com.jmex.bui.event.ChangeListener;
/*    */ import java.util.List;
/*    */ 
/*    */ public abstract class SelectableButtonModel
/*    */ {
/* 10 */   private final List<ChangeListener> listeners = (List<ChangeListener>)new SizeCheckedArrayList(1, getClass().getSimpleName(), 2);
/*    */   
/*    */   public void addChangeListener(ChangeListener changeListener) {
/* 13 */     this.listeners.add(changeListener);
/*    */   }
/*    */   
/*    */   public void fireStateChanged() {
/* 17 */     ChangeEvent changeEvent = new ChangeEvent(this);
/* 18 */     for (ChangeListener listener : this.listeners)
/* 19 */       listener.stateChanged(changeEvent); 
/*    */   }
/*    */   
/*    */   public abstract boolean isSelected();
/*    */   
/*    */   public abstract void setSelected(boolean paramBoolean);
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\SelectableButtonModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */